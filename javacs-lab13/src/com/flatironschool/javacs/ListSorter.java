/**
 *
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {

		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * Returns a list that might be new.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.size() == 1) {
			return list;
		}
		//Split the list in half
		int midpoint = list.size() / 2;
		List<T> leftHalf = list.subList(0, midpoint);
		List<T> rightHalf = list.subList(midpoint, list.size());

		//Merge together sorted lists
		return merge(mergeSort(leftHalf, comparator), mergeSort(rightHalf, comparator), comparator);
	}

	public List<T> merge(List<T> list1, List<T> list2, Comparator<T> comparator) {
		List<T> mergedList = new ArrayList<T>();

		//Make new lists so original objects aren't messed with
		List<T> newList1 = new ArrayList<T>();
		List<T> newList2 = new ArrayList<T>();
		//Populate new lists
		for (T element: list1) {
			newList1.add(element);
		}
		for (T element: list2) {
			newList2.add(element);
		}

		//System.out.println("list 1 before : " + newList1);
		//System.out.println("list 2 before : " + newList2);

		while (newList1.size() != 0 && newList2.size() != 0) {
			//Attain first elements from the list
			T leftValue = newList1.get(0);
			T rightValue = newList2.get(0);

			if (comparator.compare(leftValue, rightValue) <= 0) {
				T rem = newList1.remove(0);
				mergedList.add(rem);
				//System.out.println("REMOVED " + rem + " from list1 -- it's now :" + newList1);
			} else {
				T rem = newList2.remove(0);
				mergedList.add(rem);
				//System.out.println("REMOVED " + rem + " from list2 -- it's now :" + newList2);
			}

			//System.out.println("list 1 in : " + newList1);
			//System.out.println("list 2 in : " + newList2);
		}

		//System.out.println("Merged list contains: " + mergedList);

		//System.out.println("list 1 after : " + newList1);
		//System.out.println("list 2 after : " + newList2);


		//The shorter list has no more elements, append the remaining elements
		if (newList1.size() == 0) {
			mergedList.addAll(newList2);
		} else {
			mergedList.addAll(newList1);
		}

		//System.out.println("Merged list contains after leftover additions: " + mergedList);
		return mergedList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 *
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>();
		//Add the elements of the unsorted list to the heap
		for (T element : list) {
			heap.offer(element);
		}

		//Remove all elements from the list
		list.clear();

		//Remove elements from the heap and append them to the list in sorted order
		while (heap.size() != 0) {
			list.add(heap.poll());
		}
	}


	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 *
	 * @param k
	 * @param list
	 * @param comparator
	 * @return
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		PriorityQueue<T> heap = new PriorityQueue<T>();
		List<T> sorted = new ArrayList<T>();

		while (list.size() != 0) {
			T removal = list.remove(0);

			if (heap.size() != k) {
				//If the heap is not "full"
				heap.offer(removal);

			} else {
				//If the heap is full, compare the smallest element in heap
				T smallestHeapElement = heap.peek();
				if (comparator.compare(removal, smallestHeapElement) > 0) {
					heap.poll(); //Remove smallest element
					heap.offer(removal); //Insert the list element
				}
			}
		}

		//Remove elements from the heap and append them to the list in sorted order
		while (heap.size() != 0) {
			sorted.add(heap.poll());
		}
		return sorted;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));

		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};

		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);


		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);

		/*
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);*/
	}
}
