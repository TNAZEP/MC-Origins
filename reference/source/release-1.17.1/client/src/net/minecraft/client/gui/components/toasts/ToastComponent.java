package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Arrays;
import java.util.Deque;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.Mth;

public class ToastComponent extends GuiComponent {
   private static final int VISIBLE_TOASTS = 5;
   final Minecraft minecraft;
   private final ToastComponent.ToastInstance<?>[] visible = new ToastComponent.ToastInstance[5];
   private final Deque<Toast> queued = Queues.<Toast>newArrayDeque();

   public ToastComponent(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void render(PoseStack var1) {
      if (!this.minecraft.options.hideGui) {
         for(int â˜ƒ = 0; â˜ƒ < this.visible.length; ++â˜ƒ) {
            ToastComponent.ToastInstance<?> â˜ƒx = this.visible[â˜ƒ];
            if (â˜ƒx != null && â˜ƒx.render(this.minecraft.getWindow().getGuiScaledWidth(), â˜ƒ, â˜ƒ)) {
               this.visible[â˜ƒ] = null;
            }

            if (this.visible[â˜ƒ] == null && !this.queued.isEmpty()) {
               this.visible[â˜ƒ] = new ToastComponent.ToastInstance((Toast)this.queued.removeFirst());
            }
         }
      }
   }

   @Nullable
   public <T extends Toast> T getToast(Class<? extends T> var1, Object var2) {
      for(ToastComponent.ToastInstance<?> â˜ƒ : this.visible) {
         if (â˜ƒ != null && â˜ƒ.isAssignableFrom(â˜ƒ.getToast().getClass()) && â˜ƒ.getToast().getToken().equals(â˜ƒ)) {
            return (T)â˜ƒ.getToast();
         }
      }

      for(Toast â˜ƒ : this.queued) {
         if (â˜ƒ.isAssignableFrom(â˜ƒ.getClass()) && â˜ƒ.getToken().equals(â˜ƒ)) {
            return (T)â˜ƒ;
         }
      }

      return null;
   }

   public void clear() {
      Arrays.fill(this.visible, null);
      this.queued.clear();
   }

   public void addToast(Toast var1) {
      this.queued.add(â˜ƒ);
   }

   public Minecraft getMinecraft() {
      return this.minecraft;
   }

   class ToastInstance<T extends Toast> {
      private static final long ANIMATION_TIME = 600L;
      private final T toast;
      private long animationTime = -1L;
      private long visibleTime = -1L;
      private Toast.Visibility visibility = Toast.Visibility.SHOW;

      ToastInstance(T var2) {
         this.toast = â˜ƒ;
      }

      public T getToast() {
         return this.toast;
      }

      private float getVisibility(long var1) {
         float â˜ƒ = Mth.clamp((float)(â˜ƒ - this.animationTime) / 600.0F, 0.0F, 1.0F);
         â˜ƒ *= â˜ƒ;
         return this.visibility == Toast.Visibility.HIDE ? 1.0F - â˜ƒ : â˜ƒ;
      }

      public boolean render(int var1, int var2, PoseStack var3) {
         long â˜ƒ = Util.getMillis();
         if (this.animationTime == -1L) {
            this.animationTime = â˜ƒ;
            this.visibility.playSound(ToastComponent.this.minecraft.getSoundManager());
         }

         if (this.visibility == Toast.Visibility.SHOW && â˜ƒ - this.animationTime <= 600L) {
            this.visibleTime = â˜ƒ;
         }

         PoseStack â˜ƒ = RenderSystem.getModelViewStack();
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)((float)â˜ƒ - (float)this.toast.width() * this.getVisibility(â˜ƒ)), (double)(â˜ƒ * this.toast.height()), (double)(800 + â˜ƒ));
         RenderSystem.applyModelViewMatrix();
         Toast.Visibility â˜ƒx = this.toast.render(â˜ƒ, ToastComponent.this, â˜ƒ - this.visibleTime);
         â˜ƒ.popPose();
         RenderSystem.applyModelViewMatrix();
         if (â˜ƒx != this.visibility) {
            this.animationTime = â˜ƒ - (long)((int)((1.0F - this.getVisibility(â˜ƒ)) * 600.0F));
            this.visibility = â˜ƒx;
            this.visibility.playSound(ToastComponent.this.minecraft.getSoundManager());
         }

         return this.visibility == Toast.Visibility.HIDE && â˜ƒ - this.animationTime > 600L;
      }
   }
}
