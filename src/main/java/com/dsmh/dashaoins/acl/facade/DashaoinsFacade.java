package com.dsmh.dashaoins.acl.facade;

import com.dsmh.insurance.sdk.message.ClaimStatusCallbackMessage;
import com.dsmh.insurance.sdk.message.IndividualMediaDownloadMessage;
import com.dsmh.insurance.sdk.message.IndividualPolicyCallbackMessage;
import com.dsmh.insurance.sdk.message.PolicyStatusCallbackMessage;
import com.dsmh.insurance.sdk.response.IndividualMediaDownloadResponse;
import com.dsmh.insurance.sdk.response.NoContentResponse;

public interface DashaoinsFacade {

    NoContentResponse informInsPolicyResult(IndividualPolicyCallbackMessage individualPolicyCallbackMessage);

    IndividualMediaDownloadResponse downloadMedia(IndividualMediaDownloadMessage individualMediaDownloadMessage);

    NoContentResponse informClaimStatusResult(ClaimStatusCallbackMessage claimStatusCallbackMessage);

    NoContentResponse informPolicyStatusResult(PolicyStatusCallbackMessage policyStatusCallbackMessage);
}
