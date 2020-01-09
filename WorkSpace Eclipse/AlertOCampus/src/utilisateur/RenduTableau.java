package utilisateur;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class RenduTableau implements TableCellRenderer, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return (JPanel)value;
	}

}
