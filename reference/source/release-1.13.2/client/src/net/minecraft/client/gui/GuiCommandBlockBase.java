package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;

public abstract class GuiCommandBlockBase extends GuiScreen {
   protected GuiTextField field_195237_a;
   protected GuiTextField field_195239_f;
   protected GuiButton field_195240_g;
   protected GuiButton field_195241_h;
   protected GuiButton field_195242_i;
   protected boolean field_195238_s;
   protected final List<String> field_209111_t = Lists.newArrayList();
   protected int field_209112_u;
   protected int field_209113_v;
   protected ParseResults<ISuggestionProvider> field_209114_w;
   protected CompletableFuture<Suggestions> field_209115_x;
   protected GuiCommandBlockBase.SuggestionsList field_209116_y;
   private boolean field_212342_z;

   @Override
   public void func_73876_c() {
      this.field_195237_a.func_146178_a();
   }

   abstract CommandBlockBaseLogic func_195231_h();

   abstract int func_195236_i();

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      this.field_195240_g = this.func_189646_b(
         new GuiButton(0, this.field_146294_l / 2 - 4 - 150, this.field_146295_m / 4 + 120 + 12, 150, 20, I18n.func_135052_a("gui.done")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiCommandBlockBase.this.func_195234_k();
            }
         }
      );
      this.field_195241_h = this.func_189646_b(
         new GuiButton(1, this.field_146294_l / 2 + 4, this.field_146295_m / 4 + 120 + 12, 150, 20, I18n.func_135052_a("gui.cancel")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiCommandBlockBase.this.func_195232_m();
            }
         }
      );
      this.field_195242_i = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 150 - 20, this.func_195236_i(), 20, 20, "O") {
         @Override
         public void func_194829_a(double var1, double var3) {
            CommandBlockBaseLogic ☃ = GuiCommandBlockBase.this.func_195231_h();
            ☃.func_175573_a(!☃.func_175571_m());
            GuiCommandBlockBase.this.func_195233_j();
         }
      });
      this.field_195237_a = new GuiTextField(2, this.field_146289_q, this.field_146294_l / 2 - 150, 50, 300, 20) {
         @Override
         public void func_146195_b(boolean var1) {
            super.func_146195_b(☃);
            if (☃) {
               GuiCommandBlockBase.this.field_195239_f.func_146195_b(false);
            }
         }
      };
      this.field_195237_a.func_146203_f(32500);
      this.field_195237_a.func_195607_a(this::func_209104_a);
      this.field_195237_a.func_195609_a(this::func_209103_a);
      this.field_195124_j.add(this.field_195237_a);
      this.field_195239_f = new GuiTextField(3, this.field_146289_q, this.field_146294_l / 2 - 150, this.func_195236_i(), 276, 20) {
         @Override
         public void func_146195_b(boolean var1) {
            super.func_146195_b(☃);
            if (☃) {
               GuiCommandBlockBase.this.field_195237_a.func_146195_b(false);
            }
         }
      };
      this.field_195239_f.func_146203_f(32500);
      this.field_195239_f.func_146184_c(false);
      this.field_195239_f.func_146180_a("-");
      this.field_195124_j.add(this.field_195239_f);
      this.field_195237_a.func_146195_b(true);
      this.func_195073_a(this.field_195237_a);
      this.func_209106_o();
   }

   @Override
   public void func_175273_b(Minecraft var1, int var2, int var3) {
      String ☃ = this.field_195237_a.func_146179_b();
      this.func_146280_a(☃, ☃, ☃);
      this.func_209102_a(☃);
      this.func_209106_o();
   }

   protected void func_195233_j() {
      if (this.func_195231_h().func_175571_m()) {
         this.field_195242_i.field_146126_j = "O";
         this.field_195239_f.func_146180_a(this.func_195231_h().func_145749_h().getString());
      } else {
         this.field_195242_i.field_146126_j = "X";
         this.field_195239_f.func_146180_a("-");
      }
   }

   protected void func_195234_k() {
      CommandBlockBaseLogic ☃ = this.func_195231_h();
      this.func_195235_a(☃);
      if (!☃.func_175571_m()) {
         ☃.func_145750_b(null);
      }

      this.field_146297_k.func_147108_a(null);
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
   }

   protected abstract void func_195235_a(CommandBlockBaseLogic var1);

   protected void func_195232_m() {
      this.func_195231_h().func_175573_a(this.field_195238_s);
      this.field_146297_k.func_147108_a(null);
   }

   @Override
   public void func_195122_V_() {
      this.func_195232_m();
   }

   private void func_209103_a(int var1, String var2) {
      this.func_209106_o();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (☃ == 257 || ☃ == 335) {
         this.func_195234_k();
         return true;
      } else if (this.field_209116_y != null && this.field_209116_y.func_209133_b(☃, ☃, ☃)) {
         return true;
      } else {
         if (☃ == 258) {
            this.func_209109_s();
         }

         return super.keyPressed(☃, ☃, ☃);
      }
   }

   @Override
   public boolean mouseScrolled(double var1) {
      return this.field_209116_y != null && this.field_209116_y.func_209232_a(MathHelper.func_151237_a(☃, -1.0, 1.0)) ? true : super.mouseScrolled(☃);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      return this.field_209116_y != null && this.field_209116_y.func_209233_a((int)☃, (int)☃, ☃) ? true : super.mouseClicked(☃, ☃, ☃);
   }

   protected void func_209106_o() {
      this.field_209114_w = null;
      if (!this.field_212342_z) {
         this.field_195237_a.func_195612_c(null);
         this.field_209116_y = null;
      }

      this.field_209111_t.clear();
      CommandDispatcher<ISuggestionProvider> ☃ = this.field_146297_k.field_71439_g.field_71174_a.func_195515_i();
      String ☃x = this.field_195237_a.func_146179_b();
      StringReader ☃xx = new StringReader(☃x);
      if (☃xx.canRead() && ☃xx.peek() == '/') {
         ☃xx.skip();
      }

      this.field_209114_w = ☃.parse(☃xx, this.field_146297_k.field_71439_g.field_71174_a.func_195513_b());
      if (this.field_209116_y == null || !this.field_212342_z) {
         StringReader ☃ = new StringReader(☃x.substring(0, Math.min(☃x.length(), this.field_195237_a.func_146198_h())));
         if (☃.canRead() && ☃.peek() == '/') {
            ☃.skip();
         }

         ParseResults<ISuggestionProvider> ☃ = ☃.parse(☃, this.field_146297_k.field_71439_g.field_71174_a.func_195513_b());
         this.field_209115_x = ☃.getCompletionSuggestions(☃);
         this.field_209115_x.thenRun(() -> {
            if (this.field_209115_x.isDone()) {
               this.func_209107_u();
            }
         });
      }
   }

   private void func_209107_u() {
      if (((Suggestions)this.field_209115_x.join()).isEmpty()
         && !this.field_209114_w.getExceptions().isEmpty()
         && this.field_195237_a.func_146198_h() == this.field_195237_a.func_146179_b().length()) {
         int ☃ = 0;

         for(Entry<CommandNode<ISuggestionProvider>, CommandSyntaxException> ☃x : this.field_209114_w.getExceptions().entrySet()) {
            CommandSyntaxException ☃xx = (CommandSyntaxException)☃x.getValue();
            if (☃xx.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
               ++☃;
            } else {
               this.field_209111_t.add(☃xx.getMessage());
            }
         }

         if (☃ > 0) {
            this.field_209111_t.add(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create().getMessage());
         }
      }

      this.field_209112_u = 0;
      this.field_209113_v = this.field_146294_l;
      if (this.field_209111_t.isEmpty()) {
         this.func_209108_a(TextFormatting.GRAY);
      }

      this.field_209116_y = null;
      if (this.field_146297_k.field_71474_y.field_198018_T) {
         this.func_209109_s();
      }
   }

   private String func_209104_a(String var1, int var2) {
      return this.field_209114_w != null ? GuiChat.func_212336_a(this.field_209114_w, ☃, ☃) : ☃;
   }

   private void func_209108_a(TextFormatting var1) {
      CommandContextBuilder<ISuggestionProvider> ☃ = this.field_209114_w.getContext();
      CommandContextBuilder<ISuggestionProvider> ☃x = ☃.getLastChild();
      if (!☃x.getNodes().isEmpty()) {
         CommandNode<ISuggestionProvider> ☃xx;
         int ☃xxx;
         if (this.field_209114_w.getReader().canRead()) {
            Entry<CommandNode<ISuggestionProvider>, StringRange> ☃xxxx = Iterables.getLast(☃x.getNodes().entrySet());
            ☃xx = (CommandNode)☃xxxx.getKey();
            ☃xxx = ((StringRange)☃xxxx.getValue()).getEnd() + 1;
         } else if (☃x.getNodes().size() > 1) {
            Entry<CommandNode<ISuggestionProvider>, StringRange> ☃xx = Iterables.get(☃x.getNodes().entrySet(), ☃x.getNodes().size() - 2);
            ☃xx = (CommandNode)☃xx.getKey();
            ☃xxx = ((StringRange)☃xx.getValue()).getEnd() + 1;
         } else {
            if (☃ == ☃x || ☃x.getNodes().isEmpty()) {
               return;
            }

            Entry<CommandNode<ISuggestionProvider>, StringRange> ☃xx = Iterables.getLast(☃x.getNodes().entrySet());
            ☃xx = (CommandNode)☃xx.getKey();
            ☃xxx = ((StringRange)☃xx.getValue()).getEnd() + 1;
         }

         Map<CommandNode<ISuggestionProvider>, String> ☃xx = this.field_146297_k
            .field_71439_g
            .field_71174_a
            .func_195515_i()
            .getSmartUsage(☃xx, this.field_146297_k.field_71439_g.field_71174_a.func_195513_b());
         List<String> ☃xxx = Lists.newArrayList();
         int ☃xxxx = 0;

         for(Entry<CommandNode<ISuggestionProvider>, String> ☃xxxxx : ☃xx.entrySet()) {
            if (!(☃xxxxx.getKey() instanceof LiteralCommandNode)) {
               ☃xxx.add(☃ + (String)☃xxxxx.getValue());
               ☃xxxx = Math.max(☃xxxx, this.field_146289_q.func_78256_a((String)☃xxxxx.getValue()));
            }
         }

         if (!☃xxx.isEmpty()) {
            this.field_209111_t.addAll(☃xxx);
            this.field_209112_u = MathHelper.func_76125_a(
               this.field_195237_a.func_195611_j(☃xxx) + this.field_146289_q.func_78256_a(" "),
               0,
               this.field_195237_a.func_195611_j(0) + this.field_146289_q.func_78256_a(" ") + this.field_195237_a.func_146200_o() - ☃xxxx
            );
            this.field_209113_v = ☃xxxx;
         }
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("advMode.setCommand"), this.field_146294_l / 2, 20, 16777215);
      this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.command"), this.field_146294_l / 2 - 150, 40, 10526880);
      this.field_195237_a.func_195608_a(☃, ☃, ☃);
      int ☃ = 75;
      if (!this.field_195239_f.func_146179_b().isEmpty()) {
         ☃ += 5 * this.field_146289_q.field_78288_b + 1 + this.func_195236_i() - 135;
         this.func_73731_b(this.field_146289_q, I18n.func_135052_a("advMode.previousOutput"), this.field_146294_l / 2 - 150, ☃ + 4, 10526880);
         this.field_195239_f.func_195608_a(☃, ☃, ☃);
      }

      super.func_73863_a(☃, ☃, ☃);
      if (this.field_209116_y != null) {
         this.field_209116_y.func_209129_a(☃, ☃);
      } else {
         ☃ = 0;

         for(String ☃ : this.field_209111_t) {
            func_73734_a(this.field_209112_u - 1, 72 + 12 * ☃, this.field_209112_u + this.field_209113_v + 1, 84 + 12 * ☃, Integer.MIN_VALUE);
            this.field_146289_q.func_175063_a(☃, (float)this.field_209112_u, (float)(74 + 12 * ☃), -1);
            ++☃;
         }
      }
   }

   public void func_209109_s() {
      if (this.field_209115_x != null && this.field_209115_x.isDone()) {
         Suggestions ☃ = (Suggestions)this.field_209115_x.join();
         if (!☃.isEmpty()) {
            int ☃x = 0;

            for(Suggestion ☃xx : ☃.getList()) {
               ☃x = Math.max(☃x, this.field_146289_q.func_78256_a(☃xx.getText()));
            }

            int ☃xx = MathHelper.func_76125_a(
               this.field_195237_a.func_195611_j(☃.getRange().getStart()) + this.field_146289_q.func_78256_a(" "),
               0,
               this.field_195237_a.func_195611_j(0) + this.field_146289_q.func_78256_a(" ") + this.field_195237_a.func_146200_o() - ☃x
            );
            this.field_209116_y = new GuiCommandBlockBase.SuggestionsList(☃xx, 72, ☃x, ☃);
         }
      }
   }

   protected void func_209102_a(String var1) {
      this.field_195237_a.func_146180_a(☃);
   }

   @Nullable
   private static String func_212339_b(String var0, String var1) {
      return ☃.startsWith(☃) ? ☃.substring(☃.length()) : null;
   }

   class SuggestionsList {
      private final Rectangle2d field_209135_b;
      private final Suggestions field_209136_c;
      private final String field_212467_d;
      private int field_209138_e;
      private int field_209139_f;
      private Vec2f field_209140_g = Vec2f.field_189974_a;
      private boolean field_209141_h;

      private SuggestionsList(int var2, int var3, int var4, Suggestions var5) {
         this.field_209135_b = new Rectangle2d(☃ - 1, ☃, ☃ + 1, Math.min(☃.getList().size(), 7) * 12);
         this.field_209136_c = ☃;
         this.field_212467_d = GuiCommandBlockBase.this.field_195237_a.func_146179_b();
         this.func_209130_b(0);
      }

      public void func_209129_a(int var1, int var2) {
         int ☃ = Math.min(this.field_209136_c.getList().size(), 7);
         int ☃x = Integer.MIN_VALUE;
         int ☃xx = -5592406;
         boolean ☃xxx = this.field_209138_e > 0;
         boolean ☃xxxx = this.field_209136_c.getList().size() > this.field_209138_e + ☃;
         boolean ☃xxxxx = ☃xxx || ☃xxxx;
         boolean ☃xxxxxx = this.field_209140_g.field_189982_i != (float)☃ || this.field_209140_g.field_189983_j != (float)☃;
         if (☃xxxxxx) {
            this.field_209140_g = new Vec2f((float)☃, (float)☃);
         }

         if (☃xxxxx) {
            Gui.func_73734_a(
               this.field_209135_b.func_199318_a(),
               this.field_209135_b.func_199319_b() - 1,
               this.field_209135_b.func_199318_a() + this.field_209135_b.func_199316_c(),
               this.field_209135_b.func_199319_b(),
               Integer.MIN_VALUE
            );
            Gui.func_73734_a(
               this.field_209135_b.func_199318_a(),
               this.field_209135_b.func_199319_b() + this.field_209135_b.func_199317_d(),
               this.field_209135_b.func_199318_a() + this.field_209135_b.func_199316_c(),
               this.field_209135_b.func_199319_b() + this.field_209135_b.func_199317_d() + 1,
               Integer.MIN_VALUE
            );
            if (☃xxx) {
               for(int ☃ = 0; ☃ < this.field_209135_b.func_199316_c(); ++☃) {
                  if (☃ % 2 == 0) {
                     Gui.func_73734_a(
                        this.field_209135_b.func_199318_a() + ☃,
                        this.field_209135_b.func_199319_b() - 1,
                        this.field_209135_b.func_199318_a() + ☃ + 1,
                        this.field_209135_b.func_199319_b(),
                        -1
                     );
                  }
               }
            }

            if (☃xxxx) {
               for(int ☃ = 0; ☃ < this.field_209135_b.func_199316_c(); ++☃) {
                  if (☃ % 2 == 0) {
                     Gui.func_73734_a(
                        this.field_209135_b.func_199318_a() + ☃,
                        this.field_209135_b.func_199319_b() + this.field_209135_b.func_199317_d(),
                        this.field_209135_b.func_199318_a() + ☃ + 1,
                        this.field_209135_b.func_199319_b() + this.field_209135_b.func_199317_d() + 1,
                        -1
                     );
                  }
               }
            }
         }

         boolean ☃ = false;

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            Suggestion ☃xx = (Suggestion)this.field_209136_c.getList().get(☃x + this.field_209138_e);
            Gui.func_73734_a(
               this.field_209135_b.func_199318_a(),
               this.field_209135_b.func_199319_b() + 12 * ☃x,
               this.field_209135_b.func_199318_a() + this.field_209135_b.func_199316_c(),
               this.field_209135_b.func_199319_b() + 12 * ☃x + 12,
               Integer.MIN_VALUE
            );
            if (☃ > this.field_209135_b.func_199318_a()
               && ☃ < this.field_209135_b.func_199318_a() + this.field_209135_b.func_199316_c()
               && ☃ > this.field_209135_b.func_199319_b() + 12 * ☃x
               && ☃ < this.field_209135_b.func_199319_b() + 12 * ☃x + 12) {
               if (☃xxxxxx) {
                  this.func_209130_b(☃x + this.field_209138_e);
               }

               ☃ = true;
            }

            GuiCommandBlockBase.this.field_146289_q
               .func_175063_a(
                  ☃xx.getText(),
                  (float)(this.field_209135_b.func_199318_a() + 1),
                  (float)(this.field_209135_b.func_199319_b() + 2 + 12 * ☃x),
                  ☃x + this.field_209138_e == this.field_209139_f ? -256 : -5592406
               );
         }

         if (☃) {
            Message ☃x = ((Suggestion)this.field_209136_c.getList().get(this.field_209139_f)).getTooltip();
            if (☃x != null) {
               GuiCommandBlockBase.this.func_146279_a(TextComponentUtils.func_202465_a(☃x).func_150254_d(), ☃, ☃);
            }
         }
      }

      public boolean func_209233_a(int var1, int var2, int var3) {
         if (!this.field_209135_b.func_199315_b(☃, ☃)) {
            return false;
         } else {
            int ☃ = (☃ - this.field_209135_b.func_199319_b()) / 12 + this.field_209138_e;
            if (☃ >= 0 && ☃ < this.field_209136_c.getList().size()) {
               this.func_209130_b(☃);
               this.func_209131_a();
            }

            return true;
         }
      }

      public boolean func_209232_a(double var1) {
         int ☃ = (int)(
            GuiCommandBlockBase.this.field_146297_k.field_71417_B.func_198024_e()
               * (double)GuiCommandBlockBase.this.field_146297_k.field_195558_d.func_198107_o()
               / (double)GuiCommandBlockBase.this.field_146297_k.field_195558_d.func_198105_m()
         );
         int ☃x = (int)(
            GuiCommandBlockBase.this.field_146297_k.field_71417_B.func_198026_f()
               * (double)GuiCommandBlockBase.this.field_146297_k.field_195558_d.func_198087_p()
               / (double)GuiCommandBlockBase.this.field_146297_k.field_195558_d.func_198083_n()
         );
         if (this.field_209135_b.func_199315_b(☃, ☃x)) {
            this.field_209138_e = MathHelper.func_76125_a((int)((double)this.field_209138_e - ☃), 0, Math.max(this.field_209136_c.getList().size() - 7, 0));
            return true;
         } else {
            return false;
         }
      }

      public boolean func_209133_b(int var1, int var2, int var3) {
         if (☃ == 265) {
            this.func_209128_a(-1);
            this.field_209141_h = false;
            return true;
         } else if (☃ == 264) {
            this.func_209128_a(1);
            this.field_209141_h = false;
            return true;
         } else if (☃ == 258) {
            if (this.field_209141_h) {
               this.func_209128_a(GuiScreen.func_146272_n() ? -1 : 1);
            }

            this.func_209131_a();
            return true;
         } else if (☃ == 256) {
            this.func_209132_b();
            return true;
         } else {
            return false;
         }
      }

      public void func_209128_a(int var1) {
         this.func_209130_b(this.field_209139_f + ☃);
         int ☃ = this.field_209138_e;
         int ☃x = this.field_209138_e + 7 - 1;
         if (this.field_209139_f < ☃) {
            this.field_209138_e = MathHelper.func_76125_a(this.field_209139_f, 0, Math.max(this.field_209136_c.getList().size() - 7, 0));
         } else if (this.field_209139_f > ☃x) {
            this.field_209138_e = MathHelper.func_76125_a(this.field_209139_f - 7, 0, Math.max(this.field_209136_c.getList().size() - 7, 0));
         }
      }

      public void func_209130_b(int var1) {
         this.field_209139_f = ☃;
         if (this.field_209139_f < 0) {
            this.field_209139_f += this.field_209136_c.getList().size();
         }

         if (this.field_209139_f >= this.field_209136_c.getList().size()) {
            this.field_209139_f -= this.field_209136_c.getList().size();
         }

         Suggestion ☃ = (Suggestion)this.field_209136_c.getList().get(this.field_209139_f);
         GuiCommandBlockBase.this.field_195237_a
            .func_195612_c(GuiCommandBlockBase.func_212339_b(GuiCommandBlockBase.this.field_195237_a.func_146179_b(), ☃.apply(this.field_212467_d)));
      }

      public void func_209131_a() {
         Suggestion ☃ = (Suggestion)this.field_209136_c.getList().get(this.field_209139_f);
         GuiCommandBlockBase.this.field_212342_z = true;
         GuiCommandBlockBase.this.func_209102_a(☃.apply(this.field_212467_d));
         int ☃x = ☃.getRange().getStart() + ☃.getText().length();
         GuiCommandBlockBase.this.field_195237_a.func_212422_f(☃x);
         GuiCommandBlockBase.this.field_195237_a.func_146199_i(☃x);
         this.func_209130_b(this.field_209139_f);
         GuiCommandBlockBase.this.field_212342_z = false;
         this.field_209141_h = true;
      }

      public void func_209132_b() {
         GuiCommandBlockBase.this.field_209116_y = null;
      }
   }
}
