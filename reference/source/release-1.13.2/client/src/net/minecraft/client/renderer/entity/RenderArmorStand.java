package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.model.ModelArmorStand;
import net.minecraft.client.renderer.entity.model.ModelArmorStandArmor;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderArmorStand extends RenderLivingBase<EntityArmorStand> {
   public static final ResourceLocation field_177103_a = new ResourceLocation("textures/entity/armorstand/wood.png");

   public RenderArmorStand(RenderManager var1) {
      super(☃, new ModelArmorStand(), 0.0F);
      LayerBipedArmor ☃ = new LayerBipedArmor(this) {
         @Override
         protected void func_177177_a() {
            this.field_177189_c = new ModelArmorStandArmor(0.5F);
            this.field_177186_d = new ModelArmorStandArmor(1.0F);
         }
      };
      this.func_177094_a(☃);
      this.func_177094_a(new LayerHeldItem(this));
      this.func_177094_a(new LayerElytra(this));
      this.func_177094_a(new LayerCustomHead(this.func_177087_b().field_78116_c));
   }

   protected ResourceLocation func_110775_a(EntityArmorStand var1) {
      return field_177103_a;
   }

   public ModelArmorStand func_177087_b() {
      return (ModelArmorStand)super.func_177087_b();
   }

   protected void func_77043_a(EntityArmorStand var1, float var2, float var3, float var4) {
      GlStateManager.func_179114_b(180.0F - ☃, 0.0F, 1.0F, 0.0F);
      float ☃ = (float)(☃.field_70170_p.func_82737_E() - ☃.field_175437_i) + ☃;
      if (☃ < 5.0F) {
         GlStateManager.func_179114_b(MathHelper.func_76126_a(☃ / 1.5F * (float) Math.PI) * 3.0F, 0.0F, 1.0F, 0.0F);
      }
   }

   protected boolean func_177070_b(EntityArmorStand var1) {
      return ☃.func_174833_aM();
   }

   public void func_76986_a(EntityArmorStand var1, double var2, double var4, double var6, float var8, float var9) {
      if (☃.func_181026_s()) {
         this.field_188323_j = true;
      }

      super.func_76986_a(☃, ☃, ☃, ☃, ☃, ☃);
      if (☃.func_181026_s()) {
         this.field_188323_j = false;
      }
   }
}
