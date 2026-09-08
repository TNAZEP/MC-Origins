package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmorDyeable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase> {
   protected static final ResourceLocation field_177188_b = new ResourceLocation("textures/misc/enchanted_item_glint.png");
   protected T field_177189_c;
   protected T field_177186_d;
   private final RenderLivingBase<?> field_177190_a;
   private float field_177187_e = 1.0F;
   private float field_177184_f = 1.0F;
   private float field_177185_g = 1.0F;
   private float field_177192_h = 1.0F;
   private boolean field_177193_i;
   private static final Map<String, ResourceLocation> field_177191_j = Maps.newHashMap();

   public LayerArmorBase(RenderLivingBase<?> var1) {
      this.field_177190_a = ☃;
      this.func_177177_a();
   }

   @Override
   public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.func_188361_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, EntityEquipmentSlot.CHEST);
      this.func_188361_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, EntityEquipmentSlot.LEGS);
      this.func_188361_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, EntityEquipmentSlot.FEET);
      this.func_188361_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, EntityEquipmentSlot.HEAD);
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }

   private void func_188361_a(
      EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, EntityEquipmentSlot var9
   ) {
      ItemStack ☃ = ☃.func_184582_a(☃);
      if (☃.func_77973_b() instanceof ItemArmor) {
         ItemArmor ☃x = (ItemArmor)☃.func_77973_b();
         if (☃x.func_185083_B_() == ☃) {
            T ☃xx = this.func_188360_a(☃);
            ☃xx.func_178686_a(this.field_177190_a.func_177087_b());
            ☃xx.func_78086_a(☃, ☃, ☃, ☃);
            this.func_188359_a(☃xx, ☃);
            boolean ☃xxx = this.func_188363_b(☃);
            this.field_177190_a.func_110776_a(this.func_177181_a(☃x, ☃xxx));
            if (☃x instanceof ItemArmorDyeable) {
               int ☃xxxx = ((ItemArmorDyeable)☃x).func_200886_f(☃);
               float ☃xxxxx = (float)(☃xxxx >> 16 & 0xFF) / 255.0F;
               float ☃xxxxxx = (float)(☃xxxx >> 8 & 0xFF) / 255.0F;
               float ☃xxxxxxx = (float)(☃xxxx & 0xFF) / 255.0F;
               GlStateManager.func_179131_c(this.field_177184_f * ☃xxxxx, this.field_177185_g * ☃xxxxxx, this.field_177192_h * ☃xxxxxxx, this.field_177187_e);
               ☃xx.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
               this.field_177190_a.func_110776_a(this.func_177178_a(☃x, ☃xxx, "overlay"));
            }

            GlStateManager.func_179131_c(this.field_177184_f, this.field_177185_g, this.field_177192_h, this.field_177187_e);
            ☃xx.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
            if (!this.field_177193_i && ☃.func_77948_v()) {
               func_188364_a(this.field_177190_a, ☃, ☃xx, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
            }
         }
      }
   }

   public T func_188360_a(EntityEquipmentSlot var1) {
      return (T)(this.func_188363_b(☃) ? this.field_177189_c : this.field_177186_d);
   }

   private boolean func_188363_b(EntityEquipmentSlot var1) {
      return ☃ == EntityEquipmentSlot.LEGS;
   }

   public static void func_188364_a(
      RenderLivingBase<?> var0, EntityLivingBase var1, ModelBase var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9
   ) {
      float ☃ = (float)☃.field_70173_aa + ☃;
      ☃.func_110776_a(field_177188_b);
      Minecraft.func_71410_x().field_71460_t.func_191514_d(true);
      GlStateManager.func_179147_l();
      GlStateManager.func_179143_c(514);
      GlStateManager.func_179132_a(false);
      float ☃x = 0.5F;
      GlStateManager.func_179131_c(0.5F, 0.5F, 0.5F, 1.0F);

      for(int ☃xx = 0; ☃xx < 2; ++☃xx) {
         GlStateManager.func_179140_f();
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);
         float ☃xxx = 0.76F;
         GlStateManager.func_179131_c(0.38F, 0.19F, 0.608F, 1.0F);
         GlStateManager.func_179128_n(5890);
         GlStateManager.func_179096_D();
         float ☃xxxx = 0.33333334F;
         GlStateManager.func_179152_a(0.33333334F, 0.33333334F, 0.33333334F);
         GlStateManager.func_179114_b(30.0F - (float)☃xx * 60.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.func_179109_b(0.0F, ☃ * (0.001F + (float)☃xx * 0.003F) * 20.0F, 0.0F);
         GlStateManager.func_179128_n(5888);
         ☃.func_78088_a(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      }

      GlStateManager.func_179128_n(5890);
      GlStateManager.func_179096_D();
      GlStateManager.func_179128_n(5888);
      GlStateManager.func_179145_e();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179084_k();
      Minecraft.func_71410_x().field_71460_t.func_191514_d(false);
   }

   private ResourceLocation func_177181_a(ItemArmor var1, boolean var2) {
      return this.func_177178_a(☃, ☃, null);
   }

   private ResourceLocation func_177178_a(ItemArmor var1, boolean var2, @Nullable String var3) {
      String ☃ = "textures/models/armor/" + ☃.func_200880_d().func_200897_d() + "_layer_" + (☃ ? 2 : 1) + (☃ == null ? "" : "_" + ☃) + ".png";
      return (ResourceLocation)field_177191_j.computeIfAbsent(☃, ResourceLocation::new);
   }

   protected abstract void func_177177_a();

   protected abstract void func_188359_a(T var1, EntityEquipmentSlot var2);
}
