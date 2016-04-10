package com.tylerrockwood.atg.api;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.repackaged.com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadService extends HttpServlet {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BlobKey> blobs = blobstoreService.getUploads(req).get("file");
        BlobKey blobKey = blobs.get(0);

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

        String servingUrl = imagesService.getServingUrl(servingOptions);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");

        JsonObject json = new JsonObject();
        json.addProperty("servingUrl", servingUrl);
        json.addProperty("blobKey", blobKey.getKeyString());
        PrintWriter out = resp.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();
    }
}
