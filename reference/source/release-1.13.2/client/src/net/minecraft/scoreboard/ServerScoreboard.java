package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer field_96555_a;
   private final Set<ScoreObjective> field_96553_b = Sets.<ScoreObjective>newHashSet();
   private Runnable[] field_186685_c = new Runnable[0];

   public ServerScoreboard(MinecraftServer var1) {
      this.field_96555_a = ☃;
   }

   @Override
   public void func_96536_a(Score var1) {
      super.func_96536_a(☃);
      if (this.field_96553_b.contains(☃.func_96645_d())) {
         this.field_96555_a
            .func_184103_al()
            .func_148540_a(new SPacketUpdateScore(ServerScoreboard.Action.CHANGE, ☃.func_96645_d().func_96679_b(), ☃.func_96653_e(), ☃.func_96652_c()));
      }

      this.func_96551_b();
   }

   @Override
   public void func_96516_a(String var1) {
      super.func_96516_a(☃);
      this.field_96555_a.func_184103_al().func_148540_a(new SPacketUpdateScore(ServerScoreboard.Action.REMOVE, null, ☃, 0));
      this.func_96551_b();
   }

   @Override
   public void func_178820_a(String var1, ScoreObjective var2) {
      super.func_178820_a(☃, ☃);
      if (this.field_96553_b.contains(☃)) {
         this.field_96555_a.func_184103_al().func_148540_a(new SPacketUpdateScore(ServerScoreboard.Action.REMOVE, ☃.func_96679_b(), ☃, 0));
      }

      this.func_96551_b();
   }

   @Override
   public void func_96530_a(int var1, @Nullable ScoreObjective var2) {
      ScoreObjective ☃ = this.func_96539_a(☃);
      super.func_96530_a(☃, ☃);
      if (☃ != ☃ && ☃ != null) {
         if (this.func_96552_h(☃) > 0) {
            this.field_96555_a.func_184103_al().func_148540_a(new SPacketDisplayObjective(☃, ☃));
         } else {
            this.func_96546_g(☃);
         }
      }

      if (☃ != null) {
         if (this.field_96553_b.contains(☃)) {
            this.field_96555_a.func_184103_al().func_148540_a(new SPacketDisplayObjective(☃, ☃));
         } else {
            this.func_96549_e(☃);
         }
      }

      this.func_96551_b();
   }

   @Override
   public boolean func_197901_a(String var1, ScorePlayerTeam var2) {
      if (super.func_197901_a(☃, ☃)) {
         this.field_96555_a.func_184103_al().func_148540_a(new SPacketTeams(☃, Arrays.asList(☃), 3));
         this.func_96551_b();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void func_96512_b(String var1, ScorePlayerTeam var2) {
      super.func_96512_b(☃, ☃);
      this.field_96555_a.func_184103_al().func_148540_a(new SPacketTeams(☃, Arrays.asList(☃), 4));
      this.func_96551_b();
   }

   @Override
   public void func_96522_a(ScoreObjective var1) {
      super.func_96522_a(☃);
      this.func_96551_b();
   }

   @Override
   public void func_199869_b(ScoreObjective var1) {
      super.func_199869_b(☃);
      if (this.field_96553_b.contains(☃)) {
         this.field_96555_a.func_184103_al().func_148540_a(new SPacketScoreboardObjective(☃, 2));
      }

      this.func_96551_b();
   }

   @Override
   public void func_96533_c(ScoreObjective var1) {
      super.func_96533_c(☃);
      if (this.field_96553_b.contains(☃)) {
         this.func_96546_g(☃);
      }

      this.func_96551_b();
   }

   @Override
   public void func_96523_a(ScorePlayerTeam var1) {
      super.func_96523_a(☃);
      this.field_96555_a.func_184103_al().func_148540_a(new SPacketTeams(☃, 0));
      this.func_96551_b();
   }

   @Override
   public void func_96538_b(ScorePlayerTeam var1) {
      super.func_96538_b(☃);
      this.field_96555_a.func_184103_al().func_148540_a(new SPacketTeams(☃, 2));
      this.func_96551_b();
   }

   @Override
   public void func_96513_c(ScorePlayerTeam var1) {
      super.func_96513_c(☃);
      this.field_96555_a.func_184103_al().func_148540_a(new SPacketTeams(☃, 1));
      this.func_96551_b();
   }

   public void func_186684_a(Runnable var1) {
      this.field_186685_c = (Runnable[])Arrays.copyOf(this.field_186685_c, this.field_186685_c.length + 1);
      this.field_186685_c[this.field_186685_c.length - 1] = ☃;
   }

   protected void func_96551_b() {
      for(Runnable ☃ : this.field_186685_c) {
         ☃.run();
      }
   }

   public List<Packet<?>> func_96550_d(ScoreObjective var1) {
      List<Packet<?>> ☃ = Lists.<Packet<?>>newArrayList();
      ☃.add(new SPacketScoreboardObjective(☃, 0));

      for(int ☃x = 0; ☃x < 19; ++☃x) {
         if (this.func_96539_a(☃x) == ☃) {
            ☃.add(new SPacketDisplayObjective(☃x, ☃));
         }
      }

      for(Score ☃x : this.func_96534_i(☃)) {
         ☃.add(new SPacketUpdateScore(ServerScoreboard.Action.CHANGE, ☃x.func_96645_d().func_96679_b(), ☃x.func_96653_e(), ☃x.func_96652_c()));
      }

      return ☃;
   }

   public void func_96549_e(ScoreObjective var1) {
      List<Packet<?>> ☃ = this.func_96550_d(☃);

      for(EntityPlayerMP ☃x : this.field_96555_a.func_184103_al().func_181057_v()) {
         for(Packet<?> ☃xx : ☃) {
            ☃x.field_71135_a.func_147359_a(☃xx);
         }
      }

      this.field_96553_b.add(☃);
   }

   public List<Packet<?>> func_96548_f(ScoreObjective var1) {
      List<Packet<?>> ☃ = Lists.<Packet<?>>newArrayList();
      ☃.add(new SPacketScoreboardObjective(☃, 1));

      for(int ☃x = 0; ☃x < 19; ++☃x) {
         if (this.func_96539_a(☃x) == ☃) {
            ☃.add(new SPacketDisplayObjective(☃x, ☃));
         }
      }

      return ☃;
   }

   public void func_96546_g(ScoreObjective var1) {
      List<Packet<?>> ☃ = this.func_96548_f(☃);

      for(EntityPlayerMP ☃x : this.field_96555_a.func_184103_al().func_181057_v()) {
         for(Packet<?> ☃xx : ☃) {
            ☃x.field_71135_a.func_147359_a(☃xx);
         }
      }

      this.field_96553_b.remove(☃);
   }

   public int func_96552_h(ScoreObjective var1) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < 19; ++☃x) {
         if (this.func_96539_a(☃x) == ☃) {
            ++☃;
         }
      }

      return ☃;
   }

   public static enum Action {
      CHANGE,
      REMOVE;
   }
}
