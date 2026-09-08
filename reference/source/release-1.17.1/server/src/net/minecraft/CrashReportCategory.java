package net.minecraft;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CrashReportCategory {
   private final String title;
   private final List<CrashReportCategory.Entry> entries = Lists.<CrashReportCategory.Entry>newArrayList();
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReportCategory(String var1) {
      this.title = â˜ƒ;
   }

   public static String formatLocation(LevelHeightAccessor var0, double var1, double var3, double var5) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", â˜ƒ, â˜ƒ, â˜ƒ, formatLocation(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ)));
   }

   public static String formatLocation(LevelHeightAccessor var0, BlockPos var1) {
      return formatLocation(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public static String formatLocation(LevelHeightAccessor var0, int var1, int var2, int var3) {
      StringBuilder â˜ƒ = new StringBuilder();

      try {
         â˜ƒ.append(String.format("World: (%d,%d,%d)", â˜ƒ, â˜ƒ, â˜ƒ));
      } catch (Throwable var19) {
         â˜ƒ.append("(Error finding world loc)");
      }

      â˜ƒ.append(", ");

      try {
         int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ);
         int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ);
         int â˜ƒxxx = SectionPos.blockToSectionCoord(â˜ƒ);
         int â˜ƒxxxx = â˜ƒ & 15;
         int â˜ƒxxxxx = â˜ƒ & 15;
         int â˜ƒxxxxxx = â˜ƒ & 15;
         int â˜ƒxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒx);
         int â˜ƒxxxxxxxx = â˜ƒ.getMinBuildHeight();
         int â˜ƒxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒxxx);
         int â˜ƒxxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒx + 1) - 1;
         int â˜ƒxxxxxxxxxxx = â˜ƒ.getMaxBuildHeight() - 1;
         int â˜ƒxxxxxxxxxxxx = SectionPos.sectionToBlockCoord(â˜ƒxxx + 1) - 1;
         â˜ƒ.append(
            String.format(
               "Section: (at %d,%d,%d in %d,%d,%d; chunk contains blocks %d,%d,%d to %d,%d,%d)",
               â˜ƒxxxx,
               â˜ƒxxxxx,
               â˜ƒxxxxxx,
               â˜ƒx,
               â˜ƒxx,
               â˜ƒxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxx,
               â˜ƒxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx
            )
         );
      } catch (Throwable var18) {
         â˜ƒ.append("(Error finding chunk loc)");
      }

      â˜ƒ.append(", ");

      try {
         int â˜ƒx = â˜ƒ >> 9;
         int â˜ƒxx = â˜ƒ >> 9;
         int â˜ƒxxx = â˜ƒx << 5;
         int â˜ƒxxxx = â˜ƒxx << 5;
         int â˜ƒxxxxx = (â˜ƒx + 1 << 5) - 1;
         int â˜ƒxxxxxx = (â˜ƒxx + 1 << 5) - 1;
         int â˜ƒxxxxxxx = â˜ƒx << 9;
         int â˜ƒxxxxxxxx = â˜ƒ.getMinBuildHeight();
         int â˜ƒxxxxxxxxx = â˜ƒxx << 9;
         int â˜ƒxxxxxxxxxx = (â˜ƒx + 1 << 9) - 1;
         int â˜ƒxxxxxxxxxxx = â˜ƒ.getMaxBuildHeight() - 1;
         int â˜ƒxxxxxxxxxxxx = (â˜ƒxx + 1 << 9) - 1;
         â˜ƒ.append(
            String.format(
               "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,%d,%d to %d,%d,%d)",
               â˜ƒx,
               â˜ƒxx,
               â˜ƒxxx,
               â˜ƒxxxx,
               â˜ƒxxxxx,
               â˜ƒxxxxxx,
               â˜ƒxxxxxxx,
               â˜ƒxxxxxxxx,
               â˜ƒxxxxxxxxx,
               â˜ƒxxxxxxxxxx,
               â˜ƒxxxxxxxxxxx,
               â˜ƒxxxxxxxxxxxx
            )
         );
      } catch (Throwable var17) {
         â˜ƒ.append("(Error finding world loc)");
      }

      return â˜ƒ.toString();
   }

   public CrashReportCategory setDetail(String var1, CrashReportDetail<String> var2) {
      try {
         this.setDetail(â˜ƒ, â˜ƒ.call());
      } catch (Throwable var4) {
         this.setDetailError(â˜ƒ, var4);
      }

      return this;
   }

   public CrashReportCategory setDetail(String var1, Object var2) {
      this.entries.add(new CrashReportCategory.Entry(â˜ƒ, â˜ƒ));
      return this;
   }

   public void setDetailError(String var1, Throwable var2) {
      this.setDetail(â˜ƒ, â˜ƒ);
   }

   public int fillInStackTrace(int var1) {
      StackTraceElement[] â˜ƒ = Thread.currentThread().getStackTrace();
      if (â˜ƒ.length <= 0) {
         return 0;
      } else {
         this.stackTrace = new StackTraceElement[â˜ƒ.length - 3 - â˜ƒ];
         System.arraycopy(â˜ƒ, 3 + â˜ƒ, this.stackTrace, 0, this.stackTrace.length);
         return this.stackTrace.length;
      }
   }

   public boolean validateStackTrace(StackTraceElement var1, StackTraceElement var2) {
      if (this.stackTrace.length != 0 && â˜ƒ != null) {
         StackTraceElement â˜ƒ = this.stackTrace[0];
         if (â˜ƒ.isNativeMethod() == â˜ƒ.isNativeMethod()
            && â˜ƒ.getClassName().equals(â˜ƒ.getClassName())
            && â˜ƒ.getFileName().equals(â˜ƒ.getFileName())
            && â˜ƒ.getMethodName().equals(â˜ƒ.getMethodName())) {
            if (â˜ƒ != null != this.stackTrace.length > 1) {
               return false;
            } else if (â˜ƒ != null && !this.stackTrace[1].equals(â˜ƒ)) {
               return false;
            } else {
               this.stackTrace[0] = â˜ƒ;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void trimStacktrace(int var1) {
      StackTraceElement[] â˜ƒ = new StackTraceElement[this.stackTrace.length - â˜ƒ];
      System.arraycopy(this.stackTrace, 0, â˜ƒ, 0, â˜ƒ.length);
      this.stackTrace = â˜ƒ;
   }

   public void getDetails(StringBuilder var1) {
      â˜ƒ.append("-- ").append(this.title).append(" --\n");
      â˜ƒ.append("Details:");

      for(CrashReportCategory.Entry â˜ƒ : this.entries) {
         â˜ƒ.append("\n\t");
         â˜ƒ.append(â˜ƒ.getKey());
         â˜ƒ.append(": ");
         â˜ƒ.append(â˜ƒ.getValue());
      }

      if (this.stackTrace != null && this.stackTrace.length > 0) {
         â˜ƒ.append("\nStacktrace:");

         for(StackTraceElement â˜ƒ : this.stackTrace) {
            â˜ƒ.append("\n\tat ");
            â˜ƒ.append(â˜ƒ);
         }
      }
   }

   public StackTraceElement[] getStacktrace() {
      return this.stackTrace;
   }

   public static void populateBlockDetails(CrashReportCategory var0, LevelHeightAccessor var1, BlockPos var2, @Nullable BlockState var3) {
      if (â˜ƒ != null) {
         â˜ƒ.setDetail("Block", â˜ƒ::toString);
      }

      â˜ƒ.setDetail("Block location", (CrashReportDetail<String>)(() -> formatLocation(â˜ƒ, â˜ƒ)));
   }

   static class Entry {
      private final String key;
      private final String value;

      public Entry(String var1, @Nullable Object var2) {
         this.key = â˜ƒ;
         if (â˜ƒ == null) {
            this.value = "~~NULL~~";
         } else if (â˜ƒ instanceof Throwable â˜ƒ) {
            this.value = "~~ERROR~~ " + â˜ƒ.getClass().getSimpleName() + ": " + â˜ƒ.getMessage();
         } else {
            this.value = â˜ƒ.toString();
         }
      }

      public String getKey() {
         return this.key;
      }

      public String getValue() {
         return this.value;
      }
   }
}
