package net.minecraft.world.level.chunk;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap.Entry;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.IdMapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.Mth;
import net.minecraft.util.ThreadingDetector;

public class PalettedContainer<T> implements PaletteResize<T> {
   private static final int SIZE = 4096;
   public static final int GLOBAL_PALETTE_BITS = 9;
   public static final int MIN_PALETTE_SIZE = 4;
   private final Palette<T> globalPalette;
   private final PaletteResize<T> dummyPaletteResize = (var0, var1x) -> 0;
   private final IdMapper<T> registry;
   private final Function<CompoundTag, T> reader;
   private final Function<T, CompoundTag> writer;
   private final T defaultValue;
   protected BitStorage storage;
   private Palette<T> palette;
   private int bits;
   private final Semaphore lock = new Semaphore(1);
   @Nullable
   private final DebugBuffer<Pair<Thread, StackTraceElement[]>> traces = null;

   public void acquire() {
      if (this.traces != null) {
         Thread â˜ƒ = Thread.currentThread();
         this.traces.push(Pair.of(â˜ƒ, â˜ƒ.getStackTrace()));
      }

      ThreadingDetector.checkAndLock(this.lock, this.traces, "PalettedContainer");
   }

   public void release() {
      this.lock.release();
   }

   public PalettedContainer(Palette<T> var1, IdMapper<T> var2, Function<CompoundTag, T> var3, Function<T, CompoundTag> var4, T var5) {
      this.globalPalette = â˜ƒ;
      this.registry = â˜ƒ;
      this.reader = â˜ƒ;
      this.writer = â˜ƒ;
      this.defaultValue = â˜ƒ;
      this.setBits(4);
   }

   private static int getIndex(int var0, int var1, int var2) {
      return â˜ƒ << 8 | â˜ƒ << 4 | â˜ƒ;
   }

   private void setBits(int var1) {
      if (â˜ƒ != this.bits) {
         this.bits = â˜ƒ;
         if (this.bits <= 4) {
            this.bits = 4;
            this.palette = new LinearPalette<>(this.registry, this.bits, this, this.reader);
         } else if (this.bits < 9) {
            this.palette = new HashMapPalette<>(this.registry, this.bits, this, this.reader, this.writer);
         } else {
            this.palette = this.globalPalette;
            this.bits = Mth.ceillog2(this.registry.size());
         }

         this.palette.idFor(this.defaultValue);
         this.storage = new BitStorage(this.bits, 4096);
      }
   }

   @Override
   public int onResize(int var1, T var2) {
      BitStorage â˜ƒ = this.storage;
      Palette<T> â˜ƒx = this.palette;
      this.setBits(â˜ƒ);

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getSize(); ++â˜ƒxx) {
         T â˜ƒxxx = â˜ƒx.valueFor(â˜ƒ.get(â˜ƒxx));
         if (â˜ƒxxx != null) {
            this.set(â˜ƒxx, â˜ƒxxx);
         }
      }

      return this.palette.idFor(â˜ƒ);
   }

   public T getAndSet(int var1, int var2, int var3, T var4) {
      Object var6;
      try {
         this.acquire();
         T â˜ƒ = this.getAndSet(getIndex(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
         var6 = â˜ƒ;
      } finally {
         this.release();
      }

      return (T)var6;
   }

   public T getAndSetUnchecked(int var1, int var2, int var3, T var4) {
      return this.getAndSet(getIndex(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
   }

   private T getAndSet(int var1, T var2) {
      int â˜ƒ = this.palette.idFor(â˜ƒ);
      int â˜ƒx = this.storage.getAndSet(â˜ƒ, â˜ƒ);
      T â˜ƒxx = this.palette.valueFor(â˜ƒx);
      return (T)(â˜ƒxx == null ? this.defaultValue : â˜ƒxx);
   }

   public void set(int var1, int var2, int var3, T var4) {
      try {
         this.acquire();
         this.set(getIndex(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
      } finally {
         this.release();
      }
   }

   private void set(int var1, T var2) {
      int â˜ƒ = this.palette.idFor(â˜ƒ);
      this.storage.set(â˜ƒ, â˜ƒ);
   }

   public T get(int var1, int var2, int var3) {
      return this.get(getIndex(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   protected T get(int var1) {
      T â˜ƒ = this.palette.valueFor(this.storage.get(â˜ƒ));
      return (T)(â˜ƒ == null ? this.defaultValue : â˜ƒ);
   }

   public void read(FriendlyByteBuf var1) {
      try {
         this.acquire();
         int â˜ƒ = â˜ƒ.readByte();
         if (this.bits != â˜ƒ) {
            this.setBits(â˜ƒ);
         }

         this.palette.read(â˜ƒ);
         â˜ƒ.readLongArray(this.storage.getRaw());
      } finally {
         this.release();
      }
   }

   public void write(FriendlyByteBuf var1) {
      try {
         this.acquire();
         â˜ƒ.writeByte(this.bits);
         this.palette.write(â˜ƒ);
         â˜ƒ.writeLongArray(this.storage.getRaw());
      } finally {
         this.release();
      }
   }

   public void read(ListTag var1, long[] var2) {
      try {
         this.acquire();
         int â˜ƒ = Math.max(4, Mth.ceillog2(â˜ƒ.size()));
         if (â˜ƒ != this.bits) {
            this.setBits(â˜ƒ);
         }

         this.palette.read(â˜ƒ);
         int â˜ƒ = â˜ƒ.length * 64 / 4096;
         if (this.palette == this.globalPalette) {
            Palette<T> â˜ƒx = new HashMapPalette<>(this.registry, â˜ƒ, this.dummyPaletteResize, this.reader, this.writer);
            â˜ƒx.read(â˜ƒ);
            BitStorage â˜ƒxx = new BitStorage(â˜ƒ, 4096, â˜ƒ);

            for(int â˜ƒxxx = 0; â˜ƒxxx < 4096; ++â˜ƒxxx) {
               this.storage.set(â˜ƒxxx, this.globalPalette.idFor(â˜ƒx.valueFor(â˜ƒxx.get(â˜ƒxxx))));
            }
         } else if (â˜ƒ == this.bits) {
            System.arraycopy(â˜ƒ, 0, this.storage.getRaw(), 0, â˜ƒ.length);
         } else {
            BitStorage â˜ƒ = new BitStorage(â˜ƒ, 4096, â˜ƒ);

            for(int â˜ƒx = 0; â˜ƒx < 4096; ++â˜ƒx) {
               this.storage.set(â˜ƒx, â˜ƒ.get(â˜ƒx));
            }
         }
      } finally {
         this.release();
      }
   }

   public void write(CompoundTag var1, String var2, String var3) {
      try {
         this.acquire();
         HashMapPalette<T> â˜ƒ = new HashMapPalette<>(this.registry, this.bits, this.dummyPaletteResize, this.reader, this.writer);
         T â˜ƒx = this.defaultValue;
         int â˜ƒxx = â˜ƒ.idFor(this.defaultValue);
         int[] â˜ƒxxx = new int[4096];

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 4096; ++â˜ƒxxxx) {
            T â˜ƒxxxxx = this.get(â˜ƒxxxx);
            if (â˜ƒxxxxx != â˜ƒx) {
               â˜ƒx = â˜ƒxxxxx;
               â˜ƒxx = â˜ƒ.idFor(â˜ƒxxxxx);
            }

            â˜ƒxxx[â˜ƒxxxx] = â˜ƒxx;
         }

         ListTag â˜ƒxxxx = new ListTag();
         â˜ƒ.write(â˜ƒxxxx);
         â˜ƒ.put(â˜ƒ, â˜ƒxxxx);
         int â˜ƒxxxxx = Math.max(4, Mth.ceillog2(â˜ƒxxxx.size()));
         BitStorage â˜ƒxxxxxx = new BitStorage(â˜ƒxxxxx, 4096);

         for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒxxx.length; ++â˜ƒxxxxxxx) {
            â˜ƒxxxxxx.set(â˜ƒxxxxxxx, â˜ƒxxx[â˜ƒxxxxxxx]);
         }

         â˜ƒ.putLongArray(â˜ƒ, â˜ƒxxxxxx.getRaw());
      } finally {
         this.release();
      }
   }

   public int getSerializedSize() {
      return 1 + this.palette.getSerializedSize() + FriendlyByteBuf.getVarIntSize(this.storage.getSize()) + this.storage.getRaw().length * 8;
   }

   public boolean maybeHas(Predicate<T> var1) {
      return this.palette.maybeHas(â˜ƒ);
   }

   public void count(PalettedContainer.CountConsumer<T> var1) {
      Int2IntMap â˜ƒ = new Int2IntOpenHashMap();
      this.storage.getAll(var1x -> â˜ƒ.put(var1x, â˜ƒ.get(var1x) + 1));
      â˜ƒ.int2IntEntrySet().forEach(var2x -> â˜ƒ.accept(this.palette.valueFor(var2x.getIntKey()), var2x.getIntValue()));
   }

   @FunctionalInterface
   public interface CountConsumer<T> {
      void accept(T var1, int var2);
   }
}
