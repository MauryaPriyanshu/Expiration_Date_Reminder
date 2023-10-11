package com.foodwastemanagement.Model;

import java.io.Serializable;

/**
 * Created by hp on 1/24/2019.
 */

public class Event implements Serializable {
    private String e_id;
    private String eId;
    private String eTime;
    private String eDate;
    private String eType;
    private String eCount;
    private String eStatus;
    private String eImage;
    private String eRemark;
    private String eMsg;
    private String eResID;
    private String eUserID;
    private String eEmpName;
    private String eEmpMobile;
    private String eEmpIDProof;
    private String eEmpMsg;
    private String eNgoToken;
    private String eResToken;


    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String geteResID() {
        return eResID;
    }

    public void seteResID(String eresId) {
        this.eResID = eresId;
    }

    public String geteUserID() {
        return eUserID;
    }

    public void seteUserID(String euserId) {
        this.eUserID = euserId;
    }




    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String geteCount() {
        return eCount;
    }

    public void seteCount(String eCount) {
        this.eCount = eCount;
    }

    public String geteImage() {
        return eImage;
    }
    public void seteImage(String eImage) {
        this.eImage = eImage;
    }

    public String geteStatus() {
        return eStatus;
    }
    public void seteStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String geteRemark() {
        return eRemark;
    }

    public void seteRemark(String eRemark) {
        this.eRemark = eRemark;
    }

    public String geteMsg() {
        return eMsg;
    }
    public void seteMsg(String eMsg) {
        this.eMsg = eMsg;
    }


    public String geteEmpName() {
        return eEmpName;
    }
    public void seteEmpName(String eEmpName) {
        this.eEmpName = eEmpName;
    }

    public String geteEmpMobile() {
        return eEmpMobile;
    }
    public void seteEmpMobile(String eEmpMobile) {
        this.eEmpMobile = eEmpMobile;
    }

    public String geteEmpIDProof() {
        return eEmpIDProof;
    }
    public void seteEmpIDProof(String eEmpIDProof) {
        this.eEmpIDProof = eEmpIDProof;
    }

    public String geteEmpMsg() {
        return eEmpMsg;
    }
    public void seteEmpMsg(String eEmpMsg) {
        this.eEmpMsg = eEmpMsg;
    }

    public String geteNgoToken() {
        return eNgoToken;
    }
    public void seteNgoToken(String eNgoToken) {
        this.eNgoToken = eNgoToken;
    }

    public String geteResToken() {
        return eResToken;
    }
    public void seteResToken(String eResToken) {
        this.eResToken = eResToken;
    }



}