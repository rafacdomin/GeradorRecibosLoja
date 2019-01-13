package geradornotafiscal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;

/**
 *
 * @author rafael domingues
 */
public class ExportExcel {
    
    private static String getDateTime() { 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	Date date = new Date(); 
	return dateFormat.format(date); 
    }
    
    public static void toExcel2(JTable jTable1, File file){
        TableModel model = jTable1.getModel();
        HSSFWorkbook wb = new HSSFWorkbook();//criar planilha
        HSSFFont Font = wb.createFont();
        Font.setFontHeightInPoints((short)8);
        HSSFSheet sheet = wb.createSheet("Planilha 1");//criar aba 1
        sheet.setColumnWidth(0, 10*155);
        sheet.setColumnWidth(1, 10*350);
        sheet.setColumnWidth(2, 10*210);
        sheet.setColumnWidth(3, 10*210);
        sheet.setMargin(Sheet.LeftMargin, 0.1);
        sheet.setMargin(Sheet.RightMargin, 0);
        sheet.setMargin(Sheet.TopMargin, 0);
        sheet.setMargin(Sheet.BottomMargin, 0);
        CellStyle style = wb.createCellStyle();
        style.setFont(Font);
        DataFormat format = wb.createDataFormat();
        // linha 1
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(1);
        cell.setCellValue("DROGARIA REDE POPULAR LTDA");
        CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
        cell.setCellStyle(style);
        // linha 2
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue("    Av Boulevard, 24 - P. S. Vicente, B. Roxo - RJ");
        CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
        cell.setCellStyle(style);
        // linha 3
        row = sheet.createRow(2);
        cell = row.createCell(1);
        cell.setCellValue("      DATA: " + getDateTime());
        CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
        cell.setCellStyle(style);
        //tabela colunas
        row = sheet.createRow(4);
        for(int i =0;i<model.getColumnCount();i++){
            cell = row.createCell(i);
            cell.setCellValue(model.getColumnName(i));
            CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
            cell.setCellStyle(style);
        }
        //tabela conteudo
        int i;
        for (i =0;i<model.getRowCount();i++){
            row = sheet.createRow(i+5);
            for (int j =0;j<model.getColumnCount();j++){
                cell = row.createCell(j);
                switch (j) {
                    case 0:
                        cell.setCellValue((int)model.getValueAt(i,j));
                        cell.setCellStyle(style);
                        break;
                    case 1:
                        cell.setCellValue((String)model.getValueAt(i,j));
                        cell.setCellStyle(style);
                        break;
                    default:
                        cell.setCellValue((Float)model.getValueAt(i,j));
                        CellStyle style2 = wb.createCellStyle();
                        style2.setDataFormat(format.getFormat("0.00"));
                        style2.setFont(Font);
                        cell.setCellStyle(style2);
                        break;
                }
                CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
            }
        }
        row = sheet.createRow(i+5+3);
        cell = row.createCell(2);
        cell.setCellValue("Total: ");
        CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
        cell.setCellStyle(style);
        
        cell = row.createCell(3);
        cell.setCellValue(InterfGrafica.getTotal());
        CellStyle style2 = wb.createCellStyle();
        style2.setDataFormat(format.getFormat("0.00"));
        style2.setFont(Font);
        cell.setCellStyle(style2);
        CellUtil.setAlignment(cell, HorizontalAlignment.LEFT);
        
        row = sheet.createRow(i+5+6);
        cell = row.createCell(0);
        cell.setCellValue("           Obrigado pela preferência - 2758-8249");
        cell.setCellStyle(style);
        wb.setPrintArea(0, 0, 3, 0, i+5+6+1);
        
        try {
            FileOutputStream arquivo = new FileOutputStream(file);
            wb.write(arquivo);
            arquivo.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ExportExcel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
} 
