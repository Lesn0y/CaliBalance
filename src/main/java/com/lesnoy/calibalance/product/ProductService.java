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
    public List<Product> findAllProductsByType(Integer typeId) throws EmptyCollectionException, UserNotFoundException {
        return findAllProductsByOwnerAndType("ADMIN", typeId);
    }

    public List<Product> findAllProductsByOwnerAndType(String login, int typeId) throws UserNotFoundException, EmptyCollectionException {
        ProductType productType = ProductType.values()[typeId];
        User user = userService.findUserByUsername(login);
        List<Product> products = user.getProducts()
                .stream()
                .filter(product -> product.getProductType().ordinal() == typeId)
                .toList();

        if (products.isEmpty()) {
            throw new EmptyCollectionException("User has not saved any product with type '" + productType.name() + "' yet");
        }
        return products;
    }

    public Product saveProductToUser(Product product, String owner) throws UserNotFoundException {
        User user = userService.findUserByUsername(owner);
        user.getProducts().add(product);
        return productRepository.save(product);
    }

    public void deleteProductFromUserMenu(int productId, String owner) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findUserByUsername(owner);
        Optional<Product> product = user.getProducts().stream().filter(pr -> pr.getId() == productId).findFirst();
        if (product.isEmpty()) {
            throw new NoValuePresentException("Product with id = " + productId + " not exists");
        }
        user.getProducts().remove(product.get());
        userService.saveUser(user);
    }

}
