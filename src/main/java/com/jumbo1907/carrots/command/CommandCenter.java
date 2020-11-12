package com.jumbo1907.carrots.command;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import com.jumbo1907.carrots.Carrots;

import java.util.List;
import java.util.stream.Stream;

public class CommandCenter extends Command {

    private final Carrots carrots;
    public CommandCenter(IBaritone iBaritone, Carrots carrots) {
        super(iBaritone, "center");
        this.carrots = carrots;
    }

    @Override
    public void execute(String s, IArgConsumer args) {
        final BetterBlockPos origin = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().playerFeet();
        carrots.setCenter(origin);
        Helper.HELPER.logDirect(String.format("Center set (%s)", origin.toString()));
    }

    @Override
    public Stream<String> tabComplete(String s, IArgConsumer iArgConsumer) {
        return null;
    }

    @Override
    public String getShortDesc() {
        return null;
    }

    @Override
    public List<String> getLongDesc() {
        return null;
    }
}
