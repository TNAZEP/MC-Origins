package net.minecraft.entity.ai;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAITasks {
   private static final Logger field_151506_a = LogManager.getLogger();
   private final Set<EntityAITasks.EntityAITaskEntry> field_75782_a = Sets.<EntityAITasks.EntityAITaskEntry>newLinkedHashSet();
   private final Set<EntityAITasks.EntityAITaskEntry> field_75780_b = Sets.<EntityAITasks.EntityAITaskEntry>newLinkedHashSet();
   private final Profiler field_75781_c;
   private int field_75778_d;
   private int field_75779_e = 3;
   private int field_188529_g;

   public EntityAITasks(Profiler var1) {
      this.field_75781_c = ☃;
   }

   public void func_75776_a(int var1, EntityAIBase var2) {
      this.field_75782_a.add(new EntityAITasks.EntityAITaskEntry(☃, ☃));
   }

   public void func_85156_a(EntityAIBase var1) {
      Iterator<EntityAITasks.EntityAITaskEntry> ☃ = this.field_75782_a.iterator();

      while(☃.hasNext()) {
         EntityAITasks.EntityAITaskEntry ☃x = (EntityAITasks.EntityAITaskEntry)☃.next();
         EntityAIBase ☃xx = ☃x.field_75733_a;
         if (☃xx == ☃) {
            if (☃x.field_188524_c) {
               ☃x.field_188524_c = false;
               ☃x.field_75733_a.func_75251_c();
               this.field_75780_b.remove(☃x);
            }

            ☃.remove();
            return;
         }
      }
   }

   public void func_75774_a() {
      this.field_75781_c.func_76320_a("goalSetup");
      if (this.field_75778_d++ % this.field_75779_e == 0) {
         for(EntityAITasks.EntityAITaskEntry ☃ : this.field_75782_a) {
            if (☃.field_188524_c) {
               if (!this.func_75775_b(☃) || !this.func_75773_a(☃)) {
                  ☃.field_188524_c = false;
                  ☃.field_75733_a.func_75251_c();
                  this.field_75780_b.remove(☃);
               }
            } else if (this.func_75775_b(☃) && ☃.field_75733_a.func_75250_a()) {
               ☃.field_188524_c = true;
               ☃.field_75733_a.func_75249_e();
               this.field_75780_b.add(☃);
            }
         }
      } else {
         Iterator<EntityAITasks.EntityAITaskEntry> ☃ = this.field_75780_b.iterator();

         while(☃.hasNext()) {
            EntityAITasks.EntityAITaskEntry ☃x = (EntityAITasks.EntityAITaskEntry)☃.next();
            if (!this.func_75773_a(☃x)) {
               ☃x.field_188524_c = false;
               ☃x.field_75733_a.func_75251_c();
               ☃.remove();
            }
         }
      }

      this.field_75781_c.func_76319_b();
      if (!this.field_75780_b.isEmpty()) {
         this.field_75781_c.func_76320_a("goalTick");

         for(EntityAITasks.EntityAITaskEntry ☃ : this.field_75780_b) {
            ☃.field_75733_a.func_75246_d();
         }

         this.field_75781_c.func_76319_b();
      }
   }

   private boolean func_75773_a(EntityAITasks.EntityAITaskEntry var1) {
      return ☃.field_75733_a.func_75253_b();
   }

   private boolean func_75775_b(EntityAITasks.EntityAITaskEntry var1) {
      if (this.field_75780_b.isEmpty()) {
         return true;
      } else if (this.func_188528_b(☃.field_75733_a.func_75247_h())) {
         return false;
      } else {
         for(EntityAITasks.EntityAITaskEntry ☃ : this.field_75780_b) {
            if (☃ != ☃) {
               if (☃.field_75731_b >= ☃.field_75731_b) {
                  if (!this.func_75777_a(☃, ☃)) {
                     return false;
                  }
               } else if (!☃.field_75733_a.func_75252_g()) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private boolean func_75777_a(EntityAITasks.EntityAITaskEntry var1, EntityAITasks.EntityAITaskEntry var2) {
      return (☃.field_75733_a.func_75247_h() & ☃.field_75733_a.func_75247_h()) == 0;
   }

   public boolean func_188528_b(int var1) {
      return (this.field_188529_g & ☃) > 0;
   }

   public void func_188526_c(int var1) {
      this.field_188529_g |= ☃;
   }

   public void func_188525_d(int var1) {
      this.field_188529_g &= ~☃;
   }

   public void func_188527_a(int var1, boolean var2) {
      if (☃) {
         this.func_188525_d(☃);
      } else {
         this.func_188526_c(☃);
      }
   }

   class EntityAITaskEntry {
      public final EntityAIBase field_75733_a;
      public final int field_75731_b;
      public boolean field_188524_c;

      public EntityAITaskEntry(int var2, EntityAIBase var3) {
         this.field_75731_b = ☃;
         this.field_75733_a = ☃;
      }

      public boolean equals(@Nullable Object var1) {
         if (this == ☃) {
            return true;
         } else {
            return ☃ != null && this.getClass() == ☃.getClass() ? this.field_75733_a.equals(((EntityAITasks.EntityAITaskEntry)☃).field_75733_a) : false;
         }
      }

      public int hashCode() {
         return this.field_75733_a.hashCode();
      }
   }
}
