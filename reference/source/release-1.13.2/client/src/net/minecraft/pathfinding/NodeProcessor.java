package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public abstract class NodeProcessor {
   protected IBlockReader field_176169_a;
   protected EntityLiving field_186326_b;
   protected final IntHashMap<PathPoint> field_176167_b = new IntHashMap<>();
   protected int field_176168_c;
   protected int field_176165_d;
   protected int field_176166_e;
   protected boolean field_176180_f;
   protected boolean field_176181_g;
   protected boolean field_176184_i;

   public void func_186315_a(IBlockReader var1, EntityLiving var2) {
      this.field_176169_a = ☃;
      this.field_186326_b = ☃;
      this.field_176167_b.func_76046_c();
      this.field_176168_c = MathHelper.func_76141_d(☃.field_70130_N + 1.0F);
      this.field_176165_d = MathHelper.func_76141_d(☃.field_70131_O + 1.0F);
      this.field_176166_e = MathHelper.func_76141_d(☃.field_70130_N + 1.0F);
   }

   public void func_176163_a() {
      this.field_176169_a = null;
      this.field_186326_b = null;
   }

   protected PathPoint func_176159_a(int var1, int var2, int var3) {
      int ☃ = PathPoint.func_75830_a(☃, ☃, ☃);
      PathPoint ☃x = this.field_176167_b.func_76041_a(☃);
      if (☃x == null) {
         ☃x = new PathPoint(☃, ☃, ☃);
         this.field_176167_b.func_76038_a(☃, ☃x);
      }

      return ☃x;
   }

   public abstract PathPoint func_186318_b();

   public abstract PathPoint func_186325_a(double var1, double var3, double var5);

   public abstract int func_186320_a(PathPoint[] var1, PathPoint var2, PathPoint var3, float var4);

   public abstract PathNodeType func_186319_a(
      IBlockReader var1, int var2, int var3, int var4, EntityLiving var5, int var6, int var7, int var8, boolean var9, boolean var10
   );

   public abstract PathNodeType func_186330_a(IBlockReader var1, int var2, int var3, int var4);

   public void func_186317_a(boolean var1) {
      this.field_176180_f = ☃;
   }

   public void func_186321_b(boolean var1) {
      this.field_176181_g = ☃;
   }

   public void func_186316_c(boolean var1) {
      this.field_176184_i = ☃;
   }

   public boolean func_186323_c() {
      return this.field_176180_f;
   }

   public boolean func_186324_d() {
      return this.field_176181_g;
   }

   public boolean func_186322_e() {
      return this.field_176184_i;
   }
}
