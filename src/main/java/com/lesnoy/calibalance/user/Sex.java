package com.lesnoy.calibalance.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sex {

    MAN(5),
    WOMAN(- 161);

    private final int arg;
}
