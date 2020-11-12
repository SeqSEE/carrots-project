package com.jumbo1907.carrots.command;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.command.Command;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import com.jumbo1907.carrots.Carrots;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;
import java.util.stream.Stream;

public class CommandRemoteSel extends Command {

    private final Carrots carrots;
    private ISelectionManager manager = baritone.getSelectionManager();

    public CommandRemoteSel(IBaritone iBaritone, Carrots carrots) {
        super(iBaritone, "remotesel");
        this.carrots = carrots;
    }

    private BetterBlockPos pos1 = null;

    @Override
    public void execute(String s, IArgConsumer args) {
        args.requireMin(2);

        final String playername = args.getString();
        final String pos = args.getString();

        if (!pos.equalsIgnoreCase("pos1") && !pos.equalsIgnoreCase("pos2")) {
            Helper.HELPER.logDirect("Use #remotesel <player> <pos1/pos2>");
            return;
        }
        final Vector3d vector3d = getLocation(playername);
        if (vector3d == null) {
            Helper.HELPER.logDirect("Use #remotesel <player> <pos1/pos2>");
            Helper.HELPER.logDirect("Player not found or not in render distance.");
            return;
        }

        if (pos.equalsIgnoreCase("pos2")) {
            if (pos1 == null) {
                logDirect("Set pos1 first");
            } else {
                logDirect("Selection added");
                manager.removeAllSelections();
                manager.addSelection(pos1,new BetterBlockPos(vector3d.x, vector3d.y, vector3d.z));
            }
        } else {
            logDirect("Position 1 has been set");
            pos1 = new BetterBlockPos(vector3d.x, vector3d.y, vector3d.z);
        }


    }

    private Vector3d getLocation(String playerName) {
        for (Entity entity : BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().entities()) {
            if (entity instanceof PlayerEntity && entity.getName().getString().equalsIgnoreCase(playerName))
                return entity.getPositionVec();
        }

        return null;
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
