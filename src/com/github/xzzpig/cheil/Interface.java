package com.github.xzzpig.cheil;

import com.github.xzzpig.cheil.Chessboard.SIDE;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 9042416528191430122L;
    private JPanel contentPane;
    private JTable table_cheil;
    private JLabel Label_Score;
    private JButton btnNewButton;

    /**
     * Create the frame.
     */
    public Interface() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 38) {
                    Chessboard.getInstance().move(SIDE.UP);
                } else if (e.getKeyCode() == 40) {
                    Chessboard.getInstance().move(SIDE.DOWN);
                } else if (e.getKeyCode() == 37) {
                    Chessboard.getInstance().move(SIDE.LEFT);
                } else if (e.getKeyCode() == 39) {
                    Chessboard.getInstance().move(SIDE.RIGHT);
                }
                upDate();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {
                upDate();
                table_cheil.setFont(new Font("宋体", 0, (table_cheil.getWidth()) * 32 / (100 * Chessboard.getInstance().size)));
            }
        });
        setTitle("2048");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 570);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        table_cheil = new JTable() {
            /**
             *
             */
            private static final long serialVersionUID = 8616833897432519221L;

            @Override
            public Component prepareRenderer(TableCellRenderer arg0, int arg1, int arg2) {
                Component c = super.prepareRenderer(arg0, arg1, arg2);
                c.setFont(new Font("Segoe Script", Font.BOLD, 32));
                if (c instanceof JComponent) {
                    JComponent com = ((JComponent) c);
                    //com.setOpaque(false);
                    try {
                        if (table_cheil.getValueAt(arg1, arg2) == null) {
                            com.setBackground(Color.WHITE);
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 2) {
                            com.setBackground(new Color(0xede5da));
                            c.setForeground(new Color(0x000000));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 4) {
                            com.setBackground(new Color(0xede0c8));
                            c.setForeground(new Color(0x000000));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 8) {
                            com.setBackground(new Color(0xf3b079));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 16) {
                            com.setBackground(new Color(0xf59563));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 32) {
                            com.setBackground(new Color(0xf57c5f));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 64) {
                            com.setBackground(new Color(0xf75e3e));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 128) {
                            com.setBackground(new Color(0xedce71));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 256) {
                            com.setBackground(new Color(0xeccb60));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 512) {
                            com.setBackground(new Color(0xebc74f));
                            c.setForeground(new Color(0xffffff));
                        } else if ((int) table_cheil.getValueAt(arg1, arg2) == 1024) {
                            com.setBackground(new Color(0xecc440));
                            c.setForeground(new Color(0xffffff));
                        } else {
                            com.setBackground(new Color(0xedc02f));
                            c.setForeground(new Color(0xffffff));
                        }
                    } catch (Exception e) {
                    }
                }
                return c;
            }
        };
        table_cheil.addMouseListener(new MouseAdapter() {
            private int x, y;

            @Override
            public void mousePressed(MouseEvent arg0) {
                x = arg0.getX();
                y = arg0.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int dx = e.getX() - x, dy = e.getY() - y;
                int ax = Math.abs(dx), ay = Math.abs(dy);
                if (dx < 2 && dy < 2)
                    return;
                if (ax > ay) {
                    if (dx > 0) {
                        Chessboard.getInstance().move(SIDE.RIGHT);

                    } else {
                        Chessboard.getInstance().move(SIDE.LEFT);
                    }
                } else {
                    if (dy > 0) {
                        Chessboard.getInstance().move(SIDE.DOWN);

                    } else {
                        Chessboard.getInstance().move(SIDE.UP);
                    }
                }
                upDate();
            }
        });
        table_cheil.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 38) {
                    Chessboard.getInstance().move(SIDE.UP);
                } else if (e.getKeyCode() == 40) {
                    Chessboard.getInstance().move(SIDE.DOWN);
                } else if (e.getKeyCode() == 37) {
                    Chessboard.getInstance().move(SIDE.LEFT);
                } else if (e.getKeyCode() == 39) {
                    Chessboard.getInstance().move(SIDE.RIGHT);
                }
                upDate();
            }
        });
        table_cheil.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        table_cheil.setOpaque(false);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        table_cheil.setDefaultRenderer(Object.class, renderer);
        String[] title = new String[Chessboard.getInstance().size];
        table_cheil
                .setModel(new DefaultTableModel(new Object[Chessboard.getInstance().size][Chessboard.getInstance().size], title));
        upDate();
        contentPane.setLayout(new MigLayout("", "[468px,grow][right]", "[30.00][][434px,grow]"));

        Label_Score = new JLabel("\u5206\u6570:0");
        Label_Score.setOpaque(false);
        Label_Score.setFont(new Font("宋体", Font.PLAIN, 28));
        contentPane.add(Label_Score, "flowx,cell 0 0");

        btnNewButton = new JButton("\u91CD\u7F6E");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String size_s = JOptionPane.showInputDialog(Interface.this, "棋盘大小", "2048", TrayIcon.MessageType.INFO.ordinal());
                int size = 4;
                try {
                    size = Integer.parseInt(size_s);
                } catch (Exception e2) {
                }
                new Chessboard(size);
                String[] title = new String[Chessboard.getInstance().size];
                table_cheil.setModel(new DefaultTableModel(new Object[size][size], title));
                table_cheil.setFont(new Font("宋体", 0, (table_cheil.getWidth()) * 32 / (100 * Chessboard.getInstance().size)));
                upDate();
            }
        });
        btnNewButton.setRequestFocusEnabled(false);
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 18));
        contentPane.add(btnNewButton, "cell 1 0,growy");
        table_cheil.setEnabled(false);
        contentPane.add(table_cheil, "cell 0 2 2 1,grow");
        btnNewButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 38) {
                    Chessboard.getInstance().move(SIDE.UP);
                } else if (e.getKeyCode() == 40) {
                    Chessboard.getInstance().move(SIDE.DOWN);
                } else if (e.getKeyCode() == 37) {
                    Chessboard.getInstance().move(SIDE.LEFT);
                } else if (e.getKeyCode() == 39) {
                    Chessboard.getInstance().move(SIDE.RIGHT);
                }
                upDate();
            }
        });
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface frame = new Interface();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void upDate() {
        for (int i = 0; i < Chessboard.getInstance().size; i++) {
            for (int j = 0; j < Chessboard.getInstance().size; j++) {
                int data = Chessboard.getInstance().getData()[i][j];
                if (data != 0)
                    table_cheil.getModel().setValueAt(data, j, i);
                else
                    table_cheil.getModel().setValueAt(null, j, i);
            }
        }
        try {
            table_cheil.setRowHeight(table_cheil.getWidth() / table_cheil.getRowCount());
            Label_Score.setText("分数:" + Chessboard.getInstance().getScore());
            if (Chessboard.getInstance().isGameOver()) {
                JOptionPane.showMessageDialog(this.getContentPane(), "游戏结束\n分数:" + Chessboard.getInstance().getScore());
                new Chessboard();
                upDate();
            } else if (Chessboard.getInstance().isWin()) {
                JOptionPane.showMessageDialog(this.getContentPane(), "游戏胜利！\n分数:" + Chessboard.getInstance().getScore());
                new Chessboard();
                upDate();
            }
        } catch (Exception e) {
        }
    }
}
