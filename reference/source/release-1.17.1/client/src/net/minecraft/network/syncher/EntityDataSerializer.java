package net.minecraft.network.syncher;

import net.minecraft.network.FriendlyByteBuf;

public interface EntityDataSerializer<T> {
   void write(FriendlyByteBuf var1, T var2);

   T read(FriendlyByteBuf var1);

   default EntityDataAccessor<T> createAccessor(int var1) {
      return new EntityDataAccessor<>(â˜ƒ, this);
   }

   T copy(T var1);
}
