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

public interface ConfigService {
    
    /**
     * 获取 所有属性
     * @return
     */
    public List<MatchProperty> getAllMatchProperty();

    /**
     * 添加属性 或修改属性
     * @param matchProperty
     * @return  true 返回添加或者修改属性 
     */
    public boolean opeationMatchProperty(MatchProperty matchProperty);

    
    /**
     * 操作单个分值配置项
     * @param matchFactor
     * @return
     */
    public boolean operationMatchFactor(MatchFactor matchFactor);

    /**
     * 操作多个分值配置项
     * @param matchFactors
     * @return
     */
    public boolean  operationMatchFactor(List<MatchFactor> matchFactors);

    /**
     * 获取所有分值配置项
     * @return
     */
    public List<MatchFactor> getAllMatchFator();


    //*************纳排相关操作****************** */

    /**
     * 修改纳排的筛选属性
     * @param matchProperties
     * @return
     */
    public  boolean  operationSelectionProperty(List<MatchProperty> matchProperties);


    /**
     * 获取所有纳排筛选属性
     * @return
     */
    public List<MatchProperty> getAllSelectionProperty();


    /**
     * 疑似分值、合并分值、姓名匹配规则设置
     * @param code  
     * @param grade
     * @return
     */
    public boolean setEmpiconfig(String  code ,String value); 


    /**
     * 获取empi的配置
     * @return
     */
    public Map<String,Object> getEmpiConfig(); 
}
