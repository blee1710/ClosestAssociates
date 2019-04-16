import java.util.List;

public class Vertex {
	private int[] edgeWeights;
	private int edgeCount;
	
	public Vertex(int arraySize, int edgeCount) {
		edgeWeights = new int[arraySize];
		this.edgeCount = edgeCount;
	}
	
	public void addEdge(int weight) {
		edgeWeights[edgeCount] = weight;
		edgeCount++;
		
		resizeWhenFull();
	}
	
	public int edgeCount() {
		return edgeCount;
	}
	
	public int getEdgeWeight(int edgeIndex) {
		return edgeWeights[edgeIndex];
	}
	
	public void updateWeight(int edgeIndex, int weight) {
		edgeWeights[edgeIndex] = weight;
	}
	
	public int getArraySize() {
		return edgeWeights.length;
	}
	
	public void removeEdge(int edgeIndex) {
		for(int i = edgeIndex + 1; i < edgeCount; i++) {
			edgeWeights[i - 1] = edgeWeights[i];
		}
		
		edgeCount--;
	}
	
	private void resizeWhenFull() {
		if(edgeCount >= edgeWeights.length) {
			resize();
		}
	}
	
	private void resize() {
		int[] expandedWeights = new int[edgeWeights.length * 2];
		
		for(int i = 0; i < edgeWeights.length; i++) {
			expandedWeights[i] = edgeWeights[i];
		}
		edgeWeights = expandedWeights;
	}
}
