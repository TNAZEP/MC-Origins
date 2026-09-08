package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class Sensing {
   private final Mob mob;
   private final List<Entity> seen = Lists.<Entity>newArrayList();
   private final List<Entity> unseen = Lists.<Entity>newArrayList();

   public Sensing(Mob var1) {
      this.mob = â˜ƒ;
   }

   public void tick() {
      this.seen.clear();
      this.unseen.clear();
   }

   public boolean hasLineOfSight(Entity var1) {
      if (this.seen.contains(â˜ƒ)) {
         return true;
      } else if (this.unseen.contains(â˜ƒ)) {
         return false;
      } else {
         this.mob.level.getProfiler().push("hasLineOfSight");
         boolean â˜ƒ = this.mob.hasLineOfSight(â˜ƒ);
         this.mob.level.getProfiler().pop();
         if (â˜ƒ) {
            this.seen.add(â˜ƒ);
         } else {
            this.unseen.add(â˜ƒ);
         }

         return â˜ƒ;
      }
   }
}
