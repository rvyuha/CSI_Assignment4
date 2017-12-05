import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
/**
 * 
 */

/**
 * @author sizum
 *
 */
public class ParisMetro {

private static final int WALKTIME =90;
private static String[] stationNames;
private static EdgeWeightedDigraph metro;
private static Integer[][] metroMatrix;
private static Integer[] stats;
 	
	protected static void readMetro(String fileName) throws Exception, IOException {
		BufferedReader graphFile = new BufferedReader(new FileReader(fileName));
		//REad first line
		String fLine = graphFile.readLine();
		StringTokenizer sf = new StringTokenizer(fLine);
		Integer v = new Integer(sf.nextToken());
		Integer e = new Integer(sf.nextToken());
		metroMatrix = new Integer[v][v];
		stats = new Integer[v];
		metro = new EdgeWeightedDigraph(v,e);
		stationNames = new String[v];
		//read station names
		String sLine;
		int ind=0;
		while(!((sLine = graphFile.readLine()).equals("$"))){
			StringTokenizer ss = new StringTokenizer(sLine);
			if (ss.countTokens()<2) {
				throw new IOException("Incorrect input at line"+ sLine);
			}
			Integer waste = new Integer(ss.nextToken());
			String name="";
			while(ss.hasMoreTokens()){
				String n = ss.nextToken();
				name+=n;
			}
			stationNames[ind]=name;
			stats[ind]=0;
			ind++;
		}
		//read direactional edges
		String line;
		while ((line = graphFile.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(line);
			if (st.countTokens()!=3) {
				throw new IOException("Incorrect input file at line" + line);
			}
			Integer source = new Integer(st.nextToken());
			Integer dest = new Integer(st.nextToken());
			Integer weight = new Integer(st.nextToken());
			if (weight==-1) {
				weight= WALKTIME;
			}
			DirectedEdge ed = new DirectedEdge(source,dest,weight);
			metroMatrix[source][dest]=weight;
			metro.addEdge(ed);
		}
		//System.out.println(metro);

	}
	public static void shortPath(int s, int e){
		DijkstraSP algo = new DijkstraSP(metro,s);
		System.out.println(algo.pathTo(e));
		System.out.println(algo.distTo(e));
	}

	public static String ajacencyMatrix (int v,int p){
		String ret ="";
		for (int i=0;i<metroMatrix.length ;i++ ) {
			if (metroMatrix[v][i]==null||metroMatrix[v][i]==90||i==p||stats[i]==1) {
				if (i==p) {
					
				//System.out.println("i: "+i+", P "+p);
				}
				continue;
			}
			else {
				ret+=i+" ";
				stats[i]=1;
				//System.out.println(i);
				ret+=ajacencyMatrix(i,v);
			}
		}
		return ret;
	}
	public static void retArray(){
		for (int i=0;i<metroMatrix.length ;i++ ) {
			stats[i]=0;
		}
	}

	public static void disablePath(int s){
		ajacencyMatrix(s,s);
		stats[s]=1;
		for (int i=0;i<stats.length ;i++ ) {
			if (stats[i]==1) {
				for (DirectedEdge e: metro.adj(i) ) {
					if (e.weight!=90) {
						e.weight=99999;
						//System.out.println("meow");
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		try{
			readMetro("metro.txt");
		}catch (Exception except){
			System.err.println(except);
			except.printStackTrace();
		}
		if (args.length<1) {
			System.err.println("Usage: java WeightGraph file name");
			System.exit(-1);
		}
		else if (args.length==1) {
			System.out.print(args[0]+" ");
			System.out.print(ajacencyMatrix(Integer.parseInt(args[0]),Integer.parseInt(args[0])));
		}
		else if (args.length==2) {
			shortPath(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		}
		else if (args.length==3) {
			disablePath(Integer.parseInt(args[2]));
			shortPath(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
		}
		//shortPath(0,50);
		//disablePath(77);
		//shortPath(0,50);
		//testing shortest path
		/*
		for (int i=1;i<metro.V() ;i++ ) {
			
		shortPath(i,0);
		}
		*/
		//testing line finding
		/*
		for (int i=0;i<metroMatrix.length ;i+=2 ) {
			//System.out.println(i);
			System.out.println(ajacencyMatrix(i,i ));
			retArray();
		}
		*/
//108 152 277 82 87 300 341 169 63 123 250 44 162 59 224 282 228 255 71 254 345 149 241 49 164 244 352 184 161 364 365 363 260 266 261 237 179
	//54 11 154 349 99 358 346 174 221 74 195 47 148 117 373
	//196 259 36 37 37 198 52 201 145 54 11 154 349 99 358 346 174 221 74 195 47 148 117
	}

	//
	//
}
		
