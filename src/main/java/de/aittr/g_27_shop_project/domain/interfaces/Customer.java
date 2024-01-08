package de.aittr.g_27_shop_project.domain.interfaces;

public interface Customer {

  int getId();

  boolean isActive();

  String getName();

  Cart getCart();
}
