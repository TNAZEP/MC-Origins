package net.minecraft.client.gui.screens.inventory;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentScreen extends AbstractContainerScreen<EnchantmentMenu> {
   private static final ResourceLocation ENCHANTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation ENCHANTING_BOOK_LOCATION = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private final Random random = new Random();
   private BookModel bookModel;
   public int time;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   private ItemStack last = ItemStack.EMPTY;

   public EnchantmentScreen(EnchantmentMenu var1, Inventory var2, Component var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void init() {
      super.init();
      this.bookModel = new BookModel(this.minecraft.getEntityModels().bakeLayer(ModelLayers.BOOK));
   }

   @Override
   public void containerTick() {
      super.containerTick();
      this.tickBook();
   }

   @Override
   public boolean mouseClicked(double var1, double var3, int var5) {
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;

      for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
         double â˜ƒxxx = â˜ƒ - (double)(â˜ƒ + 60);
         double â˜ƒxxxx = â˜ƒ - (double)(â˜ƒx + 14 + 19 * â˜ƒxx);
         if (â˜ƒxxx >= 0.0 && â˜ƒxxxx >= 0.0 && â˜ƒxxx < 108.0 && â˜ƒxxxx < 19.0 && this.menu.clickMenuButton(this.minecraft.player, â˜ƒxx)) {
            this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, â˜ƒxx);
            return true;
         }
      }

      return super.mouseClicked(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void renderBg(PoseStack var1, float var2, int var3, int var4) {
      Lighting.setupForFlatItems();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, ENCHANTING_TABLE_LOCATION);
      int â˜ƒ = (this.width - this.imageWidth) / 2;
      int â˜ƒx = (this.height - this.imageHeight) / 2;
      this.blit(â˜ƒ, â˜ƒ, â˜ƒx, 0, 0, this.imageWidth, this.imageHeight);
      int â˜ƒxx = (int)this.minecraft.getWindow().getGuiScale();
      RenderSystem.viewport((this.width - 320) / 2 * â˜ƒxx, (this.height - 240) / 2 * â˜ƒxx, 320 * â˜ƒxx, 240 * â˜ƒxx);
      Matrix4f â˜ƒxxx = Matrix4f.createTranslateMatrix(-0.34F, 0.23F, 0.0F);
      â˜ƒxxx.multiply(Matrix4f.perspective(90.0, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.backupProjectionMatrix();
      RenderSystem.setProjectionMatrix(â˜ƒxxx);
      â˜ƒ.pushPose();
      PoseStack.Pose â˜ƒxxxx = â˜ƒ.last();
      â˜ƒxxxx.pose().setIdentity();
      â˜ƒxxxx.normal().setIdentity();
      â˜ƒ.translate(0.0, 3.3F, 1984.0);
      float â˜ƒxxxxx = 5.0F;
      â˜ƒ.scale(5.0F, 5.0F, 5.0F);
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(20.0F));
      float â˜ƒxxxxxx = Mth.lerp(â˜ƒ, this.oOpen, this.open);
      â˜ƒ.translate((double)((1.0F - â˜ƒxxxxxx) * 0.2F), (double)((1.0F - â˜ƒxxxxxx) * 0.1F), (double)((1.0F - â˜ƒxxxxxx) * 0.25F));
      float â˜ƒxxxxxxx = -(1.0F - â˜ƒxxxxxx) * 90.0F - 90.0F;
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxxxxxxx));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(180.0F));
      float â˜ƒxxxxxxxx = Mth.lerp(â˜ƒ, this.oFlip, this.flip) + 0.25F;
      float â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒ, this.oFlip, this.flip) + 0.75F;
      â˜ƒxxxxxxxx = (â˜ƒxxxxxxxx - (float)Mth.fastFloor((double)â˜ƒxxxxxxxx)) * 1.6F - 0.3F;
      â˜ƒxxxxxxxxx = (â˜ƒxxxxxxxxx - (float)Mth.fastFloor((double)â˜ƒxxxxxxxxx)) * 1.6F - 0.3F;
      if (â˜ƒxxxxxxxx < 0.0F) {
         â˜ƒxxxxxxxx = 0.0F;
      }

      if (â˜ƒxxxxxxxxx < 0.0F) {
         â˜ƒxxxxxxxxx = 0.0F;
      }

      if (â˜ƒxxxxxxxx > 1.0F) {
         â˜ƒxxxxxxxx = 1.0F;
      }

      if (â˜ƒxxxxxxxxx > 1.0F) {
         â˜ƒxxxxxxxxx = 1.0F;
      }

      this.bookModel.setupAnim(0.0F, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxx);
      MultiBufferSource.BufferSource â˜ƒ = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
      VertexConsumer â˜ƒx = â˜ƒ.getBuffer(this.bookModel.renderType(ENCHANTING_BOOK_LOCATION));
      this.bookModel.renderToBuffer(â˜ƒ, â˜ƒx, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.endBatch();
      â˜ƒ.popPose();
      RenderSystem.viewport(0, 0, this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
      RenderSystem.restoreProjectionMatrix();
      Lighting.setupFor3DItems();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNames.getInstance().initSeed((long)this.menu.getEnchantmentSeed());
      int â˜ƒxx = this.menu.getGoldCount();

      for(int â˜ƒxxx = 0; â˜ƒxxx < 3; ++â˜ƒxxx) {
         int â˜ƒxxxx = â˜ƒ + 60;
         int â˜ƒxxxxx = â˜ƒxxxx + 20;
         this.setBlitOffset(0);
         RenderSystem.setShader(GameRenderer::getPositionTexShader);
         RenderSystem.setShaderTexture(0, ENCHANTING_TABLE_LOCATION);
         int â˜ƒxxxxxx = this.menu.costs[â˜ƒxxx];
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         if (â˜ƒxxxxxx == 0) {
            this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒx + 14 + 19 * â˜ƒxxx, 0, 185, 108, 19);
         } else {
            String â˜ƒxxxx = â˜ƒxxxxxx + "";
            int â˜ƒxxxxx = 86 - this.font.width(â˜ƒxxxx);
            FormattedText â˜ƒxxxxxx = EnchantmentNames.getInstance().getRandomName(this.font, â˜ƒxxxxx);
            int â˜ƒxxxxxxx = 6839882;
            if ((â˜ƒxx < â˜ƒxxx + 1 || this.minecraft.player.experienceLevel < â˜ƒxxxxxx) && !this.minecraft.player.getAbilities().instabuild) {
               this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒx + 14 + 19 * â˜ƒxxx, 0, 185, 108, 19);
               this.blit(â˜ƒ, â˜ƒxxxx + 1, â˜ƒx + 15 + 19 * â˜ƒxxx, 16 * â˜ƒxxx, 239, 16, 16);
               this.font.drawWordWrap(â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒx + 16 + 19 * â˜ƒxxx, â˜ƒxxxxx, (â˜ƒxxxxxxx & 16711422) >> 1);
               â˜ƒxxxxxxx = 4226832;
            } else {
               int â˜ƒxxxx = â˜ƒ - (â˜ƒ + 60);
               int â˜ƒxxxxx = â˜ƒ - (â˜ƒx + 14 + 19 * â˜ƒxxx);
               if (â˜ƒxxxx >= 0 && â˜ƒxxxxx >= 0 && â˜ƒxxxx < 108 && â˜ƒxxxxx < 19) {
                  this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒx + 14 + 19 * â˜ƒxxx, 0, 204, 108, 19);
                  â˜ƒxxxxxxx = 16777088;
               } else {
                  this.blit(â˜ƒ, â˜ƒxxxx, â˜ƒx + 14 + 19 * â˜ƒxxx, 0, 166, 108, 19);
               }

               this.blit(â˜ƒ, â˜ƒxxxx + 1, â˜ƒx + 15 + 19 * â˜ƒxxx, 16 * â˜ƒxxx, 223, 16, 16);
               this.font.drawWordWrap(â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒx + 16 + 19 * â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxxxxx);
               â˜ƒxxxxxxx = 8453920;
            }

            this.font.drawShadow(â˜ƒ, â˜ƒxxxx, (float)(â˜ƒxxxxx + 86 - this.font.width(â˜ƒxxxx)), (float)(â˜ƒx + 16 + 19 * â˜ƒxxx + 7), â˜ƒxxxxxxx);
         }
      }
   }

   @Override
   public void render(PoseStack var1, int var2, int var3, float var4) {
      â˜ƒ = this.minecraft.getFrameTime();
      this.renderBackground(â˜ƒ);
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.renderTooltip(â˜ƒ, â˜ƒ, â˜ƒ);
      boolean â˜ƒ = this.minecraft.player.getAbilities().instabuild;
      int â˜ƒx = this.menu.getGoldCount();

      for(int â˜ƒxx = 0; â˜ƒxx < 3; ++â˜ƒxx) {
         int â˜ƒxxx = this.menu.costs[â˜ƒxx];
         Enchantment â˜ƒxxxx = Enchantment.byId(this.menu.enchantClue[â˜ƒxx]);
         int â˜ƒxxxxx = this.menu.levelClue[â˜ƒxx];
         int â˜ƒxxxxxx = â˜ƒxx + 1;
         if (this.isHovering(60, 14 + 19 * â˜ƒxx, 108, 17, (double)â˜ƒ, (double)â˜ƒ) && â˜ƒxxx > 0 && â˜ƒxxxxx >= 0 && â˜ƒxxxx != null) {
            List<Component> â˜ƒxxxxxxx = Lists.<Component>newArrayList();
            â˜ƒxxxxxxx.add(new TranslatableComponent("container.enchant.clue", â˜ƒxxxx.getFullname(â˜ƒxxxxx)).withStyle(ChatFormatting.WHITE));
            if (!â˜ƒ) {
               â˜ƒxxxxxxx.add(TextComponent.EMPTY);
               if (this.minecraft.player.experienceLevel < â˜ƒxxx) {
                  â˜ƒxxxxxxx.add(new TranslatableComponent("container.enchant.level.requirement", this.menu.costs[â˜ƒxx]).withStyle(ChatFormatting.RED));
               } else {
                  MutableComponent â˜ƒxxxxxxxx;
                  if (â˜ƒxxxxxx == 1) {
                     â˜ƒxxxxxxxx = new TranslatableComponent("container.enchant.lapis.one");
                  } else {
                     â˜ƒxxxxxxxx = new TranslatableComponent("container.enchant.lapis.many", â˜ƒxxxxxx);
                  }

                  â˜ƒxxxxxxx.add(â˜ƒxxxxxxxx.withStyle(â˜ƒx >= â˜ƒxxxxxx ? ChatFormatting.GRAY : ChatFormatting.RED));
                  MutableComponent â˜ƒxxxxxxxx;
                  if (â˜ƒxxxxxx == 1) {
                     â˜ƒxxxxxxxx = new TranslatableComponent("container.enchant.level.one");
                  } else {
                     â˜ƒxxxxxxxx = new TranslatableComponent("container.enchant.level.many", â˜ƒxxxxxx);
                  }

                  â˜ƒxxxxxxx.add(â˜ƒxxxxxxxx.withStyle(ChatFormatting.GRAY));
               }
            }

            this.renderComponentTooltip(â˜ƒ, â˜ƒxxxxxxx, â˜ƒ, â˜ƒ);
            break;
         }
      }
   }

   public void tickBook() {
      ItemStack â˜ƒ = this.menu.getSlot(0).getItem();
      if (!ItemStack.matches(â˜ƒ, this.last)) {
         this.last = â˜ƒ;

         do {
            this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while(this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
      }

      ++this.time;
      this.oFlip = this.flip;
      this.oOpen = this.open;
      boolean â˜ƒ = false;

      for(int â˜ƒx = 0; â˜ƒx < 3; ++â˜ƒx) {
         if (this.menu.costs[â˜ƒx] != 0) {
            â˜ƒ = true;
         }
      }

      if (â˜ƒ) {
         this.open += 0.2F;
      } else {
         this.open -= 0.2F;
      }

      this.open = Mth.clamp(this.open, 0.0F, 1.0F);
      float â˜ƒx = (this.flipT - this.flip) * 0.4F;
      float â˜ƒxx = 0.2F;
      â˜ƒx = Mth.clamp(â˜ƒx, -0.2F, 0.2F);
      this.flipA += (â˜ƒx - this.flipA) * 0.9F;
      this.flip += this.flipA;
   }
}
