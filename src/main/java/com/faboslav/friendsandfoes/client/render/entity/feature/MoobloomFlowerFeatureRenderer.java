package com.faboslav.friendsandfoes.client.render.entity.feature;

import com.faboslav.friendsandfoes.entity.passive.MoobloomEntity;
import com.faboslav.friendsandfoes.registry.BlockRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class MoobloomFlowerFeatureRenderer<T extends MoobloomEntity> extends FeatureRenderer<T, CowEntityModel<T>>
{
    public MoobloomFlowerFeatureRenderer(
            FeatureRendererContext<T, CowEntityModel<T>> featureRendererContext
    ) {
        super(featureRendererContext);
    }

    public void render(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int light,
            T moobloomEntity,
            float f,
            float g,
            float h,
            float j,
            float k,
            float l
    ) {
        if (!moobloomEntity.isBaby() && !moobloomEntity.isInvisible()) {
            BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
            BlockState blockState = BlockRegistry.BUTTERCUP.getDefaultState();
            int overlay = LivingEntityRenderer.getOverlay(moobloomEntity, 0.0F);

            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            boolean renderAsModel = minecraftClient.hasOutline(moobloomEntity) && moobloomEntity.isInvisible();
            BakedModel bakedModel = blockRenderManager.getModel(blockState);

            // Head
            matrixStack.push();
            this.getContextModel().getHead().rotate(matrixStack);
            matrixStack.translate(0.09D, -0.6D, -0.185D);
            matrixStack.scale(-0.8F, -0.8F, 0.8F);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockRenderManager, blockState, overlay, bakedModel);
            matrixStack.pop();

            // Body
            matrixStack.push();
            matrixStack.translate(0.22D, -0.28D, -0.06D);
            matrixStack.scale(-0.8F, -0.8F, 0.8F);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockRenderManager, blockState, overlay, bakedModel);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(-0.2D, -0.22D, 0.01D);
            matrixStack.scale(-0.8F, -0.8F, 0.8F);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockRenderManager, blockState, overlay, bakedModel);
            matrixStack.pop();

            matrixStack.push();
            matrixStack.translate(0.03D, -0.28D, 0.47D);
            matrixStack.scale(-0.8F, -0.8F, 0.8F);
            matrixStack.translate(-0.5D, -0.5D, -0.5D);
            this.renderFlower(matrixStack, vertexConsumerProvider, light, renderAsModel, blockRenderManager, blockState, overlay, bakedModel);
            matrixStack.pop();
        }
    }

    private void renderFlower(
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            boolean renderAsModel,
            BlockRenderManager blockRenderManager,
            BlockState moobloomState,
            int overlay,
            BakedModel moobloomModel
    ) {
        if (renderAsModel) {
            blockRenderManager.getModelRenderer().render(matrices.peek(), vertexConsumers.getBuffer(RenderLayer.getOutline(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), moobloomState, moobloomModel, 0.0F, 0.0F, 0.0F, light, overlay);
        } else {
            blockRenderManager.renderBlockAsEntity(moobloomState, matrices, vertexConsumers, light, overlay);
        }

    }
}
