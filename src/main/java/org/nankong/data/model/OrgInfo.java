package org.nankong.data.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

public class OrgInfo implements Serializable {
    @QuerySqlField
    private String orgName;
    @QuerySqlField(index = true)
    private String orgCode;
    private String orgAddress;
    private String orgOpenDate;
    private int orgType;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgOpenDate() {
        return orgOpenDate;
    }

    public void setOrgOpenDate(String orgOpenDate) {
        this.orgOpenDate = orgOpenDate;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public OrgInfo() {
    }

    public OrgInfo(String orgName, String orgCode, String orgAddress, String orgOpenDate, int orgType) {
        this.orgName = orgName;
        this.orgCode = orgCode;
        this.orgAddress = orgAddress;
        this.orgOpenDate = orgOpenDate;
        this.orgType = orgType;
    }
    public OrgInfo(String p9Str) {
        String custStrs[] = p9Str.split("\\|@\\|");
        orgName = custStrs[0];
        orgCode = custStrs[1];
        orgAddress = custStrs[2];
        orgOpenDate = custStrs[3];
        orgType = Integer.parseInt(custStrs[4]);
    }
    public String toP9String(){
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(orgName);
        strBuf.append("|@|");
        strBuf.append(orgCode);
        strBuf.append("|@|");
        strBuf.append(orgAddress);
        strBuf.append("|@|");
        strBuf.append(orgOpenDate);
        strBuf.append("|@|");
        strBuf.append(orgType);
        return strBuf.toString();
    }
}
