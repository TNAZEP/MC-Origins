package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerInfo;

public class ServerSelectionList extends GuiListExtended<ServerSelectionList.Entry> {
   private final GuiMultiplayer field_148200_k;
   private final List<ServerListEntryNormal> field_148198_l = Lists.<ServerListEntryNormal>newArrayList();
   private final ServerSelectionList.Entry field_148196_n = new ServerListEntryLanScan();
   private final List<ServerListEntryLanDetected> field_148199_m = Lists.<ServerListEntryLanDetected>newArrayList();
   private int field_148197_o = -1;

   private void func_195094_h() {
      this.func_195086_c();
      this.field_148198_l.forEach(this::func_195085_a);
      this.func_195085_a(this.field_148196_n);
      this.field_148199_m.forEach(this::func_195085_a);
   }

   public ServerSelectionList(GuiMultiplayer var1, Minecraft var2, int var3, int var4, int var5, int var6, int var7) {
      super(☃, ☃, ☃, ☃, ☃, ☃);
      this.field_148200_k = ☃;
   }

   public void func_148192_c(int var1) {
      this.field_148197_o = ☃;
   }

   @Override
   protected boolean func_148131_a(int var1) {
      return ☃ == this.field_148197_o;
   }

   public int func_148193_k() {
      return this.field_148197_o;
   }

   public void func_148195_a(ServerList var1) {
      this.field_148198_l.clear();

      for(int ☃ = 0; ☃ < ☃.func_78856_c(); ++☃) {
         this.field_148198_l.add(new ServerListEntryNormal(this.field_148200_k, ☃.func_78850_a(☃)));
      }

      this.func_195094_h();
   }

   public void func_148194_a(List<LanServerInfo> var1) {
      this.field_148199_m.clear();

      for(LanServerInfo ☃ : ☃) {
         this.field_148199_m.add(new ServerListEntryLanDetected(this.field_148200_k, ☃));
      }

      this.func_195094_h();
   }

   @Override
   protected int func_148137_d() {
      return super.func_148137_d() + 30;
   }

   @Override
   public int func_148139_c() {
      return super.func_148139_c() + 85;
   }

   public abstract static class Entry extends GuiListExtended.IGuiListEntry<ServerSelectionList.Entry> {
   }
}
