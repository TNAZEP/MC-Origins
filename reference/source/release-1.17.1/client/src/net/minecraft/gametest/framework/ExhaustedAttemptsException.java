package net.minecraft.gametest.framework;

class ExhaustedAttemptsException extends Throwable {
   public ExhaustedAttemptsException(int var1, int var2, GameTestInfo var3) {
      super(
         "Not enough successes: "
            + â˜ƒ
            + " out of "
            + â˜ƒ
            + " attempts. Required successes: "
            + â˜ƒ.requiredSuccesses()
            + ". max attempts: "
            + â˜ƒ.maxAttempts()
            + ".",
         â˜ƒ.getError()
      );
   }
}
