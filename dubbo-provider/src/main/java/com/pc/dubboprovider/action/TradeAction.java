package com.pc.dubboprovider.action;

import com.pc.dubboprovider.action.param.PayParam;

/**
 */
public interface TradeAction<P extends PayParam,R extends PayResult> {
    /**
     * 执行业务
     * @param p
     * @return
     */
    R doBusiness(P p);

}
