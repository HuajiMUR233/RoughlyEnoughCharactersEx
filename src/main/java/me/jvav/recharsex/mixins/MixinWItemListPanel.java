package me.jvav.recharsex.mixins;

import com.oott123.rechars.helpers.MatchHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import red.jackf.chesttracker.gui.widgets.WItemListPanel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.function.BiConsumer;

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
        this.filteredItems = items.stream().filter(stack -> MatchHelper.contains(stack.getName().getString().toLowerCase(), this.filter)
                || (stack.hasCustomName() && MatchHelper.contains(stack.getItem().getName(stack).getString().toLowerCase(), this.filter))
                || stack.getNbt() != null && MatchHelper.contains(stack.getNbt().toString().toLowerCase(), this.filter)).collect(Collectors.toList());
        this.pageCount = ((filteredItems.size() - 1) / (columns * rows)) + 1;
        this.currentPage = Math.min(currentPage, pageCount);
        if (this.pageChangeHook != null) this.pageChangeHook.accept(this.currentPage, this.pageCount);
        ci.cancel();
    }
}
