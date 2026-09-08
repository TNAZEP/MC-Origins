package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSilverfish extends Block {
   private final Block field_196469_a;
   private static final Map<Block, Block> field_196470_b = Maps.<Block, Block>newIdentityHashMap();

   public BlockSilverfish(Block var1, Block.Properties var2) {
      super(☃);
      this.field_196469_a = ☃;
      field_196470_b.put(☃, this);
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   public Block func_196468_d() {
      return this.field_196469_a;
   }

   public static boolean func_196466_i(IBlockState var0) {
      return field_196470_b.containsKey(☃.func_177230_c());
   }

   @Override
   protected ItemStack func_180643_i(IBlockState var1) {
      return new ItemStack(this.field_196469_a);
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      if (!☃.field_72995_K && ☃.func_82736_K().func_82766_b("doTileDrops")) {
         EntitySilverfish ☃ = new EntitySilverfish(☃);
         ☃.func_70012_b((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o(), (double)☃.func_177952_p() + 0.5, 0.0F, 0.0F);
         ☃.func_72838_d(☃);
         ☃.func_70656_aK();
      }
   }

   public static IBlockState func_196467_h(Block var0) {
      return ((Block)field_196470_b.get(☃)).func_176223_P();
   }
}
