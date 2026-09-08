package net.minecraft.server.dedicated;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import net.minecraft.server.management.PlayerList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedPlayerList extends PlayerList {
   private static final Logger field_164439_d = LogManager.getLogger();

   public DedicatedPlayerList(DedicatedServer var1) {
      super(☃);
      this.func_152611_a(☃.func_71327_a("view-distance", 10));
      this.field_72405_c = ☃.func_71327_a("max-players", 20);
      this.func_72371_a(☃.func_71332_a("white-list", false));
      if (!☃.func_71264_H()) {
         this.func_152608_h().func_152686_a(true);
         this.func_72363_f().func_152686_a(true);
      }

      this.func_187246_z();
      this.func_187248_x();
      this.func_187249_y();
      this.func_187247_w();
      this.func_72417_t();
      this.func_72418_v();
      this.func_72419_u();
      if (!this.func_152599_k().func_152691_c().exists()) {
         this.func_72421_w();
      }
   }

   @Override
   public void func_72371_a(boolean var1) {
      super.func_72371_a(☃);
      this.func_72365_p().func_71328_a("white-list", ☃);
      this.func_72365_p().func_71326_a();
   }

   @Override
   public void func_152605_a(GameProfile var1) {
      super.func_152605_a(☃);
      this.func_72419_u();
   }

   @Override
   public void func_152610_b(GameProfile var1) {
      super.func_152610_b(☃);
      this.func_72419_u();
   }

   @Override
   public void func_187244_a() {
      this.func_72418_v();
   }

   private void func_187247_w() {
      try {
         this.func_72363_f().func_152678_f();
      } catch (IOException var2) {
         field_164439_d.warn("Failed to save ip banlist: ", var2);
      }
   }

   private void func_187248_x() {
      try {
         this.func_152608_h().func_152678_f();
      } catch (IOException var2) {
         field_164439_d.warn("Failed to save user banlist: ", var2);
      }
   }

   private void func_187249_y() {
      try {
         this.func_72363_f().func_152679_g();
      } catch (IOException var2) {
         field_164439_d.warn("Failed to load ip banlist: ", var2);
      }
   }

   private void func_187246_z() {
      try {
         this.func_152608_h().func_152679_g();
      } catch (IOException var2) {
         field_164439_d.warn("Failed to load user banlist: ", var2);
      }
   }

   private void func_72417_t() {
      try {
         this.func_152603_m().func_152679_g();
      } catch (Exception var2) {
         field_164439_d.warn("Failed to load operators list: ", var2);
      }
   }

   private void func_72419_u() {
      try {
         this.func_152603_m().func_152678_f();
      } catch (Exception var2) {
         field_164439_d.warn("Failed to save operators list: ", var2);
      }
   }

   private void func_72418_v() {
      try {
         this.func_152599_k().func_152679_g();
      } catch (Exception var2) {
         field_164439_d.warn("Failed to load white-list: ", var2);
      }
   }

   private void func_72421_w() {
      try {
         this.func_152599_k().func_152678_f();
      } catch (Exception var2) {
         field_164439_d.warn("Failed to save white-list: ", var2);
      }
   }

   @Override
   public boolean func_152607_e(GameProfile var1) {
      return !this.func_72383_n() || this.func_152596_g(☃) || this.func_152599_k().func_152705_a(☃);
   }

   public DedicatedServer func_72365_p() {
      return (DedicatedServer)super.func_72365_p();
   }

   @Override
   public boolean func_183023_f(GameProfile var1) {
      return this.func_152603_m().func_183026_b(☃);
   }
}
