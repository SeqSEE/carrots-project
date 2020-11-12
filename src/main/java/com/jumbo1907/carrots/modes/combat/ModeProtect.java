package com.jumbo1907.carrots.modes.combat;

import baritone.api.BaritoneAPI;
import com.jumbo1907.carrots.modes.ModeCombat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;

public class ModeProtect extends ModeCombat {

    private final String playerName;

    public ModeProtect(String playerName) {
        super("Protect");
        this.playerName = playerName;

        target(entity -> entity instanceof PlayerEntity && entity.getName().getString().equalsIgnoreCase(playerName));

    }

    @Override
    public HashMap<Entity, Double> getEntitiesInRange() {
        final HashMap<Entity, Double> entities = super.getEntitiesInRange();
        entities.remove(getPlayerFromName(playerName));
        return entities;
    }

    @Override
    public Vector3d getCenterForDistanceCalculation() {
        final PlayerEntity player = getPlayerFromName(playerName);
        return player == null ? null : player.getPositionVec();
    }

    private PlayerEntity getPlayerFromName(String name) {
        for (Entity entity : BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().entities()) {
            if (entity instanceof PlayerEntity && entity.getName().getString().equalsIgnoreCase(name))
                return (PlayerEntity) entity;
        }
        return null;
    }
}