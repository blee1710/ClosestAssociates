import java.io.*;
import java.util.*;


/**
 * Incident matrix implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */

public class IncidenceMatrix extends AbstractAssocGraph {
	private Map<String, Integer> vertexMap;
	private Map<String, Integer> edgeMap;
	private VertexList verticies;
	
	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	vertexMap = new HashMap<>();
    	edgeMap = new HashMap<>();
    	verticies = new VertexList();
    } // end of IncidenceMatrix()


    public void addVertex(String vertLabel) {
    	if(!vertexMap.containsKey(vertLabel)) {
	    	int index = verticies.vertexCount();
	    	vertexMap.put(vertLabel, index);
	        verticies.addVertex();
    	}
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
    	String edgeLabel = getEdgeLabel(srcLabel, tarLabel);
    	int index = verticies.edgeCount();
        edgeMap.put(edgeLabel, index);
        
        int srcIndex = vertexMap.get(srcLabel);
        int tarIndex = vertexMap.get(tarLabel);
        verticies.addEdge(srcIndex, tarIndex, weight);
    } // end of addEdge()


	public int getEdgeWeight(String srcLabel, String tarLabel) {
		String edgeLabel = getEdgeLabel(srcLabel, tarLabel);
		
		if(!edgeMap.containsKey(edgeLabel)) {
			return EDGE_NOT_EXIST;
		}
		
		int srcIndex = vertexMap.get(srcLabel);
		int edgeIndex = edgeMap.get(edgeLabel);
		return verticies.getEdgeWeight(srcIndex, edgeIndex);
	} // end of existEdge()


	public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        String edgeLabel = getEdgeLabel(srcLabel, tarLabel);
        
        try {
	        int srcIndex = vertexMap.get(srcLabel);
	        int tarIndex = vertexMap.get(tarLabel);
	        int edgeIndex = edgeMap.get(edgeLabel);
	        
	        if(weight == 0) {
	        	verticies.removeEdge(edgeIndex);
	        	removeEdgeMapping(edgeLabel);
	        }
	        else {
	        	verticies.updateEdgeWeight(srcIndex, tarIndex, edgeIndex, weight);
	        }
        }
        catch(NullPointerException e) {
        	
        }        
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
    	if(!vertexMap.containsKey(vertLabel)) {
    		return;
    	}
        int vertIndex = vertexMap.get(vertLabel);
        
        removeVertexMapping(vertLabel);
        removeVertexEdges(vertLabel);
        verticies.removeVertex(vertIndex);
    } // end of removeVertex()


	public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
		List<MyPair> neighbours = new ArrayList<>();
        int vertIndex = vertexMap.get(vertLabel);
        
        for(String edgeLabel : edgeMap.keySet()) {
        	String srcLabel = edgeLabel.split(":")[0];
        	int edgeIndex = edgeMap.get(edgeLabel);
        	int edgeWeight = verticies.getEdgeWeight(vertIndex, edgeIndex);
        	
        	if(edgeWeight < 0) {
        		MyPair inNeighbour = new MyPair(srcLabel, Math.abs(edgeWeight));
        		neighbours.add(inNeighbour);
        	}
        }
        
        neighbours.sort(new SortEdges(false));
        
        if(k > 0) {
        	neighbours = neighbours.subList(0, Math.min(neighbours.size(), k));
        }
        
        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();
        int vertIndex = vertexMap.get(vertLabel);
        
        for(String edgeLabel : edgeMap.keySet()) {
        	String tarLabel = edgeLabel.split(":")[1];
        	int edgeIndex = edgeMap.get(edgeLabel);
        	int edgeWeight = verticies.getEdgeWeight(vertIndex, edgeIndex);
        	
        	if(edgeWeight > 0) {
        		MyPair inNeighbour = new MyPair(tarLabel, edgeWeight);
        		neighbours.add(inNeighbour);
        	}
        }
        
        neighbours.sort(new SortEdges(true));

        if(k > 0) {
        	neighbours = neighbours.subList(0, Math.min(neighbours.size(), k));
        }
        
        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        String vertexList = String.join(" ", vertexMap.keySet());
        os.println(vertexList);
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
    	String[] edgeLabels = getEdgeLabels();
    	
    	for(int i = 0; i < verticies.edgeCount(); i++) {
    		String[] vertLabels = edgeLabels[i].split(":");
    		String srcLabel = vertLabels[0];
    		String tarLabel = vertLabels[1];
    		
    		int srcIndex = vertexMap.get(srcLabel);
    		int edgeIndex = edgeMap.get(edgeLabels[i]);
    		int weight = verticies.getEdgeWeight(srcIndex, edgeIndex);
    		
    		if(weight > 0) {
	    		String edgeString = String.format("%s %s %d", srcLabel, tarLabel, weight);
	    		os.println(edgeString);
    		}
    	}
    } // end of printEdges()
    
    private String getEdgeLabel(String srcLabel, String tarLabel) {
    	return srcLabel + ":" + tarLabel;
    }
    
    private void removeVertexMapping(String tarLabel) {
    	int tarIndex = vertexMap.get(tarLabel);
    	vertexMap.remove(tarLabel);
    	
    	vertexMap.replaceAll((vertLabel, vertIndex) -> {
    		if(vertIndex > tarIndex) {
    			return vertIndex - 1;
    		}
    		else {
    			return vertIndex;
    		}
    	});
    }
    
    private void removeVertexEdges(String tarLabel) {
    	String[] edgeLabels = getEdgeLabels();
    	for(String edgeLabel : edgeLabels) {
    		if(edgeLabel.contains(tarLabel)) {
    			int edgeIndex = edgeMap.get(edgeLabel);
    			
    			verticies.removeEdge(edgeIndex);
    			removeEdgeMapping(edgeLabel);
    		}
    	}
    }
    
    private void removeEdgeMapping(String tarLabel) {
    	int tarIndex = edgeMap.get(tarLabel);
    	edgeMap.remove(tarLabel);
    	
    	edgeMap.replaceAll((edgeLabel, edgeIndex) -> {
    		if(edgeIndex > tarIndex) {
    			return edgeIndex - 1;
    		}
    		else {
    			return edgeIndex;
    		}
    	});
    }
    
    private String[] getEdgeLabels() {
    	int edgeCount = verticies.edgeCount();
    	String[] edgeLabels = edgeMap.keySet().toArray(new String[edgeCount]);
    	return edgeLabels;
    }
    

} // end of class IncidenceMatrix
