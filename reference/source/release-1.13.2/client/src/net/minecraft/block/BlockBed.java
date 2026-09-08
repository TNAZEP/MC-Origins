package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockBed extends BlockHorizontal implements ITileEntityProvider {
   public static final EnumProperty<BedPart> field_176472_a = BlockStateProperties.field_208139_an;
   public static final BooleanProperty field_176471_b = BlockStateProperties.field_208192_s;
   protected static final VoxelShape field_196351_c = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
   private final EnumDyeColor field_196352_y;

   public BlockBed(EnumDyeColor var1, Block.Properties var2) {
      super(☃);
      this.field_196352_y = ☃;
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176472_a, BedPart.FOOT).func_206870_a(field_176471_b, Boolean.valueOf(false)));
   }

   @Override
   public MaterialColor func_180659_g(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177229_b(field_176472_a) == BedPart.FOOT ? this.field_196352_y.func_196055_e() : MaterialColor.field_151659_e;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else {
         if (☃.func_177229_b(field_176472_a) != BedPart.HEAD) {
            ☃ = ☃.func_177972_a(☃.func_177229_b(field_185512_D));
            ☃ = ☃.func_180495_p(☃);
            if (☃.func_177230_c() != this) {
               return true;
            }
         }

         if (☃.field_73011_w.func_76567_e() && ☃.func_180494_b(☃) != Biomes.field_76778_j) {
            if (☃.func_177229_b(field_176471_b)) {
               EntityPlayer ☃ = this.func_176470_e(☃, ☃);
               if (☃ != null) {
                  ☃.func_146105_b(new TextComponentTranslation("block.minecraft.bed.occupied"), true);
                  return true;
               }

               ☃ = ☃.func_206870_a(field_176471_b, Boolean.valueOf(false));
               ☃.func_180501_a(☃, ☃, 4);
            }

            EntityPlayer.SleepResult ☃ = ☃.func_180469_a(☃);
            if (☃ == EntityPlayer.SleepResult.OK) {
               ☃ = ☃.func_206870_a(field_176471_b, Boolean.valueOf(true));
               ☃.func_180501_a(☃, ☃, 4);
               return true;
            } else {
               if (☃ == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
                  ☃.func_146105_b(new TextComponentTranslation("block.minecraft.bed.no_sleep"), true);
               } else if (☃ == EntityPlayer.SleepResult.NOT_SAFE) {
                  ☃.func_146105_b(new TextComponentTranslation("block.minecraft.bed.not_safe"), true);
               } else if (☃ == EntityPlayer.SleepResult.TOO_FAR_AWAY) {
                  ☃.func_146105_b(new TextComponentTranslation("block.minecraft.bed.too_far_away"), true);
               }

               return true;
            }
         } else {
            ☃.func_175698_g(☃);
            BlockPos ☃ = ☃.func_177972_a(((EnumFacing)☃.func_177229_b(field_185512_D)).func_176734_d());
            if (☃.func_180495_p(☃).func_177230_c() == this) {
               ☃.func_175698_g(☃);
            }

            ☃.func_211529_a(
               null,
               DamageSource.func_199683_a(),
               (double)☃.func_177958_n() + 0.5,
               (double)☃.func_177956_o() + 0.5,
               (double)☃.func_177952_p() + 0.5,
               5.0F,
               true,
               true
            );
            return true;
         }
      }
   }

   @Nullable
   private EntityPlayer func_176470_e(World var1, BlockPos var2) {
      for(EntityPlayer ☃ : ☃.field_73010_i) {
         if (☃.func_70608_bn() && ☃.field_71081_bT.equals(☃)) {
            return ☃;
         }
      }

      return null;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public void func_180658_a(World var1, BlockPos var2, Entity var3, float var4) {
      super.func_180658_a(☃, ☃, ☃, ☃ * 0.5F);
   }

   @Override
   public void func_176216_a(IBlockReader var1, Entity var2) {
      if (☃.func_70093_af()) {
         super.func_176216_a(☃, ☃);
      } else if (☃.field_70181_x < 0.0) {
         ☃.field_70181_x = -☃.field_70181_x * 0.66F;
         if (!(☃ instanceof EntityLivingBase)) {
            ☃.field_70181_x *= 0.8;
         }
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ == func_208070_a(☃.func_177229_b(field_176472_a), ☃.func_177229_b(field_185512_D))) {
         return ☃.func_177230_c() == this && ☃.func_177229_b(field_176472_a) != ☃.func_177229_b(field_176472_a)
            ? ☃.func_206870_a(field_176471_b, ☃.func_177229_b(field_176471_b))
            : Blocks.field_150350_a.func_176223_P();
      } else {
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   private static EnumFacing func_208070_a(BedPart var0, EnumFacing var1) {
      return ☃ == BedPart.FOOT ? ☃ : ☃.func_176734_d();
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      super.func_180657_a(☃, ☃, ☃, Blocks.field_150350_a.func_176223_P(), ☃, ☃);
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
         ☃.func_175713_t(☃);
      }
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      BedPart ☃ = ☃.func_177229_b(field_176472_a);
      boolean ☃x = ☃ == BedPart.HEAD;
      BlockPos ☃xx = ☃.func_177972_a(func_208070_a(☃, ☃.func_177229_b(field_185512_D)));
      IBlockState ☃xxx = ☃.func_180495_p(☃xx);
      if (☃xxx.func_177230_c() == this && ☃xxx.func_177229_b(field_176472_a) != ☃) {
         ☃.func_180501_a(☃xx, Blocks.field_150350_a.func_176223_P(), 35);
         ☃.func_180498_a(☃, 2001, ☃xx, Block.func_196246_j(☃xxx));
         if (!☃.field_72995_K && !☃.func_184812_l_()) {
            if (☃x) {
               ☃.func_196949_c(☃, ☃, 0);
            } else {
               ☃xxx.func_196949_c(☃, ☃xx, 0);
            }
         }

         ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      EnumFacing ☃ = ☃.func_195992_f();
      BlockPos ☃x = ☃.func_195995_a();
      BlockPos ☃xx = ☃x.func_177972_a(☃);
      return ☃.func_195991_k().func_180495_p(☃xx).func_196953_a(☃) ? this.func_176223_P().func_206870_a(field_185512_D, ☃) : null;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return (IItemProvider)(☃.func_177229_b(field_176472_a) == BedPart.FOOT ? Items.field_190931_a : super.func_199769_a(☃, ☃, ☃, ☃));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196351_c;
   }

   @Override
   public boolean func_190946_v(IBlockState var1) {
      return true;
   }

   @Nullable
   public static BlockPos func_176468_a(IBlockReader var0, BlockPos var1, int var2) {
      EnumFacing ☃ = ☃.func_180495_p(☃).func_177229_b(field_185512_D);
      int ☃x = ☃.func_177958_n();
      int ☃xx = ☃.func_177956_o();
      int ☃xxx = ☃.func_177952_p();

      for(int ☃xxxx = 0; ☃xxxx <= 1; ++☃xxxx) {
         int ☃xxxxx = ☃x - ☃.func_82601_c() * ☃xxxx - 1;
         int ☃xxxxxx = ☃xxx - ☃.func_82599_e() * ☃xxxx - 1;
         int ☃xxxxxxx = ☃xxxxx + 2;
         int ☃xxxxxxxx = ☃xxxxxx + 2;

         for(int ☃xxxxxxxxx = ☃xxxxx; ☃xxxxxxxxx <= ☃xxxxxxx; ++☃xxxxxxxxx) {
            for(int ☃xxxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxxx <= ☃xxxxxxxx; ++☃xxxxxxxxxx) {
               BlockPos ☃xxxxxxxxxxx = new BlockPos(☃xxxxxxxxx, ☃xx, ☃xxxxxxxxxx);
               if (func_176469_d(☃, ☃xxxxxxxxxxx)) {
                  if (☃ <= 0) {
                     return ☃xxxxxxxxxxx;
                  }

                  --☃;
               }
            }
         }
      }

      return null;
   }

   protected static boolean func_176469_d(IBlockReader var0, BlockPos var1) {
      return ☃.func_180495_p(☃.func_177977_b()).func_185896_q()
         && !☃.func_180495_p(☃).func_185904_a().func_76220_a()
         && !☃.func_180495_p(☃.func_177984_a()).func_185904_a().func_76220_a();
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.DESTROY;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176472_a, field_176471_b);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityBed(this.field_196352_y);
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, @Nullable EntityLivingBase var4, ItemStack var5) {
      super.func_180633_a(☃, ☃, ☃, ☃, ☃);
      if (!☃.field_72995_K) {
         BlockPos ☃ = ☃.func_177972_a(☃.func_177229_b(field_185512_D));
         ☃.func_180501_a(☃, ☃.func_206870_a(field_176472_a, BedPart.HEAD), 3);
         ☃.func_195592_c(☃, Blocks.field_150350_a);
         ☃.func_196946_a(☃, ☃, 3);
      }
   }

   public EnumDyeColor func_196350_d() {
      return this.field_196352_y;
   }

   @Override
   public long func_209900_a(IBlockState var1, BlockPos var2) {
      BlockPos ☃ = ☃.func_177967_a(☃.func_177229_b(field_185512_D), ☃.func_177229_b(field_176472_a) == BedPart.HEAD ? 0 : 1);
      return MathHelper.func_180187_c(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
