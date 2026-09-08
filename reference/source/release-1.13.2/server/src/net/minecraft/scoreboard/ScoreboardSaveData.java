package net.minecraft.scoreboard;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData {
   private static final Logger field_151481_a = LogManager.getLogger();
   private Scoreboard field_96507_a;
   private NBTTagCompound field_96506_b;

   public ScoreboardSaveData() {
      this("scoreboard");
   }

   public ScoreboardSaveData(String var1) {
      super(☃);
   }

   public void func_96499_a(Scoreboard var1) {
      this.field_96507_a = ☃;
      if (this.field_96506_b != null) {
         this.func_76184_a(this.field_96506_b);
      }
   }

   @Override
   public void func_76184_a(NBTTagCompound var1) {
      if (this.field_96507_a == null) {
         this.field_96506_b = ☃;
      } else {
         this.func_96501_b(☃.func_150295_c("Objectives", 10));
         this.field_96507_a.func_197905_a(☃.func_150295_c("PlayerScores", 10));
         if (☃.func_150297_b("DisplaySlots", 10)) {
            this.func_96504_c(☃.func_74775_l("DisplaySlots"));
         }

         if (☃.func_150297_b("Teams", 9)) {
            this.func_96498_a(☃.func_150295_c("Teams", 10));
         }
      }
   }

   protected void func_96498_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         String ☃xx = ☃x.func_74779_i("Name");
         if (☃xx.length() > 16) {
            ☃xx = ☃xx.substring(0, 16);
         }

         ScorePlayerTeam ☃x = this.field_96507_a.func_96527_f(☃xx);
         ITextComponent ☃xx = ITextComponent.Serializer.func_150699_a(☃x.func_74779_i("DisplayName"));
         if (☃xx != null) {
            ☃x.func_96664_a(☃xx);
         }

         if (☃x.func_150297_b("TeamColor", 8)) {
            ☃x.func_178774_a(TextFormatting.func_96300_b(☃x.func_74779_i("TeamColor")));
         }

         if (☃x.func_150297_b("AllowFriendlyFire", 99)) {
            ☃x.func_96660_a(☃x.func_74767_n("AllowFriendlyFire"));
         }

         if (☃x.func_150297_b("SeeFriendlyInvisibles", 99)) {
            ☃x.func_98300_b(☃x.func_74767_n("SeeFriendlyInvisibles"));
         }

         if (☃x.func_150297_b("MemberNamePrefix", 8)) {
            ITextComponent ☃x = ITextComponent.Serializer.func_150699_a(☃x.func_74779_i("MemberNamePrefix"));
            if (☃x != null) {
               ☃x.func_207408_a(☃x);
            }
         }

         if (☃x.func_150297_b("MemberNameSuffix", 8)) {
            ITextComponent ☃x = ITextComponent.Serializer.func_150699_a(☃x.func_74779_i("MemberNameSuffix"));
            if (☃x != null) {
               ☃x.func_207409_b(☃x);
            }
         }

         if (☃x.func_150297_b("NameTagVisibility", 8)) {
            Team.EnumVisible ☃x = Team.EnumVisible.func_178824_a(☃x.func_74779_i("NameTagVisibility"));
            if (☃x != null) {
               ☃x.func_178772_a(☃x);
            }
         }

         if (☃x.func_150297_b("DeathMessageVisibility", 8)) {
            Team.EnumVisible ☃x = Team.EnumVisible.func_178824_a(☃x.func_74779_i("DeathMessageVisibility"));
            if (☃x != null) {
               ☃x.func_178773_b(☃x);
            }
         }

         if (☃x.func_150297_b("CollisionRule", 8)) {
            Team.CollisionRule ☃x = Team.CollisionRule.func_186686_a(☃x.func_74779_i("CollisionRule"));
            if (☃x != null) {
               ☃x.func_186682_a(☃x);
            }
         }

         this.func_96502_a(☃x, ☃x.func_150295_c("Players", 8));
      }
   }

   protected void func_96502_a(ScorePlayerTeam var1, NBTTagList var2) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         this.field_96507_a.func_197901_a(☃.func_150307_f(☃), ☃);
      }
   }

   protected void func_96504_c(NBTTagCompound var1) {
      for(int ☃ = 0; ☃ < 19; ++☃) {
         if (☃.func_150297_b("slot_" + ☃, 8)) {
            String ☃x = ☃.func_74779_i("slot_" + ☃);
            ScoreObjective ☃xx = this.field_96507_a.func_96518_b(☃x);
            this.field_96507_a.func_96530_a(☃, ☃xx);
         }
      }
   }

   protected void func_96501_b(NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         ScoreCriteria ☃xx = ScoreCriteria.func_197911_a(☃x.func_74779_i("CriteriaName"));
         if (☃xx != null) {
            String ☃xxx = ☃x.func_74779_i("Name");
            if (☃xxx.length() > 16) {
               ☃xxx = ☃xxx.substring(0, 16);
            }

            ITextComponent ☃xxx = ITextComponent.Serializer.func_150699_a(☃x.func_74779_i("DisplayName"));
            ScoreCriteria.RenderType ☃xxxx = ScoreCriteria.RenderType.func_211839_a(☃x.func_74779_i("RenderType"));
            this.field_96507_a.func_199868_a(☃xxx, ☃xx, ☃xxx, ☃xxxx);
         }
      }
   }

   @Override
   public NBTTagCompound func_189551_b(NBTTagCompound var1) {
      if (this.field_96507_a == null) {
         field_151481_a.warn("Tried to save scoreboard without having a scoreboard...");
         return ☃;
      } else {
         ☃.func_74782_a("Objectives", this.func_96505_b());
         ☃.func_74782_a("PlayerScores", this.field_96507_a.func_197902_i());
         ☃.func_74782_a("Teams", this.func_96496_a());
         this.func_96497_d(☃);
         return ☃;
      }
   }

   protected NBTTagList func_96496_a() {
      NBTTagList ☃ = new NBTTagList();

      for(ScorePlayerTeam ☃x : this.field_96507_a.func_96525_g()) {
         NBTTagCompound ☃xx = new NBTTagCompound();
         ☃xx.func_74778_a("Name", ☃x.func_96661_b());
         ☃xx.func_74778_a("DisplayName", ITextComponent.Serializer.func_150696_a(☃x.func_96669_c()));
         if (☃x.func_178775_l().func_175746_b() >= 0) {
            ☃xx.func_74778_a("TeamColor", ☃x.func_178775_l().func_96297_d());
         }

         ☃xx.func_74757_a("AllowFriendlyFire", ☃x.func_96665_g());
         ☃xx.func_74757_a("SeeFriendlyInvisibles", ☃x.func_98297_h());
         ☃xx.func_74778_a("MemberNamePrefix", ITextComponent.Serializer.func_150696_a(☃x.func_207406_e()));
         ☃xx.func_74778_a("MemberNameSuffix", ITextComponent.Serializer.func_150696_a(☃x.func_207407_f()));
         ☃xx.func_74778_a("NameTagVisibility", ☃x.func_178770_i().field_178830_e);
         ☃xx.func_74778_a("DeathMessageVisibility", ☃x.func_178771_j().field_178830_e);
         ☃xx.func_74778_a("CollisionRule", ☃x.func_186681_k().field_186693_e);
         NBTTagList ☃xx = new NBTTagList();

         for(String ☃xxx : ☃x.func_96670_d()) {
            ☃xx.add((INBTBase)(new NBTTagString(☃xxx)));
         }

         ☃xx.func_74782_a("Players", ☃xx);
         ☃.add((INBTBase)☃xx);
      }

      return ☃;
   }

   protected void func_96497_d(NBTTagCompound var1) {
      NBTTagCompound ☃ = new NBTTagCompound();
      boolean ☃x = false;

      for(int ☃xx = 0; ☃xx < 19; ++☃xx) {
         ScoreObjective ☃xxx = this.field_96507_a.func_96539_a(☃xx);
         if (☃xxx != null) {
            ☃.func_74778_a("slot_" + ☃xx, ☃xxx.func_96679_b());
            ☃x = true;
         }
      }

      if (☃x) {
         ☃.func_74782_a("DisplaySlots", ☃);
      }
   }

   protected NBTTagList func_96505_b() {
      NBTTagList ☃ = new NBTTagList();

      for(ScoreObjective ☃x : this.field_96507_a.func_96514_c()) {
         if (☃x.func_96680_c() != null) {
            NBTTagCompound ☃xx = new NBTTagCompound();
            ☃xx.func_74778_a("Name", ☃x.func_96679_b());
            ☃xx.func_74778_a("CriteriaName", ☃x.func_96680_c().func_96636_a());
            ☃xx.func_74778_a("DisplayName", ITextComponent.Serializer.func_150696_a(☃x.func_96678_d()));
            ☃xx.func_74778_a("RenderType", ☃x.func_199865_f().func_211838_a());
            ☃.add((INBTBase)☃xx);
         }
      }

      return ☃;
   }
}
