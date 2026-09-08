package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LecternRenderer implements BlockEntityRenderer<LecternBlockEntity> {
   private final BookModel bookModel;

   public LecternRenderer(BlockEntityRendererProvider.Context var1) {
      this.bookModel = new BookModel(â˜ƒ.bakeLayer(ModelLayers.BOOK));
   }

   public void render(LecternBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      BlockState â˜ƒ = â˜ƒ.getBlockState();
      if (â˜ƒ.getValue(LecternBlock.HAS_BOOK)) {
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.5, 1.0625, 0.5);
         float â˜ƒx = ((Direction)â˜ƒ.getValue(LecternBlock.FACING)).getClockWise().toYRot();
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-â˜ƒx));
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(67.5F));
         â˜ƒ.translate(0.0, -0.125, 0.0);
         this.bookModel.setupAnim(0.0F, 0.1F, 0.9F, 1.2F);
         VertexConsumer â˜ƒxx = EnchantTableRenderer.BOOK_LOCATION.buffer(â˜ƒ, RenderType::entitySolid);
         this.bookModel.render(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
         â˜ƒ.popPose();
      }
   }
}
