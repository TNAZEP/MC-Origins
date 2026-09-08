package net.minecraft.server.dedicated;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.rcon.IServer;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.network.rcon.RConThreadMain;
import net.minecraft.network.rcon.RConThreadQuery;
import net.minecraft.profiler.Snooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerEula;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.CryptManager;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.DefaultUncaughtExceptionHandlerWithName;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServer extends MinecraftServer implements IServer {
   private static final Logger field_155771_h = LogManager.getLogger();
   private static final Pattern field_189647_l = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final List<PendingCommand> field_71341_l = Collections.synchronizedList(Lists.newArrayList());
   private RConThreadQuery field_71342_m;
   private final RConConsoleSource field_184115_n = new RConConsoleSource(this);
   private RConThreadMain field_71339_n;
   private PropertyManager field_71340_o;
   private ServerEula field_154332_n;
   private boolean field_71338_p;
   private GameType field_71337_q;
   private boolean field_71335_s;

   public DedicatedServer(
      File var1, DataFixer var2, YggdrasilAuthenticationService var3, MinecraftSessionService var4, GameProfileRepository var5, PlayerProfileCache var6
   ) {
      super(☃, Proxy.NO_PROXY, ☃, new Commands(true), ☃, ☃, ☃, ☃);
      new Thread("Server Infinisleeper") {
         {
            this.setDaemon(true);
            this.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(DedicatedServer.field_155771_h));
            this.start();
         }

         public void run() {
            while(true) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
               }
            }
         }
      };
   }

   @Override
   protected boolean func_71197_b() throws IOException {
      Thread ☃ = new Thread("Server console handler") {
         public void run() {
            BufferedReader ☃ = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            String ☃;
            try {
               while(!DedicatedServer.this.func_71241_aa() && DedicatedServer.this.func_71278_l() && (☃ = ☃.readLine()) != null) {
                  DedicatedServer.this.func_195581_a(☃, DedicatedServer.this.func_195573_aM());
               }
            } catch (IOException var4) {
               DedicatedServer.field_155771_h.error("Exception handling console input", var4);
            }
         }
      };
      ☃.setDaemon(true);
      ☃.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(field_155771_h));
      ☃.start();
      field_155771_h.info("Starting minecraft server version 1.13.2");
      if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         field_155771_h.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      field_155771_h.info("Loading properties");
      this.field_71340_o = new PropertyManager(new File("server.properties"));
      this.field_154332_n = new ServerEula(new File("eula.txt"));
      if (!this.field_154332_n.func_154346_a()) {
         field_155771_h.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
         this.field_154332_n.func_154348_b();
         return false;
      } else {
         if (this.func_71264_H()) {
            this.func_71189_e("127.0.0.1");
         } else {
            this.func_71229_d(this.field_71340_o.func_73670_a("online-mode", true));
            this.func_190517_e(this.field_71340_o.func_73670_a("prevent-proxy-connections", false));
            this.func_71189_e(this.field_71340_o.func_73671_a("server-ip", ""));
         }

         this.func_71251_e(this.field_71340_o.func_73670_a("spawn-animals", true));
         this.func_71257_f(this.field_71340_o.func_73670_a("spawn-npcs", true));
         this.func_71188_g(this.field_71340_o.func_73670_a("pvp", true));
         this.func_71245_h(this.field_71340_o.func_73670_a("allow-flight", false));
         this.func_180507_a_(this.field_71340_o.func_73671_a("resource-pack", ""), this.func_184113_aK());
         this.func_71205_p(this.field_71340_o.func_73671_a("motd", "A Minecraft Server"));
         this.func_104055_i(this.field_71340_o.func_73670_a("force-gamemode", false));
         this.func_143006_e(this.field_71340_o.func_73669_a("player-idle-timeout", 0));
         this.func_205741_k(this.field_71340_o.func_73670_a("enforce-whitelist", false));
         if (this.field_71340_o.func_73669_a("difficulty", 1) < 0) {
            this.field_71340_o.func_73667_a("difficulty", 0);
         } else if (this.field_71340_o.func_73669_a("difficulty", 1) > 3) {
            this.field_71340_o.func_73667_a("difficulty", 3);
         }

         this.field_71338_p = this.field_71340_o.func_73670_a("generate-structures", true);
         int ☃ = this.field_71340_o.func_73669_a("gamemode", GameType.SURVIVAL.func_77148_a());
         this.field_71337_q = WorldSettings.func_77161_a(☃);
         field_155771_h.info("Default game type: {}", this.field_71337_q);
         InetAddress ☃x = null;
         if (!this.func_71211_k().isEmpty()) {
            ☃x = InetAddress.getByName(this.func_71211_k());
         }

         if (this.func_71215_F() < 0) {
            this.func_71208_b(this.field_71340_o.func_73669_a("server-port", 25565));
         }

         field_155771_h.info("Generating keypair");
         this.func_71253_a(CryptManager.func_75891_b());
         field_155771_h.info("Starting Minecraft server on {}:{}", this.func_71211_k().isEmpty() ? "*" : this.func_71211_k(), this.func_71215_F());

         try {
            this.func_147137_ag().func_151265_a(☃x, this.func_71215_F());
         } catch (IOException var18) {
            field_155771_h.warn("**** FAILED TO BIND TO PORT!");
            field_155771_h.warn("The exception was: {}", var18.toString());
            field_155771_h.warn("Perhaps a server is already running on that port?");
            return false;
         }

         if (!this.func_71266_T()) {
            field_155771_h.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            field_155771_h.warn("The server will make no attempt to authenticate usernames. Beware.");
            field_155771_h.warn(
               "While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose."
            );
            field_155771_h.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
         }

         if (this.func_152368_aE()) {
            this.func_152358_ax().func_152658_c();
         }

         if (!PreYggdrasilConverter.func_152714_a(this.field_71340_o)) {
            return false;
         } else {
            this.func_184105_a(new DedicatedPlayerList(this));
            long ☃ = Util.func_211178_c();
            if (this.func_71270_I() == null) {
               this.func_71261_m(this.field_71340_o.func_73671_a("level-name", "world"));
            }

            String ☃ = this.field_71340_o.func_73671_a("level-seed", "");
            String ☃x = this.field_71340_o.func_73671_a("level-type", "DEFAULT");
            String ☃xx = this.field_71340_o.func_73671_a("generator-settings", "");
            long ☃xxx = new Random().nextLong();
            if (!☃.isEmpty()) {
               try {
                  long ☃xxxx = Long.parseLong(☃);
                  if (☃xxxx != 0L) {
                     ☃xxx = ☃xxxx;
                  }
               } catch (NumberFormatException var17) {
                  ☃xxx = (long)☃.hashCode();
               }
            }

            WorldType ☃ = WorldType.func_77130_a(☃x);
            if (☃ == null) {
               ☃ = WorldType.field_77137_b;
            }

            this.func_82356_Z();
            this.func_110455_j();
            this.func_70002_Q();
            this.func_175577_aI();
            this.func_71191_d(this.field_71340_o.func_73669_a("max-build-height", 256));
            this.func_71191_d((this.func_71207_Z() + 8) / 16 * 16);
            this.func_71191_d(MathHelper.func_76125_a(this.func_71207_Z(), 64, 256));
            this.field_71340_o.func_73667_a("max-build-height", this.func_71207_Z());
            TileEntitySkull.func_184293_a(this.func_152358_ax());
            TileEntitySkull.func_184294_a(this.func_147130_as());
            PlayerProfileCache.func_187320_a(this.func_71266_T());
            field_155771_h.info("Preparing level \"{}\"", this.func_71270_I());
            JsonObject ☃ = new JsonObject();
            if (☃ == WorldType.field_77138_c) {
               ☃.addProperty("flat_world_options", ☃xx);
            } else if (!☃xx.isEmpty()) {
               ☃ = JsonUtils.func_212745_a(☃xx);
            }

            this.func_71247_a(this.func_71270_I(), this.func_71270_I(), ☃xxx, ☃, ☃);
            long ☃ = Util.func_211178_c() - ☃;
            String ☃x = String.format(Locale.ROOT, "%.3fs", (double)☃ / 1.0E9);
            field_155771_h.info("Done ({})! For help, type \"help\"", ☃x);
            if (this.field_71340_o.func_187239_a("announce-player-achievements")) {
               this.func_200252_aR()
                  .func_82764_b("announceAdvancements", this.field_71340_o.func_73670_a("announce-player-achievements", true) ? "true" : "false", this);
               this.field_71340_o.func_187238_b("announce-player-achievements");
               this.field_71340_o.func_73668_b();
            }

            if (this.field_71340_o.func_73670_a("enable-query", false)) {
               field_155771_h.info("Starting GS4 status listener");
               this.field_71342_m = new RConThreadQuery(this);
               this.field_71342_m.func_72602_a();
            }

            if (this.field_71340_o.func_73670_a("enable-rcon", false)) {
               field_155771_h.info("Starting remote control listener");
               this.field_71339_n = new RConThreadMain(this);
               this.field_71339_n.func_72602_a();
            }

            if (this.func_175593_aQ() > 0L) {
               Thread ☃ = new Thread(new ServerHangWatchdog(this));
               ☃.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(field_155771_h));
               ☃.setName("Server Watchdog");
               ☃.setDaemon(true);
               ☃.start();
            }

            Items.field_190931_a.func_150895_a(ItemGroup.field_78027_g, NonNullList.func_191196_a());
            return true;
         }
      }
   }

   public String func_184113_aK() {
      if (this.field_71340_o.func_187239_a("resource-pack-hash")) {
         if (this.field_71340_o.func_187239_a("resource-pack-sha1")) {
            field_155771_h.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
         } else {
            field_155771_h.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
            this.field_71340_o.func_73671_a("resource-pack-sha1", this.field_71340_o.func_73671_a("resource-pack-hash", ""));
            this.field_71340_o.func_187238_b("resource-pack-hash");
         }
      }

      String ☃ = this.field_71340_o.func_73671_a("resource-pack-sha1", "");
      if (!☃.isEmpty() && !field_189647_l.matcher(☃).matches()) {
         field_155771_h.warn("Invalid sha1 for ressource-pack-sha1");
      }

      if (!this.field_71340_o.func_73671_a("resource-pack", "").isEmpty() && ☃.isEmpty()) {
         field_155771_h.warn(
            "You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack."
         );
      }

      return ☃;
   }

   @Override
   public void func_71235_a(GameType var1) {
      super.func_71235_a(☃);
      this.field_71337_q = ☃;
   }

   @Override
   public boolean func_71225_e() {
      return this.field_71338_p;
   }

   @Override
   public GameType func_71265_f() {
      return this.field_71337_q;
   }

   @Override
   public EnumDifficulty func_147135_j() {
      return EnumDifficulty.func_151523_a(this.field_71340_o.func_73669_a("difficulty", EnumDifficulty.NORMAL.func_151525_a()));
   }

   @Override
   public boolean func_71199_h() {
      return this.field_71340_o.func_73670_a("hardcore", false);
   }

   @Override
   public CrashReport func_71230_b(CrashReport var1) {
      ☃ = super.func_71230_b(☃);
      ☃.func_85056_g().func_189529_a("Is Modded", () -> {
         String ☃ = this.getServerModName();
         return !"vanilla".equals(☃) ? "Definitely; Server brand changed to '" + ☃ + "'" : "Unknown (can't tell)";
      });
      ☃.func_85056_g().func_189529_a("Type", () -> "Dedicated Server (map_server.txt)");
      return ☃;
   }

   @Override
   protected void func_71240_o() {
      System.exit(0);
   }

   @Override
   protected void func_71190_q(BooleanSupplier var1) {
      super.func_71190_q(☃);
      this.func_71333_ah();
   }

   @Override
   public boolean func_71255_r() {
      return this.field_71340_o.func_73670_a("allow-nether", true);
   }

   @Override
   public boolean func_71193_K() {
      return this.field_71340_o.func_73670_a("spawn-monsters", true);
   }

   @Override
   public void func_70000_a(Snooper var1) {
      ☃.func_152768_a("whitelist_enabled", this.func_184103_al().func_72383_n());
      ☃.func_152768_a("whitelist_count", this.func_184103_al().func_152598_l().length);
      super.func_70000_a(☃);
   }

   @Override
   public boolean func_70002_Q() {
      if (this.field_71340_o.func_73670_a("snooper-enabled", true)) {
      }

      return false;
   }

   public void func_195581_a(String var1, CommandSource var2) {
      this.field_71341_l.add(new PendingCommand(☃, ☃));
   }

   public void func_71333_ah() {
      while(!this.field_71341_l.isEmpty()) {
         PendingCommand ☃ = (PendingCommand)this.field_71341_l.remove(0);
         this.func_195571_aL().func_197059_a(☃.field_73701_b, ☃.field_73702_a);
      }
   }

   @Override
   public boolean func_71262_S() {
      return true;
   }

   @Override
   public boolean func_181035_ah() {
      return this.field_71340_o.func_73670_a("use-native-transport", true);
   }

   public DedicatedPlayerList func_184103_al() {
      return (DedicatedPlayerList)super.func_184103_al();
   }

   @Override
   public boolean func_71344_c() {
      return true;
   }

   @Override
   public int func_71327_a(String var1, int var2) {
      return this.field_71340_o.func_73669_a(☃, ☃);
   }

   @Override
   public String func_71330_a(String var1, String var2) {
      return this.field_71340_o.func_73671_a(☃, ☃);
   }

   public boolean func_71332_a(String var1, boolean var2) {
      return this.field_71340_o.func_73670_a(☃, ☃);
   }

   @Override
   public void func_71328_a(String var1, Object var2) {
      this.field_71340_o.func_73667_a(☃, ☃);
   }

   @Override
   public void func_71326_a() {
      this.field_71340_o.func_73668_b();
   }

   @Override
   public String func_71329_c() {
      File ☃ = this.field_71340_o.func_73665_c();
      return ☃ != null ? ☃.getAbsolutePath() : "No settings file";
   }

   @Override
   public String func_71277_t() {
      return this.func_71211_k();
   }

   @Override
   public int func_71234_u() {
      return this.func_71215_F();
   }

   @Override
   public String func_71274_v() {
      return this.func_71273_Y();
   }

   public void func_120011_ar() {
      MinecraftServerGui.func_120016_a(this);
      this.field_71335_s = true;
   }

   @Override
   public boolean func_71279_ae() {
      return this.field_71335_s;
   }

   @Override
   public boolean func_195565_a(GameType var1, boolean var2, int var3) {
      return false;
   }

   @Override
   public boolean func_82356_Z() {
      return this.field_71340_o.func_73670_a("enable-command-block", false);
   }

   @Override
   public int func_82357_ak() {
      return this.field_71340_o.func_73669_a("spawn-protection", super.func_82357_ak());
   }

   @Override
   public boolean func_175579_a(World var1, BlockPos var2, EntityPlayer var3) {
      if (☃.field_73011_w.func_186058_p() != DimensionType.OVERWORLD) {
         return false;
      } else if (this.func_184103_al().func_152603_m().func_152690_d()) {
         return false;
      } else if (this.func_184103_al().func_152596_g(☃.func_146103_bH())) {
         return false;
      } else if (this.func_82357_ak() <= 0) {
         return false;
      } else {
         BlockPos ☃ = ☃.func_175694_M();
         int ☃x = MathHelper.func_76130_a(☃.func_177958_n() - ☃.func_177958_n());
         int ☃xx = MathHelper.func_76130_a(☃.func_177952_p() - ☃.func_177952_p());
         int ☃xxx = Math.max(☃x, ☃xx);
         return ☃xxx <= this.func_82357_ak();
      }
   }

   @Override
   public int func_110455_j() {
      return this.field_71340_o.func_73669_a("op-permission-level", 4);
   }

   @Override
   public void func_143006_e(int var1) {
      super.func_143006_e(☃);
      this.field_71340_o.func_73667_a("player-idle-timeout", ☃);
      this.func_71326_a();
   }

   @Override
   public boolean func_195569_l() {
      return this.field_71340_o.func_73670_a("broadcast-rcon-to-ops", true);
   }

   @Override
   public boolean func_195041_r_() {
      return this.field_71340_o.func_73670_a("broadcast-console-to-ops", true);
   }

   @Override
   public int func_175580_aG() {
      int ☃ = this.field_71340_o.func_73669_a("max-world-size", super.func_175580_aG());
      if (☃ < 1) {
         ☃ = 1;
      } else if (☃ > super.func_175580_aG()) {
         ☃ = super.func_175580_aG();
      }

      return ☃;
   }

   @Override
   public int func_175577_aI() {
      return this.field_71340_o.func_73669_a("network-compression-threshold", super.func_175577_aI());
   }

   protected boolean func_152368_aE() {
      boolean ☃ = false;

      for(int ☃x = 0; !☃ && ☃x <= 2; ++☃x) {
         if (☃x > 0) {
            field_155771_h.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.func_152369_aG();
         }

         ☃ = PreYggdrasilConverter.func_152724_a(this);
      }

      boolean ☃x = false;

      for(int var7 = 0; !☃x && var7 <= 2; ++var7) {
         if (var7 > 0) {
            field_155771_h.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.func_152369_aG();
         }

         ☃x = PreYggdrasilConverter.func_152722_b(this);
      }

      boolean ☃xx = false;

      for(int var8 = 0; !☃xx && var8 <= 2; ++var8) {
         if (var8 > 0) {
            field_155771_h.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.func_152369_aG();
         }

         ☃xx = PreYggdrasilConverter.func_152718_c(this);
      }

      boolean ☃xxx = false;

      for(int var9 = 0; !☃xxx && var9 <= 2; ++var9) {
         if (var9 > 0) {
            field_155771_h.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.func_152369_aG();
         }

         ☃xxx = PreYggdrasilConverter.func_152710_d(this);
      }

      boolean ☃xxxx = false;

      for(int var10 = 0; !☃xxxx && var10 <= 2; ++var10) {
         if (var10 > 0) {
            field_155771_h.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.func_152369_aG();
         }

         ☃xxxx = PreYggdrasilConverter.func_152723_a(this, this.field_71340_o);
      }

      return ☃ || ☃x || ☃xx || ☃xxx || ☃xxxx;
   }

   private void func_152369_aG() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var2) {
      }
   }

   public long func_175593_aQ() {
      return this.field_71340_o.func_179885_a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
   }

   @Override
   public String func_71258_A() {
      return "";
   }

   @Override
   public String func_71252_i(String var1) {
      this.field_184115_n.func_70007_b();
      this.func_195571_aL().func_197059_a(this.field_184115_n.func_195540_f(), ☃);
      return this.field_184115_n.func_70008_c();
   }
}
