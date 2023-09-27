package com.lesnoy.calibalance.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByIdCreatorAndProductType(int idCreator, ProductType productType);

    List<Product> findAllByName(String name);
}
