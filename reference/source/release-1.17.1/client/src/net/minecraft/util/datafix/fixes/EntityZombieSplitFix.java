package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class EntityZombieSplitFix extends SimpleEntityRenameFix {
   public EntityZombieSplitFix(Schema var1, boolean var2) {
      super("EntityZombieSplitFix", â˜ƒ, â˜ƒ);
   }

   @Override
   protected Pair<String, Dynamic<?>> getNewNameAndTag(String var1, Dynamic<?> var2) {
      if (Objects.equals("Zombie", â˜ƒ)) {
         String â˜ƒ = "Zombie";
         int â˜ƒx = â˜ƒ.get("ZombieType").asInt(0);
         switch(â˜ƒx) {
            case 0:
            default:
               break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               â˜ƒ = "ZombieVillager";
               â˜ƒ = â˜ƒ.set("Profession", â˜ƒ.createInt(â˜ƒx - 1));
               break;
            case 6:
               â˜ƒ = "Husk";
         }

         â˜ƒ = â˜ƒ.remove("ZombieType");
         return Pair.of(â˜ƒ, â˜ƒ);
      } else {
         return Pair.of(â˜ƒ, â˜ƒ);
      }
   }
}
