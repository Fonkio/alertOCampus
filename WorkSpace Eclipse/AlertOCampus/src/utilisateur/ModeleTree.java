package utilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.event.TreeModelListener;
import javax.swing.plaf.metal.MetalIconFactory.TreeLeafIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ModeleTree implements TreeModel {
	
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Groupes");
	Utilisateur currentUser;
	List<Groupe> groupes = new ArrayList<>();
	List<Fil> fils = new ArrayList<>();

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		
	}
	
	public List<Fil> getFilListOfGroup(Groupe g) {
		List<Fil> lf = new ArrayList<>();
		for(Fil f : fils) {
			if (f.getDestination().equals(g)) {
				lf.add(f);
			}
		}
		return lf;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (((DefaultMutableTreeNode)parent).equals(root)) {
			return new DefaultMutableTreeNode(groupes.get(index).getLibelle());
		} else {
			return new DefaultMutableTreeNode(fils.get(index).getTitre());
		}
		
	}

	@Override
	public int getChildCount(Object parent) {
		if (((DefaultMutableTreeNode)parent).equals(root)) {
			return groupes.size();
		} else {
			return fils.size();
		}
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (((DefaultMutableTreeNode)parent).equals(root)) {
			return groupes.indexOf((Groupe) child);
		} else {
			
			return fils.indexOf((Fil) child);
		}
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		/*for (Fil f : fils) {
			if (f.getTitre().equals(((Fil)node).getTitre())) {
				return true;
			}
		}*/
		return false;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
		
	}

}
