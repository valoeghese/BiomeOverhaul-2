package tk.valoeghese.biomeoverhaul;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import tk.valoeghese.biomeoverhaul.worldtype.BiomeOverhaulWorldTypes;

public class BiomeOverhaul implements ModInitializer {
	public static final Logger modLog = LogManager.getLogger("BiomeOverhaul");

	@Override
	public void onInitialize() {
		BiomeOverhaulWorldTypes.init();
	}
}
