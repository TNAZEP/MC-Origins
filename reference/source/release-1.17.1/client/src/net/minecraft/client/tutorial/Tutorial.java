package net.minecraft.client.tutorial;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.Input;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.KeybindComponent;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class Tutorial {
   private final Minecraft minecraft;
   @Nullable
   private TutorialStepInstance instance;
   private final List<Tutorial.TimedToast> timedToasts = Lists.<Tutorial.TimedToast>newArrayList();
   private final BundleTutorial bundleTutorial;

   public Tutorial(Minecraft var1, Options var2) {
      this.minecraft = â˜ƒ;
      this.bundleTutorial = new BundleTutorial(this, â˜ƒ);
   }

   public void onInput(Input var1) {
      if (this.instance != null) {
         this.instance.onInput(â˜ƒ);
      }
   }

   public void onMouse(double var1, double var3) {
      if (this.instance != null) {
         this.instance.onMouse(â˜ƒ, â˜ƒ);
      }
   }

   public void onLookAt(@Nullable ClientLevel var1, @Nullable HitResult var2) {
      if (this.instance != null && â˜ƒ != null && â˜ƒ != null) {
         this.instance.onLookAt(â˜ƒ, â˜ƒ);
      }
   }

   public void onDestroyBlock(ClientLevel var1, BlockPos var2, BlockState var3, float var4) {
      if (this.instance != null) {
         this.instance.onDestroyBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public void onOpenInventory() {
      if (this.instance != null) {
         this.instance.onOpenInventory();
      }
   }

   public void onGetItem(ItemStack var1) {
      if (this.instance != null) {
         this.instance.onGetItem(â˜ƒ);
      }
   }

   public void stop() {
      if (this.instance != null) {
         this.instance.clear();
         this.instance = null;
      }
   }

   public void start() {
      if (this.instance != null) {
         this.stop();
      }

      this.instance = this.minecraft.options.tutorialStep.create(this);
   }

   public void addTimedToast(TutorialToast var1, int var2) {
      this.timedToasts.add(new Tutorial.TimedToast(â˜ƒ, â˜ƒ));
      this.minecraft.getToasts().addToast(â˜ƒ);
   }

   public void removeTimedToast(TutorialToast var1) {
      this.timedToasts.removeIf(var1x -> var1x.toast == â˜ƒ);
      â˜ƒ.hide();
   }

   public void tick() {
      this.timedToasts.removeIf(Tutorial.TimedToast::updateProgress);
      if (this.instance != null) {
         if (this.minecraft.level != null) {
            this.instance.tick();
         } else {
            this.stop();
         }
      } else if (this.minecraft.level != null) {
         this.start();
      }
   }

   public void setStep(TutorialSteps var1) {
      this.minecraft.options.tutorialStep = â˜ƒ;
      this.minecraft.options.save();
      if (this.instance != null) {
         this.instance.clear();
         this.instance = â˜ƒ.create(this);
      }
   }

   public Minecraft getMinecraft() {
      return this.minecraft;
   }

   public boolean isSurvival() {
      if (this.minecraft.gameMode == null) {
         return false;
      } else {
         return this.minecraft.gameMode.getPlayerMode() == GameType.SURVIVAL;
      }
   }

   public static Component key(String var0) {
      return new KeybindComponent("key." + â˜ƒ).withStyle(ChatFormatting.BOLD);
   }

   public void onInventoryAction(ItemStack var1, ItemStack var2, ClickAction var3) {
      this.bundleTutorial.onInventoryAction(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   static final class TimedToast {
      final TutorialToast toast;
      private final int durationTicks;
      private int progress;

      TimedToast(TutorialToast var1, int var2) {
         this.toast = â˜ƒ;
         this.durationTicks = â˜ƒ;
      }

      private boolean updateProgress() {
         this.toast.updateProgress(Math.min((float)(++this.progress) / (float)this.durationTicks, 1.0F));
         if (this.progress > this.durationTicks) {
            this.toast.hide();
            return true;
         } else {
            return false;
         }
      }
   }
}
