package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieRenderer extends AbstractZombieRenderer<Zombie, ZombieModel<Zombie>> {
   public ZombieRenderer(EntityRendererProvider.Context var1) {
      this(â˜ƒ, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
   }

   public ZombieRenderer(EntityRendererProvider.Context var1, ModelLayerLocation var2, ModelLayerLocation var3, ModelLayerLocation var4) {
      super(â˜ƒ, new ZombieModel<>(â˜ƒ.bakeLayer(â˜ƒ)), new ZombieModel<>(â˜ƒ.bakeLayer(â˜ƒ)), new ZombieModel<>(â˜ƒ.bakeLayer(â˜ƒ)));
   }
}
