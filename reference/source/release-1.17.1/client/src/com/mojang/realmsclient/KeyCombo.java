package com.mojang.realmsclient;

import java.util.Arrays;

public class KeyCombo {
   private final char[] chars;
   private int matchIndex;
   private final Runnable onCompletion;

   public KeyCombo(char[] var1, Runnable var2) {
      this.onCompletion = â˜ƒ;
      if (â˜ƒ.length < 1) {
         throw new IllegalArgumentException("Must have at least one char");
      } else {
         this.chars = â˜ƒ;
      }
   }

   public KeyCombo(char[] var1) {
      this(â˜ƒ, () -> {
      });
   }

   public boolean keyPressed(char var1) {
      if (â˜ƒ == this.chars[this.matchIndex++]) {
         if (this.matchIndex == this.chars.length) {
            this.reset();
            this.onCompletion.run();
            return true;
         }
      } else {
         this.reset();
      }

      return false;
   }

   public void reset() {
      this.matchIndex = 0;
   }

   public String toString() {
      return "KeyCombo{chars=" + Arrays.toString(this.chars) + ", matchIndex=" + this.matchIndex + "}";
   }
}
