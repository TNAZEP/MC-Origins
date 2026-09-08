package net.minecraft.client.renderer.color;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockShearableDoublePlant;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;

public class BlockColors {
   private final ObjectIntIdentityMap<IBlockColor> field_186725_a = new ObjectIntIdentityMap<>(32);

   public static BlockColors func_186723_a() {
      BlockColors ☃ = new BlockColors();
      ☃.func_186722_a(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null
               ? BiomeColors.func_180286_a(
                  var1, var0x.func_177229_b(BlockShearableDoublePlant.field_208063_b) == DoubleBlockHalf.UPPER ? var2.func_177977_b() : var2
               )
               : -1,
         Blocks.field_196805_gi,
         Blocks.field_196804_gh
      );
      ☃.func_186722_a(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.func_180286_a(var1, var2) : GrassColors.func_77480_a(0.5, 1.0),
         Blocks.field_196658_i,
         Blocks.field_196554_aH,
         Blocks.field_150349_c,
         Blocks.field_196683_eB
      );
      ☃.func_186722_a((var0x, var1, var2, var3) -> FoliageColors.func_77466_a(), Blocks.field_196645_X);
      ☃.func_186722_a((var0x, var1, var2, var3) -> FoliageColors.func_77469_b(), Blocks.field_196647_Y);
      ☃.func_186722_a(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.func_180287_b(var1, var2) : FoliageColors.func_77468_c(),
         Blocks.field_196642_W,
         Blocks.field_196648_Z,
         Blocks.field_196572_aa,
         Blocks.field_196574_ab,
         Blocks.field_150395_bd
      );
      ☃.func_186722_a(
         (var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.func_180288_c(var1, var2) : -1,
         Blocks.field_150355_j,
         Blocks.field_203203_C,
         Blocks.field_150383_bp
      );
      ☃.func_186722_a(
         (var0x, var1, var2, var3) -> BlockRedstoneWire.func_176337_b(var0x.func_177229_b(BlockRedstoneWire.field_176351_O)), Blocks.field_150488_af
      );
      ☃.func_186722_a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? BiomeColors.func_180286_a(var1, var2) : -1, Blocks.field_196608_cF);
      ☃.func_186722_a((var0x, var1, var2, var3) -> 14731036, Blocks.field_196713_dt, Blocks.field_196711_ds);
      ☃.func_186722_a((var0x, var1, var2, var3) -> {
         int ☃ = var0x.func_177229_b(BlockStem.field_176484_a);
         int ☃x = ☃ * 32;
         int ☃xx = 255 - ☃ * 8;
         int ☃xxx = ☃ * 4;
         return ☃x << 16 | ☃xx << 8 | ☃xxx;
      }, Blocks.field_150394_bc, Blocks.field_150393_bb);
      ☃.func_186722_a((var0x, var1, var2, var3) -> var1 != null && var2 != null ? 2129968 : 7455580, Blocks.field_196651_dG);
      return ☃;
   }

   public int func_189991_a(IBlockState var1, World var2, BlockPos var3) {
      IBlockColor ☃ = this.field_186725_a.func_148745_a(IRegistry.field_212618_g.func_148757_b(☃.func_177230_c()));
      if (☃ != null) {
         return ☃.getColor(☃, null, null, 0);
      } else {
         MaterialColor ☃ = ☃.func_185909_g(☃, ☃);
         return ☃ != null ? ☃.field_76291_p : -1;
      }
   }

   public int func_186724_a(IBlockState var1, @Nullable IWorldReaderBase var2, @Nullable BlockPos var3, int var4) {
      IBlockColor ☃ = this.field_186725_a.func_148745_a(IRegistry.field_212618_g.func_148757_b(☃.func_177230_c()));
      return ☃ == null ? -1 : ☃.getColor(☃, ☃, ☃, ☃);
   }

   public void func_186722_a(IBlockColor var1, Block... var2) {
      for(Block ☃ : ☃) {
         this.field_186725_a.func_148746_a(☃, IRegistry.field_212618_g.func_148757_b(☃));
      }
   }
}
