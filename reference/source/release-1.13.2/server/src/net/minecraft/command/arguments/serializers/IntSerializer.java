package net.minecraft.command.arguments.serializers;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.arguments.IArgumentSerializer;
import net.minecraft.network.PacketBuffer;

public class IntSerializer implements IArgumentSerializer<IntegerArgumentType> {
   public void func_197072_a(IntegerArgumentType var1, PacketBuffer var2) {
      boolean ☃ = ☃.getMinimum() != Integer.MIN_VALUE;
      boolean ☃x = ☃.getMaximum() != Integer.MAX_VALUE;
      ☃.writeByte(BrigadierSerializers.func_197508_a(☃, ☃x));
      if (☃) {
         ☃.writeInt(☃.getMinimum());
      }

      if (☃x) {
         ☃.writeInt(☃.getMaximum());
      }
   }

   public IntegerArgumentType func_197071_b(PacketBuffer var1) {
      byte ☃ = ☃.readByte();
      int ☃x = BrigadierSerializers.func_197510_a(☃) ? ☃.readInt() : Integer.MIN_VALUE;
      int ☃xx = BrigadierSerializers.func_197509_b(☃) ? ☃.readInt() : Integer.MAX_VALUE;
      return IntegerArgumentType.integer(☃x, ☃xx);
   }

   public void func_212244_a(IntegerArgumentType var1, JsonObject var2) {
      if (☃.getMinimum() != Integer.MIN_VALUE) {
         ☃.addProperty("min", ☃.getMinimum());
      }

      if (☃.getMaximum() != Integer.MAX_VALUE) {
         ☃.addProperty("max", ☃.getMaximum());
      }
   }
}
