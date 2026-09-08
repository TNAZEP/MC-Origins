package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RedstoneSide;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockRedstoneWire extends Block {
   public static final EnumProperty<RedstoneSide> field_176348_a = BlockStateProperties.field_208160_M;
   public static final EnumProperty<RedstoneSide> field_176347_b = BlockStateProperties.field_208159_L;
   public static final EnumProperty<RedstoneSide> field_176349_M = BlockStateProperties.field_208161_N;
   public static final EnumProperty<RedstoneSide> field_176350_N = BlockStateProperties.field_208162_O;
   public static final IntegerProperty field_176351_O = BlockStateProperties.field_208136_ak;
   public static final Map<EnumFacing, EnumProperty<RedstoneSide>> field_196498_A = Maps.newEnumMap(
      ImmutableMap.of(EnumFacing.NORTH, field_176348_a, EnumFacing.EAST, field_176347_b, EnumFacing.SOUTH, field_176349_M, EnumFacing.WEST, field_176350_N)
   );
   protected static final VoxelShape[] field_196499_B = new VoxelShape[]{
      Block.func_208617_a(3.0, 0.0, 3.0, 13.0, 1.0, 13.0),
      Block.func_208617_a(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 3.0, 13.0, 1.0, 13.0),
      Block.func_208617_a(0.0, 0.0, 3.0, 13.0, 1.0, 16.0),
      Block.func_208617_a(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
      Block.func_208617_a(3.0, 0.0, 0.0, 13.0, 1.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 13.0, 1.0, 13.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 13.0, 1.0, 16.0),
      Block.func_208617_a(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
      Block.func_208617_a(3.0, 0.0, 3.0, 16.0, 1.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 3.0, 16.0, 1.0, 13.0),
      Block.func_208617_a(0.0, 0.0, 3.0, 16.0, 1.0, 16.0),
      Block.func_208617_a(3.0, 0.0, 0.0, 16.0, 1.0, 13.0),
      Block.func_208617_a(3.0, 0.0, 0.0, 16.0, 1.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 1.0, 13.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0)
   };
   private boolean field_150181_a = true;
   private final Set<BlockPos> field_150179_b = Sets.<BlockPos>newHashSet();

   public BlockRedstoneWire(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176348_a, RedstoneSide.NONE)
            .func_206870_a(field_176347_b, RedstoneSide.NONE)
            .func_206870_a(field_176349_M, RedstoneSide.NONE)
            .func_206870_a(field_176350_N, RedstoneSide.NONE)
            .func_206870_a(field_176351_O, Integer.valueOf(0))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196499_B[func_185699_x(☃)];
   }

   private static int func_185699_x(IBlockState var0) {
      int ☃ = 0;
      boolean ☃x = ☃.func_177229_b(field_176348_a) != RedstoneSide.NONE;
      boolean ☃xx = ☃.func_177229_b(field_176347_b) != RedstoneSide.NONE;
      boolean ☃xxx = ☃.func_177229_b(field_176349_M) != RedstoneSide.NONE;
      boolean ☃xxxx = ☃.func_177229_b(field_176350_N) != RedstoneSide.NONE;
      if (☃x || ☃xxx && !☃x && !☃xx && !☃xxxx) {
         ☃ |= 1 << EnumFacing.NORTH.func_176736_b();
      }

      if (☃xx || ☃xxxx && !☃x && !☃xx && !☃xxx) {
         ☃ |= 1 << EnumFacing.EAST.func_176736_b();
      }

      if (☃xxx || ☃x && !☃xx && !☃xxx && !☃xxxx) {
         ☃ |= 1 << EnumFacing.SOUTH.func_176736_b();
      }

      if (☃xxxx || ☃xx && !☃x && !☃xxx && !☃xxxx) {
         ☃ |= 1 << EnumFacing.WEST.func_176736_b();
      }

      return ☃;
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      return this.func_176223_P()
         .func_206870_a(field_176350_N, this.func_208074_a(☃, ☃x, EnumFacing.WEST))
         .func_206870_a(field_176347_b, this.func_208074_a(☃, ☃x, EnumFacing.EAST))
         .func_206870_a(field_176348_a, this.func_208074_a(☃, ☃x, EnumFacing.NORTH))
         .func_206870_a(field_176349_M, this.func_208074_a(☃, ☃x, EnumFacing.SOUTH));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ == EnumFacing.DOWN) {
         return ☃;
      } else {
         return ☃ == EnumFacing.UP
            ? ☃.func_206870_a(field_176350_N, this.func_208074_a(☃, ☃, EnumFacing.WEST))
               .func_206870_a(field_176347_b, this.func_208074_a(☃, ☃, EnumFacing.EAST))
               .func_206870_a(field_176348_a, this.func_208074_a(☃, ☃, EnumFacing.NORTH))
               .func_206870_a(field_176349_M, this.func_208074_a(☃, ☃, EnumFacing.SOUTH))
            : ☃.func_206870_a((IProperty)field_196498_A.get(☃), this.func_208074_a(☃, ☃, ☃));
      }
   }

   @Override
   public void func_196248_b(IBlockState var1, IWorld var2, BlockPos var3, int var4) {
      try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
            RedstoneSide ☃xx = ☃.func_177229_b((IProperty<RedstoneSide>)field_196498_A.get(☃x));
            if (☃xx != RedstoneSide.NONE && ☃.func_180495_p(☃.func_189533_g(☃).func_189536_c(☃x)).func_177230_c() != this) {
               ☃.func_189536_c(EnumFacing.DOWN);
               IBlockState ☃xxx = ☃.func_180495_p(☃);
               if (☃xxx.func_177230_c() != Blocks.field_190976_dk) {
                  BlockPos ☃xxxx = ☃.func_177972_a(☃x.func_176734_d());
                  IBlockState ☃xxxxx = ☃xxx.func_196956_a(☃x.func_176734_d(), ☃.func_180495_p(☃xxxx), ☃, ☃, ☃xxxx);
                  func_196263_a(☃xxx, ☃xxxxx, ☃, ☃, ☃);
               }

               ☃.func_189533_g(☃).func_189536_c(☃x).func_189536_c(EnumFacing.UP);
               IBlockState ☃xxx = ☃.func_180495_p(☃);
               if (☃xxx.func_177230_c() != Blocks.field_190976_dk) {
                  BlockPos ☃xxxx = ☃.func_177972_a(☃x.func_176734_d());
                  IBlockState ☃xxxxx = ☃xxx.func_196956_a(☃x.func_176734_d(), ☃.func_180495_p(☃xxxx), ☃, ☃, ☃xxxx);
                  func_196263_a(☃xxx, ☃xxxxx, ☃, ☃, ☃);
               }
            }
         }
      }
   }

   private RedstoneSide func_208074_a(IBlockReader var1, BlockPos var2, EnumFacing var3) {
      BlockPos ☃ = ☃.func_177972_a(☃);
      IBlockState ☃x = ☃.func_180495_p(☃.func_177972_a(☃));
      IBlockState ☃xx = ☃.func_180495_p(☃.func_177984_a());
      if (!☃xx.func_185915_l()) {
         boolean ☃xxx = ☃.func_180495_p(☃).func_185896_q() || ☃.func_180495_p(☃).func_177230_c() == Blocks.field_150426_aN;
         if (☃xxx && func_176346_d(☃.func_180495_p(☃.func_177984_a()))) {
            if (☃x.func_185898_k()) {
               return RedstoneSide.UP;
            }

            return RedstoneSide.SIDE;
         }
      }

      return !func_176343_a(☃.func_180495_p(☃), ☃) && (☃x.func_185915_l() || !func_176346_d(☃.func_180495_p(☃.func_177977_b())))
         ? RedstoneSide.NONE
         : RedstoneSide.SIDE;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      return ☃.func_185896_q() || ☃.func_177230_c() == Blocks.field_150426_aN;
   }

   private IBlockState func_176338_e(World var1, BlockPos var2, IBlockState var3) {
      ☃ = this.func_212568_b(☃, ☃, ☃);
      List<BlockPos> ☃ = Lists.<BlockPos>newArrayList(this.field_150179_b);
      this.field_150179_b.clear();

      for(BlockPos ☃x : ☃) {
         ☃.func_195593_d(☃x, this);
      }

      return ☃;
   }

   private IBlockState func_212568_b(World var1, BlockPos var2, IBlockState var3) {
      IBlockState ☃ = ☃;
      int ☃x = ☃.func_177229_b(field_176351_O);
      int ☃xx = 0;
      ☃xx = this.func_212567_a(☃xx, ☃);
      this.field_150181_a = false;
      int ☃xxx = ☃.func_175687_A(☃);
      this.field_150181_a = true;
      if (☃xxx > 0 && ☃xxx > ☃xx - 1) {
         ☃xx = ☃xxx;
      }

      int ☃ = 0;

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃xx = ☃.func_177972_a(☃x);
         boolean ☃xxx = ☃xx.func_177958_n() != ☃.func_177958_n() || ☃xx.func_177952_p() != ☃.func_177952_p();
         IBlockState ☃xxxx = ☃.func_180495_p(☃xx);
         if (☃xxx) {
            ☃ = this.func_212567_a(☃, ☃xxxx);
         }

         if (☃xxxx.func_185915_l() && !☃.func_180495_p(☃.func_177984_a()).func_185915_l()) {
            if (☃xxx && ☃.func_177956_o() >= ☃.func_177956_o()) {
               ☃ = this.func_212567_a(☃, ☃.func_180495_p(☃xx.func_177984_a()));
            }
         } else if (!☃xxxx.func_185915_l() && ☃xxx && ☃.func_177956_o() <= ☃.func_177956_o()) {
            ☃ = this.func_212567_a(☃, ☃.func_180495_p(☃xx.func_177977_b()));
         }
      }

      if (☃ > ☃xx) {
         ☃xx = ☃ - 1;
      } else if (☃xx > 0) {
         --☃xx;
      } else {
         ☃xx = 0;
      }

      if (☃xxx > ☃xx - 1) {
         ☃xx = ☃xxx;
      }

      if (☃x != ☃xx) {
         ☃ = ☃.func_206870_a(field_176351_O, Integer.valueOf(☃xx));
         if (☃.func_180495_p(☃) == ☃) {
            ☃.func_180501_a(☃, ☃, 2);
         }

         this.field_150179_b.add(☃);

         for(EnumFacing ☃x : EnumFacing.values()) {
            this.field_150179_b.add(☃.func_177972_a(☃x));
         }
      }

      return ☃;
   }

   private void func_176344_d(World var1, BlockPos var2) {
      if (☃.func_180495_p(☃).func_177230_c() == this) {
         ☃.func_195593_d(☃, this);

         for(EnumFacing ☃ : EnumFacing.values()) {
            ☃.func_195593_d(☃.func_177972_a(☃), this);
         }
      }
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c() && !☃.field_72995_K) {
         this.func_176338_e(☃, ☃, ☃);

         for(EnumFacing ☃ : EnumFacing.Plane.VERTICAL) {
            ☃.func_195593_d(☃.func_177972_a(☃), this);
         }

         for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
            this.func_176344_d(☃, ☃.func_177972_a(☃));
         }

         for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
            BlockPos ☃x = ☃.func_177972_a(☃);
            if (☃.func_180495_p(☃x).func_185915_l()) {
               this.func_176344_d(☃, ☃x.func_177984_a());
            } else {
               this.func_176344_d(☃, ☃x.func_177977_b());
            }
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (!☃ && ☃.func_177230_c() != ☃.func_177230_c()) {
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         if (!☃.field_72995_K) {
            for(EnumFacing ☃ : EnumFacing.values()) {
               ☃.func_195593_d(☃.func_177972_a(☃), this);
            }

            this.func_176338_e(☃, ☃, ☃);

            for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
               this.func_176344_d(☃, ☃.func_177972_a(☃));
            }

            for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
               BlockPos ☃x = ☃.func_177972_a(☃);
               if (☃.func_180495_p(☃x).func_185915_l()) {
                  this.func_176344_d(☃, ☃x.func_177984_a());
               } else {
                  this.func_176344_d(☃, ☃x.func_177977_b());
               }
            }
         }
      }
   }

   private int func_212567_a(int var1, IBlockState var2) {
      if (☃.func_177230_c() != this) {
         return ☃;
      } else {
         int ☃ = ☃.func_177229_b(field_176351_O);
         return ☃ > ☃ ? ☃ : ☃;
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         if (☃.func_196955_c(☃, ☃)) {
            this.func_176338_e(☃, ☃, ☃);
         } else {
            ☃.func_196949_c(☃, ☃, 0);
            ☃.func_175698_g(☃);
         }
      }
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return !this.field_150181_a ? 0 : ☃.func_185911_a(☃, ☃, ☃);
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      if (!this.field_150181_a) {
         return 0;
      } else {
         int ☃ = ☃.func_177229_b(field_176351_O);
         if (☃ == 0) {
            return 0;
         } else if (☃ == EnumFacing.UP) {
            return ☃;
         } else {
            EnumSet<EnumFacing> ☃ = EnumSet.noneOf(EnumFacing.class);

            for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
               if (this.func_176339_d(☃, ☃, ☃x)) {
                  ☃.add(☃x);
               }
            }

            if (☃.func_176740_k().func_176722_c() && ☃.isEmpty()) {
               return ☃;
            } else {
               return ☃.contains(☃) && !☃.contains(☃.func_176735_f()) && !☃.contains(☃.func_176746_e()) ? ☃ : 0;
            }
         }
      }
   }

   private boolean func_176339_d(IBlockReader var1, BlockPos var2, EnumFacing var3) {
      BlockPos ☃ = ☃.func_177972_a(☃);
      IBlockState ☃x = ☃.func_180495_p(☃);
      boolean ☃xx = ☃x.func_185915_l();
      boolean ☃xxx = ☃.func_180495_p(☃.func_177984_a()).func_185915_l();
      if (!☃xxx && ☃xx && func_176340_e(☃, ☃.func_177984_a())) {
         return true;
      } else if (func_176343_a(☃x, ☃)) {
         return true;
      } else if (☃x.func_177230_c() == Blocks.field_196633_cV
         && ☃x.func_177229_b(BlockRedstoneDiode.field_196348_c)
         && ☃x.func_177229_b(BlockRedstoneDiode.field_185512_D) == ☃) {
         return true;
      } else {
         return !☃xx && func_176340_e(☃, ☃.func_177977_b());
      }
   }

   protected static boolean func_176340_e(IBlockReader var0, BlockPos var1) {
      return func_176346_d(☃.func_180495_p(☃));
   }

   protected static boolean func_176346_d(IBlockState var0) {
      return func_176343_a(☃, null);
   }

   protected static boolean func_176343_a(IBlockState var0, @Nullable EnumFacing var1) {
      Block ☃ = ☃.func_177230_c();
      if (☃ == Blocks.field_150488_af) {
         return true;
      } else if (☃.func_177230_c() == Blocks.field_196633_cV) {
         EnumFacing ☃ = ☃.func_177229_b(BlockRedstoneRepeater.field_185512_D);
         return ☃ == ☃ || ☃.func_176734_d() == ☃;
      } else if (Blocks.field_190976_dk == ☃.func_177230_c()) {
         return ☃ == ☃.func_177229_b(BlockObserver.field_176387_N);
      } else {
         return ☃.func_185897_m() && ☃ != null;
      }
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return this.field_150181_a;
   }

   public static int func_176337_b(int var0) {
      float ☃ = (float)☃ / 15.0F;
      float ☃x = ☃ * 0.6F + 0.4F;
      if (☃ == 0) {
         ☃x = 0.3F;
      }

      float ☃ = ☃ * ☃ * 0.7F - 0.5F;
      float ☃x = ☃ * ☃ * 0.6F - 0.7F;
      if (☃ < 0.0F) {
         ☃ = 0.0F;
      }

      if (☃x < 0.0F) {
         ☃x = 0.0F;
      }

      int ☃ = MathHelper.func_76125_a((int)(☃x * 255.0F), 0, 255);
      int ☃x = MathHelper.func_76125_a((int)(☃ * 255.0F), 0, 255);
      int ☃xx = MathHelper.func_76125_a((int)(☃x * 255.0F), 0, 255);
      return 0xFF000000 | ☃ << 16 | ☃x << 8 | ☃xx;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      int ☃ = ☃.func_177229_b(field_176351_O);
      if (☃ != 0) {
         double ☃x = (double)☃.func_177958_n() + 0.5 + ((double)☃.nextFloat() - 0.5) * 0.2;
         double ☃xx = (double)((float)☃.func_177956_o() + 0.0625F);
         double ☃xxx = (double)☃.func_177952_p() + 0.5 + ((double)☃.nextFloat() - 0.5) * 0.2;
         float ☃xxxx = (float)☃ / 15.0F;
         float ☃xxxxx = ☃xxxx * 0.6F + 0.4F;
         float ☃xxxxxx = Math.max(0.0F, ☃xxxx * ☃xxxx * 0.7F - 0.5F);
         float ☃xxxxxxx = Math.max(0.0F, ☃xxxx * ☃xxxx * 0.6F - 0.7F);
         ☃.func_195594_a(new RedstoneParticleData(☃xxxxx, ☃xxxxxx, ☃xxxxxxx, 1.0F), ☃x, ☃xx, ☃xxx, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            return ☃.func_206870_a(field_176348_a, ☃.func_177229_b(field_176349_M))
               .func_206870_a(field_176347_b, ☃.func_177229_b(field_176350_N))
               .func_206870_a(field_176349_M, ☃.func_177229_b(field_176348_a))
               .func_206870_a(field_176350_N, ☃.func_177229_b(field_176347_b));
         case COUNTERCLOCKWISE_90:
            return ☃.func_206870_a(field_176348_a, ☃.func_177229_b(field_176347_b))
               .func_206870_a(field_176347_b, ☃.func_177229_b(field_176349_M))
               .func_206870_a(field_176349_M, ☃.func_177229_b(field_176350_N))
               .func_206870_a(field_176350_N, ☃.func_177229_b(field_176348_a));
         case CLOCKWISE_90:
            return ☃.func_206870_a(field_176348_a, ☃.func_177229_b(field_176350_N))
               .func_206870_a(field_176347_b, ☃.func_177229_b(field_176348_a))
               .func_206870_a(field_176349_M, ☃.func_177229_b(field_176347_b))
               .func_206870_a(field_176350_N, ☃.func_177229_b(field_176349_M));
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      switch(☃) {
         case LEFT_RIGHT:
            return ☃.func_206870_a(field_176348_a, ☃.func_177229_b(field_176349_M)).func_206870_a(field_176349_M, ☃.func_177229_b(field_176348_a));
         case FRONT_BACK:
            return ☃.func_206870_a(field_176347_b, ☃.func_177229_b(field_176350_N)).func_206870_a(field_176350_N, ☃.func_177229_b(field_176347_b));
         default:
            return super.func_185471_a(☃, ☃);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176348_a, field_176347_b, field_176349_M, field_176350_N, field_176351_O);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
