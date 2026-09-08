package net.minecraft.world.inventory;

public class SimpleContainerData implements ContainerData {
   private final int[] ints;

   public SimpleContainerData(int var1) {
      this.ints = new int[â˜ƒ];
   }

   @Override
   public int get(int var1) {
      return this.ints[â˜ƒ];
   }

   @Override
   public void set(int var1, int var2) {
      this.ints[â˜ƒ] = â˜ƒ;
   }

   @Override
   public int getCount() {
      return this.ints.length;
   }
}
