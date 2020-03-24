package tk.valoeghese.biomeoverhaul.populator.normal;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import tk.valoeghese.biomeoverhaul.populator.BasicTree;
import tk.valoeghese.worldcomet.api.surface.SurfaceProvider;

public class PineTree extends BasicTree {
	@Override
	protected int getCount(Random rand, SurfaceProvider surfaceProvider, int chunkX, int chunkZ) {
		double temperature = NormalPopulatorMaths.INSTANCE.getTemperature(chunkX, chunkZ);

		//if (temperature > 0.6) {
			// [0,2]
			double humidity = NormalPopulatorMaths.INSTANCE.getHumidity(chunkX, chunkZ) + 1.0;

			int countModifier = NormalPopulatorMaths.INSTANCE.getCountModifier(surfaceProvider.getSurface(chunkX << 4, chunkZ << 4, this.heightSample));
	
			// 3 * countModifier at average humidity
			return (int) (3 * countModifier * humidity);
		//}
	}

	@Override
	protected boolean generate(IWorld world, Random rand, SurfaceProvider surfaceProvider, int x, int y, int z) {
		BlockPos.Mutable pos = new BlockPos.Mutable(x, y, z);
		setBlockState(world, pos, Blocks.BEDROCK.getDefaultState());
		return true;
	}
}
