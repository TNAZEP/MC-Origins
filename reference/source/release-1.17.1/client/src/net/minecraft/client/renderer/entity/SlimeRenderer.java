package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;

public class SlimeRenderer extends MobRenderer<Slime, SlimeModel<Slime>> {
   private static final ResourceLocation SLIME_LOCATION = new ResourceLocation("textures/entity/slime/slime.png");

   public SlimeRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new SlimeModel<>(â˜ƒ.bakeLayer(ModelLayers.SLIME)), 0.25F);
      this.addLayer(new SlimeOuterLayer<>(this, â˜ƒ.getModelSet()));
   }

   public void render(Slime var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      this.shadowRadius = 0.25F * (float)â˜ƒ.getSize();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void scale(Slime var1, PoseStack var2, float var3) {
      float â˜ƒ = 0.999F;
      â˜ƒ.scale(0.999F, 0.999F, 0.999F);
      â˜ƒ.translate(0.0, 0.001F, 0.0);
      float â˜ƒx = (float)â˜ƒ.getSize();
      float â˜ƒxx = Mth.lerp(â˜ƒ, â˜ƒ.oSquish, â˜ƒ.squish) / (â˜ƒx * 0.5F + 1.0F);
      float â˜ƒxxx = 1.0F / (â˜ƒxx + 1.0F);
      â˜ƒ.scale(â˜ƒxxx * â˜ƒx, 1.0F / â˜ƒxxx * â˜ƒx, â˜ƒxxx * â˜ƒx);
   }

   public ResourceLocation getTextureLocation(Slime var1) {
      return SLIME_LOCATION;
   }
}
