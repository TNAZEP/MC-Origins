package net.minecraft.world.level.levelgen;

@FunctionalInterface
public interface NoiseModifier {
   NoiseModifier PASSTHROUGH = (var0, var2, var3, var4) -> var0;

   double modifyNoise(double var1, int var3, int var4, int var5);
}
