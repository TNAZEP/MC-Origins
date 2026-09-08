package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.CPacketEditBook;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GuiScreenBook extends GuiScreen {
   private static final Logger field_146473_a = LogManager.getLogger();
   private static final ResourceLocation field_146466_f = new ResourceLocation("textures/gui/book.png");
   private final EntityPlayer field_146468_g;
   private final ItemStack field_146474_h;
   private final boolean field_146475_i;
   private boolean field_146481_r;
   private boolean field_146480_s;
   private int field_146479_t;
   private final int field_146478_u = 192;
   private final int field_146477_v = 192;
   private int field_146476_w = 1;
   private int field_146484_x;
   private NBTTagList field_146483_y;
   private String field_146482_z = "";
   private List<ITextComponent> field_175386_A;
   private int field_175387_B = -1;
   private GuiScreenBook.NextPageButton field_146470_A;
   private GuiScreenBook.NextPageButton field_146471_B;
   private GuiButton field_146472_C;
   private GuiButton field_146465_D;
   private GuiButton field_146467_E;
   private GuiButton field_146469_F;
   private final EnumHand field_212343_J;

   public GuiScreenBook(EntityPlayer var1, ItemStack var2, boolean var3, EnumHand var4) {
      this.field_146468_g = ☃;
      this.field_146474_h = ☃;
      this.field_146475_i = ☃;
      this.field_212343_J = ☃;
      if (☃.func_77942_o()) {
         NBTTagCompound ☃ = ☃.func_77978_p();
         this.field_146483_y = ☃.func_150295_c("pages", 8).func_74737_b();
         this.field_146476_w = this.field_146483_y.size();
         if (this.field_146476_w < 1) {
            this.field_146483_y.add((INBTBase)(new NBTTagString("")));
            this.field_146476_w = 1;
         }
      }

      if (this.field_146483_y == null && ☃) {
         this.field_146483_y = new NBTTagList();
         this.field_146483_y.add((INBTBase)(new NBTTagString("")));
         this.field_146476_w = 1;
      }
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      ++this.field_146479_t;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      if (this.field_146475_i) {
         this.field_146465_D = this.func_189646_b(new GuiButton(3, this.field_146294_l / 2 - 100, 196, 98, 20, I18n.func_135052_a("book.signButton")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiScreenBook.this.field_146480_s = true;
               GuiScreenBook.this.func_146464_h();
            }
         });
         this.field_146472_C = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 + 2, 196, 98, 20, I18n.func_135052_a("gui.done")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiScreenBook.this.field_146297_k.func_147108_a(null);
               GuiScreenBook.this.func_146462_a(false);
            }
         });
         this.field_146467_E = this.func_189646_b(new GuiButton(5, this.field_146294_l / 2 - 100, 196, 98, 20, I18n.func_135052_a("book.finalizeButton")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               if (GuiScreenBook.this.field_146480_s) {
                  GuiScreenBook.this.func_146462_a(true);
                  GuiScreenBook.this.field_146297_k.func_147108_a(null);
               }
            }
         });
         this.field_146469_F = this.func_189646_b(new GuiButton(4, this.field_146294_l / 2 + 2, 196, 98, 20, I18n.func_135052_a("gui.cancel")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               if (GuiScreenBook.this.field_146480_s) {
                  GuiScreenBook.this.field_146480_s = false;
               }

               GuiScreenBook.this.func_146464_h();
            }
         });
      } else {
         this.field_146472_C = this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, 196, 200, 20, I18n.func_135052_a("gui.done")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiScreenBook.this.field_146297_k.func_147108_a(null);
               GuiScreenBook.this.func_146462_a(false);
            }
         });
      }

      int ☃ = (this.field_146294_l - 192) / 2;
      int ☃x = 2;
      this.field_146470_A = this.func_189646_b(new GuiScreenBook.NextPageButton(1, ☃ + 120, 156, true) {
         @Override
         public void func_194829_a(double var1, double var3) {
            if (GuiScreenBook.this.field_146484_x < GuiScreenBook.this.field_146476_w - 1) {
               GuiScreenBook.this.field_146484_x++;
            } else if (GuiScreenBook.this.field_146475_i) {
               GuiScreenBook.this.func_146461_i();
               if (GuiScreenBook.this.field_146484_x < GuiScreenBook.this.field_146476_w - 1) {
                  GuiScreenBook.this.field_146484_x++;
               }
            }

            GuiScreenBook.this.func_146464_h();
         }
      });
      this.field_146471_B = this.func_189646_b(new GuiScreenBook.NextPageButton(2, ☃ + 38, 156, false) {
         @Override
         public void func_194829_a(double var1, double var3) {
            if (GuiScreenBook.this.field_146484_x > 0) {
               GuiScreenBook.this.field_146484_x--;
            }

            GuiScreenBook.this.func_146464_h();
         }
      });
      this.func_146464_h();
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
   }

   private void func_146464_h() {
      this.field_146470_A.field_146125_m = !this.field_146480_s && (this.field_146484_x < this.field_146476_w - 1 || this.field_146475_i);
      this.field_146471_B.field_146125_m = !this.field_146480_s && this.field_146484_x > 0;
      this.field_146472_C.field_146125_m = !this.field_146475_i || !this.field_146480_s;
      if (this.field_146475_i) {
         this.field_146465_D.field_146125_m = !this.field_146480_s;
         this.field_146469_F.field_146125_m = this.field_146480_s;
         this.field_146467_E.field_146125_m = this.field_146480_s;
         this.field_146467_E.field_146124_l = !this.field_146482_z.trim().isEmpty();
      }
   }

   private void func_146462_a(boolean var1) {
      if (this.field_146475_i && this.field_146481_r) {
         if (this.field_146483_y != null) {
            while(this.field_146483_y.size() > 1) {
               String ☃ = this.field_146483_y.func_150307_f(this.field_146483_y.size() - 1);
               if (!☃.isEmpty()) {
                  break;
               }

               this.field_146483_y.remove(this.field_146483_y.size() - 1);
            }

            this.field_146474_h.func_77983_a("pages", this.field_146483_y);
            if (☃) {
               this.field_146474_h.func_77983_a("author", new NBTTagString(this.field_146468_g.func_146103_bH().getName()));
               this.field_146474_h.func_77983_a("title", new NBTTagString(this.field_146482_z.trim()));
            }

            this.field_146297_k.func_147114_u().func_147297_a(new CPacketEditBook(this.field_146474_h, ☃, this.field_212343_J));
         }
      }
   }

   private void func_146461_i() {
      if (this.field_146483_y != null && this.field_146483_y.size() < 50) {
         this.field_146483_y.add((INBTBase)(new NBTTagString("")));
         ++this.field_146476_w;
         this.field_146481_r = true;
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      if (super.keyPressed(☃, ☃, ☃)) {
         return true;
      } else if (this.field_146475_i) {
         return this.field_146480_s ? this.func_195267_b(☃, ☃, ☃) : this.func_195259_a(☃, ☃, ☃);
      } else {
         return false;
      }
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (super.charTyped(☃, ☃)) {
         return true;
      } else if (this.field_146475_i) {
         if (this.field_146480_s) {
            if (this.field_146482_z.length() < 16 && SharedConstants.func_71566_a(☃)) {
               this.field_146482_z = this.field_146482_z + Character.toString(☃);
               this.func_146464_h();
               this.field_146481_r = true;
               return true;
            } else {
               return false;
            }
         } else if (SharedConstants.func_71566_a(☃)) {
            this.func_146459_b(Character.toString(☃));
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean func_195259_a(int var1, int var2, int var3) {
      if (GuiScreen.func_175279_e(☃)) {
         this.func_146459_b(this.field_146297_k.field_195559_v.func_197965_a());
         return true;
      } else {
         switch(☃) {
            case 257:
            case 335:
               this.func_146459_b("\n");
               return true;
            case 259:
               String ☃ = this.func_146456_p();
               if (!☃.isEmpty()) {
                  this.func_146457_a(☃.substring(0, ☃.length() - 1));
               }

               return true;
            default:
               return false;
         }
      }
   }

   private boolean func_195267_b(int var1, int var2, int var3) {
      switch(☃) {
         case 257:
         case 335:
            if (!this.field_146482_z.isEmpty()) {
               this.func_146462_a(true);
               this.field_146297_k.func_147108_a(null);
            }

            return true;
         case 259:
            if (!this.field_146482_z.isEmpty()) {
               this.field_146482_z = this.field_146482_z.substring(0, this.field_146482_z.length() - 1);
               this.func_146464_h();
            }

            return true;
         default:
            return false;
      }
   }

   private String func_146456_p() {
      return this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.size()
         ? this.field_146483_y.func_150307_f(this.field_146484_x)
         : "";
   }

   private void func_146457_a(String var1) {
      if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.size()) {
         this.field_146483_y.set(this.field_146484_x, (INBTBase)(new NBTTagString(☃)));
         this.field_146481_r = true;
      }
   }

   private void func_146459_b(String var1) {
      String ☃ = this.func_146456_p();
      String ☃x = ☃ + ☃;
      int ☃xx = this.field_146289_q.func_78267_b(☃x + "" + TextFormatting.BLACK + "_", 118);
      if (☃xx <= 128 && ☃x.length() < 256) {
         this.func_146457_a(☃x);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_146466_f);
      int ☃ = (this.field_146294_l - 192) / 2;
      int ☃x = 2;
      this.func_73729_b(☃, 2, 0, 0, 192, 192);
      if (this.field_146480_s) {
         String ☃xx = this.field_146482_z;
         if (this.field_146475_i) {
            if (this.field_146479_t / 6 % 2 == 0) {
               ☃xx = ☃xx + "" + TextFormatting.BLACK + "_";
            } else {
               ☃xx = ☃xx + "" + TextFormatting.GRAY + "_";
            }
         }

         String ☃xx = I18n.func_135052_a("book.editTitle");
         int ☃xxx = this.field_146289_q.func_78256_a(☃xx);
         this.field_146289_q.func_211126_b(☃xx, (float)(☃ + 36 + (116 - ☃xxx) / 2), 34.0F, 0);
         int ☃xxxx = this.field_146289_q.func_78256_a(☃xx);
         this.field_146289_q.func_211126_b(☃xx, (float)(☃ + 36 + (116 - ☃xxxx) / 2), 50.0F, 0);
         String ☃xxxxx = I18n.func_135052_a("book.byAuthor", this.field_146468_g.func_200200_C_().getString());
         int ☃xxxxxx = this.field_146289_q.func_78256_a(☃xxxxx);
         this.field_146289_q.func_211126_b(TextFormatting.DARK_GRAY + ☃xxxxx, (float)(☃ + 36 + (116 - ☃xxxxxx) / 2), 60.0F, 0);
         String ☃xxxxxxx = I18n.func_135052_a("book.finalizeWarning");
         this.field_146289_q.func_78279_b(☃xxxxxxx, ☃ + 36, 82, 116, 0);
      } else {
         String ☃ = I18n.func_135052_a("book.pageIndicator", this.field_146484_x + 1, this.field_146476_w);
         String ☃x = "";
         if (this.field_146483_y != null && this.field_146484_x >= 0 && this.field_146484_x < this.field_146483_y.size()) {
            ☃x = this.field_146483_y.func_150307_f(this.field_146484_x);
         }

         if (this.field_146475_i) {
            if (this.field_146289_q.func_78260_a()) {
               ☃x = ☃x + "_";
            } else if (this.field_146479_t / 6 % 2 == 0) {
               ☃x = ☃x + "" + TextFormatting.BLACK + "_";
            } else {
               ☃x = ☃x + "" + TextFormatting.GRAY + "_";
            }
         } else if (this.field_175387_B != this.field_146484_x) {
            if (ItemWrittenBook.func_77828_a(this.field_146474_h.func_77978_p())) {
               try {
                  ITextComponent ☃ = ITextComponent.Serializer.func_150699_a(☃x);
                  this.field_175386_A = ☃ != null ? GuiUtilRenderComponents.func_178908_a(☃, 116, this.field_146289_q, true, true) : null;
               } catch (JsonParseException var13) {
                  this.field_175386_A = null;
               }
            } else {
               this.field_175386_A = Lists.<ITextComponent>newArrayList(new TextComponentTranslation("book.invalid.tag").func_211708_a(TextFormatting.DARK_RED));
            }

            this.field_175387_B = this.field_146484_x;
         }

         int ☃ = this.field_146289_q.func_78256_a(☃);
         this.field_146289_q.func_211126_b(☃, (float)(☃ - ☃ + 192 - 44), 18.0F, 0);
         if (this.field_175386_A == null) {
            this.field_146289_q.func_78279_b(☃x, ☃ + 36, 34, 116, 0);
         } else {
            int ☃ = Math.min(128 / this.field_146289_q.field_78288_b, this.field_175386_A.size());

            for(int ☃x = 0; ☃x < ☃; ++☃x) {
               ITextComponent ☃xx = (ITextComponent)this.field_175386_A.get(☃x);
               this.field_146289_q.func_211126_b(☃xx.func_150254_d(), (float)(☃ + 36), (float)(34 + ☃x * this.field_146289_q.field_78288_b), 0);
            }

            ITextComponent ☃x = this.func_195260_a((double)☃, (double)☃);
            if (☃x != null) {
               this.func_175272_a(☃x, ☃, ☃);
            }
         }
      }

      super.func_73863_a(☃, ☃, ☃);
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (☃ == 0) {
         ITextComponent ☃ = this.func_195260_a(☃, ☃);
         if (☃ != null && this.func_175276_a(☃)) {
            return true;
         }
      }

      return super.mouseClicked(☃, ☃, ☃);
   }

   @Override
   public boolean func_175276_a(ITextComponent var1) {
      ClickEvent ☃ = ☃.func_150256_b().func_150235_h();
      if (☃ == null) {
         return false;
      } else if (☃.func_150669_a() == ClickEvent.Action.CHANGE_PAGE) {
         String ☃ = ☃.func_150668_b();

         try {
            int ☃x = Integer.parseInt(☃) - 1;
            if (☃x >= 0 && ☃x < this.field_146476_w && ☃x != this.field_146484_x) {
               this.field_146484_x = ☃x;
               this.func_146464_h();
               return true;
            }
         } catch (Throwable var5) {
         }

         return false;
      } else {
         boolean ☃ = super.func_175276_a(☃);
         if (☃ && ☃.func_150669_a() == ClickEvent.Action.RUN_COMMAND) {
            this.field_146297_k.func_147108_a(null);
         }

         return ☃;
      }
   }

   @Nullable
   public ITextComponent func_195260_a(double var1, double var3) {
      if (this.field_175386_A == null) {
         return null;
      } else {
         int ☃ = MathHelper.func_76128_c(☃ - (double)((this.field_146294_l - 192) / 2) - 36.0);
         int ☃x = MathHelper.func_76128_c(☃ - 2.0 - 16.0 - 16.0);
         if (☃ >= 0 && ☃x >= 0) {
            int ☃xx = Math.min(128 / this.field_146289_q.field_78288_b, this.field_175386_A.size());
            if (☃ <= 116 && ☃x < this.field_146297_k.field_71466_p.field_78288_b * ☃xx + ☃xx) {
               int ☃xxx = ☃x / this.field_146297_k.field_71466_p.field_78288_b;
               if (☃xxx >= 0 && ☃xxx < this.field_175386_A.size()) {
                  ITextComponent ☃xxxx = (ITextComponent)this.field_175386_A.get(☃xxx);
                  int ☃xxxxx = 0;

                  for(ITextComponent ☃xxxxxx : ☃xxxx) {
                     if (☃xxxxxx instanceof TextComponentString) {
                        ☃xxxxx += this.field_146297_k.field_71466_p.func_78256_a(☃xxxxxx.func_150254_d());
                        if (☃xxxxx > ☃) {
                           return ☃xxxxxx;
                        }
                     }
                  }
               }

               return null;
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   abstract static class NextPageButton extends GuiButton {
      private final boolean field_146151_o;

      public NextPageButton(int var1, int var2, int var3, boolean var4) {
         super(☃, ☃, ☃, 23, 13, "");
         this.field_146151_o = ☃;
      }

      @Override
      public void func_194828_a(int var1, int var2, float var3) {
         if (this.field_146125_m) {
            boolean ☃ = ☃ >= this.field_146128_h
               && ☃ >= this.field_146129_i
               && ☃ < this.field_146128_h + this.field_146120_f
               && ☃ < this.field_146129_i + this.field_146121_g;
            GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(GuiScreenBook.field_146466_f);
            int ☃x = 0;
            int ☃xx = 192;
            if (☃) {
               ☃x += 23;
            }

            if (!this.field_146151_o) {
               ☃xx += 13;
            }

            this.func_73729_b(this.field_146128_h, this.field_146129_i, ☃x, ☃xx, 23, 13);
         }
      }
   }
}
