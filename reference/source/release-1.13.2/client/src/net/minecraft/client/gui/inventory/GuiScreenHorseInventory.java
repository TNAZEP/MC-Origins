package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiScreenHorseInventory extends GuiContainer {
   private static final ResourceLocation field_147031_u = new ResourceLocation("textures/gui/container/horse.png");
   private final IInventory field_147030_v;
   private final IInventory field_147029_w;
   private final AbstractHorse field_147034_x;
   private float field_147033_y;
   private float field_147032_z;

   public GuiScreenHorseInventory(IInventory var1, IInventory var2, AbstractHorse var3) {
      super(new ContainerHorseInventory(☃, ☃, ☃, Minecraft.func_71410_x().field_71439_g));
      this.field_147030_v = ☃;
      this.field_147029_w = ☃;
      this.field_147034_x = ☃;
      this.field_146291_p = false;
   }

   @Override
   protected void func_146979_b(int var1, int var2) {
      this.field_146289_q.func_211126_b(this.field_147029_w.func_145748_c_().func_150254_d(), 8.0F, 6.0F, 4210752);
      this.field_146289_q.func_211126_b(this.field_147030_v.func_145748_c_().func_150254_d(), 8.0F, (float)(this.field_147000_g - 96 + 2), 4210752);
   }

   @Override
   protected void func_146976_a(float var1, int var2, int var3) {
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_146297_k.func_110434_K().func_110577_a(field_147031_u);
      int ☃ = (this.field_146294_l - this.field_146999_f) / 2;
      int ☃x = (this.field_146295_m - this.field_147000_g) / 2;
      this.func_73729_b(☃, ☃x, 0, 0, this.field_146999_f, this.field_147000_g);
      if (this.field_147034_x instanceof AbstractChestHorse) {
         AbstractChestHorse ☃xx = (AbstractChestHorse)this.field_147034_x;
         if (☃xx.func_190695_dh()) {
            this.func_73729_b(☃ + 79, ☃x + 17, 0, this.field_147000_g, ☃xx.func_190696_dl() * 18, 54);
         }
      }

      if (this.field_147034_x.func_190685_dA()) {
         this.func_73729_b(☃ + 7, ☃x + 35 - 18, 18, this.field_147000_g + 54, 18, 18);
      }

      if (this.field_147034_x.func_190677_dK()) {
         if (this.field_147034_x instanceof EntityLlama) {
            this.func_73729_b(☃ + 7, ☃x + 35, 36, this.field_147000_g + 54, 18, 18);
         } else {
            this.func_73729_b(☃ + 7, ☃x + 35, 0, this.field_147000_g + 54, 18, 18);
         }
      }

      GuiInventory.func_147046_a(☃ + 51, ☃x + 60, 17, (float)(☃ + 51) - this.field_147033_y, (float)(☃x + 75 - 50) - this.field_147032_z, this.field_147034_x);
   }

   @Override
   public void func_73863_a(int var1, int var2, float var3) {
      this.func_146276_q_();
      this.field_147033_y = (float)☃;
      this.field_147032_z = (float)☃;
      super.func_73863_a(☃, ☃, ☃);
      this.func_191948_b(☃, ☃);
   }
}
