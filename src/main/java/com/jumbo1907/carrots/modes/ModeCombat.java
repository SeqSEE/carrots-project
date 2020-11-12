package com.jumbo1907.carrots.modes;

import baritone.api.BaritoneAPI;
import baritone.api.process.IFollowProcess;
import com.jumbo1907.carrots.Carrots;
import com.jumbo1907.carrots.modes.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;
import java.util.function.Predicate;

public abstract class ModeCombat extends Mode {

    final ClientPlayerEntity playerEntity;
    final PlayerController playerController;
    private final Carrots carrots;

    public ModeCombat(String name) {
        super(name);
        this.playerEntity = Minecraft.getInstance().player;
        this.playerController = Minecraft.getInstance().playerController;
        this.carrots = Carrots.getInstance();
    }

    public void target(Predicate<Entity> predicate) {
        BaritoneAPI.getProvider().getPrimaryBaritone().getFollowProcess().follow(predicate.and(entity ->
               !(entity instanceof PlayerEntity && carrots.getPlayerWhitelist().contains(entity.getName().getString().toLowerCase()) || carrots.getWhitelist().contains(entity.getType()))
        ));
    }

    private long updateCounter = 0;

    @Override
    public void update() {
        updateCounter++;
        if (updateCounter % 5 != 0 || playerEntity == null || playerController == null) return;
        updateCounter = 0;

        //Loop through every target in the following list.
        Entity closestEntity = null;
        double closestDist = Double.MAX_VALUE;

        final HashMap<Entity, Double> entitiesInRange = getEntitiesInRange();

        for (Entity entity : entitiesInRange.keySet()) {
            final double currentDist = entitiesInRange.get(entity);
            if ((closestEntity == null || currentDist < closestDist)) {
                closestEntity = entity;
                closestDist = currentDist;
            }
        }

        //Attack
        if (closestEntity != null) KillAura.attack(playerEntity, closestEntity);
    }

    public HashMap<Entity, Double> getEntitiesInRange() {
        final HashMap<Entity, Double> entities = new HashMap<>();

        final Vector3d center = getCenterForDistanceCalculation();
        final IFollowProcess followProcess = BaritoneAPI.getProvider().getPrimaryBaritone().getFollowProcess();

        if (center == null || followProcess == null || followProcess.following() == null) return entities;

        for (Entity entity : BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().entities()) {
            if (!entity.isAlive() || entity instanceof ClientPlayerEntity || !(entity instanceof LivingEntity))
                continue;

            final double currentDist = entity.getPositionVec().distanceTo(center);
            if (currentDist <= playerController.getBlockReachDistance()) {
                entities.put(entity, currentDist);
            }
        }
        return entities;
    }

    public Vector3d getCenterForDistanceCalculation() {
        return playerEntity.getPositionVec();
    }

    public ClientPlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }
}
