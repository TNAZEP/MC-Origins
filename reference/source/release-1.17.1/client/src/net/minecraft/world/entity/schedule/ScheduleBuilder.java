package net.minecraft.world.entity.schedule;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ScheduleBuilder {
   private final Schedule schedule;
   private final List<ScheduleBuilder.ActivityTransition> transitions = Lists.<ScheduleBuilder.ActivityTransition>newArrayList();

   public ScheduleBuilder(Schedule var1) {
      this.schedule = â˜ƒ;
   }

   public ScheduleBuilder changeActivityAt(int var1, Activity var2) {
      this.transitions.add(new ScheduleBuilder.ActivityTransition(â˜ƒ, â˜ƒ));
      return this;
   }

   public Schedule build() {
      ((Set)this.transitions.stream().map(ScheduleBuilder.ActivityTransition::getActivity).collect(Collectors.toSet()))
         .forEach(this.schedule::ensureTimelineExistsFor);
      this.transitions.forEach(var1 -> {
         Activity â˜ƒ = var1.getActivity();
         this.schedule.getAllTimelinesExceptFor(â˜ƒ).forEach(var1x -> var1x.addKeyframe(var1.getTime(), 0.0F));
         this.schedule.getTimelineFor(â˜ƒ).addKeyframe(var1.getTime(), 1.0F);
      });
      return this.schedule;
   }

   static class ActivityTransition {
      private final int time;
      private final Activity activity;

      public ActivityTransition(int var1, Activity var2) {
         this.time = â˜ƒ;
         this.activity = â˜ƒ;
      }

      public int getTime() {
         return this.time;
      }

      public Activity getActivity() {
         return this.activity;
      }
   }
}
