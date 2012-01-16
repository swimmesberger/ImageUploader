/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imageuploader.hoster;

import java.awt.image.BufferedImage;
import java.io.File;




/**
 *
 * @author Simon
 */
public interface ImageUploader
{
    public HosterImage uploadFile(BufferedImage imag);
    public HosterImage uploadFile(File f);
    public int getProgress();
}
