package net.minecraft.block;

import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.StatList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final BooleanProperty field_176277_a = BlockSixWay.field_196496_z;
   public static final BooleanProperty field_176273_b = BlockSixWay.field_196488_a;
   public static final BooleanProperty field_176278_M = BlockSixWay.field_196490_b;
   public static final BooleanProperty field_176279_N = BlockSixWay.field_196492_c;
   public static final BooleanProperty field_176280_O = BlockSixWay.field_196495_y;
   public static final Map<EnumFacing, BooleanProperty> field_196546_A = (Map<EnumFacing, BooleanProperty>)BlockSixWay.field_196491_B
      .entrySet()
      .stream()
      .filter(var0 -> var0.getKey() != EnumFacing.DOWN)
      .collect(Util.func_199749_a());
   protected static final VoxelShape field_185757_g = Block.func_208617_a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185753_B = Block.func_208617_a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   protected static final VoxelShape field_185754_C = Block.func_208617_a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185755_D = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   protected static final VoxelShape field_185756_E = Block.func_208617_a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

   public BlockVine(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_176277_a, Boolean.valueOf(false))
            .func_206870_a(field_176273_b, Boolean.valueOf(false))
            .func_206870_a(field_176278_M, Boolean.valueOf(false))
            .func_206870_a(field_176279_N, Boolean.valueOf(false))
            .func_206870_a(field_176280_O, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      VoxelShape ☃ = VoxelShapes.func_197880_a();
      if (☃.func_177229_b(field_176277_a)) {
         ☃ = VoxelShapes.func_197872_a(☃, field_185757_g);
      }

      if (☃.func_177229_b(field_176273_b)) {
         ☃ = VoxelShapes.func_197872_a(☃, field_185755_D);
      }

      if (☃.func_177229_b(field_176278_M)) {
         ☃ = VoxelShapes.func_197872_a(☃, field_185754_C);
      }

      if (☃.func_177229_b(field_176279_N)) {
         ☃ = VoxelShapes.func_197872_a(☃, field_185756_E);
      }

      if (☃.func_177229_b(field_176280_O)) {
         ☃ = VoxelShapes.func_197872_a(☃, field_185753_B);
      }

      return ☃;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return this.func_196543_i(this.func_196545_h(☃, ☃, ☃));
   }

   private boolean func_196543_i(IBlockState var1) {
      return this.func_208496_w(☃) > 0;
   }

   private int func_208496_w(IBlockState var1) {
      int ☃ = 0;

      for(BooleanProperty ☃x : field_196546_A.values()) {
         if (☃.func_177229_b(☃x)) {
            ++☃;
         }
      }

      return ☃;
   }

   private boolean func_196541_a(IBlockReader var1, BlockPos var2, EnumFacing var3) {
      if (☃ == EnumFacing.DOWN) {
         return false;
      } else {
         BlockPos ☃ = ☃.func_177972_a(☃);
         if (this.func_196542_b(☃, ☃, ☃)) {
            return true;
         } else if (☃.func_176740_k() == EnumFacing.Axis.Y) {
            return false;
         } else {
            BooleanProperty ☃ = (BooleanProperty)field_196546_A.get(☃);
            IBlockState ☃x = ☃.func_180495_p(☃.func_177984_a());
            return ☃x.func_177230_c() == this && ☃x.func_177229_b(☃);
         }
      }
   }

   private boolean func_196542_b(IBlockReader var1, BlockPos var2, EnumFacing var3) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      return ☃.func_193401_d(☃, ☃, ☃.func_176734_d()) == BlockFaceShape.SOLID && !func_193397_e(☃.func_177230_c());
   }

   protected static boolean func_193397_e(Block var0) {
      return ☃ instanceof BlockShulkerBox
         || ☃ instanceof BlockStainedGlass
         || ☃ == Blocks.field_150461_bJ
         || ☃ == Blocks.field_150383_bp
         || ☃ == Blocks.field_150359_w
         || ☃ == Blocks.field_150331_J
         || ☃ == Blocks.field_150320_F
         || ☃ == Blocks.field_150332_K
         || ☃.func_203417_a(BlockTags.field_212186_k);
   }

   private IBlockState func_196545_h(IBlockState var1, IBlockReader var2, BlockPos var3) {
      BlockPos ☃ = ☃.func_177984_a();
      if (☃.func_177229_b(field_176277_a)) {
         ☃ = ☃.func_206870_a(field_176277_a, Boolean.valueOf(this.func_196542_b(☃, ☃, EnumFacing.DOWN)));
      }

      IBlockState ☃ = null;

      for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
         BooleanProperty ☃xx = func_176267_a(☃x);
         if (☃.func_177229_b(☃xx)) {
            boolean ☃xxx = this.func_196541_a(☃, ☃, ☃x);
            if (!☃xxx) {
               if (☃ == null) {
                  ☃ = ☃.func_180495_p(☃);
               }

               ☃xxx = ☃.func_177230_c() == this && ☃.func_177229_b(☃xx);
            }

            ☃ = ☃.func_206870_a(☃xx, Boolean.valueOf(☃xxx));
         }
      }

      return ☃;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ == EnumFacing.DOWN) {
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         IBlockState ☃ = this.func_196545_h(☃, ☃, ☃);
         return !this.func_196543_i(☃) ? Blocks.field_150350_a.func_176223_P() : ☃;
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         IBlockState ☃ = this.func_196545_h(☃, ☃, ☃);
         if (☃ != ☃) {
            if (this.func_196543_i(☃)) {
               ☃.func_180501_a(☃, ☃, 2);
            } else {
               ☃.func_196949_c(☃, ☃, 0);
               ☃.func_175698_g(☃);
            }
         } else if (☃.field_73012_v.nextInt(4) == 0) {
            EnumFacing ☃ = EnumFacing.func_176741_a(☃);
            BlockPos ☃x = ☃.func_177984_a();
            if (☃.func_176740_k().func_176722_c() && !☃.func_177229_b(func_176267_a(☃))) {
               if (this.func_196539_a(☃, ☃)) {
                  BlockPos ☃xx = ☃.func_177972_a(☃);
                  IBlockState ☃xxx = ☃.func_180495_p(☃xx);
                  if (☃xxx.func_196958_f()) {
                     EnumFacing ☃xxxx = ☃.func_176746_e();
                     EnumFacing ☃xxxxx = ☃.func_176735_f();
                     boolean ☃xxxxxx = ☃.func_177229_b(func_176267_a(☃xxxx));
                     boolean ☃xxxxxxx = ☃.func_177229_b(func_176267_a(☃xxxxx));
                     BlockPos ☃xxxxxxxx = ☃xx.func_177972_a(☃xxxx);
                     BlockPos ☃xxxxxxxxx = ☃xx.func_177972_a(☃xxxxx);
                     if (☃xxxxxx && this.func_196542_b(☃, ☃xxxxxxxx, ☃xxxx)) {
                        ☃.func_180501_a(☃xx, this.func_176223_P().func_206870_a(func_176267_a(☃xxxx), Boolean.valueOf(true)), 2);
                     } else if (☃xxxxxxx && this.func_196542_b(☃, ☃xxxxxxxxx, ☃xxxxx)) {
                        ☃.func_180501_a(☃xx, this.func_176223_P().func_206870_a(func_176267_a(☃xxxxx), Boolean.valueOf(true)), 2);
                     } else {
                        EnumFacing ☃xxxx = ☃.func_176734_d();
                        if (☃xxxxxx && ☃.func_175623_d(☃xxxxxxxx) && this.func_196542_b(☃, ☃.func_177972_a(☃xxxx), ☃xxxx)) {
                           ☃.func_180501_a(☃xxxxxxxx, this.func_176223_P().func_206870_a(func_176267_a(☃xxxx), Boolean.valueOf(true)), 2);
                        } else if (☃xxxxxxx && ☃.func_175623_d(☃xxxxxxxxx) && this.func_196542_b(☃, ☃.func_177972_a(☃xxxxx), ☃xxxx)) {
                           ☃.func_180501_a(☃xxxxxxxxx, this.func_176223_P().func_206870_a(func_176267_a(☃xxxx), Boolean.valueOf(true)), 2);
                        } else if ((double)☃.field_73012_v.nextFloat() < 0.05 && this.func_196542_b(☃, ☃xx.func_177984_a(), EnumFacing.UP)) {
                           ☃.func_180501_a(☃xx, this.func_176223_P().func_206870_a(field_176277_a, Boolean.valueOf(true)), 2);
                        }
                     }
                  } else if (this.func_196542_b(☃, ☃xx, ☃)) {
                     ☃.func_180501_a(☃, ☃.func_206870_a(func_176267_a(☃), Boolean.valueOf(true)), 2);
                  }
               }
            } else {
               if (☃ == EnumFacing.UP && ☃.func_177956_o() < 255) {
                  if (this.func_196541_a(☃, ☃, ☃)) {
                     ☃.func_180501_a(☃, ☃.func_206870_a(field_176277_a, Boolean.valueOf(true)), 2);
                     return;
                  }

                  if (☃.func_175623_d(☃x)) {
                     if (!this.func_196539_a(☃, ☃)) {
                        return;
                     }

                     IBlockState ☃ = ☃;

                     for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
                        if (☃.nextBoolean() || !this.func_196542_b(☃, ☃x.func_177972_a(☃x), EnumFacing.UP)) {
                           ☃ = ☃.func_206870_a(func_176267_a(☃x), Boolean.valueOf(false));
                        }
                     }

                     if (this.func_196540_x(☃)) {
                        ☃.func_180501_a(☃x, ☃, 2);
                     }

                     return;
                  }
               }

               if (☃.func_177956_o() > 0) {
                  BlockPos ☃ = ☃.func_177977_b();
                  IBlockState ☃x = ☃.func_180495_p(☃);
                  if (☃x.func_196958_f() || ☃x.func_177230_c() == this) {
                     IBlockState ☃xx = ☃x.func_196958_f() ? this.func_176223_P() : ☃x;
                     IBlockState ☃xxx = this.func_196544_a(☃, ☃xx, ☃);
                     if (☃xx != ☃xxx && this.func_196540_x(☃xxx)) {
                        ☃.func_180501_a(☃, ☃xxx, 2);
                     }
                  }
               }
            }
         }
      }
   }

   private IBlockState func_196544_a(IBlockState var1, IBlockState var2, Random var3) {
      for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
         if (☃.nextBoolean()) {
            BooleanProperty ☃x = func_176267_a(☃);
            if (☃.func_177229_b(☃x)) {
               ☃ = ☃.func_206870_a(☃x, Boolean.valueOf(true));
            }
         }
      }

      return ☃;
   }

   private boolean func_196540_x(IBlockState var1) {
      return ☃.func_177229_b(field_176273_b) || ☃.func_177229_b(field_176278_M) || ☃.func_177229_b(field_176279_N) || ☃.func_177229_b(field_176280_O);
   }

   private boolean func_196539_a(IBlockReader var1, BlockPos var2) {
      int ☃ = 4;
      Iterable<BlockPos.MutableBlockPos> ☃x = BlockPos.MutableBlockPos.func_191531_b(
         ☃.func_177958_n() - 4, ☃.func_177956_o() - 1, ☃.func_177952_p() - 4, ☃.func_177958_n() + 4, ☃.func_177956_o() + 1, ☃.func_177952_p() + 4
      );
      int ☃xx = 5;

      for(BlockPos ☃xxx : ☃x) {
         if (☃.func_180495_p(☃xxx).func_177230_c() == this) {
            if (--☃xx <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean func_196253_a(IBlockState var1, BlockItemUseContext var2) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      if (☃.func_177230_c() == this) {
         return this.func_208496_w(☃) < field_196546_A.size();
      } else {
         return super.func_196253_a(☃, ☃);
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      boolean ☃x = ☃.func_177230_c() == this;
      IBlockState ☃xx = ☃x ? ☃ : this.func_176223_P();

      for(EnumFacing ☃xxx : ☃.func_196009_e()) {
         if (☃xxx != EnumFacing.DOWN) {
            BooleanProperty ☃xxxx = func_176267_a(☃xxx);
            boolean ☃xxxxx = ☃x && ☃.func_177229_b(☃xxxx);
            if (!☃xxxxx && this.func_196541_a(☃.func_195991_k(), ☃.func_195995_a(), ☃xxx)) {
               return ☃xx.func_206870_a(☃xxxx, Boolean.valueOf(true));
            }
         }
      }

      return ☃x ? ☃xx : null;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      if (!☃.field_72995_K && ☃.func_77973_b() == Items.field_151097_aZ) {
         ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
         ☃.func_71020_j(0.005F);
         func_180635_a(☃, ☃, new ItemStack(Blocks.field_150395_bd));
      } else {
         super.func_180657_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176277_a, field_176273_b, field_176278_M, field_176279_N, field_176280_O);
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      switch(☃) {
         case CLOCKWISE_180:
            return ☃.func_206870_a(field_176273_b, ☃.func_177229_b(field_176279_N))
               .func_206870_a(field_176278_M, ☃.func_177229_b(field_176280_O))
               .func_206870_a(field_176279_N, ☃.func_177229_b(field_176273_b))
               .func_206870_a(field_176280_O, ☃.func_177229_b(field_176278_M));
         case COUNTERCLOCKWISE_90:
            return ☃.func_206870_a(field_176273_b, ☃.func_177229_b(field_176278_M))
               .func_206870_a(field_176278_M, ☃.func_177229_b(field_176279_N))
               .func_206870_a(field_176279_N, ☃.func_177229_b(field_176280_O))
               .func_206870_a(field_176280_O, ☃.func_177229_b(field_176273_b));
         case CLOCKWISE_90:
            return ☃.func_206870_a(field_176273_b, ☃.func_177229_b(field_176280_O))
               .func_206870_a(field_176278_M, ☃.func_177229_b(field_176273_b))
               .func_206870_a(field_176279_N, ☃.func_177229_b(field_176278_M))
               .func_206870_a(field_176280_O, ☃.func_177229_b(field_176279_N));
         default:
            return ☃;
      }
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      switch(☃) {
         case LEFT_RIGHT:
            return ☃.func_206870_a(field_176273_b, ☃.func_177229_b(field_176279_N)).func_206870_a(field_176279_N, ☃.func_177229_b(field_176273_b));
         case FRONT_BACK:
            return ☃.func_206870_a(field_176278_M, ☃.func_177229_b(field_176280_O)).func_206870_a(field_176280_O, ☃.func_177229_b(field_176278_M));
         default:
            return super.func_185471_a(☃, ☃);
      }
   }

   public static BooleanProperty func_176267_a(EnumFacing var0) {
      return (BooleanProperty)field_196546_A.get(☃);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
