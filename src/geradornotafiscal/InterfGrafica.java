package geradornotafiscal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

/**
 *
 * @author rafael domingues
 */
public class InterfGrafica extends JFrame implements ActionListener{
    public JScrollPane pimpr;
    public String nomeproduto, txt;
    public float preçoproduto;
    public static float total;
    public int quantidade;
    public JLabel lblnome, lblpreço, lblqnt, lbltot;
    public JTextField nome, preço, qnt, t;
    public JTextArea saida;
    public JButton limpar, confirmar,imprimir,novocliente;
    public JTable jTable1;
    public InterfGrafica(){
        super("Recibos - Drogaria Rede Popular");
        total = 0;
        //painel com dados
        lblnome = new JLabel(" Produto: ");
        nome = new JTextField();
        nome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    preço.requestFocus();
                }
            }
        });
        lblpreço = new JLabel(" Preço: ");
        preço = new JTextField();
        preço.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    qnt.requestFocus();
                }
            }
        });
        lblqnt = new JLabel(" Quantidade: ");
        qnt = new JTextField();
        qnt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    nomeproduto = nome.getText();
                    nomeproduto = nomeproduto.substring(0,1).toUpperCase().concat(nomeproduto.substring(1));
                    preçoproduto = Float.parseFloat(preço.getText().replace(",","."));
                    quantidade = Integer.parseInt(qnt.getText());
                    total = total + (preçoproduto*quantidade);
                    nome.setText("");
                    preço.setText("");
                    qnt.setText("");
                    t.setText(String.valueOf(total));
                    nome.requestFocus();

                    DefaultTableModel dtmProdutos = (DefaultTableModel) jTable1.getModel();
                    Object[] dados = {quantidade,nomeproduto,preçoproduto,
                        (preçoproduto*quantidade)};
                    dtmProdutos.addRow(dados);
                }
            }
        });
        JPanel pGrid = new JPanel(new GridLayout(3,2));
        pGrid.add(lblnome);
        pGrid.add(nome);
        pGrid.add(lblpreço);
        pGrid.add(preço);
        pGrid.add(lblqnt);
        pGrid.add(qnt);
        //painel com botoes
        confirmar = new JButton("Confirmar");
        limpar = new JButton("Limpar");
        imprimir = new JButton("Imprimir");
        novocliente = new JButton("Novo");
        confirmar.addActionListener(this);
        limpar.addActionListener(this);
        imprimir.addActionListener(this);
        novocliente.addActionListener(this);
        JPanel pbotoes = new JPanel(new GridLayout(2,4));
        pbotoes.add(new JLabel(""));
        pbotoes.add(new JLabel(""));
        
        //Painel valor total
        lbltot = new JLabel("Total: ");
        t = new JTextField();
        t.setEditable(false);
        pbotoes.add(lbltot);
        pbotoes.add(t);
        pbotoes.add(confirmar);
        pbotoes.add(limpar);
        pbotoes.add(imprimir);
        pbotoes.add(novocliente);
        //Painel de pre impressao
        
        jTable1 = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        jTable1.setDefaultRenderer(Object.class, new CellRenderer());
        jTable1.setFocusable(false);
        jTable1.setRowSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quant.", "Produtos", "Valor Unit.", "Valor Total"
            }
        ));
        
        pimpr = new JScrollPane(jTable1);
        
        //Painel do JFrame
        this.setLayout(new BorderLayout());
        this.getContentPane().add(pGrid,BorderLayout.NORTH);
        this.getContentPane().add(pimpr,BorderLayout.CENTER);
        //this.getContentPane().add(tot);
        this.getContentPane().add(pbotoes,BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        this.setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("Imagens/icone.png")).getImage());
    }

    @Override
    public void actionPerformed(ActionEvent in) {
        Object o = in.getSource();
        
        if(o == confirmar){
            nomeproduto = nome.getText();
            nomeproduto = nomeproduto.substring(0,1).toUpperCase().concat(nomeproduto.substring(1));
            preçoproduto = Float.parseFloat(preço.getText().replace(",","."));
            quantidade = Integer.parseInt(qnt.getText());
            total = total + (preçoproduto*quantidade);
            nome.setText("");
            preço.setText("");
            qnt.setText("");
            t.setText(String.valueOf(total));
            nome.requestFocus();
            
            DefaultTableModel dtmProdutos = (DefaultTableModel) jTable1.getModel();
            Object[] dados = {quantidade,nomeproduto,preçoproduto,
                (preçoproduto*quantidade)};
            dtmProdutos.addRow(dados);
        }
        if(o == limpar){
            nome.setText("");
            preço.setText("");
            qnt.setText("");
            nome.requestFocus();
        }
        if(o == imprimir){
            String file = "vendas\\venda.xls";
            ExportExcel.toExcel2(jTable1, new File(file));
            try{
               Runtime.getRuntime().exec("cmd.exe /c start scalc.exe -pt \"MP-100S TH\" vendas\\venda.xls");
            }catch(IOException iOException){
                JOptionPane.showMessageDialog(null,"Ocorreu um erro ao imprimir","Erro",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(o == novocliente){
            this.dispose();
            total = 0;
            InterfGrafica interf = new InterfGrafica();
        }
    }
    public static float getTotal(){
        return total;
    }
}
