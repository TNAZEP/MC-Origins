package net.minecraft.entity.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Particles;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class EntityAIMate extends EntityAIBase {
   protected final EntityAnimal field_75390_d;
   private final Class<? extends EntityAnimal> field_190857_e;
   protected World field_75394_a;
   protected EntityAnimal field_75391_e;
   private int field_75392_b;
   private final double field_75393_c;

   public EntityAIMate(EntityAnimal var1, double var2) {
      this(☃, ☃, ☃.getClass());
   }

   public EntityAIMate(EntityAnimal var1, double var2, Class<? extends EntityAnimal> var4) {
      this.field_75390_d = ☃;
      this.field_75394_a = ☃.field_70170_p;
      this.field_190857_e = ☃;
      this.field_75393_c = ☃;
      this.func_75248_a(3);
   }

   @Override
   public boolean func_75250_a() {
      if (!this.field_75390_d.func_70880_s()) {
         return false;
      } else {
         this.field_75391_e = this.func_75389_f();
         return this.field_75391_e != null;
      }
   }

   @Override
   public boolean func_75253_b() {
      return this.field_75391_e.func_70089_S() && this.field_75391_e.func_70880_s() && this.field_75392_b < 60;
   }

   @Override
   public void func_75251_c() {
      this.field_75391_e = null;
      this.field_75392_b = 0;
   }

   @Override
   public void func_75246_d() {
      this.field_75390_d.func_70671_ap().func_75651_a(this.field_75391_e, 10.0F, (float)this.field_75390_d.func_70646_bf());
      this.field_75390_d.func_70661_as().func_75497_a(this.field_75391_e, this.field_75393_c);
      ++this.field_75392_b;
      if (this.field_75392_b >= 60 && this.field_75390_d.func_70068_e(this.field_75391_e) < 9.0) {
         this.func_75388_i();
      }
   }

   private EntityAnimal func_75389_f() {
      List<EntityAnimal> ☃ = this.field_75394_a.func_72872_a(this.field_190857_e, this.field_75390_d.func_174813_aQ().func_186662_g(8.0));
      double ☃x = Double.MAX_VALUE;
      EntityAnimal ☃xx = null;

      for(EntityAnimal ☃xxx : ☃) {
         if (this.field_75390_d.func_70878_b(☃xxx) && this.field_75390_d.func_70068_e(☃xxx) < ☃x) {
            ☃xx = ☃xxx;
            ☃x = this.field_75390_d.func_70068_e(☃xxx);
         }
      }

      return ☃xx;
   }

   protected void func_75388_i() {
      EntityAgeable ☃ = this.field_75390_d.func_90011_a(this.field_75391_e);
      if (☃ != null) {
         EntityPlayerMP ☃x = this.field_75390_d.func_191993_do();
         if (☃x == null && this.field_75391_e.func_191993_do() != null) {
            ☃x = this.field_75391_e.func_191993_do();
         }

         if (☃x != null) {
            ☃x.func_195066_a(StatList.field_151186_x);
            CriteriaTriggers.field_192134_n.func_192168_a(☃x, this.field_75390_d, this.field_75391_e, ☃);
         }

         this.field_75390_d.func_70873_a(6000);
         this.field_75391_e.func_70873_a(6000);
         this.field_75390_d.func_70875_t();
         this.field_75391_e.func_70875_t();
         ☃.func_70873_a(-24000);
         ☃.func_70012_b(this.field_75390_d.field_70165_t, this.field_75390_d.field_70163_u, this.field_75390_d.field_70161_v, 0.0F, 0.0F);
         this.field_75394_a.func_72838_d(☃);
         Random ☃x = this.field_75390_d.func_70681_au();

         for(int ☃xx = 0; ☃xx < 7; ++☃xx) {
            double ☃xxx = ☃x.nextGaussian() * 0.02;
            double ☃xxxx = ☃x.nextGaussian() * 0.02;
            double ☃xxxxx = ☃x.nextGaussian() * 0.02;
            double ☃xxxxxx = ☃x.nextDouble() * (double)this.field_75390_d.field_70130_N * 2.0 - (double)this.field_75390_d.field_70130_N;
            double ☃xxxxxxx = 0.5 + ☃x.nextDouble() * (double)this.field_75390_d.field_70131_O;
            double ☃xxxxxxxx = ☃x.nextDouble() * (double)this.field_75390_d.field_70130_N * 2.0 - (double)this.field_75390_d.field_70130_N;
            this.field_75394_a
               .func_195594_a(
                  Particles.field_197633_z,
                  this.field_75390_d.field_70165_t + ☃xxxxxx,
                  this.field_75390_d.field_70163_u + ☃xxxxxxx,
                  this.field_75390_d.field_70161_v + ☃xxxxxxxx,
                  ☃xxx,
                  ☃xxxx,
                  ☃xxxxx
               );
         }

         if (this.field_75394_a.func_82736_K().func_82766_b("doMobLoot")) {
            this.field_75394_a
               .func_72838_d(
                  new EntityXPOrb(
                     this.field_75394_a,
                     this.field_75390_d.field_70165_t,
                     this.field_75390_d.field_70163_u,
                     this.field_75390_d.field_70161_v,
                     ☃x.nextInt(7) + 1
                  )
               );
         }
      }
   }
}
