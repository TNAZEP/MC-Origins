package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public enum GameType {
   NOT_SET(-1, ""),
   SURVIVAL(0, "survival"),
   CREATIVE(1, "creative"),
   ADVENTURE(2, "adventure"),
   SPECTATOR(3, "spectator");

   private final int field_77154_e;
   private final String field_77151_f;

   private GameType(int var3, String var4) {
      this.field_77154_e = ☃;
      this.field_77151_f = ☃;
   }

   public int func_77148_a() {
      return this.field_77154_e;
   }

   public String func_77149_b() {
      return this.field_77151_f;
   }

   public ITextComponent func_196220_c() {
      return new TextComponentTranslation("gameMode." + this.field_77151_f);
   }

   public void func_77147_a(PlayerCapabilities var1) {
      if (this == CREATIVE) {
         ☃.field_75101_c = true;
         ☃.field_75098_d = true;
         ☃.field_75102_a = true;
      } else if (this == SPECTATOR) {
         ☃.field_75101_c = true;
         ☃.field_75098_d = false;
         ☃.field_75102_a = true;
         ☃.field_75100_b = true;
      } else {
         ☃.field_75101_c = false;
         ☃.field_75098_d = false;
         ☃.field_75102_a = false;
         ☃.field_75100_b = false;
      }

      ☃.field_75099_e = !this.func_82752_c();
   }

   public boolean func_82752_c() {
      return this == ADVENTURE || this == SPECTATOR;
   }

   public boolean func_77145_d() {
      return this == CREATIVE;
   }

   public boolean func_77144_e() {
      return this == SURVIVAL || this == ADVENTURE;
   }

   public static GameType func_77146_a(int var0) {
      return func_185329_a(☃, SURVIVAL);
   }

   public static GameType func_185329_a(int var0, GameType var1) {
      for(GameType ☃ : values()) {
         if (☃.field_77154_e == ☃) {
            return ☃;
         }
      }

      return ☃;
   }

   public static GameType func_77142_a(String var0) {
      return func_185328_a(☃, SURVIVAL);
   }

   public static GameType func_185328_a(String var0, GameType var1) {
      for(GameType ☃ : values()) {
         if (☃.field_77151_f.equals(☃)) {
            return ☃;
         }
      }

      return ☃;
   }
}
