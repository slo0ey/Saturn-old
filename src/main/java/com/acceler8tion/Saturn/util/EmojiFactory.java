package com.acceler8tion.Saturn.util;

public enum EmojiFactory {

    SAD_BUT_RELIEVED_FACE(0x1F625),
    ANGRY_FACE(0x1F620),
    ANGRY_FACE_WITH_HORNS(0x1F47F),
    GRINNING_FACE(0x1F600),
    WHITE_CHECK_MARK(0x2705),
    BLUE_SQUARE(0x1F7E6),
    WHITE_LARGE_SQUARE(0x2B1C),
    BOMB(0x1F4A3),
    COLLISION(0x1F4A5),
    TRIANGULAR_FLAG_ON_POST(0x1F6A9),
    CONFUSED_FACE(0x1F615);

    private final int emoji;

    EmojiFactory(int emoji){
        this.emoji = emoji;
    }

    public String getEmoji(){
        return new StringBuilder().appendCodePoint(emoji).toString();
    }
}
