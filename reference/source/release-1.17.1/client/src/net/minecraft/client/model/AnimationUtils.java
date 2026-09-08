package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CrossbowItem;

public class AnimationUtils {
   public static void animateCrossbowHold(ModelPart var0, ModelPart var1, ModelPart var2, boolean var3) {
      ModelPart â˜ƒ = â˜ƒ ? â˜ƒ : â˜ƒ;
      ModelPart â˜ƒx = â˜ƒ ? â˜ƒ : â˜ƒ;
      â˜ƒ.yRot = (â˜ƒ ? -0.3F : 0.3F) + â˜ƒ.yRot;
      â˜ƒx.yRot = (â˜ƒ ? 0.6F : -0.6F) + â˜ƒ.yRot;
      â˜ƒ.xRot = (float) (-Math.PI / 2) + â˜ƒ.xRot + 0.1F;
      â˜ƒx.xRot = -1.5F + â˜ƒ.xRot;
   }

   public static void animateCrossbowCharge(ModelPart var0, ModelPart var1, LivingEntity var2, boolean var3) {
      ModelPart â˜ƒ = â˜ƒ ? â˜ƒ : â˜ƒ;
      ModelPart â˜ƒx = â˜ƒ ? â˜ƒ : â˜ƒ;
      â˜ƒ.yRot = â˜ƒ ? -0.8F : 0.8F;
      â˜ƒ.xRot = -0.97079635F;
      â˜ƒx.xRot = â˜ƒ.xRot;
      float â˜ƒxx = (float)CrossbowItem.getChargeDuration(â˜ƒ.getUseItem());
      float â˜ƒxxx = Mth.clamp((float)â˜ƒ.getTicksUsingItem(), 0.0F, â˜ƒxx);
      float â˜ƒxxxx = â˜ƒxxx / â˜ƒxx;
      â˜ƒx.yRot = Mth.lerp(â˜ƒxxxx, 0.4F, 0.85F) * (float)(â˜ƒ ? 1 : -1);
      â˜ƒx.xRot = Mth.lerp(â˜ƒxxxx, â˜ƒx.xRot, (float) (-Math.PI / 2));
   }

   public static <T extends Mob> void swingWeaponDown(ModelPart var0, ModelPart var1, T var2, float var3, float var4) {
      float â˜ƒ = Mth.sin(â˜ƒ * (float) Math.PI);
      float â˜ƒx = Mth.sin((1.0F - (1.0F - â˜ƒ) * (1.0F - â˜ƒ)) * (float) Math.PI);
      â˜ƒ.zRot = 0.0F;
      â˜ƒ.zRot = 0.0F;
      â˜ƒ.yRot = (float) (Math.PI / 20);
      â˜ƒ.yRot = (float) (-Math.PI / 20);
      if (â˜ƒ.getMainArm() == HumanoidArm.RIGHT) {
         â˜ƒ.xRot = -1.8849558F + Mth.cos(â˜ƒ * 0.09F) * 0.15F;
         â˜ƒ.xRot = -0.0F + Mth.cos(â˜ƒ * 0.19F) * 0.5F;
         â˜ƒ.xRot += â˜ƒ * 2.2F - â˜ƒx * 0.4F;
         â˜ƒ.xRot += â˜ƒ * 1.2F - â˜ƒx * 0.4F;
      } else {
         â˜ƒ.xRot = -0.0F + Mth.cos(â˜ƒ * 0.19F) * 0.5F;
         â˜ƒ.xRot = -1.8849558F + Mth.cos(â˜ƒ * 0.09F) * 0.15F;
         â˜ƒ.xRot += â˜ƒ * 1.2F - â˜ƒx * 0.4F;
         â˜ƒ.xRot += â˜ƒ * 2.2F - â˜ƒx * 0.4F;
      }

      bobArms(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void bobModelPart(ModelPart var0, float var1, float var2) {
      â˜ƒ.zRot += â˜ƒ * (Mth.cos(â˜ƒ * 0.09F) * 0.05F + 0.05F);
      â˜ƒ.xRot += â˜ƒ * Mth.sin(â˜ƒ * 0.067F) * 0.05F;
   }

   public static void bobArms(ModelPart var0, ModelPart var1, float var2) {
      bobModelPart(â˜ƒ, â˜ƒ, 1.0F);
      bobModelPart(â˜ƒ, â˜ƒ, -1.0F);
   }

   public static void animateZombieArms(ModelPart var0, ModelPart var1, boolean var2, float var3, float var4) {
      float â˜ƒ = Mth.sin(â˜ƒ * (float) Math.PI);
      float â˜ƒx = Mth.sin((1.0F - (1.0F - â˜ƒ) * (1.0F - â˜ƒ)) * (float) Math.PI);
      â˜ƒ.zRot = 0.0F;
      â˜ƒ.zRot = 0.0F;
      â˜ƒ.yRot = -(0.1F - â˜ƒ * 0.6F);
      â˜ƒ.yRot = 0.1F - â˜ƒ * 0.6F;
      float â˜ƒxx = (float) -Math.PI / (â˜ƒ ? 1.5F : 2.25F);
      â˜ƒ.xRot = â˜ƒxx;
      â˜ƒ.xRot = â˜ƒxx;
      â˜ƒ.xRot += â˜ƒ * 1.2F - â˜ƒx * 0.4F;
      â˜ƒ.xRot += â˜ƒ * 1.2F - â˜ƒx * 0.4F;
      bobArms(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
