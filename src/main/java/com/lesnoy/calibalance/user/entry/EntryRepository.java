package com.lesnoy.calibalance.user.entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Integer> {

    @Query(value = "SELECT * FROM entries WHERE date >= CURRENT_DATE ORDER BY DATE;",
            nativeQuery = true)
    List<Entry> findAllTodayEntries(int userId);
}
