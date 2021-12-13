package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDeDados();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        Produto produto = produtoDao.buscarPorId(1l);

        ClienteDao clienteDao = new ClienteDao(em);
        Cliente cliente = clienteDao.buscarPorId(1l);

        em.getTransaction().begin();


        Pedido pedido = new Pedido(cliente);

        pedido.adicionarItem(new ItemPedido(10, pedido, produto));

        PedidoDao pedidoDao = new PedidoDao(em);
        pedidoDao.salvar(pedido);

        em.getTransaction().commit();

        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println("Valor total: " + totalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
        relatorio.forEach(System.out::println);
        
        em.close();

    }

    private static void popularBancoDeDados() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi", "Muito legal", new BigDecimal("800"), celulares);
        Cliente cliente = new Cliente("Rodrigo", "123456");

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);
        ClienteDao clienteDao = new ClienteDao(em);

        em.getTransaction().begin();

        categoriaDao.salvar(celulares);
        produtoDao.salvar(celular);
        clienteDao.salvar(cliente);

        em.getTransaction().commit();
        em.close();
    }
}
