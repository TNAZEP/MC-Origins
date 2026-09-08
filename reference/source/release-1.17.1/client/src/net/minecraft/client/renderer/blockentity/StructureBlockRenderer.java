package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;

public class StructureBlockRenderer implements BlockEntityRenderer<StructureBlockEntity> {
   public StructureBlockRenderer(BlockEntityRendererProvider.Context var1) {
   }

   public void render(StructureBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      if (Minecraft.getInstance().player.canUseGameMasterBlocks() || Minecraft.getInstance().player.isSpectator()) {
         BlockPos â˜ƒ = â˜ƒ.getStructurePos();
         Vec3i â˜ƒx = â˜ƒ.getStructureSize();
         if (â˜ƒx.getX() >= 1 && â˜ƒx.getY() >= 1 && â˜ƒx.getZ() >= 1) {
            if (â˜ƒ.getMode() == StructureMode.SAVE || â˜ƒ.getMode() == StructureMode.LOAD) {
               double â˜ƒxxxx = (double)â˜ƒ.getX();
               double â˜ƒxxxxx = (double)â˜ƒ.getZ();
               double â˜ƒxxxxxx = (double)â˜ƒ.getY();
               double â˜ƒxxxxxxx = â˜ƒxxxxxx + (double)â˜ƒx.getY();
               double â˜ƒxx;
               double â˜ƒxxx;
               switch(â˜ƒ.getMirror()) {
                  case LEFT_RIGHT:
                     â˜ƒxx = (double)â˜ƒx.getX();
                     â˜ƒxxx = (double)(-â˜ƒx.getZ());
                     break;
                  case FRONT_BACK:
                     â˜ƒxx = (double)(-â˜ƒx.getX());
                     â˜ƒxxx = (double)â˜ƒx.getZ();
                     break;
                  default:
                     â˜ƒxx = (double)â˜ƒx.getX();
                     â˜ƒxxx = (double)â˜ƒx.getZ();
               }

               double â˜ƒxx;
               double â˜ƒxxx;
               double â˜ƒxxxx;
               double â˜ƒxxxxx;
               switch(â˜ƒ.getRotation()) {
                  case CLOCKWISE_90:
                     â˜ƒxx = â˜ƒxxx < 0.0 ? â˜ƒxxxx : â˜ƒxxxx + 1.0;
                     â˜ƒxxx = â˜ƒxx < 0.0 ? â˜ƒxxxxx + 1.0 : â˜ƒxxxxx;
                     â˜ƒxxxx = â˜ƒxx - â˜ƒxxx;
                     â˜ƒxxxxx = â˜ƒxxx + â˜ƒxx;
                     break;
                  case CLOCKWISE_180:
                     â˜ƒxx = â˜ƒxx < 0.0 ? â˜ƒxxxx : â˜ƒxxxx + 1.0;
                     â˜ƒxxx = â˜ƒxxx < 0.0 ? â˜ƒxxxxx : â˜ƒxxxxx + 1.0;
                     â˜ƒxxxx = â˜ƒxx - â˜ƒxx;
                     â˜ƒxxxxx = â˜ƒxxx - â˜ƒxxx;
                     break;
                  case COUNTERCLOCKWISE_90:
                     â˜ƒxx = â˜ƒxxx < 0.0 ? â˜ƒxxxx + 1.0 : â˜ƒxxxx;
                     â˜ƒxxx = â˜ƒxx < 0.0 ? â˜ƒxxxxx : â˜ƒxxxxx + 1.0;
                     â˜ƒxxxx = â˜ƒxx + â˜ƒxxx;
                     â˜ƒxxxxx = â˜ƒxxx - â˜ƒxx;
                     break;
                  default:
                     â˜ƒxx = â˜ƒxx < 0.0 ? â˜ƒxxxx + 1.0 : â˜ƒxxxx;
                     â˜ƒxxx = â˜ƒxxx < 0.0 ? â˜ƒxxxxx + 1.0 : â˜ƒxxxxx;
                     â˜ƒxxxx = â˜ƒxx + â˜ƒxx;
                     â˜ƒxxxxx = â˜ƒxxx + â˜ƒxxx;
               }

               float â˜ƒxx = 1.0F;
               float â˜ƒxxx = 0.9F;
               float â˜ƒxxxx = 0.5F;
               VertexConsumer â˜ƒxxxxx = â˜ƒ.getBuffer(RenderType.lines());
               if (â˜ƒ.getMode() == StructureMode.SAVE || â˜ƒ.getShowBoundingBox()) {
                  LevelRenderer.renderLineBox(â˜ƒ, â˜ƒxxxxx, â˜ƒxx, â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxxx, â˜ƒxxxxx, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
               }

               if (â˜ƒ.getMode() == StructureMode.SAVE && â˜ƒ.getShowAir()) {
                  this.renderInvisibleBlocks(â˜ƒ, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
               }
            }
         }
      }
   }

   private void renderInvisibleBlocks(StructureBlockEntity var1, VertexConsumer var2, BlockPos var3, PoseStack var4) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getBlockPos();
      BlockPos â˜ƒxx = â˜ƒx.offset(â˜ƒ);

      for(BlockPos â˜ƒxxx : BlockPos.betweenClosed(â˜ƒxx, â˜ƒxx.offset(â˜ƒ.getStructureSize()).offset(-1, -1, -1))) {
         BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
         boolean â˜ƒxxxxx = â˜ƒxxxx.isAir();
         boolean â˜ƒxxxxxx = â˜ƒxxxx.is(Blocks.STRUCTURE_VOID);
         boolean â˜ƒxxxxxxx = â˜ƒxxxx.is(Blocks.BARRIER);
         boolean â˜ƒxxxxxxxx = â˜ƒxxxx.is(Blocks.LIGHT);
         boolean â˜ƒxxxxxxxxx = â˜ƒxxxxxx || â˜ƒxxxxxxx || â˜ƒxxxxxxxx;
         if (â˜ƒxxxxx || â˜ƒxxxxxxxxx) {
            float â˜ƒxxxxxxxxxx = â˜ƒxxxxx ? 0.05F : 0.0F;
            double â˜ƒxxxxxxxxxxx = (double)((float)(â˜ƒxxx.getX() - â˜ƒx.getX()) + 0.45F - â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxx = (double)((float)(â˜ƒxxx.getY() - â˜ƒx.getY()) + 0.45F - â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxx = (double)((float)(â˜ƒxxx.getZ() - â˜ƒx.getZ()) + 0.45F - â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxxx = (double)((float)(â˜ƒxxx.getX() - â˜ƒx.getX()) + 0.55F + â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxxxx = (double)((float)(â˜ƒxxx.getY() - â˜ƒx.getY()) + 0.55F + â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxxxxxx = (double)((float)(â˜ƒxxx.getZ() - â˜ƒx.getZ()) + 0.55F + â˜ƒxxxxxxxxxx);
            if (â˜ƒxxxxx) {
               LevelRenderer.renderLineBox(
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxx,
                  0.5F,
                  0.5F,
                  1.0F,
                  1.0F,
                  0.5F,
                  0.5F,
                  1.0F
               );
            } else if (â˜ƒxxxxxx) {
               LevelRenderer.renderLineBox(
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxx,
                  1.0F,
                  0.75F,
                  0.75F,
                  1.0F,
                  1.0F,
                  0.75F,
                  0.75F
               );
            } else if (â˜ƒxxxxxxx) {
               LevelRenderer.renderLineBox(
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxx,
                  1.0F,
                  0.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  0.0F,
                  0.0F
               );
            } else if (â˜ƒxxxxxxxx) {
               LevelRenderer.renderLineBox(
                  â˜ƒ,
                  â˜ƒ,
                  â˜ƒxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxx,
                  1.0F,
                  1.0F,
                  0.0F,
                  1.0F,
                  1.0F,
                  1.0F,
                  0.0F
               );
            }
         }
      }
   }

   public boolean shouldRenderOffScreen(StructureBlockEntity var1) {
      return true;
   }

   @Override
   public int getViewDistance() {
      return 96;
   }
}
