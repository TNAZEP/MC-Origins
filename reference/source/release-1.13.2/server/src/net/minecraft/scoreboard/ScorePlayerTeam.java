package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

public class ScorePlayerTeam extends Team {
   private final Scoreboard field_96677_a;
   private final String field_96675_b;
   private final Set<String> field_96676_c = Sets.newHashSet();
   private ITextComponent field_96673_d;
   private ITextComponent field_207410_e = new TextComponentString("");
   private ITextComponent field_207411_f = new TextComponentString("");
   private boolean field_96672_g = true;
   private boolean field_98301_h = true;
   private Team.EnumVisible field_178778_i = Team.EnumVisible.ALWAYS;
   private Team.EnumVisible field_178776_j = Team.EnumVisible.ALWAYS;
   private TextFormatting field_178777_k = TextFormatting.RESET;
   private Team.CollisionRule field_186683_l = Team.CollisionRule.ALWAYS;

   public ScorePlayerTeam(Scoreboard var1, String var2) {
      this.field_96677_a = ☃;
      this.field_96675_b = ☃;
      this.field_96673_d = new TextComponentString(☃);
   }

   @Override
   public String func_96661_b() {
      return this.field_96675_b;
   }

   public ITextComponent func_96669_c() {
      return this.field_96673_d;
   }

   public ITextComponent func_197892_d() {
      ITextComponent ☃ = TextComponentUtils.func_197676_a(
         this.field_96673_d
            .func_212638_h()
            .func_211710_a(
               var1x -> var1x.func_179989_a(this.field_96675_b)
                     .func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(this.field_96675_b)))
            )
      );
      TextFormatting ☃x = this.func_178775_l();
      if (☃x != TextFormatting.RESET) {
         ☃.func_211708_a(☃x);
      }

      return ☃;
   }

   public void func_96664_a(ITextComponent var1) {
      if (☃ == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.field_96673_d = ☃;
         this.field_96677_a.func_96538_b(this);
      }
   }

   public void func_207408_a(@Nullable ITextComponent var1) {
      this.field_207410_e = (ITextComponent)(☃ == null ? new TextComponentString("") : ☃.func_212638_h());
      this.field_96677_a.func_96538_b(this);
   }

   public ITextComponent func_207406_e() {
      return this.field_207410_e;
   }

   public void func_207409_b(@Nullable ITextComponent var1) {
      this.field_207411_f = (ITextComponent)(☃ == null ? new TextComponentString("") : ☃.func_212638_h());
      this.field_96677_a.func_96538_b(this);
   }

   public ITextComponent func_207407_f() {
      return this.field_207411_f;
   }

   @Override
   public Collection<String> func_96670_d() {
      return this.field_96676_c;
   }

   @Override
   public ITextComponent func_200540_a(ITextComponent var1) {
      ITextComponent ☃ = new TextComponentString("").func_150257_a(this.field_207410_e).func_150257_a(☃).func_150257_a(this.field_207411_f);
      TextFormatting ☃x = this.func_178775_l();
      if (☃x != TextFormatting.RESET) {
         ☃.func_211708_a(☃x);
      }

      return ☃;
   }

   public static ITextComponent func_200541_a(@Nullable Team var0, ITextComponent var1) {
      return ☃ == null ? ☃.func_212638_h() : ☃.func_200540_a(☃);
   }

   @Override
   public boolean func_96665_g() {
      return this.field_96672_g;
   }

   public void func_96660_a(boolean var1) {
      this.field_96672_g = ☃;
      this.field_96677_a.func_96538_b(this);
   }

   public boolean func_98297_h() {
      return this.field_98301_h;
   }

   public void func_98300_b(boolean var1) {
      this.field_98301_h = ☃;
      this.field_96677_a.func_96538_b(this);
   }

   public Team.EnumVisible func_178770_i() {
      return this.field_178778_i;
   }

   @Override
   public Team.EnumVisible func_178771_j() {
      return this.field_178776_j;
   }

   public void func_178772_a(Team.EnumVisible var1) {
      this.field_178778_i = ☃;
      this.field_96677_a.func_96538_b(this);
   }

   public void func_178773_b(Team.EnumVisible var1) {
      this.field_178776_j = ☃;
      this.field_96677_a.func_96538_b(this);
   }

   @Override
   public Team.CollisionRule func_186681_k() {
      return this.field_186683_l;
   }

   public void func_186682_a(Team.CollisionRule var1) {
      this.field_186683_l = ☃;
      this.field_96677_a.func_96538_b(this);
   }

   public int func_98299_i() {
      int ☃ = 0;
      if (this.func_96665_g()) {
         ☃ |= 1;
      }

      if (this.func_98297_h()) {
         ☃ |= 2;
      }

      return ☃;
   }

   public void func_178774_a(TextFormatting var1) {
      this.field_178777_k = ☃;
      this.field_96677_a.func_96538_b(this);
   }

   @Override
   public TextFormatting func_178775_l() {
      return this.field_178777_k;
   }
}
