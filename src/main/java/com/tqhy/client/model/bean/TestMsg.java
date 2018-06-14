package com.tqhy.client.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class TestMsg {
 public @SerializedName("data") Data data;
 public @SerializedName("desc") String desc;
 public @SerializedName("status") String status;

    @Override
    public String toString() {
        return "TestMsg{" +
                "data=" + data +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
