package com.tqhy.client.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Yiheng
 * @create 2018/6/13
 * @since 1.0.0
 */
public class Data {
    public @SerializedName("part")
    List<String> part;
    public @SerializedName("source")
    List<String> source;
    public @SerializedName("type")
    List<String> type;

    @Override
    public String toString() {
        return "Data{" +
                "part=" + part +
                ", source=" + source +
                ", type=" + type +
                '}';
    }
}
