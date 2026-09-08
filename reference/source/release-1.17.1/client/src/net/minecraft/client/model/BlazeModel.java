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

public class BlazeModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart[] upperBodyParts;
   private final ModelPart head;

   public BlazeModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.head = â˜ƒ.getChild("head");
      this.upperBodyParts = new ModelPart[12];
      Arrays.setAll(this.upperBodyParts, var1x -> â˜ƒ.getChild(getPartName(var1x)));
   }

   private static String getPartName(int var0) {
      return "part" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
      float â˜ƒxx = 0.0F;
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 4; ++â˜ƒxxxx) {
         float â˜ƒxxxxx = Mth.cos(â˜ƒxx) * 9.0F;
         float â˜ƒxxxxxx = -2.0F + Mth.cos((float)(â˜ƒxxxx * 2) * 0.25F);
         float â˜ƒxxxxxxx = Mth.sin(â˜ƒxx) * 9.0F;
         â˜ƒx.addOrReplaceChild(getPartName(â˜ƒxxxx), â˜ƒxxx, PartPose.offset(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx));
         ++â˜ƒxx;
      }

      â˜ƒxx = (float) (Math.PI / 4);

      for(int â˜ƒxxxx = 4; â˜ƒxxxx < 8; ++â˜ƒxxxx) {
         float â˜ƒxxxxx = Mth.cos(â˜ƒxx) * 7.0F;
         float â˜ƒxxxxxx = 2.0F + Mth.cos((float)(â˜ƒxxxx * 2) * 0.25F);
         float â˜ƒxxxxxxx = Mth.sin(â˜ƒxx) * 7.0F;
         â˜ƒx.addOrReplaceChild(getPartName(â˜ƒxxxx), â˜ƒxxx, PartPose.offset(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx));
         ++â˜ƒxx;
      }

      â˜ƒxx = 0.47123894F;

      for(int â˜ƒxxxx = 8; â˜ƒxxxx < 12; ++â˜ƒxxxx) {
         float â˜ƒxxxxx = Mth.cos(â˜ƒxx) * 5.0F;
         float â˜ƒxxxxxx = 11.0F + Mth.cos((float)â˜ƒxxxx * 1.5F * 0.5F);
         float â˜ƒxxxxxxx = Mth.sin(â˜ƒxx) * 5.0F;
         â˜ƒx.addOrReplaceChild(getPartName(â˜ƒxxxx), â˜ƒxxx, PartPose.offset(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx));
         ++â˜ƒxx;
      }

      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = â˜ƒ * (float) Math.PI * -0.1F;

      for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
         this.upperBodyParts[â˜ƒx].y = -2.0F + Mth.cos(((float)(â˜ƒx * 2) + â˜ƒ) * 0.25F);
         this.upperBodyParts[â˜ƒx].x = Mth.cos(â˜ƒ) * 9.0F;
         this.upperBodyParts[â˜ƒx].z = Mth.sin(â˜ƒ) * 9.0F;
         ++â˜ƒ;
      }

      â˜ƒ = (float) (Math.PI / 4) + â˜ƒ * (float) Math.PI * 0.03F;

      for(int â˜ƒx = 4; â˜ƒx < 8; ++â˜ƒx) {
         this.upperBodyParts[â˜ƒx].y = 2.0F + Mth.cos(((float)(â˜ƒx * 2) + â˜ƒ) * 0.25F);
         this.upperBodyParts[â˜ƒx].x = Mth.cos(â˜ƒ) * 7.0F;
         this.upperBodyParts[â˜ƒx].z = Mth.sin(â˜ƒ) * 7.0F;
         ++â˜ƒ;
      }

      â˜ƒ = 0.47123894F + â˜ƒ * (float) Math.PI * -0.05F;

      for(int â˜ƒx = 8; â˜ƒx < 12; ++â˜ƒx) {
         this.upperBodyParts[â˜ƒx].y = 11.0F + Mth.cos(((float)â˜ƒx * 1.5F + â˜ƒ) * 0.5F);
         this.upperBodyParts[â˜ƒx].x = Mth.cos(â˜ƒ) * 5.0F;
         this.upperBodyParts[â˜ƒx].z = Mth.sin(â˜ƒ) * 5.0F;
         ++â˜ƒ;
      }

      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
   }
}
