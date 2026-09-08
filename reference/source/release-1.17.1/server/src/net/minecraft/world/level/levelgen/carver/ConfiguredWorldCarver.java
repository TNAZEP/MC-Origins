package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public class ConfiguredWorldCarver<WC extends CarverConfiguration> {
   public static final Codec<ConfiguredWorldCarver<?>> DIRECT_CODEC = Registry.CARVER.dispatch(var0 -> var0.worldCarver, WorldCarver::configuredCodec);
   public static final Codec<Supplier<ConfiguredWorldCarver<?>>> CODEC = RegistryFileCodec.create(Registry.CONFIGURED_CARVER_REGISTRY, DIRECT_CODEC);
   public static final Codec<List<Supplier<ConfiguredWorldCarver<?>>>> LIST_CODEC = RegistryFileCodec.homogeneousList(
      Registry.CONFIGURED_CARVER_REGISTRY, DIRECT_CODEC
   );
   private final WorldCarver<WC> worldCarver;
   private final WC config;

   public ConfiguredWorldCarver(WorldCarver<WC> var1, WC var2) {
      this.worldCarver = â˜ƒ;
      this.config = â˜ƒ;
   }

   public WC config() {
      return this.config;
   }

   public boolean isStartChunk(Random var1) {
      return this.worldCarver.isStartChunk(this.config, â˜ƒ);
   }

   public boolean carve(CarvingContext var1, ChunkAccess var2, Function<BlockPos, Biome> var3, Random var4, Aquifer var5, ChunkPos var6, BitSet var7) {
      return this.worldCarver.carve(â˜ƒ, this.config, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
