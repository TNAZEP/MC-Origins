package net.minecraft.util;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;

public final class EntitySelectors {
   public static final Predicate<Entity> field_94557_a = Entity::func_70089_S;
   public static final Predicate<EntityLivingBase> field_212545_b = EntityLivingBase::func_70089_S;
   public static final Predicate<Entity> field_152785_b = var0 -> var0.func_70089_S() && !var0.func_184207_aI() && !var0.func_184218_aH();
   public static final Predicate<Entity> field_96566_b = var0 -> var0 instanceof IInventory && var0.func_70089_S();
   public static final Predicate<Entity> field_188444_d = var0 -> !(var0 instanceof EntityPlayer)
         || !((EntityPlayer)var0).func_175149_v() && !((EntityPlayer)var0).func_184812_l_();
   public static final Predicate<Entity> field_180132_d = var0 -> !(var0 instanceof EntityPlayer) || !((EntityPlayer)var0).func_175149_v();

   public static Predicate<Entity> func_188443_a(double var0, double var2, double var4, double var6) {
      double ☃ = ☃ * ☃;
      return var8x -> var8x != null && var8x.func_70092_e(☃, ☃, ☃) <= ☃;
   }

   public static Predicate<Entity> func_200823_a(Entity var0) {
      Team ☃ = ☃.func_96124_cp();
      Team.CollisionRule ☃x = ☃ == null ? Team.CollisionRule.ALWAYS : ☃.func_186681_k();
      return (Predicate<Entity>)(☃x == Team.CollisionRule.NEVER ? Predicates.alwaysFalse() : field_180132_d.and(var3 -> {
         if (!var3.func_70104_M()) {
            return false;
         } else if (!☃.field_70170_p.field_72995_K || var3 instanceof EntityPlayer && ((EntityPlayer)var3).func_175144_cb()) {
            Team ☃ = var3.func_96124_cp();
            Team.CollisionRule ☃x = ☃ == null ? Team.CollisionRule.ALWAYS : ☃.func_186681_k();
            if (☃x == Team.CollisionRule.NEVER) {
               return false;
            } else {
               boolean ☃ = ☃ != null && ☃.func_142054_a(☃);
               if ((☃ == Team.CollisionRule.PUSH_OWN_TEAM || ☃x == Team.CollisionRule.PUSH_OWN_TEAM) && ☃) {
                  return false;
               } else {
                  return ☃ != Team.CollisionRule.PUSH_OTHER_TEAMS && ☃x != Team.CollisionRule.PUSH_OTHER_TEAMS || ☃;
               }
            }
         } else {
            return false;
         }
      }));
   }

   public static Predicate<Entity> func_200820_b(Entity var0) {
      return var1 -> {
         while(var1.func_184218_aH()) {
            var1 = var1.func_184187_bx();
            if (var1 == ☃) {
               return false;
            }
         }

         return true;
      };
   }

   public static class ArmoredMob implements Predicate<Entity> {
      private final ItemStack field_96567_c;

      public ArmoredMob(ItemStack var1) {
         this.field_96567_c = ☃;
      }

      public boolean test(@Nullable Entity var1) {
         if (!☃.func_70089_S()) {
            return false;
         } else if (!(☃ instanceof EntityLivingBase)) {
            return false;
         } else {
            EntityLivingBase ☃ = (EntityLivingBase)☃;
            EntityEquipmentSlot ☃x = EntityLiving.func_184640_d(this.field_96567_c);
            if (!☃.func_184582_a(☃x).func_190926_b()) {
               return false;
            } else if (☃ instanceof EntityLiving) {
               return ((EntityLiving)☃).func_98052_bS();
            } else if (☃ instanceof EntityArmorStand) {
               return !((EntityArmorStand)☃).func_184796_b(☃x);
            } else {
               return ☃ instanceof EntityPlayer;
            }
         }
      }
   }
}
