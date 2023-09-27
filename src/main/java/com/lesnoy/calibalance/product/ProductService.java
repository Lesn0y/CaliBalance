package com.lesnoy.calibalance.product;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.NoValuePresentException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public Product findProductById(int id) throws NoValuePresentException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NoValuePresentException("Product with id = " + id + " not exists");
        }
        return product.get();
    }

    public List<Product> findProductsByName(String name) throws EmptyCollectionException {
        List<Product> products = productRepository.findAllByName(name);
        if (products.isEmpty()) {
            throw new EmptyCollectionException("No one product with name " + name);
        }
        return products;
    }
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
        User user = userService.findUserByUsername(login);
        ProductType productType = ProductType.values()[typeId];
        List<Product> products = productRepository
                .findAllByIdCreatorAndProductType(user.getId(), productType);
        if (products.isEmpty()) {
            throw new EmptyCollectionException("User '" + user.getUsername() + "' has not saved any product with type '" + productType.name() + "' yet");
        }
        return products;
    }

    public Product saveProduct(ProductDTO productDto) throws UserNotFoundException {
        User userByLogin = userService.findUserByUsername(productDto.getOwnerName());
        Product product = new Product(productDto);
        product.setIdCreator(userByLogin.getId());
        return productRepository.save(product);
    }

}
