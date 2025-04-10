/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("invoice") // 指定表名
public class Invoice {
    @TableId(type = IdType.AUTO) // 主键，自增
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("machine_number")
    private String machineNumber;

    @TableField("code")
    private String code;

    @TableField("number")
    private String number;

    @TableField("date")
    private Date date;

    @TableField("checksum")
    private String checksum;

    @TableField("buyer_name")
    private String buyerName;

    @TableField("buyer_code")
    private String buyerCode;

    @TableField("buyer_address")
    private String buyerAddress;

    @TableField("buyer_account")
    private String buyerAccount;

    @TableField("password")
    private String password;

    @TableField("amount")
    private BigDecimal amount;

    @TableField("tax_amount")
    private BigDecimal taxAmount;

    @TableField("total_amount_string")
    private String totalAmountString;

    @TableField("total_amount")
    private BigDecimal totalAmount;

    @TableField("seller_name")
    private String sellerName;

    @TableField("seller_code")
    private String sellerCode;

    @TableField("seller_address")
    private String sellerAddress;

    @TableField("seller_account")
    private String sellerAccount;

    @TableField("payee")
    private String payee;

    @TableField("reviewer")
    private String reviewer;

    @TableField("drawer")
    private String drawer;

    @TableField("type")
    private String type;


}