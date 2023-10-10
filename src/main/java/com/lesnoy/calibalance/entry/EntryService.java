package com.lesnoy.calibalance.entry;

import com.lesnoy.calibalance.exception.NoValuePresentException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.product.Product;
import com.lesnoy.calibalance.product.ProductService;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final UserService userService;
    private final ProductService productService;

    public Entry getLastModifiedEntry(String username) throws UserNotFoundException {
        User user = userService.findByUsername(username);
        return entryRepository.findTodayLastEntry(user.getId());
    }

    public Entry saveNewEntry(EntryDTO entryDTO) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findByUsername(entryDTO.getUsername());
        Product product = productService.findProductById(entryDTO.getProductId());

        Entry newEntry = new Entry();
        newEntry.setUser(user);
        newEntry.setProduct(product);
        newEntry.setDate(new Date());
        newEntry.setGrams(entryDTO.getGrams());

        Entry lastModifiedEntry = getLastModifiedEntry(user.getUsername());
        if (lastModifiedEntry == null) {
            newEntry.setCalLeft(user.getCal() - (product.getCal() * entryDTO.getGrams() / 100));
            newEntry.setProtLeft(user.getProt() - (product.getProt() * entryDTO.getGrams() / 100));
            newEntry.setFatsLeft(user.getFats() - (product.getFats() * entryDTO.getGrams() / 100));
            newEntry.setCarbsLeft(user.getCarbs() - (product.getFats() * entryDTO.getGrams() / 100));
        } else {
            newEntry.setCalLeft(lastModifiedEntry.getCalLeft() - (product.getCal() * entryDTO.getGrams() / 100));
            newEntry.setProtLeft(lastModifiedEntry.getProtLeft() - (product.getProt() * entryDTO.getGrams() / 100));
            newEntry.setFatsLeft(lastModifiedEntry.getFatsLeft() - (product.getFats() * entryDTO.getGrams() / 100));
            newEntry.setCarbsLeft(lastModifiedEntry.getCarbsLeft() - (product.getFats() * entryDTO.getGrams() / 100));
        }

        return entryRepository.save(newEntry);
    }
}