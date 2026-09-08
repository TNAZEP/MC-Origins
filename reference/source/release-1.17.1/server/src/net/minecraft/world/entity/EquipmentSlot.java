package net.minecraft.world.entity;

public enum EquipmentSlot {
   MAINHAND(EquipmentSlot.Type.HAND, 0, 0, "mainhand"),
   OFFHAND(EquipmentSlot.Type.HAND, 1, 5, "offhand"),
   FEET(EquipmentSlot.Type.ARMOR, 0, 1, "feet"),
   LEGS(EquipmentSlot.Type.ARMOR, 1, 2, "legs"),
   CHEST(EquipmentSlot.Type.ARMOR, 2, 3, "chest"),
   HEAD(EquipmentSlot.Type.ARMOR, 3, 4, "head");

   private final EquipmentSlot.Type type;
   private final int index;
   private final int filterFlag;
   private final String name;

   private EquipmentSlot(EquipmentSlot.Type var3, int var4, int var5, String var6) {
      this.type = â˜ƒ;
      this.index = â˜ƒ;
      this.filterFlag = â˜ƒ;
      this.name = â˜ƒ;
   }

   public EquipmentSlot.Type getType() {
      return this.type;
   }

   public int getIndex() {
      return this.index;
   }

   public int getIndex(int var1) {
      return â˜ƒ + this.index;
   }

   public int getFilterFlag() {
      return this.filterFlag;
   }

   public String getName() {
      return this.name;
   }

   public static EquipmentSlot byName(String var0) {
      for(EquipmentSlot â˜ƒ : values()) {
         if (â˜ƒ.getName().equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + â˜ƒ + "'");
   }

   public static EquipmentSlot byTypeAndIndex(EquipmentSlot.Type var0, int var1) {
      for(EquipmentSlot â˜ƒ : values()) {
         if (â˜ƒ.getType() == â˜ƒ && â˜ƒ.getIndex() == â˜ƒ) {
            return â˜ƒ;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + â˜ƒ + "': " + â˜ƒ);
   }

   public static enum Type {
      HAND,
      ARMOR;
   }
}
