package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.StructureMode;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureUtils {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final String DEFAULT_TEST_STRUCTURES_DIR = "gameteststructures";
   public static String testStructuresDir = "gameteststructures";
   private static final int HOW_MANY_CHUNKS_TO_LOAD_IN_EACH_DIRECTION_OF_STRUCTURE = 4;

   public static Rotation getRotationForRotationSteps(int var0) {
      switch(â˜ƒ) {
         case 0:
            return Rotation.NONE;
         case 1:
            return Rotation.CLOCKWISE_90;
         case 2:
            return Rotation.CLOCKWISE_180;
         case 3:
            return Rotation.COUNTERCLOCKWISE_90;
         default:
            throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + â˜ƒ);
      }
   }

   public static int getRotationStepsForRotation(Rotation var0) {
      switch(â˜ƒ) {
         case NONE:
            return 0;
         case CLOCKWISE_90:
            return 1;
         case CLOCKWISE_180:
            return 2;
         case COUNTERCLOCKWISE_90:
            return 3;
         default:
            throw new IllegalArgumentException("Unknown rotation value, don't know how many steps it represents: " + â˜ƒ);
      }
   }

   public static void main(String[] var0) throws IOException {
      Bootstrap.bootStrap();
      Files.walk(Paths.get(testStructuresDir)).filter(var0x -> var0x.toString().endsWith(".snbt")).forEach(var0x -> {
         try {
            String â˜ƒ = new String(Files.readAllBytes(var0x), StandardCharsets.UTF_8);
            CompoundTag â˜ƒx = NbtUtils.snbtToStructure(â˜ƒ);
            CompoundTag â˜ƒxx = StructureUpdater.update(var0x.toString(), â˜ƒx);
            NbtToSnbt.writeSnbt(var0x, NbtUtils.structureToSnbt(â˜ƒxx));
         } catch (IOException | CommandSyntaxException var4) {
            LOGGER.error("Something went wrong upgrading: {}", var0x, var4);
         }
      });
   }

   public static AABB getStructureBounds(StructureBlockEntity var0) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ.getStructureSize().offset(-1, -1, -1));
      BlockPos â˜ƒxx = StructureTemplate.transform(â˜ƒx, Mirror.NONE, â˜ƒ.getRotation(), â˜ƒ);
      return new AABB(â˜ƒ, â˜ƒxx);
   }

   public static BoundingBox getStructureBoundingBox(StructureBlockEntity var0) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ.getStructureSize().offset(-1, -1, -1));
      BlockPos â˜ƒxx = StructureTemplate.transform(â˜ƒx, Mirror.NONE, â˜ƒ.getRotation(), â˜ƒ);
      return BoundingBox.fromCorners(â˜ƒ, â˜ƒxx);
   }

   public static void addCommandBlockAndButtonToStartTest(BlockPos var0, BlockPos var1, Rotation var2, ServerLevel var3) {
      BlockPos â˜ƒ = StructureTemplate.transform(â˜ƒ.offset(â˜ƒ), Mirror.NONE, â˜ƒ, â˜ƒ);
      â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.COMMAND_BLOCK.defaultBlockState());
      CommandBlockEntity â˜ƒx = (CommandBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ);
      â˜ƒx.getCommandBlock().setCommand("test runthis");
      BlockPos â˜ƒxx = StructureTemplate.transform(â˜ƒ.offset(0, 0, -1), Mirror.NONE, â˜ƒ, â˜ƒ);
      â˜ƒ.setBlockAndUpdate(â˜ƒxx, Blocks.STONE_BUTTON.defaultBlockState().rotate(â˜ƒ));
   }

   public static void createNewEmptyStructureBlock(String var0, BlockPos var1, Vec3i var2, Rotation var3, ServerLevel var4) {
      BoundingBox â˜ƒ = getStructureBoundingBox(â˜ƒ, â˜ƒ, â˜ƒ);
      clearSpaceForStructure(â˜ƒ, â˜ƒ.getY(), â˜ƒ);
      â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.STRUCTURE_BLOCK.defaultBlockState());
      StructureBlockEntity â˜ƒx = (StructureBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ);
      â˜ƒx.setIgnoreEntities(false);
      â˜ƒx.setStructureName(new ResourceLocation(â˜ƒ));
      â˜ƒx.setStructureSize(â˜ƒ);
      â˜ƒx.setMode(StructureMode.SAVE);
      â˜ƒx.setShowBoundingBox(true);
   }

   public static StructureBlockEntity spawnStructure(String var0, BlockPos var1, Rotation var2, int var3, ServerLevel var4, boolean var5) {
      Vec3i â˜ƒx = getStructureTemplate(â˜ƒ, â˜ƒ).getSize();
      BoundingBox â˜ƒxx = getStructureBoundingBox(â˜ƒ, â˜ƒx, â˜ƒ);
      BlockPos â˜ƒ;
      if (â˜ƒ == Rotation.NONE) {
         â˜ƒ = â˜ƒ;
      } else if (â˜ƒ == Rotation.CLOCKWISE_90) {
         â˜ƒ = â˜ƒ.offset(â˜ƒx.getZ() - 1, 0, 0);
      } else if (â˜ƒ == Rotation.CLOCKWISE_180) {
         â˜ƒ = â˜ƒ.offset(â˜ƒx.getX() - 1, 0, â˜ƒx.getZ() - 1);
      } else {
         if (â˜ƒ != Rotation.COUNTERCLOCKWISE_90) {
            throw new IllegalArgumentException("Invalid rotation: " + â˜ƒ);
         }

         â˜ƒ = â˜ƒ.offset(0, 0, â˜ƒx.getX() - 1);
      }

      forceLoadChunks(â˜ƒ, â˜ƒ);
      clearSpaceForStructure(â˜ƒxx, â˜ƒ.getY(), â˜ƒ);
      StructureBlockEntity â˜ƒ = createStructureBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getBlockTicks().fetchTicksInArea(â˜ƒxx, true, false);
      â˜ƒ.clearBlockEvents(â˜ƒxx);
      return â˜ƒ;
   }

   private static void forceLoadChunks(BlockPos var0, ServerLevel var1) {
      ChunkPos â˜ƒ = new ChunkPos(â˜ƒ);

      for(int â˜ƒx = -1; â˜ƒx < 4; ++â˜ƒx) {
         for(int â˜ƒxx = -1; â˜ƒxx < 4; ++â˜ƒxx) {
            int â˜ƒxxx = â˜ƒ.x + â˜ƒx;
            int â˜ƒxxxx = â˜ƒ.z + â˜ƒxx;
            â˜ƒ.setChunkForced(â˜ƒxxx, â˜ƒxxxx, true);
         }
      }
   }

   public static void clearSpaceForStructure(BoundingBox var0, int var1, ServerLevel var2) {
      BoundingBox â˜ƒ = new BoundingBox(â˜ƒ.minX() - 2, â˜ƒ.minY() - 3, â˜ƒ.minZ() - 3, â˜ƒ.maxX() + 3, â˜ƒ.maxY() + 20, â˜ƒ.maxZ() + 3);
      BlockPos.betweenClosedStream(â˜ƒ).forEach(var2x -> clearBlock(â˜ƒ, var2x, â˜ƒ));
      â˜ƒ.getBlockTicks().fetchTicksInArea(â˜ƒ, true, false);
      â˜ƒ.clearBlockEvents(â˜ƒ);
      AABB â˜ƒx = new AABB((double)â˜ƒ.minX(), (double)â˜ƒ.minY(), (double)â˜ƒ.minZ(), (double)â˜ƒ.maxX(), (double)â˜ƒ.maxY(), (double)â˜ƒ.maxZ());
      List<Entity> â˜ƒxx = â˜ƒ.getEntitiesOfClass(Entity.class, â˜ƒx, var0x -> !(var0x instanceof Player));
      â˜ƒxx.forEach(Entity::discard);
   }

   public static BoundingBox getStructureBoundingBox(BlockPos var0, Vec3i var1, Rotation var2) {
      BlockPos â˜ƒ = â˜ƒ.offset(â˜ƒ).offset(-1, -1, -1);
      BlockPos â˜ƒx = StructureTemplate.transform(â˜ƒ, Mirror.NONE, â˜ƒ, â˜ƒ);
      BoundingBox â˜ƒxx = BoundingBox.fromCorners(â˜ƒ, â˜ƒx);
      int â˜ƒxxx = Math.min(â˜ƒxx.minX(), â˜ƒxx.maxX());
      int â˜ƒxxxx = Math.min(â˜ƒxx.minZ(), â˜ƒxx.maxZ());
      return â˜ƒxx.move(â˜ƒ.getX() - â˜ƒxxx, 0, â˜ƒ.getZ() - â˜ƒxxxx);
   }

   public static Optional<BlockPos> findStructureBlockContainingPos(BlockPos var0, int var1, ServerLevel var2) {
      return findStructureBlocks(â˜ƒ, â˜ƒ, â˜ƒ).stream().filter(var2x -> doesStructureContain(var2x, â˜ƒ, â˜ƒ)).findFirst();
   }

   @Nullable
   public static BlockPos findNearestStructureBlock(BlockPos var0, int var1, ServerLevel var2) {
      Comparator<BlockPos> â˜ƒ = Comparator.comparingInt(var1x -> var1x.distManhattan(â˜ƒ));
      Collection<BlockPos> â˜ƒx = findStructureBlocks(â˜ƒ, â˜ƒ, â˜ƒ);
      Optional<BlockPos> â˜ƒxx = â˜ƒx.stream().min(â˜ƒ);
      return (BlockPos)â˜ƒxx.orElse(null);
   }

   public static Collection<BlockPos> findStructureBlocks(BlockPos var0, int var1, ServerLevel var2) {
      Collection<BlockPos> â˜ƒ = Lists.<BlockPos>newArrayList();
      AABB â˜ƒx = new AABB(â˜ƒ);
      â˜ƒx = â˜ƒx.inflate((double)â˜ƒ);

      for(int â˜ƒxx = (int)â˜ƒx.minX; â˜ƒxx <= (int)â˜ƒx.maxX; ++â˜ƒxx) {
         for(int â˜ƒxxx = (int)â˜ƒx.minY; â˜ƒxxx <= (int)â˜ƒx.maxY; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = (int)â˜ƒx.minZ; â˜ƒxxxx <= (int)â˜ƒx.maxZ; ++â˜ƒxxxx) {
               BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
               BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
               if (â˜ƒxxxxxx.is(Blocks.STRUCTURE_BLOCK)) {
                  â˜ƒ.add(â˜ƒxxxxx);
               }
            }
         }
      }

      return â˜ƒ;
   }

   private static StructureTemplate getStructureTemplate(String var0, ServerLevel var1) {
      StructureManager â˜ƒ = â˜ƒ.getStructureManager();
      Optional<StructureTemplate> â˜ƒx = â˜ƒ.get(new ResourceLocation(â˜ƒ));
      if (â˜ƒx.isPresent()) {
         return (StructureTemplate)â˜ƒx.get();
      } else {
         String â˜ƒ = â˜ƒ + ".snbt";
         Path â˜ƒx = Paths.get(testStructuresDir, â˜ƒ);
         CompoundTag â˜ƒxx = tryLoadStructure(â˜ƒx);
         if (â˜ƒxx == null) {
            throw new RuntimeException("Could not find structure file " + â˜ƒx + ", and the structure is not available in the world structures either.");
         } else {
            return â˜ƒ.readStructure(â˜ƒxx);
         }
      }
   }

   private static StructureBlockEntity createStructureBlock(String var0, BlockPos var1, Rotation var2, ServerLevel var3, boolean var4) {
      â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.STRUCTURE_BLOCK.defaultBlockState());
      StructureBlockEntity â˜ƒ = (StructureBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ);
      â˜ƒ.setMode(StructureMode.LOAD);
      â˜ƒ.setRotation(â˜ƒ);
      â˜ƒ.setIgnoreEntities(false);
      â˜ƒ.setStructureName(new ResourceLocation(â˜ƒ));
      â˜ƒ.loadStructure(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getStructureSize() != Vec3i.ZERO) {
         return â˜ƒ;
      } else {
         StructureTemplate â˜ƒ = getStructureTemplate(â˜ƒ, â˜ƒ);
         â˜ƒ.loadStructure(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ.getStructureSize() == Vec3i.ZERO) {
            throw new RuntimeException("Failed to load structure " + â˜ƒ);
         } else {
            return â˜ƒ;
         }
      }
   }

   @Nullable
   private static CompoundTag tryLoadStructure(Path var0) {
      try {
         BufferedReader â˜ƒ = Files.newBufferedReader(â˜ƒ);
         String â˜ƒx = IOUtils.toString(â˜ƒ);
         return NbtUtils.snbtToStructure(â˜ƒx);
      } catch (IOException var3) {
         return null;
      } catch (CommandSyntaxException var4) {
         throw new RuntimeException("Error while trying to load structure " + â˜ƒ, var4);
      }
   }

   private static void clearBlock(int var0, BlockPos var1, ServerLevel var2) {
      BlockState â˜ƒ = null;
      FlatLevelGeneratorSettings â˜ƒx = FlatLevelGeneratorSettings.getDefault(â˜ƒ.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY));
      if (â˜ƒx instanceof FlatLevelGeneratorSettings) {
         List<BlockState> â˜ƒxx = â˜ƒx.getLayers();
         int â˜ƒxxx = â˜ƒ.getY() - â˜ƒ.getMinBuildHeight();
         if (â˜ƒ.getY() < â˜ƒ && â˜ƒxxx > 0 && â˜ƒxxx <= â˜ƒxx.size()) {
            â˜ƒ = (BlockState)â˜ƒxx.get(â˜ƒxxx - 1);
         }
      } else if (â˜ƒ.getY() == â˜ƒ - 1) {
         â˜ƒ = â˜ƒ.getBiome(â˜ƒ).getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial();
      } else if (â˜ƒ.getY() < â˜ƒ - 1) {
         â˜ƒ = â˜ƒ.getBiome(â˜ƒ).getGenerationSettings().getSurfaceBuilderConfig().getUnderMaterial();
      }

      if (â˜ƒ == null) {
         â˜ƒ = Blocks.AIR.defaultBlockState();
      }

      BlockInput â˜ƒ = new BlockInput(â˜ƒ, Collections.emptySet(), null);
      â˜ƒ.place(â˜ƒ, â˜ƒ, 2);
      â˜ƒ.blockUpdated(â˜ƒ, â˜ƒ.getBlock());
   }

   private static boolean doesStructureContain(BlockPos var0, BlockPos var1, ServerLevel var2) {
      StructureBlockEntity â˜ƒ = (StructureBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ);
      AABB â˜ƒx = getStructureBounds(â˜ƒ).inflate(1.0);
      return â˜ƒx.contains(Vec3.atCenterOf(â˜ƒ));
   }
}
