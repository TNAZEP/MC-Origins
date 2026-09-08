package net.minecraft.client.gui.screens;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.ScreenNarrationCollector;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Screen extends AbstractContainerEventHandler implements Widget {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<String> ALLOWED_PROTOCOLS = Sets.newHashSet("http", "https");
   private static final int EXTRA_SPACE_AFTER_FIRST_TOOLTIP_LINE = 2;
   private static final Component USAGE_NARRATION = new TranslatableComponent("narrator.screen.usage");
   protected final Component title;
   private final List<GuiEventListener> children = Lists.<GuiEventListener>newArrayList();
   private final List<NarratableEntry> narratables = Lists.<NarratableEntry>newArrayList();
   @Nullable
   protected Minecraft minecraft;
   protected ItemRenderer itemRenderer;
   public int width;
   public int height;
   private final List<Widget> renderables = Lists.<Widget>newArrayList();
   public boolean passEvents;
   protected Font font;
   private URI clickedLink;
   private static final long NARRATE_SUPPRESS_AFTER_INIT_TIME = TimeUnit.SECONDS.toMillis(2L);
   private static final long NARRATE_DELAY_NARRATOR_ENABLED = NARRATE_SUPPRESS_AFTER_INIT_TIME;
   private static final long NARRATE_DELAY_MOUSE_MOVE = 750L;
   private static final long NARRATE_DELAY_MOUSE_ACTION = 200L;
   private static final long NARRATE_DELAY_KEYBOARD_ACTION = 200L;
   private final ScreenNarrationCollector narrationState = new ScreenNarrationCollector();
   private long narrationSuppressTime = Long.MIN_VALUE;
   private long nextNarrationTime = Long.MAX_VALUE;
   @Nullable
   private NarratableEntry lastNarratable;

   protected Screen(Component var1) {
      this.title = â˜ƒ;
   }

   public Component getTitle() {
      return this.title;
   }

   public Component getNarrationMessage() {
      return this.getTitle();
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      for(Widget â˜ƒ : this.renderables) {
         â˜ƒ.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (â˜ƒ == 256 && this.shouldCloseOnEsc()) {
         this.onClose();
         return true;
      } else if (â˜ƒ == 258) {
         boolean â˜ƒ = !hasShiftDown();
         if (!this.changeFocus(â˜ƒ)) {
            this.changeFocus(â˜ƒ);
         }

         return false;
      } else {
         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public boolean shouldCloseOnEsc() {
      return true;
   }

   public void onClose() {
      this.minecraft.setScreen(null);
   }

   protected <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T var1) {
      this.renderables.add(â˜ƒ);
      return this.addWidget(â˜ƒ);
   }

   protected <T extends Widget> T addRenderableOnly(T var1) {
      this.renderables.add(â˜ƒ);
      return â˜ƒ;
   }

   protected <T extends GuiEventListener & NarratableEntry> T addWidget(T var1) {
      this.children.add(â˜ƒ);
      this.narratables.add(â˜ƒ);
      return â˜ƒ;
   }

   protected void removeWidget(GuiEventListener var1) {
      if (â˜ƒ instanceof Widget) {
         this.renderables.remove((Widget)â˜ƒ);
      }

      if (â˜ƒ instanceof NarratableEntry) {
         this.narratables.remove((NarratableEntry)â˜ƒ);
      }

      this.children.remove(â˜ƒ);
   }

   protected void clearWidgets() {
      this.renderables.clear();
      this.children.clear();
      this.narratables.clear();
   }

   protected void renderTooltip(PoseStack var1, ItemStack var2, int var3, int var4) {
      this.renderTooltip(â˜ƒ, this.getTooltipFromItem(â˜ƒ), â˜ƒ.getTooltipImage(), â˜ƒ, â˜ƒ);
   }

   public void renderTooltip(PoseStack var1, List<Component> var2, Optional<TooltipComponent> var3, int var4, int var5) {
      List<ClientTooltipComponent> â˜ƒ = (List)â˜ƒ.stream().map(Component::getVisualOrderText).map(ClientTooltipComponent::create).collect(Collectors.toList());
      â˜ƒ.ifPresent(var1x -> â˜ƒ.add(1, ClientTooltipComponent.create(var1x)));
      this.renderTooltipInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public List<Component> getTooltipFromItem(ItemStack var1) {
      return â˜ƒ.getTooltipLines(this.minecraft.player, this.minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
   }

   public void renderTooltip(PoseStack var1, Component var2, int var3, int var4) {
      this.renderTooltip(â˜ƒ, Arrays.asList(â˜ƒ.getVisualOrderText()), â˜ƒ, â˜ƒ);
   }

   public void renderComponentTooltip(PoseStack var1, List<Component> var2, int var3, int var4) {
      this.renderTooltip(â˜ƒ, Lists.transform(â˜ƒ, Component::getVisualOrderText), â˜ƒ, â˜ƒ);
   }

   public void renderTooltip(PoseStack var1, List<? extends FormattedCharSequence> var2, int var3, int var4) {
      this.renderTooltipInternal(â˜ƒ, (List<ClientTooltipComponent>)â˜ƒ.stream().map(ClientTooltipComponent::create).collect(Collectors.toList()), â˜ƒ, â˜ƒ);
   }

   private void renderTooltipInternal(PoseStack var1, List<ClientTooltipComponent> var2, int var3, int var4) {
      if (!â˜ƒ.isEmpty()) {
         int â˜ƒ = 0;
         int â˜ƒx = â˜ƒ.size() == 1 ? -2 : 0;

         for(ClientTooltipComponent â˜ƒxx : â˜ƒ) {
            int â˜ƒxxx = â˜ƒxx.getWidth(this.font);
            if (â˜ƒxxx > â˜ƒ) {
               â˜ƒ = â˜ƒxxx;
            }

            â˜ƒx += â˜ƒxx.getHeight();
         }

         int â˜ƒxx = â˜ƒ + 12;
         int â˜ƒxxx = â˜ƒ - 12;
         if (â˜ƒxx + â˜ƒ > this.width) {
            â˜ƒxx -= 28 + â˜ƒ;
         }

         if (â˜ƒxxx + â˜ƒx + 6 > this.height) {
            â˜ƒxxx = this.height - â˜ƒx - 6;
         }

         â˜ƒ.pushPose();
         int â˜ƒxx = -267386864;
         int â˜ƒxxx = 1347420415;
         int â˜ƒxxxx = 1344798847;
         int â˜ƒxxxxx = 400;
         float â˜ƒxxxxxx = this.itemRenderer.blitOffset;
         this.itemRenderer.blitOffset = 400.0F;
         Tesselator â˜ƒxxxxxxx = Tesselator.getInstance();
         BufferBuilder â˜ƒxxxxxxxx = â˜ƒxxxxxxx.getBuilder();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         â˜ƒxxxxxxxx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
         Matrix4f â˜ƒxxxxxxxxx = â˜ƒ.last().pose();
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 3, â˜ƒxxx - 4, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx - 3, 400, -267386864, -267386864);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 3, â˜ƒxxx + â˜ƒx + 3, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx + â˜ƒx + 4, 400, -267386864, -267386864);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 3, â˜ƒxxx - 3, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx + â˜ƒx + 3, 400, -267386864, -267386864);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 4, â˜ƒxxx - 3, â˜ƒxx - 3, â˜ƒxxx + â˜ƒx + 3, 400, -267386864, -267386864);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx - 3, â˜ƒxx + â˜ƒ + 4, â˜ƒxxx + â˜ƒx + 3, 400, -267386864, -267386864);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 3, â˜ƒxxx - 3 + 1, â˜ƒxx - 3 + 1, â˜ƒxxx + â˜ƒx + 3 - 1, 400, 1347420415, 1344798847);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx + â˜ƒ + 2, â˜ƒxxx - 3 + 1, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx + â˜ƒx + 3 - 1, 400, 1347420415, 1344798847);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 3, â˜ƒxxx - 3, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx - 3 + 1, 400, 1347420415, 1347420415);
         fillGradient(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxx - 3, â˜ƒxxx + â˜ƒx + 2, â˜ƒxx + â˜ƒ + 3, â˜ƒxxx + â˜ƒx + 3, 400, 1344798847, 1344798847);
         RenderSystem.enableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         â˜ƒxxxxxxxx.end();
         BufferUploader.end(â˜ƒxxxxxxxx);
         RenderSystem.disableBlend();
         RenderSystem.enableTexture();
         MultiBufferSource.BufferSource â˜ƒxxxxxxxxxx = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
         â˜ƒ.translate(0.0, 0.0, 400.0);
         int â˜ƒxxxxxxxxxxx = â˜ƒxxx;

         for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒ.size(); ++â˜ƒxxxxxxxxxxxx) {
            ClientTooltipComponent â˜ƒxxxxxxxxxxxxx = (ClientTooltipComponent)â˜ƒ.get(â˜ƒxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxx.renderText(this.font, â˜ƒxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
            â˜ƒxxxxxxxxxxx += â˜ƒxxxxxxxxxxxxx.getHeight() + (â˜ƒxxxxxxxxxxxx == 0 ? 2 : 0);
         }

         â˜ƒxxxxxxxxxx.endBatch();
         â˜ƒ.popPose();
         â˜ƒxxxxxxxxxxx = â˜ƒxxx;

         for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒ.size(); ++â˜ƒxxxxxxxxxxxx) {
            ClientTooltipComponent â˜ƒxxxxxxxxxxxxx = (ClientTooltipComponent)â˜ƒ.get(â˜ƒxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxx.renderImage(this.font, â˜ƒxx, â˜ƒxxxxxxxxxxx, â˜ƒ, this.itemRenderer, 400, this.minecraft.getTextureManager());
            â˜ƒxxxxxxxxxxx += â˜ƒxxxxxxxxxxxxx.getHeight() + (â˜ƒxxxxxxxxxxxx == 0 ? 2 : 0);
         }

         this.itemRenderer.blitOffset = â˜ƒxxxxxx;
      }
   }

   protected void renderComponentHoverEffect(PoseStack var1, @Nullable Style var2, int var3, int var4) {
      if (â˜ƒ != null && â˜ƒ.getHoverEvent() != null) {
         HoverEvent â˜ƒ = â˜ƒ.getHoverEvent();
         HoverEvent.ItemStackInfo â˜ƒx = â˜ƒ.getValue(HoverEvent.Action.SHOW_ITEM);
         if (â˜ƒx != null) {
            this.renderTooltip(â˜ƒ, â˜ƒx.getItemStack(), â˜ƒ, â˜ƒ);
         } else {
            HoverEvent.EntityTooltipInfo â˜ƒ = â˜ƒ.getValue(HoverEvent.Action.SHOW_ENTITY);
            if (â˜ƒ != null) {
               if (this.minecraft.options.advancedItemTooltips) {
                  this.renderComponentTooltip(â˜ƒ, â˜ƒ.getTooltipLines(), â˜ƒ, â˜ƒ);
               }
            } else {
               Component â˜ƒ = â˜ƒ.getValue(HoverEvent.Action.SHOW_TEXT);
               if (â˜ƒ != null) {
                  this.renderTooltip(â˜ƒ, this.minecraft.font.split(â˜ƒ, Math.max(this.width / 2, 200)), â˜ƒ, â˜ƒ);
               }
            }
         }
      }
   }

   protected void insertText(String var1, boolean var2) {
   }

   public boolean handleComponentClicked(@Nullable Style var1) {
      if (â˜ƒ == null) {
         return false;
      } else {
         ClickEvent â˜ƒ = â˜ƒ.getClickEvent();
         if (hasShiftDown()) {
            if (â˜ƒ.getInsertion() != null) {
               this.insertText(â˜ƒ.getInsertion(), false);
            }
         } else if (â˜ƒ != null) {
            if (â˜ƒ.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.minecraft.options.chatLinks) {
                  return false;
               }

               try {
                  URI â˜ƒ = new URI(â˜ƒ.getValue());
                  String â˜ƒx = â˜ƒ.getScheme();
                  if (â˜ƒx == null) {
                     throw new URISyntaxException(â˜ƒ.getValue(), "Missing protocol");
                  }

                  if (!ALLOWED_PROTOCOLS.contains(â˜ƒx.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(â˜ƒ.getValue(), "Unsupported protocol: " + â˜ƒx.toLowerCase(Locale.ROOT));
                  }

                  if (this.minecraft.options.chatLinksPrompt) {
                     this.clickedLink = â˜ƒ;
                     this.minecraft.setScreen(new ConfirmLinkScreen(this::confirmLink, â˜ƒ.getValue(), false));
                  } else {
                     this.openLink(â˜ƒ);
                  }
               } catch (URISyntaxException var5) {
                  LOGGER.error("Can't open url for {}", â˜ƒ, var5);
               }
            } else if (â˜ƒ.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI â˜ƒ = new File(â˜ƒ.getValue()).toURI();
               this.openLink(â˜ƒ);
            } else if (â˜ƒ.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.insertText(â˜ƒ.getValue(), true);
            } else if (â˜ƒ.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.sendMessage(â˜ƒ.getValue(), false);
            } else if (â˜ƒ.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
               this.minecraft.keyboardHandler.setClipboard(â˜ƒ.getValue());
            } else {
               LOGGER.error("Don't know how to handle {}", â˜ƒ);
            }

            return true;
         }

         return false;
      }
   }

   public void sendMessage(String var1) {
      this.sendMessage(â˜ƒ, true);
   }

   public void sendMessage(String var1, boolean var2) {
      if (â˜ƒ) {
         this.minecraft.gui.getChat().addRecentChat(â˜ƒ);
      }

      this.minecraft.player.chat(â˜ƒ);
   }

   public final void init(Minecraft var1, int var2, int var3) {
      this.minecraft = â˜ƒ;
      this.itemRenderer = â˜ƒ.getItemRenderer();
      this.font = â˜ƒ.font;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.clearWidgets();
      this.setFocused(null);
      this.init();
      this.triggerImmediateNarration(false);
      this.suppressNarration(NARRATE_SUPPRESS_AFTER_INIT_TIME);
   }

   @Override
   public List<? extends GuiEventListener> children() {
      return this.children;
   }

   protected void init() {
   }

   public void tick() {
   }

   public void removed() {
   }

   public void renderBackground(PoseStack var1) {
      this.renderBackground(â˜ƒ, 0);
   }

   public void renderBackground(PoseStack var1, int var2) {
      if (this.minecraft.level != null) {
         this.fillGradient(â˜ƒ, 0, 0, this.width, this.height, -1072689136, -804253680);
      } else {
         this.renderDirtBackground(â˜ƒ);
      }
   }

   public void renderDirtBackground(int var1) {
      Tesselator â˜ƒ = Tesselator.getInstance();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
      RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      float â˜ƒxx = 32.0F;
      â˜ƒx.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
      â˜ƒx.vertex(0.0, (double)this.height, 0.0).uv(0.0F, (float)this.height / 32.0F + (float)â˜ƒ).color(64, 64, 64, 255).endVertex();
      â˜ƒx.vertex((double)this.width, (double)this.height, 0.0)
         .uv((float)this.width / 32.0F, (float)this.height / 32.0F + (float)â˜ƒ)
         .color(64, 64, 64, 255)
         .endVertex();
      â˜ƒx.vertex((double)this.width, 0.0, 0.0).uv((float)this.width / 32.0F, (float)â˜ƒ).color(64, 64, 64, 255).endVertex();
      â˜ƒx.vertex(0.0, 0.0, 0.0).uv(0.0F, (float)â˜ƒ).color(64, 64, 64, 255).endVertex();
      â˜ƒ.end();
   }

   public boolean isPauseScreen() {
      return true;
   }

   private void confirmLink(boolean var1) {
      if (â˜ƒ) {
         this.openLink(this.clickedLink);
      }

      this.clickedLink = null;
      this.minecraft.setScreen(this);
   }

   private void openLink(URI var1) {
      Util.getPlatform().openUri(â˜ƒ);
   }

   public static boolean hasControlDown() {
      if (Minecraft.ON_OSX) {
         return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 343)
            || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 347);
      } else {
         return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 341)
            || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 345);
      }
   }

   public static boolean hasShiftDown() {
      return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340)
         || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344);
   }

   public static boolean hasAltDown() {
      return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 342)
         || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 346);
   }

   public static boolean isCut(int var0) {
      return â˜ƒ == 88 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public static boolean isPaste(int var0) {
      return â˜ƒ == 86 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public static boolean isCopy(int var0) {
      return â˜ƒ == 67 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public static boolean isSelectAll(int var0) {
      return â˜ƒ == 65 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public void resize(Minecraft var1, int var2, int var3) {
      this.init(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void wrapScreenError(Runnable var0, String var1, String var2) {
      try {
         â˜ƒ.run();
      } catch (Throwable var6) {
         CrashReport â˜ƒ = CrashReport.forThrowable(var6, â˜ƒ);
         CrashReportCategory â˜ƒx = â˜ƒ.addCategory("Affected screen");
         â˜ƒx.setDetail("Screen name", (CrashReportDetail<String>)(() -> â˜ƒ));
         throw new ReportedException(â˜ƒ);
      }
   }

   protected boolean isValidCharacterForName(String var1, char var2, int var3) {
      int â˜ƒ = â˜ƒ.indexOf(58);
      int â˜ƒx = â˜ƒ.indexOf(47);
      if (â˜ƒ == ':') {
         return (â˜ƒx == -1 || â˜ƒ <= â˜ƒx) && â˜ƒ == -1;
      } else if (â˜ƒ == '/') {
         return â˜ƒ > â˜ƒ;
      } else {
         return â˜ƒ == '_' || â˜ƒ == '-' || â˜ƒ >= 'a' && â˜ƒ <= 'z' || â˜ƒ >= '0' && â˜ƒ <= '9' || â˜ƒ == '.';
      }
   }

   @Override
   public boolean isMouseOver(double var1, double var3) {
      return true;
   }

   public void onFilesDrop(List<Path> var1) {
   }

   private void scheduleNarration(long var1, boolean var3) {
      this.nextNarrationTime = Util.getMillis() + â˜ƒ;
      if (â˜ƒ) {
         this.narrationSuppressTime = Long.MIN_VALUE;
      }
   }

   private void suppressNarration(long var1) {
      this.narrationSuppressTime = Util.getMillis() + â˜ƒ;
   }

   public void afterMouseMove() {
      this.scheduleNarration(750L, false);
   }

   public void afterMouseAction() {
      this.scheduleNarration(200L, true);
   }

   public void afterKeyboardAction() {
      this.scheduleNarration(200L, true);
   }

   private boolean shouldRunNarration() {
      return NarratorChatListener.INSTANCE.isActive();
   }

   public void handleDelayedNarration() {
      if (this.shouldRunNarration()) {
         long â˜ƒ = Util.getMillis();
         if (â˜ƒ > this.nextNarrationTime && â˜ƒ > this.narrationSuppressTime) {
            this.runNarration(true);
            this.nextNarrationTime = Long.MAX_VALUE;
         }
      }
   }

   protected void triggerImmediateNarration(boolean var1) {
      if (this.shouldRunNarration()) {
         this.runNarration(â˜ƒ);
      }
   }

   private void runNarration(boolean var1) {
      this.narrationState.update(this::updateNarrationState);
      String â˜ƒ = this.narrationState.collectNarrationText(!â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         NarratorChatListener.INSTANCE.sayNow(â˜ƒ);
      }
   }

   protected void updateNarrationState(NarrationElementOutput var1) {
      â˜ƒ.add(NarratedElementType.TITLE, this.getNarrationMessage());
      â˜ƒ.add(NarratedElementType.USAGE, USAGE_NARRATION);
      this.updateNarratedWidget(â˜ƒ);
   }

   protected void updateNarratedWidget(NarrationElementOutput var1) {
      ImmutableList<NarratableEntry> â˜ƒ = (ImmutableList)this.narratables.stream().filter(NarratableEntry::isActive).collect(ImmutableList.toImmutableList());
      Screen.NarratableSearchResult â˜ƒx = findNarratableWidget(â˜ƒ, this.lastNarratable);
      if (â˜ƒx != null) {
         if (â˜ƒx.priority.isTerminal()) {
            this.lastNarratable = â˜ƒx.entry;
         }

         if (â˜ƒ.size() > 1) {
            â˜ƒ.add(NarratedElementType.POSITION, new TranslatableComponent("narrator.position.screen", â˜ƒx.index + 1, â˜ƒ.size()));
            if (â˜ƒx.priority == NarratableEntry.NarrationPriority.FOCUSED) {
               â˜ƒ.add(NarratedElementType.USAGE, new TranslatableComponent("narration.component_list.usage"));
            }
         }

         â˜ƒx.entry.updateNarration(â˜ƒ.nest());
      }
   }

   @Nullable
   public static Screen.NarratableSearchResult findNarratableWidget(List<? extends NarratableEntry> var0, @Nullable NarratableEntry var1) {
      Screen.NarratableSearchResult â˜ƒ = null;
      Screen.NarratableSearchResult â˜ƒx = null;
      int â˜ƒxx = 0;

      for(int â˜ƒxxx = â˜ƒ.size(); â˜ƒxx < â˜ƒxxx; ++â˜ƒxx) {
         NarratableEntry â˜ƒxxxx = (NarratableEntry)â˜ƒ.get(â˜ƒxx);
         NarratableEntry.NarrationPriority â˜ƒxxxxx = â˜ƒxxxx.narrationPriority();
         if (â˜ƒxxxxx.isTerminal()) {
            if (â˜ƒxxxx != â˜ƒ) {
               return new Screen.NarratableSearchResult(â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
            }

            â˜ƒx = new Screen.NarratableSearchResult(â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
         } else if (â˜ƒxxxxx.compareTo(â˜ƒ != null ? â˜ƒ.priority : NarratableEntry.NarrationPriority.NONE) > 0) {
            â˜ƒ = new Screen.NarratableSearchResult(â˜ƒxxxx, â˜ƒxx, â˜ƒxxxxx);
         }
      }

      return â˜ƒ != null ? â˜ƒ : â˜ƒx;
   }

   public void narrationEnabled() {
      this.scheduleNarration(NARRATE_DELAY_NARRATOR_ENABLED, false);
   }

   public static class NarratableSearchResult {
      public final NarratableEntry entry;
      public final int index;
      public final NarratableEntry.NarrationPriority priority;

      public NarratableSearchResult(NarratableEntry var1, int var2, NarratableEntry.NarrationPriority var3) {
         this.entry = â˜ƒ;
         this.index = â˜ƒ;
         this.priority = â˜ƒ;
      }
   }
}
