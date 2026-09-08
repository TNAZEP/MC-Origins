package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;

public class GuiChat extends GuiScreen {
   private static final Pattern field_208608_i = Pattern.compile("(\\s+)");
   private String field_146410_g = "";
   private int field_146416_h = -1;
   protected GuiTextField field_146415_a;
   private String field_146409_v = "";
   protected final List<String> field_195136_f = Lists.newArrayList();
   protected int field_195138_g;
   protected int field_195140_h;
   private ParseResults<ISuggestionProvider> field_195135_u;
   private CompletableFuture<Suggestions> field_195137_v;
   private GuiChat.SuggestionsList field_195139_w;
   private boolean field_211139_z;
   private boolean field_212338_z;

   public GuiChat() {
   }

   public GuiChat(String var1) {
      this.field_146409_v = ☃;
   }

   @Nullable
   @Override
   public IGuiEventListener getFocused() {
      return this.field_146415_a;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      this.field_146416_h = this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().size();
      this.field_146415_a = new GuiTextField(0, this.field_146289_q, 4, this.field_146295_m - 12, this.field_146294_l - 4, 12);
      this.field_146415_a.func_146203_f(256);
      this.field_146415_a.func_146185_a(false);
      this.field_146415_a.func_146195_b(true);
      this.field_146415_a.func_146180_a(this.field_146409_v);
      this.field_146415_a.func_146205_d(false);
      this.field_146415_a.func_195607_a(this::func_195130_a);
      this.field_146415_a.func_195609_a(this::func_195128_a);
      this.field_195124_j.add(this.field_146415_a);
      this.func_195129_h();
   }

   @Override
   public void func_175273_b(Minecraft var1, int var2, int var3) {
      String ☃ = this.field_146415_a.func_146179_b();
      this.func_146280_a(☃, ☃, ☃);
      this.func_208604_b(☃);
      this.func_195129_h();
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
      this.field_146297_k.field_71456_v.func_146158_b().func_146240_d();
   }

   @Override
   public void func_73876_c() {
      this.field_146415_a.func_146178_a();
   }

   private void func_195128_a(int var1, String var2) {
      String ☃ = this.field_146415_a.func_146179_b();
      this.field_211139_z = !☃.equals(this.field_146409_v);
      this.func_195129_h();
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (this.field_195139_w != null && this.field_195139_w.func_198503_b(☃, ☃, ☃)) {
         return true;
      } else if (☃ == 256) {
         this.field_146297_k.func_147108_a(null);
         return true;
      } else if (☃ == 257 || ☃ == 335) {
         String ☃ = this.field_146415_a.func_146179_b().trim();
         if (!☃.isEmpty()) {
            this.func_175275_f(☃);
         }

         this.field_146297_k.func_147108_a(null);
         return true;
      } else if (☃ == 265) {
         this.func_146402_a(-1);
         return true;
      } else if (☃ == 264) {
         this.func_146402_a(1);
         return true;
      } else if (☃ == 266) {
         this.field_146297_k.field_71456_v.func_146158_b().func_194813_a((double)(this.field_146297_k.field_71456_v.func_146158_b().func_146232_i() - 1));
         return true;
      } else if (☃ == 267) {
         this.field_146297_k.field_71456_v.func_146158_b().func_194813_a((double)(-this.field_146297_k.field_71456_v.func_146158_b().func_146232_i() + 1));
         return true;
      } else {
         if (☃ == 258) {
            this.field_211139_z = true;
            this.func_195131_X_();
         }

         return this.field_146415_a.keyPressed(☃, ☃, ☃);
      }
   }

   public void func_195131_X_() {
      if (this.field_195137_v != null && this.field_195137_v.isDone()) {
         int ☃ = 0;
         Suggestions ☃x = (Suggestions)this.field_195137_v.join();
         if (!☃x.getList().isEmpty()) {
            for(Suggestion ☃xx : ☃x.getList()) {
               ☃ = Math.max(☃, this.field_146289_q.func_78256_a(☃xx.getText()));
            }

            int ☃xx = MathHelper.func_76125_a(this.field_146415_a.func_195611_j(☃x.getRange().getStart()), 0, this.field_146294_l - ☃);
            this.field_195139_w = new GuiChat.SuggestionsList(☃xx, this.field_146295_m - 12, ☃, ☃x);
         }
      }
   }

   private static int func_208603_a(String var0) {
      if (Strings.isNullOrEmpty(☃)) {
         return 0;
      } else {
         int ☃ = 0;
         Matcher ☃x = field_208608_i.matcher(☃);

         while(☃x.find()) {
            ☃ = ☃x.end();
         }

         return ☃;
      }
   }

   private void func_195129_h() {
      this.field_195135_u = null;
      if (!this.field_212338_z) {
         this.field_146415_a.func_195612_c(null);
         this.field_195139_w = null;
      }

      this.field_195136_f.clear();
      String ☃ = this.field_146415_a.func_146179_b();
      StringReader ☃x = new StringReader(☃);
      if (☃x.canRead() && ☃x.peek() == '/') {
         ☃x.skip();
         CommandDispatcher<ISuggestionProvider> ☃xx = this.field_146297_k.field_71439_g.field_71174_a.func_195515_i();
         this.field_195135_u = ☃xx.parse(☃x, this.field_146297_k.field_71439_g.field_71174_a.func_195513_b());
         if (this.field_195139_w == null || !this.field_212338_z) {
            StringReader ☃xxx = new StringReader(☃.substring(0, Math.min(☃.length(), this.field_146415_a.func_146198_h())));
            if (☃xxx.canRead() && ☃xxx.peek() == '/') {
               ☃xxx.skip();
               ParseResults<ISuggestionProvider> ☃xxxx = ☃xx.parse(☃xxx, this.field_146297_k.field_71439_g.field_71174_a.func_195513_b());
               this.field_195137_v = ☃xx.getCompletionSuggestions(☃xxxx);
               this.field_195137_v.thenRun(() -> {
                  if (this.field_195137_v.isDone()) {
                     this.func_195133_i();
                  }
               });
            }
         }
      } else {
         int ☃ = func_208603_a(☃);
         Collection<String> ☃x = this.field_146297_k.field_71439_g.field_71174_a.func_195513_b().func_197011_j();
         this.field_195137_v = ISuggestionProvider.func_197005_b(☃x, new SuggestionsBuilder(☃, ☃));
      }
   }

   private void func_195133_i() {
      if (((Suggestions)this.field_195137_v.join()).isEmpty()
         && !this.field_195135_u.getExceptions().isEmpty()
         && this.field_146415_a.func_146198_h() == this.field_146415_a.func_146179_b().length()) {
         int ☃ = 0;

         for(Entry<CommandNode<ISuggestionProvider>, CommandSyntaxException> ☃x : this.field_195135_u.getExceptions().entrySet()) {
            CommandSyntaxException ☃xx = (CommandSyntaxException)☃x.getValue();
            if (☃xx.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
               ++☃;
            } else {
               this.field_195136_f.add(☃xx.getMessage());
            }
         }

         if (☃ > 0) {
            this.field_195136_f.add(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create().getMessage());
         }
      }

      this.field_195138_g = 0;
      this.field_195140_h = this.field_146294_l;
      if (this.field_195136_f.isEmpty()) {
         this.func_195132_a(TextFormatting.GRAY);
      }

      this.field_195139_w = null;
      if (this.field_211139_z && this.field_146297_k.field_71474_y.field_198018_T) {
         this.func_195131_X_();
      }
   }

   private String func_195130_a(String var1, int var2) {
      return this.field_195135_u != null ? func_212336_a(this.field_195135_u, ☃, ☃) : ☃;
   }

   public static String func_212336_a(ParseResults<ISuggestionProvider> var0, String var1, int var2) {
      TextFormatting[] ☃ = new TextFormatting[]{
         TextFormatting.AQUA, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.LIGHT_PURPLE, TextFormatting.GOLD
      };
      String ☃x = TextFormatting.GRAY.toString();
      StringBuilder ☃xx = new StringBuilder(☃x);
      int ☃xxx = 0;
      int ☃xxxx = -1;
      CommandContextBuilder<ISuggestionProvider> ☃xxxxx = ☃.getContext().getLastChild();

      for(ParsedArgument<ISuggestionProvider, ?> ☃xxxxxx : ☃xxxxx.getArguments().values()) {
         if (++☃xxxx >= ☃.length) {
            ☃xxxx = 0;
         }

         int ☃xxxxxxx = Math.max(☃xxxxxx.getRange().getStart() - ☃, 0);
         if (☃xxxxxxx >= ☃.length()) {
            break;
         }

         int ☃xxxxxxx = Math.min(☃xxxxxx.getRange().getEnd() - ☃, ☃.length());
         if (☃xxxxxxx > 0) {
            ☃xx.append(☃, ☃xxx, ☃xxxxxxx);
            ☃xx.append(☃[☃xxxx]);
            ☃xx.append(☃, ☃xxxxxxx, ☃xxxxxxx);
            ☃xx.append(☃x);
            ☃xxx = ☃xxxxxxx;
         }
      }

      if (☃.getReader().canRead()) {
         int ☃xxxxxx = Math.max(☃.getReader().getCursor() - ☃, 0);
         if (☃xxxxxx < ☃.length()) {
            int ☃xxxxxxx = Math.min(☃xxxxxx + ☃.getReader().getRemainingLength(), ☃.length());
            ☃xx.append(☃, ☃xxx, ☃xxxxxx);
            ☃xx.append(TextFormatting.RED);
            ☃xx.append(☃, ☃xxxxxx, ☃xxxxxxx);
            ☃xxx = ☃xxxxxxx;
         }
      }

      ☃xx.append(☃, ☃xxx, ☃.length());
      return ☃xx.toString();
   }

   @Override
   public boolean mouseScrolled(double var1) {
      if (☃ > 1.0) {
         ☃ = 1.0;
      }

      if (☃ < -1.0) {
         ☃ = -1.0;
      }

      if (this.field_195139_w != null && this.field_195139_w.func_198498_a(☃)) {
         return true;
      } else {
         if (!func_146272_n()) {
            ☃ *= 7.0;
         }

         this.field_146297_k.field_71456_v.func_146158_b().func_194813_a(☃);
         return true;
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (this.field_195139_w != null && this.field_195139_w.func_198499_a((int)☃, (int)☃, ☃)) {
         return true;
      } else {
         if (☃ == 0) {
            ITextComponent ☃ = this.field_146297_k.field_71456_v.func_146158_b().func_194817_a(☃, ☃);
            if (☃ != null && this.func_175276_a(☃)) {
               return true;
            }
         }

         return this.field_146415_a.mouseClicked(☃, ☃, ☃) ? true : super.mouseClicked(☃, ☃, ☃);
      }
   }

   @Override
   protected void func_175274_a(String var1, boolean var2) {
      if (☃) {
         this.field_146415_a.func_146180_a(☃);
      } else {
         this.field_146415_a.func_146191_b(☃);
      }
   }

   public void func_146402_a(int var1) {
      int ☃ = this.field_146416_h + ☃;
      int ☃x = this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().size();
      ☃ = MathHelper.func_76125_a(☃, 0, ☃x);
      if (☃ != this.field_146416_h) {
         if (☃ == ☃x) {
            this.field_146416_h = ☃x;
            this.field_146415_a.func_146180_a(this.field_146410_g);
         } else {
            if (this.field_146416_h == ☃x) {
               this.field_146410_g = this.field_146415_a.func_146179_b();
            }

            this.field_146415_a.func_146180_a((String)this.field_146297_k.field_71456_v.func_146158_b().func_146238_c().get(☃));
            this.field_195139_w = null;
            this.field_146416_h = ☃;
            this.field_211139_z = false;
         }
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      func_73734_a(2, this.field_146295_m - 14, this.field_146294_l - 2, this.field_146295_m - 2, Integer.MIN_VALUE);
      this.field_146415_a.func_195608_a(☃, ☃, ☃);
      if (this.field_195139_w != null) {
         this.field_195139_w.func_198500_a(☃, ☃);
      } else {
         int ☃ = 0;

         for(String ☃x : this.field_195136_f) {
            func_73734_a(
               this.field_195138_g - 1,
               this.field_146295_m - 14 - 13 - 12 * ☃,
               this.field_195138_g + this.field_195140_h + 1,
               this.field_146295_m - 2 - 13 - 12 * ☃,
               -16777216
            );
            this.field_146289_q.func_175063_a(☃x, (float)this.field_195138_g, (float)(this.field_146295_m - 14 - 13 + 2 - 12 * ☃), -1);
            ++☃;
         }
      }

      ITextComponent ☃ = this.field_146297_k.field_71456_v.func_146158_b().func_194817_a((double)☃, (double)☃);
      if (☃ != null && ☃.func_150256_b().func_150210_i() != null) {
         this.func_175272_a(☃, ☃, ☃);
      }

      super.func_73863_a(☃, ☃, ☃);
   }

   @Override
   public boolean func_73868_f() {
      return false;
   }

   private void func_195132_a(TextFormatting var1) {
      CommandContextBuilder<ISuggestionProvider> ☃ = this.field_195135_u.getContext();
      CommandContextBuilder<ISuggestionProvider> ☃x = ☃.getLastChild();
      if (!☃x.getNodes().isEmpty()) {
         CommandNode<ISuggestionProvider> ☃xx;
         int ☃xxx;
         if (this.field_195135_u.getReader().canRead()) {
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
            this.field_195136_f.addAll(☃xxx);
            this.field_195138_g = MathHelper.func_76125_a(
               this.field_146415_a.func_195611_j(☃xxx) + this.field_146289_q.func_78256_a(" "), 0, this.field_146294_l - ☃xxxx
            );
            this.field_195140_h = ☃xxxx;
         }
      }
   }

   @Nullable
   private static String func_208602_b(String var0, String var1) {
      return ☃.startsWith(☃) ? ☃.substring(☃.length()) : null;
   }

   private void func_208604_b(String var1) {
      this.field_146415_a.func_146180_a(☃);
   }

   class SuggestionsList {
      private final Rectangle2d field_198505_b;
      private final Suggestions field_198506_c;
      private final String field_212466_d;
      private int field_198507_d;
      private int field_198508_e;
      private Vec2f field_198509_f = Vec2f.field_189974_a;
      private boolean field_199880_h;

      private SuggestionsList(int var2, int var3, int var4, Suggestions var5) {
         this.field_198505_b = new Rectangle2d(☃ - 1, ☃ - 3 - Math.min(☃.getList().size(), 10) * 12, ☃ + 1, Math.min(☃.getList().size(), 10) * 12);
         this.field_198506_c = ☃;
         this.field_212466_d = GuiChat.this.field_146415_a.func_146179_b();
         this.func_199675_a(0);
      }

      public void func_198500_a(int var1, int var2) {
         int ☃ = Math.min(this.field_198506_c.getList().size(), 10);
         int ☃x = -5592406;
         boolean ☃xx = this.field_198507_d > 0;
         boolean ☃xxx = this.field_198506_c.getList().size() > this.field_198507_d + ☃;
         boolean ☃xxxx = ☃xx || ☃xxx;
         boolean ☃xxxxx = this.field_198509_f.field_189982_i != (float)☃ || this.field_198509_f.field_189983_j != (float)☃;
         if (☃xxxxx) {
            this.field_198509_f = new Vec2f((float)☃, (float)☃);
         }

         if (☃xxxx) {
            Gui.func_73734_a(
               this.field_198505_b.func_199318_a(),
               this.field_198505_b.func_199319_b() - 1,
               this.field_198505_b.func_199318_a() + this.field_198505_b.func_199316_c(),
               this.field_198505_b.func_199319_b(),
               -805306368
            );
            Gui.func_73734_a(
               this.field_198505_b.func_199318_a(),
               this.field_198505_b.func_199319_b() + this.field_198505_b.func_199317_d(),
               this.field_198505_b.func_199318_a() + this.field_198505_b.func_199316_c(),
               this.field_198505_b.func_199319_b() + this.field_198505_b.func_199317_d() + 1,
               -805306368
            );
            if (☃xx) {
               for(int ☃ = 0; ☃ < this.field_198505_b.func_199316_c(); ++☃) {
                  if (☃ % 2 == 0) {
                     Gui.func_73734_a(
                        this.field_198505_b.func_199318_a() + ☃,
                        this.field_198505_b.func_199319_b() - 1,
                        this.field_198505_b.func_199318_a() + ☃ + 1,
                        this.field_198505_b.func_199319_b(),
                        -1
                     );
                  }
               }
            }

            if (☃xxx) {
               for(int ☃ = 0; ☃ < this.field_198505_b.func_199316_c(); ++☃) {
                  if (☃ % 2 == 0) {
                     Gui.func_73734_a(
                        this.field_198505_b.func_199318_a() + ☃,
                        this.field_198505_b.func_199319_b() + this.field_198505_b.func_199317_d(),
                        this.field_198505_b.func_199318_a() + ☃ + 1,
                        this.field_198505_b.func_199319_b() + this.field_198505_b.func_199317_d() + 1,
                        -1
                     );
                  }
               }
            }
         }

         boolean ☃ = false;

         for(int ☃x = 0; ☃x < ☃; ++☃x) {
            Suggestion ☃xx = (Suggestion)this.field_198506_c.getList().get(☃x + this.field_198507_d);
            Gui.func_73734_a(
               this.field_198505_b.func_199318_a(),
               this.field_198505_b.func_199319_b() + 12 * ☃x,
               this.field_198505_b.func_199318_a() + this.field_198505_b.func_199316_c(),
               this.field_198505_b.func_199319_b() + 12 * ☃x + 12,
               -805306368
            );
            if (☃ > this.field_198505_b.func_199318_a()
               && ☃ < this.field_198505_b.func_199318_a() + this.field_198505_b.func_199316_c()
               && ☃ > this.field_198505_b.func_199319_b() + 12 * ☃x
               && ☃ < this.field_198505_b.func_199319_b() + 12 * ☃x + 12) {
               if (☃xxxxx) {
                  this.func_199675_a(☃x + this.field_198507_d);
               }

               ☃ = true;
            }

            GuiChat.this.field_146289_q
               .func_175063_a(
                  ☃xx.getText(),
                  (float)(this.field_198505_b.func_199318_a() + 1),
                  (float)(this.field_198505_b.func_199319_b() + 2 + 12 * ☃x),
                  ☃x + this.field_198507_d == this.field_198508_e ? -256 : -5592406
               );
         }

         if (☃) {
            Message ☃x = ((Suggestion)this.field_198506_c.getList().get(this.field_198508_e)).getTooltip();
            if (☃x != null) {
               GuiChat.this.func_146279_a(TextComponentUtils.func_202465_a(☃x).func_150254_d(), ☃, ☃);
            }
         }
      }

      public boolean func_198499_a(int var1, int var2, int var3) {
         if (!this.field_198505_b.func_199315_b(☃, ☃)) {
            return false;
         } else {
            int ☃ = (☃ - this.field_198505_b.func_199319_b()) / 12 + this.field_198507_d;
            if (☃ >= 0 && ☃ < this.field_198506_c.getList().size()) {
               this.func_199675_a(☃);
               this.func_198501_a();
            }

            return true;
         }
      }

      public boolean func_198498_a(double var1) {
         int ☃ = (int)(
            GuiChat.this.field_146297_k.field_71417_B.func_198024_e()
               * (double)GuiChat.this.field_146297_k.field_195558_d.func_198107_o()
               / (double)GuiChat.this.field_146297_k.field_195558_d.func_198105_m()
         );
         int ☃x = (int)(
            GuiChat.this.field_146297_k.field_71417_B.func_198026_f()
               * (double)GuiChat.this.field_146297_k.field_195558_d.func_198087_p()
               / (double)GuiChat.this.field_146297_k.field_195558_d.func_198083_n()
         );
         if (this.field_198505_b.func_199315_b(☃, ☃x)) {
            this.field_198507_d = MathHelper.func_76125_a((int)((double)this.field_198507_d - ☃), 0, Math.max(this.field_198506_c.getList().size() - 10, 0));
            return true;
         } else {
            return false;
         }
      }

      public boolean func_198503_b(int var1, int var2, int var3) {
         if (☃ == 265) {
            this.func_199879_a(-1);
            this.field_199880_h = false;
            return true;
         } else if (☃ == 264) {
            this.func_199879_a(1);
            this.field_199880_h = false;
            return true;
         } else if (☃ == 258) {
            if (this.field_199880_h) {
               this.func_199879_a(GuiScreen.func_146272_n() ? -1 : 1);
            }

            this.func_198501_a();
            return true;
         } else if (☃ == 256) {
            this.func_198502_b();
            return true;
         } else {
            return false;
         }
      }

      public void func_199879_a(int var1) {
         this.func_199675_a(this.field_198508_e + ☃);
         int ☃ = this.field_198507_d;
         int ☃x = this.field_198507_d + 10 - 1;
         if (this.field_198508_e < ☃) {
            this.field_198507_d = MathHelper.func_76125_a(this.field_198508_e, 0, Math.max(this.field_198506_c.getList().size() - 10, 0));
         } else if (this.field_198508_e > ☃x) {
            this.field_198507_d = MathHelper.func_76125_a(this.field_198508_e + 1 - 10, 0, Math.max(this.field_198506_c.getList().size() - 10, 0));
         }
      }

      public void func_199675_a(int var1) {
         this.field_198508_e = ☃;
         if (this.field_198508_e < 0) {
            this.field_198508_e += this.field_198506_c.getList().size();
         }

         if (this.field_198508_e >= this.field_198506_c.getList().size()) {
            this.field_198508_e -= this.field_198506_c.getList().size();
         }

         Suggestion ☃ = (Suggestion)this.field_198506_c.getList().get(this.field_198508_e);
         GuiChat.this.field_146415_a.func_195612_c(GuiChat.func_208602_b(GuiChat.this.field_146415_a.func_146179_b(), ☃.apply(this.field_212466_d)));
      }

      public void func_198501_a() {
         Suggestion ☃ = (Suggestion)this.field_198506_c.getList().get(this.field_198508_e);
         GuiChat.this.field_212338_z = true;
         GuiChat.this.func_208604_b(☃.apply(this.field_212466_d));
         int ☃x = ☃.getRange().getStart() + ☃.getText().length();
         GuiChat.this.field_146415_a.func_212422_f(☃x);
         GuiChat.this.field_146415_a.func_146199_i(☃x);
         this.func_199675_a(this.field_198508_e);
         GuiChat.this.field_212338_z = false;
         this.field_199880_h = true;
      }

      public void func_198502_b() {
         GuiChat.this.field_195139_w = null;
      }
   }
}
