package net.minecraft.world.damagesource;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CombatTracker {
   public static final int RESET_DAMAGE_STATUS_TIME = 100;
   public static final int RESET_COMBAT_STATUS_TIME = 300;
   private final List<CombatEntry> entries = Lists.<CombatEntry>newArrayList();
   private final LivingEntity mob;
   private int lastDamageTime;
   private int combatStartTime;
   private int combatEndTime;
   private boolean inCombat;
   private boolean takingDamage;
   @Nullable
   private String nextLocation;

   public CombatTracker(LivingEntity var1) {
      this.mob = â˜ƒ;
   }

   public void prepareForDamage() {
      this.resetPreparedStatus();
      Optional<BlockPos> â˜ƒ = this.mob.getLastClimbablePos();
      if (â˜ƒ.isPresent()) {
         BlockState â˜ƒx = this.mob.level.getBlockState((BlockPos)â˜ƒ.get());
         if (â˜ƒx.is(Blocks.LADDER) || â˜ƒx.is(BlockTags.TRAPDOORS)) {
            this.nextLocation = "ladder";
         } else if (â˜ƒx.is(Blocks.VINE)) {
            this.nextLocation = "vines";
         } else if (â˜ƒx.is(Blocks.WEEPING_VINES) || â˜ƒx.is(Blocks.WEEPING_VINES_PLANT)) {
            this.nextLocation = "weeping_vines";
         } else if (â˜ƒx.is(Blocks.TWISTING_VINES) || â˜ƒx.is(Blocks.TWISTING_VINES_PLANT)) {
            this.nextLocation = "twisting_vines";
         } else if (â˜ƒx.is(Blocks.SCAFFOLDING)) {
            this.nextLocation = "scaffolding";
         } else {
            this.nextLocation = "other_climbable";
         }
      } else if (this.mob.isInWater()) {
         this.nextLocation = "water";
      }
   }

   public void recordDamage(DamageSource var1, float var2, float var3) {
      this.recheckStatus();
      this.prepareForDamage();
      CombatEntry â˜ƒ = new CombatEntry(â˜ƒ, this.mob.tickCount, â˜ƒ, â˜ƒ, this.nextLocation, this.mob.fallDistance);
      this.entries.add(â˜ƒ);
      this.lastDamageTime = this.mob.tickCount;
      this.takingDamage = true;
      if (â˜ƒ.isCombatRelated() && !this.inCombat && this.mob.isAlive()) {
         this.inCombat = true;
         this.combatStartTime = this.mob.tickCount;
         this.combatEndTime = this.combatStartTime;
         this.mob.onEnterCombat();
      }
   }

   public Component getDeathMessage() {
      if (this.entries.isEmpty()) {
         return new TranslatableComponent("death.attack.generic", this.mob.getDisplayName());
      } else {
         CombatEntry â˜ƒx = this.getMostSignificantFall();
         CombatEntry â˜ƒxx = (CombatEntry)this.entries.get(this.entries.size() - 1);
         Component â˜ƒxxx = â˜ƒxx.getAttackerName();
         Entity â˜ƒxxxx = â˜ƒxx.getSource().getEntity();
         Component â˜ƒ;
         if (â˜ƒx != null && â˜ƒxx.getSource() == DamageSource.FALL) {
            Component â˜ƒxxxxx = â˜ƒx.getAttackerName();
            if (â˜ƒx.getSource() == DamageSource.FALL || â˜ƒx.getSource() == DamageSource.OUT_OF_WORLD) {
               â˜ƒ = new TranslatableComponent("death.fell.accident." + this.getFallLocation(â˜ƒx), this.mob.getDisplayName());
            } else if (â˜ƒxxxxx != null && !â˜ƒxxxxx.equals(â˜ƒxxx)) {
               Entity â˜ƒxxxxx = â˜ƒx.getSource().getEntity();
               ItemStack â˜ƒxxxxxx = â˜ƒxxxxx instanceof LivingEntity ? ((LivingEntity)â˜ƒxxxxx).getMainHandItem() : ItemStack.EMPTY;
               if (!â˜ƒxxxxxx.isEmpty() && â˜ƒxxxxxx.hasCustomHoverName()) {
                  â˜ƒ = new TranslatableComponent("death.fell.assist.item", this.mob.getDisplayName(), â˜ƒxxxxx, â˜ƒxxxxxx.getDisplayName());
               } else {
                  â˜ƒ = new TranslatableComponent("death.fell.assist", this.mob.getDisplayName(), â˜ƒxxxxx);
               }
            } else if (â˜ƒxxx != null) {
               ItemStack â˜ƒxxxxx = â˜ƒxxxx instanceof LivingEntity ? ((LivingEntity)â˜ƒxxxx).getMainHandItem() : ItemStack.EMPTY;
               if (!â˜ƒxxxxx.isEmpty() && â˜ƒxxxxx.hasCustomHoverName()) {
                  â˜ƒ = new TranslatableComponent("death.fell.finish.item", this.mob.getDisplayName(), â˜ƒxxx, â˜ƒxxxxx.getDisplayName());
               } else {
                  â˜ƒ = new TranslatableComponent("death.fell.finish", this.mob.getDisplayName(), â˜ƒxxx);
               }
            } else {
               â˜ƒ = new TranslatableComponent("death.fell.killer", this.mob.getDisplayName());
            }
         } else {
            â˜ƒ = â˜ƒxx.getSource().getLocalizedDeathMessage(this.mob);
         }

         return â˜ƒ;
      }
   }

   @Nullable
   public LivingEntity getKiller() {
      LivingEntity â˜ƒ = null;
      Player â˜ƒx = null;
      float â˜ƒxx = 0.0F;
      float â˜ƒxxx = 0.0F;

      for(CombatEntry â˜ƒxxxx : this.entries) {
         if (â˜ƒxxxx.getSource().getEntity() instanceof Player && (â˜ƒx == null || â˜ƒxxxx.getDamage() > â˜ƒxxx)) {
            â˜ƒxxx = â˜ƒxxxx.getDamage();
            â˜ƒx = (Player)â˜ƒxxxx.getSource().getEntity();
         }

         if (â˜ƒxxxx.getSource().getEntity() instanceof LivingEntity && (â˜ƒ == null || â˜ƒxxxx.getDamage() > â˜ƒxx)) {
            â˜ƒxx = â˜ƒxxxx.getDamage();
            â˜ƒ = (LivingEntity)â˜ƒxxxx.getSource().getEntity();
         }
      }

      return (LivingEntity)(â˜ƒx != null && â˜ƒxxx >= â˜ƒxx / 3.0F ? â˜ƒx : â˜ƒ);
   }

   @Nullable
   private CombatEntry getMostSignificantFall() {
      CombatEntry â˜ƒ = null;
      CombatEntry â˜ƒx = null;
      float â˜ƒxx = 0.0F;
      float â˜ƒxxx = 0.0F;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < this.entries.size(); ++â˜ƒxxxx) {
         CombatEntry â˜ƒxxxxx = (CombatEntry)this.entries.get(â˜ƒxxxx);
         CombatEntry â˜ƒxxxxxx = â˜ƒxxxx > 0 ? (CombatEntry)this.entries.get(â˜ƒxxxx - 1) : null;
         if ((â˜ƒxxxxx.getSource() == DamageSource.FALL || â˜ƒxxxxx.getSource() == DamageSource.OUT_OF_WORLD)
            && â˜ƒxxxxx.getFallDistance() > 0.0F
            && (â˜ƒ == null || â˜ƒxxxxx.getFallDistance() > â˜ƒxxx)) {
            if (â˜ƒxxxx > 0) {
               â˜ƒ = â˜ƒxxxxxx;
            } else {
               â˜ƒ = â˜ƒxxxxx;
            }

            â˜ƒxxx = â˜ƒxxxxx.getFallDistance();
         }

         if (â˜ƒxxxxx.getLocation() != null && (â˜ƒx == null || â˜ƒxxxxx.getDamage() > â˜ƒxx)) {
            â˜ƒx = â˜ƒxxxxx;
            â˜ƒxx = â˜ƒxxxxx.getDamage();
         }
      }

      if (â˜ƒxxx > 5.0F && â˜ƒ != null) {
         return â˜ƒ;
      } else {
         return â˜ƒxx > 5.0F && â˜ƒx != null ? â˜ƒx : null;
      }
   }

   private String getFallLocation(CombatEntry var1) {
      return â˜ƒ.getLocation() == null ? "generic" : â˜ƒ.getLocation();
   }

   public boolean isTakingDamage() {
      this.recheckStatus();
      return this.takingDamage;
   }

   public boolean isInCombat() {
      this.recheckStatus();
      return this.inCombat;
   }

   public int getCombatDuration() {
      return this.inCombat ? this.mob.tickCount - this.combatStartTime : this.combatEndTime - this.combatStartTime;
   }

   private void resetPreparedStatus() {
      this.nextLocation = null;
   }

   public void recheckStatus() {
      int â˜ƒ = this.inCombat ? 300 : 100;
      if (this.takingDamage && (!this.mob.isAlive() || this.mob.tickCount - this.lastDamageTime > â˜ƒ)) {
         boolean â˜ƒx = this.inCombat;
         this.takingDamage = false;
         this.inCombat = false;
         this.combatEndTime = this.mob.tickCount;
         if (â˜ƒx) {
            this.mob.onLeaveCombat();
         }

         this.entries.clear();
      }
   }

   public LivingEntity getMob() {
      return this.mob;
   }

   @Nullable
   public CombatEntry getLastEntry() {
      return this.entries.isEmpty() ? null : (CombatEntry)this.entries.get(this.entries.size() - 1);
   }

   public int getKillerId() {
      LivingEntity â˜ƒ = this.getKiller();
      return â˜ƒ == null ? -1 : â˜ƒ.getId();
   }
}
