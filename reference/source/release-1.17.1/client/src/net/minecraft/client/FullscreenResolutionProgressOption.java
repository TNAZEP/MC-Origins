package net.minecraft.client;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.blaze3d.platform.Window;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class FullscreenResolutionProgressOption extends ProgressOption {
   private static final double CURRENT_MODE = -1.0;

   public FullscreenResolutionProgressOption(Window var1) {
      this(â˜ƒ, â˜ƒ.findBestMonitor());
   }

   private FullscreenResolutionProgressOption(Window var1, @Nullable Monitor var2) {
      super(
         "options.fullscreen.resolution",
         -1.0,
         â˜ƒ != null ? (double)(â˜ƒ.getModeCount() - 1) : -1.0,
         1.0F,
         var2x -> {
            if (â˜ƒ == null) {
               return -1.0;
            } else {
               Optional<VideoMode> â˜ƒ = â˜ƒ.getPreferredFullscreenVideoMode();
               return (Double)â˜ƒ.map(var1x -> (double)â˜ƒ.getVideoModeIndex(var1x)).orElse(-1.0);
            }
         },
         (var2x, var3) -> {
            if (â˜ƒ != null) {
               if (var3 == -1.0) {
                  â˜ƒ.setPreferredFullscreenVideoMode(Optional.empty());
               } else {
                  â˜ƒ.setPreferredFullscreenVideoMode(Optional.of(â˜ƒ.getMode(var3.intValue())));
               }
            }
         },
         (var1x, var2x) -> {
            if (â˜ƒ == null) {
               return new TranslatableComponent("options.fullscreen.unavailable");
            } else {
               double â˜ƒ = var2x.get(var1x);
               return â˜ƒ == -1.0
                  ? var2x.genericValueLabel(new TranslatableComponent("options.fullscreen.current"))
                  : var2x.genericValueLabel(new TextComponent(â˜ƒ.getMode((int)â˜ƒ).toString()));
            }
         }
      );
   }
}
