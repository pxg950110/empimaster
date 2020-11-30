package empi.core.utils;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 2020/11/28  18:54
 * </p>
 * <p>
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/11/28
 * @Version 1.0.0
 * @description  缓存使用 </p>
 */
public class MatchFactorUtil {
    @ApiModelProperty(value = "自增主键")
    private Integer id;
    @ApiModelProperty(value = "属性名称")
    private String propertyName;
    @ApiModelProperty(value = "正分数-加分")
    private Integer positiveGrede;
    @ApiModelProperty(value = "负分数-减分")
    private Integer negativeGrade;


    public MatchFactorUtil() {
    }

    public MatchFactorUtil(Integer id, String propertyName, Integer positiveGrede, Integer negativeGrade) {
        this.id = id;
        this.propertyName = propertyName;
        this.positiveGrede = positiveGrede;
        this.negativeGrade = negativeGrade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositiveGrede() {
        return positiveGrede;
    }

    public void setPositiveGrede(Integer positiveGrede) {
        this.positiveGrede = positiveGrede;
    }

    public Integer getNegativeGrade() {
        return negativeGrade;
    }

    public void setNegativeGrade(Integer negativeGrade) {
        this.negativeGrade = negativeGrade;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return "MatchFactorUtil{" +
                "id=" + id +
                ", positiveGrede=" + positiveGrede +
                ", negativeGrade=" + negativeGrade +
                ", propertyName='" + propertyName + '\'' +
                '}';
    }
}
