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

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Simon
 */
public class HistoryTableModel extends DefaultTableModel
{
    private final static DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
    
    public String[] columns;
    private ArrayList<HistoryEntry> entrys;
    private File outputFile;
    public HistoryTableModel(File saveHistoryFile)
    {
        this.outputFile = saveHistoryFile;
        columns = new String[]{"URL", "Hoster", "Time"};
        entrys = new ArrayList<HistoryEntry>();
        if(saveHistoryFile.exists())
            readHistoryFile();
    }

    @Override
    public String getColumnName(int column)
    {
        return columns[column];
    }

    @Override
    public int getColumnCount()
    {
        return columns.length;
    }

    @Override
    public int getRowCount()
    {
        if(entrys == null)return 0;
        return entrys.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return String.class;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        HistoryEntry get = entrys.get(row);
        switch(column)
        {
            case 0:
                return get.getUrl();
            case 1:
                return get.getHoster();
            case 2:
                return formatter.format(get.getUploadDate());
        }
        return null;
    }

    @Override
    public void removeRow(int row)
    {
        this.entrys.remove(row);
        fireTableRowsDeleted(row-1, row);
        dataChanged();
    }

    @Override
    public void setValueAt(Object aValue, int row, int column)
    {
        HistoryEntry get = this.entrys.get(row);
        switch(column)
        {
            case 0:
                get.setUrl(aValue.toString());
                break;
            case 1:
                get.setHoster(aValue.toString());
                break;
            case 2:
                if(aValue instanceof Date)
                {
                    get.setUploadDate((Date)aValue);
                }
                else
                {
                    try
                    {
                        get.setUploadDate(formatter.parse(aValue.toString()));
                    } catch (ParseException ex)
                    {
                        ex.printStackTrace();
                    }
                }
                break;
        }
    }

    public HistoryEntry getRow(int row)
    {
        return this.entrys.get(row);
    }
    
    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }
    
    public void addHistory(HistoryEntry entry)
    {
        this.entrys.add(entry);
        fireTableRowsInserted(this.entrys.size()-1, this.entrys.size());
        dataChanged();
    }
    
    public void deleteHistory(HistoryEntry entry)
    {
        this.entrys.remove(entry);
        fireTableRowsDeleted(this.entrys.size()-1, this.entrys.size());
        dataChanged();
    }
    
    private void dataChanged()
    {
       writeHistoryFile();
    }
    
    private void readHistoryFile()
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(outputFile));
            while(true)
            {
                String line = br.readLine();
                if(line == null)break;
                String[] split = line.split(HistoryEntry.valueSeperator);
                String url = split[0];
                String hoster = split[1];
                Date uploadDate = new Date(Long.parseLong(split[2]));
                this.entrys.add(new HistoryEntry(url, hoster, uploadDate));
            }
            br.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private void writeHistoryFile()
    {
        try
        {
            PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
            for(HistoryEntry entry : entrys)
            {
                pw.println(entry.toString());
            }
            pw.flush();
            pw.close();
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
