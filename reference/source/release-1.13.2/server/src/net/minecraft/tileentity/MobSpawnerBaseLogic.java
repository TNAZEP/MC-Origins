package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Particles;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MobSpawnerBaseLogic {
   private static final Logger field_209160_a = LogManager.getLogger();
   private int field_98286_b = 20;
   private final List<WeightedSpawnerEntity> field_98285_e = Lists.<WeightedSpawnerEntity>newArrayList();
   private WeightedSpawnerEntity field_98282_f = new WeightedSpawnerEntity();
   private double field_98287_c;
   private double field_98284_d;
   private int field_98283_g = 200;
   private int field_98293_h = 800;
   private int field_98294_i = 4;
   private Entity field_98291_j;
   private int field_98292_k = 6;
   private int field_98289_l = 16;
   private int field_98290_m = 4;

   @Nullable
   private ResourceLocation func_190895_g() {
      String ☃ = this.field_98282_f.func_185277_b().func_74779_i("id");

      try {
         return StringUtils.func_151246_b(☃) ? null : new ResourceLocation(☃);
      } catch (ResourceLocationException var4) {
         BlockPos ☃x = this.func_177221_b();
         field_209160_a.warn(
            "Invalid entity id '{}' at spawner {}:[{},{},{}]",
            ☃,
            this.func_98271_a().field_73011_w.func_186058_p(),
            ☃x.func_177958_n(),
            ☃x.func_177956_o(),
            ☃x.func_177952_p()
         );
         return null;
      }
   }

   public void func_200876_a(EntityType<?> var1) {
      this.field_98282_f.func_185277_b().func_74778_a("id", IRegistry.field_212629_r.func_177774_c(☃).toString());
   }

   private boolean func_98279_f() {
      BlockPos ☃ = this.func_177221_b();
      return this.func_98271_a()
         .func_212417_b((double)☃.func_177958_n() + 0.5, (double)☃.func_177956_o() + 0.5, (double)☃.func_177952_p() + 0.5, (double)this.field_98289_l);
   }

   public void func_98278_g() {
      if (!this.func_98279_f()) {
         this.field_98284_d = this.field_98287_c;
      } else {
         BlockPos ☃ = this.func_177221_b();
         if (this.func_98271_a().field_72995_K) {
            double ☃x = (double)((float)☃.func_177958_n() + this.func_98271_a().field_73012_v.nextFloat());
            double ☃xx = (double)((float)☃.func_177956_o() + this.func_98271_a().field_73012_v.nextFloat());
            double ☃xxx = (double)((float)☃.func_177952_p() + this.func_98271_a().field_73012_v.nextFloat());
            this.func_98271_a().func_195594_a(Particles.field_197601_L, ☃x, ☃xx, ☃xxx, 0.0, 0.0, 0.0);
            this.func_98271_a().func_195594_a(Particles.field_197631_x, ☃x, ☃xx, ☃xxx, 0.0, 0.0, 0.0);
            if (this.field_98286_b > 0) {
               --this.field_98286_b;
            }

            this.field_98284_d = this.field_98287_c;
            this.field_98287_c = (this.field_98287_c + (double)(1000.0F / ((float)this.field_98286_b + 200.0F))) % 360.0;
         } else {
            if (this.field_98286_b == -1) {
               this.func_98273_j();
            }

            if (this.field_98286_b > 0) {
               --this.field_98286_b;
               return;
            }

            boolean ☃ = false;

            for(int ☃x = 0; ☃x < this.field_98294_i; ++☃x) {
               NBTTagCompound ☃xx = this.field_98282_f.func_185277_b();
               NBTTagList ☃xxx = ☃xx.func_150295_c("Pos", 6);
               World ☃xxxx = this.func_98271_a();
               int ☃xxxxx = ☃xxx.size();
               double ☃xxxxxx = ☃xxxxx >= 1
                  ? ☃xxx.func_150309_d(0)
                  : (double)☃.func_177958_n() + (☃xxxx.field_73012_v.nextDouble() - ☃xxxx.field_73012_v.nextDouble()) * (double)this.field_98290_m + 0.5;
               double ☃xxxxxxx = ☃xxxxx >= 2 ? ☃xxx.func_150309_d(1) : (double)(☃.func_177956_o() + ☃xxxx.field_73012_v.nextInt(3) - 1);
               double ☃xxxxxxxx = ☃xxxxx >= 3
                  ? ☃xxx.func_150309_d(2)
                  : (double)☃.func_177952_p() + (☃xxxx.field_73012_v.nextDouble() - ☃xxxx.field_73012_v.nextDouble()) * (double)this.field_98290_m + 0.5;
               Entity ☃xxxxxxxxx = AnvilChunkLoader.func_186054_a(☃xx, ☃xxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, false);
               if (☃xxxxxxxxx == null) {
                  this.func_98273_j();
                  return;
               }

               int ☃xx = ☃xxxx.func_72872_a(
                     ☃xxxxxxxxx.getClass(),
                     new AxisAlignedBB(
                           (double)☃.func_177958_n(),
                           (double)☃.func_177956_o(),
                           (double)☃.func_177952_p(),
                           (double)(☃.func_177958_n() + 1),
                           (double)(☃.func_177956_o() + 1),
                           (double)(☃.func_177952_p() + 1)
                        )
                        .func_186662_g((double)this.field_98290_m)
                  )
                  .size();
               if (☃xx >= this.field_98292_k) {
                  this.func_98273_j();
                  return;
               }

               EntityLiving ☃xx = ☃xxxxxxxxx instanceof EntityLiving ? (EntityLiving)☃xxxxxxxxx : null;
               ☃xxxxxxxxx.func_70012_b(
                  ☃xxxxxxxxx.field_70165_t, ☃xxxxxxxxx.field_70163_u, ☃xxxxxxxxx.field_70161_v, ☃xxxx.field_73012_v.nextFloat() * 360.0F, 0.0F
               );
               if (☃xx == null || ☃xx.func_205020_a(☃xxxx, true) && ☃xx.func_70058_J()) {
                  if (this.field_98282_f.func_185277_b().func_186856_d() == 1
                     && this.field_98282_f.func_185277_b().func_150297_b("id", 8)
                     && ☃xxxxxxxxx instanceof EntityLiving) {
                     ((EntityLiving)☃xxxxxxxxx).func_204210_a(☃xxxx.func_175649_E(new BlockPos(☃xxxxxxxxx)), null, null);
                  }

                  AnvilChunkLoader.func_186052_a(☃xxxxxxxxx, ☃xxxx);
                  ☃xxxx.func_175718_b(2004, ☃, 0);
                  if (☃xx != null) {
                     ☃xx.func_70656_aK();
                  }

                  ☃ = true;
               }
            }

            if (☃) {
               this.func_98273_j();
            }
         }
      }
   }

   private void func_98273_j() {
      if (this.field_98293_h <= this.field_98283_g) {
         this.field_98286_b = this.field_98283_g;
      } else {
         this.field_98286_b = this.field_98283_g + this.func_98271_a().field_73012_v.nextInt(this.field_98293_h - this.field_98283_g);
      }

      if (!this.field_98285_e.isEmpty()) {
         this.func_184993_a(WeightedRandom.func_76271_a(this.func_98271_a().field_73012_v, this.field_98285_e));
      }

      this.func_98267_a(1);
   }

   public void func_98270_a(NBTTagCompound var1) {
      this.field_98286_b = ☃.func_74765_d("Delay");
      this.field_98285_e.clear();
      if (☃.func_150297_b("SpawnPotentials", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("SpawnPotentials", 10);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_98285_e.add(new WeightedSpawnerEntity(☃.func_150305_b(☃x)));
         }
      }

      if (☃.func_150297_b("SpawnData", 10)) {
         this.func_184993_a(new WeightedSpawnerEntity(1, ☃.func_74775_l("SpawnData")));
      } else if (!this.field_98285_e.isEmpty()) {
         this.func_184993_a(WeightedRandom.func_76271_a(this.func_98271_a().field_73012_v, this.field_98285_e));
      }

      if (☃.func_150297_b("MinSpawnDelay", 99)) {
         this.field_98283_g = ☃.func_74765_d("MinSpawnDelay");
         this.field_98293_h = ☃.func_74765_d("MaxSpawnDelay");
         this.field_98294_i = ☃.func_74765_d("SpawnCount");
      }

      if (☃.func_150297_b("MaxNearbyEntities", 99)) {
         this.field_98292_k = ☃.func_74765_d("MaxNearbyEntities");
         this.field_98289_l = ☃.func_74765_d("RequiredPlayerRange");
      }

      if (☃.func_150297_b("SpawnRange", 99)) {
         this.field_98290_m = ☃.func_74765_d("SpawnRange");
      }

      if (this.func_98271_a() != null) {
         this.field_98291_j = null;
      }
   }

   public NBTTagCompound func_189530_b(NBTTagCompound var1) {
      ResourceLocation ☃ = this.func_190895_g();
      if (☃ == null) {
         return ☃;
      } else {
         ☃.func_74777_a("Delay", (short)this.field_98286_b);
         ☃.func_74777_a("MinSpawnDelay", (short)this.field_98283_g);
         ☃.func_74777_a("MaxSpawnDelay", (short)this.field_98293_h);
         ☃.func_74777_a("SpawnCount", (short)this.field_98294_i);
         ☃.func_74777_a("MaxNearbyEntities", (short)this.field_98292_k);
         ☃.func_74777_a("RequiredPlayerRange", (short)this.field_98289_l);
         ☃.func_74777_a("SpawnRange", (short)this.field_98290_m);
         ☃.func_74782_a("SpawnData", this.field_98282_f.func_185277_b().func_74737_b());
         NBTTagList ☃ = new NBTTagList();
         if (this.field_98285_e.isEmpty()) {
            ☃.add((INBTBase)this.field_98282_f.func_185278_a());
         } else {
            for(WeightedSpawnerEntity ☃ : this.field_98285_e) {
               ☃.add((INBTBase)☃.func_185278_a());
            }
         }

         ☃.func_74782_a("SpawnPotentials", ☃);
         return ☃;
      }
   }

   public boolean func_98268_b(int var1) {
      if (☃ == 1 && this.func_98271_a().field_72995_K) {
         this.field_98286_b = this.field_98283_g;
         return true;
      } else {
         return false;
      }
   }

   public void func_184993_a(WeightedSpawnerEntity var1) {
      this.field_98282_f = ☃;
   }

   public abstract void func_98267_a(int var1);

   public abstract World func_98271_a();

   public abstract BlockPos func_177221_b();
}
