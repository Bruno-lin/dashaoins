package com.dsmh.dashaoins.acl;

import com.dsmh.insurance.sdk.message.PayeeInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Map;

/**
 * @author TeamScorpio
 * @since 2022/4/18
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "insurance.client")
public class DashaoinsClientProperties {


    private int batchSubPolicySize = 100;


    private PayeeInfo payee = PayeeInfo.builder()
            .cnapsCode("104584002662")
            .accountNo("753675516747")
            .accountName("深圳大韶医药健康科技有限公司")
            .bankName("中国银行深圳中心区支行")
            .build();

    private Map<String, Map<String, String>> url;

    private boolean enable = true;


}
