package empi.core.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MatchFactor implements Serializable {
    @ApiModelProperty(value = "自增主键")
    private Integer id;

    @ApiModelProperty(value = "属性ID")
    private Integer propertyId;

    @ApiModelProperty(value = "正分数-加分")
    private Integer positiveGrede;

    @ApiModelProperty(value = "负分数-减分")
    private Integer negativeGrade;

    @ApiModelProperty(value = "匹配度 默认为 1 完全匹配")
    private BigDecimal threshold;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value = "更新人")
    private Integer updateBy;

    @ApiModelProperty(value = "删除标志 0有效  1删除")
    private Short isDeleted;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    @ApiModelProperty(value = "属性代码")
    private String propertyCode;

    @ApiModelProperty(value = "属性名称")
    private String propertyName;

    @ApiModelProperty(value = "匹配使用的函数")
    private String matchFunction;

    @ApiModelProperty(value = "匹配的条件")
    private String matchCondition;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
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

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal i) {
        this.threshold = i;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getMatchFunction() {
        return matchFunction;
    }

    public void setMatchFunction(String matchFunction) {
        this.matchFunction = matchFunction;
    }

    public String getMatchCondition() {
        return matchCondition;
    }

    public void setMatchCondition(String matchCondition) {
        this.matchCondition = matchCondition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", propertyId=").append(propertyId);
        sb.append(", positiveGrede=").append(positiveGrede);
        sb.append(", negativeGrade=").append(negativeGrade);
        sb.append(", threshold=").append(threshold);
        sb.append(", createTime=").append(createTime);
        sb.append(", createBy=").append(createBy);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", sort=").append(sort);
        sb.append(", propertyCode=").append(propertyCode);
        sb.append(", propertyName=").append(propertyName);
        sb.append(", matchFunction=").append(matchFunction);
        sb.append(", matchCondition=").append(matchCondition);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}