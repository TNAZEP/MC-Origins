package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Vector3f;
import java.util.List;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BannerBlock;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.state.BlockState;

public class BannerRenderer implements BlockEntityRenderer<BannerBlockEntity> {
   private static final int BANNER_WIDTH = 20;
   private static final int BANNER_HEIGHT = 40;
   private static final int MAX_PATTERNS = 16;
   public static final String FLAG = "flag";
   private static final String POLE = "pole";
   private static final String BAR = "bar";
   private final ModelPart flag;
   private final ModelPart pole;
   private final ModelPart bar;

   public BannerRenderer(BlockEntityRendererProvider.Context var1) {
      ModelPart â˜ƒ = â˜ƒ.bakeLayer(ModelLayers.BANNER);
      this.flag = â˜ƒ.getChild("flag");
      this.pole = â˜ƒ.getChild("pole");
      this.bar = â˜ƒ.getChild("bar");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("flag", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 42).addBox(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void render(BannerBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      List<Pair<BannerPattern, DyeColor>> â˜ƒ = â˜ƒ.getPatterns();
      if (â˜ƒ != null) {
         float â˜ƒxx = 0.6666667F;
         boolean â˜ƒxxx = â˜ƒ.getLevel() == null;
         â˜ƒ.pushPose();
         long â˜ƒx;
         if (â˜ƒxxx) {
            â˜ƒx = 0L;
            â˜ƒ.translate(0.5, 0.5, 0.5);
            this.pole.visible = true;
         } else {
            â˜ƒx = â˜ƒ.getLevel().getGameTime();
            BlockState â˜ƒx = â˜ƒ.getBlockState();
            if (â˜ƒx.getBlock() instanceof BannerBlock) {
               â˜ƒ.translate(0.5, 0.5, 0.5);
               float â˜ƒxx = (float)(-â˜ƒx.getValue(BannerBlock.ROTATION) * 360) / 16.0F;
               â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxx));
               this.pole.visible = true;
            } else {
               â˜ƒ.translate(0.5, -0.16666667F, 0.5);
               float â˜ƒx = -((Direction)â˜ƒx.getValue(WallBannerBlock.FACING)).toYRot();
               â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx));
               â˜ƒ.translate(0.0, -0.3125, -0.4375);
               this.pole.visible = false;
            }
         }

         â˜ƒ.pushPose();
         â˜ƒ.scale(0.6666667F, -0.6666667F, -0.6666667F);
         VertexConsumer â˜ƒx = ModelBakery.BANNER_BASE.buffer(â˜ƒ, RenderType::entitySolid);
         this.pole.render(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
         this.bar.render(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ);
         BlockPos â˜ƒxx = â˜ƒ.getBlockPos();
         float â˜ƒxxx = ((float)Math.floorMod((long)(â˜ƒxx.getX() * 7 + â˜ƒxx.getY() * 9 + â˜ƒxx.getZ() * 13) + â˜ƒx, 100L) + â˜ƒ) / 100.0F;
         this.flag.xRot = (-0.0125F + 0.01F * Mth.cos((float) (Math.PI * 2) * â˜ƒxxx)) * (float) Math.PI;
         this.flag.y = -32.0F;
         renderPatterns(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.flag, ModelBakery.BANNER_BASE, true, â˜ƒ);
         â˜ƒ.popPose();
         â˜ƒ.popPose();
      }
   }

   public static void renderPatterns(
      PoseStack var0, MultiBufferSource var1, int var2, int var3, ModelPart var4, Material var5, boolean var6, List<Pair<BannerPattern, DyeColor>> var7
   ) {
      renderPatterns(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public static void renderPatterns(
      PoseStack var0,
      MultiBufferSource var1,
      int var2,
      int var3,
      ModelPart var4,
      Material var5,
      boolean var6,
      List<Pair<BannerPattern, DyeColor>> var7,
      boolean var8
   ) {
      â˜ƒ.render(â˜ƒ, â˜ƒ.buffer(â˜ƒ, RenderType::entitySolid, â˜ƒ), â˜ƒ, â˜ƒ);

      for(int â˜ƒ = 0; â˜ƒ < 17 && â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         Pair<BannerPattern, DyeColor> â˜ƒx = (Pair)â˜ƒ.get(â˜ƒ);
         float[] â˜ƒxx = â˜ƒx.getSecond().getTextureDiffuseColors();
         BannerPattern â˜ƒxxx = (BannerPattern)â˜ƒx.getFirst();
         Material â˜ƒxxxx = â˜ƒ ? Sheets.getBannerMaterial(â˜ƒxxx) : Sheets.getShieldMaterial(â˜ƒxxx);
         â˜ƒ.render(â˜ƒ, â˜ƒxxxx.buffer(â˜ƒ, RenderType::entityNoOutline), â˜ƒ, â˜ƒ, â˜ƒxx[0], â˜ƒxx[1], â˜ƒxx[2], 1.0F);
      }
   }
}
