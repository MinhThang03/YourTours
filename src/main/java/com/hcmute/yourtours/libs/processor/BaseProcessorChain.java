package com.hcmute.yourtours.libs.processor;

import org.springframework.transaction.annotation.Transactional;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.RestException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseProcessorChain<REQ, RESP> implements IProcessorChain<REQ, RESP> {

    private final List<BaseProcessor<REQ, RESP>> processors = new ArrayList<>();

    public BaseProcessorChain(List<BaseProcessor<REQ, RESP>> processors) {
        this.processors.addAll(processors);
    }

    @Transactional
    @Override
    public RESP startProcess(REQ request) {
        return doProcess(request, processors.iterator());
    }

    private RESP doProcess(REQ request, Iterator<BaseProcessor<REQ, RESP>> iterator) {
        if (!iterator.hasNext()) {
            throw new RestException(ErrorCode.BAD_REQUEST);
        }
        return iterator.next().doChainProcess(request, this, iterator);
    }

    public static abstract class BaseProcessor<REQ, RESP> {

        private RESP doChainProcess(
                REQ request,
                BaseProcessorChain<REQ, RESP> processorChain,
                Iterator<BaseProcessor<REQ, RESP>> iterator
        ) {

            if (this.canProcess(request)) {
                return this.doProcess(request);
            }

            return processorChain.doProcess(request, iterator);
        }

        protected abstract boolean canProcess(REQ request);

        protected abstract RESP doProcess(REQ request);

    }

}
