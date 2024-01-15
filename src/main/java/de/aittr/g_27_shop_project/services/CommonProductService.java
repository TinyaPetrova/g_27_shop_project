package de.aittr.g_27_shop_project.services;

import de.aittr.g_27_shop_project.domain.interfaces.Product;
import de.aittr.g_27_shop_project.repositories.interfaces.ProductRepository;
import de.aittr.g_27_shop_project.services.interfaces.ProductService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class CommonProductService implements ProductService {

  private ProductRepository repository;

  public CommonProductService(ProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Product save(Product product) {

    if (product == null) {
      throw new IllegalArgumentException("Product field cannot be empty!");
    }

    if (product.getName() == null || product.getName().isEmpty()) {
      throw new IllegalArgumentException("Name of the product should be filled in!");
    }

    if (product.getPrice() <= 0) {
      throw new IllegalArgumentException("Price of the product cannot be negative or equal 0!");
    }

    return repository.save(product);
  }

  @Override
  public List<Product> getAllActiveProducts() {
    List<Product> products = repository.getAll();

    if (products.isEmpty()) {
      throw new NoSuchElementException("Database doesn't contain any products");
    }
    return products;
  }

  @Override
  public Product getActiveProductsById(int id) {
    return null;
  }

  @Override
  public void update(Product product) {

  }

  @Override
  public void deleteById(int id) {

  }

  @Override
  public void deleteByName(String name) {

  }

  @Override
  public void restoreById(int id) {

  }

  @Override
  public int getActiveProductsCount() {
    return 0;
  }

  @Override
  public double getActiveProductsTotalPrice() {
    return 0;
  }

  @Override
  public double getActiveProductsAveragePrice() {
    return 0;
  }
}
