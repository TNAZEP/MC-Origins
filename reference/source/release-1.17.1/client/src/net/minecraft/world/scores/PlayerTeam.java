package net.minecraft.world.scores;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;

public class PlayerTeam extends Team {
   public static final int MAX_NAME_LENGTH = 16;
   private static final int BIT_FRIENDLY_FIRE = 0;
   private static final int BIT_SEE_INVISIBLES = 1;
   private final Scoreboard scoreboard;
   private final String name;
   private final Set<String> players = Sets.newHashSet();
   private Component displayName;
   private Component playerPrefix = TextComponent.EMPTY;
   private Component playerSuffix = TextComponent.EMPTY;
   private boolean allowFriendlyFire = true;
   private boolean seeFriendlyInvisibles = true;
   private Team.Visibility nameTagVisibility = Team.Visibility.ALWAYS;
   private Team.Visibility deathMessageVisibility = Team.Visibility.ALWAYS;
   private ChatFormatting color = ChatFormatting.RESET;
   private Team.CollisionRule collisionRule = Team.CollisionRule.ALWAYS;
   private final Style displayNameStyle;

   public PlayerTeam(Scoreboard var1, String var2) {
      this.scoreboard = â˜ƒ;
      this.name = â˜ƒ;
      this.displayName = new TextComponent(â˜ƒ);
      this.displayNameStyle = Style.EMPTY.withInsertion(â˜ƒ).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent(â˜ƒ)));
   }

   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public Component getDisplayName() {
      return this.displayName;
   }

   public MutableComponent getFormattedDisplayName() {
      MutableComponent â˜ƒ = ComponentUtils.wrapInSquareBrackets(this.displayName.copy().withStyle(this.displayNameStyle));
      ChatFormatting â˜ƒx = this.getColor();
      if (â˜ƒx != ChatFormatting.RESET) {
         â˜ƒ.withStyle(â˜ƒx);
      }

      return â˜ƒ;
   }

   public void setDisplayName(Component var1) {
      if (â˜ƒ == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.displayName = â˜ƒ;
         this.scoreboard.onTeamChanged(this);
      }
   }

   public void setPlayerPrefix(@Nullable Component var1) {
      this.playerPrefix = â˜ƒ == null ? TextComponent.EMPTY : â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   public Component getPlayerPrefix() {
      return this.playerPrefix;
   }

   public void setPlayerSuffix(@Nullable Component var1) {
      this.playerSuffix = â˜ƒ == null ? TextComponent.EMPTY : â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   public Component getPlayerSuffix() {
      return this.playerSuffix;
   }

   @Override
   public Collection<String> getPlayers() {
      return this.players;
   }

   @Override
   public MutableComponent getFormattedName(Component var1) {
      MutableComponent â˜ƒ = new TextComponent("").append(this.playerPrefix).append(â˜ƒ).append(this.playerSuffix);
      ChatFormatting â˜ƒx = this.getColor();
      if (â˜ƒx != ChatFormatting.RESET) {
         â˜ƒ.withStyle(â˜ƒx);
      }

      return â˜ƒ;
   }

   public static MutableComponent formatNameForTeam(@Nullable Team var0, Component var1) {
      return â˜ƒ == null ? â˜ƒ.copy() : â˜ƒ.getFormattedName(â˜ƒ);
   }

   @Override
   public boolean isAllowFriendlyFire() {
      return this.allowFriendlyFire;
   }

   public void setAllowFriendlyFire(boolean var1) {
      this.allowFriendlyFire = â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   @Override
   public boolean canSeeFriendlyInvisibles() {
      return this.seeFriendlyInvisibles;
   }

   public void setSeeFriendlyInvisibles(boolean var1) {
      this.seeFriendlyInvisibles = â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   @Override
   public Team.Visibility getNameTagVisibility() {
      return this.nameTagVisibility;
   }

   @Override
   public Team.Visibility getDeathMessageVisibility() {
      return this.deathMessageVisibility;
   }

   public void setNameTagVisibility(Team.Visibility var1) {
      this.nameTagVisibility = â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   public void setDeathMessageVisibility(Team.Visibility var1) {
      this.deathMessageVisibility = â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   @Override
   public Team.CollisionRule getCollisionRule() {
      return this.collisionRule;
   }

   public void setCollisionRule(Team.CollisionRule var1) {
      this.collisionRule = â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   public int packOptions() {
      int â˜ƒ = 0;
      if (this.isAllowFriendlyFire()) {
         â˜ƒ |= 1;
      }

      if (this.canSeeFriendlyInvisibles()) {
         â˜ƒ |= 2;
      }

      return â˜ƒ;
   }

   public void unpackOptions(int var1) {
      this.setAllowFriendlyFire((â˜ƒ & 1) > 0);
      this.setSeeFriendlyInvisibles((â˜ƒ & 2) > 0);
   }

   public void setColor(ChatFormatting var1) {
      this.color = â˜ƒ;
      this.scoreboard.onTeamChanged(this);
   }

   @Override
   public ChatFormatting getColor() {
      return this.color;
   }
}
