package me.jvav.recharsex.mixins.malilib;

import static me.jvav.recharsex.Constants.MALILIB;

import com.oott123.rechars.helpers.MatchHelper;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(MALILIB))
@Mixin(value = WidgetListBase.class, remap = false)
public class MixinWidgetListConfigOptions {
    @Redirect(method = "matchesFilter(Ljava/lang/String;Ljava/lang/String;)Z", at = @At(value = "INVOKE", target = "Ljava/lang/String;contains(Ljava/lang/CharSequence;)Z"))
    private boolean proxyStringContains(String instance, CharSequence s) {
        return MatchHelper.contains(instance, s);
    }
}
