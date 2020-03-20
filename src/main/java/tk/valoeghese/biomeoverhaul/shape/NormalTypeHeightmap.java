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

		this.continent = new OctaveOpenSimplexNoise(rand, 2, 1580.0, 52, 38);

		this.mountains = new MountainsNoise(rand, 3, 580.0, 8.0, 100.0, 0.5, 0.4);
		this.mountainDetail = new OctaveOpenSimplexNoise(rand, 1, 12.0, 4.0);

		// was: 410.0
		this.hills = new OctaveOpenSimplexNoise(rand, 3, 310.0, 35, 1);

		// was: 70.0
		this.detail = new OctaveOpenSimplexNoise(rand, 3, 60.0);
		this.detailScale = new OctaveOpenSimplexNoise(rand, 1, 256.0);
	}

	private final Noise
	continent,
	mountains, mountainDetail,
	hills,
	detail, detailScale;

	public boolean isMountain(int x, int z) {
		return this.mountains.sample(x, z) > 0.0;
	}

	public boolean canAddCliffs(int x, int z) {
		if (this.mountains.sample(x, z) > 0.0) return true;
		if (this.detailScale.sample(x, z) > 0.1) return true;
		return false;
	}

	@Override
	public double getHeight(int x, int z) {
		// continent
		double result = this.continent.sample(x, z);

		// mountains
		double mountains = this.mountains.sample(x, z);

		if (mountains > -0.2) {
			double mdetailEase = clamp(mountains + 0.2, 10.0) * 0.1;
			mountains += mdetailEase * this.mountainDetail.sample(x, z);
		}

		result += mountains;

		// hills
		result += this.hills.sample(x, z);

		// detail
		double scale = 1 + 9 * (this.detailScale.sample(x, z) + 1.0);
		return result + scale * this.detail.sample(x, z);
	}

	private static double clamp(double value, double max) {
		return value > max ? max : value;
	}

	public static NormalTypeHeightmap INSTANCE;
}