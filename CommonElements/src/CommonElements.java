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
// [ ] For each collection create an array of frequencies for each object
// [ ] Set the smallest collection as the query collection
// [ ] Make a 2d array [i][j] with i corresponding to the frequency of the object
// 		from query collection and j the collection it came from
// [ ] Add frequency of object from the non-query collection into j
// [ ] Add to commonElements each object that has k-1 length (write function to get)

	public Comparable[] findCommonElements(Comparable[][] collections)
	{
		Comparable[] commonElements = new Comparable[collections.length];
		collections = sortCollections(collections);
		getFrequency(collections);

		return (commonElements);
	}

	public void deleteDuplicates(Comparable[][] collection, Object obj)
	{

	}

	public Comparable[] getFrequencyObject(Comparable[][] collection, Comparable obj)
	{
		Comparable[] f = new Comparable[2];
		int freq = 0;
		for (Comparable c : collection)
		{
			if (c.compareTo(obj))
			{

			}
		}
		return (f);
	}

	public Comparable[][] getFrequency(Comparable[][] collection)
	{
		Comparable[][] freq = new Comparable[collection.length][2];
		int i = 0;
		for (Comparable c : collection)
		{
			if (c != null)
				freq[i++] = getFrequencyObject(collection, c);
		}
		// reduce array
		return (freq);
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
