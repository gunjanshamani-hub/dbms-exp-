import java.util.*;

class Node {
    ArrayList<Integer> keys = new ArrayList<>();
    ArrayList<Node> child = new ArrayList<>();
    boolean leaf = true;
}

public class BTree {
    Node root;
    int order;

    BTree(int order) {
        this.order = order;
        root = new Node();
    }

    void insert(int key) {
        if (root.keys.size() == order - 1) {
            Node newRoot = new Node();
            newRoot.leaf = false;
            newRoot.child.add(root);
            split(newRoot, 0);
            root = newRoot;
        }
        insertNonFull(root, key);
    }

    void insertNonFull(Node node, int key) {
        int i = node.keys.size() - 1;

        if (node.leaf) {
            node.keys.add(0);
            while (i >= 0 && node.keys.get(i) > key) {
                node.keys.set(i + 1, node.keys.get(i));
                i--;
            }
            node.keys.set(i + 1, key);
        } else {
            while (i >= 0 && key < node.keys.get(i)) {
    i--;
}
            i++;

            if (node.child.get(i).keys.size() == order - 1) {
                split(node, i);
                if (key > node.keys.get(i))
                    i++;
            }
            insertNonFull(node.child.get(i), key);
        }
    }

    void split(Node parent, int index) {
        Node left = parent.child.get(index);
        Node right = new Node();

        int mid = left.keys.size() / 2;
        int median = left.keys.get(mid);

        for (int j = mid + 1; j < left.keys.size(); j++)
            right.keys.add(left.keys.get(j));

        if (!left.leaf) {
            for (int j = mid + 1; j < left.child.size(); j++)
                right.child.add(left.child.get(j));
            right.leaf = false;
        }

        while (left.keys.size() > mid)
            left.keys.remove(left.keys.size() - 1);

        if (!left.leaf) {
            while (left.child.size() > mid + 1)
                left.child.remove(left.child.size() - 1);
        }

        parent.keys.add(index, median);
        parent.child.add(index + 1, right);
    }

    void delete(int key) {
        deleteKey(root, key);

        if (root.keys.isEmpty() && !root.leaf)
            root = root.child.get(0);
    }

    void deleteKey(Node node, int key) {
        int index = 0;

        while (index < node.keys.size() && node.keys.get(index) < key)
            index++;

        if (index < node.keys.size() && node.keys.get(index) == key) {
            if (node.leaf) {
                node.keys.remove(index);
            } else {
                deleteInternal(node, index);
            }
        } else if (!node.leaf) {
            deleteFromChild(node, index, key);
        }
    }

    void deleteInternal(Node node, int index) {
        int key = node.keys.get(index);
        Node left = node.child.get(index);
        Node right = node.child.get(index + 1);

        if (left.keys.size() > 1) {
            int predecessor = left.keys.get(left.keys.size() - 1);
            node.keys.set(index, predecessor);
            deleteKey(left, predecessor);
        } else if (right.keys.size() > 1) {
            int successor = right.keys.get(0);
            node.keys.set(index, successor);
            deleteKey(right, successor);
        } else {
            left.keys.add(key);
            left.keys.addAll(right.keys);
            if (!left.leaf)
                left.child.addAll(right.child);

            node.keys.remove(index);
            node.child.remove(index + 1);
            deleteKey(left, key);
        }
    }

    void deleteFromChild(Node node, int index, int key) {
        Node child = node.child.get(index);

        if (child.keys.size() == 1) {
            if (index > 0 && node.child.get(index - 1).keys.size() > 1) {
                borrowLeft(node, index);
            } else if (index < node.child.size() - 1
                    && node.child.get(index + 1).keys.size() > 1) {
                borrowRight(node, index);
            } else {
                if (index < node.child.size() - 1)
                    merge(node, index);
                else {
                    merge(node, index - 1);
                    index--;
                }
            }
        }

        deleteKey(node.child.get(index), key);
    }

    void borrowLeft(Node parent, int index) {
        Node child = parent.child.get(index);
        Node left = parent.child.get(index - 1);

        child.keys.add(0, parent.keys.get(index - 1));
        parent.keys.set(index - 1, left.keys.remove(left.keys.size() - 1));

        if (!left.leaf)
            child.child.add(0, left.child.remove(left.child.size() - 1));
    }

    void borrowRight(Node parent, int index) {
        Node child = parent.child.get(index);
        Node right = parent.child.get(index + 1);

        child.keys.add(parent.keys.get(index));
        parent.keys.set(index, right.keys.remove(0));

        if (!right.leaf)
            child.child.add(right.child.remove(0));
    }

    void merge(Node parent, int index) {
        Node left = parent.child.get(index);
        Node right = parent.child.get(index + 1);

        left.keys.add(parent.keys.remove(index));
        left.keys.addAll(right.keys);

        if (!left.leaf)
            left.child.addAll(right.child);

        parent.child.remove(index + 1);
    }

    void display(Node node) {
        System.out.print(node.keys + " ");

        if (!node.leaf) {
            for (Node child : node.child)
                display(child);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter order of B-Tree: ");
        int order = sc.nextInt();

        BTree tree = new BTree(order);

        System.out.print("Enter number of keys: ");
        int n = sc.nextInt();

        System.out.println("Enter keys:");
        for (int i = 0; i < n; i++)
            tree.insert(sc.nextInt());

        System.out.print("B-Tree: ");
        tree.display(tree.root);

        System.out.print("\nEnter key to delete: ");
        int key = sc.nextInt();

        tree.delete(key);

        System.out.print("After deletion: ");
        tree.display(tree.root);

        System.out.println("\nTime Complexity: O(log n)");
        System.out.println("Space Complexity: O(n)");

        sc.close();
    }
}