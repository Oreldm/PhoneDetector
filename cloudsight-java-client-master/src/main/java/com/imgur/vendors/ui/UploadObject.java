package com.imgur.vendors.ui;

import java.io.File;
import java.io.IOException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.EmailAddressGrantee;
import com.amazonaws.services.s3.model.Grantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Permission;

public class UploadObject {
	
//   String clientRegion = Regions.EU_WEST_1.toString(); //Ireland
	 private String bucketName = "nbaprojectoracle";
	 private String stringObjKeyName = "test1";
	 private String fileObjKeyName = "phoneImage.jpg";
//     private String fileName = "C:\\Users\\h1z1\\Desktop\\images1.jpg";

     UploadObject(String fileName) {
        try {
        	AWSCredentials credentials = new BasicAWSCredentials(
        			  "AKIAJTGBEGGLVKGWEMWA", 
        			  "TGMXgFnsmJakLZXBUj7MCmQq4RtEu7YIiTJScGB2"
        			);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            		.withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(Regions.EU_WEST_1)
                    .build();
            // Upload a text string as a new object.
            s3Client.putObject(bucketName, stringObjKeyName, "Uploaded String Object");
            
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new File(fileName));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
            
            try {
                // get the current ACL
                AccessControlList acl = s3Client.getObjectAcl(bucketName, "Bucks.gif");
                // set access for the grantee
//                EmailAddressGrantee grantee = new EmailAddressGrantee(email);
//                Permission permission = Permission.Read;
//                acl.grantPermission(grantee, permission);
                s3Client.setObjectAcl(bucketName, fileObjKeyName, acl);
            } catch (AmazonServiceException e) {
                System.err.println(e.getErrorMessage());
                System.exit(1);
            }
        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}