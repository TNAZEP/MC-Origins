package net.minecraft.client.renderer.entity;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;

public class ItemRenderer implements ResourceManagerReloadListener {
   public static final ResourceLocation ENCHANT_GLINT_LOCATION = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   private static final Set<Item> IGNORED = Sets.<Item>newHashSet(Items.AIR);
   private static final int GUI_SLOT_CENTER_X = 8;
   private static final int GUI_SLOT_CENTER_Y = 8;
   public static final int ITEM_COUNT_BLIT_OFFSET = 200;
   public static final float COMPASS_FOIL_UI_SCALE = 0.5F;
   public static final float COMPASS_FOIL_FIRST_PERSON_SCALE = 0.75F;
   public float blitOffset;
   private final ItemModelShaper itemModelShaper;
   private final TextureManager textureManager;
   private final ItemColors itemColors;
   private final BlockEntityWithoutLevelRenderer blockEntityRenderer;

   public ItemRenderer(TextureManager var1, ModelManager var2, ItemColors var3, BlockEntityWithoutLevelRenderer var4) {
      this.textureManager = â˜ƒ;
      this.itemModelShaper = new ItemModelShaper(â˜ƒ);
      this.blockEntityRenderer = â˜ƒ;

      for(Item â˜ƒ : Registry.ITEM) {
         if (!IGNORED.contains(â˜ƒ)) {
            this.itemModelShaper.register(â˜ƒ, new ModelResourceLocation(Registry.ITEM.getKey(â˜ƒ), "inventory"));
         }
      }

      this.itemColors = â˜ƒ;
   }

   public ItemModelShaper getItemModelShaper() {
      return this.itemModelShaper;
   }

   private void renderModelLists(BakedModel var1, ItemStack var2, int var3, int var4, PoseStack var5, VertexConsumer var6) {
      Random â˜ƒ = new Random();
      long â˜ƒx = 42L;

      for(Direction â˜ƒxx : Direction.values()) {
         â˜ƒ.setSeed(42L);
         this.renderQuadList(â˜ƒ, â˜ƒ, â˜ƒ.getQuads(null, â˜ƒxx, â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
      }

      â˜ƒ.setSeed(42L);
      this.renderQuadList(â˜ƒ, â˜ƒ, â˜ƒ.getQuads(null, null, â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void render(
      ItemStack var1, ItemTransforms.TransformType var2, boolean var3, PoseStack var4, MultiBufferSource var5, int var6, int var7, BakedModel var8
   ) {
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.pushPose();
         boolean â˜ƒ = â˜ƒ == ItemTransforms.TransformType.GUI || â˜ƒ == ItemTransforms.TransformType.GROUND || â˜ƒ == ItemTransforms.TransformType.FIXED;
         if (â˜ƒ) {
            if (â˜ƒ.is(Items.TRIDENT)) {
               â˜ƒ = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
            } else if (â˜ƒ.is(Items.SPYGLASS)) {
               â˜ƒ = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass#inventory"));
            }
         }

         â˜ƒ.getTransforms().getTransform(â˜ƒ).apply(â˜ƒ, â˜ƒ);
         â˜ƒ.translate(-0.5, -0.5, -0.5);
         if (!â˜ƒ.isCustomRenderer() && (!â˜ƒ.is(Items.TRIDENT) || â˜ƒ)) {
            boolean â˜ƒ;
            if (â˜ƒ != ItemTransforms.TransformType.GUI && !â˜ƒ.firstPerson() && â˜ƒ.getItem() instanceof BlockItem) {
               Block â˜ƒx = ((BlockItem)â˜ƒ.getItem()).getBlock();
               â˜ƒ = !(â˜ƒx instanceof HalfTransparentBlock) && !(â˜ƒx instanceof StainedGlassPaneBlock);
            } else {
               â˜ƒ = true;
            }

            RenderType â˜ƒx = ItemBlockRenderTypes.getRenderType(â˜ƒ, â˜ƒ);
            VertexConsumer â˜ƒ;
            if (â˜ƒ.is(Items.COMPASS) && â˜ƒ.hasFoil()) {
               â˜ƒ.pushPose();
               PoseStack.Pose â˜ƒxx = â˜ƒ.last();
               if (â˜ƒ == ItemTransforms.TransformType.GUI) {
                  â˜ƒxx.pose().multiply(0.5F);
               } else if (â˜ƒ.firstPerson()) {
                  â˜ƒxx.pose().multiply(0.75F);
               }

               if (â˜ƒ) {
                  â˜ƒ = getCompassFoilBufferDirect(â˜ƒ, â˜ƒx, â˜ƒxx);
               } else {
                  â˜ƒ = getCompassFoilBuffer(â˜ƒ, â˜ƒx, â˜ƒxx);
               }

               â˜ƒ.popPose();
            } else if (â˜ƒ) {
               â˜ƒ = getFoilBufferDirect(â˜ƒ, â˜ƒx, true, â˜ƒ.hasFoil());
            } else {
               â˜ƒ = getFoilBuffer(â˜ƒ, â˜ƒx, true, â˜ƒ.hasFoil());
            }

            this.renderModelLists(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            this.blockEntityRenderer.renderByItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         â˜ƒ.popPose();
      }
   }

   public static VertexConsumer getArmorFoilBuffer(MultiBufferSource var0, RenderType var1, boolean var2, boolean var3) {
      return â˜ƒ
         ? VertexMultiConsumer.create(â˜ƒ.getBuffer(â˜ƒ ? RenderType.armorGlint() : RenderType.armorEntityGlint()), â˜ƒ.getBuffer(â˜ƒ))
         : â˜ƒ.getBuffer(â˜ƒ);
   }

   public static VertexConsumer getCompassFoilBuffer(MultiBufferSource var0, RenderType var1, PoseStack.Pose var2) {
      return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(â˜ƒ.getBuffer(RenderType.glint()), â˜ƒ.pose(), â˜ƒ.normal()), â˜ƒ.getBuffer(â˜ƒ));
   }

   public static VertexConsumer getCompassFoilBufferDirect(MultiBufferSource var0, RenderType var1, PoseStack.Pose var2) {
      return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(â˜ƒ.getBuffer(RenderType.glintDirect()), â˜ƒ.pose(), â˜ƒ.normal()), â˜ƒ.getBuffer(â˜ƒ));
   }

   public static VertexConsumer getFoilBuffer(MultiBufferSource var0, RenderType var1, boolean var2, boolean var3) {
      if (â˜ƒ) {
         return Minecraft.useShaderTransparency() && â˜ƒ == Sheets.translucentItemSheet()
            ? VertexMultiConsumer.create(â˜ƒ.getBuffer(RenderType.glintTranslucent()), â˜ƒ.getBuffer(â˜ƒ))
            : VertexMultiConsumer.create(â˜ƒ.getBuffer(â˜ƒ ? RenderType.glint() : RenderType.entityGlint()), â˜ƒ.getBuffer(â˜ƒ));
      } else {
         return â˜ƒ.getBuffer(â˜ƒ);
      }
   }

   public static VertexConsumer getFoilBufferDirect(MultiBufferSource var0, RenderType var1, boolean var2, boolean var3) {
      return â˜ƒ
         ? VertexMultiConsumer.create(â˜ƒ.getBuffer(â˜ƒ ? RenderType.glintDirect() : RenderType.entityGlintDirect()), â˜ƒ.getBuffer(â˜ƒ))
         : â˜ƒ.getBuffer(â˜ƒ);
   }

   private void renderQuadList(PoseStack var1, VertexConsumer var2, List<BakedQuad> var3, ItemStack var4, int var5, int var6) {
      boolean â˜ƒ = !â˜ƒ.isEmpty();
      PoseStack.Pose â˜ƒx = â˜ƒ.last();

      for(BakedQuad â˜ƒxx : â˜ƒ) {
         int â˜ƒxxx = -1;
         if (â˜ƒ && â˜ƒxx.isTinted()) {
            â˜ƒxxx = this.itemColors.getColor(â˜ƒ, â˜ƒxx.getTintIndex());
         }

         float â˜ƒxxx = (float)(â˜ƒxxx >> 16 & 0xFF) / 255.0F;
         float â˜ƒxxxx = (float)(â˜ƒxxx >> 8 & 0xFF) / 255.0F;
         float â˜ƒxxxxx = (float)(â˜ƒxxx & 0xFF) / 255.0F;
         â˜ƒ.putBulkData(â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ);
      }
   }

   public BakedModel getModel(ItemStack var1, @Nullable Level var2, @Nullable LivingEntity var3, int var4) {
      BakedModel â˜ƒ;
      if (â˜ƒ.is(Items.TRIDENT)) {
         â˜ƒ = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:trident_in_hand#inventory"));
      } else if (â˜ƒ.is(Items.SPYGLASS)) {
         â˜ƒ = this.itemModelShaper.getModelManager().getModel(new ModelResourceLocation("minecraft:spyglass_in_hand#inventory"));
      } else {
         â˜ƒ = this.itemModelShaper.getItemModel(â˜ƒ);
      }

      ClientLevel â˜ƒ = â˜ƒ instanceof ClientLevel ? (ClientLevel)â˜ƒ : null;
      BakedModel â˜ƒx = â˜ƒ.getOverrides().resolve(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒx == null ? this.itemModelShaper.getModelManager().getMissingModel() : â˜ƒx;
   }

   public void renderStatic(ItemStack var1, ItemTransforms.TransformType var2, int var3, int var4, PoseStack var5, MultiBufferSource var6, int var7) {
      this.renderStatic(null, â˜ƒ, â˜ƒ, false, â˜ƒ, â˜ƒ, null, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void renderStatic(
      @Nullable LivingEntity var1,
      ItemStack var2,
      ItemTransforms.TransformType var3,
      boolean var4,
      PoseStack var5,
      MultiBufferSource var6,
      @Nullable Level var7,
      int var8,
      int var9,
      int var10
   ) {
      if (!â˜ƒ.isEmpty()) {
         BakedModel â˜ƒ = this.getModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void renderGuiItem(ItemStack var1, int var2, int var3) {
      this.renderGuiItem(â˜ƒ, â˜ƒ, â˜ƒ, this.getModel(â˜ƒ, null, null, 0));
   }

   protected void renderGuiItem(ItemStack var1, int var2, int var3, BakedModel var4) {
      this.textureManager.getTexture(TextureAtlas.LOCATION_BLOCKS).setFilter(false, false);
      RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      PoseStack â˜ƒ = RenderSystem.getModelViewStack();
      â˜ƒ.pushPose();
      â˜ƒ.translate((double)â˜ƒ, (double)â˜ƒ, (double)(100.0F + this.blitOffset));
      â˜ƒ.translate(8.0, 8.0, 0.0);
      â˜ƒ.scale(1.0F, -1.0F, 1.0F);
      â˜ƒ.scale(16.0F, 16.0F, 16.0F);
      RenderSystem.applyModelViewMatrix();
      PoseStack â˜ƒx = new PoseStack();
      MultiBufferSource.BufferSource â˜ƒxx = Minecraft.getInstance().renderBuffers().bufferSource();
      boolean â˜ƒxxx = !â˜ƒ.usesBlockLight();
      if (â˜ƒxxx) {
         Lighting.setupForFlatItems();
      }

      this.render(â˜ƒ, ItemTransforms.TransformType.GUI, false, â˜ƒx, â˜ƒxx, 15728880, OverlayTexture.NO_OVERLAY, â˜ƒ);
      â˜ƒxx.endBatch();
      RenderSystem.enableDepthTest();
      if (â˜ƒxxx) {
         Lighting.setupFor3DItems();
      }

      â˜ƒ.popPose();
      RenderSystem.applyModelViewMatrix();
   }

   public void renderAndDecorateItem(ItemStack var1, int var2, int var3) {
      this.tryRenderGuiItem(Minecraft.getInstance().player, â˜ƒ, â˜ƒ, â˜ƒ, 0);
   }

   public void renderAndDecorateItem(ItemStack var1, int var2, int var3, int var4) {
      this.tryRenderGuiItem(Minecraft.getInstance().player, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void renderAndDecorateItem(ItemStack var1, int var2, int var3, int var4, int var5) {
      this.tryRenderGuiItem(Minecraft.getInstance().player, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void renderAndDecorateFakeItem(ItemStack var1, int var2, int var3) {
      this.tryRenderGuiItem(null, â˜ƒ, â˜ƒ, â˜ƒ, 0);
   }

   public void renderAndDecorateItem(LivingEntity var1, ItemStack var2, int var3, int var4, int var5) {
      this.tryRenderGuiItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void tryRenderGuiItem(@Nullable LivingEntity var1, ItemStack var2, int var3, int var4, int var5) {
      this.tryRenderGuiItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0);
   }

   private void tryRenderGuiItem(@Nullable LivingEntity var1, ItemStack var2, int var3, int var4, int var5, int var6) {
      if (!â˜ƒ.isEmpty()) {
         BakedModel â˜ƒ = this.getModel(â˜ƒ, null, â˜ƒ, â˜ƒ);
         this.blitOffset = â˜ƒ.isGui3d() ? this.blitOffset + 50.0F + (float)â˜ƒ : this.blitOffset + 50.0F;

         try {
            this.renderGuiItem(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         } catch (Throwable var11) {
            CrashReport â˜ƒx = CrashReport.forThrowable(var11, "Rendering item");
            CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Item being rendered");
            â˜ƒxx.setDetail("Item Type", (CrashReportDetail<String>)(() -> String.valueOf(â˜ƒ.getItem())));
            â˜ƒxx.setDetail("Item Damage", (CrashReportDetail<String>)(() -> String.valueOf(â˜ƒ.getDamageValue())));
            â˜ƒxx.setDetail("Item NBT", (CrashReportDetail<String>)(() -> String.valueOf(â˜ƒ.getTag())));
            â˜ƒxx.setDetail("Item Foil", (CrashReportDetail<String>)(() -> String.valueOf(â˜ƒ.hasFoil())));
            throw new ReportedException(â˜ƒx);
         }

         this.blitOffset = â˜ƒ.isGui3d() ? this.blitOffset - 50.0F - (float)â˜ƒ : this.blitOffset - 50.0F;
      }
   }

   public void renderGuiItemDecorations(Font var1, ItemStack var2, int var3, int var4) {
      this.renderGuiItemDecorations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, null);
   }

   public void renderGuiItemDecorations(Font var1, ItemStack var2, int var3, int var4, @Nullable String var5) {
      if (!â˜ƒ.isEmpty()) {
         PoseStack â˜ƒ = new PoseStack();
         if (â˜ƒ.getCount() != 1 || â˜ƒ != null) {
            String â˜ƒx = â˜ƒ == null ? String.valueOf(â˜ƒ.getCount()) : â˜ƒ;
            â˜ƒ.translate(0.0, 0.0, (double)(this.blitOffset + 200.0F));
            MultiBufferSource.BufferSource â˜ƒxx = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
            â˜ƒ.drawInBatch(â˜ƒx, (float)(â˜ƒ + 19 - 2 - â˜ƒ.width(â˜ƒx)), (float)(â˜ƒ + 6 + 3), 16777215, true, â˜ƒ.last().pose(), â˜ƒxx, false, 0, 15728880);
            â˜ƒxx.endBatch();
         }

         if (â˜ƒ.isBarVisible()) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.disableBlend();
            Tesselator â˜ƒ = Tesselator.getInstance();
            BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
            int â˜ƒxx = â˜ƒ.getBarWidth();
            int â˜ƒxxx = â˜ƒ.getBarColor();
            this.fillRect(â˜ƒx, â˜ƒ + 2, â˜ƒ + 13, 13, 2, 0, 0, 0, 255);
            this.fillRect(â˜ƒx, â˜ƒ + 2, â˜ƒ + 13, â˜ƒxx, 1, â˜ƒxxx >> 16 & 0xFF, â˜ƒxxx >> 8 & 0xFF, â˜ƒxxx & 0xFF, 255);
            RenderSystem.enableBlend();
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }

         LocalPlayer â˜ƒ = Minecraft.getInstance().player;
         float â˜ƒx = â˜ƒ == null ? 0.0F : â˜ƒ.getCooldowns().getCooldownPercent(â˜ƒ.getItem(), Minecraft.getInstance().getFrameTime());
         if (â˜ƒx > 0.0F) {
            RenderSystem.disableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            Tesselator â˜ƒxx = Tesselator.getInstance();
            BufferBuilder â˜ƒxxx = â˜ƒxx.getBuilder();
            this.fillRect(â˜ƒxxx, â˜ƒ, â˜ƒ + Mth.floor(16.0F * (1.0F - â˜ƒx)), 16, Mth.ceil(16.0F * â˜ƒx), 255, 255, 255, 127);
            RenderSystem.enableTexture();
            RenderSystem.enableDepthTest();
         }
      }
   }

   private void fillRect(BufferBuilder var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      RenderSystem.setShader(GameRenderer::getPositionColorShader);
      â˜ƒ.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      â˜ƒ.vertex((double)(â˜ƒ + 0), (double)(â˜ƒ + 0), 0.0).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex((double)(â˜ƒ + 0), (double)(â˜ƒ + â˜ƒ), 0.0).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex((double)(â˜ƒ + â˜ƒ), (double)(â˜ƒ + â˜ƒ), 0.0).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.vertex((double)(â˜ƒ + â˜ƒ), (double)(â˜ƒ + 0), 0.0).color(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).endVertex();
      â˜ƒ.end();
      BufferUploader.end(â˜ƒ);
   }

   @Override
   public void onResourceManagerReload(ResourceManager var1) {
      this.itemModelShaper.rebuildCache();
   }
}
