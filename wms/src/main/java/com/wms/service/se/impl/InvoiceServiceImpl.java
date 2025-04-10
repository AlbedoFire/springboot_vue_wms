/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.service.se.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.se.Invoice;
import com.wms.mapper.se.InvoiceMapper;
import com.wms.service.se.InvoiceService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {
}
