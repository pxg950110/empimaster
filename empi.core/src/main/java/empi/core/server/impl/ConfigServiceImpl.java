package empi.core.server.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import empi.core.dao.MatchFactorMapper;
import empi.core.dao.MatchPropertyMapper;
import empi.core.init.EmpiProtertyCache;
import empi.core.model.MatchFactor;
import empi.core.model.MatchFactorExample;
import empi.core.model.MatchProperty;
import empi.core.model.MatchPropertyExample;
import empi.core.server.ConfigService;
import empi.core.utils.CommonResult;
import empi.core.utils.CommonUtils;
import empi.core.utils.HttpResultStatus;

/**
 * @author chpxg
 * @version v1.0.0
 * @description 配置服务的实现类
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final static Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);
    @Autowired
    private MatchPropertyMapper matchPropertyMapper;
    @Autowired
    private MatchFactorMapper matchFactorMapper;

    @Override
    public CommonResult<Object> getAllMatchProperty() {
        //
        try {
            // 实例化
            MatchPropertyExample matchPropertyExample = new MatchPropertyExample();
            // 查询有效的属性
            matchPropertyExample.createCriteria().andIsDeletedEqualTo((short) 0);
            // 查询返回数据
            List<MatchProperty> matchProperties = matchPropertyMapper.selectByExample(matchPropertyExample);
            // 返回成功
            return CommonResult.commomResult(matchProperties, HttpResultStatus.STATUS200);
        } catch (Exception e) {
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> opeationMatchProperty(MatchProperty matchProperty) {
        try {
            // 判断属性是否存在
            MatchPropertyExample example = new MatchPropertyExample();
            // 存在有效的条件
            example.createCriteria().andCodeEqualTo(matchProperty.getCode()).andIsDeletedEqualTo((short) 0);
            // 查询数据
            List<MatchProperty> properties = matchPropertyMapper.selectByExample(example);
            // 如果code存在
            if (properties.size() > 0) {
                log.info(properties.toString());
                // 存在只会有一个
                MatchProperty oldMatchProperty = properties.get(0);
                MatchProperty updateMatchProperty = oldMatchProperty;
                // 更新人 更新时间 code不允许更新
                if (!StringUtils.isEmpty(matchProperty.getName())) {
                    updateMatchProperty.setName(matchProperty.getName());
                }
                log.info("传入属性值：{}", matchProperty.getName());
                // 更新人
                if (matchProperty.getUpdateBy() == null) {
                    updateMatchProperty.setUpdateBy(1);
                }
                // 更新时间
                updateMatchProperty.setLastUpdateTime(new Date());
                // 属性是否有效
                if (matchProperty.getIsDeleted() != null) {
                    updateMatchProperty.setIsDeleted(matchProperty.getIsDeleted());
                }
                // 更新数据
                matchPropertyMapper.updateByPrimaryKey(updateMatchProperty);
                return CommonResult.success(true, "修改属性成功");
            } else {
                // 生成数据 验证数据是否为空
                if (StringUtils.isEmpty(matchProperty.getCode()) || StringUtils.isEmpty(matchProperty.getName())) {
                    return CommonResult.commomResult("属性代码和属性名称不允许为空", HttpResultStatus.STATUS400);
                }
                // @ApiModelProperty(value = "创建时间")
                // 创建时间为空 当前时间
                if (matchProperty.getCreateTime() == null) {
                    matchProperty.setCreateTime(new Date());
                }
                // private Integer createBy;
                if (matchProperty.getCreateBy() == null || matchProperty.getCreateBy() == 0) {
                    matchProperty.setCreateBy(1);
                }
                // private Date lastUpdateTime;
                if (matchProperty.getLastUpdateTime() == null) {
                    matchProperty.setLastUpdateTime(new Date());
                }
                // private Integer updateBy;
                if (matchProperty.getUpdateBy() == null || matchProperty.getUpdateBy() == 0) {
                    matchProperty.setUpdateBy(1);
                }
                // private Short isDeleted;
                matchProperty.setIsDeleted((short) 0);
                // 设置为序列+1
                matchProperty.setSort(matchPropertyMapper.queryMaxSort() + 1);
                matchPropertyMapper.insert(matchProperty);
                return CommonResult.success(true, "新增属性成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }

    }

    @Override
    public CommonResult<Object> operationMatchFactor(MatchFactor matchFactor) {
        String uid = CommonUtils.uuid();
        try {
            // 属性code 属性name 加分 减分
            //添加或修改属性和分值
            //获取所有属性
            Map<String, String> propertyMap = EmpiProtertyCache.getPropertyMap();
            //如果属性不存在 检查或重启服务后再调试
            if (propertyMap.get(matchFactor.getPropertyCode()) == null) {
                return CommonResult.commomResult("属性不存在" + matchFactor.getPropertyCode() + "---"
                        + matchFactor.getPropertyName() + ",请重新检查或重启服务", HttpResultStatus.STATUS500);
            }
            //如果属性存在
            //获取属性id code name
            MatchPropertyExample example = new MatchPropertyExample();
            example.createCriteria().andCodeEqualTo(matchFactor.getPropertyCode()).andIsDeletedEqualTo((short) 0);
            MatchProperty matchProperty = matchPropertyMapper.selectByExample(example).get(0);
            //设置属性ID
            matchFactor.setPropertyId(matchProperty.getId());
            //设置属性名称
            matchFactor.setPropertyName(matchProperty.getName());
            // 加分值判断
            Integer positiveGrede = matchFactor.getPositiveGrede();
            if (positiveGrede == null || positiveGrede < 0) {
                return CommonResult.commomResult("加分值允许值为正数", HttpResultStatus.STATUS300);
            }
            //减分 分值 更新也必须要有减分
            Integer negativeGrade = matchFactor.getNegativeGrade();
            if (negativeGrade == null || negativeGrade < 0) {
                //设置分值为0
                matchFactor.setNegativeGrade(0);
            }

            //加入验证是否存在数据 存在更新  不存在 插入
            //
            MatchFactorExample matchFactorExample = new MatchFactorExample();
            //创建 code  is_deleted的查询
            matchFactorExample.createCriteria().andPropertyCodeEqualTo(matchFactor.getPropertyCode()).andIsDeletedEqualTo((short) 0);
            List<MatchFactor> matchFactors = matchFactorMapper.selectByExample(matchFactorExample);
            //
            if (matchFactors.size() > 0) {
                MatchFactor oldMatchFactor = matchFactors.get(0);
                //设置属性开始
                matchFactor.setCreateTime(oldMatchFactor.getCreateTime());
                matchFactor.setCreateBy(oldMatchFactor.getCreateBy());
                matchFactor.setId(oldMatchFactor.getId());
                if (matchFactor.getUpdateBy() == null) {
                    matchFactor.setUpdateBy(1);
                }
                matchFactor.setLastUpdateTime(new Date());
                matchFactor.setIsDeleted((short) 0);
                matchFactor.setThreshold(oldMatchFactor.getThreshold());
                matchFactor.setMatchFunction(oldMatchFactor.getMatchFunction());
                matchFactor.setMatchCondition(oldMatchFactor.getMatchCondition());
                //更新数据()
                matchFactor.setSort(oldMatchFactor.getSort());
                matchFactorMapper.updateByPrimaryKey(matchFactor);
                return CommonResult.commomResult("规则修改成功", HttpResultStatus.STATUS200);

            } else {
                //新数据插入
                if (matchFactor.getCreateBy() == null) {
                    matchFactor.setCreateBy(1);
                }
                matchFactor.setCreateTime(new Date());
                if (matchFactor.getUpdateBy() == null) {
                    matchFactor.setUpdateBy(1);
                }
                matchFactor.setLastUpdateTime(new Date());
                //v1.0.0不加入函数和condition 配置匹配度为1
                matchFactor.setThreshold(BigDecimal.valueOf(1));
                return CommonResult.commomResult("规则写入成功", HttpResultStatus.STATUS200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Transactional
    @Override
    public CommonResult<Object> operationMatchFactor(List<MatchFactor> matchFactors) {
        List<MatchFactor> errors = new ArrayList<>();
        List<MatchFactor> success = new ArrayList<>();
        try {
            //批量添加
            matchFactors.forEach(element -> {
                CommonResult commonResult = operationMatchFactor(element);
                if (commonResult.getCode() == 200) {
                    //成功数组
                    success.add(element);
                } else {
                    //失败数组
                    errors.add(element);
                }
            });
            return CommonResult.commomResult("成功数量：" + success.size()
                    + ",失败数量：" + errors.size(), HttpResultStatus.STATUS200);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> getAllMatchFator() {
        //获取所有的配置
        try {
            //查询状态有效的数据
            MatchFactorExample example = new MatchFactorExample();
            example.createCriteria().andIsDeletedEqualTo((short) 1);
            return CommonResult.commomResult(matchFactorMapper.selectByExample(example), HttpResultStatus.STATUS200);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> operationSelectionProperty(List<MatchProperty> matchProperties) {
        //纳排队列 纳排数据 存放为 json key value

        //纳排队列 要求
        return null;
    }

    @Override
    public CommonResult<Object> getAllSelectionProperty() {
        return null;
    }

    @Override
    public CommonResult<Object> setEmpiconfig(String code, String value) {
        return null;
    }

    @Override
    public CommonResult<Object> getEmpiConfig() {
        return null;
    }

}
