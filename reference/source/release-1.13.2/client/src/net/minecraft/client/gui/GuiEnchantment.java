package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.model.ModelBook;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.INameable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class GuiEnchantment extends GuiContainer {
   private static final ResourceLocation field_147078_C = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation field_147070_D = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private static final ModelBook field_147072_E = new ModelBook();
   private final InventoryPlayer field_175379_F;
   private final Random field_147074_F = new Random();
   private final ContainerEnchantment field_147075_G;
   public int field_147073_u;
   public float field_147071_v;
   public float field_147069_w;
   public float field_147082_x;
   public float field_147081_y;
   public float field_147080_z;
   public float field_147076_A;
   private ItemStack field_147077_B = ItemStack.field_190927_a;
   private final INameable field_175380_I;

   public GuiEnchantment(InventoryPlayer var1, World var2, INameable var3) {
      super(new ContainerEnchantment(☃, ☃));
      this.field_175379_F = ☃;
      this.field_147075_G = (ContainerEnchantment)this.field_147002_h;
      this.field_175380_I = ☃;
   }

   @Override
   protected void func_146979_b(int var1, int var2) {
      this.field_146289_q.func_211126_b(this.field_175380_I.func_145748_c_().func_150254_d(), 12.0F, 5.0F, 4210752);
      this.field_146289_q.func_211126_b(this.field_175379_F.func_145748_c_().func_150254_d(), 8.0F, (float)(this.field_147000_g - 96 + 2), 4210752);
   }

   @Override
   public void func_73876_c() {
      super.func_73876_c();
      this.func_147068_g();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      int ☃ = (this.field_146294_l - this.field_146999_f) / 2;
      int ☃x = (this.field_146295_m - this.field_147000_g) / 2;

      for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
         double ☃xxx = ☃ - (double)(☃ + 60);
         double ☃xxxx = ☃ - (double)(☃x + 14 + 19 * ☃xx);
         if (☃xxx >= 0.0 && ☃xxxx >= 0.0 && ☃xxx < 108.0 && ☃xxxx < 19.0 && this.field_147075_G.func_75140_a(this.field_146297_k.field_71439_g, ☃xx)) {
            this.field_146297_k.field_71442_b.func_78756_a(this.field_147075_G.field_75152_c, ☃xx);
            return true;
         }
      }

      return super.mouseClicked(☃, ☃, ☃);
   }

   @Override
   protected void func_146976_a(float var1, int var2, int var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147078_C);
      int ☃ = (this.field_146294_l - this.field_146999_f) / 2;
      int ☃x = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(☃, ☃x, 0, 0, this.field_146999_f, this.field_147000_g);
      GlStateManager.func_179094_E();
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179094_E();
      GlStateManager.func_179096_D();
      int ☃xx = (int)this.field_146297_k.field_195558_d.func_198100_s();
      GlStateManager.func_179083_b((this.field_146294_l - 320) / 2 * ☃xx, (this.field_146295_m - 240) / 2 * ☃xx, 320 * ☃xx, 240 * ☃xx);
      GlStateManager.func_179109_b(-0.34F, 0.23F, 0.0F);
      GlStateManager.func_199294_a(Matrix4f.func_195876_a(90.0, 1.3333334F, 9.0F, 80.0F));
      float ☃xxx = 1.0F;
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179096_D();
      RenderHelper.func_74519_b();
      GlStateManager.func_179109_b(0.0F, 3.3F, -16.0F);
      GlStateManager.func_179152_a(1.0F, 1.0F, 1.0F);
      float ☃xxxx = 5.0F;
      GlStateManager.func_179152_a(5.0F, 5.0F, 5.0F);
      GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147070_D);
      GlStateManager.func_179114_b(20.0F, 1.0F, 0.0F, 0.0F);
      float ☃xxxxx = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * ☃;
      GlStateManager.func_179109_b((1.0F - ☃xxxxx) * 0.2F, (1.0F - ☃xxxxx) * 0.1F, (1.0F - ☃xxxxx) * 0.25F);
      GlStateManager.func_179114_b(-(1.0F - ☃xxxxx) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(180.0F, 1.0F, 0.0F, 0.0F);
      float ☃xxxxxx = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * ☃ + 0.25F;
      float ☃xxxxxxx = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * ☃ + 0.75F;
      ☃xxxxxx = (☃xxxxxx - (float)MathHelper.func_76140_b((double)☃xxxxxx)) * 1.6F - 0.3F;
      ☃xxxxxxx = (☃xxxxxxx - (float)MathHelper.func_76140_b((double)☃xxxxxxx)) * 1.6F - 0.3F;
      if (☃xxxxxx < 0.0F) {
         ☃xxxxxx = 0.0F;
      }

      if (☃xxxxxxx < 0.0F) {
         ☃xxxxxxx = 0.0F;
      }

      if (☃xxxxxx > 1.0F) {
         ☃xxxxxx = 1.0F;
      }

      if (☃xxxxxxx > 1.0F) {
         ☃xxxxxxx = 1.0F;
      }

      GlStateManager.func_179091_B();
      field_147072_E.func_78088_a(null, 0.0F, ☃xxxxxx, ☃xxxxxxx, ☃xxxxx, 0.0F, 0.0625F);
      GlStateManager.func_179101_C();
      RenderHelper.func_74518_a();
      GlStateManager.func_179128_n(5889);
      GlStateManager.func_179083_b(0, 0, this.field_146297_k.field_195558_d.func_198109_k(), this.field_146297_k.field_195558_d.func_198091_l());
      GlStateManager.func_179121_F();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179121_F();
      RenderHelper.func_74518_a();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.func_178176_a().func_148335_a((long)this.field_147075_G.field_178149_f);
      int ☃ = this.field_147075_G.func_178147_e();

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         int ☃xx = ☃ + 60;
         int ☃xxx = ☃xx + 20;
         this.field_73735_i = 0.0F;
         this.field_146297_k.func_110434_K().func_110577_a(field_147078_C);
         int ☃xxxx = this.field_147075_G.field_75167_g[☃x];
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         if (☃xxxx == 0) {
            this.func_73729_b(☃xx, ☃x + 14 + 19 * ☃x, 0, 185, 108, 19);
         } else {
            String ☃xx = "" + ☃xxxx;
            int ☃xxx = 86 - this.field_146289_q.func_78256_a(☃xx);
            String ☃xxxx = EnchantmentNameParts.func_178176_a().func_148334_a(this.field_146289_q, ☃xxx);
            FontRenderer ☃xxxxx = this.field_146297_k.func_211500_ak().func_211504_a(Minecraft.field_71464_q);
            int ☃xxxxxx = 6839882;
            if ((☃ < ☃x + 1 || this.field_146297_k.field_71439_g.field_71068_ca < ☃xxxx) && !this.field_146297_k.field_71439_g.field_71075_bZ.field_75098_d) {
               this.func_73729_b(☃xx, ☃x + 14 + 19 * ☃x, 0, 185, 108, 19);
               this.func_73729_b(☃xx + 1, ☃x + 15 + 19 * ☃x, 16 * ☃x, 239, 16, 16);
               ☃xxxxx.func_78279_b(☃xxxx, ☃xxx, ☃x + 16 + 19 * ☃x, ☃xxx, (☃xxxxxx & 16711422) >> 1);
               ☃xxxxxx = 4226832;
            } else {
               int ☃xx = ☃ - (☃ + 60);
               int ☃xxx = ☃ - (☃x + 14 + 19 * ☃x);
               if (☃xx >= 0 && ☃xxx >= 0 && ☃xx < 108 && ☃xxx < 19) {
                  this.func_73729_b(☃xx, ☃x + 14 + 19 * ☃x, 0, 204, 108, 19);
                  ☃xxxxxx = 16777088;
               } else {
                  this.func_73729_b(☃xx, ☃x + 14 + 19 * ☃x, 0, 166, 108, 19);
               }

               this.func_73729_b(☃xx + 1, ☃x + 15 + 19 * ☃x, 16 * ☃x, 223, 16, 16);
               ☃xxxxx.func_78279_b(☃xxxx, ☃xxx, ☃x + 16 + 19 * ☃x, ☃xxx, ☃xxxxxx);
               ☃xxxxxx = 8453920;
            }

            ☃xxxxx = this.field_146297_k.field_71466_p;
            ☃xxxxx.func_175063_a(☃xx, (float)(☃xxx + 86 - ☃xxxxx.func_78256_a(☃xx)), (float)(☃x + 16 + 19 * ☃x + 7), ☃xxxxxx);
         }
      }
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      ☃ = this.field_146297_k.func_184121_ak();
      this.func_146276_q_();
      super.func_73863_a(☃, ☃, ☃);
      this.func_191948_b(☃, ☃);
      boolean ☃ = this.field_146297_k.field_71439_g.field_71075_bZ.field_75098_d;
      int ☃x = this.field_147075_G.func_178147_e();

      for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
         int ☃xxx = this.field_147075_G.field_75167_g[☃xx];
         Enchantment ☃xxxx = Enchantment.func_185262_c(this.field_147075_G.field_185001_h[☃xx]);
         int ☃xxxxx = this.field_147075_G.field_185002_i[☃xx];
         int ☃xxxxxx = ☃xx + 1;
         if (this.func_195359_a(60, 14 + 19 * ☃xx, 108, 17, (double)☃, (double)☃) && ☃xxx > 0 && ☃xxxxx >= 0 && ☃xxxx != null) {
            List<String> ☃xxxxxxx = Lists.newArrayList();
            ☃xxxxxxx.add(
               "" + TextFormatting.WHITE + TextFormatting.ITALIC + I18n.func_135052_a("container.enchant.clue", ☃xxxx.func_200305_d(☃xxxxx).func_150254_d())
            );
            if (!☃) {
               ☃xxxxxxx.add("");
               if (this.field_146297_k.field_71439_g.field_71068_ca < ☃xxx) {
                  ☃xxxxxxx.add(TextFormatting.RED + I18n.func_135052_a("container.enchant.level.requirement", this.field_147075_G.field_75167_g[☃xx]));
               } else {
                  String ☃xxxxxxxx;
                  if (☃xxxxxx == 1) {
                     ☃xxxxxxxx = I18n.func_135052_a("container.enchant.lapis.one");
                  } else {
                     ☃xxxxxxxx = I18n.func_135052_a("container.enchant.lapis.many", ☃xxxxxx);
                  }

                  TextFormatting ☃xxxxxxxx = ☃x >= ☃xxxxxx ? TextFormatting.GRAY : TextFormatting.RED;
                  ☃xxxxxxx.add(☃xxxxxxxx + "" + ☃xxxxxxxx);
                  if (☃xxxxxx == 1) {
                     ☃xxxxxxxx = I18n.func_135052_a("container.enchant.level.one");
                  } else {
                     ☃xxxxxxxx = I18n.func_135052_a("container.enchant.level.many", ☃xxxxxx);
                  }

                  ☃xxxxxxx.add(TextFormatting.GRAY + "" + ☃xxxxxxxx);
               }
            }

            this.func_146283_a(☃xxxxxxx, ☃, ☃);
            break;
         }
      }
   }

   public void func_147068_g() {
      ItemStack ☃ = this.field_147002_h.func_75139_a(0).func_75211_c();
      if (!ItemStack.func_77989_b(☃, this.field_147077_B)) {
         this.field_147077_B = ☃;

         do {
            this.field_147082_x += (float)(this.field_147074_F.nextInt(4) - this.field_147074_F.nextInt(4));
         } while(this.field_147071_v <= this.field_147082_x + 1.0F && this.field_147071_v >= this.field_147082_x - 1.0F);
      }

      ++this.field_147073_u;
      this.field_147069_w = this.field_147071_v;
      this.field_147076_A = this.field_147080_z;
      boolean ☃ = false;

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         if (this.field_147075_G.field_75167_g[☃x] != 0) {
            ☃ = true;
         }
      }

      if (☃) {
         this.field_147080_z += 0.2F;
      } else {
         this.field_147080_z -= 0.2F;
      }

      this.field_147080_z = MathHelper.func_76131_a(this.field_147080_z, 0.0F, 1.0F);
      float ☃x = (this.field_147082_x - this.field_147071_v) * 0.4F;
      float ☃xx = 0.2F;
      ☃x = MathHelper.func_76131_a(☃x, -0.2F, 0.2F);
      this.field_147081_y += (☃x - this.field_147081_y) * 0.9F;
      this.field_147071_v += this.field_147081_y;
   }
}
