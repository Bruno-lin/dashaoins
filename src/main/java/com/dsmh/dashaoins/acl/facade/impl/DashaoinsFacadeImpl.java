package com.dsmh.dashaoins.acl.facade.impl;

import com.dsmh.dashaoins.acl.facade.DashaoinsFacade;
import com.dsmh.insurance.sdk.message.ClaimStatusCallbackMessage;
import com.dsmh.insurance.sdk.message.IndividualMediaDownloadMessage;
import com.dsmh.insurance.sdk.message.IndividualPolicyCallbackMessage;
import com.dsmh.insurance.sdk.message.PolicyStatusCallbackMessage;
import com.dsmh.insurance.sdk.response.IndividualMediaDownloadResponse;
import com.dsmh.insurance.sdk.response.NoContentResponse;
import org.springframework.stereotype.Service;


@Service
public class DashaoinsFacadeImpl implements DashaoinsFacade {

    @Override
    public NoContentResponse informInsPolicyResult(IndividualPolicyCallbackMessage individualPolicyCallbackMessage) {
        return null;
    }

    @Override
    public IndividualMediaDownloadResponse downloadMedia(IndividualMediaDownloadMessage individualMediaDownloadMessage) {
        return null;
    }

    @Override
    public NoContentResponse informClaimStatusResult(ClaimStatusCallbackMessage claimStatusCallbackMessage) {
        return null;
    }

    @Override
    public NoContentResponse informPolicyStatusResult(PolicyStatusCallbackMessage policyStatusCallbackMessage) {
        return null;
    }
}
