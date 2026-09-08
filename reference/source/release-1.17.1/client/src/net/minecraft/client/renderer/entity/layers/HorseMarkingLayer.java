package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.EnumMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Markings;

public class HorseMarkingLayer extends RenderLayer<Horse, HorseModel<Horse>> {
   private static final Map<Markings, ResourceLocation> LOCATION_BY_MARKINGS = Util.make(Maps.newEnumMap(Markings.class), var0 -> {
      var0.put(Markings.NONE, null);
      var0.put(Markings.WHITE, new ResourceLocation("textures/entity/horse/horse_markings_white.png"));
      var0.put(Markings.WHITE_FIELD, new ResourceLocation("textures/entity/horse/horse_markings_whitefield.png"));
      var0.put(Markings.WHITE_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_whitedots.png"));
      var0.put(Markings.BLACK_DOTS, new ResourceLocation("textures/entity/horse/horse_markings_blackdots.png"));
   });

   public HorseMarkingLayer(RenderLayerParent<Horse, HorseModel<Horse>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Horse var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      ResourceLocation â˜ƒ = (ResourceLocation)LOCATION_BY_MARKINGS.get(â˜ƒ.getMarkings());
      if (â˜ƒ != null && !â˜ƒ.isInvisible()) {
         VertexConsumer â˜ƒx = â˜ƒ.getBuffer(RenderType.entityTranslucent(â˜ƒ));
         this.getParentModel().renderToBuffer(â˜ƒ, â˜ƒx, â˜ƒ, LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
