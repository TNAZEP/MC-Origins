package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft minecraft;
   private final List<ServerData> serverList = Lists.<ServerData>newArrayList();

   public ServerList(Minecraft var1) {
      this.minecraft = â˜ƒ;
      this.load();
   }

   public void load() {
      try {
         this.serverList.clear();
         CompoundTag â˜ƒ = NbtIo.read(new File(this.minecraft.gameDirectory, "servers.dat"));
         if (â˜ƒ == null) {
            return;
         }

         ListTag â˜ƒ = â˜ƒ.getList("servers", 10);

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            this.serverList.add(ServerData.read(â˜ƒ.getCompound(â˜ƒx)));
         }
      } catch (Exception var4) {
         LOGGER.error("Couldn't load server list", var4);
      }
   }

   public void save() {
      try {
         ListTag â˜ƒ = new ListTag();

         for(ServerData â˜ƒx : this.serverList) {
            â˜ƒ.add(â˜ƒx.write());
         }

         CompoundTag â˜ƒx = new CompoundTag();
         â˜ƒx.put("servers", â˜ƒ);
         File â˜ƒxx = File.createTempFile("servers", ".dat", this.minecraft.gameDirectory);
         NbtIo.write(â˜ƒx, â˜ƒxx);
         File â˜ƒxxx = new File(this.minecraft.gameDirectory, "servers.dat_old");
         File â˜ƒxxxx = new File(this.minecraft.gameDirectory, "servers.dat");
         Util.safeReplaceFile(â˜ƒxxxx, â˜ƒxx, â˜ƒxxx);
      } catch (Exception var6) {
         LOGGER.error("Couldn't save server list", var6);
      }
   }

   public ServerData get(int var1) {
      return (ServerData)this.serverList.get(â˜ƒ);
   }

   public void remove(ServerData var1) {
      this.serverList.remove(â˜ƒ);
   }

   public void add(ServerData var1) {
      this.serverList.add(â˜ƒ);
   }

   public int size() {
      return this.serverList.size();
   }

   public void swap(int var1, int var2) {
      ServerData â˜ƒ = this.get(â˜ƒ);
      this.serverList.set(â˜ƒ, this.get(â˜ƒ));
      this.serverList.set(â˜ƒ, â˜ƒ);
      this.save();
   }

   public void replace(int var1, ServerData var2) {
      this.serverList.set(â˜ƒ, â˜ƒ);
   }

   public static void saveSingleServer(ServerData var0) {
      ServerList â˜ƒ = new ServerList(Minecraft.getInstance());
      â˜ƒ.load();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
         ServerData â˜ƒxx = â˜ƒ.get(â˜ƒx);
         if (â˜ƒxx.name.equals(â˜ƒ.name) && â˜ƒxx.ip.equals(â˜ƒ.ip)) {
            â˜ƒ.replace(â˜ƒx, â˜ƒ);
            break;
         }
      }

      â˜ƒ.save();
   }
}
