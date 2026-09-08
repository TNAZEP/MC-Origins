package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.IChatListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.chat.NormalChatListener;
import net.minecraft.client.gui.chat.OverlayChatListener;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;
import net.minecraft.world.border.WorldBorder;

public class GuiIngame extends Gui {
   private static final ResourceLocation field_110329_b = new ResourceLocation("textures/misc/vignette.png");
   private static final ResourceLocation field_110330_c = new ResourceLocation("textures/gui/widgets.png");
   private static final ResourceLocation field_110328_d = new ResourceLocation("textures/misc/pumpkinblur.png");
   private final Random field_73842_c = new Random();
   private final Minecraft field_73839_d;
   private final ItemRenderer field_73841_b;
   private final GuiNewChat field_73840_e;
   private int field_73837_f;
   private String field_73838_g = "";
   private int field_73845_h;
   private boolean field_73844_j;
   public float field_73843_a = 1.0F;
   private int field_92017_k;
   private ItemStack field_92016_l = ItemStack.field_190927_a;
   private final GuiOverlayDebug field_175198_t;
   private final GuiSubtitleOverlay field_184049_t;
   private final GuiSpectator field_175197_u;
   private final GuiPlayerTabOverlay field_175196_v;
   private final GuiBossOverlay field_184050_w;
   private int field_175195_w;
   private String field_175201_x = "";
   private String field_175200_y = "";
   private int field_175199_z;
   private int field_175192_A;
   private int field_175193_B;
   private int field_175194_C;
   private int field_175189_D;
   private long field_175190_E;
   private long field_175191_F;
   private int field_194811_H;
   private int field_194812_I;
   private final Map<ChatType, List<IChatListener>> field_191743_I = Maps.newHashMap();

   public GuiIngame(Minecraft var1) {
      this.field_73839_d = ☃;
      this.field_73841_b = ☃.func_175599_af();
      this.field_175198_t = new GuiOverlayDebug(☃);
      this.field_175197_u = new GuiSpectator(☃);
      this.field_73840_e = new GuiNewChat(☃);
      this.field_175196_v = new GuiPlayerTabOverlay(☃, this);
      this.field_184050_w = new GuiBossOverlay(☃);
      this.field_184049_t = new GuiSubtitleOverlay(☃);

      for(ChatType ☃ : ChatType.values()) {
         this.field_191743_I.put(☃, Lists.newArrayList());
      }

      IChatListener ☃ = NarratorChatListener.field_193643_a;
      ((List)this.field_191743_I.get(ChatType.CHAT)).add(new NormalChatListener(☃));
      ((List)this.field_191743_I.get(ChatType.CHAT)).add(☃);
      ((List)this.field_191743_I.get(ChatType.SYSTEM)).add(new NormalChatListener(☃));
      ((List)this.field_191743_I.get(ChatType.SYSTEM)).add(☃);
      ((List)this.field_191743_I.get(ChatType.GAME_INFO)).add(new OverlayChatListener(☃));
      this.func_175177_a();
   }

   public void func_175177_a() {
      this.field_175199_z = 10;
      this.field_175192_A = 70;
      this.field_175193_B = 20;
   }

   public void func_175180_a(float var1) {
      this.field_194811_H = this.field_73839_d.field_195558_d.func_198107_o();
      this.field_194812_I = this.field_73839_d.field_195558_d.func_198087_p();
      FontRenderer ☃ = this.func_175179_f();
      GlStateManager.func_179147_l();
      if (Minecraft.func_71375_t()) {
         this.func_212303_b(this.field_73839_d.func_175606_aa());
      } else {
         GlStateManager.func_179126_j();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
      }

      ItemStack ☃ = this.field_73839_d.field_71439_g.field_71071_by.func_70440_f(3);
      if (this.field_73839_d.field_71474_y.field_74320_O == 0 && ☃.func_77973_b() == Blocks.field_196625_cS.func_199767_j()) {
         this.func_194808_p();
      }

      if (!this.field_73839_d.field_71439_g.func_70644_a(MobEffects.field_76431_k)) {
         float ☃ = this.field_73839_d.field_71439_g.field_71080_cy
            + (this.field_73839_d.field_71439_g.field_71086_bY - this.field_73839_d.field_71439_g.field_71080_cy) * ☃;
         if (☃ > 0.0F) {
            this.func_194805_e(☃);
         }
      }

      if (this.field_73839_d.field_71442_b.func_178889_l() == GameType.SPECTATOR) {
         this.field_175197_u.func_195622_a(☃);
      } else if (!this.field_73839_d.field_71474_y.field_74319_N) {
         this.func_194806_b(☃);
      }

      if (!this.field_73839_d.field_71474_y.field_74319_N) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73839_d.func_110434_K().func_110577_a(field_110324_m);
         GlStateManager.func_179147_l();
         GlStateManager.func_179141_d();
         this.func_194798_c(☃);
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         this.field_73839_d.field_71424_I.func_76320_a("bossHealth");
         this.field_184050_w.func_184051_a();
         this.field_73839_d.field_71424_I.func_76319_b();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73839_d.func_110434_K().func_110577_a(field_110324_m);
         if (this.field_73839_d.field_71442_b.func_78755_b()) {
            this.func_194807_n();
         }

         this.func_194799_o();
         GlStateManager.func_179084_k();
         int ☃ = this.field_194811_H / 2 - 91;
         if (this.field_73839_d.field_71439_g.func_110317_t()) {
            this.func_194803_a(☃);
         } else if (this.field_73839_d.field_71442_b.func_78763_f()) {
            this.func_194804_b(☃);
         }

         if (this.field_73839_d.field_71474_y.field_92117_D && this.field_73839_d.field_71442_b.func_178889_l() != GameType.SPECTATOR) {
            this.func_194801_c();
         } else if (this.field_73839_d.field_71439_g.func_175149_v()) {
            this.field_175197_u.func_195623_a();
         }
      }

      if (this.field_73839_d.field_71439_g.func_71060_bI() > 0) {
         this.field_73839_d.field_71424_I.func_76320_a("sleep");
         GlStateManager.func_179097_i();
         GlStateManager.func_179118_c();
         float ☃ = (float)this.field_73839_d.field_71439_g.func_71060_bI();
         float ☃x = ☃ / 100.0F;
         if (☃x > 1.0F) {
            ☃x = 1.0F - (☃ - 100.0F) / 10.0F;
         }

         int ☃ = (int)(220.0F * ☃x) << 24 | 1052704;
         func_73734_a(0, 0, this.field_194811_H, this.field_194812_I, ☃);
         GlStateManager.func_179141_d();
         GlStateManager.func_179126_j();
         this.field_73839_d.field_71424_I.func_76319_b();
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      }

      if (this.field_73839_d.func_71355_q()) {
         this.func_194810_d();
      }

      this.func_194809_b();
      if (this.field_73839_d.field_71474_y.field_74330_P) {
         this.field_175198_t.func_194818_a();
      }

      if (!this.field_73839_d.field_71474_y.field_74319_N) {
         if (this.field_73845_h > 0) {
            this.field_73839_d.field_71424_I.func_76320_a("overlayMessage");
            float ☃ = (float)this.field_73845_h - ☃;
            int ☃x = (int)(☃ * 255.0F / 20.0F);
            if (☃x > 255) {
               ☃x = 255;
            }

            if (☃x > 8) {
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)(this.field_194811_H / 2), (float)(this.field_194812_I - 68), 0.0F);
               GlStateManager.func_179147_l();
               GlStateManager.func_187428_a(
                  GlStateManager.SourceFactor.SRC_ALPHA,
                  GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                  GlStateManager.SourceFactor.ONE,
                  GlStateManager.DestFactor.ZERO
               );
               int ☃ = 16777215;
               if (this.field_73844_j) {
                  ☃ = MathHelper.func_181758_c(☃ / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               ☃.func_211126_b(this.field_73838_g, (float)(-☃.func_78256_a(this.field_73838_g) / 2), -4.0F, ☃ + (☃x << 24 & 0xFF000000));
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
            }

            this.field_73839_d.field_71424_I.func_76319_b();
         }

         if (this.field_175195_w > 0) {
            this.field_73839_d.field_71424_I.func_76320_a("titleAndSubtitle");
            float ☃ = (float)this.field_175195_w - ☃;
            int ☃x = 255;
            if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
               float ☃xx = (float)(this.field_175199_z + this.field_175192_A + this.field_175193_B) - ☃;
               ☃x = (int)(☃xx * 255.0F / (float)this.field_175199_z);
            }

            if (this.field_175195_w <= this.field_175193_B) {
               ☃x = (int)(☃ * 255.0F / (float)this.field_175193_B);
            }

            ☃x = MathHelper.func_76125_a(☃x, 0, 255);
            if (☃x > 8) {
               GlStateManager.func_179094_E();
               GlStateManager.func_179109_b((float)(this.field_194811_H / 2), (float)(this.field_194812_I / 2), 0.0F);
               GlStateManager.func_179147_l();
               GlStateManager.func_187428_a(
                  GlStateManager.SourceFactor.SRC_ALPHA,
                  GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                  GlStateManager.SourceFactor.ONE,
                  GlStateManager.DestFactor.ZERO
               );
               GlStateManager.func_179094_E();
               GlStateManager.func_179152_a(4.0F, 4.0F, 4.0F);
               int ☃ = ☃x << 24 & 0xFF000000;
               ☃.func_175063_a(this.field_175201_x, (float)(-☃.func_78256_a(this.field_175201_x) / 2), -10.0F, 16777215 | ☃);
               GlStateManager.func_179121_F();
               GlStateManager.func_179094_E();
               GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
               ☃.func_175063_a(this.field_175200_y, (float)(-☃.func_78256_a(this.field_175200_y) / 2), 5.0F, 16777215 | ☃);
               GlStateManager.func_179121_F();
               GlStateManager.func_179084_k();
               GlStateManager.func_179121_F();
            }

            this.field_73839_d.field_71424_I.func_76319_b();
         }

         this.field_184049_t.func_195620_a();
         Scoreboard ☃ = this.field_73839_d.field_71441_e.func_96441_U();
         ScoreObjective ☃x = null;
         ScorePlayerTeam ☃xx = ☃.func_96509_i(this.field_73839_d.field_71439_g.func_195047_I_());
         if (☃xx != null) {
            int ☃xxx = ☃xx.func_178775_l().func_175746_b();
            if (☃xxx >= 0) {
               ☃x = ☃.func_96539_a(3 + ☃xxx);
            }
         }

         ScoreObjective ☃ = ☃x != null ? ☃x : ☃.func_96539_a(1);
         if (☃ != null) {
            this.func_194802_a(☃);
         }

         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         GlStateManager.func_179118_c();
         GlStateManager.func_179094_E();
         GlStateManager.func_179109_b(0.0F, (float)(this.field_194812_I - 48), 0.0F);
         this.field_73839_d.field_71424_I.func_76320_a("chat");
         this.field_73840_e.func_146230_a(this.field_73837_f);
         this.field_73839_d.field_71424_I.func_76319_b();
         GlStateManager.func_179121_F();
         ☃ = ☃.func_96539_a(0);
         if (!this.field_73839_d.field_71474_y.field_74321_H.func_151470_d()
            || this.field_73839_d.func_71387_A() && this.field_73839_d.field_71439_g.field_71174_a.func_175106_d().size() <= 1 && ☃ == null) {
            this.field_175196_v.func_175246_a(false);
         } else {
            this.field_175196_v.func_175246_a(true);
            this.field_175196_v.func_175249_a(this.field_194811_H, ☃, ☃);
         }
      }

      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179140_f();
      GlStateManager.func_179141_d();
   }

   private void func_194798_c(float var1) {
      GameSettings ☃ = this.field_73839_d.field_71474_y;
      if (☃.field_74320_O == 0) {
         if (this.field_73839_d.field_71442_b.func_178889_l() == GameType.SPECTATOR && this.field_73839_d.field_147125_j == null) {
            RayTraceResult ☃x = this.field_73839_d.field_71476_x;
            if (☃x == null || ☃x.field_72313_a != RayTraceResult.Type.BLOCK) {
               return;
            }

            BlockPos ☃x = ☃x.func_178782_a();
            if (!this.field_73839_d.field_71441_e.func_180495_p(☃x).func_177230_c().func_149716_u()
               || !(this.field_73839_d.field_71441_e.func_175625_s(☃x) instanceof IInventory)) {
               return;
            }
         }

         if (☃.field_74330_P && !☃.field_74319_N && !this.field_73839_d.field_71439_g.func_175140_cp() && !☃.field_178879_v) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179109_b((float)(this.field_194811_H / 2), (float)(this.field_194812_I / 2), this.field_73735_i);
            Entity ☃x = this.field_73839_d.func_175606_aa();
            GlStateManager.func_179114_b(☃x.field_70127_C + (☃x.field_70125_A - ☃x.field_70127_C) * ☃, -1.0F, 0.0F, 0.0F);
            GlStateManager.func_179114_b(☃x.field_70126_B + (☃x.field_70177_z - ☃x.field_70126_B) * ☃, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179152_a(-1.0F, -1.0F, -1.0F);
            OpenGlHelper.func_188785_m(10);
            GlStateManager.func_179121_F();
         } else {
            GlStateManager.func_187428_a(
               GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR,
               GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR,
               GlStateManager.SourceFactor.ONE,
               GlStateManager.DestFactor.ZERO
            );
            int ☃x = 15;
            this.func_175174_a((float)this.field_194811_H / 2.0F - 7.5F, (float)this.field_194812_I / 2.0F - 7.5F, 0, 0, 15, 15);
            if (this.field_73839_d.field_71474_y.field_186716_M == 1) {
               float ☃xx = this.field_73839_d.field_71439_g.func_184825_o(0.0F);
               boolean ☃xxx = false;
               if (this.field_73839_d.field_147125_j != null && this.field_73839_d.field_147125_j instanceof EntityLivingBase && ☃xx >= 1.0F) {
                  ☃xxx = this.field_73839_d.field_71439_g.func_184818_cX() > 5.0F;
                  ☃xxx &= this.field_73839_d.field_147125_j.func_70089_S();
               }

               int ☃xx = this.field_194812_I / 2 - 7 + 16;
               int ☃xxx = this.field_194811_H / 2 - 8;
               if (☃xxx) {
                  this.func_73729_b(☃xxx, ☃xx, 68, 94, 16, 16);
               } else if (☃xx < 1.0F) {
                  int ☃xx = (int)(☃xx * 17.0F);
                  this.func_73729_b(☃xxx, ☃xx, 36, 94, 16, 4);
                  this.func_73729_b(☃xxx, ☃xx, 52, 94, ☃xx, 4);
               }
            }
         }
      }
   }

   protected void func_194809_b() {
      Collection<PotionEffect> ☃ = this.field_73839_d.field_71439_g.func_70651_bq();
      if (!☃.isEmpty()) {
         this.field_73839_d.func_110434_K().func_110577_a(GuiContainer.field_147001_a);
         GlStateManager.func_179147_l();
         int ☃x = 0;
         int ☃xx = 0;

         for(PotionEffect ☃xxx : Ordering.natural().reverse().sortedCopy(☃)) {
            Potion ☃xxxx = ☃xxx.func_188419_a();
            if (☃xxxx.func_76400_d() && ☃xxx.func_205348_f()) {
               int ☃xxxxx = this.field_194811_H;
               int ☃xxxxxx = 1;
               if (this.field_73839_d.func_71355_q()) {
                  ☃xxxxxx += 15;
               }

               int ☃xxxxx = ☃xxxx.func_76392_e();
               if (☃xxxx.func_188408_i()) {
                  ++☃x;
                  ☃xxxxx -= 25 * ☃x;
               } else {
                  ++☃xx;
                  ☃xxxxx -= 25 * ☃xx;
                  ☃xxxxxx += 26;
               }

               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               float ☃xxxxx = 1.0F;
               if (☃xxx.func_82720_e()) {
                  this.func_73729_b(☃xxxxx, ☃xxxxxx, 165, 166, 24, 24);
               } else {
                  this.func_73729_b(☃xxxxx, ☃xxxxxx, 141, 166, 24, 24);
                  if (☃xxx.func_76459_b() <= 200) {
                     int ☃xxxxx = 10 - ☃xxx.func_76459_b() / 20;
                     ☃xxxxx = MathHelper.func_76131_a((float)☃xxx.func_76459_b() / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
                        + MathHelper.func_76134_b((float)☃xxx.func_76459_b() * (float) Math.PI / 5.0F)
                           * MathHelper.func_76131_a((float)☃xxxxx / 10.0F * 0.25F, 0.0F, 0.25F);
                  }
               }

               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, ☃xxxxx);
               int ☃xxxxx = ☃xxxxx % 12;
               int ☃xxxxxx = ☃xxxxx / 12;
               this.func_73729_b(☃xxxxx + 3, ☃xxxxxx + 3, ☃xxxxx * 18, 198 + ☃xxxxxx * 18, 18, 18);
            }
         }
      }
   }

   protected void func_194806_b(float var1) {
      EntityPlayer ☃ = this.func_212304_m();
      if (☃ != null) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_73839_d.func_110434_K().func_110577_a(field_110330_c);
         ItemStack ☃x = ☃.func_184592_cb();
         EnumHandSide ☃xx = ☃.func_184591_cq().func_188468_a();
         int ☃xxx = this.field_194811_H / 2;
         float ☃xxxx = this.field_73735_i;
         int ☃xxxxx = 182;
         int ☃xxxxxx = 91;
         this.field_73735_i = -90.0F;
         this.func_73729_b(☃xxx - 91, this.field_194812_I - 22, 0, 0, 182, 22);
         this.func_73729_b(☃xxx - 91 - 1 + ☃.field_71071_by.field_70461_c * 20, this.field_194812_I - 22 - 1, 0, 22, 24, 22);
         if (!☃x.func_190926_b()) {
            if (☃xx == EnumHandSide.LEFT) {
               this.func_73729_b(☃xxx - 91 - 29, this.field_194812_I - 23, 24, 22, 29, 24);
            } else {
               this.func_73729_b(☃xxx + 91, this.field_194812_I - 23, 53, 22, 29, 24);
            }
         }

         this.field_73735_i = ☃xxxx;
         GlStateManager.func_179091_B();
         GlStateManager.func_179147_l();
         GlStateManager.func_187428_a(
            GlStateManager.SourceFactor.SRC_ALPHA,
            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
            GlStateManager.SourceFactor.ONE,
            GlStateManager.DestFactor.ZERO
         );
         RenderHelper.func_74520_c();

         for(int ☃x = 0; ☃x < 9; ++☃x) {
            int ☃xx = ☃xxx - 90 + ☃x * 20 + 2;
            int ☃xxx = this.field_194812_I - 16 - 3;
            this.func_184044_a(☃xx, ☃xxx, ☃, ☃, ☃.field_71071_by.field_70462_a.get(☃x));
         }

         if (!☃x.func_190926_b()) {
            int ☃x = this.field_194812_I - 16 - 3;
            if (☃xx == EnumHandSide.LEFT) {
               this.func_184044_a(☃xxx - 91 - 26, ☃x, ☃, ☃, ☃x);
            } else {
               this.func_184044_a(☃xxx + 91 + 10, ☃x, ☃, ☃, ☃x);
            }
         }

         if (this.field_73839_d.field_71474_y.field_186716_M == 2) {
            float ☃x = this.field_73839_d.field_71439_g.func_184825_o(0.0F);
            if (☃x < 1.0F) {
               int ☃xx = this.field_194812_I - 20;
               int ☃xxx = ☃xxx + 91 + 6;
               if (☃xx == EnumHandSide.RIGHT) {
                  ☃xxx = ☃xxx - 91 - 22;
               }

               this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
               int ☃xx = (int)(☃x * 19.0F);
               GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
               this.func_73729_b(☃xxx, ☃xx, 0, 94, 18, 18);
               this.func_73729_b(☃xxx, ☃xx + 18 - ☃xx, 18, 112 - ☃xx, 18, ☃xx);
            }
         }

         RenderHelper.func_74518_a();
         GlStateManager.func_179101_C();
         GlStateManager.func_179084_k();
      }
   }

   public void func_194803_a(int var1) {
      this.field_73839_d.field_71424_I.func_76320_a("jumpBar");
      this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
      float ☃ = this.field_73839_d.field_71439_g.func_110319_bJ();
      int ☃x = 182;
      int ☃xx = (int)(☃ * 183.0F);
      int ☃xxx = this.field_194812_I - 32 + 3;
      this.func_73729_b(☃, ☃xxx, 0, 84, 182, 5);
      if (☃xx > 0) {
         this.func_73729_b(☃, ☃xxx, 0, 89, ☃xx, 5);
      }

      this.field_73839_d.field_71424_I.func_76319_b();
   }

   public void func_194804_b(int var1) {
      this.field_73839_d.field_71424_I.func_76320_a("expBar");
      this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
      int ☃ = this.field_73839_d.field_71439_g.func_71050_bK();
      if (☃ > 0) {
         int ☃x = 182;
         int ☃xx = (int)(this.field_73839_d.field_71439_g.field_71106_cc * 183.0F);
         int ☃xxx = this.field_194812_I - 32 + 3;
         this.func_73729_b(☃, ☃xxx, 0, 64, 182, 5);
         if (☃xx > 0) {
            this.func_73729_b(☃, ☃xxx, 0, 69, ☃xx, 5);
         }
      }

      this.field_73839_d.field_71424_I.func_76319_b();
      if (this.field_73839_d.field_71439_g.field_71068_ca > 0) {
         this.field_73839_d.field_71424_I.func_76320_a("expLevel");
         String ☃ = "" + this.field_73839_d.field_71439_g.field_71068_ca;
         int ☃x = (this.field_194811_H - this.func_175179_f().func_78256_a(☃)) / 2;
         int ☃xx = this.field_194812_I - 31 - 4;
         this.func_175179_f().func_211126_b(☃, (float)(☃x + 1), (float)☃xx, 0);
         this.func_175179_f().func_211126_b(☃, (float)(☃x - 1), (float)☃xx, 0);
         this.func_175179_f().func_211126_b(☃, (float)☃x, (float)(☃xx + 1), 0);
         this.func_175179_f().func_211126_b(☃, (float)☃x, (float)(☃xx - 1), 0);
         this.func_175179_f().func_211126_b(☃, (float)☃x, (float)☃xx, 8453920);
         this.field_73839_d.field_71424_I.func_76319_b();
      }
   }

   public void func_194801_c() {
      this.field_73839_d.field_71424_I.func_76320_a("selectedItemName");
      if (this.field_92017_k > 0 && !this.field_92016_l.func_190926_b()) {
         ITextComponent ☃ = new TextComponentString("")
            .func_150257_a(this.field_92016_l.func_200301_q())
            .func_211708_a(this.field_92016_l.func_77953_t().field_77937_e);
         if (this.field_92016_l.func_82837_s()) {
            ☃.func_211708_a(TextFormatting.ITALIC);
         }

         String ☃ = ☃.func_150254_d();
         int ☃x = (this.field_194811_H - this.func_175179_f().func_78256_a(☃)) / 2;
         int ☃xx = this.field_194812_I - 59;
         if (!this.field_73839_d.field_71442_b.func_78755_b()) {
            ☃xx += 14;
         }

         int ☃ = (int)((float)this.field_92017_k * 256.0F / 10.0F);
         if (☃ > 255) {
            ☃ = 255;
         }

         if (☃ > 0) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(
               GlStateManager.SourceFactor.SRC_ALPHA,
               GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
               GlStateManager.SourceFactor.ONE,
               GlStateManager.DestFactor.ZERO
            );
            this.func_175179_f().func_175063_a(☃, (float)☃x, (float)☃xx, 16777215 + (☃ << 24));
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
         }
      }

      this.field_73839_d.field_71424_I.func_76319_b();
   }

   public void func_194810_d() {
      this.field_73839_d.field_71424_I.func_76320_a("demo");
      String ☃;
      if (this.field_73839_d.field_71441_e.func_82737_E() >= 120500L) {
         ☃ = I18n.func_135052_a("demo.demoExpired");
      } else {
         ☃ = I18n.func_135052_a("demo.remainingTime", StringUtils.func_76337_a((int)(120500L - this.field_73839_d.field_71441_e.func_82737_E())));
      }

      int ☃ = this.func_175179_f().func_78256_a(☃);
      this.func_175179_f().func_175063_a(☃, (float)(this.field_194811_H - ☃ - 10), 5.0F, 16777215);
      this.field_73839_d.field_71424_I.func_76319_b();
   }

   private void func_194802_a(ScoreObjective var1) {
      Scoreboard ☃ = ☃.func_96682_a();
      Collection<Score> ☃x = ☃.func_96534_i(☃);
      List<Score> ☃xx = (List)☃x.stream().filter(var0 -> var0.func_96653_e() != null && !var0.func_96653_e().startsWith("#")).collect(Collectors.toList());
      if (☃xx.size() > 15) {
         ☃x = Lists.<Score>newArrayList(Iterables.skip(☃xx, ☃x.size() - 15));
      } else {
         ☃x = ☃xx;
      }

      String ☃ = ☃.func_96678_d().func_150254_d();
      int ☃x = this.func_175179_f().func_78256_a(☃);
      int ☃xx = ☃x;

      for(Score ☃xxx : ☃x) {
         ScorePlayerTeam ☃xxxx = ☃.func_96509_i(☃xxx.func_96653_e());
         String ☃xxxxx = ScorePlayerTeam.func_200541_a(☃xxxx, new TextComponentString(☃xxx.func_96653_e())).func_150254_d()
            + ": "
            + TextFormatting.RED
            + ☃xxx.func_96652_c();
         ☃xx = Math.max(☃xx, this.func_175179_f().func_78256_a(☃xxxxx));
      }

      int ☃xxx = ☃x.size() * this.func_175179_f().field_78288_b;
      int ☃xxxx = this.field_194812_I / 2 + ☃xxx / 3;
      int ☃xxxxx = 3;
      int ☃xxxxxx = this.field_194811_H - ☃xx - 3;
      int ☃xxxxxxx = 0;

      for(Score ☃xxxxxxxx : ☃x) {
         ++☃xxxxxxx;
         ScorePlayerTeam ☃xxxxxxxxx = ☃.func_96509_i(☃xxxxxxxx.func_96653_e());
         String ☃xxxxxxxxxx = ScorePlayerTeam.func_200541_a(☃xxxxxxxxx, new TextComponentString(☃xxxxxxxx.func_96653_e())).func_150254_d();
         String ☃xxxxxxxxxxx = TextFormatting.RED + "" + ☃xxxxxxxx.func_96652_c();
         int ☃xxxxxxxxxxxx = ☃xxxx - ☃xxxxxxx * this.func_175179_f().field_78288_b;
         int ☃xxxxxxxxxxxxx = this.field_194811_H - 3 + 2;
         func_73734_a(☃xxxxxx - 2, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxx + this.func_175179_f().field_78288_b, 1342177280);
         this.func_175179_f().func_211126_b(☃xxxxxxxxxx, (float)☃xxxxxx, (float)☃xxxxxxxxxxxx, 553648127);
         this.func_175179_f()
            .func_211126_b(☃xxxxxxxxxxx, (float)(☃xxxxxxxxxxxxx - this.func_175179_f().func_78256_a(☃xxxxxxxxxxx)), (float)☃xxxxxxxxxxxx, 553648127);
         if (☃xxxxxxx == ☃x.size()) {
            func_73734_a(☃xxxxxx - 2, ☃xxxxxxxxxxxx - this.func_175179_f().field_78288_b - 1, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxx - 1, 1610612736);
            func_73734_a(☃xxxxxx - 2, ☃xxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxx, 1342177280);
            this.func_175179_f().func_211126_b(☃, (float)(☃xxxxxx + ☃xx / 2 - ☃x / 2), (float)(☃xxxxxxxxxxxx - this.func_175179_f().field_78288_b), 553648127);
         }
      }
   }

   private EntityPlayer func_212304_m() {
      return !(this.field_73839_d.func_175606_aa() instanceof EntityPlayer) ? null : (EntityPlayer)this.field_73839_d.func_175606_aa();
   }

   private EntityLivingBase func_212305_n() {
      EntityPlayer ☃ = this.func_212304_m();
      if (☃ != null) {
         Entity ☃x = ☃.func_184187_bx();
         if (☃x == null) {
            return null;
         }

         if (☃x instanceof EntityLivingBase) {
            return (EntityLivingBase)☃x;
         }
      }

      return null;
   }

   private int func_212306_a(EntityLivingBase var1) {
      if (☃ != null && ☃.func_203003_aK()) {
         float ☃ = ☃.func_110138_aP();
         int ☃x = (int)(☃ + 0.5F) / 2;
         if (☃x > 30) {
            ☃x = 30;
         }

         return ☃x;
      } else {
         return 0;
      }
   }

   private int func_212302_c(int var1) {
      return (int)Math.ceil((double)☃ / 10.0);
   }

   private void func_194807_n() {
      EntityPlayer ☃ = this.func_212304_m();
      if (☃ != null) {
         int ☃x = MathHelper.func_76123_f(☃.func_110143_aJ());
         boolean ☃xx = this.field_175191_F > (long)this.field_73837_f && (this.field_175191_F - (long)this.field_73837_f) / 3L % 2L == 1L;
         long ☃xxx = Util.func_211177_b();
         if (☃x < this.field_175194_C && ☃.field_70172_ad > 0) {
            this.field_175190_E = ☃xxx;
            this.field_175191_F = (long)(this.field_73837_f + 20);
         } else if (☃x > this.field_175194_C && ☃.field_70172_ad > 0) {
            this.field_175190_E = ☃xxx;
            this.field_175191_F = (long)(this.field_73837_f + 10);
         }

         if (☃xxx - this.field_175190_E > 1000L) {
            this.field_175194_C = ☃x;
            this.field_175189_D = ☃x;
            this.field_175190_E = ☃xxx;
         }

         this.field_175194_C = ☃x;
         int ☃x = this.field_175189_D;
         this.field_73842_c.setSeed((long)(this.field_73837_f * 312871));
         FoodStats ☃xx = ☃.func_71024_bL();
         int ☃xxx = ☃xx.func_75116_a();
         IAttributeInstance ☃xxxx = ☃.func_110148_a(SharedMonsterAttributes.field_111267_a);
         int ☃xxxxx = this.field_194811_H / 2 - 91;
         int ☃xxxxxx = this.field_194811_H / 2 + 91;
         int ☃xxxxxxx = this.field_194812_I - 39;
         float ☃xxxxxxxx = (float)☃xxxx.func_111126_e();
         int ☃xxxxxxxxx = MathHelper.func_76123_f(☃.func_110139_bj());
         int ☃xxxxxxxxxx = MathHelper.func_76123_f((☃xxxxxxxx + (float)☃xxxxxxxxx) / 2.0F / 10.0F);
         int ☃xxxxxxxxxxx = Math.max(10 - (☃xxxxxxxxxx - 2), 3);
         int ☃xxxxxxxxxxxx = ☃xxxxxxx - (☃xxxxxxxxxx - 1) * ☃xxxxxxxxxxx - 10;
         int ☃xxxxxxxxxxxxx = ☃xxxxxxx - 10;
         int ☃xxxxxxxxxxxxxx = ☃xxxxxxxxx;
         int ☃xxxxxxxxxxxxxxx = ☃.func_70658_aO();
         int ☃xxxxxxxxxxxxxxxx = -1;
         if (☃.func_70644_a(MobEffects.field_76428_l)) {
            ☃xxxxxxxxxxxxxxxx = this.field_73837_f % MathHelper.func_76123_f(☃xxxxxxxx + 5.0F);
         }

         this.field_73839_d.field_71424_I.func_76320_a("armor");

         for(int ☃x = 0; ☃x < 10; ++☃x) {
            if (☃xxxxxxxxxxxxxxx > 0) {
               int ☃xx = ☃xxxxx + ☃x * 8;
               if (☃x * 2 + 1 < ☃xxxxxxxxxxxxxxx) {
                  this.func_73729_b(☃xx, ☃xxxxxxxxxxxx, 34, 9, 9, 9);
               }

               if (☃x * 2 + 1 == ☃xxxxxxxxxxxxxxx) {
                  this.func_73729_b(☃xx, ☃xxxxxxxxxxxx, 25, 9, 9, 9);
               }

               if (☃x * 2 + 1 > ☃xxxxxxxxxxxxxxx) {
                  this.func_73729_b(☃xx, ☃xxxxxxxxxxxx, 16, 9, 9, 9);
               }
            }
         }

         this.field_73839_d.field_71424_I.func_76318_c("health");

         for(int ☃x = MathHelper.func_76123_f((☃xxxxxxxx + (float)☃xxxxxxxxx) / 2.0F) - 1; ☃x >= 0; --☃x) {
            int ☃xx = 16;
            if (☃.func_70644_a(MobEffects.field_76436_u)) {
               ☃xx += 36;
            } else if (☃.func_70644_a(MobEffects.field_82731_v)) {
               ☃xx += 72;
            }

            int ☃xx = 0;
            if (☃xx) {
               ☃xx = 1;
            }

            int ☃xx = MathHelper.func_76123_f((float)(☃x + 1) / 10.0F) - 1;
            int ☃xxx = ☃xxxxx + ☃x % 10 * 8;
            int ☃xxxx = ☃xxxxxxx - ☃xx * ☃xxxxxxxxxxx;
            if (☃x <= 4) {
               ☃xxxx += this.field_73842_c.nextInt(2);
            }

            if (☃xxxxxxxxxxxxxx <= 0 && ☃x == ☃xxxxxxxxxxxxxxxx) {
               ☃xxxx -= 2;
            }

            int ☃xx = 0;
            if (☃.field_70170_p.func_72912_H().func_76093_s()) {
               ☃xx = 5;
            }

            this.func_73729_b(☃xxx, ☃xxxx, 16 + ☃xx * 9, 9 * ☃xx, 9, 9);
            if (☃xx) {
               if (☃x * 2 + 1 < ☃x) {
                  this.func_73729_b(☃xxx, ☃xxxx, ☃xx + 54, 9 * ☃xx, 9, 9);
               }

               if (☃x * 2 + 1 == ☃x) {
                  this.func_73729_b(☃xxx, ☃xxxx, ☃xx + 63, 9 * ☃xx, 9, 9);
               }
            }

            if (☃xxxxxxxxxxxxxx > 0) {
               if (☃xxxxxxxxxxxxxx == ☃xxxxxxxxx && ☃xxxxxxxxx % 2 == 1) {
                  this.func_73729_b(☃xxx, ☃xxxx, ☃xx + 153, 9 * ☃xx, 9, 9);
                  --☃xxxxxxxxxxxxxx;
               } else {
                  this.func_73729_b(☃xxx, ☃xxxx, ☃xx + 144, 9 * ☃xx, 9, 9);
                  ☃xxxxxxxxxxxxxx -= 2;
               }
            } else {
               if (☃x * 2 + 1 < ☃x) {
                  this.func_73729_b(☃xxx, ☃xxxx, ☃xx + 36, 9 * ☃xx, 9, 9);
               }

               if (☃x * 2 + 1 == ☃x) {
                  this.func_73729_b(☃xxx, ☃xxxx, ☃xx + 45, 9 * ☃xx, 9, 9);
               }
            }
         }

         EntityLivingBase ☃x = this.func_212305_n();
         int ☃xx = this.func_212306_a(☃x);
         if (☃xx == 0) {
            this.field_73839_d.field_71424_I.func_76318_c("food");

            for(int ☃xxx = 0; ☃xxx < 10; ++☃xxx) {
               int ☃xxxx = ☃xxxxxxx;
               int ☃xxxxx = 16;
               int ☃xxxxxx = 0;
               if (☃.func_70644_a(MobEffects.field_76438_s)) {
                  ☃xxxxx += 36;
                  ☃xxxxxx = 13;
               }

               if (☃.func_71024_bL().func_75115_e() <= 0.0F && this.field_73837_f % (☃xxx * 3 + 1) == 0) {
                  ☃xxxx = ☃xxxxxxx + (this.field_73842_c.nextInt(3) - 1);
               }

               int ☃xxxx = ☃xxxxxx - ☃xxx * 8 - 9;
               this.func_73729_b(☃xxxx, ☃xxxx, 16 + ☃xxxxxx * 9, 27, 9, 9);
               if (☃xxx * 2 + 1 < ☃xxx) {
                  this.func_73729_b(☃xxxx, ☃xxxx, ☃xxxxx + 36, 27, 9, 9);
               }

               if (☃xxx * 2 + 1 == ☃xxx) {
                  this.func_73729_b(☃xxxx, ☃xxxx, ☃xxxxx + 45, 27, 9, 9);
               }
            }

            ☃xxxxxxxxxxxxx -= 10;
         }

         this.field_73839_d.field_71424_I.func_76318_c("air");
         int ☃x = ☃.func_70086_ai();
         int ☃xx = ☃.func_205010_bg();
         if (☃.func_208600_a(FluidTags.field_206959_a) || ☃x < ☃xx) {
            int ☃xxx = this.func_212302_c(☃xx) - 1;
            ☃xxxxxxxxxxxxx -= ☃xxx * 10;
            int ☃xxxx = MathHelper.func_76143_f((double)(☃x - 2) * 10.0 / (double)☃xx);
            int ☃xxxxx = MathHelper.func_76143_f((double)☃x * 10.0 / (double)☃xx) - ☃xxxx;

            for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxx + ☃xxxxx; ++☃xxxxxx) {
               if (☃xxxxxx < ☃xxxx) {
                  this.func_73729_b(☃xxxxxx - ☃xxxxxx * 8 - 9, ☃xxxxxxxxxxxxx, 16, 18, 9, 9);
               } else {
                  this.func_73729_b(☃xxxxxx - ☃xxxxxx * 8 - 9, ☃xxxxxxxxxxxxx, 25, 18, 9, 9);
               }
            }
         }

         this.field_73839_d.field_71424_I.func_76319_b();
      }
   }

   private void func_194799_o() {
      EntityLivingBase ☃ = this.func_212305_n();
      if (☃ != null) {
         int ☃x = this.func_212306_a(☃);
         if (☃x != 0) {
            int ☃xx = (int)Math.ceil((double)☃.func_110143_aJ());
            this.field_73839_d.field_71424_I.func_76318_c("mountHealth");
            int ☃xxx = this.field_194812_I - 39;
            int ☃xxxx = this.field_194811_H / 2 + 91;
            int ☃xxxxx = ☃xxx;
            int ☃xxxxxx = 0;

            for(boolean ☃xxxxxxx = false; ☃x > 0; ☃xxxxxx += 20) {
               int ☃xxxxxxxx = Math.min(☃x, 10);
               ☃x -= ☃xxxxxxxx;

               for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxxxxxx; ++☃xxxxxxxxx) {
                  int ☃xxxxxxxxxx = 52;
                  int ☃xxxxxxxxxxx = 0;
                  int ☃xxxxxxxxxxxx = ☃xxxx - ☃xxxxxxxxx * 8 - 9;
                  this.func_73729_b(☃xxxxxxxxxxxx, ☃xxxxx, 52 + ☃xxxxxxxxxxx * 9, 9, 9, 9);
                  if (☃xxxxxxxxx * 2 + 1 + ☃xxxxxx < ☃xx) {
                     this.func_73729_b(☃xxxxxxxxxxxx, ☃xxxxx, 88, 9, 9, 9);
                  }

                  if (☃xxxxxxxxx * 2 + 1 + ☃xxxxxx == ☃xx) {
                     this.func_73729_b(☃xxxxxxxxxxxx, ☃xxxxx, 97, 9, 9, 9);
                  }
               }

               ☃xxxxx -= 10;
            }
         }
      }
   }

   private void func_194808_p() {
      GlStateManager.func_179097_i();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179118_c();
      this.field_73839_d.func_110434_K().func_110577_a(field_110328_d);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(0.0, (double)this.field_194812_I, -90.0).func_187315_a(0.0, 1.0).func_181675_d();
      ☃x.func_181662_b((double)this.field_194811_H, (double)this.field_194812_I, -90.0).func_187315_a(1.0, 1.0).func_181675_d();
      ☃x.func_181662_b((double)this.field_194811_H, 0.0, -90.0).func_187315_a(1.0, 0.0).func_181675_d();
      ☃x.func_181662_b(0.0, 0.0, -90.0).func_187315_a(0.0, 0.0).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179141_d();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void func_212307_a(Entity var1) {
      if (☃ != null) {
         float ☃ = MathHelper.func_76131_a(1.0F - ☃.func_70013_c(), 0.0F, 1.0F);
         this.field_73843_a = (float)((double)this.field_73843_a + (double)(☃ - this.field_73843_a) * 0.01);
      }
   }

   private void func_212303_b(Entity var1) {
      WorldBorder ☃ = this.field_73839_d.field_71441_e.func_175723_af();
      float ☃x = (float)☃.func_177745_a(☃);
      double ☃xx = Math.min(☃.func_177749_o() * (double)☃.func_177740_p() * 1000.0, Math.abs(☃.func_177751_j() - ☃.func_177741_h()));
      double ☃xxx = Math.max((double)☃.func_177748_q(), ☃xx);
      if ((double)☃x < ☃xxx) {
         ☃x = 1.0F - (float)((double)☃x / ☃xxx);
      } else {
         ☃x = 0.0F;
      }

      GlStateManager.func_179097_i();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      if (☃x > 0.0F) {
         GlStateManager.func_179131_c(0.0F, ☃x, ☃x, 1.0F);
      } else {
         GlStateManager.func_179131_c(this.field_73843_a, this.field_73843_a, this.field_73843_a, 1.0F);
      }

      this.field_73839_d.func_110434_K().func_110577_a(field_110329_b);
      Tessellator ☃ = Tessellator.func_178181_a();
      BufferBuilder ☃x = ☃.func_178180_c();
      ☃x.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃x.func_181662_b(0.0, (double)this.field_194812_I, -90.0).func_187315_a(0.0, 1.0).func_181675_d();
      ☃x.func_181662_b((double)this.field_194811_H, (double)this.field_194812_I, -90.0).func_187315_a(1.0, 1.0).func_181675_d();
      ☃x.func_181662_b((double)this.field_194811_H, 0.0, -90.0).func_187315_a(1.0, 0.0).func_181675_d();
      ☃x.func_181662_b(0.0, 0.0, -90.0).func_187315_a(0.0, 0.0).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
   }

   private void func_194805_e(float var1) {
      if (☃ < 1.0F) {
         ☃ *= ☃;
         ☃ *= ☃;
         ☃ = ☃ * 0.8F + 0.2F;
      }

      GlStateManager.func_179118_c();
      GlStateManager.func_179097_i();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, ☃);
      this.field_73839_d.func_110434_K().func_110577_a(TextureMap.field_110575_b);
      TextureAtlasSprite ☃ = this.field_73839_d.func_175602_ab().func_175023_a().func_178122_a(Blocks.field_150427_aO.func_176223_P());
      float ☃x = ☃.func_94209_e();
      float ☃xx = ☃.func_94206_g();
      float ☃xxx = ☃.func_94212_f();
      float ☃xxxx = ☃.func_94210_h();
      Tessellator ☃xxxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxxx = ☃xxxxx.func_178180_c();
      ☃xxxxxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃xxxxxx.func_181662_b(0.0, (double)this.field_194812_I, -90.0).func_187315_a((double)☃x, (double)☃xxxx).func_181675_d();
      ☃xxxxxx.func_181662_b((double)this.field_194811_H, (double)this.field_194812_I, -90.0).func_187315_a((double)☃xxx, (double)☃xxxx).func_181675_d();
      ☃xxxxxx.func_181662_b((double)this.field_194811_H, 0.0, -90.0).func_187315_a((double)☃xxx, (double)☃xx).func_181675_d();
      ☃xxxxxx.func_181662_b(0.0, 0.0, -90.0).func_187315_a((double)☃x, (double)☃xx).func_181675_d();
      ☃xxxxx.func_78381_a();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179126_j();
      GlStateManager.func_179141_d();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void func_184044_a(int var1, int var2, float var3, EntityPlayer var4, ItemStack var5) {
      if (!☃.func_190926_b()) {
         float ☃ = (float)☃.func_190921_D() - ☃;
         if (☃ > 0.0F) {
            GlStateManager.func_179094_E();
            float ☃x = 1.0F + ☃ / 5.0F;
            GlStateManager.func_179109_b((float)(☃ + 8), (float)(☃ + 12), 0.0F);
            GlStateManager.func_179152_a(1.0F / ☃x, (☃x + 1.0F) / 2.0F, 1.0F);
            GlStateManager.func_179109_b((float)(-(☃ + 8)), (float)(-(☃ + 12)), 0.0F);
         }

         this.field_73841_b.func_184391_a(☃, ☃, ☃, ☃);
         if (☃ > 0.0F) {
            GlStateManager.func_179121_F();
         }

         this.field_73841_b.func_175030_a(this.field_73839_d.field_71466_p, ☃, ☃, ☃);
      }
   }

   public void func_73831_a() {
      if (this.field_73845_h > 0) {
         --this.field_73845_h;
      }

      if (this.field_175195_w > 0) {
         --this.field_175195_w;
         if (this.field_175195_w <= 0) {
            this.field_175201_x = "";
            this.field_175200_y = "";
         }
      }

      ++this.field_73837_f;
      Entity ☃ = this.field_73839_d.func_175606_aa();
      if (☃ != null) {
         this.func_212307_a(☃);
      }

      if (this.field_73839_d.field_71439_g != null) {
         ItemStack ☃ = this.field_73839_d.field_71439_g.field_71071_by.func_70448_g();
         if (☃.func_190926_b()) {
            this.field_92017_k = 0;
         } else if (this.field_92016_l.func_190926_b()
            || ☃.func_77973_b() != this.field_92016_l.func_77973_b()
            || !☃.func_200301_q().equals(this.field_92016_l.func_200301_q())) {
            this.field_92017_k = 40;
         } else if (this.field_92017_k > 0) {
            --this.field_92017_k;
         }

         this.field_92016_l = ☃;
      }
   }

   public void func_73833_a(String var1) {
      this.func_110326_a(I18n.func_135052_a("record.nowPlaying", ☃), true);
   }

   public void func_110326_a(String var1, boolean var2) {
      this.field_73838_g = ☃;
      this.field_73845_h = 60;
      this.field_73844_j = ☃;
   }

   public void func_175178_a(String var1, String var2, int var3, int var4, int var5) {
      if (☃ == null && ☃ == null && ☃ < 0 && ☃ < 0 && ☃ < 0) {
         this.field_175201_x = "";
         this.field_175200_y = "";
         this.field_175195_w = 0;
      } else if (☃ != null) {
         this.field_175201_x = ☃;
         this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
      } else if (☃ != null) {
         this.field_175200_y = ☃;
      } else {
         if (☃ >= 0) {
            this.field_175199_z = ☃;
         }

         if (☃ >= 0) {
            this.field_175192_A = ☃;
         }

         if (☃ >= 0) {
            this.field_175193_B = ☃;
         }

         if (this.field_175195_w > 0) {
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
         }
      }
   }

   public void func_175188_a(ITextComponent var1, boolean var2) {
      this.func_110326_a(☃.getString(), ☃);
   }

   public void func_191742_a(ChatType var1, ITextComponent var2) {
      for(IChatListener ☃ : (List)this.field_191743_I.get(☃)) {
         ☃.func_192576_a(☃, ☃);
      }
   }

   public GuiNewChat func_146158_b() {
      return this.field_73840_e;
   }

   public int func_73834_c() {
      return this.field_73837_f;
   }

   public FontRenderer func_175179_f() {
      return this.field_73839_d.field_71466_p;
   }

   public GuiSpectator func_175187_g() {
      return this.field_175197_u;
   }

   public GuiPlayerTabOverlay func_175181_h() {
      return this.field_175196_v;
   }

   public void func_181029_i() {
      this.field_175196_v.func_181030_a();
      this.field_184050_w.func_184057_b();
      this.field_73839_d.func_193033_an().func_191788_b();
   }

   public GuiBossOverlay func_184046_j() {
      return this.field_184050_w;
   }
}
