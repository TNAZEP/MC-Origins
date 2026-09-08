package net.minecraft.client.gui.components;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class SubtitleOverlay extends GuiComponent implements SoundEventListener {
   private static final long DISPLAY_TIME = 3000L;
   private final Minecraft minecraft;
   private final List<SubtitleOverlay.Subtitle> subtitles = Lists.<SubtitleOverlay.Subtitle>newArrayList();
   private boolean isListening;

   public SubtitleOverlay(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void render(PoseStack var1) {
      if (!this.isListening && this.minecraft.options.showSubtitles) {
         this.minecraft.getSoundManager().addListener(this);
         this.isListening = true;
      } else if (this.isListening && !this.minecraft.options.showSubtitles) {
         this.minecraft.getSoundManager().removeListener(this);
         this.isListening = false;
      }

      if (this.isListening && !this.subtitles.isEmpty()) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         Vec3 â˜ƒ = new Vec3(this.minecraft.player.getX(), this.minecraft.player.getEyeY(), this.minecraft.player.getZ());
         Vec3 â˜ƒx = new Vec3(0.0, 0.0, -1.0)
            .xRot(-this.minecraft.player.getXRot() * (float) (Math.PI / 180.0))
            .yRot(-this.minecraft.player.getYRot() * (float) (Math.PI / 180.0));
         Vec3 â˜ƒxx = new Vec3(0.0, 1.0, 0.0)
            .xRot(-this.minecraft.player.getXRot() * (float) (Math.PI / 180.0))
            .yRot(-this.minecraft.player.getYRot() * (float) (Math.PI / 180.0));
         Vec3 â˜ƒxxx = â˜ƒx.cross(â˜ƒxx);
         int â˜ƒxxxx = 0;
         int â˜ƒxxxxx = 0;
         Iterator<SubtitleOverlay.Subtitle> â˜ƒxxxxxx = this.subtitles.iterator();

         while(â˜ƒxxxxxx.hasNext()) {
            SubtitleOverlay.Subtitle â˜ƒxxxxxxx = (SubtitleOverlay.Subtitle)â˜ƒxxxxxx.next();
            if (â˜ƒxxxxxxx.getTime() + 3000L <= Util.getMillis()) {
               â˜ƒxxxxxx.remove();
            } else {
               â˜ƒxxxxx = Math.max(â˜ƒxxxxx, this.minecraft.font.width(â˜ƒxxxxxxx.getText()));
            }
         }

         â˜ƒxxxxx += this.minecraft.font.width("<") + this.minecraft.font.width(" ") + this.minecraft.font.width(">") + this.minecraft.font.width(" ");

         for(SubtitleOverlay.Subtitle â˜ƒxxxxxxx : this.subtitles) {
            int â˜ƒxxxxxxxx = 255;
            Component â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.getText();
            Vec3 â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx.getLocation().subtract(â˜ƒ).normalize();
            double â˜ƒxxxxxxxxxxx = -â˜ƒxxx.dot(â˜ƒxxxxxxxxxx);
            double â˜ƒxxxxxxxxxxxx = -â˜ƒx.dot(â˜ƒxxxxxxxxxx);
            boolean â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx > 0.5;
            int â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxx / 2;
            int â˜ƒxxxxxxxxxxxxxxx = 9;
            int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx / 2;
            float â˜ƒxxxxxxxxxxxxxxxxx = 1.0F;
            int â˜ƒxxxxxxxxxxxxxxxxxx = this.minecraft.font.width(â˜ƒxxxxxxxxx);
            int â˜ƒxxxxxxxxxxxxxxxxxxx = Mth.floor(Mth.clampedLerp(255.0F, 75.0F, (float)(Util.getMillis() - â˜ƒxxxxxxx.getTime()) / 3000.0F));
            int â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx << 16 | â˜ƒxxxxxxxxxxxxxxxxxxx << 8 | â˜ƒxxxxxxxxxxxxxxxxxxx;
            â˜ƒ.pushPose();
            â˜ƒ.translate(
               (double)((float)this.minecraft.getWindow().getGuiScaledWidth() - (float)â˜ƒxxxxxxxxxxxxxx * 1.0F - 2.0F),
               (double)((float)(this.minecraft.getWindow().getGuiScaledHeight() - 30) - (float)(â˜ƒxxxx * (â˜ƒxxxxxxxxxxxxxxx + 1)) * 1.0F),
               0.0
            );
            â˜ƒ.scale(1.0F, 1.0F, 1.0F);
            fill(
               â˜ƒ,
               -â˜ƒxxxxxxxxxxxxxx - 1,
               -â˜ƒxxxxxxxxxxxxxxxx - 1,
               â˜ƒxxxxxxxxxxxxxx + 1,
               â˜ƒxxxxxxxxxxxxxxxx + 1,
               this.minecraft.options.getBackgroundColor(0.8F)
            );
            RenderSystem.enableBlend();
            if (!â˜ƒxxxxxxxxxxxxx) {
               if (â˜ƒxxxxxxxxxxx > 0.0) {
                  this.minecraft
                     .font
                     .draw(
                        â˜ƒ,
                        ">",
                        (float)(â˜ƒxxxxxxxxxxxxxx - this.minecraft.font.width(">")),
                        (float)(-â˜ƒxxxxxxxxxxxxxxxx),
                        â˜ƒxxxxxxxxxxxxxxxxxxxx + -16777216
                     );
               } else if (â˜ƒxxxxxxxxxxx < 0.0) {
                  this.minecraft.font.draw(â˜ƒ, "<", (float)(-â˜ƒxxxxxxxxxxxxxx), (float)(-â˜ƒxxxxxxxxxxxxxxxx), â˜ƒxxxxxxxxxxxxxxxxxxxx + -16777216);
               }
            }

            this.minecraft
               .font
               .draw(â˜ƒ, â˜ƒxxxxxxxxx, (float)(-â˜ƒxxxxxxxxxxxxxxxxxx / 2), (float)(-â˜ƒxxxxxxxxxxxxxxxx), â˜ƒxxxxxxxxxxxxxxxxxxxx + -16777216);
            â˜ƒ.popPose();
            ++â˜ƒxxxx;
         }

         RenderSystem.disableBlend();
      }
   }

   @Override
   public void onPlaySound(SoundInstance var1, WeighedSoundEvents var2) {
      if (â˜ƒ.getSubtitle() != null) {
         Component â˜ƒ = â˜ƒ.getSubtitle();
         if (!this.subtitles.isEmpty()) {
            for(SubtitleOverlay.Subtitle â˜ƒx : this.subtitles) {
               if (â˜ƒx.getText().equals(â˜ƒ)) {
                  â˜ƒx.refresh(new Vec3(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()));
                  return;
               }
            }
         }

         this.subtitles.add(new SubtitleOverlay.Subtitle(â˜ƒ, new Vec3(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ())));
      }
   }

   public static class Subtitle {
      private final Component text;
      private long time;
      private Vec3 location;

      public Subtitle(Component var1, Vec3 var2) {
         this.text = â˜ƒ;
         this.location = â˜ƒ;
         this.time = Util.getMillis();
      }

      public Component getText() {
         return this.text;
      }

      public long getTime() {
         return this.time;
      }

      public Vec3 getLocation() {
         return this.location;
      }

      public void refresh(Vec3 var1) {
         this.location = â˜ƒ;
         this.time = Util.getMillis();
      }
   }
}
