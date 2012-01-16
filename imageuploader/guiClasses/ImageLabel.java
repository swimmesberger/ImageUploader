/*
 * Copyright (C) 2011 Simon Wimmesberger<www.fseek.org>
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
package imageuploader.guiClasses;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;

public class ImageLabel extends JLabel
{

    public ImageLabel(MainFrame frame)
    {
        super();
        this.setTransferHandler(new ImageTransferhandler(frame));
        setMappings();
        this.setOpaque(false);
    }

    public ImageLabel(MainFrame frame, Icon image, int horizontalAlignment)
    {
        super(image, horizontalAlignment);
        this.setTransferHandler(new ImageTransferhandler(frame));
        setMappings();
    }

    private void setMappings()
    {
        this.registerKeyboardAction(ImageTransferhandler.getPasteAction(), "Paste",
        KeyStroke.getKeyStroke(KeyEvent.VK_V,
        ActionEvent.CTRL_MASK, false),
        JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap map = this.getActionMap();
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME), ImageTransferhandler.getPasteAction());
        InputMap imap = this.getInputMap();
        imap.put(KeyStroke.getKeyStroke("ctrl V"), TransferHandler.getPasteAction().getValue(Action.NAME));

    }
}
