package com.jumbo1907.carrots;

import baritone.api.BaritoneAPI;
import baritone.api.schematic.CompositeSchematic;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockOptionalMeta;
import net.minecraft.block.Block;
import net.minecraft.util.math.vector.Vector3i;

import java.util.List;

public class PlaceTask {

    private final List<BetterBlockPos> betterBlockPosList;
    private final Block block;

    public PlaceTask(List<BetterBlockPos> betterBlockPosList, Block block) {
        this.betterBlockPosList = betterBlockPosList;
        this.block = block;
    }

    public List<BetterBlockPos> getBetterBlockPosList() {
        return betterBlockPosList;
    }

    public Block getBlock() {
        return block;
    }

    public void execute(){
         final ISelectionManager manager = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager();
        manager.removeAllSelections();

        if(betterBlockPosList.isEmpty()) return;

        betterBlockPosList.forEach(betterBlockPos -> manager.addSelection(betterBlockPos, betterBlockPos));

            final ISelection[] selections = manager.getSelections();

            BetterBlockPos origin = selections[0].min();
            final CompositeSchematic composite = new CompositeSchematic(0, 0, 0);

            for (ISelection selection : selections) {
                BetterBlockPos min = selection.min();
                origin = new BetterBlockPos(
                        Math.min(origin.getX(), min.getX()),
                        Math.min(origin.getZ(), min.getY()),
                        Math.min(origin.getZ(), min.getZ())
                );
            }

            for (ISelection selection : selections) {
                final Vector3i size = selection.size();
                final BetterBlockPos min = selection.min();
                final ISchematic schematic = new FillSchematic(size.getX(), size.getY(), size.getZ(), new BlockOptionalMeta(block));
                composite.put(schematic, min.getX() - origin.getX(), min.getY() - origin.getY(), min.getZ() - origin.getZ());
            }
            BaritoneAPI.getProvider().getPrimaryBaritone().getBuilderProcess().build("Fill", composite, origin);
    }
}
