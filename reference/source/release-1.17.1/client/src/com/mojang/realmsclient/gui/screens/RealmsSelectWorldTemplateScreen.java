package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.TextRenderingUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSelectWorldTemplateScreen extends RealmsScreen {
   static final Logger LOGGER = LogManager.getLogger();
   static final ResourceLocation LINK_ICON = new ResourceLocation("realms", "textures/gui/realms/link_icons.png");
   static final ResourceLocation TRAILER_ICON = new ResourceLocation("realms", "textures/gui/realms/trailer_icons.png");
   static final ResourceLocation SLOT_FRAME_LOCATION = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
   static final Component PUBLISHER_LINK_TOOLTIP = new TranslatableComponent("mco.template.info.tooltip");
   static final Component TRAILER_LINK_TOOLTIP = new TranslatableComponent("mco.template.trailer.tooltip");
   private final Consumer<WorldTemplate> callback;
   RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList worldTemplateObjectSelectionList;
   int selectedTemplate = -1;
   private Button selectButton;
   private Button trailerButton;
   private Button publisherButton;
   @Nullable
   Component toolTip;
   String currentLink;
   private final RealmsServer.WorldType worldType;
   int clicks;
   @Nullable
   private Component[] warning;
   private String warningURL;
   boolean displayWarning;
   private boolean hoverWarning;
   @Nullable
   List<TextRenderingUtils.Line> noTemplatesMessage;

   public RealmsSelectWorldTemplateScreen(Component var1, Consumer<WorldTemplate> var2, RealmsServer.WorldType var3) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, null);
   }

   public RealmsSelectWorldTemplateScreen(Component var1, Consumer<WorldTemplate> var2, RealmsServer.WorldType var3, @Nullable WorldTemplatePaginatedList var4) {
      super(â˜ƒ);
      this.callback = â˜ƒ;
      this.worldType = â˜ƒ;
      if (â˜ƒ == null) {
         this.worldTemplateObjectSelectionList = new RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList();
         this.fetchTemplatesAsync(new WorldTemplatePaginatedList(10));
      } else {
         this.worldTemplateObjectSelectionList = new RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList(
            Lists.<WorldTemplate>newArrayList(â˜ƒ.templates)
         );
         this.fetchTemplatesAsync(â˜ƒ);
      }
   }

   public void setWarning(Component... var1) {
      this.warning = â˜ƒ;
      this.displayWarning = true;
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.hoverWarning && this.warningURL != null) {
         Util.getPlatform().openUri("https://www.minecraft.net/realms/adventure-maps-in-1-9");
         return true;
      } else {
         return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void init() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      this.worldTemplateObjectSelectionList = new RealmsSelectWorldTemplateScreen.WorldTemplateObjectSelectionList(
         this.worldTemplateObjectSelectionList.getTemplates()
      );
      this.trailerButton = this.addRenderableWidget(
         new Button(this.width / 2 - 206, this.height - 32, 100, 20, new TranslatableComponent("mco.template.button.trailer"), var1x -> this.onTrailer())
      );
      this.selectButton = this.addRenderableWidget(
         new Button(this.width / 2 - 100, this.height - 32, 100, 20, new TranslatableComponent("mco.template.button.select"), var1x -> this.selectTemplate())
      );
      Component â˜ƒ = this.worldType == RealmsServer.WorldType.MINIGAME ? CommonComponents.GUI_CANCEL : CommonComponents.GUI_BACK;
      Button â˜ƒx = new Button(this.width / 2 + 6, this.height - 32, 100, 20, â˜ƒ, var1x -> this.onClose());
      this.addRenderableWidget(â˜ƒx);
      this.publisherButton = this.addRenderableWidget(
         new Button(this.width / 2 + 112, this.height - 32, 100, 20, new TranslatableComponent("mco.template.button.publisher"), var1x -> this.onPublish())
      );
      this.selectButton.active = false;
      this.trailerButton.visible = false;
      this.publisherButton.visible = false;
      this.addWidget(this.worldTemplateObjectSelectionList);
      this.magicalSpecialHackyFocus(this.worldTemplateObjectSelectionList);
   }

   @Override
   public Component getNarrationMessage() {
      List<Component> â˜ƒ = Lists.<Component>newArrayListWithCapacity(2);
      if (this.title != null) {
         â˜ƒ.add(this.title);
      }

      if (this.warning != null) {
         â˜ƒ.addAll(Arrays.asList(this.warning));
      }

      return CommonComponents.joinLines(â˜ƒ);
   }

   void updateButtonStates() {
      this.publisherButton.visible = this.shouldPublisherBeVisible();
      this.trailerButton.visible = this.shouldTrailerBeVisible();
      this.selectButton.active = this.shouldSelectButtonBeActive();
   }

   private boolean shouldSelectButtonBeActive() {
      return this.selectedTemplate != -1;
   }

   private boolean shouldPublisherBeVisible() {
      return this.selectedTemplate != -1 && !this.getSelectedTemplate().link.isEmpty();
   }

   private WorldTemplate getSelectedTemplate() {
      return this.worldTemplateObjectSelectionList.get(this.selectedTemplate);
   }

   private boolean shouldTrailerBeVisible() {
      return this.selectedTemplate != -1 && !this.getSelectedTemplate().trailer.isEmpty();
   }

   @Override
   public void tick() {
      super.tick();
      --this.clicks;
      if (this.clicks < 0) {
         this.clicks = 0;
      }
   }

   @Override
   public void onClose() {
      this.callback.accept(null);
   }

   void selectTemplate() {
      if (this.hasValidTemplate()) {
         this.callback.accept(this.getSelectedTemplate());
      }
   }

   private boolean hasValidTemplate() {
      return this.selectedTemplate >= 0 && this.selectedTemplate < this.worldTemplateObjectSelectionList.getItemCount();
   }

   private void onTrailer() {
      if (this.hasValidTemplate()) {
         WorldTemplate â˜ƒ = this.getSelectedTemplate();
         if (!"".equals(â˜ƒ.trailer)) {
            Util.getPlatform().openUri(â˜ƒ.trailer);
         }
      }
   }

   private void onPublish() {
      if (this.hasValidTemplate()) {
         WorldTemplate â˜ƒ = this.getSelectedTemplate();
         if (!"".equals(â˜ƒ.link)) {
            Util.getPlatform().openUri(â˜ƒ.link);
         }
      }
   }

   private void fetchTemplatesAsync(final WorldTemplatePaginatedList var1) {
      (new Thread("realms-template-fetcher") {
            public void run() {
               WorldTemplatePaginatedList â˜ƒ = â˜ƒ;
   
               Either<WorldTemplatePaginatedList, String> â˜ƒ;
               for(RealmsClient â˜ƒx = RealmsClient.create();
                  â˜ƒ != null;
                  â˜ƒ = (WorldTemplatePaginatedList)RealmsSelectWorldTemplateScreen.this.minecraft
                     .submit(
                        () -> {
                           if (â˜ƒ.right().isPresent()) {
                              RealmsSelectWorldTemplateScreen.LOGGER.error("Couldn't fetch templates: {}", â˜ƒ.right().get());
                              if (RealmsSelectWorldTemplateScreen.this.worldTemplateObjectSelectionList.isEmpty()) {
                                 RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(I18n.get("mco.template.select.failure"));
                              }
            
                              return null;
                           } else {
                              WorldTemplatePaginatedList â˜ƒ = (WorldTemplatePaginatedList)â˜ƒ.left().get();
            
                              for(WorldTemplate â˜ƒx : â˜ƒ.templates) {
                                 RealmsSelectWorldTemplateScreen.this.worldTemplateObjectSelectionList.addEntry(â˜ƒx);
                              }
            
                              if (â˜ƒ.templates.isEmpty()) {
                                 if (RealmsSelectWorldTemplateScreen.this.worldTemplateObjectSelectionList.isEmpty()) {
                                    String â˜ƒx = I18n.get("mco.template.select.none", "%link");
                                    TextRenderingUtils.LineSegment â˜ƒxx = TextRenderingUtils.LineSegment.link(
                                       I18n.get("mco.template.select.none.linkTitle"), "https://aka.ms/MinecraftRealmsContentCreator"
                                    );
                                    RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(â˜ƒx, â˜ƒxx);
                                 }
            
                                 return null;
                              } else {
                                 return â˜ƒ;
                              }
                           }
                        }
                     )
                     .join()
               ) {
                  â˜ƒ = RealmsSelectWorldTemplateScreen.this.fetchTemplates(â˜ƒ, â˜ƒx);
               }
            }
         })
         .start();
   }

   Either<WorldTemplatePaginatedList, String> fetchTemplates(WorldTemplatePaginatedList var1, RealmsClient var2) {
      try {
         return Either.left(â˜ƒ.fetchWorldTemplates(â˜ƒ.page + 1, â˜ƒ.size, this.worldType));
      } catch (RealmsServiceException var4) {
         return Either.right(var4.getMessage());
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.toolTip = null;
      this.currentLink = null;
      this.hoverWarning = false;
      this.renderBackground(â˜ƒ);
      this.worldTemplateObjectSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.noTemplatesMessage != null) {
         this.renderMultilineMessage(â˜ƒ, â˜ƒ, â˜ƒ, this.noTemplatesMessage);
      }

      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 13, 16777215);
      if (this.displayWarning) {
         Component[] â˜ƒ = this.warning;

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            int â˜ƒxx = this.font.width(â˜ƒ[â˜ƒx]);
            int â˜ƒxxx = this.width / 2 - â˜ƒxx / 2;
            int â˜ƒxxxx = row(-1 + â˜ƒx);
            if (â˜ƒ >= â˜ƒxxx && â˜ƒ <= â˜ƒxxx + â˜ƒxx && â˜ƒ >= â˜ƒxxxx && â˜ƒ <= â˜ƒxxxx + 9) {
               this.hoverWarning = true;
            }
         }

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
            Component â˜ƒxx = â˜ƒ[â˜ƒx];
            int â˜ƒxxx = 10526880;
            if (this.warningURL != null) {
               if (this.hoverWarning) {
                  â˜ƒxxx = 7107012;
                  â˜ƒxx = â˜ƒxx.copy().withStyle(ChatFormatting.STRIKETHROUGH);
               } else {
                  â˜ƒxxx = 3368635;
               }
            }

            drawCenteredString(â˜ƒ, this.font, â˜ƒxx, this.width / 2, row(-1 + â˜ƒx), â˜ƒxxx);
         }
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderMousehoverTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
   }

   private void renderMultilineMessage(PoseStack var1, int var2, int var3, List<TextRenderingUtils.Line> var4) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         TextRenderingUtils.Line â˜ƒx = (TextRenderingUtils.Line)â˜ƒ.get(â˜ƒ);
         int â˜ƒxx = row(4 + â˜ƒ);
         int â˜ƒxxx = â˜ƒx.segments.stream().mapToInt(var1x -> this.font.width(var1x.renderedText())).sum();
         int â˜ƒxxxx = this.width / 2 - â˜ƒxxx / 2;

         for(TextRenderingUtils.LineSegment â˜ƒxxxxx : â˜ƒx.segments) {
            int â˜ƒxxxxxx = â˜ƒxxxxx.isLink() ? 3368635 : 16777215;
            int â˜ƒxxxxxxx = this.font.drawShadow(â˜ƒ, â˜ƒxxxxx.renderedText(), (float)â˜ƒxxxx, (float)â˜ƒxx, â˜ƒxxxxxx);
            if (â˜ƒxxxxx.isLink() && â˜ƒ > â˜ƒxxxx && â˜ƒ < â˜ƒxxxxxxx && â˜ƒ > â˜ƒxx - 3 && â˜ƒ < â˜ƒxx + 8) {
               this.toolTip = new TextComponent(â˜ƒxxxxx.getLinkUrl());
               this.currentLink = â˜ƒxxxxx.getLinkUrl();
            }

            â˜ƒxxxx = â˜ƒxxxxxxx;
         }
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

   class Entry extends ObjectSelectionList.Entry<RealmsSelectWorldTemplateScreen.Entry> {
      final WorldTemplate template;

      public Entry(WorldTemplate var2) {
         this.template = â˜ƒ;
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.renderWorldTemplateItem(â˜ƒ, this.template, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void renderWorldTemplateItem(PoseStack var1, WorldTemplate var2, int var3, int var4, int var5, int var6) {
         int â˜ƒ = â˜ƒ + 45 + 20;
         RealmsSelectWorldTemplateScreen.this.font.draw(â˜ƒ, â˜ƒ.name, (float)â˜ƒ, (float)(â˜ƒ + 2), 16777215);
         RealmsSelectWorldTemplateScreen.this.font.draw(â˜ƒ, â˜ƒ.author, (float)â˜ƒ, (float)(â˜ƒ + 15), 7105644);
         RealmsSelectWorldTemplateScreen.this.font
            .draw(â˜ƒ, â˜ƒ.version, (float)(â˜ƒ + 227 - RealmsSelectWorldTemplateScreen.this.font.width(â˜ƒ.version)), (float)(â˜ƒ + 1), 7105644);
         if (!"".equals(â˜ƒ.link) || !"".equals(â˜ƒ.trailer) || !"".equals(â˜ƒ.recommendedPlayers)) {
            this.drawIcons(â˜ƒ, â˜ƒ - 1, â˜ƒ + 25, â˜ƒ, â˜ƒ, â˜ƒ.link, â˜ƒ.trailer, â˜ƒ.recommendedPlayers);
         }

         this.drawImage(â˜ƒ, â˜ƒ, â˜ƒ + 1, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void drawImage(PoseStack var1, int var2, int var3, int var4, int var5, WorldTemplate var6) {
         RealmsTextureManager.bindWorldTemplate(â˜ƒ.id, â˜ƒ.image);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.blit(â˜ƒ, â˜ƒ + 1, â˜ƒ + 1, 0.0F, 0.0F, 38, 38, 38, 38);
         RenderSystem.setShaderTexture(0, RealmsSelectWorldTemplateScreen.SLOT_FRAME_LOCATION);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 40, 40, 40, 40);
      }

      private void drawIcons(PoseStack var1, int var2, int var3, int var4, int var5, String var6, String var7, String var8) {
         if (!"".equals(â˜ƒ)) {
            RealmsSelectWorldTemplateScreen.this.font.draw(â˜ƒ, â˜ƒ, (float)â˜ƒ, (float)(â˜ƒ + 4), 5000268);
         }

         int â˜ƒ = "".equals(â˜ƒ) ? 0 : RealmsSelectWorldTemplateScreen.this.font.width(â˜ƒ) + 2;
         boolean â˜ƒx = false;
         boolean â˜ƒxx = false;
         boolean â˜ƒxxx = "".equals(â˜ƒ);
         if (â˜ƒ >= â˜ƒ + â˜ƒ && â˜ƒ <= â˜ƒ + â˜ƒ + 32 && â˜ƒ >= â˜ƒ && â˜ƒ <= â˜ƒ + 15 && â˜ƒ < RealmsSelectWorldTemplateScreen.this.height - 15 && â˜ƒ > 32) {
            if (â˜ƒ <= â˜ƒ + 15 + â˜ƒ && â˜ƒ > â˜ƒ) {
               if (â˜ƒxxx) {
                  â˜ƒxx = true;
               } else {
                  â˜ƒx = true;
               }
            } else if (!â˜ƒxxx) {
               â˜ƒxx = true;
            }
         }

         if (!â˜ƒxxx) {
            RenderSystem.setShaderTexture(0, RealmsSelectWorldTemplateScreen.LINK_ICON);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            float â˜ƒ = â˜ƒx ? 15.0F : 0.0F;
            GuiComponent.blit(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 15, 15, 30, 15);
         }

         if (!"".equals(â˜ƒ)) {
            RenderSystem.setShaderTexture(0, RealmsSelectWorldTemplateScreen.TRAILER_ICON);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int â˜ƒ = â˜ƒ + â˜ƒ + (â˜ƒxxx ? 0 : 17);
            float â˜ƒx = â˜ƒxx ? 15.0F : 0.0F;
            GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, 0.0F, 15, 15, 30, 15);
         }

         if (â˜ƒx) {
            RealmsSelectWorldTemplateScreen.this.toolTip = RealmsSelectWorldTemplateScreen.PUBLISHER_LINK_TOOLTIP;
            RealmsSelectWorldTemplateScreen.this.currentLink = â˜ƒ;
         } else if (â˜ƒxx && !"".equals(â˜ƒ)) {
            RealmsSelectWorldTemplateScreen.this.toolTip = RealmsSelectWorldTemplateScreen.TRAILER_LINK_TOOLTIP;
            RealmsSelectWorldTemplateScreen.this.currentLink = â˜ƒ;
         }
      }

      @Override
      public Component getNarration() {
         Component â˜ƒ = CommonComponents.joinLines(
            new TextComponent(this.template.name),
            new TranslatableComponent("mco.template.select.narrate.authors", this.template.author),
            new TextComponent(this.template.recommendedPlayers),
            new TranslatableComponent("mco.template.select.narrate.version", this.template.version)
         );
         return new TranslatableComponent("narrator.select", â˜ƒ);
      }
   }

   class WorldTemplateObjectSelectionList extends RealmsObjectSelectionList<RealmsSelectWorldTemplateScreen.Entry> {
      public WorldTemplateObjectSelectionList() {
         this(Collections.emptyList());
      }

      public WorldTemplateObjectSelectionList(Iterable<WorldTemplate> var2) {
         super(
            RealmsSelectWorldTemplateScreen.this.width,
            RealmsSelectWorldTemplateScreen.this.height,
            RealmsSelectWorldTemplateScreen.this.displayWarning ? RealmsSelectWorldTemplateScreen.row(1) : 32,
            RealmsSelectWorldTemplateScreen.this.height - 40,
            46
         );
         â˜ƒ.forEach(this::addEntry);
      }

      public void addEntry(WorldTemplate var1) {
         this.addEntry(RealmsSelectWorldTemplateScreen.this.new Entry(â˜ƒ));
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         if (â˜ƒ == 0 && â˜ƒ >= (double)this.y0 && â˜ƒ <= (double)this.y1) {
            int â˜ƒ = this.width / 2 - 150;
            if (RealmsSelectWorldTemplateScreen.this.currentLink != null) {
               Util.getPlatform().openUri(RealmsSelectWorldTemplateScreen.this.currentLink);
            }

            int â˜ƒ = (int)Math.floor(â˜ƒ - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int â˜ƒx = â˜ƒ / this.itemHeight;
            if (â˜ƒ >= (double)â˜ƒ && â˜ƒ < (double)this.getScrollbarPosition() && â˜ƒx >= 0 && â˜ƒ >= 0 && â˜ƒx < this.getItemCount()) {
               this.selectItem(â˜ƒx);
               this.itemClicked(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, this.width);
               if (â˜ƒx >= RealmsSelectWorldTemplateScreen.this.worldTemplateObjectSelectionList.getItemCount()) {
                  return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
               }

               RealmsSelectWorldTemplateScreen.this.clicks += 7;
               if (RealmsSelectWorldTemplateScreen.this.clicks >= 10) {
                  RealmsSelectWorldTemplateScreen.this.selectTemplate();
               }

               return true;
            }
         }

         return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void setSelected(@Nullable RealmsSelectWorldTemplateScreen.Entry var1) {
         super.setSelected(â˜ƒ);
         RealmsSelectWorldTemplateScreen.this.selectedTemplate = this.children().indexOf(â˜ƒ);
         RealmsSelectWorldTemplateScreen.this.updateButtonStates();
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 46;
      }

      @Override
      public int getRowWidth() {
         return 300;
      }

      @Override
      public void renderBackground(PoseStack var1) {
         RealmsSelectWorldTemplateScreen.this.renderBackground(â˜ƒ);
      }

      @Override
      public boolean isFocused() {
         return RealmsSelectWorldTemplateScreen.this.getFocused() == this;
      }

      public boolean isEmpty() {
         return this.getItemCount() == 0;
      }

      public WorldTemplate get(int var1) {
         return ((RealmsSelectWorldTemplateScreen.Entry)this.children().get(â˜ƒ)).template;
      }

      public List<WorldTemplate> getTemplates() {
         return (List<WorldTemplate>)this.children().stream().map(var0 -> var0.template).collect(Collectors.toList());
      }
   }
}
