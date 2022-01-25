package me.jvav.recharsex.mixins;

import com.oott123.rechars.helpers.MatchHelper;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigOption;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptions;
import fi.dy.masa.malilib.gui.widgets.WidgetListConfigOptionsBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = WidgetListConfigOptions.class, remap = false)
public abstract class MixinWidgetListConfigOptions extends WidgetListConfigOptionsBase<GuiConfigsBase.ConfigOptionWrapper, WidgetConfigOption> {

    public MixinWidgetListConfigOptions(int x, int y, int width, int height, int configWidth) {
        super(x, y, width, height, configWidth);
    }

    @Override
    protected boolean matchesFilter(String entryString, String filterText) {
        filterText = filterText.toLowerCase();
        if (filterText.isEmpty()) {
            return true;
        }

        for (String filter : filterText.split("\\|")) {
            if (MatchHelper.contains(entryString, filter)) {
                return true;
            }
        }

        return false;
    }
}
