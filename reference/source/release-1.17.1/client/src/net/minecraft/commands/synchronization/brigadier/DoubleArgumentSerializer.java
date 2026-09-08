package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.network.FriendlyByteBuf;

public class DoubleArgumentSerializer implements ArgumentSerializer<DoubleArgumentType> {
   public void serializeToNetwork(DoubleArgumentType var1, FriendlyByteBuf var2) {
      boolean â˜ƒ = â˜ƒ.getMinimum() != -Double.MAX_VALUE;
      boolean â˜ƒx = â˜ƒ.getMaximum() != Double.MAX_VALUE;
      â˜ƒ.writeByte(BrigadierArgumentSerializers.createNumberFlags(â˜ƒ, â˜ƒx));
      if (â˜ƒ) {
         â˜ƒ.writeDouble(â˜ƒ.getMinimum());
      }

      if (â˜ƒx) {
         â˜ƒ.writeDouble(â˜ƒ.getMaximum());
      }
   }

   public DoubleArgumentType deserializeFromNetwork(FriendlyByteBuf var1) {
      byte â˜ƒ = â˜ƒ.readByte();
      double â˜ƒx = BrigadierArgumentSerializers.numberHasMin(â˜ƒ) ? â˜ƒ.readDouble() : -Double.MAX_VALUE;
      double â˜ƒxx = BrigadierArgumentSerializers.numberHasMax(â˜ƒ) ? â˜ƒ.readDouble() : Double.MAX_VALUE;
      return DoubleArgumentType.doubleArg(â˜ƒx, â˜ƒxx);
   }

   public void serializeToJson(DoubleArgumentType var1, JsonObject var2) {
      if (â˜ƒ.getMinimum() != -Double.MAX_VALUE) {
         â˜ƒ.addProperty("min", â˜ƒ.getMinimum());
      }

      if (â˜ƒ.getMaximum() != Double.MAX_VALUE) {
         â˜ƒ.addProperty("max", â˜ƒ.getMaximum());
      }
   }
}
