package net.minecraft.client.model;

import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public abstract class EntityModel<T extends Entity> extends Model {
   public float attackTime;
   public boolean riding;
   public boolean young = true;

   protected EntityModel() {
      this(RenderType::entityCutoutNoCull);
   }

   protected EntityModel(Function<ResourceLocation, RenderType> var1) {
      super(â˜ƒ);
   }

   public abstract void setupAnim(T var1, float var2, float var3, float var4, float var5, float var6);

   public void prepareMobModel(T var1, float var2, float var3, float var4) {
   }

   public void copyPropertiesTo(EntityModel<T> var1) {
      â˜ƒ.attackTime = this.attackTime;
      â˜ƒ.riding = this.riding;
      â˜ƒ.young = this.young;
   }
}
