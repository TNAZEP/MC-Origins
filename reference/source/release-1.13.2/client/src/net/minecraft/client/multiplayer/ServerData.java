package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ServerData {
   public String field_78847_a;
   public String field_78845_b;
   public String field_78846_c;
   public String field_78843_d;
   public long field_78844_e;
   public int field_82821_f = 404;
   public String field_82822_g = "1.13.2";
   public boolean field_78841_f;
   public String field_147412_i;
   private ServerData.ServerResourceMode field_152587_j = ServerData.ServerResourceMode.PROMPT;
   private String field_147411_m;
   private boolean field_181042_l;

   public ServerData(String var1, String var2, boolean var3) {
      this.field_78847_a = ☃;
      this.field_78845_b = ☃;
      this.field_181042_l = ☃;
   }

   public NBTTagCompound func_78836_a() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74778_a("name", this.field_78847_a);
      ☃.func_74778_a("ip", this.field_78845_b);
      if (this.field_147411_m != null) {
         ☃.func_74778_a("icon", this.field_147411_m);
      }

      if (this.field_152587_j == ServerData.ServerResourceMode.ENABLED) {
         ☃.func_74757_a("acceptTextures", true);
      } else if (this.field_152587_j == ServerData.ServerResourceMode.DISABLED) {
         ☃.func_74757_a("acceptTextures", false);
      }

      return ☃;
   }

   public ServerData.ServerResourceMode func_152586_b() {
      return this.field_152587_j;
   }

   public void func_152584_a(ServerData.ServerResourceMode var1) {
      this.field_152587_j = ☃;
   }

   public static ServerData func_78837_a(NBTTagCompound var0) {
      ServerData ☃ = new ServerData(☃.func_74779_i("name"), ☃.func_74779_i("ip"), false);
      if (☃.func_150297_b("icon", 8)) {
         ☃.func_147407_a(☃.func_74779_i("icon"));
      }

      if (☃.func_150297_b("acceptTextures", 1)) {
         if (☃.func_74767_n("acceptTextures")) {
            ☃.func_152584_a(ServerData.ServerResourceMode.ENABLED);
         } else {
            ☃.func_152584_a(ServerData.ServerResourceMode.DISABLED);
         }
      } else {
         ☃.func_152584_a(ServerData.ServerResourceMode.PROMPT);
      }

      return ☃;
   }

   public String func_147409_e() {
      return this.field_147411_m;
   }

   public void func_147407_a(String var1) {
      this.field_147411_m = ☃;
   }

   public boolean func_181041_d() {
      return this.field_181042_l;
   }

   public void func_152583_a(ServerData var1) {
      this.field_78845_b = ☃.field_78845_b;
      this.field_78847_a = ☃.field_78847_a;
      this.func_152584_a(☃.func_152586_b());
      this.field_147411_m = ☃.field_147411_m;
      this.field_181042_l = ☃.field_181042_l;
   }

   public static enum ServerResourceMode {
      ENABLED("enabled"),
      DISABLED("disabled"),
      PROMPT("prompt");

      private final ITextComponent field_152594_d;

      private ServerResourceMode(String var3) {
         this.field_152594_d = new TextComponentTranslation("addServer.resourcePack." + ☃);
      }

      public ITextComponent func_152589_a() {
         return this.field_152594_d;
      }
   }
}
