package pt.jkaiui.ui.modes;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.AbstractListModel;

/**
 *
 * @author jbi
 */
public class DefaultSortableListModel extends AbstractListModel {

    private Vector delegate = new Vector();
    private boolean Sorted = true;

    public void setSorted(boolean Sorted) {
        this.Sorted = Sorted;
    }

    @Override
    public int getSize() {
        return delegate.size();
    }

    @Override
    public Object getElementAt(int index) {
        return delegate.elementAt(index);
    }

    public void copyInto(Object anArray[]) {
        delegate.copyInto(anArray);
    }

    public void trimToSize() {
        delegate.trimToSize();
    }

    public void ensureCapacity(int minCapacity) {
        delegate.ensureCapacity(minCapacity);
    }

    public void setSize(int newSize) {
        int oldSize = delegate.size();
        delegate.setSize(newSize);
        if (oldSize > newSize) {
            fireIntervalRemoved(this, newSize, oldSize - 1);
        } else if (oldSize < newSize) {
            fireIntervalAdded(this, oldSize, newSize - 1);
        }
    }

    public int capacity() {
        return delegate.capacity();
    }

    public int size() {
        return delegate.size();
    }

    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    public boolean contains(Object elem) {
        return delegate.contains(elem);
    }

    public int indexOf(Object elem) {
        return delegate.indexOf(elem);
    }

    public int indexOf(Object elem, int index) {
        return delegate.indexOf(elem, index);
    }

    public int lastIndexOf(Object elem) {
        return delegate.lastIndexOf(elem);
    }

    public int lastIndexOf(Object elem, int index) {
        return delegate.lastIndexOf(elem, index);
    }

    public Object elementAt(int index) {
        return delegate.elementAt(index);
    }

    public Object firstElement() {
        return delegate.firstElement();
    }

    public Object lastElement() {
        return delegate.lastElement();
    }

    public void setElementAt(Object obj, int index) {
        delegate.setElementAt(obj, index);
        fireContentsChanged(this, index, index);
    }

    public void removeElementAt(int index) {
        delegate.removeElementAt(index);
        fireIntervalRemoved(this, index, index);
    }

    public void insertElementAt(Object obj, int index) {
        delegate.insertElementAt(obj, index);
        fireIntervalAdded(this, index, index);
    }

    public void addElement(Object obj) {
        int index = delegate.size();
        delegate.addElement(obj);
        fireIntervalAdded(this, index, index);
        if (Sorted) {
            Collections.sort(delegate, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {

                    if (!(o1 instanceof String && o2 instanceof String)) {
                        return 1;
                    }
                    return goodify((String) o1).compareTo(goodify((String) o2));
                }

                public String goodify(String x) {
                    // No more unix sorting!
                    x = x.toLowerCase();

                    if (Character.isLetterOrDigit(x.charAt(0))) {
                        return x;
                    }

                    // Traverse the letters finding the first real comparable letter. 
                    int firstGood = 1;
                    for (int i = 1; i < x.length(); i++) {
                        if (Character.isLetterOrDigit(x.charAt(i))) {
                            firstGood = i;
                            break;
                        }
                    }

                    return x.substring(firstGood);
                }
            });
        }
    }

    public boolean removeElement(Object obj) {
        int index = indexOf(obj);
        boolean rv = delegate.removeElement(obj);
        if (index >= 0) {
            fireIntervalRemoved(this, index, index);
        }
        return rv;
    }

    public void removeAllElements() {
        int index1 = delegate.size() - 1;
        delegate.removeAllElements();
        if (index1 >= 0) {
            fireIntervalRemoved(this, 0, index1);
        }
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    public Object[] toArray() {
        Object[] rv = new Object[delegate.size()];
        delegate.copyInto(rv);
        return rv;
    }

    public Object get(int index) {
        return delegate.elementAt(index);
    }

    public Object set(int index, Object element) {
        Object rv = delegate.elementAt(index);
        delegate.setElementAt(element, index);
        fireContentsChanged(this, index, index);
        return rv;
    }

    public void add(int index, Object element) {
        delegate.insertElementAt(element, index);
        fireIntervalAdded(this, index, index);
    }

    public Object remove(int index) {
        Object rv = delegate.elementAt(index);
        delegate.removeElementAt(index);
        fireIntervalRemoved(this, index, index);
        return rv;
    }

    public void clear() {
        int index1 = delegate.size() - 1;
        delegate.removeAllElements();
        if (index1 >= 0) {
            fireIntervalRemoved(this, 0, index1);
        }
    }

    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex must be <= toIndex");
        }
        for (int i = toIndex; i >= fromIndex; i--) {
            delegate.removeElementAt(i);
        }
        fireIntervalRemoved(this, fromIndex, toIndex);
    }
}
// EVERYTHING BELOW THIS IS A PART OF AN EXPERIMENTAL VERSION OF THIS CLASS THAT
// USES A TREESET INSTEAD OF A VECTOR
///*
// * DefaultSortableListModel.java
// *
// * Created on 30. Juni 2005, 22:20
// *
// */
//
//package pt.jkaiui.ui.modes;
//import java.util.Collections;
//import java.util.TreeSet;
//import java.util.Comparator;
//import java.util.Iterator;
//import javax.swing.AbstractListModel;
//
//
//
//
///**
// *
// * @author jbi
// */
//
//public class DefaultSortableListModel extends AbstractListModel {
//    
//    private TreeSet delegate = new TreeSet();
//    private HashMap locations = new HashMap();
//    
//    public DefaultSortableListModel() {
//        delegate = new TreeSet(new Comparator() {
//            public int compare(Object o1, Object o2) {
//                
//                if (!(o1 instanceof String && o2 instanceof String)) return 1;
//                return goodify((String)o1).compareTo(goodify((String)o2));
//            }
//            
//            public String goodify(String x) {
//                // No more unix sorting!
//                x = x.toLowerCase();
//                
//                if (Character.isLetterOrDigit(x.charAt(0)))
//                    return x;
//                
//                // Traverse the letters finding the first real comparable letter. 
//                int firstGood = 1;
//                for(int i = 1; i < x.length(); i++) {
//                    if(Character.isLetterOrDigit(x.charAt(i))) {
//                        firstGood = i;
//                        break;
//                    }
//                }
//                
//                return x.substring(firstGood);
//            }
//        });
//    }
//    
//    /** This will probably never be called, but since it's required, we might as 
//        well make it work properly. Since the "getElementAt" concept involves actually
//        returning an Object at a certain <strong>index</strong> and our data is
//        virtually modeled as a binary search tree, we have to resort to a
//        sequential search, reducing the runtime efficiency to O(n).
//     */
//    public Object getElementAt(int i) {
//        if(i == 0) return delegate.first();
//        if(i == delegate.size() - 1) return delegate.last();
//        if(i >= delegate.size()) throw new ArrayIndexOutOfBoundsException();
//        
//        Iterator it = delegate.iterator();
//        
//        // Skip through all the unneeded elements referenced in the Iterator
//        int index = 1;
//        while(index++ < i) it.next();
//        
//        return it.next();
//    }
//    
//    public int getSize() {
//        return delegate.size();
//    }
//    
//    public void clear() {
//        delegate.clear();
//    }
//    
//    public boolean contains(Object o) {
//        return delegate.contains(o);
//    }
//    
//    public void addElement(String element) {
//        delegate.add(element);
//    }
//    
//    public void removeElement(String element) {
//        delegate.remove(element);
//        this.fireIntervalRemoved()
//    }
//    
//    public int indexOf(Object o) {
//        if(o == null) return -1;
//        
//        
//    }
//}