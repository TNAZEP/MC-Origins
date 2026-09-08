package net.minecraft.server.gui;

import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class PlayerListComponent extends JList<String> {
   private final MinecraftServer server;
   private int tickCount;

   public PlayerListComponent(MinecraftServer var1) {
      this.server = â˜ƒ;
      â˜ƒ.addTickable(this::tick);
   }

   public void tick() {
      if (this.tickCount++ % 20 == 0) {
         Vector<String> â˜ƒ = new Vector();

         for(int â˜ƒx = 0; â˜ƒx < this.server.getPlayerList().getPlayers().size(); ++â˜ƒx) {
            â˜ƒ.add(((ServerPlayer)this.server.getPlayerList().getPlayers().get(â˜ƒx)).getGameProfile().getName());
         }

         this.setListData(â˜ƒ);
      }
   }
}
