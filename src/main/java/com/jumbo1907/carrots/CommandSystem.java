package com.jumbo1907.carrots;

import baritone.api.BaritoneAPI;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import com.jumbo1907.carrots.modes.combat.ModeAttackPlayer;
import com.jumbo1907.carrots.modes.combat.ModeGuard;
import com.jumbo1907.carrots.modes.combat.ModeProtect;
import com.jumbo1907.carrots.modes.writing.ModeBTCPrice;
import com.jumbo1907.carrots.modes.writing.ModeClock;
import com.jumbo1907.carrots.modes.writing.ModeWriteText;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommandSystem {

    private final Carrots carrots;

    public CommandSystem(Carrots carrots) {
        this.carrots = carrots;
    }


    String identifier;
    private static final String WILDCARD = "!*";
    private String lastCommand;

    @SubscribeEvent
    public void chatMessage(ClientChatReceivedEvent event) {
        if (identifier == null) identifier = "!" + Minecraft.getInstance().player.getName().getString().toLowerCase();

        final StringBuilder stringBuilder = new StringBuilder();
        boolean startReading = false;

        String message = event.getMessage().getString();
        if(!message.contains(WILDCARD+" ")) message.replaceFirst(WILDCARD,WILDCARD+" ");
        if(!message.contains(identifier+" ")) message.replaceFirst(identifier,identifier+" ");

        for (String s : message.split(" ")) {
            if (s.equalsIgnoreCase(identifier) || s.equals(WILDCARD)) {
                startReading = true;
                continue;
            }

            if (startReading) stringBuilder.append(stringBuilder.toString().isEmpty() ? "" : " ").append(s);
        }

        if (!stringBuilder.toString().isEmpty() && (lastCommand == null || lastCommand.equalsIgnoreCase(stringBuilder.toString()))){
            lastCommand = stringBuilder.toString();
            BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().execute(stringBuilder.toString());
            System.out.println("Commnd: " + lastCommand);
        }
    }

    private boolean onCommand(String command, boolean broadcast) {
        final String[] args = command.split(" ");

        if (args[0].equalsIgnoreCase("center")) {
            final BetterBlockPos origin = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().playerFeet();
            carrots.setCenter(origin);
            Helper.HELPER.logDirect(String.format("Center set (%s)", origin.toString()));
            return true;
        }

        if (args[0].equalsIgnoreCase("write")) {
            carrots.setMode(new ModeWriteText(command.replaceFirst(args[0] + " ", "")));
            Helper.HELPER.logDirect("Write mode enabled");
            return true;
        }

        if (args[0].equalsIgnoreCase("clock")) {
            String format = "HH:mm";
            if (args.length > 1) {
                StringBuilder stringBuilder = new StringBuilder();

                for (String arg : args) {
                    if (!arg.equalsIgnoreCase(args[0])) {
                        stringBuilder.append(arg + " ");
                    }
                }

                format = stringBuilder.toString();
            }

            carrots.setMode(new ModeClock(format));
            Helper.HELPER.logDirect("Clock mode enabled");
            return true;
        }

        if (args[0].equalsIgnoreCase("btc")) {

            carrots.setMode(new ModeBTCPrice());
            Helper.HELPER.logDirect("BTC mode enabled");
            return true;
        }
        if (args[0].equalsIgnoreCase("guard")) {
            carrots.setMode(new ModeGuard());
            Helper.HELPER.logDirect("Guard mode enabled");

            return true;
        }

        if (args[0].equalsIgnoreCase("protect")) {
            carrots.setMode(new ModeProtect(args[1]));
            Helper.HELPER.logDirect("Protect mode enabled");

            return true;
        }
        if (args[0].equalsIgnoreCase("attackplayer")) {
            carrots.setMode(new ModeAttackPlayer(args[1]));
            Helper.HELPER.logDirect("Attack mode enabled");
            return true;
        }


        if (args[0].equalsIgnoreCase("canceltask")) {
            final ISelectionManager manager = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager();
            manager.removeAllSelections();
            carrots.cancelMode();
            Helper.HELPER.logDirect("Canceled");
            return true;
        }

        if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length == 3) {
                final String target = args[2];
                final EntityType entityType = getEntityTypeFromString(target);
                if (args[1].equalsIgnoreCase("add")) {
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
                    return true;
                } else if (args[1].equalsIgnoreCase("remove")) {
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
                    return true;
                }
            } else

                //Usage:
                //whitelist add <entityType or Player>
                //whitelist remove <entityType or player>
                //whitelist list
                if (args.length == 2 && args[1].equalsIgnoreCase("list")) {

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

                    return true;
                }
            Helper.HELPER.logDirect("Usage:");
            Helper.HELPER.logDirect("whitelist add <entityType or Player>");
            Helper.HELPER.logDirect("whitelist remove <entityType or player>");
            Helper.HELPER.logDirect("whitelist list");
            return true;
        }
        Minecraft.getInstance().player.sendChatMessage(command);
        return false;
    }

    private void sendMessage(String s, boolean broadcast) {
        Helper.HELPER.logDirect(s);
    }

    private EntityType getEntityTypeFromString(String entityType) {
        return EntityType.byKey(entityType).orElse(null);
    }
}
