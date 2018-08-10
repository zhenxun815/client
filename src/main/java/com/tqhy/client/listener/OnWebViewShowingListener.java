package com.tqhy.client.listener;

import com.tqhy.client.controller.WebViewController;

/**
 * 监听是否弹出webview窗体
 *
 * @author Yiheng
 * @create 2018/7/16
 * @since 1.0.0
 */
public interface OnWebViewShowingListener {
    void bindShowingProperty(WebViewController webViewController);
}
