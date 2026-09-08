package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class TreeNodePosition {
   private final Advancement advancement;
   private final TreeNodePosition parent;
   private final TreeNodePosition previousSibling;
   private final int childIndex;
   private final List<TreeNodePosition> children = Lists.<TreeNodePosition>newArrayList();
   private TreeNodePosition ancestor;
   private TreeNodePosition thread;
   private int x;
   private float y;
   private float mod;
   private float change;
   private float shift;

   public TreeNodePosition(Advancement var1, @Nullable TreeNodePosition var2, @Nullable TreeNodePosition var3, int var4, int var5) {
      if (â˜ƒ.getDisplay() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.advancement = â˜ƒ;
         this.parent = â˜ƒ;
         this.previousSibling = â˜ƒ;
         this.childIndex = â˜ƒ;
         this.ancestor = this;
         this.x = â˜ƒ;
         this.y = -1.0F;
         TreeNodePosition â˜ƒ = null;

         for(Advancement â˜ƒx : â˜ƒ.getChildren()) {
            â˜ƒ = this.addChild(â˜ƒx, â˜ƒ);
         }
      }
   }

   @Nullable
   private TreeNodePosition addChild(Advancement var1, @Nullable TreeNodePosition var2) {
      if (â˜ƒ.getDisplay() != null) {
         â˜ƒ = new TreeNodePosition(â˜ƒ, this, â˜ƒ, this.children.size() + 1, this.x + 1);
         this.children.add(â˜ƒ);
      } else {
         for(Advancement â˜ƒ : â˜ƒ.getChildren()) {
            â˜ƒ = this.addChild(â˜ƒ, â˜ƒ);
         }
      }

      return â˜ƒ;
   }

   private void firstWalk() {
      if (this.children.isEmpty()) {
         if (this.previousSibling != null) {
            this.y = this.previousSibling.y + 1.0F;
         } else {
            this.y = 0.0F;
         }
      } else {
         TreeNodePosition â˜ƒ = null;

         for(TreeNodePosition â˜ƒx : this.children) {
            â˜ƒx.firstWalk();
            â˜ƒ = â˜ƒx.apportion(â˜ƒ == null ? â˜ƒx : â˜ƒ);
         }

         this.executeShifts();
         float â˜ƒx = (((TreeNodePosition)this.children.get(0)).y + ((TreeNodePosition)this.children.get(this.children.size() - 1)).y) / 2.0F;
         if (this.previousSibling != null) {
            this.y = this.previousSibling.y + 1.0F;
            this.mod = this.y - â˜ƒx;
         } else {
            this.y = â˜ƒx;
         }
      }
   }

   private float secondWalk(float var1, int var2, float var3) {
      this.y += â˜ƒ;
      this.x = â˜ƒ;
      if (this.y < â˜ƒ) {
         â˜ƒ = this.y;
      }

      for(TreeNodePosition â˜ƒ : this.children) {
         â˜ƒ = â˜ƒ.secondWalk(â˜ƒ + this.mod, â˜ƒ + 1, â˜ƒ);
      }

      return â˜ƒ;
   }

   private void thirdWalk(float var1) {
      this.y += â˜ƒ;

      for(TreeNodePosition â˜ƒ : this.children) {
         â˜ƒ.thirdWalk(â˜ƒ);
      }
   }

   private void executeShifts() {
      float â˜ƒ = 0.0F;
      float â˜ƒx = 0.0F;

      for(int â˜ƒxx = this.children.size() - 1; â˜ƒxx >= 0; --â˜ƒxx) {
         TreeNodePosition â˜ƒxxx = (TreeNodePosition)this.children.get(â˜ƒxx);
         â˜ƒxxx.y += â˜ƒ;
         â˜ƒxxx.mod += â˜ƒ;
         â˜ƒx += â˜ƒxxx.change;
         â˜ƒ += â˜ƒxxx.shift + â˜ƒx;
      }
   }

   @Nullable
   private TreeNodePosition previousOrThread() {
      if (this.thread != null) {
         return this.thread;
      } else {
         return !this.children.isEmpty() ? (TreeNodePosition)this.children.get(0) : null;
      }
   }

   @Nullable
   private TreeNodePosition nextOrThread() {
      if (this.thread != null) {
         return this.thread;
      } else {
         return !this.children.isEmpty() ? (TreeNodePosition)this.children.get(this.children.size() - 1) : null;
      }
   }

   private TreeNodePosition apportion(TreeNodePosition var1) {
      if (this.previousSibling == null) {
         return â˜ƒ;
      } else {
         TreeNodePosition â˜ƒ = this;
         TreeNodePosition â˜ƒx = this;
         TreeNodePosition â˜ƒxx = this.previousSibling;
         TreeNodePosition â˜ƒxxx = (TreeNodePosition)this.parent.children.get(0);
         float â˜ƒxxxx = this.mod;
         float â˜ƒxxxxx = this.mod;
         float â˜ƒxxxxxx = â˜ƒxx.mod;

         float â˜ƒ;
         for(â˜ƒ = â˜ƒxxx.mod; â˜ƒxx.nextOrThread() != null && â˜ƒ.previousOrThread() != null; â˜ƒxxxxx += â˜ƒx.mod) {
            â˜ƒxx = â˜ƒxx.nextOrThread();
            â˜ƒ = â˜ƒ.previousOrThread();
            â˜ƒxxx = â˜ƒxxx.previousOrThread();
            â˜ƒx = â˜ƒx.nextOrThread();
            â˜ƒx.ancestor = this;
            float â˜ƒxxxxxxx = â˜ƒxx.y + â˜ƒxxxxxx - (â˜ƒ.y + â˜ƒxxxx) + 1.0F;
            if (â˜ƒxxxxxxx > 0.0F) {
               â˜ƒxx.getAncestor(this, â˜ƒ).moveSubtree(this, â˜ƒxxxxxxx);
               â˜ƒxxxx += â˜ƒxxxxxxx;
               â˜ƒxxxxx += â˜ƒxxxxxxx;
            }

            â˜ƒxxxxxx += â˜ƒxx.mod;
            â˜ƒxxxx += â˜ƒ.mod;
            â˜ƒ += â˜ƒxxx.mod;
         }

         if (â˜ƒxx.nextOrThread() != null && â˜ƒx.nextOrThread() == null) {
            â˜ƒx.thread = â˜ƒxx.nextOrThread();
            â˜ƒx.mod += â˜ƒxxxxxx - â˜ƒxxxxx;
         } else {
            if (â˜ƒ.previousOrThread() != null && â˜ƒxxx.previousOrThread() == null) {
               â˜ƒxxx.thread = â˜ƒ.previousOrThread();
               â˜ƒxxx.mod += â˜ƒxxxx - â˜ƒ;
            }

            â˜ƒ = this;
         }

         return â˜ƒ;
      }
   }

   private void moveSubtree(TreeNodePosition var1, float var2) {
      float â˜ƒ = (float)(â˜ƒ.childIndex - this.childIndex);
      if (â˜ƒ != 0.0F) {
         â˜ƒ.change -= â˜ƒ / â˜ƒ;
         this.change += â˜ƒ / â˜ƒ;
      }

      â˜ƒ.shift += â˜ƒ;
      â˜ƒ.y += â˜ƒ;
      â˜ƒ.mod += â˜ƒ;
   }

   private TreeNodePosition getAncestor(TreeNodePosition var1, TreeNodePosition var2) {
      return this.ancestor != null && â˜ƒ.parent.children.contains(this.ancestor) ? this.ancestor : â˜ƒ;
   }

   private void finalizePosition() {
      if (this.advancement.getDisplay() != null) {
         this.advancement.getDisplay().setLocation((float)this.x, this.y);
      }

      if (!this.children.isEmpty()) {
         for(TreeNodePosition â˜ƒ : this.children) {
            â˜ƒ.finalizePosition();
         }
      }
   }

   public static void run(Advancement var0) {
      if (â˜ƒ.getDisplay() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         TreeNodePosition â˜ƒ = new TreeNodePosition(â˜ƒ, null, null, 1, 0);
         â˜ƒ.firstWalk();
         float â˜ƒx = â˜ƒ.secondWalk(0.0F, 0, â˜ƒ.y);
         if (â˜ƒx < 0.0F) {
            â˜ƒ.thirdWalk(-â˜ƒx);
         }

         â˜ƒ.finalizePosition();
      }
   }
}
