    package br.univille.ativchat.service;

    import br.univille.ativchat.model.Mensagem;
    import br.univille.ativchat.view.Form;

    public interface BrokerMensagemService {
        void enviarMensagem(Mensagem mensagem);
        void buscarMensagens(Form form);
    }
