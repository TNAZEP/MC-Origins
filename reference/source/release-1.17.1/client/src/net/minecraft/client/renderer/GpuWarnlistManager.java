package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.platform.GlUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GpuWarnlistManager extends SimplePreparableReloadListener<GpuWarnlistManager.Preparations> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ResourceLocation GPU_WARNLIST_LOCATION = new ResourceLocation("gpu_warnlist.json");
   private ImmutableMap<String, String> warnings = ImmutableMap.of();
   private boolean showWarning;
   private boolean warningDismissed;
   private boolean skipFabulous;

   public boolean hasWarnings() {
      return !this.warnings.isEmpty();
   }

   public boolean willShowWarning() {
      return this.hasWarnings() && !this.warningDismissed;
   }

   public void showWarning() {
      this.showWarning = true;
   }

   public void dismissWarning() {
      this.warningDismissed = true;
   }

   public void dismissWarningAndSkipFabulous() {
      this.warningDismissed = true;
      this.skipFabulous = true;
   }

   public boolean isShowingWarning() {
      return this.showWarning && !this.warningDismissed;
   }

   public boolean isSkippingFabulous() {
      return this.skipFabulous;
   }

   public void resetWarnings() {
      this.showWarning = false;
      this.warningDismissed = false;
      this.skipFabulous = false;
   }

   @Nullable
   public String getRendererWarnings() {
      return (String)this.warnings.get("renderer");
   }

   @Nullable
   public String getVersionWarnings() {
      return (String)this.warnings.get("version");
   }

   @Nullable
   public String getVendorWarnings() {
      return (String)this.warnings.get("vendor");
   }

   @Nullable
   public String getAllWarnings() {
      StringBuilder â˜ƒ = new StringBuilder();
      this.warnings.forEach((var1x, var2) -> â˜ƒ.append(var1x).append(": ").append(var2));
      return â˜ƒ.length() == 0 ? null : â˜ƒ.toString();
   }

   protected GpuWarnlistManager.Preparations prepare(ResourceManager var1, ProfilerFiller var2) {
      List<Pattern> â˜ƒ = Lists.newArrayList();
      List<Pattern> â˜ƒx = Lists.newArrayList();
      List<Pattern> â˜ƒxx = Lists.newArrayList();
      â˜ƒ.startTick();
      JsonObject â˜ƒxxx = parseJson(â˜ƒ, â˜ƒ);
      if (â˜ƒxxx != null) {
         â˜ƒ.push("compile_regex");
         compilePatterns(â˜ƒxxx.getAsJsonArray("renderer"), â˜ƒ);
         compilePatterns(â˜ƒxxx.getAsJsonArray("version"), â˜ƒx);
         compilePatterns(â˜ƒxxx.getAsJsonArray("vendor"), â˜ƒxx);
         â˜ƒ.pop();
      }

      â˜ƒ.endTick();
      return new GpuWarnlistManager.Preparations(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   protected void apply(GpuWarnlistManager.Preparations var1, ResourceManager var2, ProfilerFiller var3) {
      this.warnings = â˜ƒ.apply();
   }

   private static void compilePatterns(JsonArray var0, List<Pattern> var1) {
      â˜ƒ.forEach(var1x -> â˜ƒ.add(Pattern.compile(var1x.getAsString(), 2)));
   }

   @Nullable
   private static JsonObject parseJson(ResourceManager var0, ProfilerFiller var1) {
      â˜ƒ.push("parse_json");
      JsonObject â˜ƒ = null;

      try {
         Resource â˜ƒx = â˜ƒ.getResource(GPU_WARNLIST_LOCATION);

         try {
            BufferedReader â˜ƒxx = new BufferedReader(new InputStreamReader(â˜ƒx.getInputStream(), StandardCharsets.UTF_8));

            try {
               â˜ƒ = new JsonParser().parse(â˜ƒxx).getAsJsonObject();
            } catch (Throwable var9) {
               try {
                  â˜ƒxx.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }

               throw var9;
            }

            â˜ƒxx.close();
         } catch (Throwable var10) {
            if (â˜ƒx != null) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var7) {
                  var10.addSuppressed(var7);
               }
            }

            throw var10;
         }

         if (â˜ƒx != null) {
            â˜ƒx.close();
         }
      } catch (JsonSyntaxException | IOException var11) {
         LOGGER.warn("Failed to load GPU warnlist");
      }

      â˜ƒ.pop();
      return â˜ƒ;
   }

   protected static final class Preparations {
      private final List<Pattern> rendererPatterns;
      private final List<Pattern> versionPatterns;
      private final List<Pattern> vendorPatterns;

      Preparations(List<Pattern> var1, List<Pattern> var2, List<Pattern> var3) {
         this.rendererPatterns = â˜ƒ;
         this.versionPatterns = â˜ƒ;
         this.vendorPatterns = â˜ƒ;
      }

      private static String matchAny(List<Pattern> var0, String var1) {
         List<String> â˜ƒ = Lists.newArrayList();

         for(Pattern â˜ƒx : â˜ƒ) {
            Matcher â˜ƒxx = â˜ƒx.matcher(â˜ƒ);

            while(â˜ƒxx.find()) {
               â˜ƒ.add(â˜ƒxx.group());
            }
         }

         return String.join(", ", â˜ƒ);
      }

      ImmutableMap<String, String> apply() {
         Builder<String, String> â˜ƒ = new Builder();
         String â˜ƒx = matchAny(this.rendererPatterns, GlUtil.getRenderer());
         if (!â˜ƒx.isEmpty()) {
            â˜ƒ.put("renderer", â˜ƒx);
         }

         String â˜ƒ = matchAny(this.versionPatterns, GlUtil.getOpenGLVersion());
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.put("version", â˜ƒ);
         }

         String â˜ƒ = matchAny(this.vendorPatterns, GlUtil.getVendor());
         if (!â˜ƒ.isEmpty()) {
            â˜ƒ.put("vendor", â˜ƒ);
         }

         return â˜ƒ.build();
      }
   }
}
