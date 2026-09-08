package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IGuiEventListenerDeferred;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatType;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class GuiStats extends GuiScreen implements IProgressMeter {
   protected GuiScreen field_146549_a;
   protected String field_146542_f = "Select world";
   private GuiStats.StatsGeneral field_146550_h;
   private GuiStats.StatsItem field_146551_i;
   private GuiStats.StatsMobsList field_146547_s;
   private final StatisticsManager field_146546_t;
   private GuiSlot field_146545_u;
   private boolean field_146543_v = true;

   public GuiStats(GuiScreen var1, StatisticsManager var2) {
      this.field_146549_a = ☃;
      this.field_146546_t = ☃;
   }

   @Override
   public IGuiEventListener getFocused() {
      return this.field_146545_u;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146542_f = I18n.func_135052_a("gui.stats");
      this.field_146543_v = true;
      this.field_146297_k.func_147114_u().func_147297_a(new CPacketClientStatus(CPacketClientStatus.State.REQUEST_STATS));
   }

   public void func_193028_a() {
      this.field_146550_h = new GuiStats.StatsGeneral(this.field_146297_k);
      this.field_146551_i = new GuiStats.StatsItem(this.field_146297_k);
      this.field_146547_s = new GuiStats.StatsMobsList(this.field_146297_k);
   }

   public void func_193029_f() {
      this.func_189646_b(new GuiButton(0, this.field_146294_l / 2 - 100, this.field_146295_m - 28, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiStats.this.field_146297_k.func_147108_a(GuiStats.this.field_146549_a);
         }
      });
      this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 - 120, this.field_146295_m - 52, 80, 20, I18n.func_135052_a("stat.generalButton")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiStats.this.field_146545_u = GuiStats.this.field_146550_h;
         }
      });
      GuiButton ☃ = this.func_189646_b(
         new GuiButton(3, this.field_146294_l / 2 - 40, this.field_146295_m - 52, 80, 20, I18n.func_135052_a("stat.itemsButton")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiStats.this.field_146545_u = GuiStats.this.field_146551_i;
            }
         }
      );
      GuiButton ☃x = this.func_189646_b(
         new GuiButton(4, this.field_146294_l / 2 + 40, this.field_146295_m - 52, 80, 20, I18n.func_135052_a("stat.mobsButton")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiStats.this.field_146545_u = GuiStats.this.field_146547_s;
            }
         }
      );
      if (this.field_146551_i.func_148127_b() == 0) {
         ☃.field_146124_l = false;
      }

      if (this.field_146547_s.func_148127_b() == 0) {
         ☃x.field_146124_l = false;
      }

      this.field_195124_j.add((IGuiEventListenerDeferred)() -> this.field_146545_u);
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      if (this.field_146543_v) {
         this.func_146276_q_();
         this.func_73732_a(this.field_146289_q, I18n.func_135052_a("multiplayer.downloadingStats"), this.field_146294_l / 2, this.field_146295_m / 2, 16777215);
         this.func_73732_a(
            this.field_146289_q,
            field_146510_b_[(int)(Util.func_211177_b() / 150L % (long)field_146510_b_.length)],
            this.field_146294_l / 2,
            this.field_146295_m / 2 + this.field_146289_q.field_78288_b * 2,
            16777215
         );
      } else {
         this.field_146545_u.func_148128_a(☃, ☃, ☃);
         this.func_73732_a(this.field_146289_q, this.field_146542_f, this.field_146294_l / 2, 20, 16777215);
         super.func_73863_a(☃, ☃, ☃);
      }
   }

   @Override
   public void func_193026_g() {
      if (this.field_146543_v) {
         this.func_193028_a();
         this.func_193029_f();
         this.field_146545_u = this.field_146550_h;
         this.field_146543_v = false;
      }
   }

   @Override
   public boolean func_73868_f() {
      return !this.field_146543_v;
   }

   private int func_195224_b(int var1) {
      return 115 + 40 * ☃;
   }

   private void func_146521_a(int var1, int var2, Item var3) {
      this.func_146531_b(☃ + 1, ☃ + 1);
      GlStateManager.func_179091_B();
      RenderHelper.func_74520_c();
      this.field_146296_j.func_175042_a(☃.func_190903_i(), ☃ + 2, ☃ + 2);
      RenderHelper.func_74518_a();
      GlStateManager.func_179101_C();
   }

   private void func_146531_b(int var1, int var2) {
      this.func_146527_c(☃, ☃, 0, 0);
   }

   private void func_146527_c(int var1, int var2, int var3, int var4) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_110323_l);
      float ☃ = 0.0078125F;
      float ☃x = 0.0078125F;
      int ☃xx = 18;
      int ☃xxx = 18;
      Tessellator ☃xxxx = Tessellator.func_178181_a();
      BufferBuilder ☃xxxxx = ☃xxxx.func_178180_c();
      ☃xxxxx.func_181668_a(7, DefaultVertexFormats.field_181707_g);
      ☃xxxxx.func_181662_b((double)(☃ + 0), (double)(☃ + 18), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 0) * 0.0078125F), (double)((float)(☃ + 18) * 0.0078125F))
         .func_181675_d();
      ☃xxxxx.func_181662_b((double)(☃ + 18), (double)(☃ + 18), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 18) * 0.0078125F), (double)((float)(☃ + 18) * 0.0078125F))
         .func_181675_d();
      ☃xxxxx.func_181662_b((double)(☃ + 18), (double)(☃ + 0), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 18) * 0.0078125F), (double)((float)(☃ + 0) * 0.0078125F))
         .func_181675_d();
      ☃xxxxx.func_181662_b((double)(☃ + 0), (double)(☃ + 0), (double)this.field_73735_i)
         .func_187315_a((double)((float)(☃ + 0) * 0.0078125F), (double)((float)(☃ + 0) * 0.0078125F))
         .func_181675_d();
      ☃xxxx.func_78381_a();
   }

   class StatsGeneral extends GuiSlot {
      private Iterator<Stat<ResourceLocation>> field_195102_w;

      public StatsGeneral(Minecraft var2) {
         super(☃, GuiStats.this.field_146294_l, GuiStats.this.field_146295_m, 32, GuiStats.this.field_146295_m - 64, 10);
         this.func_193651_b(false);
      }

      @Override
      protected int func_148127_b() {
         return StatList.field_199092_j.func_199081_b();
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return false;
      }

      @Override
      protected int func_148138_e() {
         return this.func_148127_b() * 10;
      }

      @Override
      protected void func_148123_a() {
         GuiStats.this.func_146276_q_();
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         if (☃ == 0) {
            this.field_195102_w = StatList.field_199092_j.iterator();
         }

         Stat<ResourceLocation> ☃ = (Stat)this.field_195102_w.next();
         ITextComponent ☃x = new TextComponentTranslation("stat." + ☃.func_197920_b().toString().replace(':', '.')).func_211708_a(TextFormatting.GRAY);
         this.func_73731_b(GuiStats.this.field_146289_q, ☃x.getString(), ☃ + 2, ☃ + 1, ☃ % 2 == 0 ? 16777215 : 9474192);
         String ☃xx = ☃.func_75968_a(GuiStats.this.field_146546_t.func_77444_a(☃));
         this.func_73731_b(
            GuiStats.this.field_146289_q, ☃xx, ☃ + 2 + 213 - GuiStats.this.field_146289_q.func_78256_a(☃xx), ☃ + 1, ☃ % 2 == 0 ? 16777215 : 9474192
         );
      }
   }

   class StatsItem extends GuiSlot {
      protected final List<StatType<Block>> field_195113_v;
      protected final List<StatType<Item>> field_195114_w;
      private final int[] field_195112_D = new int[]{3, 4, 1, 2, 5, 6};
      protected int field_195115_x = -1;
      protected final List<Item> field_195116_y;
      protected final java.util.Comparator<Item> field_195117_z = new GuiStats.StatsItem.Comparator();
      @Nullable
      protected StatType<?> field_195110_A;
      protected int field_195111_B;

      public StatsItem(Minecraft var2) {
         super(☃, GuiStats.this.field_146294_l, GuiStats.this.field_146295_m, 32, GuiStats.this.field_146295_m - 64, 20);
         this.field_195113_v = Lists.<StatType<Block>>newArrayList();
         this.field_195113_v.add(StatList.field_188065_ae);
         this.field_195114_w = Lists.<StatType<Item>>newArrayList(
            StatList.field_199088_e, StatList.field_188066_af, StatList.field_75929_E, StatList.field_199089_f, StatList.field_188068_aj
         );
         this.func_193651_b(false);
         this.func_148133_a(true, 20);
         Set<Item> ☃ = Sets.newIdentityHashSet();

         for(Item ☃x : IRegistry.field_212630_s) {
            boolean ☃xx = false;

            for(StatType<Item> ☃xxx : this.field_195114_w) {
               if (☃xxx.func_199079_a(☃x) && GuiStats.this.field_146546_t.func_77444_a(☃xxx.func_199076_b(☃x)) > 0) {
                  ☃xx = true;
               }
            }

            if (☃xx) {
               ☃.add(☃x);
            }
         }

         for(Block ☃x : IRegistry.field_212618_g) {
            boolean ☃xx = false;

            for(StatType<Block> ☃xxx : this.field_195113_v) {
               if (☃xxx.func_199079_a(☃x) && GuiStats.this.field_146546_t.func_77444_a(☃xxx.func_199076_b(☃x)) > 0) {
                  ☃xx = true;
               }
            }

            if (☃xx) {
               ☃.add(☃x.func_199767_j());
            }
         }

         ☃.remove(Items.field_190931_a);
         this.field_195116_y = Lists.<Item>newArrayList(☃);
      }

      @Override
      protected void func_148129_a(int var1, int var2, Tessellator var3) {
         if (!this.field_148161_k.field_71417_B.func_198030_b()) {
            this.field_195115_x = -1;
         }

         for(int ☃ = 0; ☃ < this.field_195112_D.length; ++☃) {
            GuiStats.this.func_146527_c(☃ + GuiStats.this.func_195224_b(☃) - 18, ☃ + 1, 0, this.field_195115_x == ☃ ? 0 : 18);
         }

         if (this.field_195110_A != null) {
            int ☃ = GuiStats.this.func_195224_b(this.func_195105_b(this.field_195110_A)) - 36;
            int ☃x = this.field_195111_B == 1 ? 2 : 1;
            GuiStats.this.func_146527_c(☃ + ☃, ☃ + 1, 18 * ☃x, 0);
         }

         for(int ☃ = 0; ☃ < this.field_195112_D.length; ++☃) {
            int ☃x = this.field_195115_x != ☃ ? 0 : 1;
            GuiStats.this.func_146527_c(☃ + GuiStats.this.func_195224_b(☃) - 18 + ☃x, ☃ + 1 + ☃x, 18 * this.field_195112_D[☃], 18);
         }
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         Item ☃ = this.func_195106_c(☃);
         GuiStats.this.func_146521_a(☃ + 40, ☃, ☃);

         for(int ☃x = 0; ☃x < this.field_195113_v.size(); ++☃x) {
            Stat<Block> ☃xx;
            if (☃ instanceof ItemBlock) {
               ☃xx = ((StatType)this.field_195113_v.get(☃x)).func_199076_b(((ItemBlock)☃).func_179223_d());
            } else {
               ☃xx = null;
            }

            this.func_195103_a(☃xx, ☃ + GuiStats.this.func_195224_b(☃x), ☃, ☃ % 2 == 0);
         }

         for(int ☃x = 0; ☃x < this.field_195114_w.size(); ++☃x) {
            this.func_195103_a(
               ((StatType)this.field_195114_w.get(☃x)).func_199076_b(☃), ☃ + GuiStats.this.func_195224_b(☃x + this.field_195113_v.size()), ☃, ☃ % 2 == 0
            );
         }
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return false;
      }

      @Override
      public int func_148139_c() {
         return 375;
      }

      @Override
      protected int func_148137_d() {
         return this.field_148155_a / 2 + 140;
      }

      @Override
      protected void func_148123_a() {
         GuiStats.this.func_146276_q_();
      }

      @Override
      protected void func_148132_a(int var1, int var2) {
         this.field_195115_x = -1;

         for(int ☃ = 0; ☃ < this.field_195112_D.length; ++☃) {
            int ☃x = ☃ - GuiStats.this.func_195224_b(☃);
            if (☃x >= -36 && ☃x <= 0) {
               this.field_195115_x = ☃;
               break;
            }
         }

         if (this.field_195115_x >= 0) {
            this.func_195107_a(this.func_195108_d(this.field_195115_x));
            this.field_148161_k.func_147118_V().func_147682_a(SimpleSound.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
         }
      }

      private StatType<?> func_195108_d(int var1) {
         return ☃ < this.field_195113_v.size() ? (StatType)this.field_195113_v.get(☃) : (StatType)this.field_195114_w.get(☃ - this.field_195113_v.size());
      }

      private int func_195105_b(StatType<?> var1) {
         int ☃ = this.field_195113_v.indexOf(☃);
         if (☃ >= 0) {
            return ☃;
         } else {
            int ☃ = this.field_195114_w.indexOf(☃);
            return ☃ >= 0 ? ☃ + this.field_195113_v.size() : -1;
         }
      }

      @Override
      protected final int func_148127_b() {
         return this.field_195116_y.size();
      }

      protected final Item func_195106_c(int var1) {
         return (Item)this.field_195116_y.get(☃);
      }

      protected void func_195103_a(@Nullable Stat<?> var1, int var2, int var3, boolean var4) {
         String ☃ = ☃ == null ? "-" : ☃.func_75968_a(GuiStats.this.field_146546_t.func_77444_a(☃));
         this.func_73731_b(GuiStats.this.field_146289_q, ☃, ☃ - GuiStats.this.field_146289_q.func_78256_a(☃), ☃ + 5, ☃ ? 16777215 : 9474192);
      }

      @Override
      protected void func_148142_b(int var1, int var2) {
         if (☃ >= this.field_148153_b && ☃ <= this.field_148154_c) {
            int ☃ = this.func_195083_a((double)☃, (double)☃);
            int ☃x = (this.field_148155_a - this.func_148139_c()) / 2;
            if (☃ >= 0) {
               if (☃ < ☃x + 40 || ☃ > ☃x + 40 + 20) {
                  return;
               }

               Item ☃xx = this.func_195106_c(☃);
               this.func_200207_a(this.func_200208_a(☃xx), ☃, ☃);
            } else {
               ITextComponent ☃ = null;
               int ☃x = ☃ - ☃x;

               for(int ☃xx = 0; ☃xx < this.field_195112_D.length; ++☃xx) {
                  int ☃xxx = GuiStats.this.func_195224_b(☃xx);
                  if (☃x >= ☃xxx - 18 && ☃x <= ☃xxx) {
                     ☃ = new TextComponentTranslation(this.func_195108_d(☃xx).func_199078_c());
                     break;
                  }
               }

               this.func_200207_a(☃, ☃, ☃);
            }
         }
      }

      protected void func_200207_a(@Nullable ITextComponent var1, int var2, int var3) {
         if (☃ != null) {
            String ☃ = ☃.func_150254_d();
            int ☃x = ☃ + 12;
            int ☃xx = ☃ - 12;
            int ☃xxx = GuiStats.this.field_146289_q.func_78256_a(☃);
            this.func_73733_a(☃x - 3, ☃xx - 3, ☃x + ☃xxx + 3, ☃xx + 8 + 3, -1073741824, -1073741824);
            GuiStats.this.field_146289_q.func_175063_a(☃, (float)☃x, (float)☃xx, -1);
         }
      }

      protected ITextComponent func_200208_a(Item var1) {
         return ☃.func_200296_o();
      }

      protected void func_195107_a(StatType<?> var1) {
         if (☃ != this.field_195110_A) {
            this.field_195110_A = ☃;
            this.field_195111_B = -1;
         } else if (this.field_195111_B == -1) {
            this.field_195111_B = 1;
         } else {
            this.field_195110_A = null;
            this.field_195111_B = 0;
         }

         this.field_195116_y.sort(this.field_195117_z);
      }

      class Comparator implements java.util.Comparator<Item> {
         private Comparator() {
         }

         public int compare(Item var1, Item var2) {
            int ☃;
            int ☃x;
            if (StatsItem.this.field_195110_A == null) {
               ☃ = 0;
               ☃x = 0;
            } else if (StatsItem.this.field_195113_v.contains(StatsItem.this.field_195110_A)) {
               StatType<Block> ☃ = StatsItem.this.field_195110_A;
               ☃ = ☃ instanceof ItemBlock ? GuiStats.this.field_146546_t.func_199060_a(☃, ((ItemBlock)☃).func_179223_d()) : -1;
               ☃x = ☃ instanceof ItemBlock ? GuiStats.this.field_146546_t.func_199060_a(☃, ((ItemBlock)☃).func_179223_d()) : -1;
            } else {
               StatType<Item> ☃ = StatsItem.this.field_195110_A;
               ☃ = GuiStats.this.field_146546_t.func_199060_a(☃, ☃);
               ☃x = GuiStats.this.field_146546_t.func_199060_a(☃, ☃);
            }

            return ☃ == ☃x
               ? StatsItem.this.field_195111_B * Integer.compare(Item.func_150891_b(☃), Item.func_150891_b(☃))
               : StatsItem.this.field_195111_B * Integer.compare(☃, ☃x);
         }
      }
   }

   class StatsMobsList extends GuiSlot {
      private final List<EntityType<?>> field_148222_l = Lists.<EntityType<?>>newArrayList();

      public StatsMobsList(Minecraft var2) {
         super(
            ☃,
            GuiStats.this.field_146294_l,
            GuiStats.this.field_146295_m,
            32,
            GuiStats.this.field_146295_m - 64,
            GuiStats.this.field_146289_q.field_78288_b * 4
         );
         this.func_193651_b(false);

         for(EntityType<?> ☃ : IRegistry.field_212629_r) {
            if (GuiStats.this.field_146546_t.func_77444_a(StatList.field_199090_h.func_199076_b(☃)) > 0
               || GuiStats.this.field_146546_t.func_77444_a(StatList.field_199091_i.func_199076_b(☃)) > 0) {
               this.field_148222_l.add(☃);
            }
         }
      }

      @Override
      protected int func_148127_b() {
         return this.field_148222_l.size();
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return false;
      }

      @Override
      protected int func_148138_e() {
         return this.func_148127_b() * GuiStats.this.field_146289_q.field_78288_b * 4;
      }

      @Override
      protected void func_148123_a() {
         GuiStats.this.func_146276_q_();
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         EntityType<?> ☃ = (EntityType)this.field_148222_l.get(☃);
         String ☃x = I18n.func_135052_a(Util.func_200697_a("entity", EntityType.func_200718_a(☃)));
         int ☃xx = GuiStats.this.field_146546_t.func_77444_a(StatList.field_199090_h.func_199076_b(☃));
         int ☃xxx = GuiStats.this.field_146546_t.func_77444_a(StatList.field_199091_i.func_199076_b(☃));
         this.func_73731_b(GuiStats.this.field_146289_q, ☃x, ☃ + 2 - 10, ☃ + 1, 16777215);
         this.func_73731_b(
            GuiStats.this.field_146289_q, this.func_199707_a(☃x, ☃xx), ☃ + 2, ☃ + 1 + GuiStats.this.field_146289_q.field_78288_b, ☃xx == 0 ? 6316128 : 9474192
         );
         this.func_73731_b(
            GuiStats.this.field_146289_q,
            this.func_199706_b(☃x, ☃xxx),
            ☃ + 2,
            ☃ + 1 + GuiStats.this.field_146289_q.field_78288_b * 2,
            ☃xxx == 0 ? 6316128 : 9474192
         );
      }

      private String func_199707_a(String var1, int var2) {
         String ☃ = StatList.field_199090_h.func_199078_c();
         return ☃ == 0 ? I18n.func_135052_a(☃ + ".none", ☃) : I18n.func_135052_a(☃, ☃, ☃);
      }

      private String func_199706_b(String var1, int var2) {
         String ☃ = StatList.field_199091_i.func_199078_c();
         return ☃ == 0 ? I18n.func_135052_a(☃ + ".none", ☃) : I18n.func_135052_a(☃, ☃, ☃);
      }
   }
}
