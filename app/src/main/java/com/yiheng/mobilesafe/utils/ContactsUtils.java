package com.yiheng.mobilesafe.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.yiheng.mobilesafe.bean.ContactsInfo;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Yiheng on 2016/11/3 0003.
 * 获取联系人信息
 */

public class ContactsUtils {
    public static ArrayList<ContactsInfo> getContactsInfos(Context context){

        ArrayList<ContactsInfo> contactsInfos = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Uri contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection =new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };

        Cursor cursor = resolver.query(contentUri,projection,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String name = cursor.getString(0);
                String num = cursor.getString(1);
                int id = cursor.getInt(2);
                ContactsInfo contactInfo = new ContactsInfo(name,num,id);
                contactsInfos.add(contactInfo);
            }
            cursor.close();
        }

        return contactsInfos;
    }

    public static Bitmap getContactIcon(Context context,int id){
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, id + "");
        InputStream inputStream = ContactsContract.Contacts
                .openContactPhotoInputStream(resolver, uri);
        Bitmap contactIcon = BitmapFactory.decodeStream(inputStream);
        return contactIcon;
    }
}
