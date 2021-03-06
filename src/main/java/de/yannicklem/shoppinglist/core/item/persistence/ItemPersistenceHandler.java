package de.yannicklem.shoppinglist.core.item.persistence;

import de.yannicklem.restutils.entity.service.EntityPersistenceHandler;

import de.yannicklem.shoppinglist.core.exception.AlreadyExistsException;
import de.yannicklem.shoppinglist.core.exception.EntityInvalidException;
import de.yannicklem.shoppinglist.core.exception.NotFoundException;
import de.yannicklem.shoppinglist.core.item.entity.Item;
import de.yannicklem.shoppinglist.core.item.validation.ItemValidationService;
import de.yannicklem.shoppinglist.core.list.entity.ShoppingList;
import de.yannicklem.shoppinglist.core.list.persistence.ShoppingListService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * @author  Yannic Klem - yann.klem@gmail.com
 */
@Service
public class ItemPersistenceHandler implements EntityPersistenceHandler<Item> {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    private final ItemValidationService itemValidationService;
    private final ItemReadOnlyService itemReadOnlyService;
    private final ShoppingListService shoppingListService;

    @Autowired
    public ItemPersistenceHandler(ItemValidationService itemValidationService, ShoppingListService shoppingListService,
        @Qualifier("readOnlyItemService") ItemReadOnlyService itemReadOnlyService) {

        this.itemValidationService = itemValidationService;
        this.itemReadOnlyService = itemReadOnlyService;
        this.shoppingListService = shoppingListService;
    }

    @Override
    public void handleBeforeCreate(Item item) {

        if (item != null && item.getArticle() != null) {
            item.getArticle().getOwners().addAll(item.getOwners());
        }

        if (item != null && itemReadOnlyService.exists(item.getEntityId())) {
            throw new AlreadyExistsException("Item already exists");
        }

        itemValidationService.validate(item);
    }


    @Override
    public void handleAfterCreate(Item createdItem) {

        String articleName = createdItem.getArticle() == null ? null : createdItem.getArticle().getName();
        LOGGER.info(String.format("Created item: %s (%s)", createdItem.getEntityId(), articleName));
    }


    @Override
    public void handleBeforeUpdate(Item item) {

        if (item != null && item.getArticle() != null) {
            item.getArticle().getOwners().addAll(item.getOwners());
        }

        if (item == null || !itemReadOnlyService.exists(item.getEntityId())) {
            throw new NotFoundException("Item not found");
        }

        itemValidationService.validate(item);
    }


    @Override
    public void handleAfterUpdate(Item updatedItem) {

        String articleName = updatedItem.getArticle() == null ? null : updatedItem.getArticle().getName();
        LOGGER.info(String.format("Updated item: %s (%s)", updatedItem.getEntityId(), articleName));
    }


    @Override
    public void handleBeforeDelete(Item item) {

        if (item == null || !itemReadOnlyService.exists(item.getEntityId())) {
            throw new NotFoundException("Item not found");
        }

        deleteItemOutOfContainingLists(item);
    }


    private void deleteItemOutOfContainingLists(Item item) {

        try {
            List<ShoppingList> shoppingListsContainingItem = shoppingListService.findShoppingListsContainingItem(item);

            for (ShoppingList shoppingList : shoppingListsContainingItem) {
                shoppingList.getItems().remove(item);
                shoppingListService.update(shoppingList);
            }
        } catch (EntityInvalidException entityInvalidException) {
            LOGGER.warn(
                "An error occurred while deleting item ('{}') out of shopping lists containing the item that should be "
                + "deleted", item.getArticle().getName());
            LOGGER.debug("The following exception occurred while handling before delete of item '{}'",
                entityInvalidException);
            throw entityInvalidException;
        }
    }


    @Override
    public void handleAfterDelete(Item entity) {

        String articleName = entity.getArticle() == null ? null : entity.getArticle().getName();
        LOGGER.info(String.format("Deleted item: %s (%s)", entity.getEntityId(), articleName));
    }
}
