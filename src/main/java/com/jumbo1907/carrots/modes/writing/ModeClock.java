package com.jumbo1907.carrots.modes.writing;

import com.jumbo1907.carrots.modes.WritingMode;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModeClock extends WritingMode {

    private final String format;
    public ModeClock(String format) {
        super("Clock", 1000L);
        this.format = format;
    }

    @Override
    public String fetch() {
        return new SimpleDateFormat(format).format(new Date());
    }
}
