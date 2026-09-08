package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.DyeableHorseArmorItem;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;

public class HorseArmorLayer extends RenderLayer<Horse, HorseModel<Horse>> {
   private final HorseModel<Horse> model;

   public HorseArmorLayer(RenderLayerParent<Horse, HorseModel<Horse>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.model = new HorseModel<>(â˜ƒ.bakeLayer(ModelLayers.HORSE_ARMOR));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Horse var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ItemStack â˜ƒ = â˜ƒ.getArmor();
      if (â˜ƒ.getItem() instanceof HorseArmorItem) {
         HorseArmorItem â˜ƒxxxx = (HorseArmorItem)â˜ƒ.getItem();
         this.getParentModel().copyPropertiesTo(this.model);
         this.model.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.model.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒx;
         float â˜ƒxx;
         float â˜ƒxxx;
         if (â˜ƒxxxx instanceof DyeableHorseArmorItem) {
            int â˜ƒxxxxx = ((DyeableHorseArmorItem)â˜ƒxxxx).getColor(â˜ƒ);
            â˜ƒx = (float)(â˜ƒxxxxx >> 16 & 0xFF) / 255.0F;
            â˜ƒxx = (float)(â˜ƒxxxxx >> 8 & 0xFF) / 255.0F;
            â˜ƒxxx = (float)(â˜ƒxxxxx & 0xFF) / 255.0F;
         } else {
            â˜ƒx = 1.0F;
            â˜ƒxx = 1.0F;
            â˜ƒxxx = 1.0F;
         }

         VertexConsumer â˜ƒx = â˜ƒ.getBuffer(RenderType.entityCutoutNoCull(â˜ƒxxxx.getTexture()));
         this.model.renderToBuffer(â˜ƒ, â˜ƒx, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒx, â˜ƒxx, â˜ƒxxx, 1.0F);
      }
   }
}
