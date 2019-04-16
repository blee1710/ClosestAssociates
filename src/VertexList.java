import java.util.List;

public class VertexList {
	private Vertex[] verticies;
	private int vertexCount;
	
	public VertexList() {
		verticies = new Vertex[1];
		vertexCount = 0;
	}
	
	public void addVertex() {
		verticies[vertexCount] = new Vertex(getEdgeArraySize(), getEdgeCount());
		vertexCount++;
		
		resizeWhenFull();
	}
	
	public void addEdge(int srcIndex, int tarIndex, int weight) {
		for(int i = 0; i < vertexCount; i++) {
			if(i == srcIndex) {
				verticies[i].addEdge(weight);
			}
			else if(i == tarIndex) {
				verticies[i].addEdge(-weight);
			}
			else {
				verticies[i].addEdge(0);
			}
		}
	}
	
	public int edgeCount() {
		if(verticies[0] != null) {
			return verticies[0].edgeCount();
		}
		else {
			return 0;
		}
	}
	
	public int vertexCount() {
		return vertexCount;
	}
	
	public int getEdgeWeight(int srcIndex, int edgeIndex) {
		Vertex srcVertex = verticies[srcIndex];
		int weight = srcVertex.getEdgeWeight(edgeIndex);
		
		return weight;
	}
	
	public void updateEdgeWeight(int srcIndex, int tarIndex, int edgeIndex, int weight) {
		Vertex srcVertex = verticies[srcIndex];
		Vertex tarVertex = verticies[tarIndex];
		
		srcVertex.updateWeight(edgeIndex, weight);
		tarVertex.updateWeight(edgeIndex, -weight);
	}
	
	public void removeVertex(int vertIndex) {
		for(int i = vertIndex + 1; i < vertexCount; i++) {
			verticies[i - 1] = verticies[i];
		}
		verticies[vertexCount - 1] = null;
		vertexCount--;
	}
	
	public void removeEdge(int edgeIndex) {
		for(int i = 0; i < vertexCount; i++) {
			verticies[i].removeEdge(edgeIndex);
		}
	}
		
	private void resizeWhenFull() {
		if(vertexCount >= verticies.length) {
			resize();
		}
	}
	
	private void resize() {
		Vertex[] expandedVerticies = new Vertex[verticies.length * 2];
		
		for(int i = 0; i < verticies.length; i++) {
			expandedVerticies[i] = verticies[i];
		}
		
		verticies = expandedVerticies;
	}
	
	private int getEdgeArraySize() {
		if(verticies[0] != null) {
			return verticies[0].getArraySize();
		}
		else {
			return 1;
		}
	}
	
	private int getEdgeCount() {
		if(verticies[0] != null) {
			return verticies[0].edgeCount();
		}
		else {
			return 0;
		}
	}
}
