package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.entity.passive.AbstractHorse;

public abstract class RenderAbstractHorse<T extends AbstractHorse> extends RenderLiving<AbstractHorse> {
   private final float field_191360_j;

   public RenderAbstractHorse(RenderManager var1, ModelBase var2, float var3) {
      super(☃, ☃, 0.75F);
      this.field_191360_j = ☃;
   }

   protected void func_77041_b(AbstractHorse var1, float var2) {
      GlStateManager.func_179152_a(this.field_191360_j, this.field_191360_j, this.field_191360_j);
      super.func_77041_b((T)☃, ☃);
   }
}
