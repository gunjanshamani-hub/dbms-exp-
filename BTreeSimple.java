import java.util.*;

public class BTreeSimple {

    class Node {
        ArrayList<Integer> keys = new ArrayList<>();
        ArrayList<Node> child = new ArrayList<>();
    }

    class Split {
        int key;
        Node right;

        Split(int key, Node right) {
            this.key = key;
            this.right = right;
        }
    }

    Node root = new Node();
    int order;

    BTreeSimple(int order) {
        this.order = order;
    }

    Split insert(Node node, int key) {
        int i = 0;

        while (i < node.keys.size() && key > node.keys.get(i)
            ) {
            i++;
        }

        if (i < node.keys.size() && key == node.keys.get(i)) {
            return null;
        }

        if (node.child.isEmpty()) {
            node.keys.add(i, key);
        } else {
            Split s = insert(node.child.get(i), key);

            if (s != null) {
                node.keys.add(i, s.key);
                node.child.add(i + 1, s.right);
            }
        }

        if (node.keys.size() >= order) {
            int mid = node.keys.size() / 2;
            int median = node.keys.get(mid);
            Node right = new Node();

            for (int j = mid + 1; j < node.keys.size(); j++) {
                right.keys.add(node.keys.get(j));
            }

            while (node.keys.size() > mid) {
                node.keys.remove(node.keys.size() - 1);
            }

            if (!node.child.isEmpty()) {
                for (int j = mid + 1; j < node.child.size(); j++) {
                    right.child.add(node.child.get(j));
                }

                while (node.child.size() > mid + 1) {
                    node.child.remove(node.child.size() - 1);
                }
            }

            return new Split(median, right);
        }

        return null;
    }

    void add(int key) {
        Split s = insert(root, key);

        if (s != null) {
            Node newRoot = new Node();
            newRoot.keys.add(s.key);
            newRoot.child.add(root);
            newRoot.child.add(s.right);
            root = newRoot;
        }
    }

    void collect(Node node, ArrayList<Integer> list) {
        for (int i = 0; i < node.keys.size(); i++) {
            if (!node.child.isEmpty()) {
                collect(node.child.get(i), list);
            }

            list.add(node.keys.get(i));
        }

        if (!node.child.isEmpty()) {
            collect(node.child.get(node.keys.size()), list);
        }
    }

    void delete(int key) {
        ArrayList<Integer> list = new ArrayList<>();
        collect(root, list);

        if (!list.remove(Integer.valueOf(key))) {
            System.out.println("Key not found.");
            return;
        }

        root = new Node();

        for (int k : list) {
            add(k);
        }
    }

    void display(Node node) {
        System.out.print(node.keys + " ");

        for (Node c : node.child) {
            display(c);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter B-Tree order (minimum 3): ");
        int order = sc.nextInt();

        if (order < 3) {
            System.out.println("Order must be at least 3.");
            return;
        }

        BTreeSimple tree = new BTreeSimple(order);

        System.out.print("Enter number of keys: ");
        int n = sc.nextInt();

        System.out.println("Enter keys:");

        for (int i = 0; i < n; i++) {
            tree.add(sc.nextInt());
        }

        System.out.println("B-Tree after insertion:");
        tree.display(tree.root);
        System.out.println();

        System.out.print("Enter key to delete: ");
        int key = sc.nextInt();

        tree.delete(key);

        System.out.println("B-Tree after deletion:");
        tree.display(tree.root);
        System.out.println();

        System.out.println("Time Complexity: Insertion O(log n)");
        System.out.println("Deletion O(n log n)");
        System.out.println("Space Complexity: O(n)");
    }
}