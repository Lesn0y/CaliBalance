package com.lesnoy.calibalance.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Goal {

    PUMP_UP(0.15f, 2, 1),
    KEEP_FIT(0, 1.5f, 0.9f),
    SLIM(-0.15f, 2, 0.7f);

    private final float calMultiplier;
    private final float protMultiplier;
    private final float fatMultiplier;
}
