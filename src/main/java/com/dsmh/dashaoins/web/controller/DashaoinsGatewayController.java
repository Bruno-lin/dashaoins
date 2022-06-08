package com.dsmh.dashaoins.web.controller;

import cn.hutool.json.JSONUtil;
import com.dsmh.common.trouble.Trouble;
import com.dsmh.dashaoins.acl.INSClientManager;
import com.dsmh.dashaoins.manager.DashaoinsManager;
import com.dsmh.insurance.sdk.MethodRoute;
import com.dsmh.insurance.sdk.client.ins.INSClient;
import com.dsmh.insurance.sdk.exception.ClientException;
import com.dsmh.insurance.sdk.message.BaseMessage;
import com.dsmh.insurance.sdk.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TeamScorpio
 * @since 2022/4/17
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/gateway.do")
public class DashaoinsGatewayController {

    private DashaoinsManager dashaoinsManager;

    private INSClientManager insClientManager;

    @Autowired
    public DashaoinsGatewayController(DashaoinsManager dashaoinsManager, INSClientManager insClientManager) {
        this.dashaoinsManager = dashaoinsManager;
        this.insClientManager = insClientManager;
    }

//    @Autowired
//    private RequestLogService logService;

    @PostMapping
    public BaseResponse<?> gateway(@RequestBody BaseMessage<?> message) {
        log.info("traceId: {}, received message: {}", message.getTraceId(), JSONUtil.toJsonStr(message));
        INSClient insClient = insClientManager.getInsClient();
        if (insClient == null) {
            BaseResponse<Object> response = new BaseResponse<>();
            response.setCode("50000");
            response.setMsg("Invalid appId");
            return response;
        }
        MethodRoute methodRoute;
        BaseResponse<?> response;
        try {
            insClient.checkSign(message);
            insClient.decryptMessage(message);
            log.info("traceId: {}, decrypted message: {}", message.getTraceId(), JSONUtil.toJsonStr(message.getData()));
            methodRoute = MethodRoute.valueOfMethodName(message.getMethod());
            switch (methodRoute) {
                case GROUP_SYNC_INSURE:
                    GroupSyncInsureResponse insureResponse = GroupSyncInsureResponse.builder()
                            .setDsPolicyNo("110")
                            .setInsPolicyNo("110")
                            .addSubPolicy(GroupSyncInsureResponse.SubPolicy.builder()
                                    .dsSubPolicyNo("111")
                                    .insSubPolicyNo("111")
                                    .build())
                            .build();
                    response = insClient.success(message, insureResponse);
                    break;
                case GROUP_INSURED_APPLY:
//                    GroupInsuredApplyResponse.builder()
//                            .build();
                    response = insClient.success(message, new NoContentResponse());
                    break;
                case INDIVIDUAL_POLICY_QUERY:
                    IndividualPolicyQueryResponse individualPolicyQueryResponse = IndividualPolicyQueryResponse.builder()
                            .insSubPolicyNo("111")
                            .pdfUrl("https://www.baidu.com")
                            .build();
                    response = insClient.success(message, individualPolicyQueryResponse);
                    break;
                case INDIVIDUAL_CLAIM_APPLY:
                    IndividualClaimApplyResponse individualClaimApplyResponse = IndividualClaimApplyResponse.builder()
                            .reportNo("111")
                            .caseNo("111")
                            .build();
                    response = insClient.success(message, individualClaimApplyResponse);
                    break;
                case GROUP_DUMMY_REVOKE:
                    response = insClient.success(message, new NoContentResponse());
                    break;
                default:
                    throw new ClientException("Invalid method");
            }
            return response;
        } catch (ClientException | Trouble e) {
            log.warn("traceId: {}, Business failed: {}", message.getTraceId(), e.getMessage());
            response = insClient.invalidRequest(message);
            return response;
        } catch (Exception e) {
            log.error("traceId: {}, deal message error", message.getTraceId(), e);
            response = insClient.systemError(message);
            return response;
//        } finally {
//            logService.logINSRequest(RequestLog.builder()
//                    .appId(message.getAppId())
//                    .method(message.getMethod())
//                    .traceId(message.getTraceId())
//                    .message(JSONUtil.toJsonStr(message))
//                    .response(methodRoute == MethodRoute.INDIVIDUAL_MEDIA_DOWNLOAD ? "ignore": JSONUtil.toJsonStr(response))
//                    .build());
        }
    }


}
