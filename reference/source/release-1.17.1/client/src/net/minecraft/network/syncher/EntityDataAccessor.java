package net.minecraft.network.syncher;

public class EntityDataAccessor<T> {
   private final int id;
   private final EntityDataSerializer<T> serializer;

   public EntityDataAccessor(int var1, EntityDataSerializer<T> var2) {
      this.id = â˜ƒ;
      this.serializer = â˜ƒ;
   }

   public int getId() {
      return this.id;
   }

   public EntityDataSerializer<T> getSerializer() {
      return this.serializer;
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         EntityDataAccessor<?> â˜ƒ = (EntityDataAccessor)â˜ƒ;
         return this.id == â˜ƒ.id;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id;
   }

   public String toString() {
      return "<entity data: " + this.id + ">";
   }
}
