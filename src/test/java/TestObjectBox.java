import com.tqhy.client.db.entities.MyObjectBox;
import com.tqhy.client.db.entities.User;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Yiheng
 * @create 2018/7/23
 * @since 1.0.0
 */
public class TestObjectBox {

    private File boxStoreDir;
    private BoxStore store;
    private Logger logger = LoggerFactory.getLogger(TestObjectBox.class);

    @Before
    public void setUp() throws IOException {
        // store the database in the systems temporary files folder
        File tempFile = File.createTempFile("object-store-test", "");
        // ensure file does not exist so builder creates a directory instead
        tempFile.delete();
        boxStoreDir = tempFile;
        store = MyObjectBox.builder()
                // add directory flag to change where ObjectBox puts its database files
                .directory(boxStoreDir)
                // optional: add debug flags for more detailed ObjectBox log output
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store.deleteAllFiles();
        }
    }

    @Test
    public void testOB() {
        BoxStore store = MyObjectBox.builder().name("objectbox-notes-db").build();
        Box<User> box = store.boxFor(User.class);
        box.put(new User(0, "asdf", "扁鹊", "123"));
        logger.info(box.count() + " notes in ObjectBox database:");

        box.getAll().forEach(user -> {
            if ("扁鹊".equals(user.getName())) {
                long id = user.getId();
                logger.info("id is: " + id);
                box.remove(id);
            }
        });

        logger.info("count is: " + box.count());
        store.close();
    }
}
