import com.tqhy.client.utils.ViewsUtils;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.junit.Test;

/**
 * @author Yiheng
 * @create 2018/6/21
 * @since 1.0.0
 */
public class TestViewUtils {
    @Test
    public void testWidthAndHeight() {
        Platform.runLater(() -> {
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            double minX = visualBounds.getMinX();
            double maxX = visualBounds.getMaxX();
            double width = visualBounds.getWidth();
            logger.info("minX: " + minX + "maxX: " + maxX + "width: " + width);
        });

    }
}
