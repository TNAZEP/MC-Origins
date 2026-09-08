package net.minecraft.tileentity;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.ILootContainer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;

public abstract class TileEntityLockableLoot extends TileEntityLockable implements ILootContainer {
   protected ResourceLocation field_184284_m;
   protected long field_184285_n;
   protected ITextComponent field_190577_o;

   protected TileEntityLockableLoot(TileEntityType<?> var1) {
      super(☃);
   }

   public static void func_195479_a(IBlockReader var0, Random var1, BlockPos var2, ResourceLocation var3) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityLockableLoot) {
         ((TileEntityLockableLoot)☃).func_189404_a(☃, ☃.nextLong());
      }
   }

   protected boolean func_184283_b(NBTTagCompound var1) {
      if (☃.func_150297_b("LootTable", 8)) {
         this.field_184284_m = new ResourceLocation(☃.func_74779_i("LootTable"));
         this.field_184285_n = ☃.func_74763_f("LootTableSeed");
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_184282_c(NBTTagCompound var1) {
      if (this.field_184284_m == null) {
         return false;
      } else {
         ☃.func_74778_a("LootTable", this.field_184284_m.toString());
         if (this.field_184285_n != 0L) {
            ☃.func_74772_a("LootTableSeed", this.field_184285_n);
         }

         return true;
      }
   }

   public void func_184281_d(@Nullable EntityPlayer var1) {
      if (this.field_184284_m != null && this.field_145850_b.func_73046_m() != null) {
         LootTable ☃x = this.field_145850_b.func_73046_m().func_200249_aQ().func_186521_a(this.field_184284_m);
         this.field_184284_m = null;
         Random ☃;
         if (this.field_184285_n == 0L) {
            ☃ = new Random();
         } else {
            ☃ = new Random(this.field_184285_n);
         }

         LootContext.Builder ☃ = new LootContext.Builder((WorldServer)this.field_145850_b);
         ☃.func_204313_a(this.field_174879_c);
         if (☃ != null) {
            ☃.func_186469_a(☃.func_184817_da());
         }

         ☃x.func_186460_a(this, ☃, ☃.func_186471_a());
      }
   }

   @Override
   public ResourceLocation func_184276_b() {
      return this.field_184284_m;
   }

   public void func_189404_a(ResourceLocation var1, long var2) {
      this.field_184284_m = ☃;
      this.field_184285_n = ☃;
   }

   @Override
   public boolean func_145818_k_() {
      return this.field_190577_o != null;
   }

   public void func_200226_a(@Nullable ITextComponent var1) {
      this.field_190577_o = ☃;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_190577_o;
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      this.func_184281_d(null);
      return this.func_190576_q().get(☃);
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      this.func_184281_d(null);
      ItemStack ☃ = ItemStackHelper.func_188382_a(this.func_190576_q(), ☃, ☃);
      if (!☃.func_190926_b()) {
         this.func_70296_d();
      }

      return ☃;
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      this.func_184281_d(null);
      return ItemStackHelper.func_188383_a(this.func_190576_q(), ☃);
   }

   @Override
   public void func_70299_a(int var1, @Nullable ItemStack var2) {
      this.func_184281_d(null);
      this.func_190576_q().set(☃, ☃);
      if (☃.func_190916_E() > this.func_70297_j_()) {
         ☃.func_190920_e(this.func_70297_j_());
      }

      this.func_70296_d();
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return !(
            ☃.func_70092_e(
                  (double)this.field_174879_c.func_177958_n() + 0.5,
                  (double)this.field_174879_c.func_177956_o() + 0.5,
                  (double)this.field_174879_c.func_177952_p() + 0.5
               )
               > 64.0
         );
      }
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
   }

   @Override
   public boolean func_94041_b(int var1, ItemStack var2) {
      return true;
   }

   @Override
   public int func_174887_a_(int var1) {
      return 0;
   }

   @Override
   public void func_174885_b(int var1, int var2) {
   }

   @Override
   public int func_174890_g() {
      return 0;
   }

   @Override
   public void func_174888_l() {
      this.func_190576_q().clear();
   }

   protected abstract NonNullList<ItemStack> func_190576_q();

   protected abstract void func_199721_a(NonNullList<ItemStack> var1);
}
