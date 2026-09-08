package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {
   public EntityMinecartChest(World var1) {
      super(EntityType.field_200773_M, ☃);
   }

   public EntityMinecartChest(World var1, double var2, double var4, double var6) {
      super(EntityType.field_200773_M, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_94095_a(DamageSource var1) {
      super.func_94095_a(☃);
      if (this.field_70170_p.func_82736_K().func_82766_b("doEntityDrops")) {
         this.func_199703_a(Blocks.field_150486_ae);
      }
   }

   @Override
   public int func_70302_i_() {
      return 27;
   }

   @Override
   public EntityMinecart.Type func_184264_v() {
      return EntityMinecart.Type.CHEST;
   }

   @Override
   public IBlockState func_180457_u() {
      return Blocks.field_150486_ae.func_176223_P().func_206870_a(BlockChest.field_176459_a, EnumFacing.NORTH);
   }

   @Override
   public int func_94085_r() {
      return 8;
   }

   @Override
   public String func_174875_k() {
      return "minecraft:chest";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      this.func_184288_f(☃);
      return new ContainerChest(☃, this, ☃);
   }
}
