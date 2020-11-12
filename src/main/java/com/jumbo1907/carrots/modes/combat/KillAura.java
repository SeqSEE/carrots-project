package com.jumbo1907.carrots.modes.combat;

import baritone.api.BaritoneAPI;
import baritone.api.utils.RotationUtils;
import com.jumbo1907.carrots.Carrots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class KillAura {

    private static long delayForAttack(ClientPlayerEntity player) {
        final ItemStack itemStack = player.inventory.getCurrentItem();

        for (AttributeModifier value : itemStack.getAttributeModifiers(EquipmentSlotType.MAINHAND).values()) {
            if (value.getID().toString().equals("fa233e1c-4180-4865-b01b-bcce9785aca3"))
                return ((long) ((1d / (4d + value.getAmount())) * 1000d)) + 50;
        }
        return 250;
    }

    private static long lastHit = 0;

    public static void attack(ClientPlayerEntity player, Entity z) {
        final Carrots carrots = Carrots.getInstance();

        if(z instanceof PlayerEntity && carrots.getPlayerWhitelist().contains(z.getName().getString().toLowerCase())) return;
        if(carrots.getWhitelist().contains(z.getType())) return;

        if (lastHit == 0 || (System.currentTimeMillis() - lastHit) > delayForAttack(player)) {
            lastHit = System.currentTimeMillis();

            //Look at target
            BaritoneAPI.getProvider().getPrimaryBaritone().getLookBehavior().updateTarget(RotationUtils.calcRotationFromCoords(
                            new BlockPos(player.getPositionVec().x, player.getPositionVec().y, player.getPositionVec().z),
                            new BlockPos(z.getBoundingBox().getCenter().x, z.getBoundingBox().getCenter().y, z.getBoundingBox().getCenter().z)
            ), true);

            //Hit target
            Minecraft.getInstance().playerController.attackEntity(player, z);

            //Swing I guess
            player.swingArm(Hand.MAIN_HAND);
        }
    }
}
