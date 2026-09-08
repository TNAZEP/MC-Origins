package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GuiScreen extends GuiEventHandler implements GuiYesNoCallback {
   private static final Logger field_175287_a = LogManager.getLogger();
   private static final Set<String> field_175284_f = Sets.newHashSet("http", "https");
   protected final List<IGuiEventListener> field_195124_j = Lists.<IGuiEventListener>newArrayList();
   protected Minecraft field_146297_k;
   protected ItemRenderer field_146296_j;
   public int field_146294_l;
   public int field_146295_m;
   protected final List<GuiButton> field_146292_n = Lists.<GuiButton>newArrayList();
   protected final List<GuiLabel> field_146293_o = Lists.<GuiLabel>newArrayList();
   public boolean field_146291_p;
   protected FontRenderer field_146289_q;
   private URI field_175286_t;

   public void func_73863_a(int var1, int var2, float var3) {
      for(int ☃ = 0; ☃ < this.field_146292_n.size(); ++☃) {
         ((GuiButton)this.field_146292_n.get(☃)).func_194828_a(☃, ☃, ☃);
      }

      for(int ☃ = 0; ☃ < this.field_146293_o.size(); ++☃) {
         ((GuiLabel)this.field_146293_o.get(☃)).func_194997_a(☃, ☃, ☃);
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (☃ == 256 && this.func_195120_Y_()) {
         this.func_195122_V_();
         return true;
      } else {
         return super.keyPressed(☃, ☃, ☃);
      }
   }

   public boolean func_195120_Y_() {
      return true;
   }

   public void func_195122_V_() {
      this.field_146297_k.func_147108_a(null);
   }

   protected <T extends GuiButton> T func_189646_b(T var1) {
      this.field_146292_n.add(☃);
      this.field_195124_j.add(☃);
      return ☃;
   }

   protected void func_146285_a(ItemStack var1, int var2, int var3) {
      this.func_146283_a(this.func_191927_a(☃), ☃, ☃);
   }

   public List<String> func_191927_a(ItemStack var1) {
      List<ITextComponent> ☃ = ☃.func_82840_a(
         this.field_146297_k.field_71439_g,
         this.field_146297_k.field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL
      );
      List<String> ☃x = Lists.newArrayList();

      for(ITextComponent ☃xx : ☃) {
         ☃x.add(☃xx.func_150254_d());
      }

      return ☃x;
   }

   public void func_146279_a(String var1, int var2, int var3) {
      this.func_146283_a(Arrays.asList(☃), ☃, ☃);
   }

   public void func_146283_a(List<String> var1, int var2, int var3) {
      if (!☃.isEmpty()) {
         GlStateManager.func_179101_C();
         RenderHelper.func_74518_a();
         GlStateManager.func_179140_f();
         GlStateManager.func_179097_i();
         int ☃ = 0;

         for(String ☃x : ☃) {
            int ☃xx = this.field_146289_q.func_78256_a(☃x);
            if (☃xx > ☃) {
               ☃ = ☃xx;
            }
         }

         int ☃x = ☃ + 12;
         int ☃xx = ☃ - 12;
         int ☃xxx = 8;
         if (☃.size() > 1) {
            ☃xxx += 2 + (☃.size() - 1) * 10;
         }

         if (☃x + ☃ > this.field_146294_l) {
            ☃x -= 28 + ☃;
         }

         if (☃xx + ☃xxx + 6 > this.field_146295_m) {
            ☃xx = this.field_146295_m - ☃xxx - 6;
         }

         this.field_73735_i = 300.0F;
         this.field_146296_j.field_77023_b = 300.0F;
         int ☃x = -267386864;
         this.func_73733_a(☃x - 3, ☃xx - 4, ☃x + ☃ + 3, ☃xx - 3, -267386864, -267386864);
         this.func_73733_a(☃x - 3, ☃xx + ☃xxx + 3, ☃x + ☃ + 3, ☃xx + ☃xxx + 4, -267386864, -267386864);
         this.func_73733_a(☃x - 3, ☃xx - 3, ☃x + ☃ + 3, ☃xx + ☃xxx + 3, -267386864, -267386864);
         this.func_73733_a(☃x - 4, ☃xx - 3, ☃x - 3, ☃xx + ☃xxx + 3, -267386864, -267386864);
         this.func_73733_a(☃x + ☃ + 3, ☃xx - 3, ☃x + ☃ + 4, ☃xx + ☃xxx + 3, -267386864, -267386864);
         int ☃xx = 1347420415;
         int ☃xxx = 1344798847;
         this.func_73733_a(☃x - 3, ☃xx - 3 + 1, ☃x - 3 + 1, ☃xx + ☃xxx + 3 - 1, 1347420415, 1344798847);
         this.func_73733_a(☃x + ☃ + 2, ☃xx - 3 + 1, ☃x + ☃ + 3, ☃xx + ☃xxx + 3 - 1, 1347420415, 1344798847);
         this.func_73733_a(☃x - 3, ☃xx - 3, ☃x + ☃ + 3, ☃xx - 3 + 1, 1347420415, 1347420415);
         this.func_73733_a(☃x - 3, ☃xx + ☃xxx + 2, ☃x + ☃ + 3, ☃xx + ☃xxx + 3, 1344798847, 1344798847);

         for(int ☃xxxx = 0; ☃xxxx < ☃.size(); ++☃xxxx) {
            String ☃xxxxx = (String)☃.get(☃xxxx);
            this.field_146289_q.func_175063_a(☃xxxxx, (float)☃x, (float)☃xx, -1);
            if (☃xxxx == 0) {
               ☃xx += 2;
            }

            ☃xx += 10;
         }

         this.field_73735_i = 0.0F;
         this.field_146296_j.field_77023_b = 0.0F;
         GlStateManager.func_179145_e();
         GlStateManager.func_179126_j();
         RenderHelper.func_74519_b();
         GlStateManager.func_179091_B();
      }
   }

   protected void func_175272_a(ITextComponent var1, int var2, int var3) {
      if (☃ != null && ☃.func_150256_b().func_150210_i() != null) {
         HoverEvent ☃ = ☃.func_150256_b().func_150210_i();
         if (☃.func_150701_a() == HoverEvent.Action.SHOW_ITEM) {
            ItemStack ☃x = ItemStack.field_190927_a;

            try {
               INBTBase ☃xx = JsonToNBT.func_180713_a(☃.func_150702_b().getString());
               if (☃xx instanceof NBTTagCompound) {
                  ☃x = ItemStack.func_199557_a((NBTTagCompound)☃xx);
               }
            } catch (CommandSyntaxException var10) {
            }

            if (☃x.func_190926_b()) {
               this.func_146279_a(TextFormatting.RED + "Invalid Item!", ☃, ☃);
            } else {
               this.func_146285_a(☃x, ☃, ☃);
            }
         } else if (☃.func_150701_a() == HoverEvent.Action.SHOW_ENTITY) {
            if (this.field_146297_k.field_71474_y.field_82882_x) {
               try {
                  NBTTagCompound ☃ = JsonToNBT.func_180713_a(☃.func_150702_b().getString());
                  List<String> ☃x = Lists.newArrayList();
                  ITextComponent ☃xx = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("name"));
                  if (☃xx != null) {
                     ☃x.add(☃xx.func_150254_d());
                  }

                  if (☃.func_150297_b("type", 8)) {
                     String ☃ = ☃.func_74779_i("type");
                     ☃x.add("Type: " + ☃);
                  }

                  ☃x.add(☃.func_74779_i("id"));
                  this.func_146283_a(☃x, ☃, ☃);
               } catch (CommandSyntaxException | JsonSyntaxException var9) {
                  this.func_146279_a(TextFormatting.RED + "Invalid Entity!", ☃, ☃);
               }
            }
         } else if (☃.func_150701_a() == HoverEvent.Action.SHOW_TEXT) {
            this.func_146283_a(this.field_146297_k.field_71466_p.func_78271_c(☃.func_150702_b().func_150254_d(), Math.max(this.field_146294_l / 2, 200)), ☃, ☃);
         }

         GlStateManager.func_179140_f();
      }
   }

   protected void func_175274_a(String var1, boolean var2) {
   }

   public boolean func_175276_a(ITextComponent var1) {
      if (☃ == null) {
         return false;
      } else {
         ClickEvent ☃ = ☃.func_150256_b().func_150235_h();
         if (func_146272_n()) {
            if (☃.func_150256_b().func_179986_j() != null) {
               this.func_175274_a(☃.func_150256_b().func_179986_j(), false);
            }
         } else if (☃ != null) {
            if (☃.func_150669_a() == ClickEvent.Action.OPEN_URL) {
               if (!this.field_146297_k.field_71474_y.field_74359_p) {
                  return false;
               }

               try {
                  URI ☃ = new URI(☃.func_150668_b());
                  String ☃x = ☃.getScheme();
                  if (☃x == null) {
                     throw new URISyntaxException(☃.func_150668_b(), "Missing protocol");
                  }

                  if (!field_175284_f.contains(☃x.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(☃.func_150668_b(), "Unsupported protocol: " + ☃x.toLowerCase(Locale.ROOT));
                  }

                  if (this.field_146297_k.field_71474_y.field_74358_q) {
                     this.field_175286_t = ☃;
                     this.field_146297_k.func_147108_a(new GuiConfirmOpenLink(this, ☃.func_150668_b(), 31102009, false));
                  } else {
                     this.func_175282_a(☃);
                  }
               } catch (URISyntaxException var5) {
                  field_175287_a.error("Can't open url for {}", ☃, var5);
               }
            } else if (☃.func_150669_a() == ClickEvent.Action.OPEN_FILE) {
               URI ☃ = new File(☃.func_150668_b()).toURI();
               this.func_175282_a(☃);
            } else if (☃.func_150669_a() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.func_175274_a(☃.func_150668_b(), true);
            } else if (☃.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
               this.func_175281_b(☃.func_150668_b(), false);
            } else {
               field_175287_a.error("Don't know how to handle {}", ☃);
            }

            return true;
         }

         return false;
      }
   }

   public void func_175275_f(String var1) {
      this.func_175281_b(☃, true);
   }

   public void func_175281_b(String var1, boolean var2) {
      if (☃) {
         this.field_146297_k.field_71456_v.func_146158_b().func_146239_a(☃);
      }

      this.field_146297_k.field_71439_g.func_71165_d(☃);
   }

   public void func_146280_a(Minecraft var1, int var2, int var3) {
      this.field_146297_k = ☃;
      this.field_146296_j = ☃.func_175599_af();
      this.field_146289_q = ☃.field_71466_p;
      this.field_146294_l = ☃;
      this.field_146295_m = ☃;
      this.field_146292_n.clear();
      this.field_195124_j.clear();
      this.func_73866_w_();
   }

   @Override
   public List<? extends IGuiEventListener> func_195074_b() {
      return this.field_195124_j;
   }

   protected void func_73866_w_() {
      this.field_195124_j.addAll(this.field_146293_o);
   }

   public void func_73876_c() {
   }

   public void func_146281_b() {
   }

   public void func_146276_q_() {
      this.func_146270_b(0);
   }

   public void func_146270_b(int var1) {
      if (this.field_146297_k.field_71441_e != null) {
         this.func_73733_a(0, 0, this.field_146294_l, this.field_146295_m, -1072689136, -804253680);
      } else {
         this.func_146278_c(☃);
      }
   }

   public void func_146278_c(int var1) {
      GlStateManager.func_179140_f();
      GlStateManager.func_179106_n();
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      this.field_146297_k.func_110434_K().func_110577_a(field_110325_k);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      float ☃xx = 32.0F;
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181709_i);
      ☃x.func_181662_b(0.0, (double)this.field_146295_m, 0.0)
         .func_187315_a(0.0, (double)((float)this.field_146295_m / 32.0F + (float)☃))
         .func_181669_b(64, 64, 64, 255)
         .func_181675_d();
      ☃x.func_181662_b((double)this.field_146294_l, (double)this.field_146295_m, 0.0)
         .func_187315_a((double)((float)this.field_146294_l / 32.0F), (double)((float)this.field_146295_m / 32.0F + (float)☃))
         .func_181669_b(64, 64, 64, 255)
         .func_181675_d();
      ☃x.func_181662_b((double)this.field_146294_l, 0.0, 0.0)
         .func_187315_a((double)((float)this.field_146294_l / 32.0F), (double)☃)
         .func_181669_b(64, 64, 64, 255)
         .func_181675_d();
      ☃x.func_181662_b(0.0, 0.0, 0.0).func_187315_a(0.0, (double)☃).func_181669_b(64, 64, 64, 255).func_181675_d();
      ☃.func_78381_a();
   }

   public boolean func_73868_f() {
      return true;
   }

   @Override
   public void confirmResult(boolean var1, int var2) {
      if (☃ == 31102009) {
         if (☃) {
            this.func_175282_a(this.field_175286_t);
         }

         this.field_175286_t = null;
         this.field_146297_k.func_147108_a(this);
      }
   }

   private void func_175282_a(URI var1) {
      Util.func_110647_a().func_195642_a(☃);
   }

   public static boolean func_146271_m() {
      if (Minecraft.field_142025_a) {
         return InputMappings.func_197956_a(343) || InputMappings.func_197956_a(347);
      } else {
         return InputMappings.func_197956_a(341) || InputMappings.func_197956_a(345);
      }
   }

   public static boolean func_146272_n() {
      return InputMappings.func_197956_a(340) || InputMappings.func_197956_a(344);
   }

   public static boolean func_175283_s() {
      return InputMappings.func_197956_a(342) || InputMappings.func_197956_a(346);
   }

   public static boolean func_175277_d(int var0) {
      return ☃ == 88 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public static boolean func_175279_e(int var0) {
      return ☃ == 86 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public static boolean func_175280_f(int var0) {
      return ☃ == 67 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public static boolean func_175278_g(int var0) {
      return ☃ == 65 && func_146271_m() && !func_146272_n() && !func_175283_s();
   }

   public void func_175273_b(Minecraft var1, int var2, int var3) {
      this.func_146280_a(☃, ☃, ☃);
   }

   public static void func_195121_a(Runnable var0, String var1, String var2) {
      try {
         ☃.run();
      } catch (Throwable var6) {
         CrashReport ☃ = CrashReport.func_85055_a(var6, ☃);
         CrashReportCategory ☃x = ☃.func_85058_a("Affected screen");
         ☃x.func_189529_a("Screen name", () -> ☃);
         throw new ReportedException(☃);
      }
   }
}
