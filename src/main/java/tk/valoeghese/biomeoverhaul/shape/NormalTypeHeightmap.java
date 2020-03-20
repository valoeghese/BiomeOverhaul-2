package tk.valoeghese.biomeoverhaul.shape;

import java.util.Random;

import tk.valoeghese.biomeoverhaul.noise.MountainsNoise;
import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;
import tk.valoeghese.worldcomet.api.terrain.function.HeightmapFunction;

public final class NormalTypeHeightmap implements HeightmapFunction {
	public NormalTypeHeightmap(long seed) {
		INSTANCE = this;
		Random rand = new Random(seed);

		this.continentHeightmap = new OctaveOpenSimplexNoise(rand, 2, 1580.0, 52, 38);
		this.mountainsHeightmap = new MountainsNoise(rand, 3, 560.0, 8.0, 100.0, 0.5, 0.5);
		this.hillsHeightmap = new OctaveOpenSimplexNoise(rand, 3, 410.0, 35, 1);
		this.detailHeightmap = new OctaveOpenSimplexNoise(rand, 3, 70.0);
		this.detailScaleHeightmap = new OctaveOpenSimplexNoise(rand, 1, 256.0);
	}

	private final Noise
	continentHeightmap, mountainsHeightmap, hillsHeightmap,
	detailHeightmap, detailScaleHeightmap;

	public boolean isMountain(int x, int z) {
		return this.mountainsHeightmap.sample(x, z) > 0.0;
	}

	public boolean canAddCliffs(int x, int z) {
		if (this.mountainsHeightmap.sample(x, z) > 0.0) return true;
		if (this.detailScaleHeightmap.sample(x, z) > 0.1) return true;
		return false;
	}

	@Override
	public double getHeight(int x, int z) {
		// continent
		double result = this.continentHeightmap.sample(x, z);
		// mountains
		result += this.mountainsHeightmap.sample(x, z);
		// hills
		result += this.hillsHeightmap.sample(x, z);

		// detail
		double scale = 1 + 9 * (this.detailScaleHeightmap.sample(x, z) + 1.0);
		return result + scale * this.detailHeightmap.sample(x, z);
	}

	public static NormalTypeHeightmap INSTANCE;
}