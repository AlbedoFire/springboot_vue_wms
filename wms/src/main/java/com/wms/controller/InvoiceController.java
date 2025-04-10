

package com.wms.controller;


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
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        invoiceService.save(invoice);
        return invoice;
    }

    // 根据ID获取发票
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Integer id) {
        return invoiceService.getById(id);
    }

    // 获取所有发票
    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.list();
    }

    // 更新发票
    @PutMapping("/{id}")
    public Invoice updateInvoice(@PathVariable Integer id, @RequestBody Invoice invoice) {
        invoice.setInvoiceId(id);
        invoiceService.updateById(invoice);
        return invoice;
    }

    // 删除发票
    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Integer id) {
        invoiceService.removeById(id);
    }
}
