package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class LongArgumentSerializer implements ArgumentSerializer<LongArgumentType> {
   public void serializeToNetwork(LongArgumentType var1, FriendlyByteBuf var2) {
      boolean â˜ƒ = â˜ƒ.getMinimum() != Long.MIN_VALUE;
      boolean â˜ƒx = â˜ƒ.getMaximum() != Long.MAX_VALUE;
      â˜ƒ.writeByte(BrigadierArgumentSerializers.createNumberFlags(â˜ƒ, â˜ƒx));
      if (â˜ƒ) {
         â˜ƒ.writeLong(â˜ƒ.getMinimum());
      }

      if (â˜ƒx) {
         â˜ƒ.writeLong(â˜ƒ.getMaximum());
      }
   }

   public LongArgumentType deserializeFromNetwork(FriendlyByteBuf var1) {
      byte â˜ƒ = â˜ƒ.readByte();
      long â˜ƒx = BrigadierArgumentSerializers.numberHasMin(â˜ƒ) ? â˜ƒ.readLong() : Long.MIN_VALUE;
      long â˜ƒxx = BrigadierArgumentSerializers.numberHasMax(â˜ƒ) ? â˜ƒ.readLong() : Long.MAX_VALUE;
      return LongArgumentType.longArg(â˜ƒx, â˜ƒxx);
   }

   public void serializeToJson(LongArgumentType var1, JsonObject var2) {
      if (â˜ƒ.getMinimum() != Long.MIN_VALUE) {
         â˜ƒ.addProperty("min", â˜ƒ.getMinimum());
      }

      if (â˜ƒ.getMaximum() != Long.MAX_VALUE) {
         â˜ƒ.addProperty("max", â˜ƒ.getMaximum());
      }
   }
}
