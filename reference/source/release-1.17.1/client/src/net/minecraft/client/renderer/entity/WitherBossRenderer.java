package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.boss.wither.WitherBoss;

public class WitherBossRenderer extends MobRenderer<WitherBoss, WitherBossModel<WitherBoss>> {
   private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");

   public WitherBossRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new WitherBossModel<>(â˜ƒ.bakeLayer(ModelLayers.WITHER)), 1.0F);
      this.addLayer(new WitherArmorLayer(this, â˜ƒ.getModelSet()));
   }

   protected int getBlockLightLevel(WitherBoss var1, BlockPos var2) {
      return 15;
   }

   public ResourceLocation getTextureLocation(WitherBoss var1) {
      int â˜ƒ = â˜ƒ.getInvulnerableTicks();
      return â˜ƒ > 0 && (â˜ƒ > 80 || â˜ƒ / 5 % 2 != 1) ? WITHER_INVULNERABLE_LOCATION : WITHER_LOCATION;
   }

   protected void scale(WitherBoss var1, PoseStack var2, float var3) {
      float â˜ƒ = 2.0F;
      int â˜ƒx = â˜ƒ.getInvulnerableTicks();
      if (â˜ƒx > 0) {
         â˜ƒ -= ((float)â˜ƒx - â˜ƒ) / 220.0F * 0.5F;
      }

      â˜ƒ.scale(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
