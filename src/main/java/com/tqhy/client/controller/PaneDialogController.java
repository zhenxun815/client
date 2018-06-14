package com.tqhy.client.controller;

import com.tqhy.client.model.bean.AiResult;
import com.tqhy.client.utils.FxmlUtils;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class PaneDialogController extends BaseDialogController {

    private AiResult result;

    public PaneDialogController(AiResult result) {
        this.result = result;
        FxmlUtils.load("", this);
        initDialog();
    }
}
