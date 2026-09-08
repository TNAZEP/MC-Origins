package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.IdMapper;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

public class StructureTemplate {
   public static final String PALETTE_TAG = "palette";
   public static final String PALETTE_LIST_TAG = "palettes";
   public static final String ENTITIES_TAG = "entities";
   public static final String BLOCKS_TAG = "blocks";
   public static final String BLOCK_TAG_POS = "pos";
   public static final String BLOCK_TAG_STATE = "state";
   public static final String BLOCK_TAG_NBT = "nbt";
   public static final String ENTITY_TAG_POS = "pos";
   public static final String ENTITY_TAG_BLOCKPOS = "blockPos";
   public static final String ENTITY_TAG_NBT = "nbt";
   public static final String SIZE_TAG = "size";
   static final int CHUNK_SIZE = 16;
   private final List<StructureTemplate.Palette> palettes = Lists.<StructureTemplate.Palette>newArrayList();
   private final List<StructureTemplate.StructureEntityInfo> entityInfoList = Lists.<StructureTemplate.StructureEntityInfo>newArrayList();
   private Vec3i size = Vec3i.ZERO;
   private String author = "?";

   public Vec3i getSize() {
      return this.size;
   }

   public void setAuthor(String var1) {
      this.author = â˜ƒ;
   }

   public String getAuthor() {
      return this.author;
   }

   public void fillFromWorld(Level var1, BlockPos var2, Vec3i var3, boolean var4, @Nullable Block var5) {
      if (â˜ƒ.getX() >= 1 && â˜ƒ.getY() >= 1 && â˜ƒ.getZ() >= 1) {
         BlockPos â˜ƒ = â˜ƒ.offset(â˜ƒ).offset(-1, -1, -1);
         List<StructureTemplate.StructureBlockInfo> â˜ƒx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
         List<StructureTemplate.StructureBlockInfo> â˜ƒxx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
         List<StructureTemplate.StructureBlockInfo> â˜ƒxxx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
         BlockPos â˜ƒxxxx = new BlockPos(Math.min(â˜ƒ.getX(), â˜ƒ.getX()), Math.min(â˜ƒ.getY(), â˜ƒ.getY()), Math.min(â˜ƒ.getZ(), â˜ƒ.getZ()));
         BlockPos â˜ƒxxxxx = new BlockPos(Math.max(â˜ƒ.getX(), â˜ƒ.getX()), Math.max(â˜ƒ.getY(), â˜ƒ.getY()), Math.max(â˜ƒ.getZ(), â˜ƒ.getZ()));
         this.size = â˜ƒ;

         for(BlockPos â˜ƒxxxxxx : BlockPos.betweenClosed(â˜ƒxxxx, â˜ƒxxxxx)) {
            BlockPos â˜ƒxxxxxxx = â˜ƒxxxxxx.subtract(â˜ƒxxxx);
            BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
            if (â˜ƒ == null || !â˜ƒxxxxxxxx.is(â˜ƒ)) {
               BlockEntity â˜ƒxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxx);
               StructureTemplate.StructureBlockInfo â˜ƒxxxxxxxxx;
               if (â˜ƒxxxxxxxxxx != null) {
                  CompoundTag â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.save(new CompoundTag());
                  â˜ƒxxxxxxxxxxx.remove("x");
                  â˜ƒxxxxxxxxxxx.remove("y");
                  â˜ƒxxxxxxxxxxx.remove("z");
                  â˜ƒxxxxxxxxx = new StructureTemplate.StructureBlockInfo(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx.copy());
               } else {
                  â˜ƒxxxxxxxxx = new StructureTemplate.StructureBlockInfo(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, null);
               }

               addToLists(â˜ƒxxxxxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxx);
            }
         }

         List<StructureTemplate.StructureBlockInfo> â˜ƒxxxxxx = buildInfoList(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         this.palettes.clear();
         this.palettes.add(new StructureTemplate.Palette(â˜ƒxxxxxx));
         if (â˜ƒ) {
            this.fillEntityList(â˜ƒ, â˜ƒxxxx, â˜ƒxxxxx.offset(1, 1, 1));
         } else {
            this.entityInfoList.clear();
         }
      }
   }

   private static void addToLists(
      StructureTemplate.StructureBlockInfo var0,
      List<StructureTemplate.StructureBlockInfo> var1,
      List<StructureTemplate.StructureBlockInfo> var2,
      List<StructureTemplate.StructureBlockInfo> var3
   ) {
      if (â˜ƒ.nbt != null) {
         â˜ƒ.add(â˜ƒ);
      } else if (!â˜ƒ.state.getBlock().hasDynamicShape() && â˜ƒ.state.isCollisionShapeFullBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO)) {
         â˜ƒ.add(â˜ƒ);
      } else {
         â˜ƒ.add(â˜ƒ);
      }
   }

   private static List<StructureTemplate.StructureBlockInfo> buildInfoList(
      List<StructureTemplate.StructureBlockInfo> var0, List<StructureTemplate.StructureBlockInfo> var1, List<StructureTemplate.StructureBlockInfo> var2
   ) {
      Comparator<StructureTemplate.StructureBlockInfo> â˜ƒ = Comparator.comparingInt(var0x -> var0x.pos.getY())
         .thenComparingInt(var0x -> var0x.pos.getX())
         .thenComparingInt(var0x -> var0x.pos.getZ());
      â˜ƒ.sort(â˜ƒ);
      â˜ƒ.sort(â˜ƒ);
      â˜ƒ.sort(â˜ƒ);
      List<StructureTemplate.StructureBlockInfo> â˜ƒx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
      â˜ƒx.addAll(â˜ƒ);
      â˜ƒx.addAll(â˜ƒ);
      â˜ƒx.addAll(â˜ƒ);
      return â˜ƒx;
   }

   private void fillEntityList(Level var1, BlockPos var2, BlockPos var3) {
      List<Entity> â˜ƒ = â˜ƒ.getEntitiesOfClass(Entity.class, new AABB(â˜ƒ, â˜ƒ), var0 -> !(var0 instanceof Player));
      this.entityInfoList.clear();

      for(Entity â˜ƒx : â˜ƒ) {
         Vec3 â˜ƒxxx = new Vec3(â˜ƒx.getX() - (double)â˜ƒ.getX(), â˜ƒx.getY() - (double)â˜ƒ.getY(), â˜ƒx.getZ() - (double)â˜ƒ.getZ());
         CompoundTag â˜ƒxxxx = new CompoundTag();
         â˜ƒx.save(â˜ƒxxxx);
         BlockPos â˜ƒxx;
         if (â˜ƒx instanceof Painting) {
            â˜ƒxx = ((Painting)â˜ƒx).getPos().subtract(â˜ƒ);
         } else {
            â˜ƒxx = new BlockPos(â˜ƒxxx);
         }

         this.entityInfoList.add(new StructureTemplate.StructureEntityInfo(â˜ƒxxx, â˜ƒxx, â˜ƒxxxx.copy()));
      }
   }

   public List<StructureTemplate.StructureBlockInfo> filterBlocks(BlockPos var1, StructurePlaceSettings var2, Block var3) {
      return this.filterBlocks(â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   public List<StructureTemplate.StructureBlockInfo> filterBlocks(BlockPos var1, StructurePlaceSettings var2, Block var3, boolean var4) {
      List<StructureTemplate.StructureBlockInfo> â˜ƒ = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
      BoundingBox â˜ƒx = â˜ƒ.getBoundingBox();
      if (this.palettes.isEmpty()) {
         return Collections.emptyList();
      } else {
         for(StructureTemplate.StructureBlockInfo â˜ƒ : â˜ƒ.getRandomPalette(this.palettes, â˜ƒ).blocks(â˜ƒ)) {
            BlockPos â˜ƒx = â˜ƒ ? calculateRelativePosition(â˜ƒ, â˜ƒ.pos).offset(â˜ƒ) : â˜ƒ.pos;
            if (â˜ƒx == null || â˜ƒx.isInside(â˜ƒx)) {
               â˜ƒ.add(new StructureTemplate.StructureBlockInfo(â˜ƒx, â˜ƒ.state.rotate(â˜ƒ.getRotation()), â˜ƒ.nbt));
            }
         }

         return â˜ƒ;
      }
   }

   public BlockPos calculateConnectedPosition(StructurePlaceSettings var1, BlockPos var2, StructurePlaceSettings var3, BlockPos var4) {
      BlockPos â˜ƒ = calculateRelativePosition(â˜ƒ, â˜ƒ);
      BlockPos â˜ƒx = calculateRelativePosition(â˜ƒ, â˜ƒ);
      return â˜ƒ.subtract(â˜ƒx);
   }

   public static BlockPos calculateRelativePosition(StructurePlaceSettings var0, BlockPos var1) {
      return transform(â˜ƒ, â˜ƒ.getMirror(), â˜ƒ.getRotation(), â˜ƒ.getRotationPivot());
   }

   public boolean placeInWorld(ServerLevelAccessor var1, BlockPos var2, BlockPos var3, StructurePlaceSettings var4, Random var5, int var6) {
      if (this.palettes.isEmpty()) {
         return false;
      } else {
         List<StructureTemplate.StructureBlockInfo> â˜ƒ = â˜ƒ.getRandomPalette(this.palettes, â˜ƒ).blocks();
         if ((!â˜ƒ.isEmpty() || !â˜ƒ.isIgnoreEntities() && !this.entityInfoList.isEmpty())
            && this.size.getX() >= 1
            && this.size.getY() >= 1
            && this.size.getZ() >= 1) {
            BoundingBox â˜ƒx = â˜ƒ.getBoundingBox();
            List<BlockPos> â˜ƒxx = Lists.<BlockPos>newArrayListWithCapacity(â˜ƒ.shouldKeepLiquids() ? â˜ƒ.size() : 0);
            List<BlockPos> â˜ƒxxx = Lists.<BlockPos>newArrayListWithCapacity(â˜ƒ.shouldKeepLiquids() ? â˜ƒ.size() : 0);
            List<Pair<BlockPos, CompoundTag>> â˜ƒxxxx = Lists.<Pair<BlockPos, CompoundTag>>newArrayListWithCapacity(â˜ƒ.size());
            int â˜ƒxxxxx = Integer.MAX_VALUE;
            int â˜ƒxxxxxx = Integer.MAX_VALUE;
            int â˜ƒxxxxxxx = Integer.MAX_VALUE;
            int â˜ƒxxxxxxxx = Integer.MIN_VALUE;
            int â˜ƒxxxxxxxxx = Integer.MIN_VALUE;
            int â˜ƒxxxxxxxxxx = Integer.MIN_VALUE;

            for(StructureTemplate.StructureBlockInfo â˜ƒxxxxxxxxxxx : processBlockInfos(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
               BlockPos â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.pos;
               if (â˜ƒx == null || â˜ƒx.isInside(â˜ƒxxxxxxxxxxxx)) {
                  FluidState â˜ƒxxxxxxxxxxxxx = â˜ƒ.shouldKeepLiquids() ? â˜ƒ.getFluidState(â˜ƒxxxxxxxxxxxx) : null;
                  BlockState â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.state.mirror(â˜ƒ.getMirror()).rotate(â˜ƒ.getRotation());
                  if (â˜ƒxxxxxxxxxxx.nbt != null) {
                     BlockEntity â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxxxxxx);
                     Clearable.tryClear(â˜ƒxxxxxxxxxxxxxxx);
                     â˜ƒ.setBlock(â˜ƒxxxxxxxxxxxx, Blocks.BARRIER.defaultBlockState(), 20);
                  }

                  if (â˜ƒ.setBlock(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒ)) {
                     â˜ƒxxxxx = Math.min(â˜ƒxxxxx, â˜ƒxxxxxxxxxxxx.getX());
                     â˜ƒxxxxxx = Math.min(â˜ƒxxxxxx, â˜ƒxxxxxxxxxxxx.getY());
                     â˜ƒxxxxxxx = Math.min(â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx.getZ());
                     â˜ƒxxxxxxxx = Math.max(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx.getX());
                     â˜ƒxxxxxxxxx = Math.max(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxxxx.getY());
                     â˜ƒxxxxxxxxxx = Math.max(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxxx.getZ());
                     â˜ƒxxxx.add(Pair.of(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx.nbt));
                     if (â˜ƒxxxxxxxxxxx.nbt != null) {
                        BlockEntity â˜ƒxxxxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxxxxxx);
                        if (â˜ƒxxxxxxxxxxxxx != null) {
                           â˜ƒxxxxxxxxxxx.nbt.putInt("x", â˜ƒxxxxxxxxxxxx.getX());
                           â˜ƒxxxxxxxxxxx.nbt.putInt("y", â˜ƒxxxxxxxxxxxx.getY());
                           â˜ƒxxxxxxxxxxx.nbt.putInt("z", â˜ƒxxxxxxxxxxxx.getZ());
                           if (â˜ƒxxxxxxxxxxxxx instanceof RandomizableContainerBlockEntity) {
                              â˜ƒxxxxxxxxxxx.nbt.putLong("LootTableSeed", â˜ƒ.nextLong());
                           }

                           â˜ƒxxxxxxxxxxxxx.load(â˜ƒxxxxxxxxxxx.nbt);
                        }
                     }

                     if (â˜ƒxxxxxxxxxxxxx != null) {
                        if (â˜ƒxxxxxxxxxxxxxx.getFluidState().isSource()) {
                           â˜ƒxxx.add(â˜ƒxxxxxxxxxxxx);
                        } else if (â˜ƒxxxxxxxxxxxxxx.getBlock() instanceof LiquidBlockContainer) {
                           ((LiquidBlockContainer)â˜ƒxxxxxxxxxxxxxx.getBlock()).placeLiquid(â˜ƒ, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
                           if (!â˜ƒxxxxxxxxxxxxx.isSource()) {
                              â˜ƒxx.add(â˜ƒxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }

            boolean â˜ƒxxxxxxxxxxx = true;
            Direction[] â˜ƒxxxxxxxxxxxx = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

            while(â˜ƒxxxxxxxxxxx && !â˜ƒxx.isEmpty()) {
               â˜ƒxxxxxxxxxxx = false;
               Iterator<BlockPos> â˜ƒxxxxxxxxxxxxx = â˜ƒxx.iterator();

               while(â˜ƒxxxxxxxxxxxxx.hasNext()) {
                  BlockPos â˜ƒxxxxxxxxxxxxxx = (BlockPos)â˜ƒxxxxxxxxxxxxx.next();
                  FluidState â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxxxxxxxxxx);

                  for(int â˜ƒxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxx.length && !â˜ƒxxxxxxxxxxxxxxx.isSource(); ++â˜ƒxxxxxxxxxxxxxxxx) {
                     BlockPos â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx.relative(â˜ƒxxxxxxxxxxxx[â˜ƒxxxxxxxxxxxxxxxx]);
                     FluidState â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxxxxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxxxxxxx.isSource() && !â˜ƒxxx.contains(â˜ƒxxxxxxxxxxxxxxxxx)) {
                        â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx;
                     }
                  }

                  if (â˜ƒxxxxxxxxxxxxxxx.isSource()) {
                     BlockState â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxxxxx);
                     Block â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.getBlock();
                     if (â˜ƒxxxxxxxxxxxxxxxxx instanceof LiquidBlockContainer) {
                        ((LiquidBlockContainer)â˜ƒxxxxxxxxxxxxxxxxx).placeLiquid(â˜ƒ, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx);
                        â˜ƒxxxxxxxxxxx = true;
                        â˜ƒxxxxxxxxxxxxx.remove();
                     }
                  }
               }
            }

            if (â˜ƒxxxxx <= â˜ƒxxxxxxxx) {
               if (!â˜ƒ.getKnownShape()) {
                  DiscreteVoxelShape â˜ƒxxxxxxxxxxxxx = new BitSetDiscreteVoxelShape(
                     â˜ƒxxxxxxxx - â˜ƒxxxxx + 1, â˜ƒxxxxxxxxx - â˜ƒxxxxxx + 1, â˜ƒxxxxxxxxxx - â˜ƒxxxxxxx + 1
                  );
                  int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxx;
                  int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxx;
                  int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx;

                  for(Pair<BlockPos, CompoundTag> â˜ƒxxxxxxxxxxxxxxxxx : â˜ƒxxxx) {
                     BlockPos â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx.getFirst();
                     â˜ƒxxxxxxxxxxxxx.fill(
                        â˜ƒxxxxxxxxxxxxxxxxxx.getX() - â˜ƒxxxxxxxxxxxxxx,
                        â˜ƒxxxxxxxxxxxxxxxxxx.getY() - â˜ƒxxxxxxxxxxxxxxx,
                        â˜ƒxxxxxxxxxxxxxxxxxx.getZ() - â˜ƒxxxxxxxxxxxxxxxx
                     );
                  }

                  updateShapeAtEdge(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx);
               }

               for(Pair<BlockPos, CompoundTag> â˜ƒxxxxxxxxxxxxx : â˜ƒxxxx) {
                  BlockPos â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.getFirst();
                  if (!â˜ƒ.getKnownShape()) {
                     BlockState â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxxxxx);
                     BlockState â˜ƒxxxxxxxxxxxxxxxx = Block.updateFromNeighbourShapes(â˜ƒxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒxxxxxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxxxx != â˜ƒxxxxxxxxxxxxxxxx) {
                        â˜ƒ.setBlock(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒ & -2 | 16);
                     }

                     â˜ƒ.blockUpdated(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx.getBlock());
                  }

                  if (â˜ƒxxxxxxxxxxxxx.getSecond() != null) {
                     BlockEntity â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getBlockEntity(â˜ƒxxxxxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxxx != null) {
                        â˜ƒxxxxxxxxxxxxxx.setChanged();
                     }
                  }
               }
            }

            if (!â˜ƒ.isIgnoreEntities()) {
               this.placeEntities(â˜ƒ, â˜ƒ, â˜ƒ.getMirror(), â˜ƒ.getRotation(), â˜ƒ.getRotationPivot(), â˜ƒx, â˜ƒ.shouldFinalizeEntities());
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static void updateShapeAtEdge(LevelAccessor var0, int var1, DiscreteVoxelShape var2, int var3, int var4, int var5) {
      â˜ƒ.forAllFaces((var5x, var6, var7, var8) -> {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ + var6, â˜ƒ + var7, â˜ƒ + var8);
         BlockPos â˜ƒx = â˜ƒ.relative(var5x);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
         BlockState â˜ƒxxxx = â˜ƒxx.updateShape(var5x, â˜ƒxxx, â˜ƒ, â˜ƒ, â˜ƒx);
         if (â˜ƒxx != â˜ƒxxxx) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒxxxx, â˜ƒ & -2);
         }

         BlockState â˜ƒ = â˜ƒxxx.updateShape(var5x.getOpposite(), â˜ƒxxxx, â˜ƒ, â˜ƒx, â˜ƒ);
         if (â˜ƒxxx != â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒx, â˜ƒ, â˜ƒ & -2);
         }
      });
   }

   public static List<StructureTemplate.StructureBlockInfo> processBlockInfos(
      LevelAccessor var0, BlockPos var1, BlockPos var2, StructurePlaceSettings var3, List<StructureTemplate.StructureBlockInfo> var4
   ) {
      List<StructureTemplate.StructureBlockInfo> â˜ƒ = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();

      for(StructureTemplate.StructureBlockInfo â˜ƒx : â˜ƒ) {
         BlockPos â˜ƒxx = calculateRelativePosition(â˜ƒ, â˜ƒx.pos).offset(â˜ƒ);
         StructureTemplate.StructureBlockInfo â˜ƒxxx = new StructureTemplate.StructureBlockInfo(â˜ƒxx, â˜ƒx.state, â˜ƒx.nbt != null ? â˜ƒx.nbt.copy() : null);
         Iterator<StructureProcessor> â˜ƒxxxx = â˜ƒ.getProcessors().iterator();

         while(â˜ƒxxx != null && â˜ƒxxxx.hasNext()) {
            â˜ƒxxx = ((StructureProcessor)â˜ƒxxxx.next()).processBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒ);
         }

         if (â˜ƒxxx != null) {
            â˜ƒ.add(â˜ƒxxx);
         }
      }

      return â˜ƒ;
   }

   private void placeEntities(ServerLevelAccessor var1, BlockPos var2, Mirror var3, Rotation var4, BlockPos var5, @Nullable BoundingBox var6, boolean var7) {
      for(StructureTemplate.StructureEntityInfo â˜ƒ : this.entityInfoList) {
         BlockPos â˜ƒx = transform(â˜ƒ.blockPos, â˜ƒ, â˜ƒ, â˜ƒ).offset(â˜ƒ);
         if (â˜ƒ == null || â˜ƒ.isInside(â˜ƒx)) {
            CompoundTag â˜ƒxx = â˜ƒ.nbt.copy();
            Vec3 â˜ƒxxx = transform(â˜ƒ.pos, â˜ƒ, â˜ƒ, â˜ƒ);
            Vec3 â˜ƒxxxx = â˜ƒxxx.add((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
            ListTag â˜ƒxxxxx = new ListTag();
            â˜ƒxxxxx.add(DoubleTag.valueOf(â˜ƒxxxx.x));
            â˜ƒxxxxx.add(DoubleTag.valueOf(â˜ƒxxxx.y));
            â˜ƒxxxxx.add(DoubleTag.valueOf(â˜ƒxxxx.z));
            â˜ƒxx.put("Pos", â˜ƒxxxxx);
            â˜ƒxx.remove("UUID");
            createEntityIgnoreException(â˜ƒ, â˜ƒxx).ifPresent(var6x -> {
               float â˜ƒ = var6x.mirror(â˜ƒ);
               â˜ƒ += var6x.getYRot() - var6x.rotate(â˜ƒ);
               var6x.moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ, var6x.getXRot());
               if (â˜ƒ && var6x instanceof Mob) {
                  ((Mob)var6x).finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(new BlockPos(â˜ƒ)), MobSpawnType.STRUCTURE, null, â˜ƒ);
               }

               â˜ƒ.addFreshEntityWithPassengers(var6x);
            });
         }
      }
   }

   private static Optional<Entity> createEntityIgnoreException(ServerLevelAccessor var0, CompoundTag var1) {
      try {
         return EntityType.create(â˜ƒ, â˜ƒ.getLevel());
      } catch (Exception var3) {
         return Optional.empty();
      }
   }

   public Vec3i getSize(Rotation var1) {
      switch(â˜ƒ) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            return new Vec3i(this.size.getZ(), this.size.getY(), this.size.getX());
         default:
            return this.size;
      }
   }

   public static BlockPos transform(BlockPos var0, Mirror var1, Rotation var2, BlockPos var3) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getY();
      int â˜ƒxx = â˜ƒ.getZ();
      boolean â˜ƒxxx = true;
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            â˜ƒxx = -â˜ƒxx;
            break;
         case FRONT_BACK:
            â˜ƒ = -â˜ƒ;
            break;
         default:
            â˜ƒxxx = false;
      }

      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      switch(â˜ƒ) {
         case COUNTERCLOCKWISE_90:
            return new BlockPos(â˜ƒ - â˜ƒx + â˜ƒxx, â˜ƒx, â˜ƒ + â˜ƒx - â˜ƒ);
         case CLOCKWISE_90:
            return new BlockPos(â˜ƒ + â˜ƒx - â˜ƒxx, â˜ƒx, â˜ƒx - â˜ƒ + â˜ƒ);
         case CLOCKWISE_180:
            return new BlockPos(â˜ƒ + â˜ƒ - â˜ƒ, â˜ƒx, â˜ƒx + â˜ƒx - â˜ƒxx);
         default:
            return â˜ƒxxx ? new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx) : â˜ƒ;
      }
   }

   public static Vec3 transform(Vec3 var0, Mirror var1, Rotation var2, BlockPos var3) {
      double â˜ƒ = â˜ƒ.x;
      double â˜ƒx = â˜ƒ.y;
      double â˜ƒxx = â˜ƒ.z;
      boolean â˜ƒxxx = true;
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            â˜ƒxx = 1.0 - â˜ƒxx;
            break;
         case FRONT_BACK:
            â˜ƒ = 1.0 - â˜ƒ;
            break;
         default:
            â˜ƒxxx = false;
      }

      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      switch(â˜ƒ) {
         case COUNTERCLOCKWISE_90:
            return new Vec3((double)(â˜ƒ - â˜ƒx) + â˜ƒxx, â˜ƒx, (double)(â˜ƒ + â˜ƒx + 1) - â˜ƒ);
         case CLOCKWISE_90:
            return new Vec3((double)(â˜ƒ + â˜ƒx + 1) - â˜ƒxx, â˜ƒx, (double)(â˜ƒx - â˜ƒ) + â˜ƒ);
         case CLOCKWISE_180:
            return new Vec3((double)(â˜ƒ + â˜ƒ + 1) - â˜ƒ, â˜ƒx, (double)(â˜ƒx + â˜ƒx + 1) - â˜ƒxx);
         default:
            return â˜ƒxxx ? new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx) : â˜ƒ;
      }
   }

   public BlockPos getZeroPositionWithTransform(BlockPos var1, Mirror var2, Rotation var3) {
      return getZeroPositionWithTransform(â˜ƒ, â˜ƒ, â˜ƒ, this.getSize().getX(), this.getSize().getZ());
   }

   public static BlockPos getZeroPositionWithTransform(BlockPos var0, Mirror var1, Rotation var2, int var3, int var4) {
      --â˜ƒ;
      --â˜ƒ;
      int â˜ƒ = â˜ƒ == Mirror.FRONT_BACK ? â˜ƒ : 0;
      int â˜ƒx = â˜ƒ == Mirror.LEFT_RIGHT ? â˜ƒ : 0;
      BlockPos â˜ƒxx = â˜ƒ;
      switch(â˜ƒ) {
         case COUNTERCLOCKWISE_90:
            â˜ƒxx = â˜ƒ.offset(â˜ƒx, 0, â˜ƒ - â˜ƒ);
            break;
         case CLOCKWISE_90:
            â˜ƒxx = â˜ƒ.offset(â˜ƒ - â˜ƒx, 0, â˜ƒ);
            break;
         case CLOCKWISE_180:
            â˜ƒxx = â˜ƒ.offset(â˜ƒ - â˜ƒ, 0, â˜ƒ - â˜ƒx);
            break;
         case NONE:
            â˜ƒxx = â˜ƒ.offset(â˜ƒ, 0, â˜ƒx);
      }

      return â˜ƒxx;
   }

   public BoundingBox getBoundingBox(StructurePlaceSettings var1, BlockPos var2) {
      return this.getBoundingBox(â˜ƒ, â˜ƒ.getRotation(), â˜ƒ.getRotationPivot(), â˜ƒ.getMirror());
   }

   public BoundingBox getBoundingBox(BlockPos var1, Rotation var2, BlockPos var3, Mirror var4) {
      return getBoundingBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.size);
   }

   @VisibleForTesting
   protected static BoundingBox getBoundingBox(BlockPos var0, Rotation var1, BlockPos var2, Mirror var3, Vec3i var4) {
      Vec3i â˜ƒ = â˜ƒ.offset(-1, -1, -1);
      BlockPos â˜ƒx = transform(BlockPos.ZERO, â˜ƒ, â˜ƒ, â˜ƒ);
      BlockPos â˜ƒxx = transform(BlockPos.ZERO.offset(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
      return BoundingBox.fromCorners(â˜ƒx, â˜ƒxx).move(â˜ƒ);
   }

   public CompoundTag save(CompoundTag var1) {
      if (this.palettes.isEmpty()) {
         â˜ƒ.put("blocks", new ListTag());
         â˜ƒ.put("palette", new ListTag());
      } else {
         List<StructureTemplate.SimplePalette> â˜ƒ = Lists.<StructureTemplate.SimplePalette>newArrayList();
         StructureTemplate.SimplePalette â˜ƒx = new StructureTemplate.SimplePalette();
         â˜ƒ.add(â˜ƒx);

         for(int â˜ƒxx = 1; â˜ƒxx < this.palettes.size(); ++â˜ƒxx) {
            â˜ƒ.add(new StructureTemplate.SimplePalette());
         }

         ListTag â˜ƒxx = new ListTag();
         List<StructureTemplate.StructureBlockInfo> â˜ƒxxx = ((StructureTemplate.Palette)this.palettes.get(0)).blocks();

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx.size(); ++â˜ƒxxxx) {
            StructureTemplate.StructureBlockInfo â˜ƒxxxxx = (StructureTemplate.StructureBlockInfo)â˜ƒxxx.get(â˜ƒxxxx);
            CompoundTag â˜ƒxxxxxx = new CompoundTag();
            â˜ƒxxxxxx.put("pos", this.newIntegerList(â˜ƒxxxxx.pos.getX(), â˜ƒxxxxx.pos.getY(), â˜ƒxxxxx.pos.getZ()));
            int â˜ƒxxxxxxx = â˜ƒx.idFor(â˜ƒxxxxx.state);
            â˜ƒxxxxxx.putInt("state", â˜ƒxxxxxxx);
            if (â˜ƒxxxxx.nbt != null) {
               â˜ƒxxxxxx.put("nbt", â˜ƒxxxxx.nbt);
            }

            â˜ƒxx.add(â˜ƒxxxxxx);

            for(int â˜ƒxxxxx = 1; â˜ƒxxxxx < this.palettes.size(); ++â˜ƒxxxxx) {
               StructureTemplate.SimplePalette â˜ƒxxxxxx = (StructureTemplate.SimplePalette)â˜ƒ.get(â˜ƒxxxxx);
               â˜ƒxxxxxx.addMapping(
                  ((StructureTemplate.StructureBlockInfo)((StructureTemplate.Palette)this.palettes.get(â˜ƒxxxxx)).blocks().get(â˜ƒxxxx)).state, â˜ƒxxxxxxx
               );
            }
         }

         â˜ƒ.put("blocks", â˜ƒxx);
         if (â˜ƒ.size() == 1) {
            ListTag â˜ƒxxxx = new ListTag();

            for(BlockState â˜ƒxxxxx : â˜ƒx) {
               â˜ƒxxxx.add(NbtUtils.writeBlockState(â˜ƒxxxxx));
            }

            â˜ƒ.put("palette", â˜ƒxxxx);
         } else {
            ListTag â˜ƒxxxx = new ListTag();

            for(StructureTemplate.SimplePalette â˜ƒxxxxx : â˜ƒ) {
               ListTag â˜ƒxxxxxx = new ListTag();

               for(BlockState â˜ƒxxxxxxx : â˜ƒxxxxx) {
                  â˜ƒxxxxxx.add(NbtUtils.writeBlockState(â˜ƒxxxxxxx));
               }

               â˜ƒxxxx.add(â˜ƒxxxxxx);
            }

            â˜ƒ.put("palettes", â˜ƒxxxx);
         }
      }

      ListTag â˜ƒ = new ListTag();

      for(StructureTemplate.StructureEntityInfo â˜ƒx : this.entityInfoList) {
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒxx.put("pos", this.newDoubleList(â˜ƒx.pos.x, â˜ƒx.pos.y, â˜ƒx.pos.z));
         â˜ƒxx.put("blockPos", this.newIntegerList(â˜ƒx.blockPos.getX(), â˜ƒx.blockPos.getY(), â˜ƒx.blockPos.getZ()));
         if (â˜ƒx.nbt != null) {
            â˜ƒxx.put("nbt", â˜ƒx.nbt);
         }

         â˜ƒ.add(â˜ƒxx);
      }

      â˜ƒ.put("entities", â˜ƒ);
      â˜ƒ.put("size", this.newIntegerList(this.size.getX(), this.size.getY(), this.size.getZ()));
      â˜ƒ.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
      return â˜ƒ;
   }

   public void load(CompoundTag var1) {
      this.palettes.clear();
      this.entityInfoList.clear();
      ListTag â˜ƒ = â˜ƒ.getList("size", 3);
      this.size = new Vec3i(â˜ƒ.getInt(0), â˜ƒ.getInt(1), â˜ƒ.getInt(2));
      ListTag â˜ƒx = â˜ƒ.getList("blocks", 10);
      if (â˜ƒ.contains("palettes", 9)) {
         ListTag â˜ƒxx = â˜ƒ.getList("palettes", 9);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
            this.loadPalette(â˜ƒxx.getList(â˜ƒxxx), â˜ƒx);
         }
      } else {
         this.loadPalette(â˜ƒ.getList("palette", 10), â˜ƒx);
      }

      ListTag â˜ƒ = â˜ƒ.getList("entities", 10);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         CompoundTag â˜ƒxx = â˜ƒ.getCompound(â˜ƒx);
         ListTag â˜ƒxxx = â˜ƒxx.getList("pos", 6);
         Vec3 â˜ƒxxxx = new Vec3(â˜ƒxxx.getDouble(0), â˜ƒxxx.getDouble(1), â˜ƒxxx.getDouble(2));
         ListTag â˜ƒxxxxx = â˜ƒxx.getList("blockPos", 3);
         BlockPos â˜ƒxxxxxx = new BlockPos(â˜ƒxxxxx.getInt(0), â˜ƒxxxxx.getInt(1), â˜ƒxxxxx.getInt(2));
         if (â˜ƒxx.contains("nbt")) {
            CompoundTag â˜ƒxxxxxxx = â˜ƒxx.getCompound("nbt");
            this.entityInfoList.add(new StructureTemplate.StructureEntityInfo(â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx));
         }
      }
   }

   private void loadPalette(ListTag var1, ListTag var2) {
      StructureTemplate.SimplePalette â˜ƒ = new StructureTemplate.SimplePalette();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         â˜ƒ.addMapping(NbtUtils.readBlockState(â˜ƒ.getCompound(â˜ƒx)), â˜ƒx);
      }

      List<StructureTemplate.StructureBlockInfo> â˜ƒx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
      List<StructureTemplate.StructureBlockInfo> â˜ƒxx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();
      List<StructureTemplate.StructureBlockInfo> â˜ƒxxx = Lists.<StructureTemplate.StructureBlockInfo>newArrayList();

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ.size(); ++â˜ƒxxxx) {
         CompoundTag â˜ƒxxxxxx = â˜ƒ.getCompound(â˜ƒxxxx);
         ListTag â˜ƒxxxxxxx = â˜ƒxxxxxx.getList("pos", 3);
         BlockPos â˜ƒxxxxxxxx = new BlockPos(â˜ƒxxxxxxx.getInt(0), â˜ƒxxxxxxx.getInt(1), â˜ƒxxxxxxx.getInt(2));
         BlockState â˜ƒxxxxxxxxx = â˜ƒ.stateFor(â˜ƒxxxxxx.getInt("state"));
         CompoundTag â˜ƒxxxxx;
         if (â˜ƒxxxxxx.contains("nbt")) {
            â˜ƒxxxxx = â˜ƒxxxxxx.getCompound("nbt");
         } else {
            â˜ƒxxxxx = null;
         }

         StructureTemplate.StructureBlockInfo â˜ƒxxxxx = new StructureTemplate.StructureBlockInfo(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxx);
         addToLists(â˜ƒxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }

      List<StructureTemplate.StructureBlockInfo> â˜ƒxxxx = buildInfoList(â˜ƒx, â˜ƒxx, â˜ƒxxx);
      this.palettes.add(new StructureTemplate.Palette(â˜ƒxxxx));
   }

   private ListTag newIntegerList(int... var1) {
      ListTag â˜ƒ = new ListTag();

      for(int â˜ƒx : â˜ƒ) {
         â˜ƒ.add(IntTag.valueOf(â˜ƒx));
      }

      return â˜ƒ;
   }

   private ListTag newDoubleList(double... var1) {
      ListTag â˜ƒ = new ListTag();

      for(double â˜ƒx : â˜ƒ) {
         â˜ƒ.add(DoubleTag.valueOf(â˜ƒx));
      }

      return â˜ƒ;
   }

   public static final class Palette {
      private final List<StructureTemplate.StructureBlockInfo> blocks;
      private final Map<Block, List<StructureTemplate.StructureBlockInfo>> cache = Maps.newHashMap();

      Palette(List<StructureTemplate.StructureBlockInfo> var1) {
         this.blocks = â˜ƒ;
      }

      public List<StructureTemplate.StructureBlockInfo> blocks() {
         return this.blocks;
      }

      public List<StructureTemplate.StructureBlockInfo> blocks(Block var1) {
         return (List<StructureTemplate.StructureBlockInfo>)this.cache
            .computeIfAbsent(â˜ƒ, var1x -> (List)this.blocks.stream().filter(var1xx -> var1xx.state.is(var1x)).collect(Collectors.toList()));
      }
   }

   static class SimplePalette implements Iterable<BlockState> {
      public static final BlockState DEFAULT_BLOCK_STATE = Blocks.AIR.defaultBlockState();
      private final IdMapper<BlockState> ids = new IdMapper<>(16);
      private int lastId;

      public int idFor(BlockState var1) {
         int â˜ƒ = this.ids.getId(â˜ƒ);
         if (â˜ƒ == -1) {
            â˜ƒ = this.lastId++;
            this.ids.addMapping(â˜ƒ, â˜ƒ);
         }

         return â˜ƒ;
      }

      @Nullable
      public BlockState stateFor(int var1) {
         BlockState â˜ƒ = this.ids.byId(â˜ƒ);
         return â˜ƒ == null ? DEFAULT_BLOCK_STATE : â˜ƒ;
      }

      public Iterator<BlockState> iterator() {
         return this.ids.iterator();
      }

      public void addMapping(BlockState var1, int var2) {
         this.ids.addMapping(â˜ƒ, â˜ƒ);
      }
   }

   public static class StructureBlockInfo {
      public final BlockPos pos;
      public final BlockState state;
      public final CompoundTag nbt;

      public StructureBlockInfo(BlockPos var1, BlockState var2, @Nullable CompoundTag var3) {
         this.pos = â˜ƒ;
         this.state = â˜ƒ;
         this.nbt = â˜ƒ;
      }

      public String toString() {
         return String.format("<StructureBlockInfo | %s | %s | %s>", this.pos, this.state, this.nbt);
      }
   }

   public static class StructureEntityInfo {
      public final Vec3 pos;
      public final BlockPos blockPos;
      public final CompoundTag nbt;

      public StructureEntityInfo(Vec3 var1, BlockPos var2, CompoundTag var3) {
         this.pos = â˜ƒ;
         this.blockPos = â˜ƒ;
         this.nbt = â˜ƒ;
      }
   }
}
