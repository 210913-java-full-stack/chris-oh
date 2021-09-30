package SBAutils;


public class CusArrLis<E> implements CusLisInterface<E> {
    private Object[] al = new Object[2];
    private int size;

    //Method to Expand the Array
    private void expand() {
        Object[] tempal = new Object[(2*size)];
        for (int i = 0; i < size; i++) {
            tempal[i] = al[i];
        }
        al = tempal;
    }

    //Get the size of the array list
    @Override
    public int size() {
        return size;
    }

    //Add a new Object to the end of the list
    @Override
    public void add(E e) {
        int check = al.length;
        if (size == check) {
            this.expand();
        }
        al[size] = e;
        size++;
    }

    //Add a new Object to any valid index on the list
    @Override
    public void add(E e, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        int check = al.length;
        if (size == check) {
            this.expand();
        }

        Object[] tempal = new Object[al.length];
        for (int i = 0; i < index; i++) {
            tempal[i] = al[i];
        }
        tempal[index] = e;
        for (int i = index +1; i <= size; i++) {
            tempal[i] = al[i-1];
        }
        al = tempal;
        size++;
    }

    //Return the object at any valid index in the array
    @Override
    public E get(int index) {
        if (index > size || index < 0 || al[index] == null) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        return (E) al[index];
    }

    //Remove a valid object from a valid index in the array
    @Override
    public void remove(int index) {
        if (index > size || index < 0 || al[index] == null) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        Object[] tempal = new Object[al.length];
        for (int i = 0; i < index; i++){
            tempal[i] = al[i];
        }
        for (int i = index; i < size-1; i++){
            tempal[i] = al[i+1];
        }
        al = tempal;
        size--;
    }

    //Replace the Array with an empty one, and reset the size
    @Override
    public void clear() {
        al = new Object[2];
        size = 0;
    }

    //Check to see if there is a1n identical object to the input in the array. Returns first index it happens
    @Override
    public int contains(E e) {
        for (int i = 0; i < size; i++){
            if (al[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }
}
