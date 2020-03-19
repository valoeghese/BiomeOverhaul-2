package tk.valoeghese.biomeoverhaul.shape;

import java.util.Random;

import tk.valoeghese.worldcomet.api.noise.Noise;
import tk.valoeghese.worldcomet.api.noise.OctaveOpenSimplexNoise;
import tk.valoeghese.worldcomet.api.terrain.function.DepthmapFunction;

public class OverhangDepthmapFunction implements DepthmapFunction {
	public OverhangDepthmapFunction(long seed) {
		this.noise = new OctaveOpenSimplexNoise(new Random(seed + 6), 2, 40).stretch3DY(5.0 / 40.0);
	}

	final Noise noise;

	@Override
	public double getHeight(int x, int y, int z) {
		return this.noise.sample(x, y, z) > 0.2D ? 5 : 0;
	}
}
