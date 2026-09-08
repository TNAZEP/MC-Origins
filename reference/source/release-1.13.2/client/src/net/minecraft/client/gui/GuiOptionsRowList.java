package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;

public class GuiOptionsRowList extends GuiListExtended<GuiOptionsRowList.Row> {
   public GuiOptionsRowList(Minecraft var1, int var2, int var3, int var4, int var5, int var6, GameSettings.Options... var7) {
      super(☃, ☃, ☃, ☃, ☃, ☃);
      this.field_148163_i = false;
      this.func_195085_a(new GuiOptionsRowList.Row(☃, GameSettings.Options.FULLSCREEN_RESOLUTION));

      for(int ☃ = 0; ☃ < ☃.length; ☃ += 2) {
         GameSettings.Options ☃x = ☃[☃];
         GameSettings.Options ☃xx = ☃ < ☃.length - 1 ? ☃[☃ + 1] : null;
         this.func_195085_a(new GuiOptionsRowList.Row(☃, ☃x, ☃xx));
      }
   }

   @Nullable
   private static GuiButton func_195092_b(final Minecraft var0, int var1, int var2, int var3, @Nullable final GameSettings.Options var4) {
      if (☃ == null) {
         return null;
      } else {
         int ☃ = ☃.func_74381_c();
         return (GuiButton)(☃.func_74380_a()
            ? new GuiOptionSlider(☃, ☃, ☃, ☃, 20, ☃, 0.0, 1.0)
            : new GuiOptionButton(☃, ☃, ☃, ☃, 20, ☃, ☃.field_71474_y.func_74297_c(☃)) {
               @Override
               public void func_194829_a(double var1, double var3) {
                  ☃.field_71474_y.func_74306_a(☃, 1);
                  this.field_146126_j = ☃.field_71474_y.func_74297_c(GameSettings.Options.func_74379_a(this.field_146127_k));
               }
            });
      }
   }

   @Override
   public int func_148139_c() {
      return 400;
   }

   @Override
   protected int func_148137_d() {
      return super.func_148137_d() + 32;
   }

   public final class Row extends GuiListExtended.IGuiListEntry<GuiOptionsRowList.Row> {
      @Nullable
      private final GuiButton field_148323_b;
      @Nullable
      private final GuiButton field_148324_c;

      public Row(@Nullable GuiButton var2, @Nullable GuiButton var3) {
         this.field_148323_b = ☃;
         this.field_148324_c = ☃;
      }

      public Row(int var2, GameSettings.Options var3) {
         this(GuiOptionsRowList.func_195092_b(GuiOptionsRowList.this.field_148161_k, ☃ / 2 - 155, 0, 310, ☃), null);
      }

      public Row(int var2, GameSettings.Options var3, @Nullable GameSettings.Options var4) {
         this(
            GuiOptionsRowList.func_195092_b(GuiOptionsRowList.this.field_148161_k, ☃ / 2 - 155, 0, 150, ☃),
            GuiOptionsRowList.func_195092_b(GuiOptionsRowList.this.field_148161_k, ☃ / 2 - 155 + 160, 0, 150, ☃)
         );
      }

      @Override
      public void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6) {
         if (this.field_148323_b != null) {
            this.field_148323_b.field_146129_i = this.func_195001_c();
            this.field_148323_b.func_194828_a(☃, ☃, ☃);
         }

         if (this.field_148324_c != null) {
            this.field_148324_c.field_146129_i = this.func_195001_c();
            this.field_148324_c.func_194828_a(☃, ☃, ☃);
         }
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (this.field_148323_b.mouseClicked(☃, ☃, ☃)) {
            return true;
         } else {
            return this.field_148324_c != null && this.field_148324_c.mouseClicked(☃, ☃, ☃);
         }
      }

      @Override
      public boolean mouseReleased(double var1, double var3, int var5) {
         boolean ☃ = this.field_148323_b != null && this.field_148323_b.mouseReleased(☃, ☃, ☃);
         boolean ☃x = this.field_148324_c != null && this.field_148324_c.mouseReleased(☃, ☃, ☃);
         return ☃ || ☃x;
      }

      @Override
      public void func_195000_a(float var1) {
      }
   }
}
