package de.aittr.g_27_shop_project.controllers;

import de.aittr.g_27_shop_project.domain.dto.ProductDto;
import de.aittr.g_27_shop_project.domain.jdbc.CommonProduct;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  public ProductDto save(@RequestBody ProductDto product) {
    return service.save(product);
  }

  @GetMapping
  public List<ProductDto> getAll() {
    return service.getAllActiveProducts();
  }

  @GetMapping("/{id}")
  public ProductDto getById(@PathVariable int id) {
    return service.getActiveProductsById(id);
  }

  @PutMapping
  public void update(@RequestBody ProductDto product) {
    service.update(product);
  }

  @GetMapping("/restore/{id}")
  public void restoreById(@PathVariable int id) {
    service.restoreById(id);
  }
}
