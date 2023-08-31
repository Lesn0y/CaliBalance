package com.lesnoy.calibalance.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Activity {

    MINIMUM(1.2f),
    MIDDLE(1.38f),
    EVERYDAY(1.64f),
    MAXIMUM(1.9f);

    private final float multiplier;
}
