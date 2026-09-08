package net.minecraft.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.Vec3;

public class GuardianModel extends HierarchicalModel<Guardian> {
   private static final float[] SPIKE_X_ROT = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
   private static final float[] SPIKE_Y_ROT = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
   private static final float[] SPIKE_Z_ROT = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
   private static final float[] SPIKE_X = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
   private static final float[] SPIKE_Y = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
   private static final float[] SPIKE_Z = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
   private static final String EYE = "eye";
   private static final String TAIL_0 = "tail0";
   private static final String TAIL_1 = "tail1";
   private static final String TAIL_2 = "tail2";
   private final ModelPart root;
   private final ModelPart head;
   private final ModelPart eye;
   private final ModelPart[] spikeParts;
   private final ModelPart[] tailParts;

   public GuardianModel(ModelPart var1) {
      this.root = â˜ƒ;
      this.spikeParts = new ModelPart[12];
      this.head = â˜ƒ.getChild("head");

      for(int â˜ƒ = 0; â˜ƒ < this.spikeParts.length; ++â˜ƒ) {
         this.spikeParts[â˜ƒ] = this.head.getChild(createSpikeName(â˜ƒ));
      }

      this.eye = this.head.getChild("eye");
      this.tailParts = new ModelPart[3];
      this.tailParts[0] = this.head.getChild("tail0");
      this.tailParts[1] = this.tailParts[0].getChild("tail1");
      this.tailParts[2] = this.tailParts[1].getChild("tail2");
   }

   private static String createSpikeName(int var0) {
      return "spike" + â˜ƒ;
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      PartDefinition â˜ƒxx = â˜ƒx.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F)
            .texOffs(0, 28)
            .addBox(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F)
            .texOffs(0, 28)
            .addBox(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true)
            .texOffs(16, 40)
            .addBox(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F)
            .texOffs(16, 40)
            .addBox(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F),
         PartPose.ZERO
      );
      CubeListBuilder â˜ƒxxx = CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 12; ++â˜ƒxxxx) {
         float â˜ƒxxxxx = getSpikeX(â˜ƒxxxx, 0.0F, 0.0F);
         float â˜ƒxxxxxx = getSpikeY(â˜ƒxxxx, 0.0F, 0.0F);
         float â˜ƒxxxxxxx = getSpikeZ(â˜ƒxxxx, 0.0F, 0.0F);
         float â˜ƒxxxxxxxx = (float) Math.PI * SPIKE_X_ROT[â˜ƒxxxx];
         float â˜ƒxxxxxxxxx = (float) Math.PI * SPIKE_Y_ROT[â˜ƒxxxx];
         float â˜ƒxxxxxxxxxx = (float) Math.PI * SPIKE_Z_ROT[â˜ƒxxxx];
         â˜ƒxx.addOrReplaceChild(
            createSpikeName(â˜ƒxxxx), â˜ƒxxx, PartPose.offsetAndRotation(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx)
         );
      }

      â˜ƒxx.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 0.0F, -8.25F));
      PartDefinition â˜ƒxxxx = â˜ƒxx.addOrReplaceChild(
         "tail0", CubeListBuilder.create().texOffs(40, 0).addBox(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F), PartPose.ZERO
      );
      PartDefinition â˜ƒxxxxx = â˜ƒxxxx.addOrReplaceChild(
         "tail1", CubeListBuilder.create().texOffs(0, 54).addBox(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F), PartPose.offset(-1.5F, 0.5F, 14.0F)
      );
      â˜ƒxxxxx.addOrReplaceChild(
         "tail2",
         CubeListBuilder.create().texOffs(41, 32).addBox(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F).texOffs(25, 19).addBox(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F),
         PartPose.offset(0.5F, 0.5F, 6.0F)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }

   public void setupAnim(Guardian var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = â˜ƒ - (float)â˜ƒ.tickCount;
      this.head.yRot = â˜ƒ * (float) (Math.PI / 180.0);
      this.head.xRot = â˜ƒ * (float) (Math.PI / 180.0);
      float â˜ƒx = (1.0F - â˜ƒ.getSpikesAnimation(â˜ƒ)) * 0.55F;
      this.setupSpikes(â˜ƒ, â˜ƒx);
      Entity â˜ƒxx = Minecraft.getInstance().getCameraEntity();
      if (â˜ƒ.hasActiveAttackTarget()) {
         â˜ƒxx = â˜ƒ.getActiveAttackTarget();
      }

      if (â˜ƒxx != null) {
         Vec3 â˜ƒ = â˜ƒxx.getEyePosition(0.0F);
         Vec3 â˜ƒx = â˜ƒ.getEyePosition(0.0F);
         double â˜ƒxx = â˜ƒ.y - â˜ƒx.y;
         if (â˜ƒxx > 0.0) {
            this.eye.y = 0.0F;
         } else {
            this.eye.y = 1.0F;
         }

         Vec3 â˜ƒ = â˜ƒ.getViewVector(0.0F);
         â˜ƒ = new Vec3(â˜ƒ.x, 0.0, â˜ƒ.z);
         Vec3 â˜ƒx = new Vec3(â˜ƒx.x - â˜ƒ.x, 0.0, â˜ƒx.z - â˜ƒ.z).normalize().yRot((float) (Math.PI / 2));
         double â˜ƒxx = â˜ƒ.dot(â˜ƒx);
         this.eye.x = Mth.sqrt((float)Math.abs(â˜ƒxx)) * 2.0F * (float)Math.signum(â˜ƒxx);
      }

      this.eye.visible = true;
      float â˜ƒ = â˜ƒ.getTailAnimation(â˜ƒ);
      this.tailParts[0].yRot = Mth.sin(â˜ƒ) * (float) Math.PI * 0.05F;
      this.tailParts[1].yRot = Mth.sin(â˜ƒ) * (float) Math.PI * 0.1F;
      this.tailParts[2].yRot = Mth.sin(â˜ƒ) * (float) Math.PI * 0.15F;
   }

   private void setupSpikes(float var1, float var2) {
      for(int â˜ƒ = 0; â˜ƒ < 12; ++â˜ƒ) {
         this.spikeParts[â˜ƒ].x = getSpikeX(â˜ƒ, â˜ƒ, â˜ƒ);
         this.spikeParts[â˜ƒ].y = getSpikeY(â˜ƒ, â˜ƒ, â˜ƒ);
         this.spikeParts[â˜ƒ].z = getSpikeZ(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static float getSpikeOffset(int var0, float var1, float var2) {
      return 1.0F + Mth.cos(â˜ƒ * 1.5F + (float)â˜ƒ) * 0.01F - â˜ƒ;
   }

   private static float getSpikeX(int var0, float var1, float var2) {
      return SPIKE_X[â˜ƒ] * getSpikeOffset(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static float getSpikeY(int var0, float var1, float var2) {
      return 16.0F + SPIKE_Y[â˜ƒ] * getSpikeOffset(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static float getSpikeZ(int var0, float var1, float var2) {
      return SPIKE_Z[â˜ƒ] * getSpikeOffset(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
