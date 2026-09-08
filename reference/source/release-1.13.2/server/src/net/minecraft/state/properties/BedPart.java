package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum BedPart implements IStringSerializable {
   HEAD("head"),
   FOOT("foot");

   private final String field_177036_c;

   private BedPart(String var3) {
      this.field_177036_c = ☃;
   }

   public String toString() {
      return this.field_177036_c;
   }

   @Override
   public String func_176610_l() {
      return this.field_177036_c;
   }
}
