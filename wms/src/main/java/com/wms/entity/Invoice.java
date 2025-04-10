package com.wms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("invoices")
public class Invoice {
    @TableId(type = IdType.AUTO)
    private Integer invoiceId;

    @TableField("invoice_number")
    private String invoiceNumber;

    @TableField("issue_date")
    private LocalDate issueDate;

    @TableField("due_date")
    private LocalDate dueDate;

    @TableField("total_amount")
    private Double totalAmount;

    @TableField("tax_amount")
    private Double taxAmount;

    @TableField("currency")
    private String currency;

    @TableField("status")
    private String status;

    @TableField("customer_id")
    private Integer customerId;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}