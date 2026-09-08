package net.minecraft.entity;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.AttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.IAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntitySpectralArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.entity.projectile.EntityTrident;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry {
   private static final Logger field_151262_p = LogManager.getLogger();
   private final Entity field_73132_a;
   private final int field_73130_b;
   private int field_187262_f;
   private final int field_73131_c;
   private long field_73128_d;
   private long field_73129_e;
   private long field_73126_f;
   private int field_73127_g;
   private int field_73139_h;
   private int field_73140_i;
   private double field_73137_j;
   private double field_73138_k;
   private double field_73135_l;
   public int field_73136_m;
   private double field_73147_p;
   private double field_73146_q;
   private double field_73145_r;
   private boolean field_73144_s;
   private final boolean field_73143_t;
   private int field_73142_u;
   private List<Entity> field_187263_w = Collections.emptyList();
   private boolean field_73141_v;
   private boolean field_180234_y;
   public boolean field_73133_n;
   private final Set<EntityPlayerMP> field_73134_o = Sets.<EntityPlayerMP>newHashSet();

   public EntityTrackerEntry(Entity var1, int var2, int var3, int var4, boolean var5) {
      this.field_73132_a = ☃;
      this.field_73130_b = ☃;
      this.field_187262_f = ☃;
      this.field_73131_c = ☃;
      this.field_73143_t = ☃;
      this.field_73128_d = EntityTracker.func_187253_a(☃.field_70165_t);
      this.field_73129_e = EntityTracker.func_187253_a(☃.field_70163_u);
      this.field_73126_f = EntityTracker.func_187253_a(☃.field_70161_v);
      this.field_73127_g = MathHelper.func_76141_d(☃.field_70177_z * 256.0F / 360.0F);
      this.field_73139_h = MathHelper.func_76141_d(☃.field_70125_A * 256.0F / 360.0F);
      this.field_73140_i = MathHelper.func_76141_d(☃.func_70079_am() * 256.0F / 360.0F);
      this.field_180234_y = ☃.field_70122_E;
   }

   public boolean equals(Object var1) {
      if (☃ instanceof EntityTrackerEntry) {
         return ((EntityTrackerEntry)☃).field_73132_a.func_145782_y() == this.field_73132_a.func_145782_y();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.field_73132_a.func_145782_y();
   }

   public void func_73122_a(List<EntityPlayer> var1) {
      this.field_73133_n = false;
      if (!this.field_73144_s || this.field_73132_a.func_70092_e(this.field_73147_p, this.field_73146_q, this.field_73145_r) > 16.0) {
         this.field_73147_p = this.field_73132_a.field_70165_t;
         this.field_73146_q = this.field_73132_a.field_70163_u;
         this.field_73145_r = this.field_73132_a.field_70161_v;
         this.field_73144_s = true;
         this.field_73133_n = true;
         this.func_73125_b(☃);
      }

      List<Entity> ☃ = this.field_73132_a.func_184188_bt();
      if (!☃.equals(this.field_187263_w)) {
         this.field_187263_w = ☃;
         this.func_151259_a(new SPacketSetPassengers(this.field_73132_a));
      }

      if (this.field_73132_a instanceof EntityItemFrame && this.field_73136_m % 10 == 0) {
         EntityItemFrame ☃ = (EntityItemFrame)this.field_73132_a;
         ItemStack ☃x = ☃.func_82335_i();
         if (☃x.func_77973_b() instanceof ItemMap) {
            MapData ☃xx = ItemMap.func_195950_a(☃x, this.field_73132_a.field_70170_p);

            for(EntityPlayer ☃xxx : ☃) {
               EntityPlayerMP ☃xxxx = (EntityPlayerMP)☃xxx;
               ☃xx.func_76191_a(☃xxxx, ☃x);
               Packet<?> ☃xxxxx = ((ItemMap)☃x.func_77973_b()).func_150911_c(☃x, this.field_73132_a.field_70170_p, ☃xxxx);
               if (☃xxxxx != null) {
                  ☃xxxx.field_71135_a.func_147359_a(☃xxxxx);
               }
            }
         }

         this.func_111190_b();
      }

      if (this.field_73136_m % this.field_73131_c == 0 || this.field_73132_a.field_70160_al || this.field_73132_a.func_184212_Q().func_187223_a()) {
         if (this.field_73132_a.func_184218_aH()) {
            int ☃ = MathHelper.func_76141_d(this.field_73132_a.field_70177_z * 256.0F / 360.0F);
            int ☃x = MathHelper.func_76141_d(this.field_73132_a.field_70125_A * 256.0F / 360.0F);
            boolean ☃xx = Math.abs(☃ - this.field_73127_g) >= 1 || Math.abs(☃x - this.field_73139_h) >= 1;
            if (☃xx) {
               this.func_151259_a(new SPacketEntity.Look(this.field_73132_a.func_145782_y(), (byte)☃, (byte)☃x, this.field_73132_a.field_70122_E));
               this.field_73127_g = ☃;
               this.field_73139_h = ☃x;
            }

            this.field_73128_d = EntityTracker.func_187253_a(this.field_73132_a.field_70165_t);
            this.field_73129_e = EntityTracker.func_187253_a(this.field_73132_a.field_70163_u);
            this.field_73126_f = EntityTracker.func_187253_a(this.field_73132_a.field_70161_v);
            this.func_111190_b();
            this.field_73141_v = true;
         } else {
            ++this.field_73142_u;
            long ☃ = EntityTracker.func_187253_a(this.field_73132_a.field_70165_t);
            long ☃x = EntityTracker.func_187253_a(this.field_73132_a.field_70163_u);
            long ☃xx = EntityTracker.func_187253_a(this.field_73132_a.field_70161_v);
            int ☃xxx = MathHelper.func_76141_d(this.field_73132_a.field_70177_z * 256.0F / 360.0F);
            int ☃xxxx = MathHelper.func_76141_d(this.field_73132_a.field_70125_A * 256.0F / 360.0F);
            long ☃xxxxx = ☃ - this.field_73128_d;
            long ☃xxxxxx = ☃x - this.field_73129_e;
            long ☃xxxxxxx = ☃xx - this.field_73126_f;
            Packet<?> ☃xxxxxxxx = null;
            boolean ☃xxxxxxxxx = ☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx + ☃xxxxxxx * ☃xxxxxxx >= 128L || this.field_73136_m % 60 == 0;
            boolean ☃xxxxxxxxxx = Math.abs(☃xxx - this.field_73127_g) >= 1 || Math.abs(☃xxxx - this.field_73139_h) >= 1;
            if (this.field_73136_m > 0 || this.field_73132_a instanceof EntityArrow) {
               if (☃xxxxx >= -32768L
                  && ☃xxxxx < 32768L
                  && ☃xxxxxx >= -32768L
                  && ☃xxxxxx < 32768L
                  && ☃xxxxxxx >= -32768L
                  && ☃xxxxxxx < 32768L
                  && this.field_73142_u <= 400
                  && !this.field_73141_v
                  && this.field_180234_y == this.field_73132_a.field_70122_E) {
                  if ((!☃xxxxxxxxx || !☃xxxxxxxxxx) && !(this.field_73132_a instanceof EntityArrow)) {
                     if (☃xxxxxxxxx) {
                        ☃xxxxxxxx = new SPacketEntity.RelMove(this.field_73132_a.func_145782_y(), ☃xxxxx, ☃xxxxxx, ☃xxxxxxx, this.field_73132_a.field_70122_E);
                     } else if (☃xxxxxxxxxx) {
                        ☃xxxxxxxx = new SPacketEntity.Look(this.field_73132_a.func_145782_y(), (byte)☃xxx, (byte)☃xxxx, this.field_73132_a.field_70122_E);
                     }
                  } else {
                     ☃xxxxxxxx = new SPacketEntity.Move(
                        this.field_73132_a.func_145782_y(), ☃xxxxx, ☃xxxxxx, ☃xxxxxxx, (byte)☃xxx, (byte)☃xxxx, this.field_73132_a.field_70122_E
                     );
                  }
               } else {
                  this.field_180234_y = this.field_73132_a.field_70122_E;
                  this.field_73142_u = 0;
                  this.func_187261_c();
                  ☃xxxxxxxx = new SPacketEntityTeleport(this.field_73132_a);
               }
            }

            boolean ☃ = this.field_73143_t || this.field_73132_a.field_70160_al;
            if (this.field_73132_a instanceof EntityLivingBase && ((EntityLivingBase)this.field_73132_a).func_184613_cA()) {
               ☃ = true;
            }

            if (☃ && this.field_73136_m > 0) {
               double ☃ = this.field_73132_a.field_70159_w - this.field_73137_j;
               double ☃x = this.field_73132_a.field_70181_x - this.field_73138_k;
               double ☃xx = this.field_73132_a.field_70179_y - this.field_73135_l;
               double ☃xxx = 0.02;
               double ☃xxxx = ☃ * ☃ + ☃x * ☃x + ☃xx * ☃xx;
               if (☃xxxx > 4.0E-4
                  || ☃xxxx > 0.0
                     && this.field_73132_a.field_70159_w == 0.0
                     && this.field_73132_a.field_70181_x == 0.0
                     && this.field_73132_a.field_70179_y == 0.0) {
                  this.field_73137_j = this.field_73132_a.field_70159_w;
                  this.field_73138_k = this.field_73132_a.field_70181_x;
                  this.field_73135_l = this.field_73132_a.field_70179_y;
                  this.func_151259_a(new SPacketEntityVelocity(this.field_73132_a.func_145782_y(), this.field_73137_j, this.field_73138_k, this.field_73135_l));
               }
            }

            if (☃xxxxxxxx != null) {
               this.func_151259_a(☃xxxxxxxx);
            }

            this.func_111190_b();
            if (☃xxxxxxxxx) {
               this.field_73128_d = ☃;
               this.field_73129_e = ☃x;
               this.field_73126_f = ☃xx;
            }

            if (☃xxxxxxxxxx) {
               this.field_73127_g = ☃xxx;
               this.field_73139_h = ☃xxxx;
            }

            this.field_73141_v = false;
         }

         int ☃ = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F);
         if (Math.abs(☃ - this.field_73140_i) >= 1) {
            this.func_151259_a(new SPacketEntityHeadLook(this.field_73132_a, (byte)☃));
            this.field_73140_i = ☃;
         }

         this.field_73132_a.field_70160_al = false;
      }

      ++this.field_73136_m;
      if (this.field_73132_a.field_70133_I) {
         this.func_151261_b(new SPacketEntityVelocity(this.field_73132_a));
         this.field_73132_a.field_70133_I = false;
      }
   }

   private void func_111190_b() {
      EntityDataManager ☃ = this.field_73132_a.func_184212_Q();
      if (☃.func_187223_a()) {
         this.func_151261_b(new SPacketEntityMetadata(this.field_73132_a.func_145782_y(), ☃, false));
      }

      if (this.field_73132_a instanceof EntityLivingBase) {
         AttributeMap ☃ = (AttributeMap)((EntityLivingBase)this.field_73132_a).func_110140_aT();
         Set<IAttributeInstance> ☃x = ☃.func_111161_b();
         if (!☃x.isEmpty()) {
            this.func_151261_b(new SPacketEntityProperties(this.field_73132_a.func_145782_y(), ☃x));
         }

         ☃x.clear();
      }
   }

   public void func_151259_a(Packet<?> var1) {
      for(EntityPlayerMP ☃ : this.field_73134_o) {
         ☃.field_71135_a.func_147359_a(☃);
      }
   }

   public void func_151261_b(Packet<?> var1) {
      this.func_151259_a(☃);
      if (this.field_73132_a instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.field_73132_a).field_71135_a.func_147359_a(☃);
      }
   }

   public void func_73119_a() {
      for(EntityPlayerMP ☃ : this.field_73134_o) {
         this.field_73132_a.func_184203_c(☃);
         ☃.func_152339_d(this.field_73132_a);
      }
   }

   public void func_73118_a(EntityPlayerMP var1) {
      if (this.field_73134_o.contains(☃)) {
         this.field_73132_a.func_184203_c(☃);
         ☃.func_152339_d(this.field_73132_a);
         this.field_73134_o.remove(☃);
      }
   }

   public void func_73117_b(EntityPlayerMP var1) {
      if (☃ != this.field_73132_a) {
         if (this.func_180233_c(☃)) {
            if (!this.field_73134_o.contains(☃) && (this.func_73121_d(☃) || this.field_73132_a.field_98038_p)) {
               this.field_73134_o.add(☃);
               Packet<?> ☃ = this.func_151260_c();
               ☃.field_71135_a.func_147359_a(☃);
               if (!this.field_73132_a.func_184212_Q().func_187228_d()) {
                  ☃.field_71135_a.func_147359_a(new SPacketEntityMetadata(this.field_73132_a.func_145782_y(), this.field_73132_a.func_184212_Q(), true));
               }

               boolean ☃ = this.field_73143_t;
               if (this.field_73132_a instanceof EntityLivingBase) {
                  AttributeMap ☃x = (AttributeMap)((EntityLivingBase)this.field_73132_a).func_110140_aT();
                  Collection<IAttributeInstance> ☃xx = ☃x.func_111160_c();
                  if (!☃xx.isEmpty()) {
                     ☃.field_71135_a.func_147359_a(new SPacketEntityProperties(this.field_73132_a.func_145782_y(), ☃xx));
                  }

                  if (((EntityLivingBase)this.field_73132_a).func_184613_cA()) {
                     ☃ = true;
                  }
               }

               this.field_73137_j = this.field_73132_a.field_70159_w;
               this.field_73138_k = this.field_73132_a.field_70181_x;
               this.field_73135_l = this.field_73132_a.field_70179_y;
               if (☃ && !(☃ instanceof SPacketSpawnMob)) {
                  ☃.field_71135_a
                     .func_147359_a(
                        new SPacketEntityVelocity(
                           this.field_73132_a.func_145782_y(),
                           this.field_73132_a.field_70159_w,
                           this.field_73132_a.field_70181_x,
                           this.field_73132_a.field_70179_y
                        )
                     );
               }

               if (this.field_73132_a instanceof EntityLivingBase) {
                  for(EntityEquipmentSlot ☃ : EntityEquipmentSlot.values()) {
                     ItemStack ☃x = ((EntityLivingBase)this.field_73132_a).func_184582_a(☃);
                     if (!☃x.func_190926_b()) {
                        ☃.field_71135_a.func_147359_a(new SPacketEntityEquipment(this.field_73132_a.func_145782_y(), ☃, ☃x));
                     }
                  }
               }

               if (this.field_73132_a instanceof EntityPlayer) {
                  EntityPlayer ☃ = (EntityPlayer)this.field_73132_a;
                  if (☃.func_70608_bn()) {
                     ☃.field_71135_a.func_147359_a(new SPacketUseBed(☃, new BlockPos(this.field_73132_a)));
                  }
               }

               if (this.field_73132_a instanceof EntityLivingBase) {
                  EntityLivingBase ☃ = (EntityLivingBase)this.field_73132_a;

                  for(PotionEffect ☃x : ☃.func_70651_bq()) {
                     ☃.field_71135_a.func_147359_a(new SPacketEntityEffect(this.field_73132_a.func_145782_y(), ☃x));
                  }
               }

               if (!this.field_73132_a.func_184188_bt().isEmpty()) {
                  ☃.field_71135_a.func_147359_a(new SPacketSetPassengers(this.field_73132_a));
               }

               if (this.field_73132_a.func_184218_aH()) {
                  ☃.field_71135_a.func_147359_a(new SPacketSetPassengers(this.field_73132_a.func_184187_bx()));
               }

               this.field_73132_a.func_184178_b(☃);
               ☃.func_184848_d(this.field_73132_a);
            }
         } else if (this.field_73134_o.contains(☃)) {
            this.field_73134_o.remove(☃);
            this.field_73132_a.func_184203_c(☃);
            ☃.func_152339_d(this.field_73132_a);
         }
      }
   }

   public boolean func_180233_c(EntityPlayerMP var1) {
      double ☃ = ☃.field_70165_t - (double)this.field_73128_d / 4096.0;
      double ☃x = ☃.field_70161_v - (double)this.field_73126_f / 4096.0;
      int ☃xx = Math.min(this.field_73130_b, this.field_187262_f);
      return ☃ >= (double)(-☃xx) && ☃ <= (double)☃xx && ☃x >= (double)(-☃xx) && ☃x <= (double)☃xx && this.field_73132_a.func_174827_a(☃);
   }

   private boolean func_73121_d(EntityPlayerMP var1) {
      return ☃.func_71121_q().func_184164_w().func_72694_a(☃, this.field_73132_a.field_70176_ah, this.field_73132_a.field_70164_aj);
   }

   public void func_73125_b(List<EntityPlayer> var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         this.func_73117_b((EntityPlayerMP)☃.get(☃));
      }
   }

   private Packet<?> func_151260_c() {
      if (this.field_73132_a.field_70128_L) {
         field_151262_p.warn("Fetching addPacket for removed entity");
      }

      if (this.field_73132_a instanceof EntityPlayerMP) {
         return new SPacketSpawnPlayer((EntityPlayer)this.field_73132_a);
      } else if (this.field_73132_a instanceof IAnimal) {
         this.field_73140_i = MathHelper.func_76141_d(this.field_73132_a.func_70079_am() * 256.0F / 360.0F);
         return new SPacketSpawnMob((EntityLivingBase)this.field_73132_a);
      } else if (this.field_73132_a instanceof EntityPainting) {
         return new SPacketSpawnPainting((EntityPainting)this.field_73132_a);
      } else if (this.field_73132_a instanceof EntityItem) {
         return new SPacketSpawnObject(this.field_73132_a, 2, 1);
      } else if (this.field_73132_a instanceof EntityMinecart) {
         EntityMinecart ☃ = (EntityMinecart)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 10, ☃.func_184264_v().func_184956_a());
      } else if (this.field_73132_a instanceof EntityBoat) {
         return new SPacketSpawnObject(this.field_73132_a, 1);
      } else if (this.field_73132_a instanceof EntityXPOrb) {
         return new SPacketSpawnExperienceOrb((EntityXPOrb)this.field_73132_a);
      } else if (this.field_73132_a instanceof EntityFishHook) {
         Entity ☃ = ((EntityFishHook)this.field_73132_a).func_190619_l();
         return new SPacketSpawnObject(this.field_73132_a, 90, ☃ == null ? this.field_73132_a.func_145782_y() : ☃.func_145782_y());
      } else if (this.field_73132_a instanceof EntitySpectralArrow) {
         Entity ☃ = ((EntitySpectralArrow)this.field_73132_a).func_212360_k();
         return new SPacketSpawnObject(this.field_73132_a, 91, 1 + (☃ == null ? this.field_73132_a.func_145782_y() : ☃.func_145782_y()));
      } else if (this.field_73132_a instanceof EntityTippedArrow) {
         Entity ☃ = ((EntityArrow)this.field_73132_a).func_212360_k();
         return new SPacketSpawnObject(this.field_73132_a, 60, 1 + (☃ == null ? this.field_73132_a.func_145782_y() : ☃.func_145782_y()));
      } else if (this.field_73132_a instanceof EntitySnowball) {
         return new SPacketSpawnObject(this.field_73132_a, 61);
      } else if (this.field_73132_a instanceof EntityTrident) {
         Entity ☃ = ((EntityArrow)this.field_73132_a).func_212360_k();
         return new SPacketSpawnObject(this.field_73132_a, 94, 1 + (☃ == null ? this.field_73132_a.func_145782_y() : ☃.func_145782_y()));
      } else if (this.field_73132_a instanceof EntityLlamaSpit) {
         return new SPacketSpawnObject(this.field_73132_a, 68);
      } else if (this.field_73132_a instanceof EntityPotion) {
         return new SPacketSpawnObject(this.field_73132_a, 73);
      } else if (this.field_73132_a instanceof EntityExpBottle) {
         return new SPacketSpawnObject(this.field_73132_a, 75);
      } else if (this.field_73132_a instanceof EntityEnderPearl) {
         return new SPacketSpawnObject(this.field_73132_a, 65);
      } else if (this.field_73132_a instanceof EntityEnderEye) {
         return new SPacketSpawnObject(this.field_73132_a, 72);
      } else if (this.field_73132_a instanceof EntityFireworkRocket) {
         return new SPacketSpawnObject(this.field_73132_a, 76);
      } else if (this.field_73132_a instanceof EntityFireball) {
         EntityFireball ☃ = (EntityFireball)this.field_73132_a;
         int ☃x = 63;
         if (this.field_73132_a instanceof EntitySmallFireball) {
            ☃x = 64;
         } else if (this.field_73132_a instanceof EntityDragonFireball) {
            ☃x = 93;
         } else if (this.field_73132_a instanceof EntityWitherSkull) {
            ☃x = 66;
         }

         SPacketSpawnObject ☃;
         if (☃.field_70235_a == null) {
            ☃ = new SPacketSpawnObject(this.field_73132_a, ☃x, 0);
         } else {
            ☃ = new SPacketSpawnObject(this.field_73132_a, ☃x, ((EntityFireball)this.field_73132_a).field_70235_a.func_145782_y());
         }

         ☃.func_149003_d((int)(☃.field_70232_b * 8000.0));
         ☃.func_149000_e((int)(☃.field_70233_c * 8000.0));
         ☃.func_149007_f((int)(☃.field_70230_d * 8000.0));
         return ☃;
      } else if (this.field_73132_a instanceof EntityShulkerBullet) {
         SPacketSpawnObject ☃ = new SPacketSpawnObject(this.field_73132_a, 67, 0);
         ☃.func_149003_d((int)(this.field_73132_a.field_70159_w * 8000.0));
         ☃.func_149000_e((int)(this.field_73132_a.field_70181_x * 8000.0));
         ☃.func_149007_f((int)(this.field_73132_a.field_70179_y * 8000.0));
         return ☃;
      } else if (this.field_73132_a instanceof EntityEgg) {
         return new SPacketSpawnObject(this.field_73132_a, 62);
      } else if (this.field_73132_a instanceof EntityEvokerFangs) {
         return new SPacketSpawnObject(this.field_73132_a, 79);
      } else if (this.field_73132_a instanceof EntityTNTPrimed) {
         return new SPacketSpawnObject(this.field_73132_a, 50);
      } else if (this.field_73132_a instanceof EntityEnderCrystal) {
         return new SPacketSpawnObject(this.field_73132_a, 51);
      } else if (this.field_73132_a instanceof EntityFallingBlock) {
         EntityFallingBlock ☃ = (EntityFallingBlock)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 70, Block.func_196246_j(☃.func_195054_l()));
      } else if (this.field_73132_a instanceof EntityArmorStand) {
         return new SPacketSpawnObject(this.field_73132_a, 78);
      } else if (this.field_73132_a instanceof EntityItemFrame) {
         EntityItemFrame ☃ = (EntityItemFrame)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 71, ☃.field_174860_b.func_176745_a(), ☃.func_174857_n());
      } else if (this.field_73132_a instanceof EntityLeashKnot) {
         EntityLeashKnot ☃ = (EntityLeashKnot)this.field_73132_a;
         return new SPacketSpawnObject(this.field_73132_a, 77, 0, ☃.func_174857_n());
      } else if (this.field_73132_a instanceof EntityAreaEffectCloud) {
         return new SPacketSpawnObject(this.field_73132_a, 3);
      } else {
         throw new IllegalArgumentException("Don't know how to add " + this.field_73132_a.getClass() + "!");
      }
   }

   public void func_73123_c(EntityPlayerMP var1) {
      if (this.field_73134_o.contains(☃)) {
         this.field_73134_o.remove(☃);
         this.field_73132_a.func_184203_c(☃);
         ☃.func_152339_d(this.field_73132_a);
      }
   }

   public Entity func_187260_b() {
      return this.field_73132_a;
   }

   public void func_187259_a(int var1) {
      this.field_187262_f = ☃;
   }

   public void func_187261_c() {
      this.field_73144_s = false;
   }
}
