package com.tqhy.client.model;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class AiResult {

    public static final int GET_RESULT_SUCCESS = 1;
    public static final int GET_RESULT_FAILURE = 0;
    private String aiDrId;

    private String patientId;

    /**
     * 0未知 1肺结核 2非肺结核
     */
    private int tbFlag;

    /**
     * 阴性1,阳性2
     */
    private int aiResultFlag;

    private String aiImgResult;

    /**
     * 请求结果状态码,1数据获取成功,0,数据获取失败
     */
    private int status;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getTbFlag() {
        return tbFlag;
    }

    public void setTbFlag(int tbFlag) {
        this.tbFlag = tbFlag;
    }

    public int getAiResultFlag() {
        return aiResultFlag;
    }

    public void setAiResultFlag(int aiResultFlag) {
        this.aiResultFlag = aiResultFlag;
    }

    public String getAiDrId() {
        return aiDrId;
    }

    public void setAiDrId(String aiDrId) {
        this.aiDrId = aiDrId;
    }

    public String getAiImgResult() {
        return aiImgResult;
    }

    public void setAiImgResult(String aiImgResult) {
        this.aiImgResult = aiImgResult;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AiResult{" +
                "aiDrId='" + aiDrId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", tbFlag=" + tbFlag +
                ", aiResultFlag=" + aiResultFlag +
                ", aiImgResult='" + aiImgResult + '\'' +
                ", status=" + status +
                '}';
    }
}
