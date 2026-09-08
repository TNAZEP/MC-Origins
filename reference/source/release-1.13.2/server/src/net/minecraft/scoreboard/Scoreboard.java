package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class Scoreboard {
   private final Map<String, ScoreObjective> field_96545_a = Maps.newHashMap();
   private final Map<ScoreCriteria, List<ScoreObjective>> field_96543_b = Maps.newHashMap();
   private final Map<String, Map<ScoreObjective, Score>> field_96544_c = Maps.newHashMap();
   private final ScoreObjective[] field_96541_d = new ScoreObjective[19];
   private final Map<String, ScorePlayerTeam> field_96542_e = Maps.newHashMap();
   private final Map<String, ScorePlayerTeam> field_96540_f = Maps.newHashMap();
   private static String[] field_178823_g;

   public ScoreObjective func_197899_c(String var1) {
      return (ScoreObjective)this.field_96545_a.get(☃);
   }

   @Nullable
   public ScoreObjective func_96518_b(@Nullable String var1) {
      return (ScoreObjective)this.field_96545_a.get(☃);
   }

   public ScoreObjective func_199868_a(String var1, ScoreCriteria var2, ITextComponent var3, ScoreCriteria.RenderType var4) {
      if (☃.length() > 16) {
         throw new IllegalArgumentException("The objective name '" + ☃ + "' is too long!");
      } else if (this.field_96545_a.containsKey(☃)) {
         throw new IllegalArgumentException("An objective with the name '" + ☃ + "' already exists!");
      } else {
         ScoreObjective ☃ = new ScoreObjective(this, ☃, ☃, ☃, ☃);
         ((List)this.field_96543_b.computeIfAbsent(☃, var0 -> Lists.newArrayList())).add(☃);
         this.field_96545_a.put(☃, ☃);
         this.func_96522_a(☃);
         return ☃;
      }
   }

   public final void func_197893_a(ScoreCriteria var1, String var2, Consumer<Score> var3) {
      ((List)this.field_96543_b.getOrDefault(☃, Collections.emptyList())).forEach(var3x -> ☃.accept(this.func_96529_a(☃, var3x)));
   }

   public boolean func_178819_b(String var1, ScoreObjective var2) {
      Map<ScoreObjective, Score> ☃ = (Map)this.field_96544_c.get(☃);
      if (☃ == null) {
         return false;
      } else {
         Score ☃ = (Score)☃.get(☃);
         return ☃ != null;
      }
   }

   public Score func_96529_a(String var1, ScoreObjective var2) {
      if (☃.length() > 40) {
         throw new IllegalArgumentException("The player name '" + ☃ + "' is too long!");
      } else {
         Map<ScoreObjective, Score> ☃ = (Map)this.field_96544_c.computeIfAbsent(☃, var0 -> Maps.newHashMap());
         return (Score)☃.computeIfAbsent(☃, var2x -> {
            Score ☃ = new Score(this, var2x, ☃);
            ☃.func_96647_c(0);
            return ☃;
         });
      }
   }

   public Collection<Score> func_96534_i(ScoreObjective var1) {
      List<Score> ☃ = Lists.<Score>newArrayList();

      for(Map<ScoreObjective, Score> ☃x : this.field_96544_c.values()) {
         Score ☃xx = (Score)☃x.get(☃);
         if (☃xx != null) {
            ☃.add(☃xx);
         }
      }

      Collections.sort(☃, Score.field_96658_a);
      return ☃;
   }

   public Collection<ScoreObjective> func_96514_c() {
      return this.field_96545_a.values();
   }

   public Collection<String> func_197897_d() {
      return this.field_96545_a.keySet();
   }

   public Collection<String> func_96526_d() {
      return Lists.newArrayList(this.field_96544_c.keySet());
   }

   public void func_178822_d(String var1, @Nullable ScoreObjective var2) {
      if (☃ == null) {
         Map<ScoreObjective, Score> ☃ = (Map)this.field_96544_c.remove(☃);
         if (☃ != null) {
            this.func_96516_a(☃);
         }
      } else {
         Map<ScoreObjective, Score> ☃ = (Map)this.field_96544_c.get(☃);
         if (☃ != null) {
            Score ☃x = (Score)☃.remove(☃);
            if (☃.size() < 1) {
               Map<ScoreObjective, Score> ☃xx = (Map)this.field_96544_c.remove(☃);
               if (☃xx != null) {
                  this.func_96516_a(☃);
               }
            } else if (☃x != null) {
               this.func_178820_a(☃, ☃);
            }
         }
      }
   }

   public Map<ScoreObjective, Score> func_96510_d(String var1) {
      Map<ScoreObjective, Score> ☃ = (Map)this.field_96544_c.get(☃);
      if (☃ == null) {
         ☃ = Maps.<ScoreObjective, Score>newHashMap();
      }

      return ☃;
   }

   public void func_96519_k(ScoreObjective var1) {
      this.field_96545_a.remove(☃.func_96679_b());

      for(int ☃ = 0; ☃ < 19; ++☃) {
         if (this.func_96539_a(☃) == ☃) {
            this.func_96530_a(☃, null);
         }
      }

      List<ScoreObjective> ☃ = (List)this.field_96543_b.get(☃.func_96680_c());
      if (☃ != null) {
         ☃.remove(☃);
      }

      for(Map<ScoreObjective, Score> ☃ : this.field_96544_c.values()) {
         ☃.remove(☃);
      }

      this.func_96533_c(☃);
   }

   public void func_96530_a(int var1, @Nullable ScoreObjective var2) {
      this.field_96541_d[☃] = ☃;
   }

   @Nullable
   public ScoreObjective func_96539_a(int var1) {
      return this.field_96541_d[☃];
   }

   public ScorePlayerTeam func_96508_e(String var1) {
      return (ScorePlayerTeam)this.field_96542_e.get(☃);
   }

   public ScorePlayerTeam func_96527_f(String var1) {
      if (☃.length() > 16) {
         throw new IllegalArgumentException("The team name '" + ☃ + "' is too long!");
      } else {
         ScorePlayerTeam ☃ = this.func_96508_e(☃);
         if (☃ != null) {
            throw new IllegalArgumentException("A team with the name '" + ☃ + "' already exists!");
         } else {
            ☃ = new ScorePlayerTeam(this, ☃);
            this.field_96542_e.put(☃, ☃);
            this.func_96523_a(☃);
            return ☃;
         }
      }
   }

   public void func_96511_d(ScorePlayerTeam var1) {
      this.field_96542_e.remove(☃.func_96661_b());

      for(String ☃ : ☃.func_96670_d()) {
         this.field_96540_f.remove(☃);
      }

      this.func_96513_c(☃);
   }

   public boolean func_197901_a(String var1, ScorePlayerTeam var2) {
      if (☃.length() > 40) {
         throw new IllegalArgumentException("The player name '" + ☃ + "' is too long!");
      } else {
         if (this.func_96509_i(☃) != null) {
            this.func_96524_g(☃);
         }

         this.field_96540_f.put(☃, ☃);
         return ☃.func_96670_d().add(☃);
      }
   }

   public boolean func_96524_g(String var1) {
      ScorePlayerTeam ☃ = this.func_96509_i(☃);
      if (☃ != null) {
         this.func_96512_b(☃, ☃);
         return true;
      } else {
         return false;
      }
   }

   public void func_96512_b(String var1, ScorePlayerTeam var2) {
      if (this.func_96509_i(☃) != ☃) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + ☃.func_96661_b() + "'.");
      } else {
         this.field_96540_f.remove(☃);
         ☃.func_96670_d().remove(☃);
      }
   }

   public Collection<String> func_96531_f() {
      return this.field_96542_e.keySet();
   }

   public Collection<ScorePlayerTeam> func_96525_g() {
      return this.field_96542_e.values();
   }

   @Nullable
   public ScorePlayerTeam func_96509_i(String var1) {
      return (ScorePlayerTeam)this.field_96540_f.get(☃);
   }

   public void func_96522_a(ScoreObjective var1) {
   }

   public void func_199869_b(ScoreObjective var1) {
   }

   public void func_96533_c(ScoreObjective var1) {
   }

   public void func_96536_a(Score var1) {
   }

   public void func_96516_a(String var1) {
   }

   public void func_178820_a(String var1, ScoreObjective var2) {
   }

   public void func_96523_a(ScorePlayerTeam var1) {
   }

   public void func_96538_b(ScorePlayerTeam var1) {
   }

   public void func_96513_c(ScorePlayerTeam var1) {
   }

   public static String func_96517_b(int var0) {
      switch(☃) {
         case 0:
            return "list";
         case 1:
            return "sidebar";
         case 2:
            return "belowName";
         default:
            if (☃ >= 3 && ☃ <= 18) {
               TextFormatting ☃ = TextFormatting.func_175744_a(☃ - 3);
               if (☃ != null && ☃ != TextFormatting.RESET) {
                  return "sidebar.team." + ☃.func_96297_d();
               }
            }

            return null;
      }
   }

   public static int func_96537_j(String var0) {
      if ("list".equalsIgnoreCase(☃)) {
         return 0;
      } else if ("sidebar".equalsIgnoreCase(☃)) {
         return 1;
      } else if ("belowName".equalsIgnoreCase(☃)) {
         return 2;
      } else {
         if (☃.startsWith("sidebar.team.")) {
            String ☃ = ☃.substring("sidebar.team.".length());
            TextFormatting ☃x = TextFormatting.func_96300_b(☃);
            if (☃x != null && ☃x.func_175746_b() >= 0) {
               return ☃x.func_175746_b() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] func_178821_h() {
      if (field_178823_g == null) {
         field_178823_g = new String[19];

         for(int ☃ = 0; ☃ < 19; ++☃) {
            field_178823_g[☃] = func_96517_b(☃);
         }
      }

      return field_178823_g;
   }

   public void func_181140_a(Entity var1) {
      if (☃ != null && !(☃ instanceof EntityPlayer) && !☃.func_70089_S()) {
         String ☃ = ☃.func_189512_bd();
         this.func_178822_d(☃, null);
         this.func_96524_g(☃);
      }
   }

   protected NBTTagList func_197902_i() {
      NBTTagList ☃ = new NBTTagList();
      this.field_96544_c.values().stream().map(Map::values).forEach(var1x -> var1x.stream().filter(var0x -> var0x.func_96645_d() != null).forEach(var1xx -> {
            NBTTagCompound ☃ = new NBTTagCompound();
            ☃.func_74778_a("Name", var1xx.func_96653_e());
            ☃.func_74778_a("Objective", var1xx.func_96645_d().func_96679_b());
            ☃.func_74768_a("Score", var1xx.func_96652_c());
            ☃.func_74757_a("Locked", var1xx.func_178816_g());
            ☃.add((INBTBase)☃);
         }));
      return ☃;
   }

   protected void func_197905_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         ScoreObjective ☃xx = this.func_197899_c(☃x.func_74779_i("Objective"));
         String ☃xxx = ☃x.func_74779_i("Name");
         if (☃xxx.length() > 40) {
            ☃xxx = ☃xxx.substring(0, 40);
         }

         Score ☃x = this.func_96529_a(☃xxx, ☃xx);
         ☃x.func_96647_c(☃x.func_74762_e("Score"));
         if (☃x.func_74764_b("Locked")) {
            ☃x.func_178815_a(☃x.func_74767_n("Locked"));
         }
      }
   }
}
