package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Abilities;

public enum GameType {
   SURVIVAL(0, "survival"),
   CREATIVE(1, "creative"),
   ADVENTURE(2, "adventure"),
   SPECTATOR(3, "spectator");

   public static final GameType DEFAULT_MODE = SURVIVAL;
   private static final int NOT_SET = -1;
   private final int id;
   private final String name;
   private final Component shortName;
   private final Component longName;

   private GameType(int var3, String var4) {
      this.id = â˜ƒ;
      this.name = â˜ƒ;
      this.shortName = new TranslatableComponent("selectWorld.gameMode." + â˜ƒ);
      this.longName = new TranslatableComponent("gameMode." + â˜ƒ);
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public Component getLongDisplayName() {
      return this.longName;
   }

   public Component getShortDisplayName() {
      return this.shortName;
   }

   public void updatePlayerAbilities(Abilities var1) {
      if (this == CREATIVE) {
         â˜ƒ.mayfly = true;
         â˜ƒ.instabuild = true;
         â˜ƒ.invulnerable = true;
      } else if (this == SPECTATOR) {
         â˜ƒ.mayfly = true;
         â˜ƒ.instabuild = false;
         â˜ƒ.invulnerable = true;
         â˜ƒ.flying = true;
      } else {
         â˜ƒ.mayfly = false;
         â˜ƒ.instabuild = false;
         â˜ƒ.invulnerable = false;
         â˜ƒ.flying = false;
      }

      â˜ƒ.mayBuild = !this.isBlockPlacingRestricted();
   }

   public boolean isBlockPlacingRestricted() {
      return this == ADVENTURE || this == SPECTATOR;
   }

   public boolean isCreative() {
      return this == CREATIVE;
   }

   public boolean isSurvival() {
      return this == SURVIVAL || this == ADVENTURE;
   }

   public static GameType byId(int var0) {
      return byId(â˜ƒ, DEFAULT_MODE);
   }

   public static GameType byId(int var0, GameType var1) {
      for(GameType â˜ƒ : values()) {
         if (â˜ƒ.id == â˜ƒ) {
            return â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   public static GameType byName(String var0) {
      return byName(â˜ƒ, SURVIVAL);
   }

   public static GameType byName(String var0, GameType var1) {
      for(GameType â˜ƒ : values()) {
         if (â˜ƒ.name.equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   public static int getNullableId(@Nullable GameType var0) {
      return â˜ƒ != null ? â˜ƒ.id : -1;
   }

   @Nullable
   public static GameType byNullableId(int var0) {
      return â˜ƒ == -1 ? null : byId(â˜ƒ);
   }
}
