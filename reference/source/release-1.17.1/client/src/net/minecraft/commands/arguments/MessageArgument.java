package net.minecraft.commands.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class MessageArgument implements ArgumentType<MessageArgument.Message> {
   private static final Collection<String> EXAMPLES = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public static MessageArgument message() {
      return new MessageArgument();
   }

   public static Component getMessage(CommandContext<CommandSourceStack> var0, String var1) throws CommandSyntaxException {
      return â˜ƒ.<MessageArgument.Message>getArgument(â˜ƒ, MessageArgument.Message.class).toComponent(â˜ƒ.getSource(), â˜ƒ.getSource().hasPermission(2));
   }

   public MessageArgument.Message parse(StringReader var1) throws CommandSyntaxException {
      return MessageArgument.Message.parseText(â˜ƒ, true);
   }

   @Override
   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public static class Message {
      private final String text;
      private final MessageArgument.Part[] parts;

      public Message(String var1, MessageArgument.Part[] var2) {
         this.text = â˜ƒ;
         this.parts = â˜ƒ;
      }

      public String getText() {
         return this.text;
      }

      public MessageArgument.Part[] getParts() {
         return this.parts;
      }

      public Component toComponent(CommandSourceStack var1, boolean var2) throws CommandSyntaxException {
         if (this.parts.length != 0 && â˜ƒ) {
            MutableComponent â˜ƒ = new TextComponent(this.text.substring(0, this.parts[0].getStart()));
            int â˜ƒx = this.parts[0].getStart();

            for(MessageArgument.Part â˜ƒxx : this.parts) {
               Component â˜ƒxxx = â˜ƒxx.toComponent(â˜ƒ);
               if (â˜ƒx < â˜ƒxx.getStart()) {
                  â˜ƒ.append(this.text.substring(â˜ƒx, â˜ƒxx.getStart()));
               }

               if (â˜ƒxxx != null) {
                  â˜ƒ.append(â˜ƒxxx);
               }

               â˜ƒx = â˜ƒxx.getEnd();
            }

            if (â˜ƒx < this.text.length()) {
               â˜ƒ.append(this.text.substring(â˜ƒx, this.text.length()));
            }

            return â˜ƒ;
         } else {
            return new TextComponent(this.text);
         }
      }

      public static MessageArgument.Message parseText(StringReader var0, boolean var1) throws CommandSyntaxException {
         String â˜ƒ = â˜ƒ.getString().substring(â˜ƒ.getCursor(), â˜ƒ.getTotalLength());
         if (!â˜ƒ) {
            â˜ƒ.setCursor(â˜ƒ.getTotalLength());
            return new MessageArgument.Message(â˜ƒ, new MessageArgument.Part[0]);
         } else {
            List<MessageArgument.Part> â˜ƒ = Lists.<MessageArgument.Part>newArrayList();
            int â˜ƒx = â˜ƒ.getCursor();

            while(true) {
               int â˜ƒ;
               EntitySelector â˜ƒ;
               while(true) {
                  if (!â˜ƒ.canRead()) {
                     return new MessageArgument.Message(â˜ƒ, (MessageArgument.Part[])â˜ƒ.toArray(new MessageArgument.Part[â˜ƒ.size()]));
                  }

                  if (â˜ƒ.peek() == '@') {
                     â˜ƒ = â˜ƒ.getCursor();

                     try {
                        EntitySelectorParser â˜ƒxx = new EntitySelectorParser(â˜ƒ);
                        â˜ƒ = â˜ƒxx.parse();
                        break;
                     } catch (CommandSyntaxException var8) {
                        if (var8.getType() != EntitySelectorParser.ERROR_MISSING_SELECTOR_TYPE
                           && var8.getType() != EntitySelectorParser.ERROR_UNKNOWN_SELECTOR_TYPE) {
                           throw var8;
                        }

                        â˜ƒ.setCursor(â˜ƒ + 1);
                     }
                  } else {
                     â˜ƒ.skip();
                  }
               }

               â˜ƒ.add(new MessageArgument.Part(â˜ƒ - â˜ƒx, â˜ƒ.getCursor() - â˜ƒx, â˜ƒ));
            }
         }
      }
   }

   public static class Part {
      private final int start;
      private final int end;
      private final EntitySelector selector;

      public Part(int var1, int var2, EntitySelector var3) {
         this.start = â˜ƒ;
         this.end = â˜ƒ;
         this.selector = â˜ƒ;
      }

      public int getStart() {
         return this.start;
      }

      public int getEnd() {
         return this.end;
      }

      public EntitySelector getSelector() {
         return this.selector;
      }

      @Nullable
      public Component toComponent(CommandSourceStack var1) throws CommandSyntaxException {
         return EntitySelector.joinNames(this.selector.findEntities(â˜ƒ));
      }
   }
}
