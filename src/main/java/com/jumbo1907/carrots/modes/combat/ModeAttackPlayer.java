package com.jumbo1907.carrots.modes.combat;


import com.jumbo1907.carrots.modes.ModeCombat;
import net.minecraft.entity.player.PlayerEntity;

public class ModeAttackPlayer extends ModeCombat {
    public ModeAttackPlayer(String playerName) {
        super("Attack Player");
        target(entity ->  entity instanceof PlayerEntity && entity.getName().getString().equalsIgnoreCase(playerName));
    }
}
