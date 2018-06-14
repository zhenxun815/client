package com.tqhy.client.controller;

import com.tqhy.client.utils.FxmlUtils;

/**
 * @author Yiheng
 * @create 2018/6/14
 * @since 1.0.0
 */
public class LandDialogController extends BaseDialogController{

    public LandDialogController() {
        FxmlUtils.load("/dialog/land_dialog/land_dialog.fxml",this );
        initDialog();
    }
}
