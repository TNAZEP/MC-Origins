package net.minecraft.scoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.TextFormatting;

public class ScoreCriteria {
   public static final Map<String, ScoreCriteria> field_96643_a = Maps.newHashMap();
   public static final ScoreCriteria field_96641_b = new ScoreCriteria("dummy");
   public static final ScoreCriteria field_178791_c = new ScoreCriteria("trigger");
   public static final ScoreCriteria field_96642_c = new ScoreCriteria("deathCount");
   public static final ScoreCriteria field_96639_d = new ScoreCriteria("playerKillCount");
   public static final ScoreCriteria field_96640_e = new ScoreCriteria("totalKillCount");
   public static final ScoreCriteria field_96638_f = new ScoreCriteria("health", true, ScoreCriteria.RenderType.HEARTS);
   public static final ScoreCriteria field_186698_h = new ScoreCriteria("food", true, ScoreCriteria.RenderType.INTEGER);
   public static final ScoreCriteria field_186699_i = new ScoreCriteria("air", true, ScoreCriteria.RenderType.INTEGER);
   public static final ScoreCriteria field_186700_j = new ScoreCriteria("armor", true, ScoreCriteria.RenderType.INTEGER);
   public static final ScoreCriteria field_186701_k = new ScoreCriteria("xp", true, ScoreCriteria.RenderType.INTEGER);
   public static final ScoreCriteria field_186702_l = new ScoreCriteria("level", true, ScoreCriteria.RenderType.INTEGER);
   public static final ScoreCriteria[] field_197913_m = new ScoreCriteria[]{
      new ScoreCriteria("teamkill." + TextFormatting.BLACK.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.DARK_BLUE.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.DARK_GREEN.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.DARK_AQUA.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.DARK_RED.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.DARK_PURPLE.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.GOLD.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.GRAY.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.DARK_GRAY.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.BLUE.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.GREEN.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.AQUA.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.RED.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.LIGHT_PURPLE.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.YELLOW.func_96297_d()),
      new ScoreCriteria("teamkill." + TextFormatting.WHITE.func_96297_d())
   };
   public static final ScoreCriteria[] field_197914_n = new ScoreCriteria[]{
      new ScoreCriteria("killedByTeam." + TextFormatting.BLACK.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.DARK_BLUE.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.DARK_GREEN.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.DARK_AQUA.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.DARK_RED.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.DARK_PURPLE.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.GOLD.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.GRAY.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.DARK_GRAY.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.BLUE.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.GREEN.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.AQUA.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.RED.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.LIGHT_PURPLE.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.YELLOW.func_96297_d()),
      new ScoreCriteria("killedByTeam." + TextFormatting.WHITE.func_96297_d())
   };
   private final String field_197915_o;
   private final boolean field_197916_p;
   private final ScoreCriteria.RenderType field_197917_q;

   public ScoreCriteria(String var1) {
      this(☃, false, ScoreCriteria.RenderType.INTEGER);
   }

   protected ScoreCriteria(String var1, boolean var2, ScoreCriteria.RenderType var3) {
      this.field_197915_o = ☃;
      this.field_197916_p = ☃;
      this.field_197917_q = ☃;
      field_96643_a.put(☃, this);
   }

   @Nullable
   public static ScoreCriteria func_197911_a(String var0) {
      if (field_96643_a.containsKey(☃)) {
         return (ScoreCriteria)field_96643_a.get(☃);
      } else {
         int ☃ = ☃.indexOf(58);
         if (☃ < 0) {
            return null;
         } else {
            StatType<?> ☃ = IRegistry.field_212634_w.func_212608_b(ResourceLocation.func_195828_a(☃.substring(0, ☃), '.'));
            return ☃ == null ? null : func_197912_a(☃, ResourceLocation.func_195828_a(☃.substring(☃ + 1), '.'));
         }
      }
   }

   @Nullable
   private static <T> ScoreCriteria func_197912_a(StatType<T> var0, ResourceLocation var1) {
      IRegistry<T> ☃ = ☃.func_199080_a();
      return ☃.func_212607_c(☃) ? ☃.func_199076_b(☃.func_212608_b(☃)) : null;
   }

   public String func_96636_a() {
      return this.field_197915_o;
   }

   public boolean func_96637_b() {
      return this.field_197916_p;
   }

   public ScoreCriteria.RenderType func_178790_c() {
      return this.field_197917_q;
   }

   public static enum RenderType {
      INTEGER("integer"),
      HEARTS("hearts");

      private final String field_211840_c;
      private static final Map<String, ScoreCriteria.RenderType> field_211841_d;

      private RenderType(String var3) {
         this.field_211840_c = ☃;
      }

      public String func_211838_a() {
         return this.field_211840_c;
      }

      public static ScoreCriteria.RenderType func_211839_a(String var0) {
         return (ScoreCriteria.RenderType)field_211841_d.getOrDefault(☃, INTEGER);
      }

      static {
         Builder<String, ScoreCriteria.RenderType> ☃ = ImmutableMap.builder();

         for(ScoreCriteria.RenderType ☃x : values()) {
            ☃.put(☃x.field_211840_c, ☃x);
         }

         field_211841_d = ☃.build();
      }
   }
}
