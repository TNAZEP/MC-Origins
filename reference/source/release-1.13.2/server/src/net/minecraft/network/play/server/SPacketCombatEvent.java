package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class SPacketCombatEvent implements Packet<INetHandlerPlayClient> {
   public SPacketCombatEvent.Event field_179776_a;
   public int field_179774_b;
   public int field_179775_c;
   public int field_179772_d;
   public ITextComponent field_179773_e;

   public SPacketCombatEvent() {
   }

   public SPacketCombatEvent(CombatTracker var1, SPacketCombatEvent.Event var2) {
      this(☃, ☃, new TextComponentString(""));
   }

   public SPacketCombatEvent(CombatTracker var1, SPacketCombatEvent.Event var2, ITextComponent var3) {
      this.field_179776_a = ☃;
      EntityLivingBase ☃ = ☃.func_94550_c();
      switch(☃) {
         case END_COMBAT:
            this.field_179772_d = ☃.func_180134_f();
            this.field_179775_c = ☃ == null ? -1 : ☃.func_145782_y();
            break;
         case ENTITY_DIED:
            this.field_179774_b = ☃.func_180135_h().func_145782_y();
            this.field_179775_c = ☃ == null ? -1 : ☃.func_145782_y();
            this.field_179773_e = ☃;
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179776_a = ☃.func_179257_a(SPacketCombatEvent.Event.class);
      if (this.field_179776_a == SPacketCombatEvent.Event.END_COMBAT) {
         this.field_179772_d = ☃.func_150792_a();
         this.field_179775_c = ☃.readInt();
      } else if (this.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
         this.field_179774_b = ☃.func_150792_a();
         this.field_179775_c = ☃.readInt();
         this.field_179773_e = ☃.func_179258_d();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179249_a(this.field_179776_a);
      if (this.field_179776_a == SPacketCombatEvent.Event.END_COMBAT) {
         ☃.func_150787_b(this.field_179772_d);
         ☃.writeInt(this.field_179775_c);
      } else if (this.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED) {
         ☃.func_150787_b(this.field_179774_b);
         ☃.writeInt(this.field_179775_c);
         ☃.func_179256_a(this.field_179773_e);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_175098_a(this);
   }

   @Override
   public boolean func_211402_a() {
      return this.field_179776_a == SPacketCombatEvent.Event.ENTITY_DIED;
   }

   public static enum Event {
      ENTER_COMBAT,
      END_COMBAT,
      ENTITY_DIED;
   }
}
