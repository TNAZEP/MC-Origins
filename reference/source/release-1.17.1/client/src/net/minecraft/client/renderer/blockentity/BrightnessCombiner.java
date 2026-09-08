package net.minecraft.client.renderer.blockentity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BrightnessCombiner<S extends BlockEntity> implements DoubleBlockCombiner.Combiner<S, Int2IntFunction> {
   public Int2IntFunction acceptDouble(S var1, S var2) {
      return var2x -> {
         int â˜ƒ = LevelRenderer.getLightColor(â˜ƒ.getLevel(), â˜ƒ.getBlockPos());
         int â˜ƒx = LevelRenderer.getLightColor(â˜ƒ.getLevel(), â˜ƒ.getBlockPos());
         int â˜ƒxx = LightTexture.block(â˜ƒ);
         int â˜ƒxxx = LightTexture.block(â˜ƒx);
         int â˜ƒxxxx = LightTexture.sky(â˜ƒ);
         int â˜ƒxxxxx = LightTexture.sky(â˜ƒx);
         return LightTexture.pack(Math.max(â˜ƒxx, â˜ƒxxx), Math.max(â˜ƒxxxx, â˜ƒxxxxx));
      };
   }

   public Int2IntFunction acceptSingle(S var1) {
      return var0 -> var0;
   }

   public Int2IntFunction acceptNone() {
      return var0 -> var0;
   }
}
