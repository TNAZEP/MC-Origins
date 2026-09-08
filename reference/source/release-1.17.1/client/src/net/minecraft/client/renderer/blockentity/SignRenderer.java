package net.minecraft.client.renderer.blockentity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;

public class SignRenderer implements BlockEntityRenderer<SignBlockEntity> {
   public static final int MAX_LINE_WIDTH = 90;
   private static final int LINE_HEIGHT = 10;
   private static final String STICK = "stick";
   private static final int BLACK_TEXT_OUTLINE_COLOR = -988212;
   private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
   private final Map<WoodType, SignRenderer.SignModel> signModels;
   private final Font font;

   public SignRenderer(BlockEntityRendererProvider.Context var1) {
      this.signModels = (Map)WoodType.values()
         .collect(ImmutableMap.toImmutableMap(var0 -> var0, var1x -> new SignRenderer.SignModel(â˜ƒ.bakeLayer(ModelLayers.createSignModelName(var1x)))));
      this.font = â˜ƒ.getFont();
   }

   public void render(SignBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      BlockState â˜ƒ = â˜ƒ.getBlockState();
      â˜ƒ.pushPose();
      float â˜ƒx = 0.6666667F;
      WoodType â˜ƒxx = getWoodType(â˜ƒ.getBlock());
      SignRenderer.SignModel â˜ƒxxx = (SignRenderer.SignModel)this.signModels.get(â˜ƒxx);
      if (â˜ƒ.getBlock() instanceof StandingSignBlock) {
         â˜ƒ.translate(0.5, 0.5, 0.5);
         float â˜ƒxxxx = -((float)(â˜ƒ.getValue(StandingSignBlock.ROTATION) * 360) / 16.0F);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxxxx));
         â˜ƒxxx.stick.visible = true;
      } else {
         â˜ƒ.translate(0.5, 0.5, 0.5);
         float â˜ƒ = -((Direction)â˜ƒ.getValue(WallSignBlock.FACING)).toYRot();
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ));
         â˜ƒ.translate(0.0, -0.3125, -0.4375);
         â˜ƒxxx.stick.visible = false;
      }

      â˜ƒ.pushPose();
      â˜ƒ.scale(0.6666667F, -0.6666667F, -0.6666667F);
      Material â˜ƒxxx = Sheets.getSignMaterial(â˜ƒxx);
      VertexConsumer â˜ƒxxxx = â˜ƒxxx.buffer(â˜ƒ, â˜ƒxxx::renderType);
      â˜ƒxxx.root.render(â˜ƒ, â˜ƒxxxx, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
      float â˜ƒxxxxx = 0.010416667F;
      â˜ƒ.translate(0.0, 0.33333334F, 0.046666667F);
      â˜ƒ.scale(0.010416667F, -0.010416667F, 0.010416667F);
      int â˜ƒxxxxxx = getDarkColor(â˜ƒ);
      int â˜ƒxxxxxxx = 20;
      FormattedCharSequence[] â˜ƒxxxxxxxx = â˜ƒ.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), var1x -> {
         List<FormattedCharSequence> â˜ƒ = this.font.split(var1x, 90);
         return â˜ƒ.isEmpty() ? FormattedCharSequence.EMPTY : (FormattedCharSequence)â˜ƒ.get(0);
      });
      int â˜ƒ;
      boolean â˜ƒx;
      int â˜ƒxx;
      if (â˜ƒ.hasGlowingText()) {
         â˜ƒ = â˜ƒ.getColor().getTextColor();
         â˜ƒx = isOutlineVisible(â˜ƒ, â˜ƒ);
         â˜ƒxx = 15728880;
      } else {
         â˜ƒ = â˜ƒxxxxxx;
         â˜ƒx = false;
         â˜ƒxx = â˜ƒ;
      }

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         FormattedCharSequence â˜ƒx = â˜ƒxxxxxxxx[â˜ƒ];
         float â˜ƒxx = (float)(-this.font.width(â˜ƒx) / 2);
         if (â˜ƒx) {
            this.font.drawInBatch8xOutline(â˜ƒx, â˜ƒxx, (float)(â˜ƒ * 10 - 20), â˜ƒ, â˜ƒxxxxxx, â˜ƒ.last().pose(), â˜ƒ, â˜ƒxx);
         } else {
            this.font.drawInBatch(â˜ƒx, â˜ƒxx, (float)(â˜ƒ * 10 - 20), â˜ƒ, false, â˜ƒ.last().pose(), â˜ƒ, false, 0, â˜ƒxx);
         }
      }

      â˜ƒ.popPose();
   }

   private static boolean isOutlineVisible(SignBlockEntity var0, int var1) {
      if (â˜ƒ == DyeColor.BLACK.getTextColor()) {
         return true;
      } else {
         Minecraft â˜ƒ = Minecraft.getInstance();
         LocalPlayer â˜ƒx = â˜ƒ.player;
         if (â˜ƒx != null && â˜ƒ.options.getCameraType().isFirstPerson() && â˜ƒx.isScoping()) {
            return true;
         } else {
            Entity â˜ƒ = â˜ƒ.getCameraEntity();
            return â˜ƒ != null && â˜ƒ.distanceToSqr(Vec3.atCenterOf(â˜ƒ.getBlockPos())) < (double)OUTLINE_RENDER_DISTANCE;
         }
      }
   }

   private static int getDarkColor(SignBlockEntity var0) {
      int â˜ƒ = â˜ƒ.getColor().getTextColor();
      double â˜ƒx = 0.4;
      int â˜ƒxx = (int)((double)NativeImage.getR(â˜ƒ) * 0.4);
      int â˜ƒxxx = (int)((double)NativeImage.getG(â˜ƒ) * 0.4);
      int â˜ƒxxxx = (int)((double)NativeImage.getB(â˜ƒ) * 0.4);
      return â˜ƒ == DyeColor.BLACK.getTextColor() && â˜ƒ.hasGlowingText() ? -988212 : NativeImage.combine(0, â˜ƒxxxx, â˜ƒxxx, â˜ƒxx);
   }

   public static WoodType getWoodType(Block var0) {
      WoodType â˜ƒ;
      if (â˜ƒ instanceof SignBlock) {
         â˜ƒ = ((SignBlock)â˜ƒ).type();
      } else {
         â˜ƒ = WoodType.OAK;
      }

      return â˜ƒ;
   }

   public static SignRenderer.SignModel createSignModel(EntityModelSet var0, WoodType var1) {
      return new SignRenderer.SignModel(â˜ƒ.bakeLayer(ModelLayers.createSignModelName(â˜ƒ)));
   }

   public static LayerDefinition createSignLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("stick", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public static final class SignModel extends Model {
      public final ModelPart root;
      public final ModelPart stick;

      public SignModel(ModelPart var1) {
         super(RenderType::entityCutoutNoCull);
         this.root = â˜ƒ;
         this.stick = â˜ƒ.getChild("stick");
      }

      @Override
      public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         this.root.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
