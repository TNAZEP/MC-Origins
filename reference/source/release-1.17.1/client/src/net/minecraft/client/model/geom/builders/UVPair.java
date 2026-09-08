package net.minecraft.client.model.geom.builders;

public class UVPair {
   private final float u;
   private final float v;

   public UVPair(float var1, float var2) {
      this.u = â˜ƒ;
      this.v = â˜ƒ;
   }

   public float u() {
      return this.u;
   }

   public float v() {
      return this.v;
   }

   public String toString() {
      return "(" + this.u + "," + this.v + ")";
   }
}
