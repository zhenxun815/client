package com.tqhy.client.network.api;


/**
 * 包装向后台发送的消息对象
 * 
 * @author Yiheng
 * @create 2018/6/27
 * @since 1.0.0
 */

public class ApiBean<T> {

    public T bean;

    public String key;

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
