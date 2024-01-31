package de.aittr.g_27_shop_project.controllers;

import de.aittr.g_27_shop_project.domain.dto.ProductDto;
import de.aittr.g_27_shop_project.domain.jdbc.CommonProduct;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.exception_handling.Response;
import de.aittr.g_27_shop_project.exception_handling.exceptions.FirstTestException;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  // аннотация Вэлид показывает, что нам требуется валидация
  public ProductDto save(@Valid @RequestBody ProductDto product) {
    return service.save(product);
  }

  @GetMapping
  public List<ProductDto> getAll() {
    return service.getAllActiveProducts();
  }

  @GetMapping("/{id}")
  public ProductDto getById(@PathVariable int id) {
    ProductDto dto = service.getActiveProductsById(id);

    if (dto == null) {
      throw new FirstTestException("Product with this ID is absent in DB.");
    }

    return dto;
  }

  @PutMapping
  public void update(@RequestBody ProductDto product) {
    service.update(product);
  }

  @GetMapping("/restore/{id}")
  public void restoreById(@PathVariable int id) {
    service.restoreById(id);
  }

  // первый способ - написание метода-хэндлера в контроллере
  // минус - когда требуется одинаковая обработка ошбок, такой хэндлер придётся написать в каждом контроллере
  // плюс - когда требуется разная обработка ошибок для разных контроллеров, такой способ позволяет точечно настроить разную логику обработки под каждый контроллер в отдельности

  // аннотация нужна, чтобы метод срабатывал только при
  // выкидывание ошибок в процессе работы именно этого контроллера
  @ExceptionHandler(FirstTestException.class)
  // аннотация, которая полезна, чтоб, например,
  // при ошибке не было ОК после запроса в постмане
  @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
  public Response handleException(FirstTestException e) {
    return new Response(e.getMessage());
  }
}
