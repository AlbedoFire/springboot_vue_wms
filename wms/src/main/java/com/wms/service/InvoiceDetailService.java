/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.InvoiceDetail;

import java.util.List;

public interface InvoiceDetailService extends IService<InvoiceDetail> {
    public List<InvoiceDetail> selectInvoicDetailsByinvoiceId(Integer id);
}
