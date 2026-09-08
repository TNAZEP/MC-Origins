package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;

public final class RegistryDataPackCodec<E> implements Codec<MappedRegistry<E>> {
   private final Codec<MappedRegistry<E>> directCodec;
   private final ResourceKey<? extends Registry<E>> registryKey;
   private final Codec<E> elementCodec;

   public static <E> RegistryDataPackCodec<E> create(ResourceKey<? extends Registry<E>> var0, Lifecycle var1, Codec<E> var2) {
      return new RegistryDataPackCodec<>(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private RegistryDataPackCodec(ResourceKey<? extends Registry<E>> var1, Lifecycle var2, Codec<E> var3) {
      this.directCodec = MappedRegistry.directCodec(â˜ƒ, â˜ƒ, â˜ƒ);
      this.registryKey = â˜ƒ;
      this.elementCodec = â˜ƒ;
   }

   public <T> DataResult<T> encode(MappedRegistry<E> var1, DynamicOps<T> var2, T var3) {
      return this.directCodec.encode(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public <T> DataResult<Pair<MappedRegistry<E>, T>> decode(DynamicOps<T> var1, T var2) {
      DataResult<Pair<MappedRegistry<E>, T>> â˜ƒ = this.directCodec.decode(â˜ƒ, â˜ƒ);
      return â˜ƒ instanceof RegistryReadOps
         ? â˜ƒ.flatMap(
            var2x -> ((RegistryReadOps)â˜ƒ)
                  .decodeElements((MappedRegistry<E>)var2x.getFirst(), this.registryKey, this.elementCodec)
                  .map(var1x -> Pair.of(var1x, var2x.getSecond()))
         )
         : â˜ƒ;
   }

   public String toString() {
      return "RegistryDataPackCodec[" + this.directCodec + " " + this.registryKey + " " + this.elementCodec + "]";
   }
}
