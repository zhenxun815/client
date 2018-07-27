package com.tqhy.client.view.animation;

import com.tqhy.client.view.ViewsUtils;
import javafx.animation.TranslateTransition;
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
    private Stage stage;
    private StageMovingAnimMode stageMovingAnimMode;
    private Logger logger = LoggerFactory.getLogger(StageMovingAnim.class);

    public void bindStageWithAnim() {

        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        switch (stageMovingAnimMode) {
            case SLIDE_OUT_TO_BOTTOM:
                fromX = stage.getX();
                fromY = stage.getY();
                toX = fromX;
                toY = screenHeight;
                stage.setOnHiding(event -> setAnimation());
                break;
            case SLIDE_IN_FROM_TOP_RIGHT:
                fromX = screenWidth - stageWidth;
                fromY = -stageHeight;
                toX = fromX;
                toY = 0;
                stage.setOnShowing(event -> setAnimation());
                break;
            case SLIDE_IN_FROM_BOTTOM_RIGHT:
                fromX = screenWidth - stageWidth;
                fromY = screenHeight;
                toX = fromX;
                toY = screenHeight - stageHeight;
                stage.setOnShowing(event -> setAnimation());
                break;
            case NO_ANIMATION:
                break;
        }

    }

    /**
     * 设定动画事件
     */
    public void setAnimation() {
        logger.info("into setAnimation...");
        Pane pane = new Pane();
        TranslateTransition transTrans = new TranslateTransition(Duration.millis(500), pane);

        transTrans.setFromX(fromX);
        transTrans.setFromY(fromY);
        transTrans.setToX(toX);
        transTrans.setToY(toY);
        pane.translateXProperty()
            .addListener((observable, oldValue, newValue) -> {
                stage.setX(newValue.doubleValue());
            });
        pane.translateYProperty()
            .addListener((observable, oldValue, newValue) -> {
                stage.setY(newValue.doubleValue());
            });
        transTrans.play();
        //stage.setResizable(false);
        logger.info("animation finished...");

    }

    public StageMovingAnim(Stage stage, StageMovingAnimMode stageMovingAnimMode) {
        this.stage = stage;
        this.stageMovingAnimMode = stageMovingAnimMode;
    }

    public enum StageMovingAnimMode {
        NO_ANIMATION,
        SLIDE_IN_FROM_TOP_RIGHT,
        SLIDE_IN_FROM_BOTTOM_RIGHT,
        SLIDE_OUT_TO_BOTTOM
    }
}
