package tk.valoeghese.biomeoverhaul.worldtype;

import tk.valoeghese.worldcomet.api.type.WorldType;

public final class BiomeOverhaulWorldTypes {
	public static final WorldType<?> NORMAL = NormalWorldType.create();
	public static final WorldType<?> FANTASY = FantasyWorldType.create();

	public static void init() {
		// NO-OP
	}
}