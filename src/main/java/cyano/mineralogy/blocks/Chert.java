package cyano.mineralogy.blocks;

import cyano.mineralogy.Mineralogy;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Chert extends Rock {

	private final Random prng = new Random();
	private final String itemName = "chert";
	public Chert(){
		super(false, (float)1.5, (float)10, 1, SoundType.STONE);
		this.setUnlocalizedName(Mineralogy.MODID + "_" + itemName);
		this.setCreativeTab(Mineralogy.mineralogyTab);
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		if(prng.nextInt(10) == 0) {
			return Arrays.asList(new ItemStack(Items.FLINT, 1 + Math.max(0, fortune)));
		} else {
			return super.getDrops(world, pos, state, fortune);
		}
	}
}
