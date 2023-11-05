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

    public Product findById(int id) throws NoValuePresentException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NoValuePresentException("Product with id = " + id + " not exists");
        }
        return product.get();
    }

    public List<Product> findProductsByOwnerAndName(String owner, String name) throws EmptyCollectionException, UserNotFoundException {
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

    public List<Product> findAdminProductsByName(String name) throws EmptyCollectionException, UserNotFoundException {
        return findProductsByOwnerAndName("ADMIN", name);
    }

    public List<Product> findProductsByOwnerAndType(String owner, int typeOrdinal) throws UserNotFoundException, EmptyCollectionException {
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

    public List<Product> findAdminProductsByType(int typeOrdinal) throws EmptyCollectionException, UserNotFoundException {
        return findProductsByOwnerAndType("ADMIN", typeOrdinal);
    }

    public Product saveProductToUser(Product product, String owner) throws UserNotFoundException {
        User user = userService.findByUsername(owner);
        user.getProducts().add(product);
        return productRepository.save(product);
    }

    public void deleteProductFromUser(int productId, String owner) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findByUsername(owner);
        Optional<Product> product = user.getProducts().stream().filter(pr -> pr.getId() == productId).findFirst();

        if (product.isEmpty()) {
            throw new NoValuePresentException("Product with id = " + productId + " is not saved with user");
        }
        user.getProducts().remove(product.get());
        userService.save(user);
    }
}
