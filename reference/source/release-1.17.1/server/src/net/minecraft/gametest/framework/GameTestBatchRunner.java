package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameTestBatchRunner {
   private static final Logger LOGGER = LogManager.getLogger();
   private final BlockPos firstTestNorthWestCorner;
   final ServerLevel level;
   private final GameTestTicker testTicker;
   private final int testsPerRow;
   private final List<GameTestInfo> allTestInfos;
   private final List<Pair<GameTestBatch, Collection<GameTestInfo>>> batches;
   private final BlockPos.MutableBlockPos nextTestNorthWestCorner;

   public GameTestBatchRunner(Collection<GameTestBatch> var1, BlockPos var2, Rotation var3, ServerLevel var4, GameTestTicker var5, int var6) {
      this.nextTestNorthWestCorner = â˜ƒ.mutable();
      this.firstTestNorthWestCorner = â˜ƒ;
      this.level = â˜ƒ;
      this.testTicker = â˜ƒ;
      this.testsPerRow = â˜ƒ;
      this.batches = (List)â˜ƒ.stream()
         .map(
            var2x -> {
               Collection<GameTestInfo> â˜ƒ = (Collection)var2x.getTestFunctions()
                  .stream()
                  .map(var2xx -> new GameTestInfo(var2xx, â˜ƒ, â˜ƒ))
                  .collect(ImmutableList.toImmutableList());
               return Pair.of(var2x, â˜ƒ);
            }
         )
         .collect(ImmutableList.toImmutableList());
      this.allTestInfos = (List)this.batches.stream().flatMap(var0 -> ((Collection)var0.getSecond()).stream()).collect(ImmutableList.toImmutableList());
   }

   public List<GameTestInfo> getTestInfos() {
      return this.allTestInfos;
   }

   public void start() {
      this.runBatch(0);
   }

   void runBatch(final int var1) {
      if (â˜ƒ < this.batches.size()) {
         Pair<GameTestBatch, Collection<GameTestInfo>> â˜ƒ = (Pair)this.batches.get(â˜ƒ);
         final GameTestBatch â˜ƒx = â˜ƒ.getFirst();
         Collection<GameTestInfo> â˜ƒxx = (Collection)â˜ƒ.getSecond();
         Map<GameTestInfo, BlockPos> â˜ƒxxx = this.createStructuresForBatch(â˜ƒxx);
         String â˜ƒxxxx = â˜ƒx.getName();
         LOGGER.info("Running test batch '{}' ({} tests)...", â˜ƒxxxx, â˜ƒxx.size());
         â˜ƒx.runBeforeBatchFunction(this.level);
         final MultipleTestTracker â˜ƒxxxxx = new MultipleTestTracker();
         â˜ƒxx.forEach(â˜ƒxxxxx::addTestToTrack);
         â˜ƒxxxxx.addListener(new GameTestListener() {
            private void testCompleted() {
               if (â˜ƒ.isDone()) {
                  â˜ƒ.runAfterBatchFunction(GameTestBatchRunner.this.level);
                  GameTestBatchRunner.this.runBatch(â˜ƒ + 1);
               }
            }

            @Override
            public void testStructureLoaded(GameTestInfo var1x) {
            }

            @Override
            public void testPassed(GameTestInfo var1x) {
               this.testCompleted();
            }

            @Override
            public void testFailed(GameTestInfo var1x) {
               this.testCompleted();
            }
         });
         â˜ƒxx.forEach(var2x -> {
            BlockPos â˜ƒ = (BlockPos)â˜ƒ.get(var2x);
            GameTestRunner.runTest(var2x, â˜ƒ, this.testTicker);
         });
      }
   }

   private Map<GameTestInfo, BlockPos> createStructuresForBatch(Collection<GameTestInfo> var1) {
      Map<GameTestInfo, BlockPos> â˜ƒ = Maps.<GameTestInfo, BlockPos>newHashMap();
      int â˜ƒx = 0;
      AABB â˜ƒxx = new AABB(this.nextTestNorthWestCorner);

      for(GameTestInfo â˜ƒxxx : â˜ƒ) {
         BlockPos â˜ƒxxxx = new BlockPos(this.nextTestNorthWestCorner);
         StructureBlockEntity â˜ƒxxxxx = StructureUtils.spawnStructure(â˜ƒxxx.getStructureName(), â˜ƒxxxx, â˜ƒxxx.getRotation(), 2, this.level, true);
         AABB â˜ƒxxxxxx = StructureUtils.getStructureBounds(â˜ƒxxxxx);
         â˜ƒxxx.setStructureBlockPos(â˜ƒxxxxx.getBlockPos());
         â˜ƒ.put(â˜ƒxxx, new BlockPos(this.nextTestNorthWestCorner));
         â˜ƒxx = â˜ƒxx.minmax(â˜ƒxxxxxx);
         this.nextTestNorthWestCorner.move((int)â˜ƒxxxxxx.getXsize() + 5, 0, 0);
         if (â˜ƒx++ % this.testsPerRow == this.testsPerRow - 1) {
            this.nextTestNorthWestCorner.move(0, 0, (int)â˜ƒxx.getZsize() + 6);
            this.nextTestNorthWestCorner.setX(this.firstTestNorthWestCorner.getX());
            â˜ƒxx = new AABB(this.nextTestNorthWestCorner);
         }
      }

      return â˜ƒ;
   }
}
