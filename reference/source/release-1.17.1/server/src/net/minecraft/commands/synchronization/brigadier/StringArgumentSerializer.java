package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class StringArgumentSerializer implements ArgumentSerializer<StringArgumentType> {
   public void serializeToNetwork(StringArgumentType var1, FriendlyByteBuf var2) {
      â˜ƒ.writeEnum(â˜ƒ.getType());
   }

   public StringArgumentType deserializeFromNetwork(FriendlyByteBuf var1) {
      StringType â˜ƒ = â˜ƒ.readEnum(StringType.class);
      switch(â˜ƒ) {
         case SINGLE_WORD:
            return StringArgumentType.word();
         case QUOTABLE_PHRASE:
            return StringArgumentType.string();
         case GREEDY_PHRASE:
         default:
            return StringArgumentType.greedyString();
      }
   }

   public void serializeToJson(StringArgumentType var1, JsonObject var2) {
      switch(â˜ƒ.getType()) {
         case SINGLE_WORD:
            â˜ƒ.addProperty("type", "word");
            break;
         case QUOTABLE_PHRASE:
            â˜ƒ.addProperty("type", "phrase");
            break;
         case GREEDY_PHRASE:
         default:
            â˜ƒ.addProperty("type", "greedy");
      }
   }
}
