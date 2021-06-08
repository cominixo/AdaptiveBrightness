package me.cominixo.adaptivebrightness;

import me.cominixo.adaptivebrightness.config.Config;
import net.fabricmc.api.ClientModInitializer;

import java.io.IOException;

public class AdaptiveBrightness implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        try {
            Config.load();
        } catch (IOException ignored) { }
    }
}
