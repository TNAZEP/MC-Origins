package net.minecraft.client.gui.screens.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.server.LanServer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerSelectionList extends ObjectSelectionList<ServerSelectionList.Entry> {
   static final Logger LOGGER = LogManager.getLogger();
   static final ThreadPoolExecutor THREAD_POOL = new ScheduledThreadPoolExecutor(
      5,
      new ThreadFactoryBuilder()
         .setNameFormat("Server Pinger #%d")
         .setDaemon(true)
         .setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER))
         .build()
   );
   static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
   static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/server_selection.png");
   static final Component SCANNING_LABEL = new TranslatableComponent("lanServer.scanning");
   static final Component CANT_RESOLVE_TEXT = new TranslatableComponent("multiplayer.status.cannot_resolve").withStyle(ChatFormatting.DARK_RED);
   static final Component CANT_CONNECT_TEXT = new TranslatableComponent("multiplayer.status.cannot_connect").withStyle(ChatFormatting.DARK_RED);
   static final Component INCOMPATIBLE_TOOLTIP = new TranslatableComponent("multiplayer.status.incompatible");
   static final Component NO_CONNECTION_TOOLTIP = new TranslatableComponent("multiplayer.status.no_connection");
   static final Component PINGING_TOOLTIP = new TranslatableComponent("multiplayer.status.pinging");
   private final JoinMultiplayerScreen screen;
   private final List<ServerSelectionList.OnlineServerEntry> onlineServers = Lists.<ServerSelectionList.OnlineServerEntry>newArrayList();
   private final ServerSelectionList.Entry lanHeader = new ServerSelectionList.LANHeader();
   private final List<ServerSelectionList.NetworkServerEntry> networkServers = Lists.<ServerSelectionList.NetworkServerEntry>newArrayList();

   public ServerSelectionList(JoinMultiplayerScreen var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.screen = â˜ƒ;
   }

   private void refreshEntries() {
      this.clearEntries();
      this.onlineServers.forEach(var1 -> this.addEntry(var1));
      this.addEntry(this.lanHeader);
      this.networkServers.forEach(var1 -> this.addEntry(var1));
   }

   public void setSelected(@Nullable ServerSelectionList.Entry var1) {
      super.setSelected(â˜ƒ);
      this.screen.onSelectedChange();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      ServerSelectionList.Entry â˜ƒ = this.getSelected();
      return â˜ƒ != null ? â˜ƒ.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ) : super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void moveSelection(AbstractSelectionList.SelectionDirection var1) {
      this.moveSelection(â˜ƒ, var0 -> !(var0 instanceof ServerSelectionList.LANHeader));
   }

   public void updateOnlineServers(ServerList var1) {
      this.onlineServers.clear();

      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.size(); ++â˜ƒ) {
         this.onlineServers.add(new ServerSelectionList.OnlineServerEntry(this.screen, â˜ƒ.get(â˜ƒ)));
      }

      this.refreshEntries();
   }

   public void updateNetworkServers(List<LanServer> var1) {
      this.networkServers.clear();

      for(LanServer â˜ƒ : â˜ƒ) {
         this.networkServers.add(new ServerSelectionList.NetworkServerEntry(this.screen, â˜ƒ));
      }

      this.refreshEntries();
   }

   @Override
   protected int getScrollbarPosition() {
      return super.getScrollbarPosition() + 30;
   }

   @Override
   public int getRowWidth() {
      return super.getRowWidth() + 85;
   }

   @Override
   protected boolean isFocused() {
      return this.screen.getFocused() == this;
   }

   public abstract static class Entry extends ObjectSelectionList.Entry<ServerSelectionList.Entry> {
   }

   public static class LANHeader extends ServerSelectionList.Entry {
      private final Minecraft minecraft = Minecraft.getInstance();

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         int â˜ƒ = â˜ƒ + â˜ƒ / 2 - 9 / 2;
         this.minecraft
            .font
            .draw(
               â˜ƒ,
               ServerSelectionList.SCANNING_LABEL,
               (float)(this.minecraft.screen.width / 2 - this.minecraft.font.width(ServerSelectionList.SCANNING_LABEL) / 2),
               (float)â˜ƒ,
               16777215
            );

         String var12 = switch((int)(Util.getMillis() / 300L % 4L)) {
            default -> "O o o";
            case 1, 3 -> "o O o";
            case 2 -> "o o O";
         };
         this.minecraft.font.draw(â˜ƒ, var12, (float)(this.minecraft.screen.width / 2 - this.minecraft.font.width(var12) / 2), (float)(â˜ƒ + 9), 8421504);
      }

      @Override
      public Component getNarration() {
         return TextComponent.EMPTY;
      }
   }

   public static class NetworkServerEntry extends ServerSelectionList.Entry {
      private static final int ICON_WIDTH = 32;
      private static final Component LAN_SERVER_HEADER = new TranslatableComponent("lanServer.title");
      private static final Component HIDDEN_ADDRESS_TEXT = new TranslatableComponent("selectServer.hiddenAddress");
      private final JoinMultiplayerScreen screen;
      protected final Minecraft minecraft;
      protected final LanServer serverData;
      private long lastClickTime;

      protected NetworkServerEntry(JoinMultiplayerScreen var1, LanServer var2) {
         this.screen = â˜ƒ;
         this.serverData = â˜ƒ;
         this.minecraft = Minecraft.getInstance();
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.minecraft.font.draw(â˜ƒ, LAN_SERVER_HEADER, (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 1), 16777215);
         this.minecraft.font.draw(â˜ƒ, this.serverData.getMotd(), (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 12), 8421504);
         if (this.minecraft.options.hideServerAddress) {
            this.minecraft.font.draw(â˜ƒ, HIDDEN_ADDRESS_TEXT, (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 12 + 11), 3158064);
         } else {
            this.minecraft.font.draw(â˜ƒ, this.serverData.getAddress(), (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 12 + 11), 3158064);
         }
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         this.screen.setSelected(this);
         if (Util.getMillis() - this.lastClickTime < 250L) {
            this.screen.joinSelectedServer();
         }

         this.lastClickTime = Util.getMillis();
         return false;
      }

      public LanServer getServerData() {
         return this.serverData;
      }

      @Override
      public Component getNarration() {
         return new TranslatableComponent("narrator.select", new TextComponent("").append(LAN_SERVER_HEADER).append(" ").append(this.serverData.getMotd()));
      }
   }

   public class OnlineServerEntry extends ServerSelectionList.Entry {
      private static final int ICON_WIDTH = 32;
      private static final int ICON_HEIGHT = 32;
      private static final int ICON_OVERLAY_X_MOVE_RIGHT = 0;
      private static final int ICON_OVERLAY_X_MOVE_LEFT = 32;
      private static final int ICON_OVERLAY_X_MOVE_DOWN = 64;
      private static final int ICON_OVERLAY_X_MOVE_UP = 96;
      private static final int ICON_OVERLAY_Y_UNSELECTED = 0;
      private static final int ICON_OVERLAY_Y_SELECTED = 32;
      private final JoinMultiplayerScreen screen;
      private final Minecraft minecraft;
      private final ServerData serverData;
      private final ResourceLocation iconLocation;
      private String lastIconB64;
      @Nullable
      private DynamicTexture icon;
      private long lastClickTime;

      protected OnlineServerEntry(JoinMultiplayerScreen var2, ServerData var3) {
         this.screen = â˜ƒ;
         this.serverData = â˜ƒ;
         this.minecraft = Minecraft.getInstance();
         this.iconLocation = new ResourceLocation("servers/" + Hashing.sha1().hashUnencodedChars(â˜ƒ.ip) + "/icon");
         AbstractTexture â˜ƒ = this.minecraft.getTextureManager().getTexture(this.iconLocation, MissingTextureAtlasSprite.getTexture());
         if (â˜ƒ != MissingTextureAtlasSprite.getTexture() && â˜ƒ instanceof DynamicTexture) {
            this.icon = (DynamicTexture)â˜ƒ;
         }
      }

      @Override
      public void render(PoseStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         if (!this.serverData.pinged) {
            this.serverData.pinged = true;
            this.serverData.ping = -2L;
            this.serverData.motd = TextComponent.EMPTY;
            this.serverData.status = TextComponent.EMPTY;
            ServerSelectionList.THREAD_POOL.submit(() -> {
               try {
                  this.screen.getPinger().pingServer(this.serverData, () -> this.minecraft.execute(this::updateServerList));
               } catch (UnknownHostException var2xx) {
                  this.serverData.ping = -1L;
                  this.serverData.motd = ServerSelectionList.CANT_RESOLVE_TEXT;
               } catch (Exception var3xx) {
                  this.serverData.ping = -1L;
                  this.serverData.motd = ServerSelectionList.CANT_CONNECT_TEXT;
               }
            });
         }

         boolean â˜ƒ = this.serverData.protocol != SharedConstants.getCurrentVersion().getProtocolVersion();
         this.minecraft.font.draw(â˜ƒ, this.serverData.name, (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 1), 16777215);
         List<FormattedCharSequence> â˜ƒx = this.minecraft.font.split(this.serverData.motd, â˜ƒ - 32 - 2);

         for(int â˜ƒxx = 0; â˜ƒxx < Math.min(â˜ƒx.size(), 2); ++â˜ƒxx) {
            this.minecraft.font.draw(â˜ƒ, (FormattedCharSequence)â˜ƒx.get(â˜ƒxx), (float)(â˜ƒ + 32 + 3), (float)(â˜ƒ + 12 + 9 * â˜ƒxx), 8421504);
         }

         Component â˜ƒxxxxx = (Component)(â˜ƒ ? this.serverData.version.copy().withStyle(ChatFormatting.RED) : this.serverData.status);
         int â˜ƒxxxxxx = this.minecraft.font.width(â˜ƒxxxxx);
         this.minecraft.font.draw(â˜ƒ, â˜ƒxxxxx, (float)(â˜ƒ + â˜ƒ - â˜ƒxxxxxx - 15 - 2), (float)(â˜ƒ + 1), 8421504);
         int â˜ƒxxxxxxx = 0;
         int â˜ƒxx;
         List<Component> â˜ƒxxx;
         Component â˜ƒxxxx;
         if (â˜ƒ) {
            â˜ƒxx = 5;
            â˜ƒxxxx = ServerSelectionList.INCOMPATIBLE_TOOLTIP;
            â˜ƒxxx = this.serverData.playerList;
         } else if (this.serverData.pinged && this.serverData.ping != -2L) {
            if (this.serverData.ping < 0L) {
               â˜ƒxx = 5;
            } else if (this.serverData.ping < 150L) {
               â˜ƒxx = 0;
            } else if (this.serverData.ping < 300L) {
               â˜ƒxx = 1;
            } else if (this.serverData.ping < 600L) {
               â˜ƒxx = 2;
            } else if (this.serverData.ping < 1000L) {
               â˜ƒxx = 3;
            } else {
               â˜ƒxx = 4;
            }

            if (this.serverData.ping < 0L) {
               â˜ƒxxxx = ServerSelectionList.NO_CONNECTION_TOOLTIP;
               â˜ƒxxx = Collections.emptyList();
            } else {
               â˜ƒxxxx = new TranslatableComponent("multiplayer.status.ping", this.serverData.ping);
               â˜ƒxxx = this.serverData.playerList;
            }
         } else {
            â˜ƒxxxxxxx = 1;
            â˜ƒxx = (int)(Util.getMillis() / 100L + (long)(â˜ƒ * 2) & 7L);
            if (â˜ƒxx > 4) {
               â˜ƒxx = 8 - â˜ƒxx;
            }

            â˜ƒxxxx = ServerSelectionList.PINGING_TOOLTIP;
            â˜ƒxxx = Collections.emptyList();
         }

         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
         GuiComponent.blit(â˜ƒ, â˜ƒ + â˜ƒ - 15, â˜ƒ, (float)(â˜ƒxxxxxxx * 10), (float)(176 + â˜ƒxx * 8), 10, 8, 256, 256);
         String â˜ƒxx = this.serverData.getIconB64();
         if (!Objects.equals(â˜ƒxx, this.lastIconB64)) {
            if (this.uploadServerIcon(â˜ƒxx)) {
               this.lastIconB64 = â˜ƒxx;
            } else {
               this.serverData.setIconB64(null);
               this.updateServerList();
            }
         }

         if (this.icon == null) {
            this.drawIcon(â˜ƒ, â˜ƒ, â˜ƒ, ServerSelectionList.ICON_MISSING);
         } else {
            this.drawIcon(â˜ƒ, â˜ƒ, â˜ƒ, this.iconLocation);
         }

         int â˜ƒxx = â˜ƒ - â˜ƒ;
         int â˜ƒxxx = â˜ƒ - â˜ƒ;
         if (â˜ƒxx >= â˜ƒ - 15 && â˜ƒxx <= â˜ƒ - 5 && â˜ƒxxx >= 0 && â˜ƒxxx <= 8) {
            this.screen.setToolTip(Collections.singletonList(â˜ƒxxxx));
         } else if (â˜ƒxx >= â˜ƒ - â˜ƒxxxxxx - 15 - 2 && â˜ƒxx <= â˜ƒ - 15 - 2 && â˜ƒxxx >= 0 && â˜ƒxxx <= 8) {
            this.screen.setToolTip(â˜ƒxxx);
         }

         if (this.minecraft.options.touchscreen || â˜ƒ) {
            RenderSystem.setShaderTexture(0, ServerSelectionList.ICON_OVERLAY_LOCATION);
            GuiComponent.fill(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 32, â˜ƒ + 32, -1601138544);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int â˜ƒxx = â˜ƒ - â˜ƒ;
            int â˜ƒxxx = â˜ƒ - â˜ƒ;
            if (this.canJoin()) {
               if (â˜ƒxx < 32 && â˜ƒxx > 16) {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (â˜ƒ > 0) {
               if (â˜ƒxx < 16 && â˜ƒxxx < 16) {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 96.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (â˜ƒ < this.screen.getServers().size() - 1) {
               if (â˜ƒxx < 16 && â˜ƒxxx > 16) {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 64.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 64.0F, 0.0F, 32, 32, 256, 256);
               }
            }
         }
      }

      public void updateServerList() {
         this.screen.getServers().save();
      }

      protected void drawIcon(PoseStack var1, int var2, int var3, ResourceLocation var4) {
         RenderSystem.setShaderTexture(0, â˜ƒ);
         RenderSystem.enableBlend();
         GuiComponent.blit(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
      }

      private boolean canJoin() {
         return true;
      }

      private boolean uploadServerIcon(@Nullable String var1) {
         if (â˜ƒ == null) {
            this.minecraft.getTextureManager().release(this.iconLocation);
            if (this.icon != null && this.icon.getPixels() != null) {
               this.icon.getPixels().close();
            }

            this.icon = null;
         } else {
            try {
               NativeImage â˜ƒ = NativeImage.fromBase64(â˜ƒ);
               Validate.validState(â˜ƒ.getWidth() == 64, "Must be 64 pixels wide");
               Validate.validState(â˜ƒ.getHeight() == 64, "Must be 64 pixels high");
               if (this.icon == null) {
                  this.icon = new DynamicTexture(â˜ƒ);
               } else {
                  this.icon.setPixels(â˜ƒ);
                  this.icon.upload();
               }

               this.minecraft.getTextureManager().register(this.iconLocation, this.icon);
            } catch (Throwable var3) {
               ServerSelectionList.LOGGER.error("Invalid icon for server {} ({})", this.serverData.name, this.serverData.ip, var3);
               return false;
            }
         }

         return true;
      }

      @Override
      public boolean keyPressed(int var1, int var2, int var3) {
         if (Screen.hasShiftDown()) {
            ServerSelectionList â˜ƒ = this.screen.serverSelectionList;
            int â˜ƒx = â˜ƒ.children().indexOf(this);
            if (â˜ƒ == 264 && â˜ƒx < this.screen.getServers().size() - 1 || â˜ƒ == 265 && â˜ƒx > 0) {
               this.swap(â˜ƒx, â˜ƒ == 264 ? â˜ƒx + 1 : â˜ƒx - 1);
               return true;
            }
         }

         return super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      private void swap(int var1, int var2) {
         this.screen.getServers().swap(â˜ƒ, â˜ƒ);
         this.screen.serverSelectionList.updateOnlineServers(this.screen.getServers());
         ServerSelectionList.Entry â˜ƒ = (ServerSelectionList.Entry)this.screen.serverSelectionList.children().get(â˜ƒ);
         this.screen.serverSelectionList.setSelected(â˜ƒ);
         ServerSelectionList.this.ensureVisible(â˜ƒ);
      }

      @Override
      public boolean mouseClicked(double var1, double var3, int var5) {
         double â˜ƒ = â˜ƒ - (double)ServerSelectionList.this.getRowLeft();
         double â˜ƒx = â˜ƒ - (double)ServerSelectionList.this.getRowTop(ServerSelectionList.this.children().indexOf(this));
         if (â˜ƒ <= 32.0) {
            if (â˜ƒ < 32.0 && â˜ƒ > 16.0 && this.canJoin()) {
               this.screen.setSelected(this);
               this.screen.joinSelectedServer();
               return true;
            }

            int â˜ƒxx = this.screen.serverSelectionList.children().indexOf(this);
            if (â˜ƒ < 16.0 && â˜ƒx < 16.0 && â˜ƒxx > 0) {
               this.swap(â˜ƒxx, â˜ƒxx - 1);
               return true;
            }

            if (â˜ƒ < 16.0 && â˜ƒx > 16.0 && â˜ƒxx < this.screen.getServers().size() - 1) {
               this.swap(â˜ƒxx, â˜ƒxx + 1);
               return true;
            }
         }

         this.screen.setSelected(this);
         if (Util.getMillis() - this.lastClickTime < 250L) {
            this.screen.joinSelectedServer();
         }

         this.lastClickTime = Util.getMillis();
         return false;
      }

      public ServerData getServerData() {
         return this.serverData;
      }

      @Override
      public Component getNarration() {
         return new TranslatableComponent("narrator.select", this.serverData.name);
      }
   }
}
