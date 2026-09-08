package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class MultipleTestTracker {
   private static final char NOT_STARTED_TEST_CHAR = ' ';
   private static final char ONGOING_TEST_CHAR = '_';
   private static final char SUCCESSFUL_TEST_CHAR = '+';
   private static final char FAILED_OPTIONAL_TEST_CHAR = 'x';
   private static final char FAILED_REQUIRED_TEST_CHAR = 'X';
   private final Collection<GameTestInfo> tests = Lists.<GameTestInfo>newArrayList();
   @Nullable
   private final Collection<GameTestListener> listeners = Lists.<GameTestListener>newArrayList();

   public MultipleTestTracker() {
   }

   public MultipleTestTracker(Collection<GameTestInfo> var1) {
      this.tests.addAll(â˜ƒ);
   }

   public void addTestToTrack(GameTestInfo var1) {
      this.tests.add(â˜ƒ);
      this.listeners.forEach(â˜ƒ::addListener);
   }

   public void addListener(GameTestListener var1) {
      this.listeners.add(â˜ƒ);
      this.tests.forEach(var1x -> var1x.addListener(â˜ƒ));
   }

   public void addFailureListener(final Consumer<GameTestInfo> var1) {
      this.addListener(new GameTestListener() {
         @Override
         public void testStructureLoaded(GameTestInfo var1x) {
         }

         @Override
         public void testPassed(GameTestInfo var1x) {
         }

         @Override
         public void testFailed(GameTestInfo var1x) {
            â˜ƒ.accept(â˜ƒ);
         }
      });
   }

   public int getFailedRequiredCount() {
      return (int)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isRequired).count();
   }

   public int getFailedOptionalCount() {
      return (int)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isOptional).count();
   }

   public int getDoneCount() {
      return (int)this.tests.stream().filter(GameTestInfo::isDone).count();
   }

   public boolean hasFailedRequired() {
      return this.getFailedRequiredCount() > 0;
   }

   public boolean hasFailedOptional() {
      return this.getFailedOptionalCount() > 0;
   }

   public Collection<GameTestInfo> getFailedRequired() {
      return (Collection<GameTestInfo>)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isRequired).collect(Collectors.toList());
   }

   public Collection<GameTestInfo> getFailedOptional() {
      return (Collection<GameTestInfo>)this.tests.stream().filter(GameTestInfo::hasFailed).filter(GameTestInfo::isOptional).collect(Collectors.toList());
   }

   public int getTotalCount() {
      return this.tests.size();
   }

   public boolean isDone() {
      return this.getDoneCount() == this.getTotalCount();
   }

   public String getProgressBar() {
      StringBuffer â˜ƒ = new StringBuffer();
      â˜ƒ.append('[');
      this.tests.forEach(var1x -> {
         if (!var1x.hasStarted()) {
            â˜ƒ.append(' ');
         } else if (var1x.hasSucceeded()) {
            â˜ƒ.append('+');
         } else if (var1x.hasFailed()) {
            â˜ƒ.append((char)(var1x.isRequired() ? 'X' : 'x'));
         } else {
            â˜ƒ.append('_');
         }
      });
      â˜ƒ.append(']');
      return â˜ƒ.toString();
   }

   public String toString() {
      return this.getProgressBar();
   }
}
