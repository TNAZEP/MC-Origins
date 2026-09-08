package net.minecraft.world.level.levelgen.structure;

import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class ShipwreckPieces {
   static final BlockPos PIVOT = new BlockPos(4, 0, 15);
   private static final ResourceLocation[] STRUCTURE_LOCATION_BEACHED = new ResourceLocation[]{
      new ResourceLocation("shipwreck/with_mast"),
      new ResourceLocation("shipwreck/sideways_full"),
      new ResourceLocation("shipwreck/sideways_fronthalf"),
      new ResourceLocation("shipwreck/sideways_backhalf"),
      new ResourceLocation("shipwreck/rightsideup_full"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf"),
      new ResourceLocation("shipwreck/rightsideup_backhalf"),
      new ResourceLocation("shipwreck/with_mast_degraded"),
      new ResourceLocation("shipwreck/rightsideup_full_degraded"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"),
      new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")
   };
   private static final ResourceLocation[] STRUCTURE_LOCATION_OCEAN = new ResourceLocation[]{
      new ResourceLocation("shipwreck/with_mast"),
      new ResourceLocation("shipwreck/upsidedown_full"),
      new ResourceLocation("shipwreck/upsidedown_fronthalf"),
      new ResourceLocation("shipwreck/upsidedown_backhalf"),
      new ResourceLocation("shipwreck/sideways_full"),
      new ResourceLocation("shipwreck/sideways_fronthalf"),
      new ResourceLocation("shipwreck/sideways_backhalf"),
      new ResourceLocation("shipwreck/rightsideup_full"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf"),
      new ResourceLocation("shipwreck/rightsideup_backhalf"),
      new ResourceLocation("shipwreck/with_mast_degraded"),
      new ResourceLocation("shipwreck/upsidedown_full_degraded"),
      new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"),
      new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"),
      new ResourceLocation("shipwreck/sideways_full_degraded"),
      new ResourceLocation("shipwreck/sideways_fronthalf_degraded"),
      new ResourceLocation("shipwreck/sideways_backhalf_degraded"),
      new ResourceLocation("shipwreck/rightsideup_full_degraded"),
      new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"),
      new ResourceLocation("shipwreck/rightsideup_backhalf_degraded")
   };

   public static void addPieces(StructureManager var0, BlockPos var1, Rotation var2, StructurePieceAccessor var3, Random var4, ShipwreckConfiguration var5) {
      ResourceLocation â˜ƒ = Util.getRandom(â˜ƒ.isBeached ? STRUCTURE_LOCATION_BEACHED : STRUCTURE_LOCATION_OCEAN, â˜ƒ);
      â˜ƒ.addPiece(new ShipwreckPieces.ShipwreckPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.isBeached));
   }

   public static class ShipwreckPiece extends TemplateStructurePiece {
      private final boolean isBeached;

      public ShipwreckPiece(StructureManager var1, ResourceLocation var2, BlockPos var3, Rotation var4, boolean var5) {
         super(StructurePieceType.SHIPWRECK_PIECE, 0, â˜ƒ, â˜ƒ, â˜ƒ.toString(), makeSettings(â˜ƒ), â˜ƒ);
         this.isBeached = â˜ƒ;
      }

      public ShipwreckPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.SHIPWRECK_PIECE, â˜ƒ, â˜ƒ, var1x -> makeSettings(Rotation.valueOf(â˜ƒ.getString("Rot"))));
         this.isBeached = â˜ƒ.getBoolean("isBeached");
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putBoolean("isBeached", this.isBeached);
         â˜ƒ.putString("Rot", this.placeSettings.getRotation().name());
      }

      private static StructurePlaceSettings makeSettings(Rotation var0) {
         return new StructurePlaceSettings()
            .setRotation(â˜ƒ)
            .setMirror(Mirror.NONE)
            .setRotationPivot(ShipwreckPieces.PIVOT)
            .addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
      }

      @Override
      protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
         if ("map_chest".equals(â˜ƒ)) {
            RandomizableContainerBlockEntity.setLootTable(â˜ƒ, â˜ƒ, â˜ƒ.below(), BuiltInLootTables.SHIPWRECK_MAP);
         } else if ("treasure_chest".equals(â˜ƒ)) {
            RandomizableContainerBlockEntity.setLootTable(â˜ƒ, â˜ƒ, â˜ƒ.below(), BuiltInLootTables.SHIPWRECK_TREASURE);
         } else if ("supply_chest".equals(â˜ƒ)) {
            RandomizableContainerBlockEntity.setLootTable(â˜ƒ, â˜ƒ, â˜ƒ.below(), BuiltInLootTables.SHIPWRECK_SUPPLY);
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         int â˜ƒ = â˜ƒ.getMaxBuildHeight();
         int â˜ƒx = 0;
         Vec3i â˜ƒxx = this.template.getSize();
         Heightmap.Types â˜ƒxxx = this.isBeached ? Heightmap.Types.WORLD_SURFACE_WG : Heightmap.Types.OCEAN_FLOOR_WG;
         int â˜ƒxxxx = â˜ƒxx.getX() * â˜ƒxx.getZ();
         if (â˜ƒxxxx == 0) {
            â˜ƒx = â˜ƒ.getHeight(â˜ƒxxx, this.templatePosition.getX(), this.templatePosition.getZ());
         } else {
            BlockPos â˜ƒ = this.templatePosition.offset(â˜ƒxx.getX() - 1, 0, â˜ƒxx.getZ() - 1);

            for(BlockPos â˜ƒx : BlockPos.betweenClosed(this.templatePosition, â˜ƒ)) {
               int â˜ƒxx = â˜ƒ.getHeight(â˜ƒxxx, â˜ƒx.getX(), â˜ƒx.getZ());
               â˜ƒx += â˜ƒxx;
               â˜ƒ = Math.min(â˜ƒ, â˜ƒxx);
            }

            â˜ƒx /= â˜ƒxxxx;
         }

         int â˜ƒ = this.isBeached ? â˜ƒ - â˜ƒxx.getY() / 2 - â˜ƒ.nextInt(3) : â˜ƒx;
         this.templatePosition = new BlockPos(this.templatePosition.getX(), â˜ƒ, this.templatePosition.getZ());
         return super.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
