package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.chunk.Chunk;

public class ServerTickList<T> implements ITickList<T> {
   protected final Predicate<T> field_205371_a;
   protected final Function<T, ResourceLocation> field_205372_b;
   protected final Function<ResourceLocation, T> field_205373_c;
   protected final Set<NextTickListEntry<T>> field_205374_d = Sets.<NextTickListEntry<T>>newHashSet();
   protected final TreeSet<NextTickListEntry<T>> field_205375_e = new TreeSet();
   private final WorldServer field_205376_f;
   private final List<NextTickListEntry<T>> field_205377_g = Lists.<NextTickListEntry<T>>newArrayList();
   private final Consumer<NextTickListEntry<T>> field_205378_h;

   public ServerTickList(
      WorldServer var1, Predicate<T> var2, Function<T, ResourceLocation> var3, Function<ResourceLocation, T> var4, Consumer<NextTickListEntry<T>> var5
   ) {
      this.field_205371_a = ☃;
      this.field_205372_b = ☃;
      this.field_205373_c = ☃;
      this.field_205376_f = ☃;
      this.field_205378_h = ☃;
   }

   public void func_205365_a() {
      int ☃ = this.field_205375_e.size();
      if (☃ != this.field_205374_d.size()) {
         throw new IllegalStateException("TickNextTick list out of synch");
      } else {
         if (☃ > 65536) {
            ☃ = 65536;
         }

         this.field_205376_f.field_72984_F.func_76320_a("cleaning");

         for(int ☃ = 0; ☃ < ☃; ++☃) {
            NextTickListEntry<T> ☃x = (NextTickListEntry)this.field_205375_e.first();
            if (☃x.field_77180_e > this.field_205376_f.func_82737_E()) {
               break;
            }

            this.field_205375_e.remove(☃x);
            this.field_205374_d.remove(☃x);
            this.field_205377_g.add(☃x);
         }

         this.field_205376_f.field_72984_F.func_76319_b();
         this.field_205376_f.field_72984_F.func_76320_a("ticking");
         Iterator<NextTickListEntry<T>> ☃ = this.field_205377_g.iterator();

         while(☃.hasNext()) {
            NextTickListEntry<T> ☃x = (NextTickListEntry)☃.next();
            ☃.remove();
            int ☃xx = 0;
            if (this.field_205376_f.func_175707_a(☃x.field_180282_a.func_177982_a(0, 0, 0), ☃x.field_180282_a.func_177982_a(0, 0, 0))) {
               try {
                  this.field_205378_h.accept(☃x);
               } catch (Throwable var8) {
                  CrashReport ☃xxx = CrashReport.func_85055_a(var8, "Exception while ticking");
                  CrashReportCategory ☃xxxx = ☃xxx.func_85058_a("Block being ticked");
                  CrashReportCategory.func_175750_a(☃xxxx, ☃x.field_180282_a, null);
                  throw new ReportedException(☃xxx);
               }
            } else {
               this.func_205360_a(☃x.field_180282_a, ☃x.func_151351_a(), 0);
            }
         }

         this.field_205376_f.field_72984_F.func_76319_b();
         this.field_205377_g.clear();
      }
   }

   @Override
   public boolean func_205361_b(BlockPos var1, T var2) {
      return this.field_205377_g.contains(new NextTickListEntry(☃, ☃));
   }

   public List<NextTickListEntry<T>> func_205364_a(Chunk var1, boolean var2) {
      ChunkPos ☃ = ☃.func_76632_l();
      int ☃x = (☃.field_77276_a << 4) - 2;
      int ☃xx = ☃x + 16 + 2;
      int ☃xxx = (☃.field_77275_b << 4) - 2;
      int ☃xxxx = ☃xxx + 16 + 2;
      return this.func_205366_a(new MutableBoundingBox(☃x, 0, ☃xxx, ☃xx, 256, ☃xxxx), ☃);
   }

   public List<NextTickListEntry<T>> func_205366_a(MutableBoundingBox var1, boolean var2) {
      List<NextTickListEntry<T>> ☃ = null;

      for(int ☃x = 0; ☃x < 2; ++☃x) {
         Iterator<NextTickListEntry<T>> ☃xx;
         if (☃x == 0) {
            ☃xx = this.field_205375_e.iterator();
         } else {
            ☃xx = this.field_205377_g.iterator();
         }

         while(☃xx.hasNext()) {
            NextTickListEntry<T> ☃xx = (NextTickListEntry)☃xx.next();
            BlockPos ☃xxx = ☃xx.field_180282_a;
            if (☃xxx.func_177958_n() >= ☃.field_78897_a
               && ☃xxx.func_177958_n() < ☃.field_78893_d
               && ☃xxx.func_177952_p() >= ☃.field_78896_c
               && ☃xxx.func_177952_p() < ☃.field_78892_f) {
               if (☃) {
                  if (☃x == 0) {
                     this.field_205374_d.remove(☃xx);
                  }

                  ☃xx.remove();
               }

               if (☃ == null) {
                  ☃ = Lists.<NextTickListEntry<T>>newArrayList();
               }

               ☃.add(☃xx);
            }
         }
      }

      return ☃ == null ? Collections.emptyList() : ☃;
   }

   public void func_205368_a(MutableBoundingBox var1, BlockPos var2) {
      for(NextTickListEntry<T> ☃ : this.func_205366_a(☃, false)) {
         if (☃.func_175898_b(☃.field_180282_a)) {
            BlockPos ☃x = ☃.field_180282_a.func_177971_a(☃);
            this.func_205367_b(☃x, ☃.func_151351_a(), (int)(☃.field_77180_e - this.field_205376_f.func_72912_H().func_82573_f()), ☃.field_82754_f);
         }
      }
   }

   public NBTTagList func_205363_a(Chunk var1) {
      List<NextTickListEntry<T>> ☃ = this.func_205364_a(☃, false);
      long ☃x = this.field_205376_f.func_82737_E();
      NBTTagList ☃xx = new NBTTagList();

      for(NextTickListEntry<T> ☃xxx : ☃) {
         NBTTagCompound ☃xxxx = new NBTTagCompound();
         ☃xxxx.func_74778_a("i", ((ResourceLocation)this.field_205372_b.apply(☃xxx.func_151351_a())).toString());
         ☃xxxx.func_74768_a("x", ☃xxx.field_180282_a.func_177958_n());
         ☃xxxx.func_74768_a("y", ☃xxx.field_180282_a.func_177956_o());
         ☃xxxx.func_74768_a("z", ☃xxx.field_180282_a.func_177952_p());
         ☃xxxx.func_74768_a("t", (int)(☃xxx.field_77180_e - ☃x));
         ☃xxxx.func_74768_a("p", ☃xxx.field_82754_f.func_205398_a());
         ☃xx.add((INBTBase)☃xxxx);
      }

      return ☃xx;
   }

   public void func_205369_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         T ☃xx = (T)this.field_205373_c.apply(new ResourceLocation(☃x.func_74779_i("i")));
         if (☃xx != null) {
            this.func_205367_b(
               new BlockPos(☃x.func_74762_e("x"), ☃x.func_74762_e("y"), ☃x.func_74762_e("z")),
               ☃xx,
               ☃x.func_74762_e("t"),
               TickPriority.func_205397_a(☃x.func_74762_e("p"))
            );
         }
      }
   }

   @Override
   public boolean func_205359_a(BlockPos var1, T var2) {
      return this.field_205374_d.contains(new NextTickListEntry(☃, ☃));
   }

   @Override
   public void func_205362_a(BlockPos var1, T var2, int var3, TickPriority var4) {
      if (!this.field_205371_a.test(☃)) {
         if (this.field_205376_f.func_175667_e(☃)) {
            this.func_205370_c(☃, ☃, ☃, ☃);
         }
      }
   }

   protected void func_205367_b(BlockPos var1, T var2, int var3, TickPriority var4) {
      if (!this.field_205371_a.test(☃)) {
         this.func_205370_c(☃, ☃, ☃, ☃);
      }
   }

   private void func_205370_c(BlockPos var1, T var2, int var3, TickPriority var4) {
      NextTickListEntry<T> ☃ = new NextTickListEntry<>(☃, ☃, (long)☃ + this.field_205376_f.func_82737_E(), ☃);
      if (!this.field_205374_d.contains(☃)) {
         this.field_205374_d.add(☃);
         this.field_205375_e.add(☃);
      }
   }
}
