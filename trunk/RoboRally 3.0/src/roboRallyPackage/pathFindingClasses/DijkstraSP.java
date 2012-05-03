
package roboRallyPackage.pathFindingClasses;


/**
 * Dijkstra's algorithm. Computes the shortest path tree.
 * Assumes all weights are nonnegative.
 * 
 * @invar	...	
 * 			| check(EdgeWeightedDigraph G, int s)
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class DijkstraSP
{
    /**
     * Variable representing an array that contains the shortest distance between the starting point s and the vertex represented by index v.
     */
	private double[] distTo;          // distTo[v] = distance  of shortest s->v path
	
	/**
	 * Variable representing an array that contains the last edge on the shortest path between the starting point s and the vertex represented by index v.
	 * 
	 * F.e. if the shortest path to get to point 5 (starting from 0) is: 0 -> 3 -> 2 -> 8 -> 5
	 * 		then edgeTo[5].to() = 5 and edgeTo[5].from() = 8
	 * 			 edgeTo[8].to() = 8 and edgeTo[8].from() = 2
	 * 			 edgeTo[2].to() = 2 and edgeTo[2].from() = 3
	 * 			 edgeTo[3].to() = 3 and edgeTo[3].from() = 0	
 	 */
    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    
    /**
     * Variable representing a priority queue of vertices.
     */
    private IndexMinPQ<Double> pq;

    /**
     * Initializes a new Dijkstra Shortest Path with a given digraph and a vertex representing the starting point.
     * 
     * @param	G
     * 			The edge-weighted digraph that represents the gameboard.
     * @param	s
     * 			An integer that represents the starting point.
     */
    public DijkstraSP(EdgeWeightedDigraph G, int s)
    {
        // G.V() the number of vertices (nodes) in the edge-weighted digraph
    	distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        
        // initialize all distances to infinity, except the distance to the starting point (this is set to zero)
        for (int v = 0; v < G.V(); v++)
        {
        	distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty())
        {
            // deletes a minimal key (the position that is closest to the starting point s) 
        	// from the IndexMinPQ and returns its associated index.
        	int v = pq.delMin();

        	// each edge describes a way to get to a certain end point;
        	// the best way to get to this end point is stored in distTo[] and edgeTo[].
        	// this is done for each point in the board.
            for (DirectedEdge e : G.adj(v))
            {
            	relax(e);
            }
        }

        // check optimality conditions
        assert check(G, s);
    }

    /**
     * relax edge e and update pq if changed
     * 
     * If a new, better path to the end point of the given edge is found,
     * the path to this end point is changed into this new path.
     * 
     * @param	e
     * 			The edge ('arrow') from which the end point is compared.
     */
    private void relax(DirectedEdge e)
    {
        int v = e.from();
        int w = e.to();
        
        // the given edge leads to a better way to get to the end point than the way that is stored now.
        if (distTo[w] > distTo[v] + e.weight())
        {
            // the new distance to the point w is the distance to get to the starting point from the edge plus the cost to cross the edge
        	distTo[w] = distTo[v] + e.weight();
        	// the new way to get to w is via v, so this edge is stored for w.
            edgeTo[w] = e;
            if (pq.contains(w))
            {
            	pq.change(w, distTo[w]);
            }
            else
            {
            	pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the length of the shortest path from s to v
     * 
     * @param	v
     * 			An integer that represents the position to calculate the distance to.
     * @return	...
     * 			| distTo[v]
     */
    public double distTo(int v)
    {
        return distTo[v];
    }

    /**
     * Checks whether there is a path from s to v.
     * 
     * @param	v
     * 			An integer that represents the position to check the way to.
     * @return	...
     * 			| distTo[v] < Double.POSITIVE_INFINITY
     */
    public boolean hasPathTo(int v)
    {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the shortest path from s to v as an Iterable.
     * Returns null if no such path is found.
     * 
     * @param	v
     * 			An integer that represents the position to calculate the shortest path to.
     * @return	...
     * 			| if (!hasPathTo(v)) then result == null
     * @return	...
     * 			| 
     */
    public Iterable<DirectedEdge> pathTo(int v)
    {
        if (!hasPathTo(v))
        {
        	return null;
        }
        
        // stack: LIFO; that's why you start calculating from the end point v.
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        
        // start from the point v, get the edge that has v as an ending point and take its starting point as the next point
        // each point is added to a list of edges referring to the positions forming the path from the starting point to the point v.
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
        {
        	path.push(e);
        }
        return path;
    }

    /**
     * check optimality conditions:
     * (i)  for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
     * (ii) for all edge e on the SPT:  distTo[e.to()] == distTo[e.from()] + e.weight()
     * 
     * @param	G
     * 			The given EdgeWeightedDigraph
     * @param	s
     * 			An integer representing the starting point
     * @return	True if the edge weights are nonnegative,
     * 					the distance to the end point of an edge in edge[] is equal to the distance to its starting point, plus the weight of the edge and
     * 					distTo[v] and edgeTo[v] are consistent with each other.
     * 			| for each edge in edge[]:
     * 			|	edge.weight() >= 0
     *          |   && distTo[edge.to()] == distTo[edge.from()] + edge.weight()
     *          |   && ! (distTo[s] != 0.0 || edgeTo[s] != null)
     * 			|   && ! (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY )
     * @return	True if the distance to the end point of an edge is equal to (or less than) the distance to its starting point, plus the weight of the edge.
     * 			| for each edge in G.edges():
     * 			|   distTo[edge.to()] <= distTo[edge.from()] + edge.weight()
     */
    private boolean check(EdgeWeightedDigraph G, int s)
    {
        // check that edge weights are nonnegative
        for (DirectedEdge e : G.edges())
        {
            if (e.weight() < 0)
            {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null)
        {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++)
        {
            if (v == s)
            {
            	continue;
            }
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY)
            {
            	System.err.println("distTo[] and edgeTo[] inconsistent");
            	return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++)
        {
            for (DirectedEdge e : G.adj(v))
            {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w])
                {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++)
        {
        	if (edgeTo[w] == null)
        	{
        		continue;
        	}
        	DirectedEdge e = edgeTo[w];
        	int v = e.from();
        	if (w != e.to())
        	{
        		return false;
        	}
            if (distTo[v] + e.weight() != distTo[w])
            {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }
}
