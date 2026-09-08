package net.minecraft.block;

import com.google.common.cache.LoadingCache;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockPortal extends Block {
   public static final EnumProperty<EnumFacing.Axis> field_176550_a = BlockStateProperties.field_208199_z;
   protected static final VoxelShape field_185683_b = Block.func_208617_a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape field_185684_c = Block.func_208617_a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

   public BlockPortal(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176550_a, EnumFacing.Axis.X));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch((EnumFacing.Axis)☃.func_177229_b(field_176550_a)) {
         case Z:
            return field_185684_c;
         case X:
         default:
            return field_185683_b;
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.field_73011_w.func_76569_d() && ☃.func_82736_K().func_82766_b("doMobSpawning") && ☃.nextInt(2000) < ☃.func_175659_aa().func_151525_a()) {
         int ☃ = ☃.func_177956_o();
         BlockPos ☃x = ☃;

         while(!☃.func_180495_p(☃x).func_185896_q() && ☃x.func_177956_o() > 0) {
            ☃x = ☃x.func_177977_b();
         }

         if (☃ > 0 && !☃.func_180495_p(☃x.func_177984_a()).func_185915_l()) {
            Entity ☃xx = EntityType.field_200785_Y.func_208050_a(☃, null, null, null, ☃x.func_177984_a(), false, false);
            if (☃xx != null) {
               ☃xx.field_71088_bW = ☃xx.func_82147_ab();
            }
         }
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   public boolean func_176548_d(IWorld var1, BlockPos var2) {
      BlockPortal.Size ☃ = this.func_201816_b(☃, ☃);
      if (☃ != null) {
         ☃.func_150859_c();
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public BlockPortal.Size func_201816_b(IWorld var1, BlockPos var2) {
      BlockPortal.Size ☃ = new BlockPortal.Size(☃, ☃, EnumFacing.Axis.X);
      if (☃.func_150860_b() && ☃.field_150864_e == 0) {
         return ☃;
      } else {
         BlockPortal.Size ☃ = new BlockPortal.Size(☃, ☃, EnumFacing.Axis.Z);
         return ☃.func_150860_b() && ☃.field_150864_e == 0 ? ☃ : null;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      EnumFacing.Axis ☃ = ☃.func_176740_k();
      EnumFacing.Axis ☃x = ☃.func_177229_b(field_176550_a);
      boolean ☃xx = ☃x != ☃ && ☃.func_176722_c();
      return !☃xx && ☃.func_177230_c() != this && !new BlockPortal.Size(☃, ☃, ☃x).func_208508_f()
         ? Blocks.field_150350_a.func_176223_P()
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.func_184218_aH() && !☃.func_184207_aI() && ☃.func_184222_aU()) {
         ☃.func_181015_d(☃);
      }
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.nextInt(100) == 0) {
         ☃.func_184134_a(
            (double)☃.func_177958_n() + 0.5,
            (double)☃.func_177956_o() + 0.5,
            (double)☃.func_177952_p() + 0.5,
            SoundEvents.field_187810_eg,
            SoundCategory.BLOCKS,
            0.5F,
            ☃.nextFloat() * 0.4F + 0.8F,
            false
         );
      }

      for(int ☃ = 0; ☃ < 4; ++☃) {
         double ☃x = (double)((float)☃.func_177958_n() + ☃.nextFloat());
         double ☃xx = (double)((float)☃.func_177956_o() + ☃.nextFloat());
         double ☃xxx = (double)((float)☃.func_177952_p() + ☃.nextFloat());
         double ☃xxxx = ((double)☃.nextFloat() - 0.5) * 0.5;
         double ☃xxxxx = ((double)☃.nextFloat() - 0.5) * 0.5;
         double ☃xxxxxx = ((double)☃.nextFloat() - 0.5) * 0.5;
         int ☃xxxxxxx = ☃.nextInt(2) * 2 - 1;
         if (☃.func_180495_p(☃.func_177976_e()).func_177230_c() != this && ☃.func_180495_p(☃.func_177974_f()).func_177230_c() != this) {
            ☃x = (double)☃.func_177958_n() + 0.5 + 0.25 * (double)☃xxxxxxx;
            ☃xxxx = (double)(☃.nextFloat() * 2.0F * (float)☃xxxxxxx);
         } else {
            ☃xxx = (double)☃.func_177952_p() + 0.5 + 0.25 * (double)☃xxxxxxx;
            ☃xxxxxx = (double)(☃.nextFloat() * 2.0F * (float)☃xxxxxxx);
         }

         ☃.func_195594_a(Particles.field_197599_J, ☃x, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx);
      }
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return ItemStack.field_190927_a;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch((EnumFacing.Axis)☃.func_177229_b(field_176550_a)) {
               case Z:
                  return ☃.func_206870_a(field_176550_a, EnumFacing.Axis.X);
               case X:
                  return ☃.func_206870_a(field_176550_a, EnumFacing.Axis.Z);
               default:
                  return ☃;
            }
         default:
            return ☃;
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176550_a);
   }

   public BlockPattern.PatternHelper func_181089_f(IWorld var1, BlockPos var2) {
      EnumFacing.Axis ☃ = EnumFacing.Axis.Z;
      BlockPortal.Size ☃x = new BlockPortal.Size(☃, ☃, EnumFacing.Axis.X);
      LoadingCache<BlockPos, BlockWorldState> ☃xx = BlockPattern.func_181627_a(☃, true);
      if (!☃x.func_150860_b()) {
         ☃ = EnumFacing.Axis.X;
         ☃x = new BlockPortal.Size(☃, ☃, EnumFacing.Axis.Z);
      }

      if (!☃x.func_150860_b()) {
         return new BlockPattern.PatternHelper(☃, EnumFacing.NORTH, EnumFacing.UP, ☃xx, 1, 1, 1);
      } else {
         int[] ☃ = new int[EnumFacing.AxisDirection.values().length];
         EnumFacing ☃x = ☃x.field_150866_c.func_176735_f();
         BlockPos ☃xx = ☃x.field_150861_f.func_177981_b(☃x.func_181100_a() - 1);

         for(EnumFacing.AxisDirection ☃xxx : EnumFacing.AxisDirection.values()) {
            BlockPattern.PatternHelper ☃xxxx = new BlockPattern.PatternHelper(
               ☃x.func_176743_c() == ☃xxx ? ☃xx : ☃xx.func_177967_a(☃x.field_150866_c, ☃x.func_181101_b() - 1),
               EnumFacing.func_181076_a(☃xxx, ☃),
               EnumFacing.UP,
               ☃xx,
               ☃x.func_181101_b(),
               ☃x.func_181100_a(),
               1
            );

            for(int ☃xxxxx = 0; ☃xxxxx < ☃x.func_181101_b(); ++☃xxxxx) {
               for(int ☃xxxxxx = 0; ☃xxxxxx < ☃x.func_181100_a(); ++☃xxxxxx) {
                  BlockWorldState ☃xxxxxxx = ☃xxxx.func_177670_a(☃xxxxx, ☃xxxxxx, 1);
                  if (!☃xxxxxxx.func_177509_a().func_196958_f()) {
                     ☃[☃xxx.ordinal()]++;
                  }
               }
            }
         }

         EnumFacing.AxisDirection ☃xxx = EnumFacing.AxisDirection.POSITIVE;

         for(EnumFacing.AxisDirection ☃xxxx : EnumFacing.AxisDirection.values()) {
            if (☃[☃xxxx.ordinal()] < ☃[☃xxx.ordinal()]) {
               ☃xxx = ☃xxxx;
            }
         }

         return new BlockPattern.PatternHelper(
            ☃x.func_176743_c() == ☃xxx ? ☃xx : ☃xx.func_177967_a(☃x.field_150866_c, ☃x.func_181101_b() - 1),
            EnumFacing.func_181076_a(☃xxx, ☃),
            EnumFacing.UP,
            ☃xx,
            ☃x.func_181101_b(),
            ☃x.func_181100_a(),
            1
         );
      }
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   public static class Size {
      private final IWorld field_150867_a;
      private final EnumFacing.Axis field_150865_b;
      private final EnumFacing field_150866_c;
      private final EnumFacing field_150863_d;
      private int field_150864_e;
      private BlockPos field_150861_f;
      private int field_150862_g;
      private int field_150868_h;

      public Size(IWorld var1, BlockPos var2, EnumFacing.Axis var3) {
         this.field_150867_a = ☃;
         this.field_150865_b = ☃;
         if (☃ == EnumFacing.Axis.X) {
            this.field_150863_d = EnumFacing.EAST;
            this.field_150866_c = EnumFacing.WEST;
         } else {
            this.field_150863_d = EnumFacing.NORTH;
            this.field_150866_c = EnumFacing.SOUTH;
         }

         BlockPos ☃ = ☃;

         while(☃.func_177956_o() > ☃.func_177956_o() - 21 && ☃.func_177956_o() > 0 && this.func_196900_a(☃.func_180495_p(☃.func_177977_b()))) {
            ☃ = ☃.func_177977_b();
         }

         int ☃x = this.func_180120_a(☃, this.field_150863_d) - 1;
         if (☃x >= 0) {
            this.field_150861_f = ☃.func_177967_a(this.field_150863_d, ☃x);
            this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
            if (this.field_150868_h < 2 || this.field_150868_h > 21) {
               this.field_150861_f = null;
               this.field_150868_h = 0;
            }
         }

         if (this.field_150861_f != null) {
            this.field_150862_g = this.func_150858_a();
         }
      }

      protected int func_180120_a(BlockPos var1, EnumFacing var2) {
         int ☃;
         for(☃ = 0; ☃ < 22; ++☃) {
            BlockPos ☃ = ☃.func_177967_a(☃, ☃);
            if (!this.func_196900_a(this.field_150867_a.func_180495_p(☃))
               || this.field_150867_a.func_180495_p(☃.func_177977_b()).func_177230_c() != Blocks.field_150343_Z) {
               break;
            }
         }

         Block ☃ = this.field_150867_a.func_180495_p(☃.func_177967_a(☃, ☃)).func_177230_c();
         return ☃ == Blocks.field_150343_Z ? ☃ : 0;
      }

      public int func_181100_a() {
         return this.field_150862_g;
      }

      public int func_181101_b() {
         return this.field_150868_h;
      }

      protected int func_150858_a() {
         label56:
         for(this.field_150862_g = 0; this.field_150862_g < 21; ++this.field_150862_g) {
            for(int ☃ = 0; ☃ < this.field_150868_h; ++☃) {
               BlockPos ☃x = this.field_150861_f.func_177967_a(this.field_150866_c, ☃).func_177981_b(this.field_150862_g);
               IBlockState ☃xx = this.field_150867_a.func_180495_p(☃x);
               if (!this.func_196900_a(☃xx)) {
                  break label56;
               }

               Block ☃x = ☃xx.func_177230_c();
               if (☃x == Blocks.field_150427_aO) {
                  ++this.field_150864_e;
               }

               if (☃ == 0) {
                  ☃x = this.field_150867_a.func_180495_p(☃x.func_177972_a(this.field_150863_d)).func_177230_c();
                  if (☃x != Blocks.field_150343_Z) {
                     break label56;
                  }
               } else if (☃ == this.field_150868_h - 1) {
                  ☃x = this.field_150867_a.func_180495_p(☃x.func_177972_a(this.field_150866_c)).func_177230_c();
                  if (☃x != Blocks.field_150343_Z) {
                     break label56;
                  }
               }
            }
         }

         for(int ☃ = 0; ☃ < this.field_150868_h; ++☃) {
            if (this.field_150867_a.func_180495_p(this.field_150861_f.func_177967_a(this.field_150866_c, ☃).func_177981_b(this.field_150862_g)).func_177230_c()
               != Blocks.field_150343_Z) {
               this.field_150862_g = 0;
               break;
            }
         }

         if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
            return this.field_150862_g;
         } else {
            this.field_150861_f = null;
            this.field_150868_h = 0;
            this.field_150862_g = 0;
            return 0;
         }
      }

      protected boolean func_196900_a(IBlockState var1) {
         Block ☃ = ☃.func_177230_c();
         return ☃.func_196958_f() || ☃ == Blocks.field_150480_ab || ☃ == Blocks.field_150427_aO;
      }

      public boolean func_150860_b() {
         return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
      }

      public void func_150859_c() {
         for(int ☃ = 0; ☃ < this.field_150868_h; ++☃) {
            BlockPos ☃x = this.field_150861_f.func_177967_a(this.field_150866_c, ☃);

            for(int ☃xx = 0; ☃xx < this.field_150862_g; ++☃xx) {
               this.field_150867_a
                  .func_180501_a(
                     ☃x.func_177981_b(☃xx), Blocks.field_150427_aO.func_176223_P().func_206870_a(BlockPortal.field_176550_a, this.field_150865_b), 18
                  );
            }
         }
      }

      private boolean func_196899_f() {
         return this.field_150864_e >= this.field_150868_h * this.field_150862_g;
      }

      public boolean func_208508_f() {
         return this.func_150860_b() && this.func_196899_f();
      }
   }
}
