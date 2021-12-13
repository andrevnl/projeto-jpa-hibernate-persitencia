package br.com.alura.loja.testes;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);

        Produto p = produtoDao.buscarPorId(1l);
        System.out.println(p.getPreco());

        final List<Produto> todos = produtoDao.buscarTodos();
        final List<Produto> porNome = produtoDao.buscarPorNome("Xiaomi");
        final List<Produto> porCategoria = produtoDao.buscarPorNomeDaCategoria("CELULARES");
        final BigDecimal precoDoProduto = produtoDao.buscarPrecoDoProdutoComNome("Xiaomi");
        todos.forEach(produto -> System.out.println(produto.getNome()));
        porNome.forEach(produto -> System.out.println(produto.getNome()));
        porCategoria.forEach(produto -> System.out.println(produto.getNome()));
        System.out.println("Preço " + precoDoProduto);
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");
        Produto celular = new Produto("Xiaomi", "Muito legal", new BigDecimal("800"), celulares);

        EntityManager em = JPAUtil.getEntityManager();
        ProdutoDao produtoDao = new ProdutoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();
        categoriaDao.salvar(celulares);
        produtoDao.salvar(celular);
        em.getTransaction().commit();
        em.close();
    }
}
