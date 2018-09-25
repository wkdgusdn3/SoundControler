package com.wkdgusdn3.model;

import java.util.Objects;

import lombok.Getter;

@Getter
public enum SoundFunctionType {

    DISABLE(0, null),
    MUSIC_PLAY(1, null),
    VOLUME_UP(2, null),
    VOLUME_DOWN(3, null),
    VOLUME_0(4, 0),
    VOLUME_1(5, 1),
    VOLUME_2(6, 2),
    VOLUME_3(7, 3),
    VOLUME_4(8, 4),
    VOLUME_5(9, 5),
    VOLUME_6(10, 6),
    VOLUME_7(11, 7),
    VOLUME_8(12, 8),
    VOLUME_9(13, 9),
    VOLUME_10(14, 10),
    VOLUME_11(15, 11),
    VOLUME_12(16, 12),
    VOLUME_13(17, 13),
    VOLUME_14(18, 14),
    VOLUME_15(19, 15);

    private int position;
    private Integer volumeAmount;

    SoundFunctionType(
        int position,
        Integer volumeAmount) {

        this.position = position;
        this.volumeAmount = volumeAmount;
    }

    public static SoundFunctionType getSoundFunction(int position) {

        for(SoundFunctionType soundFunctionType : values()) {
            if(soundFunctionType.getPosition() == position) {
                return soundFunctionType;
            }
        }

        // TODO exception 처리
        return DISABLE;
    }
}
