package net.minecraft.world.level;

public enum TickPriority {
   EXTREMELY_HIGH(-3),
   VERY_HIGH(-2),
   HIGH(-1),
   NORMAL(0),
   LOW(1),
   VERY_LOW(2),
   EXTREMELY_LOW(3);

   private final int value;

   private TickPriority(int var3) {
      this.value = â˜ƒ;
   }

   public static TickPriority byValue(int var0) {
      for(TickPriority â˜ƒ : values()) {
         if (â˜ƒ.value == â˜ƒ) {
            return â˜ƒ;
         }
      }

      return â˜ƒ < EXTREMELY_HIGH.value ? EXTREMELY_HIGH : EXTREMELY_LOW;
   }

   public int getValue() {
      return this.value;
   }
}
