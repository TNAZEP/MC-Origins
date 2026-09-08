package net.minecraft.world.damagesource;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class IndirectEntityDamageSource extends EntityDamageSource {
   @Nullable
   private final Entity owner;

   public IndirectEntityDamageSource(String var1, Entity var2, @Nullable Entity var3) {
      super(â˜ƒ, â˜ƒ);
      this.owner = â˜ƒ;
   }

   @Nullable
   @Override
   public Entity getDirectEntity() {
      return this.entity;
   }

   @Nullable
   @Override
   public Entity getEntity() {
      return this.owner;
   }

   @Override
   public Component getLocalizedDeathMessage(LivingEntity var1) {
      Component â˜ƒ = this.owner == null ? this.entity.getDisplayName() : this.owner.getDisplayName();
      ItemStack â˜ƒx = this.owner instanceof LivingEntity ? ((LivingEntity)this.owner).getMainHandItem() : ItemStack.EMPTY;
      String â˜ƒxx = "death.attack." + this.msgId;
      String â˜ƒxxx = â˜ƒxx + ".item";
      return !â˜ƒx.isEmpty() && â˜ƒx.hasCustomHoverName()
         ? new TranslatableComponent(â˜ƒxxx, â˜ƒ.getDisplayName(), â˜ƒ, â˜ƒx.getDisplayName())
         : new TranslatableComponent(â˜ƒxx, â˜ƒ.getDisplayName(), â˜ƒ);
   }
}
