package tools;

//General imports
import java.util.Iterator;
import java.util.Stack;

//Local imports
import objects.HealthDrop;
import objects.Inimicus;


public class CircuArray<E> implements Iterable<E> 
{
    private int manyItems;
    private Stack<Integer> freeIndex;
    private int size;
    private Object[] data;
    
	public CircuArray() 
	{
		manyItems = 0;
		data = new Object[100];
		freeIndex = new Stack<Integer>();
		for (int i = data.length-1; i >=0; i--)
			freeIndex.push(i);		
		size = 100;
	}
	
	public CircuArray(int size) 
	{
		manyItems = 0;
		freeIndex = new Stack<Integer>();
		data = new Object[size];
		for (int i = data.length-1; i >=0; i--)
			freeIndex.push(i);	
		this.size = size;
	}
	
	/**
	 * Method to retreve an item at a
	 * given index
	 * @param index
	 * @return item in that index.
	 */
	@SuppressWarnings("unchecked")
	public E get(int index)
	{
		return (E) data[index];
	}
	
	/**
	 * Adds an object to the array.
	 * If there is not an available index, do nothing.
	 * 
	 * @param object to add.
	 */
	public void add(E e)
	{
		if (!freeIndex.isEmpty())
		{
			data[(int)freeIndex.pop()] = e;
			manyItems++;
		}
	}
	
	/**
	 * Removes item at given index.
	 * 
	 * @param index to remove at.
	 */
	public void remove(int i)
	{
		data[i] = null;
		
		freeIndex.push(i);
		manyItems--;
	}
	
	/**
	 * Method to return the size of the array.
	 * @return size
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Method to return how many objects there are in the array.
	 * @return manyItems
	 */
	public int items()
	{
		return manyItems;
	}
	
	/**
	 * Method to count occurances of a single object.
	 * Only 
	 * @param c Class to count 
	 * @return count of occurances of that class
	 */
	public int items(Class<?> c)
	{
	    int count = 0;
	    
	    if(c == Inimicus.class)
	    {
	        for (int i = 0; i < size; i++)
	        {
	            if(data[i] instanceof Inimicus)
	                count++;
	        }
	    }
	    if(c == HealthDrop.class)
        {
            for (int i = 0; i < size; i++)
            {
                if(data[i] instanceof HealthDrop)
                    count++;
            }
        }
	    return count;

	}
	
	/**
	 * Method to check if the spot at the given index is empty.
	 * @param index
	 * @return
	 */
	public boolean isEmpty(int index)
	{
		return (data[index] == null);
	}
	
	/**
	 * Method to search for a taken slot in the array.
	 * If none, return -1.
	 * @param index to start at.
	 * @return taken index
	 */
	public int closedSearch(int index)
	{
		int none = -1;
		for (int i = ((index+1) % data.length), count = 0; 
				i < (data.length + index) &&
				count <= size(); i = ((i + 1) % data.length), count++)
		{
			if (data[i] != null)
				return i;
			System.out.println("Looping... "+count);
		}
		return none;
	}

	/**
	 * Method to search for an open slot in the array.
	 * If none, return -1. (Not Used)
	 * @param index to start at.
	 * @return available index.
	 */
	public int openSearch(int index)
	{
		int none = -1;
		for (int i = ((index+1) % data.length), count = 0; 
				i < (data.length + index) &&
				count <= size(); i = ((i + 1) % data.length), count++)
		{
			if (data[i] == null)
				return i;
			System.out.println("Looping... "+count);
		}
		return none;
	}
	
	/**
	 * Method to clear all the data in the array.
	 */
	public void clear()
	{
		for (int i = 0; i < data.length; i++)
			data[i] = null;
	}

	@Override
	public Iterator<E> iterator() 
	{
		return new CircuArrayIter();
	}
	
	private class CircuArrayIter implements Iterator<E>
	{
		int index;
		int lastReturnedIndex;
		
		public CircuArrayIter()
		{
			index = 0;
			lastReturnedIndex = -1;
		}
		
		@Override
		public boolean hasNext() 
		{
			return (openSearch(0) > 0);
		}

		@SuppressWarnings("unchecked")
		public E next() 
		{
			lastReturnedIndex = index;
			index = (index + 1) % data.length;
			return (E) data[lastReturnedIndex];
		}
		
		public void remove()
		{
			if (lastReturnedIndex == -1)
				throw new IllegalStateException();
			data[lastReturnedIndex] = null;
			lastReturnedIndex = -1;
			manyItems--;
		}
		
	}

}
