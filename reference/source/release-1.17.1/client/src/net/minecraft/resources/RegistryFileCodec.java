package net.minecraft.resources;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Registry;

public final class RegistryFileCodec<E> implements Codec<Supplier<E>> {
   private final ResourceKey<? extends Registry<E>> registryKey;
   private final Codec<E> elementCodec;
   private final boolean allowInline;

   public static <E> RegistryFileCodec<E> create(ResourceKey<? extends Registry<E>> var0, Codec<E> var1) {
      return create(â˜ƒ, â˜ƒ, true);
   }

   public static <E> Codec<List<Supplier<E>>> homogeneousList(ResourceKey<? extends Registry<E>> var0, Codec<E> var1) {
      return Codec.either(create(â˜ƒ, â˜ƒ, false).listOf(), â˜ƒ.xmap(var0x -> () -> var0x, Supplier::get).listOf())
         .xmap(var0x -> var0x.map(var0xx -> var0xx, var0xx -> var0xx), Either::left);
   }

   private static <E> RegistryFileCodec<E> create(ResourceKey<? extends Registry<E>> var0, Codec<E> var1, boolean var2) {
      return new RegistryFileCodec<>(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private RegistryFileCodec(ResourceKey<? extends Registry<E>> var1, Codec<E> var2, boolean var3) {
      this.registryKey = â˜ƒ;
      this.elementCodec = â˜ƒ;
      this.allowInline = â˜ƒ;
   }

   public <T> DataResult<T> encode(Supplier<E> var1, DynamicOps<T> var2, T var3) {
      return â˜ƒ instanceof RegistryWriteOps
         ? ((RegistryWriteOps)â˜ƒ).encode(â˜ƒ.get(), â˜ƒ, this.registryKey, this.elementCodec)
         : this.elementCodec.encode((E)â˜ƒ.get(), â˜ƒ, â˜ƒ);
   }

   @Override
   public <T> DataResult<Pair<Supplier<E>, T>> decode(DynamicOps<T> var1, T var2) {
      return â˜ƒ instanceof RegistryReadOps
         ? ((RegistryReadOps)â˜ƒ).decodeElement(â˜ƒ, this.registryKey, this.elementCodec, this.allowInline)
         : this.elementCodec.decode(â˜ƒ, â˜ƒ).map(var0 -> var0.mapFirst(var0x -> () -> var0x));
   }

   public String toString() {
      return "RegistryFileCodec[" + this.registryKey + " " + this.elementCodec + "]";
   }
}
