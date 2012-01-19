/*
 * Copyright (C) 2012 Thedeath<www.fseek.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package imageuploader.hoster;

import imageuploader.hoster.imagr.ImagrUploader;
import imageuploader.hoster.imgur.ImgurUploader;

public class HosterFactory
{
    public static final int IMGUR_UPLOADER = 1;
    public static final int IMAGR_UPLOADER = 2;
    
    public static String[] HOSTERS = new String[]{"imgur.com", "imagr.eu"};
    
    public static ImageUploader createUploader(int uploaderID)
    {
        ImageUploader uploader = null;
        switch(uploaderID)
        {
            case 1:
                uploader = new ImgurUploader();
                break;
            case 2:
                uploader = new ImagrUploader();
                break;
            default:
                throw new IllegalArgumentException("Hoster id not defined !");
        }
        return uploader;
    }
}
