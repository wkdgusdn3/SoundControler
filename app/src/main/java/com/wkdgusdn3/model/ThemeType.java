package com.wkdgusdn3.model;

import lombok.Getter;

@Getter
public enum ThemeType {

    DARK(0),
    WHITE(1);

    private int position;

    ThemeType(int position) {
        this.position = position;
    }

    public static ThemeType getThemeType(int position) {

        for(ThemeType themeType : values()) {
            if(themeType.getPosition() == position) {
                return themeType;
            }
        }

        // TODO exception 처리
        return DARK;
    }
}
