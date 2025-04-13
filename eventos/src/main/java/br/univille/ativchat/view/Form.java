package br.univille.ativchat.view;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.univille.ativchat.controller.Controller;

public class Form extends JFrame{
    private JPanel jpnSul;
    private JScrollPane jpnCentro;
    private JTextArea txtChat;
    private JTextField txtNovaMsg;
    private JButton btnEnviar;
    private JButton btnBuscar;
    private String nome;
    private Controller controller = new Controller(this);

    public String getNome(){
        return this.nome;
    }
    public String getMensagem(){
        return txtNovaMsg.getText();
    }
    public void setMensagem(String msg){
        txtNovaMsg.setText(txtNovaMsg.getText() + "\n" + msg);
    }
    
    public Form() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CHAT");
        setSize(500,400);
        solicitarNome();

        criaJpnCentro();
        criaJpnSul();
        setVisible(true);
    }

    private void solicitarNome() {
        this.nome = javax.swing.JOptionPane.showInputDialog("Digite seu nome:");
        if (nome != null && !nome.trim().isEmpty()) {
            setTitle("CHAT - " + nome);
        } else {
            solicitarNome();
        }
    }
    private void criaJpnCentro() {
        txtChat = new JTextArea(10, 20);
        jpnCentro = new JScrollPane(txtChat);
        txtChat.setText("Bem-vindo ao chat!\n\n");	
        txtChat.setEditable(false);
        txtChat.setLineWrap(true);
        getContentPane().add(jpnCentro, "Center");
    }
    private void criaJpnSul() {
        jpnSul = new JPanel();
        jpnSul.setBorder(BorderFactory.createTitledBorder("Nova Mensagem"));
        jpnSul.setBounds(0, 350, 300, 50);
        txtNovaMsg = new JTextField(20);
        btnEnviar = new JButton("Enviar");
        btnBuscar = new JButton("🔁");
        jpnSul.add(txtNovaMsg);
        jpnSul.add(btnEnviar);
        jpnSul.add(btnBuscar);
        btnEnviar.addActionListener(controller);
        btnBuscar.addActionListener(controller); 
        getContentPane().add(jpnSul, "South");
    }

    public JButton getBtnEnviar() {
        return btnEnviar;
    }
    
    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    // criei esse pra adicionar as mensagens na janela do chat
    public void adicionarMensagemNoChat(String msg) {
        txtChat.append(msg + "\n");
    }

    // e esse pra fazer uma frescurinha visual
    public void adicionarLinhaSeparadoraNoChat(){
        txtChat.append("--------------------------------------------------------------------------------------------\n");
    }
}
