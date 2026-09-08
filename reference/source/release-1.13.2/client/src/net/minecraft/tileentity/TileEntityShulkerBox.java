package net.minecraft.tileentity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityShulkerBox extends TileEntityLockableLoot implements ISidedInventory, ITickable {
   private static final int[] field_190595_a = IntStream.range(0, 27).toArray();
   private NonNullList<ItemStack> field_190596_f = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
   private boolean field_190597_g;
   private int field_190598_h;
   private TileEntityShulkerBox.AnimationStatus field_190599_i = TileEntityShulkerBox.AnimationStatus.CLOSED;
   private float field_190600_j;
   private float field_190601_k;
   private EnumDyeColor field_190602_l;
   private boolean field_204400_o;
   private boolean field_190594_p;

   public TileEntityShulkerBox(@Nullable EnumDyeColor var1) {
      super(TileEntityType.field_200993_x);
      this.field_190602_l = ☃;
   }

   public TileEntityShulkerBox() {
      this(null);
      this.field_204400_o = true;
   }

   @Override
   public void func_73660_a() {
      this.func_190583_o();
      if (this.field_190599_i == TileEntityShulkerBox.AnimationStatus.OPENING || this.field_190599_i == TileEntityShulkerBox.AnimationStatus.CLOSING) {
         this.func_190589_G();
      }
   }

   protected void func_190583_o() {
      this.field_190601_k = this.field_190600_j;
      switch(this.field_190599_i) {
         case CLOSED:
            this.field_190600_j = 0.0F;
            break;
         case OPENING:
            this.field_190600_j += 0.1F;
            if (this.field_190600_j >= 1.0F) {
               this.func_190589_G();
               this.field_190599_i = TileEntityShulkerBox.AnimationStatus.OPENED;
               this.field_190600_j = 1.0F;
            }
            break;
         case CLOSING:
            this.field_190600_j -= 0.1F;
            if (this.field_190600_j <= 0.0F) {
               this.field_190599_i = TileEntityShulkerBox.AnimationStatus.CLOSED;
               this.field_190600_j = 0.0F;
            }
            break;
         case OPENED:
            this.field_190600_j = 1.0F;
      }
   }

   public TileEntityShulkerBox.AnimationStatus func_190591_p() {
      return this.field_190599_i;
   }

   public AxisAlignedBB func_190584_a(IBlockState var1) {
      return this.func_190587_b(☃.func_177229_b(BlockShulkerBox.field_190957_a));
   }

   public AxisAlignedBB func_190587_b(EnumFacing var1) {
      return VoxelShapes.func_197868_b()
         .func_197752_a()
         .func_72321_a(
            (double)(0.5F * this.func_190585_a(1.0F) * (float)☃.func_82601_c()),
            (double)(0.5F * this.func_190585_a(1.0F) * (float)☃.func_96559_d()),
            (double)(0.5F * this.func_190585_a(1.0F) * (float)☃.func_82599_e())
         );
   }

   private AxisAlignedBB func_190588_c(EnumFacing var1) {
      EnumFacing ☃ = ☃.func_176734_d();
      return this.func_190587_b(☃).func_191195_a((double)☃.func_82601_c(), (double)☃.func_96559_d(), (double)☃.func_82599_e());
   }

   private void func_190589_G() {
      IBlockState ☃ = this.field_145850_b.func_180495_p(this.func_174877_v());
      if (☃.func_177230_c() instanceof BlockShulkerBox) {
         EnumFacing ☃x = ☃.func_177229_b(BlockShulkerBox.field_190957_a);
         AxisAlignedBB ☃xx = this.func_190588_c(☃x).func_186670_a(this.field_174879_c);
         List<Entity> ☃xxx = this.field_145850_b.func_72839_b(null, ☃xx);
         if (!☃xxx.isEmpty()) {
            for(int ☃xxxx = 0; ☃xxxx < ☃xxx.size(); ++☃xxxx) {
               Entity ☃xxxxx = (Entity)☃xxx.get(☃xxxx);
               if (☃xxxxx.func_184192_z() != EnumPushReaction.IGNORE) {
                  double ☃xxxxxx = 0.0;
                  double ☃xxxxxxx = 0.0;
                  double ☃xxxxxxxx = 0.0;
                  AxisAlignedBB ☃xxxxxxxxx = ☃xxxxx.func_174813_aQ();
                  switch(☃x.func_176740_k()) {
                     case X:
                        if (☃x.func_176743_c() == EnumFacing.AxisDirection.POSITIVE) {
                           ☃xxxxxx = ☃xx.field_72336_d - ☃xxxxxxxxx.field_72340_a;
                        } else {
                           ☃xxxxxx = ☃xxxxxxxxx.field_72336_d - ☃xx.field_72340_a;
                        }

                        ☃xxxxxx += 0.01;
                        break;
                     case Y:
                        if (☃x.func_176743_c() == EnumFacing.AxisDirection.POSITIVE) {
                           ☃xxxxxxx = ☃xx.field_72337_e - ☃xxxxxxxxx.field_72338_b;
                        } else {
                           ☃xxxxxxx = ☃xxxxxxxxx.field_72337_e - ☃xx.field_72338_b;
                        }

                        ☃xxxxxxx += 0.01;
                        break;
                     case Z:
                        if (☃x.func_176743_c() == EnumFacing.AxisDirection.POSITIVE) {
                           ☃xxxxxxxx = ☃xx.field_72334_f - ☃xxxxxxxxx.field_72339_c;
                        } else {
                           ☃xxxxxxxx = ☃xxxxxxxxx.field_72334_f - ☃xx.field_72339_c;
                        }

                        ☃xxxxxxxx += 0.01;
                  }

                  ☃xxxxx.func_70091_d(
                     MoverType.SHULKER_BOX, ☃xxxxxx * (double)☃x.func_82601_c(), ☃xxxxxxx * (double)☃x.func_96559_d(), ☃xxxxxxxx * (double)☃x.func_82599_e()
                  );
               }
            }
         }
      }
   }

   @Override
   public int func_70302_i_() {
      return this.field_190596_f.size();
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public boolean func_145842_c(int var1, int var2) {
      if (☃ == 1) {
         this.field_190598_h = ☃;
         if (☃ == 0) {
            this.field_190599_i = TileEntityShulkerBox.AnimationStatus.CLOSING;
         }

         if (☃ == 1) {
            this.field_190599_i = TileEntityShulkerBox.AnimationStatus.OPENING;
         }

         return true;
      } else {
         return super.func_145842_c(☃, ☃);
      }
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
      if (!☃.func_175149_v()) {
         if (this.field_190598_h < 0) {
            this.field_190598_h = 0;
         }

         ++this.field_190598_h;
         this.field_145850_b.func_175641_c(this.field_174879_c, this.func_195044_w().func_177230_c(), 1, this.field_190598_h);
         if (this.field_190598_h == 1) {
            this.field_145850_b
               .func_184133_a(
                  null,
                  this.field_174879_c,
                  SoundEvents.field_191262_fB,
                  SoundCategory.BLOCKS,
                  0.5F,
                  this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F
               );
         }
      }
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
      if (!☃.func_175149_v()) {
         --this.field_190598_h;
         this.field_145850_b.func_175641_c(this.field_174879_c, this.func_195044_w().func_177230_c(), 1, this.field_190598_h);
         if (this.field_190598_h <= 0) {
            this.field_145850_b
               .func_184133_a(
                  null,
                  this.field_174879_c,
                  SoundEvents.field_191261_fA,
                  SoundCategory.BLOCKS,
                  0.5F,
                  this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F
               );
         }
      }
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerShulkerBox(☃, this, ☃);
   }

   @Override
   public String func_174875_k() {
      return "minecraft:shulker_box";
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      return (ITextComponent)(☃ != null ? ☃ : new TextComponentTranslation("container.shulkerBox"));
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.func_190586_e(☃);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      return this.func_190580_f(☃);
   }

   public void func_190586_e(NBTTagCompound var1) {
      this.field_190596_f = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(☃) && ☃.func_150297_b("Items", 9)) {
         ItemStackHelper.func_191283_b(☃, this.field_190596_f);
      }

      if (☃.func_150297_b("CustomName", 8)) {
         this.field_190577_o = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName"));
      }
   }

   public NBTTagCompound func_190580_f(NBTTagCompound var1) {
      if (!this.func_184282_c(☃)) {
         ItemStackHelper.func_191281_a(☃, this.field_190596_f, false);
      }

      ITextComponent ☃ = this.func_200201_e();
      if (☃ != null) {
         ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(☃));
      }

      if (!☃.func_74764_b("Lock") && this.func_174893_q_()) {
         this.func_174891_i().func_180157_a(☃);
      }

      return ☃;
   }

   @Override
   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_190596_f;
   }

   @Override
   protected void func_199721_a(NonNullList<ItemStack> var1) {
      this.field_190596_f = ☃;
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_190596_f) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int[] func_180463_a(EnumFacing var1) {
      return field_190595_a;
   }

   @Override
   public boolean func_180462_a(int var1, ItemStack var2, @Nullable EnumFacing var3) {
      return !(Block.func_149634_a(☃.func_77973_b()) instanceof BlockShulkerBox);
   }

   @Override
   public boolean func_180461_b(int var1, ItemStack var2, EnumFacing var3) {
      return true;
   }

   @Override
   public void func_174888_l() {
      this.field_190597_g = true;
      super.func_174888_l();
   }

   public boolean func_190590_r() {
      return this.field_190597_g;
   }

   public float func_190585_a(float var1) {
      return this.field_190601_k + (this.field_190600_j - this.field_190601_k) * ☃;
   }

   public EnumDyeColor func_190592_s() {
      if (this.field_204400_o) {
         this.field_190602_l = BlockShulkerBox.func_190954_c(this.func_195044_w().func_177230_c());
         this.field_204400_o = false;
      }

      return this.field_190602_l;
   }

   @Nullable
   @Override
   public SPacketUpdateTileEntity func_189518_D_() {
      return new SPacketUpdateTileEntity(this.field_174879_c, 10, this.func_189517_E_());
   }

   public boolean func_190581_E() {
      return this.field_190594_p;
   }

   public void func_190579_a(boolean var1) {
      this.field_190594_p = ☃;
   }

   public boolean func_190582_F() {
      return !this.func_190581_E() || !this.func_191420_l() || this.func_145818_k_() || this.field_184284_m != null;
   }

   public static enum AnimationStatus {
      CLOSED,
      OPENING,
      OPENED,
      CLOSING;
   }
}
