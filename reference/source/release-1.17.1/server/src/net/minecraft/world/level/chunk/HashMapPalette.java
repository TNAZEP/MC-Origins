package net.minecraft.world.level.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;

public class HashMapPalette<T> implements Palette<T> {
   private final IdMapper<T> registry;
   private final CrudeIncrementalIntIdentityHashBiMap<T> values;
   private final PaletteResize<T> resizeHandler;
   private final Function<CompoundTag, T> reader;
   private final Function<T, CompoundTag> writer;
   private final int bits;

   public HashMapPalette(IdMapper<T> var1, int var2, PaletteResize<T> var3, Function<CompoundTag, T> var4, Function<T, CompoundTag> var5) {
      this.registry = â˜ƒ;
      this.bits = â˜ƒ;
      this.resizeHandler = â˜ƒ;
      this.reader = â˜ƒ;
      this.writer = â˜ƒ;
      this.values = new CrudeIncrementalIntIdentityHashBiMap<>(1 << â˜ƒ);
   }

   @Override
   public int idFor(T var1) {
      int â˜ƒ = this.values.getId(â˜ƒ);
      if (â˜ƒ == -1) {
         â˜ƒ = this.values.add(â˜ƒ);
         if (â˜ƒ >= 1 << this.bits) {
            â˜ƒ = this.resizeHandler.onResize(this.bits + 1, â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean maybeHas(Predicate<T> var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.getSize(); ++â˜ƒ) {
         if (â˜ƒ.test(this.values.byId(â˜ƒ))) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public T valueFor(int var1) {
      return this.values.byId(â˜ƒ);
   }

   @Override
   public void read(FriendlyByteBuf var1) {
      this.values.clear();
      int â˜ƒ = â˜ƒ.readVarInt();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         this.values.add(this.registry.byId(â˜ƒ.readVarInt()));
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      int â˜ƒ = this.getSize();
      â˜ƒ.writeVarInt(â˜ƒ);

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         â˜ƒ.writeVarInt(this.registry.getId(this.values.byId(â˜ƒx)));
      }
   }

   @Override
   public int getSerializedSize() {
      int â˜ƒ = FriendlyByteBuf.getVarIntSize(this.getSize());

      for(int â˜ƒx = 0; â˜ƒx < this.getSize(); ++â˜ƒx) {
         â˜ƒ += FriendlyByteBuf.getVarIntSize(this.registry.getId(this.values.byId(â˜ƒx)));
      }

      return â˜ƒ;
   }

   @Override
   public int getSize() {
      return this.values.size();
   }

   @Override
   public void read(ListTag var1) {
      this.values.clear();

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         this.values.add((T)this.reader.apply(â˜ƒ.getCompound(â˜ƒ)));
      }
   }

   public void write(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.getSize(); ++â˜ƒ) {
         â˜ƒ.add((Tag)this.writer.apply(this.values.byId(â˜ƒ)));
      }
   }
}
