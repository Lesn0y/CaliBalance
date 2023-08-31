package com.lesnoy.calibalance.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Goal {

    PUMP_UP(0.15f, 0.3f / 4, 0.15f / 9, 0.55f / 4),
    KEEP_FIT(0, 0.33f / 4, 0.25f / 9, 0.42f / 4),
    SLIM(-0.15f, 0.4f / 4, 0.25f / 9, 0.35f / 4);

    private final float calMultiplier;
    private final float protMultiplier;
    private final float fatMultiplier;
    private final float carbsMultiplier;
}
