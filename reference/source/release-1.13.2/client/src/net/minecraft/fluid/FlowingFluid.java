package net.minecraft.fluid;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class FlowingFluid extends Fluid {
   public static final BooleanProperty field_207209_a = BlockStateProperties.field_208183_j;
   public static final IntegerProperty field_207210_b = BlockStateProperties.field_208131_af;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey>> field_212756_e = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey> ☃ = new Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey>(200) {
         @Override
         protected void rehash(int var1) {
         }
      };
      ☃.defaultReturnValue((byte)127);
      return ☃;
   });

   @Override
   protected void func_207184_a(StateContainer.Builder<Fluid, IFluidState> var1) {
      ☃.func_206894_a(field_207209_a);
   }

   @Override
   public Vec3d func_205564_a(IWorldReaderBase var1, BlockPos var2, IFluidState var3) {
      double ☃ = 0.0;
      double ☃x = 0.0;

      Vec3d var27;
      try (BlockPos.PooledMutableBlockPos ☃xx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(EnumFacing ☃xxx : EnumFacing.Plane.HORIZONTAL) {
            ☃xx.func_189533_g(☃).func_189536_c(☃xxx);
            IFluidState ☃xxxx = ☃.func_204610_c(☃xx);
            if (this.func_212189_g(☃xxxx)) {
               float ☃xxxxx = ☃xxxx.func_206885_f();
               float ☃xxxxxx = 0.0F;
               if (☃xxxxx == 0.0F) {
                  if (!☃.func_180495_p(☃xx).func_185904_a().func_76230_c()) {
                     IFluidState ☃xxxxxxx = ☃.func_204610_c(☃xx.func_177977_b());
                     if (this.func_212189_g(☃xxxxxxx)) {
                        ☃xxxxx = ☃xxxxxxx.func_206885_f();
                        if (☃xxxxx > 0.0F) {
                           ☃xxxxxx = ☃.func_206885_f() - (☃xxxxx - 0.8888889F);
                        }
                     }
                  }
               } else if (☃xxxxx > 0.0F) {
                  ☃xxxxxx = ☃.func_206885_f() - ☃xxxxx;
               }

               if (☃xxxxxx != 0.0F) {
                  ☃ += (double)((float)☃xxx.func_82601_c() * ☃xxxxxx);
                  ☃x += (double)((float)☃xxx.func_82599_e() * ☃xxxxxx);
               }
            }
         }

         Vec3d ☃xxx = new Vec3d(☃, 0.0, ☃x);
         if (☃.func_177229_b(field_207209_a)) {
            for(EnumFacing ☃xxxx : EnumFacing.Plane.HORIZONTAL) {
               ☃xx.func_189533_g(☃).func_189536_c(☃xxxx);
               if (this.func_205573_a(☃, ☃xx, ☃xxxx) || this.func_205573_a(☃, ☃xx.func_177984_a(), ☃xxxx)) {
                  ☃xxx = ☃xxx.func_72432_b().func_72441_c(0.0, -6.0, 0.0);
                  break;
               }
            }
         }

         var27 = ☃xxx.func_72432_b();
      }

      return var27;
   }

   private boolean func_212189_g(IFluidState var1) {
      return ☃.func_206888_e() || ☃.func_206886_c().func_207187_a(this);
   }

   protected boolean func_205573_a(IBlockReader var1, BlockPos var2, EnumFacing var3) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      IFluidState ☃xx = ☃.func_204610_c(☃);
      if (☃xx.func_206886_c().func_207187_a(this)) {
         return false;
      } else if (☃ == EnumFacing.UP) {
         return true;
      } else if (☃.func_185904_a() == Material.field_151588_w) {
         return false;
      } else {
         boolean ☃ = Block.func_193382_c(☃x) || ☃x instanceof BlockStairs;
         return !☃ && ☃.func_193401_d(☃, ☃, ☃) == BlockFaceShape.SOLID;
      }
   }

   protected void func_205575_a(IWorld var1, BlockPos var2, IFluidState var3) {
      if (!☃.func_206888_e()) {
         IBlockState ☃ = ☃.func_180495_p(☃);
         BlockPos ☃x = ☃.func_177977_b();
         IBlockState ☃xx = ☃.func_180495_p(☃x);
         IFluidState ☃xxx = this.func_205576_a(☃, ☃x, ☃xx);
         if (this.func_205570_b(☃, ☃, ☃, EnumFacing.DOWN, ☃x, ☃xx, ☃.func_204610_c(☃x), ☃xxx.func_206886_c())) {
            this.func_205574_a(☃, ☃x, ☃xx, EnumFacing.DOWN, ☃xxx);
            if (this.func_207936_a(☃, ☃) >= 3) {
               this.func_207937_a(☃, ☃, ☃, ☃);
            }
         } else if (☃.func_206889_d() || !this.func_211759_a(☃, ☃xxx.func_206886_c(), ☃, ☃, ☃x, ☃xx)) {
            this.func_207937_a(☃, ☃, ☃, ☃);
         }
      }
   }

   private void func_207937_a(IWorld var1, BlockPos var2, IFluidState var3, IBlockState var4) {
      int ☃ = ☃.func_206882_g() - this.func_204528_b(☃);
      if (☃.func_177229_b(field_207209_a)) {
         ☃ = 7;
      }

      if (☃ > 0) {
         Map<EnumFacing, IFluidState> ☃ = this.func_205572_b(☃, ☃, ☃);

         for(Entry<EnumFacing, IFluidState> ☃x : ☃.entrySet()) {
            EnumFacing ☃xx = (EnumFacing)☃x.getKey();
            IFluidState ☃xxx = (IFluidState)☃x.getValue();
            BlockPos ☃xxxx = ☃.func_177972_a(☃xx);
            IBlockState ☃xxxxx = ☃.func_180495_p(☃xxxx);
            if (this.func_205570_b(☃, ☃, ☃, ☃xx, ☃xxxx, ☃xxxxx, ☃.func_204610_c(☃xxxx), ☃xxx.func_206886_c())) {
               this.func_205574_a(☃, ☃xxxx, ☃xxxxx, ☃xx, ☃xxx);
            }
         }
      }
   }

   protected IFluidState func_205576_a(IWorldReaderBase var1, BlockPos var2, IBlockState var3) {
      int ☃ = 0;
      int ☃x = 0;

      for(EnumFacing ☃xx : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃xxx = ☃.func_177972_a(☃xx);
         IBlockState ☃xxxx = ☃.func_180495_p(☃xxx);
         IFluidState ☃xxxxx = ☃xxxx.func_204520_s();
         if (☃xxxxx.func_206886_c().func_207187_a(this) && this.func_212751_a(☃xx, ☃, ☃, ☃, ☃xxx, ☃xxxx)) {
            if (☃xxxxx.func_206889_d()) {
               ++☃x;
            }

            ☃ = Math.max(☃, ☃xxxxx.func_206882_g());
         }
      }

      if (this.func_205579_d() && ☃x >= 2) {
         IBlockState ☃xx = ☃.func_180495_p(☃.func_177977_b());
         IFluidState ☃xxx = ☃xx.func_204520_s();
         if (☃xx.func_185904_a().func_76220_a() || this.func_211758_g(☃xxx)) {
            return this.func_207204_a(false);
         }
      }

      BlockPos ☃xx = ☃.func_177984_a();
      IBlockState ☃xxx = ☃.func_180495_p(☃xx);
      IFluidState ☃xxxx = ☃xxx.func_204520_s();
      if (!☃xxxx.func_206888_e() && ☃xxxx.func_206886_c().func_207187_a(this) && this.func_212751_a(EnumFacing.UP, ☃, ☃, ☃, ☃xx, ☃xxx)) {
         return this.func_207207_a(8, true);
      } else {
         int ☃xx = ☃ - this.func_204528_b(☃);
         return ☃xx <= 0 ? Fluids.field_204541_a.func_207188_f() : this.func_207207_a(☃xx, false);
      }
   }

   private boolean func_212751_a(EnumFacing var1, IBlockReader var2, BlockPos var3, IBlockState var4, BlockPos var5, IBlockState var6) {
      Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey> ☃;
      if (!☃.func_177230_c().func_208619_r() && !☃.func_177230_c().func_208619_r()) {
         ☃ = (Object2ByteLinkedOpenHashMap)field_212756_e.get();
      } else {
         ☃ = null;
      }

      Block.RenderSideCacheKey ☃;
      if (☃ != null) {
         ☃ = new Block.RenderSideCacheKey(☃, ☃, ☃);
         byte ☃x = ☃.getAndMoveToFirst(☃);
         if (☃x != 127) {
            return ☃x != 0;
         }
      } else {
         ☃ = null;
      }

      VoxelShape ☃ = ☃.func_196952_d(☃, ☃);
      VoxelShape ☃x = ☃.func_196952_d(☃, ☃);
      boolean ☃xx = !VoxelShapes.func_204642_b(☃, ☃x, ☃);
      if (☃ != null) {
         if (☃.size() == 200) {
            ☃.removeLastByte();
         }

         ☃.putAndMoveToFirst(☃, (byte)(☃xx ? 1 : 0));
      }

      return ☃xx;
   }

   public abstract Fluid func_210197_e();

   public IFluidState func_207207_a(int var1, boolean var2) {
      return this.func_210197_e().func_207188_f().func_206870_a(field_207210_b, Integer.valueOf(☃)).func_206870_a(field_207209_a, Boolean.valueOf(☃));
   }

   public abstract Fluid func_210198_f();

   public IFluidState func_207204_a(boolean var1) {
      return this.func_210198_f().func_207188_f().func_206870_a(field_207209_a, Boolean.valueOf(☃));
   }

   protected abstract boolean func_205579_d();

   protected void func_205574_a(IWorld var1, BlockPos var2, IBlockState var3, EnumFacing var4, IFluidState var5) {
      if (☃.func_177230_c() instanceof ILiquidContainer) {
         ((ILiquidContainer)☃.func_177230_c()).func_204509_a(☃, ☃, ☃, ☃);
      } else {
         if (!☃.func_196958_f()) {
            this.func_205580_a(☃, ☃, ☃);
         }

         ☃.func_180501_a(☃, ☃.func_206883_i(), 3);
      }
   }

   protected abstract void func_205580_a(IWorld var1, BlockPos var2, IBlockState var3);

   private static short func_212752_a(BlockPos var0, BlockPos var1) {
      int ☃ = ☃.func_177958_n() - ☃.func_177958_n();
      int ☃x = ☃.func_177952_p() - ☃.func_177952_p();
      return (short)((☃ + 128 & 0xFF) << 8 | ☃x + 128 & 0xFF);
   }

   protected int func_205571_a(
      IWorldReaderBase var1,
      BlockPos var2,
      int var3,
      EnumFacing var4,
      IBlockState var5,
      BlockPos var6,
      Short2ObjectMap<Pair<IBlockState, IFluidState>> var7,
      Short2BooleanMap var8
   ) {
      int ☃ = 1000;

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         if (☃x != ☃) {
            BlockPos ☃xx = ☃.func_177972_a(☃x);
            short ☃xxx = func_212752_a(☃, ☃xx);
            Pair<IBlockState, IFluidState> ☃xxxx = ☃.computeIfAbsent(☃xxx, var2x -> {
               IBlockState ☃ = ☃.func_180495_p(☃);
               return Pair.of(☃, ☃.func_204520_s());
            });
            IBlockState ☃xxxxx = ☃xxxx.getFirst();
            IFluidState ☃xxxxxx = ☃xxxx.getSecond();
            if (this.func_211760_a(☃, this.func_210197_e(), ☃, ☃, ☃x, ☃xx, ☃xxxxx, ☃xxxxxx)) {
               boolean ☃xxxxxxx = ☃.computeIfAbsent(☃xxx, var4x -> {
                  BlockPos ☃ = ☃.func_177977_b();
                  IBlockState ☃x = ☃.func_180495_p(☃);
                  return this.func_211759_a(☃, this.func_210197_e(), ☃, ☃, ☃, ☃x);
               });
               if (☃xxxxxxx) {
                  return ☃;
               }

               if (☃ < this.func_185698_b(☃)) {
                  int ☃xxxxxxx = this.func_205571_a(☃, ☃xx, ☃ + 1, ☃x.func_176734_d(), ☃xxxxx, ☃, ☃, ☃);
                  if (☃xxxxxxx < ☃) {
                     ☃ = ☃xxxxxxx;
                  }
               }
            }
         }
      }

      return ☃;
   }

   private boolean func_211759_a(IBlockReader var1, Fluid var2, BlockPos var3, IBlockState var4, BlockPos var5, IBlockState var6) {
      if (!this.func_212751_a(EnumFacing.DOWN, ☃, ☃, ☃, ☃, ☃)) {
         return false;
      } else {
         return ☃.func_204520_s().func_206886_c().func_207187_a(this) ? true : this.func_211761_a(☃, ☃, ☃, ☃);
      }
   }

   private boolean func_211760_a(
      IBlockReader var1, Fluid var2, BlockPos var3, IBlockState var4, EnumFacing var5, BlockPos var6, IBlockState var7, IFluidState var8
   ) {
      return !this.func_211758_g(☃) && this.func_212751_a(☃, ☃, ☃, ☃, ☃, ☃) && this.func_211761_a(☃, ☃, ☃, ☃);
   }

   private boolean func_211758_g(IFluidState var1) {
      return ☃.func_206886_c().func_207187_a(this) && ☃.func_206889_d();
   }

   protected abstract int func_185698_b(IWorldReaderBase var1);

   private int func_207936_a(IWorldReaderBase var1, BlockPos var2) {
      int ☃ = 0;

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃xx = ☃.func_177972_a(☃x);
         IFluidState ☃xxx = ☃.func_204610_c(☃xx);
         if (this.func_211758_g(☃xxx)) {
            ++☃;
         }
      }

      return ☃;
   }

   protected Map<EnumFacing, IFluidState> func_205572_b(IWorldReaderBase var1, BlockPos var2, IBlockState var3) {
      int ☃ = 1000;
      Map<EnumFacing, IFluidState> ☃x = Maps.newEnumMap(EnumFacing.class);
      Short2ObjectMap<Pair<IBlockState, IFluidState>> ☃xx = new Short2ObjectOpenHashMap<>();
      Short2BooleanMap ☃xxx = new Short2BooleanOpenHashMap();

      for(EnumFacing ☃xxxx : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃xxxxx = ☃.func_177972_a(☃xxxx);
         short ☃xxxxxx = func_212752_a(☃, ☃xxxxx);
         Pair<IBlockState, IFluidState> ☃xxxxxxx = ☃xx.computeIfAbsent(☃xxxxxx, var2x -> {
            IBlockState ☃ = ☃.func_180495_p(☃);
            return Pair.of(☃, ☃.func_204520_s());
         });
         IBlockState ☃xxxxxxxx = ☃xxxxxxx.getFirst();
         IFluidState ☃xxxxxxxxx = ☃xxxxxxx.getSecond();
         IFluidState ☃xxxxxxxxxx = this.func_205576_a(☃, ☃xxxxx, ☃xxxxxxxx);
         if (this.func_211760_a(☃, ☃xxxxxxxxxx.func_206886_c(), ☃, ☃, ☃xxxx, ☃xxxxx, ☃xxxxxxxx, ☃xxxxxxxxx)) {
            BlockPos ☃xxxxxxxxxxxx = ☃xxxxx.func_177977_b();
            boolean ☃xxxxxxxxxxxxx = ☃xxx.computeIfAbsent(☃xxxxxx, var5x -> {
               IBlockState ☃ = ☃.func_180495_p(☃);
               return this.func_211759_a(☃, this.func_210197_e(), ☃, ☃, ☃, ☃);
            });
            int ☃xxxxxxxxxxx;
            if (☃xxxxxxxxxxxxx) {
               ☃xxxxxxxxxxx = 0;
            } else {
               ☃xxxxxxxxxxx = this.func_205571_a(☃, ☃xxxxx, 1, ☃xxxx.func_176734_d(), ☃xxxxxxxx, ☃, ☃xx, ☃xxx);
            }

            if (☃xxxxxxxxxxx < ☃) {
               ☃x.clear();
            }

            if (☃xxxxxxxxxxx <= ☃) {
               ☃x.put(☃xxxx, ☃xxxxxxxxxx);
               ☃ = ☃xxxxxxxxxxx;
            }
         }
      }

      return ☃x;
   }

   private boolean func_211761_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      Block ☃ = ☃.func_177230_c();
      if (☃ instanceof ILiquidContainer) {
         return ((ILiquidContainer)☃).func_204510_a(☃, ☃, ☃, ☃);
      } else if (!(☃ instanceof BlockDoor)
         && ☃ != Blocks.field_196649_cc
         && ☃ != Blocks.field_150468_ap
         && ☃ != Blocks.field_196608_cF
         && ☃ != Blocks.field_203203_C) {
         Material ☃ = ☃.func_185904_a();
         if (☃ != Material.field_151567_E && ☃ != Material.field_189963_J && ☃ != Material.field_203243_f && ☃ != Material.field_204868_h) {
            return !☃.func_76230_c();
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected boolean func_205570_b(
      IBlockReader var1, BlockPos var2, IBlockState var3, EnumFacing var4, BlockPos var5, IBlockState var6, IFluidState var7, Fluid var8
   ) {
      return ☃.func_211725_a(☃, ☃) && this.func_212751_a(☃, ☃, ☃, ☃, ☃, ☃) && this.func_211761_a(☃, ☃, ☃, ☃);
   }

   protected abstract int func_204528_b(IWorldReaderBase var1);

   protected int func_205578_a(World var1, IFluidState var2, IFluidState var3) {
      return this.func_205569_a(☃);
   }

   @Override
   public void func_207191_a(World var1, BlockPos var2, IFluidState var3) {
      if (!☃.func_206889_d()) {
         IFluidState ☃ = this.func_205576_a(☃, ☃, ☃.func_180495_p(☃));
         int ☃x = this.func_205578_a(☃, ☃, ☃);
         if (☃.func_206888_e()) {
            ☃ = ☃;
            ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 3);
         } else if (!☃.equals(☃)) {
            ☃ = ☃;
            IBlockState ☃ = ☃.func_206883_i();
            ☃.func_180501_a(☃, ☃, 2);
            ☃.func_205219_F_().func_205360_a(☃, ☃.func_206886_c(), ☃x);
            ☃.func_195593_d(☃, ☃.func_177230_c());
         }
      }

      this.func_205575_a(☃, ☃, ☃);
   }

   protected static int func_207205_e(IFluidState var0) {
      return ☃.func_206889_d() ? 0 : 8 - Math.min(☃.func_206882_g(), 8) + (☃.func_177229_b(field_207209_a) ? 8 : 0);
   }

   @Override
   public float func_207181_a(IFluidState var1) {
      return (float)☃.func_206882_g() / 9.0F;
   }
}
