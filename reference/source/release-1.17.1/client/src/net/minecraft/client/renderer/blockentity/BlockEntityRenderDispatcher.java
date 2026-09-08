package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.HitResult;

public class BlockEntityRenderDispatcher implements ResourceManagerReloadListener {
   private Map<BlockEntityType<?>, BlockEntityRenderer<?>> renderers = ImmutableMap.of();
   private final Font font;
   private final EntityModelSet entityModelSet;
   public Level level;
   public Camera camera;
   public HitResult cameraHitResult;
   private final Supplier<BlockRenderDispatcher> blockRenderDispatcher;

   public BlockEntityRenderDispatcher(Font var1, EntityModelSet var2, Supplier<BlockRenderDispatcher> var3) {
      this.font = â˜ƒ;
      this.entityModelSet = â˜ƒ;
      this.blockRenderDispatcher = â˜ƒ;
   }

   @Nullable
   public <E extends BlockEntity> BlockEntityRenderer<E> getRenderer(E var1) {
      return (BlockEntityRenderer<E>)this.renderers.get(â˜ƒ.getType());
   }

   public void prepare(Level var1, Camera var2, HitResult var3) {
      if (this.level != â˜ƒ) {
         this.setLevel(â˜ƒ);
      }

      this.camera = â˜ƒ;
      this.cameraHitResult = â˜ƒ;
   }

   public <E extends BlockEntity> void render(E var1, float var2, PoseStack var3, MultiBufferSource var4) {
      BlockEntityRenderer<E> â˜ƒ = this.getRenderer(â˜ƒ);
      if (â˜ƒ != null) {
         if (â˜ƒ.hasLevel() && â˜ƒ.getType().isValid(â˜ƒ.getBlockState())) {
            if (â˜ƒ.shouldRender(â˜ƒ, this.camera.getPosition())) {
               tryRender(â˜ƒ, () -> setupAndRender(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
            }
         }
      }
   }

   private static <T extends BlockEntity> void setupAndRender(BlockEntityRenderer<T> var0, T var1, float var2, PoseStack var3, MultiBufferSource var4) {
      Level â˜ƒx = â˜ƒ.getLevel();
      int â˜ƒ;
      if (â˜ƒx != null) {
         â˜ƒ = LevelRenderer.getLightColor(â˜ƒx, â˜ƒ.getBlockPos());
      } else {
         â˜ƒ = 15728880;
      }

      â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY);
   }

   public <E extends BlockEntity> boolean renderItem(E var1, PoseStack var2, MultiBufferSource var3, int var4, int var5) {
      BlockEntityRenderer<E> â˜ƒ = this.getRenderer(â˜ƒ);
      if (â˜ƒ == null) {
         return true;
      } else {
         tryRender(â˜ƒ, () -> â˜ƒ.render(â˜ƒ, 0.0F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
         return false;
      }
   }

   private static void tryRender(BlockEntity var0, Runnable var1) {
      try {
         â˜ƒ.run();
      } catch (Throwable var5) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var5, "Rendering Block Entity");
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Block Entity Details");
         â˜ƒ.fillCrashReportCategory(â˜ƒx);
         throw new ReportedException(â˜ƒ);
      }
   }

   public void setLevel(@Nullable Level var1) {
      this.level = â˜ƒ;
      if (â˜ƒ == null) {
         this.camera = null;
      }
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      BlockEntityRendererProvider.Context â˜ƒ = new BlockEntityRendererProvider.Context(
         this, (BlockRenderDispatcher)this.blockRenderDispatcher.get(), this.entityModelSet, this.font
      );
      this.renderers = BlockEntityRenderers.createEntityRenderers(â˜ƒ);
   }
}
