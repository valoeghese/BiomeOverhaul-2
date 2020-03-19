package tk.valoeghese.biomeoverhaul.shape;

import java.util.Random;

import org.apache.commons.lang3.NotImplementedException;

import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveNoise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;
import tk.valoeghese.worldcomet.api.noise.OpenSimplexNoise;
import tk.valoeghese.worldcomet.api.noise.RidgedNoise;
import tk.valoeghese.worldcomet.api.terrain.function.HeightmapFunction;

public final class NormalTypeHeightmap implements HeightmapFunction {
	public NormalTypeHeightmap(long seed) {
		INSTANCE = this;
		Random rand = new Random(seed);
		this.continentHeightmap = new OctaveOpenSimplexNoise(rand, 3, 1850.0, 45, 30);
		this.mountainsHeightmap = new MountainsNoise(rand, 3, 530.0, 100.0, 0.5, 0.5);
		this.detailHeightmap = new OctaveOpenSimplexNoise(rand, 2, 60.0, 1.0);
		this.detailScaleHeightmap = new OctaveOpenSimplexNoise(rand, 1, 256.0, 1.0);
	}

	private final Noise
	continentHeightmap, mountainsHeightmap,
	detailHeightmap, detailScaleHeightmap;

	@Override
	public double getHeight(int x, int z) {
		// continent
		double result = this.continentHeightmap.sample(x, z);
		// mountains
		result += this.mountainsHeightmap.sample(x, z);

		// detail
		double scale = 1 + 5 * (this.detailScaleHeightmap.sample(x, z) + 1.0);
		return result + scale * this.detailHeightmap.sample(x, z);
	}

	public static NormalTypeHeightmap INSTANCE;
}

class MountainsNoise extends OctaveNoise {
	public MountainsNoise(Random rand, int octaves, double spread, double max, double shift, double sparsity) {
		this.samplers = new Noise[octaves];
		this.clamp = 1D / (1D - (1D / Math.pow(2, octaves)));

		for (int i = 0; i < octaves; ++i) {
			samplers[i] = new RidgedNoise(new OpenSimplexNoise(rand.nextLong()), shift);
		}

		this.setSpread(spread);
		this.amplitudeHigh = 1.0;
		this.amplitudeLow = 1.0;
		this.sparsity = sparsity;
		this.scale = max / sparsity;
	}

	private final double sparsity, scale;

	@Override
	public double sample(double x, double y) {
		double sample = super.sample(x, y) - this.sparsity;
		return sample > 0.0 ? sample * this.scale : 0;
	}

	@Override
	public double sample(double x, double y, double z) {
		double sample = super.sample(x, y, z) - this.sparsity;
		return sample > 0.0 ? sample * this.scale : 0;
	}

	@Override
	public double sample(double x, double y, double z, double spreadModifier, double amplitudeHMod, double amplitudeLMod, int octaves) {
		throw new NotImplementedException("Custom sample method not implemented in MountainsNoise!");
	}
}