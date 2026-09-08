package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTracker {
   private static final Logger field_151249_a = LogManager.getLogger();
   private final WorldServer field_72795_a;
   private final Set<EntityTrackerEntry> field_72793_b = Sets.<EntityTrackerEntry>newHashSet();
   private final IntHashMap<EntityTrackerEntry> field_72794_c = new IntHashMap<>();
   private int field_72792_d;

   public EntityTracker(WorldServer var1) {
      this.field_72795_a = ☃;
      this.field_72792_d = ☃.func_73046_m().func_184103_al().func_72372_a();
   }

   public static long func_187253_a(double var0) {
      return MathHelper.func_76124_d(☃ * 4096.0);
   }

   public static void func_187254_a(Entity var0, double var1, double var3, double var5) {
      ☃.field_70118_ct = func_187253_a(☃);
      ☃.field_70117_cu = func_187253_a(☃);
      ☃.field_70116_cv = func_187253_a(☃);
   }

   public void func_72786_a(Entity var1) {
      if (☃ instanceof EntityPlayerMP) {
         this.func_72791_a(☃, 512, 2);
         EntityPlayerMP ☃ = (EntityPlayerMP)☃;

         for(EntityTrackerEntry ☃x : this.field_72793_b) {
            if (☃x.func_187260_b() != ☃) {
               ☃x.func_73117_b(☃);
            }
         }
      } else if (☃ instanceof EntityFishHook) {
         this.func_72785_a(☃, 64, 5, true);
      } else if (☃ instanceof EntityArrow) {
         this.func_72785_a(☃, 64, 20, false);
      } else if (☃ instanceof EntitySmallFireball) {
         this.func_72785_a(☃, 64, 10, false);
      } else if (☃ instanceof EntityFireball) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntitySnowball) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntityLlamaSpit) {
         this.func_72785_a(☃, 64, 10, false);
      } else if (☃ instanceof EntityEnderPearl) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntityEnderEye) {
         this.func_72785_a(☃, 64, 4, true);
      } else if (☃ instanceof EntityEgg) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntityPotion) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntityExpBottle) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntityFireworkRocket) {
         this.func_72785_a(☃, 64, 10, true);
      } else if (☃ instanceof EntityItem) {
         this.func_72785_a(☃, 64, 20, true);
      } else if (☃ instanceof EntityMinecart) {
         this.func_72785_a(☃, 80, 3, true);
      } else if (☃ instanceof EntityBoat) {
         this.func_72785_a(☃, 80, 3, true);
      } else if (☃ instanceof EntitySquid) {
         this.func_72785_a(☃, 64, 3, true);
      } else if (☃ instanceof EntityWither) {
         this.func_72785_a(☃, 80, 3, false);
      } else if (☃ instanceof EntityShulkerBullet) {
         this.func_72785_a(☃, 80, 3, true);
      } else if (☃ instanceof EntityBat) {
         this.func_72785_a(☃, 80, 3, false);
      } else if (☃ instanceof EntityDragon) {
         this.func_72785_a(☃, 160, 3, true);
      } else if (☃ instanceof IAnimal) {
         this.func_72785_a(☃, 80, 3, true);
      } else if (☃ instanceof EntityTNTPrimed) {
         this.func_72785_a(☃, 160, 10, true);
      } else if (☃ instanceof EntityFallingBlock) {
         this.func_72785_a(☃, 160, 20, true);
      } else if (☃ instanceof EntityHanging) {
         this.func_72785_a(☃, 160, Integer.MAX_VALUE, false);
      } else if (☃ instanceof EntityArmorStand) {
         this.func_72785_a(☃, 160, 3, true);
      } else if (☃ instanceof EntityXPOrb) {
         this.func_72785_a(☃, 160, 20, true);
      } else if (☃ instanceof EntityAreaEffectCloud) {
         this.func_72785_a(☃, 160, Integer.MAX_VALUE, true);
      } else if (☃ instanceof EntityEnderCrystal) {
         this.func_72785_a(☃, 256, Integer.MAX_VALUE, false);
      } else if (☃ instanceof EntityEvokerFangs) {
         this.func_72785_a(☃, 160, 2, false);
      }
   }

   public void func_72791_a(Entity var1, int var2, int var3) {
      this.func_72785_a(☃, ☃, ☃, false);
   }

   public void func_72785_a(Entity var1, int var2, int var3, boolean var4) {
      try {
         if (this.field_72794_c.func_76037_b(☃.func_145782_y())) {
            throw new IllegalStateException("Entity is already tracked!");
         }

         EntityTrackerEntry ☃ = new EntityTrackerEntry(☃, ☃, this.field_72792_d, ☃, ☃);
         this.field_72793_b.add(☃);
         this.field_72794_c.func_76038_a(☃.func_145782_y(), ☃);
         ☃.func_73125_b(this.field_72795_a.field_73010_i);
      } catch (Throwable var10) {
         CrashReport ☃ = CrashReport.func_85055_a(var10, "Adding entity to track");
         CrashReportCategory ☃x = ☃.func_85058_a("Entity To Track");
         ☃x.func_71507_a("Tracking range", ☃ + " blocks");
         ☃x.func_189529_a("Update interval", () -> {
            String ☃ = "Once per " + ☃ + " ticks";
            if (☃ == Integer.MAX_VALUE) {
               ☃ = "Maximum (" + ☃ + ")";
            }

            return ☃;
         });
         ☃.func_85029_a(☃x);
         this.field_72794_c.func_76041_a(☃.func_145782_y()).func_187260_b().func_85029_a(☃.func_85058_a("Entity That Is Already Tracked"));

         try {
            throw new ReportedException(☃);
         } catch (ReportedException var9) {
            field_151249_a.error("\"Silently\" catching entity tracking error.", var9);
         }
      }
   }

   public void func_72790_b(Entity var1) {
      if (☃ instanceof EntityPlayerMP) {
         EntityPlayerMP ☃ = (EntityPlayerMP)☃;

         for(EntityTrackerEntry ☃x : this.field_72793_b) {
            ☃x.func_73118_a(☃);
         }
      }

      EntityTrackerEntry ☃ = this.field_72794_c.func_76049_d(☃.func_145782_y());
      if (☃ != null) {
         this.field_72793_b.remove(☃);
         ☃.func_73119_a();
      }
   }

   public void func_72788_a() {
      List<EntityPlayerMP> ☃ = Lists.<EntityPlayerMP>newArrayList();

      for(EntityTrackerEntry ☃x : this.field_72793_b) {
         ☃x.func_73122_a(this.field_72795_a.field_73010_i);
         if (☃x.field_73133_n) {
            Entity ☃xx = ☃x.func_187260_b();
            if (☃xx instanceof EntityPlayerMP) {
               ☃.add((EntityPlayerMP)☃xx);
            }
         }
      }

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         EntityPlayerMP ☃xx = (EntityPlayerMP)☃.get(☃x);

         for(EntityTrackerEntry ☃xxx : this.field_72793_b) {
            if (☃xxx.func_187260_b() != ☃xx) {
               ☃xxx.func_73117_b(☃xx);
            }
         }
      }
   }

   public void func_180245_a(EntityPlayerMP var1) {
      for(EntityTrackerEntry ☃ : this.field_72793_b) {
         if (☃.func_187260_b() == ☃) {
            ☃.func_73125_b(this.field_72795_a.field_73010_i);
         } else {
            ☃.func_73117_b(☃);
         }
      }
   }

   public void func_151247_a(Entity var1, Packet<?> var2) {
      EntityTrackerEntry ☃ = this.field_72794_c.func_76041_a(☃.func_145782_y());
      if (☃ != null) {
         ☃.func_151259_a(☃);
      }
   }

   public void func_151248_b(Entity var1, Packet<?> var2) {
      EntityTrackerEntry ☃ = this.field_72794_c.func_76041_a(☃.func_145782_y());
      if (☃ != null) {
         ☃.func_151261_b(☃);
      }
   }

   public void func_72787_a(EntityPlayerMP var1) {
      for(EntityTrackerEntry ☃ : this.field_72793_b) {
         ☃.func_73123_c(☃);
      }
   }

   public void func_85172_a(EntityPlayerMP var1, Chunk var2) {
      List<Entity> ☃ = Lists.<Entity>newArrayList();
      List<Entity> ☃x = Lists.<Entity>newArrayList();

      for(EntityTrackerEntry ☃xx : this.field_72793_b) {
         Entity ☃xxx = ☃xx.func_187260_b();
         if (☃xxx != ☃ && ☃xxx.field_70176_ah == ☃.field_76635_g && ☃xxx.field_70164_aj == ☃.field_76647_h) {
            ☃xx.func_73117_b(☃);
            if (☃xxx instanceof EntityLiving && ((EntityLiving)☃xxx).func_110166_bE() != null) {
               ☃.add(☃xxx);
            }

            if (!☃xxx.func_184188_bt().isEmpty()) {
               ☃x.add(☃xxx);
            }
         }
      }

      if (!☃.isEmpty()) {
         for(Entity ☃xx : ☃) {
            ☃.field_71135_a.func_147359_a(new SPacketEntityAttach(☃xx, ((EntityLiving)☃xx).func_110166_bE()));
         }
      }

      if (!☃x.isEmpty()) {
         for(Entity ☃xx : ☃x) {
            ☃.field_71135_a.func_147359_a(new SPacketSetPassengers(☃xx));
         }
      }
   }

   public void func_187252_a(int var1) {
      this.field_72792_d = (☃ - 1) * 16;

      for(EntityTrackerEntry ☃ : this.field_72793_b) {
         ☃.func_187259_a(this.field_72792_d);
      }
   }
}
