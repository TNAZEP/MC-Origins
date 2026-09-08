package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class WaterAvoidingRandomStrollGoal extends RandomStrollGoal {
   public static final float PROBABILITY = 0.001F;
   protected final float probability;

   public WaterAvoidingRandomStrollGoal(PathfinderMob var1, double var2) {
      this(â˜ƒ, â˜ƒ, 0.001F);
   }

   public WaterAvoidingRandomStrollGoal(PathfinderMob var1, double var2, float var4) {
      super(â˜ƒ, â˜ƒ);
      this.probability = â˜ƒ;
   }

   @Nullable
   @Override
   protected Vec3 getPosition() {
      if (this.mob.isInWaterOrBubble()) {
         Vec3 â˜ƒ = LandRandomPos.getPos(this.mob, 15, 7);
         return â˜ƒ == null ? super.getPosition() : â˜ƒ;
      } else {
         return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
      }
   }
}
