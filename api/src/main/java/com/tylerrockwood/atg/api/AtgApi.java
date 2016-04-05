/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.tylerrockwood.atg.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.LoadType;
import com.tylerrockwood.atg.api.models.GroceryItem;

import java.util.ArrayList;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;


@Api(
        name = "atgApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "api.atg.tylerrockwood.com",
                ownerName = "api.atg.tylerrockwood.com",
                packagePath = ""
        ),
        description = "The API for AT Grocery"
)
public class AtgApi {

    static {
        ObjectifyService.register(GroceryItem.class);
    }

    @ApiMethod(name = "getAllGroceries")
    public List<GroceryItem> getAllGroceries() {
        List<GroceryItem> response = new ArrayList<>();
        LoadType<GroceryItem> groceryItems = ofy().load().type(GroceryItem.class);
        for (GroceryItem g : groceryItems) {
            response.add(g);
        }
        return response;
    }

    @ApiMethod(name = "addGrocery")
    public GroceryItem addGrocery(GroceryItem item) {
        Key<GroceryItem> itemKey = ofy().save().entity(item).now();
        item.setId(itemKey.getId());
        return item;
    }


}