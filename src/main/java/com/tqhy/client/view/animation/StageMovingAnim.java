package com.tqhy.client.view.animation;

import com.tqhy.client.view.ViewsUtils;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/7/27
 * @since 1.0.0
 */
public class StageMovingAnim {

    private double fromX = 0;
    private double fromY = 0;
    private double toX = 0;
    private double toY = 0;
    private double screenWidth = ViewsUtils.getScreenWidth();
    private double screenHeight = ViewsUtils.getScreenHeight();
    private SimpleDoubleProperty primaryStageX = new SimpleDoubleProperty();
    private SimpleDoubleProperty primaryStageY = new SimpleDoubleProperty();
    private Stage movingStage;
    private StageMovingAnimMode stageMovingAnimMode;
    private Logger logger = LoggerFactory.getLogger(StageMovingAnim.class);


    public void bindStageWithAnim() {

        double stageWidth = movingStage.getWidth();
        double stageHeight = movingStage.getHeight();

        switch (stageMovingAnimMode) {
            case SLIDE_OUT_TO_BOTTOM:
                fromX = movingStage.getX();
                fromY = movingStage.getY();
                // logger.info("fromX is: " + fromX);
                // logger.info("fromY is: " + fromY);
                toX = fromX;
                toY = screenHeight;
                movingStage.setOnHiding(event -> {
                    // logger.info("event is: " + event.getEventType());
                    setAnimation();
                });
                break;
            case SLIDE_IN_FROM_TOP_RIGHT:
                fromX = screenWidth - stageWidth;
                fromY = -stageHeight;
                toX = fromX;
                toY = 0;

                movingStage.setOnShowing(event -> {
                    //logger.info("event is: "+event.getEventType());
                    setAnimation();
                });
                break;
            case SLIDE_IN_FROM_BOTTOM_RIGHT:
                fromX = screenWidth - stageWidth;
                fromY = screenHeight;
                toX = fromX;
                toY = screenHeight - stageHeight;
                movingStage.setOnShowing(event -> setAnimation());
                break;
            case ACCORDING_TO_PRIMARY_STAGE:
                fromX = getPrimaryStageX();
                fromY = getPrimaryStageY();
                toX = fromX - movingStage.getWidth();
                toY = fromY;
                //todo 动画事件未完成,但需求更改,暂保留逻辑
                movingStage.setOnShowing(event -> setAnimation());
                break;
            case NO_ANIMATION:
                break;
        }

    }

    /**
     * 设定动画事件
     */
    private void setAnimation() {
        logger.info("into setAnimation...");
        Pane pane = new Pane();
        TranslateTransition transTrans = new TranslateTransition(Duration.millis(500), pane);
        movingStage.setX(fromX);
        movingStage.setY(fromY);
        transTrans.setFromX(fromX);
        transTrans.setFromY(fromY);
        transTrans.setToX(toX);
        transTrans.setToY(toY);
        pane.translateXProperty()
            .addListener((observable, oldValue, newValue) -> {
                movingStage.setX(newValue.doubleValue());
                // logger.info("movingStage x is: " + movingStage.getX());
            });
        pane.translateYProperty()
            .addListener((observable, oldValue, newValue) -> {
                movingStage.setY(newValue.doubleValue());
                // logger.info("movingStage y is: " + movingStage.getY());
            });
        transTrans.play();
        //movingStage.setResizable(false);
        // logger.info("animation finished...");

    }

    public StageMovingAnim(Stage movingStage, StageMovingAnimMode stageMovingAnimMode) {
        this.movingStage = movingStage;
        this.stageMovingAnimMode = stageMovingAnimMode;
    }

    public double getPrimaryStageX() {
        return primaryStageX.get();
    }

    public SimpleDoubleProperty primaryStageXProperty() {
        return primaryStageX;
    }

    public void setPrimaryStageX(double primaryStageX) {
        this.primaryStageX.set(primaryStageX);
    }

    public double getPrimaryStageY() {
        return primaryStageY.get();
    }

    public SimpleDoubleProperty primaryStageYProperty() {
        return primaryStageY;
    }

    public void setPrimaryStageY(double primaryStageY) {
        this.primaryStageY.set(primaryStageY);
    }

    public enum StageMovingAnimMode {
        NO_ANIMATION,
        SLIDE_IN_FROM_TOP_RIGHT,
        SLIDE_IN_FROM_BOTTOM_RIGHT,
        SLIDE_OUT_TO_BOTTOM,
        ACCORDING_TO_PRIMARY_STAGE
    }
}
