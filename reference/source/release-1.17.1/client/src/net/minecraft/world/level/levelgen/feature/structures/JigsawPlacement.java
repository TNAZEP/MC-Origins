package net.minecraft.world.level.levelgen.feature.structures;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JigsawPlacement {
   static final Logger LOGGER = LogManager.getLogger();

   public static void addPieces(
      RegistryAccess var0,
      JigsawConfiguration var1,
      JigsawPlacement.PieceFactory var2,
      ChunkGenerator var3,
      StructureManager var4,
      BlockPos var5,
      StructurePieceAccessor var6,
      Random var7,
      boolean var8,
      boolean var9,
      LevelHeightAccessor var10
   ) {
      StructureFeature.bootstrap();
      List<PoolElementStructurePiece> â˜ƒ = Lists.<PoolElementStructurePiece>newArrayList();
      Registry<StructureTemplatePool> â˜ƒx = â˜ƒ.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
      Rotation â˜ƒxx = Rotation.getRandom(â˜ƒ);
      StructureTemplatePool â˜ƒxxx = (StructureTemplatePool)â˜ƒ.startPool().get();
      StructurePoolElement â˜ƒxxxx = â˜ƒxxx.getRandomTemplate(â˜ƒ);
      if (â˜ƒxxxx != EmptyPoolElement.INSTANCE) {
         PoolElementStructurePiece â˜ƒxxxxxx = â˜ƒ.create(â˜ƒ, â˜ƒxxxx, â˜ƒ, â˜ƒxxxx.getGroundLevelDelta(), â˜ƒxx, â˜ƒxxxx.getBoundingBox(â˜ƒ, â˜ƒ, â˜ƒxx));
         BoundingBox â˜ƒxxxxxxx = â˜ƒxxxxxx.getBoundingBox();
         int â˜ƒxxxxxxxx = (â˜ƒxxxxxxx.maxX() + â˜ƒxxxxxxx.minX()) / 2;
         int â˜ƒxxxxxxxxx = (â˜ƒxxxxxxx.maxZ() + â˜ƒxxxxxxx.minZ()) / 2;
         int â˜ƒxxxxx;
         if (â˜ƒ) {
            â˜ƒxxxxx = â˜ƒ.getY() + â˜ƒ.getFirstFreeHeight(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
         } else {
            â˜ƒxxxxx = â˜ƒ.getY();
         }

         int â˜ƒxxxxx = â˜ƒxxxxxxx.minY() + â˜ƒxxxxxx.getGroundLevelDelta();
         â˜ƒxxxxxx.move(0, â˜ƒxxxxx - â˜ƒxxxxx, 0);
         â˜ƒ.add(â˜ƒxxxxxx);
         if (â˜ƒ.maxDepth() > 0) {
            int â˜ƒxxxxxx = 80;
            AABB â˜ƒxxxxxxx = new AABB(
               (double)(â˜ƒxxxxxxxx - 80),
               (double)(â˜ƒxxxxx - 80),
               (double)(â˜ƒxxxxxxxxx - 80),
               (double)(â˜ƒxxxxxxxx + 80 + 1),
               (double)(â˜ƒxxxxx + 80 + 1),
               (double)(â˜ƒxxxxxxxxx + 80 + 1)
            );
            JigsawPlacement.Placer â˜ƒxxxxxxxx = new JigsawPlacement.Placer(â˜ƒx, â˜ƒ.maxDepth(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒxxxxxxxx.placing
               .addLast(
                  new JigsawPlacement.PieceState(
                     â˜ƒxxxxxx,
                     new MutableObject<>(Shapes.join(Shapes.create(â˜ƒxxxxxxx), Shapes.create(AABB.of(â˜ƒxxxxxxx)), BooleanOp.ONLY_FIRST)),
                     â˜ƒxxxxx + 80,
                     0
                  )
               );

            while(!â˜ƒxxxxxxxx.placing.isEmpty()) {
               JigsawPlacement.PieceState â˜ƒxxxxxxxxx = (JigsawPlacement.PieceState)â˜ƒxxxxxxxx.placing.removeFirst();
               â˜ƒxxxxxxxx.tryPlacingChildren(â˜ƒxxxxxxxxx.piece, â˜ƒxxxxxxxxx.free, â˜ƒxxxxxxxxx.boundsTop, â˜ƒxxxxxxxxx.depth, â˜ƒ, â˜ƒ);
            }

            â˜ƒ.forEach(â˜ƒ::addPiece);
         }
      }
   }

   public static void addPieces(
      RegistryAccess var0,
      PoolElementStructurePiece var1,
      int var2,
      JigsawPlacement.PieceFactory var3,
      ChunkGenerator var4,
      StructureManager var5,
      List<? super PoolElementStructurePiece> var6,
      Random var7,
      LevelHeightAccessor var8
   ) {
      Registry<StructureTemplatePool> â˜ƒ = â˜ƒ.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
      JigsawPlacement.Placer â˜ƒx = new JigsawPlacement.Placer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒx.placing.addLast(new JigsawPlacement.PieceState(â˜ƒ, new MutableObject<>(Shapes.INFINITY), 0, 0));

      while(!â˜ƒx.placing.isEmpty()) {
         JigsawPlacement.PieceState â˜ƒxx = (JigsawPlacement.PieceState)â˜ƒx.placing.removeFirst();
         â˜ƒx.tryPlacingChildren(â˜ƒxx.piece, â˜ƒxx.free, â˜ƒxx.boundsTop, â˜ƒxx.depth, false, â˜ƒ);
      }
   }

   public interface PieceFactory {
      PoolElementStructurePiece create(StructureManager var1, StructurePoolElement var2, BlockPos var3, int var4, Rotation var5, BoundingBox var6);
   }

   static final class PieceState {
      final PoolElementStructurePiece piece;
      final MutableObject<VoxelShape> free;
      final int boundsTop;
      final int depth;

      PieceState(PoolElementStructurePiece var1, MutableObject<VoxelShape> var2, int var3, int var4) {
         this.piece = â˜ƒ;
         this.free = â˜ƒ;
         this.boundsTop = â˜ƒ;
         this.depth = â˜ƒ;
      }
   }

   static final class Placer {
      private final Registry<StructureTemplatePool> pools;
      private final int maxDepth;
      private final JigsawPlacement.PieceFactory factory;
      private final ChunkGenerator chunkGenerator;
      private final StructureManager structureManager;
      private final List<? super PoolElementStructurePiece> pieces;
      private final Random random;
      final Deque<JigsawPlacement.PieceState> placing = Queues.<JigsawPlacement.PieceState>newArrayDeque();

      Placer(
         Registry<StructureTemplatePool> var1,
         int var2,
         JigsawPlacement.PieceFactory var3,
         ChunkGenerator var4,
         StructureManager var5,
         List<? super PoolElementStructurePiece> var6,
         Random var7
      ) {
         this.pools = â˜ƒ;
         this.maxDepth = â˜ƒ;
         this.factory = â˜ƒ;
         this.chunkGenerator = â˜ƒ;
         this.structureManager = â˜ƒ;
         this.pieces = â˜ƒ;
         this.random = â˜ƒ;
      }

      void tryPlacingChildren(PoolElementStructurePiece var1, MutableObject<VoxelShape> var2, int var3, int var4, boolean var5, LevelHeightAccessor var6) {
         StructurePoolElement â˜ƒ = â˜ƒ.getElement();
         BlockPos â˜ƒx = â˜ƒ.getPosition();
         Rotation â˜ƒxx = â˜ƒ.getRotation();
         StructureTemplatePool.Projection â˜ƒxxx = â˜ƒ.getProjection();
         boolean â˜ƒxxxx = â˜ƒxxx == StructureTemplatePool.Projection.RIGID;
         MutableObject<VoxelShape> â˜ƒxxxxx = new MutableObject<>();
         BoundingBox â˜ƒxxxxxx = â˜ƒ.getBoundingBox();
         int â˜ƒxxxxxxx = â˜ƒxxxxxx.minY();

         label137:
         for(StructureTemplate.StructureBlockInfo â˜ƒxxxxxxxx : â˜ƒ.getShuffledJigsawBlocks(this.structureManager, â˜ƒx, â˜ƒxx, this.random)) {
            Direction â˜ƒxxxxxxxxx = JigsawBlock.getFrontFacing(â˜ƒxxxxxxxx.state);
            BlockPos â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx.pos;
            BlockPos â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.relative(â˜ƒxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getY() - â˜ƒxxxxxxx;
            int â˜ƒxxxxxxxxxxxxx = -1;
            ResourceLocation â˜ƒxxxxxxxxxxxxxx = new ResourceLocation(â˜ƒxxxxxxxx.nbt.getString("pool"));
            Optional<StructureTemplatePool> â˜ƒxxxxxxxxxxxxxxx = this.pools.getOptional(â˜ƒxxxxxxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxxxxxx.isPresent()
               && (((StructureTemplatePool)â˜ƒxxxxxxxxxxxxxxx.get()).size() != 0 || Objects.equals(â˜ƒxxxxxxxxxxxxxx, Pools.EMPTY.location()))) {
               ResourceLocation â˜ƒxxxxxxxxxxxxxxxx = ((StructureTemplatePool)â˜ƒxxxxxxxxxxxxxxx.get()).getFallback();
               Optional<StructureTemplatePool> â˜ƒxxxxxxxxxxxxxxxxx = this.pools.getOptional(â˜ƒxxxxxxxxxxxxxxxx);
               if (â˜ƒxxxxxxxxxxxxxxxxx.isPresent()
                  && (((StructureTemplatePool)â˜ƒxxxxxxxxxxxxxxxxx.get()).size() != 0 || Objects.equals(â˜ƒxxxxxxxxxxxxxxxx, Pools.EMPTY.location()))) {
                  boolean â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxx.isInside(â˜ƒxxxxxxxxxxx);
                  MutableObject<VoxelShape> â˜ƒxxxxxxxxxxxxxxxxxx;
                  int â˜ƒxxxxxxxxxxxxxxxxxxx;
                  if (â˜ƒxxxxxxxxxxxxxxxxxxxx) {
                     â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxx;
                     â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx;
                     if (â˜ƒxxxxx.getValue() == null) {
                        â˜ƒxxxxx.setValue(Shapes.create(AABB.of(â˜ƒxxxxxx)));
                     }
                  } else {
                     â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ;
                     â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ;
                  }

                  List<StructurePoolElement> â˜ƒxxxxxxxxxxxxxxxxxx = Lists.<StructurePoolElement>newArrayList();
                  if (â˜ƒ != this.maxDepth) {
                     â˜ƒxxxxxxxxxxxxxxxxxx.addAll(((StructureTemplatePool)â˜ƒxxxxxxxxxxxxxxx.get()).getShuffledTemplates(this.random));
                  }

                  â˜ƒxxxxxxxxxxxxxxxxxx.addAll(((StructureTemplatePool)â˜ƒxxxxxxxxxxxxxxxxx.get()).getShuffledTemplates(this.random));

                  for(StructurePoolElement â˜ƒxxxxxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxxxxxxx) {
                     if (â˜ƒxxxxxxxxxxxxxxxxxx == EmptyPoolElement.INSTANCE) {
                        break;
                     }

                     for(Rotation â˜ƒxxxxxxxxxxxxxxxxxxx : Rotation.getShuffled(this.random)) {
                        List<StructureTemplate.StructureBlockInfo> â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.getShuffledJigsawBlocks(
                           this.structureManager, BlockPos.ZERO, â˜ƒxxxxxxxxxxxxxxxxxxx, this.random
                        );
                        BoundingBox â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.getBoundingBox(
                           this.structureManager, BlockPos.ZERO, â˜ƒxxxxxxxxxxxxxxxxxxx
                        );
                        int â˜ƒxxxxxxxxxxxxxxxxxxxx;
                        if (â˜ƒ && â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getYSpan() <= 16) {
                           â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx.stream().mapToInt(var2x -> {
                              if (!â˜ƒ.isInside(var2x.pos.relative(JigsawBlock.getFrontFacing(var2x.state)))) {
                                 return 0;
                              } else {
                                 ResourceLocation â˜ƒ = new ResourceLocation(var2x.nbt.getString("pool"));
                                 Optional<StructureTemplatePool> â˜ƒx = this.pools.getOptional(â˜ƒ);
                                 Optional<StructureTemplatePool> â˜ƒxx = â˜ƒx.flatMap(var1x -> this.pools.getOptional(var1x.getFallback()));
                                 int â˜ƒxxx = â˜ƒx.map(var1x -> var1x.getMaxSize(this.structureManager)).orElse(0);
                                 int â˜ƒxxxx = â˜ƒxx.map(var1x -> var1x.getMaxSize(this.structureManager)).orElse(0);
                                 return Math.max(â˜ƒxxx, â˜ƒxxxx);
                              }
                           }).max().orElse(0);
                        } else {
                           â˜ƒxxxxxxxxxxxxxxxxxxxx = 0;
                        }

                        for(StructureTemplate.StructureBlockInfo â˜ƒxxxxxxxxxxxxxxxxxxxx : â˜ƒxxxxxxxxxxxxxxxxxxxxx) {
                           if (JigsawBlock.canAttach(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx)) {
                              BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxx.pos;
                              BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.subtract(â˜ƒxxxxxxxxxxxxxxxxxxxxxx);
                              BoundingBox â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.getBoundingBox(
                                 this.structureManager, â˜ƒxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx
                              );
                              int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.minY();
                              StructureTemplatePool.Projection â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.getProjection();
                              boolean â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx == StructureTemplatePool.Projection.RIGID;
                              int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx.getY();
                              int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx
                                 - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                 + JigsawBlock.getFrontFacing(â˜ƒxxxxxxxx.state).getStepY();
                              int â˜ƒxxxxxxxxxxxxxxxxxxxxx;
                              if (â˜ƒxxxx && â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                 â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              } else {
                                 if (â˜ƒxxxxxxxxxxxxx == -1) {
                                    â˜ƒxxxxxxxxxxxxx = this.chunkGenerator
                                       .getFirstFreeHeight(â˜ƒxxxxxxxxxx.getX(), â˜ƒxxxxxxxxxx.getZ(), Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
                                 }

                                 â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              }

                              int â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx;
                              BoundingBox â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.moved(0, â˜ƒxxxxxxxxxxxxxxxxxxxxx, 0);
                              BlockPos â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxx.offset(0, â˜ƒxxxxxxxxxxxxxxxxxxxxx, 0);
                              if (â˜ƒxxxxxxxxxxxxxxxxxxxx > 0) {
                                 int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                                    â˜ƒxxxxxxxxxxxxxxxxxxxx + 1, â˜ƒxxxxxxxxxxxxxxxxxxxxxx.maxY() - â˜ƒxxxxxxxxxxxxxxxxxxxxxx.minY()
                                 );
                                 â˜ƒxxxxxxxxxxxxxxxxxxxxxx.encapsulate(
                                    new BlockPos(
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxx.minX(),
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxx.minY() + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxx.minZ()
                                    )
                                 );
                              }

                              if (!Shapes.joinIsNotEmpty(
                                 â˜ƒxxxxxxxxxxxxxxxxxx.getValue(), Shapes.create(AABB.of(â˜ƒxxxxxxxxxxxxxxxxxxxxxx).deflate(0.25)), BooleanOp.ONLY_SECOND
                              )) {
                                 â˜ƒxxxxxxxxxxxxxxxxxx.setValue(
                                    Shapes.joinUnoptimized(
                                       â˜ƒxxxxxxxxxxxxxxxxxx.getValue(), Shapes.create(AABB.of(â˜ƒxxxxxxxxxxxxxxxxxxxxxx)), BooleanOp.ONLY_FIRST
                                    )
                                 );
                                 int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getGroundLevelDelta();
                                 int â˜ƒxxxxxxxxxxxxxxxxxxxxx;
                                 if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                    â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 } else {
                                    â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx.getGroundLevelDelta();
                                 }

                                 PoolElementStructurePiece â˜ƒxxxxxxxxxxxxxxxxxxxxxx = this.factory
                                    .create(
                                       this.structureManager,
                                       â˜ƒxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxx
                                    );
                                 int â˜ƒxxxxxxxxxxxxxxxxxxxxx;
                                 if (â˜ƒxxxx) {
                                    â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxx + â˜ƒxxxxxxxxxxxx;
                                 } else if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                    â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 } else {
                                    if (â˜ƒxxxxxxxxxxxxx == -1) {
                                       â˜ƒxxxxxxxxxxxxx = this.chunkGenerator
                                          .getFirstFreeHeight(â˜ƒxxxxxxxxxx.getX(), â˜ƒxxxxxxxxxx.getZ(), Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
                                    }

                                    â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 2;
                                 }

                                 â˜ƒ.addJunction(
                                    new JigsawJunction(
                                       â˜ƒxxxxxxxxxxx.getX(),
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxx.getZ(),
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx
                                    )
                                 );
                                 â˜ƒxxxxxxxxxxxxxxxxxxxxxx.addJunction(
                                    new JigsawJunction(
                                       â˜ƒxxxxxxxxxx.getX(),
                                       â˜ƒxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxxxxxxxxx.getZ(),
                                       -â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       â˜ƒxxx
                                    )
                                 );
                                 this.pieces.add(â˜ƒxxxxxxxxxxxxxxxxxxxxxx);
                                 if (â˜ƒ + 1 <= this.maxDepth) {
                                    this.placing
                                       .addLast(
                                          new JigsawPlacement.PieceState(â˜ƒxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ + 1)
                                       );
                                 }
                                 continue label137;
                              }
                           }
                        }
                     }
                  }
               } else {
                  JigsawPlacement.LOGGER.warn("Empty or non-existent fallback pool: {}", â˜ƒxxxxxxxxxxxxxxxx);
               }
            } else {
               JigsawPlacement.LOGGER.warn("Empty or non-existent pool: {}", â˜ƒxxxxxxxxxxxxxx);
            }
         }
      }
   }
}
