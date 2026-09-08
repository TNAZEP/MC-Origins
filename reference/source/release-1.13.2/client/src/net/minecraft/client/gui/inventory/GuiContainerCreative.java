package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.settings.HotbarSnapshot;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class GuiContainerCreative extends InventoryEffectRenderer {
   private static final ResourceLocation field_147061_u = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
   private static final InventoryBasic field_195378_x = new InventoryBasic(new TextComponentString("tmp"), 45);
   private static int field_147058_w = ItemGroup.field_78030_b.func_78021_a();
   private float field_147067_x;
   private boolean field_147066_y;
   private GuiTextField field_147062_A;
   private List<Slot> field_147063_B;
   private Slot field_147064_C;
   private CreativeCrafting field_147059_E;
   private boolean field_195377_F;
   private boolean field_199506_G;

   public GuiContainerCreative(EntityPlayer var1) {
      super(new GuiContainerCreative.ContainerCreative(☃));
      ☃.field_71070_bA = this.field_147002_h;
      this.field_146291_p = true;
      this.field_147000_g = 136;
      this.field_146999_f = 195;
   }

   @Override
   public void func_73876_c() {
      if (!this.field_146297_k.field_71442_b.func_78758_h()) {
         this.field_146297_k.func_147108_a(new GuiInventory(this.field_146297_k.field_71439_g));
      }
   }

   @Override
   protected void func_184098_a(@Nullable Slot var1, int var2, int var3, ClickType var4) {
      if (this.func_208018_a(☃)) {
         this.field_147062_A.func_146202_e();
         this.field_147062_A.func_146199_i(0);
      }

      boolean ☃ = ☃ == ClickType.QUICK_MOVE;
      ☃ = ☃ == -999 && ☃ == ClickType.PICKUP ? ClickType.THROW : ☃;
      if (☃ == null && field_147058_w != ItemGroup.field_78036_m.func_78021_a() && ☃ != ClickType.QUICK_CRAFT) {
         InventoryPlayer ☃x = this.field_146297_k.field_71439_g.field_71071_by;
         if (!☃x.func_70445_o().func_190926_b() && this.field_199506_G) {
            if (☃ == 0) {
               this.field_146297_k.field_71439_g.func_71019_a(☃x.func_70445_o(), true);
               this.field_146297_k.field_71442_b.func_78752_a(☃x.func_70445_o());
               ☃x.func_70437_b(ItemStack.field_190927_a);
            }

            if (☃ == 1) {
               ItemStack ☃xx = ☃x.func_70445_o().func_77979_a(1);
               this.field_146297_k.field_71439_g.func_71019_a(☃xx, true);
               this.field_146297_k.field_71442_b.func_78752_a(☃xx);
            }
         }
      } else {
         if (☃ != null && !☃.func_82869_a(this.field_146297_k.field_71439_g)) {
            return;
         }

         if (☃ == this.field_147064_C && ☃) {
            for(int ☃ = 0; ☃ < this.field_146297_k.field_71439_g.field_71069_bz.func_75138_a().size(); ++☃) {
               this.field_146297_k.field_71442_b.func_78761_a(ItemStack.field_190927_a, ☃);
            }
         } else if (field_147058_w == ItemGroup.field_78036_m.func_78021_a()) {
            if (☃ == this.field_147064_C) {
               this.field_146297_k.field_71439_g.field_71071_by.func_70437_b(ItemStack.field_190927_a);
            } else if (☃ == ClickType.THROW && ☃ != null && ☃.func_75216_d()) {
               ItemStack ☃ = ☃.func_75209_a(☃ == 0 ? 1 : ☃.func_75211_c().func_77976_d());
               ItemStack ☃x = ☃.func_75211_c();
               this.field_146297_k.field_71439_g.func_71019_a(☃, true);
               this.field_146297_k.field_71442_b.func_78752_a(☃);
               this.field_146297_k.field_71442_b.func_78761_a(☃x, ((GuiContainerCreative.CreativeSlot)☃).field_148332_b.field_75222_d);
            } else if (☃ == ClickType.THROW && !this.field_146297_k.field_71439_g.field_71071_by.func_70445_o().func_190926_b()) {
               this.field_146297_k.field_71439_g.func_71019_a(this.field_146297_k.field_71439_g.field_71071_by.func_70445_o(), true);
               this.field_146297_k.field_71442_b.func_78752_a(this.field_146297_k.field_71439_g.field_71071_by.func_70445_o());
               this.field_146297_k.field_71439_g.field_71071_by.func_70437_b(ItemStack.field_190927_a);
            } else {
               this.field_146297_k
                  .field_71439_g
                  .field_71069_bz
                  .func_184996_a(☃ == null ? ☃ : ((GuiContainerCreative.CreativeSlot)☃).field_148332_b.field_75222_d, ☃, ☃, this.field_146297_k.field_71439_g);
               this.field_146297_k.field_71439_g.field_71069_bz.func_75142_b();
            }
         } else if (☃ != ClickType.QUICK_CRAFT && ☃.field_75224_c == field_195378_x) {
            InventoryPlayer ☃ = this.field_146297_k.field_71439_g.field_71071_by;
            ItemStack ☃x = ☃.func_70445_o();
            ItemStack ☃xx = ☃.func_75211_c();
            if (☃ == ClickType.SWAP) {
               if (!☃xx.func_190926_b() && ☃ >= 0 && ☃ < 9) {
                  ItemStack ☃xxx = ☃xx.func_77946_l();
                  ☃xxx.func_190920_e(☃xxx.func_77976_d());
                  this.field_146297_k.field_71439_g.field_71071_by.func_70299_a(☃, ☃xxx);
                  this.field_146297_k.field_71439_g.field_71069_bz.func_75142_b();
               }

               return;
            }

            if (☃ == ClickType.CLONE) {
               if (☃.func_70445_o().func_190926_b() && ☃.func_75216_d()) {
                  ItemStack ☃ = ☃.func_75211_c().func_77946_l();
                  ☃.func_190920_e(☃.func_77976_d());
                  ☃.func_70437_b(☃);
               }

               return;
            }

            if (☃ == ClickType.THROW) {
               if (!☃xx.func_190926_b()) {
                  ItemStack ☃ = ☃xx.func_77946_l();
                  ☃.func_190920_e(☃ == 0 ? 1 : ☃.func_77976_d());
                  this.field_146297_k.field_71439_g.func_71019_a(☃, true);
                  this.field_146297_k.field_71442_b.func_78752_a(☃);
               }

               return;
            }

            if (!☃x.func_190926_b() && !☃xx.func_190926_b() && ☃x.func_77969_a(☃xx) && ItemStack.func_77970_a(☃x, ☃xx)) {
               if (☃ == 0) {
                  if (☃) {
                     ☃x.func_190920_e(☃x.func_77976_d());
                  } else if (☃x.func_190916_E() < ☃x.func_77976_d()) {
                     ☃x.func_190917_f(1);
                  }
               } else {
                  ☃x.func_190918_g(1);
               }
            } else if (!☃xx.func_190926_b() && ☃x.func_190926_b()) {
               ☃.func_70437_b(☃xx.func_77946_l());
               ☃x = ☃.func_70445_o();
               if (☃) {
                  ☃x.func_190920_e(☃x.func_77976_d());
               }
            } else if (☃ == 0) {
               ☃.func_70437_b(ItemStack.field_190927_a);
            } else {
               ☃.func_70445_o().func_190918_g(1);
            }
         } else if (this.field_147002_h != null) {
            ItemStack ☃ = ☃ == null ? ItemStack.field_190927_a : this.field_147002_h.func_75139_a(☃.field_75222_d).func_75211_c();
            this.field_147002_h.func_184996_a(☃ == null ? ☃ : ☃.field_75222_d, ☃, ☃, this.field_146297_k.field_71439_g);
            if (Container.func_94532_c(☃) == 2) {
               for(int ☃x = 0; ☃x < 9; ++☃x) {
                  this.field_146297_k.field_71442_b.func_78761_a(this.field_147002_h.func_75139_a(45 + ☃x).func_75211_c(), 36 + ☃x);
               }
            } else if (☃ != null) {
               ItemStack ☃ = this.field_147002_h.func_75139_a(☃.field_75222_d).func_75211_c();
               this.field_146297_k.field_71442_b.func_78761_a(☃, ☃.field_75222_d - this.field_147002_h.field_75151_b.size() + 9 + 36);
               int ☃x = 45 + ☃;
               if (☃ == ClickType.SWAP) {
                  this.field_146297_k.field_71442_b.func_78761_a(☃, ☃x - this.field_147002_h.field_75151_b.size() + 9 + 36);
               } else if (☃ == ClickType.THROW && !☃.func_190926_b()) {
                  ItemStack ☃ = ☃.func_77946_l();
                  ☃.func_190920_e(☃ == 0 ? 1 : ☃.func_77976_d());
                  this.field_146297_k.field_71439_g.func_71019_a(☃, true);
                  this.field_146297_k.field_71442_b.func_78752_a(☃);
               }

               this.field_146297_k.field_71439_g.field_71069_bz.func_75142_b();
            }
         }
      }
   }

   private boolean func_208018_a(@Nullable Slot var1) {
      return ☃ != null && ☃.field_75224_c == field_195378_x;
   }

   @Override
   protected void func_175378_g() {
      int ☃ = this.field_147003_i;
      super.func_175378_g();
      if (this.field_147062_A != null && this.field_147003_i != ☃) {
         this.field_147062_A.field_146209_f = this.field_147003_i + 82;
      }
   }

   @Override
   protected void func_73866_w_() {
      if (this.field_146297_k.field_71442_b.func_78758_h()) {
         super.func_73866_w_();
         this.field_146297_k.field_195559_v.func_197967_a(true);
         this.field_147062_A = new GuiTextField(
            0, this.field_146289_q, this.field_147003_i + 82, this.field_147009_r + 6, 80, this.field_146289_q.field_78288_b
         );
         this.field_147062_A.func_146203_f(50);
         this.field_147062_A.func_146185_a(false);
         this.field_147062_A.func_146189_e(false);
         this.field_147062_A.func_146193_g(16777215);
         this.field_195124_j.add(this.field_147062_A);
         int ☃ = field_147058_w;
         field_147058_w = -1;
         this.func_147050_b(ItemGroup.field_78032_a[☃]);
         this.field_147059_E = new CreativeCrafting(this.field_146297_k);
         this.field_146297_k.field_71439_g.field_71069_bz.func_75132_a(this.field_147059_E);
      } else {
         this.field_146297_k.func_147108_a(new GuiInventory(this.field_146297_k.field_71439_g));
      }
   }

   @Override
   public void func_175273_b(Minecraft var1, int var2, int var3) {
      String ☃ = this.field_147062_A.func_146179_b();
      this.func_146280_a(☃, ☃, ☃);
      this.field_147062_A.func_146180_a(☃);
      if (!this.field_147062_A.func_146179_b().isEmpty()) {
         this.func_147053_i();
      }
   }

   @Override
   public void func_146281_b() {
      super.func_146281_b();
      if (this.field_146297_k.field_71439_g != null && this.field_146297_k.field_71439_g.field_71071_by != null) {
         this.field_146297_k.field_71439_g.field_71069_bz.func_82847_b(this.field_147059_E);
      }

      this.field_146297_k.field_195559_v.func_197967_a(false);
   }

   @Override
   public boolean charTyped(char var1, int var2) {
      if (this.field_195377_F) {
         return false;
      } else if (field_147058_w != ItemGroup.field_78027_g.func_78021_a()) {
         return false;
      } else {
         String ☃ = this.field_147062_A.func_146179_b();
         if (this.field_147062_A.charTyped(☃, ☃)) {
            if (!Objects.equals(☃, this.field_147062_A.func_146179_b())) {
               this.func_147053_i();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean keyPressed(int var1, int var2, int var3) {
      this.field_195377_F = false;
      if (field_147058_w != ItemGroup.field_78027_g.func_78021_a()) {
         if (this.field_146297_k.field_71474_y.field_74310_D.func_197976_a(☃, ☃)) {
            this.field_195377_F = true;
            this.func_147050_b(ItemGroup.field_78027_g);
            return true;
         } else {
            return super.keyPressed(☃, ☃, ☃);
         }
      } else {
         boolean ☃ = !this.func_208018_a(this.field_147006_u) || this.field_147006_u != null && this.field_147006_u.func_75216_d();
         if (☃ && this.func_195363_d(☃, ☃)) {
            this.field_195377_F = true;
            return true;
         } else {
            String ☃ = this.field_147062_A.func_146179_b();
            if (this.field_147062_A.keyPressed(☃, ☃, ☃)) {
               if (!Objects.equals(☃, this.field_147062_A.func_146179_b())) {
                  this.func_147053_i();
               }

               return true;
            } else {
               return super.keyPressed(☃, ☃, ☃);
            }
         }
      }
   }

   @Override
   public boolean keyReleased(int var1, int var2, int var3) {
      this.field_195377_F = false;
      return super.keyReleased(☃, ☃, ☃);
   }

   private void func_147053_i() {
      GuiContainerCreative.ContainerCreative ☃ = (GuiContainerCreative.ContainerCreative)this.field_147002_h;
      ☃.field_148330_a.clear();
      if (this.field_147062_A.func_146179_b().isEmpty()) {
         for(Item ☃x : IRegistry.field_212630_s) {
            ☃x.func_150895_a(ItemGroup.field_78027_g, ☃.field_148330_a);
         }
      } else {
         ☃.field_148330_a
            .addAll(
               this.field_146297_k.func_193987_a(SearchTreeManager.field_194011_a).func_194038_a(this.field_147062_A.func_146179_b().toLowerCase(Locale.ROOT))
            );
      }

      this.field_147067_x = 0.0F;
      ☃.func_148329_a(0.0F);
   }

   @Override
   protected void func_146979_b(int var1, int var2) {
      ItemGroup ☃ = ItemGroup.field_78032_a[field_147058_w];
      if (☃.func_78019_g()) {
         GlStateManager.func_179084_k();
         this.field_146289_q.func_211126_b(I18n.func_135052_a(☃.func_78024_c()), 8.0F, 6.0F, 4210752);
      }
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      if (☃ == 0) {
         double ☃ = ☃ - (double)this.field_147003_i;
         double ☃x = ☃ - (double)this.field_147009_r;

         for(ItemGroup ☃xx : ItemGroup.field_78032_a) {
            if (this.func_195375_a(☃xx, ☃, ☃x)) {
               return true;
            }
         }

         if (field_147058_w != ItemGroup.field_78036_m.func_78021_a() && this.func_195376_a(☃, ☃)) {
            this.field_147066_y = this.func_147055_p();
            return true;
         }
      }

      return super.mouseClicked(☃, ☃, ☃);
   }

   @Override
   public boolean mouseReleased(double var1, double var3, int var5) {
      if (☃ == 0) {
         double ☃ = ☃ - (double)this.field_147003_i;
         double ☃x = ☃ - (double)this.field_147009_r;
         this.field_147066_y = false;

         for(ItemGroup ☃xx : ItemGroup.field_78032_a) {
            if (this.func_195375_a(☃xx, ☃, ☃x)) {
               this.func_147050_b(☃xx);
               return true;
            }
         }
      }

      return super.mouseReleased(☃, ☃, ☃);
   }

   private boolean func_147055_p() {
      return field_147058_w != ItemGroup.field_78036_m.func_78021_a()
         && ItemGroup.field_78032_a[field_147058_w].func_78017_i()
         && ((GuiContainerCreative.ContainerCreative)this.field_147002_h).func_148328_e();
   }

   private void func_147050_b(ItemGroup var1) {
      int ☃ = field_147058_w;
      field_147058_w = ☃.func_78021_a();
      GuiContainerCreative.ContainerCreative ☃x = (GuiContainerCreative.ContainerCreative)this.field_147002_h;
      this.field_147008_s.clear();
      ☃x.field_148330_a.clear();
      if (☃ == ItemGroup.field_192395_m) {
         CreativeSettings ☃xx = this.field_146297_k.func_199403_al();

         for(int ☃xxx = 0; ☃xxx < 9; ++☃xxx) {
            HotbarSnapshot ☃xxxx = ☃xx.func_192563_a(☃xxx);
            if (☃xxxx.isEmpty()) {
               for(int ☃xxxxx = 0; ☃xxxxx < 9; ++☃xxxxx) {
                  if (☃xxxxx == ☃xxx) {
                     ItemStack ☃xxxxxx = new ItemStack(Items.field_151121_aF);
                     ☃xxxxxx.func_190925_c("CustomCreativeLock");
                     String ☃xxxxxxx = this.field_146297_k.field_71474_y.field_151456_ac[☃xxx].func_197978_k();
                     String ☃xxxxxxxx = this.field_146297_k.field_71474_y.field_193629_ap.func_197978_k();
                     ☃xxxxxx.func_200302_a(new TextComponentTranslation("inventory.hotbarInfo", ☃xxxxxxxx, ☃xxxxxxx));
                     ☃x.field_148330_a.add(☃xxxxxx);
                  } else {
                     ☃x.field_148330_a.add(ItemStack.field_190927_a);
                  }
               }
            } else {
               ☃x.field_148330_a.addAll(☃xxxx);
            }
         }
      } else if (☃ != ItemGroup.field_78027_g) {
         ☃.func_78018_a(☃x.field_148330_a);
      }

      if (☃ == ItemGroup.field_78036_m) {
         Container ☃ = this.field_146297_k.field_71439_g.field_71069_bz;
         if (this.field_147063_B == null) {
            this.field_147063_B = ☃x.field_75151_b;
         }

         ☃x.field_75151_b = Lists.<Slot>newArrayList();

         for(int ☃ = 0; ☃ < ☃.field_75151_b.size(); ++☃) {
            Slot ☃x = new GuiContainerCreative.CreativeSlot((Slot)☃.field_75151_b.get(☃), ☃);
            ☃x.field_75151_b.add(☃x);
            if (☃ >= 5 && ☃ < 9) {
               int ☃xx = ☃ - 5;
               int ☃xxx = ☃xx / 2;
               int ☃xxxx = ☃xx % 2;
               ☃x.field_75223_e = 54 + ☃xxx * 54;
               ☃x.field_75221_f = 6 + ☃xxxx * 27;
            } else if (☃ >= 0 && ☃ < 5) {
               ☃x.field_75223_e = -2000;
               ☃x.field_75221_f = -2000;
            } else if (☃ == 45) {
               ☃x.field_75223_e = 35;
               ☃x.field_75221_f = 20;
            } else if (☃ < ☃.field_75151_b.size()) {
               int ☃x = ☃ - 9;
               int ☃xx = ☃x % 9;
               int ☃xxx = ☃x / 9;
               ☃x.field_75223_e = 9 + ☃xx * 18;
               if (☃ >= 36) {
                  ☃x.field_75221_f = 112;
               } else {
                  ☃x.field_75221_f = 54 + ☃xxx * 18;
               }
            }
         }

         this.field_147064_C = new Slot(field_195378_x, 0, 173, 112);
         ☃x.field_75151_b.add(this.field_147064_C);
      } else if (☃ == ItemGroup.field_78036_m.func_78021_a()) {
         ☃x.field_75151_b = this.field_147063_B;
         this.field_147063_B = null;
      }

      if (this.field_147062_A != null) {
         if (☃ == ItemGroup.field_78027_g) {
            this.field_147062_A.func_146189_e(true);
            this.field_147062_A.func_146205_d(false);
            this.field_147062_A.func_146195_b(true);
            if (☃ != ☃.func_78021_a()) {
               this.field_147062_A.func_146180_a("");
            }

            this.func_147053_i();
         } else {
            this.field_147062_A.func_146189_e(false);
            this.field_147062_A.func_146205_d(true);
            this.field_147062_A.func_146195_b(false);
            this.field_147062_A.func_146180_a("");
         }
      }

      this.field_147067_x = 0.0F;
      ☃x.func_148329_a(0.0F);
   }

   @Override
   public boolean mouseScrolled(double var1) {
      if (!this.func_147055_p()) {
         return false;
      } else {
         int ☃ = (((GuiContainerCreative.ContainerCreative)this.field_147002_h).field_148330_a.size() + 9 - 1) / 9 - 5;
         this.field_147067_x = (float)((double)this.field_147067_x - ☃ / (double)☃);
         this.field_147067_x = MathHelper.func_76131_a(this.field_147067_x, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
         return true;
      }
   }

   @Override
   protected boolean func_195361_a(double var1, double var3, int var5, int var6, int var7) {
      boolean ☃ = ☃ < (double)☃ || ☃ < (double)☃ || ☃ >= (double)(☃ + this.field_146999_f) || ☃ >= (double)(☃ + this.field_147000_g);
      this.field_199506_G = ☃ && !this.func_195375_a(ItemGroup.field_78032_a[field_147058_w], ☃, ☃);
      return this.field_199506_G;
   }

   protected boolean func_195376_a(double var1, double var3) {
      int ☃ = this.field_147003_i;
      int ☃x = this.field_147009_r;
      int ☃xx = ☃ + 175;
      int ☃xxx = ☃x + 18;
      int ☃xxxx = ☃xx + 14;
      int ☃xxxxx = ☃xxx + 112;
      return ☃ >= (double)☃xx && ☃ >= (double)☃xxx && ☃ < (double)☃xxxx && ☃ < (double)☃xxxxx;
   }

   @Override
   public boolean mouseDragged(double var1, double var3, int var5, double var6, double var8) {
      if (this.field_147066_y) {
         int ☃ = this.field_147009_r + 18;
         int ☃x = ☃ + 112;
         this.field_147067_x = ((float)☃ - (float)☃ - 7.5F) / ((float)(☃x - ☃) - 15.0F);
         this.field_147067_x = MathHelper.func_76131_a(this.field_147067_x, 0.0F, 1.0F);
         ((GuiContainerCreative.ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
         return true;
      } else {
         return super.mouseDragged(☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      super.func_73863_a(☃, ☃, ☃);

      for(ItemGroup ☃ : ItemGroup.field_78032_a) {
         if (this.func_147052_b(☃, ☃, ☃)) {
            break;
         }
      }

      if (this.field_147064_C != null
         && field_147058_w == ItemGroup.field_78036_m.func_78021_a()
         && this.func_195359_a(this.field_147064_C.field_75223_e, this.field_147064_C.field_75221_f, 16, 16, (double)☃, (double)☃)) {
         this.func_146279_a(I18n.func_135052_a("inventory.binSlot"), ☃, ☃);
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179140_f();
      this.func_191948_b(☃, ☃);
   }

   @Override
   protected void func_146285_a(ItemStack var1, int var2, int var3) {
      if (field_147058_w == ItemGroup.field_78027_g.func_78021_a()) {
         List<ITextComponent> ☃ = ☃.func_82840_a(
            this.field_146297_k.field_71439_g,
            this.field_146297_k.field_71474_y.field_82882_x ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL
         );
         List<String> ☃x = Lists.newArrayListWithCapacity(☃.size());

         for(ITextComponent ☃xx : ☃) {
            ☃x.add(☃xx.func_150254_d());
         }

         ItemGroup ☃xx = ☃.func_77973_b().func_77640_w();
         if (☃xx == null && ☃.func_77973_b() == Items.field_151134_bR) {
            Map<Enchantment, Integer> ☃xxx = EnchantmentHelper.func_82781_a(☃);
            if (☃xxx.size() == 1) {
               Enchantment ☃xxxx = (Enchantment)☃xxx.keySet().iterator().next();

               for(ItemGroup ☃xxxxx : ItemGroup.field_78032_a) {
                  if (☃xxxxx.func_111226_a(☃xxxx.field_77351_y)) {
                     ☃xx = ☃xxxxx;
                     break;
                  }
               }
            }
         }

         if (☃xx != null) {
            ☃x.add(1, "" + TextFormatting.BOLD + TextFormatting.BLUE + I18n.func_135052_a(☃xx.func_78024_c()));
         }

         for(int ☃xx = 0; ☃xx < ☃x.size(); ++☃xx) {
            if (☃xx == 0) {
               ☃x.set(☃xx, ☃.func_77953_t().field_77937_e + (String)☃x.get(☃xx));
            } else {
               ☃x.set(☃xx, TextFormatting.GRAY + (String)☃x.get(☃xx));
            }
         }

         this.func_146283_a(☃x, ☃, ☃);
      } else {
         super.func_146285_a(☃, ☃, ☃);
      }
   }

   @Override
   protected void func_146976_a(float var1, int var2, int var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      RenderHelper.func_74520_c();
      ItemGroup ☃ = ItemGroup.field_78032_a[field_147058_w];

      for(ItemGroup ☃x : ItemGroup.field_78032_a) {
         this.field_146297_k.func_110434_K().func_110577_a(field_147061_u);
         if (☃x.func_78021_a() != field_147058_w) {
            this.func_147051_a(☃x);
         }
      }

      this.field_146297_k.func_110434_K().func_110577_a(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + ☃.func_78015_f()));
      this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
      this.field_147062_A.func_195608_a(☃, ☃, ☃);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      int ☃x = this.field_147003_i + 175;
      int ☃xx = this.field_147009_r + 18;
      int ☃xxx = ☃xx + 112;
      this.field_146297_k.func_110434_K().func_110577_a(field_147061_u);
      if (☃.func_78017_i()) {
         this.func_73729_b(☃x, ☃xx + (int)((float)(☃xxx - ☃xx - 17) * this.field_147067_x), 232 + (this.func_147055_p() ? 0 : 12), 0, 12, 15);
      }

      this.func_147051_a(☃);
      if (☃ == ItemGroup.field_78036_m) {
         GuiInventory.func_147046_a(
            this.field_147003_i + 88,
            this.field_147009_r + 45,
            20,
            (float)(this.field_147003_i + 88 - ☃),
            (float)(this.field_147009_r + 45 - 30 - ☃),
            this.field_146297_k.field_71439_g
         );
      }
   }

   protected boolean func_195375_a(ItemGroup var1, double var2, double var4) {
      int ☃ = ☃.func_78020_k();
      int ☃x = 28 * ☃;
      int ☃xx = 0;
      if (☃.func_192394_m()) {
         ☃x = this.field_146999_f - 28 * (6 - ☃) + 2;
      } else if (☃ > 0) {
         ☃x += ☃;
      }

      if (☃.func_78023_l()) {
         ☃xx -= 32;
      } else {
         ☃xx += this.field_147000_g;
      }

      return ☃ >= (double)☃x && ☃ <= (double)(☃x + 28) && ☃ >= (double)☃xx && ☃ <= (double)(☃xx + 32);
   }

   protected boolean func_147052_b(ItemGroup var1, int var2, int var3) {
      int ☃ = ☃.func_78020_k();
      int ☃x = 28 * ☃;
      int ☃xx = 0;
      if (☃.func_192394_m()) {
         ☃x = this.field_146999_f - 28 * (6 - ☃) + 2;
      } else if (☃ > 0) {
         ☃x += ☃;
      }

      if (☃.func_78023_l()) {
         ☃xx -= 32;
      } else {
         ☃xx += this.field_147000_g;
      }

      if (this.func_195359_a(☃x + 3, ☃xx + 3, 23, 27, (double)☃, (double)☃)) {
         this.func_146279_a(I18n.func_135052_a(☃.func_78024_c()), ☃, ☃);
         return true;
      } else {
         return false;
      }
   }

   protected void func_147051_a(ItemGroup var1) {
      boolean ☃ = ☃.func_78021_a() == field_147058_w;
      boolean ☃x = ☃.func_78023_l();
      int ☃xx = ☃.func_78020_k();
      int ☃xxx = ☃xx * 28;
      int ☃xxxx = 0;
      int ☃xxxxx = this.field_147003_i + 28 * ☃xx;
      int ☃xxxxxx = this.field_147009_r;
      int ☃xxxxxxx = 32;
      if (☃) {
         ☃xxxx += 32;
      }

      if (☃.func_192394_m()) {
         ☃xxxxx = this.field_147003_i + this.field_146999_f - 28 * (6 - ☃xx);
      } else if (☃xx > 0) {
         ☃xxxxx += ☃xx;
      }

      if (☃x) {
         ☃xxxxxx -= 28;
      } else {
         ☃xxxx += 64;
         ☃xxxxxx += this.field_147000_g - 4;
      }

      GlStateManager.func_179140_f();
      this.func_73729_b(☃xxxxx, ☃xxxxxx, ☃xxx, ☃xxxx, 28, 32);
      this.field_73735_i = 100.0F;
      this.field_146296_j.field_77023_b = 100.0F;
      ☃xxxxx += 6;
      ☃xxxxxx += 8 + (☃x ? 1 : -1);
      GlStateManager.func_179145_e();
      GlStateManager.func_179091_B();
      ItemStack ☃ = ☃.func_151244_d();
      this.field_146296_j.func_180450_b(☃, ☃xxxxx, ☃xxxxxx);
      this.field_146296_j.func_175030_a(this.field_146289_q, ☃, ☃xxxxx, ☃xxxxxx);
      GlStateManager.func_179140_f();
      this.field_146296_j.field_77023_b = 0.0F;
      this.field_73735_i = 0.0F;
   }

   public int func_147056_g() {
      return field_147058_w;
   }

   public static void func_192044_a(Minecraft var0, int var1, boolean var2, boolean var3) {
      EntityPlayerSP ☃ = ☃.field_71439_g;
      CreativeSettings ☃x = ☃.func_199403_al();
      HotbarSnapshot ☃xx = ☃x.func_192563_a(☃);
      if (☃) {
         for(int ☃xxx = 0; ☃xxx < InventoryPlayer.func_70451_h(); ++☃xxx) {
            ItemStack ☃xxxx = ☃xx.get(☃xxx).func_77946_l();
            ☃.field_71071_by.func_70299_a(☃xxx, ☃xxxx);
            ☃.field_71442_b.func_78761_a(☃xxxx, 36 + ☃xxx);
         }

         ☃.field_71069_bz.func_75142_b();
      } else if (☃) {
         for(int ☃ = 0; ☃ < InventoryPlayer.func_70451_h(); ++☃) {
            ☃xx.set(☃, ☃.field_71071_by.func_70301_a(☃).func_77946_l());
         }

         String ☃ = ☃.field_71474_y.field_151456_ac[☃].func_197978_k();
         String ☃x = ☃.field_71474_y.field_193630_aq.func_197978_k();
         ☃.field_71456_v.func_175188_a(new TextComponentTranslation("inventory.hotbarSaved", ☃x, ☃), false);
         ☃x.func_192564_b();
      }
   }

   public static class ContainerCreative extends Container {
      public NonNullList<ItemStack> field_148330_a = NonNullList.func_191196_a();

      public ContainerCreative(EntityPlayer var1) {
         InventoryPlayer ☃ = ☃.field_71071_by;

         for(int ☃x = 0; ☃x < 5; ++☃x) {
            for(int ☃xx = 0; ☃xx < 9; ++☃xx) {
               this.func_75146_a(new GuiContainerCreative.LockedSlot(GuiContainerCreative.field_195378_x, ☃x * 9 + ☃xx, 9 + ☃xx * 18, 18 + ☃x * 18));
            }
         }

         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x, 9 + ☃x * 18, 112));
         }

         this.func_148329_a(0.0F);
      }

      @Override
      public boolean func_75145_c(EntityPlayer var1) {
         return true;
      }

      public void func_148329_a(float var1) {
         int ☃ = (this.field_148330_a.size() + 9 - 1) / 9 - 5;
         int ☃x = (int)((double)(☃ * (float)☃) + 0.5);
         if (☃x < 0) {
            ☃x = 0;
         }

         for(int ☃ = 0; ☃ < 5; ++☃) {
            for(int ☃x = 0; ☃x < 9; ++☃x) {
               int ☃xx = ☃x + (☃ + ☃x) * 9;
               if (☃xx >= 0 && ☃xx < this.field_148330_a.size()) {
                  GuiContainerCreative.field_195378_x.func_70299_a(☃x + ☃ * 9, this.field_148330_a.get(☃xx));
               } else {
                  GuiContainerCreative.field_195378_x.func_70299_a(☃x + ☃ * 9, ItemStack.field_190927_a);
               }
            }
         }
      }

      public boolean func_148328_e() {
         return this.field_148330_a.size() > 45;
      }

      @Override
      public ItemStack func_82846_b(EntityPlayer var1, int var2) {
         if (☃ >= this.field_75151_b.size() - 9 && ☃ < this.field_75151_b.size()) {
            Slot ☃ = (Slot)this.field_75151_b.get(☃);
            if (☃ != null && ☃.func_75216_d()) {
               ☃.func_75215_d(ItemStack.field_190927_a);
            }
         }

         return ItemStack.field_190927_a;
      }

      @Override
      public boolean func_94530_a(ItemStack var1, Slot var2) {
         return ☃.field_75221_f > 90;
      }

      @Override
      public boolean func_94531_b(Slot var1) {
         return ☃.field_75224_c instanceof InventoryPlayer || ☃.field_75221_f > 90 && ☃.field_75223_e <= 162;
      }
   }

   class CreativeSlot extends Slot {
      private final Slot field_148332_b;

      public CreativeSlot(Slot var2, int var3) {
         super(☃.field_75224_c, ☃, 0, 0);
         this.field_148332_b = ☃;
      }

      @Override
      public ItemStack func_190901_a(EntityPlayer var1, ItemStack var2) {
         this.field_148332_b.func_190901_a(☃, ☃);
         return ☃;
      }

      @Override
      public boolean func_75214_a(ItemStack var1) {
         return this.field_148332_b.func_75214_a(☃);
      }

      @Override
      public ItemStack func_75211_c() {
         return this.field_148332_b.func_75211_c();
      }

      @Override
      public boolean func_75216_d() {
         return this.field_148332_b.func_75216_d();
      }

      @Override
      public void func_75215_d(ItemStack var1) {
         this.field_148332_b.func_75215_d(☃);
      }

      @Override
      public void func_75218_e() {
         this.field_148332_b.func_75218_e();
      }

      @Override
      public int func_75219_a() {
         return this.field_148332_b.func_75219_a();
      }

      @Override
      public int func_178170_b(ItemStack var1) {
         return this.field_148332_b.func_178170_b(☃);
      }

      @Nullable
      @Override
      public String func_178171_c() {
         return this.field_148332_b.func_178171_c();
      }

      @Override
      public ItemStack func_75209_a(int var1) {
         return this.field_148332_b.func_75209_a(☃);
      }

      @Override
      public boolean func_75217_a(IInventory var1, int var2) {
         return this.field_148332_b.func_75217_a(☃, ☃);
      }

      @Override
      public boolean func_111238_b() {
         return this.field_148332_b.func_111238_b();
      }

      @Override
      public boolean func_82869_a(EntityPlayer var1) {
         return this.field_148332_b.func_82869_a(☃);
      }
   }

   static class LockedSlot extends Slot {
      public LockedSlot(IInventory var1, int var2, int var3, int var4) {
         super(☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_82869_a(EntityPlayer var1) {
         if (super.func_82869_a(☃) && this.func_75216_d()) {
            return this.func_75211_c().func_179543_a("CustomCreativeLock") == null;
         } else {
            return !this.func_75216_d();
         }
      }
   }
}
