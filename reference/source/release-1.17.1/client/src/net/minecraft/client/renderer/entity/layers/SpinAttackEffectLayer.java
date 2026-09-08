package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SpinAttackEffectLayer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/trident_riptide.png");
   public static final String BOX = "box";
   private final ModelPart box;

   public SpinAttackEffectLayer(RenderLayerParent<T, PlayerModel<T>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      ModelPart â˜ƒ = â˜ƒ.bakeLayer(ModelLayers.PLAYER_SPIN_ATTACK);
      this.box = â˜ƒ.getChild("box");
   }

   public static LayerDefinition createLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("box", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (â˜ƒ.isAutoSpinAttack()) {
         VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));

         for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
            â˜ƒ.pushPose();
            float â˜ƒxx = â˜ƒ * (float)(-(45 + â˜ƒx * 5));
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxx));
            float â˜ƒxxx = 0.75F * (float)â˜ƒx;
            â˜ƒ.scale(â˜ƒxxx, â˜ƒxxx, â˜ƒxxx);
            â˜ƒ.translate(0.0, (double)(-0.2F + 0.6F * (float)â˜ƒx), 0.0);
            this.box.render(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY);
            â˜ƒ.popPose();
         }
      }
   }
}
