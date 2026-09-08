package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class MessageArgument implements ArgumentType<MessageArgument.Message> {
   private static final Collection<String> field_201313_a = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public static MessageArgument func_197123_a() {
      return new MessageArgument();
   }

   public static ITextComponent func_197124_a(CommandContext<CommandSource> var0, String var1) throws CommandSyntaxException {
      return ☃.<MessageArgument.Message>getArgument(☃, MessageArgument.Message.class).func_201312_a(☃.getSource(), ☃.getSource().func_197034_c(2));
   }

   public MessageArgument.Message parse(StringReader var1) throws CommandSyntaxException {
      return MessageArgument.Message.func_197113_a(☃, true);
   }

   @Override
   public Collection<String> getExamples() {
      return field_201313_a;
   }

   public static class Message {
      private final String field_197114_a;
      private final MessageArgument.Part[] field_197115_b;

      public Message(String var1, MessageArgument.Part[] var2) {
         this.field_197114_a = ☃;
         this.field_197115_b = ☃;
      }

      public ITextComponent func_201312_a(CommandSource var1, boolean var2) throws CommandSyntaxException {
         if (this.field_197115_b.length != 0 && ☃) {
            ITextComponent ☃ = new TextComponentString(this.field_197114_a.substring(0, this.field_197115_b[0].func_197117_a()));
            int ☃x = this.field_197115_b[0].func_197117_a();

            for(MessageArgument.Part ☃xx : this.field_197115_b) {
               ITextComponent ☃xxx = ☃xx.func_197116_a(☃);
               if (☃x < ☃xx.func_197117_a()) {
                  ☃.func_150258_a(this.field_197114_a.substring(☃x, ☃xx.func_197117_a()));
               }

               if (☃xxx != null) {
                  ☃.func_150257_a(☃xxx);
               }

               ☃x = ☃xx.func_197118_b();
            }

            if (☃x < this.field_197114_a.length()) {
               ☃.func_150258_a(this.field_197114_a.substring(☃x, this.field_197114_a.length()));
            }

            return ☃;
         } else {
            return new TextComponentString(this.field_197114_a);
         }
      }

      public static MessageArgument.Message func_197113_a(StringReader var0, boolean var1) throws CommandSyntaxException {
         String ☃ = ☃.getString().substring(☃.getCursor(), ☃.getTotalLength());
         if (!☃) {
            ☃.setCursor(☃.getTotalLength());
            return new MessageArgument.Message(☃, new MessageArgument.Part[0]);
         } else {
            List<MessageArgument.Part> ☃ = Lists.<MessageArgument.Part>newArrayList();
            int ☃x = ☃.getCursor();

            while(true) {
               int ☃;
               EntitySelector ☃;
               while(true) {
                  if (!☃.canRead()) {
                     return new MessageArgument.Message(☃, (MessageArgument.Part[])☃.toArray(new MessageArgument.Part[☃.size()]));
                  }

                  if (☃.peek() == '@') {
                     ☃ = ☃.getCursor();

                     try {
                        EntitySelectorParser ☃xx = new EntitySelectorParser(☃);
                        ☃ = ☃xx.func_201345_m();
                        break;
                     } catch (CommandSyntaxException var8) {
                        if (var8.getType() != EntitySelectorParser.field_197410_c && var8.getType() != EntitySelectorParser.field_197409_b) {
                           throw var8;
                        }

                        ☃.setCursor(☃ + 1);
                     }
                  } else {
                     ☃.skip();
                  }
               }

               ☃.add(new MessageArgument.Part(☃ - ☃x, ☃.getCursor() - ☃x, ☃));
            }
         }
      }
   }

   public static class Part {
      private final int field_197119_a;
      private final int field_197120_b;
      private final EntitySelector field_197121_c;

      public Part(int var1, int var2, EntitySelector var3) {
         this.field_197119_a = ☃;
         this.field_197120_b = ☃;
         this.field_197121_c = ☃;
      }

      public int func_197117_a() {
         return this.field_197119_a;
      }

      public int func_197118_b() {
         return this.field_197120_b;
      }

      @Nullable
      public ITextComponent func_197116_a(CommandSource var1) throws CommandSyntaxException {
         return EntitySelector.func_197350_a(this.field_197121_c.func_197341_b(☃));
      }
   }
}
