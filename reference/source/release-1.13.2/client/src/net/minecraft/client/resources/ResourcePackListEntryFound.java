package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiResourcePackSelected;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class ResourcePackListEntryFound extends GuiListExtended.IGuiListEntry<ResourcePackListEntryFound> {
   private static final ResourceLocation field_195028_e = new ResourceLocation("textures/gui/resource_packs.png");
   private static final ITextComponent field_195029_f = new TextComponentTranslation("resourcePack.incompatible");
   private static final ITextComponent field_195030_g = new TextComponentTranslation("resourcePack.incompatible.confirm.title");
   protected final Minecraft field_195026_c;
   protected final GuiScreenResourcePacks field_195027_d;
   private final ResourcePackInfoClient field_148319_c;

   public ResourcePackListEntryFound(GuiScreenResourcePacks var1, ResourcePackInfoClient var2) {
      this.field_195027_d = ☃;
      this.field_195026_c = Minecraft.func_71410_x();
      this.field_148319_c = ☃;
   }

   public void func_195020_a(GuiResourcePackSelected var1) {
      this.func_195017_i().func_195792_i().func_198993_a(☃.func_195074_b(), this, ResourcePackListEntryFound::func_195017_i, true);
   }

   protected void func_148313_c() {
      this.field_148319_c.func_195808_a(this.field_195026_c.func_110434_K());
   }

   protected PackCompatibility func_195019_f() {
      return this.field_148319_c.func_195791_d();
   }

   protected String func_148311_a() {
      return this.field_148319_c.func_195795_c().func_150254_d();
   }

   protected String func_148312_b() {
      return this.field_148319_c.func_195789_b().func_150254_d();
   }

   public ResourcePackInfoClient func_195017_i() {
      return this.field_148319_c;
   }

   @Override
   public void func_194999_a(int var1, int var2, int var3, int var4, boolean var5, float var6) {
      int ☃ = this.func_195001_c();
      int ☃x = this.func_195002_d();
      PackCompatibility ☃xx = this.func_195019_f();
      if (!☃xx.func_198968_a()) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.func_73734_a(☃x - 1, ☃ - 1, ☃x + ☃ - 9, ☃ + ☃ + 1, -8978432);
      }

      this.func_148313_c();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.func_146110_a(☃x, ☃, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      String ☃ = this.func_148312_b();
      String ☃x = this.func_148311_a();
      if (this.func_195024_j() && (this.field_195026_c.field_71474_y.field_85185_A || ☃)) {
         this.field_195026_c.func_110434_K().func_110577_a(field_195028_e);
         Gui.func_73734_a(☃x, ☃, ☃x + 32, ☃ + 32, -1601138544);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         int ☃xx = ☃ - ☃x;
         int ☃xxx = ☃ - ☃;
         if (!☃xx.func_198968_a()) {
            ☃ = field_195029_f.func_150254_d();
            ☃x = ☃xx.func_198967_b().func_150254_d();
         }

         if (this.func_195025_k()) {
            if (☃xx < 32) {
               Gui.func_146110_a(☃x, ☃, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.func_146110_a(☃x, ☃, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         } else {
            if (this.func_195022_l()) {
               if (☃xx < 16) {
                  Gui.func_146110_a(☃x, ☃, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.func_146110_a(☃x, ☃, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_195023_m()) {
               if (☃xx < 32 && ☃xx > 16 && ☃xxx < 16) {
                  Gui.func_146110_a(☃x, ☃, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.func_146110_a(☃x, ☃, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_195021_n()) {
               if (☃xx < 32 && ☃xx > 16 && ☃xxx > 16) {
                  Gui.func_146110_a(☃x, ☃, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.func_146110_a(☃x, ☃, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }
         }
      }

      int ☃ = this.field_195026_c.field_71466_p.func_78256_a(☃);
      if (☃ > 157) {
         ☃ = this.field_195026_c.field_71466_p.func_78269_a(☃, 157 - this.field_195026_c.field_71466_p.func_78256_a("...")) + "...";
      }

      this.field_195026_c.field_71466_p.func_175063_a(☃, (float)(☃x + 32 + 2), (float)(☃ + 1), 16777215);
      List<String> ☃ = this.field_195026_c.field_71466_p.func_78271_c(☃x, 157);

      for(int ☃x = 0; ☃x < 2 && ☃x < ☃.size(); ++☃x) {
         this.field_195026_c.field_71466_p.func_175063_a((String)☃.get(☃x), (float)(☃x + 32 + 2), (float)(☃ + 12 + 10 * ☃x), 8421504);
      }
   }

   protected boolean func_195024_j() {
      return !this.field_148319_c.func_195798_h() || !this.field_148319_c.func_195797_g();
   }

   protected boolean func_195025_k() {
      return !this.field_195027_d.func_195312_c(this);
   }

   protected boolean func_195022_l() {
      return this.field_195027_d.func_195312_c(this) && !this.field_148319_c.func_195797_g();
   }

   protected boolean func_195023_m() {
      List<ResourcePackListEntryFound> ☃ = this.func_194998_a().func_195074_b();
      int ☃x = ☃.indexOf(this);
      return ☃x > 0 && !((ResourcePackListEntryFound)☃.get(☃x - 1)).field_148319_c.func_195798_h();
   }

   protected boolean func_195021_n() {
      List<ResourcePackListEntryFound> ☃ = this.func_194998_a().func_195074_b();
      int ☃x = ☃.indexOf(this);
      return ☃x >= 0 && ☃x < ☃.size() - 1 && !((ResourcePackListEntryFound)☃.get(☃x + 1)).field_148319_c.func_195798_h();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      double ☃ = ☃ - (double)this.func_195002_d();
      double ☃x = ☃ - (double)this.func_195001_c();
      if (this.func_195024_j() && ☃ <= 32.0) {
         if (this.func_195025_k()) {
            this.func_195018_o().func_175288_g();
            PackCompatibility ☃xx = this.func_195019_f();
            if (☃xx.func_198968_a()) {
               this.func_195018_o().func_195301_a(this);
            } else {
               String ☃xx = field_195030_g.func_150254_d();
               String ☃xxx = ☃xx.func_198971_c().func_150254_d();
               this.field_195026_c.func_147108_a(new GuiYesNo((var1x, var2) -> {
                  this.field_195026_c.func_147108_a(this.func_195018_o());
                  if (var1x) {
                     this.func_195018_o().func_195301_a(this);
                  }
               }, ☃xx, ☃xxx, 0));
            }

            return true;
         }

         if (☃ < 16.0 && this.func_195022_l()) {
            this.func_195018_o().func_195305_b(this);
            return true;
         }

         if (☃ > 16.0 && ☃x < 16.0 && this.func_195023_m()) {
            List<ResourcePackListEntryFound> ☃xx = this.func_194998_a().func_195074_b();
            int ☃xxx = ☃xx.indexOf(this);
            ☃xx.remove(this);
            ☃xx.add(☃xxx - 1, this);
            this.func_195018_o().func_175288_g();
            return true;
         }

         if (☃ > 16.0 && ☃x > 16.0 && this.func_195021_n()) {
            List<ResourcePackListEntryFound> ☃xx = this.func_194998_a().func_195074_b();
            int ☃xxx = ☃xx.indexOf(this);
            ☃xx.remove(this);
            ☃xx.add(☃xxx + 1, this);
            this.func_195018_o().func_175288_g();
            return true;
         }
      }

      return false;
   }

   public GuiScreenResourcePacks func_195018_o() {
      return this.field_195027_d;
   }
}
