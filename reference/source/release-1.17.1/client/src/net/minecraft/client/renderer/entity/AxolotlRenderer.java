package net.minecraft.client.renderer.entity;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.Util;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.axolotl.Axolotl;

public class AxolotlRenderer extends MobRenderer<Axolotl, AxolotlModel<Axolotl>> {
   private static final Map<Axolotl.Variant, ResourceLocation> TEXTURE_BY_TYPE = Util.make(Maps.newHashMap(), var0 -> {
      for(Axolotl.Variant â˜ƒ : Axolotl.Variant.BY_ID) {
         var0.put(â˜ƒ, new ResourceLocation(String.format("textures/entity/axolotl/axolotl_%s.png", â˜ƒ.getName())));
      }
   });

   public AxolotlRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new AxolotlModel<>(â˜ƒ.bakeLayer(ModelLayers.AXOLOTL)), 0.5F);
   }

   public ResourceLocation getTextureLocation(Axolotl var1) {
      return (ResourceLocation)TEXTURE_BY_TYPE.get(â˜ƒ.getVariant());
   }
}
