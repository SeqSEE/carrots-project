package com.jumbo1907.carrots.modes.combat;

import baritone.api.BaritoneAPI;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import com.jumbo1907.carrots.modes.ModeCombat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;

public class ModeGuard extends ModeCombat {

    public ModeGuard() {
        super("Guard");

        final ISelectionManager selectionManager = BaritoneAPI.getProvider().getPrimaryBaritone().getSelectionManager();
        if (selectionManager.getSelections().length == 0) return;

        final BetterBlockPos posA = selectionManager.getLastSelection().pos1();
        final BetterBlockPos posB = selectionManager.getLastSelection().pos2();

        target(entity -> {
            final Vector3d vec = entity.getPositionVec();
            return (entity instanceof MonsterEntity && isBetween(vec.x, posA.getX(), posB.getX())
                    && isBetween(vec.y, posA.getY(), posB.getY())
                    && isBetween(vec.z, posA.getZ(), posB.getZ()));
        });
    }

    @Override
    public HashMap<Entity, Double> getEntitiesInRange() {
        final HashMap<Entity, Double> output = new HashMap<>();
        super.getEntitiesInRange().forEach((entity, aDouble) -> {
            if (entity instanceof MonsterEntity) output.put(entity, aDouble);
        });

        return output;
    }

    private static boolean isBetween(double number, int a, int b) {
        //Make sure B has the highest value
        if (a > b) {
            final int temp = a;
            a = b;
            b = temp;
        }
        return number >= a && number <= b;
    }
}
