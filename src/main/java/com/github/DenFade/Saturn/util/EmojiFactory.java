package com.github.DenFade.Saturn.util;

public enum EmojiFactory {

    SAD_BUT_RELIEVED_FACE(0x1f625),
    GRINNING_FACE(0x1f600),
    WHITE_CHECK_MARK(0x2705),
    CONFUSED_FACE(0x1f615);

    private final int emoji;

    EmojiFactory(int emoji){
        this.emoji = emoji;
    }

    public String getEmoji(){
        return new StringBuilder().appendCodePoint(emoji).toString();
    }
}
