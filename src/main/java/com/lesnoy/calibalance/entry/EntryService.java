package com.lesnoy.calibalance.entry;

import com.lesnoy.calibalance.exception.NoValuePresentException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import com.lesnoy.calibalance.product.Product;
import com.lesnoy.calibalance.product.ProductService;
import com.lesnoy.calibalance.user.User;
import com.lesnoy.calibalance.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntryService {

    private final EntryRepository entryRepository;
    private final UserService userService;
    private final ProductService productService;

    public Entry getLastModifiedEntry(String username) throws UserNotFoundException, NoValuePresentException {
        User user = userService.findUserByLogin(username);
        Entry entry = entryRepository.findTopByUserIdOrderByDate(user.getId());
        if (entry == null)
            throw new NoValuePresentException("No one entries for user @" + username + " exists");
        return entry;
    }



}
