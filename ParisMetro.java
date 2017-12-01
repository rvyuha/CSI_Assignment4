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
	
	protected static void readMetro(String fileName) throws Exception, IOException {
		BufferedReader graphFile = new BufferedReader(new FileReader(fileName));
		//REad first line
		String fLine = graphFile.readLine();
		StringTokenizer sf = new StringTokenizer(fLine);
		Integer v = new Integer(sf.nextToken());
		Integer e = new Integer(sf.nextToken());
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
			metro.addEdge(ed);
		}
		System.out.println(metro);

	}
	public static void shortPath(int s, int e){
		DijkstraSP algo = new DijkstraSP(metro,s);
		System.out.println(algo.pathTo(e));
		System.out.println(algo.distTo(e));
	}

	public static void main(String[] args) {
		if (args.length<1) {
			System.err.println("Usage: java WeightGraph file name");
			System.exit(-1);
		}
		try{
			readMetro(args[0]);
		}catch (Exception except){
			System.err.println(except);
			except.printStackTrace();
		}
		shortPath(0,50);

	}

	//
	//
}
		
