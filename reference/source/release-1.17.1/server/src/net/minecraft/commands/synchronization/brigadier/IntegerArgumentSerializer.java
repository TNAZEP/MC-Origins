package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class IntegerArgumentSerializer implements ArgumentSerializer<IntegerArgumentType> {
   public void serializeToNetwork(IntegerArgumentType var1, FriendlyByteBuf var2) {
      boolean â˜ƒ = â˜ƒ.getMinimum() != Integer.MIN_VALUE;
      boolean â˜ƒx = â˜ƒ.getMaximum() != Integer.MAX_VALUE;
      â˜ƒ.writeByte(BrigadierArgumentSerializers.createNumberFlags(â˜ƒ, â˜ƒx));
      if (â˜ƒ) {
         â˜ƒ.writeInt(â˜ƒ.getMinimum());
      }

      if (â˜ƒx) {
         â˜ƒ.writeInt(â˜ƒ.getMaximum());
      }
   }

   public IntegerArgumentType deserializeFromNetwork(FriendlyByteBuf var1) {
      byte â˜ƒ = â˜ƒ.readByte();
      int â˜ƒx = BrigadierArgumentSerializers.numberHasMin(â˜ƒ) ? â˜ƒ.readInt() : Integer.MIN_VALUE;
      int â˜ƒxx = BrigadierArgumentSerializers.numberHasMax(â˜ƒ) ? â˜ƒ.readInt() : Integer.MAX_VALUE;
      return IntegerArgumentType.integer(â˜ƒx, â˜ƒxx);
   }

   public void serializeToJson(IntegerArgumentType var1, JsonObject var2) {
      if (â˜ƒ.getMinimum() != Integer.MIN_VALUE) {
         â˜ƒ.addProperty("min", â˜ƒ.getMinimum());
      }

      if (â˜ƒ.getMaximum() != Integer.MAX_VALUE) {
         â˜ƒ.addProperty("max", â˜ƒ.getMaximum());
      }
   }
}
