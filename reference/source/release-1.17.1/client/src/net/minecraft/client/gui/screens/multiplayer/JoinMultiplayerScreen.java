package net.minecraft.client.gui.screens.multiplayer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.EditServerScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.ServerStatusPinger;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.LanServer;
import net.minecraft.client.server.LanServerDetection;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JoinMultiplayerScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ServerStatusPinger pinger = new ServerStatusPinger();
   private final Screen lastScreen;
   protected ServerSelectionList serverSelectionList;
   private ServerList servers;
   private Button editButton;
   private Button selectButton;
   private Button deleteButton;
   private List<Component> toolTip;
   private ServerData editingServer;
   private LanServerDetection.LanServerList lanServerList;
   private LanServerDetection.LanServerDetector lanServerDetector;
   private boolean initedOnce;

   public JoinMultiplayerScreen(Screen var1) {
      super(new TranslatableComponent("multiplayer.title"));
      this.lastScreen = â˜ƒ;
   }

   @Override
   protected void init() {
      super.init();
      this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
      if (this.initedOnce) {
         this.serverSelectionList.updateSize(this.width, this.height, 32, this.height - 64);
      } else {
         this.initedOnce = true;
         this.servers = new ServerList(this.minecraft);
         this.servers.load();
         this.lanServerList = new LanServerDetection.LanServerList();

         try {
            this.lanServerDetector = new LanServerDetection.LanServerDetector(this.lanServerList);
            this.lanServerDetector.start();
         } catch (Exception var2) {
            LOGGER.warn("Unable to start LAN server detection: {}", var2.getMessage());
         }

         this.serverSelectionList = new ServerSelectionList(this, this.minecraft, this.width, this.height, 32, this.height - 64, 36);
         this.serverSelectionList.updateOnlineServers(this.servers);
      }

      this.addWidget(this.serverSelectionList);
      this.selectButton = this.addRenderableWidget(
         new Button(this.width / 2 - 154, this.height - 52, 100, 20, new TranslatableComponent("selectServer.select"), var1 -> this.joinSelectedServer())
      );
      this.addRenderableWidget(new Button(this.width / 2 - 50, this.height - 52, 100, 20, new TranslatableComponent("selectServer.direct"), var1 -> {
         this.editingServer = new ServerData(I18n.get("selectServer.defaultName"), "", false);
         this.minecraft.setScreen(new DirectJoinServerScreen(this, this::directJoinCallback, this.editingServer));
      }));
      this.addRenderableWidget(new Button(this.width / 2 + 4 + 50, this.height - 52, 100, 20, new TranslatableComponent("selectServer.add"), var1 -> {
         this.editingServer = new ServerData(I18n.get("selectServer.defaultName"), "", false);
         this.minecraft.setScreen(new EditServerScreen(this, this::addServerCallback, this.editingServer));
      }));
      this.editButton = this.addRenderableWidget(
         new Button(this.width / 2 - 154, this.height - 28, 70, 20, new TranslatableComponent("selectServer.edit"), var1 -> {
            ServerSelectionList.Entry â˜ƒ = this.serverSelectionList.getSelected();
            if (â˜ƒ instanceof ServerSelectionList.OnlineServerEntry) {
               ServerData â˜ƒx = ((ServerSelectionList.OnlineServerEntry)â˜ƒ).getServerData();
               this.editingServer = new ServerData(â˜ƒx.name, â˜ƒx.ip, false);
               this.editingServer.copyFrom(â˜ƒx);
               this.minecraft.setScreen(new EditServerScreen(this, this::editServerCallback, this.editingServer));
            }
         })
      );
      this.deleteButton = this.addRenderableWidget(
         new Button(this.width / 2 - 74, this.height - 28, 70, 20, new TranslatableComponent("selectServer.delete"), var1 -> {
            ServerSelectionList.Entry â˜ƒ = this.serverSelectionList.getSelected();
            if (â˜ƒ instanceof ServerSelectionList.OnlineServerEntry) {
               String â˜ƒx = ((ServerSelectionList.OnlineServerEntry)â˜ƒ).getServerData().name;
               if (â˜ƒx != null) {
                  Component â˜ƒxx = new TranslatableComponent("selectServer.deleteQuestion");
                  Component â˜ƒxxx = new TranslatableComponent("selectServer.deleteWarning", â˜ƒx);
                  Component â˜ƒxxxx = new TranslatableComponent("selectServer.deleteButton");
                  Component â˜ƒxxxxx = CommonComponents.GUI_CANCEL;
                  this.minecraft.setScreen(new ConfirmScreen(this::deleteCallback, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx));
               }
            }
         })
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 4, this.height - 28, 70, 20, new TranslatableComponent("selectServer.refresh"), var1 -> this.refreshServerList())
      );
      this.addRenderableWidget(
         new Button(this.width / 2 + 4 + 76, this.height - 28, 75, 20, CommonComponents.GUI_CANCEL, var1 -> this.minecraft.setScreen(this.lastScreen))
      );
      this.onSelectedChange();
   }

   @Override
   public void tick() {
      super.tick();
      if (this.lanServerList.isDirty()) {
         List<LanServer> â˜ƒ = this.lanServerList.getServers();
         this.lanServerList.markClean();
         this.serverSelectionList.updateNetworkServers(â˜ƒ);
      }

      this.pinger.tick();
   }

   @Override
   public void removed() {
      this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
      if (this.lanServerDetector != null) {
         this.lanServerDetector.interrupt();
         this.lanServerDetector = null;
      }

      this.pinger.removeAll();
   }

   private void refreshServerList() {
      this.minecraft.setScreen(new JoinMultiplayerScreen(this.lastScreen));
   }

   private void deleteCallback(boolean var1) {
      ServerSelectionList.Entry â˜ƒ = this.serverSelectionList.getSelected();
      if (â˜ƒ && â˜ƒ instanceof ServerSelectionList.OnlineServerEntry) {
         this.servers.remove(((ServerSelectionList.OnlineServerEntry)â˜ƒ).getServerData());
         this.servers.save();
         this.serverSelectionList.setSelected(null);
         this.serverSelectionList.updateOnlineServers(this.servers);
      }

      this.minecraft.setScreen(this);
   }

   private void editServerCallback(boolean var1) {
      ServerSelectionList.Entry â˜ƒ = this.serverSelectionList.getSelected();
      if (â˜ƒ && â˜ƒ instanceof ServerSelectionList.OnlineServerEntry) {
         ServerData â˜ƒx = ((ServerSelectionList.OnlineServerEntry)â˜ƒ).getServerData();
         â˜ƒx.name = this.editingServer.name;
         â˜ƒx.ip = this.editingServer.ip;
         â˜ƒx.copyFrom(this.editingServer);
         this.servers.save();
         this.serverSelectionList.updateOnlineServers(this.servers);
      }

      this.minecraft.setScreen(this);
   }

   private void addServerCallback(boolean var1) {
      if (â˜ƒ) {
         this.servers.add(this.editingServer);
         this.servers.save();
         this.serverSelectionList.setSelected(null);
         this.serverSelectionList.updateOnlineServers(this.servers);
      }

      this.minecraft.setScreen(this);
   }

   private void directJoinCallback(boolean var1) {
      if (â˜ƒ) {
         this.join(this.editingServer);
      } else {
         this.minecraft.setScreen(this);
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else if (â˜ƒ == 294) {
         this.refreshServerList();
         return true;
      } else if (this.serverSelectionList.getSelected() != null) {
         if (â˜ƒ != 257 && â˜ƒ != 335) {
            return this.serverSelectionList.keyPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            this.joinSelectedServer();
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      this.toolTip = null;
      this.renderBackground(â˜ƒ);
      this.serverSelectionList.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      drawCenteredString(â˜ƒ, this.font, this.title, this.width / 2, 20, 16777215);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (this.toolTip != null) {
         this.renderComponentTooltip(â˜ƒ, this.toolTip, â˜ƒ, â˜ƒ);
      }
   }

   public void joinSelectedServer() {
      ServerSelectionList.Entry â˜ƒ = this.serverSelectionList.getSelected();
      if (â˜ƒ instanceof ServerSelectionList.OnlineServerEntry) {
         this.join(((ServerSelectionList.OnlineServerEntry)â˜ƒ).getServerData());
      } else if (â˜ƒ instanceof ServerSelectionList.NetworkServerEntry) {
         LanServer â˜ƒ = ((ServerSelectionList.NetworkServerEntry)â˜ƒ).getServerData();
         this.join(new ServerData(â˜ƒ.getMotd(), â˜ƒ.getAddress(), true));
      }
   }

   private void join(ServerData var1) {
      ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(â˜ƒ.ip), â˜ƒ);
   }

   public void setSelected(ServerSelectionList.Entry var1) {
      this.serverSelectionList.setSelected(â˜ƒ);
      this.onSelectedChange();
   }

   protected void onSelectedChange() {
      this.selectButton.active = false;
      this.editButton.active = false;
      this.deleteButton.active = false;
      ServerSelectionList.Entry â˜ƒ = this.serverSelectionList.getSelected();
      if (â˜ƒ != null && !(â˜ƒ instanceof ServerSelectionList.LANHeader)) {
         this.selectButton.active = true;
         if (â˜ƒ instanceof ServerSelectionList.OnlineServerEntry) {
            this.editButton.active = true;
            this.deleteButton.active = true;
         }
      }
   }

   public ServerStatusPinger getPinger() {
      return this.pinger;
   }

   public void setToolTip(List<Component> var1) {
      this.toolTip = â˜ƒ;
   }

   public ServerList getServers() {
      return this.servers;
   }
}
