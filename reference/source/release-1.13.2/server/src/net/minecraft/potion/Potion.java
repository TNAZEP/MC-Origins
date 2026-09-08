package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class Potion {
   private final Map<IAttribute, AttributeModifier> field_111188_I = Maps.<IAttribute, AttributeModifier>newHashMap();
   private final boolean field_76418_K;
   private final int field_76414_N;
   @Nullable
   private String field_76416_I;
   private int field_76417_J = -1;
   private double field_76412_L;
   private boolean field_188415_h;

   @Nullable
   public static Potion func_188412_a(int var0) {
      return IRegistry.field_212631_t.func_148754_a(☃);
   }

   public static int func_188409_a(Potion var0) {
      return IRegistry.field_212631_t.func_148757_b(☃);
   }

   protected Potion(boolean var1, int var2) {
      this.field_76418_K = ☃;
      if (☃) {
         this.field_76412_L = 0.5;
      } else {
         this.field_76412_L = 1.0;
      }

      this.field_76414_N = ☃;
   }

   protected Potion func_76399_b(int var1, int var2) {
      this.field_76417_J = ☃ + ☃ * 12;
      return this;
   }

   public void func_76394_a(EntityLivingBase var1, int var2) {
      if (this == MobEffects.field_76428_l) {
         if (☃.func_110143_aJ() < ☃.func_110138_aP()) {
            ☃.func_70691_i(1.0F);
         }
      } else if (this == MobEffects.field_76436_u) {
         if (☃.func_110143_aJ() > 1.0F) {
            ☃.func_70097_a(DamageSource.field_76376_m, 1.0F);
         }
      } else if (this == MobEffects.field_82731_v) {
         ☃.func_70097_a(DamageSource.field_82727_n, 1.0F);
      } else if (this == MobEffects.field_76438_s && ☃ instanceof EntityPlayer) {
         ((EntityPlayer)☃).func_71020_j(0.005F * (float)(☃ + 1));
      } else if (this == MobEffects.field_76443_y && ☃ instanceof EntityPlayer) {
         if (!☃.field_70170_p.field_72995_K) {
            ((EntityPlayer)☃).func_71024_bL().func_75122_a(☃ + 1, 1.0F);
         }
      } else if ((this != MobEffects.field_76432_h || ☃.func_70662_br()) && (this != MobEffects.field_76433_i || !☃.func_70662_br())) {
         if (this == MobEffects.field_76433_i && !☃.func_70662_br() || this == MobEffects.field_76432_h && ☃.func_70662_br()) {
            ☃.func_70097_a(DamageSource.field_76376_m, (float)(6 << ☃));
         }
      } else {
         ☃.func_70691_i((float)Math.max(4 << ☃, 0));
      }
   }

   public void func_180793_a(@Nullable Entity var1, @Nullable Entity var2, EntityLivingBase var3, int var4, double var5) {
      if ((this != MobEffects.field_76432_h || ☃.func_70662_br()) && (this != MobEffects.field_76433_i || !☃.func_70662_br())) {
         if (this == MobEffects.field_76433_i && !☃.func_70662_br() || this == MobEffects.field_76432_h && ☃.func_70662_br()) {
            int ☃ = (int)(☃ * (double)(6 << ☃) + 0.5);
            if (☃ == null) {
               ☃.func_70097_a(DamageSource.field_76376_m, (float)☃);
            } else {
               ☃.func_70097_a(DamageSource.func_76354_b(☃, ☃), (float)☃);
            }
         } else {
            this.func_76394_a(☃, ☃);
         }
      } else {
         int ☃ = (int)(☃ * (double)(4 << ☃) + 0.5);
         ☃.func_70691_i((float)☃);
      }
   }

   public boolean func_76397_a(int var1, int var2) {
      if (this == MobEffects.field_76428_l) {
         int ☃ = 50 >> ☃;
         if (☃ > 0) {
            return ☃ % ☃ == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.field_76436_u) {
         int ☃ = 25 >> ☃;
         if (☃ > 0) {
            return ☃ % ☃ == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.field_82731_v) {
         int ☃ = 40 >> ☃;
         if (☃ > 0) {
            return ☃ % ☃ == 0;
         } else {
            return true;
         }
      } else {
         return this == MobEffects.field_76438_s;
      }
   }

   public boolean func_76403_b() {
      return false;
   }

   protected String func_210758_b() {
      if (this.field_76416_I == null) {
         this.field_76416_I = Util.func_200697_a("effect", IRegistry.field_212631_t.func_177774_c(this));
      }

      return this.field_76416_I;
   }

   public String func_76393_a() {
      return this.func_210758_b();
   }

   public ITextComponent func_199286_c() {
      return new TextComponentTranslation(this.func_76393_a());
   }

   protected Potion func_76404_a(double var1) {
      this.field_76412_L = ☃;
      return this;
   }

   public int func_76401_j() {
      return this.field_76414_N;
   }

   public Potion func_111184_a(IAttribute var1, String var2, double var3, int var5) {
      AttributeModifier ☃ = new AttributeModifier(UUID.fromString(☃), this::func_76393_a, ☃, ☃);
      this.field_111188_I.put(☃, ☃);
      return this;
   }

   public void func_111187_a(EntityLivingBase var1, AbstractAttributeMap var2, int var3) {
      for(Entry<IAttribute, AttributeModifier> ☃ : this.field_111188_I.entrySet()) {
         IAttributeInstance ☃x = ☃.func_111151_a((IAttribute)☃.getKey());
         if (☃x != null) {
            ☃x.func_111124_b((AttributeModifier)☃.getValue());
         }
      }
   }

   public void func_111185_a(EntityLivingBase var1, AbstractAttributeMap var2, int var3) {
      for(Entry<IAttribute, AttributeModifier> ☃ : this.field_111188_I.entrySet()) {
         IAttributeInstance ☃x = ☃.func_111151_a((IAttribute)☃.getKey());
         if (☃x != null) {
            AttributeModifier ☃xx = (AttributeModifier)☃.getValue();
            ☃x.func_111124_b(☃xx);
            ☃x.func_111121_a(new AttributeModifier(☃xx.func_111167_a(), this.func_76393_a() + " " + ☃, this.func_111183_a(☃, ☃xx), ☃xx.func_111169_c()));
         }
      }
   }

   public double func_111183_a(int var1, AttributeModifier var2) {
      return ☃.func_111164_d() * (double)(☃ + 1);
   }

   public Potion func_188413_j() {
      this.field_188415_h = true;
      return this;
   }

   public static void func_188411_k() {
      func_210759_a(
         1,
         "speed",
         new Potion(false, 8171462)
            .func_76399_b(0, 0)
            .func_111184_a(SharedMonsterAttributes.field_111263_d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, 2)
            .func_188413_j()
      );
      func_210759_a(
         2,
         "slowness",
         new Potion(true, 5926017).func_76399_b(1, 0).func_111184_a(SharedMonsterAttributes.field_111263_d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, 2)
      );
      func_210759_a(
         3,
         "haste",
         new Potion(false, 14270531)
            .func_76399_b(2, 0)
            .func_76404_a(1.5)
            .func_188413_j()
            .func_111184_a(SharedMonsterAttributes.field_188790_f, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.1F, 2)
      );
      func_210759_a(
         4,
         "mining_fatigue",
         new Potion(true, 4866583).func_76399_b(3, 0).func_111184_a(SharedMonsterAttributes.field_188790_f, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, 2)
      );
      func_210759_a(
         5,
         "strength",
         new PotionAttackDamage(false, 9643043, 3.0)
            .func_76399_b(4, 0)
            .func_111184_a(SharedMonsterAttributes.field_111264_e, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0, 0)
            .func_188413_j()
      );
      func_210759_a(6, "instant_health", new PotionInstant(false, 16262179).func_188413_j());
      func_210759_a(7, "instant_damage", new PotionInstant(true, 4393481).func_188413_j());
      func_210759_a(8, "jump_boost", new Potion(false, 2293580).func_76399_b(2, 1).func_188413_j());
      func_210759_a(9, "nausea", new Potion(true, 5578058).func_76399_b(3, 1).func_76404_a(0.25));
      func_210759_a(10, "regeneration", new Potion(false, 13458603).func_76399_b(7, 0).func_76404_a(0.25).func_188413_j());
      func_210759_a(11, "resistance", new Potion(false, 10044730).func_76399_b(6, 1).func_188413_j());
      func_210759_a(12, "fire_resistance", new Potion(false, 14981690).func_76399_b(7, 1).func_188413_j());
      func_210759_a(13, "water_breathing", new Potion(false, 3035801).func_76399_b(0, 2).func_188413_j());
      func_210759_a(14, "invisibility", new Potion(false, 8356754).func_76399_b(0, 1).func_188413_j());
      func_210759_a(15, "blindness", new Potion(true, 2039587).func_76399_b(5, 1).func_76404_a(0.25));
      func_210759_a(16, "night_vision", new Potion(false, 2039713).func_76399_b(4, 1).func_188413_j());
      func_210759_a(17, "hunger", new Potion(true, 5797459).func_76399_b(1, 1));
      func_210759_a(
         18,
         "weakness",
         new PotionAttackDamage(true, 4738376, -4.0)
            .func_76399_b(5, 0)
            .func_111184_a(SharedMonsterAttributes.field_111264_e, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0, 0)
      );
      func_210759_a(19, "poison", new Potion(true, 5149489).func_76399_b(6, 0).func_76404_a(0.25));
      func_210759_a(20, "wither", new Potion(true, 3484199).func_76399_b(1, 2).func_76404_a(0.25));
      func_210759_a(
         21,
         "health_boost",
         new PotionHealthBoost(false, 16284963)
            .func_76399_b(7, 2)
            .func_111184_a(SharedMonsterAttributes.field_111267_a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0)
            .func_188413_j()
      );
      func_210759_a(22, "absorption", new PotionAbsorption(false, 2445989).func_76399_b(2, 2).func_188413_j());
      func_210759_a(23, "saturation", new PotionInstant(false, 16262179).func_188413_j());
      func_210759_a(24, "glowing", new Potion(false, 9740385).func_76399_b(4, 2));
      func_210759_a(25, "levitation", new Potion(true, 13565951).func_76399_b(3, 2));
      func_210759_a(
         26,
         "luck",
         new Potion(false, 3381504)
            .func_76399_b(5, 2)
            .func_188413_j()
            .func_111184_a(SharedMonsterAttributes.field_188792_h, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0, 0)
      );
      func_210759_a(
         27,
         "unluck",
         new Potion(true, 12624973).func_76399_b(6, 2).func_111184_a(SharedMonsterAttributes.field_188792_h, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0, 0)
      );
      func_210759_a(28, "slow_falling", new Potion(false, 16773073).func_76399_b(8, 0).func_188413_j());
      func_210759_a(29, "conduit_power", new Potion(false, 1950417).func_76399_b(9, 0).func_188413_j());
      func_210759_a(30, "dolphins_grace", new Potion(false, 8954814).func_76399_b(10, 0).func_188413_j());
   }

   private static void func_210759_a(int var0, String var1, Potion var2) {
      IRegistry.field_212631_t.func_177775_a(☃, new ResourceLocation(☃), ☃);
   }
}
