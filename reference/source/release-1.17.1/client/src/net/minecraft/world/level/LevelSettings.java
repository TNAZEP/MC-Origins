package net.minecraft.world.level;

import com.mojang.serialization.Dynamic;
import net.minecraft.world.Difficulty;

public final class LevelSettings {
   private final String levelName;
   private final GameType gameType;
   private final boolean hardcore;
   private final Difficulty difficulty;
   private final boolean allowCommands;
   private final GameRules gameRules;
   private final DataPackConfig dataPackConfig;

   public LevelSettings(String var1, GameType var2, boolean var3, Difficulty var4, boolean var5, GameRules var6, DataPackConfig var7) {
      this.levelName = â˜ƒ;
      this.gameType = â˜ƒ;
      this.hardcore = â˜ƒ;
      this.difficulty = â˜ƒ;
      this.allowCommands = â˜ƒ;
      this.gameRules = â˜ƒ;
      this.dataPackConfig = â˜ƒ;
   }

   public static LevelSettings parse(Dynamic<?> var0, DataPackConfig var1) {
      GameType â˜ƒ = GameType.byId(â˜ƒ.get("GameType").asInt(0));
      return new LevelSettings(
         â˜ƒ.get("LevelName").asString(""),
         â˜ƒ,
         â˜ƒ.get("hardcore").asBoolean(false),
         (Difficulty)â˜ƒ.get("Difficulty").asNumber().map(var0x -> Difficulty.byId(var0x.byteValue())).result().orElse(Difficulty.NORMAL),
         â˜ƒ.get("allowCommands").asBoolean(â˜ƒ == GameType.CREATIVE),
         new GameRules(â˜ƒ.get("GameRules")),
         â˜ƒ
      );
   }

   public String levelName() {
      return this.levelName;
   }

   public GameType gameType() {
      return this.gameType;
   }

   public boolean hardcore() {
      return this.hardcore;
   }

   public Difficulty difficulty() {
      return this.difficulty;
   }

   public boolean allowCommands() {
      return this.allowCommands;
   }

   public GameRules gameRules() {
      return this.gameRules;
   }

   public DataPackConfig getDataPackConfig() {
      return this.dataPackConfig;
   }

   public LevelSettings withGameType(GameType var1) {
      return new LevelSettings(this.levelName, â˜ƒ, this.hardcore, this.difficulty, this.allowCommands, this.gameRules, this.dataPackConfig);
   }

   public LevelSettings withDifficulty(Difficulty var1) {
      return new LevelSettings(this.levelName, this.gameType, this.hardcore, â˜ƒ, this.allowCommands, this.gameRules, this.dataPackConfig);
   }

   public LevelSettings withDataPackConfig(DataPackConfig var1) {
      return new LevelSettings(this.levelName, this.gameType, this.hardcore, this.difficulty, this.allowCommands, this.gameRules, â˜ƒ);
   }

   public LevelSettings copy() {
      return new LevelSettings(this.levelName, this.gameType, this.hardcore, this.difficulty, this.allowCommands, this.gameRules.copy(), this.dataPackConfig);
   }
}
