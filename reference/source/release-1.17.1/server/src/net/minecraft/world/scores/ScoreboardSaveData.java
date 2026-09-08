package net.minecraft.world.scores;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

public class ScoreboardSaveData extends SavedData {
   public static final String FILE_ID = "scoreboard";
   private final Scoreboard scoreboard;

   public ScoreboardSaveData(Scoreboard var1) {
      this.scoreboard = â˜ƒ;
   }

   public ScoreboardSaveData load(CompoundTag var1) {
      this.loadObjectives(â˜ƒ.getList("Objectives", 10));
      this.scoreboard.loadPlayerScores(â˜ƒ.getList("PlayerScores", 10));
      if (â˜ƒ.contains("DisplaySlots", 10)) {
         this.loadDisplaySlots(â˜ƒ.getCompound("DisplaySlots"));
      }

      if (â˜ƒ.contains("Teams", 9)) {
         this.loadTeams(â˜ƒ.getList("Teams", 10));
      }

      return this;
   }

   private void loadTeams(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         String â˜ƒxx = â˜ƒx.getString("Name");
         if (â˜ƒxx.length() > 16) {
            â˜ƒxx = â˜ƒxx.substring(0, 16);
         }

         PlayerTeam â˜ƒx = this.scoreboard.addPlayerTeam(â˜ƒxx);
         Component â˜ƒxx = Component.Serializer.fromJson(â˜ƒx.getString("DisplayName"));
         if (â˜ƒxx != null) {
            â˜ƒx.setDisplayName(â˜ƒxx);
         }

         if (â˜ƒx.contains("TeamColor", 8)) {
            â˜ƒx.setColor(ChatFormatting.getByName(â˜ƒx.getString("TeamColor")));
         }

         if (â˜ƒx.contains("AllowFriendlyFire", 99)) {
            â˜ƒx.setAllowFriendlyFire(â˜ƒx.getBoolean("AllowFriendlyFire"));
         }

         if (â˜ƒx.contains("SeeFriendlyInvisibles", 99)) {
            â˜ƒx.setSeeFriendlyInvisibles(â˜ƒx.getBoolean("SeeFriendlyInvisibles"));
         }

         if (â˜ƒx.contains("MemberNamePrefix", 8)) {
            Component â˜ƒx = Component.Serializer.fromJson(â˜ƒx.getString("MemberNamePrefix"));
            if (â˜ƒx != null) {
               â˜ƒx.setPlayerPrefix(â˜ƒx);
            }
         }

         if (â˜ƒx.contains("MemberNameSuffix", 8)) {
            Component â˜ƒx = Component.Serializer.fromJson(â˜ƒx.getString("MemberNameSuffix"));
            if (â˜ƒx != null) {
               â˜ƒx.setPlayerSuffix(â˜ƒx);
            }
         }

         if (â˜ƒx.contains("NameTagVisibility", 8)) {
            Team.Visibility â˜ƒx = Team.Visibility.byName(â˜ƒx.getString("NameTagVisibility"));
            if (â˜ƒx != null) {
               â˜ƒx.setNameTagVisibility(â˜ƒx);
            }
         }

         if (â˜ƒx.contains("DeathMessageVisibility", 8)) {
            Team.Visibility â˜ƒx = Team.Visibility.byName(â˜ƒx.getString("DeathMessageVisibility"));
            if (â˜ƒx != null) {
               â˜ƒx.setDeathMessageVisibility(â˜ƒx);
            }
         }

         if (â˜ƒx.contains("CollisionRule", 8)) {
            Team.CollisionRule â˜ƒx = Team.CollisionRule.byName(â˜ƒx.getString("CollisionRule"));
            if (â˜ƒx != null) {
               â˜ƒx.setCollisionRule(â˜ƒx);
            }
         }

         this.loadTeamPlayers(â˜ƒx, â˜ƒx.getList("Players", 8));
      }
   }

   private void loadTeamPlayers(PlayerTeam var1, ListTag var2) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         this.scoreboard.addPlayerToTeam(â˜ƒ.getString(â˜ƒ), â˜ƒ);
      }
   }

   private void loadDisplaySlots(CompoundTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < 19; ++â˜ƒ) {
         if (â˜ƒ.contains("slot_" + â˜ƒ, 8)) {
            String â˜ƒx = â˜ƒ.getString("slot_" + â˜ƒ);
            Objective â˜ƒxx = this.scoreboard.getObjective(â˜ƒx);
            this.scoreboard.setDisplayObjective(â˜ƒ, â˜ƒxx);
         }
      }
   }

   private void loadObjectives(ListTag var1) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound(â˜ƒ);
         ObjectiveCriteria.byName(â˜ƒx.getString("CriteriaName")).ifPresent(var2x -> {
            String â˜ƒ = â˜ƒ.getString("Name");
            if (â˜ƒ.length() > 16) {
               â˜ƒ = â˜ƒ.substring(0, 16);
            }

            Component â˜ƒ = Component.Serializer.fromJson(â˜ƒ.getString("DisplayName"));
            ObjectiveCriteria.RenderType â˜ƒx = ObjectiveCriteria.RenderType.byId(â˜ƒ.getString("RenderType"));
            this.scoreboard.addObjective(â˜ƒ, var2x, â˜ƒ, â˜ƒx);
         });
      }
   }

   @Override
   public CompoundTag save(CompoundTag var1) {
      â˜ƒ.put("Objectives", this.saveObjectives());
      â˜ƒ.put("PlayerScores", this.scoreboard.savePlayerScores());
      â˜ƒ.put("Teams", this.saveTeams());
      this.saveDisplaySlots(â˜ƒ);
      return â˜ƒ;
   }

   private ListTag saveTeams() {
      ListTag â˜ƒ = new ListTag();

      for(PlayerTeam â˜ƒx : this.scoreboard.getPlayerTeams()) {
         CompoundTag â˜ƒxx = new CompoundTag();
         â˜ƒxx.putString("Name", â˜ƒx.getName());
         â˜ƒxx.putString("DisplayName", Component.Serializer.toJson(â˜ƒx.getDisplayName()));
         if (â˜ƒx.getColor().getId() >= 0) {
            â˜ƒxx.putString("TeamColor", â˜ƒx.getColor().getName());
         }

         â˜ƒxx.putBoolean("AllowFriendlyFire", â˜ƒx.isAllowFriendlyFire());
         â˜ƒxx.putBoolean("SeeFriendlyInvisibles", â˜ƒx.canSeeFriendlyInvisibles());
         â˜ƒxx.putString("MemberNamePrefix", Component.Serializer.toJson(â˜ƒx.getPlayerPrefix()));
         â˜ƒxx.putString("MemberNameSuffix", Component.Serializer.toJson(â˜ƒx.getPlayerSuffix()));
         â˜ƒxx.putString("NameTagVisibility", â˜ƒx.getNameTagVisibility().name);
         â˜ƒxx.putString("DeathMessageVisibility", â˜ƒx.getDeathMessageVisibility().name);
         â˜ƒxx.putString("CollisionRule", â˜ƒx.getCollisionRule().name);
         ListTag â˜ƒxx = new ListTag();

         for(String â˜ƒxxx : â˜ƒx.getPlayers()) {
            â˜ƒxx.add(StringTag.valueOf(â˜ƒxxx));
         }

         â˜ƒxx.put("Players", â˜ƒxx);
         â˜ƒ.add(â˜ƒxx);
      }

      return â˜ƒ;
   }

   private void saveDisplaySlots(CompoundTag var1) {
      CompoundTag â˜ƒ = new CompoundTag();
      boolean â˜ƒx = false;

      for(int â˜ƒxx = 0; â˜ƒxx < 19; ++â˜ƒxx) {
         Objective â˜ƒxxx = this.scoreboard.getDisplayObjective(â˜ƒxx);
         if (â˜ƒxxx != null) {
            â˜ƒ.putString("slot_" + â˜ƒxx, â˜ƒxxx.getName());
            â˜ƒx = true;
         }
      }

      if (â˜ƒx) {
         â˜ƒ.put("DisplaySlots", â˜ƒ);
      }
   }

   private ListTag saveObjectives() {
      ListTag â˜ƒ = new ListTag();

      for(Objective â˜ƒx : this.scoreboard.getObjectives()) {
         if (â˜ƒx.getCriteria() != null) {
            CompoundTag â˜ƒxx = new CompoundTag();
            â˜ƒxx.putString("Name", â˜ƒx.getName());
            â˜ƒxx.putString("CriteriaName", â˜ƒx.getCriteria().getName());
            â˜ƒxx.putString("DisplayName", Component.Serializer.toJson(â˜ƒx.getDisplayName()));
            â˜ƒxx.putString("RenderType", â˜ƒx.getRenderType().getId());
            â˜ƒ.add(â˜ƒxx);
         }
      }

      return â˜ƒ;
   }
}
