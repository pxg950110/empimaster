package empi.core.config;

/**
 * <p>
 * 2020/11/26  22:52
 * </p>
 * <p>
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/11/26
 * @Version 1.0.0
 * @description </p>
 */
public class SystemConstant {

    /**
     * empi筛选属性
     */
    public final static String EMPI_SELECTION_KEY = "EMPI_SELECTION_KEY";
    /**
     * empi 主标识  主标识用于匹配 和唯一标识 主标识只允许有一个 且一家医院设置中只允许一个属性设置为主标识
     */
    public final static String EMPI_PRIMARY_KEY = "EMPI_PRIMARY_KEY";
    /**
     * 副标识 可以设置多个
     */
    public final static String EMPI_SECONDARY_KEY = "EMPI_SECONDARY_KEY";
    //姓名匹配规则  ZH中文 EN英文 PY 拼音码
    public final static String EMPI_NAME_KEY = "EMPI_NAME_KEY";
}
