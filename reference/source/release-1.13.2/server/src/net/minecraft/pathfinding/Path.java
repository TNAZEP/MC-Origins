package net.minecraft.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Path {
   private final PathPoint[] field_75884_a;
   private PathPoint[] field_186312_b = new PathPoint[0];
   private PathPoint[] field_186313_c = new PathPoint[0];
   private PathPoint field_186314_d;
   private int field_75882_b;
   private int field_75883_c;

   public Path(PathPoint[] var1) {
      this.field_75884_a = ☃;
      this.field_75883_c = ☃.length;
   }

   public void func_75875_a() {
      ++this.field_75882_b;
   }

   public boolean func_75879_b() {
      return this.field_75882_b >= this.field_75883_c;
   }

   @Nullable
   public PathPoint func_75870_c() {
      return this.field_75883_c > 0 ? this.field_75884_a[this.field_75883_c - 1] : null;
   }

   public PathPoint func_75877_a(int var1) {
      return this.field_75884_a[☃];
   }

   public void func_186309_a(int var1, PathPoint var2) {
      this.field_75884_a[☃] = ☃;
   }

   public int func_75874_d() {
      return this.field_75883_c;
   }

   public void func_75871_b(int var1) {
      this.field_75883_c = ☃;
   }

   public int func_75873_e() {
      return this.field_75882_b;
   }

   public void func_75872_c(int var1) {
      this.field_75882_b = ☃;
   }

   public Vec3d func_75881_a(Entity var1, int var2) {
      double ☃ = (double)this.field_75884_a[☃].field_75839_a + (double)((int)(☃.field_70130_N + 1.0F)) * 0.5;
      double ☃x = (double)this.field_75884_a[☃].field_75837_b;
      double ☃xx = (double)this.field_75884_a[☃].field_75838_c + (double)((int)(☃.field_70130_N + 1.0F)) * 0.5;
      return new Vec3d(☃, ☃x, ☃xx);
   }

   public Vec3d func_75878_a(Entity var1) {
      return this.func_75881_a(☃, this.field_75882_b);
   }

   public Vec3d func_186310_f() {
      PathPoint ☃ = this.field_75884_a[this.field_75882_b];
      return new Vec3d((double)☃.field_75839_a, (double)☃.field_75837_b, (double)☃.field_75838_c);
   }

   public boolean func_75876_a(Path var1) {
      if (☃ == null) {
         return false;
      } else if (☃.field_75884_a.length != this.field_75884_a.length) {
         return false;
      } else {
         for(int ☃ = 0; ☃ < this.field_75884_a.length; ++☃) {
            if (this.field_75884_a[☃].field_75839_a != ☃.field_75884_a[☃].field_75839_a
               || this.field_75884_a[☃].field_75837_b != ☃.field_75884_a[☃].field_75837_b
               || this.field_75884_a[☃].field_75838_c != ☃.field_75884_a[☃].field_75838_c) {
               return false;
            }
         }

         return true;
      }
   }

   @Nullable
   public PathPoint func_189964_i() {
      return this.field_186314_d;
   }
}
