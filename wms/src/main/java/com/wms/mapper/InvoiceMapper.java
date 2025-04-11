/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.Invoice;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

@Mapper
public interface InvoiceMapper extends BaseMapper<Invoice> {
    // 在这里可以添加自定义的查询方法（如果需要）
    @Select("SELECT MAX(id) from invoice")
    Long getLastInsertId();
    
}