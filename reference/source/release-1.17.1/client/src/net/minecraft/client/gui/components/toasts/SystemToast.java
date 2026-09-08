package net.minecraft.client.gui.components.toasts;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;

public class SystemToast implements Toast {
   private static final long DISPLAY_TIME = 5000L;
   private static final int MAX_LINE_SIZE = 200;
   private final SystemToast.SystemToastIds id;
   private Component title;
   private List<FormattedCharSequence> messageLines;
   private long lastChanged;
   private boolean changed;
   private final int width;

   public SystemToast(SystemToast.SystemToastIds var1, Component var2, @Nullable Component var3) {
      this(â˜ƒ, â˜ƒ, nullToEmpty(â˜ƒ), 160);
   }

   public static SystemToast multiline(Minecraft var0, SystemToast.SystemToastIds var1, Component var2, Component var3) {
      Font â˜ƒ = â˜ƒ.font;
      List<FormattedCharSequence> â˜ƒx = â˜ƒ.split(â˜ƒ, 200);
      int â˜ƒxx = Math.max(200, â˜ƒx.stream().mapToInt(â˜ƒ::width).max().orElse(200));
      return new SystemToast(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx + 30);
   }

   private SystemToast(SystemToast.SystemToastIds var1, Component var2, List<FormattedCharSequence> var3, int var4) {
      this.id = â˜ƒ;
      this.title = â˜ƒ;
      this.messageLines = â˜ƒ;
      this.width = â˜ƒ;
   }

   private static ImmutableList<FormattedCharSequence> nullToEmpty(@Nullable Component var0) {
      return â˜ƒ == null ? ImmutableList.of() : ImmutableList.of(â˜ƒ.getVisualOrderText());
   }

   @Override
   public int width() {
      return this.width;
   }

   @Override
   public Toast.Visibility render(PoseStack var1, ToastComponent var2, long var3) {
      if (this.changed) {
         this.lastChanged = â˜ƒ;
         this.changed = false;
      }

      RenderSystem.setShaderTexture(0, TEXTURE);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int â˜ƒ = this.width();
      int â˜ƒx = 12;
      if (â˜ƒ == 160 && this.messageLines.size() <= 1) {
         â˜ƒ.blit(â˜ƒ, 0, 0, 0, 64, â˜ƒ, this.height());
      } else {
         int â˜ƒ = this.height() + Math.max(0, this.messageLines.size() - 1) * 12;
         int â˜ƒx = 28;
         int â˜ƒxx = Math.min(4, â˜ƒ - 28);
         this.renderBackgroundRow(â˜ƒ, â˜ƒ, â˜ƒ, 0, 0, 28);

         for(int â˜ƒxxx = 28; â˜ƒxxx < â˜ƒ - â˜ƒxx; â˜ƒxxx += 10) {
            this.renderBackgroundRow(â˜ƒ, â˜ƒ, â˜ƒ, 16, â˜ƒxxx, Math.min(16, â˜ƒ - â˜ƒxxx - â˜ƒxx));
         }

         this.renderBackgroundRow(â˜ƒ, â˜ƒ, â˜ƒ, 32 - â˜ƒxx, â˜ƒ - â˜ƒxx, â˜ƒxx);
      }

      if (this.messageLines == null) {
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, this.title, 18.0F, 12.0F, -256);
      } else {
         â˜ƒ.getMinecraft().font.draw(â˜ƒ, this.title, 18.0F, 7.0F, -256);

         for(int â˜ƒ = 0; â˜ƒ < this.messageLines.size(); ++â˜ƒ) {
            â˜ƒ.getMinecraft().font.draw(â˜ƒ, (FormattedCharSequence)this.messageLines.get(â˜ƒ), 18.0F, (float)(18 + â˜ƒ * 12), -1);
         }
      }

      return â˜ƒ - this.lastChanged < 5000L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
   }

   private void renderBackgroundRow(PoseStack var1, ToastComponent var2, int var3, int var4, int var5, int var6) {
      int â˜ƒ = â˜ƒ == 0 ? 20 : 5;
      int â˜ƒx = Math.min(60, â˜ƒ - â˜ƒ);
      â˜ƒ.blit(â˜ƒ, 0, â˜ƒ, 0, 64 + â˜ƒ, â˜ƒ, â˜ƒ);

      for(int â˜ƒxx = â˜ƒ; â˜ƒxx < â˜ƒ - â˜ƒx; â˜ƒxx += 64) {
         â˜ƒ.blit(â˜ƒ, â˜ƒxx, â˜ƒ, 32, 64 + â˜ƒ, Math.min(64, â˜ƒ - â˜ƒxx - â˜ƒx), â˜ƒ);
      }

      â˜ƒ.blit(â˜ƒ, â˜ƒ - â˜ƒx, â˜ƒ, 160 - â˜ƒx, 64 + â˜ƒ, â˜ƒx, â˜ƒ);
   }

   public void reset(Component var1, @Nullable Component var2) {
      this.title = â˜ƒ;
      this.messageLines = nullToEmpty(â˜ƒ);
      this.changed = true;
   }

   public SystemToast.SystemToastIds getToken() {
      return this.id;
   }

   public static void add(ToastComponent var0, SystemToast.SystemToastIds var1, Component var2, @Nullable Component var3) {
      â˜ƒ.addToast(new SystemToast(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static void addOrUpdate(ToastComponent var0, SystemToast.SystemToastIds var1, Component var2, @Nullable Component var3) {
      SystemToast â˜ƒ = â˜ƒ.getToast(SystemToast.class, â˜ƒ);
      if (â˜ƒ == null) {
         add(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         â˜ƒ.reset(â˜ƒ, â˜ƒ);
      }
   }

   public static void onWorldAccessFailure(Minecraft var0, String var1) {
      add(â˜ƒ.getToasts(), SystemToast.SystemToastIds.WORLD_ACCESS_FAILURE, new TranslatableComponent("selectWorld.access_failure"), new TextComponent(â˜ƒ));
   }

   public static void onWorldDeleteFailure(Minecraft var0, String var1) {
      add(â˜ƒ.getToasts(), SystemToast.SystemToastIds.WORLD_ACCESS_FAILURE, new TranslatableComponent("selectWorld.delete_failure"), new TextComponent(â˜ƒ));
   }

   public static void onPackCopyFailure(Minecraft var0, String var1) {
      add(â˜ƒ.getToasts(), SystemToast.SystemToastIds.PACK_COPY_FAILURE, new TranslatableComponent("pack.copyFailure"), new TextComponent(â˜ƒ));
   }

   public static enum SystemToastIds {
      TUTORIAL_HINT,
      NARRATOR_TOGGLE,
      WORLD_BACKUP,
      WORLD_GEN_SETTINGS_TRANSFER,
      PACK_LOAD_FAILURE,
      WORLD_ACCESS_FAILURE,
      PACK_COPY_FAILURE;
   }
}
