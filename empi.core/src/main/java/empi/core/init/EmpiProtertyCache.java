package empi.core.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import empi.core.utils.MatchFactorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import empi.core.dao.MatchFactorMapper;
import empi.core.dao.MatchPropertyMapper;
import empi.core.model.MatchFactor;
import empi.core.model.MatchFactorExample;
import empi.core.model.MatchProperty;
import empi.core.model.MatchPropertyExample;
import empi.core.utils.CommonUtils;

/**
 * empi初始化加载 属性缓存
 */
@Component
@Order(1)
public class EmpiProtertyCache implements CommandLineRunner {

    /**
     * slf4j日志
     */
    private static final Logger log = LoggerFactory.getLogger(EmpiProtertyCache.class);

    /**
     * 程序启动后缓存
     */
    private static Map<String, String> cacheMap = new HashMap<String, String>();

    //获取所有属性
    @Autowired
    private MatchPropertyMapper matchPropertyMapper;
    @Autowired
    private MatchFactorMapper matchFactorMapper;


    //存放所有属性的map
    private static Map<String, String> propertyMap = new HashMap<>();
    /**
     * 存放分值的map
     */
    private static Map<String, MatchFactorUtil> matchFactorUtilMap = new HashMap<String, MatchFactorUtil>();


    public static Map<String, String> getPropertyMap() {
        return propertyMap;
    }


    public static Map<String, String> getCacheMap() {
        return cacheMap;
    }

    @Override
    public void run(String... args) throws Exception {
        //创建UUID 属性绑定唯一ID  日志
        String uid = CommonUtils.uuid();
        log.info(">>{}-->开始查询配置属性", uid);
        log.info(">>{}-->查询empi属性值开始", uid);
        try {
            MatchPropertyExample matchPropertyExample = new MatchPropertyExample();
            matchPropertyExample.createCriteria().andIsDeletedEqualTo((short) 0);
            List<MatchProperty> matchProperties = matchPropertyMapper.selectByExample(matchPropertyExample);
            matchProperties.forEach(element -> {
                //属性map添加值 code-name
                propertyMap.put(element.getCode(), element.getName());
            });
            log.info(">>{}--> 查询到属性 {}", uid, matchProperties.toString());
            log.info(">>{}-->查询empi属性值结束", uid);
            //empi分子权重
            log.info(">>{}-->查询empi分子权重开始", uid);
            MatchFactorExample matchFactorExample = new MatchFactorExample();
            matchFactorExample.createCriteria().andIsDeletedEqualTo((short) 0);
            List<MatchFactor> matchFactors = matchFactorMapper.selectByExample(matchFactorExample);
            log.info(">>{}-->{}", uid, matchFactors.toString());
            matchFactors.forEach(
                    matchFactor -> {
                        //添加属性和值
                        matchFactorUtilMap.put(matchFactor.getPropertyCode(), new MatchFactorUtil(
                                matchFactor.getId(),
                                matchFactor.getPropertyName(),
                                matchFactor.getPositiveGrede(),
                                matchFactor.getNegativeGrade()
                        ));
                    }
            );
            //获取所有分值配置
            log.info(">>{}-->查询empi分子权重结束", uid);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("{}-->数据库查询失败,初始化缓存失败，请重新初始化", uid);
            log.error("系统启动失败请检查数据库配置");
            System.exit(0);
        }

        log.info("{}-->属性值为：{}", uid, propertyMap.toString());
        log.info("{}-->匹配规则为:{}", uid, matchFactorUtilMap.toString());
        System.out.println("加载配置" + EmpiProtertyCache.class);
    }

}
