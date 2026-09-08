package net.minecraft.client.util;

public interface ITooltipFlag {
   boolean func_194127_a();

   public static enum TooltipFlags implements ITooltipFlag {
      NORMAL(false),
      ADVANCED(true);

      private final boolean field_194131_c;

      private TooltipFlags(boolean var3) {
         this.field_194131_c = ☃;
      }

      @Override
      public boolean func_194127_a() {
         return this.field_194131_c;
      }
   }
}
