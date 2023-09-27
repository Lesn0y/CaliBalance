package com.lesnoy.calibalance.entry;

import com.lesnoy.calibalance.exception.NoValuePresentException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/entries")
public class EntryController {

    private final EntryService service;

    @GetMapping
    public ResponseEntity<Entry> getLastModifiedEntryByUsername(@RequestParam("username") String username) {
        try {
            return ResponseEntity.ok(service.getLastModifiedEntry(username));
        } catch (UserNotFoundException | NoValuePresentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Entry> saveEntry(@RequestBody EntryDTO entryDTO) {
        try {
            return new ResponseEntity<>(service.saveNewEntry(entryDTO), HttpStatus.CREATED);
        } catch (UserNotFoundException | NoValuePresentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
