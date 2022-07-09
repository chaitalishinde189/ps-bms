package com.ps.thm.enums;

import lombok.Getter;

public enum Language {

    ENGLISH("English"),
    HINDI("Hindi"),
    MARATHI("Marathi");

    @Getter
    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

}
