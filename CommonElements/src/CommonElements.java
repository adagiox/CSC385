import java.util.*;

public class CommonElements
{
	private int comparisons;

	public CommonElements()
	{
		comparisons = 0;
	}

	public Comparable[][] sortCollections(Comparable[][] collections)
	{
		for (Comparable[] c : collections)
		{
			Arrays.sort(c);
			for (Object e : c)
			{
				System.out.print(e + " ");
			}
			System.out.println();
		}
		return (collections);
	}

	public int getCollectionLength(Comparable[][] nonQuery)
	{
		int length = 0;

		for (Comparable[] c : nonQuery)
		{
			for (Object e : c)
			{
				if (e != null)
					length++;
			}
		}
		return (length);
	}

//    Q      1  2
//	[A:1]-->[0][1]
//
//	[B:1]-->[1][1]
//
//	[C:1]-->[2][1]
//
//	[D:3]-->[3][3]
//
// return: [B][C][D][D][D]

//
// [A][1]
// [B][1]
// [C][2]
// [E][1]
// [F][2]
//

// [X] Sort each collection
// [X] For each collection create an array of frequencies for each object
// [X] Set the smallest collection as the query collection
// [ ] Merge each non-query list
// [ ] Make sure sorted
// [ ] Compare
// [ ] Add to commonElements each object that has k-1 length (write function to get)

	public Comparable[] findCommonElements(Comparable[][] collections)
	{
		Comparable[] commonElements;
		Comparable[][][] freqList;
		collections = sortCollections(collections);
		freqList = getFrequency(collections);
		commonElements = getCommonElements(freqList);

		return (commonElements);
	}

	public int getSizeList(Comparable[][] list)
	{
		return (list.length);
	}

	public Comparable[][][] setQuery(Comparable[][][] freqList)
	{
		int min;
		int size;
		int i = 0;
		int j = 0;
		min = getSizeList(freqList[0]);
		for (Comparable[][] c : freqList)
		{
			size = getSizeList(c);
			if (size < min)
			{
				min = size;
				j = i;
			}
			i++;
		}
		if (j != 0)
		{
			Comparable[][] temp;
			temp = freqList[0];
			freqList[0] = freqList[j];
			freqList[j] = temp;
		}
		return (freqList);
	}

	public Comparable[] getCommonElements(Comparable[][][] freqList)
	{
		freqList = setQuery(freqList);


		return (commonElements);
	}

	public Comparable[] setFrequencyObject(Comparable obj, Comparable[] list, int it)
	{
		Comparable[] freqObj = new Comparable[list.length];
		freqObj[0] = obj;
		for (int i = it + 1; i < list.length; i++)
		{
			if (list[i].compareTo(obj) == 0)
			{
				for (int j = 1; j < freqObj.length; j++)
				{
					if (freqObj[j] == null)
					{
						freqObj[j] = list[i];
						break;
					}
				}
				list[i] = null;
			}
		}
		return (freqObj);
	}

	public Comparable[][] getFrequencyObject(Comparable[] list)
	{
		Comparable[][] f = new Comparable[list.length][2];
		int i = 0;
		for (Comparable c : list)
		{
			if(c != null)
			{
				f[i] = setFrequencyObject(c, list, i);
			}
			i++;
		}
		f = reduceFreqList(f);
		return (f);
	}

	public Comparable[][] reduceFreqList(Comparable[][] freqList)
	{
		int size = 0;
		for (Comparable[] c : freqList)
		{
			if (c[0] != null)
			{
				size++;
			}
		}
		Comparable[][] reduced = new Comparable[size + 1][];
		int i = 0;
		for (Comparable[] c : freqList)
		{
			if (c[0] != null)
			{
				reduced[i++] = c;
			}
		}
		return (reduced);
	}

	public Comparable[][][] getFrequency(Comparable[][] collection)
	{
		Comparable[][][] freqList = new Comparable[collection.length][][];
		int i = 0;
		for (Comparable[] list : collection)
		{
			freqList[i++] = getFrequencyObject(list);
		}
		return (freqList);
	}

	public int getComparisons()
	{
		return (this.comparisons);
	}

	public static void main(String args[])
	{
		CommonElements ce = new CommonElements();
		Comparable[] common;
		Comparable[][] compCollection = {{"A", "B", "C", "D", "E", "F", "F"},
											{"E", "A", "D", "F", "F"},
											{"D", "E", "F", "Z", "Z", "Q"},
											{"E", "A", "D", "F", "F"}};
		System.out.println(ce.getComparisons());
		common = ce.findCommonElements(compCollection);
		for (Comparable c : common)
		{
			System.out.println(c);
		}
		System.out.println(ce.getComparisons());
	}
}
