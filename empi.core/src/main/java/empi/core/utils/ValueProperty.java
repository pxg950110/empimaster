package empi.core.utils;

/**
 * <p>
 * 2020/11/29  0:39
 * </p>
 * <p>
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/11/29
 * @Version 1.0.0
 * @description </p>
 */
public class ValueProperty {
    String code;
    String value;

    public ValueProperty() {
    }

    public ValueProperty(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueProperty{" +
                "code='" + code + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
