package net.skin43d.impl.client.render.engine.attach;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerElytra;

/**
 * @author ci010
 */
public class LayerElytraOverride extends LayerElytra {
    private RenderEngineAttach engineAttach;

    public LayerElytraOverride(RenderPlayer renderPlayerIn, RenderEngineAttach engineAttach) {
        super(renderPlayerIn);
        this.engineAttach = engineAttach;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!engineAttach.renderedWings())
            super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
    }
}
