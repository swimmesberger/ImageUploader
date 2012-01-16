/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageuploader.hoster;

import imageuploader.hoster.imgur.ImgurUploader;

/**
 *
 * @author Simon
 */
public class HosterFactory
{
    public static int IMGUR_UPLOADER = 1;
    
    public static ImageUploader createUploader(int uploaderID)
    {
        ImageUploader uploader = null;
        switch(uploaderID)
        {
            case 1:
                uploader = new ImgurUploader();
                break;
            default:
                throw new IllegalArgumentException("Hoster id not defined !");
        }
        return uploader;
    }
}
