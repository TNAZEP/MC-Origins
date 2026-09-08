package net.minecraft.gametest.framework;

import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;

public class GameTestBatch {
   public static final String DEFAULT_BATCH_NAME = "defaultBatch";
   private final String name;
   private final Collection<TestFunction> testFunctions;
   @Nullable
   private final Consumer<ServerLevel> beforeBatchFunction;
   @Nullable
   private final Consumer<ServerLevel> afterBatchFunction;

   public GameTestBatch(String var1, Collection<TestFunction> var2, @Nullable Consumer<ServerLevel> var3, @Nullable Consumer<ServerLevel> var4) {
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
      } else {
         this.name = â˜ƒ;
         this.testFunctions = â˜ƒ;
         this.beforeBatchFunction = â˜ƒ;
         this.afterBatchFunction = â˜ƒ;
      }
   }

   public String getName() {
      return this.name;
   }

   public Collection<TestFunction> getTestFunctions() {
      return this.testFunctions;
   }

   public void runBeforeBatchFunction(ServerLevel var1) {
      if (this.beforeBatchFunction != null) {
         this.beforeBatchFunction.accept(â˜ƒ);
      }
   }

   public void runAfterBatchFunction(ServerLevel var1) {
      if (this.afterBatchFunction != null) {
         this.afterBatchFunction.accept(â˜ƒ);
      }
   }
}
