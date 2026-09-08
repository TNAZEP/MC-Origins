package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.PlayerMenuItem;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.GameType;

public class TeleportToPlayerMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
   private static final Ordering<PlayerInfo> PROFILE_ORDER = Ordering.from(
      (var0, var1) -> ComparisonChain.start().compare(var0.getProfile().getId(), var1.getProfile().getId()).result()
   );
   private static final Component TELEPORT_TEXT = new TranslatableComponent("spectatorMenu.teleport");
   private static final Component TELEPORT_PROMPT = new TranslatableComponent("spectatorMenu.teleport.prompt");
   private final List<SpectatorMenuItem> items = Lists.<SpectatorMenuItem>newArrayList();

   public TeleportToPlayerMenuCategory() {
      this(PROFILE_ORDER.<PlayerInfo>sortedCopy(Minecraft.getInstance().getConnection().getOnlinePlayers()));
   }

   public TeleportToPlayerMenuCategory(Collection<PlayerInfo> var1) {
      for(PlayerInfo â˜ƒ : PROFILE_ORDER.sortedCopy(â˜ƒ)) {
         if (â˜ƒ.getGameMode() != GameType.SPECTATOR) {
            this.items.add(new PlayerMenuItem(â˜ƒ.getProfile()));
         }
      }
   }

   @Override
   public List<SpectatorMenuItem> getItems() {
      return this.items;
   }

   @Override
   public Component getPrompt() {
      return TELEPORT_PROMPT;
   }

   @Override
   public void selectItem(SpectatorMenu var1) {
      â˜ƒ.selectCategory(this);
   }

   @Override
   public Component getName() {
      return TELEPORT_TEXT;
   }

   @Override
   public void renderIcon(PoseStack var1, float var2, int var3) {
      RenderSystem.setShaderTexture(0, SpectatorGui.SPECTATOR_LOCATION);
      GuiComponent.blit(â˜ƒ, 0, 0, 0.0F, 0.0F, 16, 16, 256, 256);
   }

   @Override
   public boolean isEnabled() {
      return !this.items.isEmpty();
   }
}
