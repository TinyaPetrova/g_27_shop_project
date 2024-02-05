package de.aittr.g_27_shop_project.services.jpa;

import de.aittr.g_27_shop_project.domain.dto.ProductDto;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;
import de.aittr.g_27_shop_project.domain.jpa.Task;
import de.aittr.g_27_shop_project.exception_handling.exceptions.FourthTestException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductCalculationException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductDeletingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductGettingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductNotFoundException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductSavingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ProductUpdatingException;
import de.aittr.g_27_shop_project.exception_handling.exceptions.SecondTestExceptions;
import de.aittr.g_27_shop_project.exception_handling.exceptions.ThirdTestException;
import de.aittr.g_27_shop_project.repositories.jpa.JpaProductRepository;
import de.aittr.g_27_shop_project.scheduling.ScheduleExecutor;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import de.aittr.g_27_shop_project.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JpaProductService implements ProductService {

  private JpaProductRepository repository;
  private ProductMappingService mappingService;
  // private Logger logger = LogManager.getLogger(JpaProductService.class);
  private Logger logger = LoggerFactory.getLogger(JpaProductService.class);

  public JpaProductService(JpaProductRepository repository, ProductMappingService mappingService) {
    this.repository = repository;
    this.mappingService = mappingService;
  }

  // дз: обработчик исключений ProductSavingException
  @Override
  public ProductDto save(ProductDto product) {
    try {
      JpaProduct entity = mappingService.mapDtoToEntity(product);
      entity.setId(0);
      entity = repository.save(entity);
      return mappingService.mapEntityToDto(entity);
    } catch (Exception e) {
      throw new ProductSavingException("Failed to save the product" + e.getMessage());
    }
  }

  // дз: обработчик исключений ProductGettingException
  @Override
  public List<ProductDto> getAllActiveProducts() {
    // запланировали выполнение задачи, см ScheduleExecutor
    Task task = new Task("Method getAllActiveProducts called");
    ScheduleExecutor.scheduleTaskExecution(task);

    System.out.println("Method getAllActiveProducts is launched");
    try {
      return repository.findAll()
          .stream()
          .filter(x -> x.isActive())
          .map(x -> mappingService.mapEntityToDto(x))
          .toList();
    } catch (Exception e) {
      throw new ProductGettingException("Failed to get the active products" + e.getMessage());
    }
  }

  // дз: обработчик исключений ProductNotFoundException + ProductNotFoundException
  @Override
  public ProductDto getActiveProductsById(int id) {
//    logger.log(Level.INFO, String.format("Required product with ID %d", id));
//    logger.log(Level.WARN, String.format("Required product with ID %d", id));
//    logger.log(Level.ERROR, String.format("Required product with ID %d", id));
//
//    logger.info("Required product with ID %d", id);
//    logger.warn("Required product with ID %d", id);
//    logger.error("Required product with ID %d", id);

    try {
      JpaProduct product = repository.findById(id).orElse(null);

      if (product != null && product.isActive()) {
        return mappingService.mapEntityToDto(product);
      } else {
        throw new ProductNotFoundException("Product with ID " + id + " is not active or doesn't exist");
      }
    } catch (Exception e) {
      throw new ProductNotFoundException("Failed to find product with ID " + id + ": " + e.getMessage());
    }
  }

  // дз: обработчк исключений ProductUpdatingException
  @Override
  public void update(ProductDto product) {
    try {
      JpaProduct entity = mappingService.mapDtoToEntity(product);
      repository.save(entity);
    } catch (Exception e) {
      throw new ProductUpdatingException("Failed to update product: " + e.getMessage());
    }
  }

  // дз: обработчик исключений ProductDeletingException
  // старое дз: метод удаления по айди
  @Override
  public void deleteById(int id) {
    try {
      repository.deleteById(id);
    } catch (Exception e) {
      throw new ProductDeletingException("Failed to delete product with ID " + id + ": " + e.getMessage());
    }
  }

  // дз: обработчик исключений ProductDeletingException
  // старое дз: метод удаления по имени, обозначила его в репозитории
  @Override
  public void deleteByName(String name) {
    try {
      repository.deleteByName(name);
    } catch (Exception e) {
      throw new ProductDeletingException("Failed to delete product with name " + name + ": " + e.getMessage());
    }
  }

  @Override
  @Transactional
  public void restoreById(int id) {
    JpaProduct product = repository.findById(id).orElse(null);

    if (product != null) {
      product.setActive(true);
    }
    // тут транзакция прерывается, поэтому нужна аннотация
    System.out.println("Method restore");
  }

  // дз: обработчик исключений ProductCalculationException
  @Override
  public int getActiveProductsCount() {
    try {
      List<JpaProduct> activeProducts = repository.findByIsActiveTrue();
      return activeProducts.size();
    } catch (Exception e) {
      throw new ProductCalculationException("Failed to calculate the count of active products: " + e.getMessage());
    }
  }

  // дз: обработчик исключений ProductCalculationException
  // старое дз: найти стоимость всеч активных продуктов, метод обозначен в репозитории
  @Transactional
  @Override
  public double getActiveProductsTotalPrice() {
    try {
      List<JpaProduct> activeProducts = repository.findByIsActiveTrue();

      double totalPrice = activeProducts.stream()
          .mapToDouble(JpaProduct::getPrice)
          .sum();

      return totalPrice;
    } catch (Exception e) {
      throw new ProductCalculationException(
          "Failed to calculate total price of active products: " + e.getMessage());
    }
  }

  // дз: обработчик исключений ProductCalculationException
  // старое дз: найти среднюю стоимость всеч активных продуктов, метод обозначен в репозитории
  @Transactional
  @Override
  public double getActiveProductsAveragePrice() {
    try {
      List<JpaProduct> activeProducts = repository.findByIsActiveTrue();

      double totalPrices = activeProducts.stream()
          .mapToDouble(JpaProduct::getPrice)
          .sum();

      return totalPrices / activeProducts.size();
    } catch (Exception e) {
      throw new ProductCalculationException(
          "Failed to calculate average price of active products: " + e.getMessage());
    }
  }
}
