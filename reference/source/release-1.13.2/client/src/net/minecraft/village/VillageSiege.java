package net.minecraft.village;

import javax.annotation.Nullable;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;

public class VillageSiege {
   private final World field_75537_a;
   private boolean field_75535_b;
   private int field_75536_c = -1;
   private int field_75533_d;
   private int field_75534_e;
   private Village field_75531_f;
   private int field_75532_g;
   private int field_75538_h;
   private int field_75539_i;

   public VillageSiege(World var1) {
      this.field_75537_a = ☃;
   }

   public void func_75528_a() {
      if (this.field_75537_a.func_72935_r()) {
         this.field_75536_c = 0;
      } else if (this.field_75536_c != 2) {
         if (this.field_75536_c == 0) {
            float ☃ = this.field_75537_a.func_72826_c(0.0F);
            if ((double)☃ < 0.5 || (double)☃ > 0.501) {
               return;
            }

            this.field_75536_c = this.field_75537_a.field_73012_v.nextInt(10) == 0 ? 1 : 2;
            this.field_75535_b = false;
            if (this.field_75536_c == 2) {
               return;
            }
         }

         if (this.field_75536_c != -1) {
            if (!this.field_75535_b) {
               if (!this.func_75529_b()) {
                  return;
               }

               this.field_75535_b = true;
            }

            if (this.field_75534_e > 0) {
               --this.field_75534_e;
            } else {
               this.field_75534_e = 2;
               if (this.field_75533_d > 0) {
                  this.func_75530_c();
                  --this.field_75533_d;
               } else {
                  this.field_75536_c = 2;
               }
            }
         }
      }
   }

   private boolean func_75529_b() {
      for(EntityPlayer ☃ : this.field_75537_a.field_73010_i) {
         if (!☃.func_175149_v()) {
            this.field_75531_f = this.field_75537_a.func_175714_ae().func_176056_a(new BlockPos(☃), 1);
            if (this.field_75531_f != null
               && this.field_75531_f.func_75567_c() >= 10
               && this.field_75531_f.func_75561_d() >= 20
               && this.field_75531_f.func_75562_e() >= 20) {
               BlockPos ☃x = this.field_75531_f.func_180608_a();
               float ☃xx = (float)this.field_75531_f.func_75568_b();
               boolean ☃xxx = false;

               for(int ☃xxxx = 0; ☃xxxx < 10; ++☃xxxx) {
                  float ☃xxxxx = this.field_75537_a.field_73012_v.nextFloat() * (float) (Math.PI * 2);
                  this.field_75532_g = ☃x.func_177958_n() + (int)((double)(MathHelper.func_76134_b(☃xxxxx) * ☃xx) * 0.9);
                  this.field_75538_h = ☃x.func_177956_o();
                  this.field_75539_i = ☃x.func_177952_p() + (int)((double)(MathHelper.func_76126_a(☃xxxxx) * ☃xx) * 0.9);
                  ☃xxx = false;

                  for(Village ☃xxxxxx : this.field_75537_a.func_175714_ae().func_75540_b()) {
                     if (☃xxxxxx != this.field_75531_f && ☃xxxxxx.func_179866_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i))) {
                        ☃xxx = true;
                        break;
                     }
                  }

                  if (!☃xxx) {
                     break;
                  }
               }

               if (☃xxx) {
                  return false;
               }

               Vec3d ☃xxxx = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
               if (☃xxxx != null) {
                  this.field_75534_e = 0;
                  this.field_75533_d = 20;
                  return true;
               }
            }
         }
      }

      return false;
   }

   private boolean func_75530_c() {
      Vec3d ☃ = this.func_179867_a(new BlockPos(this.field_75532_g, this.field_75538_h, this.field_75539_i));
      if (☃ == null) {
         return false;
      } else {
         EntityZombie ☃;
         try {
            ☃ = new EntityZombie(this.field_75537_a);
            ☃.func_204210_a(this.field_75537_a.func_175649_E(new BlockPos(☃)), null, null);
         } catch (Exception var4) {
            var4.printStackTrace();
            return false;
         }

         ☃.func_70012_b(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c, this.field_75537_a.field_73012_v.nextFloat() * 360.0F, 0.0F);
         this.field_75537_a.func_72838_d(☃);
         BlockPos ☃ = this.field_75531_f.func_180608_a();
         ☃.func_175449_a(☃, this.field_75531_f.func_75568_b());
         return true;
      }
   }

   @Nullable
   private Vec3d func_179867_a(BlockPos var1) {
      for(int ☃ = 0; ☃ < 10; ++☃) {
         BlockPos ☃x = ☃.func_177982_a(
            this.field_75537_a.field_73012_v.nextInt(16) - 8, this.field_75537_a.field_73012_v.nextInt(6) - 3, this.field_75537_a.field_73012_v.nextInt(16) - 8
         );
         if (this.field_75531_f.func_179866_a(☃x)
            && WorldEntitySpawner.func_209382_a(EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, this.field_75537_a, ☃x, null)) {
            return new Vec3d((double)☃x.func_177958_n(), (double)☃x.func_177956_o(), (double)☃x.func_177952_p());
         }
      }

      return null;
   }
}
