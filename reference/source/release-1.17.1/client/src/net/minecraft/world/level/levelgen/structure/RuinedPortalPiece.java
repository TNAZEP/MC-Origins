package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlackstoneReplaceProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockAgeProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.LavaSubmergedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RuinedPortalPiece extends TemplateStructurePiece {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final float PROBABILITY_OF_GOLD_GONE = 0.3F;
   private static final float PROBABILITY_OF_MAGMA_INSTEAD_OF_NETHERRACK = 0.07F;
   private static final float PROBABILITY_OF_MAGMA_INSTEAD_OF_LAVA = 0.2F;
   private static final float DEFAULT_MOSSINESS = 0.2F;
   private final RuinedPortalPiece.VerticalPlacement verticalPlacement;
   private final RuinedPortalPiece.Properties properties;

   public RuinedPortalPiece(
      StructureManager var1,
      BlockPos var2,
      RuinedPortalPiece.VerticalPlacement var3,
      RuinedPortalPiece.Properties var4,
      ResourceLocation var5,
      StructureTemplate var6,
      Rotation var7,
      Mirror var8,
      BlockPos var9
   ) {
      super(StructurePieceType.RUINED_PORTAL, 0, â˜ƒ, â˜ƒ, â˜ƒ.toString(), makeSettings(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
      this.verticalPlacement = â˜ƒ;
      this.properties = â˜ƒ;
   }

   public RuinedPortalPiece(ServerLevel var1, CompoundTag var2) {
      super(StructurePieceType.RUINED_PORTAL, â˜ƒ, â˜ƒ, var2x -> makeSettings(â˜ƒ, â˜ƒ, var2x));
      this.verticalPlacement = RuinedPortalPiece.VerticalPlacement.byName(â˜ƒ.getString("VerticalPlacement"));
      this.properties = RuinedPortalPiece.Properties.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ.get("Properties"))).getOrThrow(true, LOGGER::error);
   }

   @Override
   protected void addAdditionalSaveData(ServerLevel var1, CompoundTag var2) {
      super.addAdditionalSaveData(â˜ƒ, â˜ƒ);
      â˜ƒ.putString("Rotation", this.placeSettings.getRotation().name());
      â˜ƒ.putString("Mirror", this.placeSettings.getMirror().name());
      â˜ƒ.putString("VerticalPlacement", this.verticalPlacement.getName());
      RuinedPortalPiece.Properties.CODEC
         .encodeStart(NbtOps.INSTANCE, this.properties)
         .resultOrPartial(LOGGER::error)
         .ifPresent(var1x -> â˜ƒ.put("Properties", var1x));
   }

   private static StructurePlaceSettings makeSettings(ServerLevel var0, CompoundTag var1, ResourceLocation var2) {
      StructureTemplate â˜ƒ = â˜ƒ.getStructureManager().getOrCreate(â˜ƒ);
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getSize().getX() / 2, 0, â˜ƒ.getSize().getZ() / 2);
      return makeSettings(
         Mirror.valueOf(â˜ƒ.getString("Mirror")),
         Rotation.valueOf(â˜ƒ.getString("Rotation")),
         RuinedPortalPiece.VerticalPlacement.byName(â˜ƒ.getString("VerticalPlacement")),
         â˜ƒx,
         RuinedPortalPiece.Properties.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, â˜ƒ.get("Properties"))).getOrThrow(true, LOGGER::error)
      );
   }

   private static StructurePlaceSettings makeSettings(
      Mirror var0, Rotation var1, RuinedPortalPiece.VerticalPlacement var2, BlockPos var3, RuinedPortalPiece.Properties var4
   ) {
      BlockIgnoreProcessor â˜ƒ = â˜ƒ.airPocket ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
      List<ProcessorRule> â˜ƒx = Lists.<ProcessorRule>newArrayList();
      â˜ƒx.add(getBlockReplaceRule(Blocks.GOLD_BLOCK, 0.3F, Blocks.AIR));
      â˜ƒx.add(getLavaProcessorRule(â˜ƒ, â˜ƒ));
      if (!â˜ƒ.cold) {
         â˜ƒx.add(getBlockReplaceRule(Blocks.NETHERRACK, 0.07F, Blocks.MAGMA_BLOCK));
      }

      StructurePlaceSettings â˜ƒ = new StructurePlaceSettings()
         .setRotation(â˜ƒ)
         .setMirror(â˜ƒ)
         .setRotationPivot(â˜ƒ)
         .addProcessor(â˜ƒ)
         .addProcessor(new RuleProcessor(â˜ƒx))
         .addProcessor(new BlockAgeProcessor(â˜ƒ.mossiness))
         .addProcessor(new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE.getName()))
         .addProcessor(new LavaSubmergedBlockProcessor());
      if (â˜ƒ.replaceWithBlackstone) {
         â˜ƒ.addProcessor(BlackstoneReplaceProcessor.INSTANCE);
      }

      return â˜ƒ;
   }

   private static ProcessorRule getLavaProcessorRule(RuinedPortalPiece.VerticalPlacement var0, RuinedPortalPiece.Properties var1) {
      if (â˜ƒ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR) {
         return getBlockReplaceRule(Blocks.LAVA, Blocks.MAGMA_BLOCK);
      } else {
         return â˜ƒ.cold ? getBlockReplaceRule(Blocks.LAVA, Blocks.NETHERRACK) : getBlockReplaceRule(Blocks.LAVA, 0.2F, Blocks.MAGMA_BLOCK);
      }
   }

   @Override
   public boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   ) {
      BoundingBox â˜ƒ = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
      if (!â˜ƒ.isInside(â˜ƒ.getCenter())) {
         return true;
      } else {
         â˜ƒ.encapsulate(â˜ƒ);
         boolean â˜ƒ = super.postProcess(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.spreadNetherrack(â˜ƒ, â˜ƒ);
         this.addNetherrackDripColumnsBelowPortal(â˜ƒ, â˜ƒ);
         if (this.properties.vines || this.properties.overgrown) {
            BlockPos.betweenClosedStream(this.getBoundingBox()).forEach(var3x -> {
               if (this.properties.vines) {
                  this.maybeAddVines(â˜ƒ, â˜ƒ, var3x);
               }

               if (this.properties.overgrown) {
                  this.maybeAddLeavesAbove(â˜ƒ, â˜ƒ, var3x);
               }
            });
         }

         return â˜ƒ;
      }
   }

   @Override
   protected void handleDataMarker(String var1, BlockPos var2, ServerLevelAccessor var3, Random var4, BoundingBox var5) {
   }

   private void maybeAddVines(Random var1, LevelAccessor var2, BlockPos var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!â˜ƒ.isAir() && !â˜ƒ.is(Blocks.VINE)) {
         Direction â˜ƒx = getRandomHorizontalDirection(â˜ƒ);
         BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
         if (â˜ƒxxx.isAir()) {
            if (Block.isFaceFull(â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ), â˜ƒx)) {
               BooleanProperty â˜ƒxxxx = VineBlock.getPropertyForFace(â˜ƒx.getOpposite());
               â˜ƒ.setBlock(â˜ƒxx, Blocks.VINE.defaultBlockState().setValue(â˜ƒxxxx, Boolean.valueOf(true)), 3);
            }
         }
      }
   }

   private void maybeAddLeavesAbove(Random var1, LevelAccessor var2, BlockPos var3) {
      if (â˜ƒ.nextFloat() < 0.5F && â˜ƒ.getBlockState(â˜ƒ).is(Blocks.NETHERRACK) && â˜ƒ.getBlockState(â˜ƒ.above()).isAir()) {
         â˜ƒ.setBlock(â˜ƒ.above(), Blocks.JUNGLE_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, Boolean.valueOf(true)), 3);
      }
   }

   private void addNetherrackDripColumnsBelowPortal(Random var1, LevelAccessor var2) {
      for(int â˜ƒ = this.boundingBox.minX() + 1; â˜ƒ < this.boundingBox.maxX(); ++â˜ƒ) {
         for(int â˜ƒx = this.boundingBox.minZ() + 1; â˜ƒx < this.boundingBox.maxZ(); ++â˜ƒx) {
            BlockPos â˜ƒxx = new BlockPos(â˜ƒ, this.boundingBox.minY(), â˜ƒx);
            if (â˜ƒ.getBlockState(â˜ƒxx).is(Blocks.NETHERRACK)) {
               this.addNetherrackDripColumn(â˜ƒ, â˜ƒ, â˜ƒxx.below());
            }
         }
      }
   }

   private void addNetherrackDripColumn(Random var1, LevelAccessor var2, BlockPos var3) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      this.placeNetherrackOrMagma(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒx = 8;

      while(â˜ƒx > 0 && â˜ƒ.nextFloat() < 0.5F) {
         â˜ƒ.move(Direction.DOWN);
         --â˜ƒx;
         this.placeNetherrackOrMagma(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void spreadNetherrack(Random var1, LevelAccessor var2) {
      boolean â˜ƒ = this.verticalPlacement == RuinedPortalPiece.VerticalPlacement.ON_LAND_SURFACE
         || this.verticalPlacement == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR;
      BlockPos â˜ƒx = this.boundingBox.getCenter();
      int â˜ƒxx = â˜ƒx.getX();
      int â˜ƒxxx = â˜ƒx.getZ();
      float[] â˜ƒxxxx = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
      int â˜ƒxxxxx = â˜ƒxxxx.length;
      int â˜ƒxxxxxx = (this.boundingBox.getXSpan() + this.boundingBox.getZSpan()) / 2;
      int â˜ƒxxxxxxx = â˜ƒ.nextInt(Math.max(1, 8 - â˜ƒxxxxxx / 2));
      int â˜ƒxxxxxxxx = 3;
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxx = BlockPos.ZERO.mutable();

      for(int â˜ƒxxxxxxxxxx = â˜ƒxx - â˜ƒxxxxx; â˜ƒxxxxxxxxxx <= â˜ƒxx + â˜ƒxxxxx; ++â˜ƒxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxx = â˜ƒxxx - â˜ƒxxxxx; â˜ƒxxxxxxxxxxx <= â˜ƒxxx + â˜ƒxxxxx; ++â˜ƒxxxxxxxxxxx) {
            int â˜ƒxxxxxxxxxxxx = Math.abs(â˜ƒxxxxxxxxxx - â˜ƒxx) + Math.abs(â˜ƒxxxxxxxxxxx - â˜ƒxxx);
            int â˜ƒxxxxxxxxxxxxx = Math.max(0, â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxx);
            if (â˜ƒxxxxxxxxxxxxx < â˜ƒxxxxx) {
               float â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxxxxxxxxx];
               if (â˜ƒ.nextDouble() < (double)â˜ƒxxxxxxxxxxxxxx) {
                  int â˜ƒxxxxxxxxxxxxxxx = getSurfaceY(â˜ƒ, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, this.verticalPlacement);
                  int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ ? â˜ƒxxxxxxxxxxxxxxx : Math.min(this.boundingBox.minY(), â˜ƒxxxxxxxxxxxxxxx);
                  â˜ƒxxxxxxxxx.set(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx);
                  if (Math.abs(â˜ƒxxxxxxxxxxxxxxxx - this.boundingBox.minY()) <= 3 && this.canBlockBeReplacedByNetherrackOrMagma(â˜ƒ, â˜ƒxxxxxxxxx)) {
                     this.placeNetherrackOrMagma(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxx);
                     if (this.properties.overgrown) {
                        this.maybeAddLeavesAbove(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxx);
                     }

                     this.addNetherrackDripColumn(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxx.below());
                  }
               }
            }
         }
      }
   }

   private boolean canBlockBeReplacedByNetherrackOrMagma(LevelAccessor var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return !â˜ƒ.is(Blocks.AIR)
         && !â˜ƒ.is(Blocks.OBSIDIAN)
         && !â˜ƒ.is(BlockTags.FEATURES_CANNOT_REPLACE)
         && (this.verticalPlacement == RuinedPortalPiece.VerticalPlacement.IN_NETHER || !â˜ƒ.is(Blocks.LAVA));
   }

   private void placeNetherrackOrMagma(Random var1, LevelAccessor var2, BlockPos var3) {
      if (!this.properties.cold && â˜ƒ.nextFloat() < 0.07F) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
      } else {
         â˜ƒ.setBlock(â˜ƒ, Blocks.NETHERRACK.defaultBlockState(), 3);
      }
   }

   private static int getSurfaceY(LevelAccessor var0, int var1, int var2, RuinedPortalPiece.VerticalPlacement var3) {
      return â˜ƒ.getHeight(getHeightMapType(â˜ƒ), â˜ƒ, â˜ƒ) - 1;
   }

   public static Heightmap.Types getHeightMapType(RuinedPortalPiece.VerticalPlacement var0) {
      return â˜ƒ == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
   }

   private static ProcessorRule getBlockReplaceRule(Block var0, float var1, Block var2) {
      return new ProcessorRule(new RandomBlockMatchTest(â˜ƒ, â˜ƒ), AlwaysTrueTest.INSTANCE, â˜ƒ.defaultBlockState());
   }

   private static ProcessorRule getBlockReplaceRule(Block var0, Block var1) {
      return new ProcessorRule(new BlockMatchTest(â˜ƒ), AlwaysTrueTest.INSTANCE, â˜ƒ.defaultBlockState());
   }

   public static class Properties {
      public static final Codec<RuinedPortalPiece.Properties> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.BOOL.fieldOf("cold").forGetter(var0x -> var0x.cold),
                  Codec.FLOAT.fieldOf("mossiness").forGetter(var0x -> var0x.mossiness),
                  Codec.BOOL.fieldOf("air_pocket").forGetter(var0x -> var0x.airPocket),
                  Codec.BOOL.fieldOf("overgrown").forGetter(var0x -> var0x.overgrown),
                  Codec.BOOL.fieldOf("vines").forGetter(var0x -> var0x.vines),
                  Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(var0x -> var0x.replaceWithBlackstone)
               )
               .apply(var0, RuinedPortalPiece.Properties::new)
      );
      public boolean cold;
      public float mossiness = 0.2F;
      public boolean airPocket;
      public boolean overgrown;
      public boolean vines;
      public boolean replaceWithBlackstone;

      public Properties() {
      }

      public <T> Properties(boolean var1, float var2, boolean var3, boolean var4, boolean var5, boolean var6) {
         this.cold = â˜ƒ;
         this.mossiness = â˜ƒ;
         this.airPocket = â˜ƒ;
         this.overgrown = â˜ƒ;
         this.vines = â˜ƒ;
         this.replaceWithBlackstone = â˜ƒ;
      }
   }

   public static enum VerticalPlacement {
      ON_LAND_SURFACE("on_land_surface"),
      PARTLY_BURIED("partly_buried"),
      ON_OCEAN_FLOOR("on_ocean_floor"),
      IN_MOUNTAIN("in_mountain"),
      UNDERGROUND("underground"),
      IN_NETHER("in_nether");

      private static final Map<String, RuinedPortalPiece.VerticalPlacement> BY_NAME = (Map<String, RuinedPortalPiece.VerticalPlacement>)Arrays.stream(values())
         .collect(Collectors.toMap(RuinedPortalPiece.VerticalPlacement::getName, var0 -> var0));
      private final String name;

      private VerticalPlacement(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static RuinedPortalPiece.VerticalPlacement byName(String var0) {
         return (RuinedPortalPiece.VerticalPlacement)BY_NAME.get(â˜ƒ);
      }
   }
}
