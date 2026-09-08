package net.minecraft.advancements.criterion;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public class EntityTypePredicate {
   public static final EntityTypePredicate field_209371_a = new EntityTypePredicate();
   private static final Joiner field_209372_b = Joiner.on(", ");
   @Nullable
   private final EntityType<?> field_209373_c;

   public EntityTypePredicate(EntityType<?> var1) {
      this.field_209373_c = ☃;
   }

   private EntityTypePredicate() {
      this.field_209373_c = null;
   }

   public boolean func_209368_a(EntityType<?> var1) {
      return this.field_209373_c == null || this.field_209373_c == ☃;
   }

   public static EntityTypePredicate func_209370_a(@Nullable JsonElement var0) {
      if (☃ != null && !☃.isJsonNull()) {
         String ☃ = JsonUtils.func_151206_a(☃, "type");
         ResourceLocation ☃x = new ResourceLocation(☃);
         EntityType<?> ☃xx = IRegistry.field_212629_r.func_212608_b(☃x);
         if (☃xx == null) {
            throw new JsonSyntaxException("Unknown entity type '" + ☃x + "', valid types are: " + field_209372_b.join(IRegistry.field_212629_r.func_148742_b()));
         } else {
            return new EntityTypePredicate(☃xx);
         }
      } else {
         return field_209371_a;
      }
   }

   public JsonElement func_209369_a() {
      return (JsonElement)(this.field_209373_c == null
         ? JsonNull.INSTANCE
         : new JsonPrimitive(IRegistry.field_212629_r.func_177774_c(this.field_209373_c).toString()));
   }
}
