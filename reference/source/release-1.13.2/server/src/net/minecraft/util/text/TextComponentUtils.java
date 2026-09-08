package net.minecraft.util.text;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;

public class TextComponentUtils {
   public static ITextComponent func_211401_a(ITextComponent var0, Style var1) {
      if (☃.func_150229_g()) {
         return ☃;
      } else {
         return ☃.func_150256_b().func_150229_g()
            ? ☃.func_150255_a(☃.func_150232_l())
            : new TextComponentString("").func_150257_a(☃).func_150255_a(☃.func_150232_l());
      }
   }

   public static ITextComponent func_197680_a(@Nullable CommandSource var0, ITextComponent var1, @Nullable Entity var2) throws CommandSyntaxException {
      ITextComponent ☃;
      if (☃ instanceof TextComponentScore && ☃ != null) {
         TextComponentScore ☃xx = (TextComponentScore)☃;
         String ☃x;
         if (☃xx.func_197666_h() != null) {
            List<? extends Entity> ☃xxx = ☃xx.func_197666_h().func_197341_b(☃);
            if (☃xxx.isEmpty()) {
               ☃x = ☃xx.func_179995_g();
            } else {
               if (☃xxx.size() != 1) {
                  throw EntityArgument.field_197098_a.create();
               }

               ☃x = ((Entity)☃xxx.get(0)).func_195047_I_();
            }
         } else {
            ☃x = ☃xx.func_179995_g();
         }

         String ☃x = ☃ != null && ☃x.equals("*") ? ☃.func_195047_I_() : ☃x;
         ☃ = new TextComponentScore(☃x, ☃xx.func_179994_h());
         ((TextComponentScore)☃).func_179997_b(☃xx.func_150261_e());
         ((TextComponentScore)☃).func_197665_b(☃);
      } else if (☃ instanceof TextComponentSelector && ☃ != null) {
         ☃ = ((TextComponentSelector)☃).func_197668_a(☃);
      } else if (☃ instanceof TextComponentString) {
         ☃ = new TextComponentString(((TextComponentString)☃).func_150265_g());
      } else if (☃ instanceof TextComponentKeybind) {
         ☃ = new TextComponentKeybind(((TextComponentKeybind)☃).func_193633_h());
      } else {
         if (!(☃ instanceof TextComponentTranslation)) {
            return ☃;
         }

         Object[] ☃ = ((TextComponentTranslation)☃).func_150271_j();

         for(int ☃x = 0; ☃x < ☃.length; ++☃x) {
            Object ☃xx = ☃[☃x];
            if (☃xx instanceof ITextComponent) {
               ☃[☃x] = func_197680_a(☃, (ITextComponent)☃xx, ☃);
            }
         }

         ☃ = new TextComponentTranslation(((TextComponentTranslation)☃).func_150268_i(), ☃);
      }

      for(ITextComponent ☃ : ☃.func_150253_a()) {
         ☃.func_150257_a(func_197680_a(☃, ☃, ☃));
      }

      return func_211401_a(☃, ☃.func_150256_b());
   }

   public static ITextComponent func_197679_a(GameProfile var0) {
      if (☃.getName() != null) {
         return new TextComponentString(☃.getName());
      } else {
         return ☃.getId() != null ? new TextComponentString(☃.getId().toString()) : new TextComponentString("(unknown)");
      }
   }

   public static ITextComponent func_197678_a(Collection<String> var0) {
      return func_197675_a(☃, var0x -> new TextComponentString(var0x).func_211708_a(TextFormatting.GREEN));
   }

   public static <T extends Comparable<T>> ITextComponent func_197675_a(Collection<T> var0, Function<T, ITextComponent> var1) {
      if (☃.isEmpty()) {
         return new TextComponentString("");
      } else if (☃.size() == 1) {
         return (ITextComponent)☃.apply(☃.iterator().next());
      } else {
         List<T> ☃ = Lists.newArrayList(☃);
         ☃.sort(Comparable::compareTo);
         return func_197677_b(☃, ☃);
      }
   }

   public static <T> ITextComponent func_197677_b(Collection<T> var0, Function<T, ITextComponent> var1) {
      if (☃.isEmpty()) {
         return new TextComponentString("");
      } else if (☃.size() == 1) {
         return (ITextComponent)☃.apply(☃.iterator().next());
      } else {
         ITextComponent ☃ = new TextComponentString("");
         boolean ☃x = true;

         for(T ☃xx : ☃) {
            if (!☃x) {
               ☃.func_150257_a(new TextComponentString(", ").func_211708_a(TextFormatting.GRAY));
            }

            ☃.func_150257_a((ITextComponent)☃.apply(☃xx));
            ☃x = false;
         }

         return ☃;
      }
   }

   public static ITextComponent func_197676_a(ITextComponent var0) {
      return new TextComponentString("[").func_150257_a(☃).func_150258_a("]");
   }

   public static ITextComponent func_202465_a(Message var0) {
      return (ITextComponent)(☃ instanceof ITextComponent ? (ITextComponent)☃ : new TextComponentString(☃.getString()));
   }
}
