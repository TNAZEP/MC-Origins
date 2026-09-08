package net.minecraft.world.chunk;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ObjectIntIdentityMap;

public class BlockStatePaletteHashMap<T> implements IBlockStatePalette<T> {
   private final ObjectIntIdentityMap<T> field_205509_a;
   private final IntIdentityHashBiMap<T> field_186046_a;
   private final IBlockStatePaletteResizer<T> field_186047_b;
   private final Function<NBTTagCompound, T> field_205510_d;
   private final Function<T, NBTTagCompound> field_205511_e;
   private final int field_186048_c;

   public BlockStatePaletteHashMap(
      ObjectIntIdentityMap<T> var1, int var2, IBlockStatePaletteResizer<T> var3, Function<NBTTagCompound, T> var4, Function<T, NBTTagCompound> var5
   ) {
      this.field_205509_a = ☃;
      this.field_186048_c = ☃;
      this.field_186047_b = ☃;
      this.field_205510_d = ☃;
      this.field_205511_e = ☃;
      this.field_186046_a = new IntIdentityHashBiMap<>(1 << ☃);
   }

   @Override
   public int func_186041_a(T var1) {
      int ☃ = this.field_186046_a.func_186815_a(☃);
      if (☃ == -1) {
         ☃ = this.field_186046_a.func_186808_c(☃);
         if (☃ >= 1 << this.field_186048_c) {
            ☃ = this.field_186047_b.onResize(this.field_186048_c + 1, ☃);
         }
      }

      return ☃;
   }

   @Nullable
   @Override
   public T func_186039_a(int var1) {
      return this.field_186046_a.func_186813_a(☃);
   }

   @Override
   public void func_186038_a(PacketBuffer var1) {
      this.field_186046_a.func_186812_a();
      int ☃ = ☃.func_150792_a();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.field_186046_a.func_186808_c(this.field_205509_a.func_148745_a(☃.func_150792_a()));
      }
   }

   @Override
   public void func_186037_b(PacketBuffer var1) {
      int ☃ = this.func_202136_b();
      ☃.func_150787_b(☃);

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         ☃.func_150787_b(this.field_205509_a.func_148747_b(this.field_186046_a.func_186813_a(☃x)));
      }
   }

   @Override
   public int func_186040_a() {
      int ☃ = PacketBuffer.func_150790_a(this.func_202136_b());

      for(int ☃x = 0; ☃x < this.func_202136_b(); ++☃x) {
         ☃ += PacketBuffer.func_150790_a(this.field_205509_a.func_148747_b(this.field_186046_a.func_186813_a(☃x)));
      }

      return ☃;
   }

   public int func_202136_b() {
      return this.field_186046_a.func_186810_b();
   }

   @Override
   public void func_196968_a(NBTTagList var1) {
      this.field_186046_a.func_186812_a();

      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         this.field_186046_a.func_186808_c((T)this.field_205510_d.apply(☃.func_150305_b(☃)));
      }
   }

   public void func_196969_b(NBTTagList var1) {
      for(int ☃ = 0; ☃ < this.func_202136_b(); ++☃) {
         ☃.add((INBTBase)this.field_205511_e.apply(this.field_186046_a.func_186813_a(☃)));
      }
   }
}
