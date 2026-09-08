package net.minecraft.world.entity.schedule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import java.util.Collection;
import java.util.List;

public class Timeline {
   private final List<Keyframe> keyframes = Lists.<Keyframe>newArrayList();
   private int previousIndex;

   public ImmutableList<Keyframe> getKeyframes() {
      return ImmutableList.copyOf(this.keyframes);
   }

   public Timeline addKeyframe(int var1, float var2) {
      this.keyframes.add(new Keyframe(â˜ƒ, â˜ƒ));
      this.sortAndDeduplicateKeyframes();
      return this;
   }

   public Timeline addKeyframes(Collection<Keyframe> var1) {
      this.keyframes.addAll(â˜ƒ);
      this.sortAndDeduplicateKeyframes();
      return this;
   }

   private void sortAndDeduplicateKeyframes() {
      Int2ObjectSortedMap<Keyframe> â˜ƒ = new Int2ObjectAVLTreeMap<>();
      this.keyframes.forEach(var1x -> â˜ƒ.put(var1x.getTimeStamp(), var1x));
      this.keyframes.clear();
      this.keyframes.addAll(â˜ƒ.values());
      this.previousIndex = 0;
   }

   public float getValueAt(int var1) {
      if (this.keyframes.size() <= 0) {
         return 0.0F;
      } else {
         Keyframe â˜ƒ = (Keyframe)this.keyframes.get(this.previousIndex);
         Keyframe â˜ƒx = (Keyframe)this.keyframes.get(this.keyframes.size() - 1);
         boolean â˜ƒxx = â˜ƒ < â˜ƒ.getTimeStamp();
         int â˜ƒxxx = â˜ƒxx ? 0 : this.previousIndex;
         float â˜ƒxxxx = â˜ƒxx ? â˜ƒx.getValue() : â˜ƒ.getValue();

         for(int â˜ƒxxxxx = â˜ƒxxx; â˜ƒxxxxx < this.keyframes.size(); ++â˜ƒxxxxx) {
            Keyframe â˜ƒxxxxxx = (Keyframe)this.keyframes.get(â˜ƒxxxxx);
            if (â˜ƒxxxxxx.getTimeStamp() > â˜ƒ) {
               break;
            }

            this.previousIndex = â˜ƒxxxxx;
            â˜ƒxxxx = â˜ƒxxxxxx.getValue();
         }

         return â˜ƒxxxx;
      }
   }
}
