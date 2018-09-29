package com.wkdgusdn3.model;

import lombok.Getter;

@Getter
public enum ThemeType {

    DARK(0, "dark"),
    WHITE(1, "white");

    private int position;
    private String resourceAdditionName;

    ThemeType(int position, String resourceAdditionName) {
        this.position = position;
        this.resourceAdditionName = resourceAdditionName;
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
