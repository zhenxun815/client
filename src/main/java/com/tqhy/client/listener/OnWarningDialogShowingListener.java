package com.tqhy.client.listener;

import com.tqhy.client.controller.AiWarningDialogController;

/**
 * 监听是否弹出ai提示弹窗
 *
 * @author Yiheng
 * @create 2018/7/17
 * @since 1.0.0
 */
public interface OnWarningDialogShowingListener {
    void bindShowingProperty(AiWarningDialogController aiWarningDialogController);

}
