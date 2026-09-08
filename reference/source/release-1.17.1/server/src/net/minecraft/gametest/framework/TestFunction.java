package net.minecraft.gametest.framework;

import java.util.function.Consumer;
import net.minecraft.world.level.block.Rotation;

public class TestFunction {
   private final String batchName;
   private final String testName;
   private final String structureName;
   private final boolean required;
   private final int maxAttempts;
   private final int requiredSuccesses;
   private final Consumer<GameTestHelper> function;
   private final int maxTicks;
   private final long setupTicks;
   private final Rotation rotation;

   public TestFunction(String var1, String var2, String var3, int var4, long var5, boolean var7, Consumer<GameTestHelper> var8) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, Rotation.NONE, â˜ƒ, â˜ƒ, â˜ƒ, 1, 1, â˜ƒ);
   }

   public TestFunction(String var1, String var2, String var3, Rotation var4, int var5, long var6, boolean var8, Consumer<GameTestHelper> var9) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1, 1, â˜ƒ);
   }

   public TestFunction(
      String var1, String var2, String var3, Rotation var4, int var5, long var6, boolean var8, int var9, int var10, Consumer<GameTestHelper> var11
   ) {
      this.batchName = â˜ƒ;
      this.testName = â˜ƒ;
      this.structureName = â˜ƒ;
      this.rotation = â˜ƒ;
      this.maxTicks = â˜ƒ;
      this.required = â˜ƒ;
      this.requiredSuccesses = â˜ƒ;
      this.maxAttempts = â˜ƒ;
      this.function = â˜ƒ;
      this.setupTicks = â˜ƒ;
   }

   public void run(GameTestHelper var1) {
      this.function.accept(â˜ƒ);
   }

   public String getTestName() {
      return this.testName;
   }

   public String getStructureName() {
      return this.structureName;
   }

   public String toString() {
      return this.testName;
   }

   public int getMaxTicks() {
      return this.maxTicks;
   }

   public boolean isRequired() {
      return this.required;
   }

   public String getBatchName() {
      return this.batchName;
   }

   public long getSetupTicks() {
      return this.setupTicks;
   }

   public Rotation getRotation() {
      return this.rotation;
   }

   public boolean isFlaky() {
      return this.maxAttempts > 1;
   }

   public int getMaxAttempts() {
      return this.maxAttempts;
   }

   public int getRequiredSuccesses() {
      return this.requiredSuccesses;
   }
}
