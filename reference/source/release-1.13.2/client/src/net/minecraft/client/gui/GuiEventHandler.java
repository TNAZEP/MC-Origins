package net.minecraft.client.gui;

import java.util.List;
import javax.annotation.Nullable;

public abstract class GuiEventHandler extends Gui implements IGuiEventListenerDeferred {
   @Nullable
   private IGuiEventListener field_195075_a;
   private boolean field_195076_f;

   protected abstract List<? extends IGuiEventListener> func_195074_b();

   private final boolean func_195071_s() {
      return this.field_195076_f;
   }

   protected final void func_195072_d(boolean var1) {
      this.field_195076_f = ☃;
   }

   @Nullable
   @Override
   public IGuiEventListener getFocused() {
      return this.field_195075_a;
   }

   protected void func_195073_a(@Nullable IGuiEventListener var1) {
      this.field_195075_a = ☃;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      for(IGuiEventListener ☃ : this.func_195074_b()) {
         boolean ☃x = ☃.mouseClicked(☃, ☃, ☃);
         if (☃x) {
            this.func_205725_b(☃);
            if (☃ == 0) {
               this.func_195072_d(true);
            }

            return true;
         }
      }

      return false;
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      return IGuiEventListenerDeferred.super.keyPressed(☃, ☃, ☃);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      return this.getFocused() != null && this.func_195071_s() && ☃ == 0 ? this.getFocused().mouseDragged(☃, ☃, ☃, ☃, ☃) : false;
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      this.func_195072_d(false);
      return IGuiEventListenerDeferred.super.mouseReleased(☃, ☃, ☃);
   }

   public void func_205725_b(@Nullable IGuiEventListener var1) {
      this.func_205728_a(☃, this.func_195074_b().indexOf(this.getFocused()));
   }

   public void func_207714_t() {
      int ☃ = this.func_195074_b().indexOf(this.getFocused());
      int ☃x = ☃ == -1 ? 0 : (☃ + 1) % this.func_195074_b().size();
      this.func_205728_a(this.func_207713_a(☃x), ☃);
   }

   @Nullable
   private IGuiEventListener func_207713_a(int var1) {
      List<? extends IGuiEventListener> ☃ = this.func_195074_b();
      int ☃x = ☃.size();

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         IGuiEventListener ☃xxx = (IGuiEventListener)☃.get((☃ + ☃xx) % ☃x);
         if (☃xxx.func_207704_ae_()) {
            return ☃xxx;
         }
      }

      return null;
   }

   private void func_205728_a(@Nullable IGuiEventListener var1, int var2) {
      IGuiEventListener ☃ = ☃ == -1 ? null : (IGuiEventListener)this.func_195074_b().get(☃);
      if (☃ != ☃) {
         if (☃ != null) {
            ☃.func_205700_b(false);
         }

         if (☃ != null) {
            ☃.func_205700_b(true);
         }

         this.func_195073_a(☃);
      }
   }
}
