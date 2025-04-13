package br.univille.ativchat.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.univille.ativchat.model.Mensagem;
import br.univille.ativchat.service.BrokerMensagemService;
import br.univille.ativchat.util.AppModule;
import br.univille.ativchat.view.Form;

public class Controller implements ActionListener {
    Injector injector = Guice.createInjector(new AppModule());
    BrokerMensagemService service = injector.getInstance(BrokerMensagemService.class);
    List<Mensagem> mensagens = new ArrayList<>();

    private Form form;

    public Controller(Form form) {
        this.form = form;
    }

    @Override
public void actionPerformed(ActionEvent e) {
    Object origem = e.getSource();

    if (origem == form.getBtnEnviar()) {
        form.adicionarLinhaSeparadoraNoChat();
        Mensagem mensagem = new Mensagem(form.getNome().toUpperCase(), form.getMensagem());
        // validações da mensagem 
        if(mensagem.texto().isEmpty()){
            form.adicionarMensagemNoChat("Mensagem vazia não pode ser enviada ❌");
            return;
        }
        if(mensagem.texto().length() > 1000){
            form.adicionarMensagemNoChat("Mensagem muito longa não pode ser enviada ❌");
            return;
        }
        service.enviarMensagem(mensagem);
        form.adicionarMensagemNoChat(form.getNome().toUpperCase() + ": " + form.getMensagem());
        form.setMensagem("");

    } else if (origem == form.getBtnBuscar()) {
        form.adicionarLinhaSeparadoraNoChat();
        form.adicionarMensagemNoChat("Aguardando mensagens...\n");
        form.getBtnBuscar().setEnabled(false);
        new Thread(() -> { // fiz isso pra parar de travar a tela, JPanel e Swing maldito!!!
            service.buscarMensagens(form); 
            form.getBtnBuscar().setEnabled(true);
        }).start();
        
    }
}

}
