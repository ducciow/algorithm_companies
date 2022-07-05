import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 05, 07, 2022
 * @Description: A message flow gives infos with ids in [1, N], but in random order. Print all infos in such a way: if
 *      the last printed info has id i, then when i+1 comes, print all received infos from i+1. Implement a structure
 *      fulfilling this requirement whose entire process is in O(N).
 * @Note:   Linked List + Two HashMaps:
 *          - One headMap stores the head node of continuous infos.
 *          - One tailMap stores the tail node of continuous infos.
 *          - An integer waitPoint indicating the waiting info to trigger the printing.
 *          - When info i comes, check i-1 in tailMap and i+1 in headMap to merge continuous infos, and print the linked
 *            list with head i if i is the waitPoint.
 *          - Important to remove nodes from maps accordingly.
 */
public class Code011_ReceiveAndPrintOrderLine {

    public static class Node {
        public String info;
        public Node next;

        public Node(String s) {
            info = s;
        }
    }

    public static class MessageBox {
        private HashMap<Integer, Node> headMap;
        private HashMap<Integer, Node> tailMap;
        private int waitPoint;

        public MessageBox() {
            headMap = new HashMap<>();
            tailMap = new HashMap<>();
            waitPoint = 1;
        }

        public void receive(int id, String info) {
            if (id < 1) {
                return;
            }
            Node node = new Node(info);
            headMap.put(id, node);
            tailMap.put(id, node);
            // check head map
            if (headMap.containsKey(id + 1)) {
                node.next = headMap.get(id + 1);
                headMap.remove(id + 1);
                tailMap.remove(id);
            }
            // check tail map
            if (tailMap.containsKey(id - 1)) {
                tailMap.get(id - 1).next = node;
                tailMap.remove(id - 1);
                headMap.remove(id);
            }
            // check if it is the one waiting for
            if (id == waitPoint) {
                printFlow();
            }
        }

        private void printFlow() {
            Node node = headMap.get(waitPoint);
            headMap.remove(waitPoint);
            while (node != null) {
                System.out.print(node.info + " ");
                node = node.next;
                waitPoint++;
            }
            tailMap.remove(waitPoint - 1);
            System.out.println();
        }

    }

    public static void main(String[] args) {
        // MessageBox only receives 1~N
        MessageBox box = new MessageBox();
        box.receive(2, "B"); // - 2
        box.receive(1, "A"); // 1 2 -> print, trigger is 1
        box.receive(4, "D"); // - 4
        box.receive(5, "E"); // - 4 5
        box.receive(7, "G"); // - 4 5 - 7
        box.receive(8, "H"); // - 4 5 - 7 8
        box.receive(6, "F"); // - 4 5 6 7 8
        box.receive(3, "C"); // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(9, "I"); // 9 -> print, trigger is 9
        box.receive(10, "J"); // 10 -> print, trigger is 10
        box.receive(12, "L"); // - 12
        box.receive(13, "M"); // - 12 13
        box.receive(11, "K"); // 11 12 13 -> print, trigger is 11
    }

}
