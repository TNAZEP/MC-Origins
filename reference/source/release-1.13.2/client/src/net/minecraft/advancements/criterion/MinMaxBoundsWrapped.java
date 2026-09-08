package net.minecraft.advancements.criterion;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextComponentTranslation;

public class MinMaxBoundsWrapped {
   public static final MinMaxBoundsWrapped field_207926_a = new MinMaxBoundsWrapped(null, null);
   public static final SimpleCommandExceptionType field_211362_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.range.ints"));
   private final Float field_207929_d;
   private final Float field_207930_e;

   public MinMaxBoundsWrapped(@Nullable Float var1, @Nullable Float var2) {
      this.field_207929_d = ☃;
      this.field_207930_e = ☃;
   }

   @Nullable
   public Float func_207923_a() {
      return this.field_207929_d;
   }

   @Nullable
   public Float func_207925_b() {
      return this.field_207930_e;
   }

   public static MinMaxBoundsWrapped func_207921_a(StringReader var0, boolean var1, Function<Float, Float> var2) throws CommandSyntaxException {
      if (!☃.canRead()) {
         throw MinMaxBounds.field_196978_b.createWithContext(☃);
      } else {
         int ☃x = ☃.getCursor();
         Float ☃xx = func_207922_a(func_207924_b(☃, ☃), ☃);
         Float ☃;
         if (☃.canRead(2) && ☃.peek() == '.' && ☃.peek(1) == '.') {
            ☃.skip();
            ☃.skip();
            ☃ = func_207922_a(func_207924_b(☃, ☃), ☃);
            if (☃xx == null && ☃ == null) {
               ☃.setCursor(☃x);
               throw MinMaxBounds.field_196978_b.createWithContext(☃);
            }
         } else {
            if (!☃ && ☃.canRead() && ☃.peek() == '.') {
               ☃.setCursor(☃x);
               throw field_211362_b.createWithContext(☃);
            }

            ☃ = ☃xx;
         }

         if (☃xx == null && ☃ == null) {
            ☃.setCursor(☃x);
            throw MinMaxBounds.field_196978_b.createWithContext(☃);
         } else {
            return new MinMaxBoundsWrapped(☃xx, ☃);
         }
      }
   }

   @Nullable
   private static Float func_207924_b(StringReader var0, boolean var1) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();

      while(☃.canRead() && func_207920_c(☃, ☃)) {
         ☃.skip();
      }

      String ☃x = ☃.getString().substring(☃, ☃.getCursor());
      if (☃x.isEmpty()) {
         return null;
      } else {
         try {
            return Float.parseFloat(☃x);
         } catch (NumberFormatException var5) {
            if (☃) {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(☃, ☃x);
            } else {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(☃, ☃x);
            }
         }
      }
   }

   private static boolean func_207920_c(StringReader var0, boolean var1) {
      char ☃ = ☃.peek();
      if ((☃ < '0' || ☃ > '9') && ☃ != '-') {
         if (☃ && ☃ == '.') {
            return !☃.canRead(2) || ☃.peek(1) != '.';
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static Float func_207922_a(@Nullable Float var0, Function<Float, Float> var1) {
      return ☃ == null ? null : (Float)☃.apply(☃);
   }
}
