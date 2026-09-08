package net.minecraft.util.text;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.minecraft.util.text.translation.LanguageMap;

public class TextComponentTranslation extends TextComponentBase {
   private static final LanguageMap field_200526_d = new LanguageMap();
   private static final LanguageMap field_200527_e = LanguageMap.func_74808_a();
   private final String field_150276_d;
   private final Object[] field_150277_e;
   private final Object field_150274_f = new Object();
   private long field_150275_g = -1L;
   @VisibleForTesting
   List<ITextComponent> field_150278_b = Lists.<ITextComponent>newArrayList();
   public static final Pattern field_150279_c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public TextComponentTranslation(String var1, Object... var2) {
      this.field_150276_d = ☃;
      this.field_150277_e = ☃;

      for(int ☃ = 0; ☃ < ☃.length; ++☃) {
         Object ☃x = ☃[☃];
         if (☃x instanceof ITextComponent) {
            ITextComponent ☃xx = ((ITextComponent)☃x).func_212638_h();
            this.field_150277_e[☃] = ☃xx;
            ☃xx.func_150256_b().func_150221_a(this.func_150256_b());
         } else if (☃x == null) {
            this.field_150277_e[☃] = "null";
         }
      }
   }

   @VisibleForTesting
   synchronized void func_150270_g() {
      synchronized(this.field_150274_f) {
         long ☃ = field_200527_e.func_150510_c();
         if (☃ == this.field_150275_g) {
            return;
         }

         this.field_150275_g = ☃;
         this.field_150278_b.clear();
      }

      try {
         this.func_150269_b(field_200527_e.func_74805_b(this.field_150276_d));
      } catch (TextComponentTranslationFormatException var6) {
         this.field_150278_b.clear();

         try {
            this.func_150269_b(field_200526_d.func_74805_b(this.field_150276_d));
         } catch (TextComponentTranslationFormatException var5) {
            throw var6;
         }
      }
   }

   protected void func_150269_b(String var1) {
      Matcher ☃ = field_150279_c.matcher(☃);

      try {
         int ☃x = 0;

         int ☃;
         int ☃;
         for(☃ = 0; ☃.find(☃); ☃ = ☃) {
            int ☃xx = ☃.start();
            ☃ = ☃.end();
            if (☃xx > ☃) {
               ITextComponent ☃xxx = new TextComponentString(String.format(☃.substring(☃, ☃xx)));
               ☃xxx.func_150256_b().func_150221_a(this.func_150256_b());
               this.field_150278_b.add(☃xxx);
            }

            String ☃xx = ☃.group(2);
            String ☃xxx = ☃.substring(☃xx, ☃);
            if ("%".equals(☃xx) && "%%".equals(☃xxx)) {
               ITextComponent ☃xxxx = new TextComponentString("%");
               ☃xxxx.func_150256_b().func_150221_a(this.func_150256_b());
               this.field_150278_b.add(☃xxxx);
            } else {
               if (!"s".equals(☃xx)) {
                  throw new TextComponentTranslationFormatException(this, "Unsupported format: '" + ☃xxx + "'");
               }

               String ☃xx = ☃.group(1);
               int ☃xxx = ☃xx != null ? Integer.parseInt(☃xx) - 1 : ☃x++;
               if (☃xxx < this.field_150277_e.length) {
                  this.field_150278_b.add(this.func_150272_a(☃xxx));
               }
            }
         }

         if (☃ < ☃.length()) {
            ITextComponent ☃xx = new TextComponentString(String.format(☃.substring(☃)));
            ☃xx.func_150256_b().func_150221_a(this.func_150256_b());
            this.field_150278_b.add(☃xx);
         }
      } catch (IllegalFormatException var11) {
         throw new TextComponentTranslationFormatException(this, var11);
      }
   }

   private ITextComponent func_150272_a(int var1) {
      if (☃ >= this.field_150277_e.length) {
         throw new TextComponentTranslationFormatException(this, ☃);
      } else {
         Object ☃x = this.field_150277_e[☃];
         ITextComponent ☃;
         if (☃x instanceof ITextComponent) {
            ☃ = (ITextComponent)☃x;
         } else {
            ☃ = new TextComponentString(☃x == null ? "null" : ☃x.toString());
            ☃.func_150256_b().func_150221_a(this.func_150256_b());
         }

         return ☃;
      }
   }

   @Override
   public ITextComponent func_150255_a(Style var1) {
      super.func_150255_a(☃);

      for(Object ☃ : this.field_150277_e) {
         if (☃ instanceof ITextComponent) {
            ((ITextComponent)☃).func_150256_b().func_150221_a(this.func_150256_b());
         }
      }

      if (this.field_150275_g > -1L) {
         for(ITextComponent ☃ : this.field_150278_b) {
            ☃.func_150256_b().func_150221_a(☃);
         }
      }

      return this;
   }

   @Override
   public Stream<ITextComponent> func_212640_c() {
      this.func_150270_g();
      return Streams.concat(this.field_150278_b.stream(), this.field_150264_a.stream()).flatMap(ITextComponent::func_212640_c);
   }

   @Override
   public String func_150261_e() {
      this.func_150270_g();
      StringBuilder ☃ = new StringBuilder();

      for(ITextComponent ☃x : this.field_150278_b) {
         ☃.append(☃x.func_150261_e());
      }

      return ☃.toString();
   }

   public TextComponentTranslation func_150259_f() {
      Object[] ☃ = new Object[this.field_150277_e.length];

      for(int ☃x = 0; ☃x < this.field_150277_e.length; ++☃x) {
         if (this.field_150277_e[☃x] instanceof ITextComponent) {
            ☃[☃x] = ((ITextComponent)this.field_150277_e[☃x]).func_212638_h();
         } else {
            ☃[☃x] = this.field_150277_e[☃x];
         }
      }

      return new TextComponentTranslation(this.field_150276_d, ☃);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof TextComponentTranslation)) {
         return false;
      } else {
         TextComponentTranslation ☃ = (TextComponentTranslation)☃;
         return Arrays.equals(this.field_150277_e, ☃.field_150277_e) && this.field_150276_d.equals(☃.field_150276_d) && super.equals(☃);
      }
   }

   @Override
   public int hashCode() {
      int ☃ = super.hashCode();
      ☃ = 31 * ☃ + this.field_150276_d.hashCode();
      return 31 * ☃ + Arrays.hashCode(this.field_150277_e);
   }

   @Override
   public String toString() {
      return "TranslatableComponent{key='"
         + this.field_150276_d
         + '\''
         + ", args="
         + Arrays.toString(this.field_150277_e)
         + ", siblings="
         + this.field_150264_a
         + ", style="
         + this.func_150256_b()
         + '}';
   }

   public String func_150268_i() {
      return this.field_150276_d;
   }

   public Object[] func_150271_j() {
      return this.field_150277_e;
   }
}
