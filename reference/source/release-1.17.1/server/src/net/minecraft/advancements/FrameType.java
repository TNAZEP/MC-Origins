package net.minecraft.advancements;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public enum FrameType {
   TASK("task", 0, ChatFormatting.GREEN),
   CHALLENGE("challenge", 26, ChatFormatting.DARK_PURPLE),
   GOAL("goal", 52, ChatFormatting.GREEN);

   private final String name;
   private final int texture;
   private final ChatFormatting chatColor;
   private final Component displayName;

   private FrameType(String var3, int var4, ChatFormatting var5) {
      this.name = â˜ƒ;
      this.texture = â˜ƒ;
      this.chatColor = â˜ƒ;
      this.displayName = new TranslatableComponent("advancements.toast." + â˜ƒ);
   }

   public String getName() {
      return this.name;
   }

   public int getTexture() {
      return this.texture;
   }

   public static FrameType byName(String var0) {
      for(FrameType â˜ƒ : values()) {
         if (â˜ƒ.name.equals(â˜ƒ)) {
            return â˜ƒ;
         }
      }

      throw new IllegalArgumentException("Unknown frame type '" + â˜ƒ + "'");
   }

   public ChatFormatting getChatColor() {
      return this.chatColor;
   }

   public Component getDisplayName() {
      return this.displayName;
   }
}
