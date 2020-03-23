package tk.valoeghese.biomeoverhaul.surface;

import tk.valoeghese.biomeoverhaul.BiomeOverhaul;

public final class NormalSurfaces {
	public static final SwitchableSurface SPARSE = new RealisticSurface(0, BiomeOverhaul.id("normal_sparse"));
	public static final SwitchableSurface DEFAULT = new RealisticSurface(1, BiomeOverhaul.id("normal_default"));
	public static final SwitchableSurface LIGHT_FOREST = new RealisticSurface(2, BiomeOverhaul.id("normal_forest_light"));
	public static final SwitchableSurface DENSE_FOREST = new RealisticSurface(3, BiomeOverhaul.id("normal_forest_dense"));
}
