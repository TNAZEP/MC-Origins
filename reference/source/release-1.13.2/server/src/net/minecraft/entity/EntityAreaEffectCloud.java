package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.command.arguments.ParticleArgument;
import net.minecraft.init.Particles;
import net.minecraft.init.PotionTypes;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAreaEffectCloud extends Entity {
   private static final Logger field_195060_a = LogManager.getLogger();
   private static final DataParameter<Float> field_184498_a = EntityDataManager.func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187193_c);
   private static final DataParameter<Integer> field_184499_b = EntityDataManager.func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_184500_c = EntityDataManager.func_187226_a(EntityAreaEffectCloud.class, DataSerializers.field_187198_h);
   private static final DataParameter<IParticleData> field_184501_d = EntityDataManager.func_187226_a(
      EntityAreaEffectCloud.class, DataSerializers.field_198166_i
   );
   private PotionType field_184502_e = PotionTypes.field_185229_a;
   private final List<PotionEffect> field_184503_f = Lists.<PotionEffect>newArrayList();
   private final Map<Entity, Integer> field_184504_g = Maps.newHashMap();
   private int field_184505_h = 600;
   private int field_184506_as = 20;
   private int field_184507_at = 20;
   private boolean field_184508_au;
   private int field_184509_av;
   private float field_184510_aw;
   private float field_184511_ax;
   private EntityLivingBase field_184512_ay;
   private UUID field_184513_az;

   public EntityAreaEffectCloud(World var1) {
      super(EntityType.field_200788_b, ☃);
      this.field_70145_X = true;
      this.field_70178_ae = true;
      this.func_184483_a(3.0F);
   }

   public EntityAreaEffectCloud(World var1, double var2, double var4, double var6) {
      this(☃);
      this.func_70107_b(☃, ☃, ☃);
   }

   @Override
   protected void func_70088_a() {
      this.func_184212_Q().func_187214_a(field_184499_b, 0);
      this.func_184212_Q().func_187214_a(field_184498_a, 0.5F);
      this.func_184212_Q().func_187214_a(field_184500_c, false);
      this.func_184212_Q().func_187214_a(field_184501_d, Particles.field_197625_r);
   }

   public void func_184483_a(float var1) {
      double ☃ = this.field_70165_t;
      double ☃x = this.field_70163_u;
      double ☃xx = this.field_70161_v;
      this.func_70105_a(☃ * 2.0F, 0.5F);
      this.func_70107_b(☃, ☃x, ☃xx);
      if (!this.field_70170_p.field_72995_K) {
         this.func_184212_Q().func_187227_b(field_184498_a, ☃);
      }
   }

   public float func_184490_j() {
      return this.func_184212_Q().func_187225_a(field_184498_a);
   }

   public void func_184484_a(PotionType var1) {
      this.field_184502_e = ☃;
      if (!this.field_184508_au) {
         this.func_190618_C();
      }
   }

   private void func_190618_C() {
      if (this.field_184502_e == PotionTypes.field_185229_a && this.field_184503_f.isEmpty()) {
         this.func_184212_Q().func_187227_b(field_184499_b, 0);
      } else {
         this.func_184212_Q().func_187227_b(field_184499_b, PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.field_184502_e, this.field_184503_f)));
      }
   }

   public void func_184496_a(PotionEffect var1) {
      this.field_184503_f.add(☃);
      if (!this.field_184508_au) {
         this.func_190618_C();
      }
   }

   public int func_184492_k() {
      return this.func_184212_Q().func_187225_a(field_184499_b);
   }

   public void func_184482_a(int var1) {
      this.field_184508_au = true;
      this.func_184212_Q().func_187227_b(field_184499_b, ☃);
   }

   public IParticleData func_195058_l() {
      return this.func_184212_Q().func_187225_a(field_184501_d);
   }

   public void func_195059_a(IParticleData var1) {
      this.func_184212_Q().func_187227_b(field_184501_d, ☃);
   }

   protected void func_184488_a(boolean var1) {
      this.func_184212_Q().func_187227_b(field_184500_c, ☃);
   }

   public boolean func_184497_n() {
      return this.func_184212_Q().func_187225_a(field_184500_c);
   }

   public int func_184489_o() {
      return this.field_184505_h;
   }

   public void func_184486_b(int var1) {
      this.field_184505_h = ☃;
   }

   @Override
   public void func_70071_h_() {
      super.func_70071_h_();
      boolean ☃ = this.func_184497_n();
      float ☃x = this.func_184490_j();
      if (this.field_70170_p.field_72995_K) {
         IParticleData ☃xx = this.func_195058_l();
         if (☃) {
            if (this.field_70146_Z.nextBoolean()) {
               for(int ☃xxx = 0; ☃xxx < 2; ++☃xxx) {
                  float ☃xxxx = this.field_70146_Z.nextFloat() * (float) (Math.PI * 2);
                  float ☃xxxxx = MathHelper.func_76129_c(this.field_70146_Z.nextFloat()) * 0.2F;
                  float ☃xxxxxx = MathHelper.func_76134_b(☃xxxx) * ☃xxxxx;
                  float ☃xxxxxxx = MathHelper.func_76126_a(☃xxxx) * ☃xxxxx;
                  if (☃xx.func_197554_b() == Particles.field_197625_r) {
                     int ☃xxxxxxxx = this.field_70146_Z.nextBoolean() ? 16777215 : this.func_184492_k();
                     int ☃xxxxxxxxx = ☃xxxxxxxx >> 16 & 0xFF;
                     int ☃xxxxxxxxxx = ☃xxxxxxxx >> 8 & 0xFF;
                     int ☃xxxxxxxxxxx = ☃xxxxxxxx & 0xFF;
                     this.field_70170_p
                        .func_195589_b(
                           ☃xx,
                           this.field_70165_t + (double)☃xxxxxx,
                           this.field_70163_u,
                           this.field_70161_v + (double)☃xxxxxxx,
                           (double)((float)☃xxxxxxxxx / 255.0F),
                           (double)((float)☃xxxxxxxxxx / 255.0F),
                           (double)((float)☃xxxxxxxxxxx / 255.0F)
                        );
                  } else {
                     this.field_70170_p
                        .func_195589_b(☃xx, this.field_70165_t + (double)☃xxxxxx, this.field_70163_u, this.field_70161_v + (double)☃xxxxxxx, 0.0, 0.0, 0.0);
                  }
               }
            }
         } else {
            float ☃xx = (float) Math.PI * ☃x * ☃x;

            for(int ☃xxx = 0; (float)☃xxx < ☃xx; ++☃xxx) {
               float ☃xxxx = this.field_70146_Z.nextFloat() * (float) (Math.PI * 2);
               float ☃xxxxx = MathHelper.func_76129_c(this.field_70146_Z.nextFloat()) * ☃x;
               float ☃xxxxxx = MathHelper.func_76134_b(☃xxxx) * ☃xxxxx;
               float ☃xxxxxxx = MathHelper.func_76126_a(☃xxxx) * ☃xxxxx;
               if (☃xx.func_197554_b() == Particles.field_197625_r) {
                  int ☃xxxxxxxx = this.func_184492_k();
                  int ☃xxxxxxxxx = ☃xxxxxxxx >> 16 & 0xFF;
                  int ☃xxxxxxxxxx = ☃xxxxxxxx >> 8 & 0xFF;
                  int ☃xxxxxxxxxxx = ☃xxxxxxxx & 0xFF;
                  this.field_70170_p
                     .func_195589_b(
                        ☃xx,
                        this.field_70165_t + (double)☃xxxxxx,
                        this.field_70163_u,
                        this.field_70161_v + (double)☃xxxxxxx,
                        (double)((float)☃xxxxxxxxx / 255.0F),
                        (double)((float)☃xxxxxxxxxx / 255.0F),
                        (double)((float)☃xxxxxxxxxxx / 255.0F)
                     );
               } else {
                  this.field_70170_p
                     .func_195589_b(
                        ☃xx,
                        this.field_70165_t + (double)☃xxxxxx,
                        this.field_70163_u,
                        this.field_70161_v + (double)☃xxxxxxx,
                        (0.5 - this.field_70146_Z.nextDouble()) * 0.15,
                        0.01F,
                        (0.5 - this.field_70146_Z.nextDouble()) * 0.15
                     );
               }
            }
         }
      } else {
         if (this.field_70173_aa >= this.field_184506_as + this.field_184505_h) {
            this.func_70106_y();
            return;
         }

         boolean ☃ = this.field_70173_aa < this.field_184506_as;
         if (☃ != ☃) {
            this.func_184488_a(☃);
         }

         if (☃) {
            return;
         }

         if (this.field_184511_ax != 0.0F) {
            ☃x += this.field_184511_ax;
            if (☃x < 0.5F) {
               this.func_70106_y();
               return;
            }

            this.func_184483_a(☃x);
         }

         if (this.field_70173_aa % 5 == 0) {
            Iterator<Entry<Entity, Integer>> ☃ = this.field_184504_g.entrySet().iterator();

            while(☃.hasNext()) {
               Entry<Entity, Integer> ☃x = (Entry)☃.next();
               if (this.field_70173_aa >= ☃x.getValue()) {
                  ☃.remove();
               }
            }

            List<PotionEffect> ☃x = Lists.<PotionEffect>newArrayList();

            for(PotionEffect ☃xx : this.field_184502_e.func_185170_a()) {
               ☃x.add(new PotionEffect(☃xx.func_188419_a(), ☃xx.func_76459_b() / 4, ☃xx.func_76458_c(), ☃xx.func_82720_e(), ☃xx.func_188418_e()));
            }

            ☃x.addAll(this.field_184503_f);
            if (☃x.isEmpty()) {
               this.field_184504_g.clear();
            } else {
               List<EntityLivingBase> ☃xx = this.field_70170_p.func_72872_a(EntityLivingBase.class, this.func_174813_aQ());
               if (!☃xx.isEmpty()) {
                  for(EntityLivingBase ☃xxx : ☃xx) {
                     if (!this.field_184504_g.containsKey(☃xxx) && ☃xxx.func_184603_cC()) {
                        double ☃xxxx = ☃xxx.field_70165_t - this.field_70165_t;
                        double ☃xxxxx = ☃xxx.field_70161_v - this.field_70161_v;
                        double ☃xxxxxx = ☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx;
                        if (☃xxxxxx <= (double)(☃x * ☃x)) {
                           this.field_184504_g.put(☃xxx, this.field_70173_aa + this.field_184507_at);

                           for(PotionEffect ☃xxxxxxx : ☃x) {
                              if (☃xxxxxxx.func_188419_a().func_76403_b()) {
                                 ☃xxxxxxx.func_188419_a().func_180793_a(this, this.func_184494_w(), ☃xxx, ☃xxxxxxx.func_76458_c(), 0.5);
                              } else {
                                 ☃xxx.func_195064_c(new PotionEffect(☃xxxxxxx));
                              }
                           }

                           if (this.field_184510_aw != 0.0F) {
                              ☃x += this.field_184510_aw;
                              if (☃x < 0.5F) {
                                 this.func_70106_y();
                                 return;
                              }

                              this.func_184483_a(☃x);
                           }

                           if (this.field_184509_av != 0) {
                              this.field_184505_h += this.field_184509_av;
                              if (this.field_184505_h <= 0) {
                                 this.func_70106_y();
                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void func_184495_b(float var1) {
      this.field_184510_aw = ☃;
   }

   public void func_184487_c(float var1) {
      this.field_184511_ax = ☃;
   }

   public void func_184485_d(int var1) {
      this.field_184506_as = ☃;
   }

   public void func_184481_a(@Nullable EntityLivingBase var1) {
      this.field_184512_ay = ☃;
      this.field_184513_az = ☃ == null ? null : ☃.func_110124_au();
   }

   @Nullable
   public EntityLivingBase func_184494_w() {
      if (this.field_184512_ay == null && this.field_184513_az != null && this.field_70170_p instanceof WorldServer) {
         Entity ☃ = ((WorldServer)this.field_70170_p).func_175733_a(this.field_184513_az);
         if (☃ instanceof EntityLivingBase) {
            this.field_184512_ay = (EntityLivingBase)☃;
         }
      }

      return this.field_184512_ay;
   }

   @Override
   protected void func_70037_a(NBTTagCompound var1) {
      this.field_70173_aa = ☃.func_74762_e("Age");
      this.field_184505_h = ☃.func_74762_e("Duration");
      this.field_184506_as = ☃.func_74762_e("WaitTime");
      this.field_184507_at = ☃.func_74762_e("ReapplicationDelay");
      this.field_184509_av = ☃.func_74762_e("DurationOnUse");
      this.field_184510_aw = ☃.func_74760_g("RadiusOnUse");
      this.field_184511_ax = ☃.func_74760_g("RadiusPerTick");
      this.func_184483_a(☃.func_74760_g("Radius"));
      this.field_184513_az = ☃.func_186857_a("OwnerUUID");
      if (☃.func_150297_b("Particle", 8)) {
         try {
            this.func_195059_a(ParticleArgument.func_197189_a(new StringReader(☃.func_74779_i("Particle"))));
         } catch (CommandSyntaxException var5) {
            field_195060_a.warn("Couldn't load custom particle {}", ☃.func_74779_i("Particle"), var5);
         }
      }

      if (☃.func_150297_b("Color", 99)) {
         this.func_184482_a(☃.func_74762_e("Color"));
      }

      if (☃.func_150297_b("Potion", 8)) {
         this.func_184484_a(PotionUtils.func_185187_c(☃));
      }

      if (☃.func_150297_b("Effects", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("Effects", 10);
         this.field_184503_f.clear();

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            PotionEffect ☃xx = PotionEffect.func_82722_b(☃.func_150305_b(☃x));
            if (☃xx != null) {
               this.func_184496_a(☃xx);
            }
         }
      }
   }

   @Override
   protected void func_70014_b(NBTTagCompound var1) {
      ☃.func_74768_a("Age", this.field_70173_aa);
      ☃.func_74768_a("Duration", this.field_184505_h);
      ☃.func_74768_a("WaitTime", this.field_184506_as);
      ☃.func_74768_a("ReapplicationDelay", this.field_184507_at);
      ☃.func_74768_a("DurationOnUse", this.field_184509_av);
      ☃.func_74776_a("RadiusOnUse", this.field_184510_aw);
      ☃.func_74776_a("RadiusPerTick", this.field_184511_ax);
      ☃.func_74776_a("Radius", this.func_184490_j());
      ☃.func_74778_a("Particle", this.func_195058_l().func_197555_a());
      if (this.field_184513_az != null) {
         ☃.func_186854_a("OwnerUUID", this.field_184513_az);
      }

      if (this.field_184508_au) {
         ☃.func_74768_a("Color", this.func_184492_k());
      }

      if (this.field_184502_e != PotionTypes.field_185229_a && this.field_184502_e != null) {
         ☃.func_74778_a("Potion", IRegistry.field_212621_j.func_177774_c(this.field_184502_e).toString());
      }

      if (!this.field_184503_f.isEmpty()) {
         NBTTagList ☃ = new NBTTagList();

         for(PotionEffect ☃x : this.field_184503_f) {
            ☃.add((INBTBase)☃x.func_82719_a(new NBTTagCompound()));
         }

         ☃.func_74782_a("Effects", ☃);
      }
   }

   @Override
   public void func_184206_a(DataParameter<?> var1) {
      if (field_184498_a.equals(☃)) {
         this.func_184483_a(this.func_184490_j());
      }

      super.func_184206_a(☃);
   }

   @Override
   public EnumPushReaction func_184192_z() {
      return EnumPushReaction.IGNORE;
   }
}
