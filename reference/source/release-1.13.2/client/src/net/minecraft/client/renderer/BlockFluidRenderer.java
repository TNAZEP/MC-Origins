package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.biome.BiomeColors;

public class BlockFluidRenderer {
   private final TextureAtlasSprite[] field_178272_a = new TextureAtlasSprite[2];
   private final TextureAtlasSprite[] field_178271_b = new TextureAtlasSprite[2];
   private TextureAtlasSprite field_187501_d;

   public BlockFluidRenderer() {
      this.func_178268_a();
   }

   protected void func_178268_a() {
      TextureMap ☃ = Minecraft.func_71410_x().func_147117_R();
      this.field_178272_a[0] = Minecraft.func_71410_x().func_209506_al().func_174954_c().func_178125_b(Blocks.field_150353_l.func_176223_P()).func_177554_e();
      this.field_178272_a[1] = ☃.func_195424_a(ModelBakery.field_207766_d);
      this.field_178271_b[0] = Minecraft.func_71410_x().func_209506_al().func_174954_c().func_178125_b(Blocks.field_150355_j.func_176223_P()).func_177554_e();
      this.field_178271_b[1] = ☃.func_195424_a(ModelBakery.field_207768_f);
      this.field_187501_d = ☃.func_195424_a(ModelBakery.field_207769_g);
   }

   private static boolean func_209557_a(IBlockReader var0, BlockPos var1, EnumFacing var2, IFluidState var3) {
      BlockPos ☃ = ☃.func_177972_a(☃);
      IFluidState ☃x = ☃.func_204610_c(☃);
      return ☃x.func_206886_c().func_207187_a(☃.func_206886_c());
   }

   private static boolean func_209556_a(IBlockReader var0, BlockPos var1, EnumFacing var2, float var3) {
      BlockPos ☃ = ☃.func_177972_a(☃);
      IBlockState ☃x = ☃.func_180495_p(☃);
      if (☃x.func_200132_m()) {
         VoxelShape ☃xx = VoxelShapes.func_197873_a(0.0, 0.0, 0.0, 1.0, (double)☃, 1.0);
         VoxelShape ☃xxx = ☃x.func_196951_e(☃, ☃);
         return VoxelShapes.func_197875_a(☃xx, ☃xxx, ☃);
      } else {
         return false;
      }
   }

   public boolean func_205346_a(IWorldReader var1, BlockPos var2, BufferBuilder var3, IFluidState var4) {
      boolean ☃ = ☃.func_206884_a(FluidTags.field_206960_b);
      TextureAtlasSprite[] ☃x = ☃ ? this.field_178272_a : this.field_178271_b;
      int ☃xx = ☃ ? 16777215 : BiomeColors.func_180288_c(☃, ☃);
      float ☃xxx = (float)(☃xx >> 16 & 0xFF) / 255.0F;
      float ☃xxxx = (float)(☃xx >> 8 & 0xFF) / 255.0F;
      float ☃xxxxx = (float)(☃xx & 0xFF) / 255.0F;
      boolean ☃xxxxxx = !func_209557_a(☃, ☃, EnumFacing.UP, ☃);
      boolean ☃xxxxxxx = !func_209557_a(☃, ☃, EnumFacing.DOWN, ☃) && !func_209556_a(☃, ☃, EnumFacing.DOWN, 0.8888889F);
      boolean ☃xxxxxxxx = !func_209557_a(☃, ☃, EnumFacing.NORTH, ☃);
      boolean ☃xxxxxxxxx = !func_209557_a(☃, ☃, EnumFacing.SOUTH, ☃);
      boolean ☃xxxxxxxxxx = !func_209557_a(☃, ☃, EnumFacing.WEST, ☃);
      boolean ☃xxxxxxxxxxx = !func_209557_a(☃, ☃, EnumFacing.EAST, ☃);
      if (!☃xxxxxx && !☃xxxxxxx && !☃xxxxxxxxxxx && !☃xxxxxxxxxx && !☃xxxxxxxx && !☃xxxxxxxxx) {
         return false;
      } else {
         boolean ☃ = false;
         float ☃x = 0.5F;
         float ☃xx = 1.0F;
         float ☃xxx = 0.8F;
         float ☃xxxx = 0.6F;
         float ☃xxxxx = this.func_204504_a(☃, ☃, ☃.func_206886_c());
         float ☃xxxxxx = this.func_204504_a(☃, ☃.func_177968_d(), ☃.func_206886_c());
         float ☃xxxxxxx = this.func_204504_a(☃, ☃.func_177974_f().func_177968_d(), ☃.func_206886_c());
         float ☃xxxxxxxx = this.func_204504_a(☃, ☃.func_177974_f(), ☃.func_206886_c());
         double ☃xxxxxxxxx = (double)☃.func_177958_n();
         double ☃xxxxxxxxxx = (double)☃.func_177956_o();
         double ☃xxxxxxxxxxx = (double)☃.func_177952_p();
         float ☃xxxxxxxxxxxx = 0.001F;
         if (☃xxxxxx && !func_209556_a(☃, ☃, EnumFacing.UP, Math.min(Math.min(☃xxxxx, ☃xxxxxx), Math.min(☃xxxxxxx, ☃xxxxxxxx)))) {
            ☃ = true;
            ☃xxxxx -= 0.001F;
            ☃xxxxxx -= 0.001F;
            ☃xxxxxxx -= 0.001F;
            ☃xxxxxxxx -= 0.001F;
            Vec3d ☃xxxxxxxxxxxxxxxxxxxxx = ☃.func_206887_a(☃, ☃);
            float ☃xxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxxxxx;
            float ☃xxxxxxxxxxxxxxxxxxxx;
            if (☃xxxxxxxxxxxxxxxxxxxxx.field_72450_a == 0.0 && ☃xxxxxxxxxxxxxxxxxxxxx.field_72449_c == 0.0) {
               TextureAtlasSprite ☃xxxxxxxxxxxxxxxxxxxxxx = ☃x[0];
               ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxx.func_94214_a(0.0);
               ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxx.func_94207_b(0.0);
               ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx;
               ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxx.func_94207_b(16.0);
               ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxx.func_94214_a(16.0);
               ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxx;
               ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx;
               ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx;
            } else {
               TextureAtlasSprite ☃xxxxxxxxxxxxx = ☃x[1];
               float ☃xxxxxxxxxxxxxx = (float)MathHelper.func_181159_b(☃xxxxxxxxxxxxxxxxxxxxx.field_72449_c, ☃xxxxxxxxxxxxxxxxxxxxx.field_72450_a)
                  - (float) (Math.PI / 2);
               float ☃xxxxxxxxxxxxxxx = MathHelper.func_76126_a(☃xxxxxxxxxxxxxx) * 0.25F;
               float ☃xxxxxxxxxxxxxxxx = MathHelper.func_76134_b(☃xxxxxxxxxxxxxx) * 0.25F;
               float ☃xxxxxxxxxxxxxxxxx = 8.0F;
               ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94214_a((double)(8.0F + (-☃xxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94207_b((double)(8.0F + (-☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94214_a((double)(8.0F + (-☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94207_b((double)(8.0F + (☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94214_a((double)(8.0F + (☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94207_b((double)(8.0F + (☃xxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94214_a((double)(8.0F + (☃xxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxx) * 16.0F));
               ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_94207_b((double)(8.0F + (-☃xxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxx) * 16.0F));
            }

            int ☃xxxxxxxxxxxxx = this.func_204835_a(☃, ☃);
            int ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx >> 16 & 65535;
            int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx & 65535;
            float ☃xxxxxxxxxxxxxxxx = 1.0F * ☃xxx;
            float ☃xxxxxxxxxxxxxxxxx = 1.0F * ☃xxxx;
            float ☃xxxxxxxxxxxxxxxxxx = 1.0F * ☃xxxxx;
            ☃.func_181662_b(☃xxxxxxxxx + 0.0, ☃xxxxxxxxxx + (double)☃xxxxx, ☃xxxxxxxxxxx + 0.0)
               .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
               .func_187315_a((double)☃xxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxx)
               .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
               .func_181675_d();
            ☃.func_181662_b(☃xxxxxxxxx + 0.0, ☃xxxxxxxxxx + (double)☃xxxxxx, ☃xxxxxxxxxxx + 1.0)
               .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
               .func_187315_a((double)☃xxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxx)
               .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
               .func_181675_d();
            ☃.func_181662_b(☃xxxxxxxxx + 1.0, ☃xxxxxxxxxx + (double)☃xxxxxxx, ☃xxxxxxxxxxx + 1.0)
               .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
               .func_187315_a((double)☃xxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx)
               .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
               .func_181675_d();
            ☃.func_181662_b(☃xxxxxxxxx + 1.0, ☃xxxxxxxxxx + (double)☃xxxxxxxx, ☃xxxxxxxxxxx + 0.0)
               .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
               .func_187315_a((double)☃xxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx)
               .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
               .func_181675_d();
            if (☃.func_205586_a(☃, ☃.func_177984_a())) {
               ☃.func_181662_b(☃xxxxxxxxx + 0.0, ☃xxxxxxxxxx + (double)☃xxxxx, ☃xxxxxxxxxxx + 0.0)
                  .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃xxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxx)
                  .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
                  .func_181675_d();
               ☃.func_181662_b(☃xxxxxxxxx + 1.0, ☃xxxxxxxxxx + (double)☃xxxxxxxx, ☃xxxxxxxxxxx + 0.0)
                  .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃xxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxx)
                  .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
                  .func_181675_d();
               ☃.func_181662_b(☃xxxxxxxxx + 1.0, ☃xxxxxxxxxx + (double)☃xxxxxxx, ☃xxxxxxxxxxx + 1.0)
                  .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃xxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxx)
                  .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
                  .func_181675_d();
               ☃.func_181662_b(☃xxxxxxxxx + 0.0, ☃xxxxxxxxxx + (double)☃xxxxxx, ☃xxxxxxxxxxx + 1.0)
                  .func_181666_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃xxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxx)
                  .func_187314_a(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx)
                  .func_181675_d();
            }
         }

         if (☃xxxxxxx) {
            float ☃ = ☃x[0].func_94209_e();
            float ☃x = ☃x[0].func_94212_f();
            float ☃xx = ☃x[0].func_94206_g();
            float ☃xxx = ☃x[0].func_94210_h();
            int ☃xxxx = this.func_204835_a(☃, ☃.func_177977_b());
            int ☃xxxxx = ☃xxxx >> 16 & 65535;
            int ☃xxxxxx = ☃xxxx & 65535;
            float ☃xxxxxxx = 0.5F * ☃xxx;
            float ☃xxxxxxxx = 0.5F * ☃xxxx;
            float ☃xxxxxxxxx = 0.5F * ☃xxxxx;
            ☃.func_181662_b(☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx + 1.0)
               .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 1.0F)
               .func_187315_a((double)☃, (double)☃xxx)
               .func_187314_a(☃xxxxx, ☃xxxxxx)
               .func_181675_d();
            ☃.func_181662_b(☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx)
               .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 1.0F)
               .func_187315_a((double)☃, (double)☃xx)
               .func_187314_a(☃xxxxx, ☃xxxxxx)
               .func_181675_d();
            ☃.func_181662_b(☃xxxxxxxxx + 1.0, ☃xxxxxxxxxx, ☃xxxxxxxxxxx)
               .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 1.0F)
               .func_187315_a((double)☃x, (double)☃xx)
               .func_187314_a(☃xxxxx, ☃xxxxxx)
               .func_181675_d();
            ☃.func_181662_b(☃xxxxxxxxx + 1.0, ☃xxxxxxxxxx, ☃xxxxxxxxxxx + 1.0)
               .func_181666_a(☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, 1.0F)
               .func_187315_a((double)☃x, (double)☃xxx)
               .func_187314_a(☃xxxxx, ☃xxxxxx)
               .func_181675_d();
            ☃ = true;
         }

         for(int ☃ = 0; ☃ < 4; ++☃) {
            float ☃x;
            float ☃xx;
            double ☃xxx;
            double ☃xxxx;
            double ☃xxxxx;
            double ☃xxxxxx;
            EnumFacing ☃xxxxxxx;
            boolean ☃xxxxxxxx;
            if (☃ == 0) {
               ☃x = ☃xxxxx;
               ☃xx = ☃xxxxxxxx;
               ☃xxx = ☃xxxxxxxxx;
               ☃xxxxx = ☃xxxxxxxxx + 1.0;
               ☃xxxx = ☃xxxxxxxxxxx + 0.001F;
               ☃xxxxxx = ☃xxxxxxxxxxx + 0.001F;
               ☃xxxxxxx = EnumFacing.NORTH;
               ☃xxxxxxxx = ☃xxxxxxxx;
            } else if (☃ == 1) {
               ☃x = ☃xxxxxxx;
               ☃xx = ☃xxxxxx;
               ☃xxx = ☃xxxxxxxxx + 1.0;
               ☃xxxxx = ☃xxxxxxxxx;
               ☃xxxx = ☃xxxxxxxxxxx + 1.0 - 0.001F;
               ☃xxxxxx = ☃xxxxxxxxxxx + 1.0 - 0.001F;
               ☃xxxxxxx = EnumFacing.SOUTH;
               ☃xxxxxxxx = ☃xxxxxxxxx;
            } else if (☃ == 2) {
               ☃x = ☃xxxxxx;
               ☃xx = ☃xxxxx;
               ☃xxx = ☃xxxxxxxxx + 0.001F;
               ☃xxxxx = ☃xxxxxxxxx + 0.001F;
               ☃xxxx = ☃xxxxxxxxxxx + 1.0;
               ☃xxxxxx = ☃xxxxxxxxxxx;
               ☃xxxxxxx = EnumFacing.WEST;
               ☃xxxxxxxx = ☃xxxxxxxxxx;
            } else {
               ☃x = ☃xxxxxxxx;
               ☃xx = ☃xxxxxxx;
               ☃xxx = ☃xxxxxxxxx + 1.0 - 0.001F;
               ☃xxxxx = ☃xxxxxxxxx + 1.0 - 0.001F;
               ☃xxxx = ☃xxxxxxxxxxx;
               ☃xxxxxx = ☃xxxxxxxxxxx + 1.0;
               ☃xxxxxxx = EnumFacing.EAST;
               ☃xxxxxxxx = ☃xxxxxxxxxxx;
            }

            if (☃xxxxxxxx && !func_209556_a(☃, ☃, ☃xxxxxxx, Math.max(☃x, ☃xx))) {
               ☃ = true;
               BlockPos ☃x = ☃.func_177972_a(☃xxxxxxx);
               TextureAtlasSprite ☃xx = ☃x[1];
               if (!☃) {
                  Block ☃xxx = ☃.func_180495_p(☃x).func_177230_c();
                  if (☃xxx == Blocks.field_150359_w || ☃xxx instanceof BlockStainedGlass) {
                     ☃xx = this.field_187501_d;
                  }
               }

               float ☃x = ☃xx.func_94214_a(0.0);
               float ☃xx = ☃xx.func_94214_a(8.0);
               float ☃xxx = ☃xx.func_94207_b((double)((1.0F - ☃x) * 16.0F * 0.5F));
               float ☃xxxx = ☃xx.func_94207_b((double)((1.0F - ☃xx) * 16.0F * 0.5F));
               float ☃xxxxx = ☃xx.func_94207_b(8.0);
               int ☃xxxxxx = this.func_204835_a(☃, ☃x);
               int ☃xxxxxxx = ☃xxxxxx >> 16 & 65535;
               int ☃xxxxxxxx = ☃xxxxxx & 65535;
               float ☃xxxxxxxxx = ☃ < 2 ? 0.8F : 0.6F;
               float ☃xxxxxxxxxx = 1.0F * ☃xxxxxxxxx * ☃xxx;
               float ☃xxxxxxxxxxx = 1.0F * ☃xxxxxxxxx * ☃xxxx;
               float ☃xxxxxxxxxxxx = 1.0F * ☃xxxxxxxxx * ☃xxxxx;
               ☃.func_181662_b(☃xxx, ☃xxxxxxxxxx + (double)☃x, ☃xxxx)
                  .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃x, (double)☃xxx)
                  .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                  .func_181675_d();
               ☃.func_181662_b(☃xxxxx, ☃xxxxxxxxxx + (double)☃xx, ☃xxxxxx)
                  .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃xx, (double)☃xxxx)
                  .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                  .func_181675_d();
               ☃.func_181662_b(☃xxxxx, ☃xxxxxxxxxx + 0.0, ☃xxxxxx)
                  .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃xx, (double)☃xxxxx)
                  .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                  .func_181675_d();
               ☃.func_181662_b(☃xxx, ☃xxxxxxxxxx + 0.0, ☃xxxx)
                  .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                  .func_187315_a((double)☃x, (double)☃xxxxx)
                  .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                  .func_181675_d();
               if (☃xx != this.field_187501_d) {
                  ☃.func_181662_b(☃xxx, ☃xxxxxxxxxx + 0.0, ☃xxxx)
                     .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                     .func_187315_a((double)☃x, (double)☃xxxxx)
                     .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                     .func_181675_d();
                  ☃.func_181662_b(☃xxxxx, ☃xxxxxxxxxx + 0.0, ☃xxxxxx)
                     .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                     .func_187315_a((double)☃xx, (double)☃xxxxx)
                     .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                     .func_181675_d();
                  ☃.func_181662_b(☃xxxxx, ☃xxxxxxxxxx + (double)☃xx, ☃xxxxxx)
                     .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                     .func_187315_a((double)☃xx, (double)☃xxxx)
                     .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                     .func_181675_d();
                  ☃.func_181662_b(☃xxx, ☃xxxxxxxxxx + (double)☃x, ☃xxxx)
                     .func_181666_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, 1.0F)
                     .func_187315_a((double)☃x, (double)☃xxx)
                     .func_187314_a(☃xxxxxxx, ☃xxxxxxxx)
                     .func_181675_d();
               }
            }
         }

         return ☃;
      }
   }

   private int func_204835_a(IWorldReader var1, BlockPos var2) {
      int ☃ = ☃.func_175626_b(☃, 0);
      int ☃x = ☃.func_175626_b(☃.func_177984_a(), 0);
      int ☃xx = ☃ & 0xFF;
      int ☃xxx = ☃x & 0xFF;
      int ☃xxxx = ☃ >> 16 & 0xFF;
      int ☃xxxxx = ☃x >> 16 & 0xFF;
      return (☃xx > ☃xxx ? ☃xx : ☃xxx) | (☃xxxx > ☃xxxxx ? ☃xxxx : ☃xxxxx) << 16;
   }

   private float func_204504_a(IWorldReaderBase var1, BlockPos var2, Fluid var3) {
      int ☃ = 0;
      float ☃x = 0.0F;

      for(int ☃xx = 0; ☃xx < 4; ++☃xx) {
         BlockPos ☃xxx = ☃.func_177982_a(-(☃xx & 1), 0, -(☃xx >> 1 & 1));
         if (☃.func_204610_c(☃xxx.func_177984_a()).func_206886_c().func_207187_a(☃)) {
            return 1.0F;
         }

         IFluidState ☃xxx = ☃.func_204610_c(☃xxx);
         if (☃xxx.func_206886_c().func_207187_a(☃)) {
            if (☃xxx.func_206885_f() >= 0.8F) {
               ☃x += ☃xxx.func_206885_f() * 10.0F;
               ☃ += 10;
            } else {
               ☃x += ☃xxx.func_206885_f();
               ++☃;
            }
         } else if (!☃.func_180495_p(☃xxx).func_185904_a().func_76220_a()) {
            ++☃;
         }
      }

      return ☃x / (float)☃;
   }
}
