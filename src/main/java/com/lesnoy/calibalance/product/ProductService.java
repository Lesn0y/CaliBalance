package com.lesnoy.calibalance.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(int id) throws Exception {
        return repository.findById(id)
                .orElseThrow(Exception::new);
    }
    public List<Product> findByName(String name) {
        return repository.findByName(name);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProductById(int id) throws Exception {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent())
            repository.delete(product.get());
        else
            throw new Exception();
    }

}
