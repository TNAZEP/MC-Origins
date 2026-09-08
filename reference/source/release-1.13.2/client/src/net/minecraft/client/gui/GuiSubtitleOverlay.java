package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventListener;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GuiSubtitleOverlay extends Gui implements ISoundEventListener {
   private final Minecraft field_184069_a;
   private final List<GuiSubtitleOverlay.Subtitle> field_184070_f = Lists.<GuiSubtitleOverlay.Subtitle>newArrayList();
   private boolean field_184071_g;

   public GuiSubtitleOverlay(Minecraft var1) {
      this.field_184069_a = ☃;
   }

   public void func_195620_a() {
      if (!this.field_184071_g && this.field_184069_a.field_71474_y.field_186717_N) {
         this.field_184069_a.func_147118_V().func_184402_a(this);
         this.field_184071_g = true;
      } else if (this.field_184071_g && !this.field_184069_a.field_71474_y.field_186717_N) {
         this.field_184069_a.func_147118_V().func_184400_b(this);
         this.field_184071_g = false;
      }

      if (this.field_184071_g && !this.field_184070_f.isEmpty()) {
         GlStateManager.func_179094_E();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         Vec3d ☃ = new Vec3d(
            this.field_184069_a.field_71439_g.field_70165_t,
            this.field_184069_a.field_71439_g.field_70163_u + (double)this.field_184069_a.field_71439_g.func_70047_e(),
            this.field_184069_a.field_71439_g.field_70161_v
         );
         Vec3d ☃x = new Vec3d(0.0, 0.0, -1.0)
            .func_178789_a(-this.field_184069_a.field_71439_g.field_70125_A * (float) (Math.PI / 180.0))
            .func_178785_b(-this.field_184069_a.field_71439_g.field_70177_z * (float) (Math.PI / 180.0));
         Vec3d ☃xx = new Vec3d(0.0, 1.0, 0.0)
            .func_178789_a(-this.field_184069_a.field_71439_g.field_70125_A * (float) (Math.PI / 180.0))
            .func_178785_b(-this.field_184069_a.field_71439_g.field_70177_z * (float) (Math.PI / 180.0));
         Vec3d ☃xxx = ☃x.func_72431_c(☃xx);
         int ☃xxxx = 0;
         int ☃xxxxx = 0;
         Iterator<GuiSubtitleOverlay.Subtitle> ☃xxxxxx = this.field_184070_f.iterator();

         while(☃xxxxxx.hasNext()) {
            GuiSubtitleOverlay.Subtitle ☃xxxxxxx = (GuiSubtitleOverlay.Subtitle)☃xxxxxx.next();
            if (☃xxxxxxx.func_186825_b() + 3000L <= Util.func_211177_b()) {
               ☃xxxxxx.remove();
            } else {
               ☃xxxxx = Math.max(☃xxxxx, this.field_184069_a.field_71466_p.func_78256_a(☃xxxxxxx.func_186824_a()));
            }
         }

         ☃xxxxx += this.field_184069_a.field_71466_p.func_78256_a("<")
            + this.field_184069_a.field_71466_p.func_78256_a(" ")
            + this.field_184069_a.field_71466_p.func_78256_a(">")
            + this.field_184069_a.field_71466_p.func_78256_a(" ");

         for(GuiSubtitleOverlay.Subtitle ☃xxxxxxx : this.field_184070_f) {
            int ☃xxxxxxxx = 255;
            String ☃xxxxxxxxx = ☃xxxxxxx.func_186824_a();
            Vec3d ☃xxxxxxxxxx = ☃xxxxxxx.func_186826_c().func_178788_d(☃).func_72432_b();
            double ☃xxxxxxxxxxx = -☃xxx.func_72430_b(☃xxxxxxxxxx);
            double ☃xxxxxxxxxxxx = -☃x.func_72430_b(☃xxxxxxxxxx);
            boolean ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx > 0.5;
            int ☃xxxxxxxxxxxxxx = ☃xxxxx / 2;
            int ☃xxxxxxxxxxxxxxx = this.field_184069_a.field_71466_p.field_78288_b;
            int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx / 2;
            float ☃xxxxxxxxxxxxxxxxx = 1.0F;
            int ☃xxxxxxxxxxxxxxxxxx = this.field_184069_a.field_71466_p.func_78256_a(☃xxxxxxxxx);
            int ☃xxxxxxxxxxxxxxxxxxx = MathHelper.func_76128_c(
               MathHelper.func_151238_b(255.0, 75.0, (double)((float)(Util.func_211177_b() - ☃xxxxxxx.func_186825_b()) / 3000.0F))
            );
            int ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx << 16 | ☃xxxxxxxxxxxxxxxxxxx << 8 | ☃xxxxxxxxxxxxxxxxxxx;
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b(
               (float)this.field_184069_a.field_195558_d.func_198107_o() - (float)☃xxxxxxxxxxxxxx * 1.0F - 2.0F,
               (float)(this.field_184069_a.field_195558_d.func_198087_p() - 30) - (float)(☃xxxx * (☃xxxxxxxxxxxxxxx + 1)) * 1.0F,
               0.0F
            );
            GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
            func_73734_a(-☃xxxxxxxxxxxxxx - 1, -☃xxxxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxxx + 1, ☃xxxxxxxxxxxxxxxx + 1, -872415232);
            GlStateManager.func_179147_l();
            if (!☃xxxxxxxxxxxxx) {
               if (☃xxxxxxxxxxx > 0.0) {
                  this.field_184069_a
                     .field_71466_p
                     .func_211126_b(
                        ">",
                        (float)(☃xxxxxxxxxxxxxx - this.field_184069_a.field_71466_p.func_78256_a(">")),
                        (float)(-☃xxxxxxxxxxxxxxxx),
                        ☃xxxxxxxxxxxxxxxxxxxx + -16777216
                     );
               } else if (☃xxxxxxxxxxx < 0.0) {
                  this.field_184069_a
                     .field_71466_p
                     .func_211126_b("<", (float)(-☃xxxxxxxxxxxxxx), (float)(-☃xxxxxxxxxxxxxxxx), ☃xxxxxxxxxxxxxxxxxxxx + -16777216);
               }
            }

            this.field_184069_a
               .field_71466_p
               .func_211126_b(☃xxxxxxxxx, (float)(-☃xxxxxxxxxxxxxxxxxx / 2), (float)(-☃xxxxxxxxxxxxxxxx), ☃xxxxxxxxxxxxxxxxxxxx + -16777216);
            GlStateManager.func_179121_F();
            ++☃xxxx;
         }

         GlStateManager.func_179084_k();
         GlStateManager.func_179121_F();
      }
   }

   @Override
   public void func_184067_a(ISound var1, SoundEventAccessor var2) {
      if (☃.func_188712_c() != null) {
         String ☃ = ☃.func_188712_c().func_150254_d();
         if (!this.field_184070_f.isEmpty()) {
            for(GuiSubtitleOverlay.Subtitle ☃x : this.field_184070_f) {
               if (☃x.func_186824_a().equals(☃)) {
                  ☃x.func_186823_a(new Vec3d((double)☃.func_147649_g(), (double)☃.func_147654_h(), (double)☃.func_147651_i()));
                  return;
               }
            }
         }

         this.field_184070_f
            .add(new GuiSubtitleOverlay.Subtitle(☃, new Vec3d((double)☃.func_147649_g(), (double)☃.func_147654_h(), (double)☃.func_147651_i())));
      }
   }

   public class Subtitle {
      private final String field_186828_b;
      private long field_186829_c;
      private Vec3d field_186830_d;

      public Subtitle(String var2, Vec3d var3) {
         this.field_186828_b = ☃;
         this.field_186830_d = ☃;
         this.field_186829_c = Util.func_211177_b();
      }

      public String func_186824_a() {
         return this.field_186828_b;
      }

      public long func_186825_b() {
         return this.field_186829_c;
      }

      public Vec3d func_186826_c() {
         return this.field_186830_d;
      }

      public void func_186823_a(Vec3d var1) {
         this.field_186830_d = ☃;
         this.field_186829_c = Util.func_211177_b();
      }
   }
}
