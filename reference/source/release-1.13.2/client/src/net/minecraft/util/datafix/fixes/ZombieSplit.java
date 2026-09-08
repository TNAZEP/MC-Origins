package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;

public class ZombieSplit extends EntityRenameHelper {
   public ZombieSplit(Schema var1, boolean var2) {
      super("EntityZombieSplitFix", ☃, ☃);
   }

   @Override
   protected Pair<String, Dynamic<?>> func_209758_a(String var1, Dynamic<?> var2) {
      if (Objects.equals("Zombie", ☃)) {
         String ☃ = "Zombie";
         int ☃x = ☃.getInt("ZombieType");
         switch(☃x) {
            case 0:
            default:
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               ☃ = "ZombieVillager";
               ☃ = ☃.set("Profession", ☃.createInt(☃x - 1));
               break;
            case 6:
               ☃ = "Husk";
         }

         ☃ = ☃.remove("ZombieType");
         return Pair.of(☃, ☃);
      } else {
         return Pair.of(☃, ☃);
      }
   }
}
