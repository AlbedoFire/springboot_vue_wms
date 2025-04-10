/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.entity.se;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("invoice_detail") // 指定表名
public class InvoiceDetail {
    @TableId(type = IdType.AUTO) // 主键，自增
    private Integer id;

    @TableField("invoice_id")
    private Integer invoiceId; // 发票主表ID

    @TableField("name")
    private String name; // 项目名称

    @TableField("model")
    private String model; // 规格型号

    @TableField("unit")
    private String unit; // 单位

    @TableField("count")
    private Integer count; // 数量

    @TableField("price")
    private BigDecimal price; // 单价

    @TableField("amount")
    private BigDecimal amount; // 金额

    @TableField("tax_rate")
    private BigDecimal taxRate; // 税率

    @TableField("tax_amount")
    private BigDecimal taxAmount; // 税额

    @TableField("pdf_name")
    private String pdfName; // PDF名字
}