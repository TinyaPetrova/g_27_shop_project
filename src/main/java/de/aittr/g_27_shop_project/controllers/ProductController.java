package de.aittr.g_27_shop_project.controllers;

import de.aittr.g_27_shop_project.domain.CommonProduct;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  // http://12.34.56.78:8080/product/save - POST
  @PostMapping("/save")
  // ниже в скобках ссылка на интерфейс недопустима, поэтому Product product не подходит. нужен класс
  public Product save(@RequestBody CommonProduct product) {
    return service.save(product);
  }

  @GetMapping
  public List<Product> getAll() {
    return service.getAllActiveProducts();
  }


}
