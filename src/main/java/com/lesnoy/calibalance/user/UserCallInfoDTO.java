package com.lesnoy.calibalance.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCallInfoDTO {
    private float cal;
    private float prot;
    private float fats;
    private float carbs;
}
