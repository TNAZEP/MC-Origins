package net.minecraft.resources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;

public class RegistryWriteOps<T> extends DelegatingOps<T> {
   private final RegistryAccess registryAccess;

   public static <T> RegistryWriteOps<T> create(DynamicOps<T> var0, RegistryAccess var1) {
      return new RegistryWriteOps<>(â˜ƒ, â˜ƒ);
   }

   private RegistryWriteOps(DynamicOps<T> var1, RegistryAccess var2) {
      super(â˜ƒ);
      this.registryAccess = â˜ƒ;
   }

   protected <E> DataResult<T> encode(E var1, T var2, ResourceKey<? extends Registry<E>> var3, Codec<E> var4) {
      Optional<WritableRegistry<E>> â˜ƒ = this.registryAccess.ownedRegistry(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         WritableRegistry<E> â˜ƒx = (WritableRegistry)â˜ƒ.get();
         Optional<ResourceKey<E>> â˜ƒxx = â˜ƒx.getResourceKey(â˜ƒ);
         if (â˜ƒxx.isPresent()) {
            ResourceKey<E> â˜ƒxxx = (ResourceKey)â˜ƒxx.get();
            return ResourceLocation.CODEC.encode(â˜ƒxxx.location(), this.delegate, â˜ƒ);
         }
      }

      return â˜ƒ.encode(â˜ƒ, this, â˜ƒ);
   }
}
