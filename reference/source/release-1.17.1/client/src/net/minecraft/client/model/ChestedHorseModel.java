package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

public class ChestedHorseModel<T extends AbstractChestedHorse> extends HorseModel<T> {
   private final ModelPart leftChest = this.body.getChild("left_chest");
   private final ModelPart rightChest = this.body.getChild("right_chest");

   public ChestedHorseModel(ModelPart var1) {
      super(â˜ƒ);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = HorseModel.createBodyMesh(CubeDeformation.NONE);
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.getChild("body");
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(26, 21).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      â˜ƒxx.addOrReplaceChild("left_chest", â˜ƒxxx, PartPose.offsetAndRotation(6.0F, -8.0F, 0.0F, 0.0F, (float) (-Math.PI / 2), 0.0F));
      â˜ƒxx.addOrReplaceChild("right_chest", â˜ƒxxx, PartPose.offsetAndRotation(-6.0F, -8.0F, 0.0F, 0.0F, (float) (Math.PI / 2), 0.0F));
      PartDefinition â˜ƒxxxx = â˜ƒx.getChild("head_parts").getChild("head");
      CubeListBuilder â˜ƒxxxxx = CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      â˜ƒxxxx.addOrReplaceChild("left_ear", â˜ƒxxxxx, PartPose.offsetAndRotation(1.25F, -10.0F, 4.0F, (float) (Math.PI / 12), 0.0F, (float) (Math.PI / 12)));
      â˜ƒxxxx.addOrReplaceChild("right_ear", â˜ƒxxxxx, PartPose.offsetAndRotation(-1.25F, -10.0F, 4.0F, (float) (Math.PI / 12), 0.0F, (float) (-Math.PI / 12)));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      super.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.hasChest()) {
         this.leftChest.visible = true;
         this.rightChest.visible = true;
      } else {
         this.leftChest.visible = false;
         this.rightChest.visible = false;
      }
   }
}
