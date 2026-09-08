package net.minecraft.client.gui.screens.debug;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class GameModeSwitcherScreen extends Screen {
   static final ResourceLocation GAMEMODE_SWITCHER_LOCATION = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
   private static final int SPRITE_SHEET_WIDTH = 128;
   private static final int SPRITE_SHEET_HEIGHT = 128;
   private static final int SLOT_AREA = 26;
   private static final int SLOT_PADDING = 5;
   private static final int SLOT_AREA_PADDED = 31;
   private static final int HELP_TIPS_OFFSET_Y = 5;
   private static final int ALL_SLOTS_WIDTH = GameModeSwitcherScreen.GameModeIcon.values().length * 31 - 5;
   private static final Component SELECT_KEY = new TranslatableComponent(
      "debug.gamemodes.select_next", new TranslatableComponent("debug.gamemodes.press_f4").withStyle(ChatFormatting.AQUA)
   );
   private final Optional<GameModeSwitcherScreen.GameModeIcon> previousHovered;
   private Optional<GameModeSwitcherScreen.GameModeIcon> currentlyHovered = Optional.empty();
   private int firstMouseX;
   private int firstMouseY;
   private boolean setFirstMousePos;
   private final List<GameModeSwitcherScreen.GameModeSlot> slots = Lists.<GameModeSwitcherScreen.GameModeSlot>newArrayList();

   public GameModeSwitcherScreen() {
      super(NarratorChatListener.NO_TITLE);
      this.previousHovered = GameModeSwitcherScreen.GameModeIcon.getFromGameType(this.getDefaultSelected());
   }

   private GameType getDefaultSelected() {
      MultiPlayerGameMode â˜ƒ = Minecraft.getInstance().gameMode;
      GameType â˜ƒx = â˜ƒ.getPreviousPlayerMode();
      if (â˜ƒx != null) {
         return â˜ƒx;
      } else {
         return â˜ƒ.getPlayerMode() == GameType.CREATIVE ? GameType.SURVIVAL : GameType.CREATIVE;
      }
   }

   @Override
   protected void init() {
      super.init();
      this.currentlyHovered = this.previousHovered.isPresent()
         ? this.previousHovered
         : GameModeSwitcherScreen.GameModeIcon.getFromGameType(this.minecraft.gameMode.getPlayerMode());

      for(int â˜ƒ = 0; â˜ƒ < GameModeSwitcherScreen.GameModeIcon.VALUES.length; ++â˜ƒ) {
         GameModeSwitcherScreen.GameModeIcon â˜ƒx = GameModeSwitcherScreen.GameModeIcon.VALUES[â˜ƒ];
         this.slots.add(new GameModeSwitcherScreen.GameModeSlot(â˜ƒx, this.width / 2 - ALL_SLOTS_WIDTH / 2 + â˜ƒ * 31, this.height / 2 - 31));
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      if (!this.checkToClose()) {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         â˜ƒ.pushPose();
         RenderSystem.enableBlend();
         RenderSystem.setShaderTexture(0, GAMEMODE_SWITCHER_LOCATION);
         int â˜ƒ = this.width / 2 - 62;
         int â˜ƒx = this.height / 2 - 31 - 27;
         blit(â˜ƒ, â˜ƒ, â˜ƒx, 0.0F, 0.0F, 125, 75, 128, 128);
         â˜ƒ.popPose();
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.currentlyHovered.ifPresent(var2x -> drawCenteredString(â˜ƒ, this.font, var2x.getName(), this.width / 2, this.height / 2 - 31 - 20, -1));
         drawCenteredString(â˜ƒ, this.font, SELECT_KEY, this.width / 2, this.height / 2 + 5, 16777215);
         if (!this.setFirstMousePos) {
            this.firstMouseX = â˜ƒ;
            this.firstMouseY = â˜ƒ;
            this.setFirstMousePos = true;
         }

         boolean â˜ƒ = this.firstMouseX == â˜ƒ && this.firstMouseY == â˜ƒ;

         for(GameModeSwitcherScreen.GameModeSlot â˜ƒx : this.slots) {
            â˜ƒx.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            this.currentlyHovered.ifPresent(var1x -> â˜ƒ.setSelected(var1x == â˜ƒ.icon));
            if (!â˜ƒ && â˜ƒx.isHovered()) {
               this.currentlyHovered = Optional.of(â˜ƒx.icon);
            }
         }
      }
   }

   private void switchToHoveredGameMode() {
      switchToHoveredGameMode(this.minecraft, this.currentlyHovered);
   }

   private static void switchToHoveredGameMode(Minecraft var0, Optional<GameModeSwitcherScreen.GameModeIcon> var1) {
      if (â˜ƒ.gameMode != null && â˜ƒ.player != null && â˜ƒ.isPresent()) {
         Optional<GameModeSwitcherScreen.GameModeIcon> â˜ƒ = GameModeSwitcherScreen.GameModeIcon.getFromGameType(â˜ƒ.gameMode.getPlayerMode());
         GameModeSwitcherScreen.GameModeIcon â˜ƒx = (GameModeSwitcherScreen.GameModeIcon)â˜ƒ.get();
         if (â˜ƒ.isPresent() && â˜ƒ.player.hasPermissions(2) && â˜ƒx != â˜ƒ.get()) {
            â˜ƒ.player.chat(â˜ƒx.getCommand());
         }
      }
   }

   private boolean checkToClose() {
      if (!InputConstants.isKeyDown(this.minecraft.getWindow().getWindow(), 292)) {
         this.switchToHoveredGameMode();
         this.minecraft.setScreen(null);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 293 && this.currentlyHovered.isPresent()) {
         this.setFirstMousePos = false;
         this.currentlyHovered = ((GameModeSwitcherScreen.GameModeIcon)this.currentlyHovered.get()).getNext();
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   static enum GameModeIcon {
      CREATIVE(new TranslatableComponent("gameMode.creative"), "/gamemode creative", new ItemStack(Blocks.GRASS_BLOCK)),
      SURVIVAL(new TranslatableComponent("gameMode.survival"), "/gamemode survival", new ItemStack(Items.IRON_SWORD)),
      ADVENTURE(new TranslatableComponent("gameMode.adventure"), "/gamemode adventure", new ItemStack(Items.MAP)),
      SPECTATOR(new TranslatableComponent("gameMode.spectator"), "/gamemode spectator", new ItemStack(Items.ENDER_EYE));

      protected static final GameModeSwitcherScreen.GameModeIcon[] VALUES = values();
      private static final int ICON_AREA = 16;
      protected static final int ICON_TOP_LEFT = 5;
      final Component name;
      final String command;
      final ItemStack renderStack;

      private GameModeIcon(Component var3, String var4, ItemStack var5) {
         this.name = â˜ƒ;
         this.command = â˜ƒ;
         this.renderStack = â˜ƒ;
      }

      void drawIcon(ItemRenderer var1, int var2, int var3) {
         â˜ƒ.renderAndDecorateItem(this.renderStack, â˜ƒ, â˜ƒ);
      }

      Component getName() {
         return this.name;
      }

      String getCommand() {
         return this.command;
      }

      Optional<GameModeSwitcherScreen.GameModeIcon> getNext() {
         switch(this) {
            case CREATIVE:
               return Optional.of(SURVIVAL);
            case SURVIVAL:
               return Optional.of(ADVENTURE);
            case ADVENTURE:
               return Optional.of(SPECTATOR);
            default:
               return Optional.of(CREATIVE);
         }
      }

      static Optional<GameModeSwitcherScreen.GameModeIcon> getFromGameType(GameType var0) {
         switch(â˜ƒ) {
            case SPECTATOR:
               return Optional.of(SPECTATOR);
            case SURVIVAL:
               return Optional.of(SURVIVAL);
            case CREATIVE:
               return Optional.of(CREATIVE);
            case ADVENTURE:
               return Optional.of(ADVENTURE);
            default:
               return Optional.empty();
         }
      }
   }

   public class GameModeSlot extends AbstractWidget {
      final GameModeSwitcherScreen.GameModeIcon icon;
      private boolean isSelected;

      public GameModeSlot(GameModeSwitcherScreen.GameModeIcon var2, int var3, int var4) {
         super(â˜ƒ, â˜ƒ, 26, 26, â˜ƒ.getName());
         this.icon = â˜ƒ;
      }

      @Override
      public void renderButton(PoseStack var1, int var2, int var3, float var4) {
         Minecraft â˜ƒ = Minecraft.getInstance();
         this.drawSlot(â˜ƒ, â˜ƒ.getTextureManager());
         this.icon.drawIcon(GameModeSwitcherScreen.this.itemRenderer, this.x + 5, this.y + 5);
         if (this.isSelected) {
            this.drawSelection(â˜ƒ, â˜ƒ.getTextureManager());
         }
      }

      @Override
      public void updateNarration(NarrationElementOutput var1) {
         this.defaultButtonNarrationText(â˜ƒ);
      }

      @Override
      public boolean isHovered() {
         return super.isHovered() || this.isSelected;
      }

      public void setSelected(boolean var1) {
         this.isSelected = â˜ƒ;
      }

      private void drawSlot(PoseStack var1, TextureManager var2) {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, GameModeSwitcherScreen.GAMEMODE_SWITCHER_LOCATION);
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)this.x, (double)this.y, 0.0);
         blit(â˜ƒ, 0, 0, 0.0F, 75.0F, 26, 26, 128, 128);
         â˜ƒ.popPose();
      }

      private void drawSelection(PoseStack var1, TextureManager var2) {
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, GameModeSwitcherScreen.GAMEMODE_SWITCHER_LOCATION);
         â˜ƒ.pushPose();
         â˜ƒ.translate((double)this.x, (double)this.y, 0.0);
         blit(â˜ƒ, 0, 0, 26.0F, 75.0F, 26, 26, 128, 128);
         â˜ƒ.popPose();
      }
   }
}
