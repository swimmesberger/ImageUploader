/*
 * Copyright (C) 2011 Thedeath<www.fseek.org>
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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;

public class ImageField extends JTextArea
{
    public ImageField(MainFrame frame)
    {
        super();
        this.setTransferHandler(new ImageTransferhandler(frame));
        setMappings();
        //stop text insert
        this.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                setText("");
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                setText("");
            }
        });
    }
    
    private void setMappings()
    {
        ActionMap map = this.getActionMap();
//        map.put(TransferHandler.getCutAction().getValue(Action.NAME), ImageTransferhandler.getCutAction());
//        map.put(TransferHandler.getCopyAction().getValue(Action.NAME), ImageTransferhandler.getCopyAction());
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME), ImageTransferhandler.getPasteAction());
        InputMap imap = this.getInputMap();
//        imap.put(KeyStroke.getKeyStroke("ctrl X"), TransferHandler.getCutAction().getValue(Action.NAME));
//        imap.put(KeyStroke.getKeyStroke("ctrl C"), TransferHandler.getCopyAction().getValue(Action.NAME));
        imap.put(KeyStroke.getKeyStroke("ctrl V"), TransferHandler.getPasteAction().getValue(Action.NAME));
    }

    
    
    
}
