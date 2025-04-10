

package com.wms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Result;
import com.wms.entity.Invoice;
import com.wms.entity.Invoice;
import com.wms.service.InvoiceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Invoice invoice){
        return invoiceService.save(invoice)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Invoice invoice){
        return invoiceService.updateById(invoice)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return invoiceService.removeById(id)?Result.suc():Result.fail();
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

    // 获取所有发票
    @GetMapping
    public Result getAllInvoices() {
        List<Invoice> invoices = invoiceService.list();
        return Result.suc(invoices, (long) invoices.size());
    }

    // 更新发票
    @PutMapping("/{id}")
    public Result updateInvoice(@PathVariable Integer id, @RequestBody Invoice invoice) {
        invoice.setInvoiceId(id);
        boolean updated = invoiceService.updateById(invoice);
        if (updated) {
            return Result.suc(invoice);
        } else {
            return Result.fail();
        }
    }

    // 删除发票
    @DeleteMapping("/{id}")
    public Result deleteInvoice(@PathVariable Integer id) {
        boolean deleted = invoiceService.removeById(id);
        if (deleted) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
    // 分页查询发票
    @GetMapping("/page")
    public Result getInvoicesByPage(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size) {
        // 创建分页对象
        Page<Invoice> pageObj = new Page<>(page, size);
        // 执行分页查询
        pageObj = invoiceService.page(pageObj);
        // 封装结果
        return Result.suc(pageObj.getRecords(), pageObj.getTotal());
    }

}
