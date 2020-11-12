package com.jumbo1907.carrots.modes;

import baritone.api.utils.BetterBlockPos;
import com.jumbo1907.carrots.Carrots;
import com.jumbo1907.carrots.modes.writing.NumberFormatter;
import com.jumbo1907.carrots.PlaceTask;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class WritingMode extends Mode {

    private final long delayBetweenFetch;
    private String result;
    private final NumberFormatter numberFormatter;

    public WritingMode(String name, long delayBetweenFetch) {
        super(name);
        this.delayBetweenFetch = delayBetweenFetch;
        this.numberFormatter = new NumberFormatter();
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getDelayBetweenFetch() {
        return delayBetweenFetch;
    }



    public void updateFetch() {
        setResult(fetch());
    }

    public int[][][] buildMatrix() {
        return numberFormatter.translate(result);
    }

    private long lastFetch = 0;

    @Override
    public void update() {
        if (Math.abs(lastFetch - System.currentTimeMillis()) < delayBetweenFetch) return;

        lastFetch = System.currentTimeMillis();

        //Start a new thread every time, I know
        new Thread(this::updateFetch).start();
    }

    public List<PlaceTask> start() {
        if (getResult() == null) new ArrayList<>();

        //Settings
        Minecraft.getInstance().player.sendChatMessage("setting maxFallHeightNoWater 500");
        Minecraft.getInstance().player.sendChatMessage("setting blocksToAvoid sandstone");

        final int[][][] matrix = buildMatrix();
        int xOffset = 0;

        List<PlaceTask> queue = new ArrayList<>();
        for (int[][] ints : matrix) {
            //Place blocks first
            List<BetterBlockPos> randomBlockPos = new ArrayList<>();
            List<BetterBlockPos> blockBreaking = new ArrayList<>();
            for (int y = 0; y < ints.length; y++) {
                for (int x = 0; x < ints[y].length; x++) {
                    final int value = ints[y][x];
                    final BetterBlockPos betterBlockPos = new BetterBlockPos(Carrots.CENTER.getX() + x + xOffset, Carrots.CENTER.getY() + 4 - y, Carrots.CENTER.getZ());

                    if (value == 1)
                        randomBlockPos.add(betterBlockPos);
                    else blockBreaking.add(betterBlockPos);
                }
            }

            xOffset += (ints[0].length);

            queue.add(new PlaceTask(randomBlockPos, Blocks.SANDSTONE));
            queue.add(new PlaceTask(blockBreaking, Blocks.AIR));
        }
        return queue;
    }

    public abstract  String fetch();
}
