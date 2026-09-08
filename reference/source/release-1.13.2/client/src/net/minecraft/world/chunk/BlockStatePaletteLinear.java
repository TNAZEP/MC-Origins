package net.minecraft.world.chunk;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ObjectIntIdentityMap;

public class BlockStatePaletteLinear<T> implements IBlockStatePalette<T> {
   private final ObjectIntIdentityMap<T> field_205507_a;
   private final T[] field_186042_a;
   private final IBlockStatePaletteResizer<T> field_186043_b;
   private final Function<NBTTagCompound, T> field_205508_d;
   private final int field_186044_c;
   private int field_186045_d;

   public BlockStatePaletteLinear(ObjectIntIdentityMap<T> var1, int var2, IBlockStatePaletteResizer<T> var3, Function<NBTTagCompound, T> var4) {
      this.field_205507_a = ☃;
      this.field_186042_a = (T[])(new Object[1 << ☃]);
      this.field_186044_c = ☃;
      this.field_186043_b = ☃;
      this.field_205508_d = ☃;
   }

   @Override
   public int func_186041_a(T var1) {
      for(int ☃ = 0; ☃ < this.field_186045_d; ++☃) {
         if (this.field_186042_a[☃] == ☃) {
            return ☃;
         }
      }

      int ☃ = this.field_186045_d;
      if (☃ < this.field_186042_a.length) {
         this.field_186042_a[☃] = ☃;
         ++this.field_186045_d;
         return ☃;
      } else {
         return this.field_186043_b.onResize(this.field_186044_c + 1, ☃);
      }
   }

   @Nullable
   @Override
   public T func_186039_a(int var1) {
      return ☃ >= 0 && ☃ < this.field_186045_d ? this.field_186042_a[☃] : null;
   }

   @Override
   public void func_186038_a(PacketBuffer var1) {
      this.field_186045_d = ☃.func_150792_a();

      for(int ☃ = 0; ☃ < this.field_186045_d; ++☃) {
         this.field_186042_a[☃] = this.field_205507_a.func_148745_a(☃.func_150792_a());
      }
   }

   @Override
   public void func_186037_b(PacketBuffer var1) {
      ☃.func_150787_b(this.field_186045_d);

      for(int ☃ = 0; ☃ < this.field_186045_d; ++☃) {
         ☃.func_150787_b(this.field_205507_a.func_148747_b(this.field_186042_a[☃]));
      }
   }

   @Override
   public int func_186040_a() {
      int ☃ = PacketBuffer.func_150790_a(this.func_202137_b());

      for(int ☃x = 0; ☃x < this.func_202137_b(); ++☃x) {
         ☃ += PacketBuffer.func_150790_a(this.field_205507_a.func_148747_b(this.field_186042_a[☃x]));
      }

      return ☃;
   }

   public int func_202137_b() {
      return this.field_186045_d;
   }

   @Override
   public void func_196968_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         this.field_186042_a[☃] = (T)this.field_205508_d.apply(☃.func_150305_b(☃));
      }

      this.field_186045_d = ☃.size();
   }
}
