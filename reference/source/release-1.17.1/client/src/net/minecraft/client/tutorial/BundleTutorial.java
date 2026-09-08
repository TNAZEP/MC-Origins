package net.minecraft.client.tutorial;

import javax.annotation.Nullable;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BundleTutorial {
   private final Tutorial tutorial;
   private final Options options;
   @Nullable
   private TutorialToast toast;

   public BundleTutorial(Tutorial var1, Options var2) {
      this.tutorial = â˜ƒ;
      this.options = â˜ƒ;
   }

   private void showToast() {
      if (this.toast != null) {
         this.tutorial.removeTimedToast(this.toast);
      }

      Component â˜ƒ = new TranslatableComponent("tutorial.bundleInsert.title");
      Component â˜ƒx = new TranslatableComponent("tutorial.bundleInsert.description");
      this.toast = new TutorialToast(TutorialToast.Icons.RIGHT_CLICK, â˜ƒ, â˜ƒx, true);
      this.tutorial.addTimedToast(this.toast, 160);
   }

   private void clearToast() {
      if (this.toast != null) {
         this.tutorial.removeTimedToast(this.toast);
         this.toast = null;
      }

      if (!this.options.hideBundleTutorial) {
         this.options.hideBundleTutorial = true;
         this.options.save();
      }
   }

   public void onInventoryAction(ItemStack var1, ItemStack var2, ClickAction var3) {
      if (!this.options.hideBundleTutorial) {
         if (!â˜ƒ.isEmpty() && â˜ƒ.is(Items.BUNDLE)) {
            if (â˜ƒ == ClickAction.PRIMARY) {
               this.showToast();
            } else if (â˜ƒ == ClickAction.SECONDARY) {
               this.clearToast();
            }
         } else if (â˜ƒ.is(Items.BUNDLE) && !â˜ƒ.isEmpty() && â˜ƒ == ClickAction.SECONDARY) {
            this.clearToast();
         }
      }
   }
}
