package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.MinMaxBoundsWrapped;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;

public class EntityOptions {
   private static final Map<String, EntityOptions.IOptionHandler> field_197478_k = Maps.newHashMap();
   public static final DynamicCommandExceptionType field_197468_a = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.entity.options.unknown", var0)
   );
   public static final DynamicCommandExceptionType field_202058_b = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.entity.options.inapplicable", var0)
   );
   public static final SimpleCommandExceptionType field_197469_b = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.entity.options.distance.negative")
   );
   public static final SimpleCommandExceptionType field_197471_d = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.entity.options.level.negative")
   );
   public static final SimpleCommandExceptionType field_197472_e = new SimpleCommandExceptionType(
      new TextComponentTranslation("argument.entity.options.limit.toosmall")
   );
   public static final DynamicCommandExceptionType field_197475_h = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.entity.options.sort.irreversible", var0)
   );
   public static final DynamicCommandExceptionType field_197476_i = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.entity.options.mode.invalid", var0)
   );
   public static final DynamicCommandExceptionType field_197477_j = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.entity.options.type.invalid", var0)
   );

   private static void func_202024_a(String var0, EntityOptions.Filter var1, Predicate<EntitySelectorParser> var2, ITextComponent var3) {
      field_197478_k.put(☃, new EntityOptions.IOptionHandler(☃, ☃, ☃));
   }

   public static void func_197445_a() {
      if (field_197478_k.isEmpty()) {
         func_202024_a("name", var0 -> {
            int ☃ = var0.func_197398_f().getCursor();
            boolean ☃x = var0.func_197378_e();
            String ☃xx = var0.func_197398_f().readString();
            if (var0.func_201997_v() && !☃x) {
               var0.func_197398_f().setCursor(☃);
               throw field_202058_b.createWithContext(var0.func_197398_f(), "name");
            } else {
               if (☃x) {
                  var0.func_201998_d(true);
               } else {
                  var0.func_201990_c(true);
               }

               var0.func_197401_a(var2x -> var2x.func_200200_C_().func_150261_e().equals(☃) != ☃);
            }
         }, var0 -> !var0.func_201984_u(), new TextComponentTranslation("argument.entity.options.name.description"));
         func_202024_a("distance", var0 -> {
            int ☃ = var0.func_197398_f().getCursor();
            MinMaxBounds.FloatBound ☃x = MinMaxBounds.FloatBound.func_211357_a(var0.func_197398_f());
            if ((☃x.func_196973_a() == null || !(☃x.func_196973_a() < 0.0F)) && (☃x.func_196977_b() == null || !(☃x.func_196977_b() < 0.0F))) {
               var0.func_197397_a(☃x);
               var0.func_197365_g();
            } else {
               var0.func_197398_f().setCursor(☃);
               throw field_197469_b.createWithContext(var0.func_197398_f());
            }
         }, var0 -> var0.func_197370_h().func_211335_c(), new TextComponentTranslation("argument.entity.options.distance.description"));
         func_202024_a("level", var0 -> {
            int ☃ = var0.func_197398_f().getCursor();
            MinMaxBounds.IntBound ☃x = MinMaxBounds.IntBound.func_211342_a(var0.func_197398_f());
            if ((☃x.func_196973_a() == null || ☃x.func_196973_a() >= 0) && (☃x.func_196977_b() == null || ☃x.func_196977_b() >= 0)) {
               var0.func_197399_b(☃x);
               var0.func_197373_a(false);
            } else {
               var0.func_197398_f().setCursor(☃);
               throw field_197471_d.createWithContext(var0.func_197398_f());
            }
         }, var0 -> var0.func_197394_i().func_211335_c(), new TextComponentTranslation("argument.entity.options.level.description"));
         func_202024_a("x", var0 -> {
            var0.func_197365_g();
            var0.func_197384_a(var0.func_197398_f().readDouble());
         }, var0 -> var0.func_201965_l() == null, new TextComponentTranslation("argument.entity.options.x.description"));
         func_202024_a("y", var0 -> {
            var0.func_197365_g();
            var0.func_197395_b(var0.func_197398_f().readDouble());
         }, var0 -> var0.func_201991_m() == null, new TextComponentTranslation("argument.entity.options.y.description"));
         func_202024_a("z", var0 -> {
            var0.func_197365_g();
            var0.func_197372_c(var0.func_197398_f().readDouble());
         }, var0 -> var0.func_201983_n() == null, new TextComponentTranslation("argument.entity.options.z.description"));
         func_202024_a("dx", var0 -> {
            var0.func_197365_g();
            var0.func_197377_d(var0.func_197398_f().readDouble());
         }, var0 -> var0.func_201977_o() == null, new TextComponentTranslation("argument.entity.options.dx.description"));
         func_202024_a("dy", var0 -> {
            var0.func_197365_g();
            var0.func_197391_e(var0.func_197398_f().readDouble());
         }, var0 -> var0.func_201971_p() == null, new TextComponentTranslation("argument.entity.options.dy.description"));
         func_202024_a("dz", var0 -> {
            var0.func_197365_g();
            var0.func_197405_f(var0.func_197398_f().readDouble());
         }, var0 -> var0.func_201962_q() == null, new TextComponentTranslation("argument.entity.options.dz.description"));
         func_202024_a(
            "x_rotation",
            var0 -> var0.func_197389_c(MinMaxBoundsWrapped.func_207921_a(var0.func_197398_f(), true, MathHelper::func_76142_g)),
            var0 -> var0.func_201968_j() == MinMaxBoundsWrapped.field_207926_a,
            new TextComponentTranslation("argument.entity.options.x_rotation.description")
         );
         func_202024_a(
            "y_rotation",
            var0 -> var0.func_197387_d(MinMaxBoundsWrapped.func_207921_a(var0.func_197398_f(), true, MathHelper::func_76142_g)),
            var0 -> var0.func_201980_k() == MinMaxBoundsWrapped.field_207926_a,
            new TextComponentTranslation("argument.entity.options.y_rotation.description")
         );
         func_202024_a("limit", var0 -> {
            int ☃ = var0.func_197398_f().getCursor();
            int ☃x = var0.func_197398_f().readInt();
            if (☃x < 1) {
               var0.func_197398_f().setCursor(☃);
               throw field_197472_e.createWithContext(var0.func_197398_f());
            } else {
               var0.func_197388_a(☃x);
               var0.func_201979_e(true);
            }
         }, var0 -> !var0.func_197381_m() && !var0.func_201967_w(), new TextComponentTranslation("argument.entity.options.limit.description"));
         func_202024_a("sort", var0 -> {
            int ☃x = var0.func_197398_f().getCursor();
            String ☃xx = var0.func_197398_f().readUnquotedString();
            var0.func_201978_a((var0x, var1x) -> ISuggestionProvider.func_197005_b(Arrays.asList("nearest", "furthest", "random", "arbitrary"), var0x));
            BiConsumer<Vec3d, List<? extends Entity>> ☃;
            switch(☃xx) {
               case "nearest":
                  ☃ = EntitySelectorParser.field_197414_g;
                  break;
               case "furthest":
                  ☃ = EntitySelectorParser.field_197415_h;
                  break;
               case "random":
                  ☃ = EntitySelectorParser.field_197416_i;
                  break;
               case "arbitrary":
                  ☃ = EntitySelectorParser.field_197413_f;
                  break;
               default:
                  var0.func_197398_f().setCursor(☃x);
                  throw field_197475_h.createWithContext(var0.func_197398_f(), ☃xx);
            }

            var0.func_197376_a(☃);
            var0.func_201986_f(true);
         }, var0 -> !var0.func_197381_m() && !var0.func_201976_x(), new TextComponentTranslation("argument.entity.options.sort.description"));
         func_202024_a("gamemode", var0 -> {
            var0.func_201978_a((var1x, var2x) -> {
               String ☃ = var1x.getRemaining().toLowerCase(Locale.ROOT);
               boolean ☃x = !var0.func_201961_z();
               boolean ☃xx = true;
               if (!☃.isEmpty()) {
                  if (☃.charAt(0) == '!') {
                     ☃x = false;
                     ☃ = ☃.substring(1);
                  } else {
                     ☃xx = false;
                  }
               }

               for(GameType ☃ : GameType.values()) {
                  if (☃ != GameType.NOT_SET && ☃.func_77149_b().toLowerCase(Locale.ROOT).startsWith(☃)) {
                     if (☃xx) {
                        var1x.suggest('!' + ☃.func_77149_b());
                     }

                     if (☃x) {
                        var1x.suggest(☃.func_77149_b());
                     }
                  }
               }

               return var1x.buildFuture();
            });
            int ☃ = var0.func_197398_f().getCursor();
            boolean ☃x = var0.func_197378_e();
            if (var0.func_201961_z() && !☃x) {
               var0.func_197398_f().setCursor(☃);
               throw field_202058_b.createWithContext(var0.func_197398_f(), "gamemode");
            } else {
               String ☃ = var0.func_197398_f().readUnquotedString();
               GameType ☃x = GameType.func_185328_a(☃, GameType.NOT_SET);
               if (☃x == GameType.NOT_SET) {
                  var0.func_197398_f().setCursor(☃);
                  throw field_197476_i.createWithContext(var0.func_197398_f(), ☃);
               } else {
                  var0.func_197373_a(false);
                  var0.func_197401_a(var2x -> {
                     if (!(var2x instanceof EntityPlayerMP)) {
                        return false;
                     } else {
                        GameType ☃ = ((EntityPlayerMP)var2x).field_71134_c.func_73081_b();
                        return ☃ ? ☃ != ☃ : ☃ == ☃;
                     }
                  });
                  if (☃x) {
                     var0.func_201973_h(true);
                  } else {
                     var0.func_201988_g(true);
                  }
               }
            }
         }, var0 -> !var0.func_201987_y(), new TextComponentTranslation("argument.entity.options.gamemode.description"));
         func_202024_a("team", var0 -> {
            boolean ☃ = var0.func_197378_e();
            String ☃x = var0.func_197398_f().readUnquotedString();
            var0.func_197401_a(var2x -> {
               if (!(var2x instanceof EntityLivingBase)) {
                  return false;
               } else {
                  Team ☃ = var2x.func_96124_cp();
                  String ☃x = ☃ == null ? "" : ☃.func_96661_b();
                  return ☃x.equals(☃) != ☃;
               }
            });
            if (☃) {
               var0.func_201958_j(true);
            } else {
               var0.func_201975_i(true);
            }
         }, var0 -> !var0.func_201960_A(), new TextComponentTranslation("argument.entity.options.team.description"));
         func_202024_a("type", var0 -> {
            var0.func_201978_a((var1x, var2x) -> {
               ISuggestionProvider.func_197006_a(IRegistry.field_212629_r.func_148742_b(), var1x, String.valueOf('!'));
               if (!var0.func_201985_F()) {
                  ISuggestionProvider.func_197014_a(IRegistry.field_212629_r.func_148742_b(), var1x);
               }

               return var1x.buildFuture();
            });
            int ☃ = var0.func_197398_f().getCursor();
            boolean ☃x = var0.func_197378_e();
            if (var0.func_201985_F() && !☃x) {
               var0.func_197398_f().setCursor(☃);
               throw field_202058_b.createWithContext(var0.func_197398_f(), "type");
            } else {
               ResourceLocation ☃ = ResourceLocation.func_195826_a(var0.func_197398_f());
               EntityType<? extends Entity> ☃x = (EntityType)IRegistry.field_212629_r.func_212608_b(☃);
               if (☃x == null) {
                  var0.func_197398_f().setCursor(☃);
                  throw field_197477_j.createWithContext(var0.func_197398_f(), ☃.toString());
               } else {
                  if (Objects.equals(EntityType.field_200729_aH, ☃x) && !☃x) {
                     var0.func_197373_a(false);
                  }

                  var0.func_197401_a(var2x -> Objects.equals(☃, var2x.func_200600_R()) != ☃);
                  if (☃x) {
                     var0.func_201982_C();
                  } else {
                     var0.func_201964_a(☃x.func_201760_c());
                  }
               }
            }
         }, var0 -> !var0.func_201963_E(), new TextComponentTranslation("argument.entity.options.type.description"));
         func_202024_a("tag", var0 -> {
            boolean ☃ = var0.func_197378_e();
            String ☃x = var0.func_197398_f().readUnquotedString();
            var0.func_197401_a(var2x -> {
               if ("".equals(☃)) {
                  return var2x.func_184216_O().isEmpty() != ☃;
               } else {
                  return var2x.func_184216_O().contains(☃) != ☃;
               }
            });
         }, var0 -> true, new TextComponentTranslation("argument.entity.options.tag.description"));
         func_202024_a("nbt", var0 -> {
            boolean ☃ = var0.func_197378_e();
            NBTTagCompound ☃x = new JsonToNBT(var0.func_197398_f()).func_193593_f();
            var0.func_197401_a(var2x -> {
               NBTTagCompound ☃ = var2x.func_189511_e(new NBTTagCompound());
               if (var2x instanceof EntityPlayerMP) {
                  ItemStack ☃x = ((EntityPlayerMP)var2x).field_71071_by.func_70448_g();
                  if (!☃x.func_190926_b()) {
                     ☃.func_74782_a("SelectedItem", ☃x.func_77955_b(new NBTTagCompound()));
                  }
               }

               return NBTUtil.func_181123_a(☃, ☃, true) != ☃;
            });
         }, var0 -> true, new TextComponentTranslation("argument.entity.options.nbt.description"));
         func_202024_a("scores", var0 -> {
            StringReader ☃ = var0.func_197398_f();
            Map<String, MinMaxBounds.IntBound> ☃x = Maps.newHashMap();
            ☃.expect('{');
            ☃.skipWhitespace();

            while(☃.canRead() && ☃.peek() != '}') {
               ☃.skipWhitespace();
               String ☃xx = ☃.readUnquotedString();
               ☃.skipWhitespace();
               ☃.expect('=');
               ☃.skipWhitespace();
               MinMaxBounds.IntBound ☃xxx = MinMaxBounds.IntBound.func_211342_a(☃);
               ☃x.put(☃xx, ☃xxx);
               ☃.skipWhitespace();
               if (☃.canRead() && ☃.peek() == ',') {
                  ☃.skip();
               }
            }

            ☃.expect('}');
            if (!☃x.isEmpty()) {
               var0.func_197401_a(var1x -> {
                  Scoreboard ☃ = var1x.func_184102_h().func_200251_aP();
                  String ☃x = var1x.func_195047_I_();

                  for(Entry<String, MinMaxBounds.IntBound> ☃xx : ☃.entrySet()) {
                     ScoreObjective ☃xxx = ☃.func_96518_b((String)☃xx.getKey());
                     if (☃xxx == null) {
                        return false;
                     }

                     if (!☃.func_178819_b(☃x, ☃xxx)) {
                        return false;
                     }

                     Score ☃xxx = ☃.func_96529_a(☃x, ☃xxx);
                     int ☃xxxx = ☃xxx.func_96652_c();
                     if (!((MinMaxBounds.IntBound)☃xx.getValue()).func_211339_d(☃xxxx)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            var0.func_201970_k(true);
         }, var0 -> !var0.func_201995_G(), new TextComponentTranslation("argument.entity.options.scores.description"));
         func_202024_a("advancements", var0 -> {
            StringReader ☃ = var0.func_197398_f();
            Map<ResourceLocation, Predicate<AdvancementProgress>> ☃x = Maps.newHashMap();
            ☃.expect('{');
            ☃.skipWhitespace();

            while(☃.canRead() && ☃.peek() != '}') {
               ☃.skipWhitespace();
               ResourceLocation ☃xx = ResourceLocation.func_195826_a(☃);
               ☃.skipWhitespace();
               ☃.expect('=');
               ☃.skipWhitespace();
               if (☃.canRead() && ☃.peek() == '{') {
                  Map<String, Predicate<CriterionProgress>> ☃xxx = Maps.newHashMap();
                  ☃.skipWhitespace();
                  ☃.expect('{');
                  ☃.skipWhitespace();

                  while(☃.canRead() && ☃.peek() != '}') {
                     ☃.skipWhitespace();
                     String ☃xxxx = ☃.readUnquotedString();
                     ☃.skipWhitespace();
                     ☃.expect('=');
                     ☃.skipWhitespace();
                     boolean ☃xxxxx = ☃.readBoolean();
                     ☃xxx.put(☃xxxx, (Predicate)var1x -> var1x.func_192151_a() == ☃);
                     ☃.skipWhitespace();
                     if (☃.canRead() && ☃.peek() == ',') {
                        ☃.skip();
                     }
                  }

                  ☃.skipWhitespace();
                  ☃.expect('}');
                  ☃.skipWhitespace();
                  ☃x.put(☃xx, (Predicate)var1x -> {
                     for(Entry<String, Predicate<CriterionProgress>> ☃ : ☃.entrySet()) {
                        CriterionProgress ☃x = var1x.func_192106_c((String)☃.getKey());
                        if (☃x == null || !((Predicate)☃.getValue()).test(☃x)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean ☃xx = ☃.readBoolean();
                  ☃x.put(☃xx, (Predicate)var1x -> var1x.func_192105_a() == ☃);
               }

               ☃.skipWhitespace();
               if (☃.canRead() && ☃.peek() == ',') {
                  ☃.skip();
               }
            }

            ☃.expect('}');
            if (!☃x.isEmpty()) {
               var0.func_197401_a(var1x -> {
                  if (!(var1x instanceof EntityPlayerMP)) {
                     return false;
                  } else {
                     EntityPlayerMP ☃ = (EntityPlayerMP)var1x;
                     PlayerAdvancements ☃x = ☃.func_192039_O();
                     AdvancementManager ☃xx = ☃.func_184102_h().func_191949_aK();

                     for(Entry<ResourceLocation, Predicate<AdvancementProgress>> ☃xxx : ☃.entrySet()) {
                        Advancement ☃xxxx = ☃xx.func_192778_a((ResourceLocation)☃xxx.getKey());
                        if (☃xxxx == null || !((Predicate)☃xxx.getValue()).test(☃x.func_192747_a(☃xxxx))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               var0.func_197373_a(false);
            }

            var0.func_201992_l(true);
         }, var0 -> !var0.func_201966_H(), new TextComponentTranslation("argument.entity.options.advancements.description"));
      }
   }

   public static EntityOptions.Filter func_202017_a(EntitySelectorParser var0, String var1, int var2) throws CommandSyntaxException {
      EntityOptions.IOptionHandler ☃ = (EntityOptions.IOptionHandler)field_197478_k.get(☃);
      if (☃ != null) {
         if (☃.field_202013_b.test(☃)) {
            return ☃.field_202012_a;
         } else {
            throw field_202058_b.createWithContext(☃.func_197398_f(), ☃);
         }
      } else {
         ☃.func_197398_f().setCursor(☃);
         throw field_197468_a.createWithContext(☃.func_197398_f(), ☃);
      }
   }

   public static void func_202049_a(EntitySelectorParser var0, SuggestionsBuilder var1) {
      String ☃ = ☃.getRemaining().toLowerCase(Locale.ROOT);

      for(Entry<String, EntityOptions.IOptionHandler> ☃x : field_197478_k.entrySet()) {
         if (((EntityOptions.IOptionHandler)☃x.getValue()).field_202013_b.test(☃) && ((String)☃x.getKey()).toLowerCase(Locale.ROOT).startsWith(☃)) {
            ☃.suggest((String)☃x.getKey() + '=', ((EntityOptions.IOptionHandler)☃x.getValue()).field_202014_c);
         }
      }
   }

   public interface Filter {
      void handle(EntitySelectorParser var1) throws CommandSyntaxException;
   }

   static class IOptionHandler {
      public final EntityOptions.Filter field_202012_a;
      public final Predicate<EntitySelectorParser> field_202013_b;
      public final ITextComponent field_202014_c;

      private IOptionHandler(EntityOptions.Filter var1, Predicate<EntitySelectorParser> var2, ITextComponent var3) {
         this.field_202012_a = ☃;
         this.field_202013_b = ☃;
         this.field_202014_c = ☃;
      }
   }
}
