package kroki.app.gui.visitor;

import java.util.Iterator;
import java.util.List;

import kroki.profil.association.Hierarchy;
import kroki.profil.panel.VisibleClass;
import kroki.profil.panel.container.ManyToMany;
import kroki.profil.panel.container.ParentChild;
import kroki.profil.subsystem.BussinesSubsystem;
import kroki.profil.utils.ManyToManyUtil;
import kroki.profil.utils.ParentChildUtil;
import kroki.profil.utils.VisibleClassUtil;
import kroki.uml_core_basic.UmlClass;
import kroki.uml_core_basic.UmlPackage;
import kroki.uml_core_basic.UmlProperty;

/**
 * Used to find all panels which can be set as a hierarchy's target panel
 * @author Vladan Marsenić (vladan.marsenic@gmail.com)
 */
public class AllPosibleHierarchyPanels extends Visitor {

	@Override
	public void visit(Object object) {
		UmlProperty property = null;
		if (object instanceof UmlProperty) {
			property = (UmlProperty) object;
		} else {
			System.err.println("AllPosibleHierarchyPanels.java - 28: Object cannot be cast to UmlProperty");
			return;
		}
		UmlClass umlClass = property.umlClass();
		if (umlClass == null) {
			System.err.println("AllPosibleHierarchyPanels.java - 33: UmlProperty's class is null");
			return;
		}
		UmlPackage umlPackage = umlClass.umlPackage();
		if (umlPackage == null) {
			System.err.println("AllPosibleHierarchyPanels.java - 38: UmlClass's package is null");
			return;
		}
		while (true) {
			if (umlPackage.nestingPackage() == null) {
				break;
			} else {
				umlPackage = umlPackage.nestingPackage();
			}
		}
		((BussinesSubsystem) umlPackage).accept(this);

		if(umlClass instanceof ParentChild){
			ParentChild parentChild = (ParentChild) umlClass;
			Hierarchy hierarchy = (Hierarchy)object;
			if (hierarchy.getLevel() > 1 || hierarchy.getLevel() == -1){
				objectList.clear();
				objectList.addAll(ParentChildUtil.getAllPosibleTargetPanels(parentChild));
			}
			else{
				objectList.remove(parentChild);
				Iterator<Object> iter = objectList.iterator();
				ParentChild parentChild2;
				while (iter.hasNext()){
					Object next = iter.next();
					if (next instanceof ParentChild){
						parentChild2 = (ParentChild)next;
						for (Hierarchy h : VisibleClassUtil.containedHierarchies(parentChild2))
							if (h.getTargetPanel() == parentChild){
								iter.remove();
								break;
							}
	
					}
				}
	
			}
		} if(umlClass instanceof ManyToMany){
			ManyToMany manyToMany = (ManyToMany) umlClass;
			Hierarchy hierarchy = (Hierarchy)object;
			if (hierarchy.getLevel() > 1 || hierarchy.getLevel() == -1){
				objectList.clear();
				objectList.addAll(ManyToManyUtil.getAllPosibleTargetPanels(manyToMany));
			}
			else{
				objectList.remove(manyToMany);
				Iterator<Object> iter = objectList.iterator();
				ManyToMany manyToMany2;
				while (iter.hasNext()){
					Object next = iter.next();
					if (next instanceof ManyToMany){
						manyToMany2 = (ManyToMany)next;
						for (Hierarchy h : VisibleClassUtil.containedHierarchies(manyToMany2))
							if (h.getTargetPanel() == manyToMany){
								iter.remove();
								break;
							}
	
					}
				}
	
			}
		}


	}

	public void add(VisibleClass object) {
		this.objectList.add(object);
	}

	public void addAll(List<VisibleClass> objectList) {
		this.objectList.addAll(objectList);
	}
}
