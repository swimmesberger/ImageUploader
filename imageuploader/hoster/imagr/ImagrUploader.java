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
package imageuploader.hoster.imagr;

import imageuploader.hoster.UploaderTemplate;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class ImagrUploader extends UploaderTemplate
{
 
   private static final String URL = "http://imagr.eu/";

    public ImagrUploader(int id)
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
        return "<input onclick=\"this.select();\" name=\"urlDelete\" value=\"";
    }

    @Override
    public String getEndSearchString()
    {
        return "\" />";
    }

    @Override
    public String pastParseURL(String directUrl)
    {
        return directUrl;
    }

    @Override
    public void writeData(HttpURLConnection conn, DataOutputStream dos, ByteArrayOutputStream baos, String fileName, String boundary) throws IOException
    {
        //write file
        dos.writeBytes("Content-Disposition: form-data; name=\"source0n\"; filename=\"" + fileName + "\"" + lineEnd);
        dos.writeBytes("Content-Type: image/png" + lineEnd);
        dos.writeBytes(lineEnd);
        byte[] toByteArray = baos.toByteArray();
        dos.write(toByteArray);

        // send multipart form data necesssary after file data...
        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + lineEnd);

        dos.writeBytes("Content-Disposition: form-data; name=\"source0n\"" + lineEnd);
        dos.writeBytes(lineEnd);
        dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);

        dos.writeBytes("Content-Disposition: form-data; name=\"type\"" + lineEnd);
        dos.writeBytes(lineEnd);
        dos.writeBytes("up load");
        dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);

        dos.writeBytes("Content-Disposition: form-data; name=\"submit\"" + lineEnd);
        dos.writeBytes(lineEnd);
        dos.writeBytes("Hochladen");
    }

    @Override
    public void writeHeader(HttpURLConnection conn, String boundary)
    {
        //write header
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
    }

    @Override
    public String getSearchStringContains()
    {
        return "";
    }
}
