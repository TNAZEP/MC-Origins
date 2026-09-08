package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class IglooPieces {
   public static final int GENERATION_HEIGHT = 90;
   static final ResourceLocation STRUCTURE_LOCATION_IGLOO = new ResourceLocation("igloo/top");
   private static final ResourceLocation STRUCTURE_LOCATION_LADDER = new ResourceLocation("igloo/middle");
   private static final ResourceLocation STRUCTURE_LOCATION_LABORATORY = new ResourceLocation("igloo/bottom");
   static final Map<ResourceLocation, BlockPos> PIVOTS = ImmutableMap.of(
      STRUCTURE_LOCATION_IGLOO, new BlockPos(3, 5, 5), STRUCTURE_LOCATION_LADDER, new BlockPos(1, 3, 1), STRUCTURE_LOCATION_LABORATORY, new BlockPos(3, 6, 7)
   );
   static final Map<ResourceLocation, BlockPos> OFFSETS = ImmutableMap.of(
      STRUCTURE_LOCATION_IGLOO, BlockPos.ZERO, STRUCTURE_LOCATION_LADDER, new BlockPos(2, -3, 4), STRUCTURE_LOCATION_LABORATORY, new BlockPos(0, -3, -2)
   );

   public static void addPieces(StructureManager var0, BlockPos var1, Rotation var2, StructurePieceAccessor var3, Random var4) {
      if (â˜ƒ.nextDouble() < 0.5) {
         int â˜ƒ = â˜ƒ.nextInt(8) + 4;
         â˜ƒ.addPiece(new IglooPieces.IglooPiece(â˜ƒ, STRUCTURE_LOCATION_LABORATORY, â˜ƒ, â˜ƒ, â˜ƒ * 3));

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ - 1; ++â˜ƒx) {
            â˜ƒ.addPiece(new IglooPieces.IglooPiece(â˜ƒ, STRUCTURE_LOCATION_LADDER, â˜ƒ, â˜ƒ, â˜ƒx * 3));
         }
      }

      â˜ƒ.addPiece(new IglooPieces.IglooPiece(â˜ƒ, STRUCTURE_LOCATION_IGLOO, â˜ƒ, â˜ƒ, 0));
   }

   public static class IglooPiece extends TemplateStructurePiece {
      public IglooPiece(StructureManager var1, ResourceLocation var2, BlockPos var3, Rotation var4, int var5) {
         super(StructurePieceType.IGLOO, 0, â˜ƒ, â˜ƒ, â˜ƒ.toString(), makeSettings(â˜ƒ, â˜ƒ), makePosition(â˜ƒ, â˜ƒ, â˜ƒ));
      }

      public IglooPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.IGLOO, â˜ƒ, â˜ƒ, var1x -> makeSettings(Rotation.valueOf(â˜ƒ.getString("Rot")), var1x));
      }

      private static StructurePlaceSettings makeSettings(Rotation var0, ResourceLocation var1) {
         return new StructurePlaceSettings()
            .setRotation(â˜ƒ)
            .setMirror(Mirror.NONE)
            .setRotationPivot((BlockPos)IglooPieces.PIVOTS.get(â˜ƒ))
            .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
      }

      private static BlockPos makePosition(ResourceLocation var0, BlockPos var1, int var2) {
         return â˜ƒ.offset((Vec3i)IglooPieces.OFFSETS.get(â˜ƒ)).below(â˜ƒ);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putString("Rot", this.placeSettings.getRotation().name());
      }

      @Override
      protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
         if ("chest".equals(â˜ƒ)) {
            â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 3);
            BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ.below());
            if (â˜ƒ instanceof ChestBlockEntity) {
               ((ChestBlockEntity)â˜ƒ).setLootTable(BuiltInLootTables.IGLOO_CHEST, â˜ƒ.nextLong());
            }
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         ResourceLocation â˜ƒ = new ResourceLocation(this.templateName);
         StructurePlaceSettings â˜ƒx = makeSettings(this.placeSettings.getRotation(), â˜ƒ);
         BlockPos â˜ƒxx = (BlockPos)IglooPieces.OFFSETS.get(â˜ƒ);
         BlockPos â˜ƒxxx = this.templatePosition.offset(StructureTemplate.calculateRelativePosition(â˜ƒx, new BlockPos(3 - â˜ƒxx.getX(), 0, -â˜ƒxx.getZ())));
         int â˜ƒxxxx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE_WG, â˜ƒxxx.getX(), â˜ƒxxx.getZ());
         BlockPos â˜ƒxxxxx = this.templatePosition;
         this.templatePosition = this.templatePosition.offset(0, â˜ƒxxxx - 90 - 1, 0);
         boolean â˜ƒxxxxxx = super.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.equals(IglooPieces.STRUCTURE_LOCATION_IGLOO)) {
            BlockPos â˜ƒxxxxxxx = this.templatePosition.offset(StructureTemplate.calculateRelativePosition(â˜ƒx, new BlockPos(3, 0, 5)));
            BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxx.below());
            if (!â˜ƒxxxxxxxx.isAir() && !â˜ƒxxxxxxxx.is(Blocks.LADDER)) {
               â˜ƒ.setBlock(â˜ƒxxxxxxx, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
            }
         }

         this.templatePosition = â˜ƒxxxxx;
         return â˜ƒxxxxxx;
      }
   }
}
