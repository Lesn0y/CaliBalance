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

    public Entry getLastModifiedEntry(String username) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findUserByUsername(username);
        Entry entry = entryRepository.findTopByUserIdOrderByDateDesc(user.getId());
        if (entry == null)
            throw new NoValuePresentException("No one entries for user @" + username + " exists");
        return entry;
    }

    public Entry saveNewEntry(EntryDTO entryDTO) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findUserByUsername(entryDTO.getUsername());
        Entry lastModifiedEntry = getLastModifiedEntry(user.getUsername());
        Product product = productService.findProductById(entryDTO.getProductId());

        Entry newEntry = new Entry();
        newEntry.setUser(user);
        newEntry.setProduct(product);
        newEntry.setDate(new Date());
        newEntry.setGrams(entryDTO.getGrams());
        newEntry.setCalLeft(lastModifiedEntry.getCalLeft() - (product.getCal() * entryDTO.getGrams() / 100));
        newEntry.setProtLeft(lastModifiedEntry.getProtLeft() - (product.getProt() * entryDTO.getGrams() / 100));
        newEntry.setFatsLeft(lastModifiedEntry.getFatsLeft() - (product.getFats() * entryDTO.getGrams() / 100));
        newEntry.setCarbsLeft(lastModifiedEntry.getCarbsLeft() - (product.getFats() * entryDTO.getGrams() / 100));

        return entryRepository.save(newEntry);
    }
}