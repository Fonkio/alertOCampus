package utilisateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;

public class ModeleTableau extends AbstractTableModel {
	
	ArrayList<Message> messages = new ArrayList<>();
	
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
		
		JPanel message = new JPanel();
		message.setLayout(new BorderLayout());
		
		JPanel messageInfo = new JPanel();
		
		messageInfo.setLayout(new BoxLayout(messageInfo, BoxLayout.X_AXIS));

		messageInfo.add(new JLabel(messages.get(ligne).getExpediteur().toString()));
		messageInfo.add(Box.createHorizontalGlue());
		messageInfo.add(new JLabel(messages.get(ligne).getdCreation().toString()));
		if(!messages.get(ligne).isSend()) {
			message.setBackground(Color.gray);
			messageInfo.setBackground(Color.gray);
		} else if(messages.get(ligne).getEtatDestinataire().containsValue(Status.EN_ATTENTE)) {
			message.setBackground(Color.red);
			messageInfo.setBackground(Color.red);
		} else if(messages.get(ligne).getEtatDestinataire().containsValue(Status.RECU)) {
			message.setBackground(Color.orange);
			messageInfo.setBackground(Color.orange);
		} else {
			message.setBackground(Color.green);
			messageInfo.setBackground(Color.green);
		}
		message.add(messageInfo, BorderLayout.NORTH);
		message.add(new JLabel(messages.get(ligne).getTexte()), BorderLayout.CENTER);

		return message;
	}
	
	

	
}
