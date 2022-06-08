package com.dsmh.dashaoins.acl;

import com.dsmh.insurance.sdk.client.ClientConfig;
import com.dsmh.insurance.sdk.client.ClientHook;
import com.dsmh.insurance.sdk.client.DefaultClientHook;
import com.dsmh.insurance.sdk.client.ins.INSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class INSClientManager implements InitializingBean, ApplicationListener<RefreshScopeRefreshedEvent> {

    private final ClientHook clientHook = new DefaultClientHook();

    private final Map<String, INSClient> clientMap = new HashMap<>();

    private DashaoinsClientProperties clientProperties;

    @Autowired
    public INSClientManager(DashaoinsClientProperties clientProperties) {
        this.clientProperties = clientProperties;
    }


    public INSClient getInsClient() {
        return clientMap.get("dashaoins");
    }

    private INSClient createClient() {
        ClientConfig.ClientConfigBuilder clientConfigBuilder = ClientConfig.builder()
                .appId("123456")
                .secret("123456")
                .maxRetries(3);
        String defaultUrl = getUrl("123", "default");
        return new INSClient(clientConfigBuilder.url(defaultUrl).build(), clientHook);
    }

    private String getUrl(String companyNo, String urlGroup) {
        try {
            return clientProperties.getUrl().get(companyNo).get(urlGroup);
        } catch (Exception e) {
            throw new IllegalStateException("url not found for companyNo: " + companyNo + ", urlGroup: " + urlGroup);
        }
    }
    @Override
    public void afterPropertiesSet() {
        INSClient client = createClient();
        clientMap.put("dashaoins", client);
        log.info("INSClientManager init, clientMap : {}", clientMap);
    }

    @Override
    public void onApplicationEvent(@NotNull RefreshScopeRefreshedEvent refreshScopeRefreshedEvent) {
        afterPropertiesSet();
    }
}
