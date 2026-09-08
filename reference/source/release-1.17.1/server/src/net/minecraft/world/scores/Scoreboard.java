package net.minecraft.world.scores;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class Scoreboard {
   public static final int DISPLAY_SLOT_LIST = 0;
   public static final int DISPLAY_SLOT_SIDEBAR = 1;
   public static final int DISPLAY_SLOT_BELOW_NAME = 2;
   public static final int DISPLAY_SLOT_TEAMS_SIDEBAR_START = 3;
   public static final int DISPLAY_SLOT_TEAMS_SIDEBAR_END = 18;
   public static final int DISPLAY_SLOTS = 19;
   public static final int MAX_NAME_LENGTH = 40;
   private final Map<String, Objective> objectivesByName = Maps.newHashMap();
   private final Map<ObjectiveCriteria, List<Objective>> objectivesByCriteria = Maps.newHashMap();
   private final Map<String, Map<Objective, Score>> playerScores = Maps.newHashMap();
   private final Objective[] displayObjectives = new Objective[19];
   private final Map<String, PlayerTeam> teamsByName = Maps.newHashMap();
   private final Map<String, PlayerTeam> teamsByPlayer = Maps.newHashMap();
   private static String[] displaySlotNames;

   public boolean hasObjective(String var1) {
      return this.objectivesByName.containsKey(â˜ƒ);
   }

   public Objective getOrCreateObjective(String var1) {
      return (Objective)this.objectivesByName.get(â˜ƒ);
   }

   @Nullable
   public Objective getObjective(@Nullable String var1) {
      return (Objective)this.objectivesByName.get(â˜ƒ);
   }

   public Objective addObjective(String var1, ObjectiveCriteria var2, Component var3, ObjectiveCriteria.RenderType var4) {
      if (â˜ƒ.length() > 16) {
         throw new IllegalArgumentException("The objective name '" + â˜ƒ + "' is too long!");
      } else if (this.objectivesByName.containsKey(â˜ƒ)) {
         throw new IllegalArgumentException("An objective with the name '" + â˜ƒ + "' already exists!");
      } else {
         Objective â˜ƒ = new Objective(this, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         ((List)this.objectivesByCriteria.computeIfAbsent(â˜ƒ, var0 -> Lists.newArrayList())).add(â˜ƒ);
         this.objectivesByName.put(â˜ƒ, â˜ƒ);
         this.onObjectiveAdded(â˜ƒ);
         return â˜ƒ;
      }
   }

   public final void forAllObjectives(ObjectiveCriteria var1, String var2, Consumer<Score> var3) {
      ((List)this.objectivesByCriteria.getOrDefault(â˜ƒ, Collections.emptyList())).forEach(var3x -> â˜ƒ.accept(this.getOrCreatePlayerScore(â˜ƒ, var3x)));
   }

   public boolean hasPlayerScore(String var1, Objective var2) {
      Map<Objective, Score> â˜ƒ = (Map)this.playerScores.get(â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         Score â˜ƒ = (Score)â˜ƒ.get(â˜ƒ);
         return â˜ƒ != null;
      }
   }

   public Score getOrCreatePlayerScore(String var1, Objective var2) {
      if (â˜ƒ.length() > 40) {
         throw new IllegalArgumentException("The player name '" + â˜ƒ + "' is too long!");
      } else {
         Map<Objective, Score> â˜ƒ = (Map)this.playerScores.computeIfAbsent(â˜ƒ, var0 -> Maps.newHashMap());
         return (Score)â˜ƒ.computeIfAbsent(â˜ƒ, var2x -> {
            Score â˜ƒ = new Score(this, var2x, â˜ƒ);
            â˜ƒ.setScore(0);
            return â˜ƒ;
         });
      }
   }

   public Collection<Score> getPlayerScores(Objective var1) {
      List<Score> â˜ƒ = Lists.<Score>newArrayList();

      for(Map<Objective, Score> â˜ƒx : this.playerScores.values()) {
         Score â˜ƒxx = (Score)â˜ƒx.get(â˜ƒ);
         if (â˜ƒxx != null) {
            â˜ƒ.add(â˜ƒxx);
         }
      }

      â˜ƒ.sort(Score.SCORE_COMPARATOR);
      return â˜ƒ;
   }

   public Collection<Objective> getObjectives() {
      return this.objectivesByName.values();
   }

   public Collection<String> getObjectiveNames() {
      return this.objectivesByName.keySet();
   }

   public Collection<String> getTrackedPlayers() {
      return Lists.newArrayList(this.playerScores.keySet());
   }

   public void resetPlayerScore(String var1, @Nullable Objective var2) {
      if (â˜ƒ == null) {
         Map<Objective, Score> â˜ƒ = (Map)this.playerScores.remove(â˜ƒ);
         if (â˜ƒ != null) {
            this.onPlayerRemoved(â˜ƒ);
         }
      } else {
         Map<Objective, Score> â˜ƒ = (Map)this.playerScores.get(â˜ƒ);
         if (â˜ƒ != null) {
            Score â˜ƒx = (Score)â˜ƒ.remove(â˜ƒ);
            if (â˜ƒ.size() < 1) {
               Map<Objective, Score> â˜ƒxx = (Map)this.playerScores.remove(â˜ƒ);
               if (â˜ƒxx != null) {
                  this.onPlayerRemoved(â˜ƒ);
               }
            } else if (â˜ƒx != null) {
               this.onPlayerScoreRemoved(â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   public Map<Objective, Score> getPlayerScores(String var1) {
      Map<Objective, Score> â˜ƒ = (Map)this.playerScores.get(â˜ƒ);
      if (â˜ƒ == null) {
         â˜ƒ = Maps.<Objective, Score>newHashMap();
      }

      return â˜ƒ;
   }

   public void removeObjective(Objective var1) {
      this.objectivesByName.remove(â˜ƒ.getName());

      for(int â˜ƒ = 0; â˜ƒ < 19; ++â˜ƒ) {
         if (this.getDisplayObjective(â˜ƒ) == â˜ƒ) {
            this.setDisplayObjective(â˜ƒ, null);
         }
      }

      List<Objective> â˜ƒ = (List)this.objectivesByCriteria.get(â˜ƒ.getCriteria());
      if (â˜ƒ != null) {
         â˜ƒ.remove(â˜ƒ);
      }

      for(Map<Objective, Score> â˜ƒ : this.playerScores.values()) {
         â˜ƒ.remove(â˜ƒ);
      }

      this.onObjectiveRemoved(â˜ƒ);
   }

   public void setDisplayObjective(int var1, @Nullable Objective var2) {
      this.displayObjectives[â˜ƒ] = â˜ƒ;
   }

   @Nullable
   public Objective getDisplayObjective(int var1) {
      return this.displayObjectives[â˜ƒ];
   }

   @Nullable
   public PlayerTeam getPlayerTeam(String var1) {
      return (PlayerTeam)this.teamsByName.get(â˜ƒ);
   }

   public PlayerTeam addPlayerTeam(String var1) {
      if (â˜ƒ.length() > 16) {
         throw new IllegalArgumentException("The team name '" + â˜ƒ + "' is too long!");
      } else {
         PlayerTeam â˜ƒ = this.getPlayerTeam(â˜ƒ);
         if (â˜ƒ != null) {
            throw new IllegalArgumentException("A team with the name '" + â˜ƒ + "' already exists!");
         } else {
            â˜ƒ = new PlayerTeam(this, â˜ƒ);
            this.teamsByName.put(â˜ƒ, â˜ƒ);
            this.onTeamAdded(â˜ƒ);
            return â˜ƒ;
         }
      }
   }

   public void removePlayerTeam(PlayerTeam var1) {
      this.teamsByName.remove(â˜ƒ.getName());

      for(String â˜ƒ : â˜ƒ.getPlayers()) {
         this.teamsByPlayer.remove(â˜ƒ);
      }

      this.onTeamRemoved(â˜ƒ);
   }

   public boolean addPlayerToTeam(String var1, PlayerTeam var2) {
      if (â˜ƒ.length() > 40) {
         throw new IllegalArgumentException("The player name '" + â˜ƒ + "' is too long!");
      } else {
         if (this.getPlayersTeam(â˜ƒ) != null) {
            this.removePlayerFromTeam(â˜ƒ);
         }

         this.teamsByPlayer.put(â˜ƒ, â˜ƒ);
         return â˜ƒ.getPlayers().add(â˜ƒ);
      }
   }

   public boolean removePlayerFromTeam(String var1) {
      PlayerTeam â˜ƒ = this.getPlayersTeam(â˜ƒ);
      if (â˜ƒ != null) {
         this.removePlayerFromTeam(â˜ƒ, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   public void removePlayerFromTeam(String var1, PlayerTeam var2) {
      if (this.getPlayersTeam(â˜ƒ) != â˜ƒ) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + â˜ƒ.getName() + "'.");
      } else {
         this.teamsByPlayer.remove(â˜ƒ);
         â˜ƒ.getPlayers().remove(â˜ƒ);
      }
   }

   public Collection<String> getTeamNames() {
      return this.teamsByName.keySet();
   }

   public Collection<PlayerTeam> getPlayerTeams() {
      return this.teamsByName.values();
   }

   @Nullable
   public PlayerTeam getPlayersTeam(String var1) {
      return (PlayerTeam)this.teamsByPlayer.get(â˜ƒ);
   }

   public void onObjectiveAdded(Objective var1) {
   }

   public void onObjectiveChanged(Objective var1) {
   }

   public void onObjectiveRemoved(Objective var1) {
   }

   public void onScoreChanged(Score var1) {
   }

   public void onPlayerRemoved(String var1) {
   }

   public void onPlayerScoreRemoved(String var1, Objective var2) {
   }

   public void onTeamAdded(PlayerTeam var1) {
   }

   public void onTeamChanged(PlayerTeam var1) {
   }

   public void onTeamRemoved(PlayerTeam var1) {
   }

   public static String getDisplaySlotName(int var0) {
      switch(â˜ƒ) {
         case 0:
            return "list";
         case 1:
            return "sidebar";
         case 2:
            return "belowName";
         default:
            if (â˜ƒ >= 3 && â˜ƒ <= 18) {
               ChatFormatting â˜ƒ = ChatFormatting.getById(â˜ƒ - 3);
               if (â˜ƒ != null && â˜ƒ != ChatFormatting.RESET) {
                  return "sidebar.team." + â˜ƒ.getName();
               }
            }

            return null;
      }
   }

   public static int getDisplaySlotByName(String var0) {
      if ("list".equalsIgnoreCase(â˜ƒ)) {
         return 0;
      } else if ("sidebar".equalsIgnoreCase(â˜ƒ)) {
         return 1;
      } else if ("belowName".equalsIgnoreCase(â˜ƒ)) {
         return 2;
      } else {
         if (â˜ƒ.startsWith("sidebar.team.")) {
            String â˜ƒ = â˜ƒ.substring("sidebar.team.".length());
            ChatFormatting â˜ƒx = ChatFormatting.getByName(â˜ƒ);
            if (â˜ƒx != null && â˜ƒx.getId() >= 0) {
               return â˜ƒx.getId() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] getDisplaySlotNames() {
      if (displaySlotNames == null) {
         displaySlotNames = new String[19];

         for(int â˜ƒ = 0; â˜ƒ < 19; ++â˜ƒ) {
            displaySlotNames[â˜ƒ] = getDisplaySlotName(â˜ƒ);
         }
      }

      return displaySlotNames;
   }

   public void entityRemoved(Entity var1) {
      if (â˜ƒ != null && !(â˜ƒ instanceof Player) && !â˜ƒ.isAlive()) {
         String â˜ƒ = â˜ƒ.getStringUUID();
         this.resetPlayerScore(â˜ƒ, null);
         this.removePlayerFromTeam(â˜ƒ);
      }
   }

   protected ListTag savePlayerScores() {
      ListTag â˜ƒ = new ListTag();
      this.playerScores.values().stream().map(Map::values).forEach(var1x -> var1x.stream().filter(var0x -> var0x.getObjective() != null).forEach(var1xx -> {
            CompoundTag â˜ƒ = new CompoundTag();
            â˜ƒ.putString("Name", var1xx.getOwner());
            â˜ƒ.putString("Objective", var1xx.getObjective().getName());
            â˜ƒ.putInt("Score", var1xx.getScore());
            â˜ƒ.putBoolean("Locked", var1xx.isLocked());
            â˜ƒ.add(â˜ƒ);
         }));
      return â˜ƒ;
   }

   protected void loadPlayerScores(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         Objective â˜ƒxx = this.getOrCreateObjective(â˜ƒx.getString("Objective"));
         String â˜ƒxxx = â˜ƒx.getString("Name");
         if (â˜ƒxxx.length() > 40) {
            â˜ƒxxx = â˜ƒxxx.substring(0, 40);
         }

         Score â˜ƒx = this.getOrCreatePlayerScore(â˜ƒxxx, â˜ƒxx);
         â˜ƒx.setScore(â˜ƒx.getInt("Score"));
         if (â˜ƒx.contains("Locked")) {
            â˜ƒx.setLocked(â˜ƒx.getBoolean("Locked"));
         }
      }
   }
}
