package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.util.text.TextFormatting;

public abstract class GuiResourcePackList extends GuiListExtended<ResourcePackListEntryFound> {
   protected final Minecraft field_148205_k;

   public GuiResourcePackList(Minecraft var1, int var2, int var3) {
      super(☃, ☃, ☃, 32, ☃ - 55 + 4, 36);
      this.field_148205_k = ☃;
      this.field_148163_i = false;
      this.func_148133_a(true, (int)((float)☃.field_71466_p.field_78288_b * 1.5F));
   }

   @Override
   protected void func_148129_a(int var1, int var2, Tessellator var3) {
      String ☃ = TextFormatting.UNDERLINE + "" + TextFormatting.BOLD + this.func_148202_k();
      this.field_148205_k
         .field_71466_p
         .func_211126_b(
            ☃,
            (float)(☃ + this.field_148155_a / 2 - this.field_148205_k.field_71466_p.func_78256_a(☃) / 2),
            (float)Math.min(this.field_148153_b + 3, ☃),
            16777215
         );
   }

   protected abstract String func_148202_k();

   @Override
   public int func_148139_c() {
      return this.field_148155_a;
   }

   @Override
   protected int func_148137_d() {
      return this.field_148151_d - 6;
   }

   public void func_195095_a(ResourcePackListEntryFound var1) {
      super.func_195085_a(☃);
   }
}
