package me.jvav.recharsex.mixins.xaero_minimap;

import static me.jvav.recharsex.Constants.XAERO_MINIMAP;

import com.oott123.rechars.helpers.MatchHelper;
import xaero.common.gui.GuiSettings;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(XAERO_MINIMAP))
@Mixin(value = GuiSettings.class)
public class MixinGuiSettings {
    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Ljava/lang/String;indexOf(Ljava/lang/String;)I"))
    private int proxyStringIndexOf(String instance, String str) {
        return MatchHelper.contains(instance, str) ? 0 : instance.indexOf(str);
    }
}
