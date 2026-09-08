package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsTextureManager;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsPlayerScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation OP_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/op_icon.png");
   private static final ResourceLocation USER_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/user_icon.png");
   private static final ResourceLocation CROSS_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/cross_player_icon.png");
   private static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/options_background.png");
   private static final Component NORMAL_USER_TOOLTIP = new TranslatableComponent("mco.configure.world.invites.normal.tooltip");
   private static final Component OP_TOOLTIP = new TranslatableComponent("mco.configure.world.invites.ops.tooltip");
   private static final Component REMOVE_ENTRY_TOOLTIP = new TranslatableComponent("mco.configure.world.invites.remove.tooltip");
   private static final Component INVITED_LABEL = new TranslatableComponent("mco.configure.world.invited");
   private Component toolTip;
   private final RealmsConfigureWorldScreen lastScreen;
   final RealmsServer serverData;
   private RealmsPlayerScreen.InvitedObjectSelectionList invitedObjectSelectionList;
   int column1X;
   int columnWidth;
   private int column2X;
   private Button removeButton;
   private Button opdeopButton;
   private int selectedInvitedIndex = -1;
   private String selectedInvited;
   int player = -1;
   private boolean stateChanged;
   RealmsPlayerScreen.UserAction hoveredUserAction = RealmsPlayerScreen.UserAction.NONE;

   public RealmsPlayerScreen(RealmsConfigureWorldScreen var1, RealmsServer var2) {
      super(new TranslatableComponent("mco.configure.world.players.title"));
      this.lastScreen = â˜ƒ;
      this.serverData = â˜ƒ;
   }

   @Override
   public void init() {
      this.column1X = this.width / 2 - 160;
      this.columnWidth = 150;
      this.column2X = this.width / 2 + 12;
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.invitedObjectSelectionList = new RealmsPlayerScreen.InvitedObjectSelectionList();
      this.invitedObjectSelectionList.setLeftPos(this.column1X);
      this.addWidget(this.invitedObjectSelectionList);

      for(PlayerInfo â˜ƒ : this.serverData.players) {
         this.invitedObjectSelectionList.addEntry(â˜ƒ);
      }

      this.addRenderableWidget(
         new Button(
            this.column2X,
            row(1),
            this.columnWidth + 10,
            20,
            new TranslatableComponent("mco.configure.world.buttons.invite"),
            var1 -> this.minecraft.setScreen(new RealmsInviteScreen(this.lastScreen, this, this.serverData))
         )
      );
      this.removeButton = this.addRenderableWidget(
         new Button(
            this.column2X,
            row(7),
            this.columnWidth + 10,
            20,
            new TranslatableComponent("mco.configure.world.invites.remove.tooltip"),
            var1 -> this.uninvite(this.player)
         )
      );
      this.opdeopButton = this.addRenderableWidget(
         new Button(this.column2X, row(9), this.columnWidth + 10, 20, new TranslatableComponent("mco.configure.world.invites.ops.tooltip"), var1 -> {
            if (((PlayerInfo)this.serverData.players.get(this.player)).isOperator()) {
               this.deop(this.player);
            } else {
               this.op(this.player);
            }
         })
      );
      this.addRenderableWidget(
         new Button(
            this.column2X + this.columnWidth / 2 + 2, row(12), this.columnWidth / 2 + 10 - 2, 20, CommonComponents.GUI_BACK, var1 -> this.backButtonClicked()
         )
      );
      this.updateButtonStates();
   }

   void updateButtonStates() {
      this.removeButton.visible = this.shouldRemoveAndOpdeopButtonBeVisible(this.player);
      this.opdeopButton.visible = this.shouldRemoveAndOpdeopButtonBeVisible(this.player);
   }

   private boolean shouldRemoveAndOpdeopButtonBeVisible(int var1) {
      return â˜ƒ != -1;
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.backButtonClicked();
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void backButtonClicked() {
      if (this.stateChanged) {
         this.minecraft.setScreen(this.lastScreen.getNewScreen());
      } else {
         this.minecraft.setScreen(this.lastScreen);
      }
   }

   void op(int var1) {
      this.updateButtonStates();
      RealmsClient â˜ƒ = RealmsClient.create();
      String â˜ƒx = ((PlayerInfo)this.serverData.players.get(â˜ƒ)).getUuid();

      try {
         this.updateOps(â˜ƒ.op(this.serverData.id, â˜ƒx));
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't op the user");
      }
   }

   void deop(int var1) {
      this.updateButtonStates();
      RealmsClient â˜ƒ = RealmsClient.create();
      String â˜ƒx = ((PlayerInfo)this.serverData.players.get(â˜ƒ)).getUuid();

      try {
         this.updateOps(â˜ƒ.deop(this.serverData.id, â˜ƒx));
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't deop the user");
      }
   }

   private void updateOps(Ops var1) {
      for(PlayerInfo â˜ƒ : this.serverData.players) {
         â˜ƒ.setOperator(â˜ƒ.ops.contains(â˜ƒ.getName()));
      }
   }

   void uninvite(int var1) {
      this.updateButtonStates();
      if (â˜ƒ >= 0 && â˜ƒ < this.serverData.players.size()) {
         PlayerInfo â˜ƒ = (PlayerInfo)this.serverData.players.get(â˜ƒ);
         this.selectedInvited = â˜ƒ.getUuid();
         this.selectedInvitedIndex = â˜ƒ;
         RealmsConfirmScreen â˜ƒx = new RealmsConfirmScreen(var1x -> {
            if (var1x) {
               RealmsClient â˜ƒ = RealmsClient.create();

               try {
                  â˜ƒ.uninvite(this.serverData.id, this.selectedInvited);
               } catch (RealmsServiceException var4) {
                  LOGGER.error("Couldn't uninvite user");
               }

               this.deleteFromInvitedList(this.selectedInvitedIndex);
               this.player = -1;
               this.updateButtonStates();
            }

            this.stateChanged = true;
            this.minecraft.setScreen(this);
         }, new TextComponent("Question"), new TranslatableComponent("mco.configure.world.uninvite.question").append(" '").append(â˜ƒ.getName()).append("' ?"));
         this.minecraft.setScreen(â˜ƒx);
      }
   }

   private void deleteFromInvitedList(int var1) {
      this.serverData.players.remove(â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.toolTip = null;
      this.hoveredUserAction = RealmsPlayerScreen.UserAction.NONE;
      this.renderBackground(â˜ƒ);
      if (this.invitedObjectSelectionList != null) {
         this.invitedObjectSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 17, 16777215);
      int â˜ƒ = row(12) + 20;
      Tesselator â˜ƒx = Tesselator.getInstance();
      BufferBuilder â˜ƒxx = â˜ƒx.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      RenderSystem.setShaderTexture(0, OPTIONS_BACKGROUND);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒxxx = 32.0F;
      â˜ƒxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      â˜ƒxx.vertex(0.0, (double)this.height, 0.0).uv(0.0F, (float)(this.height - â˜ƒ) / 32.0F + 0.0F).color(64, 64, 64, 255).endVertex();
      â˜ƒxx.vertex((double)this.width, (double)this.height, 0.0)
         .uv((float)this.width / 32.0F, (float)(this.height - â˜ƒ) / 32.0F + 0.0F)
         .color(64, 64, 64, 255)
         .endVertex();
      â˜ƒxx.vertex((double)this.width, (double)â˜ƒ, 0.0).uv((float)this.width / 32.0F, 0.0F).color(64, 64, 64, 255).endVertex();
      â˜ƒxx.vertex(0.0, (double)â˜ƒ, 0.0).uv(0.0F, 0.0F).color(64, 64, 64, 255).endVertex();
      â˜ƒx.end();
      if (this.serverData != null && this.serverData.players != null) {
         this.font
            .draw(
               â˜ƒ,
               new TextComponent("").append(INVITED_LABEL).append(" (").append(Integer.toString(this.serverData.players.size())).append(")"),
               (float)this.column1X,
               (float)row(0),
               10526880
            );
      } else {
         this.font.draw(â˜ƒ, INVITED_LABEL, (float)this.column1X, (float)row(0), 10526880);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.serverData != null) {
         this.renderMousehoverTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
      }
   }

   protected void renderMousehoverTooltip(PoseStack var1, @Nullable Component var2, int var3, int var4) {
      if (â˜ƒ != null) {
         int â˜ƒ = â˜ƒ + 12;
         int â˜ƒx = â˜ƒ - 12;
         int â˜ƒxx = this.font.width(â˜ƒ);
         this.fillGradient(â˜ƒ, â˜ƒ - 3, â˜ƒx - 3, â˜ƒ + â˜ƒxx + 3, â˜ƒx + 8 + 3, -1073741824, -1073741824);
         this.font.drawShadow(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)â˜ƒx, 16777215);
      }
   }

   void drawRemoveIcon(PoseStack var1, int var2, int var3, int var4, int var5) {
      boolean â˜ƒ = â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ < row(12) + 20 && â˜ƒ > row(1);
      RenderSystem.setShaderTexture(0, CROSS_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒx = â˜ƒ ? 7.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, â˜ƒx, 8, 7, 8, 14);
      if (â˜ƒ) {
         this.toolTip = REMOVE_ENTRY_TOOLTIP;
         this.hoveredUserAction = RealmsPlayerScreen.UserAction.REMOVE;
      }
   }

   void drawOpped(PoseStack var1, int var2, int var3, int var4, int var5) {
      boolean â˜ƒ = â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ < row(12) + 20 && â˜ƒ > row(1);
      RenderSystem.setShaderTexture(0, OP_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒx = â˜ƒ ? 8.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, â˜ƒx, 8, 8, 8, 16);
      if (â˜ƒ) {
         this.toolTip = OP_TOOLTIP;
         this.hoveredUserAction = RealmsPlayerScreen.UserAction.TOGGLE_OP;
      }
   }

   void drawNormal(PoseStack var1, int var2, int var3, int var4, int var5) {
      boolean â˜ƒ = â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 9 && â˜ƒ < row(12) + 20 && â˜ƒ > row(1);
      RenderSystem.setShaderTexture(0, USER_ICON_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒx = â˜ƒ ? 8.0F : 0.0F;
      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, â˜ƒx, 8, 8, 8, 16);
      if (â˜ƒ) {
         this.toolTip = NORMAL_USER_TOOLTIP;
         this.hoveredUserAction = RealmsPlayerScreen.UserAction.TOGGLE_OP;
      }
   }

   class Entry extends ObjectSelectionList.Entry<RealmsPlayerScreen.Entry> {
      private final PlayerInfo playerInfo;

      public Entry(PlayerInfo var2) {
         this.playerInfo = â˜ƒ;
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderInvitedItem(â˜ƒ, this.playerInfo, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void renderInvitedItem(PoseStack var1, PlayerInfo var2, int var3, int var4, int var5, int var6) {
         int â˜ƒ;
         if (!â˜ƒ.getAccepted()) {
            â˜ƒ = 10526880;
         } else if (â˜ƒ.getOnline()) {
            â˜ƒ = 8388479;
         } else {
            â˜ƒ = 16777215;
         }

         RealmsPlayerScreen.this.font.draw(â˜ƒ, â˜ƒ.getName(), (float)(RealmsPlayerScreen.this.column1X + 3 + 12), (float)(â˜ƒ + 1), â˜ƒ);
         if (â˜ƒ.isOperator()) {
            RealmsPlayerScreen.this.drawOpped(â˜ƒ, RealmsPlayerScreen.this.column1X + RealmsPlayerScreen.this.columnWidth - 10, â˜ƒ + 1, â˜ƒ, â˜ƒ);
         } else {
            RealmsPlayerScreen.this.drawNormal(â˜ƒ, RealmsPlayerScreen.this.column1X + RealmsPlayerScreen.this.columnWidth - 10, â˜ƒ + 1, â˜ƒ, â˜ƒ);
         }

         RealmsPlayerScreen.this.drawRemoveIcon(â˜ƒ, RealmsPlayerScreen.this.column1X + RealmsPlayerScreen.this.columnWidth - 22, â˜ƒ + 2, â˜ƒ, â˜ƒ);
         RealmsTextureManager.withBoundFace(â˜ƒ.getUuid(), () -> {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.blit(â˜ƒ, RealmsPlayerScreen.this.column1X + 2 + 2, â˜ƒ + 1, 8, 8, 8.0F, 8.0F, 8, 8, 64, 64);
            GuiComponent.blit(â˜ƒ, RealmsPlayerScreen.this.column1X + 2 + 2, â˜ƒ + 1, 8, 8, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      @Override
      public Component getNarration() {
         return new TranslatableComponent("narrator.select", this.playerInfo.getName());
      }
   }

   class InvitedObjectSelectionList extends RealmsObjectSelectionList<RealmsPlayerScreen.Entry> {
      public InvitedObjectSelectionList() {
         super(RealmsPlayerScreen.this.columnWidth + 10, RealmsPlayerScreen.row(12) + 20, RealmsPlayerScreen.row(1), RealmsPlayerScreen.row(12) + 20, 13);
      }

      public void addEntry(PlayerInfo var1) {
         this.addEntry(RealmsPlayerScreen.this.new Entry(â˜ƒ));
      }

      @Override
      public int getRowWidth() {
         return (int)((double)this.width * 1.0);
      }

      @Override
      public boolean isFocused() {
         return RealmsPlayerScreen.this.getFocused() == this;
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (â˜ƒ == 0 && â˜ƒ < (double)this.getScrollbarPosition() && â˜ƒ >= (double)this.y0 && â˜ƒ <= (double)this.y1) {
            int â˜ƒ = RealmsPlayerScreen.this.column1X;
            int â˜ƒx = RealmsPlayerScreen.this.column1X + RealmsPlayerScreen.this.columnWidth;
            int â˜ƒxx = (int)Math.floor(â˜ƒ - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int â˜ƒxxx = â˜ƒxx / this.itemHeight;
            if (â˜ƒ >= (double)â˜ƒ && â˜ƒ <= (double)â˜ƒx && â˜ƒxxx >= 0 && â˜ƒxx >= 0 && â˜ƒxxx < this.getItemCount()) {
               this.selectItem(â˜ƒxxx);
               this.itemClicked(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒ, this.width);
            }

            return true;
         } else {
            return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      public void itemClicked(int var1, int var2, double var3, double var5, int var7) {
         if (â˜ƒ >= 0
            && â˜ƒ <= RealmsPlayerScreen.this.serverData.players.size()
            && RealmsPlayerScreen.this.hoveredUserAction != RealmsPlayerScreen.UserAction.NONE) {
            if (RealmsPlayerScreen.this.hoveredUserAction == RealmsPlayerScreen.UserAction.TOGGLE_OP) {
               if (((PlayerInfo)RealmsPlayerScreen.this.serverData.players.get(â˜ƒ)).isOperator()) {
                  RealmsPlayerScreen.this.deop(â˜ƒ);
               } else {
                  RealmsPlayerScreen.this.op(â˜ƒ);
               }
            } else if (RealmsPlayerScreen.this.hoveredUserAction == RealmsPlayerScreen.UserAction.REMOVE) {
               RealmsPlayerScreen.this.uninvite(â˜ƒ);
            }
         }
      }

      @Override
      public void selectItem(int var1) {
         super.selectItem(â˜ƒ);
         this.selectInviteListItem(â˜ƒ);
      }

      public void selectInviteListItem(int var1) {
         RealmsPlayerScreen.this.player = â˜ƒ;
         RealmsPlayerScreen.this.updateButtonStates();
      }

      public void setSelected(@Nullable RealmsPlayerScreen.Entry var1) {
         super.setSelected(â˜ƒ);
         RealmsPlayerScreen.this.player = this.children().indexOf(â˜ƒ);
         RealmsPlayerScreen.this.updateButtonStates();
      }

      @Override
      public void renderBackground(PoseStack var1) {
         RealmsPlayerScreen.this.renderBackground(â˜ƒ);
      }

      @Override
      public int getScrollbarPosition() {
         return RealmsPlayerScreen.this.column1X + this.width - 5;
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 13;
      }
   }

   static enum UserAction {
      TOGGLE_OP,
      REMOVE,
      NONE;
   }
}
