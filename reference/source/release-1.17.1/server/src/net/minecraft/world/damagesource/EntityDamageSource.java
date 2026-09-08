package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class EntityDamageSource extends DamageSource {
   protected final Entity entity;
   private boolean isThorns;

   public EntityDamageSource(String var1, Entity var2) {
      super(â˜ƒ);
      this.entity = â˜ƒ;
   }

   public EntityDamageSource setThorns() {
      this.isThorns = true;
      return this;
   }

   public boolean isThorns() {
      return this.isThorns;
   }

   @Override
   public Entity getEntity() {
      return this.entity;
   }

   @Override
   public Component getLocalizedDeathMessage(LivingEntity var1) {
      ItemStack â˜ƒ = this.entity instanceof LivingEntity ? ((LivingEntity)this.entity).getMainHandItem() : ItemStack.EMPTY;
      String â˜ƒx = "death.attack." + this.msgId;
      return !â˜ƒ.isEmpty() && â˜ƒ.hasCustomHoverName()
         ? new TranslatableComponent(â˜ƒx + ".item", â˜ƒ.getDisplayName(), this.entity.getDisplayName(), â˜ƒ.getDisplayName())
         : new TranslatableComponent(â˜ƒx, â˜ƒ.getDisplayName(), this.entity.getDisplayName());
   }

   @Override
   public boolean scalesWithDifficulty() {
      return this.entity instanceof LivingEntity && !(this.entity instanceof Player);
   }

   @Nullable
   @Override
   public Vec3 getSourcePosition() {
      return this.entity.position();
   }

   @Override
   public String toString() {
      return "EntityDamageSource (" + this.entity + ")";
   }
}
