package net.minecraft.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerFunctionManager;

public class CommandFunction {
   private final CommandFunction.Entry[] entries;
   final ResourceLocation id;

   public CommandFunction(ResourceLocation var1, CommandFunction.Entry[] var2) {
      this.id = â˜ƒ;
      this.entries = â˜ƒ;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public CommandFunction.Entry[] getEntries() {
      return this.entries;
   }

   public static CommandFunction fromLines(ResourceLocation var0, CommandDispatcher<CommandSourceStack> var1, CommandSourceStack var2, List<String> var3) {
      List<CommandFunction.Entry> â˜ƒ = Lists.<CommandFunction.Entry>newArrayListWithCapacity(â˜ƒ.size());

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         int â˜ƒxx = â˜ƒx + 1;
         String â˜ƒxxx = ((String)â˜ƒ.get(â˜ƒx)).trim();
         StringReader â˜ƒxxxx = new StringReader(â˜ƒxxx);
         if (â˜ƒxxxx.canRead() && â˜ƒxxxx.peek() != '#') {
            if (â˜ƒxxxx.peek() == '/') {
               â˜ƒxxxx.skip();
               if (â˜ƒxxxx.peek() == '/') {
                  throw new IllegalArgumentException(
                     "Unknown or invalid command '" + â˜ƒxxx + "' on line " + â˜ƒxx + " (if you intended to make a comment, use '#' not '//')"
                  );
               }

               String â˜ƒxxxxx = â˜ƒxxxx.readUnquotedString();
               throw new IllegalArgumentException(
                  "Unknown or invalid command '" + â˜ƒxxx + "' on line " + â˜ƒxx + " (did you mean '" + â˜ƒxxxxx + "'? Do not use a preceding forwards slash.)"
               );
            }

            try {
               ParseResults<CommandSourceStack> â˜ƒxxxxx = â˜ƒ.parse(â˜ƒxxxx, â˜ƒ);
               if (â˜ƒxxxxx.getReader().canRead()) {
                  throw Commands.getParseException(â˜ƒxxxxx);
               }

               â˜ƒ.add(new CommandFunction.CommandEntry(â˜ƒxxxxx));
            } catch (CommandSyntaxException var10) {
               throw new IllegalArgumentException("Whilst parsing command on line " + â˜ƒxx + ": " + var10.getMessage());
            }
         }
      }

      return new CommandFunction(â˜ƒ, (CommandFunction.Entry[])â˜ƒ.toArray(new CommandFunction.Entry[0]));
   }

   public static class CacheableFunction {
      public static final CommandFunction.CacheableFunction NONE = new CommandFunction.CacheableFunction((ResourceLocation)null);
      @Nullable
      private final ResourceLocation id;
      private boolean resolved;
      private Optional<CommandFunction> function = Optional.empty();

      public CacheableFunction(@Nullable ResourceLocation var1) {
         this.id = â˜ƒ;
      }

      public CacheableFunction(CommandFunction var1) {
         this.resolved = true;
         this.id = null;
         this.function = Optional.of(â˜ƒ);
      }

      public Optional<CommandFunction> get(ServerFunctionManager var1) {
         if (!this.resolved) {
            if (this.id != null) {
               this.function = â˜ƒ.get(this.id);
            }

            this.resolved = true;
         }

         return this.function;
      }

      @Nullable
      public ResourceLocation getId() {
         return (ResourceLocation)this.function.map(var0 -> var0.id).orElse(this.id);
      }
   }

   public static class CommandEntry implements CommandFunction.Entry {
      private final ParseResults<CommandSourceStack> parse;

      public CommandEntry(ParseResults<CommandSourceStack> var1) {
         this.parse = â˜ƒ;
      }

      @Override
      public void execute(
         ServerFunctionManager var1,
         CommandSourceStack var2,
         Deque<ServerFunctionManager.QueuedCommand> var3,
         int var4,
         int var5,
         @Nullable ServerFunctionManager.TraceCallbacks var6
      ) throws CommandSyntaxException {
         if (â˜ƒ != null) {
            String â˜ƒ = this.parse.getReader().getString();
            â˜ƒ.onCommand(â˜ƒ, â˜ƒ);
            int â˜ƒx = this.execute(â˜ƒ, â˜ƒ);
            â˜ƒ.onReturn(â˜ƒ, â˜ƒ, â˜ƒx);
         } else {
            this.execute(â˜ƒ, â˜ƒ);
         }
      }

      private int execute(ServerFunctionManager var1, CommandSourceStack var2) throws CommandSyntaxException {
         return â˜ƒ.getDispatcher().execute(new ParseResults<>(this.parse.getContext().withSource(â˜ƒ), this.parse.getReader(), this.parse.getExceptions()));
      }

      public String toString() {
         return this.parse.getReader().getString();
      }
   }

   @FunctionalInterface
   public interface Entry {
      void execute(
         ServerFunctionManager var1,
         CommandSourceStack var2,
         Deque<ServerFunctionManager.QueuedCommand> var3,
         int var4,
         int var5,
         @Nullable ServerFunctionManager.TraceCallbacks var6
      ) throws CommandSyntaxException;
   }

   public static class FunctionEntry implements CommandFunction.Entry {
      private final CommandFunction.CacheableFunction function;

      public FunctionEntry(CommandFunction var1) {
         this.function = new CommandFunction.CacheableFunction(â˜ƒ);
      }

      @Override
      public void execute(
         ServerFunctionManager var1,
         CommandSourceStack var2,
         Deque<ServerFunctionManager.QueuedCommand> var3,
         int var4,
         int var5,
         @Nullable ServerFunctionManager.TraceCallbacks var6
      ) {
         Util.ifElse(this.function.get(â˜ƒ), var5x -> {
            CommandFunction.Entry[] â˜ƒ = var5x.getEntries();
            if (â˜ƒ != null) {
               â˜ƒ.onCall(â˜ƒ, var5x.getId(), â˜ƒ.length);
            }

            int â˜ƒ = â˜ƒ - â˜ƒ.size();
            int â˜ƒx = Math.min(â˜ƒ.length, â˜ƒ);

            for(int â˜ƒxx = â˜ƒx - 1; â˜ƒxx >= 0; --â˜ƒxx) {
               â˜ƒ.addFirst(new ServerFunctionManager.QueuedCommand(â˜ƒ, â˜ƒ + 1, â˜ƒ[â˜ƒxx]));
            }
         }, () -> {
            if (â˜ƒ != null) {
               â˜ƒ.onCall(â˜ƒ, this.function.getId(), -1);
            }
         });
      }

      public String toString() {
         return "function " + this.function.getId();
      }
   }
}
