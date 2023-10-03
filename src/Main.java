import br.edu.umfg.estrategia.Carrinho;
import br.edu.umfg.estrategia.MeioPagamentoCieloCreditoEstrategia;
import br.edu.umfg.estrategia.MeioPagamentoCieloDebitoEstrategia;
import br.edu.umfg.estrategia.MeioPagamentoDinheiroEstrategia;
import br.edu.umfg.estrategia.Produto;

public class Main {
    public static void main(String[] args) {
        Produto produto1 = new Produto("0001", "Cola cola 350ml", 5.00);
        Produto produto2 = new Produto("0002", "X-salada", 21.00);
        Carrinho carrinho = new Carrinho();

        carrinho.adicionarProduto(produto1);
        carrinho.adicionarProduto(produto2);

        carrinho.pagar(new MeioPagamentoCieloCreditoEstrategia("5107694790846837", "10185301932", "281", "10/2032"));
        carrinho.pagar(new MeioPagamentoCieloDebitoEstrategia("5107694790846837", "10185301932", "281", "10/2032"));
        carrinho.pagar(new MeioPagamentoDinheiroEstrategia());
    }
}
