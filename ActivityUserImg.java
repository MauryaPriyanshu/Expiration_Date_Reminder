package com.foodwastemanagement;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.foodwastemanagement.Utility.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class ActivityUserImg extends AppCompatActivity {

    private Boolean isChanged = false;
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;
    private Uri mainImageUri = null;
    PrefManager pref;
    FirebaseFirestore myDB;
    Intent i;
    //String Typ,strTitle,strStatus,strImgColumnName,strImgName,prodID;
    CircleImageView imgAttachment;
    Button btnSubmit;
    private Bitmap compressedUserImage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_product_img);
        pref = new PrefManager(ActivityUserImg.this);
        myDB = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        i = getIntent();
      //  prodID= i.getStringExtra("ProductID");
      //  strImgColumnName= i.getStringExtra("imgColumnName");
      //  strImgName= i.getStringExtra("imgName");
        imgAttachment=(CircleImageView) findViewById(R.id.img_attachment);
        btnSubmit=findViewById(R.id.btn_submit);
        bringImagePicker();
        imgAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(ActivityUserImg.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(ActivityUserImg.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED) {
                       // Snackbar.make(findViewById(R.id.setup_layout), "Please grant permissions", Snackbar.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(ActivityUserImg.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READCODE);
                        ActivityCompat.requestPermissions(ActivityUserImg.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITECODE);

                    } else {

                        bringImagePicker();
                    }

                } else {
                    bringImagePicker();
                }

            }
        });

/*========================================*/
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //========================Start AddProof=====================
                    if (isChanged) {
                        File newImageFile = new File(mainImageUri.getPath());
                        try {
                            compressedUserImage = new Compressor(ActivityUserImg.this)
                                    .setQuality(60)
                                    .compressToBitmap(newImageFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedUserImage.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                        byte[] imageData = baos.toByteArray();

                        final StorageReference image_path = storageReference.child("UserImage").child(pref.getUserId() + ".jpg");
                        image_path.putBytes(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    upLoadAttachment(task,  image_path);
                                } else {
                                   // Snackbar.make(findViewById(R.id.setup_layout), task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                    //========================End IdProof =====================
            }
        });

    }

    private void upLoadAttachment(Task<UploadTask.TaskSnapshot> task,
                                   final StorageReference image_path){

        if (image_path!=null){
            image_path.getDownloadUrl().addOnSuccessListener(ActivityUserImg.this, new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    myDB.collection("UserReg").document(pref.getUserId()).update("uImage",uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent i=new Intent(ActivityUserImg.this, ActivityUserProfile.class);
                                startActivity(i);
                                Log.d("#333","product image  upload successfully");
                            } else {
                                Log.d("#333","product image not upload");

                            }

                        }
                    });

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageUri = result.getUri();
                imgAttachment.setImageURI(mainImageUri);
                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(ActivityUserImg.this,error.toString(), Toast.LENGTH_LONG).show();
               // Toast.makeText()
                //    Snackbar.make(findViewById(R.id.setup_layout),error.toString(),Snackbar.LENGTH_SHORT).show();

            }
        }
    }

    private void bringImagePicker(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(ActivityUserImg.this);
    }
}
