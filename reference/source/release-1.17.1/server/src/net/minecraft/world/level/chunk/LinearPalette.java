package net.minecraft.world.level.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

public class LinearPalette<T> implements Palette<T> {
   private final IdMapper<T> registry;
   private final T[] values;
   private final PaletteResize<T> resizeHandler;
   private final Function<CompoundTag, T> reader;
   private final int bits;
   private int size;

   public LinearPalette(IdMapper<T> var1, int var2, PaletteResize<T> var3, Function<CompoundTag, T> var4) {
      this.registry = â˜ƒ;
      this.values = (T[])(new Object[1 << â˜ƒ]);
      this.bits = â˜ƒ;
      this.resizeHandler = â˜ƒ;
      this.reader = â˜ƒ;
   }

   @Override
   public int idFor(T var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.size; ++â˜ƒ) {
         if (this.values[â˜ƒ] == â˜ƒ) {
            return â˜ƒ;
         }
      }

      int â˜ƒ = this.size;
      if (â˜ƒ < this.values.length) {
         this.values[â˜ƒ] = â˜ƒ;
         ++this.size;
         return â˜ƒ;
      } else {
         return this.resizeHandler.onResize(this.bits + 1, â˜ƒ);
      }
   }

   @Override
   public boolean maybeHas(Predicate<T> var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.size; ++â˜ƒ) {
         if (â˜ƒ.test(this.values[â˜ƒ])) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public T valueFor(int var1) {
      return â˜ƒ >= 0 && â˜ƒ < this.size ? this.values[â˜ƒ] : null;
   }

   @Override
   public void read(FriendlyByteBuf var1) {
      this.size = â˜ƒ.readVarInt();

      for(int â˜ƒ = 0; â˜ƒ < this.size; ++â˜ƒ) {
         this.values[â˜ƒ] = this.registry.byId(â˜ƒ.readVarInt());
      }
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.size);

      for(int â˜ƒ = 0; â˜ƒ < this.size; ++â˜ƒ) {
         â˜ƒ.writeVarInt(this.registry.getId(this.values[â˜ƒ]));
      }
   }

   @Override
   public int getSerializedSize() {
      int â˜ƒ = FriendlyByteBuf.getVarIntSize(this.getSize());

      for(int â˜ƒx = 0; â˜ƒx < this.getSize(); ++â˜ƒx) {
         â˜ƒ += FriendlyByteBuf.getVarIntSize(this.registry.getId(this.values[â˜ƒx]));
      }

      return â˜ƒ;
   }

   @Override
   public int getSize() {
      return this.size;
   }

   @Override
   public void read(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         this.values[â˜ƒ] = (T)this.reader.apply(â˜ƒ.getCompound(â˜ƒ));
      }

      this.size = â˜ƒ.size();
   }
}
