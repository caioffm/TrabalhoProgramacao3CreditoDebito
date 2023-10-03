package br.edu.umfg.estrategia;

public class MeioPagamentoCieloEstrategia implements
        MeioPagamentoEstrategia{

    private String numeroCartao;
    private String cpf;
    private String cvv;
    private String dataValidade;

    public MeioPagamentoCieloEstrategia(String numeroCartao,
                                        String cpf, String cvv,
                                        String dataValidade) {

        this.numeroCartao = numeroCartao;
        this.cpf = cpf;
        this.cvv = cvv;
        this.dataValidade = dataValidade;
    }

    @Override
    public void pagar(Double valor) {
        System.out.printf("Pagamento via Cielo no valor:" +
                valor + " realizado com sucesso \n");
    }
}
