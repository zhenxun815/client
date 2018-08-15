package com.tqhy.client.network.app;

import com.tqhy.client.controller.WebViewController;
import com.tqhy.client.listener.OnWebViewShowingListener;
import com.tqhy.client.model.AiResult;
import com.tqhy.client.model.ClientMsg;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.api.ApiBean;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Yiheng
 * @create 2018/8/10
 * @since 1.0.0
 */
public class JavaAppAiWarning extends JavaAppBase {

    //1已警告,0未警告
    Integer warning_flag = 1;
    //0警告未解除,1继续警告
    Integer ai_warning = null;
    //0非误报,1误报
    Integer error_flag = null;

    OnWebViewShowingListener webViewShowingListener;

    AiResult aiResult;
    /**
     * 用户点击详情按钮
     */
    public void pressDetail() {
        logger.info("detail click...");
        WebViewController web = new WebViewController();
        webViewShowingListener.bindShowingProperty(web);
        web.showTqWeb(aiResult.getAiDrId(), Network.AI_PROMPT_PAGE);
        super.closeWindow();
        ai_warning = 1;
        postAiWarningBack(ai_warning, error_flag, warning_flag);
    }

    /**
     * 用户点击排除按钮
     */
    public void pressExclude() {
        logger.info("exclude click...");
        super.closeWindow();
        error_flag = 1;
        ai_warning = 0;
        postAiWarningBack(ai_warning, error_flag, warning_flag);
    }

    @Override
    public void closeWindow() {
        super.closeWindow();
        ai_warning = 1;
        postAiWarningBack(ai_warning, error_flag, warning_flag);
    }

    /**
     * 向后台反馈是否接收到弹窗信息及弹框相关操作
     *
     * @param aiWarning
     * @param errorFlag
     * @param warningFlag
     */
    private void postAiWarningBack(Integer aiWarning, Integer errorFlag, Integer warningFlag) {
        ApiBean<ClientMsg> apiBean = new ApiBean<>();
        ClientMsg warningBack = new ClientMsg();
        warningBack.setOperationIp(Network.getLocalIp());
        warningBack.setAiDrId(Network.currentId);
        warningBack.setWarningBack(aiWarning, errorFlag, warningFlag);
        logger.info("post warning back: " + warningBack);
        apiBean.setBean(warningBack);

        Network.getAiHelperApi()
               .postAiWarningBack(apiBean)
               .map(body -> {
                   String json = body.string();
                   logger.info("AiWarningDialogController postAiWarningBack recieve json:" + json);
                   return json;
               })
               .observeOn(Schedulers.io())
               .subscribeOn(Schedulers.trampoline())
               .subscribe(json -> {
                   logger.info(json);
               });
    }

    public JavaAppAiWarning(AiResult aiResult, OnWebViewShowingListener webViewShowingListener) {
        this.aiResult = aiResult;
        this.webViewShowingListener = webViewShowingListener;
    }
}
