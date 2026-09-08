package net.minecraft.world.border;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class WorldBorder {
   private final List<IBorderListener> field_177758_a = Lists.<IBorderListener>newArrayList();
   private double field_177763_i = 0.2;
   private double field_177760_j = 5.0;
   private int field_177761_k = 15;
   private int field_177759_l = 5;
   private double field_177756_b;
   private double field_177757_c;
   private int field_177762_h = 29999984;
   private WorldBorder.IBorderInfo field_212674_i = new WorldBorder.StationaryBorderInfo(6.0E7);

   public boolean func_177746_a(BlockPos var1) {
      return (double)(☃.func_177958_n() + 1) > this.func_177726_b()
         && (double)☃.func_177958_n() < this.func_177728_d()
         && (double)(☃.func_177952_p() + 1) > this.func_177736_c()
         && (double)☃.func_177952_p() < this.func_177733_e();
   }

   public boolean func_177730_a(ChunkPos var1) {
      return (double)☃.func_180332_e() > this.func_177726_b()
         && (double)☃.func_180334_c() < this.func_177728_d()
         && (double)☃.func_180330_f() > this.func_177736_c()
         && (double)☃.func_180333_d() < this.func_177733_e();
   }

   public boolean func_177743_a(AxisAlignedBB var1) {
      return ☃.field_72336_d > this.func_177726_b()
         && ☃.field_72340_a < this.func_177728_d()
         && ☃.field_72334_f > this.func_177736_c()
         && ☃.field_72339_c < this.func_177733_e();
   }

   public double func_177745_a(Entity var1) {
      return this.func_177729_b(☃.field_70165_t, ☃.field_70161_v);
   }

   public double func_177729_b(double var1, double var3) {
      double ☃ = ☃ - this.func_177736_c();
      double ☃x = this.func_177733_e() - ☃;
      double ☃xx = ☃ - this.func_177726_b();
      double ☃xxx = this.func_177728_d() - ☃;
      double ☃xxxx = Math.min(☃xx, ☃xxx);
      ☃xxxx = Math.min(☃xxxx, ☃);
      return Math.min(☃xxxx, ☃x);
   }

   public EnumBorderStatus func_177734_a() {
      return this.field_212674_i.func_212655_i();
   }

   public double func_177726_b() {
      return this.field_212674_i.func_212658_a();
   }

   public double func_177736_c() {
      return this.field_212674_i.func_212656_c();
   }

   public double func_177728_d() {
      return this.field_212674_i.func_212654_b();
   }

   public double func_177733_e() {
      return this.field_212674_i.func_212648_d();
   }

   public double func_177731_f() {
      return this.field_177756_b;
   }

   public double func_177721_g() {
      return this.field_177757_c;
   }

   public void func_177739_c(double var1, double var3) {
      this.field_177756_b = ☃;
      this.field_177757_c = ☃;
      this.field_212674_i.func_212653_k();

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177693_a(this, ☃, ☃);
      }
   }

   public double func_177741_h() {
      return this.field_212674_i.func_212647_e();
   }

   public long func_177732_i() {
      return this.field_212674_i.func_212657_g();
   }

   public double func_177751_j() {
      return this.field_212674_i.func_212650_h();
   }

   public void func_177750_a(double var1) {
      this.field_212674_i = new WorldBorder.StationaryBorderInfo(☃);

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177694_a(this, ☃);
      }
   }

   public void func_177738_a(double var1, double var3, long var5) {
      this.field_212674_i = (WorldBorder.IBorderInfo)(☃ != ☃ ? new WorldBorder.MovingBorderInfo(☃, ☃, ☃) : new WorldBorder.StationaryBorderInfo(☃));

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177692_a(this, ☃, ☃, ☃);
      }
   }

   protected List<IBorderListener> func_177735_k() {
      return Lists.<IBorderListener>newArrayList(this.field_177758_a);
   }

   public void func_177737_a(IBorderListener var1) {
      this.field_177758_a.add(☃);
   }

   public void func_177725_a(int var1) {
      this.field_177762_h = ☃;
      this.field_212674_i.func_212652_j();
   }

   public int func_177722_l() {
      return this.field_177762_h;
   }

   public double func_177742_m() {
      return this.field_177760_j;
   }

   public void func_177724_b(double var1) {
      this.field_177760_j = ☃;

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177695_c(this, ☃);
      }
   }

   public double func_177727_n() {
      return this.field_177763_i;
   }

   public void func_177744_c(double var1) {
      this.field_177763_i = ☃;

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177696_b(this, ☃);
      }
   }

   public double func_177749_o() {
      return this.field_212674_i.func_212649_f();
   }

   public int func_177740_p() {
      return this.field_177761_k;
   }

   public void func_177723_b(int var1) {
      this.field_177761_k = ☃;

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177691_a(this, ☃);
      }
   }

   public int func_177748_q() {
      return this.field_177759_l;
   }

   public void func_177747_c(int var1) {
      this.field_177759_l = ☃;

      for(IBorderListener ☃ : this.func_177735_k()) {
         ☃.func_177690_b(this, ☃);
      }
   }

   public void func_212673_r() {
      this.field_212674_i = this.field_212674_i.func_212651_l();
   }

   interface IBorderInfo {
      double func_212658_a();

      double func_212654_b();

      double func_212656_c();

      double func_212648_d();

      double func_212647_e();

      double func_212649_f();

      long func_212657_g();

      double func_212650_h();

      EnumBorderStatus func_212655_i();

      void func_212652_j();

      void func_212653_k();

      WorldBorder.IBorderInfo func_212651_l();
   }

   class MovingBorderInfo implements WorldBorder.IBorderInfo {
      private final double field_212660_b;
      private final double field_212661_c;
      private final long field_212662_d;
      private final long field_212663_e;
      private final double field_212664_f;

      private MovingBorderInfo(double var2, double var4, long var6) {
         this.field_212660_b = ☃;
         this.field_212661_c = ☃;
         this.field_212664_f = (double)☃;
         this.field_212663_e = Util.func_211177_b();
         this.field_212662_d = this.field_212663_e + ☃;
      }

      @Override
      public double func_212658_a() {
         return Math.max(WorldBorder.this.func_177731_f() - this.func_212647_e() / 2.0, (double)(-WorldBorder.this.field_177762_h));
      }

      @Override
      public double func_212656_c() {
         return Math.max(WorldBorder.this.func_177721_g() - this.func_212647_e() / 2.0, (double)(-WorldBorder.this.field_177762_h));
      }

      @Override
      public double func_212654_b() {
         return Math.min(WorldBorder.this.func_177731_f() + this.func_212647_e() / 2.0, (double)WorldBorder.this.field_177762_h);
      }

      @Override
      public double func_212648_d() {
         return Math.min(WorldBorder.this.func_177721_g() + this.func_212647_e() / 2.0, (double)WorldBorder.this.field_177762_h);
      }

      @Override
      public double func_212647_e() {
         double ☃ = (double)(Util.func_211177_b() - this.field_212663_e) / this.field_212664_f;
         return ☃ < 1.0 ? this.field_212660_b + (this.field_212661_c - this.field_212660_b) * ☃ : this.field_212661_c;
      }

      @Override
      public double func_212649_f() {
         return Math.abs(this.field_212660_b - this.field_212661_c) / (double)(this.field_212662_d - this.field_212663_e);
      }

      @Override
      public long func_212657_g() {
         return this.field_212662_d - Util.func_211177_b();
      }

      @Override
      public double func_212650_h() {
         return this.field_212661_c;
      }

      @Override
      public EnumBorderStatus func_212655_i() {
         return this.field_212661_c < this.field_212660_b ? EnumBorderStatus.SHRINKING : EnumBorderStatus.GROWING;
      }

      @Override
      public void func_212653_k() {
      }

      @Override
      public void func_212652_j() {
      }

      @Override
      public WorldBorder.IBorderInfo func_212651_l() {
         return (WorldBorder.IBorderInfo)(this.func_212657_g() <= 0L ? WorldBorder.this.new StationaryBorderInfo(this.field_212661_c) : this);
      }
   }

   class StationaryBorderInfo implements WorldBorder.IBorderInfo {
      private final double field_212667_b;
      private double field_212668_c;
      private double field_212669_d;
      private double field_212670_e;
      private double field_212671_f;

      public StationaryBorderInfo(double var2) {
         this.field_212667_b = ☃;
         this.func_212665_m();
      }

      @Override
      public double func_212658_a() {
         return this.field_212668_c;
      }

      @Override
      public double func_212654_b() {
         return this.field_212670_e;
      }

      @Override
      public double func_212656_c() {
         return this.field_212669_d;
      }

      @Override
      public double func_212648_d() {
         return this.field_212671_f;
      }

      @Override
      public double func_212647_e() {
         return this.field_212667_b;
      }

      @Override
      public EnumBorderStatus func_212655_i() {
         return EnumBorderStatus.STATIONARY;
      }

      @Override
      public double func_212649_f() {
         return 0.0;
      }

      @Override
      public long func_212657_g() {
         return 0L;
      }

      @Override
      public double func_212650_h() {
         return this.field_212667_b;
      }

      private void func_212665_m() {
         this.field_212668_c = Math.max(WorldBorder.this.func_177731_f() - this.field_212667_b / 2.0, (double)(-WorldBorder.this.field_177762_h));
         this.field_212669_d = Math.max(WorldBorder.this.func_177721_g() - this.field_212667_b / 2.0, (double)(-WorldBorder.this.field_177762_h));
         this.field_212670_e = Math.min(WorldBorder.this.func_177731_f() + this.field_212667_b / 2.0, (double)WorldBorder.this.field_177762_h);
         this.field_212671_f = Math.min(WorldBorder.this.func_177721_g() + this.field_212667_b / 2.0, (double)WorldBorder.this.field_177762_h);
      }

      @Override
      public void func_212652_j() {
         this.func_212665_m();
      }

      @Override
      public void func_212653_k() {
         this.func_212665_m();
      }

      @Override
      public WorldBorder.IBorderInfo func_212651_l() {
         return this;
      }
   }
}
