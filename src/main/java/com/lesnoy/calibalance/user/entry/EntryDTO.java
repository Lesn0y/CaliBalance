package com.lesnoy.calibalance.user.entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntryDTO {
    private String username;
    @JsonProperty("product_id")
    private int productId;
    private int grams;
}
