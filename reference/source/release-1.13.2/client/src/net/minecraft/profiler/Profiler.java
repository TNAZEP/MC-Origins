package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
   private static final Logger field_151234_b = LogManager.getLogger();
   private final List<String> field_76325_b = Lists.newArrayList();
   private final List<Long> field_76326_c = Lists.newArrayList();
   private boolean field_76327_a;
   private String field_76323_d = "";
   private final Map<String, Long> field_76324_e = Maps.newHashMap();
   private long field_199099_g;
   private int field_199100_h;

   public boolean func_199094_a() {
      return this.field_76327_a;
   }

   public void func_199098_b() {
      this.field_76327_a = false;
   }

   public long func_199097_c() {
      return this.field_199099_g;
   }

   public int func_199096_d() {
      return this.field_199100_h;
   }

   public void func_199095_a(int var1) {
      if (!this.field_76327_a) {
         this.field_76327_a = true;
         this.field_76324_e.clear();
         this.field_76323_d = "";
         this.field_76325_b.clear();
         this.field_199100_h = ☃;
         this.field_199099_g = Util.func_211178_c();
      }
   }

   public void func_76320_a(String var1) {
      if (this.field_76327_a) {
         if (!this.field_76323_d.isEmpty()) {
            this.field_76323_d = this.field_76323_d + ".";
         }

         this.field_76323_d = this.field_76323_d + ☃;
         this.field_76325_b.add(this.field_76323_d);
         this.field_76326_c.add(Util.func_211178_c());
      }
   }

   public void func_194340_a(Supplier<String> var1) {
      if (this.field_76327_a) {
         this.func_76320_a((String)☃.get());
      }
   }

   public void func_76319_b() {
      if (this.field_76327_a && !this.field_76326_c.isEmpty()) {
         long ☃ = Util.func_211178_c();
         long ☃x = this.field_76326_c.remove(this.field_76326_c.size() - 1);
         this.field_76325_b.remove(this.field_76325_b.size() - 1);
         long ☃xx = ☃ - ☃x;
         if (this.field_76324_e.containsKey(this.field_76323_d)) {
            this.field_76324_e.put(this.field_76323_d, this.field_76324_e.get(this.field_76323_d) + ☃xx);
         } else {
            this.field_76324_e.put(this.field_76323_d, ☃xx);
         }

         if (☃xx > 100000000L) {
            field_151234_b.warn("Something's taking too long! '{}' took aprox {} ms", this.field_76323_d, (double)☃xx / 1000000.0);
         }

         this.field_76323_d = this.field_76325_b.isEmpty() ? "" : (String)this.field_76325_b.get(this.field_76325_b.size() - 1);
      }
   }

   public List<Profiler.Result> func_76321_b(String var1) {
      String ☃ = ☃;
      long ☃x = this.field_76324_e.containsKey("root") ? this.field_76324_e.get("root") : 0L;
      long ☃xx = this.field_76324_e.containsKey(☃) ? this.field_76324_e.get(☃) : -1L;
      List<Profiler.Result> ☃xxx = Lists.<Profiler.Result>newArrayList();
      if (!☃.isEmpty()) {
         ☃ = ☃ + ".";
      }

      long ☃ = 0L;

      for(String ☃x : this.field_76324_e.keySet()) {
         if (☃x.length() > ☃.length() && ☃x.startsWith(☃) && ☃x.indexOf(".", ☃.length() + 1) < 0) {
            ☃ += this.field_76324_e.get(☃x);
         }
      }

      float ☃x = (float)☃;
      if (☃ < ☃xx) {
         ☃ = ☃xx;
      }

      if (☃x < ☃) {
         ☃x = ☃;
      }

      for(String ☃x : this.field_76324_e.keySet()) {
         if (☃x.length() > ☃.length() && ☃x.startsWith(☃) && ☃x.indexOf(".", ☃.length() + 1) < 0) {
            long ☃xx = this.field_76324_e.get(☃x);
            double ☃xxx = (double)☃xx * 100.0 / (double)☃;
            double ☃xxxx = (double)☃xx * 100.0 / (double)☃x;
            String ☃xxxxx = ☃x.substring(☃.length());
            ☃xxx.add(new Profiler.Result(☃xxxxx, ☃xxx, ☃xxxx));
         }
      }

      for(String ☃x : this.field_76324_e.keySet()) {
         this.field_76324_e.put(☃x, this.field_76324_e.get(☃x) * 999L / 1000L);
      }

      if ((float)☃ > ☃x) {
         ☃xxx.add(new Profiler.Result("unspecified", (double)((float)☃ - ☃x) * 100.0 / (double)☃, (double)((float)☃ - ☃x) * 100.0 / (double)☃x));
      }

      Collections.sort(☃xxx);
      ☃xxx.add(0, new Profiler.Result(☃, 100.0, (double)☃ * 100.0 / (double)☃x));
      return ☃xxx;
   }

   public void func_76318_c(String var1) {
      this.func_76319_b();
      this.func_76320_a(☃);
   }

   public void func_194339_b(Supplier<String> var1) {
      this.func_76319_b();
      this.func_194340_a(☃);
   }

   public String func_76322_c() {
      return this.field_76325_b.isEmpty() ? "[UNKNOWN]" : (String)this.field_76325_b.get(this.field_76325_b.size() - 1);
   }

   public static final class Result implements Comparable<Profiler.Result> {
      public double field_76332_a;
      public double field_76330_b;
      public String field_76331_c;

      public Result(String var1, double var2, double var4) {
         this.field_76331_c = ☃;
         this.field_76332_a = ☃;
         this.field_76330_b = ☃;
      }

      public int compareTo(Profiler.Result var1) {
         if (☃.field_76332_a < this.field_76332_a) {
            return -1;
         } else {
            return ☃.field_76332_a > this.field_76332_a ? 1 : ☃.field_76331_c.compareTo(this.field_76331_c);
         }
      }

      public int func_76329_a() {
         return (this.field_76331_c.hashCode() & 11184810) + 4473924;
      }
   }
}
