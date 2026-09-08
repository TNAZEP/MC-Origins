package com.mojang.realmsclient.gui.screens;

public class UploadResult {
   public final int statusCode;
   public final String errorMessage;

   UploadResult(int var1, String var2) {
      this.statusCode = â˜ƒ;
      this.errorMessage = â˜ƒ;
   }

   public static class Builder {
      private int statusCode = -1;
      private String errorMessage;

      public UploadResult.Builder withStatusCode(int var1) {
         this.statusCode = â˜ƒ;
         return this;
      }

      public UploadResult.Builder withErrorMessage(String var1) {
         this.errorMessage = â˜ƒ;
         return this;
      }

      public UploadResult build() {
         return new UploadResult(this.statusCode, this.errorMessage);
      }
   }
}
