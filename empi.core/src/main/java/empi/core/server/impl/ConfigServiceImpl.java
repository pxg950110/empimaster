package empi.core.server.impl;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import empi.core.config.SystemConstant;
import empi.core.dao.EmpiConfigMapper;
import empi.core.dao.MatchFactorMapper;
import empi.core.dao.MatchPropertyMapper;
import empi.core.init.EmpiProtertyCache;
import empi.core.model.*;
import empi.core.server.ConfigService;
import empi.core.utils.CommonResult;
import empi.core.utils.CommonUtils;
import empi.core.utils.HttpResultStatus;
import empi.core.utils.ValueProperty;
import empi.core.utils.apisample.ApiSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.*;

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

    @Autowired
    private EmpiConfigMapper empiConfigMapper;

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
    public CommonResult<Object> operationMatchProperty(MatchProperty matchProperty) {
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
    public CommonResult<Object> deleteMatchProperty(MatchProperty matchProperty) {
        return null;
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
                matchFactor.setIsDeleted((short) 0);
                matchFactorMapper.insert(matchFactor);
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

    /**
     * 删除单个属性配置分值
     * @param matchFactor
     * @return
     */
    @Override
    public CommonResult<Object> deleteMatchFactor(MatchFactor matchFactor) {
        if (matchFactor.getId() == null) {
            if (StringUtils.isEmpty(matchFactor.getPropertyCode())) {
                return CommonResult.commomResult("移除的属性不存在请确认", HttpResultStatus.STATUS500);
            }
            //通过code获取id
            MatchFactorExample example = new MatchFactorExample();
            example.createCriteria().andIsDeletedEqualTo((short) 0).andPropertyCodeEqualTo(matchFactor.getPropertyCode());
            List<MatchFactor> matchFactors = matchFactorMapper.selectByExample(example);
            if (matchFactors.size() == 0) {
                return CommonResult.commomResult("移除的属性不存在请确认", HttpResultStatus.STATUS500);
            }
            matchFactor.setId(matchFactors.get(0).getId());
        }
        //通过id更新数据
        //查询出数据
        MatchFactor oldMatchFactor = matchFactorMapper.selectByPrimaryKey(matchFactor.getId());
        //删除数据 更新is_deleted字段为错误
        oldMatchFactor.setIsDeleted((short) 1);
        oldMatchFactor.setUpdateBy(1);
        oldMatchFactor.setLastUpdateTime(new Date());
        //更新数据
        matchFactorMapper.updateByPrimaryKey(oldMatchFactor);
        //返回
        return CommonResult.commomResult("配置属性移除[" + oldMatchFactor.getPropertyName() + "]成功", HttpResultStatus.STATUS200);
    }

    @Override
    public CommonResult<Object> getAllMatchFator() {
        //获取所有的配置
        try {
            //查询状态有效的数据
            MatchFactorExample example = new MatchFactorExample();
            example.createCriteria().andIsDeletedEqualTo((short) 0);
            return CommonResult.commomResult(matchFactorMapper.selectByExample(example), HttpResultStatus.STATUS200);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> operationSelectionProperty(List<MatchProperty> matchProperties) {
        //纳排队列 纳排数据 存放为 json key value
        String uid = CommonUtils.uuid();
        //
        List<ValueProperty> valuePropertyArrayList = new ArrayList<>();
        final boolean[] errorFlag = {false};
        matchProperties.forEach(
                matchProperty -> {
                    if (StringUtils.isEmpty(matchProperty.getCode())) {
                        errorFlag[0] = true;
                    } else if (
                            !EmpiProtertyCache.getPropertyMap().containsKey(matchProperty.getCode())
                    ) {//不在配置属性中
                        errorFlag[0] = true;
                    } else {
                        valuePropertyArrayList.add(new ValueProperty(
                                matchProperty.getCode(),
                                matchProperty.getName() == null ? EmpiProtertyCache.getPropertyMap().get(matchProperty.getCode()) : matchProperty.getName()
                        ));
                    }
                }
        );
        //如果有code为空
        if (errorFlag[0]) {
            return CommonResult.commomResult("配置属性错误请检查", HttpResultStatus.STATUS500);
        }
        try {
            // logInfo
            log.info("{}==>配置属性为=>*{}*", uid, CommonUtils.ObjectToJSONString(valuePropertyArrayList));
            // 生成 筛选属性的json
            // 更新属性
            //获取现有属性
            EmpiConfigExample example = new EmpiConfigExample();
            example.createCriteria().andConfigCodeEqualTo(SystemConstant.EMPI_SELECTION_KEY).andIsDeletedEqualTo(0);
            EmpiConfig empiConfig = empiConfigMapper.selectByExample(example).get(0);
            empiConfig.setValue(CommonUtils.ObjectToJSONString(valuePropertyArrayList));
            empiConfig.setUpdateBy(1);
            empiConfig.setUpdateTime(new Date());
            empiConfigMapper.updateByPrimaryKey(empiConfig);
            return CommonResult.commomResult("更新筛选属性成功", HttpResultStatus.STATUS200);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            //错误返回
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> getAllSelectionProperty() {
        EmpiConfigExample example = new EmpiConfigExample();
        try {
            example.createCriteria().andIsDeletedEqualTo(0).andConfigCodeEqualTo(SystemConstant.EMPI_SELECTION_KEY);
            EmpiConfig empiConfig = empiConfigMapper.selectByExample(example).get(0);
            JSONArray valueProperties = JSON.parseArray(empiConfig.getValue());
            return CommonResult.commomResult(valueProperties, HttpResultStatus.STATUS200);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> setEmpiconfig(String code, List<MatchProperty> matchProperties) {
        if (code.equals(SystemConstant.EMPI_PRIMARY_KEY)) {
            //设置主标识 只允许有一个
            if (matchProperties.size() != 1) {
                return CommonResult.commomResult("主标识只允许有一个", HttpResultStatus.STATUS500);
            }
            //
            MatchProperty matchProperty = matchProperties.get(0);
            if (!EmpiProtertyCache.getPropertyMap().containsKey(matchProperty.getCode())) {
                return CommonResult.commomResult("配置的主标识不存在属性中", HttpResultStatus.STATUS500);
            }
            ValueProperty valueProperty = new ValueProperty(matchProperty.getCode(), matchProperty.getName() == null ? EmpiProtertyCache.getPropertyMap().get(matchProperty.getCode()) : matchProperty.getName());
            List<ValueProperty> valueProperties = new ArrayList<>();
            valueProperties.add(valueProperty);
            //更新数据
            EmpiConfigExample example = new EmpiConfigExample();
            example.createCriteria().andConfigCodeEqualTo(SystemConstant.EMPI_PRIMARY_KEY).andIsDeletedEqualTo(0);
            EmpiConfig empiConfig = empiConfigMapper.selectByExample(example).get(0);
            //更新数据
            empiConfig.setValue(CommonUtils.ObjectToJSONString(valueProperties));
            empiConfig.setUpdateBy(1);
            empiConfig.setUpdateTime(new Date());
            empiConfig.setUpdateTime(new Date());
            //更新数据
            empiConfigMapper.updateByPrimaryKey(empiConfig);
            return CommonResult.commomResult("更新主标识成功", HttpResultStatus.STATUS200);

        } else if (code.equals(SystemConstant.EMPI_SECONDARY_KEY)) {//副标识设置


            //
            List<ValueProperty> valuePropertyArrayList = new ArrayList<>();
            final boolean[] errorFlag = {false};
            matchProperties.forEach(
                    matchProperty -> {
                        if (StringUtils.isEmpty(matchProperty.getCode())) {
                            errorFlag[0] = true;
                        } else if (
                                !EmpiProtertyCache.getPropertyMap().containsKey(matchProperty.getCode())
                        ) {//不在配置属性中
                            errorFlag[0] = true;
                        } else {
                            valuePropertyArrayList.add(new ValueProperty(
                                    matchProperty.getCode(),
                                    matchProperty.getName() == null ? EmpiProtertyCache.getPropertyMap().get(matchProperty.getCode()) : matchProperty.getName()
                            ));
                        }
                    }
            );
            //如果有code为空
            if (errorFlag[0]) {
                return CommonResult.commomResult("配置属性错误请检查", HttpResultStatus.STATUS500);
            }
            try {
                // 生成 筛选属性的json
                // 更新属性
                //获取现有属性
                EmpiConfigExample example = new EmpiConfigExample();
                example.createCriteria().andConfigCodeEqualTo(SystemConstant.EMPI_SECONDARY_KEY).andIsDeletedEqualTo(0);
                EmpiConfig empiConfig = empiConfigMapper.selectByExample(example).get(0);
                empiConfig.setValue(CommonUtils.ObjectToJSONString(valuePropertyArrayList));
                empiConfig.setUpdateBy(1);
                empiConfig.setUpdateTime(new Date());
                empiConfigMapper.updateByPrimaryKey(empiConfig);
                return CommonResult.commomResult("更新副标识成功", HttpResultStatus.STATUS200);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                //错误返回
                return CommonResult.commomResult(e.getMessage(), HttpResultStatus.STATUS500);
            }
        } else if (code.equals(SystemConstant.EMPI_NAME_KEY)) {//姓名格式

            return null;
        } else {
            return CommonResult.commomResult("code错误", HttpResultStatus.STATUS500);
        }
    }

    @Override
    public CommonResult<Object> getEmpiConfig(String code) {
//        String key="";
        if (code.equals(SystemConstant.EMPI_PRIMARY_KEY) || code.equals(SystemConstant.EMPI_SECONDARY_KEY)) {
            //
            EmpiConfigExample example = new EmpiConfigExample();
            example.createCriteria().andIsDeletedEqualTo(0).andConfigCodeEqualTo(code);
            EmpiConfig empiConfig = empiConfigMapper.selectByExample(example).get(0);
            return CommonResult.commomResult(JSON.parseArray(empiConfig.getValue()), HttpResultStatus.STATUS200);
        } else {
            //暂不支持吧
            return null;
        }
    }


    /**
     * 生成调用api的json样例
     * @return
     */
    @Override
    public CommonResult<Object> createAPIJsonSample() {
        Map<String, Object> map = new HashMap<>();
        //  主标识
        //生成primaryKey
        //
        EmpiConfigExample example = new EmpiConfigExample();
        example.createCriteria().andIsDeletedEqualTo(0).andConfigCodeEqualTo(SystemConstant.EMPI_PRIMARY_KEY);
        EmpiConfig empiConfig = empiConfigMapper.selectByExample(example).get(0);
        //获取key
        //生成属性值
        List<ValueProperty> valueProperties = JSON.parseArray(empiConfig.getValue()).toJavaList(ValueProperty.class);
        //生成key
        ApiSample primarySample = new ApiSample(SystemConstant.EMPI_PRIMARY_KEY, valueProperties.get(0).getCode()
                , valueProperties.get(0).getValue(), "");
        // primaryKey
        map.put(SystemConstant.EMPI_PRIMARY_KEY, primarySample);

        //  副标识
        //
        EmpiConfigExample example1 = new EmpiConfigExample();
        example1.createCriteria().andConfigCodeEqualTo(SystemConstant.EMPI_SECONDARY_KEY).andIsDeletedEqualTo(0);
        EmpiConfig secondaryEmpiConfig = empiConfigMapper.selectByExample(example1).get(0);
        log.info(CommonUtils.ObjectToJSONString(secondaryEmpiConfig));
        //
        List<ValueProperty> valuePropertieList = JSON.parseArray(secondaryEmpiConfig.getValue()).toJavaList(ValueProperty.class);
        //
//        map.put("secondaryKey","");
        //
        List<ApiSample> secondarySamples = new ArrayList<>();
        valuePropertieList.forEach(
                valueProperty1 -> {
                    secondarySamples.add(new ApiSample(SystemConstant.EMPI_SECONDARY_KEY, valueProperty1.getCode(),
                            valueProperty1.getValue(), ""));
                }
        );
        map.put(SystemConstant.EMPI_SECONDARY_KEY, secondarySamples);
        // 1.0暂时不加入字典
        // 纳排属性
        //获取纳排属性
        EmpiConfigExample selectionExample = new EmpiConfigExample();
        selectionExample.createCriteria().andIsDeletedEqualTo(0).andConfigCodeEqualTo(SystemConstant.EMPI_SELECTION_KEY);
        List<ValueProperty> selectionValueProperties = JSON.parseArray(empiConfigMapper.selectByExample(selectionExample).get(0).getValue()).toJavaList(ValueProperty.class);
        List<ApiSample> selectionApiSamples = new ArrayList<>();
        selectionValueProperties.forEach(
                selectionValuePropert -> {
                    selectionApiSamples.add(new ApiSample(SystemConstant.EMPI_SELECTION_KEY,
                            selectionValuePropert.getCode()
                            , selectionValuePropert.getValue(), ""));
                }
        );
        //  分值匹配属性
        map.put(SystemConstant.EMPI_SELECTION_KEY, selectionApiSamples);
        log.info(CommonUtils.ObjectToJSONString(map));
        //获取matchConfig
        MatchFactorExample example2 = new MatchFactorExample();
        example2.createCriteria().andIsDeletedEqualTo((short) 0);
        List<MatchFactor> matchFactors = matchFactorMapper.selectByExample(example2);
        //json样例 后期加入valueType
        matchFactors.forEach(
                matchFactor -> {
                    map.put(matchFactor.getPropertyCode(), new ApiSample(matchFactor.getPropertyCode(), matchFactor.getPropertyCode(),
                            matchFactor.getPropertyName(), ""));
                }
        );
        return CommonResult.commomResult(map, HttpResultStatus.STATUS200);
    }

    /**
     * 生成xml样例
     * @return
     */
    @Override
    public CommonResult<Object> createAPIXmlSample() {
        return null;
    }
}
