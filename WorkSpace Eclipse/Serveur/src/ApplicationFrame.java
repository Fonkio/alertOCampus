import javax.swing.JFrame;

public class ApplicationFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Serveur AlertOCampus");
		VueServeur vue = new VueServeur();
		frame.add(vue);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	

	}

}
