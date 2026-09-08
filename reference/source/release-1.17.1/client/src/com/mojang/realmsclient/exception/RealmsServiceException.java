package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.RealmsError;
import net.minecraft.client.resources.language.I18n;

public class RealmsServiceException extends Exception {
   public final int httpResultCode;
   public final String httpResponseContent;
   public final int errorCode;
   public final String errorMsg;

   public RealmsServiceException(int var1, String var2, RealmsError var3) {
      super(â˜ƒ);
      this.httpResultCode = â˜ƒ;
      this.httpResponseContent = â˜ƒ;
      this.errorCode = â˜ƒ.getErrorCode();
      this.errorMsg = â˜ƒ.getErrorMessage();
   }

   public RealmsServiceException(int var1, String var2, int var3, String var4) {
      super(â˜ƒ);
      this.httpResultCode = â˜ƒ;
      this.httpResponseContent = â˜ƒ;
      this.errorCode = â˜ƒ;
      this.errorMsg = â˜ƒ;
   }

   public String toString() {
      if (this.errorCode == -1) {
         return "Realms (" + this.httpResultCode + ") " + this.httpResponseContent;
      } else {
         String â˜ƒ = "mco.errorMessage." + this.errorCode;
         String â˜ƒx = I18n.get(â˜ƒ);
         return (â˜ƒx.equals(â˜ƒ) ? this.errorMsg : â˜ƒx) + " - " + this.errorCode;
      }
   }
}
