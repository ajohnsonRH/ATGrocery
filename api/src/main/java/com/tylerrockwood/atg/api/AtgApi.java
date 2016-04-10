/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.tylerrockwood.atg.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.LoadType;
import com.tylerrockwood.atg.api.models.GroceryItem;
import com.tylerrockwood.atg.api.models.PendingUpload;

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

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    static {
        ObjectifyService.register(GroceryItem.class);
    }

    @ApiMethod(name = "getAllGroceries", httpMethod = "GET")
    public List<GroceryItem> getAllGroceries() {
        List<GroceryItem> response = new ArrayList<>();
        LoadType<GroceryItem> groceryItems = ofy().load().type(GroceryItem.class);
        for (GroceryItem g : groceryItems) {
            response.add(g);
        }
        return response;
    }

    @ApiMethod(name = "insertGrocery", httpMethod = "POST")
    public GroceryItem insertGrocery(GroceryItem item) {
        Key<GroceryItem> itemKey = ofy().save().entity(item).now();
        item.setId(itemKey.getId());
        return item;
    }

    @ApiMethod(name = "removeGrocery", httpMethod = "DELETE")
    public GroceryItem removeGrocery(GroceryItem item) {
        ofy().delete().entity(item).now();
        return item;
    }

    @ApiMethod(name = "addImage", httpMethod = "GET")
    public PendingUpload addImage() {
        PendingUpload upload = new PendingUpload();
        String uploadUrl = blobstoreService.createUploadUrl("/image/upload");
        upload.setUrl(uploadUrl);
        return upload;
    }


}
