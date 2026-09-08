package net.minecraft;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.GraphicsCard;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

public class SystemReport {
   public static final long BYTES_PER_MEBIBYTE = 1048576L;
   private static final long ONE_GIGA = 1000000000L;
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String OPERATING_SYSTEM = System.getProperty("os.name")
      + " ("
      + System.getProperty("os.arch")
      + ") version "
      + System.getProperty("os.version");
   private static final String JAVA_VERSION = System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
   private static final String JAVA_VM_VERSION = System.getProperty("java.vm.name")
      + " ("
      + System.getProperty("java.vm.info")
      + "), "
      + System.getProperty("java.vm.vendor");
   private final Map<String, String> entries = Maps.newLinkedHashMap();

   public SystemReport() {
      this.setDetail("Minecraft Version", SharedConstants.getCurrentVersion().getName());
      this.setDetail("Minecraft Version ID", SharedConstants.getCurrentVersion().getId());
      this.setDetail("Operating System", OPERATING_SYSTEM);
      this.setDetail("Java Version", JAVA_VERSION);
      this.setDetail("Java VM Version", JAVA_VM_VERSION);
      this.setDetail("Memory", (Supplier<String>)(() -> {
         Runtime â˜ƒ = Runtime.getRuntime();
         long â˜ƒx = â˜ƒ.maxMemory();
         long â˜ƒxx = â˜ƒ.totalMemory();
         long â˜ƒxxx = â˜ƒ.freeMemory();
         long â˜ƒxxxx = â˜ƒx / 1048576L;
         long â˜ƒxxxxx = â˜ƒxx / 1048576L;
         long â˜ƒxxxxxx = â˜ƒxxx / 1048576L;
         return â˜ƒxxx + " bytes (" + â˜ƒxxxxxx + " MiB) / " + â˜ƒxx + " bytes (" + â˜ƒxxxxx + " MiB) up to " + â˜ƒx + " bytes (" + â˜ƒxxxx + " MiB)";
      }));
      this.setDetail("CPUs", (Supplier<String>)(() -> String.valueOf(Runtime.getRuntime().availableProcessors())));
      this.ignoreErrors("hardware", () -> this.putHardware(new SystemInfo()));
      this.setDetail("JVM Flags", (Supplier<String>)(() -> {
         List<String> â˜ƒ = (List)Util.getVmArguments().collect(Collectors.toList());
         return String.format("%d total; %s", â˜ƒ.size(), String.join(" ", â˜ƒ));
      }));
   }

   public void setDetail(String var1, String var2) {
      this.entries.put(â˜ƒ, â˜ƒ);
   }

   public void setDetail(String var1, Supplier<String> var2) {
      try {
         this.setDetail(â˜ƒ, (String)â˜ƒ.get());
      } catch (Exception var4) {
         LOGGER.warn("Failed to get system info for {}", â˜ƒ, var4);
         this.setDetail(â˜ƒ, "ERR");
      }
   }

   private void putHardware(SystemInfo var1) {
      HardwareAbstractionLayer â˜ƒ = â˜ƒ.getHardware();
      this.ignoreErrors("processor", () -> this.putProcessor(â˜ƒ.getProcessor()));
      this.ignoreErrors("graphics", () -> this.putGraphics(â˜ƒ.getGraphicsCards()));
      this.ignoreErrors("memory", () -> this.putMemory(â˜ƒ.getMemory()));
   }

   private void ignoreErrors(String var1, Runnable var2) {
      try {
         â˜ƒ.run();
      } catch (Throwable var4) {
         LOGGER.warn("Failed retrieving info for group {}", â˜ƒ, var4);
      }
   }

   private void putPhysicalMemory(List<PhysicalMemory> var1) {
      int â˜ƒ = 0;

      for(PhysicalMemory â˜ƒx : â˜ƒ) {
         String â˜ƒxx = String.format("Memory slot #%d ", â˜ƒ++);
         this.setDetail(â˜ƒxx + "capacity (MB)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getCapacity() / 1048576.0F)));
         this.setDetail(â˜ƒxx + "clockSpeed (GHz)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getClockSpeed() / 1.0E9F)));
         this.setDetail(â˜ƒxx + "type", â˜ƒx::getMemoryType);
      }
   }

   private void putVirtualMemory(VirtualMemory var1) {
      this.setDetail("Virtual memory max (MB)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getVirtualMax() / 1048576.0F)));
      this.setDetail("Virtual memory used (MB)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getVirtualInUse() / 1048576.0F)));
      this.setDetail("Swap memory total (MB)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getSwapTotal() / 1048576.0F)));
      this.setDetail("Swap memory used (MB)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getSwapUsed() / 1048576.0F)));
   }

   private void putMemory(GlobalMemory var1) {
      this.ignoreErrors("physical memory", () -> this.putPhysicalMemory(â˜ƒ.getPhysicalMemory()));
      this.ignoreErrors("virtual memory", () -> this.putVirtualMemory(â˜ƒ.getVirtualMemory()));
   }

   private void putGraphics(List<GraphicsCard> var1) {
      int â˜ƒ = 0;

      for(GraphicsCard â˜ƒx : â˜ƒ) {
         String â˜ƒxx = String.format("Graphics card #%d ", â˜ƒ++);
         this.setDetail(â˜ƒxx + "name", â˜ƒx::getName);
         this.setDetail(â˜ƒxx + "vendor", â˜ƒx::getVendor);
         this.setDetail(â˜ƒxx + "VRAM (MB)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getVRam() / 1048576.0F)));
         this.setDetail(â˜ƒxx + "deviceId", â˜ƒx::getDeviceId);
         this.setDetail(â˜ƒxx + "versionInfo", â˜ƒx::getVersionInfo);
      }
   }

   private void putProcessor(CentralProcessor var1) {
      ProcessorIdentifier â˜ƒ = â˜ƒ.getProcessorIdentifier();
      this.setDetail("Processor Vendor", â˜ƒ::getVendor);
      this.setDetail("Processor Name", â˜ƒ::getName);
      this.setDetail("Identifier", â˜ƒ::getIdentifier);
      this.setDetail("Microarchitecture", â˜ƒ::getMicroarchitecture);
      this.setDetail("Frequency (GHz)", (Supplier<String>)(() -> String.format("%.2f", (float)â˜ƒ.getVendorFreq() / 1.0E9F)));
      this.setDetail("Number of physical packages", (Supplier<String>)(() -> String.valueOf(â˜ƒ.getPhysicalPackageCount())));
      this.setDetail("Number of physical CPUs", (Supplier<String>)(() -> String.valueOf(â˜ƒ.getPhysicalProcessorCount())));
      this.setDetail("Number of logical CPUs", (Supplier<String>)(() -> String.valueOf(â˜ƒ.getLogicalProcessorCount())));
   }

   public void appendToCrashReportString(StringBuilder var1) {
      â˜ƒ.append("-- ").append("System Details").append(" --\n");
      â˜ƒ.append("Details:");
      this.entries.forEach((var1x, var2) -> {
         â˜ƒ.append("\n\t");
         â˜ƒ.append(var1x);
         â˜ƒ.append(": ");
         â˜ƒ.append(var2);
      });
   }

   public String toLineSeparatedString() {
      return (String)this.entries
         .entrySet()
         .stream()
         .map(var0 -> (String)var0.getKey() + ": " + (String)var0.getValue())
         .collect(Collectors.joining(System.lineSeparator()));
   }
}
