package net.minecraft.client.gui;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.CPacketHandshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiConnecting extends GuiScreen {
   private static final AtomicInteger field_146372_a = new AtomicInteger(0);
   private static final Logger field_146370_f = LogManager.getLogger();
   private NetworkManager field_146371_g;
   private boolean field_146373_h;
   private final GuiScreen field_146374_i;
   private ITextComponent field_209515_s = new TextComponentTranslation("connect.connecting");

   public GuiConnecting(GuiScreen var1, Minecraft var2, ServerData var3) {
      this.field_146297_k = ☃;
      this.field_146374_i = ☃;
      ServerAddress ☃ = ServerAddress.func_78860_a(☃.field_78845_b);
      ☃.func_71403_a(null);
      ☃.func_71351_a(☃);
      this.func_146367_a(☃.func_78861_a(), ☃.func_78864_b());
   }

   public GuiConnecting(GuiScreen var1, Minecraft var2, String var3, int var4) {
      this.field_146297_k = ☃;
      this.field_146374_i = ☃;
      ☃.func_71403_a(null);
      this.func_146367_a(☃, ☃);
   }

   private void func_146367_a(final String var1, final int var2) {
      field_146370_f.info("Connecting to {}, {}", ☃, ☃);
      Thread ☃ = new Thread("Server Connector #" + field_146372_a.incrementAndGet()) {
         public void run() {
            InetAddress ☃ = null;

            try {
               if (GuiConnecting.this.field_146373_h) {
                  return;
               }

               ☃ = InetAddress.getByName(☃);
               GuiConnecting.this.field_146371_g = NetworkManager.func_181124_a(☃, ☃, GuiConnecting.this.field_146297_k.field_71474_y.func_181148_f());
               GuiConnecting.this.field_146371_g
                  .func_150719_a(
                     new NetHandlerLoginClient(
                        GuiConnecting.this.field_146371_g,
                        GuiConnecting.this.field_146297_k,
                        GuiConnecting.this.field_146374_i,
                        var1xx -> GuiConnecting.this.func_209514_a(var1xx)
                     )
                  );
               GuiConnecting.this.field_146371_g.func_179290_a(new CPacketHandshake(☃, ☃, EnumConnectionState.LOGIN));
               GuiConnecting.this.field_146371_g.func_179290_a(new CPacketLoginStart(GuiConnecting.this.field_146297_k.func_110432_I().func_148256_e()));
            } catch (UnknownHostException var4) {
               if (GuiConnecting.this.field_146373_h) {
                  return;
               }

               GuiConnecting.field_146370_f.error("Couldn't connect to server", var4);
               GuiConnecting.this.field_146297_k
                  .func_152344_a(
                     () -> GuiConnecting.this.field_146297_k
                           .func_147108_a(
                              new GuiDisconnected(
                                 GuiConnecting.this.field_146374_i, "connect.failed", new TextComponentTranslation("disconnect.genericReason", "Unknown host")
                              )
                           )
                  );
            } catch (Exception var5) {
               if (GuiConnecting.this.field_146373_h) {
                  return;
               }

               GuiConnecting.field_146370_f.error("Couldn't connect to server", var5);
               String ☃x = ☃ == null ? var5.toString() : var5.toString().replaceAll(☃ + ":" + ☃, "");
               GuiConnecting.this.field_146297_k
                  .func_152344_a(
                     () -> GuiConnecting.this.field_146297_k
                           .func_147108_a(
                              new GuiDisconnected(
                                 GuiConnecting.this.field_146374_i, "connect.failed", new TextComponentTranslation("disconnect.genericReason", ☃)
                              )
                           )
                  );
            }
         }
      };
      ☃.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_146370_f));
      ☃.start();
   }

   private void func_209514_a(ITextComponent var1) {
      this.field_209515_s = ☃;
   }

   @Override
   public void func_73876_c() {
      if (this.field_146371_g != null) {
         if (this.field_146371_g.func_150724_d()) {
            this.field_146371_g.func_74428_b();
         } else {
            this.field_146371_g.func_179293_l();
         }
      }
   }

   @Override
   public boolean func_195120_Y_() {
      return false;
   }

   @Override
   protected void func_73866_w_() {
      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m / 4 + 120 + 12, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiConnecting.this.field_146373_h = true;
            if (GuiConnecting.this.field_146371_g != null) {
               GuiConnecting.this.field_146371_g.func_150718_a(new TextComponentTranslation("connect.aborted"));
            }

            GuiConnecting.this.field_146297_k.func_147108_a(GuiConnecting.this.field_146374_i);
         }
      });
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, this.field_209515_s.func_150254_d(), this.field_146294_l / 2, this.field_146295_m / 2 - 50, 16777215);
      super.func_73863_a(☃, ☃, ☃);
   }
}
