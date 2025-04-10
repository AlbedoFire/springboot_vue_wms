package com.wms.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customers")
public class Customer {
    @TableId(type = IdType.AUTO)
    private Integer customerId;

    @TableField("customer_name")
    private String customerName;

    @TableField("contact_person")
    private String contactPerson;

    @TableField("contact_email")
    private String contactEmail;

    @TableField("contact_phone")
    private String contactPhone;

    @TableField("address")
    private String address;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}