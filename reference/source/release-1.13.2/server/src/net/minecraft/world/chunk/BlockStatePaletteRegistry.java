package net.minecraft.world.chunk;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ObjectIntIdentityMap;

public class BlockStatePaletteRegistry<T> implements IBlockStatePalette<T> {
   private final ObjectIntIdentityMap<T> field_205505_a;
   private final T field_205506_b;

   public BlockStatePaletteRegistry(ObjectIntIdentityMap<T> var1, T var2) {
      this.field_205505_a = ☃;
      this.field_205506_b = ☃;
   }

   @Override
   public int func_186041_a(T var1) {
      int ☃ = this.field_205505_a.func_148747_b(☃);
      return ☃ == -1 ? 0 : ☃;
   }

   @Override
   public T func_186039_a(int var1) {
      T ☃ = this.field_205505_a.func_148745_a(☃);
      return (T)(☃ == null ? this.field_205506_b : ☃);
   }

   @Override
   public void func_186037_b(PacketBuffer var1) {
   }

   @Override
   public int func_186040_a() {
      return PacketBuffer.func_150790_a(0);
   }

   @Override
   public void func_196968_a(NBTTagList var1) {
   }
}
