package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

public class InteractGoal extends LookAtPlayerGoal {
   public InteractGoal(Mob var1, Class<? extends LivingEntity> var2, float var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
      this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
   }

   public InteractGoal(Mob var1, Class<? extends LivingEntity> var2, float var3, float var4) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
   }
}
