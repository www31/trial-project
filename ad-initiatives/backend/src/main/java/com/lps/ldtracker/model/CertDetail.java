package com.lps.ldtracker.model;

import java.util.Date;

public class CertDetail {
    private int employeeNum;
    private String name;
    private String teamName;
    private String certName;
    private String certLink;
    private Date expiryDate;
    private String status;
    private String fileContent;
    private String teamId;
    private String teamCode;
    private String certId;
    
    public String getTeamId() {
        return teamId;
    }

 
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }
    

    public String getCertId() {
        return certId;
    }


    public void setCertId(String certId) {
        this.certId = certId;
    }
    
    public int getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(int employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getCertLink() {
        return certLink;
    }

    public void setCertLink(String certLink) {
        this.certLink = certLink;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
