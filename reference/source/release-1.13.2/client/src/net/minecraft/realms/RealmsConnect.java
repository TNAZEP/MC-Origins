package net.minecraft.realms;

import java.net.InetAddress;
import java.net.UnknownHostException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.CPacketHandshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RealmsScreen onlineScreen;
   private volatile boolean aborted;
   private NetworkManager connection;

   public RealmsConnect(RealmsScreen var1) {
      this.onlineScreen = ☃;
   }

   public void connect(final String var1, final int var2) {
      Realms.setConnectedToRealms(true);
      (new Thread("Realms-connect-task") {
            public void run() {
               InetAddress ☃ = null;
   
               try {
                  ☃ = InetAddress.getByName(☃);
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection = NetworkManager.func_181124_a(☃, ☃, Minecraft.func_71410_x().field_71474_y.func_181148_f());
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection
                     .func_150719_a(
                        new NetHandlerLoginClient(
                           RealmsConnect.this.connection, Minecraft.func_71410_x(), RealmsConnect.this.onlineScreen.getProxy(), var0 -> {
                           }
                        )
                     );
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection.func_179290_a(new CPacketHandshake(☃, ☃, EnumConnectionState.LOGIN));
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.this.connection.func_179290_a(new CPacketLoginStart(Minecraft.func_71410_x().func_110432_I().func_148256_e()));
               } catch (UnknownHostException var5) {
                  Realms.clearResourcePack();
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.LOGGER.error("Couldn't connect to world", var5);
                  Realms.setScreen(
                     new DisconnectedRealmsScreen(
                        RealmsConnect.this.onlineScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", "Unknown host '" + ☃ + "'")
                     )
                  );
               } catch (Exception var6) {
                  Realms.clearResourcePack();
                  if (RealmsConnect.this.aborted) {
                     return;
                  }
   
                  RealmsConnect.LOGGER.error("Couldn't connect to world", var6);
                  String ☃x = var6.toString();
                  if (☃ != null) {
                     String ☃xx = ☃ + ":" + ☃;
                     ☃x = ☃x.replaceAll(☃xx, "");
                  }
   
                  Realms.setScreen(
                     new DisconnectedRealmsScreen(
                        RealmsConnect.this.onlineScreen, "connect.failed", new TextComponentTranslation("disconnect.genericReason", ☃x)
                     )
                  );
               }
            }
         })
         .start();
   }

   public void abort() {
      this.aborted = true;
      if (this.connection != null && this.connection.func_150724_d()) {
         this.connection.func_150718_a(new TextComponentTranslation("disconnect.genericReason"));
         this.connection.func_179293_l();
      }
   }

   public void tick() {
      if (this.connection != null) {
         if (this.connection.func_150724_d()) {
            this.connection.func_74428_b();
         } else {
            this.connection.func_179293_l();
         }
      }
   }
}
