package net.minecraft.util.math;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {
   protected final float field_179419_a;
   protected final float field_179417_b;
   protected final float field_179418_c;

   public Rotations(float var1, float var2, float var3) {
      this.field_179419_a = !Float.isInfinite(☃) && !Float.isNaN(☃) ? ☃ % 360.0F : 0.0F;
      this.field_179417_b = !Float.isInfinite(☃) && !Float.isNaN(☃) ? ☃ % 360.0F : 0.0F;
      this.field_179418_c = !Float.isInfinite(☃) && !Float.isNaN(☃) ? ☃ % 360.0F : 0.0F;
   }

   public Rotations(NBTTagList var1) {
      this(☃.func_150308_e(0), ☃.func_150308_e(1), ☃.func_150308_e(2));
   }

   public NBTTagList func_179414_a() {
      NBTTagList ☃ = new NBTTagList();
      ☃.add((INBTBase)(new NBTTagFloat(this.field_179419_a)));
      ☃.add((INBTBase)(new NBTTagFloat(this.field_179417_b)));
      ☃.add((INBTBase)(new NBTTagFloat(this.field_179418_c)));
      return ☃;
   }

   public boolean equals(Object var1) {
      if (!(☃ instanceof Rotations)) {
         return false;
      } else {
         Rotations ☃ = (Rotations)☃;
         return this.field_179419_a == ☃.field_179419_a && this.field_179417_b == ☃.field_179417_b && this.field_179418_c == ☃.field_179418_c;
      }
   }

   public float func_179415_b() {
      return this.field_179419_a;
   }

   public float func_179416_c() {
      return this.field_179417_b;
   }

   public float func_179413_d() {
      return this.field_179418_c;
   }
}
