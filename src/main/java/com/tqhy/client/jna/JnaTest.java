package com.tqhy.client.jna;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

/**
 * @author Yiheng
 * @create 2018/6/15
 * @since 1.0.0
 */
public interface JnaTest extends StdCallLibrary {
    JnaTest caller = Native.loadLibrary("com/tqhy/client/jna/jyTQAITools", JnaTest.class);

    int jyTestFunc(int a, int b);
}
