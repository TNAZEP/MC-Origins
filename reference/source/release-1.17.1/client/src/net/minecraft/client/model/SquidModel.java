package net.minecraft.client.model;

import java.util.Arrays;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class SquidModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart[] tentacles = new ModelPart[8];
   private final ModelPart root;

   public SquidModel(ModelPart var1) {
      this.root = â˜ƒ;
      Arrays.setAll(this.tentacles, var1x -> â˜ƒ.getChild(createTentacleName(var1x)));
   }

   private static String createTentacleName(int var0) {
      return "tentacle" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      int â˜ƒxx = -16;
      â˜ƒx.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
      int â˜ƒxxx = 8;
      CubeListBuilder â˜ƒxxxx = CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 8; ++â˜ƒxxxxx) {
         double â˜ƒxxxxxx = (double)â˜ƒxxxxx * Math.PI * 2.0 / 8.0;
         float â˜ƒxxxxxxx = (float)Math.cos(â˜ƒxxxxxx) * 5.0F;
         float â˜ƒxxxxxxxx = 15.0F;
         float â˜ƒxxxxxxxxx = (float)Math.sin(â˜ƒxxxxxx) * 5.0F;
         â˜ƒxxxxxx = (double)â˜ƒxxxxx * Math.PI * -2.0 / 8.0 + (Math.PI / 2);
         float â˜ƒxxxxxxxxxx = (float)â˜ƒxxxxxx;
         â˜ƒx.addOrReplaceChild(createTentacleName(â˜ƒxxxxx), â˜ƒxxxx, PartPose.offsetAndRotation(â˜ƒxxxxxxx, 15.0F, â˜ƒxxxxxxxxx, 0.0F, â˜ƒxxxxxxxxxx, 0.0F));
      }

      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      for(ModelPart â˜ƒ : this.tentacles) {
         â˜ƒ.xRot = â˜ƒ;
      }
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
