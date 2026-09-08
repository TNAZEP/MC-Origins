package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardSaveData;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer server;
   private final Set<Objective> trackedObjectives = Sets.<Objective>newHashSet();
   private final List<Runnable> dirtyListeners = Lists.newArrayList();

   public ServerScoreboard(MinecraftServer var1) {
      this.server = â˜ƒ;
   }

   @Override
   public void onScoreChanged(Score var1) {
      super.onScoreChanged(â˜ƒ);
      if (this.trackedObjectives.contains(â˜ƒ.getObjective())) {
         this.server
            .getPlayerList()
            .broadcastAll(new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, â˜ƒ.getObjective().getName(), â˜ƒ.getOwner(), â˜ƒ.getScore()));
      }

      this.setDirty();
   }

   @Override
   public void onPlayerRemoved(String var1) {
      super.onPlayerRemoved(â˜ƒ);
      this.server.getPlayerList().broadcastAll(new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, null, â˜ƒ, 0));
      this.setDirty();
   }

   @Override
   public void onPlayerScoreRemoved(String var1, Objective var2) {
      super.onPlayerScoreRemoved(â˜ƒ, â˜ƒ);
      if (this.trackedObjectives.contains(â˜ƒ)) {
         this.server.getPlayerList().broadcastAll(new ClientboundSetScorePacket(ServerScoreboard.Method.REMOVE, â˜ƒ.getName(), â˜ƒ, 0));
      }

      this.setDirty();
   }

   @Override
   public void setDisplayObjective(int var1, @Nullable Objective var2) {
      Objective â˜ƒ = this.getDisplayObjective(â˜ƒ);
      super.setDisplayObjective(â˜ƒ, â˜ƒ);
      if (â˜ƒ != â˜ƒ && â˜ƒ != null) {
         if (this.getObjectiveDisplaySlotCount(â˜ƒ) > 0) {
            this.server.getPlayerList().broadcastAll(new ClientboundSetDisplayObjectivePacket(â˜ƒ, â˜ƒ));
         } else {
            this.stopTrackingObjective(â˜ƒ);
         }
      }

      if (â˜ƒ != null) {
         if (this.trackedObjectives.contains(â˜ƒ)) {
            this.server.getPlayerList().broadcastAll(new ClientboundSetDisplayObjectivePacket(â˜ƒ, â˜ƒ));
         } else {
            this.startTrackingObjective(â˜ƒ);
         }
      }

      this.setDirty();
   }

   @Override
   public boolean addPlayerToTeam(String var1, PlayerTeam var2) {
      if (super.addPlayerToTeam(â˜ƒ, â˜ƒ)) {
         this.server.getPlayerList().broadcastAll(ClientboundSetPlayerTeamPacket.createPlayerPacket(â˜ƒ, â˜ƒ, ClientboundSetPlayerTeamPacket.Action.ADD));
         this.setDirty();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void removePlayerFromTeam(String var1, PlayerTeam var2) {
      super.removePlayerFromTeam(â˜ƒ, â˜ƒ);
      this.server.getPlayerList().broadcastAll(ClientboundSetPlayerTeamPacket.createPlayerPacket(â˜ƒ, â˜ƒ, ClientboundSetPlayerTeamPacket.Action.REMOVE));
      this.setDirty();
   }

   @Override
   public void onObjectiveAdded(Objective var1) {
      super.onObjectiveAdded(â˜ƒ);
      this.setDirty();
   }

   @Override
   public void onObjectiveChanged(Objective var1) {
      super.onObjectiveChanged(â˜ƒ);
      if (this.trackedObjectives.contains(â˜ƒ)) {
         this.server.getPlayerList().broadcastAll(new ClientboundSetObjectivePacket(â˜ƒ, 2));
      }

      this.setDirty();
   }

   @Override
   public void onObjectiveRemoved(Objective var1) {
      super.onObjectiveRemoved(â˜ƒ);
      if (this.trackedObjectives.contains(â˜ƒ)) {
         this.stopTrackingObjective(â˜ƒ);
      }

      this.setDirty();
   }

   @Override
   public void onTeamAdded(PlayerTeam var1) {
      super.onTeamAdded(â˜ƒ);
      this.server.getPlayerList().broadcastAll(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(â˜ƒ, true));
      this.setDirty();
   }

   @Override
   public void onTeamChanged(PlayerTeam var1) {
      super.onTeamChanged(â˜ƒ);
      this.server.getPlayerList().broadcastAll(ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(â˜ƒ, false));
      this.setDirty();
   }

   @Override
   public void onTeamRemoved(PlayerTeam var1) {
      super.onTeamRemoved(â˜ƒ);
      this.server.getPlayerList().broadcastAll(ClientboundSetPlayerTeamPacket.createRemovePacket(â˜ƒ));
      this.setDirty();
   }

   public void addDirtyListener(Runnable var1) {
      this.dirtyListeners.add(â˜ƒ);
   }

   protected void setDirty() {
      for(Runnable â˜ƒ : this.dirtyListeners) {
         â˜ƒ.run();
      }
   }

   public List<Packet<?>> getStartTrackingPackets(Objective var1) {
      List<Packet<?>> â˜ƒ = Lists.<Packet<?>>newArrayList();
      â˜ƒ.add(new ClientboundSetObjectivePacket(â˜ƒ, 0));

      for(int â˜ƒx = 0; â˜ƒx < 19; ++â˜ƒx) {
         if (this.getDisplayObjective(â˜ƒx) == â˜ƒ) {
            â˜ƒ.add(new ClientboundSetDisplayObjectivePacket(â˜ƒx, â˜ƒ));
         }
      }

      for(Score â˜ƒx : this.getPlayerScores(â˜ƒ)) {
         â˜ƒ.add(new ClientboundSetScorePacket(ServerScoreboard.Method.CHANGE, â˜ƒx.getObjective().getName(), â˜ƒx.getOwner(), â˜ƒx.getScore()));
      }

      return â˜ƒ;
   }

   public void startTrackingObjective(Objective var1) {
      List<Packet<?>> â˜ƒ = this.getStartTrackingPackets(â˜ƒ);

      for(ServerPlayer â˜ƒx : this.server.getPlayerList().getPlayers()) {
         for(Packet<?> â˜ƒxx : â˜ƒ) {
            â˜ƒx.connection.send(â˜ƒxx);
         }
      }

      this.trackedObjectives.add(â˜ƒ);
   }

   public List<Packet<?>> getStopTrackingPackets(Objective var1) {
      List<Packet<?>> â˜ƒ = Lists.<Packet<?>>newArrayList();
      â˜ƒ.add(new ClientboundSetObjectivePacket(â˜ƒ, 1));

      for(int â˜ƒx = 0; â˜ƒx < 19; ++â˜ƒx) {
         if (this.getDisplayObjective(â˜ƒx) == â˜ƒ) {
            â˜ƒ.add(new ClientboundSetDisplayObjectivePacket(â˜ƒx, â˜ƒ));
         }
      }

      return â˜ƒ;
   }

   public void stopTrackingObjective(Objective var1) {
      List<Packet<?>> â˜ƒ = this.getStopTrackingPackets(â˜ƒ);

      for(ServerPlayer â˜ƒx : this.server.getPlayerList().getPlayers()) {
         for(Packet<?> â˜ƒxx : â˜ƒ) {
            â˜ƒx.connection.send(â˜ƒxx);
         }
      }

      this.trackedObjectives.remove(â˜ƒ);
   }

   public int getObjectiveDisplaySlotCount(Objective var1) {
      int â˜ƒ = 0;

      for(int â˜ƒx = 0; â˜ƒx < 19; ++â˜ƒx) {
         if (this.getDisplayObjective(â˜ƒx) == â˜ƒ) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   public ScoreboardSaveData createData() {
      ScoreboardSaveData â˜ƒ = new ScoreboardSaveData(this);
      this.addDirtyListener(â˜ƒ::setDirty);
      return â˜ƒ;
   }

   public ScoreboardSaveData createData(CompoundTag var1) {
      return this.createData().load(â˜ƒ);
   }

   public static enum Method {
      CHANGE,
      REMOVE;
   }
}
