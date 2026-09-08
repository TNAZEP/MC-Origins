package net.minecraft.command;

import com.mojang.brigadier.exceptions.BuiltInExceptionProvider;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.util.text.TextComponentTranslation;

public class TranslatableExceptionProvider implements BuiltInExceptionProvider {
   private static final Dynamic2CommandExceptionType field_208636_a = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.double.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType field_208637_b = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.double.big", var1, var0)
   );
   private static final Dynamic2CommandExceptionType field_208638_c = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.float.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType field_208639_d = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.float.big", var1, var0)
   );
   private static final Dynamic2CommandExceptionType field_208640_e = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.integer.low", var1, var0)
   );
   private static final Dynamic2CommandExceptionType field_208641_f = new Dynamic2CommandExceptionType(
      (var0, var1) -> new TextComponentTranslation("argument.integer.big", var1, var0)
   );
   private static final DynamicCommandExceptionType field_208642_g = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("argument.literal.incorrect", var0)
   );
   private static final SimpleCommandExceptionType field_208643_h = new SimpleCommandExceptionType(new TextComponentTranslation("parsing.quote.expected.start"));
   private static final SimpleCommandExceptionType field_208644_i = new SimpleCommandExceptionType(new TextComponentTranslation("parsing.quote.expected.end"));
   private static final DynamicCommandExceptionType field_208645_j = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("parsing.quote.escape", var0)
   );
   private static final DynamicCommandExceptionType field_208646_k = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("parsing.bool.invalid", var0)
   );
   private static final DynamicCommandExceptionType field_208647_l = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("parsing.int.invalid", var0)
   );
   private static final SimpleCommandExceptionType field_208648_m = new SimpleCommandExceptionType(new TextComponentTranslation("parsing.int.expected"));
   private static final DynamicCommandExceptionType field_208649_n = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("parsing.double.invalid", var0)
   );
   private static final SimpleCommandExceptionType field_208650_o = new SimpleCommandExceptionType(new TextComponentTranslation("parsing.double.expected"));
   private static final DynamicCommandExceptionType field_208651_p = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("parsing.float.invalid", var0)
   );
   private static final SimpleCommandExceptionType field_208652_q = new SimpleCommandExceptionType(new TextComponentTranslation("parsing.float.expected"));
   private static final SimpleCommandExceptionType field_208653_r = new SimpleCommandExceptionType(new TextComponentTranslation("parsing.bool.expected"));
   private static final DynamicCommandExceptionType field_208654_s = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("parsing.expected", var0)
   );
   private static final SimpleCommandExceptionType field_208655_t = new SimpleCommandExceptionType(new TextComponentTranslation("command.unknown.command"));
   private static final SimpleCommandExceptionType field_208656_u = new SimpleCommandExceptionType(new TextComponentTranslation("command.unknown.argument"));
   private static final SimpleCommandExceptionType field_208657_v = new SimpleCommandExceptionType(new TextComponentTranslation("command.expected.separator"));
   private static final DynamicCommandExceptionType field_208658_w = new DynamicCommandExceptionType(
      var0 -> new TextComponentTranslation("command.exception", var0)
   );

   @Override
   public Dynamic2CommandExceptionType doubleTooLow() {
      return field_208636_a;
   }

   @Override
   public Dynamic2CommandExceptionType doubleTooHigh() {
      return field_208637_b;
   }

   @Override
   public Dynamic2CommandExceptionType floatTooLow() {
      return field_208638_c;
   }

   @Override
   public Dynamic2CommandExceptionType floatTooHigh() {
      return field_208639_d;
   }

   @Override
   public Dynamic2CommandExceptionType integerTooLow() {
      return field_208640_e;
   }

   @Override
   public Dynamic2CommandExceptionType integerTooHigh() {
      return field_208641_f;
   }

   @Override
   public DynamicCommandExceptionType literalIncorrect() {
      return field_208642_g;
   }

   @Override
   public SimpleCommandExceptionType readerExpectedStartOfQuote() {
      return field_208643_h;
   }

   @Override
   public SimpleCommandExceptionType readerExpectedEndOfQuote() {
      return field_208644_i;
   }

   @Override
   public DynamicCommandExceptionType readerInvalidEscape() {
      return field_208645_j;
   }

   @Override
   public DynamicCommandExceptionType readerInvalidBool() {
      return field_208646_k;
   }

   @Override
   public DynamicCommandExceptionType readerInvalidInt() {
      return field_208647_l;
   }

   @Override
   public SimpleCommandExceptionType readerExpectedInt() {
      return field_208648_m;
   }

   @Override
   public DynamicCommandExceptionType readerInvalidDouble() {
      return field_208649_n;
   }

   @Override
   public SimpleCommandExceptionType readerExpectedDouble() {
      return field_208650_o;
   }

   @Override
   public DynamicCommandExceptionType readerInvalidFloat() {
      return field_208651_p;
   }

   @Override
   public SimpleCommandExceptionType readerExpectedFloat() {
      return field_208652_q;
   }

   @Override
   public SimpleCommandExceptionType readerExpectedBool() {
      return field_208653_r;
   }

   @Override
   public DynamicCommandExceptionType readerExpectedSymbol() {
      return field_208654_s;
   }

   @Override
   public SimpleCommandExceptionType dispatcherUnknownCommand() {
      return field_208655_t;
   }

   @Override
   public SimpleCommandExceptionType dispatcherUnknownArgument() {
      return field_208656_u;
   }

   @Override
   public SimpleCommandExceptionType dispatcherExpectedArgumentSeparator() {
      return field_208657_v;
   }

   @Override
   public DynamicCommandExceptionType dispatcherParseException() {
      return field_208658_w;
   }
}
