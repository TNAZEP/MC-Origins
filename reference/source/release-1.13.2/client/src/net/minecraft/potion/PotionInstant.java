package net.minecraft.potion;

public class PotionInstant extends Potion {
   public PotionInstant(boolean var1, int var2) {
      super(☃, ☃);
   }

   @Override
   public boolean func_76403_b() {
      return true;
   }

   @Override
   public boolean func_76397_a(int var1, int var2) {
      return ☃ >= 1;
   }
}
