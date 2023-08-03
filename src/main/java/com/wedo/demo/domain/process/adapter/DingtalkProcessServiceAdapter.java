package com.wedo.demo.domain.process.adapter;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkworkflow_1_0.Client;
import com.aliyun.dingtalkworkflow_1_0.models.*;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.wedo.demo.domain.process.config.DingtalkConfiguration;
import com.wedo.demo.domain.process.entity.ProcessInstanceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DingtalkProcessServiceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DingtalkProcessServiceAdapter.class);

    @Autowired
    private DingtalkConfiguration configuration;


    private String getToken() {
        try {
            com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
            config.protocol = "https";
            config.regionId = "central";

            com.aliyun.dingtalkoauth2_1_0.Client client = new com.aliyun.dingtalkoauth2_1_0.Client(config);
            com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest getAccessTokenRequest = new com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest().setAppKey(configuration.getAppKey()).setAppSecret(configuration.getAppSecret());
            GetAccessTokenResponse resp = client.getAccessToken(getAccessTokenRequest);
            return resp.getBody().accessToken;
        } catch (Exception err) {
            err.printStackTrace();
            throw new RuntimeException(err);
        }
    }

    public String submit(ProcessInstanceEntity entity) {
        StartProcessInstanceHeaders startProcessInstanceHeaders = new StartProcessInstanceHeaders();
        startProcessInstanceHeaders.xAcsDingtalkAccessToken = getToken();

        List<StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues> values = new ArrayList<>();

        for (Map.Entry<String, String> prop : entity.getFormValues().entrySet()) {
            StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues compValue = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues();

            compValue.setName(prop.getKey());
            compValue.setValue(prop.getValue());
            compValue.setComponentType("TextField");

            values.add(compValue);
        }

        StartProcessInstanceRequest startProcessInstanceRequest = new StartProcessInstanceRequest().setOriginatorUserId(configuration.getMockUser()).setProcessCode(entity.getProcessCode()).setDeptId(-1l).setFormComponentValues(values);
        try {
            Client client = getWorkflowClient();
            StartProcessInstanceResponse response = client.startProcessInstanceWithOptions(startProcessInstanceRequest, startProcessInstanceHeaders, new RuntimeOptions());
            return response.body.getInstanceId();
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    private static Client getWorkflowClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        Client client = new Client(config);
        return client;
    }

    public void terminateIfAny(String processKey) {
        logger.debug("try terminate {}", processKey);
        TerminateProcessInstanceHeaders headers = new TerminateProcessInstanceHeaders();
        headers.xAcsDingtalkAccessToken = getToken();

        TerminateProcessInstanceRequest request = new TerminateProcessInstanceRequest().setProcessInstanceId(processKey);

        try {
            Client client = getWorkflowClient();
            client.terminateProcessInstanceWithOptions(request, headers, new com.aliyun.teautil.models.RuntimeOptions());
        } catch (Exception ex) {
            ex.printStackTrace();
            // ignore
        }
    }
}
