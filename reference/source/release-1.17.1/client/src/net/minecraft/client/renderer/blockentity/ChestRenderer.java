package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.Calendar;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;

public class ChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
   private static final String BOTTOM = "bottom";
   private static final String LID = "lid";
   private static final String LOCK = "lock";
   private final ModelPart lid;
   private final ModelPart bottom;
   private final ModelPart lock;
   private final ModelPart doubleLeftLid;
   private final ModelPart doubleLeftBottom;
   private final ModelPart doubleLeftLock;
   private final ModelPart doubleRightLid;
   private final ModelPart doubleRightBottom;
   private final ModelPart doubleRightLock;
   private boolean xmasTextures;

   public ChestRenderer(BlockEntityRendererProvider.Context var1) {
      Calendar â˜ƒ = Calendar.getInstance();
      if (â˜ƒ.get(2) + 1 == 12 && â˜ƒ.get(5) >= 24 && â˜ƒ.get(5) <= 26) {
         this.xmasTextures = true;
      }

      ModelPart â˜ƒ = â˜ƒ.bakeLayer(ModelLayers.CHEST);
      this.bottom = â˜ƒ.getChild("bottom");
      this.lid = â˜ƒ.getChild("lid");
      this.lock = â˜ƒ.getChild("lock");
      ModelPart â˜ƒx = â˜ƒ.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
      this.doubleLeftBottom = â˜ƒx.getChild("bottom");
      this.doubleLeftLid = â˜ƒx.getChild("lid");
      this.doubleLeftLock = â˜ƒx.getChild("lock");
      ModelPart â˜ƒxx = â˜ƒ.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
      this.doubleRightBottom = â˜ƒxx.getChild("bottom");
      this.doubleRightLid = â˜ƒxx.getChild("lid");
      this.doubleRightLock = â˜ƒxx.getChild("lock");
   }

   public static LayerDefinition createSingleBodyLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public static LayerDefinition createDoubleBodyRightLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0).addBox(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public static LayerDefinition createDoubleBodyLeftLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
      â˜ƒx.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   @Override
   public void render(T var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Level â˜ƒ = â˜ƒ.getLevel();
      boolean â˜ƒx = â˜ƒ != null;
      BlockState â˜ƒxx = â˜ƒx ? â˜ƒ.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
      ChestType â˜ƒxxx = â˜ƒxx.hasProperty(ChestBlock.TYPE) ? â˜ƒxx.getValue(ChestBlock.TYPE) : ChestType.SINGLE;
      Block â˜ƒxxxx = â˜ƒxx.getBlock();
      if (â˜ƒxxxx instanceof AbstractChestBlock) {
         AbstractChestBlock<?> â˜ƒxxxxxx = (AbstractChestBlock)â˜ƒxxxx;
         boolean â˜ƒxxxxxxx = â˜ƒxxx != ChestType.SINGLE;
         â˜ƒ.pushPose();
         float â˜ƒxxxxxxxx = ((Direction)â˜ƒxx.getValue(ChestBlock.FACING)).toYRot();
         â˜ƒ.translate(0.5, 0.5, 0.5);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-â˜ƒxxxxxxxx));
         â˜ƒ.translate(-0.5, -0.5, -0.5);
         DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> â˜ƒxxxxx;
         if (â˜ƒx) {
            â˜ƒxxxxx = â˜ƒxxxxxx.combine(â˜ƒxx, â˜ƒ, â˜ƒ.getBlockPos(), true);
         } else {
            â˜ƒxxxxx = DoubleBlockCombiner.Combiner::acceptNone;
         }

         float â˜ƒxxxxx = â˜ƒxxxxx.<Float2FloatFunction>apply(ChestBlock.opennessCombiner(â˜ƒ)).get(â˜ƒ);
         â˜ƒxxxxx = 1.0F - â˜ƒxxxxx;
         â˜ƒxxxxx = 1.0F - â˜ƒxxxxx * â˜ƒxxxxx * â˜ƒxxxxx;
         int â˜ƒxxxxxx = â˜ƒxxxxx.<Int2IntFunction>apply(new BrightnessCombiner<>()).applyAsInt(â˜ƒ);
         Material â˜ƒxxxxxxx = Sheets.chooseMaterial(â˜ƒ, â˜ƒxxx, this.xmasTextures);
         VertexConsumer â˜ƒxxxxxxxx = â˜ƒxxxxxxx.buffer(â˜ƒ, RenderType::entityCutout);
         if (â˜ƒxxxxxxx) {
            if (â˜ƒxxx == ChestType.LEFT) {
               this.render(â˜ƒ, â˜ƒxxxxxxxx, this.doubleLeftLid, this.doubleLeftLock, this.doubleLeftBottom, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ);
            } else {
               this.render(â˜ƒ, â˜ƒxxxxxxxx, this.doubleRightLid, this.doubleRightLock, this.doubleRightBottom, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ);
            }
         } else {
            this.render(â˜ƒ, â˜ƒxxxxxxxx, this.lid, this.lock, this.bottom, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒ);
         }

         â˜ƒ.popPose();
      }
   }

   private void render(PoseStack var1, VertexConsumer var2, ModelPart var3, ModelPart var4, ModelPart var5, float var6, int var7, int var8) {
      â˜ƒ.xRot = -(â˜ƒ * (float) (Math.PI / 2));
      â˜ƒ.xRot = â˜ƒ.xRot;
      â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
