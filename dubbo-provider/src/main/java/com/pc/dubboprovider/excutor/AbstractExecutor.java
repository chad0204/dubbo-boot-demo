package com.pc.dubboprovider.excutor;

import com.pc.dubboapi.request.TradeRequest;
import com.pc.tpt.core.TptExecutor;
import com.twodfire.share.result.Result;

/**
 *
 * @author pengchao
 * @date 18:00 2020-09-18
 */
public abstract class AbstractExecutor<REQ extends TradeRequest, RSP> implements TptExecutor<REQ, Result<RSP>> {
    @Override
    public Result<RSP> execute(REQ req) {
        return _execute(req);
    }

    protected abstract Result<RSP> _execute(REQ req);

    @Override
    public boolean isDisabled() {
        return false;
    }
}
