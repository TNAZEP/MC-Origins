package net.minecraft.world.entity.schedule;

import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;

public class Schedule {
   public static final int WORK_START_TIME = 2000;
   public static final int TOTAL_WORK_TIME = 7000;
   public static final Schedule EMPTY = register("empty").changeActivityAt(0, Activity.IDLE).build();
   public static final Schedule SIMPLE = register("simple").changeActivityAt(5000, Activity.WORK).changeActivityAt(11000, Activity.REST).build();
   public static final Schedule VILLAGER_BABY = register("villager_baby")
      .changeActivityAt(10, Activity.IDLE)
      .changeActivityAt(3000, Activity.PLAY)
      .changeActivityAt(6000, Activity.IDLE)
      .changeActivityAt(10000, Activity.PLAY)
      .changeActivityAt(12000, Activity.REST)
      .build();
   public static final Schedule VILLAGER_DEFAULT = register("villager_default")
      .changeActivityAt(10, Activity.IDLE)
      .changeActivityAt(2000, Activity.WORK)
      .changeActivityAt(9000, Activity.MEET)
      .changeActivityAt(11000, Activity.IDLE)
      .changeActivityAt(12000, Activity.REST)
      .build();
   private final Map<Activity, Timeline> timelines = Maps.<Activity, Timeline>newHashMap();

   protected static ScheduleBuilder register(String var0) {
      Schedule â˜ƒ = Registry.register(Registry.SCHEDULE, â˜ƒ, new Schedule());
      return new ScheduleBuilder(â˜ƒ);
   }

   protected void ensureTimelineExistsFor(Activity var1) {
      if (!this.timelines.containsKey(â˜ƒ)) {
         this.timelines.put(â˜ƒ, new Timeline());
      }
   }

   protected Timeline getTimelineFor(Activity var1) {
      return (Timeline)this.timelines.get(â˜ƒ);
   }

   protected List<Timeline> getAllTimelinesExceptFor(Activity var1) {
      return (List<Timeline>)this.timelines.entrySet().stream().filter(var1x -> var1x.getKey() != â˜ƒ).map(Entry::getValue).collect(Collectors.toList());
   }

   public Activity getActivityAt(int var1) {
      return (Activity)this.timelines
         .entrySet()
         .stream()
         .max(Comparator.comparingDouble(var1x -> (double)((Timeline)var1x.getValue()).getValueAt(â˜ƒ)))
         .map(Entry::getKey)
         .orElse(Activity.IDLE);
   }
}
