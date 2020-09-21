package com.pc.dubboapi.request;



/**
 *
 * @author pengchao
 * @date 17:24 2020-09-18
 */
public abstract class AbstractTradeRequest implements TradeRequest {

    public static final String ALIPAY = "alipay";
    public static final String WEIXIN = "weixin";


    private Long id;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
