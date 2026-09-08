package net.minecraft.client.network;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.util.concurrent.Future;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.client.CPacketCustomPayloadLogin;
import net.minecraft.network.login.client.CPacketEncryptionResponse;
import net.minecraft.network.login.server.SPacketCustomPayloadLogin;
import net.minecraft.network.login.server.SPacketDisconnectLogin;
import net.minecraft.network.login.server.SPacketEnableCompression;
import net.minecraft.network.login.server.SPacketEncryptionRequest;
import net.minecraft.network.login.server.SPacketLoginSuccess;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerLoginClient implements INetHandlerLoginClient {
   private static final Logger field_147396_a = LogManager.getLogger();
   private final Minecraft field_147394_b;
   @Nullable
   private final GuiScreen field_147395_c;
   private final Consumer<ITextComponent> field_209525_d;
   private final NetworkManager field_147393_d;
   private GameProfile field_175091_e;

   public NetHandlerLoginClient(NetworkManager var1, Minecraft var2, @Nullable GuiScreen var3, Consumer<ITextComponent> var4) {
      this.field_147393_d = ☃;
      this.field_147394_b = ☃;
      this.field_147395_c = ☃;
      this.field_209525_d = ☃;
   }

   @Override
   public void func_147389_a(SPacketEncryptionRequest var1) {
      SecretKey ☃ = CryptManager.func_75890_a();
      PublicKey ☃x = ☃.func_149608_d();
      String ☃xx = new BigInteger(CryptManager.func_75895_a(☃.func_149609_c(), ☃x, ☃)).toString(16);
      CPacketEncryptionResponse ☃xxx = new CPacketEncryptionResponse(☃, ☃x, ☃.func_149607_e());
      this.field_209525_d.accept(new TextComponentTranslation("connect.authorizing"));
      HttpUtil.field_180193_a.submit((Runnable)(() -> {
         ITextComponent ☃ = this.func_209522_a(☃);
         if (☃ != null) {
            if (this.field_147394_b.func_147104_D() == null || !this.field_147394_b.func_147104_D().func_181041_d()) {
               this.field_147393_d.func_150718_a(☃);
               return;
            }

            field_147396_a.warn(☃.getString());
         }

         this.field_209525_d.accept(new TextComponentTranslation("connect.encrypting"));
         this.field_147393_d.func_201058_a(☃, var2x -> this.field_147393_d.func_150727_a(☃));
      }));
   }

   @Nullable
   private ITextComponent func_209522_a(String var1) {
      try {
         this.func_147391_c().joinServer(this.field_147394_b.func_110432_I().func_148256_e(), this.field_147394_b.func_110432_I().func_148254_d(), ☃);
         return null;
      } catch (AuthenticationUnavailableException var3) {
         return new TextComponentTranslation("disconnect.loginFailedInfo", new TextComponentTranslation("disconnect.loginFailedInfo.serversUnavailable"));
      } catch (InvalidCredentialsException var4) {
         return new TextComponentTranslation("disconnect.loginFailedInfo", new TextComponentTranslation("disconnect.loginFailedInfo.invalidSession"));
      } catch (AuthenticationException var5) {
         return new TextComponentTranslation("disconnect.loginFailedInfo", var5.getMessage());
      }
   }

   private MinecraftSessionService func_147391_c() {
      return this.field_147394_b.func_152347_ac();
   }

   @Override
   public void func_147390_a(SPacketLoginSuccess var1) {
      this.field_209525_d.accept(new TextComponentTranslation("connect.joining"));
      this.field_175091_e = ☃.func_179730_a();
      this.field_147393_d.func_150723_a(EnumConnectionState.PLAY);
      this.field_147393_d.func_150719_a(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d, this.field_175091_e));
   }

   @Override
   public void func_147231_a(ITextComponent var1) {
      if (this.field_147395_c != null && this.field_147395_c instanceof GuiScreenRealmsProxy) {
         this.field_147394_b
            .func_147108_a(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.field_147395_c).func_154321_a(), "connect.failed", ☃).getProxy());
      } else {
         this.field_147394_b.func_147108_a(new GuiDisconnected(this.field_147395_c, "connect.failed", ☃));
      }
   }

   @Override
   public void func_147388_a(SPacketDisconnectLogin var1) {
      this.field_147393_d.func_150718_a(☃.func_149603_c());
   }

   @Override
   public void func_180464_a(SPacketEnableCompression var1) {
      if (!this.field_147393_d.func_150731_c()) {
         this.field_147393_d.func_179289_a(☃.func_179731_a());
      }
   }

   @Override
   public void func_209521_a(SPacketCustomPayloadLogin var1) {
      this.field_209525_d.accept(new TextComponentTranslation("connect.negotiating"));
      this.field_147393_d.func_179290_a(new CPacketCustomPayloadLogin(☃.func_209918_a(), null));
   }
}
