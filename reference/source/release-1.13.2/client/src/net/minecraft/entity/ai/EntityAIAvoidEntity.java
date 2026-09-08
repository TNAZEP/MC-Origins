package net.minecraft.entity.ai;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

public class EntityAIAvoidEntity<T extends Entity> extends EntityAIBase {
   private final Predicate<Entity> field_179509_a = new Predicate<Entity>() {
      public boolean test(@Nullable Entity var1) {
         return ☃.func_70089_S()
            && EntityAIAvoidEntity.this.field_75380_a.func_70635_at().func_75522_a(☃)
            && !EntityAIAvoidEntity.this.field_75380_a.func_184191_r(☃);
      }
   };
   protected EntityCreature field_75380_a;
   private final double field_75378_b;
   private final double field_75379_c;
   protected T field_75376_d;
   private final float field_179508_f;
   private Path field_75374_f;
   private final PathNavigate field_75375_g;
   private final Class<T> field_181064_i;
   private final Predicate<? super Entity> field_179510_i;
   private final Predicate<? super Entity> field_203784_k;

   public EntityAIAvoidEntity(EntityCreature var1, Class<T> var2, float var3, double var4, double var6) {
      this(☃, ☃, var0 -> true, ☃, ☃, ☃, EntitySelectors.field_188444_d);
   }

   public EntityAIAvoidEntity(EntityCreature var1, Class<T> var2, Predicate<? super Entity> var3, float var4, double var5, double var7, Predicate<Entity> var9) {
      this.field_75380_a = ☃;
      this.field_181064_i = ☃;
      this.field_179510_i = ☃;
      this.field_179508_f = ☃;
      this.field_75378_b = ☃;
      this.field_75379_c = ☃;
      this.field_203784_k = ☃;
      this.field_75375_g = ☃.func_70661_as();
      this.func_75248_a(1);
   }

   public EntityAIAvoidEntity(EntityCreature var1, Class<T> var2, float var3, double var4, double var6, Predicate<Entity> var8) {
      this(☃, ☃, var0 -> true, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_75250_a() {
      List<T> ☃ = this.field_75380_a
         .field_70170_p
         .func_175647_a(
            this.field_181064_i,
            this.field_75380_a.func_174813_aQ().func_72314_b((double)this.field_179508_f, 3.0, (double)this.field_179508_f),
            var1x -> this.field_203784_k.test(var1x) && this.field_179509_a.test(var1x) && this.field_179510_i.test(var1x)
         );
      if (☃.isEmpty()) {
         return false;
      } else {
         this.field_75376_d = (T)☃.get(0);
         Vec3d ☃ = RandomPositionGenerator.func_75461_b(
            this.field_75380_a, 16, 7, new Vec3d(this.field_75376_d.field_70165_t, this.field_75376_d.field_70163_u, this.field_75376_d.field_70161_v)
         );
         if (☃ == null) {
            return false;
         } else if (this.field_75376_d.func_70092_e(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c) < this.field_75376_d.func_70068_e(this.field_75380_a)) {
            return false;
         } else {
            this.field_75374_f = this.field_75375_g.func_75488_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
            return this.field_75374_f != null;
         }
      }
   }

   @Override
   public boolean func_75253_b() {
      return !this.field_75375_g.func_75500_f();
   }

   @Override
   public void func_75249_e() {
      this.field_75375_g.func_75484_a(this.field_75374_f, this.field_75378_b);
   }

   @Override
   public void func_75251_c() {
      this.field_75376_d = null;
   }

   @Override
   public void func_75246_d() {
      if (this.field_75380_a.func_70068_e(this.field_75376_d) < 49.0) {
         this.field_75380_a.func_70661_as().func_75489_a(this.field_75379_c);
      } else {
         this.field_75380_a.func_70661_as().func_75489_a(this.field_75378_b);
      }
   }
}
