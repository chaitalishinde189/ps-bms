package com.ps.thm.enums;

import lombok.Getter;

public enum Format {
    TWO_D("2D"),
    THREE_D("3D"),
    IMAX_2D("IMAX 2D"),
    IMAX_3D("IMAX 3D");

    @Getter
    private final String displayName;

    Format(String displayName) {
        this.displayName = displayName;
    }

}
