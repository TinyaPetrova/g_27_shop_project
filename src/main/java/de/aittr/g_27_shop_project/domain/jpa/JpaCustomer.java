package de.aittr.g_27_shop_project.domain.jpa;

import de.aittr.g_27_shop_project.domain.interfaces.Cart;
import de.aittr.g_27_shop_project.domain.interfaces.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Table(name = "customer")
public class JpaCustomer implements Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name")
  @Pattern(regexp = "^[А-ЯЁA-Z][а-яёa-z]{1,29}$", message = "Name should start with the capital letter and contain 2-30 symbols")
  // также можно вводить имя на латинице или кириллице по желанию
  private String name;

  @Column(name = "email")
  @Email(message = "Incorrect format of email")
  private String email;

  @Column(name = "age")
  @Min(value = 18, message = "Age should be at least 18 y.o.")
  @Max(value = 120, message = "Max age should be no bigger than 120 y.o.")
  private int age;

  @Column(name = "is_active")
  private boolean isActive;

  @OneToOne(mappedBy = "customer")
  private JpaCart cart;

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isActive() {
    return isActive;
  }

  @Override
  public Cart getCart() {
    return cart;
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
  public void setActive(boolean active) {
    isActive = active;
  }

  @Override
  public void setCart(Cart cart) {
    JpaCart entity = new JpaCart();
    entity.setId(cart.getId());
    entity.setProducts(cart.getProducts());
    this.cart = entity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JpaCustomer that = (JpaCustomer) o;
    return id == that.id && isActive == that.isActive && Objects.equals(name, that.name)
        && Objects.equals(cart, that.cart);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, isActive, cart);
  }

  @Override
  public String toString() {
    return "JpaCustomer{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", isActive=" + isActive +
        ", cart=" + cart +
        '}';
  }
}
