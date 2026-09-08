package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.PandaModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.PandaHoldsItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Panda;

public class PandaRenderer extends MobRenderer<Panda, PandaModel<Panda>> {
   private static final Map<Panda.Gene, ResourceLocation> TEXTURES = Util.make(Maps.newEnumMap(Panda.Gene.class), var0 -> {
      var0.put(Panda.Gene.NORMAL, new ResourceLocation("textures/entity/panda/panda.png"));
      var0.put(Panda.Gene.LAZY, new ResourceLocation("textures/entity/panda/lazy_panda.png"));
      var0.put(Panda.Gene.WORRIED, new ResourceLocation("textures/entity/panda/worried_panda.png"));
      var0.put(Panda.Gene.PLAYFUL, new ResourceLocation("textures/entity/panda/playful_panda.png"));
      var0.put(Panda.Gene.BROWN, new ResourceLocation("textures/entity/panda/brown_panda.png"));
      var0.put(Panda.Gene.WEAK, new ResourceLocation("textures/entity/panda/weak_panda.png"));
      var0.put(Panda.Gene.AGGRESSIVE, new ResourceLocation("textures/entity/panda/aggressive_panda.png"));
   });

   public PandaRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new PandaModel<>(â˜ƒ.bakeLayer(ModelLayers.PANDA)), 0.9F);
      this.addLayer(new PandaHoldsItemLayer(this));
   }

   public ResourceLocation getTextureLocation(Panda var1) {
      return (ResourceLocation)TEXTURES.getOrDefault(â˜ƒ.getVariant(), (ResourceLocation)TEXTURES.get(Panda.Gene.NORMAL));
   }

   protected void setupRotations(Panda var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.rollCounter > 0) {
         int â˜ƒ = â˜ƒ.rollCounter;
         int â˜ƒx = â˜ƒ + 1;
         float â˜ƒxx = 7.0F;
         float â˜ƒxxx = â˜ƒ.isBaby() ? 0.3F : 0.8F;
         if (â˜ƒ < 8) {
            float â˜ƒxxxx = (float)(90 * â˜ƒ) / 7.0F;
            float â˜ƒxxxxx = (float)(90 * â˜ƒx) / 7.0F;
            float â˜ƒxxxxxx = this.getAngle(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒx, â˜ƒ, 8.0F);
            â˜ƒ.translate(0.0, (double)((â˜ƒxxx + 0.2F) * (â˜ƒxxxxxx / 90.0F)), 0.0);
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-â˜ƒxxxxxx));
         } else if (â˜ƒ < 16) {
            float â˜ƒ = ((float)â˜ƒ - 8.0F) / 7.0F;
            float â˜ƒx = 90.0F + 90.0F * â˜ƒ;
            float â˜ƒxx = 90.0F + 90.0F * ((float)â˜ƒx - 8.0F) / 7.0F;
            float â˜ƒxxx = this.getAngle(â˜ƒx, â˜ƒxx, â˜ƒx, â˜ƒ, 16.0F);
            â˜ƒ.translate(0.0, (double)(â˜ƒxxx + 0.2F + (â˜ƒxxx - 0.2F) * (â˜ƒxxx - 90.0F) / 90.0F), 0.0);
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-â˜ƒxxx));
         } else if ((float)â˜ƒ < 24.0F) {
            float â˜ƒ = ((float)â˜ƒ - 16.0F) / 7.0F;
            float â˜ƒx = 180.0F + 90.0F * â˜ƒ;
            float â˜ƒxx = 180.0F + 90.0F * ((float)â˜ƒx - 16.0F) / 7.0F;
            float â˜ƒxxx = this.getAngle(â˜ƒx, â˜ƒxx, â˜ƒx, â˜ƒ, 24.0F);
            â˜ƒ.translate(0.0, (double)(â˜ƒxxx + â˜ƒxxx * (270.0F - â˜ƒxxx) / 90.0F), 0.0);
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-â˜ƒxxx));
         } else if (â˜ƒ < 32) {
            float â˜ƒ = ((float)â˜ƒ - 24.0F) / 7.0F;
            float â˜ƒx = 270.0F + 90.0F * â˜ƒ;
            float â˜ƒxx = 270.0F + 90.0F * ((float)â˜ƒx - 24.0F) / 7.0F;
            float â˜ƒxxx = this.getAngle(â˜ƒx, â˜ƒxx, â˜ƒx, â˜ƒ, 32.0F);
            â˜ƒ.translate(0.0, (double)(â˜ƒxxx * ((360.0F - â˜ƒxxx) / 90.0F)), 0.0);
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-â˜ƒxxx));
         }
      }

      float â˜ƒ = â˜ƒ.getSitAmount(â˜ƒ);
      if (â˜ƒ > 0.0F) {
         â˜ƒ.translate(0.0, (double)(0.8F * â˜ƒ), 0.0);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.getXRot(), â˜ƒ.getXRot() + 90.0F)));
         â˜ƒ.translate(0.0, (double)(-1.0F * â˜ƒ), 0.0);
         if (â˜ƒ.isScared()) {
            float â˜ƒx = (float)(Math.cos((double)â˜ƒ.tickCount * 1.25) * Math.PI * 0.05F);
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
            if (â˜ƒ.isBaby()) {
               â˜ƒ.translate(0.0, 0.8F, 0.55F);
            }
         }
      }

      float â˜ƒ = â˜ƒ.getLieOnBackAmount(â˜ƒ);
      if (â˜ƒ > 0.0F) {
         float â˜ƒx = â˜ƒ.isBaby() ? 0.5F : 1.3F;
         â˜ƒ.translate(0.0, (double)(â˜ƒx * â˜ƒ), 0.0);
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.getXRot(), â˜ƒ.getXRot() + 180.0F)));
      }
   }

   private float getAngle(float var1, float var2, int var3, float var4, float var5) {
      return (float)â˜ƒ < â˜ƒ ? Mth.lerp(â˜ƒ, â˜ƒ, â˜ƒ) : â˜ƒ;
   }
}
