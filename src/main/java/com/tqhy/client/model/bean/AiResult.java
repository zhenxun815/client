package com.tqhy.client.model.bean;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class AiResult {
    private String aiDrId;

    private String patientId;
    /**
     * 0未知 1肺结核 2非肺结核
     */
    private int tbFlag;
    /**
     * 阴性1阳性2
     */
    private int aiResultFlag;
    private String aiImgResult;

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

    @Override
    public String toString() {
        return "AiResult{" +
                "aiDrId='" + aiDrId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", tbFlag=" + tbFlag +
                ", aiResultFlag=" + aiResultFlag +
                ", aiImgResult='" + aiImgResult + '\'' +
                '}';
    }

}
