package net.minecraft.world.chunk;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BitArray;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.MathHelper;

public class BlockStateContainer<T> implements IBlockStatePaletteResizer<T> {
   private final IBlockStatePalette<T> field_205521_b;
   private final IBlockStatePaletteResizer<T> field_205522_c = (var0, var1x) -> 0;
   private final ObjectIntIdentityMap<T> field_205523_d;
   private final Function<NBTTagCompound, T> field_205524_e;
   private final Function<T, NBTTagCompound> field_205525_f;
   private final T field_205526_g;
   protected BitArray field_186021_b;
   private IBlockStatePalette<T> field_186022_c;
   private int field_186024_e;
   private final ReentrantLock field_210461_j = new ReentrantLock();

   private void func_210459_b() {
      if (this.field_210461_j.isLocked() && !this.field_210461_j.isHeldByCurrentThread()) {
         String ☃ = (String)Thread.getAllStackTraces()
            .keySet()
            .stream()
            .filter(Objects::nonNull)
            .map(
               var0 -> var0.getName() + ": \n\tat " + (String)Arrays.stream(var0.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "))
            )
            .collect(Collectors.joining("\n"));
         CrashReport ☃x = new CrashReport("Writing into PalettedContainer from multiple threads", new IllegalStateException());
         CrashReportCategory ☃xx = ☃x.func_85058_a("Thread dumps");
         ☃xx.func_71507_a("Thread dumps", ☃);
         throw new ReportedException(☃x);
      } else {
         this.field_210461_j.lock();
      }
   }

   private void func_210460_c() {
      this.field_210461_j.unlock();
   }

   public BlockStateContainer(
      IBlockStatePalette<T> var1, ObjectIntIdentityMap<T> var2, Function<NBTTagCompound, T> var3, Function<T, NBTTagCompound> var4, T var5
   ) {
      this.field_205521_b = ☃;
      this.field_205523_d = ☃;
      this.field_205524_e = ☃;
      this.field_205525_f = ☃;
      this.field_205526_g = ☃;
      this.func_186012_b(4);
   }

   private static int func_186011_b(int var0, int var1, int var2) {
      return ☃ << 8 | ☃ << 4 | ☃;
   }

   private void func_186012_b(int var1) {
      if (☃ != this.field_186024_e) {
         this.field_186024_e = ☃;
         if (this.field_186024_e <= 4) {
            this.field_186024_e = 4;
            this.field_186022_c = new BlockStatePaletteLinear<>(this.field_205523_d, this.field_186024_e, this, this.field_205524_e);
         } else if (this.field_186024_e < 9) {
            this.field_186022_c = new BlockStatePaletteHashMap<>(this.field_205523_d, this.field_186024_e, this, this.field_205524_e, this.field_205525_f);
         } else {
            this.field_186022_c = this.field_205521_b;
            this.field_186024_e = MathHelper.func_151241_e(this.field_205523_d.func_186804_a());
         }

         this.field_186022_c.func_186041_a(this.field_205526_g);
         this.field_186021_b = new BitArray(this.field_186024_e, 4096);
      }
   }

   @Override
   public int onResize(int var1, T var2) {
      this.func_210459_b();
      BitArray ☃ = this.field_186021_b;
      IBlockStatePalette<T> ☃x = this.field_186022_c;
      this.func_186012_b(☃);

      for(int ☃xx = 0; ☃xx < ☃.func_188144_b(); ++☃xx) {
         T ☃xxx = ☃x.func_186039_a(☃.func_188142_a(☃xx));
         if (☃xxx != null) {
            this.func_186014_b(☃xx, ☃xxx);
         }
      }

      int ☃xx = this.field_186022_c.func_186041_a(☃);
      this.func_210460_c();
      return ☃xx;
   }

   public void func_186013_a(int var1, int var2, int var3, T var4) {
      this.func_210459_b();
      this.func_186014_b(func_186011_b(☃, ☃, ☃), ☃);
      this.func_210460_c();
   }

   protected void func_186014_b(int var1, T var2) {
      int ☃ = this.field_186022_c.func_186041_a(☃);
      this.field_186021_b.func_188141_a(☃, ☃);
   }

   public T func_186016_a(int var1, int var2, int var3) {
      return this.func_186015_a(func_186011_b(☃, ☃, ☃));
   }

   protected T func_186015_a(int var1) {
      T ☃ = this.field_186022_c.func_186039_a(this.field_186021_b.func_188142_a(☃));
      return (T)(☃ == null ? this.field_205526_g : ☃);
   }

   public void func_186010_a(PacketBuffer var1) {
      this.func_210459_b();
      int ☃ = ☃.readByte();
      if (this.field_186024_e != ☃) {
         this.func_186012_b(☃);
      }

      this.field_186022_c.func_186038_a(☃);
      ☃.func_186873_b(this.field_186021_b.func_188143_a());
      this.func_210460_c();
   }

   public void func_186009_b(PacketBuffer var1) {
      this.func_210459_b();
      ☃.writeByte(this.field_186024_e);
      this.field_186022_c.func_186037_b(☃);
      ☃.func_186865_a(this.field_186021_b.func_188143_a());
      this.func_210460_c();
   }

   public void func_196964_a(NBTTagCompound var1, String var2, String var3) {
      this.func_210459_b();
      NBTTagList ☃ = ☃.func_150295_c(☃, 10);
      int ☃x = Math.max(4, MathHelper.func_151241_e(☃.size()));
      if (☃x != this.field_186024_e) {
         this.func_186012_b(☃x);
      }

      this.field_186022_c.func_196968_a(☃);
      long[] ☃ = ☃.func_197645_o(☃);
      int ☃x = ☃.length * 64 / 4096;
      if (this.field_186022_c == this.field_205521_b) {
         IBlockStatePalette<T> ☃xx = new BlockStatePaletteHashMap<>(this.field_205523_d, ☃x, this.field_205522_c, this.field_205524_e, this.field_205525_f);
         ☃xx.func_196968_a(☃);
         BitArray ☃xxx = new BitArray(☃x, 4096, ☃);

         for(int ☃xxxx = 0; ☃xxxx < 4096; ++☃xxxx) {
            this.field_186021_b.func_188141_a(☃xxxx, this.field_205521_b.func_186041_a(☃xx.func_186039_a(☃xxx.func_188142_a(☃xxxx))));
         }
      } else if (☃x == this.field_186024_e) {
         System.arraycopy(☃, 0, this.field_186021_b.func_188143_a(), 0, ☃.length);
      } else {
         BitArray ☃ = new BitArray(☃x, 4096, ☃);

         for(int ☃x = 0; ☃x < 4096; ++☃x) {
            this.field_186021_b.func_188141_a(☃x, ☃.func_188142_a(☃x));
         }
      }

      this.func_210460_c();
   }

   public void func_196963_b(NBTTagCompound var1, String var2, String var3) {
      this.func_210459_b();
      BlockStatePaletteHashMap<T> ☃ = new BlockStatePaletteHashMap<>(
         this.field_205523_d, this.field_186024_e, this.field_205522_c, this.field_205524_e, this.field_205525_f
      );
      ☃.func_186041_a(this.field_205526_g);
      int[] ☃x = new int[4096];

      for(int ☃xx = 0; ☃xx < 4096; ++☃xx) {
         ☃x[☃xx] = ☃.func_186041_a(this.func_186015_a(☃xx));
      }

      NBTTagList ☃xx = new NBTTagList();
      ☃.func_196969_b(☃xx);
      ☃.func_74782_a(☃, ☃xx);
      int ☃xxx = Math.max(4, MathHelper.func_151241_e(☃xx.size()));
      BitArray ☃xxxx = new BitArray(☃xxx, 4096);

      for(int ☃xxxxx = 0; ☃xxxxx < ☃x.length; ++☃xxxxx) {
         ☃xxxx.func_188141_a(☃xxxxx, ☃x[☃xxxxx]);
      }

      ☃.func_197644_a(☃, ☃xxxx.func_188143_a());
      this.func_210460_c();
   }

   public int func_186018_a() {
      return 1
         + this.field_186022_c.func_186040_a()
         + PacketBuffer.func_150790_a(this.field_186021_b.func_188144_b())
         + this.field_186021_b.func_188143_a().length * 8;
   }
}
