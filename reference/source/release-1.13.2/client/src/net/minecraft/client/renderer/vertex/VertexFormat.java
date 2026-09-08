package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormat {
   private static final Logger field_177357_a = LogManager.getLogger();
   private final List<VertexFormatElement> field_177355_b = Lists.<VertexFormatElement>newArrayList();
   private final List<Integer> field_177356_c = Lists.newArrayList();
   private int field_177353_d;
   private int field_177354_e = -1;
   private final List<Integer> field_177351_f = Lists.newArrayList();
   private int field_177352_g = -1;

   public VertexFormat(VertexFormat var1) {
      this();

      for(int ☃ = 0; ☃ < ☃.func_177345_h(); ++☃) {
         this.func_181721_a(☃.func_177348_c(☃));
      }

      this.field_177353_d = ☃.func_177338_f();
   }

   public VertexFormat() {
   }

   public void func_207749_a() {
      this.field_177355_b.clear();
      this.field_177356_c.clear();
      this.field_177354_e = -1;
      this.field_177351_f.clear();
      this.field_177352_g = -1;
      this.field_177353_d = 0;
   }

   public VertexFormat func_181721_a(VertexFormatElement var1) {
      if (☃.func_177374_g() && this.func_177341_i()) {
         field_177357_a.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
         return this;
      } else {
         this.field_177355_b.add(☃);
         this.field_177356_c.add(this.field_177353_d);
         switch(☃.func_177375_c()) {
            case NORMAL:
               this.field_177352_g = this.field_177353_d;
               break;
            case COLOR:
               this.field_177354_e = this.field_177353_d;
               break;
            case UV:
               this.field_177351_f.add(☃.func_177369_e(), this.field_177353_d);
         }

         this.field_177353_d += ☃.func_177368_f();
         return this;
      }
   }

   public boolean func_207751_b() {
      return this.field_177352_g >= 0;
   }

   public int func_177342_c() {
      return this.field_177352_g;
   }

   public boolean func_207752_d() {
      return this.field_177354_e >= 0;
   }

   public int func_177340_e() {
      return this.field_177354_e;
   }

   public boolean func_207750_a(int var1) {
      return this.field_177351_f.size() - 1 >= ☃;
   }

   public int func_177344_b(int var1) {
      return this.field_177351_f.get(☃);
   }

   public String toString() {
      String ☃ = "format: " + this.field_177355_b.size() + " elements: ";

      for(int ☃x = 0; ☃x < this.field_177355_b.size(); ++☃x) {
         ☃ = ☃ + ((VertexFormatElement)this.field_177355_b.get(☃x)).toString();
         if (☃x != this.field_177355_b.size() - 1) {
            ☃ = ☃ + " ";
         }
      }

      return ☃;
   }

   private boolean func_177341_i() {
      int ☃ = 0;

      for(int ☃x = this.field_177355_b.size(); ☃ < ☃x; ++☃) {
         VertexFormatElement ☃xx = (VertexFormatElement)this.field_177355_b.get(☃);
         if (☃xx.func_177374_g()) {
            return true;
         }
      }

      return false;
   }

   public int func_181719_f() {
      return this.func_177338_f() / 4;
   }

   public int func_177338_f() {
      return this.field_177353_d;
   }

   public List<VertexFormatElement> func_177343_g() {
      return this.field_177355_b;
   }

   public int func_177345_h() {
      return this.field_177355_b.size();
   }

   public VertexFormatElement func_177348_c(int var1) {
      return (VertexFormatElement)this.field_177355_b.get(☃);
   }

   public int func_181720_d(int var1) {
      return this.field_177356_c.get(☃);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (☃ != null && this.getClass() == ☃.getClass()) {
         VertexFormat ☃ = (VertexFormat)☃;
         if (this.field_177353_d != ☃.field_177353_d) {
            return false;
         } else {
            return !this.field_177355_b.equals(☃.field_177355_b) ? false : this.field_177356_c.equals(☃.field_177356_c);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int ☃ = this.field_177355_b.hashCode();
      ☃ = 31 * ☃ + this.field_177356_c.hashCode();
      return 31 * ☃ + this.field_177353_d;
   }
}
