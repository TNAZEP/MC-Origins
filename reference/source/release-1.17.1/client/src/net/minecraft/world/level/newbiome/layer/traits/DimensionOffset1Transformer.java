package net.minecraft.world.level.newbiome.layer.traits;

public interface DimensionOffset1Transformer extends DimensionTransformer {
   @Override
   default int getParentX(int var1) {
      return â˜ƒ - 1;
   }

   @Override
   default int getParentY(int var1) {
      return â˜ƒ - 1;
   }
}
