package com.tqhy.client;

import com.google.gson.Gson;
import com.tqhy.client.controller.AiWarningDialogController;
import com.tqhy.client.controller.AuthWarningDialogController;
import com.tqhy.client.jna.JnaCaller;
import com.tqhy.client.model.AiResult;
import com.tqhy.client.network.Network;
import com.tqhy.client.network.responsebody.ErrorResponseBody;
import com.tqhy.client.utils.FileUtils;
import com.tqhy.client.utils.ImgUtils;
import com.tqhy.client.utils.MD5Utils;
import com.tqhy.client.utils.PropertiesUtil;
import com.tqhy.client.view.ViewsUtils;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private String key = "";
    private Logger logger = LoggerFactory.getLogger(Main.class);
    private String rootPath = FileUtils.getRootPath();
    private String screenImgPath = rootPath + "/screen_capture.jpg";
    private String cuttedImgPath = rootPath + "/capture_cutted.jpg";

    private int cut_x;
    private int cut_y;
    private int cut_width;
    private int cut_height;

    private BooleanProperty webViewShowingFlag = new SimpleBooleanProperty(false);
    private BooleanProperty warningDialogShouldShowingFlag = new SimpleBooleanProperty(false);
    private ObjectProperty<AiResult> aiResult = new SimpleObjectProperty<>();
    private AiWarningDialogController aiWarningDialogController;

    @Override
    public void start(Stage primaryStage) throws Exception {
       /* String classPath = Main.class.getClassLoader().getResource("").getPath();
        System.out.println("path is: " + classPath);*/
        initPrimaryStage(primaryStage);
        javax.swing.SwingUtilities.invokeLater(() -> initSystemTray(primaryStage));
        //initSystemTray(primaryStage);
        initProperties();
        //doRxJava(primaryStage);
    }

    /**
     * 初始化网络地址
     */
    private void initProperties() {
        logger.info("root path is:" + FileUtils.getRootPath());
        logger.info("deploy path is:" + System.getProperty("exe.path"));
        String ip = PropertiesUtil.getPropertiesKeyValue("ip");
        logger.info("ip is:" + ip);
        Network.IP = ip;
        Network.setBaseUrl(ip);
        logger.info("base url is: " + Network.BASE_URL);

        //截图参数
        String x = PropertiesUtil.getPropertiesKeyValue("x");
        String y = PropertiesUtil.getPropertiesKeyValue("y");
        String width = PropertiesUtil.getPropertiesKeyValue("width");
        String height = PropertiesUtil.getPropertiesKeyValue("height");
        this.cut_x = Integer.parseInt(x);
        this.cut_y = Integer.parseInt(y);
        this.cut_width = Integer.parseInt(width);
        this.cut_height = Integer.parseInt(height);
    }

    /**
     * 循环调用dll文件获取key并过滤,如果是新的key则向后台请求弹出警告弹框
     *
     * @param primaryStage
     */
    private void doRxJava(Stage primaryStage) {
        JnaCaller.getUserInfo();
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                  .map(aLong -> {
                              //logger.info("webViewShowingFlag is: " + webViewShowingFlag);
                              if (!isWebViewShowingFlag()) {
                                  screenImgPath = ImgUtils.captureScreen(screenImgPath);
                                  String str = JnaCaller.fetchData(screenImgPath);
                                  //logger.info("capture screen img path: " + screenImgPath);
                                  logger.info(".dll caller get: " + str);
                                  return str;
                              } else {
                                  return key;
                              }
                          }
                  )
                  .filter(key -> {
                      boolean b = key.equals(this.key);
                      setWarningDialogShouldShowingFlag(b);
                      logger.info("setWarningDialogShouldShowingFlag: " + getWarningDialogShouldShowingFlag());
                      //logger.info("key: " + key + " this.key: " + this.key + " b: " + b);
                      this.key = key;
                      return key.length() >= 5 && !b;
                  })
                  .observeOn(Schedulers.trampoline())
                  .subscribeOn(Schedulers.trampoline())
                  .subscribe(key -> {

                      switch (key) {
                          //未授权
                          case JnaCaller.FETCH_DATA_LICENSE:
                              showfloat(primaryStage);
                              getAuthWarning(primaryStage);
                              break;
                          //非RIS界面,未获取到数据
                          case JnaCaller.FETCH_DATA_NODATA:
                              hidefloat(primaryStage);
                              break;
                          //连接动态库失败
                          case JnaCaller.FETCH_DATA_FAILED:
                              break;
                          //根据key值请求后台AiHelper
                          default:
                              showfloat(primaryStage);
                              requestAiHelper(primaryStage, key);
                              break;
                      }
                      //logger.info("subscribe: " + key);
                  });
    }

    /**
     * 显示悬浮窗
     *
     * @param primaryStage
     */
    private void showfloat(Stage primaryStage) {
        Platform.runLater(() -> {
            if (!primaryStage.isShowing()) {
                primaryStage.show();
            }
        });
    }

    /**
     * 隐藏悬浮窗
     *
     * @param primaryStage
     */
    private void hidefloat(Stage primaryStage) {
        Platform.runLater(() -> {
            if (primaryStage.isShowing()) {
                primaryStage.hide();
            }
        });
    }

    /**
     * 用户未授权,弹出未授权提示弹窗,引导用户进行授权操作
     *
     * @param primaryStage
     */
    private void getAuthWarning(Stage primaryStage) {
        Platform.runLater(() -> {
            AuthWarningDialogController authWarningDialogController = new AuthWarningDialogController();
            String show = authWarningDialogController.show(primaryStage);
            key = show;
        });

    }

    /**
     * 初始化主窗口
     *
     * @param primaryStage
     * @throws IOException
     */
    private void initPrimaryStage(Stage primaryStage) throws IOException {
        Platform.setImplicitExit(false);
        Parent root = FXMLLoader.load(getClass().getResource("/float/float.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/deploy/package/windows/logo_title.png")));
        Rectangle rect = new Rectangle(50, 50);
        rect.setArcHeight(25);
        rect.setArcWidth(25);
        root.setClip(rect);

        Scene scene = new Scene(root, 50, 50);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setX(ViewsUtils.getMaxX(50 * 2));
        primaryStage.setY(ViewsUtils.getMaxY(50 * 2));
        primaryStage.show();
    }

    /**
     * 根据key值请求后台获取ai提示内容,请求成功则弹出窗口,失败,则打印错误日志;
     */
    private void requestAiHelper(Stage primaryStage, String key) {

        String substring = key.substring(key.length() - 5);
        //logger.info(" into requestAiHelper...key is: " + key);
        //logger.info(" into requestAiHelper...substring is: " + substring);
        String md5 = MD5Utils.getMD5(substring);
        // logger.info(" into requestAiHelper...substring MD5 is: " + md5);
        logger.info(Network.BASE_URL);
        String cutImgPath = ImgUtils.cutImg(screenImgPath, cuttedImgPath, cut_x, cut_y, cut_width, cut_height);
        if (null != cutImgPath) {
            RequestBody content = Network.createRequestBody(md5);
            MultipartBody.Part part = Network.createMultipart(cutImgPath);
            //logger.info("content type is: "+content.contentType());
            Network.getAiHelperApi()
                   .getAiWarning(content, part)
                   .observeOn(Schedulers.io())
                   .subscribeOn(Schedulers.trampoline())
                   /* .onErrorReturn((error) -> {
                         logger.error("getAiDrId(key)请求异常", error);
                         return new ErrorResponseBody(error);
                     })
                    .filter(body -> body instanceof ErrorResponseBody)*/
                   .subscribe(warningBody -> {
                       String json = warningBody.string();
                       logger.info("json is: " + json);
                       AiResult aiResult = new Gson().fromJson(json, AiResult.class);
                       if (AiResult.GET_RESULT_SUCCESS == aiResult.getStatus()) {
                           Network.currentId = aiResult.getAiDrId();
                           //logger.info("set current id: " + Network.currentId);
                           if (null == aiResult.getAiImgResult()) {
                               logger.info("ai未发现异常");
                           } else {
                               this.aiResult.setValue(aiResult);
                               showWarningDialog(primaryStage);
                           }
                       } else {
                           logger.info("ai未获取到对应数据");
                       }
                   });
        } else {
            logger.info("截取图片失败...");
        }

    }

    /**
     * 弹出ai提示窗口
     *
     * @param primaryStage
     */
    private void showWarningDialog(Stage primaryStage) {
        Platform.runLater(() -> {
            logger.info("subscribe aiResult: " + aiResult);
            if (null == aiWarningDialogController) {
                aiWarningDialogController = new AiWarningDialogController();

                aiWarningDialogController.aiResultProperty()
                                         .bindBidirectional(this.aiResult);

                aiWarningDialogController.dialogShouldShowingFlagProperty()
                                         .bindBidirectional(this.warningDialogShouldShowingFlag);

                aiWarningDialogController.dialogShouldShowingFlagProperty()
                                         .addListener((observable, oldValue, newValue) -> {
                                                     logger.info("dialogShouldShowingFlag changed,oldValue is: " + oldValue + ", newValue is: " + newValue);
                                                     aiWarningDialogController.closeDialog();
                                                 }
                                         );
            }

            aiWarningDialogController.show(primaryStage, (webViewDialogController) -> {
                webViewShowingFlag.bindBidirectional(webViewDialogController.webViewShowingProperty());
                logger.info("webViewShowingFlag bined...");
            });
        });
    }

    /**
     * 通过key值请求后台获取当前aiDrId值,请求成功则赋值给{@link Network#currentId}
     *
     * @param primaryStage
     * @param key
     */
    private void getAiDrId(Stage primaryStage, String key) {
        Network.getAiHelperApi()
               .getAiDrId(key)
               .observeOn(Schedulers.io())
               .subscribeOn(Schedulers.trampoline())
               .onErrorReturn((error) -> {
                   logger.error("getAiDrId(primaryStage,aidrId)请求异常", error);
                   return new ErrorResponseBody(error);
               })
               .filter(body -> body instanceof ErrorResponseBody)
               .subscribe(warningBody -> {
                   String json = warningBody.string();
                   AiResult aiResult = new Gson().fromJson(json, AiResult.class);
                   if (AiResult.GET_RESULT_SUCCESS == aiResult.getStatus()) {
                       Network.currentId = aiResult.getAiDrId();
                   } else {
                       logger.info("未能成功获取当前对应aiDrId");
                   }
               });
    }

    /**
     * 创建系统托盘图标
     *
     * @param stage
     */
    private void initSystemTray(Stage stage) {
        try {
            Toolkit.getDefaultToolkit();
            if (!java.awt.SystemTray.isSupported()) {
                System.out.println("系统不支持托盘图标,程序退出..");
                Platform.exit();
            }
            //PopupMenu popupMenu = createPopMenu(stage);

            SystemTray systemTray = SystemTray.getSystemTray();
            String iconPath = Main.class.getResource("/deploy/package/windows/logo_systray.png").toExternalForm();
            URL imageLoc = new URL(iconPath);
            java.awt.Image image = ImageIO.read(imageLoc);
            //final TrayIcon trayIcon = new TrayIcon(image, "打开悬浮窗",popupMenu);
            final TrayIcon trayIcon = new TrayIcon(image, "双击打开悬浮窗");
            trayIcon.addActionListener(e -> Platform.runLater(stage::show));

            systemTray.add(trayIcon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建系统托盘图标右键菜单
     *
     * @param window
     * @return
     */
    private PopupMenu createPopMenu(Stage window) {
        PopupMenu popup = new PopupMenu();
        MenuItem itemDetail = new MenuItem("详情");
        MenuItem itemFloat = new MenuItem("悬窗");
        MenuItem itemConfig = new MenuItem("设置");
        MenuItem itemExit = new MenuItem("退出");
        itemDetail.addActionListener(e -> {
            logger.info("click itemShowDetail...");
        });
        itemFloat.addActionListener(e -> {
            logger.info("click itemShowFloat...");
            Platform.runLater(window::show);
        });
        itemConfig.addActionListener(e -> {
            logger.info("click itemConfig...");
        });
        itemExit.addActionListener(e -> {
            logger.info("click itemExit...");
            System.exit(0);
        });
        popup.add(itemDetail);
        popup.add(itemFloat);
        popup.add(itemConfig);
        popup.add(itemExit);
        return popup;
    }

    public static void main(String[] args) {
        launch(args);
    }


    public boolean isWebViewShowingFlag() {
        return webViewShowingFlag.get();
    }

    public BooleanProperty webViewShowingFlagProperty() {
        return webViewShowingFlag;
    }

    public void setWebViewShowingFlag(boolean webViewShowingFlag) {
        this.webViewShowingFlag.set(webViewShowingFlag);
    }

    public boolean getWarningDialogShouldShowingFlag() {
        return warningDialogShouldShowingFlag.get();
    }

    public BooleanProperty warningDialogShouldShowingFlagProperty() {
        return warningDialogShouldShowingFlag;
    }

    public void setWarningDialogShouldShowingFlag(boolean warningDialogShouldShowingFlag) {
        this.warningDialogShouldShowingFlag.set(warningDialogShouldShowingFlag);
    }
}
