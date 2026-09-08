package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class FloatSerializer implements IArgumentSerializer<FloatArgumentType> {
   public void func_197072_a(FloatArgumentType var1, PacketBuffer var2) {
      boolean ☃ = ☃.getMinimum() != -Float.MAX_VALUE;
      boolean ☃x = ☃.getMaximum() != Float.MAX_VALUE;
      ☃.writeByte(BrigadierSerializers.func_197508_a(☃, ☃x));
      if (☃) {
         ☃.writeFloat(☃.getMinimum());
      }

      if (☃x) {
         ☃.writeFloat(☃.getMaximum());
      }
   }

   public FloatArgumentType func_197071_b(PacketBuffer var1) {
      byte ☃ = ☃.readByte();
      float ☃x = BrigadierSerializers.func_197510_a(☃) ? ☃.readFloat() : -Float.MAX_VALUE;
      float ☃xx = BrigadierSerializers.func_197509_b(☃) ? ☃.readFloat() : Float.MAX_VALUE;
      return FloatArgumentType.floatArg(☃x, ☃xx);
   }

   public void func_212244_a(FloatArgumentType var1, JsonObject var2) {
      if (☃.getMinimum() != -Float.MAX_VALUE) {
         ☃.addProperty("min", ☃.getMinimum());
      }

      if (☃.getMaximum() != Float.MAX_VALUE) {
         ☃.addProperty("max", ☃.getMaximum());
      }
   }
}
