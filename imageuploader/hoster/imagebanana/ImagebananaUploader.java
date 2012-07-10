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

package imageuploader.hoster.imagebanana;

import imageuploader.hoster.UploaderTemplate;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;


public class ImagebananaUploader extends UploaderTemplate
{
    private static final String URL = "http://www.imagebanana.com/";

    public ImagebananaUploader(int id)
    {
        super(id);
    }

    
    
    @Override
    public String getURL()
    {
        return URL;
    }

    @Override
    public String getFirstSearchString()
    {
        return "[IMG]";
    }

    @Override
    public String getEndSearchString()
    {
        return "[/IMG]";
    }

    @Override
    public String pastParseURL(String directUrl)
    {
        directUrl = directUrl.replace("/thumb/", "/");
        return directUrl;
    }

    @Override
    public void writeData(HttpURLConnection conn, DataOutputStream dos, ByteArrayOutputStream baos, String fileName, String boundary) throws IOException
    {
        //write file
        dos.writeBytes("Content-Disposition: form-data; name=\"upload[]\"; filename=\"" + fileName + "\"" + lineEnd);
        dos.writeBytes("Content-Type: image/png" + lineEnd);
        dos.writeBytes(lineEnd);
        byte[] toByteArray = baos.toByteArray();
        dos.write(toByteArray);

        // send multipart form data necesssary after file data...
        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + lineEnd);

        dos.writeBytes("Content-Disposition: form-data; name=\"options[resize]\"" + lineEnd);
        dos.writeBytes(lineEnd);
        dos.write(hexStringToByteArray("2d30"));
        dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);

        dos.writeBytes("Content-Disposition: form-data; name=\"options[resize_width]\"" + lineEnd);
        dos.writeBytes(lineEnd);
        dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);

        dos.writeBytes("Content-Disposition: form-data; name=\"options[resize_height]\"" + lineEnd);
        dos.writeBytes(lineEnd);
    }

    @Override
    public void writeHeader(HttpURLConnection conn, String boundary)
    {
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
    }

    @Override
    public String getSearchStringContains()
    {
        return "";
    }
}
