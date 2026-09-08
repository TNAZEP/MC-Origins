package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.NoteBlockInstrument;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockNote extends Block {
   public static final EnumProperty<NoteBlockInstrument> field_196483_a = BlockStateProperties.field_208143_ar;
   public static final BooleanProperty field_196484_b = BlockStateProperties.field_208194_u;
   public static final IntegerProperty field_196485_c = BlockStateProperties.field_208134_ai;

   public BlockNote(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_196483_a, NoteBlockInstrument.HARP)
            .func_206870_a(field_196485_c, Integer.valueOf(0))
            .func_206870_a(field_196484_b, Boolean.valueOf(false))
      );
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P()
         .func_206870_a(field_196483_a, NoteBlockInstrument.func_208087_a(☃.func_195991_k().func_180495_p(☃.func_195995_a().func_177977_b())));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == EnumFacing.DOWN ? ☃.func_206870_a(field_196483_a, NoteBlockInstrument.func_208087_a(☃)) : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      boolean ☃ = ☃.func_175640_z(☃);
      if (☃ != ☃.func_177229_b(field_196484_b)) {
         if (☃) {
            this.func_196482_a(☃, ☃);
         }

         ☃.func_180501_a(☃, ☃.func_206870_a(field_196484_b, Boolean.valueOf(☃)), 3);
      }
   }

   private void func_196482_a(World var1, BlockPos var2) {
      if (☃.func_180495_p(☃.func_177984_a()).func_196958_f()) {
         ☃.func_175641_c(☃, this, 0, 0);
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         ☃ = ☃.func_177231_a(field_196485_c);
         ☃.func_180501_a(☃, ☃, 3);
         this.func_196482_a(☃, ☃);
         ☃.func_195066_a(StatList.field_188087_U);
         return true;
      }
   }

   @Override
   public void func_196270_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      if (!☃.field_72995_K) {
         this.func_196482_a(☃, ☃);
         ☃.func_195066_a(StatList.field_188086_T);
      }
   }

   @Override
   public boolean func_189539_a(IBlockState var1, World var2, BlockPos var3, int var4, int var5) {
      int ☃ = ☃.func_177229_b(field_196485_c);
      float ☃x = (float)Math.pow(2.0, (double)(☃ - 12) / 12.0);
      ☃.func_184133_a(null, ☃, ((NoteBlockInstrument)☃.func_177229_b(field_196483_a)).func_208088_a(), SoundCategory.RECORDS, 3.0F, ☃x);
      ☃.func_195594_a(
         Particles.field_197597_H,
         (double)☃.func_177958_n() + 0.5,
         (double)☃.func_177956_o() + 1.2,
         (double)☃.func_177952_p() + 0.5,
         (double)☃ / 24.0,
         0.0,
         0.0
      );
      return true;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196483_a, field_196484_b, field_196485_c);
   }
}
