package tk.valoeghese.biomeoverhaul.noise;

import java.util.Random;

import org.apache.commons.lang3.NotImplementedException;

import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveNoise;
import tk.valoeghese.worldcomet.api.noise.OpenSimplexNoise;
import tk.valoeghese.worldcomet.api.noise.RidgedNoise;

public final class MountainsNoise extends OctaveNoise {
	public MountainsNoise(Random rand, int octaves, double spread, double ease, double max, double shift, double sparsity) {
		this.samplers = new Noise[octaves];
		this.clamp = 1D / (1D - (1D / Math.pow(2, octaves)));

		for (int i = 0; i < octaves; ++i) {
			samplers[i] = new RidgedNoise(new OpenSimplexNoise(rand), shift);
		}

		this.setSpread(spread);
		this.amplitudeHigh = 1.0;
		this.amplitudeLow = 1.0;
		this.sparsity = sparsity;
		this.scale = max / sparsity;
		this.ease = ease / sparsity;
	}

	private final double sparsity, scale, ease;

	@Override
	public double sample(double x, double y) {
		double sample = super.sample(x, y) - this.sparsity;
		return sample > 0.0 ? sample * this.scale : sample * this.ease;
	}

	@Override
	public double sample(double x, double y, double z) {
		double sample = super.sample(x, y, z) - this.sparsity;
		return sample > 0.0 ? sample * this.scale : sample * this.ease;
	}

	@Override
	public double sample(double x, double y, double z, double spreadModifier, double amplitudeHMod, double amplitudeLMod, int octaves) {
		throw new NotImplementedException("Custom sample method not implemented in MountainsNoise!");
	}
}