package net.minecraft.gametest.framework;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeamcityTestReporter implements TestReporter {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Escaper ESCAPER = Escapers.builder()
      .addEscape('\'', "|'")
      .addEscape('\n', "|n")
      .addEscape('\r', "|r")
      .addEscape('|', "||")
      .addEscape('[', "|[")
      .addEscape(']', "|]")
      .build();

   @Override
   public void onTestFailed(GameTestInfo var1) {
      String â˜ƒ = ESCAPER.escape(â˜ƒ.getTestName());
      String â˜ƒx = ESCAPER.escape(â˜ƒ.getError().getMessage());
      String â˜ƒxx = ESCAPER.escape(Util.describeError(â˜ƒ.getError()));
      LOGGER.info("##teamcity[testStarted name='{}']", â˜ƒ);
      if (â˜ƒ.isRequired()) {
         LOGGER.info("##teamcity[testFailed name='{}' message='{}' details='{}']", â˜ƒ, â˜ƒx, â˜ƒxx);
      } else {
         LOGGER.info("##teamcity[testIgnored name='{}' message='{}' details='{}']", â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      LOGGER.info("##teamcity[testFinished name='{}' duration='{}']", â˜ƒ, â˜ƒ.getRunTime());
   }

   @Override
   public void onTestSuccess(GameTestInfo var1) {
      String â˜ƒ = ESCAPER.escape(â˜ƒ.getTestName());
      LOGGER.info("##teamcity[testStarted name='{}']", â˜ƒ);
      LOGGER.info("##teamcity[testFinished name='{}' duration='{}']", â˜ƒ, â˜ƒ.getRunTime());
   }
}
