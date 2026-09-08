package net.minecraft.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayDeque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.FunctionManager;
import net.minecraft.util.ResourceLocation;

public class FunctionObject {
   private final FunctionObject.Entry[] field_193530_b;
   private final ResourceLocation field_197002_b;

   public FunctionObject(ResourceLocation var1, FunctionObject.Entry[] var2) {
      this.field_197002_b = ☃;
      this.field_193530_b = ☃;
   }

   public ResourceLocation func_197001_a() {
      return this.field_197002_b;
   }

   public FunctionObject.Entry[] func_193528_a() {
      return this.field_193530_b;
   }

   public static FunctionObject func_197000_a(ResourceLocation var0, FunctionManager var1, List<String> var2) {
      List<FunctionObject.Entry> ☃ = Lists.<FunctionObject.Entry>newArrayListWithCapacity(☃.size());

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         int ☃xx = ☃x + 1;
         String ☃xxx = ((String)☃.get(☃x)).trim();
         StringReader ☃xxxx = new StringReader(☃xxx);
         if (☃xxxx.canRead() && ☃xxxx.peek() != '#') {
            if (☃xxxx.peek() == '/') {
               ☃xxxx.skip();
               if (☃xxxx.peek() == '/') {
                  throw new IllegalArgumentException(
                     "Unknown or invalid command '" + ☃xxx + "' on line " + ☃xx + " (if you intended to make a comment, use '#' not '//')"
                  );
               }

               String ☃xxxxx = ☃xxxx.readUnquotedString();
               throw new IllegalArgumentException(
                  "Unknown or invalid command '" + ☃xxx + "' on line " + ☃xx + " (did you mean '" + ☃xxxxx + "'? Do not use a preceding forwards slash.)"
               );
            }

            try {
               ParseResults<CommandSource> ☃xxxxx = ☃.func_195450_a().func_195571_aL().func_197054_a().parse(☃xxxx, ☃.func_195448_f());
               if (☃xxxxx.getReader().canRead()) {
                  if (☃xxxxx.getExceptions().size() == 1) {
                     throw (CommandSyntaxException)☃xxxxx.getExceptions().values().iterator().next();
                  }

                  if (☃xxxxx.getContext().getRange().isEmpty()) {
                     throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(☃xxxxx.getReader());
                  }

                  throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(☃xxxxx.getReader());
               }

               ☃.add(new FunctionObject.CommandEntry(☃xxxxx));
            } catch (CommandSyntaxException var9) {
               throw new IllegalArgumentException("Whilst parsing command on line " + ☃xx + ": " + var9.getMessage());
            }
         }
      }

      return new FunctionObject(☃, (FunctionObject.Entry[])☃.toArray(new FunctionObject.Entry[0]));
   }

   public static class CacheableFunction {
      public static final FunctionObject.CacheableFunction field_193519_a = new FunctionObject.CacheableFunction((ResourceLocation)null);
      @Nullable
      private final ResourceLocation field_193520_b;
      private boolean field_193521_c;
      private FunctionObject field_193522_d;

      public CacheableFunction(@Nullable ResourceLocation var1) {
         this.field_193520_b = ☃;
      }

      public CacheableFunction(FunctionObject var1) {
         this.field_193520_b = null;
         this.field_193522_d = ☃;
      }

      @Nullable
      public FunctionObject func_193518_a(FunctionManager var1) {
         if (!this.field_193521_c) {
            if (this.field_193520_b != null) {
               this.field_193522_d = ☃.func_193058_a(this.field_193520_b);
            }

            this.field_193521_c = true;
         }

         return this.field_193522_d;
      }

      @Nullable
      public ResourceLocation func_200376_a() {
         return this.field_193522_d != null ? this.field_193522_d.field_197002_b : this.field_193520_b;
      }
   }

   public static class CommandEntry implements FunctionObject.Entry {
      private final ParseResults<CommandSource> field_196999_a;

      public CommandEntry(ParseResults<CommandSource> var1) {
         this.field_196999_a = ☃;
      }

      @Override
      public void func_196998_a(FunctionManager var1, CommandSource var2, ArrayDeque<FunctionManager.QueuedCommand> var3, int var4) throws CommandSyntaxException {
         ☃.func_195446_d()
            .execute(
               new ParseResults<>(
                  this.field_196999_a.getContext().withSource(☃),
                  this.field_196999_a.getStartIndex(),
                  this.field_196999_a.getReader(),
                  this.field_196999_a.getExceptions()
               )
            );
      }

      public String toString() {
         return this.field_196999_a.getReader().getString();
      }
   }

   public interface Entry {
      void func_196998_a(FunctionManager var1, CommandSource var2, ArrayDeque<FunctionManager.QueuedCommand> var3, int var4) throws CommandSyntaxException;
   }

   public static class FunctionEntry implements FunctionObject.Entry {
      private final FunctionObject.CacheableFunction field_193524_a;

      public FunctionEntry(FunctionObject var1) {
         this.field_193524_a = new FunctionObject.CacheableFunction(☃);
      }

      @Override
      public void func_196998_a(FunctionManager var1, CommandSource var2, ArrayDeque<FunctionManager.QueuedCommand> var3, int var4) {
         FunctionObject ☃ = this.field_193524_a.func_193518_a(☃);
         if (☃ != null) {
            FunctionObject.Entry[] ☃x = ☃.func_193528_a();
            int ☃xx = ☃ - ☃.size();
            int ☃xxx = Math.min(☃x.length, ☃xx);

            for(int ☃xxxx = ☃xxx - 1; ☃xxxx >= 0; --☃xxxx) {
               ☃.addFirst(new FunctionManager.QueuedCommand(☃, ☃, ☃x[☃xxxx]));
            }
         }
      }

      public String toString() {
         return "function " + this.field_193524_a.func_200376_a();
      }
   }
}
