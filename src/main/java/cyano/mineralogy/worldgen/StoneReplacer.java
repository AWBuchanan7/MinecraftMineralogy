package cyano.mineralogy.worldgen;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cyano.mineralogy.Mineralogy;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class StoneReplacer implements IWorldGenerator {

	private Geology geom = null;

	public StoneReplacer() {
		//
	}

	private final Lock glock = new ReentrantLock();

	/** is thread-safe */
	final Geology getGeology(World w) {
		if (geom == null) {
			glock.lock();
			try {
				if (geom == null) {
					geom = new Geology(w.getSeed(), Mineralogy.GEOME_SIZE, Mineralogy.ROCK_LAYER_NOISE);
				}
			} finally {
				glock.unlock();
			}
		}
		return geom;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			getGeology(world).replaceStoneInChunk(chunkX, chunkZ, world);
		}
	}

}
