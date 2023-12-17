package com.lesnoy.calibalance.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lesnoy.calibalance.user.entry.Entry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntryDTO {
    private User user;
    @JsonProperty("last_entry")
    private Entry lastEntry;
}
