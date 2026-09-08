package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CrossbowAttack<E extends Mob & CrossbowAttackMob, T extends LivingEntity> extends Behavior<E> {
   private static final int TIMEOUT = 1200;
   private int attackDelay;
   private CrossbowAttack.CrossbowState crossbowState = CrossbowAttack.CrossbowState.UNCHARGED;

   public CrossbowAttack() {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), 1200);
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, E var2) {
      LivingEntity â˜ƒ = getAttackTarget(â˜ƒ);
      return â˜ƒ.isHolding(Items.CROSSBOW) && BehaviorUtils.canSee(â˜ƒ, â˜ƒ) && BehaviorUtils.isWithinAttackRange(â˜ƒ, â˜ƒ, 0);
   }

   protected boolean canStillUse(ServerLevel var1, E var2, long var3) {
      return â˜ƒ.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && this.checkExtraStartConditions(â˜ƒ, â˜ƒ);
   }

   protected void tick(ServerLevel var1, E var2, long var3) {
      LivingEntity â˜ƒ = getAttackTarget(â˜ƒ);
      this.lookAtTarget(â˜ƒ, â˜ƒ);
      this.crossbowAttack(â˜ƒ, â˜ƒ);
   }

   protected void stop(ServerLevel var1, E var2, long var3) {
      if (â˜ƒ.isUsingItem()) {
         â˜ƒ.stopUsingItem();
      }

      if (â˜ƒ.isHolding(Items.CROSSBOW)) {
         â˜ƒ.setChargingCrossbow(false);
         CrossbowItem.setCharged(â˜ƒ.getUseItem(), false);
      }
   }

   private void crossbowAttack(E var1, LivingEntity var2) {
      if (this.crossbowState == CrossbowAttack.CrossbowState.UNCHARGED) {
         â˜ƒ.startUsingItem(ProjectileUtil.getWeaponHoldingHand(â˜ƒ, Items.CROSSBOW));
         this.crossbowState = CrossbowAttack.CrossbowState.CHARGING;
         â˜ƒ.setChargingCrossbow(true);
      } else if (this.crossbowState == CrossbowAttack.CrossbowState.CHARGING) {
         if (!â˜ƒ.isUsingItem()) {
            this.crossbowState = CrossbowAttack.CrossbowState.UNCHARGED;
         }

         int â˜ƒ = â˜ƒ.getTicksUsingItem();
         ItemStack â˜ƒx = â˜ƒ.getUseItem();
         if (â˜ƒ >= CrossbowItem.getChargeDuration(â˜ƒx)) {
            â˜ƒ.releaseUsingItem();
            this.crossbowState = CrossbowAttack.CrossbowState.CHARGED;
            this.attackDelay = 20 + â˜ƒ.getRandom().nextInt(20);
            â˜ƒ.setChargingCrossbow(false);
         }
      } else if (this.crossbowState == CrossbowAttack.CrossbowState.CHARGED) {
         --this.attackDelay;
         if (this.attackDelay == 0) {
            this.crossbowState = CrossbowAttack.CrossbowState.READY_TO_ATTACK;
         }
      } else if (this.crossbowState == CrossbowAttack.CrossbowState.READY_TO_ATTACK) {
         â˜ƒ.performRangedAttack(â˜ƒ, 1.0F);
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(ProjectileUtil.getWeaponHoldingHand(â˜ƒ, Items.CROSSBOW));
         CrossbowItem.setCharged(â˜ƒ, false);
         this.crossbowState = CrossbowAttack.CrossbowState.UNCHARGED;
      }
   }

   private void lookAtTarget(Mob var1, LivingEntity var2) {
      â˜ƒ.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(â˜ƒ, true));
   }

   private static LivingEntity getAttackTarget(LivingEntity var0) {
      return (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
   }

   static enum CrossbowState {
      UNCHARGED,
      CHARGING,
      CHARGED,
      READY_TO_ATTACK;
   }
}
