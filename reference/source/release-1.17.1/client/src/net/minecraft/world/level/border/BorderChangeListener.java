package net.minecraft.world.level.border;

public interface BorderChangeListener {
   void onBorderSizeSet(WorldBorder var1, double var2);

   void onBorderSizeLerping(WorldBorder var1, double var2, double var4, long var6);

   void onBorderCenterSet(WorldBorder var1, double var2, double var4);

   void onBorderSetWarningTime(WorldBorder var1, int var2);

   void onBorderSetWarningBlocks(WorldBorder var1, int var2);

   void onBorderSetDamagePerBlock(WorldBorder var1, double var2);

   void onBorderSetDamageSafeZOne(WorldBorder var1, double var2);

   public static class DelegateBorderChangeListener implements BorderChangeListener {
      private final WorldBorder worldBorder;

      public DelegateBorderChangeListener(WorldBorder var1) {
         this.worldBorder = â˜ƒ;
      }

      @Override
      public void onBorderSizeSet(WorldBorder var1, double var2) {
         this.worldBorder.setSize(â˜ƒ);
      }

      @Override
      public void onBorderSizeLerping(WorldBorder var1, double var2, double var4, long var6) {
         this.worldBorder.lerpSizeBetween(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public void onBorderCenterSet(WorldBorder var1, double var2, double var4) {
         this.worldBorder.setCenter(â˜ƒ, â˜ƒ);
      }

      @Override
      public void onBorderSetWarningTime(WorldBorder var1, int var2) {
         this.worldBorder.setWarningTime(â˜ƒ);
      }

      @Override
      public void onBorderSetWarningBlocks(WorldBorder var1, int var2) {
         this.worldBorder.setWarningBlocks(â˜ƒ);
      }

      @Override
      public void onBorderSetDamagePerBlock(WorldBorder var1, double var2) {
         this.worldBorder.setDamagePerBlock(â˜ƒ);
      }

      @Override
      public void onBorderSetDamageSafeZOne(WorldBorder var1, double var2) {
         this.worldBorder.setDamageSafeZone(â˜ƒ);
      }
   }
}
