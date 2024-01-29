package de.aittr.g_27_shop_project.repositories.jpa;

import de.aittr.g_27_shop_project.domain.jpa.JpaProduct;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProductRepository extends JpaRepository<JpaProduct, Integer> {

  @Modifying
  @Query(value = "DELETE FROM 27_shop.product WHERE name = :productName", nativeQuery = true)
  void deleteByName(@Param("productName") String name);

  @Transactional
  List<JpaProduct> findByIsActiveTrue();
}
