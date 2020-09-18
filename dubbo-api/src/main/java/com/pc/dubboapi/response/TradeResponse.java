package com.pc.dubboapi.response;


/**
 *
 * @author pengchao
 * @date 17:27 2020-09-18
 */
public class TradeResponse implements Response {

    private Double fee;

    private String type;

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
