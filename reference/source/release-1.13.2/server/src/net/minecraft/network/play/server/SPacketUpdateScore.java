package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ServerScoreboard;

public class SPacketUpdateScore implements Packet<INetHandlerPlayClient> {
   private String field_149329_a = "";
   @Nullable
   private String field_149327_b;
   private int field_149328_c;
   private ServerScoreboard.Action field_149326_d;

   public SPacketUpdateScore() {
   }

   public SPacketUpdateScore(ServerScoreboard.Action var1, @Nullable String var2, String var3, int var4) {
      if (☃ != ServerScoreboard.Action.REMOVE && ☃ == null) {
         throw new IllegalArgumentException("Need an objective name");
      } else {
         this.field_149329_a = ☃;
         this.field_149327_b = ☃;
         this.field_149328_c = ☃;
         this.field_149326_d = ☃;
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149329_a = ☃.func_150789_c(40);
      this.field_149326_d = ☃.func_179257_a(ServerScoreboard.Action.class);
      String ☃ = ☃.func_150789_c(16);
      this.field_149327_b = Objects.equals(☃, "") ? null : ☃;
      if (this.field_149326_d != ServerScoreboard.Action.REMOVE) {
         this.field_149328_c = ☃.func_150792_a();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_180714_a(this.field_149329_a);
      ☃.func_179249_a(this.field_149326_d);
      ☃.func_180714_a(this.field_149327_b == null ? "" : this.field_149327_b);
      if (this.field_149326_d != ServerScoreboard.Action.REMOVE) {
         ☃.func_150787_b(this.field_149328_c);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147250_a(this);
   }
}
