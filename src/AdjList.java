import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class. You may add
 * methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjList extends AbstractAssocGraph
{

	HashMap<String, LinkedList> asscGraph;

	/**
	 * Contructs empty graph.
	 */
	public AdjList()
	{
		asscGraph = new HashMap<String, LinkedList>();

	} // end of AdjList()

	public void addVertex(String vertLabel)
	{
		for (String check : asscGraph.keySet())
		{
			if (vertLabel.equalsIgnoreCase(check))
			{
				return;
			}
		}
		asscGraph.put(vertLabel, new LinkedList());

	} // end of addVertex()

	/**
	 * Adds an edge to the graph. If the edge already exists in the graph, no
	 * changes are made. If one of the vertices doesn't exist, a warning to
	 * System.err should be issued and no edge or vertices should be added.
	 *
	 * @param srcLabel
	 *            Source vertex of edge to add.
	 * @param tarLabel
	 *            Target vertex of edge to add.
	 * @param weight
	 *            Integer weight to add between edges.
	 */

	public void addEdge(String srcLabel, String tarLabel, int weight)
	{
		/* checks for existing vertexes */
		boolean srcCheck = asscGraph.containsKey(srcLabel);
		boolean tarCheck = asscGraph.containsKey(tarLabel);

		if (!srcCheck || !tarCheck)
		{
			System.err.println("add edge Source vertex or target vertex does not exist, no changes made");
			return;
		}

		/* checking for duplicate edges, returns if found */
		if (asscGraph.get(srcLabel).findEdgeWeight(tarLabel) != -1)
		{
			return;
		}

		/* adds new node to linked list */
		LinkedList nList = asscGraph.get(srcLabel);
		nList.add(tarLabel, weight);
		asscGraph.put(srcLabel, nList);
		System.err.println("added edge " + tarLabel + " to vert " + srcLabel);
	} // end of addEdge()

	public int getEdgeWeight(String srcLabel, String tarLabel)
	{
		// Implement me!
		if (!asscGraph.containsKey(srcLabel))
		{
			return EDGE_NOT_EXIST;
		}
		else
		{
			LinkedList nList = asscGraph.get(srcLabel);
			int edgeWeight = nList.findEdgeWeight(tarLabel);
			if (edgeWeight != -1)
			{
				return edgeWeight;
			}
			else
			{
				return EDGE_NOT_EXIST;
			}
		}
	} // end of existEdge()

	public void updateWeightEdge(String srcLabel, String tarLabel, int weight)
	{
		// Implement me!
		/* checking for existing vertexes */
		boolean srcCheck = asscGraph.containsKey(srcLabel);
		boolean tarCheck = asscGraph.containsKey(tarLabel);

		if (!srcCheck || !tarCheck)
		{
			System.err.println("Source vertex or target vertex does not exist, no changes made");
			return;
		}

		/* checking for existing edge */
		if (asscGraph.get(srcLabel).findEdgeWeight(tarLabel) == -1)
		{
			System.err.println("Edge does not exist, no changes made");
			return;
		}

		LinkedList nList = asscGraph.get(srcLabel);
		/* checking for 0 weight, with deletion */
		if (weight == 0)
		{
			System.err.print("Edge " + tarLabel + "length is " + asscGraph.get(srcLabel).getLength());
			nList.deleteNode(tarLabel);
			asscGraph.put(srcLabel, nList);
			System.err.println("Edge " + tarLabel + " deleted(xd) from " + srcLabel + "new length is "
					+ asscGraph.get(srcLabel).getLength());
		}
		else
		{
			/* changes edge */
			nList.updateEdgeWeight(tarLabel, weight);
			asscGraph.put(srcLabel, nList);
			System.err.println("Edge " + tarLabel + " updated to " + weight);

		}

	} // end of updateWeightEdge()

	public void removeVertex(String vertLabel)
	{
		// Implement me!
		for (String check : asscGraph.keySet())
		{
			if (check.equalsIgnoreCase(vertLabel))
			{
				/* removes existing edges from other vertexes */
				for (String exist : asscGraph.keySet())
				{
					asscGraph.get(exist).deleteNode(vertLabel);
				}

				asscGraph.remove(vertLabel);
				System.err.println("removed vertex " + vertLabel);
				return;
			}
		}
		System.err.println("Vertex " + vertLabel + " not detected. No changes were made.");
	} // end of removeVertex()

	public List<MyPair> inNearestNeighbours(int k, String vertLabel)
	{
		List<MyPair> neighbours = new ArrayList<MyPair>();

		// Implement me!

		for (String srcTarget : asscGraph.keySet())
		{
			MyPair test = asscGraph.get(srcTarget).returnInPair(srcTarget, vertLabel);
			{
				if (test.getKey() != null && test.getValue() != null)
				{
					neighbours.add(test);
				}

			}
		}

		/* sorting the arraylist out in order */
		// Collections.sort(neighbours, (pair1, pair2) -> pair1.getValue() -
		// pair2.getValue());

		if (k == -1)
		{
			return neighbours;
		}
		else
		{
			neighbours = neighbours.subList(k, neighbours.size());
			return neighbours;
		}
	} // end of inNearestNeighbours()

	public List<MyPair> outNearestNeighbours(int k, String vertLabel)
	{
		List<MyPair> neighbours = new ArrayList<MyPair>();

		// Implement me!
		/* general add all */
		for (MyPair pair : asscGraph.get(vertLabel).returnPairs())
		{
			neighbours.add(pair);
		}

		/* sorting the arraylist out in order */
		Collections.sort(neighbours, (pair1, pair2) -> pair1.getValue() - pair2.getValue());

		if (k == -1)
		{
			return neighbours;
		}
		else
		{
			neighbours = neighbours.subList(k, neighbours.size());
			return neighbours;
		}
	} // end of outNearestNeighbours()

	public void printVertices(PrintWriter os)
	{
		for (String vert : asscGraph.keySet())
		{
			os.println(vert);
		}
	} // end of printVertices()

	public void printEdges(PrintWriter os)
	{
		// Implement me!

		for (String vert : asscGraph.keySet())
		{
			if (asscGraph.get(vert).getLength() != 0)
			{
				os.println(asscGraph.get(vert).generatePrintString(vert));
			}
		}
	} // end of printEdges()

	/* Linked list implementation */

	protected class Node
	{
		/* Weighted value */
		protected int edgeVal;
		/* Vertex value */
		protected String vertexEdge;
		/* next node ref */
		protected Node nNext;

		public Node(int edgeVal, String vertexEdge)
		{
			this.edgeVal = edgeVal;
			this.vertexEdge = vertexEdge;
			nNext = null;
		}

		public int getValue()
		{
			return edgeVal;
		}

		public String getVertex()
		{
			return vertexEdge;
		}

		public Node getNext()
		{
			return nNext;
		}

		public void setNode(int val)
		{
			edgeVal = val;
		}

		public void setNext(Node nextNode)
		{
			nNext = nextNode;
		}

	}// end of class Node

	protected class LinkedList
	{
		protected Node nHead;
		protected int nLength;

		public LinkedList()
		{
			nHead = null;
			nLength = 0;
		}

		public int getLength()
		{
			return nLength;
		}

		public void add(String tarVert, int weight)
		{
			Node addNode = new Node(weight, tarVert);
			if (nHead == null)
			{
				nHead = addNode;
			}
			else
			{
				addNode.setNext(nHead);
				nHead = addNode;
			}

			nLength++;
		}

		public int findEdgeWeight(String tarVert)
		{
			Node currNode = nHead;
			for (int i = 0; i < nLength; i++)
			{
				if (currNode.getVertex().equalsIgnoreCase(tarVert))
				{
					return currNode.getValue();
				}
				else
				{
					currNode = currNode.getNext();
				}
			}
			return -1;
		}

		public MyPair[] returnPairs()
		{

			Node currNode = nHead;
			MyPair pairArray[] = new MyPair[nLength];
			for (int i = 0; i < nLength; i++)
			{
				if (currNode != null)
				{
					pairArray[i] = new MyPair(currNode.getVertex(), currNode.getValue());
					currNode = currNode.getNext();
				}
			}
			return pairArray;
		}

		public MyPair returnInPair(String srcVert, String tarVert)
		{
			MyPair pair = new MyPair(null, null);
			Node currNode = nHead;
			for (int i = 0; i < nLength; i++)
			{
				if (currNode != null)
				{
					if (currNode.getVertex().equalsIgnoreCase(tarVert))
					{

						pair = new MyPair(srcVert, currNode.getValue());
					}
				}
			}

			return pair;
		}

		public void updateEdgeWeight(String tarVert, int weight)
		{
			Node currNode = nHead;

			for (int i = 0; i < nLength; i++)
			{
				if (currNode.getVertex().equalsIgnoreCase(tarVert))
				{
					currNode.setNode(weight);
					return;
				}
				else
				{
					currNode = currNode.getNext();
				}
			}
		}

		public String generatePrintString(String srcLabel)
		{
			String printString = "";
			Node currNode = nHead;

			for (int i = 0; i < nLength; i++)
			{
				if (currNode != null)
				{
					printString += (srcLabel + " " + currNode.getVertex() + " " + currNode.getValue() + " \n");
					currNode = currNode.getNext();
				}
			}

			return printString;

		}

		public void deleteNode(String tarEdge)
		{
			Node delete = nHead, prev = null;

			// deletion process of head node //
			if (delete != null && delete.getVertex().equalsIgnoreCase(tarEdge))
			{
				nHead = nHead.getNext();
				nLength--;
				return;
			}

			// search and delete node //
			while (delete != null && !(delete.getVertex().equals(tarEdge)))
			{
				prev = delete;
				delete = delete.getNext();
			}

			if (delete == null)
			{
				return;
			}
			nLength--;
			prev.setNext(delete.getNext());

		}

	}

} // end of class AdjList
