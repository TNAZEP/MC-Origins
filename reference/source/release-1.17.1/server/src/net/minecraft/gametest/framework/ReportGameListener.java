package net.minecraft.gametest.framework;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.exception.ExceptionUtils;

class ReportGameListener implements GameTestListener {
   private final GameTestInfo originalTestInfo;
   private final GameTestTicker testTicker;
   private final BlockPos structurePos;
   int attempts;
   int successes;

   public ReportGameListener(GameTestInfo var1, GameTestTicker var2, BlockPos var3) {
      this.originalTestInfo = â˜ƒ;
      this.testTicker = â˜ƒ;
      this.structurePos = â˜ƒ;
      this.attempts = 0;
      this.successes = 0;
   }

   @Override
   public void testStructureLoaded(GameTestInfo var1) {
      spawnBeacon(this.originalTestInfo, Blocks.LIGHT_GRAY_STAINED_GLASS);
      ++this.attempts;
   }

   @Override
   public void testPassed(GameTestInfo var1) {
      ++this.successes;
      if (!â˜ƒ.isFlaky()) {
         reportPassed(â˜ƒ, â˜ƒ.getTestName() + " passed!");
      } else {
         if (this.successes >= â˜ƒ.requiredSuccesses()) {
            reportPassed(â˜ƒ, â˜ƒ + " passed " + this.successes + " times of " + this.attempts + " attempts.");
         } else {
            say(
               this.originalTestInfo.getLevel(),
               ChatFormatting.GREEN,
               "Flaky test " + this.originalTestInfo + " succeeded, attempt: " + this.attempts + " successes: " + this.successes
            );
            this.rerunTest();
         }
      }
   }

   @Override
   public void testFailed(GameTestInfo var1) {
      if (!â˜ƒ.isFlaky()) {
         reportFailure(â˜ƒ, â˜ƒ.getError());
      } else {
         TestFunction â˜ƒ = this.originalTestInfo.getTestFunction();
         String â˜ƒx = "Flaky test " + this.originalTestInfo + " failed, attempt: " + this.attempts + "/" + â˜ƒ.getMaxAttempts();
         if (â˜ƒ.getRequiredSuccesses() > 1) {
            â˜ƒx = â˜ƒx + ", successes: " + this.successes + " (" + â˜ƒ.getRequiredSuccesses() + " required)";
         }

         say(this.originalTestInfo.getLevel(), ChatFormatting.YELLOW, â˜ƒx);
         if (â˜ƒ.maxAttempts() - this.attempts + this.successes >= â˜ƒ.requiredSuccesses()) {
            this.rerunTest();
         } else {
            reportFailure(â˜ƒ, new ExhaustedAttemptsException(this.attempts, this.successes, â˜ƒ));
         }
      }
   }

   public static void reportPassed(GameTestInfo var0, String var1) {
      spawnBeacon(â˜ƒ, Blocks.LIME_STAINED_GLASS);
      visualizePassedTest(â˜ƒ, â˜ƒ);
   }

   private static void visualizePassedTest(GameTestInfo var0, String var1) {
      say(â˜ƒ.getLevel(), ChatFormatting.GREEN, â˜ƒ);
      GlobalTestReporter.onTestSuccess(â˜ƒ);
   }

   protected static void reportFailure(GameTestInfo var0, Throwable var1) {
      spawnBeacon(â˜ƒ, â˜ƒ.isRequired() ? Blocks.RED_STAINED_GLASS : Blocks.ORANGE_STAINED_GLASS);
      spawnLectern(â˜ƒ, Util.describeError(â˜ƒ));
      visualizeFailedTest(â˜ƒ, â˜ƒ);
   }

   protected static void visualizeFailedTest(GameTestInfo var0, Throwable var1) {
      String â˜ƒx = â˜ƒ.getMessage() + (â˜ƒ.getCause() == null ? "" : " cause: " + Util.describeError(â˜ƒ.getCause()));
      String â˜ƒxx = (â˜ƒ.isRequired() ? "" : "(optional) ") + â˜ƒ.getTestName() + " failed! " + â˜ƒx;
      say(â˜ƒ.getLevel(), â˜ƒ.isRequired() ? ChatFormatting.RED : ChatFormatting.YELLOW, â˜ƒxx);
      Throwable â˜ƒxxx = MoreObjects.firstNonNull(ExceptionUtils.getRootCause(â˜ƒ), â˜ƒ);
      if (â˜ƒxxx instanceof GameTestAssertPosException â˜ƒ) {
         showRedBox(â˜ƒ.getLevel(), â˜ƒ.getAbsolutePos(), â˜ƒ.getMessageToShowAtBlock());
      }

      GlobalTestReporter.onTestFailed(â˜ƒ);
   }

   private void rerunTest() {
      this.originalTestInfo.clearStructure();
      GameTestInfo â˜ƒ = new GameTestInfo(this.originalTestInfo.getTestFunction(), this.originalTestInfo.getRotation(), this.originalTestInfo.getLevel());
      â˜ƒ.startExecution();
      this.testTicker.add(â˜ƒ);
      â˜ƒ.addListener(this);
      â˜ƒ.spawnStructure(this.structurePos, 2);
   }

   protected static void spawnBeacon(GameTestInfo var0, Block var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getStructureBlockPos();
      BlockPos â˜ƒxx = new BlockPos(-1, -1, -1);
      BlockPos â˜ƒxxx = StructureTemplate.transform(â˜ƒx.offset(â˜ƒxx), Mirror.NONE, â˜ƒ.getRotation(), â˜ƒx);
      â˜ƒ.setBlockAndUpdate(â˜ƒxxx, Blocks.BEACON.defaultBlockState().rotate(â˜ƒ.getRotation()));
      BlockPos â˜ƒxxxx = â˜ƒxxx.offset(0, 1, 0);
      â˜ƒ.setBlockAndUpdate(â˜ƒxxxx, â˜ƒ.defaultBlockState());

      for(int â˜ƒxxxxx = -1; â˜ƒxxxxx <= 1; ++â˜ƒxxxxx) {
         for(int â˜ƒxxxxxx = -1; â˜ƒxxxxxx <= 1; ++â˜ƒxxxxxx) {
            BlockPos â˜ƒxxxxxxx = â˜ƒxxx.offset(â˜ƒxxxxx, -1, â˜ƒxxxxxx);
            â˜ƒ.setBlockAndUpdate(â˜ƒxxxxxxx, Blocks.IRON_BLOCK.defaultBlockState());
         }
      }
   }

   private static void spawnLectern(GameTestInfo var0, String var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getStructureBlockPos();
      BlockPos â˜ƒxx = new BlockPos(-1, 1, -1);
      BlockPos â˜ƒxxx = StructureTemplate.transform(â˜ƒx.offset(â˜ƒxx), Mirror.NONE, â˜ƒ.getRotation(), â˜ƒx);
      â˜ƒ.setBlockAndUpdate(â˜ƒxxx, Blocks.LECTERN.defaultBlockState().rotate(â˜ƒ.getRotation()));
      BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
      ItemStack â˜ƒxxxxx = createBook(â˜ƒ.getTestName(), â˜ƒ.isRequired(), â˜ƒ);
      LecternBlock.tryPlaceBook(null, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
   }

   private static ItemStack createBook(String var0, boolean var1, String var2) {
      ItemStack â˜ƒ = new ItemStack(Items.WRITABLE_BOOK);
      ListTag â˜ƒx = new ListTag();
      StringBuffer â˜ƒxx = new StringBuffer();
      Arrays.stream(â˜ƒ.split("\\.")).forEach(var1x -> â˜ƒ.append(var1x).append('\n'));
      if (!â˜ƒ) {
         â˜ƒxx.append("(optional)\n");
      }

      â˜ƒxx.append("-------------------\n");
      â˜ƒx.add(StringTag.valueOf(â˜ƒxx + â˜ƒ));
      â˜ƒ.addTagElement("pages", â˜ƒx);
      return â˜ƒ;
   }

   protected static void say(ServerLevel var0, ChatFormatting var1, String var2) {
      â˜ƒ.getPlayers(var0x -> true).forEach(var2x -> var2x.sendMessage(new TextComponent(â˜ƒ).withStyle(â˜ƒ), Util.NIL_UUID));
   }

   private static void showRedBox(ServerLevel var0, BlockPos var1, String var2) {
      DebugPackets.sendGameTestAddMarker(â˜ƒ, â˜ƒ, â˜ƒ, -2130771968, Integer.MAX_VALUE);
   }
}
