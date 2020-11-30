package empi.core.utils.apisample;

import org.yaml.snakeyaml.events.Event;

/**
 * <p>
 * 2020/11/30  13:33
 * </p>
 * <p>
 * @author pxg
 * @emil pxg950110@163.com
 * @Date 2020/11/30
 * @Version 1.0.0
 * @description </p>
 */
public class ApiSample {
    String key;
    String code;
    String name;
    String value;

    public ApiSample() {
    }
    public ApiSample(String key, String code, String name, String value) {
        this.key = key;
        this.code = code;
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ApiSample{" +
                "key='" + key + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
