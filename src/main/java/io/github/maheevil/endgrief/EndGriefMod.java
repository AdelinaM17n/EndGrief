package io.github.maheevil.endgrief;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class EndGriefMod implements ModInitializer {
	//public static final Logger LOGGER = LoggerFactory.getLogger("endgrief");

	public static GameRules.Key<GameRules.BooleanValue> disableDragonGrief = GameRuleRegistry
			.register(
					"disableDragonGrief",
					GameRules.Category.MISC,
					GameRuleFactory.createBooleanRule(true)
			);
	public static GameRules.Key<EnumRule<GriefType>> pillarGriefType = GameRuleRegistry
			.register(
					"endPillarsGriefType",
					GameRules.Category.MISC,
					GameRuleFactory.createEnumRule(GriefType.REPLACE_AIR)
			);
	public static GameRules.Key<EnumRule<Level.ExplosionInteraction>> endCrystalExplosion = GameRuleRegistry
			.register(
					"endCrystalExplosionType",
					GameRules.Category.MISC,
					GameRuleFactory.createEnumRule(Level.ExplosionInteraction.NONE)
			);

	@Override
	public void onInitialize() {
	}
}
