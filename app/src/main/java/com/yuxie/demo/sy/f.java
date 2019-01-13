package com.yuxie.demo.sy;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.yuxie.demo.sy.Constant;
import com.yuxie.demo.sy.Sign;

import java.io.EOFException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.MultipartBody.Part;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/* compiled from: CommonParamsInterceptor */
public class f implements Interceptor {
    private static final Charset b = Charset.forName("UTF-8");
    private final Context a;
    private boolean c;
    private boolean d;

    public f(Context context, boolean z) {
        this.a = context;
        this.d = z;
        this.c = z;
    }

    public Response intercept(Chain chain) throws IOException {
        return a(chain, a(chain));
    }

    private Request a(Chain chain) {
        Request request = chain.request();
        String method = request.method();
        try {
            Request build;
            HashMap hashMap = new HashMap();
            hashMap.put("app_os", "android");
            hashMap.put("app_version", "1.1.0");
//            hashMap.put("timestamp", "1547312750221");
            hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
            hashMap.put("secret", "andy888");
            hashMap.put("app_channel", "XiaoMi");
            hashMap.put("imei", getImei());

            int i = 0;
            HttpUrl url;
            String encodedQuery;
            String[] split;
            if (method.equals("POST") || method.equals("PUT")) {
                RequestBody body = request.body();
                if (body instanceof MultipartBody) {
                    List parts = ((MultipartBody) body).parts();
                    Builder addFormDataPart = new Builder().addPart(body).setType(MultipartBody.FORM).addFormDataPart(Constant.q, (String) hashMap.get(Constant.q)).addFormDataPart("imei", (String) hashMap.get("imei")).addFormDataPart("app_version", (String) hashMap.get("app_version")).addFormDataPart(Constant.v, getToken(this.a));
                    while (i < parts.size()) {
                        addFormDataPart.addPart((Part) parts.get(i));
                        i++;
                    }
                    build = method.equals("POST") ? request.newBuilder().post(addFormDataPart.build()).build() : method.equals("PUT") ? request.newBuilder().put(addFormDataPart.build()).build() : request;
                } else {
                    FormBody formBody;
                    FormBody.Builder builder = new FormBody.Builder();
                    if (body instanceof FormBody) {
                        formBody = (FormBody) request.body();
                    } else {
                        formBody = builder.build();
                    }
                    while (i < formBody.size()) {
                        hashMap.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
                        builder.add(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
                        i++;
                    }
                    builder.add(Constant.q, (String) hashMap.get(Constant.q));
                    builder.add("app_version", (String) hashMap.get("app_version"));
                    builder.add(Constant.s, (String) hashMap.get(Constant.s));
                    builder.add(Constant.w, (String) hashMap.get(Constant.w));
                    builder.add("imei", (String) hashMap.get("imei"));
                    builder.add("secret", "andy888");
                    if (!hashMap.containsKey(Constant.v)) {
                        hashMap.put(Constant.v, getToken(this.a));
                        builder.add(Constant.v, (String) hashMap.get(Constant.v));
                    }

                    builder.add("sign", Sign.toStringAndSign(hashMap));
                    if (method.equals("POST")) {
                        build = request.newBuilder().post(builder.build()).build();
                    } else if (!method.equals("PUT")) {
                        return request;
                    } else {
                        build = request.newBuilder().put(builder.build()).build();
                    }
                }
            } else if (!method.equals("DELETE")) {
                return request;
            } else {
                url = request.url();
                encodedQuery = url.encodedQuery();
                if (isEmpty(encodedQuery)) {
                    for (String split22 : encodedQuery.contains("&") ? encodedQuery.split("&") : new String[]{encodedQuery}) {
                        split = split22.split("=");
                        if (split.length > 0) {
                            if (split.length == 2) {
                                hashMap.put(split[0], split[1]);
                            }
                            if (split.length == 1) {
                                hashMap.put(split[0], "");
                            }
                        }
                    }
                }
                if (hashMap.containsKey(Constant.v)) {
                    url = url.newBuilder().addEncodedQueryParameter(Constant.q, (String) hashMap.get(Constant.q)).addEncodedQueryParameter("imei", (String) hashMap.get("imei")).addEncodedQueryParameter("app_version", (String) hashMap.get("app_version")).addEncodedQueryParameter(Constant.s, (String) hashMap.get(Constant.s)).addEncodedQueryParameter(Constant.w, (String) hashMap.get(Constant.w)).addEncodedQueryParameter("sign", Sign.toStringAndSign(hashMap)).build();
                } else {
                    hashMap.put(Constant.v, getToken(this.a));
                    url = url.newBuilder().addEncodedQueryParameter(Constant.q, (String) hashMap.get(Constant.q)).addEncodedQueryParameter("imei", (String) hashMap.get("imei")).addEncodedQueryParameter("app_version", (String) hashMap.get("app_version")).addEncodedQueryParameter(Constant.s, (String) hashMap.get(Constant.s)).addEncodedQueryParameter(Constant.w, (String) hashMap.get(Constant.w)).addEncodedQueryParameter(Constant.v, (String) hashMap.get(Constant.v)).addEncodedQueryParameter("sign", Sign.toStringAndSign(hashMap)).build();
                }
                build = request.newBuilder().url(url).delete().build();
            }
            return build;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("intercept error", "拦截出错");
            return request;
        }
    }

    private Response a(Chain chain, Request request) throws IOException {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        StringBuilder stringBuilder3;
        RequestBody body = request.body();
        Object obj = body != null ? 1 : null;
        Connection connection = chain.connection();
        Object protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("--> ");
        stringBuilder4.append(request.method());
        stringBuilder4.append(' ');
        stringBuilder4.append(request.url());
        stringBuilder4.append(' ');
        stringBuilder4.append(protocol);
        String stringBuilder5 = stringBuilder4.toString();
        if (!(this.d || obj == null)) {
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append(stringBuilder5);
            stringBuilder4.append(" (");
            stringBuilder4.append(body.contentLength());
            stringBuilder4.append("-byte body)");
            stringBuilder5 = stringBuilder4.toString();
        }
        b(stringBuilder5);
        if (this.d) {
            if (obj != null) {
                if (body.contentType() != null) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Content-Type: ");
                    stringBuilder.append(body.contentType());
                    a(stringBuilder.toString());
                }
                if (body.contentLength() != -1) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Content-Length: ");
                    stringBuilder.append(body.contentLength());
                    a(stringBuilder.toString());
                }
            }
            Headers headers = request.headers();
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                String name = headers.name(i);
                if (!("Content-Type".equalsIgnoreCase(name) || "Content-Length".equalsIgnoreCase(name))) {
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append(name);
                    stringBuilder6.append(": ");
                    stringBuilder6.append(headers.value(i));
                    a(stringBuilder6.toString());
                }
            }
            if (!this.c || obj == null) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("--> END ");
                stringBuilder2.append(request.method());
                a(stringBuilder2.toString());
            } else if (a(request.headers())) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("--> END ");
                stringBuilder2.append(request.method());
                stringBuilder2.append(" (encoded body omitted)");
                a(stringBuilder2.toString());
            } else {
                Buffer buffer = new Buffer();
                body.writeTo(buffer);
                Charset charset = b;
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(b);
                }
                a("");
                if (a(buffer)) {
                    a(buffer.readString(charset));
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("--> END ");
                    stringBuilder3.append(request.method());
                    stringBuilder3.append(" (");
                    stringBuilder3.append(body.contentLength());
                    stringBuilder3.append("-byte body)");
                    a(stringBuilder3.toString());
                } else {
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("--> END ");
                    stringBuilder3.append(request.method());
                    stringBuilder3.append(" (binary ");
                    stringBuilder3.append(body.contentLength());
                    stringBuilder3.append("-byte body omitted)");
                    a(stringBuilder3.toString());
                }
            }
        }
        long nanoTime = System.nanoTime();
        StringBuilder stringBuilder7;
        try {
            String stringBuilder8;
            Response proceed = chain.proceed(request);
            nanoTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime);
            ResponseBody body2 = proceed.body();
            long contentLength = body2.contentLength();
            if (contentLength != -1) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(contentLength);
                stringBuilder2.append("-byte");
                stringBuilder8 = stringBuilder2.toString();
            } else {
                stringBuilder8 = "unknown-length";
            }
            stringBuilder4 = new StringBuilder();
            stringBuilder4.append("<-- ");
            stringBuilder4.append(proceed.code());
            stringBuilder4.append(' ');
            stringBuilder4.append(proceed.message());
            stringBuilder4.append(' ');
            stringBuilder4.append(proceed.request().url());
            stringBuilder4.append(" (");
            stringBuilder4.append(nanoTime);
            stringBuilder4.append("ms");
            if (this.d) {
                stringBuilder8 = "";
            } else {
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append(", ");
                stringBuilder3.append(stringBuilder8);
                stringBuilder3.append(" body");
                stringBuilder8 = stringBuilder3.toString();
            }
            stringBuilder4.append(stringBuilder8);
            stringBuilder4.append(')');
            a(stringBuilder4.toString());
            if (this.d) {
                Headers headers2 = proceed.headers();
                int size2 = headers2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(headers2.name(i2));
                    stringBuilder.append(": ");
                    stringBuilder.append(headers2.value(i2));
                    a(stringBuilder.toString());
                }
                if (!this.c || !HttpHeaders.hasBody(proceed)) {
                    a("<-- END HTTP");
                } else if (a(proceed.headers())) {
                    a("<-- END HTTP (encoded body omitted)");
                } else {
                    BufferedSource source = body2.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer2 = source.buffer();
                    Charset charset2 = b;
                    MediaType contentType2 = body2.contentType();
                    if (contentType2 != null) {
                        charset2 = contentType2.charset(b);
                    }
                    if (a(buffer2)) {
                        if (contentLength != 0) {
                            a("");
                            a(buffer2.clone().readString(charset2));
                        }
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("<-- END HTTP (");
                        stringBuilder7.append(buffer2.size());
                        stringBuilder7.append("-byte body)");
                        a(stringBuilder7.toString());
                    } else {
                        a("");
                        stringBuilder7 = new StringBuilder();
                        stringBuilder7.append("<-- END HTTP (binary ");
                        stringBuilder7.append(buffer2.size());
                        stringBuilder7.append("-byte body omitted)");
                        a(stringBuilder7.toString());
                        return proceed;
                    }
                }
            }
            return proceed;
        } catch (Exception e) {
            stringBuilder7 = new StringBuilder();
            stringBuilder7.append("<-- HTTP FAILED: ");
            stringBuilder7.append(e);
            a(stringBuilder7.toString());
            throw e;
        }
    }

    private void a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--> ");
        stringBuilder.append(str);
//        LogUtils.d("ApiUrl", stringBuilder.toString());
    }

    private void b(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--> ");
        stringBuilder.append(str);
//        LogUtils.d("ApiUrls", stringBuilder.toString());
    }

    static boolean a(Buffer buffer) {
        try {
            Buffer buffer2 = new Buffer();
            buffer.copyTo(buffer2, 0, buffer.size() < 64 ? buffer.size() : 64);
            for (int i = 0; i < 16 && !buffer2.exhausted(); i++) {
                int readUtf8CodePoint = buffer2.readUtf8CodePoint();
                if (Character.isISOControl(readUtf8CodePoint) && !Character.isWhitespace(readUtf8CodePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException unused) {
            return false;
        }
    }

    private boolean a(Headers headers) {
        String str = headers.get("Content-Encoding");
        return (str == null || str.equalsIgnoreCase("identity")) ? false : true;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public void b(boolean z) {
        this.d = z;
    }

    public static boolean isEmpty(String... strArr) {
        for (String obj : strArr) {
            if (TextUtils.isEmpty(obj) || "".equals(obj.trim())) {
                return false;
            }
        }
        return true;
    }

    public static String getToken(Context context) {
        return SPUtils.getInstance().getString("token");
    }

    public static String getImei() {
        return SPUtils.getInstance().getString("imei");
    }


}