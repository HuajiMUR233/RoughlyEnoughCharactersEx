package me.jvav.recharsex.mixins.chesttracker;

import static me.jvav.recharsex.Constants.CHEST_TRACKER;

import com.oott123.rechars.helpers.MatchHelper;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import red.jackf.chesttracker.gui.widgets.WItemListPanel;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.BiConsumer;

@Restriction(require = @Condition(CHEST_TRACKER))
@Mixin(value = WItemListPanel.class, remap = false)
public class MixinWItemListPanel {
    @Shadow
    private List<ItemStack> filteredItems;
    @Shadow
    private List<ItemStack> items;
    @Shadow
    private String filter;
    @Shadow
    private int pageCount;
    @Final
    @Shadow
    private int columns;
    @Final
    @Shadow
    private int rows;
    @Shadow
    private int currentPage;
    @Shadow
    @Nullable
    private BiConsumer<Integer, Integer> pageChangeHook;


    @Inject(method = "updateFilter", at = @At("HEAD"), cancellable = true)
    private void onUpdateFilter(CallbackInfo ci) {
        this.filteredItems = items.stream().filter(stack -> MatchHelper.contains(stack.getHoverName().getString().toLowerCase(), this.filter)
                || (stack.hasCustomHoverName() && MatchHelper.contains(stack.getItem().getName(stack).getString().toLowerCase(), this.filter))
                || stack.getTag() != null && MatchHelper.contains(stack.getTag().toString().toLowerCase(), this.filter)).collect(Collectors.toList());
        this.pageCount = ((filteredItems.size() - 1) / (columns * rows)) + 1;
        this.currentPage = Math.min(currentPage, pageCount);
        if (this.pageChangeHook != null) this.pageChangeHook.accept(this.currentPage, this.pageCount);
        ci.cancel();
    }
}
