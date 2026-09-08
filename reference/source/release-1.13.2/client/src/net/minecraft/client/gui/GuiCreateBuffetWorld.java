package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.biome.provider.BiomeProviderType;

public class GuiCreateBuffetWorld extends GuiScreen {
   private static final List<ResourceLocation> field_205310_a = (List<ResourceLocation>)IRegistry.field_212627_p
      .func_148742_b()
      .stream()
      .filter(var0 -> IRegistry.field_212627_p.func_212608_b(var0).func_205481_b())
      .collect(Collectors.toList());
   private final GuiCreateWorld field_205314_f;
   private final List<ResourceLocation> field_205315_g = Lists.<ResourceLocation>newArrayList();
   private final ResourceLocation[] field_205316_h = new ResourceLocation[IRegistry.field_212624_m.func_148742_b().size()];
   private String field_205317_i;
   private GuiCreateBuffetWorld.BiomeList field_205311_s;
   private int field_205312_t;
   private GuiButton field_205313_u;

   public GuiCreateBuffetWorld(GuiCreateWorld var1, NBTTagCompound var2) {
      this.field_205314_f = ☃;
      int ☃ = 0;

      for(ResourceLocation ☃x : IRegistry.field_212624_m.func_148742_b()) {
         this.field_205316_h[☃] = ☃x;
         ++☃;
      }

      Arrays.sort(this.field_205316_h, (var0, var1x) -> {
         String ☃ = IRegistry.field_212624_m.func_212608_b(var0).func_205403_k().getString();
         String ☃x = IRegistry.field_212624_m.func_212608_b(var1x).func_205403_k().getString();
         return ☃.compareTo(☃x);
      });
      this.func_210506_a(☃);
   }

   private void func_210506_a(NBTTagCompound var1) {
      if (☃.func_150297_b("chunk_generator", 10) && ☃.func_74775_l("chunk_generator").func_150297_b("type", 8)) {
         ResourceLocation ☃ = new ResourceLocation(☃.func_74775_l("chunk_generator").func_74779_i("type"));

         for(int ☃x = 0; ☃x < field_205310_a.size(); ++☃x) {
            if (((ResourceLocation)field_205310_a.get(☃x)).equals(☃)) {
               this.field_205312_t = ☃x;
               break;
            }
         }
      }

      if (☃.func_150297_b("biome_source", 10) && ☃.func_74775_l("biome_source").func_150297_b("biomes", 9)) {
         NBTTagList ☃ = ☃.func_74775_l("biome_source").func_150295_c("biomes", 8);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            this.field_205315_g.add(new ResourceLocation(☃.func_150307_f(☃x)));
         }
      }
   }

   private NBTTagCompound func_210507_j() {
      NBTTagCompound ☃ = new NBTTagCompound();
      NBTTagCompound ☃x = new NBTTagCompound();
      ☃x.func_74778_a("type", IRegistry.field_212625_n.func_177774_c(BiomeProviderType.field_205461_c).toString());
      NBTTagCompound ☃xx = new NBTTagCompound();
      NBTTagList ☃xxx = new NBTTagList();

      for(ResourceLocation ☃xxxx : this.field_205315_g) {
         ☃xxx.add((INBTBase)(new NBTTagString(☃xxxx.toString())));
      }

      ☃xx.func_74782_a("biomes", ☃xxx);
      ☃x.func_74782_a("options", ☃xx);
      NBTTagCompound ☃xxxx = new NBTTagCompound();
      NBTTagCompound ☃xxxxx = new NBTTagCompound();
      ☃xxxx.func_74778_a("type", ((ResourceLocation)field_205310_a.get(this.field_205312_t)).toString());
      ☃xxxxx.func_74778_a("default_block", "minecraft:stone");
      ☃xxxxx.func_74778_a("default_fluid", "minecraft:water");
      ☃xxxx.func_74782_a("options", ☃xxxxx);
      ☃.func_74782_a("biome_source", ☃x);
      ☃.func_74782_a("chunk_generator", ☃xxxx);
      return ☃;
   }

   @Nullable
   @Override
   public IGuiEventListener getFocused() {
      return this.field_205311_s;
   }

   @Override
   protected void func_73866_w_() {
      this.field_146297_k.field_195559_v.func_197967_a(true);
      this.field_205317_i = I18n.func_135052_a("createWorld.customize.buffet.title");
      this.field_205311_s = new GuiCreateBuffetWorld.BiomeList();
      this.field_195124_j.add(this.field_205311_s);
      this.func_189646_b(
         new GuiButton(
            2,
            (this.field_146294_l - 200) / 2,
            40,
            200,
            20,
            I18n.func_135052_a("createWorld.customize.buffet.generatortype")
               + " "
               + I18n.func_135052_a(Util.func_200697_a("generator", (ResourceLocation)field_205310_a.get(this.field_205312_t)))
         ) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiCreateBuffetWorld.this.field_205312_t++;
               if (GuiCreateBuffetWorld.this.field_205312_t >= GuiCreateBuffetWorld.field_205310_a.size()) {
                  GuiCreateBuffetWorld.this.field_205312_t = 0;
               }
   
               this.field_146126_j = I18n.func_135052_a("createWorld.customize.buffet.generatortype")
                  + " "
                  + I18n.func_135052_a(
                     Util.func_200697_a("generator", (ResourceLocation)GuiCreateBuffetWorld.field_205310_a.get(GuiCreateBuffetWorld.this.field_205312_t))
                  );
            }
         }
      );
      this.field_205313_u = this.func_189646_b(
         new GuiButton(0, this.field_146294_l / 2 - 155, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.done")) {
            @Override
            public void func_194829_a(double var1, double var3) {
               GuiCreateBuffetWorld.this.field_205314_f.field_146334_a = GuiCreateBuffetWorld.this.func_210507_j();
               GuiCreateBuffetWorld.this.field_146297_k.func_147108_a(GuiCreateBuffetWorld.this.field_205314_f);
            }
         }
      );
      this.func_189646_b(new GuiButton(1, this.field_146294_l / 2 + 5, this.field_146295_m - 28, 150, 20, I18n.func_135052_a("gui.cancel")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            GuiCreateBuffetWorld.this.field_146297_k.func_147108_a(GuiCreateBuffetWorld.this.field_205314_f);
         }
      });
      this.func_205306_h();
   }

   public void func_205306_h() {
      this.field_205313_u.field_146124_l = !this.field_205315_g.isEmpty();
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146278_c(0);
      this.field_205311_s.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, this.field_205317_i, this.field_146294_l / 2, 8, 16777215);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("createWorld.customize.buffet.generator"), this.field_146294_l / 2, 30, 10526880);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("createWorld.customize.buffet.biome"), this.field_146294_l / 2, 68, 10526880);
      super.func_73863_a(☃, ☃, ☃);
   }

   class BiomeList extends GuiSlot {
      private BiomeList() {
         super(
            GuiCreateBuffetWorld.this.field_146297_k,
            GuiCreateBuffetWorld.this.field_146294_l,
            GuiCreateBuffetWorld.this.field_146295_m,
            80,
            GuiCreateBuffetWorld.this.field_146295_m - 37,
            16
         );
      }

      @Override
      protected int func_148127_b() {
         return GuiCreateBuffetWorld.this.field_205316_h.length;
      }

      @Override
      protected boolean func_195078_a(int var1, int var2, double var3, double var5) {
         GuiCreateBuffetWorld.this.field_205315_g.clear();
         GuiCreateBuffetWorld.this.field_205315_g.add(GuiCreateBuffetWorld.this.field_205316_h[☃]);
         GuiCreateBuffetWorld.this.func_205306_h();
         return true;
      }

      @Override
      protected boolean func_148131_a(int var1) {
         return GuiCreateBuffetWorld.this.field_205315_g.contains(GuiCreateBuffetWorld.this.field_205316_h[☃]);
      }

      @Override
      protected void func_148123_a() {
      }

      @Override
      protected void func_192637_a(int var1, int var2, int var3, int var4, int var5, int var6, float var7) {
         this.func_73731_b(
            GuiCreateBuffetWorld.this.field_146289_q,
            IRegistry.field_212624_m.func_212608_b(GuiCreateBuffetWorld.this.field_205316_h[☃]).func_205403_k().getString(),
            ☃ + 5,
            ☃ + 2,
            16777215
         );
      }
   }
}
