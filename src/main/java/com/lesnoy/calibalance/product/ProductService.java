package com.lesnoy.calibalance.product;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public List<Product> findAllProductsByType(Integer typeId) throws EmptyCollectionException {
        ProductType productType = ProductType.values()[typeId];
        List<Product> products = productRepository
                .findAllByIdCreatorAndProductType(0, productType);
        if (products.isEmpty()) {
            throw new EmptyCollectionException("User has not saved any product with type '" + productType.name() + "' yet");
        }
        return products;
    }

    public List<Product> findAllProductsByOwnerAndType(String login, int typeId) throws UserNotFoundException, EmptyCollectionException {
        User user = userService.findUserByLogin(login);
        ProductType productType = ProductType.values()[typeId];
        List<Product> products = productRepository
                .findAllByIdCreatorAndProductType(user.getId(), productType);
        if (products.isEmpty()) {
            throw new EmptyCollectionException("User '" + user.getLogin() + "' has not saved any product with type '" + productType.name() + "' yet");
        }
        return products;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

}
