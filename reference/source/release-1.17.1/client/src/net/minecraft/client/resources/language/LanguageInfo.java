package net.minecraft.client.resources.language;

import com.mojang.bridge.game.Language;

public class LanguageInfo implements Language, Comparable<LanguageInfo> {
   private final String code;
   private final String region;
   private final String name;
   private final boolean bidirectional;

   public LanguageInfo(String var1, String var2, String var3, boolean var4) {
      this.code = â˜ƒ;
      this.region = â˜ƒ;
      this.name = â˜ƒ;
      this.bidirectional = â˜ƒ;
   }

   @Override
   public String getCode() {
      return this.code;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getRegion() {
      return this.region;
   }

   public boolean isBidirectional() {
      return this.bidirectional;
   }

   public String toString() {
      return String.format("%s (%s)", this.name, this.region);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return !(â˜ƒ instanceof LanguageInfo) ? false : this.code.equals(((LanguageInfo)â˜ƒ).code);
      }
   }

   public int hashCode() {
      return this.code.hashCode();
   }

   public int compareTo(LanguageInfo var1) {
      return this.code.compareTo(â˜ƒ.code);
   }
}
