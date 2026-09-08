package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.RealmsWorldSlotButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.task.OpenServerTask;
import com.mojang.realmsclient.util.task.SwitchSlotTask;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBrokenWorldScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int DEFAULT_BUTTON_WIDTH = 80;
   private final Screen lastScreen;
   private final RealmsMainScreen mainScreen;
   private RealmsServer serverData;
   private final long serverId;
   private final Component[] message = new Component[]{
      new TranslatableComponent("mco.brokenworld.message.line1"), new TranslatableComponent("mco.brokenworld.message.line2")
   };
   private int leftX;
   private int rightX;
   private final List<Integer> slotsThatHasBeenDownloaded = Lists.newArrayList();
   private int animTick;

   public RealmsBrokenWorldScreen(Screen var1, RealmsMainScreen var2, long var3, boolean var5) {
      super(â˜ƒ ? new TranslatableComponent("mco.brokenworld.minigame.title") : new TranslatableComponent("mco.brokenworld.title"));
      this.lastScreen = â˜ƒ;
      this.mainScreen = â˜ƒ;
      this.serverId = â˜ƒ;
   }

   @Override
   public void init() {
      this.leftX = this.width / 2 - 150;
      this.rightX = this.width / 2 + 190;
      this.addRenderableWidget(new Button(this.rightX - 80 + 8, row(13) - 5, 70, 20, CommonComponents.GUI_BACK, var1 -> this.backButtonClicked()));
      if (this.serverData == null) {
         this.fetchServerData(this.serverId);
      } else {
         this.addButtons();
      }

      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
   }

   @Override
   public Component getNarrationMessage() {
      return ComponentUtils.formatList(
         (Collection<? extends Component>)Stream.concat(Stream.of(this.title), Stream.of(this.message)).collect(Collectors.toList()), new TextComponent(" ")
      );
   }

   private void addButtons() {
      for(Entry<Integer, RealmsWorldOptions> â˜ƒ : this.serverData.slots.entrySet()) {
         int â˜ƒxx = â˜ƒ.getKey();
         boolean â˜ƒxxx = â˜ƒxx != this.serverData.activeSlot || this.serverData.worldType == RealmsServer.WorldType.MINIGAME;
         Button â˜ƒx;
         if (â˜ƒxxx) {
            â˜ƒx = new Button(
               this.getFramePositionX(â˜ƒxx),
               row(8),
               80,
               20,
               new TranslatableComponent("mco.brokenworld.play"),
               var2x -> {
                  if (((RealmsWorldOptions)this.serverData.slots.get(â˜ƒ)).empty) {
                     RealmsResetWorldScreen â˜ƒ = new RealmsResetWorldScreen(
                        this,
                        this.serverData,
                        new TranslatableComponent("mco.configure.world.switch.slot"),
                        new TranslatableComponent("mco.configure.world.switch.slot.subtitle"),
                        10526880,
                        CommonComponents.GUI_CANCEL,
                        this::doSwitchOrReset,
                        () -> {
                           this.minecraft.setScreen(this);
                           this.doSwitchOrReset();
                        }
                     );
                     â˜ƒ.setSlot(â˜ƒ);
                     â˜ƒ.setResetTitle(new TranslatableComponent("mco.create.world.reset.title"));
                     this.minecraft.setScreen(â˜ƒ);
                  } else {
                     this.minecraft
                        .setScreen(new RealmsLongRunningMcoTaskScreen(this.lastScreen, new SwitchSlotTask(this.serverData.id, â˜ƒ, this::doSwitchOrReset)));
                  }
               }
            );
         } else {
            â˜ƒx = new Button(this.getFramePositionX(â˜ƒxx), row(8), 80, 20, new TranslatableComponent("mco.brokenworld.download"), var2x -> {
               Component â˜ƒ = new TranslatableComponent("mco.configure.world.restore.download.question.line1");
               Component â˜ƒx = new TranslatableComponent("mco.configure.world.restore.download.question.line2");
               this.minecraft.setScreen(new RealmsLongConfirmationScreen(var2xx -> {
                  if (var2xx) {
                     this.downloadWorld(â˜ƒ);
                  } else {
                     this.minecraft.setScreen(this);
                  }
               }, RealmsLongConfirmationScreen.Type.Info, â˜ƒ, â˜ƒx, true));
            });
         }

         if (this.slotsThatHasBeenDownloaded.contains(â˜ƒxx)) {
            â˜ƒx.active = false;
            â˜ƒx.setMessage(new TranslatableComponent("mco.brokenworld.downloaded"));
         }

         this.addRenderableWidget(â˜ƒx);
         this.addRenderableWidget(new Button(this.getFramePositionX(â˜ƒxx), row(10), 80, 20, new TranslatableComponent("mco.brokenworld.reset"), var2x -> {
            RealmsResetWorldScreen â˜ƒ = new RealmsResetWorldScreen(this, this.serverData, this::doSwitchOrReset, () -> {
               this.minecraft.setScreen(this);
               this.doSwitchOrReset();
            });
            if (â˜ƒ != this.serverData.activeSlot || this.serverData.worldType == RealmsServer.WorldType.MINIGAME) {
               â˜ƒ.setSlot(â˜ƒ);
            }

            this.minecraft.setScreen(â˜ƒ);
         }));
      }
   }

   @Override
   public void tick() {
      ++this.animTick;
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 17, 16777215);

      for(int â˜ƒ = 0; â˜ƒ < this.message.length; ++â˜ƒ) {
         drawCenteredString(â˜ƒ, this.font, this.message[â˜ƒ], this.width / 2, row(-1) + 3 + â˜ƒ * 12, 10526880);
      }

      if (this.serverData != null) {
         for(Entry<Integer, RealmsWorldOptions> â˜ƒ : this.serverData.slots.entrySet()) {
            if (((RealmsWorldOptions)â˜ƒ.getValue()).templateImage != null && ((RealmsWorldOptions)â˜ƒ.getValue()).templateId != -1L) {
               this.drawSlotFrame(
                  â˜ƒ,
                  this.getFramePositionX(â˜ƒ.getKey()),
                  row(1) + 5,
                  â˜ƒ,
                  â˜ƒ,
                  this.serverData.activeSlot == â˜ƒ.getKey() && !this.isMinigame(),
                  ((RealmsWorldOptions)â˜ƒ.getValue()).getSlotName(â˜ƒ.getKey()),
                  â˜ƒ.getKey(),
                  ((RealmsWorldOptions)â˜ƒ.getValue()).templateId,
                  ((RealmsWorldOptions)â˜ƒ.getValue()).templateImage,
                  ((RealmsWorldOptions)â˜ƒ.getValue()).empty
               );
            } else {
               this.drawSlotFrame(
                  â˜ƒ,
                  this.getFramePositionX(â˜ƒ.getKey()),
                  row(1) + 5,
                  â˜ƒ,
                  â˜ƒ,
                  this.serverData.activeSlot == â˜ƒ.getKey() && !this.isMinigame(),
                  ((RealmsWorldOptions)â˜ƒ.getValue()).getSlotName(â˜ƒ.getKey()),
                  â˜ƒ.getKey(),
                  -1L,
                  null,
                  ((RealmsWorldOptions)â˜ƒ.getValue()).empty
               );
            }
         }
      }
   }

   private int getFramePositionX(int var1) {
      return this.leftX + (â˜ƒ - 1) * 110;
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
      this.minecraft.setScreen(this.lastScreen);
   }

   private void fetchServerData(long var1) {
      new Thread(() -> {
         RealmsClient â˜ƒ = RealmsClient.create();

         try {
            this.serverData = â˜ƒ.getOwnWorld(â˜ƒ);
            this.addButtons();
         } catch (RealmsServiceException var5) {
            LOGGER.error("Couldn't get own world");
            this.minecraft.setScreen(new RealmsGenericErrorScreen(Component.nullToEmpty(var5.getMessage()), this.lastScreen));
         }
      }).start();
   }

   public void doSwitchOrReset() {
      new Thread(
            () -> {
               RealmsClient â˜ƒ = RealmsClient.create();
               if (this.serverData.state == RealmsServer.State.CLOSED) {
                  this.minecraft
                     .execute(
                        () -> this.minecraft
                              .setScreen(
                                 new RealmsLongRunningMcoTaskScreen(this, new OpenServerTask(this.serverData, this, this.mainScreen, true, this.minecraft))
                              )
                     );
               } else {
                  try {
                     RealmsServer â˜ƒ = â˜ƒ.getOwnWorld(this.serverId);
                     this.minecraft.execute(() -> this.mainScreen.newScreen().play(â˜ƒ, this));
                  } catch (RealmsServiceException var3) {
                     LOGGER.error("Couldn't get own world");
                     this.minecraft.execute(() -> this.minecraft.setScreen(this.lastScreen));
                  }
               }
            }
         )
         .start();
   }

   private void downloadWorld(int var1) {
      RealmsClient â˜ƒ = RealmsClient.create();

      try {
         WorldDownload â˜ƒx = â˜ƒ.requestDownloadInfo(this.serverData.id, â˜ƒ);
         RealmsDownloadLatestWorldScreen â˜ƒxx = new RealmsDownloadLatestWorldScreen(this, â˜ƒx, this.serverData.getWorldName(â˜ƒ), var2x -> {
            if (var2x) {
               this.slotsThatHasBeenDownloaded.add(â˜ƒ);
               this.clearWidgets();
               this.addButtons();
            } else {
               this.minecraft.setScreen(this);
            }
         });
         this.minecraft.setScreen(â˜ƒxx);
      } catch (RealmsServiceException var5) {
         LOGGER.error("Couldn't download world data");
         this.minecraft.setScreen(new RealmsGenericErrorScreen(var5, this));
      }
   }

   private boolean isMinigame() {
      return this.serverData != null && this.serverData.worldType == RealmsServer.WorldType.MINIGAME;
   }

   private void drawSlotFrame(
      PoseStack var1, int var2, int var3, int var4, int var5, boolean var6, String var7, int var8, long var9, @Nullable String var11, boolean var12
   ) {
      if (â˜ƒ) {
         RenderSystem.setShaderTexture(0, RealmsWorldSlotButton.EMPTY_SLOT_LOCATION);
      } else if (â˜ƒ != null && â˜ƒ != -1L) {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(â˜ƒ), â˜ƒ);
      } else if (â˜ƒ == 1) {
         RenderSystem.setShaderTexture(0, RealmsWorldSlotButton.DEFAULT_WORLD_SLOT_1);
      } else if (â˜ƒ == 2) {
         RenderSystem.setShaderTexture(0, RealmsWorldSlotButton.DEFAULT_WORLD_SLOT_2);
      } else if (â˜ƒ == 3) {
         RenderSystem.setShaderTexture(0, RealmsWorldSlotButton.DEFAULT_WORLD_SLOT_3);
      } else {
         RealmsTextureManager.bindWorldTemplate(String.valueOf(this.serverData.minigameId), this.serverData.minigameImage);
      }

      if (!â˜ƒ) {
         RenderSystem.setShaderColor(0.56F, 0.56F, 0.56F, 1.0F);
      } else if (â˜ƒ) {
         float â˜ƒ = 0.9F + 0.1F * Mth.cos((float)this.animTick * 0.2F);
         RenderSystem.setShaderColor(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
      }

      GuiComponent.blit(â˜ƒ, â˜ƒ + 3, â˜ƒ + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      RenderSystem.setShaderTexture(0, RealmsWorldSlotButton.SLOT_FRAME_LOCATION);
      if (â˜ƒ) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.setShaderColor(0.56F, 0.56F, 0.56F, 1.0F);
      }

      GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 80, 80, 80, 80);
      drawCenteredString(â˜ƒ, this.font, â˜ƒ, â˜ƒ + 40, â˜ƒ + 66, 16777215);
   }
}
