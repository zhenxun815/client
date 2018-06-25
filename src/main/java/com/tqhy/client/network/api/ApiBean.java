package com.tqhy.client.network.api;

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
