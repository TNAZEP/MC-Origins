package net.minecraft.data;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.datafix.DataFixesManager;

public class CommandsReport implements IDataProvider {
   private final DataGenerator field_200400_a;

   public CommandsReport(DataGenerator var1) {
      this.field_200400_a = ☃;
   }

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      YggdrasilAuthenticationService ☃ = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
      MinecraftSessionService ☃x = ☃.createMinecraftSessionService();
      GameProfileRepository ☃xx = ☃.createProfileRepository();
      File ☃xxx = new File(this.field_200400_a.func_200391_b().toFile(), "tmp");
      PlayerProfileCache ☃xxxx = new PlayerProfileCache(☃xx, new File(☃xxx, MinecraftServer.field_152367_a.getName()));
      MinecraftServer ☃xxxxx = new DedicatedServer(☃xxx, DataFixesManager.func_210901_a(), ☃, ☃x, ☃xx, ☃xxxx);
      ☃xxxxx.func_195571_aL().func_200378_a(this.field_200400_a.func_200391_b().resolve("reports/commands.json").toFile());
   }

   @Override
   public String func_200397_b() {
      return "Command Syntax";
   }
}
