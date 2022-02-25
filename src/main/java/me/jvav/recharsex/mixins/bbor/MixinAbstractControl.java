package me.jvav.recharsex.mixins.bbor;

import com.oott123.rechars.helpers.MatchHelper;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Restriction(require = @Condition("bbor"))
@Mixin(targets = "com.irtimaled.bbor.client.gui.AbstractControl", remap = false)
public abstract class MixinAbstractControl extends AbstractWidget {
    public MixinAbstractControl(int i, int j, int k, int l, Component component) {
        super(i, j, k, l, component);
    }

    @Inject(method = "filter", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void afterFilter(String lowerValue, CallbackInfo ci, String lowerString) {
        this.visible = lowerValue.equals("") || MatchHelper.contains(lowerString, lowerValue);
    }
}
