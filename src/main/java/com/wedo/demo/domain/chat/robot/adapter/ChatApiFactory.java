package com.wedo.demo.domain.chat.robot.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wedo.demo.config.chat.ChatClientHttpProperty;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Invocation;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Configuration
public class ChatApiFactory {
    private static final Logger logger = LoggerFactory.getLogger(ChatApiFactory.class);

    private static final class InvocationLogger implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long startNanos = System.nanoTime();
            Response response = chain.proceed(request);
            long elapsedNanos = System.nanoTime() - startNanos;

            Invocation invocation = request.tag(Invocation.class);
            if (invocation != null) {
                logger.info(String.format("%s.%s %s HTTP %s (%.0f ms)%n", invocation.method().getDeclaringClass().getSimpleName(), invocation.method().getName(), invocation.arguments(), response.code(), elapsedNanos / 1_000_000.0));
            }

            if (!response.isSuccessful()) {
                Charset defaultCharset = Charset.forName("UTF-8");

                logger.info("request url {} fail", request.url());

                RequestBody requestBody = request.body();
                boolean hasRequestBody = requestBody != null;
                if (hasRequestBody) {
                    Buffer bufferedSink = new Buffer();
                    requestBody.writeTo(bufferedSink);
                    Charset charset = requestBody.contentType().charset();
                    charset = charset == null ? defaultCharset : charset;
                    logger.info("request body: {}", bufferedSink.readString(charset));
                }


                //获得返回的body，注意此处不要使用responseBody.string()获取返回数据，原因在于这个方法会消耗返回结果的数据(buffer)
                ResponseBody responseBody = response.body();
                //为了不消耗buffer，我们这里使用source先获得buffer对象，然后clone()后使用
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                //获得返回的数据
                Buffer buffer = source.buffer();
                //使用前clone()下，避免直接消耗
                logger.info("response:" + buffer.clone().readString(defaultCharset));
            }
            return response;
        }
    }

    private static OkHttpClient.Builder createOkHttpClient(ChatClientHttpProperty httpProperty) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (httpProperty.getConnectTimeout() > 0) {
            builder.connectTimeout(httpProperty.getConnectTimeout(), TimeUnit.SECONDS);
        }
        if (httpProperty.getReadTimeout() > 0) {
            builder.readTimeout(httpProperty.getReadTimeout(), TimeUnit.SECONDS);
        }

        if (httpProperty.getWriteTimeout() > 0) {
            builder.writeTimeout(httpProperty.getWriteTimeout(), TimeUnit.SECONDS);
        }

        return builder;
    }

    @Autowired
    private ChatClientHttpProperty httpProperty;

    @Bean
    public ChatApi getChatApi() {
        OkHttpClient.Builder builder = createOkHttpClient(httpProperty);
        builder.addInterceptor(new InvocationLogger());

        OkHttpClient client = builder.build();

        ObjectMapper objectMapper = new ObjectMapper();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(httpProperty.getEndpoint()).client(client).addConverterFactory(JacksonConverterFactory.create(objectMapper)).build();

        return retrofit.create(ChatApi.class);
    }

}
