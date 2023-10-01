package com.lesnoy.calibalance.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {

    @Query(value = "SELECT * FROM entries WHERE date >= CURRENT_DATE ORDER BY DATE DESC LIMIT 1;",
           nativeQuery = true)
    Entry findTodayLastEntry(int userId);

}
