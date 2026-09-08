package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.spectator.SpectatorGui;
import net.minecraft.client.gui.spectator.SpectatorMenu;
import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
import net.minecraft.client.gui.spectator.SpectatorMenuItem;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.scores.PlayerTeam;

public class TeleportToTeamMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
   private static final Component TELEPORT_TEXT = new TranslatableComponent("spectatorMenu.team_teleport");
   private static final Component TELEPORT_PROMPT = new TranslatableComponent("spectatorMenu.team_teleport.prompt");
   private final List<SpectatorMenuItem> items = Lists.<SpectatorMenuItem>newArrayList();

   public TeleportToTeamMenuCategory() {
      Minecraft â˜ƒ = Minecraft.getInstance();

      for(PlayerTeam â˜ƒx : â˜ƒ.level.getScoreboard().getPlayerTeams()) {
         this.items.add(new TeleportToTeamMenuCategory.TeamSelectionItem(â˜ƒx));
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
      GuiComponent.blit(â˜ƒ, 0, 0, 16.0F, 0.0F, 16, 16, 256, 256);
   }

   @Override
   public boolean isEnabled() {
      for(SpectatorMenuItem â˜ƒ : this.items) {
         if (â˜ƒ.isEnabled()) {
            return true;
         }
      }

      return false;
   }

   class TeamSelectionItem implements SpectatorMenuItem {
      private final PlayerTeam team;
      private final ResourceLocation location;
      private final List<PlayerInfo> players;

      public TeamSelectionItem(PlayerTeam var2) {
         this.team = â˜ƒ;
         this.players = Lists.<PlayerInfo>newArrayList();

         for(String â˜ƒ : â˜ƒ.getPlayers()) {
            PlayerInfo â˜ƒx = Minecraft.getInstance().getConnection().getPlayerInfo(â˜ƒ);
            if (â˜ƒx != null) {
               this.players.add(â˜ƒx);
            }
         }

         if (this.players.isEmpty()) {
            this.location = DefaultPlayerSkin.getDefaultSkin();
         } else {
            String â˜ƒ = ((PlayerInfo)this.players.get(new Random().nextInt(this.players.size()))).getProfile().getName();
            this.location = AbstractClientPlayer.getSkinLocation(â˜ƒ);
            AbstractClientPlayer.registerSkinTexture(this.location, â˜ƒ);
         }
      }

      @Override
      public void selectItem(SpectatorMenu var1) {
         â˜ƒ.selectCategory(new TeleportToPlayerMenuCategory(this.players));
      }

      @Override
      public Component getName() {
         return this.team.getDisplayName();
      }

      @Override
      public void renderIcon(PoseStack var1, float var2, int var3) {
         Integer â˜ƒ = this.team.getColor().getColor();
         if (â˜ƒ != null) {
            float â˜ƒx = (float)(â˜ƒ >> 16 & 0xFF) / 255.0F;
            float â˜ƒxx = (float)(â˜ƒ >> 8 & 0xFF) / 255.0F;
            float â˜ƒxxx = (float)(â˜ƒ & 0xFF) / 255.0F;
            GuiComponent.fill(â˜ƒ, 1, 1, 15, 15, Mth.color(â˜ƒx * â˜ƒ, â˜ƒxx * â˜ƒ, â˜ƒxxx * â˜ƒ) | â˜ƒ << 24);
         }

         RenderSystem.setShaderTexture(0, this.location);
         RenderSystem.setShaderColor(â˜ƒ, â˜ƒ, â˜ƒ, (float)â˜ƒ / 255.0F);
         GuiComponent.blit(â˜ƒ, 2, 2, 12, 12, 8.0F, 8.0F, 8, 8, 64, 64);
         GuiComponent.blit(â˜ƒ, 2, 2, 12, 12, 40.0F, 8.0F, 8, 8, 64, 64);
      }

      @Override
      public boolean isEnabled() {
         return !this.players.isEmpty();
      }
   }
}
