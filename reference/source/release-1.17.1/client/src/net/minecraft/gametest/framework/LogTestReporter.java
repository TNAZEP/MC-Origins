package net.minecraft.gametest.framework;

import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTestReporter implements TestReporter {
   private static final Logger LOGGER = LogManager.getLogger();

   @Override
   public void onTestFailed(GameTestInfo var1) {
      if (â˜ƒ.isRequired()) {
         LOGGER.error("{} failed! {}", â˜ƒ.getTestName(), Util.describeError(â˜ƒ.getError()));
      } else {
         LOGGER.warn("(optional) {} failed. {}", â˜ƒ.getTestName(), Util.describeError(â˜ƒ.getError()));
      }
   }

   @Override
   public void onTestSuccess(GameTestInfo var1) {
   }
}
