package net.minecraft.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Shulker;

public class ShulkerModel<T extends Shulker> extends ListModel<T> {
   private static final String LID = "lid";
   private static final String BASE = "base";
   private final ModelPart base;
   private final ModelPart lid;
   private final ModelPart head;

   public ShulkerModel(ModelPart var1) {
      super(RenderType::entityCutoutNoCullZOffset);
      this.lid = â˜ƒ.getChild("lid");
      this.base = â˜ƒ.getChild("base");
      this.head = â˜ƒ.getChild("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "lid", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F), PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "base", CubeListBuilder.create().texOffs(0, 28).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F), PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 52).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.offset(0.0F, 12.0F, 0.0F));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = â˜ƒ - (float)â˜ƒ.tickCount;
      float â˜ƒx = (0.5F + â˜ƒ.getClientPeekAmount(â˜ƒ)) * (float) Math.PI;
      float â˜ƒxx = -1.0F + Mth.sin(â˜ƒx);
      float â˜ƒxxx = 0.0F;
      if (â˜ƒx > (float) Math.PI) {
         â˜ƒxxx = Mth.sin(â˜ƒ * 0.1F) * 0.7F;
      }

      this.lid.setPos(0.0F, 16.0F + Mth.sin(â˜ƒx) * 8.0F + â˜ƒxxx, 0.0F);
      if (â˜ƒ.getClientPeekAmount(â˜ƒ) > 0.3F) {
         this.lid.yRot = â˜ƒxx * â˜ƒxx * â˜ƒxx * â˜ƒxx * (float) Math.PI * 0.125F;
      } else {
         this.lid.yRot = 0.0F;
      }

      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.yRot = (â˜ƒ.yHeadRot - 180.0F - â˜ƒ.yBodyRot) * (float) (Math.PI / 180.0);
   }

   @Override
   public Iterable<ModelPart> parts() {
      return ImmutableList.<ModelPart>of(this.base, this.lid);
   }

   public ModelPart getLid() {
      return this.lid;
   }

   public ModelPart getHead() {
      return this.head;
   }
}
