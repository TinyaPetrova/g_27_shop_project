package de.aittr.g_27_shop_project.controllers;

import de.aittr.g_27_shop_project.domain.CommonCustomer;
import de.aittr.g_27_shop_project.domain.CommonProduct;
import de.aittr.g_27_shop_project.domain.interfaces.Customer;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.services.interfaces.CustomerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private CustomerService service;

  public CustomerController(CustomerService service) {
    this.service = service;
  }

  @PostMapping
  public Customer save(@RequestBody CommonCustomer customer) {
    return service.save(customer);
  }

  @GetMapping
  public List<Customer> getAll() {
    return service.getAllActiveCustomers();
  }
}
