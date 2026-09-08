package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsGuiEventListener;
import net.minecraft.realms.RealmsScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiScreenRealmsProxy extends GuiScreen {
   private final RealmsScreen field_154330_a;
   private static final Logger field_212333_f = LogManager.getLogger();

   public GuiScreenRealmsProxy(RealmsScreen var1) {
      this.field_154330_a = ☃;
   }

   public RealmsScreen func_154321_a() {
      return this.field_154330_a;
   }

   @Override
   public void func_146280_a(Minecraft var1, int var2, int var3) {
      this.field_154330_a.init(☃, ☃, ☃);
      super.func_146280_a(☃, ☃, ☃);
   }

   @Override
   protected void func_73866_w_() {
      this.field_154330_a.init();
      super.func_73866_w_();
   }

   public void func_154325_a(String var1, int var2, int var3, int var4) {
      super.func_73732_a(this.field_146289_q, ☃, ☃, ☃, ☃);
   }

   public void func_207734_a(String var1, int var2, int var3, int var4, boolean var5) {
      if (☃) {
         super.func_73731_b(this.field_146289_q, ☃, ☃, ☃, ☃);
      } else {
         this.field_146289_q.func_211126_b(☃, (float)☃, (float)☃, ☃);
      }
   }

   @Override
   public void func_73729_b(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_154330_a.blit(☃, ☃, ☃, ☃, ☃, ☃);
      super.func_73729_b(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_73733_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      super.func_73733_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_146276_q_() {
      super.func_146276_q_();
   }

   @Override
   public boolean func_73868_f() {
      return super.func_73868_f();
   }

   @Override
   public void func_146270_b(int var1) {
      super.func_146270_b(☃);
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.field_154330_a.render(☃, ☃, ☃);
   }

   @Override
   public void func_146285_a(ItemStack var1, int var2, int var3) {
      super.func_146285_a(☃, ☃, ☃);
   }

   @Override
   public void func_146279_a(String var1, int var2, int var3) {
      super.func_146279_a(☃, ☃, ☃);
   }

   @Override
   public void func_146283_a(List<String> var1, int var2, int var3) {
      super.func_146283_a(☃, ☃, ☃);
   }

   @Override
   public void func_73876_c() {
      this.field_154330_a.tick();
      super.func_73876_c();
   }

   public int func_154329_h() {
      return this.field_146289_q.field_78288_b;
   }

   public int func_207731_c(String var1) {
      return this.field_146289_q.func_78256_a(☃);
   }

   public void func_207728_b(String var1, int var2, int var3, int var4) {
      this.field_146289_q.func_175063_a(☃, (float)☃, (float)☃, ☃);
   }

   public List<String> func_154323_a(String var1, int var2) {
      return this.field_146289_q.func_78271_c(☃, ☃);
   }

   public void func_207735_j() {
      this.field_195124_j.clear();
   }

   public void func_207730_a(RealmsGuiEventListener var1) {
      if (this.func_212332_c(☃) || !this.field_195124_j.add(☃.getProxy())) {
         field_212333_f.error("Tried to add the same widget multiple times: " + ☃);
      }
   }

   public void func_207733_b(RealmsGuiEventListener var1) {
      if (!this.func_212332_c(☃) || !this.field_195124_j.remove(☃.getProxy())) {
         field_212333_f.error("Tried to add the same widget multiple times: " + ☃);
      }
   }

   public boolean func_212332_c(RealmsGuiEventListener var1) {
      return this.field_195124_j.contains(☃.getProxy());
   }

   public void func_154327_a(RealmsButton var1) {
      this.func_189646_b(☃.getProxy());
   }

   public List<RealmsButton> func_154320_j() {
      List<RealmsButton> ☃ = Lists.<RealmsButton>newArrayListWithExpectedSize(this.field_146292_n.size());

      for(GuiButton ☃x : this.field_146292_n) {
         ☃.add(((GuiButtonRealmsProxy)☃x).func_154317_g());
      }

      return ☃;
   }

   public void func_207729_m() {
      HashSet<IGuiEventListener> ☃ = new HashSet(this.field_146292_n);
      this.field_195124_j.removeIf(☃::contains);
      this.field_146292_n.clear();
   }

   public void func_207732_b(RealmsButton var1) {
      this.field_195124_j.remove(☃.getProxy());
      this.field_146292_n.remove(☃.getProxy());
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.field_154330_a.mouseClicked(☃, ☃, ☃) ? true : func_205730_a(this, ☃, ☃, ☃);
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      return this.field_154330_a.mouseReleased(☃, ☃, ☃);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      return this.field_154330_a.mouseDragged(☃, ☃, ☃, ☃, ☃) ? true : super.mouseDragged(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      return this.field_154330_a.keyPressed(☃, ☃, ☃) ? true : super.keyPressed(☃, ☃, ☃);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      return this.field_154330_a.charTyped(☃, ☃) ? true : super.charTyped(☃, ☃);
   }

   @Override
   public void confirmResult(boolean var1, int var2) {
      this.field_154330_a.confirmResult(☃, ☃);
   }

   @Override
   public void func_146281_b() {
      this.field_154330_a.removed();
      super.func_146281_b();
   }

   public int func_209208_b(String var1, int var2, int var3, int var4, boolean var5) {
      return ☃ ? this.field_146289_q.func_175063_a(☃, (float)☃, (float)☃, ☃) : this.field_146289_q.func_211126_b(☃, (float)☃, (float)☃, ☃);
   }
}
