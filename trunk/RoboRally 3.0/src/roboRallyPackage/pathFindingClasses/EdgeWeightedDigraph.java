/**
 * 
 */
package roboRallyPackage.pathFindingClasses;

import be.kuleuven.cs.som.annotate.*;

/**
 *  The <tt>EdgeWeightedDigraph</tt> class represents a directed graph (digraph) of vertices (nodes)
 *  named 0 through V-1, where each edge ('arrow' between two vertices) has a real-valued weight.
 *  
 *  It supports the following operations: add an edge to the graph,
 *  									  iterate over all of edges leaving a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class EdgeWeightedDigraph
{
    /**
     * Variable representing the number of vertices (nodes).
     */
	private final int V;
	
	/**
	 * Variable representing the total number of edges ('arrows' between two vertices).
	 */
    private int E;
    
    /**
     * Variable representing an array of bags containing directedEdges ('arrows' between two vertices).
     * The bag on index i represents the vertex with number i; the directedEdges the edges that start from this vertex.
     */
    private Bag<DirectedEdge>[] adj;
    
    /**
     * Create an empty edge-weighted digraph with V vertices.
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

   /**
     * Create a edge-weighted digraph with V vertices and E edges. The Edges are randomly made.
     */
    public EdgeWeightedDigraph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
        	// Math.random generates a number x: 0 <= x < 1
        	// the vertices are represented by a random integer between 0 and V-1
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            // the weight is a random number between 0 and 1 with a precision of 0.01
            double weight = Math.round(100 * Math.random()) / 100.0;
            DirectedEdge e = new DirectedEdge(v, w, weight);
            addEdge(e);
        }
    }

//    /**
//     * Create an edge-weighted digraph from input stream.
//     */
//    public EdgeWeightedDigraph(In in) {
//        this(in.readInt());
//        int E = in.readInt();
//        for (int i = 0; i < E; i++) {
//            int v = in.readInt();
//            int w = in.readInt();
//            double weight = in.readDouble();
//            addEdge(new DirectedEdge(v, w, weight));
//        }
//    }

   /**
     * Clones the given EdgeWeightedDigraph.
     */
    public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<DirectedEdge> reverse = new Stack<DirectedEdge>();
            for (DirectedEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (DirectedEdge e : reverse) {
                adj[v].add(e);
            }
        }
    }

   /**
     * Returns the number of vertices (nodes) in this digraph.
     */
    @Basic
    public int V() {
        return V;
    }

   /**
     * Returns the total number of edges ('arrows' between two vertices) in this digraph.
     */
    @Basic
    public int E() {
        return E;
    }


   /**
     * Add the given edge to this digraph.
     * 
     * @param	e
     * 			The edge to be added to this digraph.
     */
    public void addEdge(DirectedEdge e) {
        // find out from which vertex this edge starts (v).
    	int v = e.from();
    	// add the given edge to the bag that is situated on index v. This bag contains all the edges starting from vertex v.
        adj[v].add(e);
        E++;
    }


   /**
     * Returns an iterable collection containing the edges leaving vertex v.
     * To iterate over the edges leaving vertex v, use foreach notation: <tt>for (DirectedEdge e : graph.adj(v))</tt>.
     * 
     * @param	v
     * 			An integer representing the vertex where the returned edges will start.
     * @return	...
     * 			| for each edge in result: edge.from() == v
     * 			| for each edge not in result: edge.from() != v
     */
    public Iterable<DirectedEdge> adj(int v) {
        // Returns the bag containing the edges leaving vertex v.
    	// A bag is an Iterable (collection that has an Iterator())
    	return adj[v];
    }

   /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges, use foreach notation: <tt>for (DirectedEdge e : graph.edges())</tt>.
     * 
     * @return	...
     * 			| result.size() == E()
     */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    } 

   /**
     * Returns number of edges leaving v.
     * 
     * @param	v
     * 			An integer representing the vertex for which the number of edges starting from this vertex is returned.
     * @return	...
     * 			| result == adj(v).size()
     */
    public int outdegree(int v) {
        return adj[v].size();
    }



   /**
     * Returns a string representation of this graph.
     */
    @Override
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

//    /**
//     * Test client.
//     */
//    public static void main(String[] args) {
//        In in = new In(args[0]);
//        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
//        StdOut.println(G);
//    }

}