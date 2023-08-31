package com.lesnoy.calibalance.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Goal {

    PUMP_UP(0.15f, 0.3f / 4, 0.15f / 4, 0.55f / 9),
    KEEP_FIT(0, 0.33f / 4, 0.25f / 4, 0.42f / 9),
    SLIM(-0.15f, 0.45f / 4, 0.2f / 4, 0.35f / 9);

    private float calMultiplier;
    private float protMultiplier;
    private float fatMultiplier;
    private float carbsMultiplier;
}
