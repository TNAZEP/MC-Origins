package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

public class GuiPlayerTabOverlay extends Gui {
   private static final Ordering<NetworkPlayerInfo> field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator());
   private final Minecraft field_175250_f;
   private final GuiIngame field_175251_g;
   private ITextComponent field_175255_h;
   private ITextComponent field_175256_i;
   private long field_175253_j;
   private boolean field_175254_k;

   public GuiPlayerTabOverlay(Minecraft var1, GuiIngame var2) {
      this.field_175250_f = ☃;
      this.field_175251_g = ☃;
   }

   public ITextComponent func_200262_a(NetworkPlayerInfo var1) {
      return ☃.func_178854_k() != null
         ? ☃.func_178854_k()
         : ScorePlayerTeam.func_200541_a(☃.func_178850_i(), new TextComponentString(☃.func_178845_a().getName()));
   }

   public void func_175246_a(boolean var1) {
      if (☃ && !this.field_175254_k) {
         this.field_175253_j = Util.func_211177_b();
      }

      this.field_175254_k = ☃;
   }

   public void func_175249_a(int var1, Scoreboard var2, @Nullable ScoreObjective var3) {
      NetHandlerPlayClient ☃ = this.field_175250_f.field_71439_g.field_71174_a;
      List<NetworkPlayerInfo> ☃x = field_175252_a.sortedCopy(☃.func_175106_d());
      int ☃xx = 0;
      int ☃xxx = 0;

      for(NetworkPlayerInfo ☃xxxx : ☃x) {
         int ☃xxxxx = this.field_175250_f.field_71466_p.func_78256_a(this.func_200262_a(☃xxxx).func_150254_d());
         ☃xx = Math.max(☃xx, ☃xxxxx);
         if (☃ != null && ☃.func_199865_f() != ScoreCriteria.RenderType.HEARTS) {
            ☃xxxxx = this.field_175250_f.field_71466_p.func_78256_a(" " + ☃.func_96529_a(☃xxxx.func_178845_a().getName(), ☃).func_96652_c());
            ☃xxx = Math.max(☃xxx, ☃xxxxx);
         }
      }

      ☃x = ☃x.subList(0, Math.min(☃x.size(), 80));
      int ☃xxxx = ☃x.size();
      int ☃xxxxx = ☃xxxx;

      int ☃;
      for(☃ = 1; ☃xxxxx > 20; ☃xxxxx = (☃xxxx + ☃ - 1) / ☃) {
         ++☃;
      }

      boolean ☃xxxxxxx = this.field_175250_f.func_71387_A() || this.field_175250_f.func_147114_u().func_147298_b().func_179292_f();
      int ☃xxxxxx;
      if (☃ != null) {
         if (☃.func_199865_f() == ScoreCriteria.RenderType.HEARTS) {
            ☃xxxxxx = 90;
         } else {
            ☃xxxxxx = ☃xxx;
         }
      } else {
         ☃xxxxxx = 0;
      }

      int ☃xxxxxx = Math.min(☃ * ((☃xxxxxxx ? 9 : 0) + ☃xx + ☃xxxxxx + 13), ☃ - 50) / ☃;
      int ☃xxxxxxx = ☃ / 2 - (☃xxxxxx * ☃ + (☃ - 1) * 5) / 2;
      int ☃xxxxxxxx = 10;
      int ☃xxxxxxxxx = ☃xxxxxx * ☃ + (☃ - 1) * 5;
      List<String> ☃xxxxxxxxxx = null;
      if (this.field_175256_i != null) {
         ☃xxxxxxxxxx = this.field_175250_f.field_71466_p.func_78271_c(this.field_175256_i.func_150254_d(), ☃ - 50);

         for(String ☃xxxxxxxxxxx : ☃xxxxxxxxxx) {
            ☃xxxxxxxxx = Math.max(☃xxxxxxxxx, this.field_175250_f.field_71466_p.func_78256_a(☃xxxxxxxxxxx));
         }
      }

      List<String> ☃xxxxxx = null;
      if (this.field_175255_h != null) {
         ☃xxxxxx = this.field_175250_f.field_71466_p.func_78271_c(this.field_175255_h.func_150254_d(), ☃ - 50);

         for(String ☃xxxxxxx : ☃xxxxxx) {
            ☃xxxxxxxxx = Math.max(☃xxxxxxxxx, this.field_175250_f.field_71466_p.func_78256_a(☃xxxxxxx));
         }
      }

      if (☃xxxxxxxxxx != null) {
         func_73734_a(
            ☃ / 2 - ☃xxxxxxxxx / 2 - 1,
            ☃xxxxxxxx - 1,
            ☃ / 2 + ☃xxxxxxxxx / 2 + 1,
            ☃xxxxxxxx + ☃xxxxxxxxxx.size() * this.field_175250_f.field_71466_p.field_78288_b,
            Integer.MIN_VALUE
         );

         for(String ☃xxxxxx : ☃xxxxxxxxxx) {
            int ☃xxxxxxx = this.field_175250_f.field_71466_p.func_78256_a(☃xxxxxx);
            this.field_175250_f.field_71466_p.func_175063_a(☃xxxxxx, (float)(☃ / 2 - ☃xxxxxxx / 2), (float)☃xxxxxxxx, -1);
            ☃xxxxxxxx += this.field_175250_f.field_71466_p.field_78288_b;
         }

         ++☃xxxxxxxx;
      }

      func_73734_a(☃ / 2 - ☃xxxxxxxxx / 2 - 1, ☃xxxxxxxx - 1, ☃ / 2 + ☃xxxxxxxxx / 2 + 1, ☃xxxxxxxx + ☃xxxxx * 9, Integer.MIN_VALUE);

      for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxx; ++☃xxxxxx) {
         int ☃xxxxxxx = ☃xxxxxx / ☃xxxxx;
         int ☃xxxxxxxx = ☃xxxxxx % ☃xxxxx;
         int ☃xxxxxxxxx = ☃xxxxxxx + ☃xxxxxxx * ☃xxxxxx + ☃xxxxxxx * 5;
         int ☃xxxxxxxxxx = ☃xxxxxxxx + ☃xxxxxxxx * 9;
         func_73734_a(☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxx + ☃xxxxxx, ☃xxxxxxxxxx + 8, 553648127);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.func_179141_d();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         if (☃xxxxxx < ☃x.size()) {
            NetworkPlayerInfo ☃xxxxxxxxxxx = (NetworkPlayerInfo)☃x.get(☃xxxxxx);
            GameProfile ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx.func_178845_a();
            if (☃xxxxxxx) {
               EntityPlayer ☃xxxxxxxxxxxxx = this.field_175250_f.field_71441_e.func_152378_a(☃xxxxxxxxxxxx.getId());
               boolean ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx != null
                  && ☃xxxxxxxxxxxxx.func_175148_a(EnumPlayerModelParts.CAPE)
                  && ("Dinnerbone".equals(☃xxxxxxxxxxxx.getName()) || "Grumm".equals(☃xxxxxxxxxxxx.getName()));
               this.field_175250_f.func_110434_K().func_110577_a(☃xxxxxxxxxxx.func_178837_g());
               int ☃xxxxxxxxxxxxxxx = 8 + (☃xxxxxxxxxxxxxx ? 8 : 0);
               int ☃xxxxxxxxxxxxxxxx = 8 * (☃xxxxxxxxxxxxxx ? -1 : 1);
               Gui.func_152125_a(☃xxxxxxxxx, ☃xxxxxxxxxx, 8.0F, (float)☃xxxxxxxxxxxxxxx, 8, ☃xxxxxxxxxxxxxxxx, 8, 8, 64.0F, 64.0F);
               if (☃xxxxxxxxxxxxx != null && ☃xxxxxxxxxxxxx.func_175148_a(EnumPlayerModelParts.HAT)) {
                  int ☃xxxxxxxxxxxxxxxxx = 8 + (☃xxxxxxxxxxxxxx ? 8 : 0);
                  int ☃xxxxxxxxxxxxxxxxxx = 8 * (☃xxxxxxxxxxxxxx ? -1 : 1);
                  Gui.func_152125_a(☃xxxxxxxxx, ☃xxxxxxxxxx, 40.0F, (float)☃xxxxxxxxxxxxxxxxx, 8, ☃xxxxxxxxxxxxxxxxxx, 8, 8, 64.0F, 64.0F);
               }

               ☃xxxxxxxxx += 9;
            }

            String ☃xxxxxxxxxxx = this.func_200262_a(☃xxxxxxxxxxx).func_150254_d();
            if (☃xxxxxxxxxxx.func_178848_b() == GameType.SPECTATOR) {
               this.field_175250_f.field_71466_p.func_175063_a(TextFormatting.ITALIC + ☃xxxxxxxxxxx, (float)☃xxxxxxxxx, (float)☃xxxxxxxxxx, -1862270977);
            } else {
               this.field_175250_f.field_71466_p.func_175063_a(☃xxxxxxxxxxx, (float)☃xxxxxxxxx, (float)☃xxxxxxxxxx, -1);
            }

            if (☃ != null && ☃xxxxxxxxxxx.func_178848_b() != GameType.SPECTATOR) {
               int ☃xxxxxxxxxxx = ☃xxxxxxxxx + ☃xx + 1;
               int ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx + ☃xxxxxx;
               if (☃xxxxxxxxxxxx - ☃xxxxxxxxxxx > 5) {
                  this.func_175247_a(☃, ☃xxxxxxxxxx, ☃xxxxxxxxxxxx.getName(), ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxx);
               }
            }

            this.func_175245_a(☃xxxxxx, ☃xxxxxxxxx - (☃xxxxxxx ? 9 : 0), ☃xxxxxxxxxx, ☃xxxxxxxxxxx);
         }
      }

      if (☃xxxxxx != null) {
         ☃xxxxxxxx += ☃xxxxx * 9 + 1;
         func_73734_a(
            ☃ / 2 - ☃xxxxxxxxx / 2 - 1,
            ☃xxxxxxxx - 1,
            ☃ / 2 + ☃xxxxxxxxx / 2 + 1,
            ☃xxxxxxxx + ☃xxxxxx.size() * this.field_175250_f.field_71466_p.field_78288_b,
            Integer.MIN_VALUE
         );

         for(String ☃xxxxxx : ☃xxxxxx) {
            int ☃xxxxxxx = this.field_175250_f.field_71466_p.func_78256_a(☃xxxxxx);
            this.field_175250_f.field_71466_p.func_175063_a(☃xxxxxx, (float)(☃ / 2 - ☃xxxxxxx / 2), (float)☃xxxxxxxx, -1);
            ☃xxxxxxxx += this.field_175250_f.field_71466_p.field_78288_b;
         }
      }
   }

   protected void func_175245_a(int var1, int var2, int var3, NetworkPlayerInfo var4) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_175250_f.func_110434_K().func_110577_a(field_110324_m);
      int ☃x = 0;
      int ☃;
      if (☃.func_178853_c() < 0) {
         ☃ = 5;
      } else if (☃.func_178853_c() < 150) {
         ☃ = 0;
      } else if (☃.func_178853_c() < 300) {
         ☃ = 1;
      } else if (☃.func_178853_c() < 600) {
         ☃ = 2;
      } else if (☃.func_178853_c() < 1000) {
         ☃ = 3;
      } else {
         ☃ = 4;
      }

      this.field_73735_i += 100.0F;
      this.func_73729_b(☃ + ☃ - 11, ☃, 0, 176 + ☃ * 8, 10, 8);
      this.field_73735_i -= 100.0F;
   }

   private void func_175247_a(ScoreObjective var1, int var2, String var3, int var4, int var5, NetworkPlayerInfo var6) {
      int ☃ = ☃.func_96682_a().func_96529_a(☃, ☃).func_96652_c();
      if (☃.func_199865_f() == ScoreCriteria.RenderType.HEARTS) {
         this.field_175250_f.func_110434_K().func_110577_a(field_110324_m);
         long ☃x = Util.func_211177_b();
         if (this.field_175253_j == ☃.func_178855_p()) {
            if (☃ < ☃.func_178835_l()) {
               ☃.func_178846_a(☃x);
               ☃.func_178844_b((long)(this.field_175251_g.func_73834_c() + 20));
            } else if (☃ > ☃.func_178835_l()) {
               ☃.func_178846_a(☃x);
               ☃.func_178844_b((long)(this.field_175251_g.func_73834_c() + 10));
            }
         }

         if (☃x - ☃.func_178847_n() > 1000L || this.field_175253_j != ☃.func_178855_p()) {
            ☃.func_178836_b(☃);
            ☃.func_178857_c(☃);
            ☃.func_178846_a(☃x);
         }

         ☃.func_178843_c(this.field_175253_j);
         ☃.func_178836_b(☃);
         int ☃x = MathHelper.func_76123_f((float)Math.max(☃, ☃.func_178860_m()) / 2.0F);
         int ☃xx = Math.max(MathHelper.func_76123_f((float)(☃ / 2)), Math.max(MathHelper.func_76123_f((float)(☃.func_178860_m() / 2)), 10));
         boolean ☃xxx = ☃.func_178858_o() > (long)this.field_175251_g.func_73834_c()
            && (☃.func_178858_o() - (long)this.field_175251_g.func_73834_c()) / 3L % 2L == 1L;
         if (☃x > 0) {
            float ☃xxxx = Math.min((float)(☃ - ☃ - 4) / (float)☃xx, 9.0F);
            if (☃xxxx > 3.0F) {
               for(int ☃xxxxx = ☃x; ☃xxxxx < ☃xx; ++☃xxxxx) {
                  this.func_175174_a((float)☃ + (float)☃xxxxx * ☃xxxx, (float)☃, ☃xxx ? 25 : 16, 0, 9, 9);
               }

               for(int ☃xxxxx = 0; ☃xxxxx < ☃x; ++☃xxxxx) {
                  this.func_175174_a((float)☃ + (float)☃xxxxx * ☃xxxx, (float)☃, ☃xxx ? 25 : 16, 0, 9, 9);
                  if (☃xxx) {
                     if (☃xxxxx * 2 + 1 < ☃.func_178860_m()) {
                        this.func_175174_a((float)☃ + (float)☃xxxxx * ☃xxxx, (float)☃, 70, 0, 9, 9);
                     }

                     if (☃xxxxx * 2 + 1 == ☃.func_178860_m()) {
                        this.func_175174_a((float)☃ + (float)☃xxxxx * ☃xxxx, (float)☃, 79, 0, 9, 9);
                     }
                  }

                  if (☃xxxxx * 2 + 1 < ☃) {
                     this.func_175174_a((float)☃ + (float)☃xxxxx * ☃xxxx, (float)☃, ☃xxxxx >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (☃xxxxx * 2 + 1 == ☃) {
                     this.func_175174_a((float)☃ + (float)☃xxxxx * ☃xxxx, (float)☃, ☃xxxxx >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float ☃xxxx = MathHelper.func_76131_a((float)☃ / 20.0F, 0.0F, 1.0F);
               int ☃xxxxx = (int)((1.0F - ☃xxxx) * 255.0F) << 16 | (int)(☃xxxx * 255.0F) << 8;
               String ☃xxxxxx = "" + (float)☃ / 2.0F;
               if (☃ - this.field_175250_f.field_71466_p.func_78256_a(☃xxxxxx + "hp") >= ☃) {
                  ☃xxxxxx = ☃xxxxxx + "hp";
               }

               this.field_175250_f
                  .field_71466_p
                  .func_175063_a(☃xxxxxx, (float)((☃ + ☃) / 2 - this.field_175250_f.field_71466_p.func_78256_a(☃xxxxxx) / 2), (float)☃, ☃xxxxx);
            }
         }
      } else {
         String ☃ = TextFormatting.YELLOW + "" + ☃;
         this.field_175250_f.field_71466_p.func_175063_a(☃, (float)(☃ - this.field_175250_f.field_71466_p.func_78256_a(☃)), (float)☃, 16777215);
      }
   }

   public void func_175248_a(@Nullable ITextComponent var1) {
      this.field_175255_h = ☃;
   }

   public void func_175244_b(@Nullable ITextComponent var1) {
      this.field_175256_i = ☃;
   }

   public void func_181030_a() {
      this.field_175256_i = null;
      this.field_175255_h = null;
   }

   static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
      private PlayerComparator() {
      }

      public int compare(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
         ScorePlayerTeam ☃ = ☃.func_178850_i();
         ScorePlayerTeam ☃x = ☃.func_178850_i();
         return ComparisonChain.start()
            .compareTrueFirst(☃.func_178848_b() != GameType.SPECTATOR, ☃.func_178848_b() != GameType.SPECTATOR)
            .compare(☃ != null ? ☃.func_96661_b() : "", ☃x != null ? ☃x.func_96661_b() : "")
            .compare(☃.func_178845_a().getName(), ☃.func_178845_a().getName(), String::compareToIgnoreCase)
            .result();
      }
   }
}
