package com.tqhy.client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Yiheng
 * @create 2018/6/22
 * @since 1.0.0
 */
public class ClientMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName(value = "aiDrId")
    private String aiDrId;

    @SerializedName(value = "operation")
    private Integer operation;

    @SerializedName(value = "date")
    private String date;

    @SerializedName(value = "operationIp")
    private String operationIp;

    @SerializedName(value = "aiWarning")
    private Integer aiWarning;

    @SerializedName(value = "errorFlag")
    private Integer errorFlag;

    @SerializedName(value = "warningFlag")
    private Integer warningFlag;

    public ClientMsg(String aiDrId, Integer operation, String operationIp) {
        this.aiDrId = aiDrId;
        this.operation = operation;
        this.operationIp = operationIp;
    }

    public ClientMsg() {
    }

    public String getOperationIp() {
        return operationIp;
    }

    public void setOperationIp(String operationIp) {
        this.operationIp = operationIp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getAiDrId() {
        return aiDrId;
    }

    public void setAiDrId(String aiDrId) {
        this.aiDrId = aiDrId;
    }

    public Integer getAiWarning() {
        return aiWarning;
    }

    public void setAiWarning(Integer aiWarning) {
        this.aiWarning = aiWarning;
    }

    public Integer getErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(Integer errorFlag) {
        this.errorFlag = errorFlag;
    }

    public Integer getWarningFlag() {
        return warningFlag;
    }

    public void setWarningFlag(Integer warningFlag) {
        this.warningFlag = warningFlag;
    }

    public void setWarningBack(Integer aiWarning, Integer errorFlag, Integer warningFlag) {
        setAiWarning(aiWarning);
        setErrorFlag(errorFlag);
        setWarningFlag(warningFlag);
    }
}
