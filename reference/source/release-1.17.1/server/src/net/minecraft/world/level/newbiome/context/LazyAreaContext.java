package net.minecraft.world.level.newbiome.context;

import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.world.level.levelgen.SimpleRandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;
import net.minecraft.world.level.newbiome.area.LazyArea;
import net.minecraft.world.level.newbiome.layer.traits.PixelTransformer;

public class LazyAreaContext implements BigContext<LazyArea> {
   private static final int MAX_CACHE = 1024;
   private final Long2IntLinkedOpenHashMap cache;
   private final int maxCache;
   private final ImprovedNoise biomeNoise;
   private final long seed;
   private long rval;

   public LazyAreaContext(int var1, long var2, long var4) {
      this.seed = mixSeed(â˜ƒ, â˜ƒ);
      this.biomeNoise = new ImprovedNoise(new SimpleRandomSource(â˜ƒ));
      this.cache = new Long2IntLinkedOpenHashMap(16, 0.25F);
      this.cache.defaultReturnValue(Integer.MIN_VALUE);
      this.maxCache = â˜ƒ;
   }

   public LazyArea createResult(PixelTransformer var1) {
      return new LazyArea(this.cache, this.maxCache, â˜ƒ);
   }

   public LazyArea createResult(PixelTransformer var1, LazyArea var2) {
      return new LazyArea(this.cache, Math.min(1024, â˜ƒ.getMaxCache() * 4), â˜ƒ);
   }

   public LazyArea createResult(PixelTransformer var1, LazyArea var2, LazyArea var3) {
      return new LazyArea(this.cache, Math.min(1024, Math.max(â˜ƒ.getMaxCache(), â˜ƒ.getMaxCache()) * 4), â˜ƒ);
   }

   @Override
   public void initRandom(long var1, long var3) {
      long â˜ƒ = this.seed;
      â˜ƒ = LinearCongruentialGenerator.next(â˜ƒ, â˜ƒ);
      â˜ƒ = LinearCongruentialGenerator.next(â˜ƒ, â˜ƒ);
      â˜ƒ = LinearCongruentialGenerator.next(â˜ƒ, â˜ƒ);
      â˜ƒ = LinearCongruentialGenerator.next(â˜ƒ, â˜ƒ);
      this.rval = â˜ƒ;
   }

   @Override
   public int nextRandom(int var1) {
      int â˜ƒ = Math.floorMod(this.rval >> 24, â˜ƒ);
      this.rval = LinearCongruentialGenerator.next(this.rval, this.seed);
      return â˜ƒ;
   }

   @Override
   public ImprovedNoise getBiomeNoise() {
      return this.biomeNoise;
   }

   private static long mixSeed(long var0, long var2) {
      long var4 = LinearCongruentialGenerator.next(â˜ƒ, â˜ƒ);
      var4 = LinearCongruentialGenerator.next(var4, â˜ƒ);
      var4 = LinearCongruentialGenerator.next(var4, â˜ƒ);
      long var6 = LinearCongruentialGenerator.next(â˜ƒ, var4);
      var6 = LinearCongruentialGenerator.next(var6, var4);
      return LinearCongruentialGenerator.next(var6, var4);
   }
}
