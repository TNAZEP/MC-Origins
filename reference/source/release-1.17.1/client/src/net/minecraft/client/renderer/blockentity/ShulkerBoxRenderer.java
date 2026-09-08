package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ShulkerBoxRenderer implements BlockEntityRenderer<ShulkerBoxBlockEntity> {
   private final ShulkerModel<?> model;

   public ShulkerBoxRenderer(BlockEntityRendererProvider.Context var1) {
      this.model = new ShulkerModel(â˜ƒ.bakeLayer(ModelLayers.SHULKER));
   }

   public void render(ShulkerBoxBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Direction â˜ƒ = Direction.UP;
      if (â˜ƒ.hasLevel()) {
         BlockState â˜ƒx = â˜ƒ.getLevel().getBlockState(â˜ƒ.getBlockPos());
         if (â˜ƒx.getBlock() instanceof ShulkerBoxBlock) {
            â˜ƒ = â˜ƒx.getValue(ShulkerBoxBlock.FACING);
         }
      }

      DyeColor â˜ƒx = â˜ƒ.getColor();
      Material â˜ƒ;
      if (â˜ƒx == null) {
         â˜ƒ = Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION;
      } else {
         â˜ƒ = (Material)Sheets.SHULKER_TEXTURE_LOCATION.get(â˜ƒx.getId());
      }

      â˜ƒ.pushPose();
      â˜ƒ.translate(0.5, 0.5, 0.5);
      float â˜ƒ = 0.9995F;
      â˜ƒ.scale(0.9995F, 0.9995F, 0.9995F);
      â˜ƒ.mulPose(â˜ƒ.getRotation());
      â˜ƒ.scale(1.0F, -1.0F, -1.0F);
      â˜ƒ.translate(0.0, -1.0, 0.0);
      ModelPart â˜ƒx = this.model.getLid();
      â˜ƒx.setPos(0.0F, 24.0F - â˜ƒ.getProgress(â˜ƒ) * 0.5F * 16.0F, 0.0F);
      â˜ƒx.yRot = 270.0F * â˜ƒ.getProgress(â˜ƒ) * (float) (Math.PI / 180.0);
      VertexConsumer â˜ƒxx = â˜ƒ.buffer(â˜ƒ, RenderType::entityCutoutNoCull);
      this.model.renderToBuffer(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒ, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
   }
}
