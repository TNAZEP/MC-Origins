package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class CrashReportCategory {
   private final CrashReport field_85078_a;
   private final String field_85076_b;
   private final List<CrashReportCategory.Entry> field_85077_c = Lists.<CrashReportCategory.Entry>newArrayList();
   private StackTraceElement[] field_85075_d = new StackTraceElement[0];

   public CrashReportCategory(CrashReport var1, String var2) {
      this.field_85078_a = ☃;
      this.field_85076_b = ☃;
   }

   public static String func_85074_a(double var0, double var2, double var4) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", ☃, ☃, ☃, func_180522_a(new BlockPos(☃, ☃, ☃)));
   }

   public static String func_180522_a(BlockPos var0) {
      return func_184876_a(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
   }

   public static String func_184876_a(int var0, int var1, int var2) {
      StringBuilder ☃ = new StringBuilder();

      try {
         ☃.append(String.format("World: (%d,%d,%d)", ☃, ☃, ☃));
      } catch (Throwable var16) {
         ☃.append("(Error finding world loc)");
      }

      ☃.append(", ");

      try {
         int ☃x = ☃ >> 4;
         int ☃xx = ☃ >> 4;
         int ☃xxx = ☃ & 15;
         int ☃xxxx = ☃ >> 4;
         int ☃xxxxx = ☃ & 15;
         int ☃xxxxxx = ☃x << 4;
         int ☃xxxxxxx = ☃xx << 4;
         int ☃xxxxxxxx = (☃x + 1 << 4) - 1;
         int ☃xxxxxxxxx = (☃xx + 1 << 4) - 1;
         ☃.append(
            String.format(
               "Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", ☃xxx, ☃xxxx, ☃xxxxx, ☃x, ☃xx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx
            )
         );
      } catch (Throwable var15) {
         ☃.append("(Error finding chunk loc)");
      }

      ☃.append(", ");

      try {
         int ☃x = ☃ >> 9;
         int ☃xx = ☃ >> 9;
         int ☃xxx = ☃x << 5;
         int ☃xxxx = ☃xx << 5;
         int ☃xxxxx = (☃x + 1 << 5) - 1;
         int ☃xxxxxx = (☃xx + 1 << 5) - 1;
         int ☃xxxxxxx = ☃x << 9;
         int ☃xxxxxxxx = ☃xx << 9;
         int ☃xxxxxxxxx = (☃x + 1 << 9) - 1;
         int ☃xxxxxxxxxx = (☃xx + 1 << 9) - 1;
         ☃.append(
            String.format(
               "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
               ☃x,
               ☃xx,
               ☃xxx,
               ☃xxxx,
               ☃xxxxx,
               ☃xxxxxx,
               ☃xxxxxxx,
               ☃xxxxxxxx,
               ☃xxxxxxxxx,
               ☃xxxxxxxxxx
            )
         );
      } catch (Throwable var14) {
         ☃.append("(Error finding world loc)");
      }

      return ☃.toString();
   }

   public void func_189529_a(String var1, ICrashReportDetail<String> var2) {
      try {
         this.func_71507_a(☃, ☃.call());
      } catch (Throwable var4) {
         this.func_71499_a(☃, var4);
      }
   }

   public void func_71507_a(String var1, Object var2) {
      this.field_85077_c.add(new CrashReportCategory.Entry(☃, ☃));
   }

   public void func_71499_a(String var1, Throwable var2) {
      this.func_71507_a(☃, ☃);
   }

   public int func_85073_a(int var1) {
      StackTraceElement[] ☃ = Thread.currentThread().getStackTrace();
      if (☃.length <= 0) {
         return 0;
      } else {
         this.field_85075_d = new StackTraceElement[☃.length - 3 - ☃];
         System.arraycopy(☃, 3 + ☃, this.field_85075_d, 0, this.field_85075_d.length);
         return this.field_85075_d.length;
      }
   }

   public boolean func_85069_a(StackTraceElement var1, StackTraceElement var2) {
      if (this.field_85075_d.length != 0 && ☃ != null) {
         StackTraceElement ☃ = this.field_85075_d[0];
         if (☃.isNativeMethod() == ☃.isNativeMethod()
            && ☃.getClassName().equals(☃.getClassName())
            && ☃.getFileName().equals(☃.getFileName())
            && ☃.getMethodName().equals(☃.getMethodName())) {
            if (☃ != null != this.field_85075_d.length > 1) {
               return false;
            } else if (☃ != null && !this.field_85075_d[1].equals(☃)) {
               return false;
            } else {
               this.field_85075_d[0] = ☃;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void func_85070_b(int var1) {
      StackTraceElement[] ☃ = new StackTraceElement[this.field_85075_d.length - ☃];
      System.arraycopy(this.field_85075_d, 0, ☃, 0, ☃.length);
      this.field_85075_d = ☃;
   }

   public void func_85072_a(StringBuilder var1) {
      ☃.append("-- ").append(this.field_85076_b).append(" --\n");
      ☃.append("Details:");

      for(CrashReportCategory.Entry ☃ : this.field_85077_c) {
         ☃.append("\n\t");
         ☃.append(☃.func_85089_a());
         ☃.append(": ");
         ☃.append(☃.func_85090_b());
      }

      if (this.field_85075_d != null && this.field_85075_d.length > 0) {
         ☃.append("\nStacktrace:");

         for(StackTraceElement ☃ : this.field_85075_d) {
            ☃.append("\n\tat ");
            ☃.append(☃);
         }
      }
   }

   public StackTraceElement[] func_147152_a() {
      return this.field_85075_d;
   }

   public static void func_175750_a(CrashReportCategory var0, BlockPos var1, @Nullable IBlockState var2) {
      if (☃ != null) {
         ☃.func_189529_a("Block", ☃::toString);
      }

      ☃.func_189529_a("Block location", () -> func_180522_a(☃));
   }

   static class Entry {
      private final String field_85092_a;
      private final String field_85091_b;

      public Entry(String var1, Object var2) {
         this.field_85092_a = ☃;
         if (☃ == null) {
            this.field_85091_b = "~~NULL~~";
         } else if (☃ instanceof Throwable) {
            Throwable ☃ = (Throwable)☃;
            this.field_85091_b = "~~ERROR~~ " + ☃.getClass().getSimpleName() + ": " + ☃.getMessage();
         } else {
            this.field_85091_b = ☃.toString();
         }
      }

      public String func_85089_a() {
         return this.field_85092_a;
      }

      public String func_85090_b() {
         return this.field_85091_b;
      }
   }
}
