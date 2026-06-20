package io.github.maheevil.endgrief;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class EndGriefMod implements ModInitializer {
	//public static final Logger LOGGER = LoggerFactory.getLogger("endgrief");
	public static final String MOD_ID = "endgrief";

	public static final GameRule<Boolean> disableDragonGrief = GameRuleBuilder
			.forBoolean(true)
			.category(GameRuleCategory.MISC)
			.buildAndRegister(
					Identifier.fromNamespaceAndPath(MOD_ID, "disable_dragon_grief")
			);
	/*GameRuleRegistry
			.register(
					"disableDragonGrief",
					GameRules.Category.MISC,
					GameRuleFactory.createBooleanRule(true)
			);*/
	public static final GameRule<GriefType> pillarGriefType = GameRuleBuilder
			.forEnum(GriefType.REPLACE_AIR)
			.category(GameRuleCategory.MISC).buildAndRegister(
					Identifier.fromNamespaceAndPath(MOD_ID,"end_pillars_grief_type")
			);
	/*GameRuleRegistry
			.register(
					"endPillarsGriefType",
					GameRules.Category.MISC,
					GameRuleFactory.createEnumRule(GriefType.REPLACE_AIR)
			);*/
	public static final GameRule<Level.ExplosionInteraction> endCrystalExplosion = GameRuleBuilder
			.forEnum(Level.ExplosionInteraction.NONE)
			.category(GameRuleCategory.MISC)
			.buildAndRegister(
					Identifier.fromNamespaceAndPath(MOD_ID, "end_crystal_regen_explosion_type")
			);
		/*GameRuleRegistry
			.register(
					"endCrystalRegenExplosionType",
					GameRules.Category.MISC,
					GameRuleFactory.createEnumRule(Level.ExplosionInteraction.NONE)
			);*/

	@Override
	public void onInitialize() {
	}
}
