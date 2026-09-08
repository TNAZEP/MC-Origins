package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.model.ModelSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;

public class LayerSheepWool implements LayerRenderer<EntitySheep> {
   private static final ResourceLocation field_177165_a = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final RenderSheep field_177163_b;
   private final ModelSheepWool field_177164_c = new ModelSheepWool();

   public LayerSheepWool(RenderSheep var1) {
      this.field_177163_b = ☃;
   }

   public void func_177141_a(EntitySheep var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!☃.func_70892_o() && !☃.func_82150_aj()) {
         this.field_177163_b.func_110776_a(field_177165_a);
         if (☃.func_145818_k_() && "jeb_".equals(☃.func_200200_C_().func_150261_e())) {
            int ☃ = 25;
            int ☃x = ☃.field_70173_aa / 25 + ☃.func_145782_y();
            int ☃xx = EnumDyeColor.values().length;
            int ☃xxx = ☃x % ☃xx;
            int ☃xxxx = (☃x + 1) % ☃xx;
            float ☃xxxxx = ((float)(☃.field_70173_aa % 25) + ☃) / 25.0F;
            float[] ☃xxxxxx = EntitySheep.func_175513_a(EnumDyeColor.func_196056_a(☃xxx));
            float[] ☃xxxxxxx = EntitySheep.func_175513_a(EnumDyeColor.func_196056_a(☃xxxx));
            GlStateManager.func_179124_c(
               ☃xxxxxx[0] * (1.0F - ☃xxxxx) + ☃xxxxxxx[0] * ☃xxxxx,
               ☃xxxxxx[1] * (1.0F - ☃xxxxx) + ☃xxxxxxx[1] * ☃xxxxx,
               ☃xxxxxx[2] * (1.0F - ☃xxxxx) + ☃xxxxxxx[2] * ☃xxxxx
            );
         } else {
            float[] ☃ = EntitySheep.func_175513_a(☃.func_175509_cj());
            GlStateManager.func_179124_c(☃[0], ☃[1], ☃[2]);
         }

         this.field_177164_c.func_178686_a(this.field_177163_b.func_177087_b());
         this.field_177164_c.func_78086_a(☃, ☃, ☃, ☃);
         this.field_177164_c.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_177142_b() {
      return true;
   }
}
