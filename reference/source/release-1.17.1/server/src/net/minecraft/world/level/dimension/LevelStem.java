package net.minecraft.world.level.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class LevelStem {
   public static final Codec<LevelStem> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               DimensionType.CODEC
                  .fieldOf("type")
                  .flatXmap(ExtraCodecs.nonNullSupplierCheck(), ExtraCodecs.nonNullSupplierCheck())
                  .forGetter(LevelStem::typeSupplier),
               ChunkGenerator.CODEC.fieldOf("generator").forGetter(LevelStem::generator)
            )
            .apply(var0, var0.stable(LevelStem::new))
   );
   public static final ResourceKey<LevelStem> OVERWORLD = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation("overworld"));
   public static final ResourceKey<LevelStem> NETHER = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation("the_nether"));
   public static final ResourceKey<LevelStem> END = ResourceKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation("the_end"));
   private static final Set<ResourceKey<LevelStem>> BUILTIN_ORDER = Sets.<ResourceKey<LevelStem>>newLinkedHashSet(ImmutableList.of(OVERWORLD, NETHER, END));
   private final Supplier<DimensionType> type;
   private final ChunkGenerator generator;

   public LevelStem(Supplier<DimensionType> var1, ChunkGenerator var2) {
      this.type = â˜ƒ;
      this.generator = â˜ƒ;
   }

   public Supplier<DimensionType> typeSupplier() {
      return this.type;
   }

   public DimensionType type() {
      return (DimensionType)this.type.get();
   }

   public ChunkGenerator generator() {
      return this.generator;
   }

   public static MappedRegistry<LevelStem> sortMap(MappedRegistry<LevelStem> var0) {
      MappedRegistry<LevelStem> â˜ƒ = new MappedRegistry<>(Registry.LEVEL_STEM_REGISTRY, Lifecycle.experimental());

      for(ResourceKey<LevelStem> â˜ƒx : BUILTIN_ORDER) {
         LevelStem â˜ƒxx = â˜ƒ.get(â˜ƒx);
         if (â˜ƒxx != null) {
            â˜ƒ.register(â˜ƒx, â˜ƒxx, â˜ƒ.lifecycle(â˜ƒxx));
         }
      }

      for(Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒx : â˜ƒ.entrySet()) {
         ResourceKey<LevelStem> â˜ƒxx = (ResourceKey)â˜ƒx.getKey();
         if (!BUILTIN_ORDER.contains(â˜ƒxx)) {
            â˜ƒ.register(â˜ƒxx, (LevelStem)â˜ƒx.getValue(), â˜ƒ.lifecycle((LevelStem)â˜ƒx.getValue()));
         }
      }

      return â˜ƒ;
   }

   public static boolean stable(long var0, MappedRegistry<LevelStem> var2) {
      List<Entry<ResourceKey<LevelStem>, LevelStem>> â˜ƒ = Lists.newArrayList(â˜ƒ.entrySet());
      if (â˜ƒ.size() != BUILTIN_ORDER.size()) {
         return false;
      } else {
         Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒ = (Entry)â˜ƒ.get(0);
         Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒx = (Entry)â˜ƒ.get(1);
         Entry<ResourceKey<LevelStem>, LevelStem> â˜ƒxx = (Entry)â˜ƒ.get(2);
         if (â˜ƒ.getKey() != OVERWORLD || â˜ƒx.getKey() != NETHER || â˜ƒxx.getKey() != END) {
            return false;
         } else if (!((LevelStem)â˜ƒ.getValue()).type().equalTo(DimensionType.DEFAULT_OVERWORLD)
            && ((LevelStem)â˜ƒ.getValue()).type() != DimensionType.DEFAULT_OVERWORLD_CAVES) {
            return false;
         } else if (!((LevelStem)â˜ƒx.getValue()).type().equalTo(DimensionType.DEFAULT_NETHER)) {
            return false;
         } else if (!((LevelStem)â˜ƒxx.getValue()).type().equalTo(DimensionType.DEFAULT_END)) {
            return false;
         } else if (((LevelStem)â˜ƒx.getValue()).generator() instanceof NoiseBasedChunkGenerator â˜ƒx
            && ((LevelStem)â˜ƒxx.getValue()).generator() instanceof NoiseBasedChunkGenerator â˜ƒ) {
            if (!â˜ƒx.stable(â˜ƒ, NoiseGeneratorSettings.NETHER)) {
               return false;
            } else if (!â˜ƒ.stable(â˜ƒ, NoiseGeneratorSettings.END)) {
               return false;
            } else if (!(â˜ƒx.getBiomeSource() instanceof MultiNoiseBiomeSource)) {
               return false;
            } else {
               MultiNoiseBiomeSource â˜ƒxx = (MultiNoiseBiomeSource)â˜ƒx.getBiomeSource();
               if (!â˜ƒxx.stable(â˜ƒ)) {
                  return false;
               } else if (!(â˜ƒ.getBiomeSource() instanceof TheEndBiomeSource)) {
                  return false;
               } else {
                  TheEndBiomeSource â˜ƒxx = (TheEndBiomeSource)â˜ƒ.getBiomeSource();
                  return â˜ƒxx.stable(â˜ƒ);
               }
            }
         } else {
            return false;
         }
      }
   }
}
