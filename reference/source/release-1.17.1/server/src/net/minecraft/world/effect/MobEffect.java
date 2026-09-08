package net.minecraft.world.effect;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public class MobEffect {
   private final Map<Attribute, AttributeModifier> attributeModifiers = Maps.<Attribute, AttributeModifier>newHashMap();
   private final MobEffectCategory category;
   private final int color;
   @Nullable
   private String descriptionId;

   @Nullable
   public static MobEffect byId(int var0) {
      return Registry.MOB_EFFECT.byId(â˜ƒ);
   }

   public static int getId(MobEffect var0) {
      return Registry.MOB_EFFECT.getId(â˜ƒ);
   }

   protected MobEffect(MobEffectCategory var1, int var2) {
      this.category = â˜ƒ;
      this.color = â˜ƒ;
   }

   public void applyEffectTick(LivingEntity var1, int var2) {
      if (this == MobEffects.REGENERATION) {
         if (â˜ƒ.getHealth() < â˜ƒ.getMaxHealth()) {
            â˜ƒ.heal(1.0F);
         }
      } else if (this == MobEffects.POISON) {
         if (â˜ƒ.getHealth() > 1.0F) {
            â˜ƒ.hurt(DamageSource.MAGIC, 1.0F);
         }
      } else if (this == MobEffects.WITHER) {
         â˜ƒ.hurt(DamageSource.WITHER, 1.0F);
      } else if (this == MobEffects.HUNGER && â˜ƒ instanceof Player) {
         ((Player)â˜ƒ).causeFoodExhaustion(0.005F * (float)(â˜ƒ + 1));
      } else if (this == MobEffects.SATURATION && â˜ƒ instanceof Player) {
         if (!â˜ƒ.level.isClientSide) {
            ((Player)â˜ƒ).getFoodData().eat(â˜ƒ + 1, 1.0F);
         }
      } else if ((this != MobEffects.HEAL || â˜ƒ.isInvertedHealAndHarm()) && (this != MobEffects.HARM || !â˜ƒ.isInvertedHealAndHarm())) {
         if (this == MobEffects.HARM && !â˜ƒ.isInvertedHealAndHarm() || this == MobEffects.HEAL && â˜ƒ.isInvertedHealAndHarm()) {
            â˜ƒ.hurt(DamageSource.MAGIC, (float)(6 << â˜ƒ));
         }
      } else {
         â˜ƒ.heal((float)Math.max(4 << â˜ƒ, 0));
      }
   }

   public void applyInstantenousEffect(@Nullable Entity var1, @Nullable Entity var2, LivingEntity var3, int var4, double var5) {
      if ((this != MobEffects.HEAL || â˜ƒ.isInvertedHealAndHarm()) && (this != MobEffects.HARM || !â˜ƒ.isInvertedHealAndHarm())) {
         if (this == MobEffects.HARM && !â˜ƒ.isInvertedHealAndHarm() || this == MobEffects.HEAL && â˜ƒ.isInvertedHealAndHarm()) {
            int â˜ƒ = (int)(â˜ƒ * (double)(6 << â˜ƒ) + 0.5);
            if (â˜ƒ == null) {
               â˜ƒ.hurt(DamageSource.MAGIC, (float)â˜ƒ);
            } else {
               â˜ƒ.hurt(DamageSource.indirectMagic(â˜ƒ, â˜ƒ), (float)â˜ƒ);
            }
         } else {
            this.applyEffectTick(â˜ƒ, â˜ƒ);
         }
      } else {
         int â˜ƒ = (int)(â˜ƒ * (double)(4 << â˜ƒ) + 0.5);
         â˜ƒ.heal((float)â˜ƒ);
      }
   }

   public boolean isDurationEffectTick(int var1, int var2) {
      if (this == MobEffects.REGENERATION) {
         int â˜ƒ = 50 >> â˜ƒ;
         if (â˜ƒ > 0) {
            return â˜ƒ % â˜ƒ == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.POISON) {
         int â˜ƒ = 25 >> â˜ƒ;
         if (â˜ƒ > 0) {
            return â˜ƒ % â˜ƒ == 0;
         } else {
            return true;
         }
      } else if (this == MobEffects.WITHER) {
         int â˜ƒ = 40 >> â˜ƒ;
         if (â˜ƒ > 0) {
            return â˜ƒ % â˜ƒ == 0;
         } else {
            return true;
         }
      } else {
         return this == MobEffects.HUNGER;
      }
   }

   public boolean isInstantenous() {
      return false;
   }

   protected String getOrCreateDescriptionId() {
      if (this.descriptionId == null) {
         this.descriptionId = Util.makeDescriptionId("effect", Registry.MOB_EFFECT.getKey(this));
      }

      return this.descriptionId;
   }

   public String getDescriptionId() {
      return this.getOrCreateDescriptionId();
   }

   public Component getDisplayName() {
      return new TranslatableComponent(this.getDescriptionId());
   }

   public MobEffectCategory getCategory() {
      return this.category;
   }

   public int getColor() {
      return this.color;
   }

   public MobEffect addAttributeModifier(Attribute var1, String var2, double var3, AttributeModifier.Operation var5) {
      AttributeModifier â˜ƒ = new AttributeModifier(UUID.fromString(â˜ƒ), this::getDescriptionId, â˜ƒ, â˜ƒ);
      this.attributeModifiers.put(â˜ƒ, â˜ƒ);
      return this;
   }

   public Map<Attribute, AttributeModifier> getAttributeModifiers() {
      return this.attributeModifiers;
   }

   public void removeAttributeModifiers(LivingEntity var1, AttributeMap var2, int var3) {
      for(Entry<Attribute, AttributeModifier> â˜ƒ : this.attributeModifiers.entrySet()) {
         AttributeInstance â˜ƒx = â˜ƒ.getInstance((Attribute)â˜ƒ.getKey());
         if (â˜ƒx != null) {
            â˜ƒx.removeModifier((AttributeModifier)â˜ƒ.getValue());
         }
      }
   }

   public void addAttributeModifiers(LivingEntity var1, AttributeMap var2, int var3) {
      for(Entry<Attribute, AttributeModifier> â˜ƒ : this.attributeModifiers.entrySet()) {
         AttributeInstance â˜ƒx = â˜ƒ.getInstance((Attribute)â˜ƒ.getKey());
         if (â˜ƒx != null) {
            AttributeModifier â˜ƒxx = (AttributeModifier)â˜ƒ.getValue();
            â˜ƒx.removeModifier(â˜ƒxx);
            â˜ƒx.addPermanentModifier(
               new AttributeModifier(â˜ƒxx.getId(), this.getDescriptionId() + " " + â˜ƒ, this.getAttributeModifierValue(â˜ƒ, â˜ƒxx), â˜ƒxx.getOperation())
            );
         }
      }
   }

   public double getAttributeModifierValue(int var1, AttributeModifier var2) {
      return â˜ƒ.getAmount() * (double)(â˜ƒ + 1);
   }

   public boolean isBeneficial() {
      return this.category == MobEffectCategory.BENEFICIAL;
   }
}
