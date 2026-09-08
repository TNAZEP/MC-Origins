package net.minecraft.world;

import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public abstract class BossEvent {
   private final UUID id;
   protected Component name;
   protected float progress;
   protected BossEvent.BossBarColor color;
   protected BossEvent.BossBarOverlay overlay;
   protected boolean darkenScreen;
   protected boolean playBossMusic;
   protected boolean createWorldFog;

   public BossEvent(UUID var1, Component var2, BossEvent.BossBarColor var3, BossEvent.BossBarOverlay var4) {
      this.id = â˜ƒ;
      this.name = â˜ƒ;
      this.color = â˜ƒ;
      this.overlay = â˜ƒ;
      this.progress = 1.0F;
   }

   public UUID getId() {
      return this.id;
   }

   public Component getName() {
      return this.name;
   }

   public void setName(Component var1) {
      this.name = â˜ƒ;
   }

   public float getProgress() {
      return this.progress;
   }

   public void setProgress(float var1) {
      this.progress = â˜ƒ;
   }

   public BossEvent.BossBarColor getColor() {
      return this.color;
   }

   public void setColor(BossEvent.BossBarColor var1) {
      this.color = â˜ƒ;
   }

   public BossEvent.BossBarOverlay getOverlay() {
      return this.overlay;
   }

   public void setOverlay(BossEvent.BossBarOverlay var1) {
      this.overlay = â˜ƒ;
   }

   public boolean shouldDarkenScreen() {
      return this.darkenScreen;
   }

   public BossEvent setDarkenScreen(boolean var1) {
      this.darkenScreen = â˜ƒ;
      return this;
   }

   public boolean shouldPlayBossMusic() {
      return this.playBossMusic;
   }

   public BossEvent setPlayBossMusic(boolean var1) {
      this.playBossMusic = â˜ƒ;
      return this;
   }

   public BossEvent setCreateWorldFog(boolean var1) {
      this.createWorldFog = â˜ƒ;
      return this;
   }

   public boolean shouldCreateWorldFog() {
      return this.createWorldFog;
   }

   public static enum BossBarColor {
      PINK("pink", ChatFormatting.RED),
      BLUE("blue", ChatFormatting.BLUE),
      RED("red", ChatFormatting.DARK_RED),
      GREEN("green", ChatFormatting.GREEN),
      YELLOW("yellow", ChatFormatting.YELLOW),
      PURPLE("purple", ChatFormatting.DARK_BLUE),
      WHITE("white", ChatFormatting.WHITE);

      private final String name;
      private final ChatFormatting formatting;

      private BossBarColor(String var3, ChatFormatting var4) {
         this.name = â˜ƒ;
         this.formatting = â˜ƒ;
      }

      public ChatFormatting getFormatting() {
         return this.formatting;
      }

      public String getName() {
         return this.name;
      }

      public static BossEvent.BossBarColor byName(String var0) {
         for(BossEvent.BossBarColor â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         return WHITE;
      }
   }

   public static enum BossBarOverlay {
      PROGRESS("progress"),
      NOTCHED_6("notched_6"),
      NOTCHED_10("notched_10"),
      NOTCHED_12("notched_12"),
      NOTCHED_20("notched_20");

      private final String name;

      private BossBarOverlay(String var3) {
         this.name = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public static BossEvent.BossBarOverlay byName(String var0) {
         for(BossEvent.BossBarOverlay â˜ƒ : values()) {
            if (â˜ƒ.name.equals(â˜ƒ)) {
               return â˜ƒ;
            }
         }

         return PROGRESS;
      }
   }
}
