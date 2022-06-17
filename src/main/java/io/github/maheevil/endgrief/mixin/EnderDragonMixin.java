package io.github.maheevil.endgrief.mixin;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragon.class)
public class EnderDragonMixin {
    @Redirect(
            method = "checkWalls",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/GameRules.getBoolean (Lnet/minecraft/world/level/GameRules$Key;)Z"
            )
    )
    public boolean checkGameRules(GameRules instance, GameRules.Key<GameRules.BooleanValue> key){
        return false;
    }
}
