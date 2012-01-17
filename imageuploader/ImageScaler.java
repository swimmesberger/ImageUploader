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
package imageuploader;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;


public class ImageScaler
{

    public BufferedImage scaleImage(BufferedImage img, Dimension d)
    {
        img = scaleByHalf(img, d);
        img = scaleExact(img, d);
        return img;
    }

    private BufferedImage scaleByHalf(BufferedImage img, Dimension d)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        float factor = getBinFactor(w, h, d);

        // make new size
        w *= factor;
        h *= factor;
        BufferedImage scaled = new BufferedImage(w, h,
        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }

    private BufferedImage scaleExact(BufferedImage img, Dimension d)
    {
        float factor = getFactor(img.getWidth(), img.getHeight(), d);

        // create the image
        int w = (int) (img.getWidth() * factor);
        int h = (int) (img.getHeight() * factor);
        BufferedImage scaled = new BufferedImage(w, h,
        BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }

    float getBinFactor(int width, int height, Dimension dim)
    {
        float factor = 1;
        float target = getFactor(width, height, dim);
        if (target <= 1)
        {
            while (factor / 2 > target)
            {
                factor /= 2;
            }
        }
        else
        {
            while (factor * 2 < target)
            {
                factor *= 2;
            }
        }
        return factor;
    }

    float getFactor(int width, int height, Dimension dim)
    {
        float sx = dim.width / (float) width;
        float sy = dim.height / (float) height;
        return Math.min(sx, sy);
    }
}