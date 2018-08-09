import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yiheng
 * @create 2018/6/7
 * @since 1.0.0
 */
public class TestJavaFxBinding {
    private Logger logger = LoggerFactory.getLogger(TestJavaFxBinding.class);

    @Test
    public void testBinding() {
        Bill bill1 = new Bill();
        Bill bill2 = new Bill();
        Bill bill3 = new Bill();

        NumberBinding total = Bindings.add(bill1.amountDueProperty().add(bill2.amountDueProperty()), bill3.amountDueProperty());
        total.addListener(o -> logger.info("The binding is now invalid."));

        bill1.setAmountDue(200.00);
        bill2.setAmountDue(100.00);
        bill3.setAmountDue(75.00);
        logger.info(total.getValue() + "");

        bill3.setAmountDue(150.00);
        logger.info(total.getValue() + "");
    }

    class Bill {

        private DoubleProperty amountDue = new SimpleDoubleProperty();

        public final double getAmountDue() {
            return amountDue.get();
        }

        public final void setAmountDue(double value) {
            amountDue.set(value);
        }

        public DoubleProperty amountDueProperty() {
            return amountDue;
        }

    }
}
