package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class BookModel extends Model {
   private static final String LEFT_PAGES = "left_pages";
   private static final String RIGHT_PAGES = "right_pages";
   private static final String FLIP_PAGE_1 = "flip_page1";
   private static final String FLIP_PAGE_2 = "flip_page2";
   private final ModelPart root;
   private final ModelPart leftLid;
   private final ModelPart rightLid;
   private final ModelPart leftPages;
   private final ModelPart rightPages;
   private final ModelPart flipPage1;
   private final ModelPart flipPage2;

   public BookModel(ModelPart var1) {
      super(RenderType::entitySolid);
      this.root = â˜ƒ;
      this.leftLid = â˜ƒ.getChild("left_lid");
      this.rightLid = â˜ƒ.getChild("right_lid");
      this.leftPages = â˜ƒ.getChild("left_pages");
      this.rightPages = â˜ƒ.getChild("right_pages");
      this.flipPage1 = â˜ƒ.getChild("flip_page1");
      this.flipPage2 = â˜ƒ.getChild("flip_page2");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild(
         "left_lid", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), PartPose.offset(0.0F, 0.0F, -1.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_lid", CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, -5.0F, -0.005F, 6.0F, 10.0F, 0.005F), PartPose.offset(0.0F, 0.0F, 1.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "seam", CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 10.0F, 0.005F), PartPose.rotation(0.0F, (float) (Math.PI / 2), 0.0F)
      );
      â˜ƒx.addOrReplaceChild("left_pages", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -4.0F, -0.99F, 5.0F, 8.0F, 1.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("right_pages", CubeListBuilder.create().texOffs(12, 10).addBox(0.0F, -4.0F, -0.01F, 5.0F, 8.0F, 1.0F), PartPose.ZERO);
      CubeListBuilder â˜ƒxx = CubeListBuilder.create().texOffs(24, 10).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.005F);
      â˜ƒx.addOrReplaceChild("flip_page1", â˜ƒxx, PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("flip_page2", â˜ƒxx, PartPose.ZERO);
      return LayerDefinition.create(â˜ƒ, 64, 32);
   }

   @Override
   public void renderToBuffer(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void render(PoseStack var1, VertexConsumer var2, int var3, int var4, float var5, float var6, float var7, float var8) {
      this.root.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void setupAnim(float var1, float var2, float var3, float var4) {
      float â˜ƒ = (Mth.sin(â˜ƒ * 0.02F) * 0.1F + 1.25F) * â˜ƒ;
      this.leftLid.yRot = (float) Math.PI + â˜ƒ;
      this.rightLid.yRot = -â˜ƒ;
      this.leftPages.yRot = â˜ƒ;
      this.rightPages.yRot = -â˜ƒ;
      this.flipPage1.yRot = â˜ƒ - â˜ƒ * 2.0F * â˜ƒ;
      this.flipPage2.yRot = â˜ƒ - â˜ƒ * 2.0F * â˜ƒ;
      this.leftPages.x = Mth.sin(â˜ƒ);
      this.rightPages.x = Mth.sin(â˜ƒ);
      this.flipPage1.x = Mth.sin(â˜ƒ);
      this.flipPage2.x = Mth.sin(â˜ƒ);
   }
}
