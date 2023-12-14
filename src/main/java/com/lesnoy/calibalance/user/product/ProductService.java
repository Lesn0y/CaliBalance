package com.lesnoy.calibalance.user.product;

import com.lesnoy.calibalance.exception.BadParametersException;
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

    private final UserService userService;
    private final ProductRepository productRepository;

    public Product findById(int id) throws NoValuePresentException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NoValuePresentException("Product with id = " + id + " not exists");
        }
        return product.get();
    }

    public List<Product> findProductsByUsername(String username) throws UserNotFoundException, EmptyCollectionException {
        User user = userService.findByUsername(username);
        if (user.getProducts().isEmpty()) {
            throw new EmptyCollectionException("User " + username + " has not saved anyone products");
        }
        return user.getProducts();
    }

    public List<Product> findProductsByUsernameAndName(String owner, String name) throws EmptyCollectionException, UserNotFoundException {
        User user = userService.findByUsername(owner);
        List<Product> products = user.getProducts()
                .stream()
                .filter(product -> product.getName().equals(name))
                .toList();
        if (products.isEmpty()) {
            throw new EmptyCollectionException("No one product with name - " + name);
        }
        return products;
    }

    public List<Product> findProductsByUsernameAndType(String owner, int typeOrdinal) throws UserNotFoundException, EmptyCollectionException, BadParametersException {
        if (typeOrdinal > ProductType.values().length - 1 || typeOrdinal < 0) {
            throw new BadParametersException("There is no product type with id=" + typeOrdinal);
        }
        User user = userService.findByUsername(owner);
        List<Product> products = user.getProducts()
                .stream()
                .filter(product -> product.getProductType().ordinal() == typeOrdinal)
                .toList();

        if (products.isEmpty()) {
            throw new EmptyCollectionException("User has not saved any product with type '" + ProductType.values()[typeOrdinal] + "' yet");
        }
        return products;
    }

    public Product saveProductToUser(Product product, String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        user.getProducts().add(product);
        return productRepository.save(product);
    }

    public void deleteProductFromUser(int productId, String username) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findByUsername(username);
        Product product = findById(productId);
        user.getProducts().remove(product);
        productRepository.delete(product);
        userService.save(user);
    }
}
