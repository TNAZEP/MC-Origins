package net.minecraft.world.level;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class ForcedChunksSavedData extends SavedData {
   public static final String FILE_ID = "chunks";
   private static final String TAG_FORCED = "Forced";
   private final LongSet chunks;

   private ForcedChunksSavedData(LongSet var1) {
      this.chunks = â˜ƒ;
   }

   public ForcedChunksSavedData() {
      this(new LongOpenHashSet());
   }

   public static ForcedChunksSavedData load(CompoundTag var0) {
      return new ForcedChunksSavedData(new LongOpenHashSet(â˜ƒ.getLongArray("Forced")));
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.putLongArray("Forced", this.chunks.toLongArray());
      return â˜ƒ;
   }

   public LongSet getChunks() {
      return this.chunks;
   }
}
