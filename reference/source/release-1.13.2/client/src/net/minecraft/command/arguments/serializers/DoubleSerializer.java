package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class DoubleSerializer implements IArgumentSerializer<DoubleArgumentType> {
   public void func_197072_a(DoubleArgumentType var1, PacketBuffer var2) {
      boolean ☃ = ☃.getMinimum() != -Double.MAX_VALUE;
      boolean ☃x = ☃.getMaximum() != Double.MAX_VALUE;
      ☃.writeByte(BrigadierSerializers.func_197508_a(☃, ☃x));
      if (☃) {
         ☃.writeDouble(☃.getMinimum());
      }

      if (☃x) {
         ☃.writeDouble(☃.getMaximum());
      }
   }

   public DoubleArgumentType func_197071_b(PacketBuffer var1) {
      byte ☃ = ☃.readByte();
      double ☃x = BrigadierSerializers.func_197510_a(☃) ? ☃.readDouble() : -Double.MAX_VALUE;
      double ☃xx = BrigadierSerializers.func_197509_b(☃) ? ☃.readDouble() : Double.MAX_VALUE;
      return DoubleArgumentType.doubleArg(☃x, ☃xx);
   }

   public void func_212244_a(DoubleArgumentType var1, JsonObject var2) {
      if (☃.getMinimum() != -Double.MAX_VALUE) {
         ☃.addProperty("min", ☃.getMinimum());
      }

      if (☃.getMaximum() != Double.MAX_VALUE) {
         ☃.addProperty("max", ☃.getMaximum());
      }
   }
}
