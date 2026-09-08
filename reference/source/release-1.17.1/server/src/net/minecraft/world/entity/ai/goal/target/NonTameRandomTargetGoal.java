package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;

public class NonTameRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final TamableAnimal tamableMob;

   public NonTameRandomTargetGoal(TamableAnimal var1, Class<T> var2, boolean var3, @Nullable Predicate<LivingEntity> var4) {
      super(â˜ƒ, â˜ƒ, 10, â˜ƒ, false, â˜ƒ);
      this.tamableMob = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      return !this.tamableMob.isTame() && super.canUse();
   }

   @Override
   public boolean canContinueToUse() {
      return this.targetConditions != null ? this.targetConditions.test(this.mob, this.target) : super.canContinueToUse();
   }
}
