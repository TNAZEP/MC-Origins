package net.minecraft.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityGiantZombie extends EntityMob {
   public EntityGiantZombie(World var1) {
      super(EntityType.field_200812_z, ☃);
      this.func_70105_a(this.field_70130_N * 6.0F, this.field_70131_O * 6.0F);
   }

   @Override
   public float func_70047_e() {
      return 10.440001F;
   }

   @Override
   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0);
      this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5);
      this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0);
   }

   @Override
   public float func_205022_a(BlockPos var1, IWorldReaderBase var2) {
      return ☃.func_205052_D(☃) - 0.5F;
   }

   @Nullable
   @Override
   protected ResourceLocation func_184647_J() {
      return LootTableList.field_186437_s;
   }
}
