package net.minecraft.network;

import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
   public static <T extends INetHandler> void func_180031_a(Packet<T> var0, T var1, IThreadListener var2) throws ThreadQuickExitException {
      if (!☃.func_152345_ab()) {
         ☃.func_152344_a(() -> ☃.func_148833_a(☃));
         throw ThreadQuickExitException.field_179886_a;
      }
   }
}
