package com.jumbo1907.carrots;

//import baritone.api.BaritoneAPI;

import baritone.api.BaritoneAPI;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.Helper;
import com.jumbo1907.carrots.command.CommandCenter;
import com.jumbo1907.carrots.command.CommandMode;
import com.jumbo1907.carrots.command.CommandRemoteSel;
import com.jumbo1907.carrots.command.CommandWhitelist;
import com.jumbo1907.carrots.modes.Mode;
import com.jumbo1907.carrots.modes.WritingMode;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod("carrot")
public class Carrots {

    private final List<EntityType> whitelist = new ArrayList<>();
    private final List<String> playerWhitelist = new ArrayList<>();

    //Default center
    public static BlockPos CENTER = new BlockPos(0, 55, 40);

    private static final Block MAIN_BLOCK = Blocks.SANDSTONE;
    private final Queue<PlaceTask> queue = new LinkedList<>();
    private Mode currentMode;

    private static Carrots CARROTS;

    public Carrots() {
        //Register the main event
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CommandSystem(this));

        CARROTS = this;
    }

    public void setCenter(BetterBlockPos betterBlockPos) {
        CENTER = betterBlockPos;
        CENTER.add(0, -1, 0);
    }

    public void setMode(Mode mode) {
        this.currentMode = mode;
        queue.clear();

        Helper.HELPER.logDirect(mode.getName() + " enabled.");

        if (mode instanceof WritingMode) {
            final ISelectionManager manager = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager();
            manager.removeAllSelections();
        }
    }

    public void cancelMode() {
        this.currentMode = null;
        queue.clear();
        scaffolding.clear();

        //Copied from the cancel command from Baritone.
        //Source: https://github.com/cabaletta/baritone/blob/9591b6fdefa2091f208dc2faed2f779269b2ec35/src/main/java/baritone/command/defaults/ForceCancelCommand.java
        final IPathingBehavior pathingBehavior = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior();
        pathingBehavior.cancelEverything();
        pathingBehavior.forceCancel();
    }

    private boolean commandsLoaded = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        final World world = Minecraft.getInstance().world;

        if (currentMode != null && BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext() != null)
            currentMode.update();
        if (Minecraft.getInstance().player == null || world == null) return;

        if (!commandsLoaded) {
            loadCommands();
            this.commandsLoaded = true;
        }
        if (currentMode instanceof WritingMode) {
            //Every 0.1 seconds, and if the last task has been completed.
            if (world.getGameTime() % 2 != 0 && !BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().isActive()) {
                if (!queue.isEmpty()) {
                    queue.remove().execute();
                    System.out.println("Next task (" + queue.size() + " tasks queued)");
                } else {
                    if (!scaffolding.isEmpty()) {
                        queue.add(new PlaceTask(new ArrayList<>(scaffolding), Blocks.AIR));
                        scaffolding.clear();
                    } else queue.addAll(((WritingMode) currentMode).start());
                }
            }
        }
    }

    private void loadCommands() {
        Arrays.asList(
                new CommandRemoteSel(BaritoneAPI.getProvider().getPrimaryBaritone(), this),
                new CommandMode(BaritoneAPI.getProvider().getPrimaryBaritone(), this),
                new CommandCenter(BaritoneAPI.getProvider().getPrimaryBaritone(), this),
                new CommandWhitelist(BaritoneAPI.getProvider().getPrimaryBaritone(), this)).forEach(command -> BaritoneAPI.getProvider().getPrimaryBaritone().getCommandManager().getRegistry().register(command));
    }

    final List<BetterBlockPos> scaffolding = new ArrayList<>();

    //Block listener for writing modes
    @SubscribeEvent
    public void onPlace(BlockEvent.EntityPlaceEvent e) {
        if (e.getPlacedBlock().getBlock() != MAIN_BLOCK && currentMode instanceof WritingMode) {
            scaffolding.add(new BetterBlockPos(e.getPos()));
        }
    }

    public List<EntityType> getWhitelist() {
        return whitelist;
    }

    public List<String> getPlayerWhitelist() {
        return playerWhitelist;
    }

    public static Carrots getInstance() {
        return CARROTS;
    }
}
