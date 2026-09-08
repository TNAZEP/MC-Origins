package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;
import java.util.UUID;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NarratorChatListener implements ChatListener {
   public static final Component NO_TITLE = TextComponent.EMPTY;
   private static final Logger LOGGER = LogManager.getLogger();
   public static final NarratorChatListener INSTANCE = new NarratorChatListener();
   private final Narrator narrator = Narrator.getNarrator();

   @Override
   public void handle(ChatType var1, Component var2, UUID var3) {
      NarratorStatus â˜ƒ = getStatus();
      if (â˜ƒ != NarratorStatus.OFF) {
         if (!this.narrator.active()) {
            this.logNarratedMessage(â˜ƒ.getString());
         } else {
            if (â˜ƒ == NarratorStatus.ALL || â˜ƒ == NarratorStatus.CHAT && â˜ƒ == ChatType.CHAT || â˜ƒ == NarratorStatus.SYSTEM && â˜ƒ == ChatType.SYSTEM) {
               Component â˜ƒx;
               if (â˜ƒ instanceof TranslatableComponent && "chat.type.text".equals(((TranslatableComponent)â˜ƒ).getKey())) {
                  â˜ƒx = new TranslatableComponent("chat.type.text.narrate", ((TranslatableComponent)â˜ƒ).getArgs());
               } else {
                  â˜ƒx = â˜ƒ;
               }

               String â˜ƒx = â˜ƒx.getString();
               this.logNarratedMessage(â˜ƒx);
               this.narrator.say(â˜ƒx, â˜ƒ.shouldInterrupt());
            }
         }
      }
   }

   public void sayNow(Component var1) {
      this.sayNow(â˜ƒ.getString());
   }

   public void sayNow(String var1) {
      NarratorStatus â˜ƒ = getStatus();
      if (â˜ƒ != NarratorStatus.OFF && â˜ƒ != NarratorStatus.CHAT && !â˜ƒ.isEmpty()) {
         this.logNarratedMessage(â˜ƒ);
         if (this.narrator.active()) {
            this.narrator.clear();
            this.narrator.say(â˜ƒ, true);
         }
      }
   }

   private static NarratorStatus getStatus() {
      return Minecraft.getInstance().options.narratorStatus;
   }

   private void logNarratedMessage(String var1) {
      if (SharedConstants.IS_RUNNING_IN_IDE) {
         LOGGER.debug("Narrating: {}", â˜ƒ.replaceAll("\n", "\\\\n"));
      }
   }

   public void updateNarratorStatus(NarratorStatus var1) {
      this.clear();
      this.narrator.say(new TranslatableComponent("options.narrator").append(" : ").append(â˜ƒ.getName()).getString(), true);
      ToastComponent â˜ƒ = Minecraft.getInstance().getToasts();
      if (this.narrator.active()) {
         if (â˜ƒ == NarratorStatus.OFF) {
            SystemToast.addOrUpdate(â˜ƒ, SystemToast.SystemToastIds.NARRATOR_TOGGLE, new TranslatableComponent("narrator.toast.disabled"), null);
         } else {
            SystemToast.addOrUpdate(â˜ƒ, SystemToast.SystemToastIds.NARRATOR_TOGGLE, new TranslatableComponent("narrator.toast.enabled"), â˜ƒ.getName());
         }
      } else {
         SystemToast.addOrUpdate(
            â˜ƒ,
            SystemToast.SystemToastIds.NARRATOR_TOGGLE,
            new TranslatableComponent("narrator.toast.disabled"),
            new TranslatableComponent("options.narrator.notavailable")
         );
      }
   }

   public boolean isActive() {
      return this.narrator.active();
   }

   public void clear() {
      if (getStatus() != NarratorStatus.OFF && this.narrator.active()) {
         this.narrator.clear();
      }
   }

   public void destroy() {
      this.narrator.destroy();
   }
}
