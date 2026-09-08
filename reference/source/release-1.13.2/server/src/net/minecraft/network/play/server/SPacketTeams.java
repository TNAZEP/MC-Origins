package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class SPacketTeams implements Packet<INetHandlerPlayClient> {
   private String field_149320_a = "";
   private ITextComponent field_149318_b = new TextComponentString("");
   private ITextComponent field_207509_c = new TextComponentString("");
   private ITextComponent field_207510_d = new TextComponentString("");
   private String field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
   private String field_186976_f = Team.CollisionRule.ALWAYS.field_186693_e;
   private TextFormatting field_179815_f = TextFormatting.RESET;
   private final Collection<String> field_149317_e = Lists.newArrayList();
   private int field_149314_f;
   private int field_149315_g;

   public SPacketTeams() {
   }

   public SPacketTeams(ScorePlayerTeam var1, int var2) {
      this.field_149320_a = ☃.func_96661_b();
      this.field_149314_f = ☃;
      if (☃ == 0 || ☃ == 2) {
         this.field_149318_b = ☃.func_96669_c();
         this.field_149315_g = ☃.func_98299_i();
         this.field_179816_e = ☃.func_178770_i().field_178830_e;
         this.field_186976_f = ☃.func_186681_k().field_186693_e;
         this.field_179815_f = ☃.func_178775_l();
         this.field_207509_c = ☃.func_207406_e();
         this.field_207510_d = ☃.func_207407_f();
      }

      if (☃ == 0) {
         this.field_149317_e.addAll(☃.func_96670_d());
      }
   }

   public SPacketTeams(ScorePlayerTeam var1, Collection<String> var2, int var3) {
      if (☃ != 3 && ☃ != 4) {
         throw new IllegalArgumentException("Method must be join or leave for player constructor");
      } else if (☃ != null && !☃.isEmpty()) {
         this.field_149314_f = ☃;
         this.field_149320_a = ☃.func_96661_b();
         this.field_149317_e.addAll(☃);
      } else {
         throw new IllegalArgumentException("Players cannot be null/empty");
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149320_a = ☃.func_150789_c(16);
      this.field_149314_f = ☃.readByte();
      if (this.field_149314_f == 0 || this.field_149314_f == 2) {
         this.field_149318_b = ☃.func_179258_d();
         this.field_149315_g = ☃.readByte();
         this.field_179816_e = ☃.func_150789_c(40);
         this.field_186976_f = ☃.func_150789_c(40);
         this.field_179815_f = ☃.func_179257_a(TextFormatting.class);
         this.field_207509_c = ☃.func_179258_d();
         this.field_207510_d = ☃.func_179258_d();
      }

      if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
         int ☃ = ☃.func_150792_a();

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            this.field_149317_e.add(☃.func_150789_c(40));
         }
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_180714_a(this.field_149320_a);
      ☃.writeByte(this.field_149314_f);
      if (this.field_149314_f == 0 || this.field_149314_f == 2) {
         ☃.func_179256_a(this.field_149318_b);
         ☃.writeByte(this.field_149315_g);
         ☃.func_180714_a(this.field_179816_e);
         ☃.func_180714_a(this.field_186976_f);
         ☃.func_179249_a(this.field_179815_f);
         ☃.func_179256_a(this.field_207509_c);
         ☃.func_179256_a(this.field_207510_d);
      }

      if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
         ☃.func_150787_b(this.field_149317_e.size());

         for(String ☃ : this.field_149317_e) {
            ☃.func_180714_a(☃);
         }
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147247_a(this);
   }
}
