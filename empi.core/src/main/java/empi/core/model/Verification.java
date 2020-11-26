package empi.core.model;

import java.util.Map;
/**
 * 验证实体类
 */
public class Verification {

    // 标识id
    private String oId;

    // 属性代码(所有属性代码要保证唯一性)
    private String prepertyCode;

    // 属性值
    private String value;

    // 主标识代码
    private String firstSignCode;
    // 患者id
    private String firstSign;

    // 副标识的map code value 可有多个副标识
    private Map<String, String> secondarySignMap;

    // 待验证的map
    private Map<String, String> verificationValueMap;

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getPrepertyCode() {
        return prepertyCode;
    }

    public void setPrepertyCode(String prepertyCode) {
        this.prepertyCode = prepertyCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFirstSignCode() {
        return firstSignCode;
    }

    public void setFirstSignCode(String firstSignCode) {
        this.firstSignCode = firstSignCode;
    }

    public String getFirstSign() {
        return firstSign;
    }

    public void setFirstSign(String firstSign) {
        this.firstSign = firstSign;
    }

    public Map<String, String> getSecondarySignMap() {
        return secondarySignMap;
    }

    public void setSecondarySignMap(Map<String, String> secondarySignMap) {
        this.secondarySignMap = secondarySignMap;
    }

    public Map<String, String> getVerificationValueMap() {
        return verificationValueMap;
    }

    public void setVerificationValueMap(Map<String, String> verificationValueMap) {
        this.verificationValueMap = verificationValueMap;
    }

    @Override
    public String toString() {
        return "Verification [firstSign=" + firstSign + ", firstSignCode=" + firstSignCode + ", oId=" + oId
                + ", prepertyCode=" + prepertyCode + ", secondarySignMap=" + secondarySignMap + ", value=" + value
                + ", verificationValueMap=" + verificationValueMap + "]";
    }



    
}
