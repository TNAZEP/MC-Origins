package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.feature.configurations.OceanRuinConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

public class OceanRuinPieces {
   private static final ResourceLocation[] WARM_RUINS = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/warm_1"),
      new ResourceLocation("underwater_ruin/warm_2"),
      new ResourceLocation("underwater_ruin/warm_3"),
      new ResourceLocation("underwater_ruin/warm_4"),
      new ResourceLocation("underwater_ruin/warm_5"),
      new ResourceLocation("underwater_ruin/warm_6"),
      new ResourceLocation("underwater_ruin/warm_7"),
      new ResourceLocation("underwater_ruin/warm_8")
   };
   private static final ResourceLocation[] RUINS_BRICK = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/brick_1"),
      new ResourceLocation("underwater_ruin/brick_2"),
      new ResourceLocation("underwater_ruin/brick_3"),
      new ResourceLocation("underwater_ruin/brick_4"),
      new ResourceLocation("underwater_ruin/brick_5"),
      new ResourceLocation("underwater_ruin/brick_6"),
      new ResourceLocation("underwater_ruin/brick_7"),
      new ResourceLocation("underwater_ruin/brick_8")
   };
   private static final ResourceLocation[] RUINS_CRACKED = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/cracked_1"),
      new ResourceLocation("underwater_ruin/cracked_2"),
      new ResourceLocation("underwater_ruin/cracked_3"),
      new ResourceLocation("underwater_ruin/cracked_4"),
      new ResourceLocation("underwater_ruin/cracked_5"),
      new ResourceLocation("underwater_ruin/cracked_6"),
      new ResourceLocation("underwater_ruin/cracked_7"),
      new ResourceLocation("underwater_ruin/cracked_8")
   };
   private static final ResourceLocation[] RUINS_MOSSY = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/mossy_1"),
      new ResourceLocation("underwater_ruin/mossy_2"),
      new ResourceLocation("underwater_ruin/mossy_3"),
      new ResourceLocation("underwater_ruin/mossy_4"),
      new ResourceLocation("underwater_ruin/mossy_5"),
      new ResourceLocation("underwater_ruin/mossy_6"),
      new ResourceLocation("underwater_ruin/mossy_7"),
      new ResourceLocation("underwater_ruin/mossy_8")
   };
   private static final ResourceLocation[] BIG_RUINS_BRICK = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/big_brick_1"),
      new ResourceLocation("underwater_ruin/big_brick_2"),
      new ResourceLocation("underwater_ruin/big_brick_3"),
      new ResourceLocation("underwater_ruin/big_brick_8")
   };
   private static final ResourceLocation[] BIG_RUINS_MOSSY = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/big_mossy_1"),
      new ResourceLocation("underwater_ruin/big_mossy_2"),
      new ResourceLocation("underwater_ruin/big_mossy_3"),
      new ResourceLocation("underwater_ruin/big_mossy_8")
   };
   private static final ResourceLocation[] BIG_RUINS_CRACKED = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/big_cracked_1"),
      new ResourceLocation("underwater_ruin/big_cracked_2"),
      new ResourceLocation("underwater_ruin/big_cracked_3"),
      new ResourceLocation("underwater_ruin/big_cracked_8")
   };
   private static final ResourceLocation[] BIG_WARM_RUINS = new ResourceLocation[]{
      new ResourceLocation("underwater_ruin/big_warm_4"),
      new ResourceLocation("underwater_ruin/big_warm_5"),
      new ResourceLocation("underwater_ruin/big_warm_6"),
      new ResourceLocation("underwater_ruin/big_warm_7")
   };

   private static ResourceLocation getSmallWarmRuin(Random var0) {
      return Util.getRandom(WARM_RUINS, â˜ƒ);
   }

   private static ResourceLocation getBigWarmRuin(Random var0) {
      return Util.getRandom(BIG_WARM_RUINS, â˜ƒ);
   }

   public static void addPieces(StructureManager var0, BlockPos var1, Rotation var2, StructurePieceAccessor var3, Random var4, OceanRuinConfiguration var5) {
      boolean â˜ƒ = â˜ƒ.nextFloat() <= â˜ƒ.largeProbability;
      float â˜ƒx = â˜ƒ ? 0.9F : 0.8F;
      addPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
      if (â˜ƒ && â˜ƒ.nextFloat() <= â˜ƒ.clusterProbability) {
         addClusterRuins(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static void addClusterRuins(
      StructureManager var0, Random var1, Rotation var2, BlockPos var3, OceanRuinConfiguration var4, StructurePieceAccessor var5
   ) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getX(), 90, â˜ƒ.getZ());
      BlockPos â˜ƒx = StructureTemplate.transform(new BlockPos(15, 0, 15), Mirror.NONE, â˜ƒ, BlockPos.ZERO).offset(â˜ƒ);
      BoundingBox â˜ƒxx = BoundingBox.fromCorners(â˜ƒ, â˜ƒx);
      BlockPos â˜ƒxxx = new BlockPos(Math.min(â˜ƒ.getX(), â˜ƒx.getX()), â˜ƒ.getY(), Math.min(â˜ƒ.getZ(), â˜ƒx.getZ()));
      List<BlockPos> â˜ƒxxxx = allPositions(â˜ƒ, â˜ƒxxx);
      int â˜ƒxxxxx = Mth.nextInt(â˜ƒ, 4, 8);

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxx) {
         if (!â˜ƒxxxx.isEmpty()) {
            int â˜ƒxxxxxxx = â˜ƒ.nextInt(â˜ƒxxxx.size());
            BlockPos â˜ƒxxxxxxxx = (BlockPos)â˜ƒxxxx.remove(â˜ƒxxxxxxx);
            Rotation â˜ƒxxxxxxxxx = Rotation.getRandom(â˜ƒ);
            BlockPos â˜ƒxxxxxxxxxx = StructureTemplate.transform(new BlockPos(5, 0, 6), Mirror.NONE, â˜ƒxxxxxxxxx, BlockPos.ZERO).offset(â˜ƒxxxxxxxx);
            BoundingBox â˜ƒxxxxxxxxxxx = BoundingBox.fromCorners(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx);
            if (!â˜ƒxxxxxxxxxxx.intersects(â˜ƒxx)) {
               addPiece(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, false, 0.8F);
            }
         }
      }
   }

   private static List<BlockPos> allPositions(Random var0, BlockPos var1) {
      List<BlockPos> â˜ƒ = Lists.<BlockPos>newArrayList();
      â˜ƒ.add(â˜ƒ.offset(-16 + Mth.nextInt(â˜ƒ, 1, 8), 0, 16 + Mth.nextInt(â˜ƒ, 1, 7)));
      â˜ƒ.add(â˜ƒ.offset(-16 + Mth.nextInt(â˜ƒ, 1, 8), 0, Mth.nextInt(â˜ƒ, 1, 7)));
      â˜ƒ.add(â˜ƒ.offset(-16 + Mth.nextInt(â˜ƒ, 1, 8), 0, -16 + Mth.nextInt(â˜ƒ, 4, 8)));
      â˜ƒ.add(â˜ƒ.offset(Mth.nextInt(â˜ƒ, 1, 7), 0, 16 + Mth.nextInt(â˜ƒ, 1, 7)));
      â˜ƒ.add(â˜ƒ.offset(Mth.nextInt(â˜ƒ, 1, 7), 0, -16 + Mth.nextInt(â˜ƒ, 4, 6)));
      â˜ƒ.add(â˜ƒ.offset(16 + Mth.nextInt(â˜ƒ, 1, 7), 0, 16 + Mth.nextInt(â˜ƒ, 3, 8)));
      â˜ƒ.add(â˜ƒ.offset(16 + Mth.nextInt(â˜ƒ, 1, 7), 0, Mth.nextInt(â˜ƒ, 1, 7)));
      â˜ƒ.add(â˜ƒ.offset(16 + Mth.nextInt(â˜ƒ, 1, 7), 0, -16 + Mth.nextInt(â˜ƒ, 4, 8)));
      return â˜ƒ;
   }

   private static void addPiece(
      StructureManager var0, BlockPos var1, Rotation var2, StructurePieceAccessor var3, Random var4, OceanRuinConfiguration var5, boolean var6, float var7
   ) {
      switch(â˜ƒ.biomeTemp) {
         case WARM:
         default: {
            ResourceLocation â˜ƒ = â˜ƒ ? getBigWarmRuin(â˜ƒ) : getSmallWarmRuin(â˜ƒ);
            â˜ƒ.addPiece(new OceanRuinPieces.OceanRuinPiece(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.biomeTemp, â˜ƒ));
            break;
         }
         case COLD: {
            ResourceLocation[] â˜ƒ = â˜ƒ ? BIG_RUINS_BRICK : RUINS_BRICK;
            ResourceLocation[] â˜ƒx = â˜ƒ ? BIG_RUINS_CRACKED : RUINS_CRACKED;
            ResourceLocation[] â˜ƒxx = â˜ƒ ? BIG_RUINS_MOSSY : RUINS_MOSSY;
            int â˜ƒxxx = â˜ƒ.nextInt(â˜ƒ.length);
            â˜ƒ.addPiece(new OceanRuinPieces.OceanRuinPiece(â˜ƒ, â˜ƒ[â˜ƒxxx], â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.biomeTemp, â˜ƒ));
            â˜ƒ.addPiece(new OceanRuinPieces.OceanRuinPiece(â˜ƒ, â˜ƒx[â˜ƒxxx], â˜ƒ, â˜ƒ, 0.7F, â˜ƒ.biomeTemp, â˜ƒ));
            â˜ƒ.addPiece(new OceanRuinPieces.OceanRuinPiece(â˜ƒ, â˜ƒxx[â˜ƒxxx], â˜ƒ, â˜ƒ, 0.5F, â˜ƒ.biomeTemp, â˜ƒ));
         }
      }
   }

   public static class OceanRuinPiece extends TemplateStructurePiece {
      private final OceanRuinFeature.Type biomeType;
      private final float integrity;
      private final boolean isLarge;

      public OceanRuinPiece(StructureManager var1, ResourceLocation var2, BlockPos var3, Rotation var4, float var5, OceanRuinFeature.Type var6, boolean var7) {
         super(StructurePieceType.OCEAN_RUIN, 0, â˜ƒ, â˜ƒ, â˜ƒ.toString(), makeSettings(â˜ƒ), â˜ƒ);
         this.integrity = â˜ƒ;
         this.biomeType = â˜ƒ;
         this.isLarge = â˜ƒ;
      }

      public OceanRuinPiece(ServerLevel var1, CompoundTag var2) {
         super(StructurePieceType.OCEAN_RUIN, â˜ƒ, â˜ƒ, var1x -> makeSettings(Rotation.valueOf(â˜ƒ.getString("Rot"))));
         this.integrity = â˜ƒ.getFloat("Integrity");
         this.biomeType = OceanRuinFeature.Type.valueOf(â˜ƒ.getString("BiomeType"));
         this.isLarge = â˜ƒ.getBoolean("IsLarge");
      }

      private static StructurePlaceSettings makeSettings(Rotation var0) {
         return new StructurePlaceSettings().setRotation(â˜ƒ).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
      }

      @Override
      protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
         super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
         â˜ƒ.putString("Rot", this.placeSettings.getRotation().name());
         â˜ƒ.putFloat("Integrity", this.integrity);
         â˜ƒ.putString("BiomeType", this.biomeType.toString());
         â˜ƒ.putBoolean("IsLarge", this.isLarge);
      }

      @Override
      protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
         if ("chest".equals(â˜ƒ)) {
            â˜ƒ.setBlock(â˜ƒ, Blocks.CHEST.defaultBlockState().setValue(ChestBlock.WATERLOGGED, Boolean.valueOf(â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER))), 2);
            BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
            if (â˜ƒ instanceof ChestBlockEntity) {
               ((ChestBlockEntity)â˜ƒ)
                  .setLootTable(this.isLarge ? BuiltInLootTables.UNDERWATER_RUIN_BIG : BuiltInLootTables.UNDERWATER_RUIN_SMALL, â˜ƒ.nextLong());
            }
         } else if ("drowned".equals(â˜ƒ)) {
            Drowned â˜ƒ = EntityType.DROWNED.create(â˜ƒ.getLevel());
            â˜ƒ.setPersistenceRequired();
            â˜ƒ.moveTo(â˜ƒ, 0.0F, 0.0F);
            â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ), MobSpawnType.STRUCTURE, null, null);
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
            if (â˜ƒ.getY() > â˜ƒ.getSeaLevel()) {
               â˜ƒ.setBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), 2);
            } else {
               â˜ƒ.setBlock(â˜ƒ, Blocks.WATER.defaultBlockState(), 2);
            }
         }
      }

      @Override
      public boolean postProcess(
         WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
      ) {
         this.placeSettings.clearProcessors().addProcessor(new BlockRotProcessor(this.integrity)).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
         int â˜ƒ = â˜ƒ.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
         this.templatePosition = new BlockPos(this.templatePosition.getX(), â˜ƒ, this.templatePosition.getZ());
         BlockPos â˜ƒx = StructureTemplate.transform(
               new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1),
               Mirror.NONE,
               this.placeSettings.getRotation(),
               BlockPos.ZERO
            )
            .offset(this.templatePosition);
         this.templatePosition = new BlockPos(this.templatePosition.getX(), this.getHeight(this.templatePosition, â˜ƒ, â˜ƒx), this.templatePosition.getZ());
         return super.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private int getHeight(BlockPos var1, BlockGetter var2, BlockPos var3) {
         int â˜ƒ = â˜ƒ.getY();
         int â˜ƒx = 512;
         int â˜ƒxx = â˜ƒ - 1;
         int â˜ƒxxx = 0;

         for(BlockPos â˜ƒxxxx : BlockPos.betweenClosed(â˜ƒ, â˜ƒ)) {
            int â˜ƒxxxxx = â˜ƒxxxx.getX();
            int â˜ƒxxxxxx = â˜ƒxxxx.getZ();
            int â˜ƒxxxxxxx = â˜ƒ.getY() - 1;
            BlockPos.MutableBlockPos â˜ƒxxxxxxxx = new BlockPos.MutableBlockPos(â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxx);
            BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);

            for(FluidState â˜ƒxxxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxxxx);
               (â˜ƒxxxxxxxxx.isAir() || â˜ƒxxxxxxxxxx.is(FluidTags.WATER) || â˜ƒxxxxxxxxx.is(BlockTags.ICE)) && â˜ƒxxxxxxx > â˜ƒ.getMinBuildHeight() + 1;
               â˜ƒxxxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxxxx)
            ) {
               â˜ƒxxxxxxxx.set(â˜ƒxxxxx, --â˜ƒxxxxxxx, â˜ƒxxxxxx);
               â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxx);
            }

            â˜ƒx = Math.min(â˜ƒx, â˜ƒxxxxxxx);
            if (â˜ƒxxxxxxx < â˜ƒxx - 2) {
               ++â˜ƒxxx;
            }
         }

         int â˜ƒxxxx = Math.abs(â˜ƒ.getX() - â˜ƒ.getX());
         if (â˜ƒxx - â˜ƒx > 2 && â˜ƒxxx > â˜ƒxxxx - 2) {
            â˜ƒ = â˜ƒx + 1;
         }

         return â˜ƒ;
      }
   }
}
