package net.minecraft.world.entity.ai.attributes;

public class Attribute {
   public static final int MAX_NAME_LENGTH = 64;
   private final double defaultValue;
   private boolean syncable;
   private final String descriptionId;

   protected Attribute(String var1, double var2) {
      this.defaultValue = â˜ƒ;
      this.descriptionId = â˜ƒ;
   }

   public double getDefaultValue() {
      return this.defaultValue;
   }

   public boolean isClientSyncable() {
      return this.syncable;
   }

   public Attribute setSyncable(boolean var1) {
      this.syncable = â˜ƒ;
      return this;
   }

   public double sanitizeValue(double var1) {
      return â˜ƒ;
   }

   public String getDescriptionId() {
      return this.descriptionId;
   }
}
