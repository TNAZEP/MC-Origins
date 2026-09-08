package net.minecraft.gametest.framework;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.apache.commons.lang3.mutable.MutableInt;

public class GameTestRunner {
   private static final int MAX_TESTS_PER_BATCH = 100;
   public static final int PADDING_AROUND_EACH_STRUCTURE = 2;
   public static final int SPACE_BETWEEN_COLUMNS = 5;
   public static final int SPACE_BETWEEN_ROWS = 6;
   public static final int DEFAULT_TESTS_PER_ROW = 8;

   public static void runTest(GameTestInfo var0, BlockPos var1, GameTestTicker var2) {
      â˜ƒ.startExecution();
      â˜ƒ.add(â˜ƒ);
      â˜ƒ.addListener(new ReportGameListener(â˜ƒ, â˜ƒ, â˜ƒ));
      â˜ƒ.spawnStructure(â˜ƒ, 2);
   }

   public static Collection<GameTestInfo> runTestBatches(
      Collection<GameTestBatch> var0, BlockPos var1, Rotation var2, ServerLevel var3, GameTestTicker var4, int var5
   ) {
      GameTestBatchRunner â˜ƒ = new GameTestBatchRunner(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.start();
      return â˜ƒ.getTestInfos();
   }

   public static Collection<GameTestInfo> runTests(Collection<TestFunction> var0, BlockPos var1, Rotation var2, ServerLevel var3, GameTestTicker var4, int var5) {
      return runTestBatches(groupTestsIntoBatches(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static Collection<GameTestBatch> groupTestsIntoBatches(Collection<TestFunction> var0) {
      Map<String, List<TestFunction>> â˜ƒ = (Map)â˜ƒ.stream().collect(Collectors.groupingBy(TestFunction::getBatchName));
      return (Collection<GameTestBatch>)â˜ƒ.entrySet()
         .stream()
         .flatMap(
            var0x -> {
               String â˜ƒ = (String)var0x.getKey();
               Consumer<ServerLevel> â˜ƒx = GameTestRegistry.getBeforeBatchFunction(â˜ƒ);
               Consumer<ServerLevel> â˜ƒxx = GameTestRegistry.getAfterBatchFunction(â˜ƒ);
               MutableInt â˜ƒxxx = new MutableInt();
               Collection<TestFunction> â˜ƒxxxx = (Collection)var0x.getValue();
               return Streams.stream(Iterables.partition(â˜ƒxxxx, 100))
                  .map(var4x -> new GameTestBatch(â˜ƒ + ":" + â˜ƒ.incrementAndGet(), ImmutableList.<TestFunction>copyOf(var4x), â˜ƒ, â˜ƒ));
            }
         )
         .collect(ImmutableList.toImmutableList());
   }

   public static void clearAllTests(ServerLevel var0, BlockPos var1, GameTestTicker var2, int var3) {
      â˜ƒ.clear();
      BlockPos â˜ƒ = â˜ƒ.offset(-â˜ƒ, 0, -â˜ƒ);
      BlockPos â˜ƒx = â˜ƒ.offset(â˜ƒ, 0, â˜ƒ);
      BlockPos.betweenClosedStream(â˜ƒ, â˜ƒx).filter(var1x -> â˜ƒ.getBlockState(var1x).is(Blocks.STRUCTURE_BLOCK)).forEach(var1x -> {
         StructureBlockEntity â˜ƒ = (StructureBlockEntity)â˜ƒ.getBlockEntity(var1x);
         BlockPos â˜ƒx = â˜ƒ.getBlockPos();
         BoundingBox â˜ƒxx = StructureUtils.getStructureBoundingBox(â˜ƒ);
         StructureUtils.clearSpaceForStructure(â˜ƒxx, â˜ƒx.getY(), â˜ƒ);
      });
   }

   public static void clearMarkers(ServerLevel var0) {
      DebugPackets.sendGameTestClearPacket(â˜ƒ);
   }
}
