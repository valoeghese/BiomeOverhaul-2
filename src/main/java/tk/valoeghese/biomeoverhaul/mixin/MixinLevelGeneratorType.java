package tk.valoeghese.biomeoverhaul.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.level.LevelGeneratorType;
import tk.valoeghese.biomeoverhaul.worldtype.BiomeOverhaulWorldTypes;

// make sure the level generator type is initialised early enough on servers
// if the fabric level type pr is merged, this will no longer be necessary

@Mixin(LevelGeneratorType.class)
public class MixinLevelGeneratorType {
	@Inject(at = @At("HEAD"), method = "getTypeFromName", cancellable = true)
	private static void injectWorldType(String name, CallbackInfoReturnable<LevelGeneratorType> cir) {
		if (name.equals("tbo_normal")) {
			cir.setReturnValue(BiomeOverhaulWorldTypes.NORMAL.generatorType);
		} else if (name.equals("tbo_fantasy")) {
			cir.setReturnValue(BiomeOverhaulWorldTypes.FANTASY.generatorType);
		}
	}
}
