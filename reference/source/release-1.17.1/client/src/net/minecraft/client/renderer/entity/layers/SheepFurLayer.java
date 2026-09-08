package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class SheepFurLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
   private static final ResourceLocation SHEEP_FUR_LOCATION = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final SheepFurModel<Sheep> model;

   public SheepFurLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.model = new SheepFurModel<>(â˜ƒ.bakeLayer(ModelLayers.SHEEP_FUR));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Sheep var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (!â˜ƒ.isSheared()) {
         if (â˜ƒ.isInvisible()) {
            Minecraft â˜ƒ = Minecraft.getInstance();
            boolean â˜ƒx = â˜ƒ.shouldEntityAppearGlowing(â˜ƒ);
            if (â˜ƒx) {
               this.getParentModel().copyPropertiesTo(this.model);
               this.model.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               this.model.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(RenderType.outline(SHEEP_FUR_LOCATION));
               this.model.renderToBuffer(â˜ƒ, â˜ƒxx, â˜ƒ, LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
            }
         } else {
            float â˜ƒ;
            float â˜ƒx;
            float â˜ƒxx;
            if (â˜ƒ.hasCustomName() && "jeb_".equals(â˜ƒ.getName().getContents())) {
               int â˜ƒxxx = 25;
               int â˜ƒxxxx = â˜ƒ.tickCount / 25 + â˜ƒ.getId();
               int â˜ƒxxxxx = DyeColor.values().length;
               int â˜ƒxxxxxx = â˜ƒxxxx % â˜ƒxxxxx;
               int â˜ƒxxxxxxx = (â˜ƒxxxx + 1) % â˜ƒxxxxx;
               float â˜ƒxxxxxxxx = ((float)(â˜ƒ.tickCount % 25) + â˜ƒ) / 25.0F;
               float[] â˜ƒxxxxxxxxx = Sheep.getColorArray(DyeColor.byId(â˜ƒxxxxxx));
               float[] â˜ƒxxxxxxxxxx = Sheep.getColorArray(DyeColor.byId(â˜ƒxxxxxxx));
               â˜ƒ = â˜ƒxxxxxxxxx[0] * (1.0F - â˜ƒxxxxxxxx) + â˜ƒxxxxxxxxxx[0] * â˜ƒxxxxxxxx;
               â˜ƒx = â˜ƒxxxxxxxxx[1] * (1.0F - â˜ƒxxxxxxxx) + â˜ƒxxxxxxxxxx[1] * â˜ƒxxxxxxxx;
               â˜ƒxx = â˜ƒxxxxxxxxx[2] * (1.0F - â˜ƒxxxxxxxx) + â˜ƒxxxxxxxxxx[2] * â˜ƒxxxxxxxx;
            } else {
               float[] â˜ƒ = Sheep.getColorArray(â˜ƒ.getColor());
               â˜ƒ = â˜ƒ[0];
               â˜ƒx = â˜ƒ[1];
               â˜ƒxx = â˜ƒ[2];
            }

            coloredCutoutModelCopyLayerRender(
               this.getParentModel(), this.model, SHEEP_FUR_LOCATION, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx
            );
         }
      }
   }
}
