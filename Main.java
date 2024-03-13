public class Main {
    public static void main(String[] args) {
        String inputStr = "{[u10:50%]{[u5:40%]{}{}}{[u15:60%]{}{}}}";
        SplayTree tree = new SplayTree(inputStr);
        System.out.println(tree.toString(tree.root, "", true));
        String inputStr2 = "{[u10:50%]{}{}}";
        SplayTree tree2 = new SplayTree(inputStr2);
        System.out.println(tree2.toString(tree2.root, "", true));
        String inputStr3 = "{[u10:50%]{[u5:40%]{}{}}{}}";
        SplayTree tree3 = new SplayTree(inputStr3);
        System.out.println(tree3.toString(tree3.root, "", true));

        Node accessedNode = tree.access(15);
        System.out.println("Node accessed by student number: " + accessedNode);

        // Access a node by student number and update its mark
        Node accessedNodeWithMark = tree.access(10, 70);
        System.out.println("Node accessed by student number and updated mark: " + accessedNodeWithMark);
        
        Node removedNode = tree.remove(15);
        System.out.println("Node removed: " + removedNode);
        System.out.println(tree.toString(tree.root, "", true));
        Node removedNode2 = tree.remove(10);
        System.out.println("Node removed: " + removedNode2);
        System.out.println(tree.toString(tree.root, "", true));
    }
}