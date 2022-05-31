package com.dsmh.dashaoins.acl;

import com.dsmh.insurance.sdk.client.ClientHook;
import com.dsmh.insurance.sdk.client.DefaultClientHook;
import com.dsmh.insurance.sdk.client.ins.INSClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class INSClientManager implements InitializingBean {

    private final ClientHook clientHook = new DefaultClientHook();


    public INSClient getClientByInsId(String insId) {
        return new INSClient();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
