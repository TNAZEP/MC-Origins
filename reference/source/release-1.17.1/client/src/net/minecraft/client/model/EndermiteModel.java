package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class EndermiteModel<T extends Entity> extends HierarchicalModel<T> {
   private static final int BODY_COUNT = 4;
   private static final int[][] BODY_SIZES = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] BODY_TEXS = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private final ModelPart root;
   private final ModelPart[] bodyParts;

   public EndermiteModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.bodyParts = new ModelPart[4];

      for(int â˜ƒ = 0; â˜ƒ < 4; ++â˜ƒ) {
         this.bodyParts[â˜ƒ] = â˜ƒ.getChild(createSegmentName(â˜ƒ));
      }
   }

   private static String createSegmentName(int var0) {
      return "segment" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      float â˜ƒxx = -3.5F;

      for(int â˜ƒxxx = 0; â˜ƒxxx < 4; ++â˜ƒxxx) {
         â˜ƒx.addOrReplaceChild(
            createSegmentName(â˜ƒxxx),
            CubeListBuilder.create()
               .texOffs(BODY_TEXS[â˜ƒxxx][0], BODY_TEXS[â˜ƒxxx][1])
               .addBox(
                  (float)BODY_SIZES[â˜ƒxxx][0] * -0.5F,
                  0.0F,
                  (float)BODY_SIZES[â˜ƒxxx][2] * -0.5F,
                  (float)BODY_SIZES[â˜ƒxxx][0],
                  (float)BODY_SIZES[â˜ƒxxx][1],
                  (float)BODY_SIZES[â˜ƒxxx][2]
               ),
            PartPose.offset(0.0F, (float)(24 - BODY_SIZES[â˜ƒxxx][1]), â˜ƒxx)
         );
         if (â˜ƒxxx < 3) {
            â˜ƒxx += (float)(BODY_SIZES[â˜ƒxxx][2] + BODY_SIZES[â˜ƒxxx + 1][2]) * 0.5F;
         }
      }

      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      for(int â˜ƒ = 0; â˜ƒ < this.bodyParts.length; ++â˜ƒ) {
         this.bodyParts[â˜ƒ].yRot = Mth.cos(â˜ƒ * 0.9F + (float)â˜ƒ * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float)(1 + Math.abs(â˜ƒ - 2));
         this.bodyParts[â˜ƒ].x = Mth.sin(â˜ƒ * 0.9F + (float)â˜ƒ * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float)Math.abs(â˜ƒ - 2);
      }
   }
}
