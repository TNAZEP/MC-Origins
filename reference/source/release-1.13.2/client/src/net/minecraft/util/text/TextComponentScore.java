package net.minecraft.util.text;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.EntitySelector;
import net.minecraft.command.arguments.EntitySelectorParser;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

public class TextComponentScore extends TextComponentBase {
   private final String field_179999_b;
   @Nullable
   private final EntitySelector field_197667_c;
   private final String field_180000_c;
   private String field_179998_d = "";

   public TextComponentScore(String var1, String var2) {
      this.field_179999_b = ☃;
      this.field_180000_c = ☃;
      EntitySelector ☃ = null;

      try {
         EntitySelectorParser ☃x = new EntitySelectorParser(new StringReader(☃));
         ☃ = ☃x.func_201345_m();
      } catch (CommandSyntaxException var5) {
      }

      this.field_197667_c = ☃;
   }

   public String func_179995_g() {
      return this.field_179999_b;
   }

   @Nullable
   public EntitySelector func_197666_h() {
      return this.field_197667_c;
   }

   public String func_179994_h() {
      return this.field_180000_c;
   }

   public void func_179997_b(String var1) {
      this.field_179998_d = ☃;
   }

   @Override
   public String func_150261_e() {
      return this.field_179998_d;
   }

   public void func_197665_b(CommandSource var1) {
      MinecraftServer ☃ = ☃.func_197028_i();
      if (☃ != null && ☃.func_175578_N() && StringUtils.func_151246_b(this.field_179998_d)) {
         Scoreboard ☃x = ☃.func_200251_aP();
         ScoreObjective ☃xx = ☃x.func_96518_b(this.field_180000_c);
         if (☃x.func_178819_b(this.field_179999_b, ☃xx)) {
            Score ☃xxx = ☃x.func_96529_a(this.field_179999_b, ☃xx);
            this.func_179997_b(String.format("%d", ☃xxx.func_96652_c()));
         } else {
            this.field_179998_d = "";
         }
      }
   }

   public TextComponentScore func_150259_f() {
      TextComponentScore ☃ = new TextComponentScore(this.field_179999_b, this.field_180000_c);
      ☃.func_179997_b(this.field_179998_d);
      return ☃;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else if (!(☃ instanceof TextComponentScore)) {
         return false;
      } else {
         TextComponentScore ☃ = (TextComponentScore)☃;
         return this.field_179999_b.equals(☃.field_179999_b) && this.field_180000_c.equals(☃.field_180000_c) && super.equals(☃);
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='"
         + this.field_179999_b
         + '\''
         + "objective='"
         + this.field_180000_c
         + '\''
         + ", siblings="
         + this.field_150264_a
         + ", style="
         + this.func_150256_b()
         + '}';
   }
}
