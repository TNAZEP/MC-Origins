package net.minecraft.world.level.block.entity;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.SinglePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class JigsawBlockEntity extends BlockEntity {
   public static final String TARGET = "target";
   public static final String POOL = "pool";
   public static final String JOINT = "joint";
   public static final String NAME = "name";
   public static final String FINAL_STATE = "final_state";
   private ResourceLocation name = new ResourceLocation("empty");
   private ResourceLocation target = new ResourceLocation("empty");
   private ResourceLocation pool = new ResourceLocation("empty");
   private JigsawBlockEntity.JointType joint = JigsawBlockEntity.JointType.ROLLABLE;
   private String finalState = "minecraft:air";

   public JigsawBlockEntity(BlockPos var1, BlockState var2) {
      super(BlockEntityType.JIGSAW, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getName() {
      return this.name;
   }

   public ResourceLocation getTarget() {
      return this.target;
   }

   public ResourceLocation getPool() {
      return this.pool;
   }

   public String getFinalState() {
      return this.finalState;
   }

   public JigsawBlockEntity.JointType getJoint() {
      return this.joint;
   }

   public void setName(ResourceLocation var1) {
      this.name = â˜ƒ;
   }

   public void setTarget(ResourceLocation var1) {
      this.target = â˜ƒ;
   }

   public void setPool(ResourceLocation var1) {
      this.pool = â˜ƒ;
   }

   public void setFinalState(String var1) {
      this.finalState = â˜ƒ;
   }

   public void setJoint(JigsawBlockEntity.JointType var1) {
      this.joint = â˜ƒ;
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      super.save(â˜ƒ);
      â˜ƒ.putString("name", this.name.toString());
      â˜ƒ.putString("target", this.target.toString());
      â˜ƒ.putString("pool", this.pool.toString());
      â˜ƒ.putString("final_state", this.finalState);
      â˜ƒ.putString("joint", this.joint.getSerializedName());
      return â˜ƒ;
   }

   @Override
   public void load(CompoundTag var1) {
      super.load(â˜ƒ);
      this.name = new ResourceLocation(â˜ƒ.getString("name"));
      this.target = new ResourceLocation(â˜ƒ.getString("target"));
      this.pool = new ResourceLocation(â˜ƒ.getString("pool"));
      this.finalState = â˜ƒ.getString("final_state");
      this.joint = (JigsawBlockEntity.JointType)JigsawBlockEntity.JointType.byName(â˜ƒ.getString("joint"))
         .orElseGet(
            () -> JigsawBlock.getFrontFacing(this.getBlockState()).getAxis().isHorizontal()
                  ? JigsawBlockEntity.JointType.ALIGNED
                  : JigsawBlockEntity.JointType.ROLLABLE
         );
   }

   @Nullable
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return new ClientboundBlockEntityDataPacket(this.worldPosition, 12, this.getUpdateTag());
   }

   @Override
   public CompoundTag getUpdateTag() {
      return this.save(new CompoundTag());
   }

   public void generate(ServerLevel var1, int var2, boolean var3) {
      ChunkGenerator â˜ƒ = â˜ƒ.getChunkSource().getGenerator();
      StructureManager â˜ƒx = â˜ƒ.getStructureManager();
      StructureFeatureManager â˜ƒxx = â˜ƒ.structureFeatureManager();
      Random â˜ƒxxx = â˜ƒ.getRandom();
      BlockPos â˜ƒxxxx = this.getBlockPos();
      List<PoolElementStructurePiece> â˜ƒxxxxx = Lists.<PoolElementStructurePiece>newArrayList();
      StructureTemplate â˜ƒxxxxxx = new StructureTemplate();
      â˜ƒxxxxxx.fillFromWorld(â˜ƒ, â˜ƒxxxx, new Vec3i(1, 1, 1), false, null);
      StructurePoolElement â˜ƒxxxxxxx = new SinglePoolElement(â˜ƒxxxxxx);
      PoolElementStructurePiece â˜ƒxxxxxxxx = new PoolElementStructurePiece(â˜ƒx, â˜ƒxxxxxxx, â˜ƒxxxx, 1, Rotation.NONE, new BoundingBox(â˜ƒxxxx));
      JigsawPlacement.addPieces(â˜ƒ.registryAccess(), â˜ƒxxxxxxxx, â˜ƒ, PoolElementStructurePiece::new, â˜ƒ, â˜ƒx, â˜ƒxxxxx, â˜ƒxxx, â˜ƒ);

      for(PoolElementStructurePiece â˜ƒxxxxxxxxx : â˜ƒxxxxx) {
         â˜ƒxxxxxxxxx.place(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx, BoundingBox.infinite(), â˜ƒxxxx, â˜ƒ);
      }
   }

   public static enum JointType implements StringRepresentable {
      ROLLABLE("rollable"),
      ALIGNED("aligned");

      private final String name;

      private JointType(String var3) {
         this.name = â˜ƒ;
      }

      @Override
      public String getSerializedName() {
         return this.name;
      }

      public static Optional<JigsawBlockEntity.JointType> byName(String var0) {
         return Arrays.stream(values()).filter(var1 -> var1.getSerializedName().equals(â˜ƒ)).findFirst();
      }

      public Component getTranslatedName() {
         return new TranslatableComponent("jigsaw_block.joint." + this.name);
      }
   }
}
