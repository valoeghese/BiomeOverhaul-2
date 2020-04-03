package tk.valoeghese.biomeoverhaul;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import tk.valoeghese.biomeoverhaul.worldtype.BiomeOverhaulWorldTypes;

public class BiomeOverhaul implements ModInitializer {
	@Override
	public void onInitialize() {
		BiomeOverhaulWorldTypes.init();
	}

	public static Identifier id(String name) {
		return new Identifier("biomeoverhaul", name);
	}

	public static final Logger modLog = LogManager.getLogger("BiomeOverhaul");
}
