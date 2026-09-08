package net.minecraft.client.model;

public class ModelUtils {
   public static float rotlerpRad(float var0, float var1, float var2) {
      float â˜ƒ = â˜ƒ - â˜ƒ;

      while(â˜ƒ < (float) -Math.PI) {
         â˜ƒ += (float) (Math.PI * 2);
      }

      while(â˜ƒ >= (float) Math.PI) {
         â˜ƒ -= (float) (Math.PI * 2);
      }

      return â˜ƒ + â˜ƒ * â˜ƒ;
   }
}
