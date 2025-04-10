package com.wms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Result;
import com.wms.entity.Customer;

import com.wms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    private Customer customer;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Customer customer){
        return customerService.save(customer)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Customer customer){
        return customerService.updateById(customer)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return customerService.removeById(id)?Result.suc():Result.fail();
    }
    // 创建客户
    @PostMapping
    public Result createCustomer(@RequestBody Customer customer) {
        boolean saved = customerService.save(customer);
        if (saved) {
            return Result.suc(customer);
        } else {
            return Result.fail();
        }
    }

    // 根据ID获取客户
    @GetMapping("/{id}")
    public Result getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getById(id);
        if (customer != null) {
            return Result.suc(customer);
        } else {
            return Result.fail();
        }
    }

    // 获取所有客户
    @GetMapping
    public Result getAllCustomers() {
        List<Customer> customers = customerService.list();
        return Result.suc(customers, (long) customers.size());
    }

    // 更新客户
    @PutMapping("/{id}")
    public Result updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        boolean updated = customerService.updateById(customer);
        if (updated) {
            return Result.suc(customer);
        } else {
            return Result.fail();
        }
    }

    // 删除客户
    @DeleteMapping("/{id}")
    public Result deleteCustomer(@PathVariable Integer id) {
        boolean deleted = customerService.removeById(id);
        if (deleted) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
    // 分页查询客户
    @GetMapping("/page")
    public Result getCustomersByPage(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size) {
        // 创建分页对象
        Page<Customer> pageObj = new Page<>(page, size);
        // 执行分页查询
        pageObj = customerService.page(pageObj);
        // 封装结果
        return Result.suc(pageObj.getRecords(), pageObj.getTotal());
    }
}