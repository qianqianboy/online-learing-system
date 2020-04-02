package com.qianqian.edu.common.dto.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单信息传输对象
 * @author minsiqian
 * @date 2020/3/17 19:10
 */
@Data
public class OrderInfoForm {

    @ApiModelProperty("订单id")
    private String id;

    @ApiModelProperty("商品名称")
    private String courseName;

    @ApiModelProperty("卖家名称")
    private String bossName;

    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单优惠金额")
    private BigDecimal concessionalPrice;

    @ApiModelProperty("订单支付方式")
    private String payment_way;

    @ApiModelProperty("订单名称")
    private String orderName;

    @ApiModelProperty("订单描述")
    private String orderDescription;

    public static final String ALI_PAY="支付宝";



}
