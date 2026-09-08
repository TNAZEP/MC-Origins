package com.mojang.blaze3d.preprocessor;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

public abstract class GlslPreprocessor {
   private static final String C_COMMENT = "/\\*(?:[^*]|\\*+[^*/])*\\*+/";
   private static final String LINE_COMMENT = "//[^\\v]*";
   private static final Pattern REGEX_MOJ_IMPORT = Pattern.compile(
      "(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*moj_import(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(?:\"(.*)\"|<(.*)>))"
   );
   private static final Pattern REGEX_VERSION = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*version(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(\\d+))\\b");
   private static final Pattern REGEX_ENDS_WITH_WHITESPACE = Pattern.compile("(?:^|\\v)(?:\\s|/\\*(?:[^*]|\\*+[^*/])*\\*+/|(//[^\\v]*))*\\z");

   public List<String> process(String var1) {
      GlslPreprocessor.Context â˜ƒ = new GlslPreprocessor.Context();
      List<String> â˜ƒx = this.processImports(â˜ƒ, â˜ƒ, "");
      â˜ƒx.set(0, this.setVersion((String)â˜ƒx.get(0), â˜ƒ.glslVersion));
      return â˜ƒx;
   }

   private List<String> processImports(String var1, GlslPreprocessor.Context var2, String var3) {
      int â˜ƒ = â˜ƒ.sourceId;
      int â˜ƒx = 0;
      String â˜ƒxx = "";
      List<String> â˜ƒxxx = Lists.newArrayList();
      Matcher â˜ƒxxxx = REGEX_MOJ_IMPORT.matcher(â˜ƒ);

      while(â˜ƒxxxx.find()) {
         if (!isDirectiveDisabled(â˜ƒ, â˜ƒxxxx, â˜ƒx)) {
            String â˜ƒxxxxx = â˜ƒxxxx.group(2);
            boolean â˜ƒxxxxxx = â˜ƒxxxxx != null;
            if (!â˜ƒxxxxxx) {
               â˜ƒxxxxx = â˜ƒxxxx.group(3);
            }

            if (â˜ƒxxxxx != null) {
               String â˜ƒxxxxx = â˜ƒ.substring(â˜ƒx, â˜ƒxxxx.start(1));
               String â˜ƒxxxxxx = â˜ƒ + â˜ƒxxxxx;
               String â˜ƒxxxxxxx = this.applyImport(â˜ƒxxxxxx, â˜ƒxxxxxx);
               if (!Strings.isEmpty(â˜ƒxxxxxxx)) {
                  if (!StringUtil.endsWithNewLine(â˜ƒxxxxxxx)) {
                     â˜ƒxxxxxxx = â˜ƒxxxxxxx + System.lineSeparator();
                  }

                  ++â˜ƒ.sourceId;
                  int â˜ƒxxxxxxxx = â˜ƒ.sourceId;
                  List<String> â˜ƒxxxxxxxxx = this.processImports(â˜ƒxxxxxxx, â˜ƒ, â˜ƒxxxxxx ? FileUtil.getFullResourcePath(â˜ƒxxxxxx) : "");
                  â˜ƒxxxxxxxxx.set(0, String.format(Locale.ROOT, "#line %d %d\n%s", 0, â˜ƒxxxxxxxx, this.processVersions((String)â˜ƒxxxxxxxxx.get(0), â˜ƒ)));
                  if (!StringUtils.isBlank(â˜ƒxxxxx)) {
                     â˜ƒxxx.add(â˜ƒxxxxx);
                  }

                  â˜ƒxxx.addAll(â˜ƒxxxxxxxxx);
               } else {
                  String â˜ƒxxxxx = â˜ƒxxxxxx ? String.format("/*#moj_import \"%s\"*/", â˜ƒxxxxx) : String.format("/*#moj_import <%s>*/", â˜ƒxxxxx);
                  â˜ƒxxx.add(â˜ƒxx + â˜ƒxxxxx + â˜ƒxxxxx);
               }

               int â˜ƒxxxxx = StringUtil.lineCount(â˜ƒ.substring(0, â˜ƒxxxx.end(1)));
               â˜ƒxx = String.format(Locale.ROOT, "#line %d %d", â˜ƒxxxxx, â˜ƒ);
               â˜ƒx = â˜ƒxxxx.end(1);
            }
         }
      }

      String â˜ƒxxxxx = â˜ƒ.substring(â˜ƒx);
      if (!StringUtils.isBlank(â˜ƒxxxxx)) {
         â˜ƒxxx.add(â˜ƒxx + â˜ƒxxxxx);
      }

      return â˜ƒxxx;
   }

   private String processVersions(String var1, GlslPreprocessor.Context var2) {
      Matcher â˜ƒ = REGEX_VERSION.matcher(â˜ƒ);
      if (â˜ƒ.find() && isDirectiveEnabled(â˜ƒ, â˜ƒ)) {
         â˜ƒ.glslVersion = Math.max(â˜ƒ.glslVersion, Integer.parseInt(â˜ƒ.group(2)));
         return â˜ƒ.substring(0, â˜ƒ.start(1)) + "/*" + â˜ƒ.substring(â˜ƒ.start(1), â˜ƒ.end(1)) + "*/" + â˜ƒ.substring(â˜ƒ.end(1));
      } else {
         return â˜ƒ;
      }
   }

   private String setVersion(String var1, int var2) {
      Matcher â˜ƒ = REGEX_VERSION.matcher(â˜ƒ);
      return â˜ƒ.find() && isDirectiveEnabled(â˜ƒ, â˜ƒ)
         ? â˜ƒ.substring(0, â˜ƒ.start(2)) + Math.max(â˜ƒ, Integer.parseInt(â˜ƒ.group(2))) + â˜ƒ.substring(â˜ƒ.end(2))
         : â˜ƒ;
   }

   private static boolean isDirectiveEnabled(String var0, Matcher var1) {
      return !isDirectiveDisabled(â˜ƒ, â˜ƒ, 0);
   }

   private static boolean isDirectiveDisabled(String var0, Matcher var1, int var2) {
      int â˜ƒ = â˜ƒ.start() - â˜ƒ;
      if (â˜ƒ == 0) {
         return false;
      } else {
         Matcher â˜ƒ = REGEX_ENDS_WITH_WHITESPACE.matcher(â˜ƒ.substring(â˜ƒ, â˜ƒ.start()));
         if (!â˜ƒ.find()) {
            return true;
         } else {
            int â˜ƒ = â˜ƒ.end(1);
            return â˜ƒ == â˜ƒ.start();
         }
      }
   }

   @Nullable
   public abstract String applyImport(boolean var1, String var2);

   static final class Context {
      int glslVersion;
      int sourceId;
   }
}
