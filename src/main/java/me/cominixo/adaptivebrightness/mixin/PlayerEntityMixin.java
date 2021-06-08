package me.cominixo.adaptivebrightness.mixin;

import me.cominixo.adaptivebrightness.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    public void adaptBrightness(CallbackInfo ci) {

        if (Config.disabled) {
            return;
        }

        if (((LivingEntity)(Object)this).world.isClient) {
            MinecraftClient client = MinecraftClient.getInstance();

            int totalLight = client.world.getChunkManager().getLightingProvider().getLight(((Entity)(Object)this).getBlockPos(), 0);
            double gammaTarget = clamp(Math.abs(totalLight-15.0)/15.0, Config.min_gamma, Config.max_gamma);

            if (client.options.gamma < gammaTarget) {
                client.options.gamma = Math.min(client.options.gamma + 0.05, gammaTarget);
            } else if (client.options.gamma > gammaTarget) {
                client.options.gamma = Math.max(client.options.gamma - 0.05, gammaTarget);
            }

        }
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

}
