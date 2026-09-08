package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class StringSerializer implements IArgumentSerializer<StringArgumentType> {
   public void func_197072_a(StringArgumentType var1, PacketBuffer var2) {
      ☃.func_179249_a(☃.getType());
   }

   public StringArgumentType func_197071_b(PacketBuffer var1) {
      StringType ☃ = ☃.func_179257_a(StringType.class);
      switch(☃) {
         case SINGLE_WORD:
            return StringArgumentType.word();
         case QUOTABLE_PHRASE:
            return StringArgumentType.string();
         case GREEDY_PHRASE:
         default:
            return StringArgumentType.greedyString();
      }
   }

   public void func_212244_a(StringArgumentType var1, JsonObject var2) {
      switch(☃.getType()) {
         case SINGLE_WORD:
            ☃.addProperty("type", "word");
            break;
         case QUOTABLE_PHRASE:
            ☃.addProperty("type", "phrase");
            break;
         case GREEDY_PHRASE:
         default:
            ☃.addProperty("type", "greedy");
      }
   }
}
