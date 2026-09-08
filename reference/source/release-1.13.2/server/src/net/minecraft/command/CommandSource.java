package net.minecraft.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import javax.annotation.Nullable;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;

public class CommandSource implements ISuggestionProvider {
   public static final SimpleCommandExceptionType field_197039_a = new SimpleCommandExceptionType(new TextComponentTranslation("permissions.requires.player"));
   public static final SimpleCommandExceptionType field_197040_b = new SimpleCommandExceptionType(new TextComponentTranslation("permissions.requires.entity"));
   private final ICommandSource field_197041_c;
   private final Vec3d field_197042_d;
   private final WorldServer field_197043_e;
   private final int field_197044_f;
   private final String field_197045_g;
   private final ITextComponent field_197046_h;
   private final MinecraftServer field_197047_i;
   private final boolean field_197048_j;
   @Nullable
   private final Entity field_197049_k;
   private final ResultConsumer<CommandSource> field_197050_l;
   private final EntityAnchorArgument.Type field_201011_m;
   private final Vec2f field_201012_n;

   public CommandSource(
      ICommandSource var1, Vec3d var2, Vec2f var3, WorldServer var4, int var5, String var6, ITextComponent var7, MinecraftServer var8, @Nullable Entity var9
   ) {
      this(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, false, (var0, var1x, var2x) -> {
      }, EntityAnchorArgument.Type.FEET);
   }

   protected CommandSource(
      ICommandSource var1,
      Vec3d var2,
      Vec2f var3,
      WorldServer var4,
      int var5,
      String var6,
      ITextComponent var7,
      MinecraftServer var8,
      @Nullable Entity var9,
      boolean var10,
      ResultConsumer<CommandSource> var11,
      EntityAnchorArgument.Type var12
   ) {
      this.field_197041_c = ☃;
      this.field_197042_d = ☃;
      this.field_197043_e = ☃;
      this.field_197048_j = ☃;
      this.field_197049_k = ☃;
      this.field_197044_f = ☃;
      this.field_197045_g = ☃;
      this.field_197046_h = ☃;
      this.field_197047_i = ☃;
      this.field_197050_l = ☃;
      this.field_201011_m = ☃;
      this.field_201012_n = ☃;
   }

   public CommandSource func_197024_a(Entity var1) {
      return this.field_197049_k == ☃
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            this.field_197043_e,
            this.field_197044_f,
            ☃.func_200200_C_().getString(),
            ☃.func_145748_c_(),
            this.field_197047_i,
            ☃,
            this.field_197048_j,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_201009_a(Vec3d var1) {
      return this.field_197042_d.equals(☃)
         ? this
         : new CommandSource(
            this.field_197041_c,
            ☃,
            this.field_201012_n,
            this.field_197043_e,
            this.field_197044_f,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_201007_a(Vec2f var1) {
      return this.field_201012_n.func_201069_c(☃)
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            ☃,
            this.field_197043_e,
            this.field_197044_f,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_197029_a(ResultConsumer<CommandSource> var1) {
      return this.field_197050_l.equals(☃)
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            this.field_197043_e,
            this.field_197044_f,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            ☃,
            this.field_201011_m
         );
   }

   public CommandSource func_209550_a(ResultConsumer<CommandSource> var1, BinaryOperator<ResultConsumer<CommandSource>> var2) {
      ResultConsumer<CommandSource> ☃ = (ResultConsumer)☃.apply(this.field_197050_l, ☃);
      return this.func_197029_a(☃);
   }

   public CommandSource func_197031_a() {
      return this.field_197048_j
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            this.field_197043_e,
            this.field_197044_f,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            true,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_197033_a(int var1) {
      return ☃ == this.field_197044_f
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            this.field_197043_e,
            ☃,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_197026_b(int var1) {
      return ☃ <= this.field_197044_f
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            this.field_197043_e,
            ☃,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_201010_a(EntityAnchorArgument.Type var1) {
      return ☃ == this.field_201011_m
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            this.field_197043_e,
            this.field_197044_f,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            this.field_197050_l,
            ☃
         );
   }

   public CommandSource func_201003_a(WorldServer var1) {
      return ☃ == this.field_197043_e
         ? this
         : new CommandSource(
            this.field_197041_c,
            this.field_197042_d,
            this.field_201012_n,
            ☃,
            this.field_197044_f,
            this.field_197045_g,
            this.field_197046_h,
            this.field_197047_i,
            this.field_197049_k,
            this.field_197048_j,
            this.field_197050_l,
            this.field_201011_m
         );
   }

   public CommandSource func_201006_a(Entity var1, EntityAnchorArgument.Type var2) throws CommandSyntaxException {
      return this.func_201005_b(☃.func_201017_a(☃));
   }

   public CommandSource func_201005_b(Vec3d var1) throws CommandSyntaxException {
      Vec3d ☃ = this.field_201011_m.func_201015_a(this);
      double ☃x = ☃.field_72450_a - ☃.field_72450_a;
      double ☃xx = ☃.field_72448_b - ☃.field_72448_b;
      double ☃xxx = ☃.field_72449_c - ☃.field_72449_c;
      double ☃xxxx = (double)MathHelper.func_76133_a(☃x * ☃x + ☃xxx * ☃xxx);
      float ☃xxxxx = MathHelper.func_76142_g((float)(-(MathHelper.func_181159_b(☃xx, ☃xxxx) * 180.0F / (float)Math.PI)));
      float ☃xxxxxx = MathHelper.func_76142_g((float)(MathHelper.func_181159_b(☃xxx, ☃x) * 180.0F / (float)Math.PI) - 90.0F);
      return this.func_201007_a(new Vec2f(☃xxxxx, ☃xxxxxx));
   }

   public ITextComponent func_197019_b() {
      return this.field_197046_h;
   }

   public String func_197037_c() {
      return this.field_197045_g;
   }

   @Override
   public boolean func_197034_c(int var1) {
      return this.field_197044_f >= ☃;
   }

   public Vec3d func_197036_d() {
      return this.field_197042_d;
   }

   public WorldServer func_197023_e() {
      return this.field_197043_e;
   }

   @Nullable
   public Entity func_197022_f() {
      return this.field_197049_k;
   }

   public Entity func_197027_g() throws CommandSyntaxException {
      if (this.field_197049_k == null) {
         throw field_197040_b.create();
      } else {
         return this.field_197049_k;
      }
   }

   public EntityPlayerMP func_197035_h() throws CommandSyntaxException {
      if (!(this.field_197049_k instanceof EntityPlayerMP)) {
         throw field_197039_a.create();
      } else {
         return (EntityPlayerMP)this.field_197049_k;
      }
   }

   public Vec2f func_201004_i() {
      return this.field_201012_n;
   }

   public MinecraftServer func_197028_i() {
      return this.field_197047_i;
   }

   public EntityAnchorArgument.Type func_201008_k() {
      return this.field_201011_m;
   }

   public void func_197030_a(ITextComponent var1, boolean var2) {
      if (this.field_197041_c.func_195039_a() && !this.field_197048_j) {
         this.field_197041_c.func_145747_a(☃);
      }

      if (☃ && this.field_197041_c.func_195041_r_() && !this.field_197048_j) {
         this.func_197020_b(☃);
      }
   }

   private void func_197020_b(ITextComponent var1) {
      ITextComponent ☃ = new TextComponentTranslation("chat.type.admin", this.func_197019_b(), ☃)
         .func_211709_a(new TextFormatting[]{TextFormatting.GRAY, TextFormatting.ITALIC});
      if (this.field_197047_i.func_200252_aR().func_82766_b("sendCommandFeedback")) {
         for(EntityPlayerMP ☃x : this.field_197047_i.func_184103_al().func_181057_v()) {
            if (☃x != this.field_197041_c && this.field_197047_i.func_184103_al().func_152596_g(☃x.func_146103_bH())) {
               ☃x.func_145747_a(☃);
            }
         }
      }

      if (this.field_197041_c != this.field_197047_i && this.field_197047_i.func_200252_aR().func_82766_b("logAdminCommands")) {
         this.field_197047_i.func_145747_a(☃);
      }
   }

   public void func_197021_a(ITextComponent var1) {
      if (this.field_197041_c.func_195040_b() && !this.field_197048_j) {
         this.field_197041_c.func_145747_a(new TextComponentString("").func_150257_a(☃).func_211708_a(TextFormatting.RED));
      }
   }

   public void func_197038_a(CommandContext<CommandSource> var1, boolean var2, int var3) {
      if (this.field_197050_l != null) {
         this.field_197050_l.onCommandComplete(☃, ☃, ☃);
      }
   }

   @Override
   public Collection<String> func_197011_j() {
      return Lists.newArrayList(this.field_197047_i.func_71213_z());
   }

   @Override
   public Collection<String> func_197012_k() {
      return this.field_197047_i.func_200251_aP().func_96531_f();
   }

   @Override
   public Collection<ResourceLocation> func_197010_l() {
      return IRegistry.field_212633_v.func_148742_b();
   }

   @Override
   public Collection<ResourceLocation> func_199612_m() {
      return this.field_197047_i.func_199529_aN().func_199511_c();
   }

   @Override
   public CompletableFuture<Suggestions> func_197009_a(CommandContext<ISuggestionProvider> var1, SuggestionsBuilder var2) {
      return null;
   }

   @Override
   public Collection<ISuggestionProvider.Coordinates> func_199613_a(boolean var1) {
      return Collections.singleton(ISuggestionProvider.Coordinates.field_209005_b);
   }
}
