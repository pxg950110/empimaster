package empi.core.server;
/**
 * @author chpxg
 * @see 配置属性的服务层
 * @version v1.0.0
 */

import java.util.List;
import java.util.Map;


import empi.core.model.MatchFactor;
import empi.core.model.MatchProperty;
import empi.core.utils.CommonResult;

public interface ConfigService {
    
    /**
     * 获取 所有属性
     * @return
     */
    public CommonResult<Object> getAllMatchProperty();

    /**
     * 添加属性 或修改属性
     * @param matchProperty
     * @return  true 返回添加或者修改属性 
     */
    public CommonResult<Object> operationMatchProperty(MatchProperty matchProperty);


    public CommonResult<Object> deleteMatchProperty(MatchProperty matchProperty);
    /**
     * 操作单个分值配置项
     * @param matchFactor
     * @return
     */
    public CommonResult<Object> operationMatchFactor(MatchFactor matchFactor);

    /**
     * 操作多个分值配置项
     * @param matchFactors
     * @return
     */
    public CommonResult<Object>  operationMatchFactor(List<MatchFactor> matchFactors);


    /**
     * 删除单个属性配置分值
     * @param matchFactor
     * @return
     */
    public CommonResult<Object> deleteMatchFactor(MatchFactor matchFactor);
    /**
     * 获取所有分值配置项
     * @return
     */
    public CommonResult<Object> getAllMatchFator();


    //*************纳排相关操作****************** */

    /**
     * 修改纳排的筛选属性
     * @param matchProperties
     * @return
     */
    public  CommonResult<Object>  operationSelectionProperty(List<MatchProperty> matchProperties);


    /**
     * 获取所有纳排筛选属性
     * @return
     */
    public CommonResult<Object> getAllSelectionProperty();


    /**
     * 疑似分值、合并分值、姓名匹配规则设置
     * @param code  
     * @param matchProperties
     * @return
     */
    public CommonResult<Object> setEmpiconfig(String  code , List<MatchProperty> matchProperties);


    /**
     * 获取empi的配置
     * @return
     */
    public CommonResult<Object> getEmpiConfig(String code);


    /**
     * 生成调用api的json样例
     * @return
     */
    public CommonResult<Object> createAPIJsonSample();

    /**
     * 生成xml样例
     * @return
     */
    public CommonResult<Object> createAPIXmlSample();
}
