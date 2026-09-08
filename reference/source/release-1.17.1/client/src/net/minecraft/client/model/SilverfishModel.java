package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class SilverfishModel<T extends Entity> extends HierarchicalModel<T> {
   private static final int BODY_COUNT = 7;
   private final ModelPart root;
   private final ModelPart[] bodyParts = new ModelPart[7];
   private final ModelPart[] bodyLayers = new ModelPart[3];
   private static final int[][] BODY_SIZES = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] BODY_TEXS = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public SilverfishModel(ModelPart var1) {
      this.root = â˜ƒ;
      Arrays.setAll(this.bodyParts, var1x -> â˜ƒ.getChild(getSegmentName(var1x)));
      Arrays.setAll(this.bodyLayers, var1x -> â˜ƒ.getChild(getLayerName(var1x)));
   }

   private static String getLayerName(int var0) {
      return "layer" + â˜ƒ;
   }

   private static String getSegmentName(int var0) {
      return "segment" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      float[] â˜ƒxx = new float[7];
      float â˜ƒxxx = -3.5F;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 7; ++â˜ƒxxxx) {
         â˜ƒx.addOrReplaceChild(
            getSegmentName(â˜ƒxxxx),
            CubeListBuilder.create()
               .texOffs(BODY_TEXS[â˜ƒxxxx][0], BODY_TEXS[â˜ƒxxxx][1])
               .addBox(
                  (float)BODY_SIZES[â˜ƒxxxx][0] * -0.5F,
                  0.0F,
                  (float)BODY_SIZES[â˜ƒxxxx][2] * -0.5F,
                  (float)BODY_SIZES[â˜ƒxxxx][0],
                  (float)BODY_SIZES[â˜ƒxxxx][1],
                  (float)BODY_SIZES[â˜ƒxxxx][2]
               ),
            PartPose.offset(0.0F, (float)(24 - BODY_SIZES[â˜ƒxxxx][1]), â˜ƒxxx)
         );
         â˜ƒxx[â˜ƒxxxx] = â˜ƒxxx;
         if (â˜ƒxxxx < 6) {
            â˜ƒxxx += (float)(BODY_SIZES[â˜ƒxxxx][2] + BODY_SIZES[â˜ƒxxxx + 1][2]) * 0.5F;
         }
      }

      â˜ƒx.addOrReplaceChild(
         getLayerName(0),
         CubeListBuilder.create().texOffs(20, 0).addBox(-5.0F, 0.0F, (float)BODY_SIZES[2][2] * -0.5F, 10.0F, 8.0F, (float)BODY_SIZES[2][2]),
         PartPose.offset(0.0F, 16.0F, â˜ƒxx[2])
      );
      â˜ƒx.addOrReplaceChild(
         getLayerName(1),
         CubeListBuilder.create().texOffs(20, 11).addBox(-3.0F, 0.0F, (float)BODY_SIZES[4][2] * -0.5F, 6.0F, 4.0F, (float)BODY_SIZES[4][2]),
         PartPose.offset(0.0F, 20.0F, â˜ƒxx[4])
      );
      â˜ƒx.addOrReplaceChild(
         getLayerName(2),
         CubeListBuilder.create().texOffs(20, 18).addBox(-3.0F, 0.0F, (float)BODY_SIZES[4][2] * -0.5F, 6.0F, 5.0F, (float)BODY_SIZES[1][2]),
         PartPose.offset(0.0F, 19.0F, â˜ƒxx[1])
      );
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      for(int â˜ƒ = 0; â˜ƒ < this.bodyParts.length; ++â˜ƒ) {
         this.bodyParts[â˜ƒ].yRot = Mth.cos(â˜ƒ * 0.9F + (float)â˜ƒ * 0.15F * (float) Math.PI) * (float) Math.PI * 0.05F * (float)(1 + Math.abs(â˜ƒ - 2));
         this.bodyParts[â˜ƒ].x = Mth.sin(â˜ƒ * 0.9F + (float)â˜ƒ * 0.15F * (float) Math.PI) * (float) Math.PI * 0.2F * (float)Math.abs(â˜ƒ - 2);
      }

      this.bodyLayers[0].yRot = this.bodyParts[2].yRot;
      this.bodyLayers[1].yRot = this.bodyParts[4].yRot;
      this.bodyLayers[1].x = this.bodyParts[4].x;
      this.bodyLayers[2].yRot = this.bodyParts[1].yRot;
      this.bodyLayers[2].x = this.bodyParts[1].x;
   }
}
