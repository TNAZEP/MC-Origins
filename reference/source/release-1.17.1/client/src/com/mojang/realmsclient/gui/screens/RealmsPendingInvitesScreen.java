package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RowButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsPendingInvitesScreen extends RealmsScreen {
   static final Logger LOGGER = LogManager.getLogger();
   static final ResourceLocation ACCEPT_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/accept_icon.png");
   static final ResourceLocation REJECT_ICON_LOCATION = new ResourceLocation("realms", "textures/gui/realms/reject_icon.png");
   private static final Component NO_PENDING_INVITES_TEXT = new TranslatableComponent("mco.invites.nopending");
   static final Component ACCEPT_INVITE_TOOLTIP = new TranslatableComponent("mco.invites.button.accept");
   static final Component REJECT_INVITE_TOOLTIP = new TranslatableComponent("mco.invites.button.reject");
   private final Screen lastScreen;
   @Nullable
   Component toolTip;
   boolean loaded;
   RealmsPendingInvitesScreen.PendingInvitationSelectionList pendingInvitationSelectionList;
   int selectedInvite = -1;
   private Button acceptButton;
   private Button rejectButton;

   public RealmsPendingInvitesScreen(Screen var1) {
      super(new TranslatableComponent("mco.invites.title"));
      this.lastScreen = â˜ƒ;
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.pendingInvitationSelectionList = new RealmsPendingInvitesScreen.PendingInvitationSelectionList();
      (new Thread("Realms-pending-invitations-fetcher") {
            public void run() {
               RealmsClient â˜ƒ = RealmsClient.create();
   
               try {
                  List<PendingInvite> â˜ƒx = â˜ƒ.pendingInvites().pendingInvites;
                  List<RealmsPendingInvitesScreen.Entry> â˜ƒxx = (List)â˜ƒx.stream()
                     .map(var1x -> RealmsPendingInvitesScreen.this.new Entry(var1x))
                     .collect(Collectors.toList());
                  RealmsPendingInvitesScreen.this.minecraft.execute(() -> RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.replaceEntries(â˜ƒ));
               } catch (RealmsServiceException var7) {
                  RealmsPendingInvitesScreen.LOGGER.error("Couldn't list invites");
               } finally {
                  RealmsPendingInvitesScreen.this.loaded = true;
               }
            }
         })
         .start();
      this.addWidget(this.pendingInvitationSelectionList);
      this.acceptButton = this.addRenderableWidget(
         new Button(this.width / 2 - 174, this.height - 32, 100, 20, new TranslatableComponent("mco.invites.button.accept"), var1 -> {
            this.accept(this.selectedInvite);
            this.selectedInvite = -1;
            this.updateButtonStates();
         })
      );
      this.addRenderableWidget(
         new Button(
            this.width / 2 - 50, this.height - 32, 100, 20, CommonComponents.GUI_DONE, var1 -> this.minecraft.setScreen(new RealmsMainScreen(this.lastScreen))
         )
      );
      this.rejectButton = this.addRenderableWidget(
         new Button(this.width / 2 + 74, this.height - 32, 100, 20, new TranslatableComponent("mco.invites.button.reject"), var1 -> {
            this.reject(this.selectedInvite);
            this.selectedInvite = -1;
            this.updateButtonStates();
         })
      );
      this.updateButtonStates();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256) {
         this.minecraft.setScreen(new RealmsMainScreen(this.lastScreen));
         return true;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   void updateList(int var1) {
      this.pendingInvitationSelectionList.removeAtIndex(â˜ƒ);
   }

   void reject(final int var1) {
      if (â˜ƒ < this.pendingInvitationSelectionList.getItemCount()) {
         (new Thread("Realms-reject-invitation") {
               public void run() {
                  try {
                     RealmsClient â˜ƒ = RealmsClient.create();
                     â˜ƒ.rejectInvitation(
                        ((RealmsPendingInvitesScreen.Entry)RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.children().get(â˜ƒ)).pendingInvite.invitationId
                     );
                     RealmsPendingInvitesScreen.this.minecraft.execute(() -> RealmsPendingInvitesScreen.this.updateList(â˜ƒ));
                  } catch (RealmsServiceException var2) {
                     RealmsPendingInvitesScreen.LOGGER.error("Couldn't reject invite");
                  }
               }
            })
            .start();
      }
   }

   void accept(final int var1) {
      if (â˜ƒ < this.pendingInvitationSelectionList.getItemCount()) {
         (new Thread("Realms-accept-invitation") {
               public void run() {
                  try {
                     RealmsClient â˜ƒ = RealmsClient.create();
                     â˜ƒ.acceptInvitation(
                        ((RealmsPendingInvitesScreen.Entry)RealmsPendingInvitesScreen.this.pendingInvitationSelectionList.children().get(â˜ƒ)).pendingInvite.invitationId
                     );
                     RealmsPendingInvitesScreen.this.minecraft.execute(() -> RealmsPendingInvitesScreen.this.updateList(â˜ƒ));
                  } catch (RealmsServiceException var2) {
                     RealmsPendingInvitesScreen.LOGGER.error("Couldn't accept invite");
                  }
               }
            })
            .start();
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.toolTip = null;
      this.renderBackground(â˜ƒ);
      this.pendingInvitationSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 12, 16777215);
      if (this.toolTip != null) {
         this.renderMousehoverTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
      }

      if (this.pendingInvitationSelectionList.getItemCount() == 0 && this.loaded) {
         drawCenteredString(â˜ƒ, this.font, NO_PENDING_INVITES_TEXT, this.width / 2, this.height / 2 - 20, 16777215);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
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

   void updateButtonStates() {
      this.acceptButton.visible = this.shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
      this.rejectButton.visible = this.shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
   }

   private boolean shouldAcceptAndRejectButtonBeVisible(int var1) {
      return â˜ƒ != -1;
   }

   class Entry extends ObjectSelectionList.Entry<RealmsPendingInvitesScreen.Entry> {
      private static final int TEXT_LEFT = 38;
      final PendingInvite pendingInvite;
      private final List<RowButton> rowButtons;

      Entry(PendingInvite var2) {
         this.pendingInvite = â˜ƒ;
         this.rowButtons = Arrays.asList(new RealmsPendingInvitesScreen.Entry.AcceptRowButton(), new RealmsPendingInvitesScreen.Entry.RejectRowButton());
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderPendingInvitationItem(â˜ƒ, this.pendingInvite, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         RowButton.rowButtonMouseClicked(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, this, this.rowButtons, â˜ƒ, â˜ƒ, â˜ƒ);
         return true;
      }

      private void renderPendingInvitationItem(PoseStack var1, PendingInvite var2, int var3, int var4, int var5, int var6) {
         RealmsPendingInvitesScreen.this.font.draw(â˜ƒ, â˜ƒ.worldName, (float)(â˜ƒ + 38), (float)(â˜ƒ + 1), 16777215);
         RealmsPendingInvitesScreen.this.font.draw(â˜ƒ, â˜ƒ.worldOwnerName, (float)(â˜ƒ + 38), (float)(â˜ƒ + 12), 7105644);
         RealmsPendingInvitesScreen.this.font
            .draw(â˜ƒ, RealmsUtil.convertToAgePresentationFromInstant(â˜ƒ.date), (float)(â˜ƒ + 38), (float)(â˜ƒ + 24), 7105644);
         RowButton.drawButtonsInRow(â˜ƒ, this.rowButtons, RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         RealmsTextureManager.withBoundFace(â˜ƒ.worldOwnerUuid, () -> {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
         });
      }

      @Override
      public Component getNarration() {
         Component â˜ƒ = CommonComponents.joinLines(
            new TextComponent(this.pendingInvite.worldName),
            new TextComponent(this.pendingInvite.worldOwnerName),
            new TextComponent(RealmsUtil.convertToAgePresentationFromInstant(this.pendingInvite.date))
         );
         return new TranslatableComponent("narrator.select", â˜ƒ);
      }

      class AcceptRowButton extends RowButton {
         AcceptRowButton() {
            super(15, 15, 215, 5);
         }

         @Override
         protected void draw(PoseStack var1, int var2, int var3, boolean var4) {
            RenderSystem.setShaderTexture(0, RealmsPendingInvitesScreen.ACCEPT_ICON_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            float â˜ƒ = â˜ƒ ? 19.0F : 0.0F;
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 18, 18, 37, 18);
            if (â˜ƒ) {
               RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.ACCEPT_INVITE_TOOLTIP;
            }
         }

         @Override
         public void onClick(int var1) {
            RealmsPendingInvitesScreen.this.accept(â˜ƒ);
         }
      }

      class RejectRowButton extends RowButton {
         RejectRowButton() {
            super(15, 15, 235, 5);
         }

         @Override
         protected void draw(PoseStack var1, int var2, int var3, boolean var4) {
            RenderSystem.setShaderTexture(0, RealmsPendingInvitesScreen.REJECT_ICON_LOCATION);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            float â˜ƒ = â˜ƒ ? 19.0F : 0.0F;
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 18, 18, 37, 18);
            if (â˜ƒ) {
               RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.REJECT_INVITE_TOOLTIP;
            }
         }

         @Override
         public void onClick(int var1) {
            RealmsPendingInvitesScreen.this.reject(â˜ƒ);
         }
      }
   }

   class PendingInvitationSelectionList extends RealmsObjectSelectionList<RealmsPendingInvitesScreen.Entry> {
      public PendingInvitationSelectionList() {
         super(RealmsPendingInvitesScreen.this.width, RealmsPendingInvitesScreen.this.height, 32, RealmsPendingInvitesScreen.this.height - 40, 36);
      }

      public void removeAtIndex(int var1) {
         this.remove(â˜ƒ);
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 36;
      }

      @Override
      public int getRowWidth() {
         return 260;
      }

      @Override
      public boolean isFocused() {
         return RealmsPendingInvitesScreen.this.getFocused() == this;
      }

      @Override
      public void renderBackground(PoseStack var1) {
         RealmsPendingInvitesScreen.this.renderBackground(â˜ƒ);
      }

      @Override
      public void selectItem(int var1) {
         super.selectItem(â˜ƒ);
         this.selectInviteListItem(â˜ƒ);
      }

      public void selectInviteListItem(int var1) {
         RealmsPendingInvitesScreen.this.selectedInvite = â˜ƒ;
         RealmsPendingInvitesScreen.this.updateButtonStates();
      }

      public void setSelected(@Nullable RealmsPendingInvitesScreen.Entry var1) {
         super.setSelected(â˜ƒ);
         RealmsPendingInvitesScreen.this.selectedInvite = this.children().indexOf(â˜ƒ);
         RealmsPendingInvitesScreen.this.updateButtonStates();
      }
   }
}
