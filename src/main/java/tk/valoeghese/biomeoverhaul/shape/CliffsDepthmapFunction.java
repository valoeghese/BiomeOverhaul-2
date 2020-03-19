package tk.valoeghese.biomeoverhaul.shape;

import java.util.Random;

import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;
import tk.valoeghese.worldcomet.api.terrain.function.DepthmapFunction;

public class CliffsDepthmapFunction implements DepthmapFunction {
	public CliffsDepthmapFunction(long seed) {
		this.rand = new Random(seed + 6);
		this.noise = new OctaveOpenSimplexNoise(this.rand, 2, 100).stretch3DY(5.0 / 100.0);
	}

	private final Noise noise;
	private final Random rand;

	@Override
	public double getHeight(int x, int y, int z) {
		if (!NormalTypeHeightmap.INSTANCE.canAddCliffs(x, z)) return 0.0;
		double sample = this.noise.sample(x, y, z);
		return sample > 0.2D ? 3 * sample + 5 : 0;
	}
}
