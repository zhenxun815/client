package com.tqhy.client.db;

import com.tqhy.client.db.entities.MyObjectBox;
import com.tqhy.client.utils.FileUtils;
import io.objectbox.BoxStore;

import java.io.File;

/**
 * @author Yiheng
 * @create 2018/7/23
 * @since 1.0.0
 */
public class DBHelper {
    /**
     * 数据库文件存放根路径设定为jar包所在路径
     */
    private static final File BASE_DIRECTORY = new File(FileUtils.getJarPath());

    public static BoxStore buildDB(String dbName) {
        BoxStore boxStore = MyObjectBox.builder()
                                       .baseDirectory(BASE_DIRECTORY)
                                       .name(dbName)
                                       .build();
        return boxStore;
    }
}
