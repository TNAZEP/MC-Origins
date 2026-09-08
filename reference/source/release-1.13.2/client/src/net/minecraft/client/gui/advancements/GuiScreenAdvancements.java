package net.minecraft.client.gui.advancements;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ClientAdvancementManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.util.ResourceLocation;

public class GuiScreenAdvancements extends GuiScreen implements ClientAdvancementManager.IListener {
   private static final ResourceLocation field_191943_f = new ResourceLocation("textures/gui/advancements/window.png");
   private static final ResourceLocation field_191945_g = new ResourceLocation("textures/gui/advancements/tabs.png");
   private final ClientAdvancementManager field_191946_h;
   private final Map<Advancement, GuiAdvancementTab> field_191947_i = Maps.<Advancement, GuiAdvancementTab>newLinkedHashMap();
   private GuiAdvancementTab field_191940_s;
   private boolean field_191944_v;

   public GuiScreenAdvancements(ClientAdvancementManager var1) {
      this.field_191946_h = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.field_191947_i.clear();
      this.field_191940_s = null;
      this.field_191946_h.func_192798_a(this);
      if (this.field_191940_s == null && !this.field_191947_i.isEmpty()) {
         this.field_191946_h.func_194230_a(((GuiAdvancementTab)this.field_191947_i.values().iterator().next()).func_193935_c(), true);
      } else {
         this.field_191946_h.func_194230_a(this.field_191940_s == null ? null : this.field_191940_s.func_193935_c(), true);
      }
   }

   @Override
   public void func_146281_b() {
      this.field_191946_h.func_192798_a(null);
      NetHandlerPlayClient ☃ = this.field_146297_k.func_147114_u();
      if (☃ != null) {
         ☃.func_147297_a(CPacketSeenAdvancements.func_194164_a());
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (☃ == 0) {
         int ☃ = (this.field_146294_l - 252) / 2;
         int ☃x = (this.field_146295_m - 140) / 2;

         for(GuiAdvancementTab ☃xx : this.field_191947_i.values()) {
            if (☃xx.func_195627_a(☃, ☃x, ☃, ☃)) {
               this.field_191946_h.func_194230_a(☃xx.func_193935_c(), true);
               break;
            }
         }
      }

      return super.mouseClicked(☃, ☃, ☃);
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.field_146297_k.field_71474_y.field_194146_ao.func_197976_a(☃, ☃)) {
         this.field_146297_k.func_147108_a(null);
         this.field_146297_k.field_71417_B.func_198034_i();
         return true;
      } else {
         return super.keyPressed(☃, ☃, ☃);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      int ☃ = (this.field_146294_l - 252) / 2;
      int ☃x = (this.field_146295_m - 140) / 2;
      this.func_146276_q_();
      this.func_191936_c(☃, ☃, ☃, ☃x);
      this.func_191934_b(☃, ☃x);
      this.func_191937_d(☃, ☃, ☃, ☃x);
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (☃ != 0) {
         this.field_191944_v = false;
         return false;
      } else {
         if (!this.field_191944_v) {
            this.field_191944_v = true;
         } else if (this.field_191940_s != null) {
            this.field_191940_s.func_195626_a(☃, ☃);
         }

         return true;
      }
   }

   private void func_191936_c(int var1, int var2, int var3, int var4) {
      GuiAdvancementTab ☃ = this.field_191940_s;
      if (☃ == null) {
         func_73734_a(☃ + 9, ☃ + 18, ☃ + 9 + 234, ☃ + 18 + 113, -16777216);
         String ☃x = I18n.func_135052_a("advancements.empty");
         int ☃xx = this.field_146289_q.func_78256_a(☃x);
         this.field_146289_q.func_211126_b(☃x, (float)(☃ + 9 + 117 - ☃xx / 2), (float)(☃ + 18 + 56 - this.field_146289_q.field_78288_b / 2), -1);
         this.field_146289_q
            .func_211126_b(
               ":(", (float)(☃ + 9 + 117 - this.field_146289_q.func_78256_a(":(") / 2), (float)(☃ + 18 + 113 - this.field_146289_q.field_78288_b), -1
            );
      } else {
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b((float)(☃ + 9), (float)(☃ + 18), -400.0F);
         GlStateManager.func_179126_j();
         ☃.func_191799_a();
         GlStateManager.func_179121_F();
         GlStateManager.func_179143_c(515);
         GlStateManager.func_179097_i();
      }
   }

   public void func_191934_b(int var1, int var2) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179147_l();
      RenderHelper.func_74518_a();
      this.field_146297_k.func_110434_K().func_110577_a(field_191943_f);
      this.func_73729_b(☃, ☃, 0, 0, 252, 140);
      if (this.field_191947_i.size() > 1) {
         this.field_146297_k.func_110434_K().func_110577_a(field_191945_g);

         for(GuiAdvancementTab ☃ : this.field_191947_i.values()) {
            ☃.func_191798_a(☃, ☃, ☃ == this.field_191940_s);
         }

         GlStateManager.func_179091_B();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         RenderHelper.func_74520_c();

         for(GuiAdvancementTab ☃ : this.field_191947_i.values()) {
            ☃.func_191796_a(☃, ☃, this.field_146296_j);
         }

         GlStateManager.func_179084_k();
      }

      this.field_146289_q.func_211126_b(I18n.func_135052_a("gui.advancements"), (float)(☃ + 8), (float)(☃ + 6), 4210752);
   }

   private void func_191937_d(int var1, int var2, int var3, int var4) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.field_191940_s != null) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179126_j();
         GlStateManager.func_179109_b((float)(☃ + 9), (float)(☃ + 18), 400.0F);
         this.field_191940_s.func_192991_a(☃ - ☃ - 9, ☃ - ☃ - 18, ☃, ☃);
         GlStateManager.func_179097_i();
         GlStateManager.func_179121_F();
      }

      if (this.field_191947_i.size() > 1) {
         for(GuiAdvancementTab ☃ : this.field_191947_i.values()) {
            if (☃.func_195627_a(☃, ☃, (double)☃, (double)☃)) {
               this.func_146279_a(☃.func_191795_d(), ☃, ☃);
            }
         }
      }
   }

   @Override
   public void func_191931_a(Advancement var1) {
      GuiAdvancementTab ☃ = GuiAdvancementTab.func_193936_a(this.field_146297_k, this, this.field_191947_i.size(), ☃);
      if (☃ != null) {
         this.field_191947_i.put(☃, ☃);
      }
   }

   @Override
   public void func_191928_b(Advancement var1) {
   }

   @Override
   public void func_191932_c(Advancement var1) {
      GuiAdvancementTab ☃ = this.func_191935_f(☃);
      if (☃ != null) {
         ☃.func_191800_a(☃);
      }
   }

   @Override
   public void func_191929_d(Advancement var1) {
   }

   @Override
   public void func_191933_a(Advancement var1, AdvancementProgress var2) {
      GuiAdvancement ☃ = this.func_191938_e(☃);
      if (☃ != null) {
         ☃.func_191824_a(☃);
      }
   }

   @Override
   public void func_193982_e(@Nullable Advancement var1) {
      this.field_191940_s = (GuiAdvancementTab)this.field_191947_i.get(☃);
   }

   @Override
   public void func_191930_a() {
      this.field_191947_i.clear();
      this.field_191940_s = null;
   }

   @Nullable
   public GuiAdvancement func_191938_e(Advancement var1) {
      GuiAdvancementTab ☃ = this.func_191935_f(☃);
      return ☃ == null ? null : ☃.func_191794_b(☃);
   }

   @Nullable
   private GuiAdvancementTab func_191935_f(Advancement var1) {
      while(☃.func_192070_b() != null) {
         ☃ = ☃.func_192070_b();
      }

      return (GuiAdvancementTab)this.field_191947_i.get(☃);
   }
}
