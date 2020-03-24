package tk.valoeghese.biomeoverhaul.worldtype;

import java.util.function.LongFunction;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import tk.valoeghese.biomeoverhaul.shape.NormalTypeHeightmap;
import tk.valoeghese.biomeoverhaul.surface.NormalSurfaces;
import tk.valoeghese.biomeoverhaul.populator.NormalPopulators;
import tk.valoeghese.biomeoverhaul.shape.CliffsDepthmapFunction;
import tk.valoeghese.worldcomet.api.WorldComet;
import tk.valoeghese.worldcomet.api.populator.FeaturePopulator;
import tk.valoeghese.worldcomet.api.populator.StructureGenSettings;
import tk.valoeghese.worldcomet.api.populator.WorldPopulator;
import tk.valoeghese.worldcomet.api.surface.fractal.FractalLongFunction;
import tk.valoeghese.worldcomet.api.surface.fractal.FractalSurfaceProvider;
import tk.valoeghese.worldcomet.api.surface.fractal.SurfaceIdMap;
import tk.valoeghese.worldcomet.api.terrain.Depthmap;
import tk.valoeghese.worldcomet.api.terrain.GeneratorSettings;
import tk.valoeghese.worldcomet.api.type.WorldCometChunkGeneratorType;
import tk.valoeghese.worldcomet.api.type.WorldType;

final class NormalWorldType {
	static final int SEA_LEVEL = 63;

	static WorldType<?> create() {
		GeneratorSettings settings = GeneratorSettings.builder()
				.seaLevel(SEA_LEVEL)
				.vanillaCarving(true)
				.build();

		LongFunction<Depthmap> shape = seed -> Depthmap.builder()
				.baseHeight(SEA_LEVEL)
				.addHeightmap(new NormalTypeHeightmap(seed))
				.addDepthmap(new CliffsDepthmapFunction(seed))
				.build();

		SurfaceIdMap surfaceIdMap = SurfaceIdMap.builder()
				.mapId(1, NormalSurfaces.SPARSE)
				.mapId(2, NormalSurfaces.LIGHT_FOREST)
				.mapId(3, NormalSurfaces.DENSE_FOREST)
				.build(NormalSurfaces.DEFAULT);

		FractalLongFunction ground = FractalLongFunction.builder((rand, x, z) -> rand.nextInt(4))
				.scale(1000L, 4)
				.build();

		FractalLongFunction ocean = FractalLongFunction.builder((rand, x, z) -> 0)
				.build();

		LongFunction<FractalSurfaceProvider> surfaceProviderFactory = FractalSurfaceProvider.factoryBuilder(ground)
				.addFactory(0, ocean)
				.buildFactory(h -> h > SEA_LEVEL ? 1 : 0, surfaceIdMap);

		WorldPopulator populator = WorldPopulator.builder()
				.addPopulator(FeaturePopulator.STRONGHOLD)
				.enableStructure(Feature.STRONGHOLD, StructureGenSettings.vanillaSettings())
				.addPopulator(NormalPopulators.PINE_TREE)
				.build();

		WorldCometChunkGeneratorType<?> cgt = WorldComet.createChunkGeneratorType(settings, shape, surfaceProviderFactory, populator);
		return WorldComet.createWorldType("tbo_normal", cgt, ImmutableSet.of(Biomes.PLAINS, Biomes.OCEAN));
	}
}
