package com.store59.kylin.dorm.data.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Dorm{
    private Integer dormId;

    private Integer sid;

    private Integer uid;

    private String name;

    private Byte gender;

    private Integer packageId;

    private String phone;

    private String deliveryAddress;

    private Integer jdAddress1;

    private Integer jdAddress2;

    private Integer jdAddress3;

    private Integer jdAddress4;

    private String jdAddressDetail;

    private String jdAddressFull;

    private Integer zipCode;

    private Integer siteId;

    private String aliAccount;

    private String aliName;

    private Integer addTime;

    private Byte isNew;

    private BigDecimal money;

    private BigDecimal overdraft;

    private Integer parentStaffId;

    private Integer dhid;

    private String notice;

    private Boolean isPass;

    private String idcardPic;

    private String studentcardPic;

    public Integer getDormId() {
        return dormId;
    }

    public void setDormId(Integer dormId) {
        this.dormId = dormId;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    public Integer getJdAddress1() {
        return jdAddress1;
    }

    public void setJdAddress1(Integer jdAddress1) {
        this.jdAddress1 = jdAddress1;
    }

    public Integer getJdAddress2() {
        return jdAddress2;
    }

    public void setJdAddress2(Integer jdAddress2) {
        this.jdAddress2 = jdAddress2;
    }

    public Integer getJdAddress3() {
        return jdAddress3;
    }

    public void setJdAddress3(Integer jdAddress3) {
        this.jdAddress3 = jdAddress3;
    }

    public Integer getJdAddress4() {
        return jdAddress4;
    }

    public void setJdAddress4(Integer jdAddress4) {
        this.jdAddress4 = jdAddress4;
    }

    public String getJdAddressDetail() {
        return jdAddressDetail;
    }

    public void setJdAddressDetail(String jdAddressDetail) {
        this.jdAddressDetail = jdAddressDetail == null ? null : jdAddressDetail.trim();
    }

    public String getJdAddressFull() {
        return jdAddressFull;
    }

    public void setJdAddressFull(String jdAddressFull) {
        this.jdAddressFull = jdAddressFull == null ? null : jdAddressFull.trim();
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(String aliAccount) {
        this.aliAccount = aliAccount == null ? null : aliAccount.trim();
    }

    public String getAliName() {
        return aliName;
    }

    public void setAliName(String aliName) {
        this.aliName = aliName == null ? null : aliName.trim();
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Byte getIsNew() {
        return isNew;
    }

    public void setIsNew(Byte isNew) {
        this.isNew = isNew;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(BigDecimal overdraft) {
        this.overdraft = overdraft;
    }

    public Integer getParentStaffId() {
        return parentStaffId;
    }

    public void setParentStaffId(Integer parentStaffId) {
        this.parentStaffId = parentStaffId;
    }

    public Integer getDhid() {
        return dhid;
    }

    public void setDhid(Integer dhid) {
        this.dhid = dhid;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice == null ? null : notice.trim();
    }

    public Boolean getIsPass() {
        return isPass;
    }

    public void setIsPass(Boolean isPass) {
        this.isPass = isPass;
    }

    public String getIdcardPic() {
        return idcardPic;
    }

    public void setIdcardPic(String idcardPic) {
        this.idcardPic = idcardPic == null ? null : idcardPic.trim();
    }

    public String getStudentcardPic() {
        return studentcardPic;
    }

    public void setStudentcardPic(String studentcardPic) {
        this.studentcardPic = studentcardPic == null ? null : studentcardPic.trim();
    }

}
