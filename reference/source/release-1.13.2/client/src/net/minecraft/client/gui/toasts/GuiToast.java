package net.minecraft.client.gui.toasts;

import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.Deque;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class GuiToast extends Gui {
   private final Minecraft field_191790_f;
   private final GuiToast.ToastInstance<?>[] field_191791_g = new GuiToast.ToastInstance[5];
   private final Deque<IToast> field_191792_h = Queues.<IToast>newArrayDeque();

   public GuiToast(Minecraft var1) {
      this.field_191790_f = ☃;
   }

   public void func_195625_a() {
      if (!this.field_191790_f.field_71474_y.field_74319_N) {
         RenderHelper.func_74518_a();

         for(int ☃ = 0; ☃ < this.field_191791_g.length; ++☃) {
            GuiToast.ToastInstance<?> ☃x = this.field_191791_g[☃];
            if (☃x != null && ☃x.func_193684_a(this.field_191790_f.field_195558_d.func_198107_o(), ☃)) {
               this.field_191791_g[☃] = null;
            }

            if (this.field_191791_g[☃] == null && !this.field_191792_h.isEmpty()) {
               this.field_191791_g[☃] = new GuiToast.ToastInstance((IToast)this.field_191792_h.removeFirst());
            }
         }
      }
   }

   @Nullable
   public <T extends IToast> T func_192990_a(Class<? extends T> var1, Object var2) {
      for(GuiToast.ToastInstance<?> ☃ : this.field_191791_g) {
         if (☃ != null && ☃.isAssignableFrom(☃.func_193685_a().getClass()) && ☃.func_193685_a().func_193652_b().equals(☃)) {
            return (T)☃.func_193685_a();
         }
      }

      for(IToast ☃ : this.field_191792_h) {
         if (☃.isAssignableFrom(☃.getClass()) && ☃.func_193652_b().equals(☃)) {
            return (T)☃;
         }
      }

      return null;
   }

   public void func_191788_b() {
      Arrays.fill(this.field_191791_g, null);
      this.field_191792_h.clear();
   }

   public void func_192988_a(IToast var1) {
      this.field_191792_h.add(☃);
   }

   public Minecraft func_192989_b() {
      return this.field_191790_f;
   }

   class ToastInstance<T extends IToast> {
      private final T field_193688_b;
      private long field_193689_c = -1L;
      private long field_193690_d = -1L;
      private IToast.Visibility field_193691_e = IToast.Visibility.SHOW;

      private ToastInstance(T var2) {
         this.field_193688_b = ☃;
      }

      public T func_193685_a() {
         return this.field_193688_b;
      }

      private float func_193686_a(long var1) {
         float ☃ = MathHelper.func_76131_a((float)(☃ - this.field_193689_c) / 600.0F, 0.0F, 1.0F);
         ☃ *= ☃;
         return this.field_193691_e == IToast.Visibility.HIDE ? 1.0F - ☃ : ☃;
      }

      public boolean func_193684_a(int var1, int var2) {
         long ☃ = Util.func_211177_b();
         if (this.field_193689_c == -1L) {
            this.field_193689_c = ☃;
            this.field_193691_e.func_194169_a(GuiToast.this.field_191790_f.func_147118_V());
         }

         if (this.field_193691_e == IToast.Visibility.SHOW && ☃ - this.field_193689_c <= 600L) {
            this.field_193690_d = ☃;
         }

         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)☃ - 160.0F * this.func_193686_a(☃), (float)(☃ * 32), (float)(500 + ☃));
         IToast.Visibility ☃ = this.field_193688_b.func_193653_a(GuiToast.this, ☃ - this.field_193690_d);
         GlStateManager.func_179121_F();
         if (☃ != this.field_193691_e) {
            this.field_193689_c = ☃ - (long)((int)((1.0F - this.func_193686_a(☃)) * 600.0F));
            this.field_193691_e = ☃;
            this.field_193691_e.func_194169_a(GuiToast.this.field_191790_f.func_147118_V());
         }

         return this.field_193691_e == IToast.Visibility.HIDE && ☃ - this.field_193689_c > 600L;
      }
   }
}
