/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Result;
import com.wms.entity.InvoiceDetail;
import com.wms.entity.Invoices;
import com.wms.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-details")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    // 新增发票明细
    @PostMapping("/save")
    public Result save(@RequestBody InvoiceDetail invoiceDetail) {
        return invoiceDetailService.save(invoiceDetail) ? Result.suc() : Result.fail();
    }

    // 更新发票明细
    @PostMapping("/update")
    public Result update(@RequestBody InvoiceDetail invoiceDetail) {
        return invoiceDetailService.updateById(invoiceDetail) ? Result.suc() : Result.fail();
    }

    // 删除发票明细
    @GetMapping("/del")
    public Result del(@RequestParam Integer id) {
        return invoiceDetailService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 创建发票明细
    @PostMapping
    public Result createInvoiceDetail(@RequestBody InvoiceDetail invoiceDetail) {
        boolean saved = invoiceDetailService.save(invoiceDetail);
        if (saved) {
            return Result.suc(invoiceDetail);
        } else {
            return Result.fail();
        }
    }
    // 根据ID获取发票明细
    @GetMapping("/{id}")
    public Result getInvoiceDetailById(@PathVariable Integer id) {
        InvoiceDetail invoiceDetail = invoiceDetailService.getById(id);
        if (invoiceDetail != null) {
            return Result.suc(invoiceDetail);
        } else {
            return Result.fail();
        }
    }
    // 根据ID更新发票明细
    @PutMapping("/{id}")
    public Result updateInvoiceDetailById(@PathVariable Integer id,@RequestBody InvoiceDetail invoiceDetail) {
        invoiceDetail.setId(id);
        boolean updated = invoiceDetailService.updateById(invoiceDetail);
        if (updated) {
            return Result.suc(invoiceDetail);
        }else {
            return Result.fail();
        }

    }

    // 根据ID删除发票明细
    @DeleteMapping("/{id}")
    public Result deleteInvoiceDetailById(@PathVariable Integer id) {
        boolean deleted = invoiceDetailService.removeById(id);
        if (deleted) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }

    // 获取所有发票明细
    @GetMapping
    public Result getAllInvoiceDetails() {
        List<InvoiceDetail> invoiceDetails = invoiceDetailService.list();
        return Result.suc(invoiceDetails, (long) invoiceDetails.size());
    }

    // 分页查询发票明细
    @GetMapping("/page")
    public Result getInvoiceDetailsByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {
        Page<InvoiceDetail> pageObj = new Page<>(page, size);
        pageObj = invoiceDetailService.page(pageObj);
        return Result.suc(pageObj.getRecords(), pageObj.getTotal());
    }
    @DeleteMapping("/invoice/{invoiceId}")
    public Result deleteInvoiceDetailsByInvoiceId(@PathVariable Integer invoiceId) {
        LambdaQueryWrapper<InvoiceDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvoiceDetail::getInvoiceId, invoiceId);
        boolean deleted = invoiceDetailService.remove(wrapper);
        if (deleted) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
    @GetMapping("/invoice/del/{invoiceId}")
    public Result delByInvoiceId(@PathVariable Integer invoiceId){
        LambdaQueryWrapper<InvoiceDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvoiceDetail::getInvoiceId, invoiceId);
        boolean deleted = invoiceDetailService.remove(wrapper);
        if (deleted) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }

}
