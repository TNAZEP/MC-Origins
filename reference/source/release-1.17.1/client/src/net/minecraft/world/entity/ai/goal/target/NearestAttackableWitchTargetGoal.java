package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raider;

public class NearestAttackableWitchTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private boolean canAttack = true;

   public NearestAttackableWitchTargetGoal(Raider var1, Class<T> var2, int var3, boolean var4, boolean var5, @Nullable Predicate<LivingEntity> var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setCanAttack(boolean var1) {
      this.canAttack = â˜ƒ;
   }

   @Override
   public boolean canUse() {
      return this.canAttack && super.canUse();
   }
}
