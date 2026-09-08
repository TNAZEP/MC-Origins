package net.minecraft.client.tutorial;

import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PunchTreeTutorialStepInstance implements TutorialStepInstance {
   private static final int HINT_DELAY = 600;
   private static final Component TITLE = new TranslatableComponent("tutorial.punch_tree.title");
   private static final Component DESCRIPTION = new TranslatableComponent("tutorial.punch_tree.description", Tutorial.key("attack"));
   private final Tutorial tutorial;
   private TutorialToast toast;
   private int timeWaiting;
   private int resetCount;

   public PunchTreeTutorialStepInstance(Tutorial var1) {
      this.tutorial = â˜ƒ;
   }

   @Override
   public void tick() {
      ++this.timeWaiting;
      if (!this.tutorial.isSurvival()) {
         this.tutorial.setStep(TutorialSteps.NONE);
      } else {
         if (this.timeWaiting == 1) {
            LocalPlayer â˜ƒ = this.tutorial.getMinecraft().player;
            if (â˜ƒ != null) {
               if (â˜ƒ.getInventory().contains(ItemTags.LOGS)) {
                  this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                  return;
               }

               if (FindTreeTutorialStepInstance.hasPunchedTreesPreviously(â˜ƒ)) {
                  this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
                  return;
               }
            }
         }

         if ((this.timeWaiting >= 600 || this.resetCount > 3) && this.toast == null) {
            this.toast = new TutorialToast(TutorialToast.Icons.TREE, TITLE, DESCRIPTION, true);
            this.tutorial.getMinecraft().getToasts().addToast(this.toast);
         }
      }
   }

   @Override
   public void clear() {
      if (this.toast != null) {
         this.toast.hide();
         this.toast = null;
      }
   }

   @Override
   public void onDestroyBlock(ClientLevel var1, BlockPos var2, BlockState var3, float var4) {
      boolean â˜ƒ = â˜ƒ.is(BlockTags.LOGS);
      if (â˜ƒ && â˜ƒ > 0.0F) {
         if (this.toast != null) {
            this.toast.updateProgress(â˜ƒ);
         }

         if (â˜ƒ >= 1.0F) {
            this.tutorial.setStep(TutorialSteps.OPEN_INVENTORY);
         }
      } else if (this.toast != null) {
         this.toast.updateProgress(0.0F);
      } else if (â˜ƒ) {
         ++this.resetCount;
      }
   }

   @Override
   public void onGetItem(ItemStack var1) {
      if (â˜ƒ.is(ItemTags.LOGS)) {
         this.tutorial.setStep(TutorialSteps.CRAFT_PLANKS);
      }
   }
}
