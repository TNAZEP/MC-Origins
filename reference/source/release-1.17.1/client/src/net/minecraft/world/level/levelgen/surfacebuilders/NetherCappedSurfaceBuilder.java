package net.minecraft.world.level.levelgen.surfacebuilders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import java.util.Comparator;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public abstract class NetherCappedSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
   private long seed;
   private ImmutableMap<BlockState, PerlinNoise> floorNoises = ImmutableMap.of();
   private ImmutableMap<BlockState, PerlinNoise> ceilingNoises = ImmutableMap.of();
   private PerlinNoise patchNoise;

   public NetherCappedSurfaceBuilder(Codec<SurfaceBuilderBaseConfiguration> var1) {
      super(â˜ƒ);
   }

   public void apply(
      Random var1,
      ChunkAccess var2,
      Biome var3,
      int var4,
      int var5,
      int var6,
      double var7,
      BlockState var9,
      BlockState var10,
      int var11,
      int var12,
      long var13,
      SurfaceBuilderBaseConfiguration var15
   ) {
      int â˜ƒ = â˜ƒ + 1;
      int â˜ƒx = â˜ƒ & 15;
      int â˜ƒxx = â˜ƒ & 15;
      int â˜ƒxxx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      int â˜ƒxxxx = (int)(â˜ƒ / 3.0 + 3.0 + â˜ƒ.nextDouble() * 0.25);
      double â˜ƒxxxxx = 0.03125;
      boolean â˜ƒxxxxxx = this.patchNoise.getValue((double)â˜ƒ * 0.03125, 109.0, (double)â˜ƒ * 0.03125) * 75.0 + â˜ƒ.nextDouble() > 0.0;
      BlockState â˜ƒxxxxxxx = (BlockState)((Entry)this.ceilingNoises
            .entrySet()
            .stream()
            .max(Comparator.comparing(var3x -> ((PerlinNoise)var3x.getValue()).getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ)))
            .get())
         .getKey();
      BlockState â˜ƒxxxxxxxx = (BlockState)((Entry)this.floorNoises
            .entrySet()
            .stream()
            .max(Comparator.comparing(var3x -> ((PerlinNoise)var3x.getValue()).getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ)))
            .get())
         .getKey();
      BlockPos.MutableBlockPos â˜ƒxxxxxxxxx = new BlockPos.MutableBlockPos();
      BlockState â˜ƒxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxx.set(â˜ƒx, 128, â˜ƒxx));

      for(int â˜ƒxxxxxxxxxxx = 127; â˜ƒxxxxxxxxxxx >= â˜ƒ; --â˜ƒxxxxxxxxxxx) {
         â˜ƒxxxxxxxxx.set(â˜ƒx, â˜ƒxxxxxxxxxxx, â˜ƒxx);
         BlockState â˜ƒxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxx);
         if (â˜ƒxxxxxxxxxx.is(â˜ƒ.getBlock()) && (â˜ƒxxxxxxxxxxxx.isAir() || â˜ƒxxxxxxxxxxxx == â˜ƒ)) {
            for(int â˜ƒxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxxxxxxxxx) {
               â˜ƒxxxxxxxxx.move(Direction.UP);
               if (!â˜ƒ.getBlockState(â˜ƒxxxxxxxxx).is(â˜ƒ.getBlock())) {
                  break;
               }

               â˜ƒ.setBlockState(â˜ƒxxxxxxxxx, â˜ƒxxxxxxx, false);
            }

            â˜ƒxxxxxxxxx.set(â˜ƒx, â˜ƒxxxxxxxxxxx, â˜ƒxx);
         }

         if ((â˜ƒxxxxxxxxxx.isAir() || â˜ƒxxxxxxxxxx == â˜ƒ) && â˜ƒxxxxxxxxxxxx.is(â˜ƒ.getBlock())) {
            for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒxxxx && â˜ƒ.getBlockState(â˜ƒxxxxxxxxx).is(â˜ƒ.getBlock()); ++â˜ƒxxxxxxxxxxxx) {
               if (â˜ƒxxxxxx && â˜ƒxxxxxxxxxxx >= â˜ƒ - 4 && â˜ƒxxxxxxxxxxx <= â˜ƒ + 1) {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxxx, this.getPatchBlockState(), false);
               } else {
                  â˜ƒ.setBlockState(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, false);
               }

               â˜ƒxxxxxxxxx.move(Direction.DOWN);
            }
         }

         â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxx;
      }
   }

   @Override
   public void initNoise(long var1) {
      if (this.seed != â˜ƒ || this.patchNoise == null || this.floorNoises.isEmpty() || this.ceilingNoises.isEmpty()) {
         this.floorNoises = initPerlinNoises(this.getFloorBlockStates(), â˜ƒ);
         this.ceilingNoises = initPerlinNoises(this.getCeilingBlockStates(), â˜ƒ + (long)this.floorNoises.size());
         this.patchNoise = new PerlinNoise(new WorldgenRandom(â˜ƒ + (long)this.floorNoises.size() + (long)this.ceilingNoises.size()), ImmutableList.of(0));
      }

      this.seed = â˜ƒ;
   }

   private static ImmutableMap<BlockState, PerlinNoise> initPerlinNoises(ImmutableList<BlockState> var0, long var1) {
      Builder<BlockState, PerlinNoise> â˜ƒ = new Builder<>();

      for(BlockState â˜ƒx : â˜ƒ) {
         â˜ƒ.put(â˜ƒx, new PerlinNoise(new WorldgenRandom(â˜ƒ), ImmutableList.of(-4)));
         ++â˜ƒ;
      }

      return â˜ƒ.build();
   }

   protected abstract ImmutableList<BlockState> getFloorBlockStates();

   protected abstract ImmutableList<BlockState> getCeilingBlockStates();

   protected abstract BlockState getPatchBlockState();
}
