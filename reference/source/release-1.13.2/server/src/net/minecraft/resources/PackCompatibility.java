package net.minecraft.resources;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public enum PackCompatibility {
   TOO_OLD("old"),
   TOO_NEW("new"),
   COMPATIBLE("compatible");

   private final ITextComponent field_198975_d;
   private final ITextComponent field_198976_e;

   private PackCompatibility(String var3) {
      this.field_198975_d = new TextComponentTranslation("resourcePack.incompatible." + ☃);
      this.field_198976_e = new TextComponentTranslation("resourcePack.incompatible.confirm." + ☃);
   }

   public boolean func_198968_a() {
      return this == COMPATIBLE;
   }

   public static PackCompatibility func_198969_a(int var0) {
      if (☃ < 4) {
         return TOO_OLD;
      } else {
         return ☃ > 4 ? TOO_NEW : COMPATIBLE;
      }
   }
}
