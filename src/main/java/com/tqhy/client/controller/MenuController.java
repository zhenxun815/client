package com.tqhy.client.controller;

import com.tqhy.client.network.Network;
import com.tqhy.client.view.FxmlUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/8
 * @since 1.0.0
 */
public class MenuController extends ContextMenu {

    @FXML
    HBox menuItemHistory;
    @FXML
    HBox menuItemMin;
    @FXML
    HBox menuItemExit;
    @FXML
    HBox menuItemInfo;

    /* @FXML
    HBox menuItemUpload;*/

    Node anchor;
    private Stage stage;
    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    public MenuController() {
        FxmlUtils.load("/menu/menu_root.fxml", this);
        initItems();
    }

    @FXML
    public void detail(MouseEvent event) {
        logger.info("detail click...");
        WebViewController web = new WebViewController();
        //web.showWeb(Network.BASE_URL+"index?id="+Network.currentId+"&pageName=history");
        web.showTqWeb(Network.currentId, Network.AI_PROMPT_PAGE);
    }

    /*  @FXML
      public void upload(MouseEvent event) {
          logger.info("upload click...");
          WebViewController web = new WebViewController();
          //web.showWeb(Network.BASE_URL+"index?id="+Network.currentId+"&pageName=report");
          logger.info("current id is: "+Network.currentId);
          web.showTqWeb(Network.currentId, Network.REPORT_PAGE);
      }
  */
    @FXML
    public void exit(MouseEvent event) {
        logger.info("exit click...");
        stage.close();
        System.exit(0);
    }

    @FXML
    public void min(MouseEvent event) {
        Platform.runLater(stage::hide);
    }

   /* @FXML
    public void info(MouseEvent event) {
        logger.info("info click...");
        testWeb();
        //testland();
    }*/

    private void testland() {
        LandDialogController landDialogController = new LandDialogController();
        landDialogController.show();
    }

    private void testWeb() {
        WebViewController web = new WebViewController();
        //web.showLocalWeb("/html/test.html");
        //web.showWeb("http://192.168.1.189:8080/html/index.html?id=02cfb244f6194ff3aebb7632ac029369&pageName=index-main");
    }

    public void initItems() {
        // logger.info("menu controller init items...");
        getItems().add(0, new CustomMenuItem(menuItemHistory));
        /* getItems().add(1, new CustomMenuItem(menuItemUpload));*/
        getItems().add(1, new CustomMenuItem(menuItemMin));
        getItems().add(2, new CustomMenuItem(menuItemExit));
        getItems().add(3, new CustomMenuItem(menuItemInfo));
    }

    public void showMenu(Node anchor, double screenX, double screenY) {
        // logger.info("menu controller showAtRightBottom...");
        // logger.info("menu items size: " + getItems().size());
        // logger.info("menu anchor is: " + anchor + " x: " + screenX + " y: " + screenY);
        this.anchor = anchor;
        this.stage = (Stage) anchor.getScene().getWindow();
        show(anchor, screenX, screenY);
    }

    public void hideMenu() {
        logger.info("menu controller hide...");
        hide();
    }
}
