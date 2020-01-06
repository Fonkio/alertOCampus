package utilisateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

public class ModeleTableau extends AbstractTableModel {
	
	Set<Message> messages = new TreeSet<>();
	
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return messages.size();
	}

	@Override
	public Object getValueAt(int ligne, int colone) {
		Iterator<Message> it = messages.iterator();
		Message m = null;
		for (int i = 0; i < ligne+1; i++) {
			m = it.next();
		}
		JPanel message = new JPanel();
		message.setLayout(new BorderLayout());
		
		JPanel messageInfo = new JPanel();
		
		messageInfo.setLayout(new BoxLayout(messageInfo, BoxLayout.X_AXIS));

		messageInfo.add(new JLabel(m.getExpediteur().toString()));
		messageInfo.add(Box.createHorizontalGlue());
		messageInfo.add(new JLabel(m.getdCreation().toString()));
		if(!m.isSend()) {
			message.setBackground(Color.gray);
			messageInfo.setBackground(Color.gray);
		} else if(m.getEtatDestinataire().containsValue(Status.EN_ATTENTE)) {
			message.setBackground(Color.red);
			messageInfo.setBackground(Color.red);
		} else if(m.getEtatDestinataire().containsValue(Status.RECU)) {
			message.setBackground(Color.orange);
			messageInfo.setBackground(Color.orange);
		} else {
			message.setBackground(Color.green);
			messageInfo.setBackground(Color.green);
		}
		message.add(messageInfo, BorderLayout.NORTH);
		message.add(new JLabel(m.getTexte()), BorderLayout.CENTER);

		return message;
	}
	
	

	
}
