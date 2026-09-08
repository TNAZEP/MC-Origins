package net.minecraft.core;

import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DefaultedRegistry<T> extends MappedRegistry<T> {
   private final ResourceLocation defaultKey;
   private T defaultValue;

   public DefaultedRegistry(String var1, ResourceKey<? extends Registry<T>> var2, Lifecycle var3) {
      super(â˜ƒ, â˜ƒ);
      this.defaultKey = new ResourceLocation(â˜ƒ);
   }

   @Override
   public <V extends T> V registerMapping(int var1, ResourceKey<T> var2, V var3, Lifecycle var4) {
      if (this.defaultKey.equals(â˜ƒ.location())) {
         this.defaultValue = (T)â˜ƒ;
      }

      return super.registerMapping(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getId(@Nullable T var1) {
      int â˜ƒ = super.getId(â˜ƒ);
      return â˜ƒ == -1 ? super.getId(this.defaultValue) : â˜ƒ;
   }

   @Nonnull
   @Override
   public ResourceLocation getKey(T var1) {
      ResourceLocation â˜ƒ = super.getKey(â˜ƒ);
      return â˜ƒ == null ? this.defaultKey : â˜ƒ;
   }

   @Nonnull
   @Override
   public T get(@Nullable ResourceLocation var1) {
      T â˜ƒ = super.get(â˜ƒ);
      return (T)(â˜ƒ == null ? this.defaultValue : â˜ƒ);
   }

   @Override
   public Optional<T> getOptional(@Nullable ResourceLocation var1) {
      return Optional.ofNullable(super.get(â˜ƒ));
   }

   @Nonnull
   @Override
   public T byId(int var1) {
      T â˜ƒ = super.byId(â˜ƒ);
      return (T)(â˜ƒ == null ? this.defaultValue : â˜ƒ);
   }

   @Nonnull
   @Override
   public T getRandom(Random var1) {
      T â˜ƒ = super.getRandom(â˜ƒ);
      return (T)(â˜ƒ == null ? this.defaultValue : â˜ƒ);
   }

   public ResourceLocation getDefaultKey() {
      return this.defaultKey;
   }
}
