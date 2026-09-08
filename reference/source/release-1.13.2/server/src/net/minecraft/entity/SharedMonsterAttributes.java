package net.minecraft.entity;

import java.util.Collection;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SharedMonsterAttributes {
   private static final Logger field_151476_f = LogManager.getLogger();
   public static final IAttribute field_111267_a = new RangedAttribute(null, "generic.maxHealth", 20.0, 0.0, 1024.0)
      .func_111117_a("Max Health")
      .func_111112_a(true);
   public static final IAttribute field_111265_b = new RangedAttribute(null, "generic.followRange", 32.0, 0.0, 2048.0).func_111117_a("Follow Range");
   public static final IAttribute field_111266_c = new RangedAttribute(null, "generic.knockbackResistance", 0.0, 0.0, 1.0)
      .func_111117_a("Knockback Resistance");
   public static final IAttribute field_111263_d = new RangedAttribute(null, "generic.movementSpeed", 0.7F, 0.0, 1024.0)
      .func_111117_a("Movement Speed")
      .func_111112_a(true);
   public static final IAttribute field_193334_e = new RangedAttribute(null, "generic.flyingSpeed", 0.4F, 0.0, 1024.0)
      .func_111117_a("Flying Speed")
      .func_111112_a(true);
   public static final IAttribute field_111264_e = new RangedAttribute(null, "generic.attackDamage", 2.0, 0.0, 2048.0);
   public static final IAttribute field_188790_f = new RangedAttribute(null, "generic.attackSpeed", 4.0, 0.0, 1024.0).func_111112_a(true);
   public static final IAttribute field_188791_g = new RangedAttribute(null, "generic.armor", 0.0, 0.0, 30.0).func_111112_a(true);
   public static final IAttribute field_189429_h = new RangedAttribute(null, "generic.armorToughness", 0.0, 0.0, 20.0).func_111112_a(true);
   public static final IAttribute field_188792_h = new RangedAttribute(null, "generic.luck", 0.0, -1024.0, 1024.0).func_111112_a(true);

   public static NBTTagList func_111257_a(AbstractAttributeMap var0) {
      NBTTagList ☃ = new NBTTagList();

      for(IAttributeInstance ☃x : ☃.func_111146_a()) {
         ☃.add((INBTBase)func_111261_a(☃x));
      }

      return ☃;
   }

   private static NBTTagCompound func_111261_a(IAttributeInstance var0) {
      NBTTagCompound ☃ = new NBTTagCompound();
      IAttribute ☃x = ☃.func_111123_a();
      ☃.func_74778_a("Name", ☃x.func_111108_a());
      ☃.func_74780_a("Base", ☃.func_111125_b());
      Collection<AttributeModifier> ☃xx = ☃.func_111122_c();
      if (☃xx != null && !☃xx.isEmpty()) {
         NBTTagList ☃xxx = new NBTTagList();

         for(AttributeModifier ☃xxxx : ☃xx) {
            if (☃xxxx.func_111165_e()) {
               ☃xxx.add((INBTBase)func_111262_a(☃xxxx));
            }
         }

         ☃.func_74782_a("Modifiers", ☃xxx);
      }

      return ☃;
   }

   public static NBTTagCompound func_111262_a(AttributeModifier var0) {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74778_a("Name", ☃.func_111166_b());
      ☃.func_74780_a("Amount", ☃.func_111164_d());
      ☃.func_74768_a("Operation", ☃.func_111169_c());
      ☃.func_186854_a("UUID", ☃.func_111167_a());
      return ☃;
   }

   public static void func_151475_a(AbstractAttributeMap var0, NBTTagList var1) {
      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         IAttributeInstance ☃xx = ☃.func_111152_a(☃x.func_74779_i("Name"));
         if (☃xx == null) {
            field_151476_f.warn("Ignoring unknown attribute '{}'", ☃x.func_74779_i("Name"));
         } else {
            func_111258_a(☃xx, ☃x);
         }
      }
   }

   private static void func_111258_a(IAttributeInstance var0, NBTTagCompound var1) {
      ☃.func_111128_a(☃.func_74769_h("Base"));
      if (☃.func_150297_b("Modifiers", 9)) {
         NBTTagList ☃ = ☃.func_150295_c("Modifiers", 10);

         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            AttributeModifier ☃xx = func_111259_a(☃.func_150305_b(☃x));
            if (☃xx != null) {
               AttributeModifier ☃xxx = ☃.func_111127_a(☃xx.func_111167_a());
               if (☃xxx != null) {
                  ☃.func_111124_b(☃xxx);
               }

               ☃.func_111121_a(☃xx);
            }
         }
      }
   }

   @Nullable
   public static AttributeModifier func_111259_a(NBTTagCompound var0) {
      UUID ☃ = ☃.func_186857_a("UUID");

      try {
         return new AttributeModifier(☃, ☃.func_74779_i("Name"), ☃.func_74769_h("Amount"), ☃.func_74762_e("Operation"));
      } catch (Exception var3) {
         field_151476_f.warn("Unable to create attribute: {}", var3.getMessage());
         return null;
      }
   }
}
