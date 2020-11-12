package com.jumbo1907.carrots.command;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.helpers.TabCompleteHelper;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.Helper;
import com.jumbo1907.carrots.Carrots;
import com.jumbo1907.carrots.modes.combat.ModeAttackPlayer;
import com.jumbo1907.carrots.modes.combat.ModeGuard;
import com.jumbo1907.carrots.modes.combat.ModeProtect;
import com.jumbo1907.carrots.modes.writing.ModeBTCPrice;
import com.jumbo1907.carrots.modes.writing.ModeClock;
import com.jumbo1907.carrots.modes.writing.ModeWriteText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class CommandMode extends Command {
    private final Carrots carrots;

    public CommandMode(IBaritone baritone, Carrots carrots) {
        super(baritone, "mode");
        this.carrots = carrots;
    }

    @Override
    public void execute(String label, IArgConsumer args) {
        args.requireMin(1);
        final String mode = args.getString();

        if (mode.equalsIgnoreCase("list")) {
            logDirect("Modes:");
            logDirect("  - write <text>");
            logDirect("  - clock [<Date format (https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html)>]");
            logDirect("  - btc");
            logDirect("  - guard");
            logDirect("  - protect <username>");
            logDirect("  - attackplayer <username>");
            logDirect("  - whitelist add <playername or entitytype>");
            logDirect("  - whitelist remove <playername or entitytype>");
            logDirect("  - whitelist list");
            logDirect(" ");
            logDirect("Use 'mode cancel' to cancel");
        } else if (mode.equalsIgnoreCase("write")) {
            args.requireMin(2);
            String end = "";
            for (ICommandArgument arg : args.getArgs()) {
                end += " " + arg.getValue();
            }
            end.replaceFirst(" ", "");

            carrots.setMode(new ModeWriteText(end));
        } else if (mode.equalsIgnoreCase("clock")) {
            String format = "";

            if (args.hasAny()) {
                for (ICommandArgument arg : args.getArgs()) {
                    format += " " + arg.getValue();
                }
                format.replaceFirst(" ", "");
            } else format = "HH:mm";
            carrots.setMode(new ModeClock(format));
        } else if (mode.equalsIgnoreCase("guard")) {
            carrots.setMode(new ModeGuard());
        } else if (mode.equalsIgnoreCase("btc")) {
            carrots.setMode(new ModeBTCPrice());
        } else if (mode.equalsIgnoreCase("protect")) {
            args.requireMin(2);
            carrots.setMode(new ModeProtect(args.getString()));
        } else if (mode.equalsIgnoreCase("attackplayer")) {
            args.requireMin(1);
            carrots.setMode(new ModeAttackPlayer(args.getString()));
        } else if (mode.equalsIgnoreCase("cancel")) {
            final ISelectionManager manager = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager();
            manager.removeAllSelections();
            carrots.cancelMode();
            Helper.HELPER.logDirect("Canceled");
        } else logDirect("Mode not found. Use #mode list");
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return new TabCompleteHelper().append("write", "guard", "protect", "attackplayer", "cancel", "clock").filterPrefix(args.getString()).sortAlphabetically().stream();
    }

    @Override
    public String getShortDesc() {
        return "Enable a mode";
    }

    @Override
    public List<String> getLongDesc() {
        return new ArrayList<>();
    }
}