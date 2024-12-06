package org.felixWegener.bastichunkchallange;

import net.fabricmc.api.ModInitializer;

public class Bastichunkchallange implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("init Felix Minecraft Mod");
        Challenge.initChallenge();
    }

}
