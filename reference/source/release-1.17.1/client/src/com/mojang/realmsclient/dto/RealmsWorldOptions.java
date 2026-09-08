package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.resources.language.I18n;

public class RealmsWorldOptions extends ValueObject {
   public final boolean pvp;
   public final boolean spawnAnimals;
   public final boolean spawnMonsters;
   public final boolean spawnNPCs;
   public final int spawnProtection;
   public final boolean commandBlocks;
   public final boolean forceGameMode;
   public final int difficulty;
   public final int gameMode;
   @Nullable
   private final String slotName;
   public long templateId;
   @Nullable
   public String templateImage;
   public boolean empty;
   private static final boolean DEFAULT_FORCE_GAME_MODE = false;
   private static final boolean DEFAULT_PVP = true;
   private static final boolean DEFAULT_SPAWN_ANIMALS = true;
   private static final boolean DEFAULT_SPAWN_MONSTERS = true;
   private static final boolean DEFAULT_SPAWN_NPCS = true;
   private static final int DEFAULT_SPAWN_PROTECTION = 0;
   private static final boolean DEFAULT_COMMAND_BLOCKS = false;
   private static final int DEFAULT_DIFFICULTY = 2;
   private static final int DEFAULT_GAME_MODE = 0;
   private static final String DEFAULT_SLOT_NAME = "";
   private static final long DEFAULT_TEMPLATE_ID = -1L;
   private static final String DEFAULT_TEMPLATE_IMAGE = null;

   public RealmsWorldOptions(
      boolean var1, boolean var2, boolean var3, boolean var4, int var5, boolean var6, int var7, int var8, boolean var9, @Nullable String var10
   ) {
      this.pvp = â˜ƒ;
      this.spawnAnimals = â˜ƒ;
      this.spawnMonsters = â˜ƒ;
      this.spawnNPCs = â˜ƒ;
      this.spawnProtection = â˜ƒ;
      this.commandBlocks = â˜ƒ;
      this.difficulty = â˜ƒ;
      this.gameMode = â˜ƒ;
      this.forceGameMode = â˜ƒ;
      this.slotName = â˜ƒ;
   }

   public static RealmsWorldOptions createDefaults() {
      return new RealmsWorldOptions(true, true, true, true, 0, false, 2, 0, false, "");
   }

   public static RealmsWorldOptions createEmptyDefaults() {
      RealmsWorldOptions â˜ƒ = createDefaults();
      â˜ƒ.setEmpty(true);
      return â˜ƒ;
   }

   public void setEmpty(boolean var1) {
      this.empty = â˜ƒ;
   }

   public static RealmsWorldOptions parse(JsonObject var0) {
      RealmsWorldOptions â˜ƒ = new RealmsWorldOptions(
         JsonUtils.getBooleanOr("pvp", â˜ƒ, true),
         JsonUtils.getBooleanOr("spawnAnimals", â˜ƒ, true),
         JsonUtils.getBooleanOr("spawnMonsters", â˜ƒ, true),
         JsonUtils.getBooleanOr("spawnNPCs", â˜ƒ, true),
         JsonUtils.getIntOr("spawnProtection", â˜ƒ, 0),
         JsonUtils.getBooleanOr("commandBlocks", â˜ƒ, false),
         JsonUtils.getIntOr("difficulty", â˜ƒ, 2),
         JsonUtils.getIntOr("gameMode", â˜ƒ, 0),
         JsonUtils.getBooleanOr("forceGameMode", â˜ƒ, false),
         JsonUtils.getStringOr("slotName", â˜ƒ, "")
      );
      â˜ƒ.templateId = JsonUtils.getLongOr("worldTemplateId", â˜ƒ, -1L);
      â˜ƒ.templateImage = JsonUtils.getStringOr("worldTemplateImage", â˜ƒ, DEFAULT_TEMPLATE_IMAGE);
      return â˜ƒ;
   }

   public String getSlotName(int var1) {
      if (this.slotName != null && !this.slotName.isEmpty()) {
         return this.slotName;
      } else {
         return this.empty ? I18n.get("mco.configure.world.slot.empty") : this.getDefaultSlotName(â˜ƒ);
      }
   }

   public String getDefaultSlotName(int var1) {
      return I18n.get("mco.configure.world.slot", â˜ƒ);
   }

   public String toJson() {
      JsonObject â˜ƒ = new JsonObject();
      if (!this.pvp) {
         â˜ƒ.addProperty("pvp", this.pvp);
      }

      if (!this.spawnAnimals) {
         â˜ƒ.addProperty("spawnAnimals", this.spawnAnimals);
      }

      if (!this.spawnMonsters) {
         â˜ƒ.addProperty("spawnMonsters", this.spawnMonsters);
      }

      if (!this.spawnNPCs) {
         â˜ƒ.addProperty("spawnNPCs", this.spawnNPCs);
      }

      if (this.spawnProtection != 0) {
         â˜ƒ.addProperty("spawnProtection", this.spawnProtection);
      }

      if (this.commandBlocks) {
         â˜ƒ.addProperty("commandBlocks", this.commandBlocks);
      }

      if (this.difficulty != 2) {
         â˜ƒ.addProperty("difficulty", this.difficulty);
      }

      if (this.gameMode != 0) {
         â˜ƒ.addProperty("gameMode", this.gameMode);
      }

      if (this.forceGameMode) {
         â˜ƒ.addProperty("forceGameMode", this.forceGameMode);
      }

      if (!Objects.equals(this.slotName, "")) {
         â˜ƒ.addProperty("slotName", this.slotName);
      }

      return â˜ƒ.toString();
   }

   public RealmsWorldOptions clone() {
      return new RealmsWorldOptions(
         this.pvp,
         this.spawnAnimals,
         this.spawnMonsters,
         this.spawnNPCs,
         this.spawnProtection,
         this.commandBlocks,
         this.difficulty,
         this.gameMode,
         this.forceGameMode,
         this.slotName
      );
   }
}
