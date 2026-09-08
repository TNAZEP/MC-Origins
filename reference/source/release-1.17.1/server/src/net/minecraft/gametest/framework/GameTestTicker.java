package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Collection;

public class GameTestTicker {
   public static final GameTestTicker SINGLETON = new GameTestTicker();
   private final Collection<GameTestInfo> testInfos = Lists.<GameTestInfo>newCopyOnWriteArrayList();

   public void add(GameTestInfo var1) {
      this.testInfos.add(â˜ƒ);
   }

   public void clear() {
      this.testInfos.clear();
   }

   public void tick() {
      this.testInfos.forEach(GameTestInfo::tick);
      this.testInfos.removeIf(GameTestInfo::isDone);
   }
}
