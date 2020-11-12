package com.jumbo1907.carrots.command;

import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.Helper;
import com.jumbo1907.carrots.Carrots;
import net.minecraft.entity.EntityType;

import java.util.List;
import java.util.stream.Stream;

public class CommandWhitelist extends Command {

    private final Carrots carrots;

    public CommandWhitelist(IBaritone iBaritone, Carrots carrots) {
        super(iBaritone, "whitelist");
        this.carrots = carrots;
    }


    @Override
    public void execute(String s, IArgConsumer args) {
        args.requireMin(1);
        final String option = args.getString();
        if (option.equalsIgnoreCase("list")) {

            Helper.HELPER.logDirect("Whitelisted entity types:");
            if (carrots.getWhitelist().isEmpty()) {
                Helper.HELPER.logDirect("Empty");
            } else for (EntityType entityType : carrots.getWhitelist()) {
                Helper.HELPER.logDirect("  - " + entityType.toString());
            }

            Helper.HELPER.logDirect("Whitelisted players");
            if (carrots.getPlayerWhitelist().isEmpty()) {
                Helper.HELPER.logDirect("Empty");
            } else for (String player : carrots.getPlayerWhitelist()) {
                Helper.HELPER.logDirect("  - " + player);
            }
        } else {
            args.requireMin(1);

            final String target = args.getString();
            final EntityType entityType = getEntityTypeFromString(target);

            if (option.equalsIgnoreCase("add")) {
                if (entityType != null) {
                    if (carrots.getWhitelist().contains(entityType)) {
                        Helper.HELPER.logDirect("This entity is already added to the whitelist");
                    } else {
                        carrots.getWhitelist().add(entityType);
                        Helper.HELPER.logDirect("Added!");
                    }
                } else {
                    if (carrots.getPlayerWhitelist().contains(target.toLowerCase())) {
                        Helper.HELPER.logDirect("This player is already added to the whitelist");
                    } else {
                        carrots.getPlayerWhitelist().add(target.toLowerCase());
                        Helper.HELPER.logDirect("Added!");
                    }
                }
            } else if (option.equalsIgnoreCase("remove")) {
                if (entityType != null) {
                    if (!carrots.getWhitelist().contains(entityType)) {
                        Helper.HELPER.logDirect("This entity is not in the whitelist");
                    } else {
                        carrots.getWhitelist().remove(entityType);
                        Helper.HELPER.logDirect("Removed!");
                    }
                } else {
                    if (!carrots.getPlayerWhitelist().contains(target.toLowerCase())) {
                        Helper.HELPER.logDirect("This player is not in the whitelist");
                    } else {
                        carrots.getPlayerWhitelist().remove(target.toLowerCase());
                        Helper.HELPER.logDirect("Removed!");
                    }
                }
            }

        }
    }

    private EntityType getEntityTypeFromString(String entityType) {
        return EntityType.byKey(entityType).orElse(null);
    }

    @Override
    public Stream<String> tabComplete(String s, IArgConsumer iArgConsumer) {
        return null;
    }

    @Override
    public String getShortDesc() {
        Helper.HELPER.logDirect("Usage:");
        Helper.HELPER.logDirect("whitelist add <entityType or Player>");
        Helper.HELPER.logDirect("whitelist remove <entityType or player>");
        Helper.HELPER.logDirect("whitelist list");
        return null;
    }

    @Override
    public List<String> getLongDesc() {
        Helper.HELPER.logDirect("Usage:");
        Helper.HELPER.logDirect("whitelist add <entityType or Player>");
        Helper.HELPER.logDirect("whitelist remove <entityType or player>");
        Helper.HELPER.logDirect("whitelist list");
        return null;
    }
}
