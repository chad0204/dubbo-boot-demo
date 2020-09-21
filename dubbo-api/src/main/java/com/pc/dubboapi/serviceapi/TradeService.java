package com.pc.dubboapi.serviceapi;

import com.pc.dubboapi.request.TradeRequest;
import com.pc.dubboapi.response.TradeResponse;
import com.twodfire.share.result.Result;

/**
 *
 * @author pengchao
 * @date 17:39 2020-09-18
 */
public interface TradeService {

    Result<TradeResponse> getTradeBill(TradeRequest request);

}
