

package com.wms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Result;
import com.wms.entity.Invoices;
import com.wms.service.InvoicesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoicesController {
    @Autowired
    private InvoicesService invoicesService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Invoices invoices){
        return invoicesService.save(invoices)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Invoices invoices){
        return invoicesService.updateById(invoices)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return invoicesService.removeById(id)?Result.suc():Result.fail();
    }
    // 创建发票
    @PostMapping
    public Result createInvoice(@RequestBody Invoices invoices) {
        boolean saved = invoicesService.save(invoices);
        if (saved) {
            return Result.suc(invoices);
        } else {
            return Result.fail();
        }
    }

    // 根据ID获取发票
    @GetMapping("/{id}")
    public Result getInvoiceById(@PathVariable Integer id) {
        Invoices invoices = invoicesService.getById(id);
        if (invoices != null) {
            return Result.suc(invoices);
        } else {
            return Result.fail();
        }
    }

    // 获取所有发票
    @GetMapping
    public Result getAllInvoices() {
        List<Invoices> invoices = invoicesService.list();
        return Result.suc(invoices, (long) invoices.size());
    }

    // 更新发票
    @PutMapping("/{id}")
    public Result updateInvoice(@PathVariable Integer id, @RequestBody Invoices invoices) {
        invoices.setInvoiceId(id);
        boolean updated = invoicesService.updateById(invoices);
        if (updated) {
            return Result.suc(invoices);
        } else {
            return Result.fail();
        }
    }

    // 删除发票
    @DeleteMapping("/{id}")
    public Result deleteInvoice(@PathVariable Integer id) {
        boolean deleted = invoicesService.removeById(id);
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
        Page<Invoices> pageObj = new Page<>(page, size);
        // 执行分页查询
        pageObj = invoicesService.page(pageObj);
        // 封装结果
        return Result.suc(pageObj.getRecords(), pageObj.getTotal());
    }
    @GetMapping("/updateStatus/{id}")
    public Result updateStatus(@RequestParam Integer id) {
        Invoices invoices = new Invoices();
        invoices.setInvoiceId(id);
        invoices.setStatus("以报销");
        boolean updated = invoicesService.updateById(invoices);
        if (updated) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
}
