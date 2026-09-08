package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.FutureTask;
import java.util.function.BooleanSupplier;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.Commands;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.Snooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.CryptManager;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.WorldSavedDataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private static final Logger field_147148_h = LogManager.getLogger();
   private final Minecraft field_71349_l;
   private final WorldSettings field_71350_m;
   private boolean field_71348_o;
   private int field_195580_l = -1;
   private ThreadLanServerPing field_71345_q;
   private UUID field_211528_n;

   public IntegratedServer(
      Minecraft var1,
      String var2,
      String var3,
      WorldSettings var4,
      YggdrasilAuthenticationService var5,
      MinecraftSessionService var6,
      GameProfileRepository var7,
      PlayerProfileCache var8
   ) {
      super(new File(☃.field_71412_D, "saves"), ☃.func_110437_J(), ☃.func_184126_aj(), new Commands(false), ☃, ☃, ☃, ☃);
      this.func_71224_l(☃.func_110432_I().func_111285_a());
      this.func_71261_m(☃);
      this.func_71246_n(☃);
      this.func_71204_b(☃.func_71355_q());
      this.func_71194_c(☃.func_77167_c());
      this.func_71191_d(256);
      this.func_184105_a(new IntegratedPlayerList(this));
      this.field_71349_l = ☃;
      this.field_71350_m = this.func_71242_L() ? WorldServerDemo.field_73071_a : ☃;
   }

   @Override
   protected void func_71247_a(String var1, String var2, long var3, WorldType var5, JsonElement var6) {
      this.func_71237_c(☃);
      ISaveHandler ☃ = this.func_71254_M().func_197715_a(☃, this);
      this.func_175584_a(this.func_71270_I(), ☃);
      WorldInfo ☃x = ☃.func_75757_d();
      if (☃x == null) {
         ☃x = new WorldInfo(this.field_71350_m, ☃);
      } else {
         ☃x.func_76062_a(☃);
      }

      this.func_195560_a(☃.func_75765_b(), ☃x);
      WorldSavedDataStorage ☃ = new WorldSavedDataStorage(☃);
      this.func_212369_a(☃, ☃, ☃x, this.field_71350_m);
      if (this.func_71218_a(DimensionType.OVERWORLD).func_72912_H().func_176130_y() == null) {
         this.func_147139_a(this.field_71349_l.field_71474_y.field_74318_M);
      }

      this.func_71222_d(☃);
   }

   @Override
   protected boolean func_71197_b() throws IOException {
      field_147148_h.info("Starting integrated minecraft server version 1.13.2");
      this.func_71229_d(true);
      this.func_71251_e(true);
      this.func_71257_f(true);
      this.func_71188_g(true);
      this.func_71245_h(true);
      field_147148_h.info("Generating keypair");
      this.func_71253_a(CryptManager.func_75891_b());
      this.func_71247_a(
         this.func_71270_I(), this.func_71221_J(), this.field_71350_m.func_77160_d(), this.field_71350_m.func_77165_h(), this.field_71350_m.func_205391_j()
      );
      this.func_71205_p(this.func_71214_G() + " - " + this.func_71218_a(DimensionType.OVERWORLD).func_72912_H().func_76065_j());
      return true;
   }

   @Override
   protected void func_71217_p(BooleanSupplier var1) {
      boolean ☃ = this.field_71348_o;
      this.field_71348_o = Minecraft.func_71410_x().func_147114_u() != null && Minecraft.func_71410_x().func_147113_T();
      if (!☃ && this.field_71348_o) {
         field_147148_h.info("Saving and pausing game...");
         this.func_184103_al().func_72389_g();
         this.func_71267_a(false);
      }

      FutureTask<?> ☃;
      if (this.field_71348_o) {
         while((☃ = (FutureTask)this.field_175589_i.poll()) != null) {
            Util.func_181617_a(☃, field_147148_h);
         }
      } else {
         super.func_71217_p(☃);
         if (this.field_71349_l.field_71474_y.field_151451_c != this.func_184103_al().func_72395_o()) {
            field_147148_h.info("Changing view distance to {}, from {}", this.field_71349_l.field_71474_y.field_151451_c, this.func_184103_al().func_72395_o());
            this.func_184103_al().func_152611_a(this.field_71349_l.field_71474_y.field_151451_c);
         }

         if (this.field_71349_l.field_71441_e != null) {
            WorldInfo ☃ = this.func_71218_a(DimensionType.OVERWORLD).func_72912_H();
            WorldInfo ☃x = this.field_71349_l.field_71441_e.func_72912_H();
            if (!☃.func_176123_z() && ☃x.func_176130_y() != ☃.func_176130_y()) {
               field_147148_h.info("Changing difficulty to {}, from {}", ☃x.func_176130_y(), ☃.func_176130_y());
               this.func_147139_a(☃x.func_176130_y());
            } else if (☃x.func_176123_z() && !☃.func_176123_z()) {
               field_147148_h.info("Locking difficulty to {}", ☃x.func_176130_y());

               for(WorldServer ☃ : this.func_212370_w()) {
                  if (☃ != null) {
                     ☃.func_72912_H().func_180783_e(true);
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean func_71225_e() {
      return false;
   }

   @Override
   public GameType func_71265_f() {
      return this.field_71350_m.func_77162_e();
   }

   @Override
   public EnumDifficulty func_147135_j() {
      return this.field_71349_l.field_71441_e.func_72912_H().func_176130_y();
   }

   @Override
   public boolean func_71199_h() {
      return this.field_71350_m.func_77158_f();
   }

   @Override
   public boolean func_195569_l() {
      return true;
   }

   @Override
   public boolean func_195041_r_() {
      return true;
   }

   @Override
   public File func_71238_n() {
      return this.field_71349_l.field_71412_D;
   }

   @Override
   public boolean func_71262_S() {
      return false;
   }

   @Override
   public boolean func_181035_ah() {
      return false;
   }

   @Override
   protected void func_71228_a(CrashReport var1) {
      this.field_71349_l.func_71404_a(☃);
   }

   @Override
   public CrashReport func_71230_b(CrashReport var1) {
      ☃ = super.func_71230_b(☃);
      ☃.func_85056_g().func_71507_a("Type", "Integrated Server (map_client.txt)");
      ☃.func_85056_g()
         .func_189529_a(
            "Is Modded",
            () -> {
               String ☃ = ClientBrandRetriever.getClientModName();
               if (!☃.equals("vanilla")) {
                  return "Definitely; Client brand changed to '" + ☃ + "'";
               } else {
                  ☃ = this.getServerModName();
                  if (!"vanilla".equals(☃)) {
                     return "Definitely; Server brand changed to '" + ☃ + "'";
                  } else {
                     return Minecraft.class.getSigners() == null
                        ? "Very likely; Jar signature invalidated"
                        : "Probably not. Jar signature remains and both client + server brands are untouched.";
                  }
               }
            }
         );
      return ☃;
   }

   @Override
   public void func_147139_a(EnumDifficulty var1) {
      super.func_147139_a(☃);
      if (this.field_71349_l.field_71441_e != null) {
         this.field_71349_l.field_71441_e.func_72912_H().func_176144_a(☃);
      }
   }

   @Override
   public void func_70000_a(Snooper var1) {
      super.func_70000_a(☃);
      ☃.func_152768_a("snooper_partner", this.field_71349_l.func_71378_E().func_80006_f());
   }

   @Override
   public boolean func_70002_Q() {
      return Minecraft.func_71410_x().func_70002_Q();
   }

   @Override
   public boolean func_195565_a(GameType var1, boolean var2, int var3) {
      try {
         this.func_147137_ag().func_151265_a(null, ☃);
         field_147148_h.info("Started serving on {}", ☃);
         this.field_195580_l = ☃;
         this.field_71345_q = new ThreadLanServerPing(this.func_71273_Y(), ☃ + "");
         this.field_71345_q.start();
         this.func_184103_al().func_152604_a(☃);
         this.func_184103_al().func_72387_b(☃);
         int ☃ = this.func_211833_a(this.field_71349_l.field_71439_g.func_146103_bH());
         this.field_71349_l.field_71439_g.func_184839_n(☃);

         for(EntityPlayerMP ☃x : this.func_184103_al().func_181057_v()) {
            this.func_195571_aL().func_197051_a(☃x);
         }

         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   @Override
   public void func_71260_j() {
      super.func_71260_j();
      if (this.field_71345_q != null) {
         this.field_71345_q.interrupt();
         this.field_71345_q = null;
      }
   }

   @Override
   public void func_71263_m() {
      Futures.getUnchecked(this.func_152344_a(() -> {
         for(EntityPlayerMP ☃ : Lists.newArrayList(this.func_184103_al().func_181057_v())) {
            if (!☃.func_110124_au().equals(this.field_211528_n)) {
               this.func_184103_al().func_72367_e(☃);
            }
         }
      }));
      super.func_71263_m();
      if (this.field_71345_q != null) {
         this.field_71345_q.interrupt();
         this.field_71345_q = null;
      }
   }

   @Override
   public boolean func_71344_c() {
      return this.field_195580_l > -1;
   }

   @Override
   public int func_71215_F() {
      return this.field_195580_l;
   }

   @Override
   public void func_71235_a(GameType var1) {
      super.func_71235_a(☃);
      this.func_184103_al().func_152604_a(☃);
   }

   @Override
   public boolean func_82356_Z() {
      return true;
   }

   @Override
   public int func_110455_j() {
      return 2;
   }

   public void func_211527_b(UUID var1) {
      this.field_211528_n = ☃;
   }
}
