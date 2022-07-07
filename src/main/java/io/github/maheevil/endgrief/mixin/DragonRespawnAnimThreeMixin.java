package io.github.maheevil.endgrief.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import io.github.maheevil.endgrief.EndGriefMod;
import io.github.maheevil.endgrief.GriefType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

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
        return instance.getGameRules().getRule(EndGriefMod.pillarGriefType).get() == GriefType.VANILA;
    }

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/server/level/ServerLevel.explode (Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Explosion$BlockInteraction;)Lnet/minecraft/world/level/Explosion;",
                    ordinal = 0
            )
    )
    public boolean modifyExplosionType(ServerLevel instance, Entity entity, double v, double x, double y, float z, Explosion.BlockInteraction blockInteraction){
        return false;
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/server/level/ServerLevel.explode (Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Explosion$BlockInteraction;)Lnet/minecraft/world/level/Explosion;",
                    ordinal = 0,
                    shift = At.Shift.BEFORE
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void injectAtExplosion(ServerLevel serverLevel, EndDragonFight endDragonFight, List<EndCrystal> list, int i, BlockPos blockPos, CallbackInfo ci, int j, boolean bl, boolean bl2, List<?> list2, int k, SpikeFeature.EndSpike endSpike){
        serverLevel.explode(
                null,
                (float)endSpike.getCenterX() + 0.5F,
                endSpike.getHeight(),
                (float)endSpike.getCenterZ() + 0.5F,
                5.0F,
                serverLevel.getGameRules().getRule(EndGriefMod.endCrystalExplosion).get()
        );
    }
}
