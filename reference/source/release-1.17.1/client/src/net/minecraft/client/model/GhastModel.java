package net.minecraft.client.model;

import java.util.Random;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class GhastModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart[] tentacles = new ModelPart[9];

   public GhastModel(ModelPart var1) {
      this.root = â˜ƒ;

      for(int â˜ƒ = 0; â˜ƒ < this.tentacles.length; ++â˜ƒ) {
         this.tentacles[â˜ƒ] = â˜ƒ.getChild(createTentacleName(â˜ƒ));
      }
   }

   private static String createTentacleName(int var0) {
      return "tentacle" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 17.6F, 0.0F)
      );
      Random â˜ƒxx = new Random(1660L);

      for(int â˜ƒxxx = 0; â˜ƒxxx < 9; ++â˜ƒxxx) {
         float â˜ƒxxxx = (((float)(â˜ƒxxx % 3) - (float)(â˜ƒxxx / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
         float â˜ƒxxxxx = ((float)(â˜ƒxxx / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
         int â˜ƒxxxxxx = â˜ƒxx.nextInt(7) + 8;
         â˜ƒx.addOrReplaceChild(
            createTentacleName(â˜ƒxxx),
            CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, (float)â˜ƒxxxxxx, 2.0F),
            PartPose.offset(â˜ƒxxxx, 24.6F, â˜ƒxxxxx)
         );
      }

      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      for(int â˜ƒ = 0; â˜ƒ < this.tentacles.length; ++â˜ƒ) {
         this.tentacles[â˜ƒ].xRot = 0.2F * Mth.sin(â˜ƒ * 0.3F + (float)â˜ƒ) + 0.4F;
      }
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
