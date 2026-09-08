package net.minecraft.command.arguments;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.WorldServer;

public class EntitySelector {
   private final int field_197354_a;
   private final boolean field_197355_b;
   private final boolean field_197356_c;
   private final Predicate<Entity> field_197357_d;
   private final MinMaxBounds.FloatBound field_197358_e;
   private final Function<Vec3d, Vec3d> field_197359_f;
   @Nullable
   private final AxisAlignedBB field_197360_g;
   private final BiConsumer<Vec3d, List<? extends Entity>> field_197361_h;
   private final boolean field_197362_i;
   @Nullable
   private final String field_197363_j;
   @Nullable
   private final UUID field_197364_k;
   private final Class<? extends Entity> field_201957_l;
   private final boolean field_210325_m;

   public EntitySelector(
      int var1,
      boolean var2,
      boolean var3,
      Predicate<Entity> var4,
      MinMaxBounds.FloatBound var5,
      Function<Vec3d, Vec3d> var6,
      @Nullable AxisAlignedBB var7,
      BiConsumer<Vec3d, List<? extends Entity>> var8,
      boolean var9,
      @Nullable String var10,
      @Nullable UUID var11,
      Class<? extends Entity> var12,
      boolean var13
   ) {
      this.field_197354_a = ☃;
      this.field_197355_b = ☃;
      this.field_197356_c = ☃;
      this.field_197357_d = ☃;
      this.field_197358_e = ☃;
      this.field_197359_f = ☃;
      this.field_197360_g = ☃;
      this.field_197361_h = ☃;
      this.field_197362_i = ☃;
      this.field_197363_j = ☃;
      this.field_197364_k = ☃;
      this.field_201957_l = ☃;
      this.field_210325_m = ☃;
   }

   public int func_197346_a() {
      return this.field_197354_a;
   }

   public boolean func_197351_b() {
      return this.field_197355_b;
   }

   public boolean func_197352_c() {
      return this.field_197362_i;
   }

   public boolean func_197353_d() {
      return this.field_197356_c;
   }

   private void func_210324_e(CommandSource var1) throws CommandSyntaxException {
      if (this.field_210325_m && !☃.func_197034_c(2)) {
         throw EntityArgument.field_210323_f.create();
      }
   }

   public Entity func_197340_a(CommandSource var1) throws CommandSyntaxException {
      this.func_210324_e(☃);
      List<? extends Entity> ☃ = this.func_197341_b(☃);
      if (☃.isEmpty()) {
         throw EntityArgument.field_197101_d.create();
      } else if (☃.size() > 1) {
         throw EntityArgument.field_197098_a.create();
      } else {
         return (Entity)☃.get(0);
      }
   }

   public List<? extends Entity> func_197341_b(CommandSource var1) throws CommandSyntaxException {
      this.func_210324_e(☃);
      if (!this.field_197355_b) {
         return this.func_197342_d(☃);
      } else if (this.field_197363_j != null) {
         EntityPlayerMP ☃ = ☃.func_197028_i().func_184103_al().func_152612_a(this.field_197363_j);
         return (List<? extends Entity>)(☃ == null ? Collections.emptyList() : Lists.newArrayList(☃));
      } else if (this.field_197364_k != null) {
         for(WorldServer ☃ : ☃.func_197028_i().func_212370_w()) {
            Entity ☃x = ☃.func_175733_a(this.field_197364_k);
            if (☃x != null) {
               return Lists.newArrayList(☃x);
            }
         }

         return Collections.emptyList();
      } else {
         Vec3d ☃ = (Vec3d)this.field_197359_f.apply(☃.func_197036_d());
         Predicate<Entity> ☃x = this.func_197349_a(☃);
         if (this.field_197362_i) {
            return (List<? extends Entity>)(☃.func_197022_f() != null && ☃x.test(☃.func_197022_f())
               ? Lists.newArrayList(☃.func_197022_f())
               : Collections.emptyList());
         } else {
            List<Entity> ☃ = Lists.<Entity>newArrayList();
            if (this.func_197353_d()) {
               this.func_197348_a(☃, ☃.func_197023_e(), ☃, ☃x);
            } else {
               for(WorldServer ☃ : ☃.func_197028_i().func_212370_w()) {
                  this.func_197348_a(☃, ☃, ☃, ☃x);
               }
            }

            return this.func_197345_a(☃, ☃);
         }
      }
   }

   private void func_197348_a(List<Entity> var1, WorldServer var2, Vec3d var3, Predicate<Entity> var4) {
      if (this.field_197360_g != null) {
         ☃.addAll(☃.func_175647_a(this.field_201957_l, this.field_197360_g.func_191194_a(☃), ☃::test));
      } else {
         ☃.addAll(☃.func_175644_a(this.field_201957_l, ☃::test));
      }
   }

   public EntityPlayerMP func_197347_c(CommandSource var1) throws CommandSyntaxException {
      this.func_210324_e(☃);
      List<EntityPlayerMP> ☃ = this.func_197342_d(☃);
      if (☃.size() != 1) {
         throw EntityArgument.field_197102_e.create();
      } else {
         return (EntityPlayerMP)☃.get(0);
      }
   }

   public List<EntityPlayerMP> func_197342_d(CommandSource var1) throws CommandSyntaxException {
      this.func_210324_e(☃);
      if (this.field_197363_j != null) {
         EntityPlayerMP ☃ = ☃.func_197028_i().func_184103_al().func_152612_a(this.field_197363_j);
         return (List<EntityPlayerMP>)(☃ == null ? Collections.emptyList() : Lists.<EntityPlayerMP>newArrayList(☃));
      } else if (this.field_197364_k != null) {
         EntityPlayerMP ☃ = ☃.func_197028_i().func_184103_al().func_177451_a(this.field_197364_k);
         return (List<EntityPlayerMP>)(☃ == null ? Collections.emptyList() : Lists.<EntityPlayerMP>newArrayList(☃));
      } else {
         Vec3d ☃ = (Vec3d)this.field_197359_f.apply(☃.func_197036_d());
         Predicate<Entity> ☃x = this.func_197349_a(☃);
         if (this.field_197362_i) {
            if (☃.func_197022_f() instanceof EntityPlayerMP) {
               EntityPlayerMP ☃xx = (EntityPlayerMP)☃.func_197022_f();
               if (☃x.test(☃xx)) {
                  return Lists.<EntityPlayerMP>newArrayList(☃xx);
               }
            }

            return Collections.emptyList();
         } else {
            List<EntityPlayerMP> ☃;
            if (this.func_197353_d()) {
               ☃ = ☃.func_197023_e().func_175661_b(EntityPlayerMP.class, ☃x::test);
            } else {
               ☃ = Lists.<EntityPlayerMP>newArrayList();

               for(EntityPlayerMP ☃ : ☃.func_197028_i().func_184103_al().func_181057_v()) {
                  if (☃x.test(☃)) {
                     ☃.add(☃);
                  }
               }
            }

            return this.func_197345_a(☃, ☃);
         }
      }
   }

   private Predicate<Entity> func_197349_a(Vec3d var1) {
      Predicate<Entity> ☃ = this.field_197357_d;
      if (this.field_197360_g != null) {
         AxisAlignedBB ☃x = this.field_197360_g.func_191194_a(☃);
         ☃ = ☃.and(var1x -> ☃.func_72326_a(var1x.func_174813_aQ()));
      }

      if (!this.field_197358_e.func_211335_c()) {
         ☃ = ☃.and(var2x -> this.field_197358_e.func_211351_a(var2x.func_195048_a(☃)));
      }

      return ☃;
   }

   private <T extends Entity> List<T> func_197345_a(Vec3d var1, List<T> var2) {
      if (☃.size() > 1) {
         this.field_197361_h.accept(☃, ☃);
      }

      return ☃.subList(0, Math.min(this.field_197354_a, ☃.size()));
   }

   public static ITextComponent func_197350_a(List<? extends Entity> var0) {
      return TextComponentUtils.func_197677_b(☃, Entity::func_145748_c_);
   }
}
