package com.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.Invoices;
import com.wms.mapper.InvoicesMapper;
import com.wms.service.InvoicesService;
import org.springframework.stereotype.Service;

@Service
public class InvoicesServiceImpl extends ServiceImpl<InvoicesMapper, Invoices> implements InvoicesService {
}
