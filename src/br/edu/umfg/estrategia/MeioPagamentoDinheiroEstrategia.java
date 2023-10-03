package br.edu.umfg.estrategia;

public class MeioPagamentoDinheiroEstrategia implements
        MeioPagamentoEstrategia {
    @Override
    public void pagar(Double valor) {
        System.out.println("Pagamento via Dinheiro no valor:" +
                valor + " realizado com sucesso");
    }
}
