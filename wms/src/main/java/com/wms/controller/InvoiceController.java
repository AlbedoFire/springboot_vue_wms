/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Result;
import com.wms.entity.Invoice;
import com.wms.entity.Invoices;
import com.wms.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    // 新增发票
    @PostMapping("/save")
    public Result save(@RequestBody Invoice invoice) {
        return invoiceService.save(invoice) ? Result.suc() : Result.fail();
    }

    // 更新发票
    @PostMapping("/update")
    public Result update(@RequestBody Invoice invoice) {
        return invoiceService.updateById(invoice) ? Result.suc() : Result.fail();
    }

    // 删除发票
    @GetMapping("/del")
    public Result del(@RequestParam Integer id) {
        return invoiceService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 根据ID获取发票
    @GetMapping("/{id}")
    public Result getInvoiceById(@PathVariable Integer id) {
        Invoice invoice = invoiceService.getById(id);
        if (invoice != null) {
            return Result.suc(invoice);
        } else {
            return Result.fail();
        }
    }

    //根据ID更新发票
    @PutMapping("/{id}")
    public Result updateInvoice(@PathVariable Integer id, @RequestBody Invoice invoice) {
        invoice.setId(id);
        boolean updated = invoiceService.updateById(invoice);
        if (updated) {
            return Result.suc(invoice);
        } else {
            return Result.fail();
        }
    }

    // 创建发票
    @PostMapping
    public Result createInvoice(@RequestBody Invoice invoice) {
        boolean saved = invoiceService.save(invoice);
        if (saved) {
            return Result.suc(invoice);
        } else {
            return Result.fail();
        }
    }
    //根据ID删除发票
    @DeleteMapping("/{id}")
    public Result deleteInvoice(@PathVariable Integer id) {
        boolean deleted = invoiceService.removeById(id);
        if (deleted) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }

    // 获取所有发票
    @GetMapping
    public Result getAllInvoices() {
        List<Invoice> invoices = invoiceService.list();
        return Result.suc(invoices, (long) invoices.size());
    }

    // 分页查询发票
    @GetMapping("/page")
    public Result getInvoicesByPage(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        Page<Invoice> pageObj = new Page<>(page, size);
        pageObj = invoiceService.page(pageObj);
        return Result.suc(pageObj.getRecords(), pageObj.getTotal());
    }
    @GetMapping("/lastInsertId")
    public Result getLastInsertId() {
        Long lastInsertId = invoiceService.getLastInsertId();
        if (lastInsertId == null) {
            return Result.fail();
        }
        return Result.suc(lastInsertId,1L);
    }
    @GetMapping("/updateStatus/{id}")
    public Result updateStatus(@PathVariable Integer id) {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setStatus("已报销");
        boolean updated = invoiceService.updateById(invoice);
        if (updated) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
}