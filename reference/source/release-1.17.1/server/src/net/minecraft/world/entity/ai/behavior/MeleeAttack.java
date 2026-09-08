package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;

public class MeleeAttack extends Behavior<Mob> {
   private final int cooldownBetweenAttacks;

   public MeleeAttack(int var1) {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryStatus.REGISTERED,
            MemoryModuleType.ATTACK_TARGET,
            MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryStatus.VALUE_ABSENT
         )
      );
      this.cooldownBetweenAttacks = â˜ƒ;
   }

   protected boolean checkExtraStartConditions(ServerLevel var1, Mob var2) {
      LivingEntity â˜ƒ = this.getAttackTarget(â˜ƒ);
      return !this.isHoldingUsableProjectileWeapon(â˜ƒ) && BehaviorUtils.canSee(â˜ƒ, â˜ƒ) && BehaviorUtils.isWithinMeleeAttackRange(â˜ƒ, â˜ƒ);
   }

   private boolean isHoldingUsableProjectileWeapon(Mob var1) {
      return â˜ƒ.isHolding(var1x -> {
         Item â˜ƒ = var1x.getItem();
         return â˜ƒ instanceof ProjectileWeaponItem && â˜ƒ.canFireProjectileWeapon((ProjectileWeaponItem)â˜ƒ);
      });
   }

   protected void start(ServerLevel var1, Mob var2, long var3) {
      LivingEntity â˜ƒ = this.getAttackTarget(â˜ƒ);
      BehaviorUtils.lookAtEntity(â˜ƒ, â˜ƒ);
      â˜ƒ.swing(InteractionHand.MAIN_HAND);
      â˜ƒ.doHurtTarget(â˜ƒ);
      â˜ƒ.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, (long)this.cooldownBetweenAttacks);
   }

   private LivingEntity getAttackTarget(Mob var1) {
      return (LivingEntity)â˜ƒ.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
   }
}
