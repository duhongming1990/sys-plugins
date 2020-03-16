package com.github.plugin.sysdict.bean;

import java.util.Date;

public class SysDict {
    private Integer id;

    private String typeCode;

    private String typeName;

    private String dictName;

    private String dictEnName;

    private String dictValue;

    private String dictFullName;

    private String dictFullEnName;

    private String dictFullValue;

    private Integer displayOrder;

    private String parentId;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private Byte status;

    private Byte isSystem;

    public SysDict(Integer id, String typeCode, String typeName, String dictName, String dictEnName, String dictValue, String dictFullName, String dictFullEnName, String dictFullValue, Integer displayOrder, String parentId, String createBy, Date createDate, String updateBy, Date updateDate, Byte status, Byte isSystem) {
        this.id = id;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.dictName = dictName;
        this.dictEnName = dictEnName;
        this.dictValue = dictValue;
        this.dictFullName = dictFullName;
        this.dictFullEnName = dictFullEnName;
        this.dictFullValue = dictFullValue;
        this.displayOrder = displayOrder;
        this.parentId = parentId;
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
        this.status = status;
        this.isSystem = isSystem;
    }

    public SysDict() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName == null ? null : dictName.trim();
    }

    public String getDictEnName() {
        return dictEnName;
    }

    public void setDictEnName(String dictEnName) {
        this.dictEnName = dictEnName == null ? null : dictEnName.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    public String getDictFullName() {
        return dictFullName;
    }

    public void setDictFullName(String dictFullName) {
        this.dictFullName = dictFullName == null ? null : dictFullName.trim();
    }

    public String getDictFullEnName() {
        return dictFullEnName;
    }

    public void setDictFullEnName(String dictFullEnName) {
        this.dictFullEnName = dictFullEnName == null ? null : dictFullEnName.trim();
    }

    public String getDictFullValue() {
        return dictFullValue;
    }

    public void setDictFullValue(String dictFullValue) {
        this.dictFullValue = dictFullValue == null ? null : dictFullValue.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Byte isSystem) {
        this.isSystem = isSystem;
    }
}