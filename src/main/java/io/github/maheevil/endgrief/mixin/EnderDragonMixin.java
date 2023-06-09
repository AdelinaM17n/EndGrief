package io.github.maheevil.endgrief.mixin;

import io.github.maheevil.endgrief.EndGriefMod;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {

    protected EnderDragonMixin(EntityType<? extends Mob> entityType, Level level){
        super(entityType, level);
    }

    @Redirect(
            method = "checkWalls",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/world/level/block/state/BlockState.is (Lnet/minecraft/tags/TagKey;)Z",
                    ordinal = 1
            )
    )
    public boolean checkGameRules(BlockState instance, TagKey<Block> tagKey){
        return this.level().getGameRules().getBoolean(EndGriefMod.disableDragonGrief) || instance.is(tagKey);
    }
}
