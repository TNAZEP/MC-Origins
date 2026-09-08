package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public class BedRenderer implements BlockEntityRenderer<BedBlockEntity> {
   private final ModelPart headRoot;
   private final ModelPart footRoot;

   public BedRenderer(BlockEntityRendererProvider.Context var1) {
      this.headRoot = â˜ƒ.bakeLayer(ModelLayers.BED_HEAD);
      this.footRoot = â˜ƒ.bakeLayer(ModelLayers.BED_FOOT);
   }

   public static LayerDefinition createHeadLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(50, 6).addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F),
         PartPose.rotation((float) (Math.PI / 2), 0.0F, (float) (Math.PI / 2))
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(50, 18).addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F),
         PartPose.rotation((float) (Math.PI / 2), 0.0F, (float) Math.PI)
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public static LayerDefinition createFootLayer() {
      MeshDefinition â˜ƒ = new MeshDefinition();
      PartDefinition â˜ƒx = â˜ƒ.getRoot();
      â˜ƒx.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), PartPose.ZERO);
      â˜ƒx.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), PartPose.rotation((float) (Math.PI / 2), 0.0F, 0.0F)
      );
      â˜ƒx.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(50, 12).addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F),
         PartPose.rotation((float) (Math.PI / 2), 0.0F, (float) (Math.PI * 3.0 / 2.0))
      );
      return LayerDefinition.create(â˜ƒ, 64, 64);
   }

   public void render(BedBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      Material â˜ƒ = Sheets.BED_TEXTURES[â˜ƒ.getColor().getId()];
      Level â˜ƒx = â˜ƒ.getLevel();
      if (â˜ƒx != null) {
         BlockState â˜ƒxx = â˜ƒ.getBlockState();
         DoubleBlockCombiner.NeighborCombineResult<? extends BedBlockEntity> â˜ƒxxx = DoubleBlockCombiner.combineWithNeigbour(
            BlockEntityType.BED,
            BedBlock::getBlockType,
            BedBlock::getConnectedDirection,
            ChestBlock.FACING,
            â˜ƒxx,
            â˜ƒx,
            â˜ƒ.getBlockPos(),
            (var0, var1x) -> false
         );
         int â˜ƒxxxx = â˜ƒxxx.<Int2IntFunction>apply(new BrightnessCombiner<>()).get(â˜ƒ);
         this.renderPiece(
            â˜ƒ, â˜ƒ, â˜ƒxx.getValue(BedBlock.PART) == BedPart.HEAD ? this.headRoot : this.footRoot, â˜ƒxx.getValue(BedBlock.FACING), â˜ƒ, â˜ƒxxxx, â˜ƒ, false
         );
      } else {
         this.renderPiece(â˜ƒ, â˜ƒ, this.headRoot, Direction.SOUTH, â˜ƒ, â˜ƒ, â˜ƒ, false);
         this.renderPiece(â˜ƒ, â˜ƒ, this.footRoot, Direction.SOUTH, â˜ƒ, â˜ƒ, â˜ƒ, true);
      }
   }

   private void renderPiece(PoseStack var1, MultiBufferSource var2, ModelPart var3, Direction var4, Material var5, int var6, int var7, boolean var8) {
      â˜ƒ.pushPose();
      â˜ƒ.translate(0.0, 0.5625, â˜ƒ ? -1.0 : 0.0);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
      â˜ƒ.translate(0.5, 0.5, 0.5);
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F + â˜ƒ.toYRot()));
      â˜ƒ.translate(-0.5, -0.5, -0.5);
      VertexConsumer â˜ƒ = â˜ƒ.buffer(â˜ƒ, RenderType::entitySolid);
      â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.popPose();
   }
}
