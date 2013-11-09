import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 05.11.13
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class WordNet {
    private int[] id;
    private String[] syn;
    private Digraph digraph;
    private int N = 0;

    private final HashMap<String, Bag<Integer>> nounToId = new HashMap<String, Bag<Integer>>();


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        // input streams
        In inSynsets = new In(synsets);
        In inHypernyms = new In(hypernyms);

        // queue used to save symsets
        Queue<String> queue = new Queue<String>();

        int i = 0;
        String[] fields;
        String s;

        // handle first input stream
        while ((s = inSynsets.readLine()) != null) {
            queue.enqueue(s);
        }

        N = queue.size();
        syn = new String[N];
        id = new int[N];

        for(String str: queue){
            fields = str.split(",");
            id[i] = Integer.parseInt(fields[0]);
            syn[i] = fields[1];
            i++;
        }

        // create new digraph
        digraph = new Digraph(N);

        // handle second input stream
        while ( (s = inHypernyms.readLine()) != null) {
            fields = s.split(",");
            int n = fields.length;
            for (int j = 1; j < n; j++){
                digraph.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[j]));
            }
        }
        // Check if input represents rotted DAG
        this.ckeckIfRooted(digraph);
        this.hasCycle(digraph);


        // create nouns set
        this.nouns();
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns(){
        String syns[];
        Bag<Integer> bag;

        for (int i = 0; i < syn.length; i++){
            syns = syn[i].split(" ");
            for(String noun: syns){
                bag = nounToId.get(noun);

                if (bag == null) {
                    bag = new Bag<Integer>();
                    bag.add(i);
                    nounToId.put(noun, bag);
                }
                else {
                    bag.add(i);
                }

            }
        }

        return nounToId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        return nounToId.keySet().contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if ((!isNoun(nounA)) || (!isNoun(nounB))) throw new IllegalArgumentException();

        SAP sap = new SAP(digraph);

        return sap.length(nounToId.get(nounA),nounToId.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if ((!isNoun(nounA)) || (!isNoun(nounB))) throw new IllegalArgumentException();

        SAP sap = new SAP(digraph);

        int anc = sap.ancestor(nounToId.get(nounA),nounToId.get(nounB));

        return syn[anc];
    }

    private void ckeckIfRooted(Digraph G){
        int rootsNum=0;
        for (int i = 0; i < G.V(); i++) {
            if(!G.adj(i).iterator().hasNext())
                rootsNum++;
        }
        if (rootsNum != 1) throw  new IllegalArgumentException("Digraph is not rooted");
    }

    private void hasCycle(Digraph G) {
        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle()) throw new IllegalArgumentException();
    }

    // for unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0],args[1]);
        StdOut.println(wordNet.sap(args[3], args[4]) + " " + wordNet.distance(args[3], args[4]));

    }

}
