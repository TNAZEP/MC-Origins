package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WorkAtComposter extends WorkAtPoi {
   private static final List<Item> COMPOSTABLE_ITEMS = ImmutableList.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS);

   @Override
   protected void useWorkstation(ServerLevel var1, Villager var2) {
      Optional<GlobalPos> â˜ƒ = â˜ƒ.getBrain().getMemory(MemoryModuleType.JOB_SITE);
      if (â˜ƒ.isPresent()) {
         GlobalPos â˜ƒx = (GlobalPos)â˜ƒ.get();
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx.pos());
         if (â˜ƒxx.is(Blocks.COMPOSTER)) {
            this.makeBread(â˜ƒ);
            this.compostItems(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
         }
      }
   }

   private void compostItems(ServerLevel var1, Villager var2, GlobalPos var3, BlockState var4) {
      BlockPos â˜ƒ = â˜ƒ.pos();
      if (â˜ƒ.getValue(ComposterBlock.LEVEL) == 8) {
         â˜ƒ = ComposterBlock.extractProduce(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      int â˜ƒ = 20;
      int â˜ƒx = 10;
      int[] â˜ƒxx = new int[COMPOSTABLE_ITEMS.size()];
      SimpleContainer â˜ƒxxx = â˜ƒ.getInventory();
      int â˜ƒxxxx = â˜ƒxxx.getContainerSize();
      BlockState â˜ƒxxxxx = â˜ƒ;

      for(int â˜ƒxxxxxx = â˜ƒxxxx - 1; â˜ƒxxxxxx >= 0 && â˜ƒ > 0; --â˜ƒxxxxxx) {
         ItemStack â˜ƒxxxxxxx = â˜ƒxxx.getItem(â˜ƒxxxxxx);
         int â˜ƒxxxxxxxx = COMPOSTABLE_ITEMS.indexOf(â˜ƒxxxxxxx.getItem());
         if (â˜ƒxxxxxxxx != -1) {
            int â˜ƒxxxxxxxxx = â˜ƒxxxxxxx.getCount();
            int â˜ƒxxxxxxxxxx = â˜ƒxx[â˜ƒxxxxxxxx] + â˜ƒxxxxxxxxx;
            â˜ƒxx[â˜ƒxxxxxxxx] = â˜ƒxxxxxxxxxx;
            int â˜ƒxxxxxxxxxxx = Math.min(Math.min(â˜ƒxxxxxxxxxx - 10, â˜ƒ), â˜ƒxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxx > 0) {
               â˜ƒ -= â˜ƒxxxxxxxxxxx;

               for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxx) {
                  â˜ƒxxxxx = ComposterBlock.insertItem(â˜ƒxxxxx, â˜ƒ, â˜ƒxxxxxxx, â˜ƒ);
                  if (â˜ƒxxxxx.getValue(ComposterBlock.LEVEL) == 7) {
                     this.spawnComposterFillEffects(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxx);
                     return;
                  }
               }
            }
         }
      }

      this.spawnComposterFillEffects(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxx);
   }

   private void spawnComposterFillEffects(ServerLevel var1, BlockState var2, BlockPos var3, BlockState var4) {
      â˜ƒ.levelEvent(1500, â˜ƒ, â˜ƒ != â˜ƒ ? 1 : 0);
   }

   private void makeBread(Villager var1) {
      SimpleContainer â˜ƒ = â˜ƒ.getInventory();
      if (â˜ƒ.countItem(Items.BREAD) <= 36) {
         int â˜ƒx = â˜ƒ.countItem(Items.WHEAT);
         int â˜ƒxx = 3;
         int â˜ƒxxx = 3;
         int â˜ƒxxxx = Math.min(3, â˜ƒx / 3);
         if (â˜ƒxxxx != 0) {
            int â˜ƒxxxxx = â˜ƒxxxx * 3;
            â˜ƒ.removeItemType(Items.WHEAT, â˜ƒxxxxx);
            ItemStack â˜ƒxxxxxx = â˜ƒ.addItem(new ItemStack(Items.BREAD, â˜ƒxxxx));
            if (!â˜ƒxxxxxx.isEmpty()) {
               â˜ƒ.spawnAtLocation(â˜ƒxxxxxx, 0.5F);
            }
         }
      }
   }
}
