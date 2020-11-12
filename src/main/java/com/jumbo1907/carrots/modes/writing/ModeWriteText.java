package com.jumbo1907.carrots.modes.writing;

import com.jumbo1907.carrots.modes.WritingMode;

public class ModeWriteText extends WritingMode {


    private final String word;

    public ModeWriteText(String word) {
        super("Writer", 1000);
        this.word = word;
    }

    @Override
    public String fetch() {
        return word;
    }
}
