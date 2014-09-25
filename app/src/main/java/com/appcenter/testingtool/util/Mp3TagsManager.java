
package com.appcenter.testingtool.util;



import com.mpatric.mp3agic.ID3Wrapper;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * Created by diskzhou on 13-9-11.
 */

public class Mp3TagsManager {





    public static boolean trySetAlbumImage(ID3Wrapper id3Wrapper ,String picPath, String mimeType) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(picPath, "rw");
            byte[] bytes = new byte[(int) file.length()];
            if (file.read(bytes) != file.length()) {
                return false;
            }
            id3Wrapper.setAlbumImage(bytes, mimeType);
            return true;
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }



}

