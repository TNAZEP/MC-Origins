package net.minecraft.client.gui.toasts;

import java.util.List;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class AdvancementToast implements IToast {
   private final Advancement field_193679_c;
   private boolean field_194168_d;

   public AdvancementToast(Advancement var1) {
      this.field_193679_c = ☃;
   }

   @Override
   public IToast.Visibility func_193653_a(GuiToast var1, long var2) {
      ☃.func_192989_b().func_110434_K().func_110577_a(field_193654_a);
      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      DisplayInfo ☃ = this.field_193679_c.func_192068_c();
      ☃.func_73729_b(0, 0, 0, 0, 160, 32);
      if (☃ != null) {
         List<String> ☃x = ☃.func_192989_b().field_71466_p.func_78271_c(☃.func_192297_a().func_150254_d(), 125);
         int ☃xx = ☃.func_192291_d() == FrameType.CHALLENGE ? 16746751 : 16776960;
         if (☃x.size() == 1) {
            ☃.func_192989_b()
               .field_71466_p
               .func_211126_b(I18n.func_135052_a("advancements.toast." + ☃.func_192291_d().func_192307_a()), 30.0F, 7.0F, ☃xx | 0xFF000000);
            ☃.func_192989_b().field_71466_p.func_211126_b(☃.func_192297_a().func_150254_d(), 30.0F, 18.0F, -1);
         } else {
            int ☃x = 1500;
            float ☃xx = 300.0F;
            if (☃ < 1500L) {
               int ☃xxx = MathHelper.func_76141_d(MathHelper.func_76131_a((float)(1500L - ☃) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
               ☃.func_192989_b()
                  .field_71466_p
                  .func_211126_b(I18n.func_135052_a("advancements.toast." + ☃.func_192291_d().func_192307_a()), 30.0F, 11.0F, ☃xx | ☃xxx);
            } else {
               int ☃x = MathHelper.func_76141_d(MathHelper.func_76131_a((float)(☃ - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
               int ☃xx = 16 - ☃x.size() * ☃.func_192989_b().field_71466_p.field_78288_b / 2;

               for(String ☃xxx : ☃x) {
                  ☃.func_192989_b().field_71466_p.func_211126_b(☃xxx, 30.0F, (float)☃xx, 16777215 | ☃x);
                  ☃xx += ☃.func_192989_b().field_71466_p.field_78288_b;
               }
            }
         }

         if (!this.field_194168_d && ☃ > 0L) {
            this.field_194168_d = true;
            if (☃.func_192291_d() == FrameType.CHALLENGE) {
               ☃.func_192989_b().func_147118_V().func_147682_a(SimpleSound.func_194007_a(SoundEvents.field_194228_if, 1.0F, 1.0F));
            }
         }

         RenderHelper.func_74520_c();
         ☃.func_192989_b().func_175599_af().func_184391_a(null, ☃.func_192298_b(), 8, 8);
         return ☃ >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
      } else {
         return IToast.Visibility.HIDE;
      }
   }
}
