package net.minecraft.network;

import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class NetHandlerStatusServer implements INetHandlerStatusServer {
   private static final ITextComponent field_183007_a = new TextComponentTranslation("multiplayer.status.request_handled");
   private final MinecraftServer field_147314_a;
   private final NetworkManager field_147313_b;
   private boolean field_183008_d;

   public NetHandlerStatusServer(MinecraftServer var1, NetworkManager var2) {
      this.field_147314_a = ☃;
      this.field_147313_b = ☃;
   }

   @Override
   public void func_147231_a(ITextComponent var1) {
   }

   @Override
   public void func_147312_a(CPacketServerQuery var1) {
      if (this.field_183008_d) {
         this.field_147313_b.func_150718_a(field_183007_a);
      } else {
         this.field_183008_d = true;
         this.field_147313_b.func_179290_a(new SPacketServerInfo(this.field_147314_a.func_147134_at()));
      }
   }

   @Override
   public void func_147311_a(CPacketPing var1) {
      this.field_147313_b.func_179290_a(new SPacketPong(☃.func_149289_c()));
      this.field_147313_b.func_150718_a(field_183007_a);
   }
}
