package net.minecraft.world;

public enum TickPriority {
   EXTREMELY_HIGH(-3),
   VERY_HIGH(-2),
   HIGH(-1),
   NORMAL(0),
   LOW(1),
   VERY_LOW(2),
   EXTREMELY_LOW(3);

   private final int field_205399_h;

   private TickPriority(int var3) {
      this.field_205399_h = ☃;
   }

   public static TickPriority func_205397_a(int var0) {
      for(TickPriority ☃ : values()) {
         if (☃.field_205399_h == ☃) {
            return ☃;
         }
      }

      return ☃ < EXTREMELY_HIGH.field_205399_h ? EXTREMELY_HIGH : EXTREMELY_LOW;
   }

   public int func_205398_a() {
      return this.field_205399_h;
   }
}
