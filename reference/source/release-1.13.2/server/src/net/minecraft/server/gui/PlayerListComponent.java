package net.minecraft.server.gui;

import java.util.Vector;
import javax.swing.JList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ITickable;

public class PlayerListComponent extends JList<String> implements ITickable {
   private final MinecraftServer field_120015_a;
   private int field_120014_b;

   public PlayerListComponent(MinecraftServer var1) {
      this.field_120015_a = ☃;
      ☃.func_82010_a(this);
   }

   @Override
   public void func_73660_a() {
      if (this.field_120014_b++ % 20 == 0) {
         Vector<String> ☃ = new Vector();

         for(int ☃x = 0; ☃x < this.field_120015_a.func_184103_al().func_181057_v().size(); ++☃x) {
            ☃.add(((EntityPlayerMP)this.field_120015_a.func_184103_al().func_181057_v().get(☃x)).func_146103_bH().getName());
         }

         this.setListData(☃);
      }
   }
}
