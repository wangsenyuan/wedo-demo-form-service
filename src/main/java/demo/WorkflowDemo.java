package demo;

import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceHeaders;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceRequest;
import com.aliyun.dingtalkworkflow_1_0.models.StartProcessInstanceResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.wedo.demo.config.Constant;
import com.wedo.demo.domain.fee.entity.FeeEntity;

public class WorkflowDemo {
    /**
     * 使用 Token 初始化账号Client
     *
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dingtalkworkflow_1_0.Client createClient() throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return new com.aliyun.dingtalkworkflow_1_0.Client(config);
    }

    public static String run(String token, FeeEntity dto) {

        StartProcessInstanceHeaders startProcessInstanceHeaders = new StartProcessInstanceHeaders();
        startProcessInstanceHeaders.xAcsDingtalkAccessToken = token;

        StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues0 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues().setName("事由").setValue(dto.getReason()).setExtValue("总个数:1").setComponentType("TextField");

        StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues1 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues().setName("金额").setValue(dto.getAmount().toString()).setComponentType("NumberField");

        StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues formComponentValues2 = new StartProcessInstanceRequest.StartProcessInstanceRequestFormComponentValues().setName("类型").setValue(dto.getType()).setComponentType("DDSelectField");

        StartProcessInstanceRequest startProcessInstanceRequest = new StartProcessInstanceRequest().setOriginatorUserId(Constant.USER_ID).setProcessCode("PROC-D5D5D54C-C651-4060-A56D-A76395A27FDC").setDeptId(-1l).setFormComponentValues(java.util.Arrays.asList(formComponentValues0, formComponentValues1, formComponentValues2));
        try {
            StartProcessInstanceResponse response = createClient().startProcessInstanceWithOptions(startProcessInstanceRequest, startProcessInstanceHeaders, new RuntimeOptions());
            return response.body.getInstanceId();
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
    }

}
