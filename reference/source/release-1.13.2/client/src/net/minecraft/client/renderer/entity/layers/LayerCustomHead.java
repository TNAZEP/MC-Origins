package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockAbstractSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import org.apache.commons.lang3.StringUtils;

public class LayerCustomHead implements LayerRenderer<EntityLivingBase> {
   private final ModelRenderer field_177209_a;

   public LayerCustomHead(ModelRenderer var1) {
      this.field_177209_a = ☃;
   }

   @Override
   public void func_177141_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack ☃ = ☃.func_184582_a(EntityEquipmentSlot.HEAD);
      if (!☃.func_190926_b()) {
         Item ☃x = ☃.func_77973_b();
         Minecraft ☃xx = Minecraft.func_71410_x();
         GlStateManager.func_179094_E();
         if (☃.func_70093_af()) {
            GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
         }

         boolean ☃x = ☃ instanceof EntityVillager || ☃ instanceof EntityZombieVillager;
         if (☃.func_70631_g_() && !(☃ instanceof EntityVillager)) {
            float ☃xx = 2.0F;
            float ☃xxx = 1.4F;
            GlStateManager.func_179109_b(0.0F, 0.5F * ☃, 0.0F);
            GlStateManager.func_179152_a(0.7F, 0.7F, 0.7F);
            GlStateManager.func_179109_b(0.0F, 16.0F * ☃, 0.0F);
         }

         this.field_177209_a.func_78794_c(0.0625F);
         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
         if (☃x instanceof ItemBlock && ((ItemBlock)☃x).func_179223_d() instanceof BlockAbstractSkull) {
            float ☃x = 1.1875F;
            GlStateManager.func_179152_a(1.1875F, -1.1875F, -1.1875F);
            if (☃x) {
               GlStateManager.func_179109_b(0.0F, 0.0625F, 0.0F);
            }

            GameProfile ☃x = null;
            if (☃.func_77942_o()) {
               NBTTagCompound ☃xx = ☃.func_77978_p();
               if (☃xx.func_150297_b("SkullOwner", 10)) {
                  ☃x = NBTUtil.func_152459_a(☃xx.func_74775_l("SkullOwner"));
               } else if (☃xx.func_150297_b("SkullOwner", 8)) {
                  String ☃xx = ☃xx.func_74779_i("SkullOwner");
                  if (!StringUtils.isBlank(☃xx)) {
                     ☃x = TileEntitySkull.func_174884_b(new GameProfile(null, ☃xx));
                     ☃xx.func_74782_a("SkullOwner", NBTUtil.func_180708_a(new NBTTagCompound(), ☃x));
                  }
               }
            }

            TileEntitySkullRenderer.field_147536_b
               .func_199355_a(-0.5F, 0.0F, -0.5F, null, 180.0F, ((BlockAbstractSkull)((ItemBlock)☃x).func_179223_d()).func_196292_N_(), ☃x, -1, ☃);
         } else if (!(☃x instanceof ItemArmor) || ((ItemArmor)☃x).func_185083_B_() != EntityEquipmentSlot.HEAD) {
            float ☃x = 0.625F;
            GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F);
            GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.func_179152_a(0.625F, -0.625F, -0.625F);
            if (☃x) {
               GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F);
            }

            ☃xx.func_175597_ag().func_178099_a(☃, ☃, ItemCameraTransforms.TransformType.HEAD);
         }

         GlStateManager.func_179121_F();
      }
   }

   @Override
   public boolean func_177142_b() {
      return false;
   }
}
