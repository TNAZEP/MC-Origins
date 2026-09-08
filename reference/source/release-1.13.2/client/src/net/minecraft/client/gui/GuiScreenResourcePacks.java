package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ResourcePackInfoClient;
import net.minecraft.client.resources.ResourcePackListEntryFound;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.Util;

public class GuiScreenResourcePacks extends GuiScreen {
   private final GuiScreen field_146965_f;
   @Nullable
   private GuiResourcePackAvailable field_146970_i;
   @Nullable
   private GuiResourcePackSelected field_146967_r;
   private boolean field_175289_s;

   public GuiScreenResourcePacks(GuiScreen var1) {
      this.field_146965_f = ☃;
   }

   @Override
   protected void func_73866_w_() {
      this.func_189646_b(new GuiOptionButton(2, this.field_146294_l / 2 - 154, this.field_146295_m - 48, I18n.func_135052_a("resourcePack.openFolder")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            Util.func_110647_a().func_195641_a(GuiScreenResourcePacks.this.field_146297_k.func_195549_J());
         }
      });
      this.func_189646_b(new GuiOptionButton(1, this.field_146294_l / 2 + 4, this.field_146295_m - 48, I18n.func_135052_a("gui.done")) {
         @Override
         public void func_194829_a(double var1, double var3) {
            if (GuiScreenResourcePacks.this.field_175289_s) {
               List<ResourcePackInfoClient> ☃ = Lists.<ResourcePackInfoClient>newArrayList();

               for(ResourcePackListEntryFound ☃x : GuiScreenResourcePacks.this.field_146967_r.func_195074_b()) {
                  ☃.add(☃x.func_195017_i());
               }

               Collections.reverse(☃);
               GuiScreenResourcePacks.this.field_146297_k.func_195548_H().func_198985_a(☃);
               GuiScreenResourcePacks.this.field_146297_k.field_71474_y.field_151453_l.clear();
               GuiScreenResourcePacks.this.field_146297_k.field_71474_y.field_183018_l.clear();

               for(ResourcePackInfoClient ☃x : ☃) {
                  if (!☃x.func_195798_h()) {
                     GuiScreenResourcePacks.this.field_146297_k.field_71474_y.field_151453_l.add(☃x.func_195790_f());
                     if (!☃x.func_195791_d().func_198968_a()) {
                        GuiScreenResourcePacks.this.field_146297_k.field_71474_y.field_183018_l.add(☃x.func_195790_f());
                     }
                  }
               }

               GuiScreenResourcePacks.this.field_146297_k.field_71474_y.func_74303_b();
               GuiScreenResourcePacks.this.field_146297_k.func_110436_a();
            }

            GuiScreenResourcePacks.this.field_146297_k.func_147108_a(GuiScreenResourcePacks.this.field_146965_f);
         }
      });
      GuiResourcePackAvailable ☃ = this.field_146970_i;
      GuiResourcePackSelected ☃x = this.field_146967_r;
      this.field_146970_i = new GuiResourcePackAvailable(this.field_146297_k, 200, this.field_146295_m);
      this.field_146970_i.func_148140_g(this.field_146294_l / 2 - 4 - 200);
      if (☃ != null) {
         this.field_146970_i.func_195074_b().addAll(☃.func_195074_b());
      }

      this.field_195124_j.add(this.field_146970_i);
      this.field_146967_r = new GuiResourcePackSelected(this.field_146297_k, 200, this.field_146295_m);
      this.field_146967_r.func_148140_g(this.field_146294_l / 2 + 4);
      if (☃x != null) {
         this.field_146967_r.func_195074_b().addAll(☃x.func_195074_b());
      }

      this.field_195124_j.add(this.field_146967_r);
      if (!this.field_175289_s) {
         this.field_146970_i.func_195074_b().clear();
         this.field_146967_r.func_195074_b().clear();
         ResourcePackList<ResourcePackInfoClient> ☃ = this.field_146297_k.func_195548_H();
         ☃.func_198983_a();
         List<ResourcePackInfoClient> ☃x = Lists.<ResourcePackInfoClient>newArrayList(☃.func_198978_b());
         ☃x.removeAll(☃.func_198980_d());

         for(ResourcePackInfoClient ☃xx : ☃x) {
            this.field_146970_i.func_195095_a(new ResourcePackListEntryFound(this, ☃xx));
         }

         for(ResourcePackInfoClient ☃xx : Lists.reverse(Lists.newArrayList(☃.func_198980_d()))) {
            this.field_146967_r.func_195095_a(new ResourcePackListEntryFound(this, ☃xx));
         }
      }
   }

   public void func_195301_a(ResourcePackListEntryFound var1) {
      this.field_146970_i.func_195074_b().remove(☃);
      ☃.func_195020_a(this.field_146967_r);
      this.func_175288_g();
   }

   public void func_195305_b(ResourcePackListEntryFound var1) {
      this.field_146967_r.func_195074_b().remove(☃);
      this.field_146970_i.func_195095_a(☃);
      this.func_175288_g();
   }

   public boolean func_195312_c(ResourcePackListEntryFound var1) {
      return this.field_146967_r.func_195074_b().contains(☃);
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146278_c(0);
      this.field_146970_i.func_148128_a(☃, ☃, ☃);
      this.field_146967_r.func_148128_a(☃, ☃, ☃);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("resourcePack.title"), this.field_146294_l / 2, 16, 16777215);
      this.func_73732_a(this.field_146289_q, I18n.func_135052_a("resourcePack.folderInfo"), this.field_146294_l / 2 - 77, this.field_146295_m - 26, 8421504);
      super.func_73863_a(☃, ☃, ☃);
   }

   public void func_175288_g() {
      this.field_175289_s = true;
   }
}
