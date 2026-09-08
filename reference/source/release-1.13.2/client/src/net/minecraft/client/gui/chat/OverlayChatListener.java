package net.minecraft.client.gui.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;

public class OverlayChatListener implements IChatListener {
   private final Minecraft field_192577_a;

   public OverlayChatListener(Minecraft var1) {
      this.field_192577_a = ☃;
   }

   @Override
   public void func_192576_a(ChatType var1, ITextComponent var2) {
      this.field_192577_a.field_71456_v.func_175188_a(☃, false);
   }
}
