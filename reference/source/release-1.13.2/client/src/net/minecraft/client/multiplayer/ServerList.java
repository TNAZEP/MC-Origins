package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
   private static final Logger field_147415_a = LogManager.getLogger();
   private final Minecraft field_78859_a;
   private final List<ServerData> field_78858_b = Lists.<ServerData>newArrayList();

   public ServerList(Minecraft var1) {
      this.field_78859_a = ☃;
      this.func_78853_a();
   }

   public void func_78853_a() {
      try {
         this.field_78858_b.clear();
         NBTTagCompound ☃ = CompressedStreamTools.func_74797_a(new File(this.field_78859_a.field_71412_D, "servers.dat"));
         if (☃ == null) {
            return;
         }

         NBTTagList ☃ = ☃.func_150295_c("servers", 10);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_78858_b.add(ServerData.func_78837_a(☃.func_150305_b(☃x)));
         }
      } catch (Exception var4) {
         field_147415_a.error("Couldn't load server list", var4);
      }
   }

   public void func_78855_b() {
      try {
         NBTTagList ☃ = new NBTTagList();

         for(ServerData ☃x : this.field_78858_b) {
            ☃.add((INBTBase)☃x.func_78836_a());
         }

         NBTTagCompound ☃x = new NBTTagCompound();
         ☃x.func_74782_a("servers", ☃);
         CompressedStreamTools.func_74793_a(☃x, new File(this.field_78859_a.field_71412_D, "servers.dat"));
      } catch (Exception var4) {
         field_147415_a.error("Couldn't save server list", var4);
      }
   }

   public ServerData func_78850_a(int var1) {
      return (ServerData)this.field_78858_b.get(☃);
   }

   public void func_78851_b(int var1) {
      this.field_78858_b.remove(☃);
   }

   public void func_78849_a(ServerData var1) {
      this.field_78858_b.add(☃);
   }

   public int func_78856_c() {
      return this.field_78858_b.size();
   }

   public void func_78857_a(int var1, int var2) {
      ServerData ☃ = this.func_78850_a(☃);
      this.field_78858_b.set(☃, this.func_78850_a(☃));
      this.field_78858_b.set(☃, ☃);
      this.func_78855_b();
   }

   public void func_147413_a(int var1, ServerData var2) {
      this.field_78858_b.set(☃, ☃);
   }

   public static void func_147414_b(ServerData var0) {
      ServerList ☃ = new ServerList(Minecraft.func_71410_x());
      ☃.func_78853_a();

      for(int ☃x = 0; ☃x < ☃.func_78856_c(); ++☃x) {
         ServerData ☃xx = ☃.func_78850_a(☃x);
         if (☃xx.field_78847_a.equals(☃.field_78847_a) && ☃xx.field_78845_b.equals(☃.field_78845_b)) {
            ☃.func_147413_a(☃x, ☃);
            break;
         }
      }

      ☃.func_78855_b();
   }
}
