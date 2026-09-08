package net.minecraft.item;

import java.util.function.Supplier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.SoundEvent;

public enum ArmorMaterial implements IArmorMaterial {
   LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.field_187728_s, 0.0F, () -> Ingredient.func_199804_a(Items.field_151116_aA)),
   CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.field_187713_n, 0.0F, () -> Ingredient.func_199804_a(Items.field_151042_j)),
   IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.field_187725_r, 0.0F, () -> Ingredient.func_199804_a(Items.field_151042_j)),
   GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.field_187722_q, 0.0F, () -> Ingredient.func_199804_a(Items.field_151043_k)),
   DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.field_187716_o, 2.0F, () -> Ingredient.func_199804_a(Items.field_151045_i)),
   TURTLE("turtle", 25, new int[]{2, 5, 6, 2}, 9, SoundEvents.field_203254_u, 0.0F, () -> Ingredient.func_199804_a(Items.field_203183_eM));

   private static final int[] field_77882_bY = new int[]{13, 15, 16, 11};
   private final String field_179243_f;
   private final int field_78048_f;
   private final int[] field_78049_g;
   private final int field_78055_h;
   private final SoundEvent field_185020_j;
   private final float field_189417_k;
   private final LazyLoadBase<Ingredient> field_200914_m;

   private ArmorMaterial(String var3, int var4, int[] var5, int var6, SoundEvent var7, float var8, Supplier<Ingredient> var9) {
      this.field_179243_f = ☃;
      this.field_78048_f = ☃;
      this.field_78049_g = ☃;
      this.field_78055_h = ☃;
      this.field_185020_j = ☃;
      this.field_189417_k = ☃;
      this.field_200914_m = new LazyLoadBase<>(☃);
   }

   @Override
   public int func_200896_a(EntityEquipmentSlot var1) {
      return field_77882_bY[☃.func_188454_b()] * this.field_78048_f;
   }

   @Override
   public int func_200902_b(EntityEquipmentSlot var1) {
      return this.field_78049_g[☃.func_188454_b()];
   }

   @Override
   public int func_200900_a() {
      return this.field_78055_h;
   }

   @Override
   public SoundEvent func_200899_b() {
      return this.field_185020_j;
   }

   @Override
   public Ingredient func_200898_c() {
      return this.field_200914_m.func_179281_c();
   }

   @Override
   public float func_200901_e() {
      return this.field_189417_k;
   }
}
