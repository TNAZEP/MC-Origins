package net.minecraft.world.level.storage;

import com.mojang.bridge.game.GameVersion;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import org.apache.commons.lang3.StringUtils;

public class LevelSummary implements Comparable<LevelSummary> {
   private final LevelSettings settings;
   private final LevelVersion levelVersion;
   private final String levelId;
   private final boolean requiresConversion;
   private final boolean locked;
   private final File icon;
   @Nullable
   private Component info;

   public LevelSummary(LevelSettings var1, LevelVersion var2, String var3, boolean var4, boolean var5, File var6) {
      this.settings = â˜ƒ;
      this.levelVersion = â˜ƒ;
      this.levelId = â˜ƒ;
      this.locked = â˜ƒ;
      this.icon = â˜ƒ;
      this.requiresConversion = â˜ƒ;
   }

   public String getLevelId() {
      return this.levelId;
   }

   public String getLevelName() {
      return StringUtils.isEmpty(this.settings.levelName()) ? this.levelId : this.settings.levelName();
   }

   public File getIcon() {
      return this.icon;
   }

   public boolean isRequiresConversion() {
      return this.requiresConversion;
   }

   public long getLastPlayed() {
      return this.levelVersion.lastPlayed();
   }

   public int compareTo(LevelSummary var1) {
      if (this.levelVersion.lastPlayed() < â˜ƒ.levelVersion.lastPlayed()) {
         return 1;
      } else {
         return this.levelVersion.lastPlayed() > â˜ƒ.levelVersion.lastPlayed() ? -1 : this.levelId.compareTo(â˜ƒ.levelId);
      }
   }

   public LevelSettings getSettings() {
      return this.settings;
   }

   public GameType getGameMode() {
      return this.settings.gameType();
   }

   public boolean isHardcore() {
      return this.settings.hardcore();
   }

   public boolean hasCheats() {
      return this.settings.allowCommands();
   }

   public MutableComponent getWorldVersionName() {
      return (MutableComponent)(StringUtil.isNullOrEmpty(this.levelVersion.minecraftVersionName())
         ? new TranslatableComponent("selectWorld.versionUnknown")
         : new TextComponent(this.levelVersion.minecraftVersionName()));
   }

   public LevelVersion levelVersion() {
      return this.levelVersion;
   }

   public boolean markVersionInList() {
      return this.askToOpenWorld() || !SharedConstants.getCurrentVersion().isStable() && !this.levelVersion.snapshot() || this.backupStatus().shouldBackup();
   }

   public boolean askToOpenWorld() {
      return this.levelVersion.minecraftVersion() > SharedConstants.getCurrentVersion().getWorldVersion();
   }

   public LevelSummary.BackupStatus backupStatus() {
      GameVersion â˜ƒ = SharedConstants.getCurrentVersion();
      int â˜ƒx = â˜ƒ.getWorldVersion();
      int â˜ƒxx = this.levelVersion.minecraftVersion();
      if (!â˜ƒ.isStable() && â˜ƒxx < â˜ƒx) {
         return LevelSummary.BackupStatus.UPGRADE_TO_SNAPSHOT;
      } else {
         return â˜ƒxx > â˜ƒx ? LevelSummary.BackupStatus.DOWNGRADE : LevelSummary.BackupStatus.NONE;
      }
   }

   public boolean isLocked() {
      return this.locked;
   }

   public boolean isIncompatibleWorldHeight() {
      int â˜ƒ = this.levelVersion.minecraftVersion();
      boolean â˜ƒx = â˜ƒ > 2692 && â˜ƒ <= 2706;
      return â˜ƒx;
   }

   public boolean isDisabled() {
      return this.isLocked() || this.isIncompatibleWorldHeight();
   }

   public Component getInfo() {
      if (this.info == null) {
         this.info = this.createInfo();
      }

      return this.info;
   }

   private Component createInfo() {
      if (this.isLocked()) {
         return new TranslatableComponent("selectWorld.locked").withStyle(ChatFormatting.RED);
      } else if (this.isIncompatibleWorldHeight()) {
         return new TranslatableComponent("selectWorld.pre_worldheight").withStyle(ChatFormatting.RED);
      } else if (this.isRequiresConversion()) {
         return new TranslatableComponent("selectWorld.conversion");
      } else {
         MutableComponent â˜ƒ = (MutableComponent)(this.isHardcore()
            ? new TextComponent("").append(new TranslatableComponent("gameMode.hardcore").withStyle(ChatFormatting.DARK_RED))
            : new TranslatableComponent("gameMode." + this.getGameMode().getName()));
         if (this.hasCheats()) {
            â˜ƒ.append(", ").append(new TranslatableComponent("selectWorld.cheats"));
         }

         MutableComponent â˜ƒ = this.getWorldVersionName();
         MutableComponent â˜ƒx = new TextComponent(", ").append(new TranslatableComponent("selectWorld.version")).append(" ");
         if (this.markVersionInList()) {
            â˜ƒx.append(â˜ƒ.withStyle(this.askToOpenWorld() ? ChatFormatting.RED : ChatFormatting.ITALIC));
         } else {
            â˜ƒx.append(â˜ƒ);
         }

         â˜ƒ.append(â˜ƒx);
         return â˜ƒ;
      }
   }

   public static enum BackupStatus {
      NONE(false, false, ""),
      DOWNGRADE(true, true, "downgrade"),
      UPGRADE_TO_SNAPSHOT(true, false, "snapshot");

      private final boolean shouldBackup;
      private final boolean severe;
      private final String translationKey;

      private BackupStatus(boolean var3, boolean var4, String var5) {
         this.shouldBackup = â˜ƒ;
         this.severe = â˜ƒ;
         this.translationKey = â˜ƒ;
      }

      public boolean shouldBackup() {
         return this.shouldBackup;
      }

      public boolean isSevere() {
         return this.severe;
      }

      public String getTranslationKey() {
         return this.translationKey;
      }
   }
}
