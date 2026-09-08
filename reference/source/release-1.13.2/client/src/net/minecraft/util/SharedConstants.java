package net.minecraft.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.types.constant.NamespacedStringType;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import net.minecraft.command.TranslatableExceptionProvider;
import net.minecraft.util.datafix.NamespacedSchema;

public class SharedConstants {
   public static final Level field_184877_a = Level.DISABLED;
   public static boolean field_206244_b;
   public static final char[] field_71567_b = new char[]{'/', '\n', '\r', '\t', '\u0000', '\f', '`', '?', '*', '\\', '<', '>', '|', '"', ':'};

   public static boolean func_71566_a(char var0) {
      return ☃ != 167 && ☃ >= ' ' && ☃ != 127;
   }

   public static String func_71565_a(String var0) {
      StringBuilder ☃ = new StringBuilder();

      for(char ☃x : ☃.toCharArray()) {
         if (func_71566_a(☃x)) {
            ☃.append(☃x);
         }
      }

      return ☃.toString();
   }

   static {
      ResourceLeakDetector.setLevel(field_184877_a);
      CommandSyntaxException.ENABLE_COMMAND_STACK_TRACES = false;
      CommandSyntaxException.BUILT_IN_EXCEPTIONS = new TranslatableExceptionProvider();
      NamespacedStringType.ENSURE_NAMESPACE = NamespacedSchema::func_206477_f;
   }
}
