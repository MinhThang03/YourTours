package com.hcmute.yourtours.libs.processor;

import com.hcmute.yourtours.libs.exceptions.InvalidException;

public interface IProcessorChain<REQ, RESP> {
    RESP startProcess(REQ request) throws InvalidException;
}
