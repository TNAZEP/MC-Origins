package net.minecraft.world.level.chunk;

import java.util.function.Predicate;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

public class GlobalPalette<T> implements Palette<T> {
   private final IdMapper<T> registry;
   private final T defaultValue;

   public GlobalPalette(IdMapper<T> var1, T var2) {
      this.registry = â˜ƒ;
      this.defaultValue = â˜ƒ;
   }

   @Override
   public int idFor(T var1) {
      int â˜ƒ = this.registry.getId(â˜ƒ);
      return â˜ƒ == -1 ? 0 : â˜ƒ;
   }

   @Override
   public boolean maybeHas(Predicate<T> var1) {
      return true;
   }

   @Override
   public T valueFor(int var1) {
      T â˜ƒ = this.registry.byId(â˜ƒ);
      return (T)(â˜ƒ == null ? this.defaultValue : â˜ƒ);
   }

   @Override
   public void read(FriendlyByteBuf var1) {
   }

   @Override
   public void write(FriendlyByteBuf var1) {
   }

   @Override
   public int getSerializedSize() {
      return FriendlyByteBuf.getVarIntSize(0);
   }

   @Override
   public int getSize() {
      return this.registry.size();
   }

   @Override
   public void read(ListTag var1) {
   }
}
