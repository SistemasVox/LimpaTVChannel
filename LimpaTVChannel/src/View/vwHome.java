package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vwHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel lblA, lblQ;
    private JButton bntMontar, btnLimpar, btnSalvar;
    private static JTextArea txtArea;
    private JTextArea txtRefe;
    private JTextArea txtEntrada;
    private JScrollPane scrollQ, scrollA, scrollArea;
    //private String[] ae = {"A", "B", "C", "D", "E"};
    //private String[] eliminar = {" ", ")"};
    private String referencia = "";
    private ArrayList<String> listaCanais = new ArrayList<String>();
    private JButton btnSair;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    vwHome frame = new vwHome();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public vwHome() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 937, 722);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        lblQ = new JLabel("Q:");
        lblQ.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQ.setBounds(851, 19, 63, 14);
        contentPane.add(lblQ);
        lblA = new JLabel("A:");
        lblA.setHorizontalAlignment(SwingConstants.RIGHT);
        lblA.setBounds(851, 43, 63, 14);
        contentPane.add(lblA);
        JLabel lblInsertQuestesSistemasvox = new JLabel("Limpa Listas TV Channers, Sistema VOX.");
        lblInsertQuestesSistemasvox.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		lerReferencia();
        		txtRefe.setText(referencia);
        	}
        });
        lblInsertQuestesSistemasvox.setBounds(76, -5, 704, 57);
        lblInsertQuestesSistemasvox.setHorizontalAlignment(SwingConstants.CENTER);
        lblInsertQuestesSistemasvox.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
        contentPane.add(lblInsertQuestesSistemasvox);
        txtArea = new JTextArea();
        txtArea.setEditable(false);
        txtArea.setWrapStyleWord(true);
        txtArea.setToolTipText("Quest\u00E3o Montada.");
        txtArea.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
        txtArea.setLineWrap(true);
        contentPane.add(this.scrollArea = new JScrollPane(txtArea));
        scrollArea.setBounds(10, 357, 713, 316);
        txtRefe = new JTextArea();
        txtRefe.setLineWrap(true);
        txtRefe.setWrapStyleWord(true);
        txtRefe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                bloquearBotao();
            }
        });
        txtRefe.setFont(new Font("Monospaced", Font.ITALIC, 14));
        txtRefe.setToolTipText("Search for:");
        contentPane.add(this.scrollQ = new JScrollPane(txtRefe));
        scrollQ.setBounds(10, 60, 907, 118);
        txtEntrada = new JTextArea();
        txtEntrada.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bloquearBotao();
            }
        });
        txtEntrada.setFont(new Font("Monospaced", Font.BOLD | Font.ITALIC, 14));
        txtEntrada.setLineWrap(true);
        txtEntrada.setWrapStyleWord(true);
        txtEntrada.setToolTipText("Cole aqui o TEXTO Bruto:");
        contentPane.add(this.scrollA = new JScrollPane(txtEntrada));
        scrollA.setBounds(10, 181, 907, 165);
        btnSalvar = new JButton("Tentativa SIMPLES");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                limparSPRO();
            }
        });
        btnLimpar = new JButton("Limpar Campos");
        btnLimpar.setBounds(752, 356, 159, 46);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                limpar();
            }
        });
        contentPane.add(btnLimpar);
        bntMontar = new JButton("Montar Lista");
        bntMontar.setBounds(752, 408, 159, 46);
        bntMontar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	limparS();
            }
        });
        contentPane.add(bntMontar);
        btnSalvar.setBounds(752, 460, 159, 46);
        contentPane.add(btnSalvar);
        JButton btnListar = new JButton("Tentativa BRUTA");
        btnListar.setEnabled(false);
        btnListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        btnListar.setBounds(752, 513, 159, 46);
        contentPane.add(btnListar);
        JButton btnExpor = new JButton("Exportar HTML");
        btnExpor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportarHTML();
            }
        });
        btnExpor.setBounds(752, 570, 159, 46);
        contentPane.add(btnExpor);
        btnSair = new JButton("Sair");
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                salvarReferencia();
            }
        });
        btnSair.setBounds(752, 627, 159, 46);
        contentPane.add(btnSair);
        bloquearBotao();
		lerReferencia();
		txtRefe.setText(referencia.trim());
    }

    protected void salvarReferencia() {
    	if (txtRefe.getText().trim().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Entrada vazia, não houve modificação na referencia.");
		} else {
    		try {
    			PrintWriter pw = new PrintWriter(new File("referencia.txt"));
    			StringBuilder sb = new StringBuilder();			
    			sb.append(txtRefe.getText().trim());			
    			pw.write(sb.toString());
    			pw.close();

    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
		}
	}

	protected void lerReferencia() {
		try {
			FileReader arq = new FileReader("referencia.txt");
			BufferedReader lerArq = new BufferedReader(arq);
			
			String linha = lerArq.readLine();
			while (linha != null) {
		        System.out.printf("%s\n", linha);
		        referencia += "\n"+ linha;
		        linha = lerArq.readLine();
		      }
			
		} catch (Exception e) {
			txtRefe.setText(e.getMessage());
		}
		
	}

	protected void limparSPRO() {
    	listaCanais.clear();
    	String[] eliminar = txtEntrada.getText().split("m3u");
    	for (int i = 0; i < eliminar.length; i++) {
			listaCanais.add(eliminar[i]+"m3u");
		}
    	montarLista();				
	}

	protected void exportarHTML() {
        ArrayList<String> canaisHTML = new ArrayList<String>();
        for (int i = 0; i < listaCanais.size(); i++) {
            canaisHTML.add("<br><a href=\"" + listaCanais.get(i) + "\">" + pegaNomeLista(listaCanais.get(i)) + "</a></br>\n");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < canaisHTML.size(); i++) {
            sb.append(canaisHTML.get(i));
        }
        try {
            PrintWriter pw = new PrintWriter(new File("ListaLimpa.html"));
            pw.write(sb.toString());
            pw.close();
            File arquivo = new File("ListaLimpa.html");
            txtArea.setText(arquivo.getAbsoluteFile() + "");
        } catch (Exception e) {
            txtArea.setText(e.getMessage());
        }
    }

    private String pegaNomeLista(String link) {
        int c = procurarPosicao(link, "=");
        int f = procurarPosicao(link, "&");
        return link.substring(c, f);
    }

    private int procurarPosicao(String link, String string) {
        for (int i = 0; i < link.length(); i++) {
            if (link.substring(i, i + 1).equals(string.toString())) {
                return i + 1;
            }
        }
        return 0;
    }

    protected void limparS() {
        String[] alternativas;
        listaCanais.clear();
        alternativas = txtEntrada.getText().split(Pattern.quote("\n"));
        for (int i = 0; i < alternativas.length; i++) {
            listaCanais.add(alternativas[i]);
        }
        montarLista();
    }

    protected void montarLista() {
        String txt = "";
        for (int i = 0; i < listaCanais.size(); i++) {
            txt += (i + 1) + ") " + listaCanais.get(i) + "\n";
        }
        txtArea.setText(txt);
    }

    static void exportarCSV(String separador, String texto) {
        File arquivo = new File("Questões.csv");
        File arquivo2 = new File("Alternativas.csv");
        if (arquivo.exists() && arquivo2.exists()) {
            JOptionPane.showMessageDialog(null, "Exportação realizada com sucesso, verifique no caminho:\n" + arquivo.getAbsoluteFile() + "\n" + arquivo2.getAbsoluteFile());
            txtArea.setText("Exportação realizada com sucesso, verifique no caminho:\n" + arquivo.getAbsoluteFile() + "\n" + arquivo2.getAbsoluteFile());
        } else {
            JOptionPane.showMessageDialog(null, "Erro na exportação" + arquivo.getAbsoluteFile() + "\n" + arquivo2.getAbsoluteFile());
        }
    }

    private void bloquearBotao() {
    }

    protected void limpar() {
        //txtEntrada.setText("");
        txtRefe.setText("");
        txtArea.setText("");
        bloquearBotao();
    }
}
