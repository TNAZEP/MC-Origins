package net.minecraft.server;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class ChainedJsonException extends IOException {
   private final List<ChainedJsonException.Entry> entries = Lists.<ChainedJsonException.Entry>newArrayList();
   private final String message;

   public ChainedJsonException(String var1) {
      this.entries.add(new ChainedJsonException.Entry());
      this.message = â˜ƒ;
   }

   public ChainedJsonException(String var1, Throwable var2) {
      super(â˜ƒ);
      this.entries.add(new ChainedJsonException.Entry());
      this.message = â˜ƒ;
   }

   public void prependJsonKey(String var1) {
      ((ChainedJsonException.Entry)this.entries.get(0)).addJsonKey(â˜ƒ);
   }

   public void setFilenameAndFlush(String var1) {
      ((ChainedJsonException.Entry)this.entries.get(0)).filename = â˜ƒ;
      this.entries.add(0, new ChainedJsonException.Entry());
   }

   public String getMessage() {
      return "Invalid " + this.entries.get(this.entries.size() - 1) + ": " + this.message;
   }

   public static ChainedJsonException forException(Exception var0) {
      if (â˜ƒ instanceof ChainedJsonException) {
         return (ChainedJsonException)â˜ƒ;
      } else {
         String â˜ƒ = â˜ƒ.getMessage();
         if (â˜ƒ instanceof FileNotFoundException) {
            â˜ƒ = "File not found";
         }

         return new ChainedJsonException(â˜ƒ, â˜ƒ);
      }
   }

   public static class Entry {
      @Nullable
      String filename;
      private final List<String> jsonKeys = Lists.newArrayList();

      Entry() {
      }

      void addJsonKey(String var1) {
         this.jsonKeys.add(0, â˜ƒ);
      }

      @Nullable
      public String getFilename() {
         return this.filename;
      }

      public String getJsonKeys() {
         return StringUtils.join(this.jsonKeys, "->");
      }

      public String toString() {
         if (this.filename != null) {
            return this.jsonKeys.isEmpty() ? this.filename : this.filename + " " + this.getJsonKeys();
         } else {
            return this.jsonKeys.isEmpty() ? "(Unknown file)" : "(Unknown file) " + this.getJsonKeys();
         }
      }
   }
}
