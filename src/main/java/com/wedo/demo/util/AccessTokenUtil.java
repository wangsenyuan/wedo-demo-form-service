package com.wedo.demo.util;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.wedo.demo.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取access_token工具类
 */
public class AccessTokenUtil {
    private static final Logger bizLogger = LoggerFactory.getLogger(AccessTokenUtil.class);

    /**
     * 使用 Token 初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkoauth2_1_0.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";

        return new com.aliyun.dingtalkoauth2_1_0.Client(config);
    }

    public static String getToken() {
        try {
            com.aliyun.dingtalkoauth2_1_0.Client client = createClient();
            com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest()
                    .setAppKey(Constant.APP_KEY)
                    .setAppSecret(Constant.APP_SECRET);
            GetAccessTokenResponse resp = client.getAccessToken(getAccessTokenRequest);
            return resp.getBody().accessToken;
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
    }
}
