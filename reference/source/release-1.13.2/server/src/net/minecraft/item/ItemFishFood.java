package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFishFood extends ItemFood {
   private final boolean field_150907_b;
   private final ItemFishFood.FishType field_195971_c;

   public ItemFishFood(ItemFishFood.FishType var1, boolean var2, Item.Properties var3) {
      super(0, 0.0F, false, ☃);
      this.field_195971_c = ☃;
      this.field_150907_b = ☃;
   }

   @Override
   public int func_150905_g(ItemStack var1) {
      ItemFishFood.FishType ☃ = ItemFishFood.FishType.func_150978_a(☃);
      return this.field_150907_b && ☃.func_150973_i() ? ☃.func_150970_e() : ☃.func_150975_c();
   }

   @Override
   public float func_150906_h(ItemStack var1) {
      return this.field_150907_b && this.field_195971_c.func_150973_i() ? this.field_195971_c.func_150977_f() : this.field_195971_c.func_150967_d();
   }

   @Override
   protected void func_77849_c(ItemStack var1, World var2, EntityPlayer var3) {
      ItemFishFood.FishType ☃ = ItemFishFood.FishType.func_150978_a(☃);
      if (☃ == ItemFishFood.FishType.PUFFERFISH) {
         ☃.func_195064_c(new PotionEffect(MobEffects.field_76436_u, 1200, 3));
         ☃.func_195064_c(new PotionEffect(MobEffects.field_76438_s, 300, 2));
         ☃.func_195064_c(new PotionEffect(MobEffects.field_76431_k, 300, 1));
      }

      super.func_77849_c(☃, ☃, ☃);
   }

   public static enum FishType {
      COD(2, 0.1F, 5, 0.6F),
      SALMON(2, 0.1F, 6, 0.8F),
      TROPICAL_FISH(1, 0.1F),
      PUFFERFISH(1, 0.1F);

      private final int field_150991_j;
      private final float field_150992_k;
      private final int field_150989_l;
      private final float field_150990_m;
      private final boolean field_150987_n;

      private FishType(int var3, float var4, int var5, float var6) {
         this.field_150991_j = ☃;
         this.field_150992_k = ☃;
         this.field_150989_l = ☃;
         this.field_150990_m = ☃;
         this.field_150987_n = ☃ != 0;
      }

      private FishType(int var3, float var4) {
         this(☃, ☃, 0, 0.0F);
      }

      public int func_150975_c() {
         return this.field_150991_j;
      }

      public float func_150967_d() {
         return this.field_150992_k;
      }

      public int func_150970_e() {
         return this.field_150989_l;
      }

      public float func_150977_f() {
         return this.field_150990_m;
      }

      public boolean func_150973_i() {
         return this.field_150987_n;
      }

      public static ItemFishFood.FishType func_150978_a(ItemStack var0) {
         Item ☃ = ☃.func_77973_b();
         return ☃ instanceof ItemFishFood ? ((ItemFishFood)☃).field_195971_c : COD;
      }
   }
}
