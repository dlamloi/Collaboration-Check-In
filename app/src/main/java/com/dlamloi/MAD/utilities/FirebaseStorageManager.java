package com.dlamloi.MAD.utilities;

import android.net.Uri;
import android.util.Log;

import com.dlamloi.MAD.home.GroupHomeContract;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

/**
 * Created by Don on 27/05/2018.
 */

public class FirebaseStorageManager {

    public static final String FILE_EXTENSION = "CloudFile Extension";
    public static final String DOWNLOAD = "Download Status";
    private GroupHomeContract.FirebaseStorageEventListener mStorageEventListener;


    public FirebaseStorageManager(GroupHomeContract.FirebaseStorageEventListener storageListener) {
        mStorageEventListener = storageListener;
    }

    public void uploadFile(String groupId, Uri file, String fileName) {
        StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference()
                .child("files/" + groupId + "/" + fileName);
        firebaseStorage.putFile(file).addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            int currentProgress = (int) progress;
            mStorageEventListener.notifyProgressChange(currentProgress);

        }).addOnSuccessListener(task -> {
            mStorageEventListener.onUploadComplete(fileName, task.getDownloadUrl().toString());
        });
    }

    public void downloadFile(String name, String uri, String externalFilesDir) {
        StorageReference urlReference = FirebaseStorage.getInstance().getReferenceFromUrl(uri);
        String fileExtension = urlReference.getName().substring(urlReference.getName().lastIndexOf('.'));
        Log.d(FILE_EXTENSION, fileExtension);
        File file = new File(externalFilesDir + "/" + name);
        urlReference.getFile(file).addOnProgressListener(taskSnapshot -> {

        }).addOnSuccessListener(task -> {
            Log.d(DOWNLOAD, "CloudFile downloaded");
        });

    }

}
