package net.minecraft.commands.synchronization.brigadier;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.commands.synchronization.EmptyArgumentSerializer;

public class BrigadierArgumentSerializers {
   private static final byte NUMBER_FLAG_MIN = 1;
   private static final byte NUMBER_FLAG_MAX = 2;

   public static void bootstrap() {
      ArgumentTypes.register("brigadier:bool", BoolArgumentType.class, new EmptyArgumentSerializer(BoolArgumentType::bool));
      ArgumentTypes.register("brigadier:float", FloatArgumentType.class, new FloatArgumentSerializer());
      ArgumentTypes.register("brigadier:double", DoubleArgumentType.class, new DoubleArgumentSerializer());
      ArgumentTypes.register("brigadier:integer", IntegerArgumentType.class, new IntegerArgumentSerializer());
      ArgumentTypes.register("brigadier:long", LongArgumentType.class, new LongArgumentSerializer());
      ArgumentTypes.register("brigadier:string", StringArgumentType.class, new StringArgumentSerializer());
   }

   public static byte createNumberFlags(boolean var0, boolean var1) {
      byte â˜ƒ = 0;
      if (â˜ƒ) {
         â˜ƒ = (byte)(â˜ƒ | 1);
      }

      if (â˜ƒ) {
         â˜ƒ = (byte)(â˜ƒ | 2);
      }

      return â˜ƒ;
   }

   public static boolean numberHasMin(byte var0) {
      return (â˜ƒ & 1) != 0;
   }

   public static boolean numberHasMax(byte var0) {
      return (â˜ƒ & 2) != 0;
   }
}
