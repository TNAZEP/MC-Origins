package net.minecraft.gametest.framework;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.io.IOUtils;

public class TestCommand {
   private static final int DEFAULT_CLEAR_RADIUS = 200;
   private static final int MAX_CLEAR_RADIUS = 1024;
   private static final int STRUCTURE_BLOCK_NEARBY_SEARCH_RADIUS = 15;
   private static final int STRUCTURE_BLOCK_FULL_SEARCH_RADIUS = 200;
   private static final int TEST_POS_Z_OFFSET_FROM_PLAYER = 3;
   private static final int SHOW_POS_DURATION_MS = 10000;
   private static final int DEFAULT_X_SIZE = 5;
   private static final int DEFAULT_Y_SIZE = 5;
   private static final int DEFAULT_Z_SIZE = 5;

   public static void register(CommandDispatcher<CommandSourceStack> var0) {
      â˜ƒ.register(
         Commands.literal("test")
            .then(Commands.literal("runthis").executes(var0x -> runNearbyTest(var0x.getSource())))
            .then(Commands.literal("runthese").executes(var0x -> runAllNearbyTests(var0x.getSource())))
            .then(
               Commands.literal("runfailed")
                  .executes(var0x -> runLastFailedTests(var0x.getSource(), false, 0, 8))
                  .then(
                     Commands.argument("onlyRequiredTests", BoolArgumentType.bool())
                        .executes(var0x -> runLastFailedTests(var0x.getSource(), BoolArgumentType.getBool(var0x, "onlyRequiredTests"), 0, 8))
                        .then(
                           Commands.argument("rotationSteps", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> runLastFailedTests(
                                       var0x.getSource(),
                                       BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                       IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                       8
                                    )
                              )
                              .then(
                                 Commands.argument("testsPerRow", IntegerArgumentType.integer())
                                    .executes(
                                       var0x -> runLastFailedTests(
                                             var0x.getSource(),
                                             BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                             IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                             IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("run")
                  .then(
                     Commands.argument("testName", TestFunctionArgument.testFunctionArgument())
                        .executes(var0x -> runTest(var0x.getSource(), TestFunctionArgument.getTestFunction(var0x, "testName"), 0))
                        .then(
                           Commands.argument("rotationSteps", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> runTest(
                                       var0x.getSource(),
                                       TestFunctionArgument.getTestFunction(var0x, "testName"),
                                       IntegerArgumentType.getInteger(var0x, "rotationSteps")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("runall")
                  .executes(var0x -> runAllTests(var0x.getSource(), 0, 8))
                  .then(
                     Commands.argument("testClassName", TestClassNameArgument.testClassName())
                        .executes(var0x -> runAllTestsInClass(var0x.getSource(), TestClassNameArgument.getTestClassName(var0x, "testClassName"), 0, 8))
                        .then(
                           Commands.argument("rotationSteps", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> runAllTestsInClass(
                                       var0x.getSource(),
                                       TestClassNameArgument.getTestClassName(var0x, "testClassName"),
                                       IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                       8
                                    )
                              )
                              .then(
                                 Commands.argument("testsPerRow", IntegerArgumentType.integer())
                                    .executes(
                                       var0x -> runAllTestsInClass(
                                             var0x.getSource(),
                                             TestClassNameArgument.getTestClassName(var0x, "testClassName"),
                                             IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                             IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                          )
                                    )
                              )
                        )
                  )
                  .then(
                     Commands.argument("rotationSteps", IntegerArgumentType.integer())
                        .executes(var0x -> runAllTests(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "rotationSteps"), 8))
                        .then(
                           Commands.argument("testsPerRow", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> runAllTests(
                                       var0x.getSource(),
                                       IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                       IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("export")
                  .then(
                     Commands.argument("testName", StringArgumentType.word())
                        .executes(var0x -> exportTestStructure(var0x.getSource(), StringArgumentType.getString(var0x, "testName")))
                  )
            )
            .then(Commands.literal("exportthis").executes(var0x -> exportNearestTestStructure(var0x.getSource())))
            .then(
               Commands.literal("import")
                  .then(
                     Commands.argument("testName", StringArgumentType.word())
                        .executes(var0x -> importTestStructure(var0x.getSource(), StringArgumentType.getString(var0x, "testName")))
                  )
            )
            .then(
               Commands.literal("pos")
                  .executes(var0x -> showPos(var0x.getSource(), "pos"))
                  .then(
                     Commands.argument("var", StringArgumentType.word())
                        .executes(var0x -> showPos(var0x.getSource(), StringArgumentType.getString(var0x, "var")))
                  )
            )
            .then(
               Commands.literal("create")
                  .then(
                     Commands.argument("testName", StringArgumentType.word())
                        .executes(var0x -> createNewStructure(var0x.getSource(), StringArgumentType.getString(var0x, "testName"), 5, 5, 5))
                        .then(
                           Commands.argument("width", IntegerArgumentType.integer())
                              .executes(
                                 var0x -> createNewStructure(
                                       var0x.getSource(),
                                       StringArgumentType.getString(var0x, "testName"),
                                       IntegerArgumentType.getInteger(var0x, "width"),
                                       IntegerArgumentType.getInteger(var0x, "width"),
                                       IntegerArgumentType.getInteger(var0x, "width")
                                    )
                              )
                              .then(
                                 Commands.argument("height", IntegerArgumentType.integer())
                                    .then(
                                       Commands.argument("depth", IntegerArgumentType.integer())
                                          .executes(
                                             var0x -> createNewStructure(
                                                   var0x.getSource(),
                                                   StringArgumentType.getString(var0x, "testName"),
                                                   IntegerArgumentType.getInteger(var0x, "width"),
                                                   IntegerArgumentType.getInteger(var0x, "height"),
                                                   IntegerArgumentType.getInteger(var0x, "depth")
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
            .then(
               Commands.literal("clearall")
                  .executes(var0x -> clearAllTests(var0x.getSource(), 200))
                  .then(
                     Commands.argument("radius", IntegerArgumentType.integer())
                        .executes(var0x -> clearAllTests(var0x.getSource(), IntegerArgumentType.getInteger(var0x, "radius")))
                  )
            )
      );
   }

   private static int createNewStructure(CommandSourceStack var0, String var1, int var2, int var3, int var4) {
      if (â˜ƒ <= 48 && â˜ƒ <= 48 && â˜ƒ <= 48) {
         ServerLevel â˜ƒ = â˜ƒ.getLevel();
         BlockPos â˜ƒx = new BlockPos(â˜ƒ.getPosition());
         BlockPos â˜ƒxx = new BlockPos(â˜ƒx.getX(), â˜ƒ.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, â˜ƒx).getY(), â˜ƒx.getZ() + 3);
         StructureUtils.createNewEmptyStructureBlock(â˜ƒ.toLowerCase(), â˜ƒxx, new Vec3i(â˜ƒ, â˜ƒ, â˜ƒ), Rotation.NONE, â˜ƒ);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ; ++â˜ƒxxxx) {
               BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx.getX() + â˜ƒxxx, â˜ƒxx.getY() + 1, â˜ƒxx.getZ() + â˜ƒxxxx);
               Block â˜ƒxxxxxx = Blocks.POLISHED_ANDESITE;
               BlockInput â˜ƒxxxxxxx = new BlockInput(â˜ƒxxxxxx.defaultBlockState(), Collections.emptySet(), null);
               â˜ƒxxxxxxx.place(â˜ƒ, â˜ƒxxxxx, 2);
            }
         }

         StructureUtils.addCommandBlockAndButtonToStartTest(â˜ƒxx, new BlockPos(1, 0, -1), Rotation.NONE, â˜ƒ);
         return 0;
      } else {
         throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
      }
   }

   private static int showPos(CommandSourceStack var0, String var1) throws CommandSyntaxException {
      BlockHitResult â˜ƒ = (BlockHitResult)â˜ƒ.getPlayerOrException().pick(10.0, 1.0F, false);
      BlockPos â˜ƒx = â˜ƒ.getBlockPos();
      ServerLevel â˜ƒxx = â˜ƒ.getLevel();
      Optional<BlockPos> â˜ƒxxx = StructureUtils.findStructureBlockContainingPos(â˜ƒx, 15, â˜ƒxx);
      if (!â˜ƒxxx.isPresent()) {
         â˜ƒxxx = StructureUtils.findStructureBlockContainingPos(â˜ƒx, 200, â˜ƒxx);
      }

      if (!â˜ƒxxx.isPresent()) {
         â˜ƒ.sendFailure(new TextComponent("Can't find a structure block that contains the targeted pos " + â˜ƒx));
         return 0;
      } else {
         StructureBlockEntity â˜ƒ = (StructureBlockEntity)â˜ƒxx.getBlockEntity((BlockPos)â˜ƒxxx.get());
         BlockPos â˜ƒx = â˜ƒx.subtract((Vec3i)â˜ƒxxx.get());
         String â˜ƒxx = â˜ƒx.getX() + ", " + â˜ƒx.getY() + ", " + â˜ƒx.getZ();
         String â˜ƒxxx = â˜ƒ.getStructurePath();
         Component â˜ƒxxxx = new TextComponent(â˜ƒxx)
            .setStyle(
               Style.EMPTY
                  .withBold(true)
                  .withColor(ChatFormatting.GREEN)
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent("Click to copy to clipboard")))
                  .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + â˜ƒ + " = new BlockPos(" + â˜ƒxx + ");"))
            );
         â˜ƒ.sendSuccess(new TextComponent("Position relative to " + â˜ƒxxx + ": ").append(â˜ƒxxxx), false);
         DebugPackets.sendGameTestAddMarker(â˜ƒxx, new BlockPos(â˜ƒx), â˜ƒxx, -2147418368, 10000);
         return 1;
      }
   }

   private static int runNearbyTest(CommandSourceStack var0) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getPosition());
      ServerLevel â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = StructureUtils.findNearestStructureBlock(â˜ƒ, 15, â˜ƒx);
      if (â˜ƒxx == null) {
         say(â˜ƒx, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
         return 0;
      } else {
         GameTestRunner.clearMarkers(â˜ƒx);
         runTest(â˜ƒx, â˜ƒxx, null);
         return 1;
      }
   }

   private static int runAllNearbyTests(CommandSourceStack var0) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getPosition());
      ServerLevel â˜ƒx = â˜ƒ.getLevel();
      Collection<BlockPos> â˜ƒxx = StructureUtils.findStructureBlocks(â˜ƒ, 200, â˜ƒx);
      if (â˜ƒxx.isEmpty()) {
         say(â˜ƒx, "Couldn't find any structure blocks within 200 block radius", ChatFormatting.RED);
         return 1;
      } else {
         GameTestRunner.clearMarkers(â˜ƒx);
         say(â˜ƒ, "Running " + â˜ƒxx.size() + " tests...");
         MultipleTestTracker â˜ƒ = new MultipleTestTracker();
         â˜ƒxx.forEach(var2x -> runTest(â˜ƒ, var2x, â˜ƒ));
         return 1;
      }
   }

   private static void runTest(ServerLevel var0, BlockPos var1, @Nullable MultipleTestTracker var2) {
      StructureBlockEntity â˜ƒ = (StructureBlockEntity)â˜ƒ.getBlockEntity(â˜ƒ);
      String â˜ƒx = â˜ƒ.getStructurePath();
      TestFunction â˜ƒxx = GameTestRegistry.getTestFunction(â˜ƒx);
      GameTestInfo â˜ƒxxx = new GameTestInfo(â˜ƒxx, â˜ƒ.getRotation(), â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.addTestToTrack(â˜ƒxxx);
         â˜ƒxxx.addListener(new TestCommand.TestSummaryDisplayer(â˜ƒ, â˜ƒ));
      }

      runTestPreparation(â˜ƒxx, â˜ƒ);
      AABB â˜ƒ = StructureUtils.getStructureBounds(â˜ƒ);
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.minX, â˜ƒ.minY, â˜ƒ.minZ);
      GameTestRunner.runTest(â˜ƒxxx, â˜ƒx, GameTestTicker.SINGLETON);
   }

   static void showTestSummaryIfAllDone(ServerLevel var0, MultipleTestTracker var1) {
      if (â˜ƒ.isDone()) {
         say(â˜ƒ, "GameTest done! " + â˜ƒ.getTotalCount() + " tests were run", ChatFormatting.WHITE);
         if (â˜ƒ.hasFailedRequired()) {
            say(â˜ƒ, â˜ƒ.getFailedRequiredCount() + " required tests failed :(", ChatFormatting.RED);
         } else {
            say(â˜ƒ, "All required tests passed :)", ChatFormatting.GREEN);
         }

         if (â˜ƒ.hasFailedOptional()) {
            say(â˜ƒ, â˜ƒ.getFailedOptionalCount() + " optional tests failed", ChatFormatting.GRAY);
         }
      }
   }

   private static int clearAllTests(CommandSourceStack var0, int var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      GameTestRunner.clearMarkers(â˜ƒ);
      BlockPos â˜ƒx = new BlockPos(
         â˜ƒ.getPosition().x,
         (double)â˜ƒ.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(â˜ƒ.getPosition())).getY(),
         â˜ƒ.getPosition().z
      );
      GameTestRunner.clearAllTests(â˜ƒ, â˜ƒx, GameTestTicker.SINGLETON, Mth.clamp(â˜ƒ, 0, 1024));
      return 1;
   }

   private static int runTest(CommandSourceStack var0, TestFunction var1, int var2) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getPosition());
      int â˜ƒxx = â˜ƒ.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, â˜ƒx).getY();
      BlockPos â˜ƒxxx = new BlockPos(â˜ƒx.getX(), â˜ƒxx, â˜ƒx.getZ() + 3);
      GameTestRunner.clearMarkers(â˜ƒ);
      runTestPreparation(â˜ƒ, â˜ƒ);
      Rotation â˜ƒxxxx = StructureUtils.getRotationForRotationSteps(â˜ƒ);
      GameTestInfo â˜ƒxxxxx = new GameTestInfo(â˜ƒ, â˜ƒxxxx, â˜ƒ);
      GameTestRunner.runTest(â˜ƒxxxxx, â˜ƒxxx, GameTestTicker.SINGLETON);
      return 1;
   }

   private static void runTestPreparation(TestFunction var0, ServerLevel var1) {
      Consumer<ServerLevel> â˜ƒ = GameTestRegistry.getBeforeBatchFunction(â˜ƒ.getBatchName());
      if (â˜ƒ != null) {
         â˜ƒ.accept(â˜ƒ);
      }
   }

   private static int runAllTests(CommandSourceStack var0, int var1, int var2) {
      GameTestRunner.clearMarkers(â˜ƒ.getLevel());
      Collection<TestFunction> â˜ƒ = GameTestRegistry.getAllTestFunctions();
      say(â˜ƒ, "Running all " + â˜ƒ.size() + " tests...");
      GameTestRegistry.forgetFailedTests();
      runTests(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return 1;
   }

   private static int runAllTestsInClass(CommandSourceStack var0, String var1, int var2, int var3) {
      Collection<TestFunction> â˜ƒ = GameTestRegistry.getTestFunctionsForClassName(â˜ƒ);
      GameTestRunner.clearMarkers(â˜ƒ.getLevel());
      say(â˜ƒ, "Running " + â˜ƒ.size() + " tests from " + â˜ƒ + "...");
      GameTestRegistry.forgetFailedTests();
      runTests(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return 1;
   }

   private static int runLastFailedTests(CommandSourceStack var0, boolean var1, int var2, int var3) {
      Collection<TestFunction> â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = (Collection)GameTestRegistry.getLastFailedTests().stream().filter(TestFunction::isRequired).collect(Collectors.toList());
      } else {
         â˜ƒ = GameTestRegistry.getLastFailedTests();
      }

      if (â˜ƒ.isEmpty()) {
         say(â˜ƒ, "No failed tests to rerun");
         return 0;
      } else {
         GameTestRunner.clearMarkers(â˜ƒ.getLevel());
         say(â˜ƒ, "Rerunning " + â˜ƒ.size() + " failed tests (" + (â˜ƒ ? "only required tests" : "including optional tests") + ")");
         runTests(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return 1;
      }
   }

   private static void runTests(CommandSourceStack var0, Collection<TestFunction> var1, int var2, int var3) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getPosition());
      BlockPos â˜ƒx = new BlockPos(â˜ƒ.getX(), â˜ƒ.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, â˜ƒ).getY(), â˜ƒ.getZ() + 3);
      ServerLevel â˜ƒxx = â˜ƒ.getLevel();
      Rotation â˜ƒxxx = StructureUtils.getRotationForRotationSteps(â˜ƒ);
      Collection<GameTestInfo> â˜ƒxxxx = GameTestRunner.runTests(â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxx, GameTestTicker.SINGLETON, â˜ƒ);
      MultipleTestTracker â˜ƒxxxxx = new MultipleTestTracker(â˜ƒxxxx);
      â˜ƒxxxxx.addListener(new TestCommand.TestSummaryDisplayer(â˜ƒxx, â˜ƒxxxxx));
      â˜ƒxxxxx.addFailureListener(var0x -> GameTestRegistry.rememberFailedTest(var0x.getTestFunction()));
   }

   private static void say(CommandSourceStack var0, String var1) {
      â˜ƒ.sendSuccess(new TextComponent(â˜ƒ), false);
   }

   private static int exportNearestTestStructure(CommandSourceStack var0) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getPosition());
      ServerLevel â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = StructureUtils.findNearestStructureBlock(â˜ƒ, 15, â˜ƒx);
      if (â˜ƒxx == null) {
         say(â˜ƒx, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
         return 0;
      } else {
         StructureBlockEntity â˜ƒ = (StructureBlockEntity)â˜ƒx.getBlockEntity(â˜ƒxx);
         String â˜ƒx = â˜ƒ.getStructurePath();
         return exportTestStructure(â˜ƒ, â˜ƒx);
      }
   }

   private static int exportTestStructure(CommandSourceStack var0, String var1) {
      Path â˜ƒ = Paths.get(StructureUtils.testStructuresDir);
      ResourceLocation â˜ƒx = new ResourceLocation("minecraft", â˜ƒ);
      Path â˜ƒxx = â˜ƒ.getLevel().getStructureManager().createPathToStructure(â˜ƒx, ".nbt");
      Path â˜ƒxxx = NbtToSnbt.convertStructure(â˜ƒxx, â˜ƒ, â˜ƒ);
      if (â˜ƒxxx == null) {
         say(â˜ƒ, "Failed to export " + â˜ƒxx);
         return 1;
      } else {
         try {
            Files.createDirectories(â˜ƒxxx.getParent());
         } catch (IOException var7) {
            say(â˜ƒ, "Could not create folder " + â˜ƒxxx.getParent());
            var7.printStackTrace();
            return 1;
         }

         say(â˜ƒ, "Exported " + â˜ƒ + " to " + â˜ƒxxx.toAbsolutePath());
         return 0;
      }
   }

   private static int importTestStructure(CommandSourceStack var0, String var1) {
      Path â˜ƒ = Paths.get(StructureUtils.testStructuresDir, â˜ƒ + ".snbt");
      ResourceLocation â˜ƒx = new ResourceLocation("minecraft", â˜ƒ);
      Path â˜ƒxx = â˜ƒ.getLevel().getStructureManager().createPathToStructure(â˜ƒx, ".nbt");

      try {
         BufferedReader â˜ƒxxx = Files.newBufferedReader(â˜ƒ);
         String â˜ƒxxxx = IOUtils.toString(â˜ƒxxx);
         Files.createDirectories(â˜ƒxx.getParent());
         OutputStream â˜ƒxxxxx = Files.newOutputStream(â˜ƒxx);

         try {
            NbtIo.writeCompressed(NbtUtils.snbtToStructure(â˜ƒxxxx), â˜ƒxxxxx);
         } catch (Throwable var11) {
            if (â˜ƒxxxxx != null) {
               try {
                  â˜ƒxxxxx.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
               }
            }

            throw var11;
         }

         if (â˜ƒxxxxx != null) {
            â˜ƒxxxxx.close();
         }

         say(â˜ƒ, "Imported to " + â˜ƒxx.toAbsolutePath());
         return 0;
      } catch (CommandSyntaxException | IOException var12) {
         System.err.println("Failed to load structure " + â˜ƒ);
         var12.printStackTrace();
         return 1;
      }
   }

   private static void say(ServerLevel var0, String var1, ChatFormatting var2) {
      â˜ƒ.getPlayers(var0x -> true).forEach(var2x -> var2x.sendMessage(new TextComponent(â˜ƒ + â˜ƒ), Util.NIL_UUID));
   }

   static class TestSummaryDisplayer implements GameTestListener {
      private final ServerLevel level;
      private final MultipleTestTracker tracker;

      public TestSummaryDisplayer(ServerLevel var1, MultipleTestTracker var2) {
         this.level = â˜ƒ;
         this.tracker = â˜ƒ;
      }

      @Override
      public void testStructureLoaded(GameTestInfo var1) {
      }

      @Override
      public void testPassed(GameTestInfo var1) {
         TestCommand.showTestSummaryIfAllDone(this.level, this.tracker);
      }

      @Override
      public void testFailed(GameTestInfo var1) {
         TestCommand.showTestSummaryIfAllDone(this.level, this.tracker);
      }
   }
}
