import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

class TreeNode {
    int value;
    TreeNode left, right;

    TreeNode(int value) {
        this.value = value;
        left = right = null;
    }
}

class BST {
    TreeNode root;

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode(value);
            return root;
        }

        if (value < root.value) {
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            root.right = insertRec(root.right, value);
        }

        return root;
    }

    public void remove(int value) {
        root = removeRec(root, value);
    }

    private TreeNode removeRec(TreeNode root, int value) {
        if (root == null) return root;

        if (value < root.value) {
            root.left = removeRec(root.left, value);
        } else if (value > root.value) {
            root.right = removeRec(root.right, value);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.value = minValue(root.right);
            root.right = removeRec(root.right, root.value);
        }

        return root;
    }

    private int minValue(TreeNode root) {
        int minValue = root.value;
        while (root.left != null) {
            minValue = root.left.value;
            root = root.left;
        }
        return minValue;
    }

    public TreeNode getRoot() {
        return root;
    }
}

class TreePanel extends JPanel {
    private TreeNode root;
    private double scale = 1.0;
    private int xOffset = 0, yOffset = 0;
    private Point2D lastMousePoint;

    public TreePanel(TreeNode root) {
        this.root = root;
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double scaleFactor = 1.1;
                if (e.getWheelRotation() > 0) {
                    scale /= scaleFactor;
                } else {
                    scale *= scaleFactor;
                }
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMousePoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point2D currentMousePoint = e.getPoint();
                xOffset += (currentMousePoint.getX() - lastMousePoint.getX());
                yOffset += (currentMousePoint.getY() - lastMousePoint.getY());
                lastMousePoint = currentMousePoint;
                repaint();
            }
        });
    }

    public void setRoot(TreeNode root) {
        this.root = root;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        g2d.translate(xOffset, yOffset);
        if (root != null) {
            int depth = calculateDepth(root);
            drawTree(g2d, root, getWidth() / 2, 40, Math.max((int) Math.pow(2, depth) * 5, 20), 100);
        }
    }

    private int calculateDepth(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateDepth(node.left), calculateDepth(node.right));
    }

    private void drawTree(Graphics2D g, TreeNode node, int x, int y, int xOffset, int yOffset) {
        if (node == null) return;

        int nodeSize = 40;
        int lineThickness = 2;
        int fontSize = 17;

        g.setColor(new Color(0, 128, 255));
        g.fillOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(lineThickness));
        g.drawOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);

        g.setColor(Color.WHITE);
        Font boldFont = new Font("Arial", Font.BOLD, fontSize);
        g.setFont(boldFont);
        FontMetrics fm = g.getFontMetrics();
        String text = String.valueOf(node.value);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g.drawString(text, x - textWidth / 2, y + textHeight / 4);

        int adjustedXOffset = Math.max(xOffset / 2, 20);

        if (node.left != null) {
            g.setColor(new Color(50, 50, 50));
            g.setStroke(new BasicStroke(lineThickness));
            g.draw(new Line2D.Double(x, y + nodeSize / 2, x - adjustedXOffset, y + yOffset - nodeSize / 2));
            drawTree(g, node.left, x - adjustedXOffset, y + yOffset, adjustedXOffset, yOffset);
        }

        if (node.right != null) {
            g.setColor(new Color(50, 50, 50));
            g.setStroke(new BasicStroke(lineThickness));
            g.draw(new Line2D.Double(x, y + nodeSize / 2, x + adjustedXOffset, y + yOffset - nodeSize / 2));
            drawTree(g, node.right, x + adjustedXOffset, y + yOffset, adjustedXOffset, yOffset);
        }
    }
}

public class BST_Visualizer {
    public static void main(String[] args) {
        BST tree = new BST();

        // int[] values = {
        //     50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85,
        //     5, 15, 23, 27, 33, 37, 43, 47, 53, 57, 63, 67, 73, 77, 83, 87,
        //     3, 7, 13, 17, 22, 24, 26, 28, 32, 34, 36, 38, 42, 44, 46, 48,
        //     52, 54, 56, 58, 62, 64, 66, 68, 72, 74, 76, 78, 82, 84, 86, 88, 0
        // };
        // for (int value : values) {
        //     tree.insert(value);
        // }

        JFrame frame = new JFrame("BST Visualizer");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreePanel treePanel = new TreePanel(tree.getRoot());
        JScrollPane scrollPane = new JScrollPane(treePanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(240, 248, 255));

        JTextField insertField = new JTextField(10);
        JButton insertButton = new JButton("Insert");
        insertButton.setBackground(new Color(60, 179, 113));
        insertButton.setForeground(Color.WHITE);

        JTextField removeField = new JTextField(10);
        JButton removeButton = new JButton("Remove");
        removeButton.setBackground(new Color(220, 20, 60));
        removeButton.setForeground(Color.WHITE);

        insertButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(insertField.getText());
                tree.insert(value);
                treePanel.setRoot(tree.getRoot());
                insertField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid integer.");
            }
        });

        removeButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(removeField.getText());
                tree.remove(value);
                treePanel.setRoot(tree.getRoot());
                removeField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid integer.");
            }
        });

        insertField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    insertButton.doClick();
                }
            }
        });

        removeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    removeButton.doClick();
                }
            }
        });

        controlPanel.add(new JLabel("Insert:"));
        controlPanel.add(insertField);
        controlPanel.add(insertButton);
        controlPanel.add(new JLabel("Remove:"));
        controlPanel.add(removeField);
        controlPanel.add(removeButton);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
