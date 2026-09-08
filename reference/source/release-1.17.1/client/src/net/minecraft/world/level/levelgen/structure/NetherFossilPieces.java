package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class NetherFossilPieces {
   private static final ResourceLocation[] FOSSILS = new ResourceLocation[]{
      new ResourceLocation("nether_fossils/fossil_1"),
      new ResourceLocation("nether_fossils/fossil_2"),
      new ResourceLocation("nether_fossils/fossil_3"),
      new ResourceLocation("nether_fossils/fossil_4"),
      new ResourceLocation("nether_fossils/fossil_5"),
      new ResourceLocation("nether_fossils/fossil_6"),
      new ResourceLocation("nether_fossils/fossil_7"),
      new ResourceLocation("nether_fossils/fossil_8"),
      new ResourceLocation("nether_fossils/fossil_9"),
      new ResourceLocation("nether_fossils/fossil_10"),
      new ResourceLocation("nether_fossils/fossil_11"),
      new ResourceLocation("nether_fossils/fossil_12"),
      new ResourceLocation("nether_fossils/fossil_13"),
      new ResourceLocation("nether_fossils/fossil_14")
   };

   public static void addPieces(StructureManager var0, StructurePieceAccessor var1, Random var2, BlockPos var3) {
      Rotation â˜ƒ = Rotation.getRandom(â˜ƒ);
      â˜ƒ.addPiece(new NetherFossilPieces.NetherFossilPiece(â˜ƒ, Util.getRandom(FOSSILS, â˜ƒ), â˜ƒ, â˜ƒ));
   }

   public static class NetherFossilPiece extends TemplateStructurePiece {
      public NetherFossilPiece(StructureManager var1, ResourceLocation var2, BlockPos var3, Rotation var4) {
         super(StructurePieceType.NETHER_FOSSIL, 0, â˜ƒ, â˜ƒ, â˜ƒ.toString(), makeSettings(â˜ƒ), â˜ƒ);
      }

      public NetherFossilPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.NETHER_FOSSIL, â˜ƒ, â˜ƒ, var1x -> makeSettings(Rotation.valueOf(â˜ƒ.getString("Rot"))));
      }

      private static StructurePlaceSettings makeSettings(Rotation var0) {
         return new StructurePlaceSettings().setRotation(â˜ƒ).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putString("Rot", this.placeSettings.getRotation().name());
      }

      @Override
      protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         â˜ƒ.encapsulate(this.template.getBoundingBox(this.placeSettings, this.templatePosition));
         return super.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
