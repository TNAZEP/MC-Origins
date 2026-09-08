package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.fluid.IFluidState;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.state.IProperty;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.ForcedChunksSaveData;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;

public class GuiOverlayDebug extends Gui {
   private final Minecraft field_175242_a;
   private final FontRenderer field_175241_f;
   private RayTraceResult field_211537_g;
   private RayTraceResult field_211538_h;

   public GuiOverlayDebug(Minecraft var1) {
      this.field_175242_a = ☃;
      this.field_175241_f = ☃.field_71466_p;
   }

   public void func_194818_a() {
      this.field_175242_a.field_71424_I.func_76320_a("debug");
      GlStateManager.func_179094_E();
      Entity ☃ = this.field_175242_a.func_175606_aa();
      this.field_211537_g = ☃.func_174822_a(20.0, 0.0F, RayTraceFluidMode.NEVER);
      this.field_211538_h = ☃.func_174822_a(20.0, 0.0F, RayTraceFluidMode.ALWAYS);
      this.func_180798_a();
      this.func_194819_c();
      GlStateManager.func_179121_F();
      if (this.field_175242_a.field_71474_y.field_181657_aC) {
         this.func_181554_e();
      }

      this.field_175242_a.field_71424_I.func_76319_b();
   }

   protected void func_180798_a() {
      List<String> ☃ = this.func_209011_c();
      ☃.add("");
      ☃.add(
         "Debug: Pie [shift]: "
            + (this.field_175242_a.field_71474_y.field_74329_Q ? "visible" : "hidden")
            + " FPS [alt]: "
            + (this.field_175242_a.field_71474_y.field_181657_aC ? "visible" : "hidden")
      );
      ☃.add("For help: press F3 + Q");

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         String ☃xx = (String)☃.get(☃x);
         if (!Strings.isNullOrEmpty(☃xx)) {
            int ☃xxx = this.field_175241_f.field_78288_b;
            int ☃xxxx = this.field_175241_f.func_78256_a(☃xx);
            int ☃xxxxx = 2;
            int ☃xxxxxx = 2 + ☃xxx * ☃x;
            func_73734_a(1, ☃xxxxxx - 1, 2 + ☃xxxx + 1, ☃xxxxxx + ☃xxx - 1, -1873784752);
            this.field_175241_f.func_211126_b(☃xx, 2.0F, (float)☃xxxxxx, 14737632);
         }
      }
   }

   protected void func_194819_c() {
      List<String> ☃ = this.func_175238_c();

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         String ☃xx = (String)☃.get(☃x);
         if (!Strings.isNullOrEmpty(☃xx)) {
            int ☃xxx = this.field_175241_f.field_78288_b;
            int ☃xxxx = this.field_175241_f.func_78256_a(☃xx);
            int ☃xxxxx = this.field_175242_a.field_195558_d.func_198107_o() - 2 - ☃xxxx;
            int ☃xxxxxx = 2 + ☃xxx * ☃x;
            func_73734_a(☃xxxxx - 1, ☃xxxxxx - 1, ☃xxxxx + ☃xxxx + 1, ☃xxxxxx + ☃xxx - 1, -1873784752);
            this.field_175241_f.func_211126_b(☃xx, (float)☃xxxxx, (float)☃xxxxxx, 14737632);
         }
      }
   }

   protected List<String> func_209011_c() {
      IntegratedServer ☃x = this.field_175242_a.func_71401_C();
      NetworkManager ☃xx = this.field_175242_a.func_147114_u().func_147298_b();
      float ☃xxx = ☃xx.func_211390_n();
      float ☃xxxx = ☃xx.func_211393_m();
      String ☃;
      if (☃x != null) {
         ☃ = String.format("Integrated server @ %.0f ms ticks, %.0f tx, %.0f rx", ☃x.func_211149_aT(), ☃xxx, ☃xxxx);
      } else {
         ☃ = String.format("\"%s\" server, %.0f tx, %.0f rx", this.field_175242_a.field_71439_g.func_142021_k(), ☃xxx, ☃xxxx);
      }

      BlockPos ☃ = new BlockPos(
         this.field_175242_a.func_175606_aa().field_70165_t,
         this.field_175242_a.func_175606_aa().func_174813_aQ().field_72338_b,
         this.field_175242_a.func_175606_aa().field_70161_v
      );
      if (this.field_175242_a.func_189648_am()) {
         return Lists.newArrayList(
            "Minecraft 1.13.2 (" + this.field_175242_a.func_175600_c() + "/" + ClientBrandRetriever.getClientModName() + ")",
            this.field_175242_a.field_71426_K,
            ☃,
            this.field_175242_a.field_71438_f.func_72735_c(),
            this.field_175242_a.field_71438_f.func_72723_d(),
            "P: " + this.field_175242_a.field_71452_i.func_78869_b() + ". T: " + this.field_175242_a.field_71441_e.func_72981_t(),
            this.field_175242_a.field_71441_e.func_72827_u(),
            "",
            String.format("Chunk-relative: %d %d %d", ☃.func_177958_n() & 15, ☃.func_177956_o() & 15, ☃.func_177952_p() & 15)
         );
      } else {
         Entity ☃ = this.field_175242_a.func_175606_aa();
         EnumFacing ☃x = ☃.func_174811_aO();
         String ☃xx = "Invalid";
         switch(☃x) {
            case NORTH:
               ☃xx = "Towards negative Z";
               break;
            case SOUTH:
               ☃xx = "Towards positive Z";
               break;
            case WEST:
               ☃xx = "Towards negative X";
               break;
            case EAST:
               ☃xx = "Towards positive X";
         }

         DimensionType ☃x = this.field_175242_a.field_71441_e.field_73011_w.func_186058_p();
         World ☃;
         if (☃x != null && ☃x.func_71218_a(☃x) != null) {
            ☃ = ☃x.func_71218_a(☃x);
         } else {
            ☃ = this.field_175242_a.field_71441_e;
         }

         ForcedChunksSaveData ☃ = ☃.func_212411_a(☃x, ForcedChunksSaveData::new, "chunks");
         List<String> ☃x = Lists.newArrayList(
            "Minecraft 1.13.2 ("
               + this.field_175242_a.func_175600_c()
               + "/"
               + ClientBrandRetriever.getClientModName()
               + ("release".equalsIgnoreCase(this.field_175242_a.func_184123_d()) ? "" : "/" + this.field_175242_a.func_184123_d())
               + ")",
            this.field_175242_a.field_71426_K,
            ☃,
            this.field_175242_a.field_71438_f.func_72735_c(),
            this.field_175242_a.field_71438_f.func_72723_d(),
            "P: " + this.field_175242_a.field_71452_i.func_78869_b() + ". T: " + this.field_175242_a.field_71441_e.func_72981_t(),
            this.field_175242_a.field_71441_e.func_72827_u(),
            DimensionType.func_212678_a(☃x).toString() + " FC: " + (☃ == null ? "n/a" : Integer.toString(☃.func_212438_a().size())),
            "",
            String.format(
               Locale.ROOT,
               "XYZ: %.3f / %.5f / %.3f",
               this.field_175242_a.func_175606_aa().field_70165_t,
               this.field_175242_a.func_175606_aa().func_174813_aQ().field_72338_b,
               this.field_175242_a.func_175606_aa().field_70161_v
            ),
            String.format("Block: %d %d %d", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p()),
            String.format(
               "Chunk: %d %d %d in %d %d %d",
               ☃.func_177958_n() & 15,
               ☃.func_177956_o() & 15,
               ☃.func_177952_p() & 15,
               ☃.func_177958_n() >> 4,
               ☃.func_177956_o() >> 4,
               ☃.func_177952_p() >> 4
            ),
            String.format(
               Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", ☃x, ☃xx, MathHelper.func_76142_g(☃.field_70177_z), MathHelper.func_76142_g(☃.field_70125_A)
            )
         );
         if (this.field_175242_a.field_71441_e != null) {
            Chunk ☃xx = this.field_175242_a.field_71441_e.func_175726_f(☃);
            if (!this.field_175242_a.field_71441_e.func_175667_e(☃) || ☃.func_177956_o() < 0 || ☃.func_177956_o() >= 256) {
               ☃x.add("Outside of world...");
            } else if (!☃xx.func_76621_g()) {
               ☃x.add("Biome: " + IRegistry.field_212624_m.func_177774_c(☃xx.func_201600_k(☃)));
               ☃x.add(
                  "Light: "
                     + ☃xx.func_201586_a(☃, 0, ☃xx.func_177412_p().field_73011_w.func_191066_m())
                     + " ("
                     + ☃xx.func_201587_a(EnumLightType.SKY, ☃, ☃xx.func_177412_p().field_73011_w.func_191066_m())
                     + " sky, "
                     + ☃xx.func_201587_a(EnumLightType.BLOCK, ☃, ☃xx.func_177412_p().field_73011_w.func_191066_m())
                     + " block)"
               );
               DifficultyInstance ☃xx = this.field_175242_a.field_71441_e.func_175649_E(☃);
               if (this.field_175242_a.func_71387_A() && ☃x != null) {
                  EntityPlayerMP ☃xxx = ☃x.func_184103_al().func_177451_a(this.field_175242_a.field_71439_g.func_110124_au());
                  if (☃xxx != null) {
                     ☃xx = ☃xxx.field_70170_p.func_175649_E(new BlockPos(☃xxx));
                  }
               }

               ☃x.add(
                  String.format(
                     Locale.ROOT,
                     "Local Difficulty: %.2f // %.2f (Day %d)",
                     ☃xx.func_180168_b(),
                     ☃xx.func_180170_c(),
                     this.field_175242_a.field_71441_e.func_72820_D() / 24000L
                  )
               );
            } else {
               ☃x.add("Waiting for chunk...");
            }
         }

         if (this.field_175242_a.field_71460_t != null && this.field_175242_a.field_71460_t.func_147702_a()) {
            ☃x.add("Shader: " + this.field_175242_a.field_71460_t.func_147706_e().func_148022_b());
         }

         if (this.field_211537_g != null && this.field_211537_g.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃ = this.field_211537_g.func_178782_a();
            ☃x.add(String.format("Looking at block: %d %d %d", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p()));
         }

         if (this.field_211538_h != null && this.field_211538_h.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃ = this.field_211538_h.func_178782_a();
            ☃x.add(String.format("Looking at liquid: %d %d %d", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p()));
         }

         return ☃x;
      }
   }

   protected List<String> func_175238_c() {
      long ☃ = Runtime.getRuntime().maxMemory();
      long ☃x = Runtime.getRuntime().totalMemory();
      long ☃xx = Runtime.getRuntime().freeMemory();
      long ☃xxx = ☃x - ☃xx;
      List<String> ☃xxxx = Lists.newArrayList(
         String.format("Java: %s %dbit", System.getProperty("java.version"), this.field_175242_a.func_147111_S() ? 64 : 32),
         String.format("Mem: % 2d%% %03d/%03dMB", ☃xxx * 100L / ☃, func_175240_a(☃xxx), func_175240_a(☃)),
         String.format("Allocated: % 2d%% %03dMB", ☃x * 100L / ☃, func_175240_a(☃x)),
         "",
         String.format("CPU: %s", OpenGlHelper.func_183029_j()),
         "",
         String.format(
            "Display: %dx%d (%s)",
            Minecraft.func_71410_x().field_195558_d.func_198109_k(),
            Minecraft.func_71410_x().field_195558_d.func_198091_l(),
            GlStateManager.func_187416_u(7936)
         ),
         GlStateManager.func_187416_u(7937),
         GlStateManager.func_187416_u(7938)
      );
      if (this.field_175242_a.func_189648_am()) {
         return ☃xxxx;
      } else {
         if (this.field_211537_g != null && this.field_211537_g.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃ = this.field_211537_g.func_178782_a();
            IBlockState ☃x = this.field_175242_a.field_71441_e.func_180495_p(☃);
            ☃xxxx.add("");
            ☃xxxx.add(TextFormatting.UNDERLINE + "Targeted Block");
            ☃xxxx.add(String.valueOf(IRegistry.field_212618_g.func_177774_c(☃x.func_177230_c())));

            for(Entry<IProperty<?>, Comparable<?>> ☃xx : ☃x.func_206871_b().entrySet()) {
               ☃xxxx.add(this.func_211534_a(☃xx));
            }

            for(ResourceLocation ☃xx : this.field_175242_a.func_147114_u().func_199724_l().func_199717_a().func_199913_a(☃x.func_177230_c())) {
               ☃xxxx.add("#" + ☃xx);
            }
         }

         if (this.field_211538_h != null && this.field_211538_h.field_72313_a == RayTraceResult.Type.BLOCK) {
            BlockPos ☃ = this.field_211538_h.func_178782_a();
            IFluidState ☃x = this.field_175242_a.field_71441_e.func_204610_c(☃);
            ☃xxxx.add("");
            ☃xxxx.add(TextFormatting.UNDERLINE + "Targeted Fluid");
            ☃xxxx.add(String.valueOf(IRegistry.field_212619_h.func_177774_c(☃x.func_206886_c())));

            for(Entry<IProperty<?>, Comparable<?>> ☃xx : ☃x.func_206871_b().entrySet()) {
               ☃xxxx.add(this.func_211534_a(☃xx));
            }

            for(ResourceLocation ☃xx : this.field_175242_a.func_147114_u().func_199724_l().func_205704_c().func_199913_a(☃x.func_206886_c())) {
               ☃xxxx.add("#" + ☃xx);
            }
         }

         Entity ☃ = this.field_175242_a.field_147125_j;
         if (☃ != null) {
            ☃xxxx.add("");
            ☃xxxx.add(TextFormatting.UNDERLINE + "Targeted Entity");
            ☃xxxx.add(String.valueOf(IRegistry.field_212629_r.func_177774_c(☃.func_200600_R())));
         }

         return ☃xxxx;
      }
   }

   private String func_211534_a(Entry<IProperty<?>, Comparable<?>> var1) {
      IProperty<?> ☃ = (IProperty)☃.getKey();
      Comparable<?> ☃x = (Comparable)☃.getValue();
      String ☃xx = Util.func_200269_a(☃, ☃x);
      if (Boolean.TRUE.equals(☃x)) {
         ☃xx = TextFormatting.GREEN + ☃xx;
      } else if (Boolean.FALSE.equals(☃x)) {
         ☃xx = TextFormatting.RED + ☃xx;
      }

      return ☃.func_177701_a() + ": " + ☃xx;
   }

   private void func_181554_e() {
      GlStateManager.func_179097_i();
      FrameTimer ☃ = this.field_175242_a.func_181539_aj();
      int ☃x = ☃.func_181749_a();
      int ☃xx = ☃.func_181750_b();
      long[] ☃xxx = ☃.func_181746_c();
      int ☃xxxx = ☃x;
      int ☃xxxxx = 0;
      int ☃xxxxxx = this.field_175242_a.field_195558_d.func_198087_p();
      func_73734_a(0, ☃xxxxxx - 60, 240, ☃xxxxxx, -1873784752);

      while(☃xxxx != ☃xx) {
         int ☃xxxxxxx = ☃.func_181748_a(☃xxx[☃xxxx], 30);
         int ☃xxxxxxxx = this.func_181552_c(MathHelper.func_76125_a(☃xxxxxxx, 0, 60), 0, 30, 60);
         this.func_73728_b(☃xxxxx, ☃xxxxxx, ☃xxxxxx - ☃xxxxxxx, ☃xxxxxxxx);
         ++☃xxxxx;
         ☃xxxx = ☃.func_181751_b(☃xxxx + 1);
      }

      func_73734_a(1, ☃xxxxxx - 30 + 1, 14, ☃xxxxxx - 30 + 10, -1873784752);
      this.field_175241_f.func_211126_b("60", 2.0F, (float)(☃xxxxxx - 30 + 2), 14737632);
      this.func_73730_a(0, 239, ☃xxxxxx - 30, -1);
      func_73734_a(1, ☃xxxxxx - 60 + 1, 14, ☃xxxxxx - 60 + 10, -1873784752);
      this.field_175241_f.func_211126_b("30", 2.0F, (float)(☃xxxxxx - 60 + 2), 14737632);
      this.func_73730_a(0, 239, ☃xxxxxx - 60, -1);
      this.func_73730_a(0, 239, ☃xxxxxx - 1, -1);
      this.func_73728_b(0, ☃xxxxxx - 60, ☃xxxxxx, -1);
      this.func_73728_b(239, ☃xxxxxx - 60, ☃xxxxxx, -1);
      if (this.field_175242_a.field_71474_y.field_74350_i <= 120) {
         this.func_73730_a(0, 239, ☃xxxxxx - 60 + this.field_175242_a.field_71474_y.field_74350_i / 2, -16711681);
      }

      GlStateManager.func_179126_j();
   }

   private int func_181552_c(int var1, int var2, int var3, int var4) {
      return ☃ < ☃ ? this.func_181553_a(-16711936, -256, (float)☃ / (float)☃) : this.func_181553_a(-256, -65536, (float)(☃ - ☃) / (float)(☃ - ☃));
   }

   private int func_181553_a(int var1, int var2, float var3) {
      int ☃ = ☃ >> 24 & 0xFF;
      int ☃x = ☃ >> 16 & 0xFF;
      int ☃xx = ☃ >> 8 & 0xFF;
      int ☃xxx = ☃ & 0xFF;
      int ☃xxxx = ☃ >> 24 & 0xFF;
      int ☃xxxxx = ☃ >> 16 & 0xFF;
      int ☃xxxxxx = ☃ >> 8 & 0xFF;
      int ☃xxxxxxx = ☃ & 0xFF;
      int ☃xxxxxxxx = MathHelper.func_76125_a((int)((float)☃ + (float)(☃xxxx - ☃) * ☃), 0, 255);
      int ☃xxxxxxxxx = MathHelper.func_76125_a((int)((float)☃x + (float)(☃xxxxx - ☃x) * ☃), 0, 255);
      int ☃xxxxxxxxxx = MathHelper.func_76125_a((int)((float)☃xx + (float)(☃xxxxxx - ☃xx) * ☃), 0, 255);
      int ☃xxxxxxxxxxx = MathHelper.func_76125_a((int)((float)☃xxx + (float)(☃xxxxxxx - ☃xxx) * ☃), 0, 255);
      return ☃xxxxxxxx << 24 | ☃xxxxxxxxx << 16 | ☃xxxxxxxxxx << 8 | ☃xxxxxxxxxxx;
   }

   private static long func_175240_a(long var0) {
      return ☃ / 1024L / 1024L;
   }
}
