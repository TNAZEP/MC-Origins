package net.minecraft.world.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AreaEffectCloud extends Entity {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int TIME_BETWEEN_APPLICATIONS = 5;
   private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(AreaEffectCloud.class, EntityDataSerializers.PARTICLE);
   private static final float MAX_RADIUS = 32.0F;
   private Potion potion = Potions.EMPTY;
   private final List<MobEffectInstance> effects = Lists.<MobEffectInstance>newArrayList();
   private final Map<Entity, Integer> victims = Maps.newHashMap();
   private int duration = 600;
   private int waitTime = 20;
   private int reapplicationDelay = 20;
   private boolean fixedColor;
   private int durationOnUse;
   private float radiusOnUse;
   private float radiusPerTick;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public AreaEffectCloud(EntityType<? extends AreaEffectCloud> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
      this.noPhysics = true;
      this.setRadius(3.0F);
   }

   public AreaEffectCloud(Level var1, double var2, double var4, double var6) {
      this(EntityType.AREA_EFFECT_CLOUD, â˜ƒ);
      this.setPos(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      this.getEntityData().define(DATA_COLOR, 0);
      this.getEntityData().define(DATA_RADIUS, 0.5F);
      this.getEntityData().define(DATA_WAITING, false);
      this.getEntityData().define(DATA_PARTICLE, ParticleTypes.ENTITY_EFFECT);
   }

   public void setRadius(float var1) {
      if (!this.level.isClientSide) {
         this.getEntityData().set(DATA_RADIUS, Mth.clamp(â˜ƒ, 0.0F, 32.0F));
      }
   }

   @Override
   public void refreshDimensions() {
      double â˜ƒ = this.getX();
      double â˜ƒx = this.getY();
      double â˜ƒxx = this.getZ();
      super.refreshDimensions();
      this.setPos(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public float getRadius() {
      return this.getEntityData().get(DATA_RADIUS);
   }

   public void setPotion(Potion var1) {
      this.potion = â˜ƒ;
      if (!this.fixedColor) {
         this.updateColor();
      }
   }

   private void updateColor() {
      if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
         this.getEntityData().set(DATA_COLOR, 0);
      } else {
         this.getEntityData().set(DATA_COLOR, PotionUtils.getColor(PotionUtils.getAllEffects(this.potion, this.effects)));
      }
   }

   public void addEffect(MobEffectInstance var1) {
      this.effects.add(â˜ƒ);
      if (!this.fixedColor) {
         this.updateColor();
      }
   }

   public int getColor() {
      return this.getEntityData().get(DATA_COLOR);
   }

   public void setFixedColor(int var1) {
      this.fixedColor = true;
      this.getEntityData().set(DATA_COLOR, â˜ƒ);
   }

   public ParticleOptions getParticle() {
      return this.getEntityData().get(DATA_PARTICLE);
   }

   public void setParticle(ParticleOptions var1) {
      this.getEntityData().set(DATA_PARTICLE, â˜ƒ);
   }

   protected void setWaiting(boolean var1) {
      this.getEntityData().set(DATA_WAITING, â˜ƒ);
   }

   public boolean isWaiting() {
      return this.getEntityData().get(DATA_WAITING);
   }

   public int getDuration() {
      return this.duration;
   }

   public void setDuration(int var1) {
      this.duration = â˜ƒ;
   }

   @Override
   public void tick() {
      super.tick();
      boolean â˜ƒ = this.isWaiting();
      float â˜ƒx = this.getRadius();
      if (this.level.isClientSide) {
         if (â˜ƒ && this.random.nextBoolean()) {
            return;
         }

         ParticleOptions â˜ƒxxxx = this.getParticle();
         int â˜ƒxx;
         float â˜ƒxxx;
         if (â˜ƒ) {
            â˜ƒxx = 2;
            â˜ƒxxx = 0.2F;
         } else {
            â˜ƒxx = Mth.ceil((float) Math.PI * â˜ƒx * â˜ƒx);
            â˜ƒxxx = â˜ƒx;
         }

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒxx; ++â˜ƒxx) {
            float â˜ƒxxxxxx = this.random.nextFloat() * (float) (Math.PI * 2);
            float â˜ƒxxxxxxx = Mth.sqrt(this.random.nextFloat()) * â˜ƒxxx;
            double â˜ƒxxxxxxxx = this.getX() + (double)(Mth.cos(â˜ƒxxxxxx) * â˜ƒxxxxxxx);
            double â˜ƒxxxxxxxxx = this.getY();
            double â˜ƒxxxxxxxxxx = this.getZ() + (double)(Mth.sin(â˜ƒxxxxxx) * â˜ƒxxxxxxx);
            double â˜ƒxxx;
            double â˜ƒxxxx;
            double â˜ƒxxxxx;
            if (â˜ƒxxxx.getType() == ParticleTypes.ENTITY_EFFECT) {
               int â˜ƒxxxxxxxxxxx = â˜ƒ && this.random.nextBoolean() ? 16777215 : this.getColor();
               â˜ƒxxx = (double)((float)(â˜ƒxxxxxxxxxxx >> 16 & 0xFF) / 255.0F);
               â˜ƒxxxx = (double)((float)(â˜ƒxxxxxxxxxxx >> 8 & 0xFF) / 255.0F);
               â˜ƒxxxxx = (double)((float)(â˜ƒxxxxxxxxxxx & 0xFF) / 255.0F);
            } else if (â˜ƒ) {
               â˜ƒxxx = 0.0;
               â˜ƒxxxx = 0.0;
               â˜ƒxxxxx = 0.0;
            } else {
               â˜ƒxxx = (0.5 - this.random.nextDouble()) * 0.15;
               â˜ƒxxxx = 0.01F;
               â˜ƒxxxxx = (0.5 - this.random.nextDouble()) * 0.15;
            }

            this.level.addAlwaysVisibleParticle(â˜ƒxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         }
      } else {
         if (this.tickCount >= this.waitTime + this.duration) {
            this.discard();
            return;
         }

         boolean â˜ƒ = this.tickCount < this.waitTime;
         if (â˜ƒ != â˜ƒ) {
            this.setWaiting(â˜ƒ);
         }

         if (â˜ƒ) {
            return;
         }

         if (this.radiusPerTick != 0.0F) {
            â˜ƒx += this.radiusPerTick;
            if (â˜ƒx < 0.5F) {
               this.discard();
               return;
            }

            this.setRadius(â˜ƒx);
         }

         if (this.tickCount % 5 == 0) {
            this.victims.entrySet().removeIf(var1x -> this.tickCount >= var1x.getValue());
            List<MobEffectInstance> â˜ƒ = Lists.<MobEffectInstance>newArrayList();

            for(MobEffectInstance â˜ƒx : this.potion.getEffects()) {
               â˜ƒ.add(new MobEffectInstance(â˜ƒx.getEffect(), â˜ƒx.getDuration() / 4, â˜ƒx.getAmplifier(), â˜ƒx.isAmbient(), â˜ƒx.isVisible()));
            }

            â˜ƒ.addAll(this.effects);
            if (â˜ƒ.isEmpty()) {
               this.victims.clear();
            } else {
               List<LivingEntity> â˜ƒx = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
               if (!â˜ƒx.isEmpty()) {
                  for(LivingEntity â˜ƒxx : â˜ƒx) {
                     if (!this.victims.containsKey(â˜ƒxx) && â˜ƒxx.isAffectedByPotions()) {
                        double â˜ƒxxx = â˜ƒxx.getX() - this.getX();
                        double â˜ƒxxxx = â˜ƒxx.getZ() - this.getZ();
                        double â˜ƒxxxxx = â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx;
                        if (â˜ƒxxxxx <= (double)(â˜ƒx * â˜ƒx)) {
                           this.victims.put(â˜ƒxx, this.tickCount + this.reapplicationDelay);

                           for(MobEffectInstance â˜ƒxxxxxx : â˜ƒ) {
                              if (â˜ƒxxxxxx.getEffect().isInstantenous()) {
                                 â˜ƒxxxxxx.getEffect().applyInstantenousEffect(this, this.getOwner(), â˜ƒxx, â˜ƒxxxxxx.getAmplifier(), 0.5);
                              } else {
                                 â˜ƒxx.addEffect(new MobEffectInstance(â˜ƒxxxxxx), this);
                              }
                           }

                           if (this.radiusOnUse != 0.0F) {
                              â˜ƒx += this.radiusOnUse;
                              if (â˜ƒx < 0.5F) {
                                 this.discard();
                                 return;
                              }

                              this.setRadius(â˜ƒx);
                           }

                           if (this.durationOnUse != 0) {
                              this.duration += this.durationOnUse;
                              if (this.duration <= 0) {
                                 this.discard();
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

   public float getRadiusOnUse() {
      return this.radiusOnUse;
   }

   public void setRadiusOnUse(float var1) {
      this.radiusOnUse = â˜ƒ;
   }

   public float getRadiusPerTick() {
      return this.radiusPerTick;
   }

   public void setRadiusPerTick(float var1) {
      this.radiusPerTick = â˜ƒ;
   }

   public int getDurationOnUse() {
      return this.durationOnUse;
   }

   public void setDurationOnUse(int var1) {
      this.durationOnUse = â˜ƒ;
   }

   public int getWaitTime() {
      return this.waitTime;
   }

   public void setWaitTime(int var1) {
      this.waitTime = â˜ƒ;
   }

   public void setOwner(@Nullable LivingEntity var1) {
      this.owner = â˜ƒ;
      this.ownerUUID = â˜ƒ == null ? null : â˜ƒ.getUUID();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel) {
         Entity â˜ƒ = ((ServerLevel)this.level).getEntity(this.ownerUUID);
         if (â˜ƒ instanceof LivingEntity) {
            this.owner = (LivingEntity)â˜ƒ;
         }
      }

      return this.owner;
   }

   @Override
   protected void readAdditionalSaveData(CompoundTag var1) {
      this.tickCount = â˜ƒ.getInt("Age");
      this.duration = â˜ƒ.getInt("Duration");
      this.waitTime = â˜ƒ.getInt("WaitTime");
      this.reapplicationDelay = â˜ƒ.getInt("ReapplicationDelay");
      this.durationOnUse = â˜ƒ.getInt("DurationOnUse");
      this.radiusOnUse = â˜ƒ.getFloat("RadiusOnUse");
      this.radiusPerTick = â˜ƒ.getFloat("RadiusPerTick");
      this.setRadius(â˜ƒ.getFloat("Radius"));
      if (â˜ƒ.hasUUID("Owner")) {
         this.ownerUUID = â˜ƒ.getUUID("Owner");
      }

      if (â˜ƒ.contains("Particle", 8)) {
         try {
            this.setParticle(ParticleArgument.readParticle(new StringReader(â˜ƒ.getString("Particle"))));
         } catch (CommandSyntaxException var5) {
            LOGGER.warn("Couldn't load custom particle {}", â˜ƒ.getString("Particle"), var5);
         }
      }

      if (â˜ƒ.contains("Color", 99)) {
         this.setFixedColor(â˜ƒ.getInt("Color"));
      }

      if (â˜ƒ.contains("Potion", 8)) {
         this.setPotion(PotionUtils.getPotion(â˜ƒ));
      }

      if (â˜ƒ.contains("Effects", 9)) {
         ListTag â˜ƒ = â˜ƒ.getList("Effects", 10);
         this.effects.clear();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            MobEffectInstance â˜ƒxx = MobEffectInstance.load(â˜ƒ.getCompound(â˜ƒx));
            if (â˜ƒxx != null) {
               this.addEffect(â˜ƒxx);
            }
         }
      }
   }

   @Override
   protected void addAdditionalSaveData(CompoundTag var1) {
      â˜ƒ.putInt("Age", this.tickCount);
      â˜ƒ.putInt("Duration", this.duration);
      â˜ƒ.putInt("WaitTime", this.waitTime);
      â˜ƒ.putInt("ReapplicationDelay", this.reapplicationDelay);
      â˜ƒ.putInt("DurationOnUse", this.durationOnUse);
      â˜ƒ.putFloat("RadiusOnUse", this.radiusOnUse);
      â˜ƒ.putFloat("RadiusPerTick", this.radiusPerTick);
      â˜ƒ.putFloat("Radius", this.getRadius());
      â˜ƒ.putString("Particle", this.getParticle().writeToString());
      if (this.ownerUUID != null) {
         â˜ƒ.putUUID("Owner", this.ownerUUID);
      }

      if (this.fixedColor) {
         â˜ƒ.putInt("Color", this.getColor());
      }

      if (this.potion != Potions.EMPTY) {
         â˜ƒ.putString("Potion", Registry.POTION.getKey(this.potion).toString());
      }

      if (!this.effects.isEmpty()) {
         ListTag â˜ƒ = new ListTag();

         for(MobEffectInstance â˜ƒx : this.effects) {
            â˜ƒ.add(â˜ƒx.save(new CompoundTag()));
         }

         â˜ƒ.put("Effects", â˜ƒ);
      }
   }

   @Override
   public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
      if (DATA_RADIUS.equals(â˜ƒ)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(â˜ƒ);
   }

   public Potion getPotion() {
      return this.potion;
   }

   @Override
   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   @Override
   public Packet<?> getAddEntityPacket() {
      return new ClientboundAddEntityPacket(this);
   }

   @Override
   public EntityDimensions getDimensions(Pose var1) {
      return EntityDimensions.scalable(this.getRadius() * 2.0F, 0.5F);
   }
}
