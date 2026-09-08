package net.minecraft.gametest.framework;

public class GlobalTestReporter {
   private static TestReporter DELEGATE = new LogTestReporter();

   public static void replaceWith(TestReporter var0) {
      DELEGATE = â˜ƒ;
   }

   public static void onTestFailed(GameTestInfo var0) {
      DELEGATE.onTestFailed(â˜ƒ);
   }

   public static void onTestSuccess(GameTestInfo var0) {
      DELEGATE.onTestSuccess(â˜ƒ);
   }

   public static void finish() {
      DELEGATE.finish();
   }
}
