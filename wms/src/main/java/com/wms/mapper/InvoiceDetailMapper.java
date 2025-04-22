/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.InvoiceDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InvoiceDetailMapper extends BaseMapper<InvoiceDetail> {
    // 在这里可以添加自定义的查询方法（如果需要）
    @Select("select * from invoice_detail where invoice_id = #{invoiceId}")
    List<InvoiceDetail> selectByInvoiceIdInvoiceDetailList(@Param("invoiceId") Integer invoice_id);
}