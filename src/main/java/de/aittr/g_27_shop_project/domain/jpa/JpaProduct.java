package de.aittr.g_27_shop_project.domain.jpa;

import de.aittr.g_27_shop_project.domain.interfaces.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "product")
public class JpaProduct implements Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  // хотим имя: только латиница, без символов и цифр, мин 4 знака, 1 заглавная
  // Test - ok
  // TEST - X
  // Tes - X
  // test - X
  // TEst - x
  // Test@ - X
  @Pattern(regexp = "[A-Z][a-z]{3,}") // регьюлар экспрешн, регулярное выражение
  // наличие паттерна делает след 2 аннотации избыточными
  @NotNull // аннотация после Вэлид на контроллере
  @NotBlank // тоже после Вэлид, чтобы строка не была пустой (просто "")
  private String name;

  @Column(name = "price")
  @Max(90000) // тоже валидация
  @Min(10) // тоже
  private double price;

  @Column(name = "is_active")
  private boolean isActive;

  public JpaProduct(int id, String name, double price, boolean isActive) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.isActive = isActive;
  }

  public JpaProduct() {
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public double getPrice() {
    return price;
  }

  @Override
  public boolean isActive() {
    return isActive;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public void setActive(boolean active) {
    isActive = active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JpaProduct that = (JpaProduct) o;
    return id == that.id && Double.compare(that.price, price) == 0
        && isActive == that.isActive && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, isActive);
  }

  @Override
  public String toString() {
    return "JpaProduct{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", isActive=" + isActive +
        '}';
  }
}
