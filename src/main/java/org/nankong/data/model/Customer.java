package org.nankong.data.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

public class Customer implements Serializable {
    @QuerySqlField
    private String custId;
    @QuerySqlField(index = true)
    private String custName;
    private String custBirthday;
    private int custSex;
    private String custCardId;
    private int isValid;
    private String custNation;
    private float custAum;
    @QuerySqlField(index = true)
    private String custInsId;

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustBirthday() {
        return custBirthday;
    }

    public void setCustBirthday(String custBirthday) {
        this.custBirthday = custBirthday;
    }

    public int getCustSex() {
        return custSex;
    }

    public void setCustSex(int custSex) {
        this.custSex = custSex;
    }

    public String getCustCardId() {
        return custCardId;
    }

    public void setCustCardId(String custCardId) {
        this.custCardId = custCardId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public String getCustNation() {
        return custNation;
    }

    public void setCustNation(String custNation) {
        this.custNation = custNation;
    }

    public float getCustAum() {
        return custAum;
    }

    public void setCustAum(float custAum) {
        this.custAum = custAum;
    }

    public String getCustInsId() {
        return custInsId;
    }

    public void setCustInsId(String custInsId) {
        this.custInsId = custInsId;
    }

    public Customer(String custId, String custName, String custBirthday, int custSex, String custCardId, int isValid, String custNation, float custAum, String custInsId) {
        this.custId = custId;
        this.custName = custName;
        this.custBirthday = custBirthday;
        this.custSex = custSex;
        this.custCardId = custCardId;
        this.isValid = isValid;
        this.custNation = custNation;
        this.custAum = custAum;
        this.custInsId = custInsId;
    }

    public Customer() {
    }
    public String toP9String(){
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(custId);
        strBuf.append("|@|");
        strBuf.append(custName);
        strBuf.append("|@|");
        strBuf.append(custBirthday);
        strBuf.append("|@|");
        strBuf.append(custSex);
        strBuf.append("|@|");
        strBuf.append(custCardId);
        strBuf.append("|@|");
        strBuf.append(isValid);
        strBuf.append("|@|");
        strBuf.append(custNation);
        strBuf.append("|@|");
        strBuf.append(custAum);
        strBuf.append("|@|");
        strBuf.append(custInsId);
        strBuf.append("|@|");
        return strBuf.toString();
    }
    public Customer(String p9Str) {
        String custStrs[] = p9Str.split("\\|@\\|");
        custId = custStrs[0];
        custName = custStrs[1];
        custBirthday = custStrs[2];
        custSex = Integer.parseInt(custStrs[3]);
        custCardId = custStrs[4];
        isValid = Integer.parseInt(custStrs[5]);
        custNation = custStrs[6];
        custAum = Float.parseFloat(custStrs[7]);
        custInsId = custStrs[8];
    }
}
