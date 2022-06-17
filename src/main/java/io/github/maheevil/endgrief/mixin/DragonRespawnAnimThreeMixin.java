package io.github.maheevil.endgrief.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net/minecraft/world/level/dimension/end/DragonRespawnAnimation$3")
public class DragonRespawnAnimThreeMixin {
    @WrapWithCondition(
            method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/dimension/end/EndDragonFight;Ljava/util/List;ILnet/minecraft/core/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/server/level/ServerLevel.removeBlock (Lnet/minecraft/core/BlockPos;Z)Z"
            )
    )
    private boolean wrapRemoveBlockWithCondition(ServerLevel instance, BlockPos pos, boolean b){
        return false;
    }

    @ModifyArg(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/server/level/ServerLevel.explode (Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Explosion$BlockInteraction;)Lnet/minecraft/world/level/Explosion;",
                    ordinal = 0
            ),
            index = 5
    )
    public Explosion.BlockInteraction modifyExplosionType(Explosion.BlockInteraction par6){
        return Explosion.BlockInteraction.NONE;
    }
}
