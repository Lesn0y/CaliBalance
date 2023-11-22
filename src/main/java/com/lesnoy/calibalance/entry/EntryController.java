package com.lesnoy.calibalance.entry;

import com.lesnoy.calibalance.exception.NoValuePresentException;
import com.lesnoy.calibalance.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/entries")
public class EntryController {

    private final EntryService entryService;

    @GetMapping
    public ResponseEntity<Entry> getLastModifiedEntryByUsername(@RequestParam("username") String username) {
        log.info("Request GET \"/api/v1/entries\" with 'username='"+  username + "' ACCEPTED");
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Entry lastEntry = entryService.getLastModifiedEntry(username);
            if (lastEntry == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(lastEntry);
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Entry> saveEntry(@RequestBody EntryDTO entryDTO) {
        log.info("Request by POST \"/api/v1/entries\" with 'entryDTO='"+  entryDTO.getUsername() + "' ACCEPTED");
        try {
            return new ResponseEntity<>(entryService.saveNewEntry(entryDTO), HttpStatus.CREATED);
        } catch (UserNotFoundException | NoValuePresentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}

