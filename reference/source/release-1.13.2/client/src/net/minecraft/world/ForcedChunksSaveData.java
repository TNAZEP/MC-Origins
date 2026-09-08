package net.minecraft.world;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

public class ForcedChunksSaveData extends WorldSavedData {
   private LongSet field_212439_a = new LongOpenHashSet();

   public ForcedChunksSaveData(String var1) {
      super(☃);
   }

   @Override
   public void func_76184_a(NBTTagCompound var1) {
      this.field_212439_a = new LongOpenHashSet(☃.func_197645_o("Forced"));
   }

   @Override
   public NBTTagCompound func_189551_b(NBTTagCompound var1) {
      ☃.func_197644_a("Forced", this.field_212439_a.toLongArray());
      return ☃;
   }

   public LongSet func_212438_a() {
      return this.field_212439_a;
   }
}
