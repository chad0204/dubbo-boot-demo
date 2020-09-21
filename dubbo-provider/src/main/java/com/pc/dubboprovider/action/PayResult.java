package com.pc.dubboprovider.action;

/**
 *
 * @author pengchao
 * @date 15:21 2020-09-21
 */
public abstract class PayResult {
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
