package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;

public class EnchantTableRenderer implements BlockEntityRenderer<EnchantmentTableBlockEntity> {
   public static final Material BOOK_LOCATION = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/enchanting_table_book"));
   private final BookModel bookModel;

   public EnchantTableRenderer(BlockEntityRendererProvider.Context var1) {
      this.bookModel = new BookModel(â˜ƒ.bakeLayer(ModelLayers.BOOK));
   }

   public void render(EnchantmentTableBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.5, 0.75, 0.5);
      float â˜ƒ = (float)â˜ƒ.time + â˜ƒ;
      â˜ƒ.translate(0.0, (double)(0.1F + Mth.sin(â˜ƒ * 0.1F) * 0.01F), 0.0);
      float â˜ƒx = â˜ƒ.rot - â˜ƒ.oRot;

      while(â˜ƒx >= (float) Math.PI) {
         â˜ƒx -= (float) (Math.PI * 2);
      }

      while(â˜ƒx < (float) -Math.PI) {
         â˜ƒx += (float) (Math.PI * 2);
      }

      float â˜ƒxx = â˜ƒ.oRot + â˜ƒx * â˜ƒ;
      â˜ƒ.mulPose(Vector3f.YP.rotation(-â˜ƒxx));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(80.0F));
      float â˜ƒxxx = Mth.lerp(â˜ƒ, â˜ƒ.oFlip, â˜ƒ.flip);
      float â˜ƒxxxx = Mth.frac(â˜ƒxxx + 0.25F) * 1.6F - 0.3F;
      float â˜ƒxxxxx = Mth.frac(â˜ƒxxx + 0.75F) * 1.6F - 0.3F;
      float â˜ƒxxxxxx = Mth.lerp(â˜ƒ, â˜ƒ.oOpen, â˜ƒ.open);
      this.bookModel.setupAnim(â˜ƒ, Mth.clamp(â˜ƒxxxx, 0.0F, 1.0F), Mth.clamp(â˜ƒxxxxx, 0.0F, 1.0F), â˜ƒxxxxxx);
      VertexConsumer â˜ƒxxxxxxx = BOOK_LOCATION.buffer(â˜ƒ, RenderType::entitySolid);
      this.bookModel.render(â˜ƒ, â˜ƒxxxxxxx, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
   }
}
