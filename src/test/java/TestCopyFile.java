import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Yiheng
 * @create 2018/7/3
 * @since 1.0.0
 */
public class TestCopyFile {

    @Test
    public void testCopyToSys32() {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream("/cif.bin");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
