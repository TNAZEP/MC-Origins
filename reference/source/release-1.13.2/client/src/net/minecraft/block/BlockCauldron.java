package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmorDyeable;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
   public static final IntegerProperty field_176591_a = BlockStateProperties.field_208130_ae;
   protected static final VoxelShape field_196403_b = Block.func_208617_a(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
   protected static final VoxelShape field_196404_c = VoxelShapes.func_197878_a(VoxelShapes.func_197868_b(), field_196403_b, IBooleanFunction.ONLY_FIRST);

   public BlockCauldron(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176591_a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196404_c;
   }

   @Override
   public boolean func_200124_e(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_199600_g(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196403_b;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      int ☃ = ☃.func_177229_b(field_176591_a);
      float ☃x = (float)☃.func_177956_o() + (6.0F + (float)(3 * ☃)) / 16.0F;
      if (!☃.field_72995_K && ☃.func_70027_ad() && ☃ > 0 && ☃.func_174813_aQ().field_72338_b <= (double)☃x) {
         ☃.func_70066_B();
         this.func_176590_a(☃, ☃, ☃, ☃ - 1);
      }
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      if (☃.func_190926_b()) {
         return true;
      } else {
         int ☃ = ☃.func_177229_b(field_176591_a);
         Item ☃x = ☃.func_77973_b();
         if (☃x == Items.field_151131_as) {
            if (☃ < 3 && !☃.field_72995_K) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_184611_a(☃, new ItemStack(Items.field_151133_ar));
               }

               ☃.func_195066_a(StatList.field_188077_K);
               this.func_176590_a(☃, ☃, ☃, 3);
               ☃.func_184133_a(null, ☃, SoundEvents.field_187624_K, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return true;
         } else if (☃x == Items.field_151133_ar) {
            if (☃ == 3 && !☃.field_72995_K) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ☃.func_190918_g(1);
                  if (☃.func_190926_b()) {
                     ☃.func_184611_a(☃, new ItemStack(Items.field_151131_as));
                  } else if (!☃.field_71071_by.func_70441_a(new ItemStack(Items.field_151131_as))) {
                     ☃.func_71019_a(new ItemStack(Items.field_151131_as), false);
                  }
               }

               ☃.func_195066_a(StatList.field_188078_L);
               this.func_176590_a(☃, ☃, ☃, 0);
               ☃.func_184133_a(null, ☃, SoundEvents.field_187630_M, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return true;
         } else if (☃x == Items.field_151069_bo) {
            if (☃ > 0 && !☃.field_72995_K) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ItemStack ☃ = PotionUtils.func_185188_a(new ItemStack(Items.field_151068_bn), PotionTypes.field_185230_b);
                  ☃.func_195066_a(StatList.field_188078_L);
                  ☃.func_190918_g(1);
                  if (☃.func_190926_b()) {
                     ☃.func_184611_a(☃, ☃);
                  } else if (!☃.field_71071_by.func_70441_a(☃)) {
                     ☃.func_71019_a(☃, false);
                  } else if (☃ instanceof EntityPlayerMP) {
                     ((EntityPlayerMP)☃).func_71120_a(☃.field_71069_bz);
                  }
               }

               ☃.func_184133_a(null, ☃, SoundEvents.field_187615_H, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.func_176590_a(☃, ☃, ☃, ☃ - 1);
            }

            return true;
         } else if (☃x == Items.field_151068_bn && PotionUtils.func_185191_c(☃) == PotionTypes.field_185230_b) {
            if (☃ < 3 && !☃.field_72995_K) {
               if (!☃.field_71075_bZ.field_75098_d) {
                  ItemStack ☃ = new ItemStack(Items.field_151069_bo);
                  ☃.func_195066_a(StatList.field_188078_L);
                  ☃.func_184611_a(☃, ☃);
                  if (☃ instanceof EntityPlayerMP) {
                     ((EntityPlayerMP)☃).func_71120_a(☃.field_71069_bz);
                  }
               }

               ☃.func_184133_a(null, ☃, SoundEvents.field_191241_J, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.func_176590_a(☃, ☃, ☃, ☃ + 1);
            }

            return true;
         } else {
            if (☃ > 0 && ☃x instanceof ItemArmorDyeable) {
               ItemArmorDyeable ☃ = (ItemArmorDyeable)☃x;
               if (☃.func_200883_f_(☃) && !☃.field_72995_K) {
                  ☃.func_200884_g(☃);
                  this.func_176590_a(☃, ☃, ☃, ☃ - 1);
                  ☃.func_195066_a(StatList.field_188079_M);
                  return true;
               }
            }

            if (☃ > 0 && ☃x instanceof ItemBanner) {
               if (TileEntityBanner.func_175113_c(☃) > 0 && !☃.field_72995_K) {
                  ItemStack ☃ = ☃.func_77946_l();
                  ☃.func_190920_e(1);
                  TileEntityBanner.func_175117_e(☃);
                  ☃.func_195066_a(StatList.field_188080_N);
                  if (!☃.field_71075_bZ.field_75098_d) {
                     ☃.func_190918_g(1);
                     this.func_176590_a(☃, ☃, ☃, ☃ - 1);
                  }

                  if (☃.func_190926_b()) {
                     ☃.func_184611_a(☃, ☃);
                  } else if (!☃.field_71071_by.func_70441_a(☃)) {
                     ☃.func_71019_a(☃, false);
                  } else if (☃ instanceof EntityPlayerMP) {
                     ((EntityPlayerMP)☃).func_71120_a(☃.field_71069_bz);
                  }
               }

               return true;
            } else if (☃ > 0 && ☃x instanceof ItemBlock) {
               Block ☃ = ((ItemBlock)☃x).func_179223_d();
               if (☃ instanceof BlockShulkerBox && !☃.func_201670_d()) {
                  ItemStack ☃x = new ItemStack(Blocks.field_204409_il, 1);
                  if (☃.func_77942_o()) {
                     ☃x.func_77982_d(☃.func_77978_p().func_74737_b());
                  }

                  ☃.func_184611_a(☃, ☃x);
                  this.func_176590_a(☃, ☃, ☃, ☃ - 1);
                  ☃.func_195066_a(StatList.field_212740_X);
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   public void func_176590_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      ☃.func_180501_a(☃, ☃.func_206870_a(field_176591_a, Integer.valueOf(MathHelper.func_76125_a(☃, 0, 3))), 2);
      ☃.func_175666_e(☃, this);
   }

   @Override
   public void func_176224_k(World var1, BlockPos var2) {
      if (☃.field_73012_v.nextInt(20) == 1) {
         float ☃ = ☃.func_180494_b(☃).func_180626_a(☃);
         if (!(☃ < 0.15F)) {
            IBlockState ☃x = ☃.func_180495_p(☃);
            if (☃x.func_177229_b(field_176591_a) < 3) {
               ☃.func_180501_a(☃, ☃x.func_177231_a(field_176591_a), 2);
            }
         }
      }
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      return ☃.func_177229_b(field_176591_a);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176591_a);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      if (☃ == EnumFacing.UP) {
         return BlockFaceShape.BOWL;
      } else {
         return ☃ == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
      }
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
