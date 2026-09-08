package net.minecraft.client.gui.chat;

import com.mojang.text2speech.Narrator;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class NarratorChatListener implements IChatListener {
   public static final NarratorChatListener field_193643_a = new NarratorChatListener();
   private final Narrator field_192580_a = Narrator.getNarrator();

   @Override
   public void func_192576_a(ChatType var1, ITextComponent var2) {
      int ☃ = Minecraft.func_71410_x().field_71474_y.field_192571_R;
      if (☃ != 0 && this.field_192580_a.active()) {
         if (☃ == 1 || ☃ == 2 && ☃ == ChatType.CHAT || ☃ == 3 && ☃ == ChatType.SYSTEM) {
            if (☃ instanceof TextComponentTranslation && "chat.type.text".equals(((TextComponentTranslation)☃).func_150268_i())) {
               this.field_192580_a.say(new TextComponentTranslation("chat.type.text.narrate", ((TextComponentTranslation)☃).func_150271_j()).getString());
            } else {
               this.field_192580_a.say(☃.getString());
            }
         }
      }
   }

   public void func_193641_a(int var1) {
      this.field_192580_a.clear();
      this.field_192580_a
         .say(new TextComponentTranslation("options.narrator").getString() + " : " + new TextComponentTranslation(GameSettings.field_193632_b[☃]).getString());
      GuiToast ☃ = Minecraft.func_71410_x().func_193033_an();
      if (this.field_192580_a.active()) {
         if (☃ == 0) {
            SystemToast.func_193657_a(☃, SystemToast.Type.NARRATOR_TOGGLE, new TextComponentTranslation("narrator.toast.disabled"), null);
         } else {
            SystemToast.func_193657_a(
               ☃,
               SystemToast.Type.NARRATOR_TOGGLE,
               new TextComponentTranslation("narrator.toast.enabled"),
               new TextComponentTranslation(GameSettings.field_193632_b[☃])
            );
         }
      } else {
         SystemToast.func_193657_a(
            ☃,
            SystemToast.Type.NARRATOR_TOGGLE,
            new TextComponentTranslation("narrator.toast.disabled"),
            new TextComponentTranslation("options.narrator.notavailable")
         );
      }
   }

   public boolean func_193640_a() {
      return this.field_192580_a.active();
   }

   public void func_193642_b() {
      this.field_192580_a.clear();
   }
}
