package com.mojang.realmsclient.exception;

public class RetryCallException extends RealmsServiceException {
   public static final int DEFAULT_DELAY = 5;
   public final int delaySeconds;

   public RetryCallException(int var1, int var2) {
      super(â˜ƒ, "Retry operation", -1, "");
      if (â˜ƒ >= 0 && â˜ƒ <= 120) {
         this.delaySeconds = â˜ƒ;
      } else {
         this.delaySeconds = 5;
      }
   }
}
