package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableSet;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.NoiseEffect;
import net.minecraft.world.level.levelgen.feature.StructurePieceType;
import net.minecraft.world.level.material.FluidState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructurePiece {
   private static final Logger LOGGER = LogManager.getLogger();
   protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.defaultBlockState();
   protected BoundingBox boundingBox;
   @Nullable
   private Direction orientation;
   private Mirror mirror;
   private Rotation rotation;
   protected int genDepth;
   private final StructurePieceType type;
   private static final Set<Block> SHAPE_CHECK_BLOCKS = ImmutableSet.<Block>builder()
      .add(Blocks.NETHER_BRICK_FENCE)
      .add(Blocks.TORCH)
      .add(Blocks.WALL_TORCH)
      .add(Blocks.OAK_FENCE)
      .add(Blocks.SPRUCE_FENCE)
      .add(Blocks.DARK_OAK_FENCE)
      .add(Blocks.ACACIA_FENCE)
      .add(Blocks.BIRCH_FENCE)
      .add(Blocks.JUNGLE_FENCE)
      .add(Blocks.LADDER)
      .add(Blocks.IRON_BARS)
      .build();

   protected StructurePiece(StructurePieceType var1, int var2, BoundingBox var3) {
      this.type = â˜ƒ;
      this.genDepth = â˜ƒ;
      this.boundingBox = â˜ƒ;
   }

   public StructurePiece(StructurePieceType var1, CompoundTag var2) {
      this(
         â˜ƒ,
         â˜ƒ.getInt("GD"),
         (BoundingBox)BoundingBox.CODEC
            .parse(NbtOps.INSTANCE, â˜ƒ.get("BB"))
            .resultOrPartial(LOGGER::error)
            .orElseThrow(() -> new IllegalArgumentException("Invalid boundingbox"))
      );
      int â˜ƒ = â˜ƒ.getInt("O");
      this.setOrientation(â˜ƒ == -1 ? null : Direction.from2DDataValue(â˜ƒ));
   }

   protected static BoundingBox makeBoundingBox(int var0, int var1, int var2, Direction var3, int var4, int var5, int var6) {
      return â˜ƒ.getAxis() == Direction.Axis.Z
         ? new BoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ - 1, â˜ƒ + â˜ƒ - 1, â˜ƒ + â˜ƒ - 1)
         : new BoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + â˜ƒ - 1, â˜ƒ + â˜ƒ - 1, â˜ƒ + â˜ƒ - 1);
   }

   protected static Direction getRandomHorizontalDirection(Random var0) {
      return Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
   }

   public final CompoundTag createTag(ServerLevel var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      â˜ƒ.putString("id", Registry.STRUCTURE_PIECE.getKey(this.getType()).toString());
      BoundingBox.CODEC.encodeStart(NbtOps.INSTANCE, this.boundingBox).resultOrPartial(LOGGER::error).ifPresent(var1x -> â˜ƒ.put("BB", var1x));
      Direction â˜ƒx = this.getOrientation();
      â˜ƒ.putInt("O", â˜ƒx == null ? -1 : â˜ƒx.get2DDataValue());
      â˜ƒ.putInt("GD", this.genDepth);
      this.addAdditionalSaveData(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   protected abstract void addAdditionalSaveData(ServerLevel var1, CompoundTag var2);

   public NoiseEffect getNoiseEffect() {
      return NoiseEffect.BEARD;
   }

   public void addChildren(StructurePiece var1, StructurePieceAccessor var2, Random var3) {
   }

   public abstract boolean postProcess(
      WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6, BlockPos var7
   );

   public BoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   public int getGenDepth() {
      return this.genDepth;
   }

   public boolean isCloseToChunk(ChunkPos var1, int var2) {
      int â˜ƒ = â˜ƒ.getMinBlockX();
      int â˜ƒx = â˜ƒ.getMinBlockZ();
      return this.boundingBox.intersects(â˜ƒ - â˜ƒ, â˜ƒx - â˜ƒ, â˜ƒ + 15 + â˜ƒ, â˜ƒx + 15 + â˜ƒ);
   }

   public BlockPos getLocatorPosition() {
      return new BlockPos(this.boundingBox.getCenter());
   }

   protected BlockPos.MutableBlockPos getWorldPos(int var1, int var2, int var3) {
      return new BlockPos.MutableBlockPos(this.getWorldX(â˜ƒ, â˜ƒ), this.getWorldY(â˜ƒ), this.getWorldZ(â˜ƒ, â˜ƒ));
   }

   protected int getWorldX(int var1, int var2) {
      Direction â˜ƒ = this.getOrientation();
      if (â˜ƒ == null) {
         return â˜ƒ;
      } else {
         switch(â˜ƒ) {
            case NORTH:
            case SOUTH:
               return this.boundingBox.minX() + â˜ƒ;
            case WEST:
               return this.boundingBox.maxX() - â˜ƒ;
            case EAST:
               return this.boundingBox.minX() + â˜ƒ;
            default:
               return â˜ƒ;
         }
      }
   }

   protected int getWorldY(int var1) {
      return this.getOrientation() == null ? â˜ƒ : â˜ƒ + this.boundingBox.minY();
   }

   protected int getWorldZ(int var1, int var2) {
      Direction â˜ƒ = this.getOrientation();
      if (â˜ƒ == null) {
         return â˜ƒ;
      } else {
         switch(â˜ƒ) {
            case NORTH:
               return this.boundingBox.maxZ() - â˜ƒ;
            case SOUTH:
               return this.boundingBox.minZ() + â˜ƒ;
            case WEST:
            case EAST:
               return this.boundingBox.minZ() + â˜ƒ;
            default:
               return â˜ƒ;
         }
      }
   }

   protected void placeBlock(WorldGenLevel var1, BlockState var2, int var3, int var4, int var5, BoundingBox var6) {
      BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isInside(â˜ƒ)) {
         if (this.canBeReplaced(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            if (this.mirror != Mirror.NONE) {
               â˜ƒ = â˜ƒ.mirror(this.mirror);
            }

            if (this.rotation != Rotation.NONE) {
               â˜ƒ = â˜ƒ.rotate(this.rotation);
            }

            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
            FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ);
            if (!â˜ƒx.isEmpty()) {
               â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, â˜ƒx.getType(), 0);
            }

            if (SHAPE_CHECK_BLOCKS.contains(â˜ƒ.getBlock())) {
               â˜ƒ.getChunk(â˜ƒ).markPosForPostprocessing(â˜ƒ);
            }
         }
      }
   }

   protected boolean canBeReplaced(LevelReader var1, int var2, int var3, int var4, BoundingBox var5) {
      return true;
   }

   protected BlockState getBlock(BlockGetter var1, int var2, int var3, int var4, BoundingBox var5) {
      BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
      return !â˜ƒ.isInside(â˜ƒ) ? Blocks.AIR.defaultBlockState() : â˜ƒ.getBlockState(â˜ƒ);
   }

   protected boolean isInterior(LevelReader var1, int var2, int var3, int var4, BoundingBox var5) {
      BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ + 1, â˜ƒ);
      if (!â˜ƒ.isInside(â˜ƒ)) {
         return false;
      } else {
         return â˜ƒ.getY() < â˜ƒ.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, â˜ƒ.getX(), â˜ƒ.getZ());
      }
   }

   protected void generateAirBox(WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
               this.placeBlock(â˜ƒ, Blocks.AIR.defaultBlockState(), â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
            }
         }
      }
   }

   protected void generateBox(
      WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, BlockState var9, BlockState var10, boolean var11
   ) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
               if (!â˜ƒ || !this.getBlock(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ).isAir()) {
                  if (â˜ƒ != â˜ƒ && â˜ƒ != â˜ƒ && â˜ƒx != â˜ƒ && â˜ƒx != â˜ƒ && â˜ƒxx != â˜ƒ && â˜ƒxx != â˜ƒ) {
                     this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                  } else {
                     this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                  }
               }
            }
         }
      }
   }

   protected void generateBox(WorldGenLevel var1, BoundingBox var2, BoundingBox var3, BlockState var4, BlockState var5, boolean var6) {
      this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ.minX(), â˜ƒ.minY(), â˜ƒ.minZ(), â˜ƒ.maxX(), â˜ƒ.maxY(), â˜ƒ.maxZ(), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void generateBox(
      WorldGenLevel var1,
      BoundingBox var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9,
      Random var10,
      StructurePiece.BlockSelector var11
   ) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
               if (!â˜ƒ || !this.getBlock(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ).isAir()) {
                  â˜ƒ.next(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ == â˜ƒ || â˜ƒ == â˜ƒ || â˜ƒx == â˜ƒ || â˜ƒx == â˜ƒ || â˜ƒxx == â˜ƒ || â˜ƒxx == â˜ƒ);
                  this.placeBlock(â˜ƒ, â˜ƒ.getNext(), â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
               }
            }
         }
      }
   }

   protected void generateBox(WorldGenLevel var1, BoundingBox var2, BoundingBox var3, boolean var4, Random var5, StructurePiece.BlockSelector var6) {
      this.generateBox(â˜ƒ, â˜ƒ, â˜ƒ.minX(), â˜ƒ.minY(), â˜ƒ.minZ(), â˜ƒ.maxX(), â˜ƒ.maxY(), â˜ƒ.maxZ(), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void generateMaybeBox(
      WorldGenLevel var1,
      BoundingBox var2,
      Random var3,
      float var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      BlockState var11,
      BlockState var12,
      boolean var13,
      boolean var14
   ) {
      for(int â˜ƒ = â˜ƒ; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
            for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
               if (!(â˜ƒ.nextFloat() > â˜ƒ)
                  && (!â˜ƒ || !this.getBlock(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ).isAir())
                  && (!â˜ƒ || this.isInterior(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ))) {
                  if (â˜ƒ != â˜ƒ && â˜ƒ != â˜ƒ && â˜ƒx != â˜ƒ && â˜ƒx != â˜ƒ && â˜ƒxx != â˜ƒ && â˜ƒxx != â˜ƒ) {
                     this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                  } else {
                     this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒ);
                  }
               }
            }
         }
      }
   }

   protected void maybeGenerateBlock(WorldGenLevel var1, BoundingBox var2, Random var3, float var4, int var5, int var6, int var7, BlockState var8) {
      if (â˜ƒ.nextFloat() < â˜ƒ) {
         this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected void generateUpperHalfSphere(
      WorldGenLevel var1, BoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, BlockState var9, boolean var10
   ) {
      float â˜ƒ = (float)(â˜ƒ - â˜ƒ + 1);
      float â˜ƒx = (float)(â˜ƒ - â˜ƒ + 1);
      float â˜ƒxx = (float)(â˜ƒ - â˜ƒ + 1);
      float â˜ƒxxx = (float)â˜ƒ + â˜ƒ / 2.0F;
      float â˜ƒxxxx = (float)â˜ƒ + â˜ƒxx / 2.0F;

      for(int â˜ƒxxxxx = â˜ƒ; â˜ƒxxxxx <= â˜ƒ; ++â˜ƒxxxxx) {
         float â˜ƒxxxxxx = (float)(â˜ƒxxxxx - â˜ƒ) / â˜ƒx;

         for(int â˜ƒxxxxxxx = â˜ƒ; â˜ƒxxxxxxx <= â˜ƒ; ++â˜ƒxxxxxxx) {
            float â˜ƒxxxxxxxx = ((float)â˜ƒxxxxxxx - â˜ƒxxx) / (â˜ƒ * 0.5F);

            for(int â˜ƒxxxxxxxxx = â˜ƒ; â˜ƒxxxxxxxxx <= â˜ƒ; ++â˜ƒxxxxxxxxx) {
               float â˜ƒxxxxxxxxxx = ((float)â˜ƒxxxxxxxxx - â˜ƒxxxx) / (â˜ƒxx * 0.5F);
               if (!â˜ƒ || !this.getBlock(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxx, â˜ƒ).isAir()) {
                  float â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx * â˜ƒxxxxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxx;
                  if (â˜ƒxxxxxxxxxxx <= 1.05F) {
                     this.placeBlock(â˜ƒ, â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxx, â˜ƒ);
                  }
               }
            }
         }
      }
   }

   protected void fillColumnDown(WorldGenLevel var1, BlockState var2, int var3, int var4, int var5, BoundingBox var6) {
      BlockPos.MutableBlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isInside(â˜ƒ)) {
         while(this.isReplaceableByStructures(â˜ƒ.getBlockState(â˜ƒ)) && â˜ƒ.getY() > â˜ƒ.getMinBuildHeight() + 1) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
            â˜ƒ.move(Direction.DOWN);
         }
      }
   }

   protected boolean isReplaceableByStructures(BlockState var1) {
      return â˜ƒ.isAir() || â˜ƒ.getMaterial().isLiquid() || â˜ƒ.is(Blocks.GLOW_LICHEN) || â˜ƒ.is(Blocks.SEAGRASS) || â˜ƒ.is(Blocks.TALL_SEAGRASS);
   }

   protected boolean createChest(WorldGenLevel var1, BoundingBox var2, Random var3, int var4, int var5, int var6, ResourceLocation var7) {
      return this.createChest(â˜ƒ, â˜ƒ, â˜ƒ, this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ, null);
   }

   public static BlockState reorient(BlockGetter var0, BlockPos var1, BlockState var2) {
      Direction â˜ƒ = null;

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
         if (â˜ƒxxx.is(Blocks.CHEST)) {
            return â˜ƒ;
         }

         if (â˜ƒxxx.isSolidRender(â˜ƒ, â˜ƒxx)) {
            if (â˜ƒ != null) {
               â˜ƒ = null;
               break;
            }

            â˜ƒ = â˜ƒx;
         }
      }

      if (â˜ƒ != null) {
         return â˜ƒ.setValue(HorizontalDirectionalBlock.FACING, â˜ƒ.getOpposite());
      } else {
         Direction â˜ƒx = â˜ƒ.getValue(HorizontalDirectionalBlock.FACING);
         BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         if (â˜ƒ.getBlockState(â˜ƒxx).isSolidRender(â˜ƒ, â˜ƒxx)) {
            â˜ƒx = â˜ƒx.getOpposite();
            â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         }

         if (â˜ƒ.getBlockState(â˜ƒxx).isSolidRender(â˜ƒ, â˜ƒxx)) {
            â˜ƒx = â˜ƒx.getClockWise();
            â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         }

         if (â˜ƒ.getBlockState(â˜ƒxx).isSolidRender(â˜ƒ, â˜ƒxx)) {
            â˜ƒx = â˜ƒx.getOpposite();
            â˜ƒxx = â˜ƒ.relative(â˜ƒx);
         }

         return â˜ƒ.setValue(HorizontalDirectionalBlock.FACING, â˜ƒx);
      }
   }

   protected boolean createChest(ServerLevelAccessor var1, BoundingBox var2, Random var3, BlockPos var4, ResourceLocation var5, @Nullable BlockState var6) {
      if (â˜ƒ.isInside(â˜ƒ) && !â˜ƒ.getBlockState(â˜ƒ).is(Blocks.CHEST)) {
         if (â˜ƒ == null) {
            â˜ƒ = reorient(â˜ƒ, â˜ƒ, Blocks.CHEST.defaultBlockState());
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof ChestBlockEntity) {
            ((ChestBlockEntity)â˜ƒ).setLootTable(â˜ƒ, â˜ƒ.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean createDispenser(WorldGenLevel var1, BoundingBox var2, Random var3, int var4, int var5, int var6, Direction var7, ResourceLocation var8) {
      BlockPos â˜ƒ = this.getWorldPos(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isInside(â˜ƒ) && !â˜ƒ.getBlockState(â˜ƒ).is(Blocks.DISPENSER)) {
         this.placeBlock(â˜ƒ, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒx instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)â˜ƒx).setLootTable(â˜ƒ, â˜ƒ.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   public void move(int var1, int var2, int var3) {
      this.boundingBox.move(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   public Direction getOrientation() {
      return this.orientation;
   }

   public void setOrientation(@Nullable Direction var1) {
      this.orientation = â˜ƒ;
      if (â˜ƒ == null) {
         this.rotation = Rotation.NONE;
         this.mirror = Mirror.NONE;
      } else {
         switch(â˜ƒ) {
            case SOUTH:
               this.mirror = Mirror.LEFT_RIGHT;
               this.rotation = Rotation.NONE;
               break;
            case WEST:
               this.mirror = Mirror.LEFT_RIGHT;
               this.rotation = Rotation.CLOCKWISE_90;
               break;
            case EAST:
               this.mirror = Mirror.NONE;
               this.rotation = Rotation.CLOCKWISE_90;
               break;
            default:
               this.mirror = Mirror.NONE;
               this.rotation = Rotation.NONE;
         }
      }
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public Mirror getMirror() {
      return this.mirror;
   }

   public StructurePieceType getType() {
      return this.type;
   }

   protected abstract static class BlockSelector {
      protected BlockState next = Blocks.AIR.defaultBlockState();

      public abstract void next(Random var1, int var2, int var3, int var4, boolean var5);

      public BlockState getNext() {
         return this.next;
      }
   }
}
