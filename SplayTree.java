import javax.swing.tree.TreeNode;

public class SplayTree {
    public Node root;
    /*
     * The functions below this line was given
     */

    @Override
    public String toString() {
        return (root == null ? "Empty Tree" : toString(root, "", true));
    }

    public String toString(Node node, String prefix, boolean end) {
        String res = "";
        if (node.right != null) {
            res += toString(node.right, prefix + (end ? "│   " : "    "), false);
        }
        res += prefix + (end ? "└── " : "┌── ") + node.toString() + "\n";
        if (node.left != null) {
            res += toString(node.left, prefix + (end ? "    " : "│   "), true);
        }
        return res;
    }

    public String toStringOneLine() {
        return (root == null ? "Empty Tree" : "{" + toStringOneLine(root) + "}");
    }

    public String toStringOneLine(Node node) {
        return node.toString()
                + (node.left == null ? "{}" : "{" + toStringOneLine(node.left) + "}")
                + (node.right == null ? "{}" : "{" + toStringOneLine(node.right) + "}");
    }

    public SplayTree() {
        root = null;
    }

    /*
     * The functions above this line was given
     */
    public SplayTree(String input) {
        if (input.equals("Empty Tree")) {
            root = null;
        } else {
            root = constructTree(input);
        }
    }

    private Node constructTree(String input) {
        if (input.equals("{}")) {
            return null;
        } else {
            int indexOfBracket = input.indexOf(']');
            String currentNodeString = input.substring(1, indexOfBracket + 1);
            String remainingString = input.substring(indexOfBracket + 1, input.length() - 1);

            int leftBracketClose = findMatchingBracket(remainingString);
            String leftNodeString = remainingString.substring(0, leftBracketClose + 1);
            String rightNodeString = remainingString.substring(leftBracketClose + 1);

            Node currentNode = createNode(currentNodeString);
            currentNode.left = constructTree(leftNodeString);
            currentNode.right = constructTree(rightNodeString);

            return currentNode;
        }
    }

    private int findMatchingBracket(String input) {
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '{') {
                count++;
            } else if (c == '}') {
                count--;
            }
            if (count == 0) {
                return i;
            }
        }
        return -1;
    }

    private Node createNode(String input) {
        if (!input.contains("[")) {
            return null;
        } else {
            input = input.replaceAll("[\\[\\]u%]", ""); // Removing unwanted characters
            String[] splitString = input.split(":");
            int studentNumber = Integer.parseInt(splitString[0]);
            int mark = Integer.parseInt(splitString[1]);
            return new Node(studentNumber, mark);
        }
    }
    
    public Node access(int studentNumber) {
        Node node = root;
        node = access(studentNumber, null);
        return node;
    }

    public Node access(int studentNumber, Integer mark) {
        Node node = root;
        node = splay(node, studentNumber);
        if (node.studentNumber == studentNumber) {
            if (mark != null) {
                node.mark = mark;
            }
        }
        return node;
    }

    private Node splay(Node node, int studentNumber) {//helper function
        if (node == null || node.studentNumber == studentNumber) {
            return node;
        }
        
        if (studentNumber < node.studentNumber) {
            if (node.left == null) {
                return node;
            }
            if (studentNumber < node.left.studentNumber) {
                node.left.left = splay(node.left.left, studentNumber);
                node = rotateRight(node);
            } else if (studentNumber > node.left.studentNumber) {
                node.left.right = splay(node.left.right, studentNumber);
                if (node.left.right != null) {
                    node.left = rotateLeft(node.left);
                }
            }
                return (node.left == null) ? node : rotateRight(node);
            
    }
    else {
        if (node.right == null) {
            return node;
        }
        if (studentNumber < node.right.studentNumber) {
            node.right.left = splay(node.right.left, studentNumber);
            if (node.right.left != null) {
                node.right = rotateRight(node.right);
            }
        } else if (studentNumber > node.right.studentNumber) {
            node.right.right = splay(node.right.right, studentNumber);
            node = rotateLeft(node);
        }
        return (node.right == null) ? node : rotateLeft(node);
    }
}
        

    private Node rotateRight(Node thisNode) {//helper to my helper function
        Node temp = thisNode.left;
        thisNode.left = temp.right;
        temp.right = thisNode;
        return temp;
    }

    private Node rotateLeft(Node thisNode) {//helper to my helper function
        Node temp = thisNode.right;
        thisNode.right = temp.left;
        temp.left = thisNode;
        return temp;
    }

    public Node remove(int studentNumber) {
        root = removeNode(studentNumber, root);
        return root;
    }

    private Node removeNode(int studentNumber, Node node) {
        if (node == null) {
            return null;
        }

        if (studentNumber < node.studentNumber) {
            node.left = removeNode(studentNumber, node.left);
        }
        else if (studentNumber > node.studentNumber) {
            node.right = removeNode(studentNumber, node.right);
        }
        else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            Node minRight = findMin(node.right);

            node.studentNumber = minRight.studentNumber;

            node.right = removeNode(minRight.studentNumber, node.right);
        }

        return node;
    }

    private Node findMin(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return findMin(node.left);
        }
    }

    public String sortByStudentNumber() {
    }

    public String sortByMark() {
    }
    
    public String treeToString(Node root) {
        if (root == null)
            return "{}";
        String leftStr = treeToString(root.left);
        String rightStr = treeToString(root.right);
        return "{[u" + root.studentNumber + ":" + root.mark + "%]" + leftStr + rightStr + "}";
    }
}
