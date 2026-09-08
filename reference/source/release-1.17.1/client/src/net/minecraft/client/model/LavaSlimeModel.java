package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Slime;

public class LavaSlimeModel<T extends Slime> extends HierarchicalModel<T> {
   private static final int SEGMENT_COUNT = 8;
   private final ModelPart root;
   private final ModelPart[] bodyCubes = new ModelPart[8];

   public LavaSlimeModel(ModelPart var1) {
      this.root = â˜ƒ;
      Arrays.setAll(this.bodyCubes, var1x -> â˜ƒ.getChild(getSegmentName(var1x)));
   }

   private static String getSegmentName(int var0) {
      return "cube" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();

      for(int â˜ƒxx = 0; â˜ƒxx < 8; ++â˜ƒxx) {
         int â˜ƒxxx = 0;
         int â˜ƒxxxx = â˜ƒxx;
         if (â˜ƒxx == 2) {
            â˜ƒxxx = 24;
            â˜ƒxxxx = 10;
         } else if (â˜ƒxx == 3) {
            â˜ƒxxx = 24;
            â˜ƒxxxx = 19;
         }

         â˜ƒx.addOrReplaceChild(
            getSegmentName(â˜ƒxx), CubeListBuilder.create().texOffs(â˜ƒxxx, â˜ƒxxxx).addBox(-4.0F, (float)(16 + â˜ƒxx), -4.0F, 8.0F, 1.0F, 8.0F), PartPose.ZERO
         );
      }

      â˜ƒx.addOrReplaceChild("inside_cube", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 18.0F, -2.0F, 4.0F, 4.0F, 4.0F), PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
   }

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
      float â˜ƒ = Mth.lerp(â˜ƒ, â˜ƒ.oSquish, â˜ƒ.squish);
      if (â˜ƒ < 0.0F) {
         â˜ƒ = 0.0F;
      }

      for(int â˜ƒ = 0; â˜ƒ < this.bodyCubes.length; ++â˜ƒ) {
         this.bodyCubes[â˜ƒ].y = (float)(-(4 - â˜ƒ)) * â˜ƒ * 1.7F;
      }
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
