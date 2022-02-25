package me.jvav.recharsex.mixins.lazylanguageloader;

import static me.jvav.recharsex.Constants.LAZY_LANGUAGE_LOADER;

import club.chachy.lazylanguageloader.client.impl.language.RegionLanguageMatcher;
import com.oott123.rechars.helpers.MatchHelper;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Restriction(require = @Condition(LAZY_LANGUAGE_LOADER))
@Mixin(value = RegionLanguageMatcher.class, remap = false)
public class MixinRegionLanguageMatcher {
    @Redirect(method = "matches", at = @At(value = "INVOKE", target = "Ljava/lang/String;contains(Ljava/lang/CharSequence;)Z"))
    private boolean proxyStringContains(String instance, CharSequence s) {
        return MatchHelper.contains(instance, s);
    }
}
