package org.felixWegener.bastichunkchallange;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.border.WorldBorder;

public class Bastichunkchallange implements ModInitializer {

    boolean mobExistes;

    @Override
    public void onInitialize() {
        System.out.println("init Felix Minecraft Mod");
        Challenge.initChallenge();
    }

}
