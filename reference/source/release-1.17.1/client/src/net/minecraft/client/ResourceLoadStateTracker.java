package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.server.packs.PackResources;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceLoadStateTracker {
   private static final Logger LOGGER = LogManager.getLogger();
   @Nullable
   private ResourceLoadStateTracker.ReloadState reloadState;
   private int reloadCount;

   public void startReload(ResourceLoadStateTracker.ReloadReason var1, List<PackResources> var2) {
      ++this.reloadCount;
      if (this.reloadState != null && !this.reloadState.finished) {
         LOGGER.warn("Reload already ongoing, replacing");
      }

      this.reloadState = new ResourceLoadStateTracker.ReloadState(
         â˜ƒ, (List<String>)â˜ƒ.stream().map(PackResources::getName).collect(ImmutableList.toImmutableList())
      );
   }

   public void startRecovery(Throwable var1) {
      if (this.reloadState == null) {
         LOGGER.warn("Trying to signal reload recovery, but nothing was started");
         this.reloadState = new ResourceLoadStateTracker.ReloadState(ResourceLoadStateTracker.ReloadReason.UNKNOWN, ImmutableList.of());
      }

      this.reloadState.recoveryReloadInfo = new ResourceLoadStateTracker.RecoveryInfo(â˜ƒ);
   }

   public void finishReload() {
      if (this.reloadState == null) {
         LOGGER.warn("Trying to finish reload, but nothing was started");
      } else {
         this.reloadState.finished = true;
      }
   }

   public void fillCrashReport(CrashReport var1) {
      CrashReportCategory â˜ƒ = â˜ƒ.addCategory("Last reload");
      â˜ƒ.setDetail("Reload number", this.reloadCount);
      if (this.reloadState != null) {
         this.reloadState.fillCrashInfo(â˜ƒ);
      }
   }

   static class RecoveryInfo {
      private final Throwable error;

      RecoveryInfo(Throwable var1) {
         this.error = â˜ƒ;
      }

      public void fillCrashInfo(CrashReportCategory var1) {
         â˜ƒ.setDetail("Recovery", "Yes");
         â˜ƒ.setDetail("Recovery reason", (CrashReportDetail<String>)(() -> {
            StringWriter â˜ƒ = new StringWriter();
            this.error.printStackTrace(new PrintWriter(â˜ƒ));
            return â˜ƒ.toString();
         }));
      }
   }

   public static enum ReloadReason {
      INITIAL("initial"),
      MANUAL("manual"),
      UNKNOWN("unknown");

      final String name;

      private ReloadReason(String var3) {
         this.name = â˜ƒ;
      }
   }

   static class ReloadState {
      private final ResourceLoadStateTracker.ReloadReason reloadReason;
      private final List<String> packs;
      @Nullable
      ResourceLoadStateTracker.RecoveryInfo recoveryReloadInfo;
      boolean finished;

      ReloadState(ResourceLoadStateTracker.ReloadReason var1, List<String> var2) {
         this.reloadReason = â˜ƒ;
         this.packs = â˜ƒ;
      }

      public void fillCrashInfo(CrashReportCategory var1) {
         â˜ƒ.setDetail("Reload reason", this.reloadReason.name);
         â˜ƒ.setDetail("Finished", this.finished ? "Yes" : "No");
         â˜ƒ.setDetail("Packs", (CrashReportDetail<String>)(() -> String.join(", ", this.packs)));
         if (this.recoveryReloadInfo != null) {
            this.recoveryReloadInfo.fillCrashInfo(â˜ƒ);
         }
      }
   }
}
