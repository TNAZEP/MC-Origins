package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.FlatGenSettings;
import net.minecraft.world.gen.FlatLayerInfo;

public class GuiFlatPresets extends GuiScreen {
   private static final List<GuiFlatPresets.LayerItem> field_146431_f = Lists.<GuiFlatPresets.LayerItem>newArrayList();
   private final GuiCreateFlatWorld field_146432_g;
   private String field_146438_h;
   private String field_146439_i;
   private String field_146436_r;
   private GuiFlatPresets.ListSlot field_146435_s;
   private GuiButton field_146434_t;
   private GuiTextField field_146433_u;

   public GuiFlatPresets(GuiCreateFlatWorld var1) {
      this.field_146432_g = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      this.field_146438_h = I18n.func_135052_a("createWorld.customize.presets.title");
      this.field_146439_i = I18n.func_135052_a("createWorld.customize.presets.share");
      this.field_146436_r = I18n.func_135052_a("createWorld.customize.presets.list");
      this.field_146433_u = new GuiTextField(2, this.field_146289_q, 50, 40, this.field_146294_l - 100, 20);
      this.field_146435_s = new GuiFlatPresets.ListSlot();
      this.field_195124_j.add(this.field_146435_s);
      this.field_146433_u.func_146203_f(1230);
      this.field_146433_u.func_146180_a(this.field_146432_g.func_210501_h());
      this.field_195124_j.add(this.field_146433_u);
      this.field_146434_t = this.func_189646_b(
         new GuiButton(0, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("createWorld.customize.presets.select")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiFlatPresets.this.field_146432_g.func_210502_a(GuiFlatPresets.this.field_146433_u.func_146179_b());
               GuiFlatPresets.this.field_146297_k.func_147108_a(GuiFlatPresets.this.field_146432_g);
            }
         }
      );
      this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiFlatPresets.this.field_146297_k.func_147108_a(GuiFlatPresets.this.field_146432_g);
         }
      });
      this.func_146426_g();
      this.func_195073_a(this.field_146435_s);
   }

   @Override
   public boolean mouseScrolled(double var1) {
      return this.field_146435_s.mouseScrolled(☃);
   }

   @Override
   public void func_175273_b(Minecraft var1, int var2, int var3) {
      String ☃ = this.field_146433_u.func_146179_b();
      this.func_146280_a(☃, ☃, ☃);
      this.field_146433_u.func_146180_a(☃);
   }

   @Override
   public void func_146281_b() {
      this.field_146297_k.field_195559_v.func_197967_a(false);
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.field_146435_s.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, this.field_146438_h, this.field_146294_l / 2, 8, 16777215);
      this.func_73731_b(this.field_146289_q, this.field_146439_i, 50, 30, 10526880);
      this.func_73731_b(this.field_146289_q, this.field_146436_r, 50, 70, 10526880);
      this.field_146433_u.func_195608_a(☃, ☃, ☃);
      super.func_73863_a(☃, ☃, ☃);
   }

   @Override
   public void func_73876_c() {
      this.field_146433_u.func_146178_a();
      super.func_73876_c();
   }

   public void func_146426_g() {
      this.field_146434_t.field_146124_l = this.func_146430_p();
   }

   private boolean func_146430_p() {
      return this.field_146435_s.field_148175_k > -1 && this.field_146435_s.field_148175_k < field_146431_f.size()
         || this.field_146433_u.func_146179_b().length() > 1;
   }

   private static void func_199709_a(String var0, IItemProvider var1, Biome var2, List<String> var3, FlatLayerInfo... var4) {
      FlatGenSettings ☃ = ChunkGeneratorType.field_205489_f.func_205483_a();

      for(int ☃x = ☃.length - 1; ☃x >= 0; --☃x) {
         ☃.func_82650_c().add(☃[☃x]);
      }

      ☃.func_82647_a(☃);
      ☃.func_82645_d();

      for(String ☃x : ☃) {
         ☃.func_82644_b().put(☃x, Maps.newHashMap());
      }

      field_146431_f.add(new GuiFlatPresets.LayerItem(☃.func_199767_j(), ☃, ☃.toString()));
   }

   static {
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.classic_flat"),
         Blocks.field_196658_i,
         Biomes.field_76772_c,
         Arrays.asList("village"),
         new FlatLayerInfo(1, Blocks.field_196658_i),
         new FlatLayerInfo(2, Blocks.field_150346_d),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.tunnelers_dream"),
         Blocks.field_150348_b,
         Biomes.field_76770_e,
         Arrays.asList("biome_1", "dungeon", "decoration", "stronghold", "mineshaft"),
         new FlatLayerInfo(1, Blocks.field_196658_i),
         new FlatLayerInfo(5, Blocks.field_150346_d),
         new FlatLayerInfo(230, Blocks.field_150348_b),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.water_world"),
         Items.field_151131_as,
         Biomes.field_150575_M,
         Arrays.asList("biome_1", "oceanmonument"),
         new FlatLayerInfo(90, Blocks.field_150355_j),
         new FlatLayerInfo(5, Blocks.field_150354_m),
         new FlatLayerInfo(5, Blocks.field_150346_d),
         new FlatLayerInfo(5, Blocks.field_150348_b),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.overworld"),
         Blocks.field_150349_c,
         Biomes.field_76772_c,
         Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"),
         new FlatLayerInfo(1, Blocks.field_196658_i),
         new FlatLayerInfo(3, Blocks.field_150346_d),
         new FlatLayerInfo(59, Blocks.field_150348_b),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.snowy_kingdom"),
         Blocks.field_150433_aE,
         Biomes.field_76774_n,
         Arrays.asList("village", "biome_1"),
         new FlatLayerInfo(1, Blocks.field_150433_aE),
         new FlatLayerInfo(1, Blocks.field_196658_i),
         new FlatLayerInfo(3, Blocks.field_150346_d),
         new FlatLayerInfo(59, Blocks.field_150348_b),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.bottomless_pit"),
         Items.field_151008_G,
         Biomes.field_76772_c,
         Arrays.asList("village", "biome_1"),
         new FlatLayerInfo(1, Blocks.field_196658_i),
         new FlatLayerInfo(3, Blocks.field_150346_d),
         new FlatLayerInfo(2, Blocks.field_150347_e)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.desert"),
         Blocks.field_150354_m,
         Biomes.field_76769_d,
         Arrays.asList("village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"),
         new FlatLayerInfo(8, Blocks.field_150354_m),
         new FlatLayerInfo(52, Blocks.field_150322_A),
         new FlatLayerInfo(3, Blocks.field_150348_b),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.redstone_ready"),
         Items.field_151137_ax,
         Biomes.field_76769_d,
         Collections.emptyList(),
         new FlatLayerInfo(52, Blocks.field_150322_A),
         new FlatLayerInfo(3, Blocks.field_150348_b),
         new FlatLayerInfo(1, Blocks.field_150357_h)
      );
      func_199709_a(
         I18n.func_135052_a("createWorld.customize.preset.the_void"),
         Blocks.field_180401_cv,
         Biomes.field_185440_P,
         Arrays.asList("decoration"),
         new FlatLayerInfo(1, Blocks.field_150350_a)
      );
   }

   static class LayerItem {
      public Item field_148234_a;
      public String field_148232_b;
      public String field_148233_c;

      public LayerItem(Item var1, String var2, String var3) {
         this.field_148234_a = ☃;
         this.field_148232_b = ☃;
         this.field_148233_c = ☃;
      }
   }

   class ListSlot extends GuiSlot {
      public int field_148175_k = -1;

      public ListSlot() {
         super(
            GuiFlatPresets.this.field_146297_k,
            GuiFlatPresets.this.field_146294_l,
            GuiFlatPresets.this.field_146295_m,
            80,
            GuiFlatPresets.this.field_146295_m - 37,
            24
         );
      }

      private void func_195101_a(int var1, int var2, Item var3) {
         this.func_148173_e(☃ + 1, ☃ + 1);
         GlStateManager.func_179091_B();
         RenderHelper.func_74520_c();
         GuiFlatPresets.this.field_146296_j.func_175042_a(new ItemStack(☃), ☃ + 2, ☃ + 2);
         RenderHelper.func_74518_a();
         GlStateManager.func_179101_C();
      }

      private void func_148173_e(int var1, int var2) {
         this.func_148171_c(☃, ☃, 0, 0);
      }

      private void func_148171_c(int var1, int var2, int var3, int var4) {
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_148161_k.func_110434_K().func_110577_a(Gui.field_110323_l);
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

      @Override
      protected int func_148127_b() {
         return GuiFlatPresets.field_146431_f.size();
      }

      @Override
      protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
         this.field_148175_k = ☃;
         GuiFlatPresets.this.func_146426_g();
         GuiFlatPresets.this.field_146433_u
            .func_146180_a(((GuiFlatPresets.LayerItem)GuiFlatPresets.field_146431_f.get(GuiFlatPresets.this.field_146435_s.field_148175_k)).field_148233_c);
         GuiFlatPresets.this.field_146433_u.func_146196_d();
         return true;
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return ☃ == this.field_148175_k;
      }

      @Override
      protected void func_148123_a() {
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         GuiFlatPresets.LayerItem ☃ = (GuiFlatPresets.LayerItem)GuiFlatPresets.field_146431_f.get(☃);
         this.func_195101_a(☃, ☃, ☃.field_148234_a);
         GuiFlatPresets.this.field_146289_q.func_211126_b(☃.field_148232_b, (float)(☃ + 18 + 5), (float)(☃ + 6), 16777215);
      }
   }
}
