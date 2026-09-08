package net.minecraft.world.level.levelgen.structure;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class StructureFeatureIndexSavedData extends SavedData {
   private static final String TAG_REMAINING_INDEXES = "Remaining";
   private static final String TAG_All_INDEXES = "All";
   private final LongSet all;
   private final LongSet remaining;

   private StructureFeatureIndexSavedData(LongSet var1, LongSet var2) {
      this.all = â˜ƒ;
      this.remaining = â˜ƒ;
   }

   public StructureFeatureIndexSavedData() {
      this(new LongOpenHashSet(), new LongOpenHashSet());
   }

   public static StructureFeatureIndexSavedData load(CompoundTag var0) {
      return new StructureFeatureIndexSavedData(new LongOpenHashSet(â˜ƒ.getLongArray("All")), new LongOpenHashSet(â˜ƒ.getLongArray("Remaining")));
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.putLongArray("All", this.all.toLongArray());
      â˜ƒ.putLongArray("Remaining", this.remaining.toLongArray());
      return â˜ƒ;
   }

   public void addIndex(long var1) {
      this.all.add(â˜ƒ);
      this.remaining.add(â˜ƒ);
   }

   public boolean hasStartIndex(long var1) {
      return this.all.contains(â˜ƒ);
   }

   public boolean hasUnhandledIndex(long var1) {
      return this.remaining.contains(â˜ƒ);
   }

   public void removeIndex(long var1) {
      this.remaining.remove(â˜ƒ);
   }

   public LongSet getAll() {
      return this.all;
   }
}
