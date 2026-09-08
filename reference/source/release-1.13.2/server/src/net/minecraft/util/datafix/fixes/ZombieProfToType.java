package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Random;
import net.minecraft.util.datafix.TypeReferences;

public class ZombieProfToType extends NamedEntityFix {
   private static final Random field_190049_a = new Random();

   public ZombieProfToType(Schema var1, boolean var2) {
      super(☃, ☃, "EntityZombieVillagerTypeFix", TypeReferences.field_211299_o, "Zombie");
   }

   public Dynamic<?> func_209656_a(Dynamic<?> var1) {
      if (☃.getBoolean("IsVillager")) {
         if (!☃.get("ZombieType").isPresent()) {
            int ☃ = this.func_191277_a(☃.getInt("VillagerProfession", -1));
            if (☃ == -1) {
               ☃ = this.func_191277_a(field_190049_a.nextInt(6));
            }

            ☃ = ☃.set("ZombieType", ☃.createInt(☃));
         }

         ☃ = ☃.remove("IsVillager");
      }

      return ☃;
   }

   private int func_191277_a(int var1) {
      return ☃ >= 0 && ☃ < 6 ? ☃ : -1;
   }

   @Override
   protected Typed<?> func_207419_a(Typed<?> var1) {
      return ☃.update(DSL.remainderFinder(), this::func_209656_a);
   }
}
