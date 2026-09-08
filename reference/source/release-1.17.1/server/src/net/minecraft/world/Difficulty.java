package net.minecraft.world;

import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum Difficulty {
   PEACEFUL(0, "peaceful"),
   EASY(1, "easy"),
   NORMAL(2, "normal"),
   HARD(3, "hard");

   private static final Difficulty[] BY_ID = (Difficulty[])Arrays.stream(values())
      .sorted(Comparator.comparingInt(Difficulty::getId))
      .toArray(var0 -> new Difficulty[var0]);
   private final int id;
   private final String key;

   private Difficulty(int var3, String var4) {
      this.id = â˜ƒ;
      this.key = â˜ƒ;
   }

   public int getId() {
      return this.id;
   }

   public Component getDisplayName() {
      return new TranslatableComponent("options.difficulty." + this.key);
   }

   public static Difficulty byId(int var0) {
      return BY_ID[â˜ƒ % BY_ID.length];
   }

   @Nullable
   public static Difficulty byName(String var0) {
      for(Difficulty â˜ƒ : values()) {
         if (â˜ƒ.key.equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      return null;
   }

   public String getKey() {
      return this.key;
   }
}
