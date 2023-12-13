package com.lesnoy.calibalance.user.entry;

import com.lesnoy.calibalance.exception.EmptyCollectionException;
import com.lesnoy.calibalance.exception.NoValuePresentException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserInfoDTO;
import com.lesnoy.calibalance.user.UserService;
import com.lesnoy.calibalance.user.product.Product;
import com.lesnoy.calibalance.user.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final ProductService productService;
    private final UserService userService;
    private final EntryRepository entryRepository;

    public List<Entry> findAllTodayEntries(String username) throws UserNotFoundException, EmptyCollectionException {
        User user = userService.findByUsername(username);
        List<Entry> todayEntries = entryRepository.findAllTodayEntries(user.getId());
        if (todayEntries.isEmpty()) {
            throw new EmptyCollectionException("User " + username + " has not saved anyone entries today");
        }
        return todayEntries;
    }

    public UserInfoDTO findActualUserInfo(String username) throws UserNotFoundException, EmptyCollectionException {
        User user = userService.findByUsername(username);
        List<Entry> todayEntries = findAllTodayEntries(username);
        return new UserInfoDTO(user, todayEntries.get(todayEntries.size() - 1));
    }

    public Entry saveNewEntry(EntryDTO entryDTO, String username) throws UserNotFoundException, NoValuePresentException, EmptyCollectionException {
        User user = userService.findByUsername(username);
        Product product = productService.findById(entryDTO.getProductId());

        Entry newEntry = new Entry();
        newEntry.setUser(user);
        newEntry.setProduct(product);
        newEntry.setDate(new Date());
        newEntry.setGrams(entryDTO.getGrams());

        Entry lastModifiedEntry = findActualUserInfo(user.getUsername()).getLastEntry();
        if (lastModifiedEntry == null) {
            newEntry.setCalLeft(user.getCal() - (product.getCal() * entryDTO.getGrams() / 100));
            newEntry.setProtLeft(user.getProt() - (product.getProt() * entryDTO.getGrams() / 100));
            newEntry.setFatsLeft(user.getFats() - (product.getFats() * entryDTO.getGrams() / 100));
            newEntry.setCarbsLeft(user.getCarbs() - (product.getFats() * entryDTO.getGrams() / 100));
        } else {
            newEntry.setCalLeft(lastModifiedEntry.getCalLeft() - (product.getCal() * entryDTO.getGrams() / 100));
            newEntry.setProtLeft(lastModifiedEntry.getProtLeft() - (product.getProt() * entryDTO.getGrams() / 100));
            newEntry.setFatsLeft(lastModifiedEntry.getFatsLeft() - (product.getFats() * entryDTO.getGrams() / 100));
            newEntry.setCarbsLeft(lastModifiedEntry.getCarbsLeft() - (product.getCarbs() * entryDTO.getGrams() / 100));
        }
        return entryRepository.save(newEntry);
    }
}