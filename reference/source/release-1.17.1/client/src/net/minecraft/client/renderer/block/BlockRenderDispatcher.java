package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class BlockRenderDispatcher implements ResourceManagerReloadListener {
   private final BlockModelShaper blockModelShaper;
   private final ModelBlockRenderer modelRenderer;
   private final BlockEntityWithoutLevelRenderer blockEntityRenderer;
   private final LiquidBlockRenderer liquidBlockRenderer;
   private final Random random = new Random();
   private final BlockColors blockColors;

   public BlockRenderDispatcher(BlockModelShaper var1, BlockEntityWithoutLevelRenderer var2, BlockColors var3) {
      this.blockModelShaper = â˜ƒ;
      this.blockEntityRenderer = â˜ƒ;
      this.blockColors = â˜ƒ;
      this.modelRenderer = new ModelBlockRenderer(this.blockColors);
      this.liquidBlockRenderer = new LiquidBlockRenderer();
   }

   public BlockModelShaper getBlockModelShaper() {
      return this.blockModelShaper;
   }

   public void renderBreakingTexture(BlockState var1, BlockPos var2, BlockAndTintGetter var3, PoseStack var4, VertexConsumer var5) {
      if (â˜ƒ.getRenderShape() == RenderShape.MODEL) {
         BakedModel â˜ƒ = this.blockModelShaper.getBlockModel(â˜ƒ);
         long â˜ƒx = â˜ƒ.getSeed(â˜ƒ);
         this.modelRenderer.tesselateBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, this.random, â˜ƒx, OverlayTexture.NO_OVERLAY);
      }
   }

   public boolean renderBatched(BlockState var1, BlockPos var2, BlockAndTintGetter var3, PoseStack var4, VertexConsumer var5, boolean var6, Random var7) {
      try {
         RenderShape â˜ƒ = â˜ƒ.getRenderShape();
         return â˜ƒ != RenderShape.MODEL
            ? false
            : this.modelRenderer.tesselateBlock(â˜ƒ, this.getBlockModel(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getSeed(â˜ƒ), OverlayTexture.NO_OVERLAY);
      } catch (Throwable var11) {
         CrashReport â˜ƒx = CrashReport.forThrowable(var11, "Tesselating block in world");
         CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Block being tesselated");
         CrashReportCategory.populateBlockDetails(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒ);
         throw new ReportedException(â˜ƒx);
      }
   }

   public boolean renderLiquid(BlockPos var1, BlockAndTintGetter var2, VertexConsumer var3, FluidState var4) {
      try {
         return this.liquidBlockRenderer.tesselate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } catch (Throwable var8) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var8, "Tesselating liquid in world");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Block being tesselated");
         CrashReportCategory.populateBlockDetails(â˜ƒx, â˜ƒ, â˜ƒ, null);
         throw new ReportedException(â˜ƒ);
      }
   }

   public ModelBlockRenderer getModelRenderer() {
      return this.modelRenderer;
   }

   public BakedModel getBlockModel(BlockState var1) {
      return this.blockModelShaper.getBlockModel(â˜ƒ);
   }

   public void renderSingleBlock(BlockState var1, PoseStack var2, MultiBufferSource var3, int var4, int var5) {
      RenderShape â˜ƒ = â˜ƒ.getRenderShape();
      if (â˜ƒ != RenderShape.INVISIBLE) {
         switch(â˜ƒ) {
            case MODEL:
               BakedModel â˜ƒx = this.getBlockModel(â˜ƒ);
               int â˜ƒxx = this.blockColors.getColor(â˜ƒ, null, null, 0);
               float â˜ƒxxx = (float)(â˜ƒxx >> 16 & 0xFF) / 255.0F;
               float â˜ƒxxxx = (float)(â˜ƒxx >> 8 & 0xFF) / 255.0F;
               float â˜ƒxxxxx = (float)(â˜ƒxx & 0xFF) / 255.0F;
               this.modelRenderer
                  .renderModel(â˜ƒ.last(), â˜ƒ.getBuffer(ItemBlockRenderTypes.getRenderType(â˜ƒ, false)), â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
               break;
            case ENTITYBLOCK_ANIMATED:
               this.blockEntityRenderer.renderByItem(new ItemStack(â˜ƒ.getBlock()), ItemTransforms.TransformType.NONE, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      this.liquidBlockRenderer.setupSprites();
   }
}
