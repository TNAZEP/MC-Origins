package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockShulkerBox extends BlockContainer {
   public static final EnumProperty<EnumFacing> field_190957_a = BlockDirectional.field_176387_N;
   @Nullable
   private final EnumDyeColor field_190958_b;

   public BlockShulkerBox(@Nullable EnumDyeColor var1, Block.Properties var2) {
      super(☃);
      this.field_190958_b = ☃;
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_190957_a, EnumFacing.UP));
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityShulkerBox(this.field_190958_b);
   }

   @Override
   public boolean func_176214_u(IBlockState var1) {
      return true;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public EnumBlockRenderType func_149645_b(IBlockState var1) {
      return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      if (☃.field_72995_K) {
         return true;
      } else if (☃.func_175149_v()) {
         return true;
      } else {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityShulkerBox) {
            EnumFacing ☃xx = ☃.func_177229_b(field_190957_a);
            boolean ☃x;
            if (((TileEntityShulkerBox)☃).func_190591_p() == TileEntityShulkerBox.AnimationStatus.CLOSED) {
               AxisAlignedBB ☃xxx = VoxelShapes.func_197868_b()
                  .func_197752_a()
                  .func_72321_a(
                     (double)(0.5F * (float)☃xx.func_82601_c()), (double)(0.5F * (float)☃xx.func_96559_d()), (double)(0.5F * (float)☃xx.func_82599_e())
                  )
                  .func_191195_a((double)☃xx.func_82601_c(), (double)☃xx.func_96559_d(), (double)☃xx.func_82599_e());
               ☃x = ☃.func_195586_b(null, ☃xxx.func_186670_a(☃.func_177972_a(☃xx)));
            } else {
               ☃x = true;
            }

            if (☃x) {
               ☃.func_195066_a(StatList.field_191272_ae);
               ☃.func_71007_a((IInventory)☃);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_190957_a, ☃.func_196000_l());
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_190957_a);
   }

   @Override
   public void func_176208_a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (☃.func_175625_s(☃) instanceof TileEntityShulkerBox) {
         TileEntityShulkerBox ☃ = (TileEntityShulkerBox)☃.func_175625_s(☃);
         ☃.func_190579_a(☃.field_71075_bZ.field_75098_d);
         ☃.func_184281_d(☃);
      }

      super.func_176208_a(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
   }

   @Override
   public void func_180633_a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (☃.func_82837_s()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityShulkerBox) {
            ((TileEntityShulkerBox)☃).func_200226_a(☃.func_200301_q());
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         TileEntity ☃ = ☃.func_175625_s(☃);
         if (☃ instanceof TileEntityShulkerBox) {
            TileEntityShulkerBox ☃x = (TileEntityShulkerBox)☃;
            if (!☃x.func_190590_r() && ☃x.func_190582_F()) {
               ItemStack ☃xx = new ItemStack(this);
               ☃xx.func_196082_o().func_74782_a("BlockEntityTag", ((TileEntityShulkerBox)☃).func_190580_f(new NBTTagCompound()));
               if (☃x.func_145818_k_()) {
                  ☃xx.func_200302_a(☃x.func_200201_e());
                  ☃x.func_200226_a(null);
               }

               func_180635_a(☃, ☃, ☃xx);
            }

            ☃.func_175666_e(☃, ☃.func_177230_c());
         }

         super.func_196243_a(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public EnumPushReaction func_149656_h(IBlockState var1) {
      return EnumPushReaction.DESTROY;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      return ☃ instanceof TileEntityShulkerBox ? VoxelShapes.func_197881_a(((TileEntityShulkerBox)☃).func_190584_a(☃)) : VoxelShapes.func_197868_b();
   }

   @Override
   public boolean func_200124_e(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_149740_M(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180641_l(IBlockState var1, World var2, BlockPos var3) {
      return Container.func_94526_b((IInventory)☃.func_175625_s(☃));
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      ItemStack ☃ = super.func_185473_a(☃, ☃, ☃);
      TileEntityShulkerBox ☃x = (TileEntityShulkerBox)☃.func_175625_s(☃);
      NBTTagCompound ☃xx = ☃x.func_190580_f(new NBTTagCompound());
      if (!☃xx.isEmpty()) {
         ☃.func_77983_a("BlockEntityTag", ☃xx);
      }

      return ☃;
   }

   public static Block func_190952_a(EnumDyeColor var0) {
      if (☃ == null) {
         return Blocks.field_204409_il;
      } else {
         switch(☃) {
            case WHITE:
               return Blocks.field_190977_dl;
            case ORANGE:
               return Blocks.field_190978_dm;
            case MAGENTA:
               return Blocks.field_190979_dn;
            case LIGHT_BLUE:
               return Blocks.field_190980_do;
            case YELLOW:
               return Blocks.field_190981_dp;
            case LIME:
               return Blocks.field_190982_dq;
            case PINK:
               return Blocks.field_190983_dr;
            case GRAY:
               return Blocks.field_190984_ds;
            case LIGHT_GRAY:
               return Blocks.field_196875_ie;
            case CYAN:
               return Blocks.field_190986_du;
            case PURPLE:
            default:
               return Blocks.field_190987_dv;
            case BLUE:
               return Blocks.field_190988_dw;
            case BROWN:
               return Blocks.field_190989_dx;
            case GREEN:
               return Blocks.field_190990_dy;
            case RED:
               return Blocks.field_190991_dz;
            case BLACK:
               return Blocks.field_190975_dA;
         }
      }
   }

   public static ItemStack func_190953_b(EnumDyeColor var0) {
      return new ItemStack(func_190952_a(☃));
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_190957_a, ☃.func_185831_a(☃.func_177229_b(field_190957_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_190957_a)));
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      EnumFacing ☃ = ☃.func_177229_b(field_190957_a);
      TileEntityShulkerBox.AnimationStatus ☃x = ((TileEntityShulkerBox)☃.func_175625_s(☃)).func_190591_p();
      return ☃x != TileEntityShulkerBox.AnimationStatus.CLOSED && (☃x != TileEntityShulkerBox.AnimationStatus.OPENED || ☃ != ☃.func_176734_d() && ☃ != ☃)
         ? BlockFaceShape.UNDEFINED
         : BlockFaceShape.SOLID;
   }
}
