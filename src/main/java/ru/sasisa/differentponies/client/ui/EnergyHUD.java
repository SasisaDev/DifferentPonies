package ru.sasisa.differentponies.client.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import ru.sasisa.differentponies.api.interfaces.IHasEnergy;

@Environment(EnvType.CLIENT)
public class EnergyHUD implements HudRenderCallback {
    private static final Identifier FILLED_ENERGY = new Identifier("differentponies", "textures/ui/energy/filled_energy.png");
    private static final Identifier EMPTY_ENERGY = new Identifier("differentponies", "textures/ui/energy/empty_energy.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;

        MinecraftClient client = MinecraftClient.getInstance();
        IHasEnergy player = null;
        if(client != null)
        {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;

            player = (IHasEnergy)(Object)(client.player);
        }

        int objects = 0;
        boolean lastIsMini = false;

        if(player != null)
        {
            if(player.GetMaxEnergy() > 0 && player.GetEnergy() != player.GetMaxEnergy()) {
                int energyPerOne = player.GetMaxEnergy() / 10;
                objects = (int)Math.ceil((double)player.GetEnergy() / (double) energyPerOne);

                int lastBulb = player.GetEnergy() - (energyPerOne * (objects - 1));

                lastIsMini = (lastBulb) <= (energyPerOne / 2);
            }

            if(client.player.getAir() != client.player.getMaxAir())
            {
                y -= 10;
            }
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, FILLED_ENERGY);
        for(int i = 0; i < objects; i++)
        {
            if(i == objects - 1 && lastIsMini)
            {
                RenderSystem.setShaderTexture(0, EMPTY_ENERGY);
            }
            DrawableHelper.drawTexture(matrixStack, x + 10 + (9*8) - (i*8), y - 49, 0, 0, 9, 9, 9,9);
        }
    }
}
