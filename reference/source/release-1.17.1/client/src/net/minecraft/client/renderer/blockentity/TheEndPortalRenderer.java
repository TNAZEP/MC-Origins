package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;

public class TheEndPortalRenderer<T extends TheEndPortalBlockEntity> implements BlockEntityRenderer<T> {
   public static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");
   public static final ResourceLocation END_PORTAL_LOCATION = new ResourceLocation("textures/entity/end_portal.png");

   public TheEndPortalRenderer(BlockEntityRendererProvider.Context var1) {
   }

   public void render(T var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Matrix4f â˜ƒ = â˜ƒ.last().pose();
      this.renderCube(â˜ƒ, â˜ƒ, â˜ƒ.getBuffer(this.renderType()));
   }

   private void renderCube(T var1, Matrix4f var2, VertexConsumer var3) {
      float â˜ƒ = this.getOffsetDown();
      float â˜ƒx = this.getOffsetUp();
      this.renderFace(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
      this.renderFace(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
      this.renderFace(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
      this.renderFace(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
      this.renderFace(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 1.0F, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
      this.renderFace(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 1.0F, â˜ƒx, â˜ƒx, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
   }

   private void renderFace(
      T var1,
      Matrix4f var2,
      VertexConsumer var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      Direction var12
   ) {
      if (â˜ƒ.shouldRenderFace(â˜ƒ)) {
         â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
         â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
         â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
         â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      }
   }

   protected float getOffsetUp() {
      return 0.75F;
   }

   protected float getOffsetDown() {
      return 0.375F;
   }

   protected RenderType renderType() {
      return RenderType.endPortal();
   }
}
