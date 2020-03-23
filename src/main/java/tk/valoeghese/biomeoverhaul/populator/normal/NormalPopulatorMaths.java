package tk.valoeghese.biomeoverhaul.populator.normal;

import java.util.Random;

import tk.valoeghese.biomeoverhaul.surface.SwitchableSurface;
import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;
import tk.valoeghese.worldcomet.api.surface.Surface;

public final class NormalPopulatorMaths {
	public NormalPopulatorMaths(Random rand) {
		this.temperatureVariationNoise = new OctaveOpenSimplexNoise(rand, 1, 50.0 / 16.0);
		this.humidityNoise = new OctaveOpenSimplexNoise(rand, 1, 360.0 / 16.0);
		this.temperatureOffset = (double) (1100 - rand.nextInt(2200)) / 16.0;

		this.temperature = new CoordinateDoubleCache((chunkx, chunkz) -> Math.abs(0.25 * this.temperatureVariationNoise.sample(chunkx, 0) + (chunkz + this.temperatureOffset) / EQUATOR_POLE_DIST));
		this.humidity = new CoordinateDoubleCache(this.humidityNoise::sample);
	}

	private final Noise temperatureVariationNoise, humidityNoise;
	private final double temperatureOffset;

	private final CoordinateDoubleCache temperature, humidity;

	public int getCountModifier(Surface surface) {
		if (surface instanceof SwitchableSurface) {
			return ((SwitchableSurface) surface).getSwitchCode();
		}
		return 1;
	}

	public double getTemperature(int x, int z) {
		return this.temperature.get(x >> 4, z >> 4);
	}

	public double getHumidity(int x, int z) {
		return this.humidity.get(x >> 4, z >> 4);
	}

	private static final double EQUATOR_POLE_DIST = 1200.0;
}
