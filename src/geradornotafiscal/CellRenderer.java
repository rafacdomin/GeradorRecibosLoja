/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradornotafiscal;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author rafael domingues
 */
public class CellRenderer extends DefaultTableCellRenderer {
/**
 * 
 */
private static final long	serialVersionUID	= 1L;
	public CellRenderer() 
	{
 		super();
 	}
 	public Component getTableCellRendererComponent(JTable table, Object value,
 			boolean isSelected, boolean hasFocus, int row, int column) {
 		this.setHorizontalAlignment(CENTER);
 		return super.getTableCellRendererComponent(table, value, isSelected,
 				hasFocus, row, column);
 	}
 }
