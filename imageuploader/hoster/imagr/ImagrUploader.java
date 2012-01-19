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

// experimental NOT WORKING AT THE MOMENT !!!
package imageuploader.hoster.imagr;

import imageuploader.hoster.HosterImage;
import imageuploader.hoster.ImageUploader;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImagrUploader implements ImageUploader
{

    private static final String URL = "http://imagr.eu/";
    private int progress = 0;
    private int insProgress = 100;
    
    public ImagrImage uploadFile(BufferedImage imag, String ext)
    {
        String fileName = generateRandomString() + "." + ext;
        HttpURLConnection conn = null;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String boundary = "-----------------------------"+UniqueCreator.getUniqueId();
        String twoHyphens = "--";
        int serverResponseCode = 0;
        try 
        { // open a URL connection to the Servlet
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imag, ext, baos);
            
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP
            conn.setInstanceFollowRedirects(true);
                                     // connection to
                                     // the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            
            //write header
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
            progress += 25;
            
            dos = new DataOutputStream(conn.getOutputStream());
            //write first boundary
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            
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
            dos.writeBytes(lineEnd + twoHyphens + boundary + twoHyphens + lineEnd);
            progress += 25;
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();
            System.out.println("Upload file to serverHTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);
            // close streams
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) 
        {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // this block will give the response of upload link
        try 
        {
            progress += 25;
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            boolean directURL = false;
            String directURLString = "";
            String line;
            while ((line = rd.readLine()) != null) 
            {
                if(directURL)
                {
                    String searchString = "<input onclick=\"this.select();\" name=\"urlDelete\" value=\"";
                    String secSearchString = "\" />";
                    int indexOf = line.indexOf(searchString);
                    int secIndexOf = line.indexOf(secSearchString);
                    if(indexOf != -1)
                    {
                        directURLString = line.substring(indexOf+searchString.length(),secIndexOf);
                        break;
                    }
                }
                if(line.contains("<div>URL</div>"))
                {
                    directURL = true;
                }
            }
            rd.close();
            progress += 25;
            System.out.println("directURLString = " + directURLString);
            return new ImagrImage(directURLString);
        } catch (IOException ioex) 
        {
            ioex.printStackTrace();
        }
        return null;

        }

    public static byte[] hexStringToByteArray(String s) 
    {
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) 
            {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
            }
            return data;
    }

    private String generateRandomString()
    {
        Random r = new Random();
        String token = Long.toString(Math.abs(r.nextLong()), 36);
        return token;
    }

    @Override
    public HosterImage uploadFile(File f)
    {
        try
        {
            String name = f.getName();
            String[] split = name.split("\\.");
            String ext = "png";
            if (split.length > 0)
            {
                ext = split[split.length - 1];
            }
            BufferedImage imag = ImageIO.read(f);
            return uploadFile(imag, ext);
        } catch (IOException ex)
        {
            Logger.getLogger(ImagrUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int getProgress()
    {
        return progress;
    }

    @Override
    public HosterImage uploadFile(BufferedImage imag)
    {
        return uploadFile(imag, "png");
    }

    /**
     * Helper to create unique id.
     */
    private static final class UniqueCreator
    {


        public static final long getUniqueId()
        {
            Random rand = new Random();
            return rand.nextLong();
        }
    }

}
