package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Blocks;

public class TntRenderer extends EntityRenderer<PrimedTnt> {
   public TntRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.shadowRadius = 0.5F;
   }

   public void render(PrimedTnt var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.5, 0.0);
      int â˜ƒ = â˜ƒ.getFuse();
      if ((float)â˜ƒ - â˜ƒ + 1.0F < 10.0F) {
         float â˜ƒx = 1.0F - ((float)â˜ƒ - â˜ƒ + 1.0F) / 10.0F;
         â˜ƒx = Mth.clamp(â˜ƒx, 0.0F, 1.0F);
         â˜ƒx *= â˜ƒx;
         â˜ƒx *= â˜ƒx;
         float â˜ƒxx = 1.0F + â˜ƒx * 0.3F;
         â˜ƒ.scale(â˜ƒxx, â˜ƒxx, â˜ƒxx);
      }

      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
      â˜ƒ.translate(-0.5, -0.5, 0.5);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(90.0F));
      TntMinecartRenderer.renderWhiteSolidBlock(Blocks.TNT.defaultBlockState(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ / 5 % 2 == 0);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(PrimedTnt var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
