package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class FloatArgumentSerializer implements ArgumentSerializer<FloatArgumentType> {
   public void serializeToNetwork(FloatArgumentType var1, FriendlyByteBuf var2) {
      boolean â˜ƒ = â˜ƒ.getMinimum() != -Float.MAX_VALUE;
      boolean â˜ƒx = â˜ƒ.getMaximum() != Float.MAX_VALUE;
      â˜ƒ.writeByte(BrigadierArgumentSerializers.createNumberFlags(â˜ƒ, â˜ƒx));
      if (â˜ƒ) {
         â˜ƒ.writeFloat(â˜ƒ.getMinimum());
      }

      if (â˜ƒx) {
         â˜ƒ.writeFloat(â˜ƒ.getMaximum());
      }
   }

   public FloatArgumentType deserializeFromNetwork(FriendlyByteBuf var1) {
      byte â˜ƒ = â˜ƒ.readByte();
      float â˜ƒx = BrigadierArgumentSerializers.numberHasMin(â˜ƒ) ? â˜ƒ.readFloat() : -Float.MAX_VALUE;
      float â˜ƒxx = BrigadierArgumentSerializers.numberHasMax(â˜ƒ) ? â˜ƒ.readFloat() : Float.MAX_VALUE;
      return FloatArgumentType.floatArg(â˜ƒx, â˜ƒxx);
   }

   public void serializeToJson(FloatArgumentType var1, JsonObject var2) {
      if (â˜ƒ.getMinimum() != -Float.MAX_VALUE) {
         â˜ƒ.addProperty("min", â˜ƒ.getMinimum());
      }

      if (â˜ƒ.getMaximum() != Float.MAX_VALUE) {
         â˜ƒ.addProperty("max", â˜ƒ.getMaximum());
      }
   }
}
