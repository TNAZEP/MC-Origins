package net.minecraft.inventory;

public enum EntityEquipmentSlot {
   MAINHAND(EntityEquipmentSlot.Type.HAND, 0, 0, "mainhand"),
   OFFHAND(EntityEquipmentSlot.Type.HAND, 1, 5, "offhand"),
   FEET(EntityEquipmentSlot.Type.ARMOR, 0, 1, "feet"),
   LEGS(EntityEquipmentSlot.Type.ARMOR, 1, 2, "legs"),
   CHEST(EntityEquipmentSlot.Type.ARMOR, 2, 3, "chest"),
   HEAD(EntityEquipmentSlot.Type.ARMOR, 3, 4, "head");

   private final EntityEquipmentSlot.Type field_188462_g;
   private final int field_188463_h;
   private final int field_188464_i;
   private final String field_188465_j;

   private EntityEquipmentSlot(EntityEquipmentSlot.Type var3, int var4, int var5, String var6) {
      this.field_188462_g = ☃;
      this.field_188463_h = ☃;
      this.field_188464_i = ☃;
      this.field_188465_j = ☃;
   }

   public EntityEquipmentSlot.Type func_188453_a() {
      return this.field_188462_g;
   }

   public int func_188454_b() {
      return this.field_188463_h;
   }

   public int func_188452_c() {
      return this.field_188464_i;
   }

   public String func_188450_d() {
      return this.field_188465_j;
   }

   public static EntityEquipmentSlot func_188451_a(String var0) {
      for(EntityEquipmentSlot ☃ : values()) {
         if (☃.func_188450_d().equals(☃)) {
            return ☃;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + ☃ + "'");
   }

   public static enum Type {
      HAND,
      ARMOR;
   }
}
