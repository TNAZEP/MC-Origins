package net.minecraft.util.monitoring.jmx;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MinecraftServerStatistics implements DynamicMBean {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftServer server;
   private final MBeanInfo mBeanInfo;
   private final Map<String, MinecraftServerStatistics.AttributeDescription> attributeDescriptionByName = (Map<String, MinecraftServerStatistics.AttributeDescription>)Stream.of(
         new MinecraftServerStatistics.AttributeDescription("tickTimes", this::getTickTimes, "Historical tick times (ms)", long[].class),
         new MinecraftServerStatistics.AttributeDescription("averageTickTime", this::getAverageTickTime, "Current average tick time (ms)", Long.TYPE)
      )
      .collect(Collectors.toMap(var0 -> var0.name, Function.identity()));

   private MinecraftServerStatistics(MinecraftServer var1) {
      this.server = â˜ƒ;
      MBeanAttributeInfo[] â˜ƒ = (MBeanAttributeInfo[])this.attributeDescriptionByName
         .values()
         .stream()
         .map(MinecraftServerStatistics.AttributeDescription::asMBeanAttributeInfo)
         .toArray(var0 -> new MBeanAttributeInfo[var0]);
      this.mBeanInfo = new MBeanInfo(
         MinecraftServerStatistics.class.getSimpleName(), "metrics for dedicated server", â˜ƒ, null, null, new MBeanNotificationInfo[0]
      );
   }

   public static void registerJmxMonitoring(MinecraftServer var0) {
      try {
         ManagementFactory.getPlatformMBeanServer().registerMBean(new MinecraftServerStatistics(â˜ƒ), new ObjectName("net.minecraft.server:type=Server"));
      } catch (InstanceAlreadyExistsException | MBeanRegistrationException | NotCompliantMBeanException | MalformedObjectNameException var2) {
         LOGGER.warn("Failed to initialise server as JMX bean", var2);
      }
   }

   private float getAverageTickTime() {
      return this.server.getAverageTickTime();
   }

   private long[] getTickTimes() {
      return this.server.tickTimes;
   }

   @Nullable
   public Object getAttribute(String var1) {
      MinecraftServerStatistics.AttributeDescription â˜ƒ = (MinecraftServerStatistics.AttributeDescription)this.attributeDescriptionByName.get(â˜ƒ);
      return â˜ƒ == null ? null : â˜ƒ.getter.get();
   }

   public void setAttribute(Attribute var1) {
   }

   public AttributeList getAttributes(String[] var1) {
      List<Attribute> â˜ƒ = (List)Arrays.stream(â˜ƒ)
         .map(this.attributeDescriptionByName::get)
         .filter(Objects::nonNull)
         .map(var0 -> new Attribute(var0.name, var0.getter.get()))
         .collect(Collectors.toList());
      return new AttributeList(â˜ƒ);
   }

   public AttributeList setAttributes(AttributeList var1) {
      return new AttributeList();
   }

   @Nullable
   public Object invoke(String var1, Object[] var2, String[] var3) {
      return null;
   }

   public MBeanInfo getMBeanInfo() {
      return this.mBeanInfo;
   }

   static final class AttributeDescription {
      final String name;
      final Supplier<Object> getter;
      private final String description;
      private final Class<?> type;

      AttributeDescription(String var1, Supplier<Object> var2, String var3, Class<?> var4) {
         this.name = â˜ƒ;
         this.getter = â˜ƒ;
         this.description = â˜ƒ;
         this.type = â˜ƒ;
      }

      private MBeanAttributeInfo asMBeanAttributeInfo() {
         return new MBeanAttributeInfo(this.name, this.type.getSimpleName(), this.description, true, false, false);
      }
   }
}
