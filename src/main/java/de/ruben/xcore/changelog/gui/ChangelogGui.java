package de.ruben.xcore.changelog.gui;

import de.ruben.xcore.changelog.model.Changelog;
import de.ruben.xcore.changelog.service.ChangeLogService;
import de.ruben.xcore.currency.XCurrency;
import de.ruben.xcore.currency.account.gui.OtherBanksGui;
import de.ruben.xcore.pagination.PaginatedArrayList;
import de.ruben.xdevapi.XDevApi;
import de.ruben.xdevapi.custom.gui.ItemPreset;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ChangelogGui extends Gui {

    private PaginatedArrayList paginatedArrayList;

    public ChangelogGui(Player player) {
        super(6, "§9§lChangelogs", Set.of(InteractionModifier.PREVENT_ITEM_PLACE, InteractionModifier.PREVENT_ITEM_SWAP, InteractionModifier.PREVENT_ITEM_TAKE));
        
        this.disableAllInteractions();
        this.getFiller().fillBorder(ItemPreset.fillItem(inventoryClickEvent -> {}));

        this.setItem((this.getRows()*9)-5, ItemPreset.closeItem(inventoryClickEvent -> this.close(player)));
    }

    @Override
    public void open(@NotNull HumanEntity player) {
        super.open(player);

        setPageItems((Player) player, 0);

        this.update();
    }

    public void open(Player player, int page){
        super.open(player);

        setPageItems((Player) player, page);


    }

    private void setPageItems(Player player, int page){

        XDevApi.getInstance().getxScheduler().async(() -> {
            this.paginatedArrayList = new PaginatedArrayList(new ChangeLogService().getChangeLogsNewestFirst(), 28);

            paginatedArrayList.gotoPage(page);

            for (int i = 0; i < 28; i++) {
                if (i >= paginatedArrayList.size()) {
                    break;
                }

                Changelog changelog = (Changelog) paginatedArrayList.get(i);

                this.addItem(ItemBuilder.from(changelog.toItemStack()).asGuiItem(inventoryClickEvent -> {
                    if(player.hasPermission("addictzone.changelog.delete")){
                        if(inventoryClickEvent.getClick() == ClickType.SHIFT_RIGHT){
                            player.closeInventory();
                            new ChangeLogService().deleteChangeLog(changelog.getId());
                            player.sendMessage(XDevApi.getInstance().getMessageService().getMessage("prefix")+"§7Du hast den Changelog mit der Id §b"+changelog.getId()+" §7erfolgreich gelöscht!");
                        }
                    }
                }));

            }

            if(paginatedArrayList.isNextPageAvailable()){
                this.setItem(50, ItemBuilder.from(Material.MAP).name(Component.text("§9Nächste Seite §8("+(paginatedArrayList.getPageIndex()+1)+")")).asGuiItem(inventoryClickEvent -> {
                    Player clicked = (Player) inventoryClickEvent.getWhoClicked();
                    new ChangelogGui(clicked).open(clicked, paginatedArrayList.getPageIndex()+1);
                }));
            }

            if(paginatedArrayList.isPreviousPageAvailable()){
                this.setItem(48, ItemBuilder.from(Material.MAP).name(Component.text("§9Letzte Seite §8("+(paginatedArrayList.getPageIndex()-1)+")")).asGuiItem(inventoryClickEvent -> {
                    Player clicked = (Player) inventoryClickEvent.getWhoClicked();
                    new ChangelogGui(clicked).open(clicked, paginatedArrayList.getPageIndex()-1);
                }));
            }

            this.update();
        });


    }
}
