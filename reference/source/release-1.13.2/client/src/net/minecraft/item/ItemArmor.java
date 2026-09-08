package net.minecraft.item;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemArmor extends Item {
   private static final UUID[] field_185084_n = new UUID[]{
      UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
      UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
      UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
      UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
   };
   public static final IBehaviorDispenseItem field_96605_cw = new BehaviorDefaultDispenseItem() {
      @Override
      protected ItemStack func_82487_b(IBlockSource var1, ItemStack var2) {
         ItemStack ☃ = ItemArmor.func_185082_a(☃, ☃);
         return ☃.func_190926_b() ? super.func_82487_b(☃, ☃) : ☃;
      }
   };
   protected final EntityEquipmentSlot field_77881_a;
   protected final int field_77879_b;
   protected final float field_189415_e;
   protected final IArmorMaterial field_200882_e;

   public static ItemStack func_185082_a(IBlockSource var0, ItemStack var1) {
      BlockPos ☃ = ☃.func_180699_d().func_177972_a(☃.func_189992_e().func_177229_b(BlockDispenser.field_176441_a));
      List<EntityLivingBase> ☃x = ☃.func_197524_h()
         .func_175647_a(EntityLivingBase.class, new AxisAlignedBB(☃), EntitySelectors.field_180132_d.and(new EntitySelectors.ArmoredMob(☃)));
      if (☃x.isEmpty()) {
         return ItemStack.field_190927_a;
      } else {
         EntityLivingBase ☃ = (EntityLivingBase)☃x.get(0);
         EntityEquipmentSlot ☃x = EntityLiving.func_184640_d(☃);
         ItemStack ☃xx = ☃.func_77979_a(1);
         ☃.func_184201_a(☃x, ☃xx);
         if (☃ instanceof EntityLiving) {
            ((EntityLiving)☃).func_184642_a(☃x, 2.0F);
            ((EntityLiving)☃).func_110163_bv();
         }

         return ☃;
      }
   }

   public ItemArmor(IArmorMaterial var1, EntityEquipmentSlot var2, Item.Properties var3) {
      super(☃.func_200915_b(☃.func_200896_a(☃)));
      this.field_200882_e = ☃;
      this.field_77881_a = ☃;
      this.field_77879_b = ☃.func_200902_b(☃);
      this.field_189415_e = ☃.func_200901_e();
      BlockDispenser.func_199774_a(this, field_96605_cw);
   }

   public EntityEquipmentSlot func_185083_B_() {
      return this.field_77881_a;
   }

   @Override
   public int func_77619_b() {
      return this.field_200882_e.func_200900_a();
   }

   public IArmorMaterial func_200880_d() {
      return this.field_200882_e;
   }

   @Override
   public boolean func_82789_a(ItemStack var1, ItemStack var2) {
      return this.field_200882_e.func_200898_c().test(☃) || super.func_82789_a(☃, ☃);
   }

   @Override
   public ActionResult<ItemStack> func_77659_a(World var1, EntityPlayer var2, EnumHand var3) {
      ItemStack ☃ = ☃.func_184586_b(☃);
      EntityEquipmentSlot ☃x = EntityLiving.func_184640_d(☃);
      ItemStack ☃xx = ☃.func_184582_a(☃x);
      if (☃xx.func_190926_b()) {
         ☃.func_184201_a(☃x, ☃.func_77946_l());
         ☃.func_190920_e(0);
         return new ActionResult<>(EnumActionResult.SUCCESS, ☃);
      } else {
         return new ActionResult<>(EnumActionResult.FAIL, ☃);
      }
   }

   @Override
   public Multimap<String, AttributeModifier> func_111205_h(EntityEquipmentSlot var1) {
      Multimap<String, AttributeModifier> ☃ = super.func_111205_h(☃);
      if (☃ == this.field_77881_a) {
         ☃.put(
            SharedMonsterAttributes.field_188791_g.func_111108_a(),
            new AttributeModifier(field_185084_n[☃.func_188454_b()], "Armor modifier", (double)this.field_77879_b, 0)
         );
         ☃.put(
            SharedMonsterAttributes.field_189429_h.func_111108_a(),
            new AttributeModifier(field_185084_n[☃.func_188454_b()], "Armor toughness", (double)this.field_189415_e, 0)
         );
      }

      return ☃;
   }

   public int func_200881_e() {
      return this.field_77879_b;
   }
}
