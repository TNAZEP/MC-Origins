package net.minecraft.world.entity.ai.goal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoalSelector {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final WrappedGoal NO_GOAL = new WrappedGoal(Integer.MAX_VALUE, new Goal() {
      @Override
      public boolean canUse() {
         return false;
      }
   }) {
      @Override
      public boolean isRunning() {
         return false;
      }
   };
   private final Map<Goal.Flag, WrappedGoal> lockedFlags = new EnumMap(Goal.Flag.class);
   private final Set<WrappedGoal> availableGoals = Sets.<WrappedGoal>newLinkedHashSet();
   private final Supplier<ProfilerFiller> profiler;
   private final EnumSet<Goal.Flag> disabledFlags = EnumSet.noneOf(Goal.Flag.class);
   private int tickCount;
   private int newGoalRate = 3;

   public GoalSelector(Supplier<ProfilerFiller> var1) {
      this.profiler = â˜ƒ;
   }

   public void addGoal(int var1, Goal var2) {
      this.availableGoals.add(new WrappedGoal(â˜ƒ, â˜ƒ));
   }

   @VisibleForTesting
   public void removeAllGoals() {
      this.availableGoals.clear();
   }

   public void removeGoal(Goal var1) {
      this.availableGoals.stream().filter(var1x -> var1x.getGoal() == â˜ƒ).filter(WrappedGoal::isRunning).forEach(WrappedGoal::stop);
      this.availableGoals.removeIf(var1x -> var1x.getGoal() == â˜ƒ);
   }

   public void tick() {
      ProfilerFiller â˜ƒ = (ProfilerFiller)this.profiler.get();
      â˜ƒ.push("goalCleanup");
      this.getRunningGoals()
         .filter(var1x -> !var1x.isRunning() || var1x.getFlags().stream().anyMatch(this.disabledFlags::contains) || !var1x.canContinueToUse())
         .forEach(Goal::stop);
      this.lockedFlags.forEach((var1x, var2) -> {
         if (!var2.isRunning()) {
            this.lockedFlags.remove(var1x);
         }
      });
      â˜ƒ.pop();
      â˜ƒ.push("goalUpdate");
      this.availableGoals
         .stream()
         .filter(var0 -> !var0.isRunning())
         .filter(var1x -> var1x.getFlags().stream().noneMatch(this.disabledFlags::contains))
         .filter(var1x -> var1x.getFlags().stream().allMatch(var2 -> ((WrappedGoal)this.lockedFlags.getOrDefault(var2, NO_GOAL)).canBeReplacedBy(var1x)))
         .filter(WrappedGoal::canUse)
         .forEach(var1x -> {
            var1x.getFlags().forEach(var2 -> {
               WrappedGoal â˜ƒ = (WrappedGoal)this.lockedFlags.getOrDefault(var2, NO_GOAL);
               â˜ƒ.stop();
               this.lockedFlags.put(var2, var1x);
            });
            var1x.start();
         });
      â˜ƒ.pop();
      â˜ƒ.push("goalTick");
      this.getRunningGoals().forEach(WrappedGoal::tick);
      â˜ƒ.pop();
   }

   public Set<WrappedGoal> getAvailableGoals() {
      return this.availableGoals;
   }

   public Stream<WrappedGoal> getRunningGoals() {
      return this.availableGoals.stream().filter(WrappedGoal::isRunning);
   }

   public void setNewGoalRate(int var1) {
      this.newGoalRate = â˜ƒ;
   }

   public void disableControlFlag(Goal.Flag var1) {
      this.disabledFlags.add(â˜ƒ);
   }

   public void enableControlFlag(Goal.Flag var1) {
      this.disabledFlags.remove(â˜ƒ);
   }

   public void setControlFlag(Goal.Flag var1, boolean var2) {
      if (â˜ƒ) {
         this.enableControlFlag(â˜ƒ);
      } else {
         this.disableControlFlag(â˜ƒ);
      }
   }
}
