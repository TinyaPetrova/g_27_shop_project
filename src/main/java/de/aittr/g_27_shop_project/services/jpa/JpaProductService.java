package de.aittr.g_27_shop_project.services.jpa;

import de.aittr.g_27_shop_project.domain.dto.ProductDto;
import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;
import de.aittr.g_27_shop_project.repositories.jpa.JpaProductRepository;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import de.aittr.g_27_shop_project.services.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class JpaProductService implements ProductService {

  private JpaProductRepository repository;
  private ProductMappingService mappingService;

  public JpaProductService(JpaProductRepository repository, ProductMappingService mappingService) {
    this.repository = repository;
    this.mappingService = mappingService;
  }

  @Override
  public ProductDto save(ProductDto product) {
    JpaProduct entity = mappingService.mapDtoToEntity(product);
    entity.setId(0);
    entity = repository.save(entity);
    return mappingService.mapEntityToDto(entity);
  }

  @Override
  public List<ProductDto> getAllActiveProducts() {
    return repository.findAll()
        .stream()
        .filter(x -> x.isActive())
        .map(x -> mappingService.mapEntityToDto(x))
        .toList();
  }

  @Override
  public ProductDto getActiveProductsById(int id) {
    JpaProduct product = repository.findById(id).orElse(null);

    if (product != null && product.isActive()) {
      return mappingService.mapEntityToDto(product);
    }

    return null;
  }

  @Override
  public void update(ProductDto product) {
    JpaProduct entity = mappingService.mapDtoToEntity(product);
    repository.save(entity);
  }

  // дз: метод удаления по айди
  @Override
  public void deleteById(int id) {
    repository.deleteById(id);
  }

  // дз: метод удаления по имени, обозначила его в репозитории
  @Override
  public void deleteByName(String name) {
    repository.deleteByName(name);
  }

  @Override
  @Transactional
  public void restoreById(int id) {
    JpaProduct product = repository.findById(id).orElse(null);

    if (product != null) {
      product.setActive(true);
    }
    // тут транзакция прерывается, поэтому нужна аннотация
  }

  @Override
  public int getActiveProductsCount() {
    List<JpaProduct> activeProducts = repository.findByIsActiveTrue();
    return activeProducts.size();
  }

  // дз: найти стоимость всеч активных продуктов, метод обозначен в репозитории
  @Transactional
  @Override
  public double getActiveProductsTotalPrice() {
    List<ProductDto> activeProductDtos = repository.findByIsActiveTrue().stream()
        .filter(x -> x.isActive())
        .map(mappingService::mapEntityToDto)
        .collect(Collectors.toList());

    double totalPrice = activeProductDtos.stream()
        .mapToDouble(x -> x.getPrice())
        .sum();

    return totalPrice;
  }

  // дз: найти среднюю стоимость всеч активных продуктов, метод обозначен в репозитории
  @Transactional
  @Override
  public double getActiveProductsAveragePrice() {
    List<ProductDto> activeProductDtos = repository.findByIsActiveTrue().stream()
        .filter(x -> x.isActive())
        .map(mappingService::mapEntityToDto)
        .collect(Collectors.toList());

    double totalPrices = activeProductDtos.stream()
        .mapToDouble(x -> x.getPrice())
        .sum();
    return totalPrices / activeProductDtos.size();
  }
}
