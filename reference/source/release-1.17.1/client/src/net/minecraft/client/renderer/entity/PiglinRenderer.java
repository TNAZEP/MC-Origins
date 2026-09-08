package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;

public class PiglinRenderer extends HumanoidMobRenderer<Mob, PiglinModel<Mob>> {
   private static final Map<EntityType<?>, ResourceLocation> TEXTURES = ImmutableMap.of(
      EntityType.PIGLIN,
      new ResourceLocation("textures/entity/piglin/piglin.png"),
      EntityType.ZOMBIFIED_PIGLIN,
      new ResourceLocation("textures/entity/piglin/zombified_piglin.png"),
      EntityType.PIGLIN_BRUTE,
      new ResourceLocation("textures/entity/piglin/piglin_brute.png")
   );
   private static final float PIGLIN_CUSTOM_HEAD_SCALE = 1.0019531F;

   public PiglinRenderer(EntityRendererProvider.Context var1, ModelLayerLocation var2, ModelLayerLocation var3, ModelLayerLocation var4, boolean var5) {
      super(â˜ƒ, createModel(â˜ƒ.getModelSet(), â˜ƒ, â˜ƒ), 0.5F, 1.0019531F, 1.0F, 1.0019531F);
      this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel(â˜ƒ.bakeLayer(â˜ƒ)), new HumanoidModel(â˜ƒ.bakeLayer(â˜ƒ))));
   }

   private static PiglinModel<Mob> createModel(EntityModelSet var0, ModelLayerLocation var1, boolean var2) {
      PiglinModel<Mob> â˜ƒ = new PiglinModel<>(â˜ƒ.bakeLayer(â˜ƒ));
      if (â˜ƒ) {
         â˜ƒ.rightEar.visible = false;
      }

      return â˜ƒ;
   }

   @Override
   public ResourceLocation getTextureLocation(Mob var1) {
      ResourceLocation â˜ƒ = (ResourceLocation)TEXTURES.get(â˜ƒ.getType());
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("I don't know what texture to use for " + â˜ƒ.getType());
      } else {
         return â˜ƒ;
      }
   }

   protected boolean isShaking(Mob var1) {
      return super.isShaking(â˜ƒ) || â˜ƒ instanceof AbstractPiglin && ((AbstractPiglin)â˜ƒ).isConverting();
   }
}
