package io.github.maheevil.endgrief.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.maheevil.endgrief.EndGriefMod;
import io.github.maheevil.endgrief.GriefType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(targets = "net/minecraft/world/level/dimension/end/DragonRespawnAnimation$3")
public class DragonRespawnAnimThreeMixin {
    @WrapOperation(
            method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/dimension/end/EndDragonFight;Ljava/util/List;ILnet/minecraft/core/BlockPos;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/server/level/ServerLevel.removeBlock (Lnet/minecraft/core/BlockPos;Z)Z"
            )
    )
    private boolean wrapRemoveBlockWithCondition(ServerLevel instance, BlockPos pos, boolean b, Operation<Boolean> original) {
        boolean respawn = Objects.requireNonNull(instance.getDragonFight()).hasPreviouslyKilledDragon();

        if (!respawn) return original.call(instance, pos, b);
        if (instance.getGameRules().get(EndGriefMod.pillarGriefType) == GriefType.VANILA) {
            return original.call(instance, pos, b);
        } else {
            return false;
        }
    }

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V",
                    ordinal = 0
            )
    )
    public boolean modifyExplosionType(ServerLevel instance, Entity entity, double v1, double v2, double v3, float v4, Level.ExplosionInteraction explosionInteraction){
        return false;
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V",
                    ordinal = 0
                    //shift = At.Shift.BEFORE
            )
    )
    private void injectAtExplosion(ServerLevel serverLevel, EndDragonFight endDragonFight, List<EndCrystal> list, int i, BlockPos blockPos, CallbackInfo ci, @Local SpikeFeature.EndSpike endSpike){
        serverLevel.explode(
                null,
                (float)endSpike.getCenterX() + 0.5F,
                endSpike.getHeight(),
                (float)endSpike.getCenterZ() + 0.5F,
                5.0F,
                serverLevel.getGameRules().get(EndGriefMod.endCrystalExplosion)
        );
    }
}
