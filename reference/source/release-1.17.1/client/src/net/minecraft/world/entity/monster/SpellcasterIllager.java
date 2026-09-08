package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public abstract class SpellcasterIllager extends AbstractIllager {
   private static final EntityDataAccessor<Byte> DATA_SPELL_CASTING_ID = SynchedEntityData.defineId(SpellcasterIllager.class, EntityDataSerializers.BYTE);
   protected int spellCastingTickCount;
   private SpellcasterIllager.IllagerSpell currentSpell = SpellcasterIllager.IllagerSpell.NONE;

   protected SpellcasterIllager(EntityType<? extends SpellcasterIllager> var1, Level var2) {
      super(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void defineSynchedData() {
      super.defineSynchedData();
      this.entityData.define(DATA_SPELL_CASTING_ID, (byte)0);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag var1) {
      super.readAdditionalSaveData(â˜ƒ);
      this.spellCastingTickCount = â˜ƒ.getInt("SpellTicks");
   }

   @Override
   public void addAdditionalSaveData(CompoundTag var1) {
      super.addAdditionalSaveData(â˜ƒ);
      â˜ƒ.putInt("SpellTicks", this.spellCastingTickCount);
   }

   @Override
   public AbstractIllager.IllagerArmPose getArmPose() {
      if (this.isCastingSpell()) {
         return AbstractIllager.IllagerArmPose.SPELLCASTING;
      } else {
         return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
      }
   }

   public boolean isCastingSpell() {
      if (this.level.isClientSide) {
         return this.entityData.get(DATA_SPELL_CASTING_ID) > 0;
      } else {
         return this.spellCastingTickCount > 0;
      }
   }

   public void setIsCastingSpell(SpellcasterIllager.IllagerSpell var1) {
      this.currentSpell = â˜ƒ;
      this.entityData.set(DATA_SPELL_CASTING_ID, (byte)â˜ƒ.id);
   }

   protected SpellcasterIllager.IllagerSpell getCurrentSpell() {
      return !this.level.isClientSide ? this.currentSpell : SpellcasterIllager.IllagerSpell.byId(this.entityData.get(DATA_SPELL_CASTING_ID));
   }

   @Override
   protected void customServerAiStep() {
      super.customServerAiStep();
      if (this.spellCastingTickCount > 0) {
         --this.spellCastingTickCount;
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.level.isClientSide && this.isCastingSpell()) {
         SpellcasterIllager.IllagerSpell â˜ƒ = this.getCurrentSpell();
         double â˜ƒx = â˜ƒ.spellColor[0];
         double â˜ƒxx = â˜ƒ.spellColor[1];
         double â˜ƒxxx = â˜ƒ.spellColor[2];
         float â˜ƒxxxx = this.yBodyRot * (float) (Math.PI / 180.0) + Mth.cos((float)this.tickCount * 0.6662F) * 0.25F;
         float â˜ƒxxxxx = Mth.cos(â˜ƒxxxx);
         float â˜ƒxxxxxx = Mth.sin(â˜ƒxxxx);
         this.level
            .addParticle(
               ParticleTypes.ENTITY_EFFECT, this.getX() + (double)â˜ƒxxxxx * 0.6, this.getY() + 1.8, this.getZ() + (double)â˜ƒxxxxxx * 0.6, â˜ƒx, â˜ƒxx, â˜ƒxxx
            );
         this.level
            .addParticle(
               ParticleTypes.ENTITY_EFFECT, this.getX() - (double)â˜ƒxxxxx * 0.6, this.getY() + 1.8, this.getZ() - (double)â˜ƒxxxxxx * 0.6, â˜ƒx, â˜ƒxx, â˜ƒxxx
            );
      }
   }

   protected int getSpellCastingTime() {
      return this.spellCastingTickCount;
   }

   protected abstract SoundEvent getCastingSoundEvent();

   protected static enum IllagerSpell {
      NONE(0, 0.0, 0.0, 0.0),
      SUMMON_VEX(1, 0.7, 0.7, 0.8),
      FANGS(2, 0.4, 0.3, 0.35),
      WOLOLO(3, 0.7, 0.5, 0.2),
      DISAPPEAR(4, 0.3, 0.3, 0.8),
      BLINDNESS(5, 0.1, 0.1, 0.2);

      final int id;
      final double[] spellColor;

      private IllagerSpell(int var3, double var4, double var6, double var8) {
         this.id = â˜ƒ;
         this.spellColor = new double[]{â˜ƒ, â˜ƒ, â˜ƒ};
      }

      public static SpellcasterIllager.IllagerSpell byId(int var0) {
         for(SpellcasterIllager.IllagerSpell â˜ƒ : values()) {
            if (â˜ƒ == â˜ƒ.id) {
               return â˜ƒ;
            }
         }

         return NONE;
      }
   }

   protected class SpellcasterCastingSpellGoal extends Goal {
      public SpellcasterCastingSpellGoal() {
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      @Override
      public boolean canUse() {
         return SpellcasterIllager.this.getSpellCastingTime() > 0;
      }

      @Override
      public void start() {
         super.start();
         SpellcasterIllager.this.navigation.stop();
      }

      @Override
      public void stop() {
         super.stop();
         SpellcasterIllager.this.setIsCastingSpell(SpellcasterIllager.IllagerSpell.NONE);
      }

      @Override
      public void tick() {
         if (SpellcasterIllager.this.getTarget() != null) {
            SpellcasterIllager.this.getLookControl()
               .setLookAt(SpellcasterIllager.this.getTarget(), (float)SpellcasterIllager.this.getMaxHeadYRot(), (float)SpellcasterIllager.this.getMaxHeadXRot());
         }
      }
   }

   protected abstract class SpellcasterUseSpellGoal extends Goal {
      protected int attackWarmupDelay;
      protected int nextAttackTickCount;

      @Override
      public boolean canUse() {
         LivingEntity â˜ƒ = SpellcasterIllager.this.getTarget();
         if (â˜ƒ == null || !â˜ƒ.isAlive()) {
            return false;
         } else if (SpellcasterIllager.this.isCastingSpell()) {
            return false;
         } else {
            return SpellcasterIllager.this.tickCount >= this.nextAttackTickCount;
         }
      }

      @Override
      public boolean canContinueToUse() {
         LivingEntity â˜ƒ = SpellcasterIllager.this.getTarget();
         return â˜ƒ != null && â˜ƒ.isAlive() && this.attackWarmupDelay > 0;
      }

      @Override
      public void start() {
         this.attackWarmupDelay = this.getCastWarmupTime();
         SpellcasterIllager.this.spellCastingTickCount = this.getCastingTime();
         this.nextAttackTickCount = SpellcasterIllager.this.tickCount + this.getCastingInterval();
         SoundEvent â˜ƒ = this.getSpellPrepareSound();
         if (â˜ƒ != null) {
            SpellcasterIllager.this.playSound(â˜ƒ, 1.0F, 1.0F);
         }

         SpellcasterIllager.this.setIsCastingSpell(this.getSpell());
      }

      @Override
      public void tick() {
         --this.attackWarmupDelay;
         if (this.attackWarmupDelay == 0) {
            this.performSpellCasting();
            SpellcasterIllager.this.playSound(SpellcasterIllager.this.getCastingSoundEvent(), 1.0F, 1.0F);
         }
      }

      protected abstract void performSpellCasting();

      protected int getCastWarmupTime() {
         return 20;
      }

      protected abstract int getCastingTime();

      protected abstract int getCastingInterval();

      @Nullable
      protected abstract SoundEvent getSpellPrepareSound();

      protected abstract SpellcasterIllager.IllagerSpell getSpell();
   }
}
