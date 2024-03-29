package io.github.maheevil.endgrief.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import io.github.maheevil.endgrief.EndGriefMod;
import io.github.maheevil.endgrief.GriefType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(SpikeFeature.class)
public class SpikeFeatureMixin {
    @WrapWithCondition(
            method = "placeSpike(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/feature/configurations/SpikeConfiguration;Lnet/minecraft/world/level/levelgen/feature/SpikeFeature$EndSpike;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/levelgen/feature/SpikeFeature.setBlock(Lnet/minecraft/world/level/LevelWriter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
                    ordinal = 0
            )
    )
    private boolean conditionedPlaceAir(SpikeFeature instance, LevelWriter levelWriter, BlockPos pos, BlockState blockState, ServerLevelAccessor levelAccessor) {
        return determineGeneration(pos, levelAccessor);
    }

    @WrapWithCondition(
            method = "placeSpike(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/feature/configurations/SpikeConfiguration;Lnet/minecraft/world/level/levelgen/feature/SpikeFeature$EndSpike;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/levelgen/feature/SpikeFeature.setBlock(Lnet/minecraft/world/level/LevelWriter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
                    ordinal = 1
            )
    )
    private boolean conditionedPlaceObsidian(SpikeFeature instance, LevelWriter levelWriter, BlockPos pos, BlockState blockState, ServerLevelAccessor levelAccessor) {
        boolean respawn = Objects.requireNonNull(levelAccessor.getLevel().getDragonFight()).hasPreviouslyKilledDragon();

        if(!respawn) return true;
        return levelAccessor.getLevelData().getGameRules().getRule(EndGriefMod.pillarGriefType).get() == GriefType.VANILA;
    }

    @WrapWithCondition(
            method = "placeSpike(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/feature/configurations/SpikeConfiguration;Lnet/minecraft/world/level/levelgen/feature/SpikeFeature$EndSpike;)V",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/levelgen/feature/SpikeFeature.setBlock(Lnet/minecraft/world/level/LevelWriter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
                    ordinal = 2
            )
    )
    private boolean conditionedPlaceIronBars(SpikeFeature instance, LevelWriter levelWriter, BlockPos pos, BlockState blockState, ServerLevelAccessor levelAccessor) {
        return determineGeneration(pos, levelAccessor);
    }

    @Unique
    private boolean determineGeneration(BlockPos pos, ServerLevelAccessor levelAccessor) {
        var grieftType = levelAccessor.getLevelData().getGameRules().getRule(EndGriefMod.pillarGriefType).get();
        boolean respawn = Objects.requireNonNull(levelAccessor.getLevel().getDragonFight()).hasPreviouslyKilledDragon();

        if(!respawn)
            return true;
        if(grieftType == GriefType.REPLACE_AIR)
            return levelAccessor.getBlockState(pos).isAir();
        return grieftType == GriefType.VANILA;
    }
}
