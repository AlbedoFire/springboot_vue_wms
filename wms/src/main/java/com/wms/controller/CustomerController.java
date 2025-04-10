package com.wms.controller;


import com.wms.common.Result;
import com.wms.entity.Customer;
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
    public Customer createCustomer(@RequestBody Customer customer) {
        this.customer = customer;
        customerService.save(customer);
        return customer;
    }

    // 根据ID获取客户
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Integer id) {
        return customerService.getById(id);
    }

    // 获取所有客户
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.list();
    }

    // 更新客户
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        customerService.updateById(customer);
        return customer;
    }

    // 删除客户
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.removeById(id);
    }
}