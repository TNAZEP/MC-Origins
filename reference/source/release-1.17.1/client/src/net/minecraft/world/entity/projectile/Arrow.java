package net.minecraft.world.entity.projectile;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class Arrow extends AbstractArrow {
   private static final int EXPOSED_POTION_DECAY_TIME = 600;
   private static final int NO_EFFECT_COLOR = -1;
   private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(Arrow.class, EntityDataSerializers.INT);
   private static final byte EVENT_POTION_PUFF = 0;
   private Potion potion = Potions.EMPTY;
   private final Set<MobEffectInstance> effects = Sets.<MobEffectInstance>newHashSet();
   private boolean fixedColor;

   public Arrow(EntityType<? extends Arrow> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public Arrow(Level var1, double var2, double var4, double var6) {
      super(EntityType.ARROW, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Arrow(Level var1, LivingEntity var2) {
      super(EntityType.ARROW, â˜ƒ, â˜ƒ);
   }

   public void setEffectsFromItem(ItemStack var1) {
      if (â˜ƒ.is(Items.TIPPED_ARROW)) {
         this.potion = PotionUtils.getPotion(â˜ƒ);
         Collection<MobEffectInstance> â˜ƒ = PotionUtils.getCustomEffects(â˜ƒ);
         if (!â˜ƒ.isEmpty()) {
            for(MobEffectInstance â˜ƒx : â˜ƒ) {
               this.effects.add(new MobEffectInstance(â˜ƒx));
            }
         }

         int â˜ƒ = getCustomColor(â˜ƒ);
         if (â˜ƒ == -1) {
            this.updateColor();
         } else {
            this.setFixedColor(â˜ƒ);
         }
      } else if (â˜ƒ.is(Items.ARROW)) {
         this.potion = Potions.EMPTY;
         this.effects.clear();
         this.entityData.set(ID_EFFECT_COLOR, -1);
      }
   }

   public static int getCustomColor(ItemStack var0) {
      CompoundTag â˜ƒ = â˜ƒ.getTag();
      return â˜ƒ != null && â˜ƒ.contains("CustomPotionColor", 99) ? â˜ƒ.getInt("CustomPotionColor") : -1;
   }

   private void updateColor() {
      this.fixedColor = false;
      if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
         this.entityData.set(ID_EFFECT_COLOR, -1);
      } else {
         this.entityData.set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
      }
   }

   public void addEffect(MobEffectInstance var1) {
      this.effects.add(â˜ƒ);
      this.getEntityData().set(ID_EFFECT_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(ID_EFFECT_COLOR, -1);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level.isClientSide) {
         if (this.inGround) {
            if (this.inGroundTime % 5 == 0) {
               this.makeParticle(1);
            }
         } else {
            this.makeParticle(2);
         }
      } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
         this.level.broadcastEntityEvent(this, (byte)0);
         this.potion = Potions.EMPTY;
         this.effects.clear();
         this.entityData.set(ID_EFFECT_COLOR, -1);
      }
   }

   private void makeParticle(int var1) {
      int â˜ƒ = this.getColor();
      if (â˜ƒ != -1 && â˜ƒ > 0) {
         double â˜ƒx = (double)(â˜ƒ >> 16 & 0xFF) / 255.0;
         double â˜ƒxx = (double)(â˜ƒ >> 8 & 0xFF) / 255.0;
         double â˜ƒxxx = (double)(â˜ƒ >> 0 & 0xFF) / 255.0;

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ; ++â˜ƒxxxx) {
            this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      }
   }

   public int getColor() {
      return this.entityData.get(ID_EFFECT_COLOR);
   }

   private void setFixedColor(int var1) {
      this.fixedColor = true;
      this.entityData.set(ID_EFFECT_COLOR, â˜ƒ);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      if (this.potion != Potions.EMPTY) {
         â˜ƒ.putString("Potion", Registry.POTION.getKey(this.potion).toString());
      }

      if (this.fixedColor) {
         â˜ƒ.putInt("Color", this.getColor());
      }

      if (!this.effects.isEmpty()) {
         ListTag â˜ƒ = new ListTag();

         for(MobEffectInstance â˜ƒx : this.effects) {
            â˜ƒ.add(â˜ƒx.save(new CompoundTag()));
         }

         â˜ƒ.put("CustomPotionEffects", â˜ƒ);
      }
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      if (â˜ƒ.contains("Potion", 8)) {
         this.potion = PotionUtils.getPotion(â˜ƒ);
      }

      for(MobEffectInstance â˜ƒ : PotionUtils.getCustomEffects(â˜ƒ)) {
         this.addEffect(â˜ƒ);
      }

      if (â˜ƒ.contains("Color", 99)) {
         this.setFixedColor(â˜ƒ.getInt("Color"));
      } else {
         this.updateColor();
      }
   }

   @Override
   protected void doPostHurtEffects(LivingEntity var1) {
      super.doPostHurtEffects(â˜ƒ);
      Entity â˜ƒ = this.getEffectSource();

      for(MobEffectInstance â˜ƒx : this.potion.getEffects()) {
         â˜ƒ.addEffect(
            new MobEffectInstance(â˜ƒx.getEffect(), Math.max(â˜ƒx.getDuration() / 8, 1), â˜ƒx.getAmplifier(), â˜ƒx.isAmbient(), â˜ƒx.isVisible()), â˜ƒ
         );
      }

      if (!this.effects.isEmpty()) {
         for(MobEffectInstance â˜ƒx : this.effects) {
            â˜ƒ.addEffect(â˜ƒx, â˜ƒ);
         }
      }
   }

   @Override
   protected ItemStack getPickupItem() {
      if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
         return new ItemStack(Items.ARROW);
      } else {
         ItemStack â˜ƒ = new ItemStack(Items.TIPPED_ARROW);
         PotionUtils.setPotion(â˜ƒ, this.potion);
         PotionUtils.setCustomEffects(â˜ƒ, this.effects);
         if (this.fixedColor) {
            â˜ƒ.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
         }

         return â˜ƒ;
      }
   }

   @Override
   public void handleEntityEvent(byte var1) {
      if (â˜ƒ == 0) {
         int â˜ƒ = this.getColor();
         if (â˜ƒ != -1) {
            double â˜ƒx = (double)(â˜ƒ >> 16 & 0xFF) / 255.0;
            double â˜ƒxx = (double)(â˜ƒ >> 8 & 0xFF) / 255.0;
            double â˜ƒxxx = (double)(â˜ƒ >> 0 & 0xFF) / 255.0;

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 20; ++â˜ƒxxxx) {
               this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), â˜ƒx, â˜ƒxx, â˜ƒxxx);
            }
         }
      } else {
         super.handleEntityEvent(â˜ƒ);
      }
   }
}
