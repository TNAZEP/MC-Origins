package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockReader;

public class TileEntityChest extends TileEntityLockableLoot implements ITickable {
   private NonNullList<ItemStack> field_145985_p = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
   protected float field_145989_m;
   protected float field_145986_n;
   protected int field_145987_o;
   private int field_145983_q;

   protected TileEntityChest(TileEntityType<?> var1) {
      super(☃);
   }

   public TileEntityChest() {
      this(TileEntityType.field_200972_c);
   }

   @Override
   public int func_70302_i_() {
      return 27;
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_145985_p) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      return (ITextComponent)(☃ != null ? ☃ : new TextComponentTranslation("container.chest"));
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145985_p = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(☃)) {
         ItemStackHelper.func_191283_b(☃, this.field_145985_p);
      }

      if (☃.func_150297_b("CustomName", 8)) {
         this.field_190577_o = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName"));
      }
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      if (!this.func_184282_c(☃)) {
         ItemStackHelper.func_191282_a(☃, this.field_145985_p);
      }

      ITextComponent ☃ = this.func_200201_e();
      if (☃ != null) {
         ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(☃));
      }

      return ☃;
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public void func_73660_a() {
      int ☃ = this.field_174879_c.func_177958_n();
      int ☃x = this.field_174879_c.func_177956_o();
      int ☃xx = this.field_174879_c.func_177952_p();
      ++this.field_145983_q;
      if (!this.field_145850_b.field_72995_K && this.field_145987_o != 0 && (this.field_145983_q + ☃ + ☃x + ☃xx) % 200 == 0) {
         this.field_145987_o = 0;
         float ☃xxx = 5.0F;

         for(EntityPlayer ☃xxxx : this.field_145850_b
            .func_72872_a(
               EntityPlayer.class,
               new AxisAlignedBB(
                  (double)((float)☃ - 5.0F),
                  (double)((float)☃x - 5.0F),
                  (double)((float)☃xx - 5.0F),
                  (double)((float)(☃ + 1) + 5.0F),
                  (double)((float)(☃x + 1) + 5.0F),
                  (double)((float)(☃xx + 1) + 5.0F)
               )
            )) {
            if (☃xxxx.field_71070_bA instanceof ContainerChest) {
               IInventory ☃xxxxx = ((ContainerChest)☃xxxx.field_71070_bA).func_85151_d();
               if (☃xxxxx == this || ☃xxxxx instanceof InventoryLargeChest && ((InventoryLargeChest)☃xxxxx).func_90010_a(this)) {
                  ++this.field_145987_o;
               }
            }
         }
      }

      this.field_145986_n = this.field_145989_m;
      float ☃ = 0.1F;
      if (this.field_145987_o > 0 && this.field_145989_m == 0.0F) {
         this.func_195483_a(SoundEvents.field_187657_V);
      }

      if (this.field_145987_o == 0 && this.field_145989_m > 0.0F || this.field_145987_o > 0 && this.field_145989_m < 1.0F) {
         float ☃ = this.field_145989_m;
         if (this.field_145987_o > 0) {
            this.field_145989_m += 0.1F;
         } else {
            this.field_145989_m -= 0.1F;
         }

         if (this.field_145989_m > 1.0F) {
            this.field_145989_m = 1.0F;
         }

         float ☃ = 0.5F;
         if (this.field_145989_m < 0.5F && ☃ >= 0.5F) {
            this.func_195483_a(SoundEvents.field_187651_T);
         }

         if (this.field_145989_m < 0.0F) {
            this.field_145989_m = 0.0F;
         }
      }
   }

   private void func_195483_a(SoundEvent var1) {
      ChestType ☃ = this.func_195044_w().func_177229_b(BlockChest.field_196314_b);
      if (☃ != ChestType.LEFT) {
         double ☃x = (double)this.field_174879_c.func_177958_n() + 0.5;
         double ☃xx = (double)this.field_174879_c.func_177956_o() + 0.5;
         double ☃xxx = (double)this.field_174879_c.func_177952_p() + 0.5;
         if (☃ == ChestType.RIGHT) {
            EnumFacing ☃xxxx = BlockChest.func_196311_i(this.func_195044_w());
            ☃x += (double)☃xxxx.func_82601_c() * 0.5;
            ☃xxx += (double)☃xxxx.func_82599_e() * 0.5;
         }

         this.field_145850_b.func_184148_a(null, ☃x, ☃xx, ☃xxx, ☃, SoundCategory.BLOCKS, 0.5F, this.field_145850_b.field_73012_v.nextFloat() * 0.1F + 0.9F);
      }
   }

   @Override
   public boolean func_145842_c(int var1, int var2) {
      if (☃ == 1) {
         this.field_145987_o = ☃;
         return true;
      } else {
         return super.func_145842_c(☃, ☃);
      }
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
      if (!☃.func_175149_v()) {
         if (this.field_145987_o < 0) {
            this.field_145987_o = 0;
         }

         ++this.field_145987_o;
         this.func_195482_p();
      }
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
      if (!☃.func_175149_v()) {
         --this.field_145987_o;
         this.func_195482_p();
      }
   }

   protected void func_195482_p() {
      Block ☃ = this.func_195044_w().func_177230_c();
      if (☃ instanceof BlockChest) {
         this.field_145850_b.func_175641_c(this.field_174879_c, ☃, 1, this.field_145987_o);
         this.field_145850_b.func_195593_d(this.field_174879_c, ☃);
      }
   }

   @Override
   public String func_174875_k() {
      return "minecraft:chest";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      this.func_184281_d(☃);
      return new ContainerChest(☃, this, ☃);
   }

   @Override
   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_145985_p;
   }

   @Override
   protected void func_199721_a(NonNullList<ItemStack> var1) {
      this.field_145985_p = ☃;
   }

   public static int func_195481_a(IBlockReader var0, BlockPos var1) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      if (☃.func_177230_c().func_149716_u()) {
         TileEntity ☃x = ☃.func_175625_s(☃);
         if (☃x instanceof TileEntityChest) {
            return ((TileEntityChest)☃x).field_145987_o;
         }
      }

      return 0;
   }

   public static void func_199722_a(TileEntityChest var0, TileEntityChest var1) {
      NonNullList<ItemStack> ☃ = ☃.func_190576_q();
      ☃.func_199721_a(☃.func_190576_q());
      ☃.func_199721_a(☃);
   }
}
