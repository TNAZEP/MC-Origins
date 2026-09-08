package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.util.text.TextComponentTranslation;

public class JsonToNBT {
   public static final SimpleCommandExceptionType field_197657_a = new SimpleCommandExceptionType(new TextComponentTranslation("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType field_197658_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType field_197659_c = new SimpleCommandExceptionType(new TextComponentTranslation("argument.nbt.expected.value"));
   public static final Dynamic2CommandExceptionType field_197660_d = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.nbt.list.mixed", var0, var1)
   );
   public static final Dynamic2CommandExceptionType field_197661_e = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.nbt.array.mixed", var0, var1)
   );
   public static final DynamicCommandExceptionType field_197662_f = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.nbt.array.invalid", var0)
   );
   private static final Pattern field_193615_a = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern field_193616_b = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern field_193617_c = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern field_193618_d = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern field_193619_e = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern field_193620_f = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern field_193621_g = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader field_197663_n;

   public static NBTTagCompound func_180713_a(String var0) throws CommandSyntaxException {
      return new JsonToNBT(new StringReader(☃)).func_193609_a();
   }

   @VisibleForTesting
   NBTTagCompound func_193609_a() throws CommandSyntaxException {
      NBTTagCompound ☃ = this.func_193593_f();
      this.field_197663_n.skipWhitespace();
      if (this.field_197663_n.canRead()) {
         throw field_197657_a.createWithContext(this.field_197663_n);
      } else {
         return ☃;
      }
   }

   public JsonToNBT(StringReader var1) {
      this.field_197663_n = ☃;
   }

   protected String func_193601_b() throws CommandSyntaxException {
      this.field_197663_n.skipWhitespace();
      if (!this.field_197663_n.canRead()) {
         throw field_197658_b.createWithContext(this.field_197663_n);
      } else {
         return this.field_197663_n.readString();
      }
   }

   protected INBTBase func_193611_c() throws CommandSyntaxException {
      this.field_197663_n.skipWhitespace();
      int ☃ = this.field_197663_n.getCursor();
      if (this.field_197663_n.peek() == '"') {
         return new NBTTagString(this.field_197663_n.readQuotedString());
      } else {
         String ☃ = this.field_197663_n.readUnquotedString();
         if (☃.isEmpty()) {
            this.field_197663_n.setCursor(☃);
            throw field_197659_c.createWithContext(this.field_197663_n);
         } else {
            return this.func_193596_c(☃);
         }
      }
   }

   private INBTBase func_193596_c(String var1) {
      try {
         if (field_193617_c.matcher(☃).matches()) {
            return new NBTTagFloat(Float.parseFloat(☃.substring(0, ☃.length() - 1)));
         }

         if (field_193618_d.matcher(☃).matches()) {
            return new NBTTagByte(Byte.parseByte(☃.substring(0, ☃.length() - 1)));
         }

         if (field_193619_e.matcher(☃).matches()) {
            return new NBTTagLong(Long.parseLong(☃.substring(0, ☃.length() - 1)));
         }

         if (field_193620_f.matcher(☃).matches()) {
            return new NBTTagShort(Short.parseShort(☃.substring(0, ☃.length() - 1)));
         }

         if (field_193621_g.matcher(☃).matches()) {
            return new NBTTagInt(Integer.parseInt(☃));
         }

         if (field_193616_b.matcher(☃).matches()) {
            return new NBTTagDouble(Double.parseDouble(☃.substring(0, ☃.length() - 1)));
         }

         if (field_193615_a.matcher(☃).matches()) {
            return new NBTTagDouble(Double.parseDouble(☃));
         }

         if ("true".equalsIgnoreCase(☃)) {
            return new NBTTagByte((byte)1);
         }

         if ("false".equalsIgnoreCase(☃)) {
            return new NBTTagByte((byte)0);
         }
      } catch (NumberFormatException var3) {
      }

      return new NBTTagString(☃);
   }

   protected INBTBase func_193610_d() throws CommandSyntaxException {
      this.field_197663_n.skipWhitespace();
      if (!this.field_197663_n.canRead()) {
         throw field_197659_c.createWithContext(this.field_197663_n);
      } else {
         char ☃ = this.field_197663_n.peek();
         if (☃ == '{') {
            return this.func_193593_f();
         } else {
            return ☃ == '[' ? this.func_193605_e() : this.func_193611_c();
         }
      }
   }

   protected INBTBase func_193605_e() throws CommandSyntaxException {
      return this.field_197663_n.canRead(3) && this.field_197663_n.peek(1) != '"' && this.field_197663_n.peek(2) == ';'
         ? this.func_193606_k()
         : this.func_193600_j();
   }

   public NBTTagCompound func_193593_f() throws CommandSyntaxException {
      this.func_193604_b('{');
      NBTTagCompound ☃ = new NBTTagCompound();
      this.field_197663_n.skipWhitespace();

      while(this.field_197663_n.canRead() && this.field_197663_n.peek() != '}') {
         int ☃x = this.field_197663_n.getCursor();
         String ☃xx = this.func_193601_b();
         if (☃xx.isEmpty()) {
            this.field_197663_n.setCursor(☃x);
            throw field_197658_b.createWithContext(this.field_197663_n);
         }

         this.func_193604_b(':');
         ☃.func_74782_a(☃xx, this.func_193610_d());
         if (!this.func_193613_m()) {
            break;
         }

         if (!this.field_197663_n.canRead()) {
            throw field_197658_b.createWithContext(this.field_197663_n);
         }
      }

      this.func_193604_b('}');
      return ☃;
   }

   private INBTBase func_193600_j() throws CommandSyntaxException {
      this.func_193604_b('[');
      this.field_197663_n.skipWhitespace();
      if (!this.field_197663_n.canRead()) {
         throw field_197659_c.createWithContext(this.field_197663_n);
      } else {
         NBTTagList ☃ = new NBTTagList();
         int ☃x = -1;

         while(this.field_197663_n.peek() != ']') {
            int ☃xx = this.field_197663_n.getCursor();
            INBTBase ☃xxx = this.func_193610_d();
            int ☃xxxx = ☃xxx.func_74732_a();
            if (☃x < 0) {
               ☃x = ☃xxxx;
            } else if (☃xxxx != ☃x) {
               this.field_197663_n.setCursor(☃xx);
               throw field_197660_d.createWithContext(this.field_197663_n, INBTBase.func_193581_j(☃xxxx), INBTBase.func_193581_j(☃x));
            }

            ☃.add(☃xxx);
            if (!this.func_193613_m()) {
               break;
            }

            if (!this.field_197663_n.canRead()) {
               throw field_197659_c.createWithContext(this.field_197663_n);
            }
         }

         this.func_193604_b(']');
         return ☃;
      }
   }

   private INBTBase func_193606_k() throws CommandSyntaxException {
      this.func_193604_b('[');
      int ☃ = this.field_197663_n.getCursor();
      char ☃x = this.field_197663_n.read();
      this.field_197663_n.read();
      this.field_197663_n.skipWhitespace();
      if (!this.field_197663_n.canRead()) {
         throw field_197659_c.createWithContext(this.field_197663_n);
      } else if (☃x == 'B') {
         return new NBTTagByteArray(this.func_193603_a((byte)7, (byte)1));
      } else if (☃x == 'L') {
         return new NBTTagLongArray(this.func_193603_a((byte)12, (byte)4));
      } else if (☃x == 'I') {
         return new NBTTagIntArray(this.func_193603_a((byte)11, (byte)3));
      } else {
         this.field_197663_n.setCursor(☃);
         throw field_197662_f.createWithContext(this.field_197663_n, String.valueOf(☃x));
      }
   }

   private <T extends Number> List<T> func_193603_a(byte var1, byte var2) throws CommandSyntaxException {
      List<T> ☃ = Lists.newArrayList();

      while(this.field_197663_n.peek() != ']') {
         int ☃x = this.field_197663_n.getCursor();
         INBTBase ☃xx = this.func_193610_d();
         int ☃xxx = ☃xx.func_74732_a();
         if (☃xxx != ☃) {
            this.field_197663_n.setCursor(☃x);
            throw field_197661_e.createWithContext(this.field_197663_n, INBTBase.func_193581_j(☃xxx), INBTBase.func_193581_j(☃));
         }

         if (☃ == 1) {
            ☃.add(((NBTPrimitive)☃xx).func_150290_f());
         } else if (☃ == 4) {
            ☃.add(((NBTPrimitive)☃xx).func_150291_c());
         } else {
            ☃.add(((NBTPrimitive)☃xx).func_150287_d());
         }

         if (!this.func_193613_m()) {
            break;
         }

         if (!this.field_197663_n.canRead()) {
            throw field_197659_c.createWithContext(this.field_197663_n);
         }
      }

      this.func_193604_b(']');
      return ☃;
   }

   private boolean func_193613_m() {
      this.field_197663_n.skipWhitespace();
      if (this.field_197663_n.canRead() && this.field_197663_n.peek() == ',') {
         this.field_197663_n.skip();
         this.field_197663_n.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void func_193604_b(char var1) throws CommandSyntaxException {
      this.field_197663_n.skipWhitespace();
      this.field_197663_n.expect(☃);
   }
}
