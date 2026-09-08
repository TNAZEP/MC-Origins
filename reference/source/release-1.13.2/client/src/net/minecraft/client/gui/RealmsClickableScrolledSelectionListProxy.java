package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsClickableScrolledSelectionList;
import net.minecraft.realms.Tezzelator;

public class RealmsClickableScrolledSelectionListProxy extends GuiSlot {
   private final RealmsClickableScrolledSelectionList field_207723_v;

   public RealmsClickableScrolledSelectionListProxy(RealmsClickableScrolledSelectionList var1, int var2, int var3, int var4, int var5, int var6) {
      super(Minecraft.func_71410_x(), ☃, ☃, ☃, ☃, ☃);
      this.field_207723_v = ☃;
   }

   @Override
   protected int func_148127_b() {
      return this.field_207723_v.getItemCount();
   }

   @Override
   protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
      return this.field_207723_v.selectItem(☃, ☃, ☃, ☃);
   }

   @Override
   protected boolean func_148131_a(int var1) {
      return this.field_207723_v.isSelectedItem(☃);
   }

   @Override
   protected void func_148123_a() {
      this.field_207723_v.renderBackground();
   }

   @Override
   protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
      this.field_207723_v.renderItem(☃, ☃, ☃, ☃, ☃, ☃);
   }

   public int func_207716_c() {
      return this.field_148155_a;
   }

   @Override
   protected int func_148138_e() {
      return this.field_207723_v.getMaxPosition();
   }

   @Override
   protected int func_148137_d() {
      return this.field_207723_v.getScrollbarPosition();
   }

   @Override
   public boolean mouseScrolled(double var1) {
      return this.field_207723_v.mouseScrolled(☃) ? true : super.mouseScrolled(☃);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.field_207723_v.mouseClicked(☃, ☃, ☃) ? true : func_207715_a(this, ☃, ☃, ☃);
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      return this.field_207723_v.mouseReleased(☃, ☃, ☃);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      return this.field_207723_v.mouseDragged(☃, ☃, ☃, ☃, ☃) ? true : super.mouseDragged(☃, ☃, ☃, ☃, ☃);
   }

   public void func_207719_a(int var1, int var2, int var3, Tezzelator var4) {
      this.field_207723_v.renderSelected(☃, ☃, ☃, ☃);
   }

   @Override
   protected void func_192638_a(int var1, int var2, int var3, int var4, float var5) {
      int ☃ = this.func_148127_b();

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         int ☃xx = ☃ + ☃x * this.field_148149_f + this.field_148160_j;
         int ☃xxx = this.field_148149_f - 4;
         if (☃xx > this.field_148154_c || ☃xx + ☃xxx < this.field_148153_b) {
            this.func_192639_a(☃x, ☃, ☃xx, ☃);
         }

         if (this.field_148166_t && this.func_148131_a(☃x)) {
            this.func_207719_a(this.field_148155_a, ☃xx, ☃xxx, Tezzelator.instance);
         }

         this.func_192637_a(☃x, ☃, ☃xx, ☃xxx, ☃, ☃, ☃);
      }
   }

   public int func_207720_g() {
      return this.field_148153_b;
   }

   public int func_207721_h() {
      return this.field_148154_c;
   }

   public int func_207722_i() {
      return this.field_148160_j;
   }

   public double func_207717_j() {
      return this.field_148169_q;
   }

   public int func_207718_k() {
      return this.field_148149_f;
   }
}
