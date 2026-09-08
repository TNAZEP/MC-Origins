package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;

public class PistonHeadRenderer implements BlockEntityRenderer<PistonMovingBlockEntity> {
   private final BlockRenderDispatcher blockRenderer;

   public PistonHeadRenderer(BlockEntityRendererProvider.Context var1) {
      this.blockRenderer = â˜ƒ.getBlockRenderDispatcher();
   }

   public void render(PistonMovingBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Level â˜ƒ = â˜ƒ.getLevel();
      if (â˜ƒ != null) {
         BlockPos â˜ƒx = â˜ƒ.getBlockPos().relative(â˜ƒ.getMovementDirection().getOpposite());
         BlockState â˜ƒxx = â˜ƒ.getMovedState();
         if (!â˜ƒxx.isAir()) {
            ModelBlockRenderer.enableCaching();
            â˜ƒ.pushPose();
            â˜ƒ.translate((double)â˜ƒ.getXOff(â˜ƒ), (double)â˜ƒ.getYOff(â˜ƒ), (double)â˜ƒ.getZOff(â˜ƒ));
            if (â˜ƒxx.is(Blocks.PISTON_HEAD) && â˜ƒ.getProgress(â˜ƒ) <= 4.0F) {
               â˜ƒxx = â˜ƒxx.setValue(PistonHeadBlock.SHORT, Boolean.valueOf(â˜ƒ.getProgress(â˜ƒ) <= 0.5F));
               this.renderBlock(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ);
            } else if (â˜ƒ.isSourcePiston() && !â˜ƒ.isExtending()) {
               PistonType â˜ƒxxx = â˜ƒxx.is(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT;
               BlockState â˜ƒxxxx = Blocks.PISTON_HEAD
                  .defaultBlockState()
                  .setValue(PistonHeadBlock.TYPE, â˜ƒxxx)
                  .setValue(PistonHeadBlock.FACING, (Direction)â˜ƒxx.getValue(PistonBaseBlock.FACING));
               â˜ƒxxxx = â˜ƒxxxx.setValue(PistonHeadBlock.SHORT, Boolean.valueOf(â˜ƒ.getProgress(â˜ƒ) >= 0.5F));
               this.renderBlock(â˜ƒx, â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ);
               BlockPos â˜ƒxxxxx = â˜ƒx.relative(â˜ƒ.getMovementDirection());
               â˜ƒ.popPose();
               â˜ƒ.pushPose();
               â˜ƒxx = â˜ƒxx.setValue(PistonBaseBlock.EXTENDED, Boolean.valueOf(true));
               this.renderBlock(â˜ƒxxxxx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, true, â˜ƒ);
            } else {
               this.renderBlock(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ, false, â˜ƒ);
            }

            â˜ƒ.popPose();
            ModelBlockRenderer.clearCache();
         }
      }
   }

   private void renderBlock(BlockPos var1, BlockState var2, PoseStack var3, MultiBufferSource var4, Level var5, boolean var6, int var7) {
      RenderType â˜ƒ = ItemBlockRenderTypes.getMovingBlockRenderType(â˜ƒ);
      VertexConsumer â˜ƒx = â˜ƒ.getBuffer(â˜ƒ);
      this.blockRenderer
         .getModelRenderer()
         .tesselateBlock(â˜ƒ, this.blockRenderer.getBlockModel(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, new Random(), â˜ƒ.getSeed(â˜ƒ), â˜ƒ);
   }

   @Override
   public int getViewDistance() {
      return 68;
   }
}
