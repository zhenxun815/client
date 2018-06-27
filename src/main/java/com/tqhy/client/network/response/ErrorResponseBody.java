package com.tqhy.client.network.response;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import java.nio.charset.Charset;

/**
 * @author Yiheng
 * @create 2018/6/27
 * @since 1.0.0
 */
public class ErrorResponseBody extends ResponseBody {

    private Throwable error;
    private Charset charset;

    public ErrorResponseBody(Throwable error) {
        this(error,Charset.forName("UTF-8"));
    }

    public ErrorResponseBody(Throwable error, Charset charset) {
        this.error = error;
        this.charset = charset;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("text/plain");
    }

    @Override
    public long contentLength() {
        return -1;
    }

    @Override
    public BufferedSource source() {
        String errorStr = error.toString();
        Buffer buffer = new Buffer().writeString(errorStr, charset);
        return buffer;
    }
}
