package net.minecraft.client.multiplayer;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementList;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientAdvancements {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft minecraft;
   private final AdvancementList advancements = new AdvancementList();
   private final Map<Advancement, AdvancementProgress> progress = Maps.<Advancement, AdvancementProgress>newHashMap();
   @Nullable
   private ClientAdvancements.Listener listener;
   @Nullable
   private Advancement selectedTab;

   public ClientAdvancements(Minecraft var1) {
      this.minecraft = â˜ƒ;
   }

   public void update(ClientboundUpdateAdvancementsPacket var1) {
      if (â˜ƒ.shouldReset()) {
         this.advancements.clear();
         this.progress.clear();
      }

      this.advancements.remove(â˜ƒ.getRemoved());
      this.advancements.add(â˜ƒ.getAdded());

      for(Entry<ResourceLocation, AdvancementProgress> â˜ƒ : â˜ƒ.getProgress().entrySet()) {
         Advancement â˜ƒx = this.advancements.get((ResourceLocation)â˜ƒ.getKey());
         if (â˜ƒx != null) {
            AdvancementProgress â˜ƒxx = (AdvancementProgress)â˜ƒ.getValue();
            â˜ƒxx.update(â˜ƒx.getCriteria(), â˜ƒx.getRequirements());
            this.progress.put(â˜ƒx, â˜ƒxx);
            if (this.listener != null) {
               this.listener.onUpdateAdvancementProgress(â˜ƒx, â˜ƒxx);
            }

            if (!â˜ƒ.shouldReset() && â˜ƒxx.isDone() && â˜ƒx.getDisplay() != null && â˜ƒx.getDisplay().shouldShowToast()) {
               this.minecraft.getToasts().addToast(new AdvancementToast(â˜ƒx));
            }
         } else {
            LOGGER.warn("Server informed client about progress for unknown advancement {}", â˜ƒ.getKey());
         }
      }
   }

   public AdvancementList getAdvancements() {
      return this.advancements;
   }

   public void setSelectedTab(@Nullable Advancement var1, boolean var2) {
      ClientPacketListener â˜ƒ = this.minecraft.getConnection();
      if (â˜ƒ != null && â˜ƒ != null && â˜ƒ) {
         â˜ƒ.send(ServerboundSeenAdvancementsPacket.openedTab(â˜ƒ));
      }

      if (this.selectedTab != â˜ƒ) {
         this.selectedTab = â˜ƒ;
         if (this.listener != null) {
            this.listener.onSelectedTabChanged(â˜ƒ);
         }
      }
   }

   public void setListener(@Nullable ClientAdvancements.Listener var1) {
      this.listener = â˜ƒ;
      this.advancements.setListener(â˜ƒ);
      if (â˜ƒ != null) {
         for(Entry<Advancement, AdvancementProgress> â˜ƒ : this.progress.entrySet()) {
            â˜ƒ.onUpdateAdvancementProgress((Advancement)â˜ƒ.getKey(), (AdvancementProgress)â˜ƒ.getValue());
         }

         â˜ƒ.onSelectedTabChanged(this.selectedTab);
      }
   }

   public interface Listener extends AdvancementList.Listener {
      void onUpdateAdvancementProgress(Advancement var1, AdvancementProgress var2);

      void onSelectedTabChanged(@Nullable Advancement var1);
   }
}
