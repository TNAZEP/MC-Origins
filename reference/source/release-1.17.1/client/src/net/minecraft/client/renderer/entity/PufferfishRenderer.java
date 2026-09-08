package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.PufferfishMidModel;
import net.minecraft.client.model.PufferfishSmallModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Pufferfish;

public class PufferfishRenderer extends MobRenderer<Pufferfish, EntityModel<Pufferfish>> {
   private static final ResourceLocation PUFFER_LOCATION = new ResourceLocation("textures/entity/fish/pufferfish.png");
   private int puffStateO = 3;
   private final EntityModel<Pufferfish> small;
   private final EntityModel<Pufferfish> mid;
   private final EntityModel<Pufferfish> big = this.getModel();

   public PufferfishRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new PufferfishBigModel<>(â˜ƒ.bakeLayer(ModelLayers.PUFFERFISH_BIG)), 0.2F);
      this.mid = new PufferfishMidModel<>(â˜ƒ.bakeLayer(ModelLayers.PUFFERFISH_MEDIUM));
      this.small = new PufferfishSmallModel<>(â˜ƒ.bakeLayer(ModelLayers.PUFFERFISH_SMALL));
   }

   public ResourceLocation getTextureLocation(Pufferfish var1) {
      return PUFFER_LOCATION;
   }

   public void render(Pufferfish var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      int â˜ƒ = â˜ƒ.getPuffState();
      if (â˜ƒ != this.puffStateO) {
         if (â˜ƒ == 0) {
            this.model = this.small;
         } else if (â˜ƒ == 1) {
            this.model = this.mid;
         } else {
            this.model = this.big;
         }
      }

      this.puffStateO = â˜ƒ;
      this.shadowRadius = 0.1F + 0.1F * (float)â˜ƒ;
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void setupRotations(Pufferfish var1, PoseStack var2, float var3, float var4, float var5) {
      â˜ƒ.translate(0.0, (double)(Mth.cos(â˜ƒ * 0.05F) * 0.08F), 0.0);
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
