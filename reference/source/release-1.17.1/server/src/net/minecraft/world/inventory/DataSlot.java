package net.minecraft.world.inventory;

public abstract class DataSlot {
   private int prevValue;

   public static DataSlot forContainer(final ContainerData var0, final int var1) {
      return new DataSlot() {
         @Override
         public int get() {
            return â˜ƒ.get(â˜ƒ);
         }

         @Override
         public void set(int var1x) {
            â˜ƒ.set(â˜ƒ, â˜ƒ);
         }
      };
   }

   public static DataSlot shared(final int[] var0, final int var1) {
      return new DataSlot() {
         @Override
         public int get() {
            return â˜ƒ[â˜ƒ];
         }

         @Override
         public void set(int var1x) {
            â˜ƒ[â˜ƒ] = â˜ƒ;
         }
      };
   }

   public static DataSlot standalone() {
      return new DataSlot() {
         private int value;

         @Override
         public int get() {
            return this.value;
         }

         @Override
         public void set(int var1) {
            this.value = â˜ƒ;
         }
      };
   }

   public abstract int get();

   public abstract void set(int var1);

   public boolean checkAndClearUpdateFlag() {
      int â˜ƒ = this.get();
      boolean â˜ƒx = â˜ƒ != this.prevValue;
      this.prevValue = â˜ƒ;
      return â˜ƒx;
   }
}
