package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;

public abstract class AbstractZombieModel<T extends Monster> extends HumanoidModel<T> {
   protected AbstractZombieModel(ModelPart var1) {
      super(â˜ƒ);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(â˜ƒ), this.attackTime, â˜ƒ);
   }

   public abstract boolean isAggressive(T var1);
}
